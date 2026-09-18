package org.emoflon.gips.core;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.emoflon.gips.core.GipsConstraint.RemovedConstraintsStats;
import org.emoflon.gips.core.api.TimeoutException;
import org.emoflon.gips.core.milp.ConstraintSorter;
import org.emoflon.gips.core.milp.ExecutionMetrics;
import org.emoflon.gips.core.milp.Solver;
import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.emoflon.gips.core.milp.model.Variable;
import org.emoflon.gips.core.trace.EclipseIntegration;
import org.emoflon.gips.core.trace.EclipseIntegrationConfig;
import org.emoflon.gips.core.trace.GipsTracer;
import org.emoflon.gips.core.util.Observer;
import org.emoflon.gips.core.util.StreamUtils;
import org.emoflon.gips.core.validation.GipsConstraintValidationLog;

public abstract class GipsEngine {

	protected TypeIndexer indexer;
	protected GipsConstraintValidationLog validationLog;
	final protected Map<String, GipsMapper<?>> mappers = Collections.synchronizedMap(new HashMap<>());
	final protected Map<Object, Map<String, Variable<?>>> nonMappingVariables = Collections
			.synchronizedMap(new HashMap<>());
	final protected Map<String, Object> globalConstants = Collections.synchronizedMap(new HashMap<>());
	final protected Map<String, GipsConstraint<?, ?, ?>> constraints = Collections.synchronizedMap(new HashMap<>());
	final protected Map<String, GipsLinearFunction<?, ?, ?>> functions = Collections.synchronizedMap(new HashMap<>());
	final protected Map<String, GipsTypeExtender<?, ?>> typeExtensions = Collections.synchronizedMap(new HashMap<>());
	protected GipsObjective<?> objective;
	protected Solver solver;

	protected ConstraintSorter constraintSorter;

	protected EclipseIntegration eclipseIntegration;
	protected GipsTracer tracer;

	/**
	 * Used to cancel internal tasks, like
	 * {@link #buildProblemInternal(boolean, boolean)}
	 */

	/**
	 * A thread-safe flag indicating whether running process should be prematurely
	 * cancelled or aborted.
	 * <p>
	 * Currently, the flag is used in {@link #buildProblem}. It is set to
	 * {@code false} at the beginning of each build and can be set to {@code true},
	 * if a condition, such as a user-defined time limit, is met.
	 *
	 * @see #checkForTaskTimeout()
	 */
	private final AtomicBoolean cancelCurrentTask = new AtomicBoolean(false);

	private final Observer observer = new Observer();
	private RemovedConstraintsStats removedConstraintsStats;

	/**
	 * GIPS configuration parameters that are not specific to the MILP solver nor
	 * the tracer, etc.
	 */
	protected GipsConfig config;

	/**
	 * Time tick of the initialization point in time, i.e., the point in time when
	 * the `api.init(...)` was called.
	 */
	protected long tickInit = -1;

	/**
	 * Time tick of the end of the initialization phase in time, i.e., the time
	 * point right before the (M)ILP solver starts solving the problem.
	 */
	protected long tockInit = -1;

	public abstract void update();

	public abstract void saveResult() throws IOException;

	public abstract void saveResult(final String path) throws IOException;

	protected abstract void updateConstants();

	/**
	 * Checks if the current task must be stopped. If so, the method throws an
	 * unchecked exception.
	 * <p>
	 * This method must be invoked periodically to ensure that all parallel tasks
	 * respond promptly to a timeout.
	 * 
	 * @throws TimeoutException If the task must be stopped due to a timeout. This
	 *                          is an unchecked exception and should not be caught
	 *                          locally by worker threads, allowing them to
	 *                          terminate <i>immediately and cleanly</i>.
	 */
	public void checkForTaskTimeout() throws TimeoutException {
		if (cancelCurrentTask.get())
			throw new TimeoutException();
	}

	/**
	 * Builds the problem with time measurement included. This method does not
	 * trigger an update of the pattern matcher and runs everything sequentially.
	 * 
	 * @throws TimeoutException if the build time exceeds the time limit configured
	 *                          in {@link #getConfig()}
	 * 
	 * @see GipsConfig#setBuildTimeLimit(java.time.Duration)
	 */
	public void buildProblem() {
		buildProblem(false, false);
	}

