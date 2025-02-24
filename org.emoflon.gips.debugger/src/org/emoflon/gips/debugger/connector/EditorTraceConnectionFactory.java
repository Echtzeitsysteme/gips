package org.emoflon.gips.debugger.connector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.ui.IEditorPart;

/**
 * This class combines multiple {@link IEditorTraceConnectionFactory}s into a
 * single factory.
 * 
 * @see #addConnectionFactory(IEditorTraceConnectionFactory)
 */
public final class EditorTraceConnectionFactory implements IEditorTraceConnectionFactory {

	private final List<IEditorTraceConnectionFactory> connects = new ArrayList<>();

	/**
	 * Adds an additional factory.
	 */
	public void addConnectionFactory(IEditorTraceConnectionFactory factory) {
		Objects.requireNonNull(factory, "factory");
		connects.add(factory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * Calls {@link #canConnect(IEditorPart)} on each added factory, until one of
	 * them returns true.
	 */
	@Override
	public boolean canConnect(IEditorPart editorPart) {
		for (var connector : connects) {
			if (connector.canConnect(editorPart)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * Iterates through all added factories and returns the first successfully
	 * created {@link IEditorTraceConnection}.
	 */
	@Override
	public IEditorTraceConnection createConnection(IEditorPart editorPart) {
		IEditorTraceConnection connection = null;

		for (var connector : connects) {
			if (connector.canConnect(editorPart)) {
				connection = connector.createConnection(editorPart);
				if (connection != null) {
					break;
				}
			}
		}

		return connection;
	}

}
