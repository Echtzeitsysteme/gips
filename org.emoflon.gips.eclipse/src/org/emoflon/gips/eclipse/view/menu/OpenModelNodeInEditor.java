package org.emoflon.gips.eclipse.view.menu;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.emoflon.gips.eclipse.utility.HelperEclipse;
import org.emoflon.gips.eclipse.utility.HelperErrorDialog;
import org.emoflon.gips.eclipse.view.model.ModelNode;

public class OpenModelNodeInEditor extends ContributionItem {

	public OpenModelNodeInEditor() {
	}

	public OpenModelNodeInEditor(String id) {
		super(id);
	}

	@Override
	public void fill(Menu menu, int index) {
		ISelectionService selectionService = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();

		if (!(selectionService.getSelection() instanceof IStructuredSelection structuredSelection))
			return;

		if (!(structuredSelection.getFirstElement() instanceof ModelNode modelNode))
			return;

		MenuItem item = new MenuItem(menu, SWT.CHECK, index);
		item.setText("Open in editor");
		item.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				IProject project = HelperEclipse.tryAndGetProject(modelNode.getContextId());
				if (project == null)
					return;

				IPath filePath = IPath.EMPTY;
				for (String segment : modelNode.modelId.split("/"))
					filePath = filePath.append(segment);

				IFile modelFile = project.getFile(filePath);

				try {
					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					IDE.openEditor(page, modelFile);
				} catch (final PartInitException ex) {
					ex.printStackTrace();
					var error = HelperErrorDialog.createMultiStatus(ex.getLocalizedMessage(), ex);
					ErrorDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error",
							"Unable to open file: " + modelFile, error);
				}
			}
		});
	}

}
