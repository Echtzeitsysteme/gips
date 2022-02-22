package org.emoflon.roam.core;

import java.util.HashMap;
import java.util.Map;

import org.emoflon.roam.core.ilp.ILPLinearFunction;
import org.emoflon.roam.intermediate.RoamIntermediate.GlobalObjective;
import org.emoflon.roam.intermediate.RoamIntermediate.Objective;

public abstract class RoamGlobalObjective {
	
	final protected RoamEngine engine;
	final protected GlobalObjective objective;
	final protected Map<Objective, RoamObjective<? extends Objective, ? extends Object, ? extends Number>> localObjectives = new HashMap<>();
	protected ILPLinearFunction<? extends Number> globalObjective;
	
	public RoamGlobalObjective(final RoamEngine engine, final GlobalObjective objective) {
		this.engine = engine;
		this.objective = objective;
		engine.getObjectives().values().forEach(obj -> localObjectives.put(obj.objective, obj));
	}
	
	public abstract void buildObjectiveFunction();
	
	public ILPLinearFunction<? extends Number> getObjectiveFunction() {
		return globalObjective;
	}
}
