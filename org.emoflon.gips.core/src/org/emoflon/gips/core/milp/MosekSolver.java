package org.emoflon.gips.core.milp;

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
import org.emoflon.gips.core.GipsMapper;
import org.emoflon.gips.core.GipsMapping;
import org.emoflon.gips.core.GipsMappingConstraint;
import org.emoflon.gips.core.GipsObjective;
import org.emoflon.gips.core.GipsTypeConstraint;
import org.emoflon.gips.core.gt.GipsPatternConstraint;
import org.emoflon.gips.core.gt.GipsRuleConstraint;
import org.emoflon.gips.core.milp.model.BinaryVariable;
import org.emoflon.gips.core.milp.model.Constant;
import org.emoflon.gips.core.milp.model.Constraint;
import org.emoflon.gips.core.milp.model.IntegerVariable;
import org.emoflon.gips.core.milp.model.NestedLinearFunction;
import org.emoflon.gips.core.milp.model.RealVariable;
import org.emoflon.gips.core.milp.model.Variable;
import org.emoflon.gips.core.milp.model.WeightedLinearFunction;
import org.emoflon.gips.intermediate.GipsIntermediate.Goal;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;

import mosek.Task;
import mosek.boundkey;
import mosek.dparam;
import mosek.objsense;
import mosek.soltype;
import mosek.variabletype;

public class MosekSolver extends Solver {

	/**
	 * Map to collect all ILP constraints (name -> collection of constraints).
	 */
	private Map<String, Collection<Constraint>> constraints = new HashMap<>();

	/**
	 * Global objective.
	 */
	private GipsObjective objective;
	
	/**
	 * Constant sum of the global objective.
	 */
	private double objectiveConstantSum = 0;

	/**
	 * LP file output path.
	 */
	private String lpPath = null;

	/**
	 * Map to collect all variables (name -> {integer (=index), type, lower bound,
	 * upper bound}).
	 */
	private final Map<String, MosekVariable> vars = new HashMap<>();

	/**
	 * The MOSEK solver model.
	 */
	private Task mosek;

	/**
	 * Initialize a new MOSEK solver object with the given GIPS engine and solver
	 * configuration.
	 * 
	 * @param engine GIPS engine.
	 * @param config Solver config for the new object.
	 */
	public MosekSolver(final GipsEngine engine, final SolverConfig config) {
		super(engine, config);
		// TODO: Check JAR env compatibility
	}

	@Override
	public void init() {
		this.lpPath = null;
		this.vars.clear();
		this.constraints.clear();
		this.objective = null;

		try {
			mosek = new Task();
			// TODO: finalize initialization
			// TODO: configuration
			mosek.putdouparam(dparam.mio_max_time, config.getTimeLimit());
		} catch (final mosek.Exception e) {
			throw new RuntimeException(e);
		}

		if (config.isEnableLpOutput()) {
			this.lpPath = config.getLpPath();
		}
	}

