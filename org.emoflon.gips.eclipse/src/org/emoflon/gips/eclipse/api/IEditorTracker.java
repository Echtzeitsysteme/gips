package org.emoflon.gips.eclipse.api;

import org.eclipse.ui.IEditorPart;

public interface IEditorTracker {

	/**
	 *
	 * @param editor
	 * @return True, if registration was successful.
	 */
	boolean registerEditor(IEditorPart editor);

	void unregisterEditor(IEditorPart editor);

}