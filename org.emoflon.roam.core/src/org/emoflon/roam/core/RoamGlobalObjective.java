package org.emoflon.roam.core;

import java.util.LinkedList;
import java.util.List;

import org.emoflon.roam.core.ilp.ILPConstant;
import org.emoflon.roam.core.ilp.ILPNestedLinearFunction;
import org.emoflon.roam.core.ilp.ILPWeightedLinearFunction;
import org.emoflon.roam.intermediate.RoamIntermediate.GlobalObjective;

public abstract class RoamGlobalObjective {

	final protected RoamEngine engine;
	final protected GlobalObjective objective;
	protected ILPNestedLinearFunction<Integer> globalObjective;
	protected List<ILPWeightedLinearFunction<Integer>> weightedFunctions;
	protected List<ILPConstant<Double>> constantTerms;

	public RoamGlobalObjective(final RoamEngine engine, final GlobalObjective objective) {
		this.engine = engine;
		this.objective = objective;
		initLocalObjectives();
	}

	public void buildObjectiveFunction() {
		weightedFunctions = new LinkedList<>();
		constantTerms = new LinkedList<>();
		buildLocalObjectives();
		buildTerms();
		globalObjective = new ILPNestedLinearFunction<>(weightedFunctions, constantTerms, objective.getTarget());
	}

	public ILPNestedLinearFunction<? extends Number> getObjectiveFunction() {
		return globalObjective;
	}

	protected void buildLocalObjectives() {
		engine.getObjectives().values().parallelStream().forEach(obj -> obj.buildObjectiveFunction());
	}

	protected abstract void initLocalObjectives();

	protected abstract void buildTerms();
}
