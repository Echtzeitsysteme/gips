package org.emoflon.gips.eclipse.connector;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Predicate;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.SelectionListenerFactory;
import org.eclipse.ui.SelectionListenerFactory.ISelectionModel;
import org.emoflon.gips.eclipse.TracePlugin;
import org.emoflon.gips.eclipse.api.ITraceContext;
import org.emoflon.gips.eclipse.api.ITraceManager;
import org.emoflon.gips.eclipse.api.TraceModelNotFoundException;
import org.emoflon.gips.eclipse.api.event.ITraceSelectionListener;
import org.emoflon.gips.eclipse.api.event.TraceSelectionEvent;
import org.emoflon.gips.eclipse.listener.PartSelectionFilterListener;

/**
 * This class provides a basic implementation to (dis)connect an editor and
 * manage selection events.
 * 
 * @param <T> type of editor
 */
public abstract class EditorTraceConnection<T extends IEditorPart> implements IEditorTraceConnection {

	public static class Predicates {
		public static Predicate<ISelectionModel> selectionNotSeenYet = SelectionListenerFactory.Predicates.alreadyDeliveredAnyPart;
		public static Predicate<ISelectionModel> fromSelf = model -> model.getCurrentSelectionPart() == model
				.getTargetPart();
	}

	protected final EditorTraceJob editorTraceJob = new EditorTraceJob();
	protected final T editor;
	protected final IHighlightStrategy<? super T> highlightStrategy;

	private boolean isInstalled = false;

	private IPartListener2 partListener;
	private IPropertyListener propertyListener;
	private ISelectionListener partSelectionListener;
	private ITraceSelectionListener traceSelectionListener;

	private String modelId = "";
	private String contextId = "";

	public EditorTraceConnection(T editor, IHighlightStrategy<? super T> highlightStrategy) {
		this.editor = Objects.requireNonNull(editor, "editor");
		this.highlightStrategy = Objects.requireNonNull(highlightStrategy, "highlightStrategy");
		computeContextAndModelId();
	}

	protected void onEditorInputChange() {
		computeContextAndModelId();
	}

	@Override
	public boolean isConnected() {
		return isInstalled;
	}

	@Override
	public void connect() {
		if (isConnected())
			return;

		this.partListener = buildPartListener();
		this.propertyListener = buildPropertyListener();
		this.partSelectionListener = buildPartSelectionListener();
		this.traceSelectionListener = buildTraceSelectionListener();

		editor.addPropertyListener(propertyListener);
		editor.getSite().getService(IPartService.class).addPartListener(partListener);
		editor.getSite().getService(ISelectionService.class).addPostSelectionListener(partSelectionListener);
		ITraceManager.getInstance().getContext(getContextId()).addListener(traceSelectionListener);

		isInstalled = true;
	}

	@Override
	public void disconnect() {
		if (!isConnected())
			return;

		editor.removePropertyListener(propertyListener);
		editor.getSite().getService(IPartService.class).removePartListener(partListener);
		editor.getSite().getService(ISelectionService.class).removePostSelectionListener(partSelectionListener);
		ITraceManager.getInstance().removeListener(traceSelectionListener);

		this.partListener = null;
		this.propertyListener = null;
		this.partSelectionListener = null;
		this.traceSelectionListener = null;

		isInstalled = false;
	}

	private IPartListener2 buildPartListener() {
		return new IPartListener2() {
			@Override
			public void partClosed(IWorkbenchPartReference partRef) {
				if (partRef.getPart(false) == editor)
					disconnect();
			}

			@Override
			public void partVisible(IWorkbenchPartReference partRef) {
				if (partRef.getPart(false) == editor)
					onPartVisible();
			}
		};
	}

	private IPropertyListener buildPropertyListener() {
		return (source, propId) -> {
			if (IEditorPart.PROP_INPUT == propId) {
				onEditorInputChange();
				ITraceManager.getInstance().removeListener(traceSelectionListener);
				ITraceManager.getInstance().getContext(getContextId()).addListener(traceSelectionListener);
			}
		};
	}

	private ISelectionListener buildPartSelectionListener() {
		return new PartSelectionFilterListener(editor, (part, selection) -> this.onEditorSelection(selection),
				Predicates.fromSelf.and(Predicates.selectionNotSeenYet));
	}

	private ITraceSelectionListener buildTraceSelectionListener() {
		return event -> {
			onTraceSelection(event);
		};
	}

