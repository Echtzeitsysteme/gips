package org.emoflon.gips.debugger.listener;

import java.util.Objects;
import java.util.function.Predicate;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.INullSelectionListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.SelectionListenerFactory.ISelectionModel;

/**
 * Inspired by {@link org.eclipse.ui.internal.PartSelectionListener} but without
 * the reference leak in
 * {@link org.eclipse.ui.internal.PartSelectionListener#addPartListener}
 */
public final class PartSelectionFilterListener implements ISelectionListener, INullSelectionListener {

	private final IWorkbenchPart part;
	private final ISelectionListener delegate;
	private final Predicate<ISelectionModel> predicate;

	private IWorkbenchPart currentSelectionPart;
	private ISelection currentSelection;

	private IWorkbenchPart lastSelectionPart;
	private ISelection lastSelection;

	/**
	 * Constructs an intermediate selection listener which filters selections and
	 * lets them only through, if the predicate resolves to true.
	 *
	 * @param part      the part this listener listens to, may not be null
	 * @param delegate  the callback listener, may not be null
	 * @param predicate the predicate to test, may not be null
	 */
	public PartSelectionFilterListener(IWorkbenchPart part, ISelectionListener delegate,
			Predicate<ISelectionModel> predicate) {
		this.part = Objects.requireNonNull(part, "part");
		this.delegate = Objects.requireNonNull(delegate, "delegate");
		this.predicate = Objects.requireNonNull(predicate, "predicate");
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		saveCurrentSelection(part, selection);
		if (predicate.test(getModel())) {
			delegateCall(part, selection);
		}
	}

	private void delegateCall(IWorkbenchPart part, ISelection selection) {
		if (selection == null && (delegate instanceof INullSelectionListener)) {
			delegate.selectionChanged(part, selection);
			saveLastSelection(part, selection);
		} else if (selection != null) {
			delegate.selectionChanged(part, selection);
			saveLastSelection(part, selection);
		}
	}

	private void saveCurrentSelection(IWorkbenchPart part, ISelection selection) {
		currentSelectionPart = part;
		currentSelection = selection;
	}

	private void saveLastSelection(IWorkbenchPart part, ISelection selection) {
		lastSelectionPart = part;
		lastSelection = selection;
	}

	private ISelectionModel getModel() {
		return new ISelectionModel() {

			@Override
			public IWorkbenchPart getTargetPart() {
				return part;
			}

			@Override
			public IWorkbenchPart getLastDeliveredSelectionPart() {
				return lastSelectionPart;
			}

			@Override
			public ISelection getLastDeliveredSelection() {
				return lastSelection;
			}

			@Override
			public IWorkbenchPart getCurrentSelectionPart() {
				return currentSelectionPart;
			}

			@Override
			public ISelection getCurrentSelection() {
				return currentSelection;
			}

			@Override
			public boolean isTargetPartVisible() {
				return part.getSite().getPage().isPartVisible(part);
			}

			@Override
			public boolean isSelectionPartVisible() {
				return getCurrentSelectionPart() != null
						&& getCurrentSelectionPart().getSite().getPage().isPartVisible(getCurrentSelectionPart());
			}
		};
	}

}
