package org.emoflon.gips.core;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.emoflon.gips.core.milp.Solver;
import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.emoflon.gips.core.milp.model.Variable;
import org.emoflon.gips.core.trace.EclipseIntegration;
import org.emoflon.gips.core.trace.EclipseIntegrationConfig;
import org.emoflon.gips.core.trace.GipsTracer;
import org.emoflon.gips.core.util.Observer;
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
	protected GipsObjective objective;
	protected Solver solver;

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

	public void buildProblemTimed(boolean doUpdate) {
		Observer observer = Observer.getInstance();
		observer.observe("BUILD", () -> {
			if (doUpdate)
				observer.observe("PM", () -> update());

			observer.observe("BUILD_GIPS", () -> {
				// Reset validation log
				validationLog = new GipsConstraintValidationLog();

				// Constraints are re-build a few lines below
				constraints.values().stream().forEach(constraint -> constraint.clear());
				
				// Reset trace
				getTracer().resetTrace();

				nonMappingVariables.clear();
				mappers.values().stream().flatMap(mapper -> mapper.getMappings().values().stream())
						.filter(m -> m.hasAdditionalVariables()).forEach(m -> {
							Map<String, Variable<?>> variables = nonMappingVariables.get(m);
							if (variables == null) {
								variables = Collections.synchronizedMap(new HashMap<>());
								nonMappingVariables.put(m, variables);
							}
							variables.putAll(m.getAdditionalVariables());
						});

				constraints.values().stream().forEach(constraint -> constraint.calcAdditionalVariables());

				updateConstants();

				constraints.values().stream().forEach(constraint -> constraint.buildConstraints());

				if (objective != null)
					objective.buildObjectiveFunction();
			});

			observer.observe("BUILD_SOLVER", () -> {
				solver.init();
				solver.buildILPProblem();
			});
			
			buildTraceGraphAndSendToIDE();
		});
	}

	public void buildProblem(boolean doUpdate) {
		if (doUpdate)
			update();

		// Reset validation log
		validationLog = new GipsConstraintValidationLog();

		// Constraints are re-build a few lines below
		constraints.values().stream().forEach(constraint -> constraint.clear());

		// Reset trace
		getTracer().resetTrace();

		// Objectives will be build by the global objective call below
//		objectives.values().stream().forEach(objective -> objective.clear());
		// TODO: It seems to me that this is not necessary for objectives. All tests
		// (and also the dedicated tests for checking this!) are happy with it.

		nonMappingVariables.clear();
		mappers.values().stream().flatMap(mapper -> mapper.getMappings().values().stream())
				.filter(m -> m.hasAdditionalVariables()).forEach(m -> {
					Map<String, Variable<?>> variables = nonMappingVariables.get(m);
					if (variables == null) {
						variables = Collections.synchronizedMap(new HashMap<>());
						nonMappingVariables.put(m, variables);
					}
					variables.putAll(m.getAdditionalVariables());
				});

		constraints.values().stream().forEach(constraint -> constraint.calcAdditionalVariables());

		updateConstants();

		constraints.values().stream().forEach(constraint -> constraint.buildConstraints());
		if (objective != null)
			objective.buildObjectiveFunction();

		solver.init();
		solver.buildILPProblem();
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
		SolverOutput out = observer.observe("SOLVE_PROBLEM", () -> {
			SolverOutput output;
			if (validationLog.isNotValid()) {
				output = new SolverOutput(SolverStatus.INFEASIBLE, Double.NaN, validationLog, 0, null);
			} else {
				this.tockInit();
				output = solver.solve();

				if (output.status() != SolverStatus.INFEASIBLE && output.solutionCount() > 0)
					solver.updateValuesFromSolution();
			}

			solver.reset();
			eclipseIntegration.sendSolutionValuesToIDE();
			return output;
		});
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
		}

		solver.reset();
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
	}

	protected abstract void initTypeIndexer();

	public synchronized Variable<?> getNonMappingVariable(final Object context, final String variableTypeName) {
		Map<String, Variable<?>> variables = nonMappingVariables.get(context);
		if (variables == null)
			throw new RuntimeException(
					"Variable <" + variableTypeName + "> is not present in the non-mapping variable index.");

		return variables.get(variableTypeName);

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

	public synchronized void removeNonMappingVariable(final Variable<?> ilpVar) {
		nonMappingVariables.remove(ilpVar.getName());
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

	protected void setObjective(final GipsObjective objective) {
		this.objective = objective;
	}

	public void setSolver(final Solver solver) {
		this.solver = solver;
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
