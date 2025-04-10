package org.emoflon.gips.eclipse.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.emoflon.gips.eclipse.ui.editor.CplexLpEditor;
import org.emoflon.gips.eclipse.utility.HelperErrorDialog;
import org.emoflon.gips.eclipse.utility.HelperGipsl;

public class OpenLPFileHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorPart editor = HandlerUtil.getActiveEditorChecked(event);
		if (!(editor instanceof XtextEditor xtextEditor))
			return null;

		IPath path = HelperGipsl.getLpPath(xtextEditor);
		if (path == null)
			return null;

		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);

		try {
			IDE.openEditor(editor.getSite().getPage(), file, CplexLpEditor.EDITOR_ID, true);
		} catch (final PartInitException ex) {
			ex.printStackTrace();
			var error = HelperErrorDialog.createMultiStatus(ex.getLocalizedMessage(), ex);
			ErrorDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error",
					"Unable to open file: " + file, error);
		}

		return null;
	}

}
