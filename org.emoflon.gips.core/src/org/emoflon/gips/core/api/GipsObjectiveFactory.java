package org.emoflon.gips.core.api;

import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsObjective;
import org.emoflon.gips.intermediate.GipsIntermediate.Objective;
import org.emoflon.ibex.gt.api.GraphTransformationAPI;

public abstract class GipsObjectiveFactory<ENGINE extends GipsEngine, EMOFLON_API extends GraphTransformationAPI> {
	protected final ENGINE engine;
	protected final EMOFLON_API eMoflonApi;

	public GipsObjectiveFactory(final ENGINE engine, final EMOFLON_API eMoflonApi) {
		this.engine = engine;
		this.eMoflonApi = eMoflonApi;
	}

	public abstract GipsObjective<ENGINE, ? extends Objective, ? extends Object, ? extends Number> createObjective(
			final Objective objective);
}
