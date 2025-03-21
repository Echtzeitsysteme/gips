package org.emoflon.gips.eclipse.api;

import java.util.Collection;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.emoflon.gips.eclipse.api.event.ITraceSelectionListener;
import org.emoflon.gips.eclipse.api.event.ITraceUpdateListener;
import org.emoflon.gips.eclipse.trace.TraceModelLink;

public interface ITraceContext {

	public ITraceManager getTraceManager();

	/**
	 * Returns the id of this context
	 * 
	 * @return the id of this context
	 */
	String getContextId();

	void addListener(ITraceSelectionListener listener);

	void removeListener(ITraceSelectionListener listener);

	void addListener(ITraceUpdateListener listener);

	void removeListener(ITraceUpdateListener listener);

	boolean hasTraceFor(String modelId);

	Set<String> getSourceModels(String modelId);

	Set<String> getTargetModels(String modelId);

	Set<String> getAllModels();

	/**
	 * 
	 * 
	 * @param srcModelId Model from which the selection originates
	 * @param elementIds which elements have been selected
	 * @throws TraceModelNotFoundException
	 */
	void selectElementsByTrace(String modelId, Collection<String> elementIds) throws TraceModelNotFoundException;

	/**
	 * Returns a link between {@code startModelId} and {@code targetModelId}. With
	 * this link it's possible to resolve elements from the start model to the end
	 * model.
	 * 
	 * @param startModelId
	 * @param targetModelId
	 * @return
	 */
	IModelLink getModelChain(String startModelId, String targetModelId);

	Collection<String> resolveElementsByTrace(String startModelId, String endModelId, Collection<String> elements,
			boolean bidirectional);

	void updateTraceModel(TraceModelLink traceLink);

	void loadAndUpdateTraceModel(URI fileURI);

}
