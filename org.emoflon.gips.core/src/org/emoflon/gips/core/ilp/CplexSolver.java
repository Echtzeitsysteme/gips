package org.emoflon.gips.core.ilp;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
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

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloObjective;
import ilog.cplex.IloCplex;

public class CplexSolver extends ILPSolver {

	/**
	 * CPLEX model.
	 */
	private IloCplex cplex;

	/**
	 * Map to collect all ILP constraints (name -> collection of constraints).
	 */
	private Map<String, Collection<ILPConstraint>> constraints = new HashMap<>();

	/**
	 * Map to collect all ILP variables (name -> CPLEX numeric vars).
	 */
	private final Map<String, IloNumVar> vars = new HashMap<>();

	/**
	 * Global objective.
	 */
	private GipsGlobalObjective objective;

	public CplexSolver(final GipsEngine engine, final ILPSolverConfig config) {
		super(engine);

		try {
			cplex = new IloCplex();
			if (config.timeLimitEnabled()) {
				// TODO: Check if unit (e.g., milliseconds) is correct
				cplex.setParam(IloCplex.Param.TimeLimit, config.timeLimit());
			}
			cplex.setParam(IloCplex.Param.RandomSeed, config.randomSeed());
			cplex.setParam(IloCplex.Param.Preprocessing.Presolve, config.enablePresolve());
			// TODO: Tolerance
			if (!config.enableOutput()) {
				cplex.setOut(null);
			}

		} catch (final IloException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public ILPSolverOutput solve() {
		setUpCnstrs();
		setUpObj();

		try {
			// Solving
			final boolean solve = cplex.solve();

			// Get the objective result
			double objVal = 0;
			if (solve) {
				objVal = cplex.getObjValue();
			}

			// Determine status
			ILPSolverStatus status = null;
			if (cplex.getStatus() == IloCplex.Status.Unbounded) {
				status = ILPSolverStatus.UNBOUNDED;
			} else if (cplex.getStatus() == IloCplex.Status.InfeasibleOrUnbounded) {
				status = ILPSolverStatus.INF_OR_UNBD;
			} else if (cplex.getStatus() == IloCplex.Status.Infeasible) {
				status = ILPSolverStatus.INFEASIBLE;
			} else if (cplex.getStatus() == IloCplex.Status.Optimal) {
				status = ILPSolverStatus.OPTIMAL;
			} else if (cplex.getStatus() == IloCplex.Status.Unknown) {
				status = ILPSolverStatus.TIME_OUT;
			} else {
				throw new RuntimeException("Unknown solver status.");
			}

			return new ILPSolverOutput(status, objVal, engine.getValidationLog());
		} catch (final IloException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void updateValuesFromSolution() {
		// Iterate over all mappers
		for (final String key : engine.getMappers().keySet()) {
			final GipsMapper<?> mapper = engine.getMapper(key);
			// Iterate over all mappings of each mapper
			for (final String k : mapper.getMappings().keySet()) {
				// Get corresponding ILP variable name
				final String varName = mapper.getMapping(k).getName();
				// Get value of the ILP variable and round it (to eliminate small deltas)
				double result = 0;
				try {
					result = cplex.getValue(vars.get(varName));
				} catch (final IloException ex) {
					throw new RuntimeException(ex);
				}
				// Save result value in specific mapping
				mapper.getMapping(k).setValue((int) result);
			}
		}
	}

	@Override
	protected void translateMapping(final GipsMapping mapping) {
		createBinVar(mapping.getName());
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
	protected void translateConstraint(GipsGlobalConstraint<?> constraint) {
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

	/**
	 * Sets all constraints for CPLEX up. Variable setup must be done before.
	 */
	private void setUpCnstrs() {
		// Determine total number of constraints
		int numRows = 0;
		for (final Collection<ILPConstraint> col : constraints.values()) {
			numRows += col.size();
		}

		// In case of 0 constraints, simply return
		if (numRows == 0) {
			return;
		}

		try {

			// Iterate over all constraint names
			for (final String name : constraints.keySet()) {
				final Iterator<ILPConstraint> cnstrIt = constraints.get(name).iterator();

				// Iterate over each "sub" constraint (if any)
				while (cnstrIt.hasNext()) {
					final ILPConstraint cnstr = cnstrIt.next();
					final IloLinearNumExpr linearNumExpr = cplex.linearNumExpr();
					for (final ILPTerm act : cnstr.lhsTerms()) {
						linearNumExpr.addTerm(act.weight(), vars.get(act.variable().getName()));
					}
					switch (cnstr.operator()) {
					case EQUAL -> {
						cplex.addEq(cnstr.rhsConstantTerm(), linearNumExpr);
					}
					// TODO: Switched operators???
					case GREATER_OR_EQUAL -> {
						cplex.addLe(cnstr.rhsConstantTerm(), linearNumExpr);
					}
					case LESS_OR_EQUAL -> {
						cplex.addGe(cnstr.rhsConstantTerm(), linearNumExpr);
					}
					default -> {
						throw new UnsupportedOperationException("Unsupported operator.");
					}
					}
				}
			}
		} catch (final IloException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Sets the objective function for CPLEX up. Variable setup must be done before.
	 */
	private void setUpObj() {
		if (objective == null) {
			return;
		}

		final ILPNestedLinearFunction nestFunc = objective.getObjectiveFunction();

		try {

			// Set goal
			IloObjective obj = null;
			switch (nestFunc.goal()) {
			case MAX -> {
				obj = cplex.addMaximize();
			}
			case MIN -> {
				obj = cplex.addMinimize();
			}
			}

			// Constants
			double constSum = 0;
			for (ILPConstant c : nestFunc.constants()) {
				constSum += c.weight();
			}

			final HashMap<String, Double> objCoeffs = new HashMap<>();

			// Terms
			// Sum up all coefficients of all variables
			for (final ILPWeightedLinearFunction lf : nestFunc.linearFunctions()) {
				lf.linearFunction().terms().forEach(t -> {
					final String name = t.variable().getName();
					if (!objCoeffs.containsKey(name)) {
						objCoeffs.put(name, t.weight() * lf.weight());
					} else {
						final double oldWeight = objCoeffs.remove(name);
						objCoeffs.put(name, oldWeight + t.weight() * lf.weight());
					}

				});

				for (final ILPConstant c : lf.linearFunction().constantTerms()) {
					constSum += (c.weight() * lf.weight());
				}
			}

			for (final String name : objCoeffs.keySet()) {
				final double coeff = objCoeffs.get(name);
				cplex.setLinearCoef(obj, coeff, vars.get(name));
			}

			// Add global constant sum
			obj.setExpr(cplex.sum(obj.getExpr(), cplex.constant(constSum)));
		} catch (final IloException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Checks the type of each given variable and creates corresponding (additional)
	 * variables in the CPLEX model.
	 * 
	 * @param variables Collection of variables to create additional CPLEX variables
	 *                  for.
	 */
	protected void createAdditionalVars(final Collection<ILPVariable<?>> variables) {
		for (final ILPVariable<?> variable : variables) {
			if (variable instanceof ILPBinaryVariable binVar) {
				createBinVar(binVar.name);
			} else if (variable instanceof ILPIntegerVariable intVar) {
				createIntVar(intVar.name);
			} else if (variable instanceof ILPRealVariable realVar) {
				createDblVar(realVar.name);
			} else {
				throw new IllegalArgumentException("Unsupported variable type: " + variable.getClass().getSimpleName());
			}
		}
	}

	/**
	 * Adds a binary variable with given name to the model.
	 * 
	 * @param name Variable name.
	 */
	private void createBinVar(final String name) {
		if (vars.containsKey(name)) {
			throw new RuntimeException();
		}

		try {
			final IloIntVar boolVar = cplex.boolVar(name);
			vars.put(name, boolVar);
		} catch (final IloException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds an integer variable with given name to the model.
	 * 
	 * @param name Variable name.
	 */
	private void createIntVar(final String name) {
		if (vars.containsKey(name)) {
			throw new RuntimeException();
		}

		try {
			final IloIntVar intVar = cplex.intVar(Integer.MIN_VALUE, Integer.MAX_VALUE);
			vars.put(name, intVar);
		} catch (final IloException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a double variable with given name to the model.
	 * 
	 * @param name Variable name.
	 */
	private void createDblVar(final String name) {
		if (vars.containsKey(name)) {
			throw new RuntimeException();
		}

		try {
			final IloNumVar numVar = cplex.numVar(-Double.MAX_VALUE, Double.MAX_VALUE);
			vars.put(name, numVar);
		} catch (final IloException e) {
			throw new RuntimeException(e);
		}
	}

}
