package org.emoflon.gips.core.milp;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
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
import org.emoflon.gips.core.milp.model.Constraint;
import org.emoflon.gips.core.milp.model.IntegerVariable;
import org.emoflon.gips.core.milp.model.NestedLinearFunction;
import org.emoflon.gips.core.milp.model.RealVariable;
import org.emoflon.gips.core.milp.model.Variable;
import org.emoflon.gips.core.util.SystemUtil;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;

import com.gurobi.gurobi.GRB;
import com.gurobi.gurobi.GRB.DoubleAttr;
import com.gurobi.gurobi.GRB.DoubleParam;
import com.gurobi.gurobi.GRB.IntParam;
import com.gurobi.gurobi.GRBEnv;
import com.gurobi.gurobi.GRBException;
import com.gurobi.gurobi.GRBLinExpr;
import com.gurobi.gurobi.GRBModel;
import com.gurobi.gurobi.GRBVar;

public class GurobiSolver extends Solver {

	/**
	 * Gurobi environment (for configuration etc.).
	 */
	private GRBEnv env;

	/**
	 * Gurobi model.
	 */
	private GRBModel model;

	/**
	 * Look-up data structure to speed-up the variable look-up.
	 */
	private final HashMap<String, GRBVar> grbVars = new HashMap<>();

	/**
	 * LP file output path.
	 */
	private String lpPath = null;

	public GurobiSolver(final GipsEngine engine, final SolverConfig config) throws Exception {
		super(engine, config);
		checkEnvJarCompatibility();
	}

	/**
	 * Checks all three necessary system environment variables to match the used
	 * GUROBI version. Expected is that the configured ENVs point to the GUROBI
	 * directory that contains the file `gurobi.jar` with the exact same version
	 * number as the GUROBI JAR that GIPS uses internally.
	 */
	private void checkEnvJarCompatibility() {
		// Example value: /opt/gurobi1201/linux64/
		checkGurobiVersionInJar("GUROBI_HOME", "lib");

		// Example value: /opt/gurobi1201/linux64/lib/
		checkGurobiVersionInJar("LD_LIBRARY_PATH", "");

		// Example value: /opt/gurobi1201/linux64/bin/
		checkGurobiVersionInJar("PATH", ".." + File.separator + "lib");
	}

