package org.emoflon.gips.core;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.emoflon.gips.core.milp.Solver;
import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.emoflon.gips.core.milp.model.Variable;
import org.emoflon.gips.core.util.Observer;
import org.emoflon.gips.core.validation.GipsConstraintValidationLog;
import org.emoflon.gips.debugger.api.Gips2IlpTraceHelper;
import org.emoflon.gips.debugger.api.ILPTraceKeywords;

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
	protected Gips2IlpTraceHelper tracer;

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
				solver.buildILPProblem();
			});
		});
	}

	public void buildProblem(boolean doUpdate) {
		if (doUpdate)
			update();

		// Reset validation log
		validationLog = new GipsConstraintValidationLog();

		// Constraints are re-build a few lines below
		constraints.values().stream().forEach(constraint -> constraint.clear());

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

		solver.buildILPProblem();
		buildTracingTree();
	}

	// TODO Rework the way the trace is saved (here) and loaded (in Eclipse). Find a
	// way to replace all the magic words here, they are also used within the LP
	// editor connector.
	protected void buildTracingTree() {
		final var tracer = getTracer();

		// try to build a bridge between ILP model and ILP text file

		for (final var mapper : this.mappers.values()) {
			tracer.gips2intern(mapper.mapping,
					ILPTraceKeywords.buildElementId(ILPTraceKeywords.TYPE_MAPPING, mapper.getName()));
//			final var fragmentPath = EcoreUtil.getURI(mapper.mapping);
		}

		for (final var constraint : this.constraints.values()) {
			tracer.gips2intern(constraint.constraint,
					ILPTraceKeywords.buildElementId(ILPTraceKeywords.TYPE_CONSTRAINT, constraint.getName()));
			for (final var ilpConstraint : constraint.getConstraints()) {
				for (final var ilpTerm : ilpConstraint.lhsTerms()) {
					tracer.gips2intern(constraint.constraint.getExpression(), ILPTraceKeywords
							.buildElementId(ILPTraceKeywords.TYPE_CONSTRAINT_VAR, ilpTerm.variable().getName()));
				}
			}

			for (final var variables : constraint.getAdditionalVariables()) {
				tracer.gips2intern(constraint.constraint.getExpression(),
						ILPTraceKeywords.buildElementId(ILPTraceKeywords.TYPE_CONSTRAINT_VAR, variables.getName()));
			}
		}

		for (final var function : this.functions.values()) {
			tracer.gips2intern(function.linearFunction,
					ILPTraceKeywords.buildElementId(ILPTraceKeywords.TYPE_FUNCTION, function.getName()));
			for (var term : function.terms) {
				tracer.gips2intern(function.linearFunction, ILPTraceKeywords
						.buildElementId(ILPTraceKeywords.TYPE_FUNCTION_VAR, term.variable().getName()));
			}
		}

//		final var constraintsInMapper = new HashSet<>(this.mappers.keySet());
//		constraintsInMapper.retainAll(this.constraints.keySet());
//
//		final var objectsInMapper = new HashSet<>(this.mappers.keySet());
//		objectsInMapper.retainAll(this.objectives.keySet());

		if (objective != null) {
			tracer.gips2intern(objective.objective,
					ILPTraceKeywords.buildElementId(ILPTraceKeywords.TYPE_OBJECTIVE, ""));
		}

		tracer.finalizeTrace();
	}

	public SolverOutput solveProblemTimed() {
		Observer observer = Observer.getInstance();
		SolverOutput out = observer.observe("SOLVE_PROBLEM", () -> {
			if (validationLog.isNotValid()) {
				SolverOutput output = new SolverOutput(SolverStatus.INFEASIBLE, Double.NaN, validationLog, 0, null);
				solver.reset();
				return output;
			}
			SolverOutput output = solver.solve();
			if (output.status() != SolverStatus.INFEASIBLE && output.solutionCount() > 0)
				solver.updateValuesFromSolution();

			solver.reset();
			return output;
		});
		return out;
	}

	public SolverOutput solveProblem() {
		if (validationLog.isNotValid()) {
			SolverOutput output = new SolverOutput(SolverStatus.INFEASIBLE, Double.NaN, validationLog, 0, null);
			solver.reset();
			return output;
		}
		SolverOutput output = solver.solve();
		if (output.status() != SolverStatus.INFEASIBLE && output.solutionCount() > 0)
			solver.updateValuesFromSolution();

		solver.reset();
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

	public void setTracer(final Gips2IlpTraceHelper tracer) {
		this.tracer = tracer;
	}

	public Gips2IlpTraceHelper getTracer() {
		return this.tracer;
	}
}
