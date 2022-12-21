package org.emoflon.gips.core;

import java.util.LinkedList;
import java.util.List;

import org.emoflon.gips.core.ilp.ILPConstant;
import org.emoflon.gips.core.ilp.ILPNestedLinearFunction;
import org.emoflon.gips.core.ilp.ILPWeightedLinearFunction;
import org.emoflon.gips.intermediate.GipsIntermediate.GlobalObjective;

public abstract class GipsGlobalObjective {

	final protected GipsEngine engine;
	final protected GlobalObjective objective;
	protected ILPNestedLinearFunction globalObjective;
	protected List<ILPWeightedLinearFunction> weightedFunctions;
	protected List<ILPConstant> constantTerms;

	public GipsGlobalObjective(final GipsEngine engine, final GlobalObjective objective) {
		this.engine = engine;
		this.objective = objective;
		initLocalObjectives();
	}

	public void buildObjectiveFunction() {
		weightedFunctions = new LinkedList<>();
		constantTerms = new LinkedList<>();
		buildLocalObjectives();
		buildTerms();
		globalObjective = new ILPNestedLinearFunction(weightedFunctions, constantTerms, objective.getTarget());
	}

	public ILPNestedLinearFunction getObjectiveFunction() {
		return globalObjective;
	}

	protected void buildLocalObjectives() {
		// TODO: stream() -> parallelStream() once GIPS is based on the new shiny GT
		// language
		engine.getObjectives().values().stream().forEach(obj -> obj.buildObjectiveFunction());
	}

	protected abstract void initLocalObjectives();

	protected abstract void buildTerms();
}