	/**
	 * Builds the problem with time measurement included. This method does trigger
	 * an update of the pattern matcher depending on the given input parameter and
	 * runs everything sequentially.
	 * 
	 * @param doUpdate If true, the pattern matcher will be updated before building
	 *                 the problem.
	 * @throws TimeoutException if the build time exceeds the time limit configured
	 *                          in {@link #getConfig()}
	 * 
	 * @see GipsConfig#setBuildTimeLimit(java.time.Duration)
	 */
	public void buildProblem(final boolean doUpdate) {
		buildProblem(doUpdate, false);
	}

	/**
	 * Builds the (M)ILP problem.
	 * <p>
	 * During execution, execution times for various stages of the process are
	 * recorded. These statistics can be retrieved after the method completes by
	 * calling {@link #getLatestMetrics()}.
	 * <p>
	 * If a time limit is configured via {@link #getConfig()}, the execution time is
	 * monitored. Exceeding this limit will cause the build process to abort on a
	 * best-effort basis <b>as soon as possible</b> and throw a timeout exception.
	 * However, certain steps are atomic and cannot be interrupted mid-execution,
	 * for example the pattern matching. Once such a step is started, it will run to
	 * completion before the timeout can be detected and enforced.
	 * 
	 * @param doUpdate If {@code true}, the pattern matcher will be updated before
	 *                 building the problem.
	 * @param parallel If {@code true}, the problem will be built in parallel.
	 * 
	 * @throws TimeoutException If the build process takes longer than the time
	 *                          limit configured in {@link #getConfig()}.
	 * 
	 * @see GipsConfig#setBuildTimeLimit(java.time.Duration)
	 * @see #getLatestMetrics()
	 * @see #getConfig()
	 */
	public void buildProblem(final boolean doUpdate, final boolean parallel) {
		cancelCurrentTask.set(false); // reset cancel token

		if (config.getBuildTimeLimit().isPositive()) {
			ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
			// cancel the current task (build), if it exceeds the time limit.
			scheduler.schedule(() -> cancelCurrentTask.set(true), //
					config.getBuildTimeLimit().toMillis(), //
					TimeUnit.MILLISECONDS //
			);

			try {
				buildProblemInternal(doUpdate, parallel);
			} finally {
				scheduler.shutdownNow();
			}
		} else {
			// no timeout, simple
			buildProblemInternal(doUpdate, parallel);

		}
	}

	protected void buildProblemInternal(final boolean doUpdate, final boolean parallel) {
		observer.resetStage(Observer.STAGE_BUILD);
		observer.singleMeasurement(Observer.STAGE_BUILD, "BUILD", () -> {
			checkForTaskTimeout();
			if (doUpdate)
				observer.singleMeasurement(Observer.STAGE_BUILD, "PM", () -> update());

			checkForTaskTimeout();
			observer.singleMeasurement(Observer.STAGE_BUILD, "BUILD_GIPS", () -> {
				// Reset validation log
				validationLog = new GipsConstraintValidationLog();

				// Constraints are re-build a few lines below
				StreamUtils.toStream(constraints.values(), parallel).forEach(constraint -> constraint.clear());
				StreamUtils.toStream(typeExtensions.values(), parallel).forEach(typeExtension -> typeExtension.clear());

				// Reset trace
				getTracer().resetTrace();

				// Objectives will be build by the global objective call below
//				objectives.values().parallelStream().forEach(objective -> objective.clear());
				// TODO: It seems to me that this is not necessary for objectives. All tests
				// (and also the dedicated tests for checking this!) are happy with it.

				nonMappingVariables.clear();
				checkForTaskTimeout();

				StreamUtils.toStream(mappers.values(), parallel) //
						.flatMap(mapper -> StreamUtils.toStream(mapper.getMappings().values(), parallel)) //
						.filter(m -> m.hasAdditionalVariables()) //
						.forEach(m -> {
							Map<String, Variable<?>> variables = nonMappingVariables.get(m);
							if (variables == null) {
								variables = Collections.synchronizedMap(new HashMap<>());
								nonMappingVariables.put(m, variables);
							}
							variables.putAll(m.getAdditionalVariables());
							checkForTaskTimeout();
						});

				StreamUtils.toStream(constraints.values(), parallel).forEach(constraint -> {
					checkForTaskTimeout();
					constraint.calcAdditionalVariables();
				});
				StreamUtils.toStream(typeExtensions.values(), parallel).forEach(typeExtension -> {
					checkForTaskTimeout();
					typeExtension.calculateExtensions();
				});

				updateConstants();

				StreamUtils.toStream(constraints.values(), parallel).forEach(constraint -> {
					checkForTaskTimeout();
					constraint.buildConstraints(parallel);
				});

				// Check if GIPS is configure to remove duplicate constraints
				if (this.config.removeUselessConstraints()) {
					checkForTaskTimeout();
					this.removedConstraintsStats = removeUselessConstraints(config.printUselessConstraintsStats());
				}

				if (objective != null)
					objective.buildObjectiveFunction(parallel);

				// Sanity check for all variable names: there must not be two different
				// variables with the same name.
				checkForTaskTimeout();
				checkVariableNameSanity();
			});

			checkForTaskTimeout();
			observer.singleMeasurement(Observer.STAGE_BUILD, "BUILD_SOLVER", () -> {
				solver.init();
				solver.buildMILPProblem();
			});

			checkForTaskTimeout();
			observer.singleMeasurement(Observer.STAGE_BUILD, "BUILD_TRACE", () -> {
				buildTraceGraphAndSendToIDE();
			});
		});
	}

