package org.emoflon.roam.core.ilp;

import org.emoflon.roam.core.RoamConstraint;
import org.emoflon.roam.core.RoamEngine;
import org.emoflon.roam.core.RoamMapping;
import org.emoflon.roam.core.RoamMappingConstraint;
import org.emoflon.roam.core.RoamMappingObjective;
import org.emoflon.roam.core.RoamObjective;
import org.emoflon.roam.core.RoamTypeConstraint;
import org.emoflon.roam.core.RoamTypeObjective;

public abstract class ILPSolver {
	final protected RoamEngine engine;
	
	public ILPSolver(final RoamEngine engine) {
		this.engine = engine;
	}
	
	public void buildILPProblem() {
		engine.getMappers().values().stream().flatMap(mapper -> mapper.getMappings().values().stream()).forEach(mapping -> translateMapping(mapping));
		engine.getConstraints().values().forEach(constraint -> translateConstraint(constraint));
		engine.getObjectives().values().forEach(objective -> translateObjective(objective));
	}
	
	public abstract void solve();
	
	public abstract void updateValuesFromSolution();
	
	protected abstract void translateMapping(final RoamMapping mapping);
	
	protected void translateConstraint(final RoamConstraint<?,?,?> constraint){
		if(constraint instanceof RoamMappingConstraint mapping) {
			translateConstraint(mapping);
		} else {
			translateConstraint((RoamTypeConstraint) constraint);
		}
	}
	
	protected abstract void translateConstraint(final RoamMappingConstraint constraint);
	
	protected abstract void translateConstraint(final RoamTypeConstraint constraint);
	
	protected void translateObjective(final RoamObjective<?, ?, ?> objective) {
		if(objective instanceof RoamMappingObjective mapping) {
			translateObjective(mapping);
		} else {
			translateObjective((RoamTypeObjective) objective);
		}
	}
	
	protected abstract void translateObjective(final RoamMappingObjective objective);
	
	protected abstract void translateObjective(final RoamTypeObjective objective);
}
