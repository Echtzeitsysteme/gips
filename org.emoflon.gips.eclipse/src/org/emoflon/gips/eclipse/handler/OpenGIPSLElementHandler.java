package org.emoflon.gips.eclipse.handler;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;
import org.emoflon.gips.eclipse.TracePlugin;
import org.emoflon.gips.eclipse.api.IModelLink;
import org.emoflon.gips.eclipse.api.ITraceContext;
import org.emoflon.gips.eclipse.api.ITraceManager;

public class OpenGIPSLElementHandler extends AbstractHandler {

	private static final String JAVA_MODEL_ID = OpenGeneratedJavaFileHandler.JAVA_MODEL_ID;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IResource resource = getResource(event);
		IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindowChecked(event).getActivePage();

		Job job = new Job(getJobName(event, "Show GIPS")) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				if (resource == null)
					return Status.error("Unable to identify resource");
				if (resource.getProject() == null)
					return Status.error("Unable to identify eclipse project for given resource.");
				return showGIPSLElement(page, resource.getProject(), resource.getProjectRelativePath(), monitor);
			}
		};
		job.setPriority(Job.SHORT);
		job.setSystem(false);
		job.setUser(true);
		job.schedule();

		return null;
	}

	private IResource getResource(ExecutionEvent event) throws ExecutionException {
		Object selection = HandlerUtil.getCurrentSelection(event);

		if (selection instanceof IStructuredSelection structuredSelection)
			selection = structuredSelection.getFirstElement();

		if (selection instanceof IAdaptable adaptable) {
			IResource resource = adaptable.getAdapter(IResource.class);
			if (resource != null)
				return resource;
		}

		if (selection instanceof IResource resource)
			return resource;

		IEditorPart part = HandlerUtil.getActiveEditor(event);
		if (part != null && part.getEditorInput() != null) {
			IResource resource = part.getEditorInput().getAdapter(IResource.class);
			if (resource != null)
				return resource;
		}

		return null;
	}

	private String getJobName(ExecutionEvent event, String defaultName) {
		try {
			return event.getCommand().getName();
		} catch (NotDefinedException e) {
			return defaultName;
		}
	}

	private IStatus showGIPSLElement(IWorkbenchPage page, IProject project, IPath javaFile, IProgressMonitor monitor) {
		if (!ITraceManager.getInstance().doesContextExist(project.getName()))
			return Status.error(
					"No generated files were found. Ensure that tracing is enabled and re-save the GIPSL specification.");

		ITraceContext context = ITraceManager.getInstance().getContext(project.getName());
		Collection<String> gipslModels = context.getAllModels().stream() //
				.filter(e -> e.toLowerCase().endsWith(".gipsl")) //
				.toList();

		Collection<String> javaElements = Collections.singleton(javaFile.toPortableString());
		boolean foundAny = false;

		Collection<IStatus> aggregatedStatuses = new LinkedList<>();
		for (String gipslModelId : gipslModels) {
			IModelLink link = context.getModelChain(JAVA_MODEL_ID, gipslModelId);
			if (!link.isResolved())
				continue;

			Collection<String> resolvedElements = link.resolveElementsFromSrcToDst(javaElements);
			if (resolvedElements.isEmpty())
				continue;

			IPath path = IPath.fromPath(Path.of(gipslModelId).normalize());
			IFile file = project.getFile(path);

			Display.getDefault().syncExec(() -> {
				try {
					IDE.openEditor(page, file, true);
				} catch (final PartInitException ex) {
					aggregatedStatuses.add(Status.error(String.format("Unable to open editor for: %s", file), ex));
				}
			});

			context.selectElementsByTrace(JAVA_MODEL_ID, javaElements);
			foundAny = true;
		}

		if (!foundAny)
			return Status.error(String.format("Unable to find any GIPSL element for %s", javaFile));

		if (aggregatedStatuses.isEmpty())
			return Status.OK_STATUS;

		String errorMessage = String.format("%d Problem(s) for java file %s", aggregatedStatuses.size(), javaFile);
		MultiStatus status = new MultiStatus(TracePlugin.PLUGIN_ID, IStatus.ERROR, errorMessage);
		for (IStatus e : aggregatedStatuses)
			status.add(e);

		return status;
	}

}
