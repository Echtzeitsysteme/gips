package org.emoflon.gips.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.emoflon.gips.core.ilp.ILPConstraint;
import org.emoflon.gips.core.ilp.ILPTerm;
import org.emoflon.gips.core.validation.GipsConstraintValidationLog;
import org.emoflon.gips.intermediate.GipsIntermediate.Constraint;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;

public abstract class GipsConstraint<ENGINE extends GipsEngine, CONSTR extends Constraint, CONTEXT extends Object, VARTYPE extends Number> {
	final protected ENGINE engine;
	final protected GipsConstraintValidationLog validationLog;
	final protected TypeIndexer indexer;
	final protected CONSTR constraint;
	final protected String name;
	final protected boolean isConstant;
	final protected Map<CONTEXT, ILPConstraint<VARTYPE>> ilpConstraints = Collections.synchronizedMap(new HashMap<>());
	final public static double EPSILON = 0.000001d;

	public GipsConstraint(final ENGINE engine, final CONSTR constraint) {
		this.engine = engine;
		validationLog = engine.getValidationLog();
		this.indexer = engine.getIndexer();
		this.constraint = constraint;
		this.name = constraint.getName();
		isConstant = constraint.isConstant();
	}

	public abstract void buildConstraints();

	public String getName() {
		return name;
	}

	public Collection<ILPConstraint<VARTYPE>> getConstraints() {
		return ilpConstraints.values();
	}

	protected abstract ILPConstraint<VARTYPE> buildConstraint(final CONTEXT context);

	protected abstract double buildConstantRhs(final CONTEXT context);

	protected abstract double buildConstantLhs(final CONTEXT context);

	protected abstract boolean buildConstantExpression(final CONTEXT context);

	protected abstract List<ILPTerm<VARTYPE, Double>> buildVariableLhs(final CONTEXT context);

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
