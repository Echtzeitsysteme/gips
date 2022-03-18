package org.emoflon.roam.core;

import java.util.List;

import org.emoflon.roam.core.ilp.ILPConstant;
import org.emoflon.roam.core.ilp.ILPLinearFunction;
import org.emoflon.roam.core.ilp.ILPTerm;
import org.emoflon.roam.intermediate.RoamIntermediate.Objective;

public abstract class RoamObjective<OBJECTIVE extends Objective, CONTEXT extends Object, VARTYPE extends Number> {
	final protected RoamEngine engine;
	final protected OBJECTIVE objective;
	final protected String name;
	protected List<ILPTerm<Integer, Double>> terms;
	protected List<ILPConstant<Double>> constantTerms;
	protected ILPLinearFunction<VARTYPE> ilpObjective;

	public RoamObjective(final RoamEngine engine, final OBJECTIVE objective) {
		this.engine = engine;
		this.objective = objective;
		this.name = objective.getName();
	}

	public abstract void buildObjectiveFunction();

	public String getName() {
		return name;
	}

	public ILPLinearFunction<VARTYPE> getObjectiveFunction() {
		return ilpObjective;
	}

	protected abstract void buildTerms(final CONTEXT context);
}
