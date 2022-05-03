package org.emoflon.gips.core;

import java.util.List;

import org.emoflon.gips.core.ilp.ILPConstant;
import org.emoflon.gips.core.ilp.ILPLinearFunction;
import org.emoflon.gips.core.ilp.ILPTerm;
import org.emoflon.gips.intermediate.GipsIntermediate.Objective;

public abstract class GipsObjective<OBJECTIVE extends Objective, CONTEXT extends Object, VARTYPE extends Number> {
	final protected GipsEngine engine;
	final protected OBJECTIVE objective;
	final protected String name;
	protected List<ILPTerm<Integer, Double>> terms;
	protected List<ILPConstant<Double>> constantTerms;
	protected ILPLinearFunction<VARTYPE> ilpObjective;

	public GipsObjective(final GipsEngine engine, final OBJECTIVE objective) {
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
