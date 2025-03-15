package org.emoflon.gips.eclipse.connector;

import org.emoflon.gips.eclipse.service.TraceManager;

/**
 * This represents the connection between an editor and a {@link TraceManager}.
 * A connected editor will send and receive selection events related to tracing.
 * The implementation may vary between different editors
 */
public interface IEditorTraceConnection {
	boolean isConnected();

	void connect();

	void disconnect();
}