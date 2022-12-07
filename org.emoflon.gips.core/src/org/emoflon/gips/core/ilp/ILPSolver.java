package org.emoflon.gips.core.ilp;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.core.GipsConstraint;
import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsGlobalConstraint;
import org.emoflon.gips.core.GipsGlobalObjective;
import org.emoflon.gips.core.GipsMapping;
import org.emoflon.gips.core.GipsMappingConstraint;
import org.emoflon.gips.core.GipsTypeConstraint;
import org.emoflon.gips.core.gt.GipsPatternConstraint;

public abstract class ILPSolver {
	final protected GipsEngine engine;

	public ILPSolver(final GipsEngine engine) {
		this.engine = engine;
	}

	public void buildILPProblem() {
		engine.getMappers().values().stream().flatMap(mapper -> mapper.getMappings().values().stream())
				.forEach(mapping -> translateMapping(mapping));
		engine.getConstraints().values().forEach(constraint -> translateConstraint(constraint));
		GipsGlobalObjective go = engine.getGlobalObjective();
		if (go != null)
			translateObjective(go);
	}

	public abstract ILPSolverOutput solve();

	public abstract void updateValuesFromSolution();

	protected abstract void translateMapping(final GipsMapping mapping);

	protected void translateConstraint(final GipsConstraint<?, ?, ?> constraint) {
		if (constraint instanceof GipsMappingConstraint<?, ?> mapping) {
			translateConstraint(mapping);
		} else if (constraint instanceof GipsPatternConstraint<?, ?, ?> pattern) {
			translateConstraint(pattern);
		} else if (constraint instanceof GipsTypeConstraint<?, ?> type) {
			translateConstraint(type);
		} else {
			translateConstraint((GipsGlobalConstraint<?>) constraint);
		}
	}

	protected abstract void translateConstraint(final GipsMappingConstraint<?, ? extends EObject> constraint);

	protected abstract void translateConstraint(final GipsPatternConstraint<?, ?, ?> constraint);

	protected abstract void translateConstraint(final GipsTypeConstraint<?, ? extends EObject> constraint);

	protected abstract void translateConstraint(final GipsGlobalConstraint<?> constraint);

	protected abstract void translateObjective(final GipsGlobalObjective objective);
	
	public abstract void terminate();
}
