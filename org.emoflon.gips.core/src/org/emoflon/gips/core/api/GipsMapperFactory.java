package org.emoflon.roam.core.api;

import org.emoflon.ibex.gt.api.GraphTransformationAPI;
import org.emoflon.roam.core.RoamEngine;
import org.emoflon.roam.core.RoamMapper;
import org.emoflon.roam.core.RoamMapping;
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping;

public abstract class RoamMapperFactory<EMOFLON_API extends GraphTransformationAPI> {
	protected final RoamEngine engine;
	protected final EMOFLON_API eMoflonApi;

	public RoamMapperFactory(final RoamEngine engine, final EMOFLON_API eMoflonApi) {
		this.engine = engine;
		this.eMoflonApi = eMoflonApi;
	}

	public abstract RoamMapper<? extends RoamMapping> createMapper(final Mapping mapping);
}
