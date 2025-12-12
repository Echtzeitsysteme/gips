package org.emoflon.gips.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.core.gt.GipsGTMapping;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;

/**
 * Class that holds the index for one specific `GipsMapper`.
 */
public class MappingIndexer {

	/**
	 * If false, the indexer was not initialized before (e.g., by another parallel
	 * call).
	 */
	private boolean initialized = false;

	/**
	 * Map that holds the indexing information, i.e., it maps a node (`EObject`) to
	 * a set of `GipsGTMapping`s.
	 */
	@SuppressWarnings("rawtypes")
	private final Map<EObject, Set<GipsGTMapping>> node2mappings = Collections.synchronizedMap(new LinkedHashMap<>());

	/**
	 * Terminates the indexer instances. This method clears all lookup data
	 * structures and sets the initialization flag to `false`.
	 */
	public void terminate() {
		node2mappings.clear();
		initialized = false;
	}

	/**
	 * Returns a set of `GipsGTMapping`s for the given `EObject node` is contained
	 * in (copy). Since this method copies the set, it is slower than the unsafe
	 * variant.
	 * 
	 * @param node EObject to return the indexed `GipsGTMapping`s for.
	 * @return Indexed `GipsGTMapping`s for the given `EObject node` (copy).
	 */
	@SuppressWarnings("rawtypes")
	private Set<GipsGTMapping> getMappingsOfNodeSafe(final EObject node) {
		Objects.requireNonNull(node);
		final Set<GipsGTMapping> query = Collections.synchronizedSet(new LinkedHashSet<>());
		query.addAll(getMappingsOfNodeUnsafe(node));
		return query;
	}

	/**
	 * Returns a set of `GipsGTMapping`s for the given `EObject node` is contained
	 * in (no copy). Since this method does not copy the set, it is faster than the
	 * safe variant.
	 * 
	 * Use with caution: manipulating the returned set of mappings will corrupt the
	 * index of the respective mapping.
	 * 
	 * @param node EObject to return the indexed `GipsGTMapping`s for.
	 * @return Indexed `GipsGTMapping`s for the given `EObject node` (no copy).
	 */
	@SuppressWarnings("rawtypes")
	private Set<GipsGTMapping> getMappingsOfNodeUnsafe(final EObject node) {
		Objects.requireNonNull(node);
		final Set<GipsGTMapping> candidates = node2mappings.get(node);
		if (candidates != null) {
			return candidates;
		}
		return Collections.synchronizedSet(new LinkedHashSet<>());
	}

	/**
	 * Returns a set of `GipsGTMapping`s for the given set of `EObject`s (i.e.,
	 * where they are contained in). A qualified `GipsGTMapping` must contain all of
	 * the given `EObject`s at once.
	 * 
	 * @param nodes Set of EObjects to return the indexed `GipsGTMapping`s for.
	 * @return Indexed `GipsGTMapping`s for the given Set of EObjects.
	 */
	@SuppressWarnings("rawtypes")
	public Set<GipsGTMapping> getMappingsOfNodes(final Set<EObject> nodes) {
		Objects.requireNonNull(nodes);

		// Find smallest set of nodes
		EObject candidateNode = null;
		int candidateSize = Integer.MAX_VALUE;
		for (final EObject obj : nodes) {
			// If a node is not contained at all, return an empty collection immediately. In
			// this case, all other calculations can be skipped since the final intersection
			// will be empty.
			if (!node2mappings.containsKey(obj)) {
				return Collections.synchronizedSet(new LinkedHashSet<>());
			}

			final int size = node2mappings.get(obj).size();
			// First node must be added as candidate regardless of its set size
			if (candidateNode == null) {
				candidateNode = obj;
				candidateSize = size;
			} else {
				// If the current node has a smaller size than the previously found candidate
				// node, replace it
				if (candidateSize > size) {
					candidateNode = obj;
					candidateSize = size;
				}
			}
		}

		// Start with smallest set of nodes
		final Set<GipsGTMapping> query = getMappingsOfNodeSafe(candidateNode);
		for (final EObject obj : nodes) {
			// Skip candidate node
			if (!obj.equals(candidateNode)) {
				query.retainAll(getMappingsOfNodeUnsafe(obj));
			}
		}

		return query;
	}

	/**
	 * Adds the given GipsGTMapping `mapping` to the index for the given EObject
	 * `node`.
	 * 
	 * @param node    EObject to add the given GipsGTMapping `mapping` to its index
	 *                for.
	 * @param mapping GipsGTMapping that should be added to the respective index.
	 */
	@SuppressWarnings("rawtypes")
	private void putMapping(final EObject node, final GipsGTMapping mapping) {
		Objects.requireNonNull(node);
		Objects.requireNonNull(mapping);
		if (!node2mappings.containsKey(node)) {
			node2mappings.put(node, Collections.synchronizedSet(new LinkedHashSet<>()));
		}
		node2mappings.get(node).add(mapping);
	}

	public synchronized void initIfNecessary(final GipsMapper<?> mapper) {
		initIfNecessary(mapper, Collections.emptySet());
	}

	/**
	 * If not already initialized, this method uses the given GipsMapper `mapper` to
	 * index the set of given nodes. Therefore, it accesses all mappings of `mapper`
	 * and collects all signature nodes of the respective match. For every node `n`
	 * of all matches, the respective `mapper` will be added to the index of `n`.
	 * 
	 * This method is synchronized to ensure that multiple parallel calls to this
	 * method just lead to exactly one index creation (and all other parallel
	 * accesses have to wait until the single index creation has finished and,
	 * afterwards, only return and do nothing).
	 * 
	 * @param mapper GipsMapper to create the index for
	 * @param nodes  to be indexed, if empty, all nodes will be indexed
	 */
	public synchronized void initIfNecessary(final GipsMapper<?> mapper, Collection<String> nodes) {
		Objects.requireNonNull(mapper);
		if (initialized)
			return;

		final Collection<String> nodeNames = getMapperNodeNames(mapper, nodes);

		mapper.getMappings().values().stream() //
				.map(mapping -> (GipsGTMapping<?, ?>) mapping) //
				.forEach(elt -> {
					IMatch match = elt.getMatch().toIMatch();
					for (final String nodeName : nodeNames) {
						final EObject node = (EObject) match.get(nodeName);
						this.putMapping(node, elt);
					}
				});

		initialized = true;
	}

	private Collection<String> getMapperNodeNames(GipsMapper<?> mapper, Collection<String> nodeNames) {
		if (!nodeNames.isEmpty())
			return nodeNames;

		// if no nodes are supplied, we assume there might be an error or, for some
		// reason, we don't know which nodes are needed. To ensure that the indexer
		// still works, we cache every node of a mapper

		List<IBeXNode> allNodesOfPattern = mapper.getMapping().getContextPattern().getSignatureNodes();

		Collection<String> allNodeNames = new HashSet<>();
		for (final IBeXNode ibexNode : allNodesOfPattern)
			allNodeNames.add(ibexNode.getName());
		return allNodeNames;
	}

}