	/**
	 * Checks if the system environment variable with the name 'envName' contains a
	 * JAR the exact version number of the used GUROBI JAR file. The
	 * 'subFolderToJar' will be concatenated to the value of the ENV.
	 * 
	 * @param envName        System environment variable to check the GUROBI version
	 *                       in.
	 * @param subFolderToJar Sub folder to the expected JAR file.
	 */
	private void checkGurobiVersionInJar(final String envName, final String subFolderToJar) {
		if (envName == null || envName.isBlank()) {
			throw new IllegalArgumentException("Given ENV name was null or empty.");
		}

		// Get system ENV value
		final String envValue = System.getenv(envName);
		if (envValue == null || envValue.isBlank()) {
			throw new IllegalStateException("The ENV '" + envName + "' was null or empty.");
		}

		// We have to split the paths in given ENV and test every path for the
		// `gurobi.jar` file.
		// This is necessary, because one ENV can contain multiple paths separated by a
		// symbol.
		// Example: /opt/test:/opt/test2:$PATH
		final String[] segments = envValue.split(File.pathSeparator);

		// Test every path -> the first match will be used (as it will be the case when
		// running GUROBI)
		String gurobiJar = null;
		for (int i = 0; i < segments.length; i++) {
			String actualPath = segments[i];
			// If there is no slash at the end, we have to add it
			if (!segments[i].endsWith(File.separator)) {
				actualPath += File.separator;
			}
			actualPath += subFolderToJar;
			actualPath += File.separator;
			actualPath += "gurobi.jar";

			// Test if the `gurobi.jar` file is present
			final File gurobiJarCandidate = new File(actualPath);
			if (gurobiJarCandidate.exists() && !gurobiJarCandidate.isDirectory()) {
				// If the file is found, stop searching
				gurobiJar = actualPath;
				break;
			}
		}

		// If `gurobi.jar` could not be found in any of the segments, throw an
		// exception
		if (gurobiJar == null || gurobiJar.isBlank()) {
			throw new InternalError(
					"Gurobi JAR file could not be found using ENV: " + envName + "; ENV value: " + envValue);
		}

		String versionString = null;

		try {
			final JarFile jarFile = new JarFile(gurobiJar);
			versionString = jarFile.getManifest().getMainAttributes().getValue(Attributes.Name.IMPLEMENTATION_VERSION);
			jarFile.close();
		} catch (final IOException e) {
			throw new InternalError("Unable to retrieve version from JAR: " + gurobiJar);
		}

		final int majorDelimiter = versionString.indexOf(".", 0);
		final int minorDelimiter = versionString.indexOf(".", majorDelimiter + 1);
		if (majorDelimiter < 0) {
			throw new InternalError("Unable to find major delimiter in: " + versionString);
		}
		if (minorDelimiter < 0) {
			throw new InternalError("Unable to find minor delimiter in: " + versionString);
		}

		// split version string up into its parts
		final int major = Integer.valueOf(versionString.substring(0, majorDelimiter));
		final int minor = Integer.valueOf(versionString.substring(majorDelimiter + 1, minorDelimiter));
		final int technical = Integer.valueOf(versionString.substring(minorDelimiter + 1));

		// Actual check of the version(s)
		if (major != GRB.VERSION_MAJOR || minor != GRB.VERSION_MINOR || technical != GRB.VERSION_TECHNICAL) {
			throw new UnsupportedOperationException(
					"You configured the wrong GUROBI version in your '" + envName + "' ENV. Expected: '" //
							+ GRB.VERSION_MAJOR + GRB.VERSION_MINOR + GRB.VERSION_TECHNICAL //
							+ "', configured: '" //
							+ major + minor + technical //
							+ "'.");
		}
	}

	@Override
	public void init() {
		try {
			// When running multiple instances in parallel,
			// the last instance to finish might otherwise
			// overwrite the default out and err streams with
			// the Null stream of other instances
			synchronized (GurobiSolver.class) {
				final var out = System.out;
				final var err = System.err;
				System.setOut(new PrintStream(OutputStream.nullOutputStream()));
				System.setErr(new PrintStream(OutputStream.nullOutputStream()));
				// Keep Gurobi init exception or error to throw it later
				GRBException gurobiInitException = null;
				Error gurobiInitError = null;
				// TODO: Gurobi log output redirect from stdout to ILPSolverOutput
				try {
					env = new GRBEnv("Gurobi_ILP.log");
				} catch (final GRBException e) {
					gurobiInitException = e;
				} catch (final Error e) {
					gurobiInitError = e;
				}
				if (!config.isEnableOutput() && env != null) {
					env.set(IntParam.OutputFlag, 0);
					env.set(IntParam.LogToConsole, 0);
				}
				System.setOut(out);
				System.setErr(err);
				// If an exception/error occurred during Gurobi initialization, throw it now
				if (gurobiInitException != null) {
					throw gurobiInitException;
				}
				if (gurobiInitError != null) {
					throw gurobiInitError;
				}
			}

			env.set(IntParam.Presolve, config.isEnablePresolve() ? 1 : 0);
			if (config.isRandomSeedEnabled()) {
				env.set(IntParam.Seed, config.getRandomSeed());
			}
			// TODO: Check specific tolerances later on
			if (config.isEnableTolerance()) {
				env.set(DoubleParam.OptimalityTol, config.getTolerance());
				env.set(DoubleParam.IntFeasTol, config.getTolerance());
			}
			if (config.isTimeLimitEnabled()) {
				env.set(DoubleParam.TimeLimit, config.getTimeLimit());
			}
			model = new GRBModel(env);
			// Double all settings to model (is this even necessary?)
			model.set(IntParam.Presolve, config.isEnablePresolve() ? 1 : 0);
			if (config.isTimeLimitEnabled()) {
				model.set(DoubleParam.TimeLimit, config.getTimeLimit());
			}
			if (config.isRandomSeedEnabled()) {
				model.set(IntParam.Seed, config.getRandomSeed());
			}
			if (config.isEnableLpOutput()) {
				this.lpPath = config.getLpPath();
			}
			// Set number of threads to use
			// If configuration option is disabled, use the maximum number of threads the
			// system provides
			if (config.isEnableThreadCount()) {
				model.set(IntParam.Threads, config.getThreadCount());
			} else {
				model.set(IntParam.Threads, SystemUtil.getSystemThreads());
			}
			// Reset local lookup data structure for the Gurobi variables in case this is
			// not the first initialization.
			grbVars.clear();
		} catch (final GRBException ex) {
			throw new InternalError(ex);
		}
	}

