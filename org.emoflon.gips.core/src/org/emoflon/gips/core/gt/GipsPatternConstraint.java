package org.emoflon.gips.core.gt;

import java.util.List;

import org.emoflon.gips.core.GipsConstraint;
import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.milp.model.BinaryVariable;
import org.emoflon.gips.core.milp.model.Constraint;
import org.emoflon.gips.core.milp.model.Term;
import org.emoflon.gips.core.milp.model.Variable;
import org.emoflon.gips.core.validation.GipsValidationEventType;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;
import org.emoflon.ibex.gt.engine.IBeXGTMatch;
import org.emoflon.ibex.gt.engine.IBeXGTPattern;

public abstract class GipsPatternConstraint<ENGINE extends GipsEngine, M extends IBeXGTMatch<M, P>, P extends IBeXGTPattern<P, M>>
		extends GipsConstraint<ENGINE, PatternConstraint, M> {

	final protected IBeXGTPattern<P, M> pattern;

	public GipsPatternConstraint(ENGINE engine, PatternConstraint constraint, final P pattern) {
		super(engine, constraint);
		this.pattern = pattern;
	}

	@Override
	public void buildConstraints() {
		// TODO: stream() -> parallelStream() once GIPS is based on the new shiny GT
		// language
		pattern.getMatches(false).stream().forEach(context -> {
			final Constraint candidate = buildConstraint(context);
			if (candidate != null) {
				ilpConstraints.put(context, buildConstraint(context));
			}
		});

		if (constraint.isDepending()) {
			// TODO: stream() -> parallelStream() once GIPS is based on the new shiny GT
			// language
			pattern.getMatches(false).stream().forEach(context -> {
				final List<Constraint> constraints = buildAdditionalConstraints(context);
				additionalIlpConstraints.put(context, constraints);
			});

		}
	}

	@Override
	public Constraint buildConstraint(final M context) {
		if (!isConstant && !(constraint.getExpression() instanceof RelationalExpression))
			throw new IllegalArgumentException("Boolean values can not be transformed to ilp relational constraints.");

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

	@Override
	public void calcAdditionalVariables() {
		for (org.emoflon.gips.intermediate.GipsIntermediate.Variable variable : constraint.getHelperVariables()) {
			for (M context : pattern.getMatches(false)) {
				Variable<?> ilpVar = buildVariable(variable, context);
				addAdditionalVariable(context, variable, ilpVar);
				engine.addNonMappingVariable(context, variable, ilpVar);
			}
		}
	}

	@Override
	public String buildVariableName(final org.emoflon.gips.intermediate.GipsIntermediate.Variable variable,
			final M context) {
		return context.getPattern().patternName + "->" + variable.getName() + "#" + variableIdx++;
	}

}
