package org.emoflon.gips.core;

import java.util.List;

import org.emoflon.gips.core.milp.model.BinaryVariable;
import org.emoflon.gips.core.milp.model.Constraint;
import org.emoflon.gips.core.milp.model.Term;
import org.emoflon.gips.core.milp.model.Variable;
import org.emoflon.gips.core.validation.GipsValidationEventType;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;

public abstract class GipsGlobalConstraint<ENGINE extends GipsEngine> extends
		GipsConstraint<ENGINE, org.emoflon.gips.intermediate.GipsIntermediate.Constraint, org.emoflon.gips.intermediate.GipsIntermediate.Constraint> {

	public GipsGlobalConstraint(ENGINE engine, org.emoflon.gips.intermediate.GipsIntermediate.Constraint constraint) {
		super(engine, constraint);
	}

	@Override
	public void buildConstraints() {
		milpConstraints.put(constraint, buildConstraint());
		if (constraint.isDepending()) {
			additionalMilpConstraints.put(constraint, buildAdditionalConstraints());
		}
	}

	protected Constraint buildConstraint() {
		if (!isConstant && !(constraint.getExpression() instanceof RelationalExpression))
			throw new IllegalArgumentException(
					"Boolean values can not be transformed to (M)ILP relational constraints.");

		if (!isConstant) {
			RelationalOperator operator = ((RelationalExpression) constraint.getExpression()).getOperator();
			double constTerm = buildConstantRhs();
			List<Term> terms = buildVariableLhs();
			if (!terms.isEmpty())
				return new Constraint(terms, ((RelationalExpression) constraint.getExpression()).getOperator(),
						constTerm);

			if (constraint.getReferencedBy() == null) {
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
				org.emoflon.gips.intermediate.GipsIntermediate.Variable symbolicVar = constraint.getSymbolicVariable();
				BinaryVariable var = (BinaryVariable) engine.getNonMappingVariable(constraint, symbolicVar.getName());

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
			additionalVariables.values().stream().flatMap(variables -> variables.values().stream())
					.forEach(variable -> engine.removeNonMappingVariable(variable));
			additionalVariables.clear();

			return null;
		} else {
			if (constraint.getReferencedBy() == null) {
				if (constraint.getExpression() instanceof RelationalExpression relExpr
						&& !relExpr.isRequiresComparables()) {
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
				org.emoflon.gips.intermediate.GipsIntermediate.Variable symbolicVar = constraint.getSymbolicVariable();
				BinaryVariable var = (BinaryVariable) engine.getNonMappingVariable(constraint, symbolicVar.getName());
				boolean result = false;

				if (constraint.getExpression() instanceof RelationalExpression relExpr
						&& !relExpr.isRequiresComparables()) {
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
		for (org.emoflon.gips.intermediate.GipsIntermediate.Variable variable : constraint.getHelperVariables()) {
			Variable<?> milpVar = buildVariable(variable, null);
			addAdditionalVariable(constraint, variable, milpVar);
			engine.addNonMappingVariable(constraint, variable, milpVar);
		}
	}

	@Override
	public String buildVariableName(final org.emoflon.gips.intermediate.GipsIntermediate.Variable variable,
			final org.emoflon.gips.intermediate.GipsIntermediate.Constraint context) {
		return "global->" + variable.getName() + "#" + variableIdx++;
	}

	abstract protected List<Constraint> buildAdditionalConstraints();

	abstract protected double buildConstantRhs();

	abstract protected double buildConstantLhs();

	abstract protected boolean buildConstantExpression();

	abstract protected List<Term> buildVariableLhs();

	@Override
	protected List<Constraint> buildAdditionalConstraints(
			org.emoflon.gips.intermediate.GipsIntermediate.Constraint context) {
		throw new UnsupportedOperationException("There is no specific context available for global constraints.");
	}

	@Override
	protected Constraint buildConstraint(org.emoflon.gips.intermediate.GipsIntermediate.Constraint context) {
		throw new UnsupportedOperationException("There is no specific context available for global constraints.");
	}

	@Override
	protected double buildConstantRhs(org.emoflon.gips.intermediate.GipsIntermediate.Constraint context) {
		throw new UnsupportedOperationException("There is no specific context available for global constraints.");
	}

	@Override
	protected double buildConstantLhs(org.emoflon.gips.intermediate.GipsIntermediate.Constraint context) {
		throw new UnsupportedOperationException("There is no specific context available for global constraints.");
	}

	@Override
	protected boolean buildConstantExpression(org.emoflon.gips.intermediate.GipsIntermediate.Constraint context) {
		throw new UnsupportedOperationException("There is no specific context available for global constraints.");
	}

	@Override
	protected List<Term> buildVariableLhs(org.emoflon.gips.intermediate.GipsIntermediate.Constraint context) {
		throw new UnsupportedOperationException("There is no specific context available for global constraints.");
	}

}
