package org.emoflon.gips.debugger.imp.connector;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Predicate;

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
import org.emoflon.gips.debugger.Activator;
import org.emoflon.gips.debugger.api.ITraceContext;
import org.emoflon.gips.debugger.api.ITraceManager;
import org.emoflon.gips.debugger.api.ITraceSelectionListener;
import org.emoflon.gips.debugger.api.TraceModelNotFoundException;
import org.emoflon.gips.debugger.listener.PartSelectionFilterListener;

/**
 * This class provides a basic implementation to (dis)connect an editor and
 * manage selection events.
 * 
 * @param <T> type of editor
 */
abstract class EditorTraceConnection<T extends IEditorPart> implements IEditorTraceConnection {

	public static class Predicates {
		public static Predicate<ISelectionModel> selectionNotSeenYet = SelectionListenerFactory.Predicates.alreadyDeliveredAnyPart;
		public static Predicate<ISelectionModel> fromSelf = model -> model.getCurrentSelectionPart() == model
				.getTargetPart();
	}

	protected final T editor;

	private boolean isInstalled = false;

	private IPartListener2 partListener;

	private IPropertyListener propertyListener;

	private ISelectionListener partSelectionListener;

	private ITraceSelectionListener traceSelectionListener;

	protected boolean pauseEditorSelection = false;
	protected boolean pauseTraceSelection = false;

	protected boolean showNoTraceModelError = true;

	public EditorTraceConnection(T editor) {
		this.editor = Objects.requireNonNull(editor, "editor");
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
		if (isConnected()) {
			return;
		}

		this.partListener = buildPartListener();
		this.propertyListener = buildPropertyListener();
		this.partSelectionListener = buildPartSelectionListener();
		this.traceSelectionListener = buildTraceSelectionListener();

		editor.addPropertyListener(propertyListener);
		editor.getSite().getService(IPartService.class).addPartListener(partListener);
		editor.getSite().getService(ISelectionService.class).addPostSelectionListener(partSelectionListener);
		Activator.getInstance().getTraceManager().addListener(getContextId(), traceSelectionListener);

		isInstalled = true;
	}

	@Override
	public void disconnect() {
		if (!isConnected()) {
			return;
		}

		editor.removePropertyListener(propertyListener);
		editor.getSite().getService(IPartService.class).removePartListener(partListener);
		editor.getSite().getService(ISelectionService.class).removePostSelectionListener(partSelectionListener);
		Activator.getInstance().getTraceManager().removeListener(traceSelectionListener);

		this.partListener = null;
		this.propertyListener = null;
		this.partSelectionListener = null;
		this.traceSelectionListener = null;

		isInstalled = false;
	}

	protected IPartListener2 buildPartListener() {
		return new IPartListener2() {
			@Override
			public void partClosed(IWorkbenchPartReference partRef) {
				if (partRef.getPart(false) == editor) {
					disconnect();
				}
			}
		};
	}

	protected IPropertyListener buildPropertyListener() {
		return (source, propId) -> {
			if (IEditorPart.PROP_INPUT == propId) {
				onEditorInputChange();
				Activator.getInstance().getTraceManager().removeListener(traceSelectionListener);
				Activator.getInstance().getTraceManager().addListener(getContextId(), traceSelectionListener);
			}
		};
	}

	protected ISelectionListener buildPartSelectionListener() {
		return new PartSelectionFilterListener(editor, (part, selection) -> this.onEditorSelection(selection),
				Predicates.fromSelf.and(Predicates.selectionNotSeenYet));
	}

	protected ITraceSelectionListener buildTraceSelectionListener() {
		return (context, modelId, selection) -> {
			onTraceSelection(context, modelId, selection);
		};
	}

	protected void onEditorSelection(ISelection selection) {
		if (pauseEditorSelection) {
			return;
		}

		ITraceManager service = Activator.getInstance().getTraceManager();
		if (!service.doesContextExist(getContextId())) {
			return;
		}

		ITraceContext context = service.getContext(getContextId());
		if (!context.hasTraceFor(getModelId())) {
			return;
		}

		removeEditorHighlight();

		postEditorSelection(context, selection);
	}

	protected void postEditorSelection(ITraceContext context, ISelection selection) {
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

	protected void onTraceSelection(ITraceContext context, String modelId, Collection<String> selectedElementsById) {
		if (pauseTraceSelection) {
			return;
		}

		if (getModelId().equals(modelId)) {
			return;
		}

		if (!editor.getSite().getPage().isPartVisible(editor)) {
			// don't bother
			return;
		}

		if (!context.hasTraceFor(getModelId())) {
			return;
		}

		if (selectedElementsById.isEmpty()) {
			removeEditorHighlight();
		} else {
			computeEditorHighligt(context, modelId, selectedElementsById);
		}
	}

	/**
	 * This method is called automatically on object instantiation and when the
	 * editor input changes.
	 */
	protected abstract void computeContextAndModelId();

	/**
	 * The context id, which is usually the name of the eclipse project in which the
	 * model is located. If there is no context with this id, the connection will
	 * not process any selection events.
	 * 
	 * @return context id, can be null
	 */
	protected abstract String getContextId();

	/**
	 * The id of the model, which is displayed by this editor. If there is no model
	 * with this id, the connection will not process any selection events.
	 * 
	 * @return a model id, can be null
	 */
	protected abstract String getModelId();

	/**
	 * Converts a selection of elements into a set of ids based on the model
	 * presented by the editor. Each id should uniquely identify one of the selected
	 * elements.
	 * 
	 * @param selection A selection within the editor
	 * @return A set of ids. The collection can be empty but not null
	 */
	protected abstract Collection<String> transformSelectionToElementIds(ISelection selection);

	protected abstract void computeEditorHighligt(ITraceContext context, String remoteModelId,
			Collection<String> remoteElementsById);

	protected abstract void removeEditorHighlight();

}