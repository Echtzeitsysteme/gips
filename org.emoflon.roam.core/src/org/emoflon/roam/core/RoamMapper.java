package org.emoflon.roam.core;

import java.util.Set;
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping;

public abstract class RoamMapper {
	final protected RoamEngine engine;
	final protected Mapping mapping;
	protected Set<IRoamMapping> mappings;
	
	protected RoamMapper(final RoamEngine engine, final Mapping mapping) {
		this.engine = engine;
		this.mapping = mapping;
	}
	
	public abstract void init();
}
