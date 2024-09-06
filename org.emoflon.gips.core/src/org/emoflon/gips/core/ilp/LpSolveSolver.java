package org.emoflon.gips.core.ilp;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsGlobalConstraint;
import org.emoflon.gips.core.GipsGlobalObjective;
import org.emoflon.gips.core.GipsMapper;
import org.emoflon.gips.core.GipsMapping;
import org.emoflon.gips.core.GipsMappingConstraint;
import org.emoflon.gips.core.GipsTypeConstraint;
import org.emoflon.gips.core.gt.GipsPatternConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;

import lpsolve.LpSolve;
import lpsolve.LpSolveException;

public class LpSolveSolver extends ILPSolver {

	LpSolve lp;

	/**
	 * Map to collect all ILP variables (name -> {integer (=index), type, lower
	 * bound, upper bound}).
	 */
	private Map<String, VarInformation> ilpVars;

	/**
	 * Record for the variable information: index, type, lower bound, upper bound
	 */
	private record VarInformation(int index, VarType type, Number lb, Number ub) {
	}

	/**
	 * Variable type (binary, integer, double).
	 */
	private enum VarType {
		BIN, INT, DBL
	}

	/**
	 * Map to collect all ILP constraints (name -> collection of constraints).
	 */
	private Map<String, Collection<ILPConstraint>> constraints;

	/**
	 * Global objective.
	 */
	private GipsGlobalObjective objective;

	/**
	 * ILP solver configuration.
	 */
	final private ILPSolverConfig config;

	/**
	 * TODO.
	 * 
	 * @param engine
	 */
	public LpSolveSolver(final GipsEngine engine, final ILPSolverConfig config) {
		super(engine);
		constraints = new HashMap<>();
		ilpVars = new HashMap<>();
		init();
		this.config = config;
	}

	@Override
	public ILPSolverOutput solve() {
		setUpVars();
		setUpCnstrs();
		setUpObj();

		if (config.lpOutput()) {
			try {
				lp.writeLp(config.lpPath());
			} catch (final LpSolveException e) {
				e.printStackTrace();
			}
		}

		// TODO: Return values
		int ret = -1;
		double obj = -Double.MAX_VALUE;
		try {
			ret = lp.solve();
			obj = lp.getObjective();
		} catch (final LpSolveException e) {
			e.printStackTrace();
		}

		int solCntr = 0;
		ILPSolverStatus status = null;
		if (ret == 0) {
			status = ILPSolverStatus.OPTIMAL;
			solCntr = 1;
		} else if (ret == 2) {
			status = ILPSolverStatus.INFEASIBLE;
		} else if (ret == 7) {
			status = ILPSolverStatus.TIME_OUT;
		} else if (ret == 3) {
			status = ILPSolverStatus.UNBOUNDED;
		} else if (ret == 1) {
			status = ILPSolverStatus.FEASIBLE;
			solCntr = 1;
		}

		return new ILPSolverOutput(status, obj, engine.getValidationLog(), solCntr);
	}

	private void setUpObj() {
		if (objective == null) {
			return;
		}

		lp.setAddRowmode(false);

		final ILPNestedLinearFunction nestFunc = objective.getObjectiveFunction();

		// Set goal
		switch (nestFunc.goal()) {
		case MAX -> {
			lp.setMaxim();
		}
		case MIN -> {
			lp.setMinim();
		}
		}

		// Constants will be handled separately

		final double[] coeffs = new double[ilpVars.size() + 1 + 1];

		// Variable terms
		for (final ILPWeightedLinearFunction lf : nestFunc.linearFunctions()) {
			for (var t : lf.linearFunction().terms()) {
				final int varIndex = ilpVars.get(t.variable().getName()).index;
				coeffs[varIndex] = coeffs[varIndex] + (t.weight() * lf.weight());
			}

			// Constants will be handled separately
		}

		final int varIndex = ilpVars.get("Constant").index;
		coeffs[varIndex] = 1;

		try {
			lp.setObjFn(coeffs);
		} catch (final LpSolveException e) {
			e.printStackTrace();
			throw new InternalError();
		}
	}

	private double getObjConst() {
		// If there is no objective, the constant equals to zero
		if (objective == null) {
			return 0;
		}

		final ILPNestedLinearFunction nestFunc = objective.getObjectiveFunction();

		// Constants
		double constSum = 0;
		for (ILPConstant c : nestFunc.constants()) {
			constSum += c.weight();
		}

		// Nested constants
		for (final ILPWeightedLinearFunction lf : nestFunc.linearFunctions()) {
			for (var ct : lf.linearFunction().constantTerms()) {
				constSum += ct.weight() * lf.weight();
			}
		}

		return constSum;
	}

