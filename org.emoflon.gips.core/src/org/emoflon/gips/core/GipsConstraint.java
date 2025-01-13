package org.emoflon.gips.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.emoflon.gips.build.transformation.GipsConstraintUtils;
import org.emoflon.gips.core.milp.model.BinaryVariable;
import org.emoflon.gips.core.milp.model.Constraint;
import org.emoflon.gips.core.milp.model.IntegerVariable;
import org.emoflon.gips.core.milp.model.RealVariable;
import org.emoflon.gips.core.milp.model.Term;
import org.emoflon.gips.core.milp.model.Variable;
import org.emoflon.gips.core.validation.GipsConstraintValidationLog;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;

public abstract class GipsConstraint<ENGINE extends GipsEngine, CONSTR extends org.emoflon.gips.intermediate.GipsIntermediate.Constraint, CONTEXT extends Object> {
	final protected ENGINE engine;
	protected GipsConstraintValidationLog validationLog;
	final protected TypeIndexer indexer;
	final protected CONSTR constraint;
	final protected String name;
	final protected boolean isConstant;
	final protected Map<CONTEXT, Constraint> ilpConstraints = Collections.synchronizedMap(new HashMap<>());
	final protected Map<CONTEXT, List<Constraint>> additionalIlpConstraints = Collections
			.synchronizedMap(new HashMap<>());
	final protected Map<CONTEXT, Map<String, Variable<?>>> additionalVariables = Collections
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

	public Collection<Variable<?>> getAdditionalVariables() {
		return additionalVariables.values().stream().flatMap(vars -> vars.values().stream())
				.collect(Collectors.toSet());
	}

	public Collection<Constraint> getConstraints() {
		return ilpConstraints.values();
	}

	public Collection<Constraint> getAdditionalConstraints() {
		return additionalIlpConstraints.values().stream().flatMap(constraints -> constraints.stream())
				.collect(Collectors.toList());
	}

	protected abstract Constraint buildConstraint(final CONTEXT context);

	protected abstract List<Constraint> buildAdditionalConstraints(final CONTEXT context);

	public abstract void calcAdditionalVariables();

	public Variable<?> addAdditionalVariable(final CONTEXT context,
			final org.emoflon.gips.intermediate.GipsIntermediate.Variable variableType, Variable<?> variable) {
		Map<String, Variable<?>> variables = additionalVariables.get(context);
		if (variables == null) {
			variables = Collections.synchronizedMap(new HashMap<>());
			additionalVariables.put(context, variables);
		}
		Variable<?> var = variables.put(variableType.getName(), variable);

		if (var == null) {
			return variable;
		} else {
			return null;
		}
	}

	public Variable<?> getAdditionalVariable(final CONTEXT context, final String variableTypeName) {
		Map<String, Variable<?>> variables = additionalVariables.get(context);
		if (variables == null)
			return null;

		return variables.get(variableTypeName);
	}

	public Variable<?> buildVariable(final org.emoflon.gips.intermediate.GipsIntermediate.Variable variable,
			final CONTEXT context) {
		return switch (variable.getType()) {
		case BINARY -> {
			BinaryVariable var = new BinaryVariable(buildVariableName(variable, context));
			var.setLowerBound((int) variable.getLowerBound());
			var.setUpperBound((int) variable.getUpperBound());
			yield var;
		}
		case INTEGER -> {
			IntegerVariable var = new IntegerVariable(buildVariableName(variable, context));
			var.setLowerBound((int) variable.getLowerBound());
			var.setUpperBound((int) variable.getUpperBound());
			yield var;
		}
		case REAL -> {
			RealVariable var = new RealVariable(buildVariableName(variable, context));
			var.setLowerBound(variable.getLowerBound());
			var.setUpperBound(variable.getUpperBound());
			yield var;
		}

		default -> {
			throw new IllegalArgumentException("Unknown ilp variable type: " + variable.getType());
		}
		};
	}

	public abstract String buildVariableName(final org.emoflon.gips.intermediate.GipsIntermediate.Variable variable,
			final CONTEXT context);

	protected abstract double buildConstantRhs(final CONTEXT context);

	protected abstract double buildConstantLhs(final CONTEXT context);

	protected abstract boolean buildConstantExpression(final CONTEXT context);

	protected abstract List<Term> buildVariableLhs(final CONTEXT context);

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
