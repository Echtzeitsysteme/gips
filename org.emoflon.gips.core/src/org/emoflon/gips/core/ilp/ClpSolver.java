package org.emoflon.gips.core.ilp;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsGlobalConstraint;
import org.emoflon.gips.core.GipsGlobalObjective;
import org.emoflon.gips.core.GipsMapping;
import org.emoflon.gips.core.GipsMappingConstraint;
import org.emoflon.gips.core.GipsTypeConstraint;
import org.emoflon.gips.core.gt.GipsPatternConstraint;

import com.quantego.clp.CLP;

public class ClpSolver extends ILPSolver {

	private CLP model;

	/**
	 * Map to collect all ILP constraints (name -> collection of constraints).
	 */
	private Map<String, Collection<ILPConstraint<Integer>>> constraints;

	/**
	 * Map to collect all ILP variables (name -> integer).
	 */
	private Map<String, Integer> ilpVars;

	/**
	 * Global objective.
	 */
	private GipsGlobalObjective objective;

	public ClpSolver(final GipsEngine engine, final ILPSolverConfig config) {
		super(engine);
		constraints = new HashMap<>();
		ilpVars = new HashMap<>();

		model = new CLP();

		// CLP initialization
		model.presolve(config.enablePresolve());
		model.maxSeconds((int) config.timeLimit());
		if (config.enableTolerance()) {
			model.primalTolerance(config.tolerance());
			model.dualTolerance(config.tolerance());
		}
		if (config.enableOutput()) {
			model.verbose(2);
		} else {
			model.verbose(0);
		}
	}

	@Override
	public ILPSolverOutput solve() {
		setUpVars();
		setUpCnstrs();
		setUpObj();

		// TODO
		return null;
	}

	@Override
	public void updateValuesFromSolution() {
		// TODO
	}

	@Override
	protected void translateMapping(final GipsMapping mapping) {
		ilpVars.put(mapping.ilpVariable, ilpVars.size() + 1);
	}

	@Override
	protected void translateConstraint(final GipsMappingConstraint<?, ? extends EObject> constraint) {
		constraints.put(constraint.getName(), constraint.getConstraints());
	}

	@Override
	protected void translateConstraint(final GipsPatternConstraint<?, ?, ?> constraint) {
		constraints.put(constraint.getName(), constraint.getConstraints());
	}

	@Override
	protected void translateConstraint(final GipsTypeConstraint<?, ? extends EObject> constraint) {
		constraints.put(constraint.getName(), constraint.getConstraints());
	}

	@Override
	protected void translateConstraint(GipsGlobalConstraint<?> constraint) {
		constraints.put(constraint.getName(), constraint.getConstraints());
	}

	@Override
	protected void translateObjective(final GipsGlobalObjective objective) {
		this.objective = objective;
	}

	/**
	 * Sets all ILP variables for CLP up.
	 */
	private void setUpVars() {
		// In case of 0 variables, simply return
		if (ilpVars.size() == 0) {
			return;
		}

		// TODO
		for (int i = 0; i < ilpVars.size(); i++) {
			model.addVariable().bounds(0, 1);
		}
	}

	/**
	 * Sets all constraints for CLP up. Variable setup must be done before.
	 */
	private void setUpCnstrs() {
		// TODO
	}

	/**
	 * Sets the objective function for CLP up. Variable setup must be done before.
	 */
	private void setUpObj() {
		// TODO
	}

}
