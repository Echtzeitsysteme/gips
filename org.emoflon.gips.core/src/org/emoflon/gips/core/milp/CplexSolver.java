package org.emoflon.gips.core.milp;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

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
import org.emoflon.gips.core.milp.model.Term;
import org.emoflon.gips.core.milp.model.Variable;
import org.emoflon.gips.core.milp.model.WeightedLinearFunction;
import org.emoflon.gips.core.util.SystemUtil;

import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.concert.IloObjective;
import ilog.cplex.IloCplex;
import ilog.cplex.IloCplex.Status;

public class CplexSolver extends Solver {

	/**
	 * CPLEX model.
	 */
	private IloCplex cplex;

	/**
	 * Map to collect all ILP constraints (name -> collection of constraints).
	 */
	private Map<String, Collection<Constraint>> constraints = new HashMap<>();

	/**
	 * Map to collect all ILP variables (name -> CPLEX numeric vars).
	 */
	private final Map<String, IloNumVar> vars = new HashMap<>();

	/**
	 * Global objective.
	 */
	private GipsObjective objective;

	/**
	 * LP file output path.
	 */
	private String lpPath = null;

	public CplexSolver(final GipsEngine engine, final SolverConfig config) {
		super(engine, config);
		checkEnvJarCompatibility();
	}

	/**
	 * Checks all two necessary system environment variables to match the used CPLEX
	 * version. Expected is that the configured ENVs contain the exact same version
	 * number as the Java class(es) provided by the CPLEX JAR.
	 */
	private void checkEnvJarCompatibility() {
		checkCplexVersionInEnv("LD_LIBRARY_PATH", ".." + File.separator + ".." + File.separator + "lib");
		checkCplexVersionInEnv("PATH", ".." + File.separator + ".." + File.separator + "lib");
	}

	/**
	 * Checks if the system environment variable with the name 'envName' contains a
	 * JAR the exact version number of the used CPLEX JAR file. The 'subFolderToJar'
	 * will be concatenated to the value of the ENV.
	 * 
	 * @param envName        System environment variable to check the CPLEX version
	 *                       in.
	 * @param subFolderToJar Sub folder to the expected JAR file.
	 */
	private void checkCplexVersionInEnv(String envName, final String subFolderToJar) {
		if (envName == null || envName.isBlank()) {
			throw new IllegalArgumentException("Given ENV name was null or empty.");
		}

		// Get system ENV
		final String envValue = System.getenv(envName);
		if (envValue == null || envValue.isBlank()) {
			throw new IllegalStateException("The ENV '" + envName + "' was null or empty.");
		}

		// We have to split the paths in given ENV and test every path for the
		// `cplex.jar` file.
		// This is necessary, because one ENV can contain multiple paths separated by a
		// symbol.
		// Example: /opt/test:/opt/test2:$PATH
		final String[] segments = envValue.split(File.pathSeparator);

		// Test every path -> the first match will be used (as it will be the case when
		// running CPLEX)
		String cplexJar = null;
		for (int i = 0; i < segments.length; i++) {
			String actualPath = segments[i];
			// If there is no slash at the end, we have to add it
			if (!segments[i].endsWith(File.separator)) {
				actualPath += File.separator;
			}
			actualPath += subFolderToJar;
			actualPath += File.separator;
			actualPath += "cplex.jar";

			// Test if the `cplex.jar` file is present
			final File cplexJarCandidate = new File(actualPath);
			if (cplexJarCandidate.exists() && !cplexJarCandidate.isDirectory()) {
				// If the file is found, stop searching
				cplexJar = actualPath;
				break;
			}
		}

		// If `cplex.jar` could not be found in any of the segments, throw an
		// exception
		if (cplexJar == null || cplexJar.isBlank()) {
			throw new InternalError(
					"Cplex JAR file could not be found using ENV: " + envName + "; ENV value: " + envValue);
		}

		String versionStringPathJar = null;

		try {
			final JarFile jarFile = new JarFile(cplexJar);
			versionStringPathJar = jarFile.getManifest().getMainAttributes()
					.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
			jarFile.close();
		} catch (final IOException e) {
			throw new InternalError("Unable to retrieve version from JAR: " + cplexJar);
		}

		final String versionStringGipsJar = IloCplex.class.getPackage().getImplementationVersion();

		// Actual check of the version(s)
		if (!versionStringPathJar.equals(versionStringGipsJar)) {
			throw new UnsupportedOperationException(
					"You configured the wrong CPLEX version in your '" + envName + "' ENV. Expected: '" //
							+ versionStringGipsJar //
							+ "', configured: '" //
							+ versionStringPathJar //
							+ "'.");
		}
	}