	private void setUpCnstrs() {
		for (final String name : constraints.keySet()) {
			final Iterator<ILPConstraint> cnstrIt = constraints.get(name).iterator();

			int localCnstrCounter = 0;
			while (cnstrIt.hasNext()) {
				final ILPConstraint cnstr = cnstrIt.next();
				if (cnstr == null) {
					continue;
				}

				// For lp_solve, we have to accumulate all weights for identical variables
				final Map<ILPVariable<?>, Double> accumulatedWeights = new HashMap<ILPVariable<?>, Double>();

				for (int i = 0; i < cnstr.lhsTerms().size(); i++) {
					final ILPVariable<?> var = cnstr.lhsTerms().get(i).variable();
					if (accumulatedWeights.containsKey(var)) {
						final double newWeight = accumulatedWeights.remove(var) + cnstr.lhsTerms().get(i).weight();
						accumulatedWeights.put(var, newWeight);
					} else {
						accumulatedWeights.put(var, cnstr.lhsTerms().get(i).weight());
					}
				}

				final int size = accumulatedWeights.size();
				final int[] vars = new int[size];
				final double[] coeffs = new double[size];

				int varIt = 0;
				for (final ILPVariable<?> v : accumulatedWeights.keySet()) {
					vars[varIt] = ilpVars.get(v.getName()).index;
					coeffs[varIt] = accumulatedWeights.get(v);
					varIt++;
				}

				try {
					// unsigned char add_constraintex(lprec *lp, int count, REAL *row, int *colno,
					// int constr_type, REAL rh);
					lp.addConstraintex(coeffs.length, coeffs, vars, convertOperator(cnstr.operator()),
							cnstr.rhsConstantTerm());
					// TODO: Not to sure about that^

					// Set row name for the newly created constraint
					lp.setRowName(lp.getNrows(), name + "_" + localCnstrCounter);
				} catch (final LpSolveException e) {
					e.printStackTrace();
					throw new InternalError();
				}

				localCnstrCounter++;
			}
		}
	}

	private int convertOperator(final RelationalOperator op) {
		switch (op) {
		case LESS:
			throw new UnsupportedOperationException("lp_solve does not support LESS in constraints.");
		case LESS_OR_EQUAL:
			return 1;
		case EQUAL:
			return 3;
		case GREATER_OR_EQUAL:
			return 2;
		case GREATER:
			throw new UnsupportedOperationException("lp_solve does not support GREATER in constraints.");
		case NOT_EQUAL:
			throw new UnsupportedOperationException("lp_solve does not support NOT EQUAL in constraints.");
		default:
			throw new UnsupportedOperationException("Not yet implemented.");
		}
	}

	private void setUpVars() {
		// Add a generic double variable to be used as a constant for the objective
		final double constSum = getObjConst();
		createDblVar("Constant", constSum, constSum);

		try {
			lp = LpSolve.makeLp(0, ilpVars.size());
			if (lp.getLp() == 0) {
				throw new InternalError();
			}

			// In case of 0 variables, simply return
			if (ilpVars.size() == 0) {
				return;
			}

			// Else, set information for each variable
			for (final String name : ilpVars.keySet()) {
				int colCntr = ilpVars.get(name).index;
				lp.setColName(colCntr, name);

				final VarInformation varInfo = ilpVars.get(name);
				if (varInfo.type == VarType.BIN) {
					lp.setBinary(colCntr, true);
				} else if (varInfo.type == VarType.INT) {
					lp.setInt(colCntr, true);
				}

				// Set bounds
				lp.setBounds(colCntr, varInfo.lb.doubleValue(), varInfo.ub.doubleValue());
			}

		} catch (final LpSolveException e) {
			e.printStackTrace();
			throw new InternalError();
		}
	}

	@Override
	public void updateValuesFromSolution() {
		double[] vals = new double[ilpVars.size()];
		try {
			lp.getVariables(vals);
		} catch (final LpSolveException e) {
			e.printStackTrace();
			throw new InternalError();
		}

		// Iterate over all mappers
		for (final String key : engine.getMappers().keySet()) {
			final GipsMapper<?> mapper = engine.getMapper(key);
			// Iterate over all mappings of each mapper
			for (final String k : mapper.getMappings().keySet()) {
				// Get corresponding ILP variable name
				final GipsMapping mapping = mapper.getMapping(k);
				final String varName = mapping.getName();
				// Get value of the ILP variable and round it (to eliminate small deltas)
				double result = Math.round(vals[ilpVars.get(varName).index - 1]);
				// Save result value in specific mapping
				mapping.setValue((int) result);

				// Save all values of additional variables if any
				if (mapping.hasAdditionalVariables()) {
					for (Entry<String, ILPVariable<?>> var : mapping.getAdditionalVariables().entrySet()) {
						double mappingVarResult = vals[ilpVars.get(var.getValue().getName()).index - 1];
						mapping.setAdditionalVariableValue(var.getKey(), mappingVarResult);
					}
				}
			}
		}
		// Solver reset will be handled by the GipsEngine afterward
	}

