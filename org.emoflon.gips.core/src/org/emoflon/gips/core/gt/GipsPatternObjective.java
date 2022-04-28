package org.emoflon.roam.core.gt;

import java.util.Collections;
import java.util.LinkedList;

import org.emoflon.ibex.gt.api.GraphTransformationMatch;
import org.emoflon.ibex.gt.api.GraphTransformationPattern;
import org.emoflon.roam.core.RoamEngine;
import org.emoflon.roam.core.RoamObjective;
import org.emoflon.roam.core.ilp.ILPLinearFunction;
import org.emoflon.roam.intermediate.RoamIntermediate.PatternObjective;

public abstract class RoamPatternObjective<M extends GraphTransformationMatch<M, P>, P extends GraphTransformationPattern<M, P>>
		extends RoamObjective<PatternObjective, GraphTransformationMatch<M, P>, Integer> {

	final protected GraphTransformationPattern<M, P> pattern;

	public RoamPatternObjective(RoamEngine engine, PatternObjective objective, final P pattern) {
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