	@Override
	public SolverOutput solve() {
		setUpVars();
		setUpCnstrs();
		setUpObj();

		// Save LP file if configured
		if (this.lpPath != null) {
			mosek.writedata(this.lpPath);
		}

		// Save problem statistics
		final ProblemStatistics stats = new ProblemStatistics( //
				engine.getMappers().values().stream() //
						.map(m -> m.getMappings().size()) //
						.reduce(0, (sum, val) -> sum + val), //
				vars.size(), //
				constraints.values().stream() //
						.map(cons -> cons.size()) //
						.reduce(0, (sum, val) -> sum + val));

		// Start solver
		mosek.optimize();

		// Get solver output status
		mosek.solsta solsta = null;
		try {
			solsta = mosek.getsolsta(soltype.itg);
		} catch (final mosek.Exception e) {
			return new SolverOutput(SolverStatus.OPTIMAL, this.objectiveConstantSum, engine.getValidationLog(), 1, stats);
		}

		// Determine status
		SolverStatus status = null;
		int solCounter = -1;
		double objVal = -1;

		if (solsta != null) {
			switch (solsta) {
			case integer_optimal:
				// Optimal solution
				status = SolverStatus.OPTIMAL;
				solCounter = 1;
				objVal = mosek.getprimalobj(soltype.itg);
				break;
			case prim_feas:
				// Feasible solution
				status = SolverStatus.FEASIBLE;
				solCounter = 1;
				objVal = mosek.getprimalobj(soltype.itg);
				break;
			case unknown:
				mosek.prosta prosta = mosek.getprosta(soltype.itg);
				switch (prosta) {
				case prim_infeas_or_unbounded:
					// Problem status Infeasible or unbounded
					status = SolverStatus.INF_OR_UNBD;
					solCounter = 0;
					break;
				case prim_infeas:
					// Problem status Infeasible
					status = SolverStatus.INFEASIBLE;
					solCounter = 0;
					break;
				case unknown:
					// Problem status unknown
					break;
				default:
					// Other problem status
					break;
				}
				break;
			default:
				// Other solution status
				break;
			}
		}

		return new SolverOutput(status, objVal, engine.getValidationLog(), solCounter, stats);
	}

	private void setUpVars() {
		// In case of 0 variables, simply return
		if (vars.size() == 0) {
			return;
		}

		mosek.appendvars(this.vars.size());
		this.vars.forEach((name, var) -> {
			// if `ub == lb`, fix the variable
			if (var.lb.equals(var.ub)) {
				mosek.putvarbound(var.index, boundkey.fx, var.lb.doubleValue(), var.ub.doubleValue());
			} else {
				mosek.putvarbound(var.index, boundkey.ra, var.lb.doubleValue(), var.ub.doubleValue());
			}

			if (var.type == VariableType.BIN) {
				mosek.putvartype(var.index, variabletype.type_int);
				mosek.putvarbound(var.index, boundkey.ra, 0.0, 1.0);
			} else if (var.type == VariableType.INT) {
				mosek.putvartype(var.index, variabletype.type_int);
			} else if (var.type == VariableType.DBL) {
				mosek.putvartype(var.index, variabletype.type_cont);
			} else {
				throw new IllegalArgumentException("Unsupported variable type: " + var.getClass().getSimpleName());
			}
		});
	}

	private void setUpCnstrs() {
		// Determine total number of constraints
		int numRows = 0;
		for (final Collection<Constraint> col : constraints.values()) {
			numRows += col.size();
		}

		// In case of 0 constraints, simply return
		if (numRows == 0) {
			return;
		}
		mosek.appendcons(numRows);

		// Iterate over all constraint name
		int globalCnstrCounter = 0;
		for (final String name : constraints.keySet()) {
			final Iterator<Constraint> cnstrIt = constraints.get(name).iterator();

			// Iterate over each "sub" constraint (if any)
			while (cnstrIt.hasNext()) {
				final Constraint cnstr = cnstrIt.next();
				if (cnstr == null) {
					continue;
				}

				// For MOSEK, we have to cumulate all weights for identical variables
				final Map<Variable<?>, Double> cumulatedWeights = new HashMap<>();

				for (int i = 0; i < cnstr.lhsTerms().size(); i++) {
					final Variable<?> var = cnstr.lhsTerms().get(i).variable();
					if (cumulatedWeights.containsKey(var)) {
						final double newWeight = cumulatedWeights.remove(var) + cnstr.lhsTerms().get(i).weight();
						cumulatedWeights.put(var, newWeight);
					} else {
						cumulatedWeights.put(var, cnstr.lhsTerms().get(i).weight());
					}
				}

				// Put the weight for each variable into the matrix A
				for (final Variable<?> v : cumulatedWeights.keySet()) {
					mosek.putaij(globalCnstrCounter, vars.get(v.getName()).index, cumulatedWeights.get(v));
				}

				// Specify the operator and bound of the constraint
				if (cnstr.operator() == RelationalOperator.EQUAL) {
					mosek.putconbound(globalCnstrCounter, boundkey.fx, cnstr.rhsConstantTerm(),
							cnstr.rhsConstantTerm());
				} else if (cnstr.operator() == RelationalOperator.LESS_OR_EQUAL) {
					mosek.putconbound(globalCnstrCounter, boundkey.up, cnstr.rhsConstantTerm(),
							cnstr.rhsConstantTerm());
				} else if (cnstr.operator() == RelationalOperator.GREATER_OR_EQUAL) {
					mosek.putconbound(globalCnstrCounter, boundkey.lo, cnstr.rhsConstantTerm(),
							cnstr.rhsConstantTerm());
				} else {
					throw new IllegalArgumentException("Unsupported bound type: " + cnstr.operator().getName());
				}

				globalCnstrCounter++;
			}
		}
	}

