package org.emoflon.gips.core;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import org.emoflon.gips.core.milp.ConstraintSorter;
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
	protected GipsObjective objective;
	protected Solver solver;

	protected ConstraintSorter constraintSorter;

	protected EclipseIntegration eclipseIntegration;
	protected GipsTracer tracer;

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
	 * Builds the problem with time measurement included. This method does not
	 * trigger an update of the pattern matcher and runs everything sequentially.
	 */
	public void buildProblemTimed() {
		buildProblemTimed(false, false);
	}

	/**
	 * Builds the problem with time measurement included. This method does trigger
	 * an update of the pattern matcher depending on the given input parameter and
	 * runs everything sequentially.
	 * 
	 * @param doUpdate If true, the pattern matcher will be updated before building
	 *                 the problem.
	 */
	public void buildProblemTimed(final boolean doUpdate) {
		buildProblemTimed(doUpdate, false);
	}

	/**
	 * Builds the problem with time measurement included. `doUpdate` defines if the
	 * pattern matcher should be updated and `parallel` decides if the method runs
	 * everything in parallel or sequentially.
	 * 
	 * @param doUpdate If true, the pattern matcher will be updated before building
	 *                 the problem.
	 * @param parallel If true, the problem will be built in parallel.
	 */
	public void buildProblemTimed(final boolean doUpdate, final boolean parallel) {
		final Observer observer = Observer.getInstance();
		observer.observe("BUILD", () -> {
			if (doUpdate)
				observer.observe("PM", () -> update());

			observer.observe("BUILD_GIPS", () -> {
				// Reset validation log
				validationLog = new GipsConstraintValidationLog();

				// Constraints are re-build a few lines below
				StreamUtils.toStream(constraints.values(), parallel).forEach(constraint -> constraint.clear());
				StreamUtils.toStream(typeExtensions.values(), parallel).forEach(typeExtension -> typeExtension.clear());

				// Reset trace
				getTracer().resetTrace();

				nonMappingVariables.clear();
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
						});

				StreamUtils.toStream(constraints.values(), parallel)
						.forEach(constraint -> constraint.calcAdditionalVariables());
				StreamUtils.toStream(typeExtensions.values(), parallel)
						.forEach(typeExtension -> typeExtension.calculateExtensions());

				updateConstants();

				StreamUtils.toStream(constraints.values(), parallel)
						.forEach(constraint -> constraint.buildConstraints(parallel));

				if (objective != null)
					objective.buildObjectiveFunction(parallel);
			});

			observer.observe("BUILD_SOLVER", () -> {
				solver.init();
				solver.buildMILPProblem();
			});

			buildTraceGraphAndSendToIDE();
		});
	}

	/**
	 * Builds the problem with no time measurement included. This method does not
	 * trigger an update of the pattern matcher and runs everything sequentially.
	 */
	public void buildProblem() {
		buildProblem(false, false);
	}

	/**
	 * Builds the problem with time no measurement included. This method does
	 * trigger an update of the pattern matcher depending on the given input
	 * parameter and runs everything sequentially.
	 * 
	 * @param doUpdate If true, the pattern matcher will be updated before building
	 *                 the problem.
	 */
	public void buildProblem(final boolean doUpdate) {
		buildProblem(doUpdate, false);
	}

	/**
	 * Builds the problem with no time measurement included. `doUpdate` defines if
	 * the pattern matcher should be updated and `parallel` decides if the method
	 * runs everything in parallel or sequentially.
	 * 
	 * @param doUpdate If true, the pattern matcher will be updated before building
	 *                 the problem.
	 * @param parallel If true, the problem will be built in parallel.
	 */
	public void buildProblem(final boolean doUpdate, final boolean parallel) {
		if (doUpdate)
			update();

		// Reset validation log
		validationLog = new GipsConstraintValidationLog();

		// Constraints are re-build a few lines below
		StreamUtils.toStream(constraints.values(), parallel).forEach(constraint -> constraint.clear());
		StreamUtils.toStream(typeExtensions.values(), parallel).forEach(typeExtension -> typeExtension.clear());

		// Reset trace
		getTracer().resetTrace();

		// Objectives will be build by the global objective call below
//		objectives.values().parallelStream().forEach(objective -> objective.clear());
		// TODO: It seems to me that this is not necessary for objectives. All tests
		// (and also the dedicated tests for checking this!) are happy with it.

		nonMappingVariables.clear();
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
				});

		StreamUtils.toStream(constraints.values(), parallel)
				.forEach(constraint -> constraint.calcAdditionalVariables());
		StreamUtils.toStream(typeExtensions.values(), parallel)
				.forEach(typeExtension -> typeExtension.calculateExtensions());

		updateConstants();

		StreamUtils.toStream(constraints.values(), parallel)
				.forEach(constraint -> constraint.buildConstraints(parallel));

		if (objective != null)
			objective.buildObjectiveFunction(parallel);

		solver.init();
		solver.buildMILPProblem();
		buildTraceGraphAndSendToIDE();
	}

	protected void buildTraceGraphAndSendToIDE() {
		if (getTracer().isTracingEnabled()) {
			getTracer().buildIntermediate2LpTraceGraph(this);
			eclipseIntegration.sendLpTraceToIDE(getTracer());
		}
	}

	public SolverOutput solveProblemTimed() {
		Observer observer = Observer.getInstance();
		SolverOutput out = observer.observe("SOLVE_PROBLEM", (Supplier<SolverOutput>) this::solveProblem);
		return out;
	}

	public SolverOutput solveProblem() {
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

		solver.reset();
		GlobalMappingIndexer.getInstance().terminate();
		eclipseIntegration.sendSolutionValuesToIDE();
		return output;
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

	public GipsObjective getObjective() {
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

	protected void setObjective(final GipsObjective objective) {
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

}
