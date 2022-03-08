package org.emoflon.roam.core.api;

import org.emoflon.ibex.gt.api.GraphTransformationAPI;
import org.emoflon.roam.core.RoamConstraint;
import org.emoflon.roam.core.RoamEngine;
import org.emoflon.roam.intermediate.RoamIntermediate.Constraint;

public abstract class RoamConstraintFactory<EMOFLON_API extends GraphTransformationAPI> {
	protected final RoamEngine engine;
	protected final EMOFLON_API eMoflonApi;

	public RoamConstraintFactory(final RoamEngine engine, final EMOFLON_API eMoflonApi) {
		this.engine = engine;
		this.eMoflonApi = eMoflonApi;
	}

	public abstract RoamConstraint<? extends Constraint, ? extends Object, ? extends Number> createConstraint(
			final Constraint constraint);
}
