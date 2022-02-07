package org.emoflon.roam.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping;

public abstract class RoamMapper <M extends RoamMapping>{
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
	
	public M putMapping(final M mapping) {
		return mappings.put(mapping.ilpVariable, mapping);
	}
	
	public M removeMapping(final M mapping) {
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
	
	protected abstract void terminate();
}