	public void init() {
		try {
			cplex = new IloCplex();
			if (config.isTimeLimitEnabled()) {
				// TODO: Check if unit (e.g., milliseconds) is correct
				cplex.setParam(IloCplex.Param.TimeLimit, config.getTimeLimit());
			}
			if (config.isRandomSeedEnabled()) {
				cplex.setParam(IloCplex.Param.RandomSeed, config.getRandomSeed());
			}
			cplex.setParam(IloCplex.Param.Preprocessing.Presolve, config.isEnablePresolve());
			// TODO: Check specific tolerances later on
			if (config.isEnableTolerance()) {
				cplex.setParam(IloCplex.Param.MIP.Tolerances.Integrality, config.getTolerance());
				cplex.setParam(IloCplex.Param.MIP.Tolerances.AbsMIPGap, config.getTolerance());
			}

			if (!config.isEnableOutput()) {
				cplex.setOut(null);
			}

			if (config.isEnableLpOutput()) {
				this.lpPath = config.getLpPath();
			}

			// Set number of threads to use
			// If configuration option is disabled, use the maximum number of threads the
			// system provides
			if (config.isEnableThreadCount()) {
				cplex.setParam(IloCplex.Param.Threads, config.getThreadCount());
			} else {
				cplex.setParam(IloCplex.Param.Threads, SystemUtil.getSystemThreads());
			}
		} catch (final IloException e) {
			throw new RuntimeException(e);
		}

		// Reset local lookup data structure for the CPLEX constraints and variables in
		// case this is not the first initialization.
		constraints.clear();
		vars.clear();
	}

	@Override
	public void terminate() {
		this.constraints.clear();
		this.vars.clear();
		this.objective = null;

		if (cplex != null) {
			try {
				cplex.setDefaults();
				cplex.clearModel();
				cplex.endModel();
			} catch (final IloException e) {
				e.printStackTrace();
			}
			cplex.end();
			cplex = null;
		}
	}

	@Override
	public SolverOutput solve() {
		setUpCnstrs();
		setUpObj();

		// Write LP file if configured
		if (this.lpPath != null) {
			try {
				cplex.exportModel(lpPath);
			} catch (final IloException e) {
				e.printStackTrace();
			}
		}

		// If necessary, overwrite time limit with:
		// new_time_limit = old_time_limit - init_time_consumed
		if (this.config.isTimeLimitIncludeInitTime() && this.engine.getInitTimeInSeconds() != 0) {
			// If the new_time_limit is not >0, the whole solver must not be started at all
			final double oldTimeLimit = this.config.getTimeLimit();
			final double newTimeLimit = oldTimeLimit - this.engine.getInitTimeInSeconds();
			if (newTimeLimit <= 0) {
				return new SolverOutput(SolverStatus.TIME_OUT, 0, null, 0, null);
			}
			this.config.setTimeLimit(newTimeLimit);
			try {
				if (config.isTimeLimitEnabled()) {
					if (this.config.isEnableOutput()) {
						System.out.println(
								"=> Debug output: Overwrite specified CPLEX time limit with: " + config.getTimeLimit());
					}
					cplex.setParam(IloCplex.Param.TimeLimit, config.getTimeLimit());
				}
			} catch (final Exception e) {
				return new SolverOutput(SolverStatus.TIME_OUT, 0, null, 0, null);
			}
		}

		try {
			// Solving
			final boolean solve = cplex.solve();

			// Get the objective result
			double objVal = 0;
			int solCounter = -1;
			if (solve) {
				objVal = cplex.getObjValue();
				solCounter = cplex.getSolnPoolNsolns();
			}

			// Determine status
			SolverStatus status = null;
			final Status cplexStatus = cplex.getStatus();
			if (cplexStatus == IloCplex.Status.Unbounded) {
				status = SolverStatus.UNBOUNDED;
			} else if (cplexStatus == IloCplex.Status.InfeasibleOrUnbounded) {
				status = SolverStatus.INF_OR_UNBD;
			} else if (cplexStatus == IloCplex.Status.Infeasible) {
				status = SolverStatus.INFEASIBLE;
			} else if (cplexStatus == IloCplex.Status.Optimal) {
				status = SolverStatus.OPTIMAL;
			} else if (cplexStatus == IloCplex.Status.Unknown) {
				status = SolverStatus.TIME_OUT;
			} else if (cplexStatus == IloCplex.Status.Feasible) {
				status = SolverStatus.FEASIBLE;
			} else {
				throw new RuntimeException("Unknown solver status.");
			}

			return new SolverOutput(status, objVal, engine.getValidationLog(), solCounter, new ProblemStatistics( //
					engine.getMappers().values().stream() //
							.map(m -> m.getMappings().size()) //
							.reduce(0, (sum, val) -> sum + val), //
					vars.size(), //
					constraints.values().stream() //
							.map(cons -> cons.size()) //
							.reduce(0, (sum, val) -> sum + val)));
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
				final GipsMapping mapping = mapper.getMapping(k);
				final String varName = mapping.getName();
				// Get value of the ILP variable and round it (to eliminate small deltas)
				double result = 0;
				try {
					result = cplex.getValue(vars.get(varName));
				} catch (final IloException ex) {
					throw new RuntimeException(ex);
				}
				// Save result value in specific mapping
				mapping.setValue((int) result);

				// Save all values of additional variables if any
				if (mapping.hasAdditionalVariables()) {
					for (Entry<String, Variable<?>> var : mapping.getAdditionalVariables().entrySet()) {
						try {
							double mappingVarResult = cplex.getValue(vars.get(var.getValue().getName()));
							mapping.setAdditionalVariableValue(var.getKey(), mappingVarResult);
						} catch (final IloException ex) {
							throw new RuntimeException(ex);
						}
					}
				}
			}
		}
		// Solver reset will be handled by the GipsEngine afterward

