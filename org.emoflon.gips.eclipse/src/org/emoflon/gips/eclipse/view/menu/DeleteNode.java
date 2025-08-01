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

		Object selectedElement = structuredSelection.getFirstElement();

		if (selectedElement instanceof ContextNode node) {
			MenuItem item = new MenuItem(menu, SWT.CHECK, index);
			item.setText("Delete context");
			item.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					ContextManager manager = TracePlugin.getInstance().getContextManager();
					manager.getContext(node.getContextId()).deleteAllModels();
				}
			});
		} else if (selectedElement instanceof ModelNode node) {
			MenuItem item = new MenuItem(menu, SWT.CHECK, index);
			item.setText("Delete model");
			item.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					ContextManager manager = TracePlugin.getInstance().getContextManager();
					manager.getContext(node.getContextId()).deleteModel(node.getModelId());
				}
			});
		} else if (selectedElement instanceof LinkModelNode node) {
			MenuItem item = new MenuItem(menu, SWT.CHECK, index);
			item.setText("Delete trace");
			item.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					ProjectContext context = TracePlugin.getInstance().getContextManager()
							.getContext(node.getContextId());

					switch (node.getDirection()) {
					case FORWARD:
						context.deleteModelLink(node.getParent().getModelId(), node.getModelId());
						break;
					case BACKWARD:
						context.deleteModelLink(node.getModelId(), node.getParent().getModelId());
						break;
					}
				}
			});
		} else if (selectedElement instanceof ValueNode node) {
			MenuItem item = new MenuItem(menu, SWT.CHECK, index);
			item.setText("Delete values");
			item.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					ProjectContext context = TracePlugin.getInstance().getContextManager()
							.getContext(node.getContextId());
					context.deleteModelValues(node.getModelId());
				}
			});
		}

	}
}
