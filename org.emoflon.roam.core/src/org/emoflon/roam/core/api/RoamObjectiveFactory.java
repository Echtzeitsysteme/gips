package org.emoflon.roam.core.api;

import org.emoflon.roam.core.RoamEngine;
import org.emoflon.roam.core.RoamObjective;
import org.emoflon.roam.intermediate.RoamIntermediate.Objective;

public abstract class RoamObjectiveFactory {
	protected final RoamEngine engine;
	
	public RoamObjectiveFactory(final RoamEngine engine) {
		this.engine = engine;
	}
	
	public abstract RoamObjective<? extends Objective, ? extends Object, ? extends Number> createObjective(final Objective objective);
}
