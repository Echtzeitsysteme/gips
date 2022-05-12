package org.emoflon.gips.core.ilp;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsGlobalObjective;
import org.emoflon.gips.core.GipsMapper;
import org.emoflon.gips.core.GipsMapping;
import org.emoflon.gips.core.GipsMappingConstraint;
import org.emoflon.gips.core.GipsTypeConstraint;
import org.emoflon.gips.core.gt.GipsPatternConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;
import org.gnu.glpk.GLPK;
import org.gnu.glpk.GLPKConstants;
import org.gnu.glpk.SWIGTYPE_p_double;
import org.gnu.glpk.SWIGTYPE_p_int;
import org.gnu.glpk.glp_iocp;
import org.gnu.glpk.glp_prob;

public class GlpkSolver extends ILPSolver {

	private glp_prob model;
	private glp_iocp iocp;
	private Collection<ILPConstraint<Integer>> constraints;
	private Map<String, Integer> ilpVars;

	public GlpkSolver(final GipsEngine engine, final ILPSolverConfig config) {
		super(engine);
		constraints = new HashSet<>();
		ilpVars = new HashMap<>();

		GLPK.glp_free_env();
		model = GLPK.glp_create_prob();
		GLPK.glp_set_prob_name(model, "model");

		// GLPK initialization
		iocp = new glp_iocp();
		GLPK.glp_init_iocp(iocp);
		if (config.enablePresolve()) {
			iocp.setPresolve(GLPK.GLP_ON);
		}
		iocp.setTm_lim((int) config.timeLimit());
		// TODO: Random seed
		// TODO: Set output flag
	}

	@Override
	public ILPSolverOutput solve() {
		setUpVars();
		setUpCnstrs();

		// Solve
		final int ret = GLPK.glp_intopt(model, iocp);
		final boolean unbounded = GLPK.glp_get_status(model) == GLPK.GLP_UNBND;
		final boolean feasible = ret == 0;

		// Determine status
		ILPSolverStatus status = null;
		if (feasible) {
			status = ILPSolverStatus.OPTIMAL;
		} else if (unbounded) {
			status = ILPSolverStatus.UNBOUNDED;
		} else {
			throw new RuntimeException("GLPK: Something went wrong.");
		}
		return new ILPSolverOutput(status, GLPK.glp_mip_obj_val(model));
	}

	@Override
	public void updateValuesFromSolution() {
		// Iterate over all mappers
		for (final String key : engine.getMappers().keySet()) {
			final GipsMapper<?> mapper = engine.getMapper(key);
			// Iterate over all mappings of each mapper
			for (final String k : mapper.getMappings().keySet()) {
				// Get corresponding ILP variable name
				final String varName = mapper.getMapping(k).ilpVariable;
				// Get value of the ILP variable and round it (to eliminate small deltas)
				double result = Math.round(GLPK.glp_mip_col_val(model, ilpVars.get(varName)));
				// Save result value in specific mapping
				mapper.getMapping(k).setValue((int) result);
			}
		}
	}

	@Override
	protected void translateMapping(final GipsMapping mapping) {
		ilpVars.put(mapping.ilpVariable, ilpVars.size() + 1);
	}

	@Override
	protected void translateConstraint(final GipsMappingConstraint<? extends EObject> constraint) {
		constraints.addAll(constraint.getConstraints());
	}

	@Override
	protected void translateConstraint(final GipsPatternConstraint<?, ?> constraint) {
		constraints.addAll(constraint.getConstraints());
	}

	@Override
	protected void translateConstraint(final GipsTypeConstraint<? extends EObject> constraint) {
		constraints.addAll(constraint.getConstraints());
	}

