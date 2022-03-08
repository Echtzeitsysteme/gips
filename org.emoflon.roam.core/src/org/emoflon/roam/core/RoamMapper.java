package org.emoflon.roam.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.emoflon.roam.intermediate.RoamIntermediate.Mapping;

public abstract class RoamMapper<M extends RoamMapping> {
	final protected RoamEngine engine;
	final protected Mapping mapping;
	final protected Map<String, M> mappings = Collections.synchronizedMap(new HashMap<>());

	public RoamMapper(final RoamEngine engine, final Mapping mapping) {
		this.engine = engine;
		this.mapping = mapping;
		init();
	}

	protected abstract void init();

	public M getMapping(final String ilpVariable) {
		return mappings.get(ilpVariable);
	}

	protected M putMapping(final M mapping) {
		return mappings.put(mapping.ilpVariable, mapping);
	}

	protected M removeMapping(final M mapping) {
		return mappings.remove(mapping.ilpVariable);
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