	/**
	 * Builds the problem. This method does not trigger an update of the pattern
	 * matcher and runs everything sequentially.
	 * 
	 * @deprecated use {@link #buildProblem()}
	 */
	@Deprecated
	public void buildProblemTimed() {
		buildProblem(false, false);
	}

	/**
	 * Builds the problem. This method does trigger an update of the pattern matcher
	 * depending on the given input parameter and runs everything sequentially.
	 * 
	 * @param doUpdate If true, the pattern matcher will be updated before building
	 *                 the problem.
	 * @deprecated use {@link #buildProblem(boolean)}
	 */
	@Deprecated
	public void buildProblemTimed(final boolean doUpdate) {
		buildProblem(doUpdate, false);
	}

	/**
	 * Builds the problem. `doUpdate` defines if the pattern matcher should be
	 * updated and `parallel` decides if the method runs everything in parallel or
	 * sequentially.
	 * 
	 * @param doUpdate If true, the pattern matcher will be updated before building
	 *                 the problem.
	 * @param parallel If true, the problem will be built in parallel.
	 * @deprecated use {@link #buildProblem(boolean, boolean)}
	 */
	@Deprecated
	public void buildProblemTimed(final boolean doUpdate, final boolean parallel) {
		buildProblem(doUpdate, parallel);
	}

	/**
	 * This method is a sanity check for all variable names. There must not be the
	 * case of two different variables with the same name. If that would be the
	 * case, this method throws an `InternalError`.
	 */
	private void checkVariableNameSanity() {
		// Map for keeping track of "$var_name" -> "$var_object"
		final Map<String, Variable<?>> variables = new HashMap<String, Variable<?>>();

		this.getMappers().values().stream() //
				.flatMap(mapper -> mapper.getMappings().values().stream()) //
				.forEach(mapping -> { //
					// implicit binary variable
					if (mapping.hasBinaryVariable()) {
						checkVariableNameCollision(variables, mapping);
					}

					// additional variables
					if (mapping.hasAdditionalVariables()) {
						mapping.getAdditionalVariables().forEach((k, v) -> {
							checkVariableNameCollision(variables, v);
						});
					}
				});
	}

	/**
	 * Checks the given map of "variable name" -> "variable object" for naming
	 * collisions with the given variable object `var`. There must not be the case
	 * of two different variable objects with the same name.
	 * 
	 * @param variables Map with key type String and value type `Variable`. This map
	 *                  will be extended if the given variable was not already dealt
	 *                  with.
	 * @param var       Variable object to check collision for.
	 */
	private void checkVariableNameCollision(final Map<String, Variable<?>> variables, final Variable<?> var) {
		Objects.requireNonNull(variables);
		Objects.requireNonNull(var);

		if (variables.containsKey(var.getName())) {
			if (!variables.get(var.getName()).equals(var)) {
				throw new InternalError("There were multiple MILP variables with the name <" + var.getName() + ">.");
			}
		} else {
			variables.put(var.getName(), var);
		}
	}

	protected void buildTraceGraphAndSendToIDE() {
		if (getTracer().isTracingEnabled()) {
			getTracer().buildIntermediate2LpTraceGraph(this);
			eclipseIntegration.sendLpTraceToIDE(getTracer());
		}
	}

