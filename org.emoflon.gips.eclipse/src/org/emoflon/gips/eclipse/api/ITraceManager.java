package org.emoflon.gips.eclipse.api;

import java.util.Collection;
import java.util.Set;

import org.emoflon.gips.eclipse.TracePlugin;
import org.emoflon.gips.eclipse.api.event.ITraceContextListener;
import org.emoflon.gips.eclipse.api.event.ITraceSelectionListener;
import org.emoflon.gips.eclipse.api.event.ITraceUpdateListener;

public interface ITraceManager {

	/**
	 * Shared singleton instance
	 */
	public static ITraceManager getInstance() {
		return TracePlugin.getInstance().getContextManager();
	}

	/**
	 * Adds the given listener for manager related events to this manager. Has no
	 * effect if the given listener is already registered. Callbacks may or may not
	 * run on the UI-Thread.
	 * 
	 * @param listener the listener, may not be null
	 * @see #removeTraceContextListener(ITraceContextListener)
	 */
	void addTraceContextListener(ITraceContextListener listener);

	/**
	 * Removes the given listener
	 * 
	 * @param listener the listener, can be null
	 */
	void removeTraceContextListener(ITraceContextListener listener);

	void removeListener(ITraceSelectionListener listener);

	void removeListener(ITraceUpdateListener listener);

	void selectElementsByTraceModel(String contextId, String modelId, Collection<String> selection)
			throws TraceModelNotFoundException;

	/**
	 * Returns a {@link ITraceContext} for the given id. The id must be the name of
	 * an accessible Eclipse project.
	 * 
	 * @param contextId name of an Eclipse project, may not be null
	 * @return a {@link ITraceContext}
	 * @exception IllegalArgumentException if there is no accessible Eclipse project
	 *                                     with the given name.
	 * @see #doesContextExist(String)
	 * @see #getAvailableContextIds()
	 */
	ITraceContext getContext(String contextId);

	/**
	 * 
	 * @param contextId name of an Eclipse project
	 * @return true if the given id is valid
	 * @see #getContext(String)
	 */
	boolean doesContextExist(String contextId);

	boolean isVisualisationActive();

	/**
	 * 
	 * @return a {@link Set} of valid context ids
	 * @see #getContext(String)
	 * @see #doesContextExist(String)
	 */
	Set<String> getAvailableContextIds();

}