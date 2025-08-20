package org.emoflon.gips.core;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.core.gt.GipsGTMapping;

public class MappingIndexer {

//	final protected GipsMapper<?> mapper;
	@SuppressWarnings("rawtypes")
	final protected Map<EObject, Set<GipsGTMapping>> node2mappings = Collections.synchronizedMap(new LinkedHashMap<>());

//	public MappingIndexer(final GipsMapper<?> mapper) {
//		this.mapper = mapper;
//		initialize();
//	}

	public boolean isInitialized() {
		return !node2mappings.isEmpty();
	}

	public void terminate() {
		node2mappings.clear();
	}

	@SuppressWarnings("rawtypes")
	public Set<GipsGTMapping> getMappingsOfNode(final EObject node) {
		final Set<GipsGTMapping> query = Collections.synchronizedSet(new LinkedHashSet<>());
		final Set<GipsGTMapping> candidates = node2mappings.get(node);
		if (candidates != null) {
			query.addAll(candidates);
		}
		return query;
	}

//	private void initialize() {
//		// TODO
//		mapper.getMappings().forEach((name, mapping) -> {
//			final GipsGTMapping<?, ?> m = (GipsGTMapping<?, ?>) mapping;
//			m.getMatch().getPattern().getPatternSet();
//		});
//	}

	@SuppressWarnings("rawtypes")
	public Set<GipsGTMapping> getMappingsOfNodes(final Set<EObject> nodes) {
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

	@SuppressWarnings("rawtypes")
	public synchronized void putMapping(final EObject node, final GipsGTMapping mapping) {
		if (!node2mappings.containsKey(node)) {
			node2mappings.put(node, Collections.synchronizedSet(new LinkedHashSet<>()));
		}
		node2mappings.get(node).add(mapping);
	}

}
