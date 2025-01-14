package org.emoflon.gips.debugger.trace;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.emoflon.gips.debugger.trace.PathFinder.Direction;
import org.emoflon.gips.debugger.trace.PathFinder.TracePath;

/**
 * A trace chain is a set of paths which lead from a source to a destination
 * model.
 */
public class TraceChain {

	private final WeakReference<TraceGraph> graph;
	private final String srcModelId;
	private final String dstModelId;
	private final Set<TracePath> paths;

	public TraceChain(final TraceGraph graph, final String srcModelId, final String dstModelId,
			final Set<TracePath> allPaths) {

		this.graph = new WeakReference<>(Objects.requireNonNull(graph, "graph"));
		this.srcModelId = Objects.requireNonNull(srcModelId, "srcModelId");
		this.dstModelId = Objects.requireNonNull(dstModelId, "dstModelId");
		this.paths = Objects.requireNonNull(allPaths, "allPaths");

		if (this.srcModelId.equals(dstModelId)) {
			if (!allPaths.isEmpty()) {
				throw new IllegalArgumentException("Chain links model to itself. Path must be empty");
			}
		}

		for (var pathData : paths) {
			var firstSegment = pathData.path().get(0);
			var firstModelId = firstSegment.direction() == Direction.Forward ? firstSegment.srcModelId()
					: firstSegment.dstModelId();
			if (!this.srcModelId.equals(firstModelId)) {
				throw new IllegalArgumentException("First element of a path must be the source model");
			}

			var lastSegment = pathData.path().get(pathData.path().size() - 1);
			var lastModelId = lastSegment.direction() == Direction.Forward ? lastSegment.dstModelId()
					: lastSegment.srcModelId();
			if (!this.dstModelId.equals(lastModelId)) {
				throw new IllegalArgumentException("Last element of a path must be the destination model");
			}
		}
	}

	private TraceGraph getGraph() {
		var graph = this.graph.get();
		if (graph == null) {
			throw new NullPointerException("TraceGraph disposed");
		}
		return graph;
	}

	public Set<String> resolveElementFromStartToEnd(String elementId) {
		return resolveElementFromStartToEnd(Collections.singleton(elementId));
	}

	public Set<String> resolveElementFromStartToEnd(final Collection<String> elementIds) {
		return resolveElementsInTransformationDirection(true, elementIds);
	}

	public Set<String> resolveElementFromEndToStart(String elementId) {
		return resolveElementFromEndToStart(Collections.singleton(elementId));
	}

	public Set<String> resolveElementFromEndToStart(Collection<String> elementIds) {
		return resolveElementsInTransformationDirection(false, elementIds);
	}

	private Set<String> resolveElementsInTransformationDirection(boolean forward, Collection<String> elementIds) {
		if (elementIds.isEmpty()) {
			return Collections.emptySet();
		}

		Set<String> resolvedNodes = new HashSet<>();
		if (paths.isEmpty()) {
			resolvedNodes.addAll(elementIds);
		}

		TraceGraph graph = getGraph();

		Collection<String> currentNodes = elementIds;
		for (var pathData : paths) {
			if (forward) {
				for (var path : pathData.path()) {
					if (currentNodes.isEmpty()) {
						break;
					}

					final var link = graph.getLink(path.srcModelId(), path.dstModelId());
					if (path.direction() == Direction.Forward) {
						currentNodes = link.resolveFromSrcToDst(currentNodes);
					} else {
						currentNodes = link.resolveFromDstToSrc(currentNodes);
					}
				}
			} else {
				var iterator = pathData.path().listIterator(pathData.path().size());
				while (iterator.hasPrevious()) {
					if (currentNodes.isEmpty()) {
						break;
					}

					var previous = iterator.previous();
					final var link = graph.getLink(previous.srcModelId(), previous.dstModelId());
					if (previous.direction() == Direction.Forward) {
						currentNodes = link.resolveFromDstToSrc(currentNodes);
					} else {
						currentNodes = link.resolveFromSrcToDst(currentNodes);
					}
				}
			}

			resolvedNodes.addAll(currentNodes);
		}

		return resolvedNodes;
	}

	public boolean isResolved() {
		return srcModelId.equals(dstModelId) || !paths.isEmpty();
	}

	public TraceModelReference getStartModel() {
		return getGraph().getModelReference(srcModelId, false);
	}

	public TraceModelReference getEndModel() {
		return getGraph().getModelReference(dstModelId, false);
	}

}
