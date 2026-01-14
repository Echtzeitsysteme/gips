package org.emoflon.gips.core;

import java.util.LinkedList;
import java.util.List;

import org.emoflon.gips.core.milp.model.Constant;
import org.emoflon.gips.core.milp.model.NestedLinearFunction;
import org.emoflon.gips.core.milp.model.WeightedLinearFunction;
import org.emoflon.gips.core.util.StreamUtils;
import org.emoflon.gips.intermediate.GipsIntermediate.Objective;

public abstract class GipsObjective {

	final protected GipsEngine engine;
	final protected TypeIndexer indexer;
	final protected Objective objective;
	protected NestedLinearFunction milpObjective;
	protected List<WeightedLinearFunction> weightedFunctions;
	protected List<Constant> constantTerms;

	public GipsObjective(final GipsEngine engine, final Objective objective) {
		this.engine = engine;
		this.indexer = engine.getIndexer();
		this.objective = objective;
		initLocalObjectives();
	}

	/**
	 * Builds the objective function. If the parameter `parallel` is true, the
	 * process may run in parallel.
	 * 
	 * @param parallel If true, the process may run in parallel.
	 */
	public void buildObjectiveFunction(final boolean parallel) {
		weightedFunctions = new LinkedList<>();
		constantTerms = new LinkedList<>();
		buildLocalObjectives(parallel);
		buildTerms();
		milpObjective = new NestedLinearFunction(weightedFunctions, constantTerms, objective.getGoal());
	}

	public Objective getIntermediateObjective() {
		return objective;
	}

	public NestedLinearFunction getObjectiveFunction() {
		return milpObjective;
	}

	/**
	 * Builds all local objectives. If the parameter `parallel` is true, the process
	 * may run in parallel.
	 * 
	 * @param parallel If true, the process may run in parallel.
	 */
	protected void buildLocalObjectives(final boolean parallel) {
		StreamUtils.toStream(engine.getLinearFunctions().values(), parallel)
				.forEach(fn -> fn.buildLinearFunction(parallel));
	}

	protected abstract void initLocalObjectives();

	protected abstract void buildTerms();
}
