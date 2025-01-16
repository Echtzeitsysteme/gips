package org.emoflon.gips.debugger.imp;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.emoflon.gips.debugger.api.IModelLink;
import org.emoflon.gips.debugger.api.ITraceContext;
import org.emoflon.gips.debugger.api.ITraceSelectionListener;
import org.emoflon.gips.debugger.api.ITraceUpdateListener;
import org.emoflon.gips.debugger.api.TraceModelNotFoundException;
import org.emoflon.gips.debugger.trace.EcoreReader;
import org.emoflon.gips.debugger.trace.ModelReference;
import org.emoflon.gips.debugger.trace.PathFinder.SearchDirection;
import org.emoflon.gips.debugger.trace.Root;
import org.emoflon.gips.debugger.trace.TraceGraph;
import org.emoflon.gips.debugger.trace.TraceModelLink;
import org.emoflon.gips.debugger.trace.TransformEcore2Graph;

final class ProjectTraceContext implements ITraceContext {

	private final TraceManager service;
	private final String contextId;
	private final TraceGraph graph = new TraceGraph();
	private final Set<ITraceSelectionListener> selectionListener = new HashSet<>();
	private final Set<ITraceUpdateListener> updateListener = new HashSet<>();

	public ProjectTraceContext(TraceManager service, String contextId) {
		this.service = Objects.requireNonNull(service, "service");
		this.contextId = Objects.requireNonNull(contextId, "contextId");
	}

	@Override
	public void addListener(ITraceSelectionListener listener) {
		Objects.requireNonNull(listener, "listener");
		selectionListener.add(listener);
	}

	@Override
	public void removeListener(ITraceSelectionListener listener) {
		selectionListener.remove(listener);
	}

	@Override
	public void addListener(ITraceUpdateListener listener) {
		Objects.requireNonNull(listener, "listener");
		updateListener.add(listener);
	}

	@Override
	public void removeListener(ITraceUpdateListener listener) {
		updateListener.remove(listener);
	}

	@Override
	public void selectElementsByTrace(String modelId, Collection<String> elementIds) throws TraceModelNotFoundException {
		Objects.requireNonNull(modelId, "modelId");
		Objects.requireNonNull(elementIds, "elementIds");

		if (!graph.hasModelReference(modelId)) {
			throw new TraceModelNotFoundException(modelId);
		}

		if (service.isVisualisationActive()) {
			fireModelSelectionNotification(modelId, elementIds);
		}
	}

	@Override
	public String getContextId() {
		return contextId;
	}

	@Override
	public boolean hasTraceFor(String modelId) {
		return graph.hasModelReference(modelId);
	}

	@Override
	public Set<String> getSourceModels(String modelId) {
		return graph.getSourceModelIds(modelId);
	}

	@Override
	public Set<String> getTargetModels(String modelId) {
		return graph.getTargetModelIds(modelId);
	}

	@Override
	public IModelLink getModelChain(String modelId1, String modelId2) {
		var chain = graph.buildTraceChain(modelId1, modelId2, SearchDirection.AllDirections);
		// TODO: some caching.
		// store chain between 1 and 2
		// recalculate iff transformation chain between 1 and 2 changes (updateTrace)
		// luckily, the graph is tiny.

		return new IModelLink() {
			@Override
			public String getStartModelId() {
				return modelId1;
			}

			@Override
			public String getEndModelId() {
				return modelId2;
			}

			@Override
			public boolean isResolved() {
				return chain.isResolved();
			}

			@Override
			public Collection<String> resolveElements(Collection<String> selectedElementsById) {
				return chain.resolveElementFromStartToEnd(selectedElementsById);
			}
		};
	}

	@Override
	public Collection<String> resolveElementsByTrace(String startModelId, String endModelId,
			Collection<String> elements, boolean bidirectional) {
		var link = getModelChain(startModelId, endModelId);
		if (link.isResolved()) {
			return link.resolveElements(elements);
		}
		return Collections.emptyList();
	}

	@Override
	public void updateTraceModel(TraceModelLink traceLink) {
		graph.addOrReplaceTraceLink(traceLink);

		var updatedModels = Set.of(traceLink.getSourceModel(), traceLink.getTargetModel());
		fireModelUpdateNotification(updatedModels);
	}

	@Override
	public void loadAndUpdateTraceModel(URI fileURI) {
		var reader = new EcoreReader(fileURI);
		if (reader.doesFileExist()) {
			Root model = reader.loadModel();
			TransformEcore2Graph.addModelToGraph(model, graph);

			var updatedModels = model.getModels().stream().map(ModelReference::getModelId)
					.collect(Collectors.toUnmodifiableSet());
			fireModelUpdateNotification(updatedModels);
		}
	}

	private void fireModelSelectionNotification(String modelId, Collection<String> elementIds) {
		Objects.requireNonNull(modelId, "modelId");
		Objects.requireNonNull(elementIds, "elementIds");

		// TODO: run (each) in a separate thread with a cancellation token

		for (var listener : selectionListener) {
			listener.selectedByModel(this, modelId, elementIds);
		}
	}

	private void fireModelUpdateNotification(Set<String> updatedModels) {
		Objects.requireNonNull(updatedModels, "updatedModels");

		for (var listener : this.updateListener) {
			listener.updatedModels(this, updatedModels);
		}
	}

}
