package org.emoflon.roam.core;

import java.util.List;

import org.emoflon.roam.core.ilp.ILPConstraint;
import org.emoflon.roam.core.ilp.ILPTerm;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingConstraint;

public abstract class RoamMappingConstraint extends RoamConstraint<MappingConstraint, RoamMapping, Integer> {
	
	final protected RoamMapper<?> mapper;

	public RoamMappingConstraint(RoamEngine engine, MappingConstraint constraint) {
		super(engine, constraint);
		mapper = engine.getMapper(constraint.getMapping().getName());
	}
	
	@Override
	public void buildConstraints() {
		mapper.getMappings().values().parallelStream().forEach(context -> {
			ilpConstraints.put(context, buildConstraint(context));
		});
	}
	
	@Override
	public ILPConstraint<Integer> buildConstraint(final RoamMapping context) {
		Integer constTerm = buildConstantTerm(context);
		List<ILPTerm<Integer, Integer>> terms = buildVariableTerms(context);
		return new ILPConstraint<Integer>(constTerm, constraint.getExpression().getOperator(), terms);
	}

}