	/**
	 * 
	 * @return
	 * @deprecated use {@link #solveProblem()}
	 */
	public SolverOutput solveProblemTimed() {
		return solveProblem();
	}

	public SolverOutput solveProblem() {
		observer.resetStage(Observer.STAGE_SOLVE);
		return observer.singleMeasurement(Observer.STAGE_SOLVE, "SOLVE_PROBLEM", () -> {
			SolverOutput output;
			if (validationLog.isNotValid()) {
				output = new SolverOutput(SolverStatus.INFEASIBLE, Double.NaN, validationLog, 0, null);
			} else {
				this.tockInit();
				output = solver.solve();

				if (output.status() != SolverStatus.INFEASIBLE && output.solutionCount() > 0)
					solver.updateValuesFromSolution();

				if (output.status() == SolverStatus.INFEASIBLE && solver.getSolverConfig().isEnableIIS())
					solver.computeIrreducibleInconsistentSubsystem();
			}

			// Set statistics values of the removed constraints
			if (this.removedConstraintsStats != null && output.stats() != null) {
				output.stats().setRemovedDuplicateConstraints(this.removedConstraintsStats.duplicates());
				output.stats().setRemovedTrivialConstraints(this.removedConstraintsStats.trivial());
			}

			solver.reset();
			GlobalMappingIndexer.getInstance().terminate();

			observer.singleMeasurement(Observer.STAGE_SOLVE, "TRACE_UPDATE_VALUES",
					() -> eclipseIntegration.sendSolutionValuesToIDE());
			return output;
		});
	}

	/**
	 * Returns the execution metrics captured during the most recent execution of
	 * {@link #buildProblem(boolean, boolean)} or {@link #solveProblem()}.
	 * <p>
	 * The metrics for {@link #buildProblem(boolean, boolean)} and
	 * {@link #solveProblem()} are stored independently. However, their respective
	 * data is overwritten whenever the corresponding method is executed again. To
	 * preserve these metrics, they should be retrieved before the respective
	 * process is called again.
	 * 
	 * @return A snapshot of the {@link ExecutionMetrics} containing the data from
	 *         the latest execution.
	 * 
	 * @see #buildProblem(boolean, boolean)
	 * @see #solveProblem()
	 */
	public ExecutionMetrics getLatestMetrics() {
		return new ExecutionMetrics(observer.clone());
	}

	public GipsMapper<?> getMapper(final String mappingName) {
		return mappers.get(mappingName);
	}

	public Map<String, GipsMapper<?>> getMappers() {
		return mappers;
	}

	public Map<String, GipsConstraint<?, ?, ?>> getConstraints() {
		return constraints;
	}

	public Map<String, GipsLinearFunction<?, ?, ?>> getLinearFunctions() {
		return functions;
	}

	public Map<String, GipsTypeExtender<?, ?>> getTypeExtensions() {
		return typeExtensions;
	}

	public TypeIndexer getIndexer() {
		return indexer;
	}

	public GipsConstraintValidationLog getValidationLog() {
		return validationLog;
	}

	public GipsObjective<?> getObjective() {
		return objective;
	}

	public void terminate() {
		solver.terminate();
		indexer.terminate();
		mappers.forEach((name, mapper) -> mapper.terminate());
		GlobalMappingIndexer.getInstance().terminate();
	}

	protected abstract void initTypeIndexer();

	public synchronized Variable<?> getNonMappingVariable(final Object context, final String variableTypeName) {
		Map<String, Variable<?>> variables = nonMappingVariables.get(context);
		if (variables == null)
			throw new RuntimeException("No variables found for context <" + context + ">.");

		Variable<?> variable = variables.get(variableTypeName);
		if (variable == null)
			throw new RuntimeException(
					"Variable <" + variableTypeName + "> is not present in the non-mapping variable index.");

		return variable;
	}

	public synchronized void addNonMappingVariable(final Object context,
			final org.emoflon.gips.intermediate.GipsIntermediate.Variable variableType, Variable<?> variable) {
		Map<String, Variable<?>> variables = nonMappingVariables.get(context);
		if (variables == null) {
			variables = Collections.synchronizedMap(new HashMap<>());
			nonMappingVariables.put(context, variables);
		}
		variables.put(variableType.getName(), variable);

	}

	public synchronized void addConstantValue(final String constant, final Object value) {
		globalConstants.put(constant, value);
	}

