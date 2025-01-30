package org.emoflon.gips.debugger.api;

import java.util.Collection;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.emoflon.gips.debugger.trace.TraceModelLink;

public interface ITraceContext {

	String getContextId();

	void addListener(ITraceSelectionListener listener);

	void removeListener(ITraceSelectionListener listener);

	void addListener(ITraceUpdateListener listener);

	void removeListener(ITraceUpdateListener listener);

	boolean hasTraceFor(String modelId);

	Set<String> getSourceModels(String modelId);

	Set<String> getTargetModels(String modelId);

	void selectElementsByTrace(String modelId, Collection<String> elementIds) throws TraceModelNotFoundException;

	IModelLink getModelChain(String modelId1, String modelId2);

	Collection<String> resolveElementsByTrace(String startModelId, String endModelId, Collection<String> elements,
			boolean bidirectional);

	void updateTraceModel(TraceModelLink traceLink);

	void loadAndUpdateTraceModel(URI fileURI);

}
