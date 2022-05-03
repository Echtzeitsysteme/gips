package org.emoflon.gips.core.api;

import org.emoflon.ibex.gt.api.GraphTransformationAPI;
import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsMapper;
import org.emoflon.gips.core.GipsMapping;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;

public abstract class GipsMapperFactory<EMOFLON_API extends GraphTransformationAPI> {
	protected final GipsEngine engine;
	protected final EMOFLON_API eMoflonApi;

	public GipsMapperFactory(final GipsEngine engine, final EMOFLON_API eMoflonApi) {
		this.engine = engine;
		this.eMoflonApi = eMoflonApi;
	}

	public abstract GipsMapper<? extends GipsMapping> createMapper(final Mapping mapping);
}
