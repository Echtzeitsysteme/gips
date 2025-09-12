package org.emoflon.gips.eclipse.view.menu;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PlatformUI;
import org.emoflon.gips.eclipse.TracePlugin;
import org.emoflon.gips.eclipse.service.ContextManager;
import org.emoflon.gips.eclipse.service.ProjectContext;
import org.emoflon.gips.eclipse.view.model.ContextNode;
import org.emoflon.gips.eclipse.view.model.LinkModelNode;
import org.emoflon.gips.eclipse.view.model.ModelNode;
import org.emoflon.gips.eclipse.view.model.ValueNode;

public class DeleteNode extends ContributionItem {

	public DeleteNode() {
	}

	public DeleteNode(String id) {
		super(id);
	}

	@Override
	public void fill(Menu menu, int index) {
		ISelectionService selectionService = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
		if (!(selectionService.getSelection() instanceof IStructuredSelection structuredSelection))
			return;

		if (structuredSelection.isEmpty())
			return;

		String buttonLabel = null;
		if (structuredSelection.size() > 1) {
			buttonLabel = "Delete all selections";
		} else {
			buttonLabel = switch (structuredSelection.getFirstElement()) {
			case ContextNode node -> "Delete context";
			case ModelNode node -> "Delete model";
			case LinkModelNode node -> "Delete trace";
			case ValueNode node -> "Delete values";
			default -> null;
			};
		}

		if (buttonLabel == null)
			return;

		MenuItem menuItem = new MenuItem(menu, SWT.CHECK, index);
		menuItem.setText(buttonLabel);
		menuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ContextManager manager = TracePlugin.getInstance().getContextManager();
				for (Object node : structuredSelection)
					deleteNode(manager, node);
			}
		});
	}

	private static void deleteNode(ContextManager manager, Object object) {
		switch (object) {
		case ContextNode node:
			manager.getContext(node.getContextId()).deleteAllModels();
			break;

		case ModelNode node:
			manager.getContext(node.getContextId()).deleteModel(node.getModelId());
			break;

		case LinkModelNode node:
			ProjectContext context = manager.getContext(node.getContextId());

			switch (node.getDirection()) {
			case FORWARD:
				context.deleteModelLink(node.getParent().getModelId(), node.getModelId());
				break;
			case BACKWARD:
				context.deleteModelLink(node.getModelId(), node.getParent().getModelId());
				break;
			}
			break;

		case ValueNode node:
			manager.getContext(node.getContextId()).deleteModelValues(node.getModelId());
			break;

		case null:
		default:
			break;
		}
	}
}
