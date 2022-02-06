package org.emoflon.roam.core.ilp;

import org.emoflon.roam.core.RoamConstraint;
import org.emoflon.roam.core.RoamEngine;
import org.emoflon.roam.core.RoamMapping;

public abstract class ILPSolver {
	final protected RoamEngine engine;
	
	public ILPSolver(final RoamEngine engine) {
		this.engine = engine;
	}
	
	public void buildILPProblem() {
		engine.getMappers().values().stream().flatMap(mapper -> mapper.getMappings().values().stream()).forEach(mapping -> translateMapping(mapping));
		engine.getConstraints().values().forEach(constraint -> translateConstraint(constraint));
	}
	
	public abstract void solve();
	
	public abstract void updateValuesFromSolution();
	
	protected abstract void translateMapping(final RoamMapping mapping);
	
	protected abstract void translateConstraint(final RoamConstraint constraint);
}