	@Override
	public void terminate() {
		if (model != null) {
			model.terminate();
			model.dispose();
			model = null;
		}

		try {
			if (env != null) {
				env.dispose();
				env = null;
			}
		} catch (final GRBException e) {
			e.printStackTrace();
		}
	}

	@Override
	public SolverOutput solve() {
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
						System.out.println("=> Debug output: Overwrite specified Gurobi time limit with: "
								+ config.getTimeLimit());
					}
					model.set(DoubleParam.TimeLimit, config.getTimeLimit());
				}
			} catch (final Exception e) {
				return new SolverOutput(SolverStatus.TIME_OUT, 0, null, 0, null);
			}
		}

		SolverStatus status = null;
		double objVal = -1;
		int solCount = -1;

		if (this.lpPath != null) {
			try {
				model.write(lpPath);
			} catch (final GRBException e) {
				e.printStackTrace();
			}
		}

		try {
			// Solving starts here
			model.update();
			// TODO: Set optimality tolerance here
			model.optimize();
			final int grbStatus = model.get(GRB.IntAttr.Status);
			solCount = model.get(GRB.IntAttr.SolCount);
			switch (grbStatus) {
			case GRB.UNBOUNDED -> {
				status = SolverStatus.UNBOUNDED;
				objVal = model.get(GRB.DoubleAttr.ObjVal);
			}
			case GRB.INF_OR_UNBD -> {
				status = SolverStatus.INF_OR_UNBD;
				objVal = 0;
			}
			case GRB.INFEASIBLE -> {
				status = SolverStatus.INFEASIBLE;
				objVal = 0;
			}
			case GRB.OPTIMAL -> {
				status = SolverStatus.OPTIMAL;
				objVal = model.get(GRB.DoubleAttr.ObjVal);
			}
			case GRB.TIME_LIMIT -> {
				status = SolverStatus.TIME_OUT;
				objVal = model.get(GRB.DoubleAttr.ObjVal);
			}
			}
		} catch (final GRBException e) {
			throw new RuntimeException(e);
		}

		return new SolverOutput(status, objVal, engine.getValidationLog(), solCount, new ProblemStatistics( //
				engine.getMappers().values().stream() //
						.map(m -> m.getMappings().size()) //
						.reduce(0, (sum, val) -> sum + val), //
				model.getVars().length, //
				model.getConstrs().length)); //
	}

	@Override
	public void updateValuesFromSolution() {
		// Iterate over all mappers
		for (final String key : engine.getMappers().keySet()) {
			final GipsMapper<?> mapper = engine.getMapper(key);
			// Iterate over all mappings of each mapper
			for (final String k : mapper.getMappings().keySet()) {
				// Get corresponding ILP variable name
				GipsMapping mapping = mapper.getMapping(k);
				final String varName = mapping.getName();
				try {
					// Get value of the ILP variable and round it (to eliminate small deltas)
					double result = Math.round(getVar(varName).get(DoubleAttr.X));
					// Save result value in specific mapping
					mapping.setValue((int) result);
					if (mapping.hasAdditionalVariables()) {
						for (Entry<String, Variable<?>> var : mapping.getAdditionalVariables().entrySet()) {
							double mappingVarResult = getVar(var.getValue().getName()).get(DoubleAttr.X);
							mapping.setAdditionalVariableValue(var.getKey(), mappingVarResult);
						}
					}
				} catch (final GRBException e) {
					throw new RuntimeException(e);
				}
			}
		}
		// Solver reset will be handled by the GipsEngine afterward

		if (engine.getEclipseIntegration().getConfig().isSolutionValuesSynchronizationEnabled()) {
			engine.getEclipseIntegration()
					.storeSolutionValues(this.grbVars.entrySet().stream().collect(Collectors.toMap(Entry::getKey, e -> {
						try {
							return Math.round(getVar(e.getKey()).get(DoubleAttr.X));
						} catch (GRBException e1) {
							return null;
						}
					})));
		}
	}

	@Override
	protected void translateMapping(final GipsMapping mapping) {
		// Add a binary variable with corresponding name for the mapping
		createOrGetBinVar(mapping);
		if (mapping.hasAdditionalVariables()) {
			createOrGetAdditionalVars(mapping.getAdditionalVariables().values());
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
	protected void translateConstraint(final GipsRuleConstraint<?, ?, ?> constraint) {
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

	protected void createOrGetAdditionalVars(final Collection<Variable<?>> variables) {
		for (Variable<?> variable : variables) {
			if (variable instanceof BinaryVariable binVar) {
				createOrGetBinVar(binVar);
			} else if (variable instanceof IntegerVariable intVar) {
				createOrGetIntegerVar(intVar);
			} else if (variable instanceof RealVariable realVar) {
				createOrGetRealVar(realVar);
			} else {
				throw new IllegalArgumentException("Unsupported variable type: " + variable.getClass().getSimpleName());
			}
		}
	}

	@Override
	protected void translateObjective(final GipsObjective objective) {
		final GRBLinExpr obj = new GRBLinExpr();
		final NestedLinearFunction nestFunc = objective.getObjectiveFunction();

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
				if (t.variable() instanceof BinaryVariable binary) {
					term.addTerm(t.weight(), createOrGetBinVar(binary));
				} else if (t.variable() instanceof IntegerVariable integer) {
					term.addTerm(t.weight(), createOrGetIntegerVar(integer));
				} else {
					RealVariable real = (RealVariable) t.variable();
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
	private int addIlpIntegerConstraintsToGrb(final Collection<Constraint> constraints, final String name,
			int counter) {
		// Have to use an iterator to be able to increment the counter
		final Iterator<Constraint> cnstrsIt = constraints.iterator();
		while (cnstrsIt.hasNext()) {
			final Constraint curr = cnstrsIt.next();

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
				if (t.variable() instanceof BinaryVariable binary) {
					grbLinExpr.addTerm(t.weight(), createOrGetBinVar(binary));
				} else if (t.variable() instanceof IntegerVariable integer) {
					grbLinExpr.addTerm(t.weight(), createOrGetIntegerVar(integer));
				} else {
					RealVariable real = (RealVariable) t.variable();
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
	private int addIlpConstraintsToGrb(final Collection<Constraint> constraints, final String name, int counter) {
		// Have to use an iterator to be able to increment the counter
		final Iterator<Constraint> cnstrsIt = constraints.iterator();
		while (cnstrsIt.hasNext()) {
			final Constraint curr = cnstrsIt.next();

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
				if (t.variable() instanceof BinaryVariable binary) {
					grbLinExpr.addTerm(t.weight(), createOrGetBinVar(binary));
				} else if (t.variable() instanceof IntegerVariable integer) {
					grbLinExpr.addTerm(t.weight(), createOrGetIntegerVar(integer));
				} else {
					RealVariable real = (RealVariable) t.variable();
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
	private GRBVar createOrGetBinVar(final BinaryVariable variable) {
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
	private GRBVar createOrGetIntegerVar(final IntegerVariable variable) {
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
	private GRBVar createOrGetRealVar(final RealVariable variable) {
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

	@Override
	public void reset() {
		try {
			init();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

}
