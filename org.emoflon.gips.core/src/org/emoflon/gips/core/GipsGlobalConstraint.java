package org.emoflon.gips.core;

import java.util.List;

import org.emoflon.gips.core.ilp.ILPConstraint;
import org.emoflon.gips.core.ilp.ILPTerm;
import org.emoflon.gips.core.validation.GipsValidationEventType;
import org.emoflon.gips.intermediate.GipsIntermediate.GlobalConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;

public abstract class GipsGlobalConstraint<ENGINE extends GipsEngine>
		extends GipsConstraint<ENGINE, GlobalConstraint, GlobalConstraint, Integer> {

	public GipsGlobalConstraint(ENGINE engine, GlobalConstraint constraint) {
		super(engine, constraint);
	}

	@Override
	public void buildConstraints() {
		ilpConstraints.put(constraint, buildConstraint());
		if (constraint.isDepending()) {
			dependingIlpConstraints.put(constraint, buildDependingConstraints());
		}
	}

	protected ILPConstraint<Integer> buildConstraint() {
		if (!isConstant) {
			double constTerm = buildConstantRhs();
			List<ILPTerm<Integer, Double>> terms = buildVariableLhs();
			return new ILPConstraint<>(terms, ((RelationalExpression) constraint.getExpression()).getOperator(),
					constTerm);
		} else {
			if (constraint.getExpression() instanceof RelationalExpression relExpr) {
				double lhs = buildConstantLhs();
				double rhs = buildConstantRhs();
				boolean result = evaluateConstantConstraint(lhs, rhs, relExpr.getOperator());
				if (!result) {
					StringBuilder sb = new StringBuilder();
					sb.append(lhs);
					sb.append(" ");
					sb.append(relExpr.getOperator());
					sb.append(" ");
					sb.append(rhs);
					sb.append(" -> ");
					sb.append(result ? "true" : "false");
					validationLog.addValidatorEvent(GipsValidationEventType.CONST_CONSTRAINT_VIOLATION, this.getClass(),
							sb.toString());
				}
			} else {
				boolean result = buildConstantExpression();
				if (!result) {
					StringBuilder sb = new StringBuilder();
					sb.append(" -> ");
					sb.append(result ? "true" : "false");
					validationLog.addValidatorEvent(GipsValidationEventType.CONST_CONSTRAINT_VIOLATION, this.getClass(),
							sb.toString());
				}
			}
			return null;
		}

	}

	abstract protected List<ILPConstraint<?>> buildDependingConstraints();

	abstract protected double buildConstantRhs();

	abstract protected double buildConstantLhs();

	abstract protected boolean buildConstantExpression();

	abstract protected List<ILPTerm<Integer, Double>> buildVariableLhs();

	@Override
	protected List<ILPConstraint<?>> buildDependingConstraints(GlobalConstraint context) {
		throw new UnsupportedOperationException("There is no specific context available for global constraints.");
	}

	@Override
	protected ILPConstraint<Integer> buildConstraint(GlobalConstraint context) {
		throw new UnsupportedOperationException("There is no specific context available for global constraints.");
	}

	@Override
	protected double buildConstantRhs(GlobalConstraint context) {
		throw new UnsupportedOperationException("There is no specific context available for global constraints.");
	}

	@Override
	protected double buildConstantLhs(GlobalConstraint context) {
		throw new UnsupportedOperationException("There is no specific context available for global constraints.");
	}

	@Override
	protected boolean buildConstantExpression(GlobalConstraint context) {
		throw new UnsupportedOperationException("There is no specific context available for global constraints.");
	}

	@Override
	protected List<ILPTerm<Integer, Double>> buildVariableLhs(GlobalConstraint context) {
		throw new UnsupportedOperationException("There is no specific context available for global constraints.");
	}

}