	@Override
	protected void translateMapping(final GipsMapping mapping) {
		createBinVar(mapping.getName(), mapping.getLowerBound(), mapping.getUpperBound());
		if (mapping.hasAdditionalVariables()) {
			createAdditionalVars(mapping.getAdditionalVariables().values());
		}

	}

	private void createAdditionalVars(final Collection<ILPVariable<?>> variables) {
		for (final ILPVariable<?> variable : variables) {
			if (variable instanceof ILPBinaryVariable binVar) {
				createBinVar(binVar.name, binVar.getLowerBound(), binVar.getUpperBound());
			} else if (variable instanceof ILPIntegerVariable intVar) {
				createIntVar(intVar.name, intVar.getLowerBound(), intVar.getUpperBound());
			} else if (variable instanceof ILPRealVariable realVar) {
				createDblVar(realVar.name, realVar.getLowerBound(), realVar.getUpperBound());
			} else {
				throw new IllegalArgumentException("Unsupported variable type: " + variable.getClass().getSimpleName());
			}
		}
	}

	private void createBinVar(final String name, final Integer lowerBound, final Integer upperBound) {
		if (ilpVars.containsKey(name)) {
			return;
		}

		ilpVars.put(name, new VarInformation(ilpVars.size() + 1, VarType.BIN, lowerBound, upperBound));
	}

	private void createIntVar(final String name, final Integer lowerBound, final Integer upperBound) {
		if (ilpVars.containsKey(name)) {
			return;
		}

		ilpVars.put(name, new VarInformation(ilpVars.size() + 1, VarType.INT, lowerBound, upperBound));
	}

	private void createDblVar(final String name, final Number lowerBound, final Number upperBound) {
		if (ilpVars.containsKey(name)) {
			return;
		}

		ilpVars.put(name, new VarInformation(ilpVars.size() + 1, VarType.DBL, lowerBound, upperBound));
	}

	@Override
	protected void translateConstraint(final GipsMappingConstraint<?, ? extends EObject> constraint) {
		createAdditionalVars(constraint.getAdditionalVariables());
		final Set<ILPConstraint> collectedCnstr = new HashSet<>();
		collectedCnstr.addAll(constraint.getConstraints());
		collectedCnstr.addAll(constraint.getAdditionalConstraints());
		constraints.put(constraint.getName(), collectedCnstr);
	}

	@Override
	protected void translateConstraint(final GipsPatternConstraint<?, ?, ?> constraint) {
		createAdditionalVars(constraint.getAdditionalVariables());
		final Set<ILPConstraint> collectedCnstr = new HashSet<>();
		collectedCnstr.addAll(constraint.getConstraints());
		collectedCnstr.addAll(constraint.getAdditionalConstraints());
		constraints.put(constraint.getName(), collectedCnstr);
	}

	@Override
	protected void translateConstraint(final GipsTypeConstraint<?, ? extends EObject> constraint) {
		createAdditionalVars(constraint.getAdditionalVariables());
		final Set<ILPConstraint> collectedCnstr = new HashSet<>();
		collectedCnstr.addAll(constraint.getConstraints());
		collectedCnstr.addAll(constraint.getAdditionalConstraints());
		constraints.put(constraint.getName(), collectedCnstr);
	}

	@Override
	protected void translateConstraint(final GipsGlobalConstraint<?> constraint) {
		createAdditionalVars(constraint.getAdditionalVariables());
		final Set<ILPConstraint> collectedCnstr = new HashSet<>();
		collectedCnstr.addAll(constraint.getConstraints());
		collectedCnstr.addAll(constraint.getAdditionalConstraints());
		constraints.put(constraint.getName(), collectedCnstr);
	}

	@Override
	protected void translateObjective(final GipsGlobalObjective objective) {
		this.objective = objective;
	}

	@Override
	public void terminate() {
		if (lp != null) {
			if (lp.getLp() != 0) {
				lp.deleteLp();
			}
		}
	}

	@Override
	public void reset() {
		init();
	}

	private void init() {
		constraints.clear();
		ilpVars.clear();
		lp = null;

		// TODO: Implement solver config specific operations
	}

}
