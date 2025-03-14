package org.emoflon.gips.eclipse.connector;

import org.eclipse.ui.IEditorPart;

public interface IEditorTraceConnectionFactory {
	/**
	 * Determines if this factory can create an {@link IEditorTraceConnection} for
	 * the given part.
	 * 
	 * @param editorPart for which an {@link IEditorTraceConnection} should be
	 *                   created
	 * @return true, if this factory can create an {@link IEditorTraceConnection}
	 *         for this part
	 */
	boolean canConnect(IEditorPart editorPart);

	/**
	 * Creates an {@link IEditorTraceConnection} for the given part. This method
	 * should only be called, if {@link #canConnect(IEditorPart)} returns true.
	 * 
	 * @param editorPart
	 * @return
	 */
	IEditorTraceConnection createConnection(IEditorPart editorPart);
}