package org.emoflon.gips.debugger.trace;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.emoflon.gips.debugger.trace.PathFinder.SearchDirection;

/**
 * This class stores traces in a graph-based structure. Each model is
 * represented by a unique id and a model transformation is stored as a
 * source/target-relation between two ids. A source is the starting point of a
 * transformation and the target its result.
 * 
 */
public class TraceGraph {

	private final Object lock = new Object();

	private final Map<String, Map<String, TraceModelLink>> links = new HashMap<>();
	private final Map<String, Set<String>> reverseLinks = new HashMap<>();
	private final Map<String, TraceModelReference> storedReferences = new HashMap<>();

	public TraceModelReference getModelReference(final String modelId, final boolean createOnDemand) {
		final var ref = storedReferences.get(modelId);
		if ((ref == null) && createOnDemand) {
			return getOrCreateModelReference(modelId);
		}
		return ref;
	}

	private TraceModelReference getOrCreateModelReference(final String modelId) {
		Objects.requireNonNull(modelId, "modelId");
		synchronized (lock) {
			var ref = storedReferences.get(modelId);
			if (ref == null) {
				ref = new TraceModelReference(modelId);

				links.put(modelId, new HashMap<>());
				reverseLinks.put(modelId, new HashSet<>());
				storedReferences.put(modelId, ref);
			}
			return ref;
		}
	}

	public void deleteModelReference(final String modelId) {
		synchronized (lock) {
			final var ref = storedReferences.remove(modelId);
			if (ref == null) {
				return;
			}

			final var model2Target = links.remove(modelId);
			for (final var targetId : model2Target.keySet()) {
				final var targetCreatedBy = reverseLinks.get(targetId);
				targetCreatedBy.remove(modelId);
			}

			final var src2Model = reverseLinks.remove(modelId);
			for (final var srcId : src2Model) {
				final var modelCreatedBy = links.get(srcId);
				modelCreatedBy.remove(modelId);
			}
		}
	}

	public boolean hasModelReference(final String modelId) {
		return storedReferences.containsKey(modelId);
	}

	public Set<String> getAllModelReferenceIds() {
		return Collections.unmodifiableSet(storedReferences.keySet());
	}

	public Collection<TraceModelReference> getAllModelReferences() {
		return Collections.unmodifiableCollection(storedReferences.values());
	}

	public void addOrReplaceTraceLink(TraceModelLink link) {
		Objects.requireNonNull(link, "link");
		synchronized (lock) {
			final var srcRef = getOrCreateModelReference(link.srcModelId);
			final var dstRef = getOrCreateModelReference(link.dstModelId);
			final var linksByDst = links.get(srcRef.getModelId());
			linksByDst.put(dstRef.getModelId(), link);
			reverseLinks.get(dstRef.getModelId()).add(srcRef.getModelId());
		}
	}

	/**
	 * 
	 * 
	 * Returns a set of target model ids. These models are directly (or partially)
	 * derived from the given model.
	 * 
	 * @param modelId source model id
	 * @return a set of, non-null, model ids. The set can be empty.
	 */
	public Set<String> getTargetModelIds(final String modelId) {
		final var targets = links.get(modelId);
		if (targets.isEmpty()) {
			return Collections.emptySet();
		}
		return Collections.unmodifiableSet(targets.keySet());
	}

	/**
	 * Returns a set of source model ids. These models were directly involved in the
	 * creation of the given model.
	 * 
	 * @param modelId target model id
	 * @return a set of, non-null, model ids. The set can be empty.
	 */
	public Set<String> getSourceModelIds(final String modelId) {
		final var sources = reverseLinks.get(modelId);
		if (sources.isEmpty()) {
			return Collections.emptySet();
		}
		return Collections.unmodifiableSet(sources);
	}

	public TraceModelLink getLink(final String srcModelId, final String dstModelId) {
		final var linksByDst = links.get(srcModelId);
		final var link = linksByDst.get(dstModelId);
		return link;
	}

	public TraceChain buildTraceChain(final String srcModelId, final String dstModelId, SearchDirection directions) {
		Objects.requireNonNull(srcModelId);
		Objects.requireNonNull(dstModelId);

		synchronized (lock) {
			checkModelReference(srcModelId);
			checkModelReference(dstModelId);

			if (srcModelId.equals(dstModelId)) {
				return new TraceChain(this, srcModelId, dstModelId, Collections.emptySet());
			}

			var allPaths = PathFinder.computePaths(this, srcModelId, dstModelId, directions);
			return new TraceChain(this, srcModelId, dstModelId, allPaths);
		}
	}

	private TraceModelReference checkModelReference(final String modelId) {
		final var ref = storedReferences.get(modelId);
		if (ref == null) {
			throw new IllegalArgumentException("Model '" + modelId + "' not found.");
		}
		return ref;
	}

}