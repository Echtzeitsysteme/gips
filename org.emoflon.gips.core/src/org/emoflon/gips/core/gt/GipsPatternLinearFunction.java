package org.emoflon.gips.core.gt;

import java.util.Collections;
import java.util.LinkedList;

import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsLinearFunction;
import org.emoflon.gips.core.milp.model.LinearFunction;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternFunction;
import org.emoflon.ibex.gt.engine.IBeXGTMatch;
import org.emoflon.ibex.gt.engine.IBeXGTPattern;

public abstract class GipsPatternLinearFunction<ENGINE extends GipsEngine, M extends IBeXGTMatch<M, P>, P extends IBeXGTPattern<P, M>>
		extends GipsLinearFunction<ENGINE, PatternFunction, M> {

	final protected P pattern;

	public GipsPatternLinearFunction(ENGINE engine, PatternFunction function, final P pattern) {
		super(engine, function);
		this.pattern = pattern;
	}

	@Override
	public void buildLinearFunction() {
		terms = Collections.synchronizedList(new LinkedList<>());
		constantTerms = Collections.synchronizedList(new LinkedList<>());
		// TODO: stream() -> parallelStream() once GIPS is based on the new shiny GT
		// language
		pattern.getMatches(false).stream().forEach(context -> buildTerms(context));
		milpLinearFunction = new LinearFunction(terms, constantTerms);
	}

}
