package org.emoflon.roam.core.ilp;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.roam.core.RoamEngine;
import org.emoflon.roam.core.RoamGlobalObjective;
import org.emoflon.roam.core.RoamMapper;
import org.emoflon.roam.core.RoamMapping;
import org.emoflon.roam.core.RoamMappingConstraint;
import org.emoflon.roam.core.RoamTypeConstraint;
import org.emoflon.roam.core.gt.RoamPatternConstraint;
import org.emoflon.roam.intermediate.RoamIntermediate.RelationalOperator;

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
	private final HashMap<String, GRBVar> grbVars = new HashMap<String, GRBVar>();

	public GurobiSolver(final RoamEngine engine, final ILPSolverConfig config) throws Exception {
		super(engine);
		
		// TODO: Gurobi log output redirect from stdout to ILPSolverOutput
		env = new GRBEnv("Gurobi_ILP.log");
		env.set(DoubleParam.TimeLimit, config.timeLimit());
		env.set(IntParam.Seed, config.randomSeed());
		env.set(IntParam.Presolve, config.enablePresolve() ? 1 : 0);
		if (!config.enableOutput()) {
			env.set(IntParam.OutputFlag, 0);
		}
		model = new GRBModel(env);
		model.set(DoubleParam.TimeLimit, config.timeLimit());
		model.set(IntParam.Seed, config.randomSeed());
	}

	@Override
	public ILPSolverOutput solve() {
		ILPSolverStatus status = null;
		double objVal = -1;
		
		try {
			// Solving starts here
			model.update();
			// TODO: Set optimality tolerance here
			model.optimize();
			objVal = model.get(GRB.DoubleAttr.ObjVal);
			final int grbStatus = model.get(GRB.IntAttr.Status);
			switch(grbStatus) {
			case GRB.UNBOUNDED -> {
				status = ILPSolverStatus.UNBOUNDED;
			}
			case GRB.INF_OR_UNBD -> {
				status = ILPSolverStatus.INF_OR_UNBD;
			}
			case GRB.INFEASIBLE -> {
				status = ILPSolverStatus.INFEASIBLE;
			}
			case GRB.OPTIMAL -> {
				status = ILPSolverStatus.OPTIMAL;
			}
			case GRB.TIME_LIMIT -> {
				status = ILPSolverStatus.TIME_OUT;
			}
			}
		} catch (final GRBException e) {
			// TODO
		}
		
		return new ILPSolverOutput(status, objVal);
	}

	@Override
	public void updateValuesFromSolution() {
		// Iterate over all mappers
		for (final String key : engine.getMappers().keySet()) {
			final RoamMapper<?> mapper = engine.getMapper(key);
			// Iterate over all mappings of each mapper
			for (final String k : mapper.getMappings().keySet()) {
				// Get corresponding ILP variable name
				final String varName = mapper.getMapping(k).ilpVariable;
				try {
					// Get value of the ILP variable and round it (to eliminate small deltas)
					double result = Math.round(getVar(varName).get(DoubleAttr.X));
					// Save result value in specific mapping
					mapper.getMapping(k).setValue((int) result);
				} catch (final GRBException e) {
					e.printStackTrace();
				}
			}
		}

		// TODO: Dispose model and environment after solution update?
		model.dispose();
		try {
			env.dispose();
		} catch (final GRBException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void translateMapping(final RoamMapping mapping) {
		// Add a binary variable with corresponding name for the mapping
		createOrGetBinVar(mapping.ilpVariable);
	}

	@Override
	protected void translateConstraint(final RoamMappingConstraint<? extends EObject> constraint) {
		addIlpConstraintsToGrb(constraint.getConstraints(), constraint.getName());
	}

	@Override
	protected void translateConstraint(final RoamPatternConstraint<?, ?> constraint) {
		addIlpConstraintsToGrb(constraint.getConstraints(), constraint.getName());
	}

	@Override
	protected void translateConstraint(final RoamTypeConstraint<? extends EObject> constraint) {
		addIlpConstraintsToGrb(constraint.getConstraints(), constraint.getName());
	}

	@Override
	protected void translateObjective(final RoamGlobalObjective objective) {
		final GRBLinExpr obj = new GRBLinExpr();
		final ILPNestedLinearFunction<?> nestFunc = objective.getObjectiveFunction();

		
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
				term.addTerm(t.weight(), createOrGetBinVar(t.variable().getName()));
				
				try {
					expr.add(term);
				} catch (final GRBException e) {
					e.printStackTrace();
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
				e.printStackTrace();
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
			e.printStackTrace();
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
	private void addIlpConstraintsToGrb(final Collection<ILPConstraint<Integer>> constraints, final String name) {
		// For each constraint, create a Gurobi constraint
		int counter = 0;

		// Have to use an iterator to be able to increment the counter
		final Iterator<ILPConstraint<Integer>> cnstrsIt = constraints.iterator();
		while (cnstrsIt.hasNext()) {
			final ILPConstraint<Integer> curr = cnstrsIt.next();
			final GRBLinExpr grbLinExpr = new GRBLinExpr();

			//
			// Operator
			//

			final char op = convertOperator(curr.operator().getValue());

			//
			// Terms
			//

			// Add each term to the GRB linear expression
			curr.terms().forEach(t -> {
				grbLinExpr.addTerm(t.weight(), createOrGetBinVar(t.variable().getName()));
			});

			// Add current constructed constraint to the GRB model
			try {
				model.addConstr(grbLinExpr, op, curr.constantTerm(), name + "_" + counter++);
			} catch (final GRBException e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	/**
	 * Converts a given operator value (from RelationalOperator) to the
	 * corresponding GRB char value.
	 * 
	 * @param op Operator value to convert.
	 * @return Corresponding GRB char value.
	 */
	private char convertOperator(final int op) {
		switch (op) {
		case RelationalOperator.LESS_VALUE:
			// https://www.gurobi.com/documentation/9.5/refman/java_model_addconstr.html
			throw new UnsupportedOperationException("Gurobi does not support LESS in constraints.");
		// break;
		// TODO: Could be expressed with !(x >= a)
		case RelationalOperator.LESS_OR_EQUAL_VALUE:
			return GRB.LESS_EQUAL;
		case RelationalOperator.EQUAL_VALUE:
			return GRB.EQUAL;
		case RelationalOperator.GREATER_OR_EQUAL_VALUE:
			return GRB.GREATER_EQUAL;
		case RelationalOperator.GREATER_VALUE:
			throw new UnsupportedOperationException("Gurobi does not support GREATER in constraints.");
		// break;
		// TODO: Could be expressed with !(x <= a)
		case RelationalOperator.NOT_EQUAL_VALUE:
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
	private GRBVar createOrGetBinVar(final String name) {
		if (getVar(name) != null) {
			return getVar(name);
		} else {
			try {
				final GRBVar var = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, name);
				grbVars.put(name, var);
				return var;
			} catch (final GRBException e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

}
