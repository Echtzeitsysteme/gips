package org.emoflon.gips.core;

import java.util.List;

import org.emoflon.gips.core.milp.model.Constant;
import org.emoflon.gips.core.milp.model.LinearFunction;
import org.emoflon.gips.core.milp.model.Term;

public abstract class GipsLinearFunction<ENGINE extends GipsEngine, LF extends org.emoflon.gips.intermediate.GipsIntermediate.LinearFunction, CONTEXT extends Object> {
	final protected ENGINE engine;
	final protected LF linearFunction;
	final protected String name;
	final protected TypeIndexer indexer;
	protected List<Term> terms;
	protected List<Constant> constantTerms;
	protected LinearFunction milpLinearFunction;

	public GipsLinearFunction(final ENGINE engine, final LF linearFunction) {
		this.engine = engine;
		this.linearFunction = linearFunction;
		this.name = linearFunction.getName();
		indexer = engine.getIndexer();
	}

	/**
	 * Clears all Lists within this linearFunction builder.
	 */
	public void clear() {
		if (terms != null) {
			terms.clear();
		}
		if (constantTerms != null) {
			constantTerms.clear();
		}
	}

	public LF getIntermediateLinearFunction() {
		return linearFunction;
	}

	public abstract void buildLinearFunction();

	public String getName() {
		return name;
	}

	public LinearFunction getLinearFunctionFunction() {
		return milpLinearFunction;
	}

	protected abstract void buildTerms(final CONTEXT context);
}
