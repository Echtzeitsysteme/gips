package org.emoflon.gips.core;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.core.ilp.ILPBinaryVariable;
import org.emoflon.gips.core.ilp.ILPConstraint;
import org.emoflon.gips.core.ilp.ILPIntegerVariable;
import org.emoflon.gips.core.ilp.ILPRealVariable;
import org.emoflon.gips.core.ilp.ILPTerm;
import org.emoflon.gips.core.ilp.ILPVariable;
import org.emoflon.gips.core.validation.GipsValidationEventType;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.Variable;

public abstract class GipsTypeConstraint<ENGINE extends GipsEngine, CONTEXT extends EObject>
		extends GipsConstraint<ENGINE, TypeConstraint, CONTEXT> {

	final protected EClass type;

	public GipsTypeConstraint(ENGINE engine, TypeConstraint constraint) {
		super(engine, constraint);
		type = constraint.getModelType().getType();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void buildConstraints() {
		indexer.getObjectsOfType(type).parallelStream().forEach(context -> {
			final ILPConstraint candidate = buildConstraint((CONTEXT) context);
			if (candidate != null) {
				ilpConstraints.put((CONTEXT) context, candidate);
			}
		});

		if (constraint.isDepending()) {
			indexer.getObjectsOfType(type).parallelStream().forEach(context -> {
				final List<ILPConstraint> constraints = buildAdditionalConstraints((CONTEXT) context);
				additionalIlpConstraints.put((CONTEXT) context, constraints);
			});

		}
	}

	@Override
	public ILPConstraint buildConstraint(final CONTEXT context) {
		if (!isConstant && !(constraint.getExpression() instanceof RelationalExpression))
			throw new IllegalArgumentException("Boolean values can not be transformed to ilp relational constraints.");

		if (!isConstant) {
			RelationalOperator operator = ((RelationalExpression) constraint.getExpression()).getOperator();
			double constTerm = buildConstantRhs(context);
			List<ILPTerm> terms = buildVariableLhs(context);
			if (!terms.isEmpty())
				return new ILPConstraint(terms, operator, constTerm);

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
			// Remove possible additional variables
			additionalVariables.values().forEach(variable -> engine.removeNonMappingVariable(variable));
			additionalVariables.clear();

			return null;
		} else {
			if (constraint.getExpression() instanceof RelationalExpression relExpr) {
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
					validationLog.addValidatorEvent(GipsValidationEventType.CONST_CONSTRAINT_VIOLATION, this.getClass(),
							sb.toString());
				}
			} else {
				boolean result = buildConstantExpression(context);
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

	@Override
	public void calcAdditionalVariables() {
		for (Variable variable : constraint.getHelperVariables()) {
			for (EObject context : indexer.getObjectsOfType(type)) {
				ILPVariable<?> ilpVar = switch (variable.getType()) {
				case BINARY -> {
					yield new ILPBinaryVariable(context + "->" + variable.getName());
				}
				case INTEGER -> {
					yield new ILPIntegerVariable(context + "->" + variable.getName());
				}
				case REAL -> {
					yield new ILPRealVariable(context + "->" + variable.getName());
				}
				default -> {
					throw new IllegalArgumentException("Unknown ilp variable type: " + variable.getType());
				}

				};
				additionalVariables.put(ilpVar.getName(), ilpVar);
				engine.addNonMappingVariable(ilpVar);
			}

		}
	}

}
