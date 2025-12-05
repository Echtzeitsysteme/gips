package org.emoflon.gips.core;

import java.util.List;

import org.emoflon.gips.core.milp.model.BinaryVariable;
import org.emoflon.gips.core.milp.model.Constraint;
import org.emoflon.gips.core.milp.model.Term;
import org.emoflon.gips.core.milp.model.Variable;
import org.emoflon.gips.core.validation.GipsValidationEventType;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;
//import org.emoflon.gips.intermediate.GipsIntermediate.Variable;

public abstract class GipsMappingConstraint<ENGINE extends GipsEngine, CONTEXT extends GipsMapping>
		extends GipsConstraint<ENGINE, MappingConstraint, CONTEXT> {

	final protected GipsMapper<CONTEXT> mapper;

	@SuppressWarnings("unchecked")
	public GipsMappingConstraint(ENGINE engine, MappingConstraint constraint) {
		super(engine, constraint);
		mapper = (GipsMapper<CONTEXT>) engine.getMapper(constraint.getMapping().getName());
	}

	@Override
	public void buildConstraints(final boolean parallel) {
		if (parallel) {
			mapper.getMappings().values().parallelStream().forEach(context -> {
				final Constraint candidate = buildConstraint(context);
				if (candidate != null) {
					milpConstraints.put(context, candidate);
				}
			});
			if (constraint.isDepending()) {
				mapper.getMappings().values().parallelStream().forEach(context -> {
					final List<Constraint> constraints = buildAdditionalConstraints(context);
					additionalMilpConstraints.put(context, constraints);
				});

			}
		} else {
			mapper.getMappings().values().stream().forEach(context -> {
				final Constraint candidate = buildConstraint(context);
				if (candidate != null) {
					milpConstraints.put(context, candidate);
				}
			});
			if (constraint.isDepending()) {
				mapper.getMappings().values().stream().forEach(context -> {
					final List<Constraint> constraints = buildAdditionalConstraints(context);
					additionalMilpConstraints.put(context, constraints);
				});

			}
		}

	}

	@Override
	public Constraint buildConstraint(final CONTEXT context) {
		if (isConstant)
			throw new IllegalArgumentException("Mapping constraints must not be constant.");

		if (!(constraint.getExpression() instanceof RelationalExpression))
			throw new IllegalArgumentException(
					"Boolean values can not be transformed to (M)ILP relational constraints.");

		double constTerm = buildConstantRhs(context);
		List<Term> terms = buildVariableLhs(context);
		RelationalOperator operator = ((RelationalExpression) constraint.getExpression()).getOperator();
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
	}

	@Override
	public void calcAdditionalVariables() {
		for (org.emoflon.gips.intermediate.GipsIntermediate.Variable variable : constraint.getHelperVariables()) {
			for (CONTEXT context : mapper.getMappings().values()) {
				Variable<?> milpVar = buildVariable(variable, context);
				addAdditionalVariable(context, variable, milpVar);
				engine.addNonMappingVariable(context, variable, milpVar);
			}
		}
	}

	@Override
	public String buildVariableName(final org.emoflon.gips.intermediate.GipsIntermediate.Variable variable,
			final CONTEXT context) {
		return context.getName() + "->" + variable.getName() + "#" + variableIdx++;
	}

}
