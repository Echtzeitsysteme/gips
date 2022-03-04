package org.emoflon.roam.core.api;

import org.emoflon.roam.core.RoamConstraint;
import org.emoflon.roam.core.RoamEngine;
import org.emoflon.roam.intermediate.RoamIntermediate.Constraint;

public abstract class RoamConstraintFactory {
	protected final RoamEngine engine;

	public RoamConstraintFactory(final RoamEngine engine) {
		this.engine = engine;
	}

	public abstract RoamConstraint<? extends Constraint, ? extends Object, ? extends Number> createConstraint(
			final Constraint constraint);
}
