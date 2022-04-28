package org.emoflon.roam.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.emoflon.roam.core.ilp.ILPSolver;
import org.emoflon.roam.core.ilp.ILPSolverOutput;
import org.emoflon.roam.core.ilp.ILPSolverStatus;

public abstract class RoamEngine {

	final protected Map<String, RoamMapper<?>> mappers = new HashMap<>();
	protected TypeIndexer indexer;
	final protected Map<String, RoamConstraint<?, ?, ?>> constraints = new HashMap<>();
	final protected Map<String, RoamObjective<?, ?, ?>> objectives = new HashMap<>();
	protected RoamGlobalObjective globalObjective;
	protected ILPSolver ilpSolver;

	public abstract void update();

	public abstract void saveResult() throws IOException;

	public abstract void saveResult(final String path) throws IOException;

	public void buildILPProblem(boolean doUpdate) {
		if (doUpdate)
			update();

		constraints.values().parallelStream().forEach(constraint -> constraint.buildConstraints());
		if (globalObjective != null)
			globalObjective.buildObjectiveFunction();

		ilpSolver.buildILPProblem();
	}

	public ILPSolverOutput solveILPProblem() {
		ILPSolverOutput output = ilpSolver.solve();
		if (output.status() != ILPSolverStatus.INFEASIBLE)
			ilpSolver.updateValuesFromSolution();

		return output;
	}

	public RoamMapper<?> getMapper(final String mappingName) {
		return mappers.get(mappingName);
	}

	public Map<String, RoamMapper<?>> getMappers() {
		return mappers;
	}

	public Map<String, RoamConstraint<?, ?, ?>> getConstraints() {
		return constraints;
	}

	public Map<String, RoamObjective<?, ?, ?>> getObjectives() {
		return objectives;
	}

	public TypeIndexer getIndexer() {
		return indexer;
	}

	public RoamGlobalObjective getGlobalObjective() {
		return globalObjective;
	}

	public void terminate() {
		indexer.terminate();
		mappers.forEach((name, mapper) -> mapper.terminate());
	}

	protected abstract void initTypeIndexer();

	protected void addMapper(final RoamMapper<?> mapper) {
		mappers.put(mapper.getName(), mapper);
	}

	protected void addConstraint(final RoamConstraint<?, ?, ?> constraint) {
		constraints.put(constraint.getName(), constraint);
	}

	protected void addObjective(final RoamObjective<?, ?, ?> objective) {
		objectives.put(objective.getName(), objective);
	}

	protected void setGlobalObjective(final RoamGlobalObjective globalObjective) {
		this.globalObjective = globalObjective;
	}

	protected void setILPSolver(final ILPSolver solver) {
		this.ilpSolver = solver;
	}
}
