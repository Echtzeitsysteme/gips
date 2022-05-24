package org.emoflon.gips.core;

import java.util.List;

import org.emoflon.gips.core.ilp.ILPBinaryVariable;
import org.emoflon.gips.core.ilp.ILPConstraint;
import org.emoflon.gips.core.ilp.ILPIntegerVariable;
import org.emoflon.gips.core.ilp.ILPRealVariable;
import org.emoflon.gips.core.ilp.ILPTerm;
import org.emoflon.gips.core.ilp.ILPVariable;
import org.emoflon.gips.core.validation.GipsValidationEventType;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.Variable;

public abstract class GipsMappingConstraint<ENGINE extends GipsEngine, CONTEXT extends GipsMapping>
		extends GipsConstraint<ENGINE, MappingConstraint, CONTEXT> {

	final protected GipsMapper<CONTEXT> mapper;

	@SuppressWarnings("unchecked")
	public GipsMappingConstraint(ENGINE engine, MappingConstraint constraint) {
		super(engine, constraint);
		mapper = (GipsMapper<CONTEXT>) engine.getMapper(constraint.getMapping().getName());
	}

	@Override
	public void buildConstraints() {
		mapper.getMappings().values().parallelStream().forEach(context -> {
			final ILPConstraint candidate = buildConstraint(context);
			if (candidate != null) {
				ilpConstraints.put(context, candidate);
			}
		});

		if (constraint.isDepending()) {
			mapper.getMappings().values().parallelStream().forEach(context -> {
				final List<ILPConstraint> constraints = buildAdditionalConstraints(context);
				additionalIlpConstraints.put(context, constraints);
			});

		}
	}

	@Override
	public ILPConstraint buildConstraint(final CONTEXT context) {
		if (isConstant)
			throw new IllegalArgumentException("Mapping constraints must not be constant.");

		if (!(constraint.getExpression() instanceof RelationalExpression))
			throw new IllegalArgumentException("Boolean values can not be transformed to ilp relational constraints.");

		double constTerm = buildConstantRhs(context);
		List<ILPTerm> terms = buildVariableLhs(context);
		RelationalOperator operator = ((RelationalExpression) constraint.getExpression()).getOperator();
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
	}

	@Override
	public void calcAdditionalVariables() {
		for (Variable variable : constraint.getHelperVariables()) {
			for (CONTEXT context : mapper.getMappings().values()) {
				ILPVariable<?> ilpVar = switch (variable.getType()) {
				case BINARY -> {
					yield new ILPBinaryVariable(context.getName() + "->" + variable.getName());
				}
				case INTEGER -> {
					yield new ILPIntegerVariable(context.getName() + "->" + variable.getName());
				}
				case REAL -> {
					yield new ILPRealVariable(context.getName() + "->" + variable.getName());
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
