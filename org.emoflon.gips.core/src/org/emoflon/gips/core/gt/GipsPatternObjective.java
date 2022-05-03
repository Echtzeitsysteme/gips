package org.emoflon.gips.core.gt;

import java.util.Collections;
import java.util.LinkedList;

import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsObjective;
import org.emoflon.gips.core.ilp.ILPLinearFunction;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternObjective;
import org.emoflon.ibex.gt.api.GraphTransformationMatch;
import org.emoflon.ibex.gt.api.GraphTransformationPattern;

public abstract class GipsPatternObjective<M extends GraphTransformationMatch<M, P>, P extends GraphTransformationPattern<M, P>>
		extends GipsObjective<PatternObjective, GraphTransformationMatch<M, P>, Integer> {

	final protected GraphTransformationPattern<M, P> pattern;

	public GipsPatternObjective(GipsEngine engine, PatternObjective objective, final P pattern) {
		super(engine, objective);
		this.pattern = pattern;
	}

	@Override
	public void buildObjectiveFunction() {
		terms = Collections.synchronizedList(new LinkedList<>());
		constantTerms = Collections.synchronizedList(new LinkedList<>());
		pattern.findMatches().parallelStream().forEach(context -> buildTerms(context));
		ilpObjective = new ILPLinearFunction<>(terms, constantTerms);
	}

}
