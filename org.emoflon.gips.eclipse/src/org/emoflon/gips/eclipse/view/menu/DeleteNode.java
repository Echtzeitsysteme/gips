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
import org.emoflon.gips.eclipse.service.ProjectTraceContext;
import org.emoflon.gips.eclipse.service.TraceManager;
import org.emoflon.gips.eclipse.view.model.ContextNode;
import org.emoflon.gips.eclipse.view.model.LinkModelNode;
import org.emoflon.gips.eclipse.view.model.ModelNode;

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
			item.setText("Delete all");
			item.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					TraceManager manager = TracePlugin.getInstance().getTraceManager();
					manager.getContext(node.getContextId()).deleteAllModels();
				}
			});
		}

		else if (selectedElement instanceof ModelNode node) {
			MenuItem item = new MenuItem(menu, SWT.CHECK, index);
			item.setText("Delete entry");
			item.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					TraceManager manager = TracePlugin.getInstance().getTraceManager();
					manager.getContext(node.getContextId()).deleteModel(node.modelId);
				}
			});
		}

		else if (selectedElement instanceof LinkModelNode node) {
			MenuItem item = new MenuItem(menu, SWT.CHECK, index);
			item.setText("Delete trace");
			item.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					ProjectTraceContext context = TracePlugin.getInstance().getTraceManager()
							.getContext(node.getContextId());

					switch (node.direction) {
					case FORWARD:
						context.deleteModelLink(node.parent.modelId, node.modelId);
						break;
					case BACKWARD:
						context.deleteModelLink(node.modelId, node.parent.modelId);
						break;
					}
				}
			});
		}

	}
}
