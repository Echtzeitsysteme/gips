package org.emoflon.roam.core.api;

import org.emoflon.roam.core.RoamEngine;
import org.emoflon.roam.core.RoamMapper;
import org.emoflon.roam.core.RoamMapping;
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping;

public abstract class RoamMapperFactory {
	protected final RoamEngine engine;

	public RoamMapperFactory(final RoamEngine engine) {
		this.engine = engine;
	}

	public abstract RoamMapper<? extends RoamMapping> createMapper(final Mapping mapping);
}
