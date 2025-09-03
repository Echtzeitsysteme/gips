package org.emoflon.gips.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;

public abstract class GipsMapper<M extends GipsMapping> {
	final protected GipsEngine engine;
	final protected Mapping mapping;
	final protected Map<String, M> mappings = Collections.synchronizedMap(new HashMap<>());

	public GipsMapper(final GipsEngine engine, final Mapping mapping) {
		this.engine = engine;
		this.mapping = mapping;
	}

	public M getMapping(final String milpVariable) {
		return mappings.get(milpVariable);
	}

	protected M putMapping(final M mapping) {
		return mappings.put(mapping.getName(), mapping);
	}

	protected M removeMapping(final M mapping) {
		return mappings.remove(mapping.getName());
	}

	public String getName() {
		return mapping.getName();
	}

	public Mapping getMapping() {
		return mapping;
	}

	public Map<String, M> getMappings() {
		return mappings;
	}

	public Collection<M> getNonZeroVariableMappings() {
		return mappings.values().stream().filter(m -> m.getValue() > 0).collect(Collectors.toSet());
	}

	public Collection<M> getZeroVariableMappings() {
		return mappings.values().stream().filter(m -> m.getValue() == 0).collect(Collectors.toSet());
	}

	public Collection<M> getMappings(Function<Integer, Boolean> predicate) {
		return mappings.values().stream().filter(m -> predicate.apply(m.getValue())).collect(Collectors.toSet());
	}

	protected abstract void terminate();
}
