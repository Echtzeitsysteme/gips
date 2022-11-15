package org.emoflon.gips.core;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.emoflon.gips.core.ilp.ILPSolver;
import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.emoflon.gips.core.ilp.ILPVariable;
import org.emoflon.gips.core.validation.GipsConstraintValidationLog;

public abstract class GipsEngine {

	final protected Map<String, GipsMapper<?>> mappers = new HashMap<>();
	final protected Map<String, ILPVariable<?>> nonMappingVariables = Collections.synchronizedMap(new HashMap<>());
	protected TypeIndexer indexer;
	protected GipsConstraintValidationLog validationLog;
	final protected Map<String, GipsConstraint<?, ?, ?>> constraints = new HashMap<>();
	final protected Map<String, GipsObjective<?, ?, ?>> objectives = new HashMap<>();
	protected GipsGlobalObjective globalObjective;
	protected ILPSolver ilpSolver;

	public abstract void update();

	public abstract void saveResult() throws IOException;

	public abstract void saveResult(final String path) throws IOException;

	public void buildILPProblem(boolean doUpdate) {
		if (doUpdate)
			update();

		nonMappingVariables.clear();
		constraints.values().stream().forEach(constraint -> constraint.calcAdditionalVariables());
		constraints.values().stream().forEach(constraint -> constraint.buildConstraints());
		if (globalObjective != null)
			globalObjective.buildObjectiveFunction();

		ilpSolver.buildILPProblem();
	}

	public ILPSolverOutput solveILPProblem() {
		if (validationLog.isNotValid()) {
			ILPSolverOutput output = new ILPSolverOutput(ILPSolverStatus.INFEASIBLE, Double.NaN, validationLog, 0);
			return output;
		}
		ILPSolverOutput output = ilpSolver.solve();
		if (output.status() != ILPSolverStatus.INFEASIBLE && output.solutionCount() > 0)
			ilpSolver.updateValuesFromSolution();

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

	public Map<String, GipsObjective<?, ?, ?>> getObjectives() {
		return objectives;
	}

	public TypeIndexer getIndexer() {
		return indexer;
	}

	public GipsConstraintValidationLog getValidationLog() {
		return validationLog;
	}

	public GipsGlobalObjective getGlobalObjective() {
		return globalObjective;
	}

	public void terminate() {
		indexer.terminate();
		mappers.forEach((name, mapper) -> mapper.terminate());
	}

	protected abstract void initTypeIndexer();

	public synchronized ILPVariable<?> getNonMappingVariable(final String name) {
		ILPVariable<?> ilpVar = nonMappingVariables.get(name);
		if (ilpVar != null)
			return ilpVar;

		throw new RuntimeException("Variable <" + name + "> is not present in the non-mapping variable index.");
	}

	public synchronized void addNonMappingVariable(final ILPVariable<?> ilpVar) {
		ILPVariable<?> oldValue = nonMappingVariables.put(ilpVar.getName(), ilpVar);
		if (oldValue != null)
			throw new RuntimeException(
					"Variable <" + ilpVar.getName() + "> already present in non-mapping variable index.");
	}

	public synchronized void removeNonMappingVariable(final ILPVariable<?> ilpVar) {
		nonMappingVariables.remove(ilpVar.getName());
	}

	protected void addMapper(final GipsMapper<?> mapper) {
		mappers.put(mapper.getName(), mapper);
	}

	protected void addConstraint(final GipsConstraint<?, ?, ?> constraint) {
		constraints.put(constraint.getName(), constraint);
	}

	protected void addObjective(final GipsObjective<?, ?, ?> objective) {
		objectives.put(objective.getName(), objective);
	}

	protected void setGlobalObjective(final GipsGlobalObjective globalObjective) {
		this.globalObjective = globalObjective;
	}

	protected void setILPSolver(final ILPSolver solver) {
		this.ilpSolver = solver;
	}
}
