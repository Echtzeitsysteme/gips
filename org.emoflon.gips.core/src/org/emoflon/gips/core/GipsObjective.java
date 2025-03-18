package org.emoflon.gips.core;

import java.util.LinkedList;
import java.util.List;

import org.emoflon.gips.core.milp.model.Constant;
import org.emoflon.gips.core.milp.model.NestedLinearFunction;
import org.emoflon.gips.core.milp.model.WeightedLinearFunction;
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

	public void buildObjectiveFunction() {
		weightedFunctions = new LinkedList<>();
		constantTerms = new LinkedList<>();
		buildLocalObjectives();
		buildTerms();
		milpObjective = new NestedLinearFunction(weightedFunctions, constantTerms, objective.getGoal());
	}

	public Objective getIntermediateObjective() {
		return objective;
	}

	public NestedLinearFunction getObjectiveFunction() {
		return milpObjective;
	}

	protected void buildLocalObjectives() {
		// TODO: stream() -> parallelStream() once GIPS is based on the new shiny GT
		// language
		engine.getLinearFunctions().values().stream().forEach(fn -> fn.buildLinearFunction());
	}

	protected abstract void initLocalObjectives();

	protected abstract void buildTerms();
}
