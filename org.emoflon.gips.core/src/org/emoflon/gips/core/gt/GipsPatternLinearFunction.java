package org.emoflon.gips.core.gt;

import java.util.Collections;
import java.util.LinkedList;

import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsLinearFunction;
import org.emoflon.gips.core.milp.model.LinearFunction;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternFunction;
import org.emoflon.ibex.gt.api.GraphTransformationMatch;
import org.emoflon.ibex.gt.api.GraphTransformationPattern;

public abstract class GipsPatternLinearFunction<ENGINE extends GipsEngine, M extends GraphTransformationMatch<M, P>, P extends GraphTransformationPattern<M, P>>
		extends GipsLinearFunction<ENGINE, PatternFunction, M> {

	final protected P pattern;

	public GipsPatternLinearFunction(ENGINE engine, PatternFunction function, final P pattern) {
		super(engine, function);
		this.pattern = pattern;
	}

	@Override
	public void buildLinearFunction(final boolean parallel) {
		terms = Collections.synchronizedList(new LinkedList<>());
		constantTerms = Collections.synchronizedList(new LinkedList<>());
		if (parallel) {
			pattern.findMatches(false).parallelStream().forEach(context -> buildTerms(context));
		} else {
			pattern.findMatches(false).stream().forEach(context -> buildTerms(context));
		}

		milpLinearFunction = new LinearFunction(terms, constantTerms);
	}

}
