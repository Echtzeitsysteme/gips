package org.emoflon.gips.debugger.api;

import java.util.Collection;

import org.emoflon.gips.debugger.Activator;

public interface ITraceManager {

	/**
	 * Shared singleton instance
	 */
	public static ITraceManager getInstance() {
		return Activator.getInstance().getTraceManager();
	}

	void addListener(String contextId, ITraceSelectionListener listener);

	void removeListener(ITraceSelectionListener listener);

	void removeListener(String contextId, ITraceSelectionListener listener);

	void addListener(String contextId, ITraceUpdateListener listener);

	void removeListener(ITraceUpdateListener listener);

	void removeListener(String contextId, ITraceUpdateListener listener);

	void selectElementsByTraceModel(String contextId, String modelId, Collection<String> selection)
			throws TraceModelNotFoundException;

	/**
	 * Allows to add or remove an editor from this manager
	 */
	IEditorTracker getEditorTracker();

	ITraceContext getContext(String contextId);

	boolean doesContextExist(String contextId);

	boolean isVisualisationActive();

	void setVisualisation(boolean active);

	boolean toggleVisualisation();

}