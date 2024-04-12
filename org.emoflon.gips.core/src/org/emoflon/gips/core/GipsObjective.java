package org.emoflon.gips.core;

import java.util.List;

import org.emoflon.gips.core.ilp.ILPConstant;
import org.emoflon.gips.core.ilp.ILPLinearFunction;
import org.emoflon.gips.core.ilp.ILPTerm;
import org.emoflon.gips.intermediate.GipsIntermediate.Objective;

public abstract class GipsObjective<ENGINE extends GipsEngine, OBJECTIVE extends Objective, CONTEXT extends Object> {
	final protected ENGINE engine;
	final protected OBJECTIVE objective;
	final protected String name;
	final protected TypeIndexer indexer;
	protected List<ILPTerm> terms;
	protected List<ILPConstant> constantTerms;
	protected ILPLinearFunction ilpObjective;

	public GipsObjective(final ENGINE engine, final OBJECTIVE objective) {
		this.engine = engine;
		this.objective = objective;
		this.name = objective.getName();
		indexer = engine.getIndexer();
	}

	/**
	 * Clears all Lists within this objective builder.
	 */
	public void clear() {
		if (terms != null) {
			terms.clear();
		}
		if (constantTerms != null) {
			constantTerms.clear();
		}
	}

	public abstract void buildObjectiveFunction();

	public String getName() {
		return name;
	}

	public ILPLinearFunction getObjectiveFunction() {
		return ilpObjective;
	}

	protected abstract void buildTerms(final CONTEXT context);
}
