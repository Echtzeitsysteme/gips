package org.emoflon.gips.eclipse.codemining;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.codemining.ICodeMining;
import org.eclipse.jface.text.codemining.ICodeMiningProvider;
import org.eclipse.jface.text.codemining.LineContentCodeMining;
import org.eclipse.jface.text.source.ISourceViewerExtension5;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextSourceViewer;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.emoflon.gips.eclipse.TracePlugin;
import org.emoflon.gips.eclipse.cplexLp.ConstraintExpression;
import org.emoflon.gips.eclipse.cplexLp.VariableDecleration;
import org.emoflon.gips.eclipse.cplexLp.VariableRef;
import org.emoflon.gips.eclipse.pref.PluginPreferences;
import org.emoflon.gips.eclipse.service.ContextManager;
import org.emoflon.gips.eclipse.service.ProjectContext;
import org.emoflon.gips.eclipse.service.event.IModelValueListener;
import org.emoflon.gips.eclipse.utility.HelperEclipse;

public class VariableValueCodeMiningProvider implements ICodeMiningProvider {

	private IModelValueListener listener;
	private String contextId;
	private String modelId;

	public VariableValueCodeMiningProvider() {
		// created for every editor
	}

	@Override
	public CompletableFuture<List<? extends ICodeMining>> provideCodeMinings(ITextViewer viewer,
			IProgressMonitor progressMonitor) {

		if (!(viewer instanceof ISourceViewerExtension5 codeMiningViewer))
			throw new IllegalArgumentException("Cannot attach to TextViewer without code mining support"); //$NON-NLS-1$

		if (!(viewer instanceof XtextSourceViewer xtextViewer))
			throw new IllegalArgumentException("Can only attach to XtextSourceViewer"); //$NON-NLS-1$

		if (!isEnabled())
			return CompletableFuture.completedFuture(Collections.emptyList());

		CompletableFuture<List<? extends ICodeMining>> task = CompletableFuture.supplyAsync(() -> {
			SubMonitor monitor = SubMonitor.convert(progressMonitor);

			if (listener == null) {
				URI uri = xtextViewer.getXtextDocument().getResourceURI();
				IProject project = HelperEclipse.tryAndGetProject(uri);
				if (project == null)
					return Collections.emptyList(); // no context id, no support
				IPath relativeFilePath = HelperEclipse.getProjectRelativPath(project, uri);

				contextId = project.getName();
				modelId = relativeFilePath.toString();

				listener = event -> {
					if (event.getModelId().equals(modelId))
						codeMiningViewer.updateCodeMinings();
				};
				TracePlugin.getInstance().getContextManager().getContext(contextId).addListener(listener);
			}

			List<? extends ICodeMining> result = xtextViewer.getXtextDocument()
					.readOnly(resource -> provideCodeMinings(xtextViewer.getXtextDocument(), resource, monitor));

			if (result == null)
				return Collections.emptyList();

			progressMonitor.isCanceled();
			return result;
		});

		return task;
	}

	private boolean isEnabled() {
		return PluginPreferences.getPreferenceStore().getBoolean(PluginPreferences.PREF_CODE_MINING_ENABLED);
	}

	// TODO: Code minings interact with annotations. They move character and line
	// positions. It seems that they are also deleted when annotations are deleted?
	private List<? extends ICodeMining> provideCodeMinings(IXtextDocument document, XtextResource resource,
			SubMonitor monitor) {

		ContextManager contextManager = TracePlugin.getInstance().getContextManager();
		ProjectContext context = contextManager.getContext(contextId);
		var map = context.getModelValues(modelId);
		if (map.isEmpty())
			return Collections.emptyList();

		List<ICodeMining> result = new LinkedList<>();
		TreeIterator<EObject> iterator = resource.getAllContents();
		while (iterator.hasNext() && !monitor.isCanceled()) {
			EObject eObject = iterator.next();
			String name = getNameOf(eObject);
			if (name == null || !map.containsKey(name))
				continue;

			ICompositeNode textNode = NodeModelUtils.findActualNodeFor(eObject);
			int position = textNode.getEndOffset();

			String value = " " + map.get(name) + "";
			result.add(createLineContentCodeMining(position, value, null));
		}

		return result;
	}

	private String getNameOf(EObject eObject) {
		return switch (eObject) {
		case VariableDecleration vd -> vd.getName();
		case VariableRef vr -> vr.getRef().getName();
		case ConstraintExpression ce -> ce.getName();
		default -> null;
		};
	}

	private LineContentCodeMining createLineContentCodeMining(int beforeCharacter, String text,
			Consumer<MouseEvent> action) {
		return new LineContentCodeMining(new Position(beforeCharacter, text.length()), this, action) {
			@Override
			protected CompletableFuture<Void> doResolve(ITextViewer viewer, IProgressMonitor monitor) {
				return CompletableFuture.runAsync(() -> {
					super.setLabel(text);
				});
			}
		};
	}

	@Override
	public void dispose() {
		ContextManager contextManager = TracePlugin.getInstance().getContextManager();
		if (listener != null && contextManager.doesContextExist(contextId))
			contextManager.getContext(contextId).removeListener(listener);
		listener = null;
	}

}