	private void onPartVisible() {
		// TODO Auto-generated method stub

	}

	/**
	 * Called when the selection on the connected editor changes. Must be passed to
	 * the trace manager.
	 * 
	 * @param selection editor selection, may be null
	 */
	private void onEditorSelection(ISelection selection) {
		ITraceManager service = TracePlugin.getInstance().getContextManager();
		if (!service.isVisualisationActive())
			return;

		if (!service.doesContextExist(getContextId()))
			return;

		ITraceContext context = service.getContext(getContextId());
		if (!context.hasTraceFor(getModelId())) {
			return;
		}

		removeEditorHighlights();

		sendEditorSelectionToTraceManager(context, selection);
	}

	/**
	 * @param context   current context for this editor
	 * @param selection current editor selection, may be null
	 */
	private void sendEditorSelectionToTraceManager(ITraceContext context, ISelection selection) {
		Collection<String> elementIds = Collections.emptySet();
		if (!selection.isEmpty()) {
			elementIds = transformSelectionToElementIds(selection);
		}

		try {
			context.selectElementsByTrace(getModelId(), elementIds);
		} catch (TraceModelNotFoundException e) {
			// TODO: maybe there is some way to recover
//			if (showNoTraceModelError) {
//				showNoTraceModelError = false;
//				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "GIPS Trace",
//						e.getMessage());
//			}
			e.printStackTrace();
		}
	}

	/*
	 * Called by the trace manager
	 */
	private void onTraceSelection(TraceSelectionEvent event) {
		if (getModelId().equals(event.getModelId()))
			return;

		if (!editor.getSite().getPage().isPartVisible(editor))
			return;

		if (!event.getContext().hasTraceFor(getModelId()))
			return;

		if (!TracePlugin.getInstance().getContextManager().isVisualisationActive())
			return;

		if (event.getElementIds().isEmpty()) {
			removeEditorHighlights();
		} else {
			computeEditorHighlights(event.getContext(), event.getModelId(), event.getElementIds());
		}
	}

	private void computeEditorHighlights(ITraceContext context, String remoteModelId,
			Collection<String> remoteElementsById) {
		editorTraceJob.cancel();
		editorTraceJob.setup(monitor -> highlightStrategy.computeEditorHighlights(editor, context, getModelId(),
				remoteModelId, remoteElementsById, monitor));
		editorTraceJob.setPriority(Job.DECORATE);
		editorTraceJob.schedule();
	}

	private void removeEditorHighlights() {
		editorTraceJob.cancel();
		editorTraceJob.setup(monitor -> highlightStrategy.removeEditorHighlights(editor, monitor));
		editorTraceJob.setPriority(Job.DECORATE);
		editorTraceJob.schedule();
	}

	public T getEditor() {
		return editor;
	}

	public IHighlightStrategy<? super T> getHighlightStrategy() {
		return highlightStrategy;
	}

	/**
	 * The context id, which is usually the name of the eclipse project in which the
	 * model is located. If there is no context with this id, the connection will
	 * not process any selection events.
	 * 
	 * @return context id, never null
	 */
	public String getContextId() {
		return contextId;
	}

	/**
	 * Sets the current context id for this editor.
	 * 
	 * @param contextId, if there is no context set to empty string, may not be null
	 */
	protected void setContextId(String contextId) {
		this.contextId = Objects.requireNonNull(contextId, "contextId");
	}

	/**
	 * The id of the model, which is displayed by this editor. If there is no model
	 * with this id, the connection will not process any selection events.
	 * 
	 * @return a model id, never null
	 */
	public String getModelId() {
		return modelId;
	}

	/**
	 * Sets the current model id for this editor.
	 * 
	 * @param modelId if there is no model set to empty string, may not be null
	 */
	protected void setModelId(String modelId) {
		this.modelId = Objects.requireNonNull(modelId, "modelId");
	}

	/**
	 * This method is called automatically on object instantiation and when the
	 * editor input changes. Compute context and model id and set them using
	 * {@link #setContextId(String)} and {@link #setModelId(String)}.
	 */
	protected abstract void computeContextAndModelId();

	/**
	 * Converts a selection of elements into a set of ids based on the model
	 * presented by the editor. Each id should uniquely identify one of the selected
	 * elements.
	 * 
	 * @param selection A selection within the editor
	 * @return A set of ids. The collection can be empty but not null
	 */
	protected abstract Collection<String> transformSelectionToElementIds(ISelection selection);

}