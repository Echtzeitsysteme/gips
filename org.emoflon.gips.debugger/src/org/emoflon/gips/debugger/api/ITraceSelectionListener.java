package org.emoflon.gips.debugger.api;

import java.util.Collection;
import java.util.EventListener;

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
	 * @param context    the context in which the event was raised
	 * @param modelId    the model that triggered the event
	 * @param elementIds the elements that have been selected on {@code modelId}
	 */
	public void selectedByModel(ITraceContext context, String modelId, Collection<String> elementIds);
}
