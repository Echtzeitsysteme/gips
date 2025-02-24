package org.emoflon.gips.debugger.api.event;

import java.util.Collection;
import java.util.EventListener;

import org.emoflon.gips.debugger.api.ITraceContext;

/**
 * Interface for listening to selection events created by the tracing framework.
 * 
 * @see {@link ITraceContext#addListener(ITraceSelectionListener)}
 * @see {@link ITraceContext#selectElementsByTrace(String, Collection)}
 */
@FunctionalInterface
public interface ITraceSelectionListener extends EventListener {
	/**
	 * Notifies this listener that a selection has been made.
	 * 
	 * @param event which was raised, contains all relevant data
	 * @see TraceSelectionEvent
	 */
	public void selectedByModel(TraceSelectionEvent event);
}
