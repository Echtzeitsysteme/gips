package org.emoflon.gips.eclipse.handler;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.emoflon.gips.eclipse.TracePlugin;
import org.emoflon.gips.eclipse.api.IModelLink;
import org.emoflon.gips.eclipse.api.ITraceContext;
import org.emoflon.gips.eclipse.api.ITraceManager;
import org.emoflon.gips.eclipse.api.ITraceSelection;

public class OpenGeneratedJavaFileHandler extends AbstractHandler {

	public static final String JAVA_MODEL_ID = "java";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindowChecked(event).getActivePage();
		IProject project = getProject(event);

		Job job = new Job("Open generated file") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				if (project == null)
					return Status.error("Unable to identify eclipse project for given editor.");

				return openFiles(page, project, monitor);
			}
		};
		job.setPriority(Job.SHORT);
		job.setSystem(false);
		job.setUser(true);
		job.schedule();

		return null;
	}

	private IStatus openFiles(IWorkbenchPage page, IProject project, IProgressMonitor monitor) {
		if (!ITraceManager.getInstance().doesContextExist(project.getName()))
			return Status.error(
					"No generated files were found. Ensure that tracing is enabled and re-save the GIPSL specification.");

		ITraceContext context = ITraceManager.getInstance().getContext(project.getName());
		ITraceSelection traceSelection = context.getSelectedElements();
		IModelLink link = context.getModelChain(traceSelection.getModelId(), JAVA_MODEL_ID);

		Collection<IStatus> aggregatedStatuses = new LinkedList<>();
		for (String selectedElement : traceSelection.getElementIds()) {
			Collection<String> generatedFiles = link
					.resolveElementsFromSrcToDst(Collections.singleton(selectedElement));

			if (generatedFiles.isEmpty()) {
				aggregatedStatuses.add(Status.error(String.format("No generated file found for: %s", selectedElement)));
				continue;
			}

			Display.getDefault().syncExec(() -> {
				for (String fileString : generatedFiles) {
					IPath path = IPath.fromPath(Path.of(fileString).normalize());
					IFile file = project.getFile(path);

					try {
						IDE.openEditor(page, file, true);
					} catch (final PartInitException ex) {
						aggregatedStatuses.add(Status.error(String.format("Unable to open editor for: %s", file), ex));
					}
				}
			});
		}

		if (aggregatedStatuses.isEmpty())
			return Status.OK_STATUS;

		String errorMessage = String.format("Unable to open generated files for %d selection(s)",
				aggregatedStatuses.size());
		MultiStatus status = new MultiStatus(TracePlugin.PLUGIN_ID, IStatus.ERROR, errorMessage);

		for (IStatus e : aggregatedStatuses)
			status.add(e);
		return status;
	}

	private IProject getProject(ExecutionEvent event) throws ExecutionException {
		IEditorPart editor = HandlerUtil.getActiveEditorChecked(event);
		if (!(editor instanceof XtextEditor xtextEditor))
			return null;

		IResource resource = xtextEditor.getResource();
		if (resource == null)
			return null;

		return resource.getProject();
	}

}
