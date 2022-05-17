package org.emoflon.gips.core.gt;

import java.util.Collections;
import java.util.LinkedList;

import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsObjective;
import org.emoflon.gips.core.ilp.ILPLinearFunction;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternObjective;
import org.emoflon.ibex.gt.api.GraphTransformationMatch;
import org.emoflon.ibex.gt.api.GraphTransformationPattern;

public abstract class GipsPatternObjective<ENGINE extends GipsEngine, M extends GraphTransformationMatch<M, P>, P extends GraphTransformationPattern<M, P>>
		extends GipsObjective<ENGINE, PatternObjective, M, Integer> {

	final protected P pattern;

	public GipsPatternObjective(ENGINE engine, PatternObjective objective, final P pattern) {
		super(engine, objective);
		this.pattern = pattern;
	}

	@Override
	public void buildObjectiveFunction() {
		terms = Collections.synchronizedList(new LinkedList<>());
		constantTerms = Collections.synchronizedList(new LinkedList<>());
		pattern.findMatches(false).parallelStream().forEach(context -> buildTerms(context));
		ilpObjective = new ILPLinearFunction<>(terms, constantTerms);
	}

}
