package org.emoflon.gips.eclipse.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.source.ISourceViewerExtension5;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class ReloadCodeMiningHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorPart part = HandlerUtil.getActiveEditorChecked(event);
		ITextViewer viewer = part.getAdapter(ITextViewer.class);
		if (viewer instanceof ISourceViewerExtension5 codeMiningViewer)
			codeMiningViewer.updateCodeMinings();

		return null;
	}

}
