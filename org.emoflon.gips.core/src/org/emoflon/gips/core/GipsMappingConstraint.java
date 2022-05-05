package org.emoflon.gips.core;

import java.util.List;

import org.emoflon.gips.core.ilp.ILPConstraint;
import org.emoflon.gips.core.ilp.ILPTerm;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint;

public abstract class GipsMappingConstraint<CONTEXT extends GipsMapping>
		extends GipsConstraint<MappingConstraint, CONTEXT, Integer> {

	final protected GipsMapper<CONTEXT> mapper;

	@SuppressWarnings("unchecked")
	public GipsMappingConstraint(GipsEngine engine, MappingConstraint constraint) {
		super(engine, constraint);
		mapper = (GipsMapper<CONTEXT>) engine.getMapper(constraint.getMapping().getName());
	}

	@Override
	public void buildConstraints() {
		mapper.getMappings().values().parallelStream().forEach(context -> {
//			final ILPConstraint<Integer> candidate = buildConstraint(context);
//			if (!candidate.lhsTerms().isEmpty()) {
			ilpConstraints.put(context, buildConstraint(context));
//			}
			// TODO: Throw an exception if the collection of LHS terms is empty (and the
			// presolver functionality is implemented.
		});
	}

	@Override
	public ILPConstraint<Integer> buildConstraint(final CONTEXT context) {
		double constTerm = buildConstantTerm(context);
		List<ILPTerm<Integer, Double>> terms = buildVariableTerms(context);
		return new ILPConstraint<>(terms, constraint.getExpression().getOperator(), constTerm);
	}

}