	private void setUpObj() {
		// If there is no objective, simply return
		if (objective == null) {
			return;
		}

		final NestedLinearFunction nestFunc = objective.getObjectiveFunction();

		// Set goal
		if (nestFunc.goal() == Goal.MAX) {
			mosek.putobjsense(objsense.maximize);
		} else if (nestFunc.goal() == Goal.MIN) {
			mosek.putobjsense(objsense.minimize);
		}

		// Constants
		double constSum = 0;
		for (Constant c : nestFunc.constants()) {
			constSum += c.weight();
		}

		// Terms
		for (final WeightedLinearFunction lf : nestFunc.linearFunctions()) {
			lf.linearFunction().terms().forEach(t -> {
				// Get index of current variable
				final int varIndex = vars.get(t.variable().getName()).index;

				// Get previous aggregated weight
				final double prevCoef = mosek.getcj(varIndex);

				// Update aggregated weight = prev + weight(var) * weight(term)
				mosek.putcj(varIndex, prevCoef + (t.weight() * lf.weight()));
			});

			for (final Constant c : lf.linearFunction().constantTerms()) {
				constSum += (c.weight() * lf.weight());
			}
		}

		// Add global constant sum
		this.objectiveConstantSum = constSum;
		mosek.putcfix(constSum);
	}

	@Override
	public void updateValuesFromSolution() {
		// Extract solution from solver
		double[] xx;
		try {
			 xx = mosek.getxx(soltype.itg);
		} catch (final mosek.Exception e) {
			return;
		}

		// Iterate over all mappers
		for (final String key : engine.getMappers().keySet()) {
			final GipsMapper<?> mapper = engine.getMapper(key);
			// Iterate over all mappings of each mapper
			for (final String k : mapper.getMappings().keySet()) {
				// Get corresponding ILP variable name
				final GipsMapping mapping = mapper.getMapping(k);
				final String varName = mapping.getName();
				// Get value of the ILP variable
				double result = xx[vars.get(varName).index];
				// Save result value in specific mapping
				mapping.setValue((int) result);

				// Save all values of additional variables if any
				if (mapping.hasAdditionalVariables()) {
					for (Entry<String, Variable<?>> var : mapping.getAdditionalVariables().entrySet()) {
						double mappingVarResult = xx[vars.get(var.getValue().getName()).index];
						mapping.setAdditionalVariableValue(var.getKey(), mappingVarResult);
					}
				}
			}
		}
		// Solver reset will be handled by the GipsEngine afterward
	}

	@Override
	protected void translateMapping(final GipsMapping mapping) {
		createBinVarIfNotExists(mapping.getName(), mapping.getLowerBound(), mapping.getUpperBound());
		if (mapping.hasAdditionalVariables()) {
			createAdditionalVars(mapping.getAdditionalVariables().values());
		}
	}

	@Override
	protected void translateConstraint(final GipsMappingConstraint<?, ? extends EObject> constraint) {
		createAdditionalVars(constraint.getAdditionalVariables());
		final Set<Constraint> collectedCnstr = new HashSet<>();
		collectedCnstr.addAll(constraint.getConstraints());
		collectedCnstr.addAll(constraint.getAdditionalConstraints());
		constraints.put(constraint.getName(), collectedCnstr);
	}

