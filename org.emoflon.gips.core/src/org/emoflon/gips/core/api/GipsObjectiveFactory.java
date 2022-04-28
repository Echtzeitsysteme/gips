package org.emoflon.roam.core.api;

import org.emoflon.ibex.gt.api.GraphTransformationAPI;
import org.emoflon.roam.core.RoamEngine;
import org.emoflon.roam.core.RoamObjective;
import org.emoflon.roam.intermediate.RoamIntermediate.Objective;

public abstract class RoamObjectiveFactory<EMOFLON_API extends GraphTransformationAPI> {
	protected final RoamEngine engine;
	protected final EMOFLON_API eMoflonApi;

	public RoamObjectiveFactory(final RoamEngine engine, final EMOFLON_API eMoflonApi) {
		this.engine = engine;
		this.eMoflonApi = eMoflonApi;
	}

	public abstract RoamObjective<? extends Objective, ? extends Object, ? extends Number> createObjective(
			final Objective objective);
}
