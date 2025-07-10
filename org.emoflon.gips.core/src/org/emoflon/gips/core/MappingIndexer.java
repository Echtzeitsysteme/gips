package org.emoflon.gips.core;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.core.gt.GipsGTMapping;

public class MappingIndexer<M extends GipsGTMapping<?, ?>> {

//	final protected GipsMapper<?> mapper;
	final protected Map<EObject, Set<M>> node2mappings = Collections.synchronizedMap(new LinkedHashMap<>());

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

	public Set<M> getMappingsOfNode(final EObject node) {
		final Set<M> query = Collections.synchronizedSet(new LinkedHashSet<>());
		query.addAll(node2mappings.get(node));
		return query;
	}

//	private void initialize() {
//		// TODO
//		mapper.getMappings().forEach((name, mapping) -> {
//			final GipsGTMapping<?, ?> m = (GipsGTMapping<?, ?>) mapping;
//			m.getMatch().getPattern().getPatternSet();
//		});
//	}

	public Set<M> getMappingsOfNodes(final Set<EObject> nodes) {
		final Set<M> query = Collections.synchronizedSet(new LinkedHashSet<>());
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

	public void putMapping(final EObject node, final M mapping) {
		if (!node2mappings.containsKey(node)) {
			node2mappings.put(node, Collections.synchronizedSet(new LinkedHashSet<>()));
		}
		node2mappings.get(node).add(mapping);
	}

}
