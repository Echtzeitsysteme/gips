package org.emoflon.gips.debugger.imp.connector;

import java.util.Objects;

import org.eclipse.ui.IEditorPart;
import org.eclipse.xtext.ui.editor.XtextEditor;

public abstract class XtextEditorTraceConnectionFactory implements IEditorTraceConnectionFactory {

	protected final String languageName;

	public XtextEditorTraceConnectionFactory(String languageName) {
		this.languageName = Objects.requireNonNull(languageName, "languageName");
	}

	@Override
	public boolean canConnect(IEditorPart editorPart) {
		return editorPart instanceof XtextEditor xtextEditor && languageName.equals(xtextEditor.getLanguageName());
	}

	@Override
	public IEditorTraceConnection createConnection(IEditorPart editorPart) {
		return createConnection((XtextEditor) editorPart);
	}

	protected abstract IEditorTraceConnection createConnection(XtextEditor editor);
}
