package org.emoflon.gips.eclipse.menu;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.ide.IDE;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.emoflon.gips.eclipse.utility.HelperErrorDialog;
import org.emoflon.gips.eclipse.utility.HelperGipsl;

public class ContributionOpenLpFile extends CompoundContributionItem {

	public ContributionOpenLpFile() {
	}

	public ContributionOpenLpFile(String id) {
		super(id);
	}

	@Override
	protected IContributionItem[] getContributionItems() {
		final var part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
		if (!(part instanceof final XtextEditor editor))
			return new IContributionItem[0];

		final var targetPath = HelperGipsl.getLpPath(editor);
		if (targetPath == null)
			return new IContributionItem[0];

		return new IContributionItem[] { new ContributionItem() {
			@Override
			public void fill(Menu menu, int index) {
				final var item = new MenuItem(menu, SWT.CHECK, index);
				item.setText("Open LP-File: " + targetPath.lastSegment());
				item.addSelectionListener(new SelectionListener() {
					@Override
					public void widgetSelected(SelectionEvent selectionEvent) {
						final var targetFile = ResourcesPlugin.getWorkspace().getRoot().getFile(targetPath);

						try {
							IDE.openEditor(part.getSite().getPage(), targetFile, "org.emoflon.gips.debugger.CplexLp",
									true);
						} catch (final PartInitException ex) {
							ex.printStackTrace();
							var error = HelperErrorDialog.createMultiStatus(ex.getLocalizedMessage(), ex);
							ErrorDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
									"Error", "Unable to open file: " + targetPath, error);
						}
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
					}
				});
			}
		} };
	}

}
