package org.emoflon.gips.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
	final protected Map<CONTEXT, Constraint> milpConstraints = Collections.synchronizedMap(new HashMap<>());
	final protected Map<CONTEXT, List<Constraint>> additionalMilpConstraints = Collections
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
	 * This method can be used to remove useless constraints from this
	 * `GipsConstraint`. A MILP constraint is considered useless if: (1) it is
	 * redundant with other MILP constraints of this GIPSL constraint or (2) if it
	 * is trivial (e.g., 1 * x <= 1 for a variable x with an upper bound of 1).
	 */
	public RemovedConstraintsStats removeUselessConstraints() {
		// Counters for the number of different constraints
		int constraintsOriginal = 0;
		int duplicatesRemoved = 0;
		int trivialConstraintsRemoved = 0;

		// Check every normal constraint
		final Set<Constraint> contained = new HashSet<Constraint>();
		final Set<CONTEXT> removalCandidates = new HashSet<CONTEXT>();

		for (final CONTEXT key : milpConstraints.keySet()) {
			final Constraint candidate = milpConstraints.get(key);
			constraintsOriginal++;
			// Check for duplicates
			if (!contained.add(candidate)) {
				removalCandidates.add(key);
				duplicatesRemoved++;
			} else {
				// Check for trivial constraints
				if (isConstraintTrivial(candidate)) {
					removalCandidates.add(key);
					trivialConstraintsRemoved++;
				}
			}
		}

		// Check every additional constraints
		final Set<Constraint> additionalContained = new HashSet<Constraint>();
		final Map<CONTEXT, List<Integer>> additionalRemovalCandidates = new HashMap<CONTEXT, List<Integer>>();

		for (final CONTEXT key : additionalMilpConstraints.keySet()) {
			final List<Constraint> additionalConstraints = additionalMilpConstraints.get(key);
			for (int i = 0; i < additionalConstraints.size(); i++) {
				final Constraint candidate = additionalConstraints.get(i);
				constraintsOriginal++;
				// Check for duplicates
				if (!additionalContained.add(candidate)) {
					if (!additionalRemovalCandidates.containsKey(key)) {
						additionalRemovalCandidates.put(key, new ArrayList<Integer>());
					}
					additionalRemovalCandidates.get(key).add(i);
					duplicatesRemoved++;
				} else {
					// Check for trivial constraints
					if (isConstraintTrivial(candidate)) {
						removalCandidates.add(key);
						trivialConstraintsRemoved++;
					}
				}
			}
		}

		// Remove all normal candidates
		removalCandidates.forEach(r -> milpConstraints.remove(r));

		// Remove all additional candidates
		additionalRemovalCandidates.forEach((k, constraints) -> {
			constraints.forEach(index -> {
				additionalMilpConstraints.get(k).remove(index.intValue());
			});
		});
		return new RemovedConstraintsStats(constraintsOriginal, duplicatesRemoved, trivialConstraintsRemoved);
	}

	/**
	 * Checks if a given constraint is trivial (e.g., 1 * x <= 1 for a variable
	 * thats upper bound is 1 anyway).
	 * 
	 * @param constraint Constraint to check triviality for.
	 * @return True if the given constraint is trivial.
	 */
	private boolean isConstraintTrivial(final Constraint constraint) {
		// Preconditions: constraint must not be null and there must only be one
		// variable term
		if (constraint != null // null check
				&& constraint.lhsTerms().size() == 1 // only one variable
		) {
			// Case: n * x <= n (for a variable with an upper bound of 1 and n >= 0)
			// This also contains the case of 1 * x <= 1 (with x' upper bound of 1)
			if (constraint.lhsTerms().get(0).variable().getUpperBound().intValue() == 1 // variable's upper
																						// bound is 1
					&& constraint.rhsConstantTerm() >= 0 // RHS is >= 0
					&& constraint.lhsTerms().get(0).weight() <= constraint.rhsConstantTerm() // variable's weight is
																								// equal to or less than
																								// the RHS' constant
																								// term
					&& constraint.operator() == RelationalOperator.LESS_OR_EQUAL // operator is <=
			) {
				return true;
			}

			// Case: n * x <= x_ub * n
			// (for a variable with an upper bound of x_ub and n >= 0)
			if (constraint.rhsConstantTerm() >= 0 // RHS is >= 0
					&& constraint.lhsTerms().get(0).weight() * constraint.lhsTerms().get(0).variable().getUpperBound()
							.doubleValue() <= constraint.rhsConstantTerm() // n * x_ub <= RHS
					&& constraint.operator() == RelationalOperator.LESS_OR_EQUAL // operator is <=
			) {
				return true;
			}

			// Case: n * x >= 0 (for a variable with a lower bound of 0)
			if (constraint.lhsTerms().get(0).variable().getLowerBound().intValue() == 0 // variable's lower
					// bound is 0
					&& constraint.rhsConstantTerm() == 0 // RHS is constant 0
					&& constraint.lhsTerms().get(0).weight() >= 0 // variable's weight is non-negative
					&& constraint.operator() == RelationalOperator.GREATER_OR_EQUAL // operator is >=
			) {
				return true;
			}
		}

		// If no previous condition was triggered, we consider the constraint to be
		// non-trivial.
		return false;
	}

	/**
	 * Clears all maps within the constraint builder.
	 */
	public void clear() {
		this.milpConstraints.clear();
		this.additionalMilpConstraints.clear();
		this.additionalVariables.clear();
		this.validationLog = engine.getValidationLog();
	}

	public abstract void buildConstraints(final boolean parallel);

	public String getName() {
		return name;
	}

	public CONSTR getIntermediateConstraint() {
		return constraint;
	}

	public Collection<Variable<?>> getAdditionalVariables() {
		return additionalVariables.values().stream().flatMap(vars -> vars.values().stream())
				.collect(Collectors.toSet());
	}

	public Collection<Constraint> getConstraints() {
		return milpConstraints.values();
	}

	public Collection<Constraint> getAdditionalConstraints() {
		return additionalMilpConstraints.values().stream().flatMap(constraints -> constraints.stream())
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
			throw new IllegalArgumentException("Unknown (M)ILP variable type: " + variable.getType());
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

	/**
	 * Record to store constraint removal statistics (number of constraints).
	 */
	public record RemovedConstraintsStats(int original, int duplicates, int trivial) {
	}

}
