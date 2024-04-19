package org.emoflon.gips.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.emoflon.gips.build.transformation.GipsConstraintUtils;
import org.emoflon.gips.core.ilp.ILPBinaryVariable;
import org.emoflon.gips.core.ilp.ILPConstraint;
import org.emoflon.gips.core.ilp.ILPIntegerVariable;
import org.emoflon.gips.core.ilp.ILPRealVariable;
import org.emoflon.gips.core.ilp.ILPTerm;
import org.emoflon.gips.core.ilp.ILPVariable;
import org.emoflon.gips.core.validation.GipsConstraintValidationLog;
import org.emoflon.gips.intermediate.GipsIntermediate.Constraint;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.Variable;

public abstract class GipsConstraint<ENGINE extends GipsEngine, CONSTR extends Constraint, CONTEXT extends Object> {
	final protected ENGINE engine;
	protected GipsConstraintValidationLog validationLog;
	final protected TypeIndexer indexer;
	final protected CONSTR constraint;
	final protected String name;
	final protected boolean isConstant;
	final protected Map<CONTEXT, ILPConstraint> ilpConstraints = Collections.synchronizedMap(new HashMap<>());
	final protected Map<CONTEXT, List<ILPConstraint>> additionalIlpConstraints = Collections
			.synchronizedMap(new HashMap<>());
	final protected Map<CONTEXT, Map<String, ILPVariable<?>>> additionalVariables = Collections
			.synchronizedMap(new HashMap<>());
	protected long variableIdx = 0;

	final public static double EPSILON = GipsConstraintUtils.EPSILON;

	public GipsConstraint(final ENGINE engine, final CONSTR constraint) {
		this.engine = engine;
		validationLog = engine.getValidationLog();
		this.indexer = engine.getIndexer();
		this.constraint = constraint;
		this.name = constraint.getName();
		isConstant = constraint.isConstant();
	}

	/**
	 * Clears all maps within the constraint builder.
	 */
	public void clear() {
		this.ilpConstraints.clear();
		this.additionalIlpConstraints.clear();
		this.additionalVariables.clear();
		this.validationLog = engine.getValidationLog();
	}

	public abstract void buildConstraints();

	public String getName() {
		return name;
	}

	public Collection<ILPVariable<?>> getAdditionalVariables() {
		return additionalVariables.values().stream().flatMap(vars -> vars.values().stream())
				.collect(Collectors.toSet());
	}

	public Collection<ILPConstraint> getConstraints() {
		return ilpConstraints.values();
	}

	public Collection<ILPConstraint> getAdditionalConstraints() {
		return additionalIlpConstraints.values().stream().flatMap(constraints -> constraints.stream())
				.collect(Collectors.toList());
	}

	protected abstract ILPConstraint buildConstraint(final CONTEXT context);

	protected abstract List<ILPConstraint> buildAdditionalConstraints(final CONTEXT context);

	public abstract void calcAdditionalVariables();

	public ILPVariable<?> addAdditionalVariable(final CONTEXT context, final Variable variableType,
			ILPVariable<?> variable) {
		Map<String, ILPVariable<?>> variables = additionalVariables.get(context);
		if (variables == null) {
			variables = Collections.synchronizedMap(new HashMap<>());
			additionalVariables.put(context, variables);
		}
		ILPVariable<?> var = variables.put(variableType.getName(), variable);

		if (var == null) {
			return variable;
		} else {
			return null;
		}
	}

	public ILPVariable<?> getAdditionalVariable(final CONTEXT context, final String variableTypeName) {
		Map<String, ILPVariable<?>> variables = additionalVariables.get(context);
		if (variables == null)
			return null;

		return variables.get(variableTypeName);
	}

	public ILPVariable<?> buildVariable(final Variable variable, final CONTEXT context) {
		return switch (variable.getType()) {
		case BINARY -> {
			ILPBinaryVariable var = new ILPBinaryVariable(buildVariableName(variable, context));
			var.setLowerBound((int) variable.getLowerBound());
			var.setUpperBound((int) variable.getUpperBound());
			yield var;
		}
		case INTEGER -> {
			ILPIntegerVariable var = new ILPIntegerVariable(buildVariableName(variable, context));
			var.setLowerBound((int) variable.getLowerBound());
			var.setUpperBound((int) variable.getUpperBound());
			yield var;
		}
		case REAL -> {
			ILPRealVariable var = new ILPRealVariable(buildVariableName(variable, context));
			var.setLowerBound(variable.getLowerBound());
			var.setUpperBound(variable.getUpperBound());
			yield var;
		}

		default -> {
			throw new IllegalArgumentException("Unknown ilp variable type: " + variable.getType());
		}
		};
	}

	public abstract String buildVariableName(final Variable variable, final CONTEXT context);

	protected abstract double buildConstantRhs(final CONTEXT context);

	protected abstract double buildConstantLhs(final CONTEXT context);

	protected abstract boolean buildConstantExpression(final CONTEXT context);

	protected abstract List<ILPTerm> buildVariableLhs(final CONTEXT context);

	protected boolean evaluateConstantConstraint(final double lhs, final double rhs,
			final RelationalOperator operator) {
		return switch (operator) {
		case EQUAL -> {
			yield Math.abs(lhs - rhs) < EPSILON;
		}
		case GREATER -> {
			yield !(Math.abs(lhs - rhs) < EPSILON || lhs - rhs < 0);
		}
		case GREATER_OR_EQUAL -> {
			yield Math.abs(lhs - rhs) < EPSILON || lhs - rhs > 0;
		}
		case LESS -> {
			yield !(Math.abs(lhs - rhs) < EPSILON || lhs - rhs > 0);
		}
		case LESS_OR_EQUAL -> {
			yield Math.abs(lhs - rhs) < EPSILON || lhs - rhs < 0;
		}
		case NOT_EQUAL -> {
			yield Math.abs(lhs - rhs) >= EPSILON;
		}
		default -> {
			throw new IllegalArgumentException("Unknown relational operator.");
		}

		};
	}

}
