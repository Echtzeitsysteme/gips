package org.emoflon.roam.core.gt;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.emoflon.ibex.gt.api.GraphTransformationMatch;
import org.emoflon.ibex.gt.api.GraphTransformationPattern;
import org.emoflon.roam.core.RoamEngine;
import org.emoflon.roam.core.RoamObjective;
import org.emoflon.roam.core.ilp.ILPLinearFunction;
import org.emoflon.roam.core.ilp.ILPTerm;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingObjective;
import org.emoflon.roam.intermediate.RoamIntermediate.PatternObjective;

public abstract class RoamPatternObjective <M extends GraphTransformationMatch<M, P>, P extends GraphTransformationPattern<M, P>> extends RoamObjective<PatternObjective, GraphTransformationMatch<M, P>, Integer> {

	final protected GraphTransformationPattern<M, P> pattern;
	
	@SuppressWarnings("unchecked")
	public RoamPatternObjective(RoamEngine engine, PatternObjective objective) {
		super(engine, objective);
		pattern = (GraphTransformationPattern<M, P>) engine.getEMoflonAPI().getPattern(objective.getPattern().getName());
	}
	
	@Override
	public void buildObjectiveFunction() {
		List<ILPTerm<Integer, Double>> terms = pattern.findMatches().parallelStream()
				.flatMap(context -> buildTerms(context).parallelStream())
				.collect(Collectors.toList());
		ilpObjective = new ILPLinearFunction<Integer>(terms, new LinkedList<>());
	}
	
	

}
