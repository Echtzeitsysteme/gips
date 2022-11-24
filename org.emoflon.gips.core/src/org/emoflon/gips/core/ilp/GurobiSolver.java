package org.emoflon.gips.core.ilp;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

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

import gurobi.GRB;
import gurobi.GRB.DoubleAttr;
import gurobi.GRB.DoubleParam;
import gurobi.GRB.IntParam;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;

public class GurobiSolver extends ILPSolver {

	/**
	 * Gurobi environment (for configuration etc.).
	 */
	private final GRBEnv env;

	/**
	 * Gurobi model.
	 */
	private GRBModel model;

	/**
	 * Look-up data structure to speed-up the variable look-up.
	 */
	private final HashMap<String, GRBVar> grbVars = new HashMap<>();

	public GurobiSolver(final GipsEngine engine, final ILPSolverConfig config) throws Exception {
		super(engine);

		// TODO: Gurobi log output redirect from stdout to ILPSolverOutput
		env = new GRBEnv("Gurobi_ILP.log");
		env.set(IntParam.Presolve, config.enablePresolve() ? 1 : 0);
		if (config.rndSeedEnabled()) {
			env.set(IntParam.Seed, config.randomSeed());
		}
		if (!config.enableOutput()) {
			env.set(IntParam.OutputFlag, 0);
		}
		if (config.enableTolerance()) {
			env.set(DoubleParam.OptimalityTol, config.tolerance());
		}
		if (config.timeLimitEnabled()) {
			env.set(DoubleParam.TimeLimit, config.timeLimit());
		}
		model = new GRBModel(env);
		// Double all settings to model (is this even necessary?)
		model.set(IntParam.Presolve, config.enablePresolve() ? 1 : 0);
		if (config.timeLimitEnabled()) {
			model.set(DoubleParam.TimeLimit, config.timeLimit());
		}
		if (config.rndSeedEnabled()) {
			model.set(IntParam.Seed, config.randomSeed());
		}
	}

