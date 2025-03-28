package org.emoflon.gips.eclipse.ui.editor;

import org.eclipse.xtext.ui.editor.XtextEditor;
import org.emoflon.gips.eclipse.CplexLpLanguage;

public class CplexLpEditor extends XtextEditor {

	public static final String EDITOR_ID = CplexLpLanguage.LANGUAGE_NAME;

//    @Inject
//    private XbaseEditorInputRedirector editorInputRedirector;

	public CplexLpEditor() {
		super();
	}

//    @Override
//    protected void doSetInput(IEditorInput input) throws CoreException {
//        try {
//            IEditorInput inputToUse = editorInputRedirector.findOriginalSource(input);
//            super.doSetInput(inputToUse);
//            return;
//        } catch (CoreException e) {
//            // ignore
//        }
//        super.doSetInput(input);
//    }

}