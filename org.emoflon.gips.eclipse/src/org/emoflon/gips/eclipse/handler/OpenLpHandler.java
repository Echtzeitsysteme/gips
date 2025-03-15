package org.emoflon.gips.eclipse.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.xtext.ui.editor.utils.EditorUtils;
import org.emoflon.gips.eclipse.utility.HelperGipsl;

public class OpenLpHandler extends AbstractHandler {

//	@Inject
//	EObjectAtOffsetHelper eObjectAtOffsetHelper;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		final var editor = EditorUtils.getActiveXtextEditor(event);
		if (editor == null) {
			return null;
		}

		final var path = HelperGipsl.getLpPath(editor);
		if (path == null) {
			return null;
		}

		final var file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);

		try {
			IDE.openEditor(editor.getSite().getPage(), file, /* "org.emoflon.gips.debugger.CplexLp", */ true);
		} catch (final PartInitException e1) {
			e1.printStackTrace();
		}

		return null;
	}

}