	@Override
	public ILPSolverOutput solve() {
		ILPSolverStatus status = null;
		double objVal = -1;
		int solCount = -1;

		try {
			// Solving starts here
			model.update();
			// TODO: Set optimality tolerance here
			model.optimize();
			final int grbStatus = model.get(GRB.IntAttr.Status);
			solCount = model.get(GRB.IntAttr.SolCount);
			switch (grbStatus) {
			case GRB.UNBOUNDED -> {
				status = ILPSolverStatus.UNBOUNDED;
				objVal = model.get(GRB.DoubleAttr.ObjVal);
			}
			case GRB.INF_OR_UNBD -> {
				status = ILPSolverStatus.INF_OR_UNBD;
				objVal = 0;
			}
			case GRB.INFEASIBLE -> {
				status = ILPSolverStatus.INFEASIBLE;
				objVal = 0;
			}
			case GRB.OPTIMAL -> {
				status = ILPSolverStatus.OPTIMAL;
				objVal = model.get(GRB.DoubleAttr.ObjVal);
			}
			case GRB.TIME_LIMIT -> {
				status = ILPSolverStatus.TIME_OUT;
				objVal = model.get(GRB.DoubleAttr.ObjVal);
			}
			}
		} catch (final GRBException e) {
			throw new RuntimeException(e);
		}

		return new ILPSolverOutput(status, objVal, engine.getValidationLog(), solCount);
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
				try {
					// Get value of the ILP variable and round it (to eliminate small deltas)
					double result = Math.round(getVar(varName).get(DoubleAttr.X));
					// Save result value in specific mapping
					mapper.getMapping(k).setValue((int) result);
					GipsMapping mapping = mapper.getMapping(k);
					if(mapping.hasVariables()) {
						for(String mappingVarName : mapping.getVariableNames()) {
							double mappingVarResult = Math.round(getVar(mappingVarName).get(DoubleAttr.X));
							mapping.setVariableValue(mappingVarName, mappingVarResult);
						}
					}
				} catch (final GRBException e) {
					throw new RuntimeException(e);
				}
			}
		}

		// TODO: Dispose model and environment after solution update?
		model.dispose();
		try {
			env.dispose();
		} catch (final GRBException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void translateMapping(final GipsMapping mapping) {
		// Add a binary variable with corresponding name for the mapping
		createOrGetBinVar(mapping);
		if(mapping.hasVariables()) {
			createOrGetAdditionalVars(mapping.getVariables().values());
		}
	}

	@Override
	protected void translateConstraint(final GipsMappingConstraint<?, ? extends EObject> constraint) {
		createOrGetAdditionalVars(constraint.getAdditionalVariables());
		int counter = addIlpIntegerConstraintsToGrb(constraint.getConstraints(), constraint.getName(), 0);
		addIlpConstraintsToGrb(constraint.getAdditionalConstraints(), constraint.getName(), counter);
	}

	@Override
	protected void translateConstraint(final GipsPatternConstraint<?, ?, ?> constraint) {
		createOrGetAdditionalVars(constraint.getAdditionalVariables());
		int counter = addIlpIntegerConstraintsToGrb(constraint.getConstraints(), constraint.getName(), 0);
		addIlpConstraintsToGrb(constraint.getAdditionalConstraints(), constraint.getName(), counter);
	}

	@Override
	protected void translateConstraint(final GipsTypeConstraint<?, ? extends EObject> constraint) {
		createOrGetAdditionalVars(constraint.getAdditionalVariables());
		int counter = addIlpIntegerConstraintsToGrb(constraint.getConstraints(), constraint.getName(), 0);
		addIlpConstraintsToGrb(constraint.getAdditionalConstraints(), constraint.getName(), counter);
	}

	@Override
	protected void translateConstraint(GipsGlobalConstraint<?> constraint) {
		createOrGetAdditionalVars(constraint.getAdditionalVariables());
		int counter = addIlpIntegerConstraintsToGrb(constraint.getConstraints(), constraint.getName(), 0);
		addIlpConstraintsToGrb(constraint.getAdditionalConstraints(), constraint.getName(), counter);
	}

	protected void createOrGetAdditionalVars(final Collection<ILPVariable<?>> variables) {
		for (ILPVariable<?> variable : variables) {
			if (variable instanceof ILPBinaryVariable binVar) {
				createOrGetBinVar(binVar);
			} else if (variable instanceof ILPIntegerVariable intVar) {
				createOrGetIntegerVar(intVar);
			} else if (variable instanceof ILPRealVariable realVar) {
				createOrGetRealVar(realVar);
			} else {
				throw new IllegalArgumentException("Unsupported variable type: " + variable.getClass().getSimpleName());
			}
		}
	}

	@Override
	protected void translateObjective(final GipsGlobalObjective objective) {
		final GRBLinExpr obj = new GRBLinExpr();
		final ILPNestedLinearFunction nestFunc = objective.getObjectiveFunction();

		// Add all constants
		nestFunc.constants().forEach(c -> {
			obj.addConstant(c.weight());
		});

		// For each linear function
		nestFunc.linearFunctions().forEach(lf -> {
			final GRBLinExpr expr = new GRBLinExpr();
			// Linear function contains terms
			lf.linearFunction().terms().forEach(t -> {
				final GRBLinExpr term = new GRBLinExpr();
				if (t.variable() instanceof ILPBinaryVariable binary) {
					term.addTerm(t.weight(), createOrGetBinVar(binary));
				} else if (t.variable() instanceof ILPIntegerVariable integer) {
					term.addTerm(t.weight(), createOrGetIntegerVar(integer));
				} else {
					ILPRealVariable real = (ILPRealVariable) t.variable();
					term.addTerm(t.weight(), createOrGetRealVar(real));
				}

				try {
					expr.add(term);
				} catch (final GRBException e) {
					throw new RuntimeException(e);
				}
			});

			// Linear function contains constant terms
			lf.linearFunction().constantTerms().forEach(c -> {
				expr.addConstant(c.weight());
			});

			try {
				// Add current linear function with its weight
				obj.multAdd(lf.weight(), expr);
			} catch (final GRBException e) {
				throw new RuntimeException(e);
			}
		});

		// Set global objective with goal
		try {
			int goal = -1;
			switch (nestFunc.goal()) {
			case MAX -> {
				goal = GRB.MAXIMIZE;
			}
			case MIN -> {
				goal = GRB.MINIMIZE;
			}
			}
			model.setObjective(obj, goal);
		} catch (final GRBException e) {
			throw new RuntimeException(e);
		}
	}

	//
	// Helper methods
	//

	/**
	 * Adds a given collection of ILP constraints and a given constraint name to the
	 * Gurobi model.
	 *
	 * @param constraints Collection of integer ILP constraints to add.
	 * @param name        Name of the overall constraint to add.
	 */
	private int addIlpIntegerConstraintsToGrb(final Collection<ILPConstraint> constraints, final String name,
			int counter) {
		// Have to use an iterator to be able to increment the counter
		final Iterator<ILPConstraint> cnstrsIt = constraints.iterator();
		while (cnstrsIt.hasNext()) {
			final ILPConstraint curr = cnstrsIt.next();

			if (curr == null) {
				continue;
			}

			final GRBLinExpr grbLinExpr = new GRBLinExpr();

			// Check if constraints of form "<empty> == const" exist and throw an exception
//			if (curr.lhsTerms().isEmpty()) {
//				throw new RuntimeException("LHS (variable terms) is empty. This produces an infeasible ILP problem.");
//			}
			// TODO: Throw an exception if the collection of LHS terms is empty (and the
			// presolver functionality is implemented.

			//
			// Operator
			//

			final char op = convertOperator(curr.operator());

			//
			// Terms
			//

			// Add each term to the GRB linear expression
			curr.lhsTerms().forEach(t -> {
				if (t.variable() instanceof ILPBinaryVariable binary) {
					grbLinExpr.addTerm(t.weight(), createOrGetBinVar(binary));
				} else if (t.variable() instanceof ILPIntegerVariable integer) {
					grbLinExpr.addTerm(t.weight(), createOrGetIntegerVar(integer));
				} else {
					ILPRealVariable real = (ILPRealVariable) t.variable();
					grbLinExpr.addTerm(t.weight(), createOrGetRealVar(real));
				}
			});

			// Add current constructed constraint to the GRB model
			try {
				// TODO: Use model.addLConstr instead (up to ~50% faster)
				model.addConstr(grbLinExpr, op, curr.rhsConstantTerm(), name + "_" + counter++);
			} catch (final GRBException e) {
				throw new RuntimeException(e);
			}
		}

		return counter;
	}

	/**
	 * Adds a given collection of ILP constraints and a given constraint name to the
	 * Gurobi model.
	 *
	 * @param constraints Collection of integer ILP constraints to add.
	 * @param name        Name of the overall constraint to add.
	 */
	private int addIlpConstraintsToGrb(final Collection<ILPConstraint> constraints, final String name, int counter) {
		// Have to use an iterator to be able to increment the counter
		final Iterator<ILPConstraint> cnstrsIt = constraints.iterator();
		while (cnstrsIt.hasNext()) {
			final ILPConstraint curr = cnstrsIt.next();

			if (curr == null) {
				continue;
			}

			final GRBLinExpr grbLinExpr = new GRBLinExpr();

			// Check if constraints of form "<empty> == const" exist and throw an exception
//			if (curr.lhsTerms().isEmpty()) {
//				throw new RuntimeException("LHS (variable terms) is empty. This produces an infeasible ILP problem.");
//			}
			// TODO: Throw an exception if the collection of LHS terms is empty (and the
			// presolver functionality is implemented.

			//
			// Operator
			//

			final char op = convertOperator(curr.operator());

			//
			// Terms
			//

			// Add each term to the GRB linear expression
			curr.lhsTerms().forEach(t -> {
				if (t.variable() instanceof ILPBinaryVariable binary) {
					grbLinExpr.addTerm(t.weight(), createOrGetBinVar(binary));
				} else if (t.variable() instanceof ILPIntegerVariable integer) {
					grbLinExpr.addTerm(t.weight(), createOrGetIntegerVar(integer));
				} else {
					ILPRealVariable real = (ILPRealVariable) t.variable();
					grbLinExpr.addTerm(t.weight(), createOrGetRealVar(real));
				}

			});

			// Add current constructed constraint to the GRB model
			try {
				// TODO: Use model.addLConstr instead (up to ~50% faster)
				model.addConstr(grbLinExpr, op, curr.rhsConstantTerm(), name + "_" + counter++);
			} catch (final GRBException e) {
				throw new RuntimeException(e);
			}
		}

		return counter;
	}

	/**
	 * Converts a given operator value (from RelationalOperator) to the
	 * corresponding GRB char value.
	 *
	 * @param op Operator value to convert.
	 * @return Corresponding GRB char value.
	 */
	private char convertOperator(final RelationalOperator op) {
		switch (op) {
		case LESS:
			// https://www.gurobi.com/documentation/9.5/refman/java_model_addconstr.html
			throw new UnsupportedOperationException("Gurobi does not support LESS in constraints.");
		// break;
		// TODO: Could be expressed with !(x >= a)
		case LESS_OR_EQUAL:
			return GRB.LESS_EQUAL;
		case EQUAL:
			return GRB.EQUAL;
		case GREATER_OR_EQUAL:
			return GRB.GREATER_EQUAL;
		case GREATER:
			throw new UnsupportedOperationException("Gurobi does not support GREATER in constraints.");
		// break;
		// TODO: Could be expressed with !(x <= a)
		case NOT_EQUAL:
			throw new UnsupportedOperationException("Gurobi does not support NOT EQUAL in constraints.");
		// TODO: This introduces an OR constraint
		// https://math.stackexchange.com/questions/37075/how-can-not-equals-be-expressed-as-an-inequality-for-a-linear-programming-model/1517850
		// break;
		default:
			throw new UnsupportedOperationException("Not yet implemented.");
		}

		// We do not support strict less-than, strict greater-than, or not-equal
		// comparators. While these other comparators may seem appropriate for
		// mathematical programming, we exclude them to avoid potential confusion
		// related to numerical tolerances.
		//
		// https://www.gurobi.com/documentation/9.5/refman/constraints.html
	}

	/**
	 * Returns the corresponding GRBVar for a given name.
	 *
	 * @param name Name to search variable for.
	 * @return GBRVar for a given name.
	 */
	private GRBVar getVar(final String name) {
		if (grbVars.containsKey(name)) {
			return grbVars.get(name);
		}

		try {
			return model.getVarByName(name);
		} catch (final GRBException e) {
			// Var not found in model -> return null
			return null;
		}
	}

	/**
	 * Creates a new binary Gurobi variable for a given name if it does not exist
	 * already. If it exists, the method returns the already existing variable.
	 *
	 * @param name Name to create new binary Gurobi variable for.
	 * @return New binary Gurobi variable.
	 */
	private GRBVar createOrGetBinVar(final ILPBinaryVariable variable) {
		if (getVar(variable.getName()) != null) {
			return getVar(variable.getName());
		} else {
			try {
				final GRBVar var = model.addVar(variable.getLowerBound(), variable.getUpperBound(), 0.0, GRB.BINARY,
						variable.getName());
				grbVars.put(variable.getName(), var);
				return var;
			} catch (final GRBException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Creates a new integer Gurobi variable for a given name if it does not exist
	 * already. If it exists, the method returns the already existing variable.
	 *
	 * @param name Name to create new integer Gurobi variable for.
	 * @return New binary Gurobi variable.
	 */
	private GRBVar createOrGetIntegerVar(final ILPIntegerVariable variable) {
		if (getVar(variable.getName()) != null) {
			return getVar(variable.getName());
		} else {
			try {
				final GRBVar var = model.addVar(variable.getLowerBound(), variable.getUpperBound(), 0.0, GRB.INTEGER,
						variable.getName());
				grbVars.put(variable.getName(), var);
				return var;
			} catch (final GRBException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Creates a new real Gurobi variable for a given name if it does not exist
	 * already. If it exists, the method returns the already existing variable.
	 *
	 * @param name Name to create new real Gurobi variable for.
	 * @return New binary Gurobi variable.
	 */
	private GRBVar createOrGetRealVar(final ILPRealVariable variable) {
		if (getVar(variable.getName()) != null) {
			return getVar(variable.getName());
		} else {
			try {
				final GRBVar var = model.addVar(variable.getLowerBound(), variable.getUpperBound(), 0.0, GRB.CONTINUOUS,
						variable.getName());
				grbVars.put(variable.getName(), var);
				return var;
			} catch (final GRBException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
