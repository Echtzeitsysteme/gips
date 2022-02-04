package org.emoflon.roam.core;

import org.emoflon.roam.intermediate.RoamIntermediate.Mapping;

public abstract class RoamMapperFactory {
	protected final RoamEngine engine;
	
	public RoamMapperFactory(final RoamEngine engine) {
		this.engine = engine;
	}
	
	public abstract RoamMapper createMapper(final Mapping mapping);
}
