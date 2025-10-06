package org.emoflon.gips.core.milp;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.core.GipsConstraint;
import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsGlobalConstraint;
import org.emoflon.gips.core.GipsMapping;
import org.emoflon.gips.core.GipsMappingConstraint;
import org.emoflon.gips.core.GipsObjective;
import org.emoflon.gips.core.GipsTypeConstraint;
import org.emoflon.gips.core.gt.GipsPatternConstraint;
import org.emoflon.gips.core.gt.GipsRuleConstraint;

public abstract class Solver {
	final protected GipsEngine engine;

	/**
	 * MILP solver configuration.
	 */
	protected final SolverConfig config;

	public Solver(final GipsEngine engine, final SolverConfig config) {
		this.engine = engine;
		this.config = config;
	}

	/**
	 * Returns the solver configuration.
	 * 
	 * @return Solver configuration.
	 */
	public SolverConfig getSolverConfig() {
		return this.config;
	}

	/**
	 * Re-initializes the (M)ILP solver. May be necessary after changing solver
	 * specific configurations in the given {@link SolverConfig}.
	 * 
	 * @throws Exception Throws an exception if the re-initialization fails.
	 */
	public void reinitialize() throws Exception {
		terminate();
		init();
	}

	/**
	 * Initialize this solver. A solver can be initialized multiple times and should
	 * not fail because it has already been initialized.
	 */
	public abstract void init();

	public void buildMILPProblem() {
		engine.getMappers().values().stream() //
				.flatMap(mapper -> mapper.getMappings().values().stream()) //
				.forEach(this::translateMapping);

		Collection<GipsConstraint<?, ?, ?>> constraints = engine.getConstraints().values();
		if (engine.getConstraintSorter() != null)
			// copy view into a sortable list
			constraints = engine.getConstraintSorter().sort(new ArrayList<>(constraints));

		constraints.forEach(this::translateConstraint);

		GipsObjective go = engine.getObjective();
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
		} else if (constraint instanceof GipsRuleConstraint<?, ?, ?> rule) {
			translateConstraint(rule);
		} else if (constraint instanceof GipsTypeConstraint<?, ?> type) {
			translateConstraint(type);
		} else {
			translateConstraint((GipsGlobalConstraint<?>) constraint);
		}
	}

	protected abstract void translateConstraint(final GipsMappingConstraint<?, ? extends EObject> constraint);

	protected abstract void translateConstraint(final GipsPatternConstraint<?, ?, ?> constraint);

	protected abstract void translateConstraint(final GipsRuleConstraint<?, ?, ?> constraint);

	protected abstract void translateConstraint(final GipsTypeConstraint<?, ? extends EObject> constraint);

	protected abstract void translateConstraint(final GipsGlobalConstraint<?> constraint);

	protected abstract void translateObjective(final GipsObjective objective);

	/**
	 * Terminates this solver and releases all resources used. A solver can be
	 * terminated more than once or before it has been initialized. This method
	 * should not fail for either reason.
	 */
	public abstract void terminate();

	public abstract void reset();

}
