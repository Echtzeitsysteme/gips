package org.emoflon.gips.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.core.gt.GipsGTMapping;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;

public class MappingIndexer {

	@SuppressWarnings("rawtypes")
	final protected Map<EObject, Set<GipsGTMapping>> node2mappings = Collections.synchronizedMap(new LinkedHashMap<>());

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
	private void putMapping(final EObject node, final GipsGTMapping mapping) {
		if (!node2mappings.containsKey(node)) {
			node2mappings.put(node, Collections.synchronizedSet(new LinkedHashSet<>()));
		}
		node2mappings.get(node).add(mapping);
	}

	@SuppressWarnings("rawtypes")
	public synchronized void init(final GipsMapper<?> mapper) {
		mapper.getMappings().values().parallelStream() //
				.map(mapping -> (GipsGTMapping) mapping).forEach(elt -> {
					final List<IBeXNode> allNodesOfPattern = mapper.getMapping().getContextPattern()
							.getSignatureNodes();
					for (final IBeXNode ibexNode : allNodesOfPattern) {
						final String methodName = "get" + StringUtils.capitalize(ibexNode.getName());
						final Class<?> c = elt.getClass();
						try {
							final Method m = c.getDeclaredMethod(methodName);
							final Object object = m.invoke(elt);
							final EObject node = (EObject) object;
							this.putMapping(node, elt);
						} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				});
	}

}
