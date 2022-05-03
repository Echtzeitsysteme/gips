package org.emoflon.gips.core.api;

import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsObjective;
import org.emoflon.gips.intermediate.GipsIntermediate.Objective;
import org.emoflon.ibex.gt.api.GraphTransformationAPI;

public abstract class GipsObjectiveFactory<EMOFLON_API extends GraphTransformationAPI> {
	protected final GipsEngine engine;
	protected final EMOFLON_API eMoflonApi;

	public GipsObjectiveFactory(final GipsEngine engine, final EMOFLON_API eMoflonApi) {
		this.engine = engine;
		this.eMoflonApi = eMoflonApi;
	}

	public abstract GipsObjective<? extends Objective, ? extends Object, ? extends Number> createObjective(
			final Objective objective);
}
