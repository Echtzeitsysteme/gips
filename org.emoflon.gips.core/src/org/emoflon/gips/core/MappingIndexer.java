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
	 * in.
	 * 
	 * @param node EObject to return the indexed `GipsGTMapping`s for.
	 * @return Indexed `GipsGTMapping`s for the given `EObject node`.
	 */
	@SuppressWarnings("rawtypes")
	public Set<GipsGTMapping> getMappingsOfNode(final EObject node) {
		Objects.requireNonNull(node);
		final Set<GipsGTMapping> query = Collections.synchronizedSet(new LinkedHashSet<>());
		final Set<GipsGTMapping> candidates = node2mappings.get(node);
		if (candidates != null) {
			query.addAll(candidates);
		}
		return query;
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
		final Set<GipsGTMapping> query = Collections.synchronizedSet(new LinkedHashSet<>());
		boolean first = true;
		for (final EObject obj : nodes) {
			if (first) {
				query.addAll(getMappingsOfNode(obj));
				first = false;
			} else {
				query.retainAll(getMappingsOfNode(obj));
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

		synchronized (this) {
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
	}

	private Collection<String> getMapperNodeNames(GipsMapper<?> mapper, Collection<String> nodeNames) {
		if (!nodeNames.isEmpty())
			return nodeNames;

		// if no nodes are supplied, we assume there might be an error or, for some
		// reason, we don't know which nodes are needed. To ensure that the indexer
		// still works, we cache every node of a mapper

		List<IBeXNode> allNodesOfPattern = mapper.getMapping().getContextPattern().getSignatureNodes();

		nodeNames = new HashSet<>();
		for (final IBeXNode ibexNode : allNodesOfPattern)
			nodeNames.add(ibexNode.getName());

		return nodeNames;
	}

}