	@Override
	protected void translateObjective(final GipsGlobalObjective objective) {
		final ILPNestedLinearFunction<?> nestFunc = objective.getObjectiveFunction();

		// Set goal
		int goal = 0;
		switch (nestFunc.goal()) {
		case MAX -> {
			goal = GLPKConstants.GLP_MAX;
		}
		case MIN -> {
			goal = GLPKConstants.GLP_MIN;
		}
		}
		GLPK.glp_set_obj_dir(model, goal);

		// Constants
		double constSum = 0;
		for (ILPConstant<Double> c : nestFunc.constants()) {
			constSum += c.weight();
		}

		// Terms
		for (final ILPWeightedLinearFunction<?> lf : nestFunc.linearFunctions()) {
			lf.linearFunction().terms().forEach(t -> {
				GLPK.glp_set_obj_coef(model, ilpVars.get(t.variable().getName()), t.weight());
			});

			for (final ILPConstant<Double> c : lf.linearFunction().constantTerms()) {
				constSum += c.weight();
			}
		}

		// Add global constant sum
		GLPK.glp_set_obj_coef(model, 0, constSum);
	}

	private void setUpVars() {
		// Set number of variables
		GLPK.glp_add_cols(model, ilpVars.size());
		for (final String var : ilpVars.keySet()) {
			GLPK.glp_set_col_name(model, ilpVars.get(var), var);

			// Currently, only binary variables are supported
			GLPK.glp_set_col_kind(model, ilpVars.get(var), GLPKConstants.GLP_BV);
			GLPK.glp_set_col_bnds(model, ilpVars.get(var), GLPKConstants.GLP_DB, 0, 1);
		}
	}

	private void setUpCnstrs() {
		int cnstrRowCounter = 1;
		GLPK.glp_add_rows(model, constraints.size());
		for (final ILPConstraint<Integer> cnstr : constraints) {
			final SWIGTYPE_p_int ind = GLPK.new_intArray(cnstr.lhsTerms().size());
			final SWIGTYPE_p_double val = GLPK.new_doubleArray(cnstr.lhsTerms().size());

			GLPK.glp_set_row_name(model, cnstrRowCounter, "cnstr_" + cnstrRowCounter);
			int termIndexCounter = 0;
			for (final ILPTerm<Integer, Double> term : cnstr.lhsTerms()) {
				final int var = ilpVars.get(term.variable().getName());

				GLPK.intArray_setitem(ind, termIndexCounter + 1, var);
				GLPK.doubleArray_setitem(val, termIndexCounter + 1, term.weight());
				termIndexCounter++;
			}

			setOperator(cnstr.operator(), cnstr.rhsConstantTerm(), cnstrRowCounter);
			GLPK.glp_set_mat_row(model, cnstrRowCounter, cnstr.lhsTerms().size(), ind, val);
		}

	}

	/**
	 * Sets a given operator value (from RelationalOperator) to the corresponding
	 * GLPK int value.
	 *
	 * @param op  Operator value to convert.
	 * @param rhs Double value for the RHS.
	 * @param row Number of the row.
	 */
	private void setOperator(final RelationalOperator op, final double rhs, final int row) {
		switch (op) {
		case LESS:
			throw new UnsupportedOperationException("GLPK does not support LESS in constraints.");
		// TODO: Could be expressed with !(x >= a)
		case LESS_OR_EQUAL:
			GLPK.glp_set_row_bnds(model, row, GLPKConstants.GLP_UP, 0, rhs);
			return;
		case EQUAL:
			GLPK.glp_set_row_bnds(model, row, GLPKConstants.GLP_FX, rhs, rhs);
			return;
		case GREATER_OR_EQUAL:
			GLPK.glp_set_row_bnds(model, row, GLPKConstants.GLP_LO, rhs, 0);
			return;
		case GREATER:
			throw new UnsupportedOperationException("GLPK does not support GREATER in constraints.");
		// TODO: Could be expressed with !(x <= a)
		case NOT_EQUAL:
			throw new UnsupportedOperationException("GLPK does not support NOT EQUAL in constraints.");
		// TODO: This introduces an OR constraint
		// https://math.stackexchange.com/questions/37075/how-can-not-equals-be-expressed-as-an-inequality-for-a-linear-programming-model/1517850
		default:
			throw new UnsupportedOperationException("Not yet implemented.");
		}
	}

}
