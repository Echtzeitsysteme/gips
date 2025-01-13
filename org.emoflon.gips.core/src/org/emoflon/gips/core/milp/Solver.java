package org.emoflon.gips.core.milp;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.core.GipsConstraint;
import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsGlobalConstraint;
import org.emoflon.gips.core.GipsObjective;
import org.emoflon.gips.core.GipsMapping;
import org.emoflon.gips.core.GipsMappingConstraint;
import org.emoflon.gips.core.GipsTypeConstraint;
import org.emoflon.gips.core.gt.GipsPatternConstraint;

public abstract class Solver {
	final protected GipsEngine engine;

	public Solver(final GipsEngine engine) {
		this.engine = engine;
	}

	public void buildILPProblem() {
		engine.getMappers().values().stream().flatMap(mapper -> mapper.getMappings().values().stream())
				.forEach(mapping -> translateMapping(mapping));
		engine.getConstraints().values().forEach(constraint -> translateConstraint(constraint));
		GipsObjective go = engine.getGlobalObjective();
		if (go != null)
			translateObjective(go);
	}

	public abstract SolverOutput solve();

	public abstract void updateValuesFromSolution();

	protected abstract void translateMapping(final GipsMapping mapping);

	@SuppressWarnings("unchecked")
	protected void translateConstraint(final GipsConstraint<?, ?, ?> constraint) {
		if (constraint instanceof GipsMappingConstraint<?, ?> mapping) {
			translateConstraint((GipsMappingConstraint<?, ? extends EObject>) mapping);
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

	protected abstract void translateObjective(final GipsObjective objective);

	public abstract void terminate();

	public abstract void reset();

}
