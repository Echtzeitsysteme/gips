package org.emoflon.gips.core;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.core.milp.model.BinaryVariable;
import org.emoflon.gips.core.milp.model.Constraint;
import org.emoflon.gips.core.milp.model.Term;
import org.emoflon.gips.core.milp.model.Variable;
import org.emoflon.gips.core.validation.GipsValidationEventType;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeConstraint;

public abstract class GipsTypeConstraint<ENGINE extends GipsEngine, CONTEXT extends EObject>
		extends GipsConstraint<ENGINE, TypeConstraint, CONTEXT> {

	final protected EClass type;

	public GipsTypeConstraint(ENGINE engine, TypeConstraint constraint) {
		super(engine, constraint);
		type = constraint.getType();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void buildConstraints(final boolean parallel) {
		if (parallel) {
			indexer.getObjectsOfType(type).parallelStream().forEach(context -> {
				final Constraint candidate = buildConstraint((CONTEXT) context);
				if (candidate != null) {
					milpConstraints.put((CONTEXT) context, candidate);
				}
			});
			if (constraint.isDepending()) {
				indexer.getObjectsOfType(type).parallelStream().forEach(context -> {
					final List<Constraint> constraints = buildAdditionalConstraints((CONTEXT) context);
					additionalMilpConstraints.put((CONTEXT) context, constraints);
				});
			}
		} else {
			indexer.getObjectsOfType(type).stream().forEach(context -> {
				final Constraint candidate = buildConstraint((CONTEXT) context);
				if (candidate != null) {
					milpConstraints.put((CONTEXT) context, candidate);
				}
			});
			if (constraint.isDepending()) {
				indexer.getObjectsOfType(type).stream().forEach(context -> {
					final List<Constraint> constraints = buildAdditionalConstraints((CONTEXT) context);
					additionalMilpConstraints.put((CONTEXT) context, constraints);
				});
			}
		}
	}

	@Override
	public Constraint buildConstraint(final CONTEXT context) {
		if (!isConstant && !(constraint.getExpression() instanceof RelationalExpression))
			throw new IllegalArgumentException(
					"Boolean values can not be transformed to (M)ILP relational constraints.");

		if (!isConstant) {
			RelationalOperator operator = ((RelationalExpression) constraint.getExpression()).getOperator();
			double constTerm = buildConstantRhs(context);
			List<Term> terms = buildVariableLhs(context);
			if (!terms.isEmpty())
				return new Constraint(terms, operator, constTerm);

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
				BinaryVariable var = (BinaryVariable) engine.getNonMappingVariable(context, symbolicVar.getName());

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
					double lhs = buildConstantLhs(context);
					double rhs = buildConstantRhs(context);
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
					boolean result = buildConstantExpression(context);
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
				BinaryVariable var = (BinaryVariable) engine.getNonMappingVariable(context, symbolicVar.getName());
				boolean result = false;

				if (constraint.getExpression() instanceof RelationalExpression relExpr
						&& !relExpr.isRequiresComparables()) {
					double lhs = buildConstantLhs(context);
					double rhs = buildConstantRhs(context);
					result = evaluateConstantConstraint(lhs, rhs, relExpr.getOperator());

				} else {
					result = buildConstantExpression(context);
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

	@SuppressWarnings("unchecked")
	@Override
	public void calcAdditionalVariables() {
		for (org.emoflon.gips.intermediate.GipsIntermediate.Variable variable : constraint.getHelperVariables()) {
			for (EObject context : indexer.getObjectsOfType(type)) {
				Variable<?> milpVar = buildVariable(variable, (CONTEXT) context);
				addAdditionalVariable((CONTEXT) context, variable, milpVar);
				engine.addNonMappingVariable((CONTEXT) context, variable, milpVar);
			}
		}
	}

	@Override
	public String buildVariableName(final org.emoflon.gips.intermediate.GipsIntermediate.Variable variable,
			final CONTEXT context) {
		return context + "->" + variable.getName() + "#" + variableIdx++;
	}

}
