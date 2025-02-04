package org.emoflon.gips.core.api;

import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsLinearFunction;
import org.emoflon.gips.intermediate.GipsIntermediate.LinearFunction;
import org.emoflon.ibex.gt.api.GraphTransformationAPI;

public abstract class GipsLinearFunctionFactory<ENGINE extends GipsEngine, EMOFLON_API extends GraphTransformationAPI> {
	protected final ENGINE engine;
	protected final EMOFLON_API eMoflonApi;

	public GipsLinearFunctionFactory(final ENGINE engine, final EMOFLON_API eMoflonApi) {
		this.engine = engine;
		this.eMoflonApi = eMoflonApi;
	}

	public abstract GipsLinearFunction<ENGINE, ? extends LinearFunction, ? extends Object> createLinearFunction(
			final LinearFunction linearFunction);
}
