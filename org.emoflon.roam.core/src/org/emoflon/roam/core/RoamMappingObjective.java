package org.emoflon.roam.core;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.emoflon.roam.core.ilp.ILPLinearFunction;
import org.emoflon.roam.core.ilp.ILPTerm;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingObjective;

public abstract class RoamMappingObjective<CONTEXT extends RoamMapping> extends RoamObjective<MappingObjective, CONTEXT, Integer> {

	final protected RoamMapper<CONTEXT> mapper;
	
	@SuppressWarnings("unchecked")
	public RoamMappingObjective(RoamEngine engine, MappingObjective objective) {
		super(engine, objective);
		mapper = (RoamMapper<CONTEXT>) engine.getMapper(objective.getMapping().getName());
	}
	
	@Override
	public void buildObjectiveFunction() {
		List<ILPTerm<Integer, Double>> terms = mapper.getMappings().values().parallelStream()
				.flatMap(context -> buildTerms(context).parallelStream())
				.collect(Collectors.toList());
		ilpObjective = new ILPLinearFunction<Integer>(terms, new LinkedList<>());
	}
	
	

}