		engine.getEclipseIntegration()
				.storeSolutionValues(this.vars.entrySet().stream().collect(Collectors.toMap(Entry::getKey, e -> {
					try {
						return Math.round(cplex.getValue(e.getValue()));
					} catch (IloException e1) {
						return 0;
					}
				})));
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
	protected void translateConstraint(GipsGlobalConstraint<?> constraint) {
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

	/**
	 * Sets all constraints for CPLEX up. Variable setup must be done before.
	 */
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

		try {

			// Iterate over all constraint names
			for (final String name : constraints.keySet()) {
				final Iterator<Constraint> cnstrIt = constraints.get(name).iterator();

				// Iterate over each "sub" constraint (if any)
				while (cnstrIt.hasNext()) {
					final Constraint cnstr = cnstrIt.next();
					if (cnstr == null) {
						continue;
					}
					final IloLinearNumExpr linearNumExpr = cplex.linearNumExpr();
					for (final Term act : cnstr.lhsTerms()) {
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

		final NestedLinearFunction nestFunc = objective.getObjectiveFunction();

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
			for (Constant c : nestFunc.constants()) {
				constSum += c.weight();
			}

			final HashMap<String, Double> objCoeffs = new HashMap<>();

			// Terms
			// Sum up all coefficients of all variables
			for (final WeightedLinearFunction lf : nestFunc.linearFunctions()) {
				lf.linearFunction().terms().forEach(t -> {
					final String name = t.variable().getName();
					if (!objCoeffs.containsKey(name)) {
						objCoeffs.put(name, t.weight() * lf.weight());
					} else {
						final double oldWeight = objCoeffs.remove(name);
						objCoeffs.put(name, oldWeight + t.weight() * lf.weight());
					}

				});

				for (final Constant c : lf.linearFunction().constantTerms()) {
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

	/**
	 * Adds a binary variable with given name to the model if there is no variable
	 * with the same name.
	 * 
	 * @param name Variable name.
	 * @param lb   Lower bound number.
	 * @param ub   Upper bound number.
	 */
	private void createBinVarIfNotExists(final String name, final Number lb, final Number ub) {
		if (vars.containsKey(name)) {
			return;
		}

		try {
			final IloIntVar boolVar = cplex.boolVar(name);
			// There is no direct way to set up bounds for binary variables in CPLEX
			boolVar.setLB(lb.doubleValue());
			boolVar.setUB(ub.doubleValue());
			vars.put(name, boolVar);
		} catch (final IloException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds an integer variable with given name to the model if there is no variable
	 * with the same name.
	 * 
	 * @param name Variable name.
	 * @param lb   Lower bound number.
	 * @param ub   Upper bound number.
	 */
	private void createIntVarIfNotExists(final String name, final Number lb, final Number ub) {
		if (vars.containsKey(name)) {
			return;
		}

		try {
			final IloIntVar intVar = cplex.intVar(lb.intValue(), ub.intValue());
			vars.put(name, intVar);
		} catch (final IloException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a double variable with given name to the model if there is no variable
	 * with the same name.
	 * 
	 * @param name Variable name.
	 * @param lb   Lower bound number.
	 * @param ub   Upper bound number.
	 */
	private void createDblVarIfNotExists(final String name, final Number lb, final Number ub) {
		if (vars.containsKey(name)) {
			return;
		}

		try {
			final IloNumVar numVar = cplex.numVar(lb.doubleValue(), ub.doubleValue());
			vars.put(name, numVar);
		} catch (final IloException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void reset() {
		init();
	}

}