	public synchronized Object getConstantValue(final String constant) {
		if (!globalConstants.containsKey(constant))
			throw new RuntimeException("Constant <" + constant + "> is not present in the constants index.");

		return globalConstants.get(constant);
	}

	public synchronized void removeNonMappingVariable(final Variable<?> milpVar) {
		for (Map<String, Variable<?>> variables : nonMappingVariables.values())
			variables.remove(milpVar.getName());
	}

	protected void addMapper(final GipsMapper<?> mapper) {
		mappers.put(mapper.getName(), mapper);
	}

	protected void addConstraint(final GipsConstraint<?, ?, ?> constraint) {
		constraints.put(constraint.getName(), constraint);
	}

	protected void addLinearFunction(final GipsLinearFunction<?, ?, ?> function) {
		functions.put(function.getName(), function);
	}

	protected void addTypeExtension(final GipsTypeExtender<?, ?> typeExtension) {
		typeExtensions.put(typeExtension.getName(), Objects.requireNonNull(typeExtension));
	}

	protected void setObjective(final GipsObjective<?> objective) {
		this.objective = objective;
	}

	public void setSolver(final Solver solver) {
		this.solver = solver;
	}

	public void setConstraintSorter(ConstraintSorter constraintSorter) {
		this.constraintSorter = constraintSorter;
	}

	public ConstraintSorter getConstraintSorter() {
		return this.constraintSorter;
	}

	public GipsTracer getTracer() {
		return tracer;
	}

	public EclipseIntegrationConfig getEclipseIntegrationConfig() {
		return eclipseIntegration.getConfig();
	}

	public EclipseIntegration getEclipseIntegration() {
		return eclipseIntegration;
	}

	/**
	 * Registers the time point when the initialization tick was executed.
	 */
	protected void tickInit() {
		this.tickInit = System.nanoTime();
	}

	/**
	 * Registers the time point when the initialization tock was executed.
	 */
	protected void tockInit() {
		this.tockInit = System.nanoTime();
	}

	/**
	 * Returns the complete initialization time in seconds.
	 * 
	 * @return Complete initialization time in seconds.
	 */
	public double getInitTimeInSeconds() {
		return 1.0 * (tockInit - tickInit) / 1_000_000_000;
	}

	/**
	 * Returns the GIPS configuration object of this GIPS engine.
	 * 
	 * @return GIPS configuration object.
	 */
	public GipsConfig getConfig() {
		return this.config;
	}

	/**
	 * Removes all duplicate and some trivial GIPS constraints per GIPSL constraint
	 * (group). This means that the method eliminates all duplicate constraints and
	 * some trivial constraints for every `constraint` block written in the
	 * respective GIPSL specification.
	 * 
	 * @param print If true, GIPS will print the number of eliminated constraints
	 *              onto the console.
	 * @return Returns the statistics of the removed constraints.
	 */
	private RemovedConstraintsStats removeUselessConstraints(final boolean print) {
		final long tick = System.nanoTime();
		int constraintsOriginal = 0;
		int duplicatesRemoved = 0;
		int trivialRemoved = 0;

		// For every specified GIPSL constraint
		for (final GipsConstraint<?, ?, ?> c : getConstraints().values()) {
			final RemovedConstraintsStats stats = c.removeUselessConstraints();
			constraintsOriginal += stats.original();
			duplicatesRemoved += stats.duplicates();
			trivialRemoved += stats.trivial();
		}

		// If configure, print statistics
		if (print) {
			final long tock = System.nanoTime();
			final double duration = (1.0 * (tock - tick) / 1_000_000_000);
			final DecimalFormat percFormat = new DecimalFormat("##.##%",
					DecimalFormatSymbols.getInstance(Locale.ENGLISH));
			final DecimalFormat secFormat = new DecimalFormat("#.##s",
					DecimalFormatSymbols.getInstance(Locale.ENGLISH));
			System.out.println("Removed " + duplicatesRemoved + " redundant constraints and " + trivialRemoved
					+ " trivial constraints out of " + constraintsOriginal + " total constraints ("
					+ percFormat.format((1.0 * (duplicatesRemoved + trivialRemoved) / constraintsOriginal)) + ") in "
					+ secFormat.format(duration) + ".");
		}

		return new RemovedConstraintsStats(constraintsOriginal, duplicatesRemoved, trivialRemoved);
	}

}
