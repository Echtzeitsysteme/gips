package org.emoflon.gips.eclipse.codemining;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.codemining.ICodeMining;
import org.eclipse.jface.text.codemining.ICodeMiningProvider;
import org.eclipse.jface.text.source.ISourceViewerExtension5;
import org.eclipse.jface.util.IPropertyChangeListener;
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

	private XtextSourceViewer viewer;
	private IModelValueListener modelListener;
	private IPropertyChangeListener propertyListener;

	private String contextId;
	private String modelId;

	public VariableValueCodeMiningProvider() {
		// created for every editor
	}

	@Override
	public CompletableFuture<List<? extends ICodeMining>> provideCodeMinings(ITextViewer viewer,
			IProgressMonitor progressMonitor) {

		if (!(viewer instanceof ISourceViewerExtension5))
			throw new IllegalArgumentException("Can only attach to TextViewer with code mining support"); //$NON-NLS-1$

		if (!(viewer instanceof XtextSourceViewer xtextViewer))
			throw new IllegalArgumentException("Can only attach to XtextSourceViewer"); //$NON-NLS-1$

		if (this.viewer == null)
			this.viewer = xtextViewer;

		CompletableFuture<List<? extends ICodeMining>> task = CompletableFuture.supplyAsync(() -> {
			SubMonitor monitor = SubMonitor.convert(progressMonitor);

			registerListeners();
			if (!isEnabled())
				return Collections.emptyList();

			List<? extends ICodeMining> result = xtextViewer.getXtextDocument()
					.readOnly(resource -> provideCodeMinings(xtextViewer.getXtextDocument(), resource, monitor));

			return result != null ? result : Collections.emptyList();
		});

		return task;
	}

	private XtextSourceViewer getXtextSourceViewer() {
		return viewer;
	}

	private ISourceViewerExtension5 getCodeMiningViewer() {
		return viewer;
	}

	private boolean isEnabled() {
		return PluginPreferences.getPreferenceStore().getBoolean(PluginPreferences.PREF_CODE_MINING_ENABLED);
	}

	private void registerListeners() {
		if (modelListener != null)
			return;

		URI uri = getXtextSourceViewer().getXtextDocument().getResourceURI();
		IProject project = HelperEclipse.tryAndGetProject(uri);
		if (project == null)
			return; // no context id, no support
		IPath relativeFilePath = HelperEclipse.getProjectRelativPath(project, uri);

		contextId = project.getName();
		modelId = relativeFilePath.toString();

		modelListener = event -> {
			if (event.getModelIds().contains(modelId) && isEnabled()) {
				getCodeMiningViewer().updateCodeMinings();
			}
		};
		TracePlugin.getInstance().getContextManager().getContext(contextId).addListener(modelListener);

		propertyListener = event -> {
			if (PluginPreferences.PREF_CODE_MINING_ENABLED.equals(event.getProperty())) {
				getCodeMiningViewer().updateCodeMinings();
			}
		};
		PluginPreferences.getPreferenceStore().addPropertyChangeListener(propertyListener);
	}

	private List<? extends ICodeMining> provideCodeMinings(IXtextDocument document, XtextResource resource,
			SubMonitor monitor) {

		ContextManager contextManager = TracePlugin.getInstance().getContextManager();
		ProjectContext context = contextManager.getContext(contextId);
		Map<String, String> map = context.getModelValues(modelId);
		if (map.isEmpty())
			return Collections.emptyList();

		List<ICodeMining> result = new LinkedList<>();
		TreeIterator<EObject> iterator = resource.getAllContents();
		while (iterator.hasNext() && !monitor.isCanceled()) {
			EObject eObject = iterator.next();
			String name = getNameOf(eObject);
			String value = map.get(name);
			if (value == null || value.isBlank())
				continue;

			String text = String.format(" (%s)", value);
			ICompositeNode textNode = NodeModelUtils.findActualNodeFor(eObject);
			result.add(new VariableValueCodeMining(textNode.getEndOffset(), text, this));
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

	@Override
	public void dispose() {
		PluginPreferences.getPreferenceStore().removePropertyChangeListener(propertyListener);
		propertyListener = null;

		ContextManager contextManager = TracePlugin.getInstance().getContextManager();
		if (modelListener != null && contextManager.doesContextExist(contextId))
			contextManager.getContext(contextId).removeListener(modelListener);
		modelListener = null;
	}

}
