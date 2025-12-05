package org.emoflon.gips.core;

import java.util.Collections;
import java.util.LinkedList;

import org.emoflon.gips.core.milp.model.LinearFunction;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingFunction;

public abstract class GipsMappingLinearFunction<ENGINE extends GipsEngine, CONTEXT extends GipsMapping>
		extends GipsLinearFunction<ENGINE, MappingFunction, CONTEXT> {

	final protected GipsMapper<CONTEXT> mapper;

	@SuppressWarnings("unchecked")
	public GipsMappingLinearFunction(ENGINE engine, MappingFunction function) {
		super(engine, function);
		mapper = (GipsMapper<CONTEXT>) engine.getMapper(function.getMapping().getName());
	}

	@Override
	public void buildLinearFunction(final boolean parallel) {
		terms = Collections.synchronizedList(new LinkedList<>());
		constantTerms = Collections.synchronizedList(new LinkedList<>());
		if (parallel) {
			mapper.getMappings().values().parallelStream().forEach(context -> buildTerms(context));
		} else {
			mapper.getMappings().values().stream().forEach(context -> buildTerms(context));
		}
		milpLinearFunction = new LinearFunction(terms, constantTerms);
	}

}
