package org.emoflon.gips.core;

import java.util.Collections;
import java.util.LinkedList;

import org.emoflon.gips.core.ilp.ILPLinearFunction;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingObjective;

public abstract class GipsMappingObjective<CONTEXT extends GipsMapping>
		extends GipsObjective<MappingObjective, CONTEXT, Integer> {

	final protected GipsMapper<CONTEXT> mapper;

	@SuppressWarnings("unchecked")
	public GipsMappingObjective(GipsEngine engine, MappingObjective objective) {
		super(engine, objective);
		mapper = (GipsMapper<CONTEXT>) engine.getMapper(objective.getMapping().getName());
	}

	@Override
	public void buildObjectiveFunction() {
		terms = Collections.synchronizedList(new LinkedList<>());
		constantTerms = Collections.synchronizedList(new LinkedList<>());
		mapper.getMappings().values().parallelStream().forEach(context -> buildTerms(context));
		ilpObjective = new ILPLinearFunction<>(terms, constantTerms);
	}

}
