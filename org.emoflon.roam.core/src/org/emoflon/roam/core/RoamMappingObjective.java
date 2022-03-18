package org.emoflon.roam.core;

import java.util.Collections;
import java.util.LinkedList;

import org.emoflon.roam.core.ilp.ILPLinearFunction;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingObjective;

public abstract class RoamMappingObjective<CONTEXT extends RoamMapping>
		extends RoamObjective<MappingObjective, CONTEXT, Integer> {

	final protected RoamMapper<CONTEXT> mapper;

	@SuppressWarnings("unchecked")
	public RoamMappingObjective(RoamEngine engine, MappingObjective objective) {
		super(engine, objective);
		mapper = (RoamMapper<CONTEXT>) engine.getMapper(objective.getMapping().getName());
	}

	@Override
	public void buildObjectiveFunction() {
		terms = Collections.synchronizedList(new LinkedList<>());
		constantTerms = Collections.synchronizedList(new LinkedList<>());
		mapper.getMappings().values().parallelStream().forEach(context -> buildTerms(context));
		ilpObjective = new ILPLinearFunction<>(terms, constantTerms);
	}

}