	@Override
	protected void translateConstraint(final GipsPatternConstraint<?, ?, ?> constraint) {
		createAdditionalVars(constraint.getAdditionalVariables());
		final Set<Constraint> collectedCnstr = new HashSet<>();
		collectedCnstr.addAll(constraint.getConstraints());
		collectedCnstr.addAll(constraint.getAdditionalConstraints());
		constraints.put(constraint.getName(), collectedCnstr);
	}

	@Override
	protected void translateConstraint(final GipsRuleConstraint<?, ?, ?> constraint) {
		createAdditionalVars(constraint.getAdditionalVariables());
		final Set<Constraint> collectedCnstr = new HashSet<>();
		collectedCnstr.addAll(constraint.getConstraints());
		collectedCnstr.addAll(constraint.getAdditionalConstraints());
		constraints.put(constraint.getName(), collectedCnstr);
	}

	@Override
	protected void translateConstraint(final GipsTypeConstraint<?, ? extends EObject> constraint) {
		createAdditionalVars(constraint.getAdditionalVariables());
		final Set<Constraint> collectedCnstr = new HashSet<>();
		collectedCnstr.addAll(constraint.getConstraints());
		collectedCnstr.addAll(constraint.getAdditionalConstraints());
		constraints.put(constraint.getName(), collectedCnstr);
	}

	@Override
	protected void translateConstraint(final GipsGlobalConstraint<?> constraint) {
		createAdditionalVars(constraint.getAdditionalVariables());
		final Set<Constraint> collectedCnstr = new HashSet<>();
		collectedCnstr.addAll(constraint.getConstraints());
		collectedCnstr.addAll(constraint.getAdditionalConstraints());
		constraints.put(constraint.getName(), collectedCnstr);
	}

	@Override
	protected void translateObjective(final GipsObjective objective) {
		this.objective = objective;
	}

	@Override
	public void terminate() {
		this.constraints.clear();
		this.vars.clear();
		this.objective = null;

		// TODO: MOSEK terminate
		this.mosek = null;
	}

	@Override
	public void reset() {
		init();
	}

	/**
	 * Checks the type of each given variable and creates corresponding (additional)
	 * variables in the MOSEK model.
	 * 
	 * @param variables Collection of variables to create additional CPLEX variables
	 *                  for.
	 */
	protected void createAdditionalVars(final Collection<Variable<?>> variables) {
		for (final Variable<?> variable : variables) {
			if (variable instanceof BinaryVariable binVar) {
				createBinVarIfNotExists(binVar.name, variable.getLowerBound(), variable.getUpperBound());
			} else if (variable instanceof IntegerVariable intVar) {
				createIntVarIfNotExists(intVar.name, variable.getLowerBound(), variable.getUpperBound());
			} else if (variable instanceof RealVariable realVar) {
				createDblVarIfNotExists(realVar.name, variable.getLowerBound(), variable.getUpperBound());
			} else {
				throw new IllegalArgumentException("Unsupported variable type: " + variable.getClass().getSimpleName());
			}
		}
	}

	private void createBinVarIfNotExists(final String name, final Number lb, final Number ub) {
		if (vars.containsKey(name)) {
			return;
		}
		this.vars.put(name, new MosekVariable(vars.size(), name, lb, ub, VariableType.BIN));
	}

	private void createIntVarIfNotExists(final String name, final Number lb, final Number ub) {
		if (vars.containsKey(name)) {
			return;
		}
		this.vars.put(name, new MosekVariable(vars.size(), name, lb, ub, VariableType.INT));
	}

	private void createDblVarIfNotExists(final String name, final Number lb, final Number ub) {
		if (vars.containsKey(name)) {
			return;
		}
		this.vars.put(name, new MosekVariable(vars.size(), name, lb, ub, VariableType.DBL));
	}

	private record MosekVariable(int index, String name, Number lb, Number ub, VariableType type) {
	}

	private enum VariableType {
		BIN, INT, DBL
	}

}
