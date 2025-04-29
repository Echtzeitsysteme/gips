package org.emoflon.gips.eclipse.connector;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.emoflon.gips.eclipse.utility.HelperEclipse;

public abstract class XtextEditorTraceConnection extends EditorTraceConnection<XtextEditor> {

	public XtextEditorTraceConnection(XtextEditor editor, IHighlightStrategy<? super XtextEditor> highlightStrategy) {
		super(editor, highlightStrategy);
	}

	@Override
	protected void computeContextAndModelId() {
		URI uri = editor.getDocument().getResourceURI();

		IProject project = HelperEclipse.tryAndGetProject(uri);
		if (project == null) { // TODO: a way to support files outside of eclipse projects
			setContextId("");
			setModelId("");
			return;
		}

		IPath relativeFilePath = HelperEclipse.getProjectRelativPath(project, uri);

		setContextId(project.getName());
		setModelId(relativeFilePath.toString());
	}

}
