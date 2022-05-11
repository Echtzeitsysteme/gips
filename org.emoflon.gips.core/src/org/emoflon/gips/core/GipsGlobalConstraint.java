package org.emoflon.gips.core;

import java.util.List;

import org.emoflon.gips.core.ilp.ILPConstraint;
import org.emoflon.gips.core.ilp.ILPTerm;
import org.emoflon.gips.intermediate.GipsIntermediate.GlobalConstraint;

public abstract class GipsGlobalConstraint extends GipsConstraint<GlobalConstraint, GlobalConstraint, Integer> {

	public GipsGlobalConstraint(GipsEngine engine, GlobalConstraint constraint) {
		super(engine, constraint);
	}

	@Override
	public void buildConstraints() {
		ilpConstraints.put(constraint, buildConstraint());
	}

	protected ILPConstraint<Integer> buildConstraint() {
		double constTerm = buildConstantTerm();
		List<ILPTerm<Integer, Double>> terms = buildVariableTerms();
		return new ILPConstraint<>(terms, constraint.getExpression().getOperator(), constTerm);
	}

	abstract protected double buildConstantTerm();

	abstract protected List<ILPTerm<Integer, Double>> buildVariableTerms();

	@Override
	protected ILPConstraint<Integer> buildConstraint(GlobalConstraint context) {
		throw new UnsupportedOperationException("There is no specific context available for global constraints.");
	}

	@Override
	protected double buildConstantTerm(GlobalConstraint context) {
		throw new UnsupportedOperationException("There is no specific context available for global constraints.");
	}

	@Override
	protected List<ILPTerm<Integer, Double>> buildVariableTerms(GlobalConstraint context) {
		throw new UnsupportedOperationException("There is no specific context available for global constraints.");
	}

}
