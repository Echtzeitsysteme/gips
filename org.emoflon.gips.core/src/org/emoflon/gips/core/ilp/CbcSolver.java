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

import com.patrikdufresne.cbc4j.CBCLibrary;

public class CbcSolver extends ILPSolver {

	// TODO
	private CBCLibrary model;

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

	public CbcSolver(final GipsEngine engine, final ILPSolverConfig config) {
		super(engine);
		constraints = new HashMap<>();
		ilpVars = new HashMap<>();

		// TODO
	}

	@Override
	public ILPSolverOutput solve() {
		setUpVars();
		setUpCnstrs();
		setUpObj();

		model = new CBCLibrary();
		CBCLibrary.load();

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
	 * Sets all ILP variables for CBC up.
	 */
	private void setUpVars() {
		// In case of 0 variables, simply return
		if (ilpVars.size() == 0) {
			return;
		}

		// TODO
	}

	/**
	 * Sets all constraints for CBC up. Variable setup must be done before.
	 */
	private void setUpCnstrs() {
		// TODO
	}

	/**
	 * Sets the objective function for CBC up. Variable setup must be done before.
	 */
	private void setUpObj() {
		// TODO
	}

}
