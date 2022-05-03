package org.emoflon.gips.core.gt;

import java.util.List;

import org.emoflon.ibex.gt.api.GraphTransformationMatch;
import org.emoflon.ibex.gt.api.GraphTransformationPattern;
import org.emoflon.gips.core.GipsConstraint;
import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.ilp.ILPConstraint;
import org.emoflon.gips.core.ilp.ILPTerm;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint;

public abstract class GipsPatternConstraint<M extends GraphTransformationMatch<M, P>, P extends GraphTransformationPattern<M, P>>
		extends GipsConstraint<PatternConstraint, M, Integer> {

	final protected GraphTransformationPattern<M, P> pattern;

	public GipsPatternConstraint(GipsEngine engine, PatternConstraint constraint, final P pattern) {
		super(engine, constraint);
		this.pattern = pattern;
	}

	@Override
	public void buildConstraints() {
		pattern.findMatches().parallelStream().forEach(context -> {
			final ILPConstraint<Integer> candidate = buildConstraint(context);
			if (!candidate.lhsTerms().isEmpty()) {
				ilpConstraints.put(context, buildConstraint(context));
			}
		});
	}

	@Override
	public ILPConstraint<Integer> buildConstraint(final M context) {
		double constTerm = buildConstantTerm(context);
		List<ILPTerm<Integer, Double>> terms = buildVariableTerms(context);
		return new ILPConstraint<>(terms, constraint.getExpression().getOperator(), constTerm);
	}

}
