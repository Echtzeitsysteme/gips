package org.emoflon.gips.core;

import java.util.List;

import org.emoflon.gips.core.ilp.ILPBinaryVariable;
import org.emoflon.gips.core.ilp.ILPConstraint;
import org.emoflon.gips.core.ilp.ILPTerm;
import org.emoflon.gips.core.ilp.ILPVariable;
import org.emoflon.gips.core.validation.GipsValidationEventType;
import org.emoflon.gips.intermediate.GipsIntermediate.GlobalConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.Variable;

public abstract class GipsGlobalConstraint<ENGINE extends GipsEngine>
		extends GipsConstraint<ENGINE, GlobalConstraint, GlobalConstraint> {

	public GipsGlobalConstraint(ENGINE engine, GlobalConstraint constraint) {
		super(engine, constraint);
	}

	@Override
	public void buildConstraints() {
		ilpConstraints.put(constraint, buildConstraint());
		if (constraint.isDepending()) {
			additionalIlpConstraints.put(constraint, buildAdditionalConstraints());
		}
	}

	protected ILPConstraint buildConstraint() {
		if (!isConstant && !(constraint.getExpression() instanceof RelationalExpression))
			throw new IllegalArgumentException("Boolean values can not be transformed to ilp relational constraints.");

		if (!isConstant) {
			RelationalOperator operator = ((RelationalExpression) constraint.getExpression()).getOperator();
			double constTerm = buildConstantRhs();
			List<ILPTerm> terms = buildVariableLhs();
			if (!terms.isEmpty())
				return new ILPConstraint(terms, ((RelationalExpression) constraint.getExpression()).getOperator(),
						constTerm);

			if (constraint.getReferencedBy() != null) {
				// If the terms list is empty, no suitable mapping candidates are present in the
				// model. Therefore, zero variables are created, which in turn, can only result
				// in a sum of zero. Hence, we will continue to evaluate the constraint with a
				// zero value, since this might be intended behavior.
				boolean result = evaluateConstantConstraint(0.0d, constTerm, operator);
				if (!result) {
					StringBuilder sb = new StringBuilder();
					sb.append(constTerm);
					sb.append(" ");
					sb.append(operator);
					sb.append(" 0.0");
					sb.append(" -> ");
					sb.append(result ? "true" : "false");
					validationLog.addValidatorEvent(GipsValidationEventType.CONST_CONSTRAINT_VIOLATION, this.getClass(),
							sb.toString());
				}
			} else {
				Variable symbolicVar = constraint.getSymbolicVariable();
				ILPBinaryVariable var = (ILPBinaryVariable) engine
						.getNonMappingVariable(buildVariableName(symbolicVar, null));

				// If the terms list is empty, no suitable mapping candidates are present in the
				// model. Therefore, zero variables are created, which in turn, can only result
				// in a sum of zero. Hence, we will continue to evaluate the constraint with a
				// zero value, since this might be intended behavior.
				boolean result = evaluateConstantConstraint(0.0d, constTerm, operator);
				if (result) {
					var.setUpperBound(1);
					var.setLowerBound(1);
				} else {
					var.setUpperBound(0);
					var.setLowerBound(0);
				}
			}

			// Remove possible additional variables
			additionalVariables.values().forEach(variable -> engine.removeNonMappingVariable(variable));
			additionalVariables.clear();

			return null;
		} else {
			if (constraint.getReferencedBy() != null) {
				if (constraint.getExpression() instanceof RelationalExpression relExpr
						&& relExpr.getOperator() != RelationalOperator.OBJECT_EQUAL
						&& relExpr.getOperator() != RelationalOperator.OBJECT_NOT_EQUAL) {
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
						validationLog.addValidatorEvent(GipsValidationEventType.CONST_CONSTRAINT_VIOLATION,
								this.getClass(), sb.toString());
					}
				} else {
					boolean result = buildConstantExpression();
					if (!result) {
						StringBuilder sb = new StringBuilder();
						sb.append(" -> ");
						sb.append(result ? "true" : "false");
						validationLog.addValidatorEvent(GipsValidationEventType.CONST_CONSTRAINT_VIOLATION,
								this.getClass(), sb.toString());
					}
				}
			} else {
				Variable symbolicVar = constraint.getSymbolicVariable();
				ILPBinaryVariable var = (ILPBinaryVariable) engine
						.getNonMappingVariable(buildVariableName(symbolicVar, null));
				boolean result = false;

				if (constraint.getExpression() instanceof RelationalExpression relExpr
						&& relExpr.getOperator() != RelationalOperator.OBJECT_EQUAL
						&& relExpr.getOperator() != RelationalOperator.OBJECT_NOT_EQUAL) {
					double lhs = buildConstantLhs();
					double rhs = buildConstantRhs();
					result = evaluateConstantConstraint(lhs, rhs, relExpr.getOperator());

				} else {
					result = buildConstantExpression();
				}

				if (result) {
					var.setUpperBound(1);
					var.setLowerBound(1);
				} else {
					var.setUpperBound(0);
					var.setLowerBound(0);
				}
			}

			return null;
		}

	}

	@Override
	public void calcAdditionalVariables() {
		for (Variable variable : constraint.getHelperVariables()) {
			ILPVariable<?> ilpVar = buildVariable(variable, null);
			additionalVariables.put(ilpVar.getName(), ilpVar);
			engine.addNonMappingVariable(ilpVar);
		}
	}

	@Override
	public String buildVariableName(final Variable variable, final GlobalConstraint context) {
		return "global->" + variable.getName();
	}

	abstract protected List<ILPConstraint> buildAdditionalConstraints();

	abstract protected double buildConstantRhs();

	abstract protected double buildConstantLhs();

	abstract protected boolean buildConstantExpression();

	abstract protected List<ILPTerm> buildVariableLhs();

	@Override
	protected List<ILPConstraint> buildAdditionalConstraints(GlobalConstraint context) {
		throw new UnsupportedOperationException("There is no specific context available for global constraints.");
	}

	@Override
	protected ILPConstraint buildConstraint(GlobalConstraint context) {
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
	protected List<ILPTerm> buildVariableLhs(GlobalConstraint context) {
		throw new UnsupportedOperationException("There is no specific context available for global constraints.");
	}

}
