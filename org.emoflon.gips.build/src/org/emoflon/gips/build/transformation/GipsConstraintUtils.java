package org.emoflon.gips.build.transformation;

import java.util.Collection;
import java.util.LinkedList;

import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticBinaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticBinaryOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Constraint;
import org.emoflon.gips.intermediate.GipsIntermediate.DoubleLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateFactory;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.RuleConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.Variable;
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference;
import org.emoflon.gips.intermediate.GipsIntermediate.VariableType;

public final class GipsConstraintUtils {
	final public static double EPSILON = 0.0001d;
	final public static double INF = 10000;

	/*
	 * This function takes a constraint and normalizes the contained generic
	 * relational expression modeling a linear equality, such that only relational
	 * operators are used, which are supported by ILP solvers (>=, <=, ==).
	 * 
	 * This function assumes that the expression in constraint is a relational
	 * expression, where the lhs is a variable arithmetic expression and the rhs is
	 * a constant expression. E.g.: f(x) <= c
	 * 
	 * This function might return a set of substitute constraints that have to be
	 * satisfied instead of modifying the expression itself. The expression and the
	 * corresponding constraint must be discarded if the return value is not an
	 * empty collection.
	 */
	static protected Collection<Constraint> normalizeOperator(final GipsTransformationData data,
			final GipsIntermediateFactory factory, final Constraint constraint, boolean isNegated) {
		RelationalExpression expression = (RelationalExpression) constraint.getExpression();
		Collection<Constraint> constraints = new LinkedList<>();
		switch (expression.getOperator()) {
		case EQUAL:
			if (isNegated) {
				expression.setOperator(RelationalOperator.NOT_EQUAL);
				return normalizeOperator(data, factory, constraint, false);
			}
			return constraints;
		case GREATER:
			if (isNegated) {
				expression.setOperator(RelationalOperator.LESS_OR_EQUAL);
			} else {
				// Transform according to: f(x) > c <=> f(x) >= c + eps,
				// with eps > 0 and eps << 1.
				expression.setOperator(RelationalOperator.GREATER_OR_EQUAL);
				ArithmeticBinaryExpression sum = factory.createArithmeticBinaryExpression();
				sum.setOperator(ArithmeticBinaryOperator.ADD);
				sum.setLhs((ArithmeticExpression) expression.getRhs());
				sum.setRhs(createEpsilon(factory, true));
				expression.setRhs(sum);
			}
			return constraints;
		case GREATER_OR_EQUAL:
			if (isNegated) {
				expression.setOperator(RelationalOperator.LESS);
				return normalizeOperator(data, factory, constraint, false);
			}
			return constraints;
		case LESS:
			if (isNegated) {
				expression.setOperator(RelationalOperator.GREATER_OR_EQUAL);
			} else {
				// Transform according to: f(x) < c <=> f(x) <= c - eps,
				// with eps > 0 and eps << 1.
				expression.setOperator(RelationalOperator.LESS_OR_EQUAL);
				ArithmeticBinaryExpression sum = factory.createArithmeticBinaryExpression();
				sum.setOperator(ArithmeticBinaryOperator.SUBTRACT);
				sum.setLhs((ArithmeticExpression) expression.getRhs());
				sum.setRhs(createEpsilon(factory, true));
				expression.setRhs(sum);
			}
			return constraints;
		case LESS_OR_EQUAL:
			if (isNegated) {
				expression.setOperator(RelationalOperator.GREATER);
				return normalizeOperator(data, factory, constraint, false);
			}
			return constraints;
		case NOT_EQUAL:
			if (isNegated) {
				expression.setOperator(RelationalOperator.EQUAL);
			} else {
				// Transform according to:
				// f(x) != c <=> (I) & (II) & (III) & (IV) & (V),
				// with eps << 1, M >> 0, eps, M in R^+\{0} and s',s'' in {0, 1}.
				//
				// (I) : f(x) - Ms' >= c + eps - M
				// (II) : f(x) - Ms' <= c
				// (III): f(x) + Ms'' >= c
				// (IV) : f(x) + Ms'' <= M + c - eps
				// (V) : s' + s'' == 1

				// Creating s' and s''
				Variable s1 = createBinaryVariable(data, factory, constraint.getName() + "_NEQslack1");
				Variable s2 = createBinaryVariable(data, factory, constraint.getName() + "_NEQslack2");

				// (I) f(x) - Ms' >= c + eps - M
				Constraint c1 = createSubstituteConstraint(factory, data, constraint, "_NEQ", 0);
				// (I) - LHS: f(x) - Ms'
				ArithmeticBinaryExpression lhs = factory.createArithmeticBinaryExpression(); // f(x) + (-Ms')
				lhs.setOperator(ArithmeticBinaryOperator.ADD);
				lhs.setLhs(
						GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getLhs())); // f(x)
				ArithmeticBinaryExpression term = factory.createArithmeticBinaryExpression(); // -Ms'
				term.setOperator(ArithmeticBinaryOperator.MULTIPLY);
				term.setLhs(createInf(factory, false)); // -M
				term.setRhs(createVariableReference(factory, s1)); // s'
				lhs.setRhs(term);
				// (I) - RHS: c + eps - M
				ArithmeticBinaryExpression rhs = factory.createArithmeticBinaryExpression(); // c + eps - M
				rhs.setOperator(ArithmeticBinaryOperator.ADD);
				rhs.setLhs(
						GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getRhs())); // c
				ArithmeticBinaryExpression rhs_rhs = factory.createArithmeticBinaryExpression(); // eps - M
				rhs_rhs.setOperator(ArithmeticBinaryOperator.SUBTRACT);
				rhs_rhs.setLhs(createEpsilon(factory, true)); // eps
				rhs_rhs.setRhs(createInf(factory, true)); // M
				rhs.setRhs(rhs_rhs);
				// (I) - Combine: f(x) - Ms' >= c + eps - M
				RelationalExpression relation = factory.createRelationalExpression();
				relation.setOperator(RelationalOperator.GREATER_OR_EQUAL); // >=
				relation.setLhs(lhs);
				relation.setRhs(rhs);
				c1.setExpression(relation);
				c1.setDepending(false);
				c1.setNegated(false);
				c1.setConstant(false);
				c1.getHelperVariables().add(s1);
				constraints.add(c1);

				// (II) f(x) - Ms' <= c
				Constraint c2 = createSubstituteConstraint(factory, data, constraint, "_NEQ", 1);
				// (II) - LHS: f(x) - Ms'
				lhs = factory.createArithmeticBinaryExpression(); // f(x) + (-Ms')
				lhs.setOperator(ArithmeticBinaryOperator.ADD);
				lhs.setLhs(
						GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getLhs())); // f(x)
				term = factory.createArithmeticBinaryExpression(); // -Ms'
				term.setOperator(ArithmeticBinaryOperator.MULTIPLY);
				term.setLhs(createInf(factory, false)); // -M
				term.setRhs(createVariableReference(factory, s1)); // s'
				lhs.setRhs(term);
				// (II) - RHS: c
				ArithmeticExpression shortRHS = GipsArithmeticTransformer.cloneExpression(factory,
						(ArithmeticExpression) expression.getRhs()); // c
				// (II) - Combine: f(x) - Ms' <= c
				relation = factory.createRelationalExpression();
				relation.setOperator(RelationalOperator.LESS_OR_EQUAL); // <=
				relation.setLhs(lhs);
				relation.setRhs(shortRHS);
				c2.setExpression(relation);
				c2.setDepending(false);
				c2.setNegated(false);
				c2.setConstant(false);
				c2.getHelperVariables().add(s1);
				constraints.add(c2);

				// (III) f(x) + Ms'' >= c
				Constraint c3 = createSubstituteConstraint(factory, data, constraint, "_NEQ", 2);
				// (III) - LHS: f(x) + Ms''
				lhs = factory.createArithmeticBinaryExpression();
				lhs.setOperator(ArithmeticBinaryOperator.ADD);
				lhs.setLhs(
						GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getLhs())); // f(x)
				term = factory.createArithmeticBinaryExpression(); // Ms''
				term.setOperator(ArithmeticBinaryOperator.MULTIPLY);
				term.setLhs(createInf(factory, true)); // M
				term.setRhs(createVariableReference(factory, s2)); // s''
				lhs.setRhs(term);
				// (III) - RHS: c
				shortRHS = GipsArithmeticTransformer.cloneExpression(factory,
						(ArithmeticExpression) expression.getRhs()); // c
				// (III) - Combine: f(x) + Ms'' >= c
				relation = factory.createRelationalExpression();
				relation.setOperator(RelationalOperator.GREATER_OR_EQUAL);
				relation.setLhs(lhs);
				relation.setRhs(shortRHS);
				c3.setExpression(relation);
				c3.setDepending(false);
				c3.setNegated(false);
				c3.setConstant(false);
				c3.getHelperVariables().add(s2);
				constraints.add(c3);

				// (IV) f(x) + Ms'' <= M + c - eps
				Constraint c4 = createSubstituteConstraint(factory, data, constraint, "_NEQ", 3);
				// (IV) - LHS: f(x) + Ms''
				lhs = factory.createArithmeticBinaryExpression();
				lhs.setOperator(ArithmeticBinaryOperator.ADD);
				lhs.setLhs(
						GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getLhs())); // f(x)
				term = factory.createArithmeticBinaryExpression(); // Ms''
				term.setOperator(ArithmeticBinaryOperator.MULTIPLY);
				term.setLhs(createInf(factory, true)); // M
				term.setRhs(createVariableReference(factory, s2)); // s''
				lhs.setRhs(term);
				// (IV) - RHS: M + c - eps
				rhs = factory.createArithmeticBinaryExpression(); // M + c - eps
				rhs.setOperator(ArithmeticBinaryOperator.ADD);
				rhs.setLhs(createInf(factory, true)); // M
				rhs_rhs = factory.createArithmeticBinaryExpression(); // c - eps
				rhs_rhs.setOperator(ArithmeticBinaryOperator.SUBTRACT);
				rhs_rhs.setLhs(
						GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getRhs())); // c
				rhs_rhs.setRhs(createEpsilon(factory, true)); // eps
				rhs.setRhs(rhs_rhs);
				// (IV) - Combine: f(x) + Ms'' <= M + c - eps
				relation = factory.createRelationalExpression();
				relation.setOperator(RelationalOperator.LESS_OR_EQUAL);
				relation.setLhs(lhs);
				relation.setRhs(rhs);
				c4.setExpression(relation);
				c4.setDepending(false);
				c4.setNegated(false);
				c4.setConstant(false);
				c4.getHelperVariables().add(s2);
				constraints.add(c4);

				// (V) s' + s'' == 1
				Constraint c5 = createSubstituteConstraint(factory, data, constraint, "_NEQ", 4);
				// (V) - LHS: s' + s''
				lhs = factory.createArithmeticBinaryExpression();
				lhs.setOperator(ArithmeticBinaryOperator.ADD);
				lhs.setLhs(createVariableReference(factory, s1));
				lhs.setRhs(createVariableReference(factory, s2));
				// (V) - RHS: 1
				shortRHS = createConstant(factory, 1.0);
				// (IV) - Combine: s' + s'' == 1
				relation = factory.createRelationalExpression();
				relation.setOperator(RelationalOperator.EQUAL);
				relation.setLhs(lhs);
				relation.setRhs(shortRHS);
				c5.setExpression(relation);
				c5.setDepending(false);
				c5.setNegated(false);
				c5.setConstant(false);
				c5.getHelperVariables().add(s1);
				c5.getHelperVariables().add(s2);
				constraints.add(c5);
			}
			return constraints;
		default:
			throw new UnsupportedOperationException(
					"Linear equalities may not contain non-numeric values aka. complex objects.");
		}
	}

	/*
	 * This function takes a constraint, inserts a symbolic variable that reflects
	 * the outcome of the constraint and normalizes the contained generic relational
	 * expression modeling a linear equality, such that only relational operators
	 * are used, which are supported by ILP solvers (>=, <=, ==).
	 * 
	 * This function assumes that the expression in constraint is a relational
	 * expression, where the lhs is a variable arithmetic expression and the rhs is
	 * a constant expression. E.g.: f(x) <= c
	 * 
	 * This function might return a set of substitute constraints that have to be
	 * satisfied instead of modifying the expression itself. The expression and the
	 * corresponding constraint must be discarded if the return value is not an
	 * empty collection.
	 */
	static protected Collection<Constraint> normalizeOperator(final GipsTransformationData data,
			final GipsIntermediateFactory factory, final Constraint constraint, Variable symbolicVariable) {
		RelationalExpression expression = (RelationalExpression) constraint.getExpression();
		Collection<Constraint> constraints = new LinkedList<>();
		switch (expression.getOperator()) {
		case EQUAL: {
			// Transform according to:
			// f(x) == c <=> s == 1, with s,s',s'' in {0, 1} and equalities (I) through
			// (VII).
			// As usual eps << 1, M >> 0, eps, M in R^+\{0}.
			//
			// (I) : f(x) + Ms' >= c + eps
			// (II) : f(x) + Ms' <= M + c
			// (III): f(x) - Ms'' >= c - M
			// (IV) : f(x) - Ms'' <= c - eps
			// (V) : s - s' <= 0
			// (VI) : s - s'' <= 0
			// (VII) : s' + s'' - s <= 1

			// Creating s' and s''
			final Variable s1 = createBinaryVariable(data, factory, constraint.getName() + "_EQslack1"); // s'
			final Variable s2 = createBinaryVariable(data, factory, constraint.getName() + "_EQslack2"); // s''

			// (I) f(x) + Ms' >= c + eps
			Constraint c1 = createSubstituteConstraint(factory, data, constraint, "_EQ", 0);
			// (I) - LHS: f(x) + Ms'
			ArithmeticBinaryExpression lhs = factory.createArithmeticBinaryExpression(); // f(x) + Ms'
			lhs.setOperator(ArithmeticBinaryOperator.ADD);
			lhs.setLhs(GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getLhs())); // f(x)
			ArithmeticBinaryExpression term = factory.createArithmeticBinaryExpression(); // Ms'
			term.setOperator(ArithmeticBinaryOperator.MULTIPLY);
			term.setLhs(createInf(factory, true)); // M
			term.setRhs(createVariableReference(factory, s1)); // s'
			lhs.setRhs(term);
			// (I) - RHS: c + eps
			ArithmeticBinaryExpression rhs = factory.createArithmeticBinaryExpression(); // c + eps
			rhs.setOperator(ArithmeticBinaryOperator.ADD);
			rhs.setLhs(GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getRhs())); // c
			rhs.setRhs(createEpsilon(factory, true)); // eps
			// (I) - Combine: f(x) + Ms' >= c + eps
			RelationalExpression relation = factory.createRelationalExpression();
			relation.setOperator(RelationalOperator.GREATER_OR_EQUAL); // >=
			relation.setLhs(lhs);
			relation.setRhs(rhs);
			c1.setExpression(relation);
			c1.setDepending(false);
			c1.setNegated(false);
			c1.setConstant(false);
			c1.getHelperVariables().add(s1);
			constraints.add(c1);

			// (II) f(x) + Ms' <= M + c
			Constraint c2 = createSubstituteConstraint(factory, data, constraint, "_EQ", 1);
			// (II) - LHS: f(x) + Ms'
			lhs = factory.createArithmeticBinaryExpression(); // f(x) + Ms'
			lhs.setOperator(ArithmeticBinaryOperator.ADD);
			lhs.setLhs(GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getLhs())); // f(x)
			term = factory.createArithmeticBinaryExpression(); // Ms'
			term.setOperator(ArithmeticBinaryOperator.MULTIPLY);
			term.setLhs(createInf(factory, true)); // M
			term.setRhs(createVariableReference(factory, s1)); // s'
			lhs.setRhs(term);
			// (II) - RHS: M + c
			rhs = factory.createArithmeticBinaryExpression(); // M + c
			rhs.setOperator(ArithmeticBinaryOperator.ADD);
			rhs.setLhs(createInf(factory, true)); // M
			rhs.setRhs(GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getRhs())); // c
			// (II) - Combine: f(x) + Ms' <= M + c
			relation = factory.createRelationalExpression();
			relation.setOperator(RelationalOperator.LESS_OR_EQUAL); // <=
			relation.setLhs(lhs);
			relation.setRhs(rhs);
			c2.setExpression(relation);
			c2.setDepending(false);
			c2.setNegated(false);
			c2.setConstant(false);
			c2.getHelperVariables().add(s1);
			constraints.add(c2);

			// (III) f(x) - Ms'' >= c - M
			Constraint c3 = createSubstituteConstraint(factory, data, constraint, "_EQ", 2);
			// (III) - LHS: f(x) - Ms''
			lhs = factory.createArithmeticBinaryExpression();
			lhs.setOperator(ArithmeticBinaryOperator.ADD);
			lhs.setLhs(GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getLhs())); // f(x)
			term = factory.createArithmeticBinaryExpression(); // -Ms''
			term.setOperator(ArithmeticBinaryOperator.MULTIPLY);
			term.setLhs(createInf(factory, false)); // -M
			term.setRhs(createVariableReference(factory, s2)); // s''
			lhs.setRhs(term);
			// (III) - RHS: c - M
			rhs = factory.createArithmeticBinaryExpression(); // c - M
			rhs.setOperator(ArithmeticBinaryOperator.SUBTRACT);
			rhs.setLhs(GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getRhs())); // c
			rhs.setRhs(createInf(factory, true)); // M
			// (III) - Combine: f(x) - Ms'' >= c - M
			relation = factory.createRelationalExpression();
			relation.setOperator(RelationalOperator.GREATER_OR_EQUAL);
			relation.setLhs(lhs);
			relation.setRhs(rhs);
			c3.setExpression(relation);
			c3.setDepending(false);
			c3.setNegated(false);
			c3.setConstant(false);
			c3.getHelperVariables().add(s2);
			constraints.add(c3);

			// (IV) f(x) - Ms'' <= c - eps
			Constraint c4 = createSubstituteConstraint(factory, data, constraint, "_EQ", 3);
			// (IV) - LHS: f(x) - Ms''
			lhs = factory.createArithmeticBinaryExpression();
			lhs.setOperator(ArithmeticBinaryOperator.ADD);
			lhs.setLhs(GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getLhs())); // f(x)
			term = factory.createArithmeticBinaryExpression(); // -Ms''
			term.setOperator(ArithmeticBinaryOperator.MULTIPLY);
			term.setLhs(createInf(factory, false)); // -M
			term.setRhs(createVariableReference(factory, s2)); // s''
			lhs.setRhs(term);
			// (IV) - RHS: c - eps
			rhs = factory.createArithmeticBinaryExpression(); // c - eps
			rhs.setOperator(ArithmeticBinaryOperator.SUBTRACT);
			rhs.setLhs(GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getRhs())); // c
			rhs.setRhs(createEpsilon(factory, true)); // eps
			// (IV) - Combine: f(x) - Ms'' <= c - eps
			relation = factory.createRelationalExpression();
			relation.setOperator(RelationalOperator.LESS_OR_EQUAL);
			relation.setLhs(lhs);
			relation.setRhs(rhs);
			c4.setExpression(relation);
			c4.setDepending(false);
			c4.setNegated(false);
			c4.setConstant(false);
			c4.getHelperVariables().add(s2);
			constraints.add(c4);

			// (V) s - s' <= 0
			final Constraint c5 = createSubstituteConstraint(factory, data, constraint, "_EQ", 4);
			// (V) - LHS: s - s'
			lhs = factory.createArithmeticBinaryExpression();
			lhs.setOperator(ArithmeticBinaryOperator.ADD);
			lhs.setLhs(createVariableReference(factory, symbolicVariable)); // s
			term = factory.createArithmeticBinaryExpression(); // -s'
			term.setOperator(ArithmeticBinaryOperator.MULTIPLY);
			term.setLhs(createConstant(factory, -1)); // -1
			term.setRhs(createVariableReference(factory, s1)); // s'
			lhs.setRhs(term);
			// (V) - RHS: 0
			ArithmeticExpression shortRHS = createConstant(factory, 0);
			relation = factory.createRelationalExpression();
			relation.setOperator(RelationalOperator.LESS_OR_EQUAL);
			relation.setLhs(lhs);
			relation.setRhs(shortRHS);
			c5.setExpression(relation);
			c5.setDepending(false);
			c5.setNegated(false);
			c5.getHelperVariables().add(s1);
			c5.getHelperVariables().add(s2);
			constraints.add(c5);

			// (VI) s - s'' <= 0
			final Constraint c6 = createSubstituteConstraint(factory, data, constraint, "_EQ", 5);
			// (VI) - LHS: s - s''
			lhs = factory.createArithmeticBinaryExpression();
			lhs.setOperator(ArithmeticBinaryOperator.ADD);
			lhs.setLhs(createVariableReference(factory, symbolicVariable)); // s
			term = factory.createArithmeticBinaryExpression(); // -s''
			term.setOperator(ArithmeticBinaryOperator.MULTIPLY);
			term.setLhs(createConstant(factory, -1)); // -1
			term.setRhs(createVariableReference(factory, s2)); // s''
			lhs.setRhs(term);
			// (VI) - RHS: 0
			shortRHS = createConstant(factory, 0);
			relation = factory.createRelationalExpression();
			relation.setOperator(RelationalOperator.LESS_OR_EQUAL);
			relation.setLhs(lhs);
			relation.setRhs(shortRHS);
			c6.setExpression(relation);
			c6.setDepending(false);
			c6.setNegated(false);
			c6.getHelperVariables().add(s1);
			c6.getHelperVariables().add(s2);
			constraints.add(c6);

			// (VII) s' + s'' - s <= 1
			final Constraint c7 = createSubstituteConstraint(factory, data, constraint, "_EQ", 6);
			// (VI) - LHS: s' + s'' - s
			lhs = factory.createArithmeticBinaryExpression();
			lhs.setOperator(ArithmeticBinaryOperator.ADD);
			lhs.setLhs(createVariableReference(factory, s1)); // s'
			term = factory.createArithmeticBinaryExpression(); // s'' - s
			term.setOperator(ArithmeticBinaryOperator.ADD);
			term.setLhs(createVariableReference(factory, s2)); // s''

			ArithmeticBinaryExpression term2 = factory.createArithmeticBinaryExpression(); // -s
			term2.setOperator(ArithmeticBinaryOperator.MULTIPLY);
			term2.setLhs(createConstant(factory, -1)); // -1
			term2.setRhs(createVariableReference(factory, symbolicVariable)); // s
			term.setRhs(term2); // -s
			lhs.setRhs(term);
			// (VI) - RHS: 1
			shortRHS = createConstant(factory, 1);
			relation = factory.createRelationalExpression();
			relation.setOperator(RelationalOperator.LESS_OR_EQUAL);
			relation.setLhs(lhs);
			relation.setRhs(shortRHS);
			c7.setExpression(relation);
			c7.setDepending(false);
			c7.setNegated(false);
			c7.getHelperVariables().add(s1);
			c7.getHelperVariables().add(s2);
			c7.getHelperVariables().add(symbolicVariable);
			constraints.add(c7);

			return constraints;
		}
		case GREATER: {
			// Transform according to:
			// f(x) > c <=> s == 1, with s in {0, 1} and equalities (I) and (II).
			// As usual eps << 1, M >> 0, eps, M in R^+\{0}.
			//
			// (I) : f(x) - Ms >= c + eps - M
			// (II) : f(x) - Ms <= c

			// (I) f(x) - Ms >= c + eps - M
			Constraint c1 = createSubstituteConstraint(factory, data, constraint, "_GE", 0);
			// (I) - LHS: f(x) - Ms
			ArithmeticBinaryExpression lhs = factory.createArithmeticBinaryExpression(); // f(x) + (-M)s
			lhs.setOperator(ArithmeticBinaryOperator.ADD);
			lhs.setLhs(GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getLhs())); // f(x)
			ArithmeticBinaryExpression term = factory.createArithmeticBinaryExpression(); // -Ms
			term.setOperator(ArithmeticBinaryOperator.MULTIPLY);
			term.setLhs(createInf(factory, false)); // M
			term.setRhs(createVariableReference(factory, symbolicVariable)); // s
			lhs.setRhs(term);
			// (I) - RHS: c + eps - M
			ArithmeticBinaryExpression rhs = factory.createArithmeticBinaryExpression(); // c + eps - M
			rhs.setOperator(ArithmeticBinaryOperator.SUBTRACT);
			rhs.setLhs(GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getRhs())); // c
			ArithmeticBinaryExpression rhs_rhs = factory.createArithmeticBinaryExpression(); // eps - M
			rhs_rhs.setOperator(ArithmeticBinaryOperator.SUBTRACT);
			rhs_rhs.setLhs(createEpsilon(factory, true)); // eps
			rhs_rhs.setRhs(createInf(factory, true)); // M
			rhs.setRhs(rhs_rhs);
			// (I) - Combine: f(x) - Ms >= c + eps - M
			RelationalExpression relation = factory.createRelationalExpression();
			relation.setOperator(RelationalOperator.GREATER_OR_EQUAL); // >=
			relation.setLhs(lhs);
			relation.setRhs(rhs);
			c1.setExpression(relation);
			c1.setDepending(false);
			c1.setNegated(false);
			c1.setConstant(false);
			c1.getHelperVariables().add(symbolicVariable);
			constraints.add(c1);

			// (II) f(x) - Ms <= c
			Constraint c2 = createSubstituteConstraint(factory, data, constraint, "_GE", 1);
			// (II) - LHS: f(x) - Ms
			lhs = factory.createArithmeticBinaryExpression(); // f(x) + (-M)s
			lhs.setOperator(ArithmeticBinaryOperator.ADD);
			lhs.setLhs(GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getLhs())); // f(x)
			term = factory.createArithmeticBinaryExpression(); // -Ms
			term.setOperator(ArithmeticBinaryOperator.MULTIPLY);
			term.setLhs(createInf(factory, false)); // M
			term.setRhs(createVariableReference(factory, symbolicVariable)); // s
			lhs.setRhs(term);
			// (II) - RHS: c
			ArithmeticExpression shortRHS = //
					GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getRhs()); // c
			// (II) - Combine: f(x) - Ms <= c
			relation = factory.createRelationalExpression();
			relation.setOperator(RelationalOperator.LESS_OR_EQUAL); // <=
			relation.setLhs(lhs);
			relation.setRhs(shortRHS);
			c2.setExpression(relation);
			c2.setDepending(false);
			c2.setNegated(false);
			c2.setConstant(false);
			c2.getHelperVariables().add(symbolicVariable);
			constraints.add(c2);

			return constraints;
		}
		case GREATER_OR_EQUAL: {
			// Transform according to:
			// f(x) >= c <=> s == 1, with s in {0, 1} and equalities (I) and (II).
			// As usual eps << 1, M >> 0, eps, M in R^+\{0}.
			//
			// (I) : f(x) - Ms >= c - M
			// (II) : f(x) - Ms <= c - eps

			// (I) f(x) - Ms >= c - M
			Constraint c1 = createSubstituteConstraint(factory, data, constraint, "_GEQ", 0);
			// (I) - LHS: f(x) - Ms
			ArithmeticBinaryExpression lhs = factory.createArithmeticBinaryExpression(); // f(x) + (-M)s
			lhs.setOperator(ArithmeticBinaryOperator.ADD);
			lhs.setLhs(GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getLhs())); // f(x)
			ArithmeticBinaryExpression term = factory.createArithmeticBinaryExpression(); // -Ms
			term.setOperator(ArithmeticBinaryOperator.MULTIPLY);
			term.setLhs(createInf(factory, false)); // M
			term.setRhs(createVariableReference(factory, symbolicVariable)); // s
			lhs.setRhs(term);
			// (I) - RHS: c - M
			ArithmeticBinaryExpression rhs = factory.createArithmeticBinaryExpression(); // c - M
			rhs.setOperator(ArithmeticBinaryOperator.SUBTRACT);
			rhs.setLhs(GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getRhs())); // c
			rhs.setRhs(createInf(factory, true)); // M
			// (I) - Combine: f(x) - Ms >= c - M
			RelationalExpression relation = factory.createRelationalExpression();
			relation.setOperator(RelationalOperator.GREATER_OR_EQUAL); // >=
			relation.setLhs(lhs);
			relation.setRhs(rhs);
			c1.setExpression(relation);
			c1.setDepending(false);
			c1.setNegated(false);
			c1.setConstant(false);
			c1.getHelperVariables().add(symbolicVariable);
			constraints.add(c1);

			// (II) f(x) - Ms <= c - eps
			Constraint c2 = createSubstituteConstraint(factory, data, constraint, "_GEQ", 1);
			// (II) - LHS: f(x) - Ms
			lhs = factory.createArithmeticBinaryExpression(); // f(x) + (-M)s
			lhs.setOperator(ArithmeticBinaryOperator.ADD);
			lhs.setLhs(GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getLhs())); // f(x)
			term = factory.createArithmeticBinaryExpression(); // -Ms
			term.setOperator(ArithmeticBinaryOperator.MULTIPLY);
			term.setLhs(createInf(factory, false)); // M
			term.setRhs(createVariableReference(factory, symbolicVariable)); // s
			lhs.setRhs(term);
			// (II) - RHS: c - eps
			rhs = factory.createArithmeticBinaryExpression(); // c - eps
			rhs.setOperator(ArithmeticBinaryOperator.SUBTRACT);
			rhs.setLhs(GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getRhs())); // c
			rhs.setRhs(createEpsilon(factory, true)); // eps
			// (II) - Combine: f(x) - Ms <= c - eps
			relation = factory.createRelationalExpression();
			relation.setOperator(RelationalOperator.LESS_OR_EQUAL); // <=
			relation.setLhs(lhs);
			relation.setRhs(rhs);
			c2.setExpression(relation);
			c2.setDepending(false);
			c2.setNegated(false);
			c2.setConstant(false);
			c2.getHelperVariables().add(symbolicVariable);
			constraints.add(c2);

			return constraints;
		}
		case LESS: {
			// Transform according to:
			// f(x) < c <=> s == 1, with s in {0, 1} and equalities (I) and (II).
			// As usual eps << 1, M >> 0, eps, M in R^+\{0}.
			//
			// (I) : f(x) + Ms >= c
			// (II) : f(x) + Ms <= M + c - eps

			// (I) f(x) + Ms >= c
			Constraint c1 = createSubstituteConstraint(factory, data, constraint, "_LE", 0);
			// (I) - LHS: f(x) + Ms
			ArithmeticBinaryExpression lhs = factory.createArithmeticBinaryExpression(); // f(x) + Ms
			lhs.setOperator(ArithmeticBinaryOperator.ADD);
			lhs.setLhs(GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getLhs())); // f(x)
			ArithmeticBinaryExpression term = factory.createArithmeticBinaryExpression(); // Ms
			term.setOperator(ArithmeticBinaryOperator.MULTIPLY);
			term.setLhs(createInf(factory, true)); // M
			term.setRhs(createVariableReference(factory, symbolicVariable)); // s
			lhs.setRhs(term);
			// (I) - RHS: c
			ArithmeticExpression shortRHS = GipsArithmeticTransformer.cloneExpression(factory,
					(ArithmeticExpression) expression.getRhs()); // c
			// (I) - Combine: f(x) + Ms >= c
			RelationalExpression relation = factory.createRelationalExpression();
			relation.setOperator(RelationalOperator.GREATER_OR_EQUAL); // >=
			relation.setLhs(lhs);
			relation.setRhs(shortRHS);
			c1.setExpression(relation);
			c1.setDepending(false);
			c1.setNegated(false);
			c1.setConstant(false);
			c1.getHelperVariables().add(symbolicVariable);
			constraints.add(c1);

			// (II) f(x) + Ms <= M + c - eps
			Constraint c2 = createSubstituteConstraint(factory, data, constraint, "_LE", 1);
			// (II) - LHS: f(x) + Ms
			lhs = factory.createArithmeticBinaryExpression(); // f(x) + Ms
			lhs.setOperator(ArithmeticBinaryOperator.ADD);
			lhs.setLhs(GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getLhs())); // f(x)
			term = factory.createArithmeticBinaryExpression(); // Ms
			term.setOperator(ArithmeticBinaryOperator.MULTIPLY);
			term.setLhs(createInf(factory, true)); // M
			term.setRhs(createVariableReference(factory, symbolicVariable)); // s
			lhs.setRhs(term);
			// (II) - RHS: M + c - eps
			ArithmeticBinaryExpression rhs = factory.createArithmeticBinaryExpression(); // M + c - eps
			rhs.setOperator(ArithmeticBinaryOperator.ADD);
			rhs.setLhs(createInf(factory, true)); // M
			ArithmeticBinaryExpression rhs_rhs = factory.createArithmeticBinaryExpression(); // c - eps
			rhs_rhs.setOperator(ArithmeticBinaryOperator.SUBTRACT);
			rhs_rhs.setLhs(
					GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getRhs())); // c
			rhs_rhs.setRhs(createEpsilon(factory, true)); // eps
			rhs.setRhs(rhs_rhs);
			// (II) - Combine: f(x) + Ms <= M + c - eps
			relation = factory.createRelationalExpression();
			relation.setOperator(RelationalOperator.LESS_OR_EQUAL); // <=
			relation.setLhs(lhs);
			relation.setRhs(rhs);
			c2.setExpression(relation);
			c2.setDepending(false);
			c2.setNegated(false);
			c2.setConstant(false);
			c2.getHelperVariables().add(symbolicVariable);
			constraints.add(c2);

			return constraints;
		}
		case LESS_OR_EQUAL: {
			// Transform according to:
			// f(x) <= c <=> s == 1, with s in {0, 1} and equalities (I) and (II).
			// As usual eps << 1, M >> 0, eps, M in R^+\{0}.
			//
			// (I) : f(x) + Ms >= c + eps
			// (II) : f(x) + Ms <= M + c

			// (I) f(x) + Ms >= c + eps
			Constraint c1 = createSubstituteConstraint(factory, data, constraint, "_LEQ", 0);
			// (I) - LHS: f(x) + Ms
			ArithmeticBinaryExpression lhs = factory.createArithmeticBinaryExpression(); // f(x) + Ms
			lhs.setOperator(ArithmeticBinaryOperator.ADD);
			lhs.setLhs(GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getLhs())); // f(x)
			ArithmeticBinaryExpression term = factory.createArithmeticBinaryExpression(); // Ms
			term.setOperator(ArithmeticBinaryOperator.MULTIPLY);
			term.setLhs(createInf(factory, true)); // M
			term.setRhs(createVariableReference(factory, symbolicVariable)); // s
			lhs.setRhs(term);
			// (I) - RHS: c + eps
			ArithmeticBinaryExpression rhs = factory.createArithmeticBinaryExpression(); // c + eps
			rhs.setOperator(ArithmeticBinaryOperator.ADD);
			rhs.setLhs(GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getRhs())); // c
			rhs.setRhs(createEpsilon(factory, true));
			// (I) - Combine: f(x) + Ms >= c + eps
			RelationalExpression relation = factory.createRelationalExpression();
			relation.setOperator(RelationalOperator.GREATER_OR_EQUAL); // >=
			relation.setLhs(lhs);
			relation.setRhs(rhs);
			c1.setExpression(relation);
			c1.setDepending(false);
			c1.setNegated(false);
			c1.setConstant(false);
			c1.getHelperVariables().add(symbolicVariable);
			constraints.add(c1);

			// (II) f(x) + Ms <= M + c
			Constraint c2 = createSubstituteConstraint(factory, data, constraint, "_LEQ", 1);
			// (II) - LHS: f(x) + Ms
			lhs = factory.createArithmeticBinaryExpression(); // f(x) + Ms
			lhs.setOperator(ArithmeticBinaryOperator.ADD);
			lhs.setLhs(GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getLhs())); // f(x)
			term = factory.createArithmeticBinaryExpression(); // Ms
			term.setOperator(ArithmeticBinaryOperator.MULTIPLY);
			term.setLhs(createInf(factory, true)); // M
			term.setRhs(createVariableReference(factory, symbolicVariable)); // s
			lhs.setRhs(term);
			// (II) - RHS: M + c
			rhs = factory.createArithmeticBinaryExpression(); // c + eps
			rhs.setOperator(ArithmeticBinaryOperator.ADD);
			rhs.setRhs(GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getRhs())); // c
			rhs.setLhs(createInf(factory, true));
			// (II) - Combine: f(x) + Ms <= M + c
			relation = factory.createRelationalExpression();
			relation.setOperator(RelationalOperator.LESS_OR_EQUAL); // <=
			relation.setLhs(lhs);
			relation.setRhs(rhs);
			c2.setExpression(relation);
			c2.setDepending(false);
			c2.setNegated(false);
			c2.setConstant(false);
			c2.getHelperVariables().add(symbolicVariable);
			constraints.add(c2);

			return constraints;
		}
		case NOT_EQUAL: {
			// Transform according to:
			// f(x) != c <=> s == 1, with s in {0, 1} and equalities (I) through (V).
			// As usual eps << 1, M >> 0, eps, M in R^+\{0} and s', s'' in {0, 1}.
			//
			// (I) : f(x) - Ms' >= c + eps - M
			// (II) : f(x) - Ms' <= c
			// (III): f(x) + Ms'' >= c
			// (IV) : f(x) + Ms'' <= c - eps
			// (V) : s - s' - s''== 0

			// Creating s' and s''
			Variable s1 = createBinaryVariable(data, factory, constraint.getName() + "_NEQslack1");
			Variable s2 = createBinaryVariable(data, factory, constraint.getName() + "_NEQslack2");

			// (I) f(x) - Ms' >= c + eps - M
			Constraint c1 = createSubstituteConstraint(factory, data, constraint, "_NEQ", 0);
			// (I) - LHS: f(x) - Ms'
			ArithmeticBinaryExpression lhs = factory.createArithmeticBinaryExpression(); // f(x) + (-Ms')
			lhs.setOperator(ArithmeticBinaryOperator.ADD);
			lhs.setLhs(GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getLhs())); // f(x)
			ArithmeticBinaryExpression term = factory.createArithmeticBinaryExpression(); // -Ms'
			term.setOperator(ArithmeticBinaryOperator.MULTIPLY);
			term.setLhs(createInf(factory, false)); // -M
			term.setRhs(createVariableReference(factory, s1)); // s'
			lhs.setRhs(term);
			// (I) - RHS: c + eps - M
			ArithmeticBinaryExpression rhs = factory.createArithmeticBinaryExpression(); // c + eps - M
			rhs.setOperator(ArithmeticBinaryOperator.ADD);
			rhs.setLhs(GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getRhs())); // c
			ArithmeticBinaryExpression rhs_rhs = factory.createArithmeticBinaryExpression(); // eps - M
			rhs_rhs.setOperator(ArithmeticBinaryOperator.SUBTRACT);
			rhs_rhs.setLhs(createEpsilon(factory, true)); // eps
			rhs_rhs.setRhs(createInf(factory, true)); // M
			rhs.setRhs(rhs_rhs);
			// (I) - Combine: f(x) - Ms' >= c + eps - M
			RelationalExpression relation = factory.createRelationalExpression();
			relation.setOperator(RelationalOperator.GREATER_OR_EQUAL); // >=
			relation.setLhs(lhs);
			relation.setRhs(rhs);
			c1.setExpression(relation);
			c1.setDepending(false);
			c1.setNegated(false);
			c1.setConstant(false);
			c1.getHelperVariables().add(s1);
			constraints.add(c1);

			// (II) f(x) - Ms' <= c
			Constraint c2 = createSubstituteConstraint(factory, data, constraint, "_NEQ", 1);
			// (II) - LHS: f(x) - Ms'
			lhs = factory.createArithmeticBinaryExpression(); // f(x) + (-Ms')
			lhs.setOperator(ArithmeticBinaryOperator.ADD);
			lhs.setLhs(GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getLhs())); // f(x)
			term = factory.createArithmeticBinaryExpression(); // -Ms'
			term.setOperator(ArithmeticBinaryOperator.MULTIPLY);
			term.setLhs(createInf(factory, false)); // -M
			term.setRhs(createVariableReference(factory, s1)); // s'
			lhs.setRhs(term);
			// (II) - RHS: c
			ArithmeticExpression shortRHS = //
					GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getRhs()); // c
			// (II) - Combine: f(x) - Ms' <= c
			relation = factory.createRelationalExpression();
			relation.setOperator(RelationalOperator.LESS_OR_EQUAL); // <=
			relation.setLhs(lhs);
			relation.setRhs(shortRHS);
			c2.setExpression(relation);
			c2.setDepending(false);
			c2.setNegated(false);
			c2.setConstant(false);
			c2.getHelperVariables().add(s1);
			constraints.add(c2);

			// (III) f(x) + Ms'' >= c
			Constraint c3 = createSubstituteConstraint(factory, data, constraint, "_NEQ", 2);
			// (III) - LHS: f(x) + Ms''
			lhs = factory.createArithmeticBinaryExpression();
			lhs.setOperator(ArithmeticBinaryOperator.ADD);
			lhs.setLhs(GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getLhs())); // f(x)
			term = factory.createArithmeticBinaryExpression(); // Ms''
			term.setOperator(ArithmeticBinaryOperator.MULTIPLY);
			term.setLhs(createInf(factory, true)); // M
			term.setRhs(createVariableReference(factory, s2)); // s''
			lhs.setRhs(term);
			// (III) - RHS: c
			shortRHS = GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getRhs()); // c
			// (III) - Combine: f(x) + Ms'' >= c
			relation = factory.createRelationalExpression();
			relation.setOperator(RelationalOperator.GREATER_OR_EQUAL);
			relation.setLhs(lhs);
			relation.setRhs(shortRHS);
			c3.setExpression(relation);
			c3.setDepending(false);
			c3.setNegated(false);
			c3.setConstant(false);
			c3.getHelperVariables().add(s2);
			constraints.add(c3);

			// (IV) f(x) + Ms'' <= M + c - eps
			Constraint c4 = createSubstituteConstraint(factory, data, constraint, "_NEQ", 3);
			// (IV) - LHS: f(x) + Ms''
			lhs = factory.createArithmeticBinaryExpression();
			lhs.setOperator(ArithmeticBinaryOperator.ADD);
			lhs.setLhs(GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getLhs())); // f(x)
			term = factory.createArithmeticBinaryExpression(); // Ms''
			term.setOperator(ArithmeticBinaryOperator.MULTIPLY);
			term.setLhs(createInf(factory, true)); // M
			term.setRhs(createVariableReference(factory, s2)); // s''
			lhs.setRhs(term);
			// (IV) - RHS: M + c - eps
			rhs = factory.createArithmeticBinaryExpression(); // M + c - eps
			rhs.setOperator(ArithmeticBinaryOperator.ADD);
			rhs.setLhs(createInf(factory, true)); // M
			rhs_rhs = factory.createArithmeticBinaryExpression(); // c - eps
			rhs_rhs.setOperator(ArithmeticBinaryOperator.SUBTRACT);
			rhs_rhs.setLhs(
					GipsArithmeticTransformer.cloneExpression(factory, (ArithmeticExpression) expression.getRhs())); // c
			rhs_rhs.setRhs(createEpsilon(factory, true)); // eps
			rhs.setRhs(rhs_rhs);
			// (IV) - Combine: f(x) + Ms'' <= M + c - eps
			relation = factory.createRelationalExpression();
			relation.setOperator(RelationalOperator.LESS_OR_EQUAL);
			relation.setLhs(lhs);
			relation.setRhs(rhs);
			c4.setExpression(relation);
			c4.setDepending(false);
			c4.setNegated(false);
			c4.setConstant(false);
			c4.getHelperVariables().add(s1);
			constraints.add(c4);

			// (V) s - s' - s'' == 0
			Constraint c5 = createSubstituteConstraint(factory, data, constraint, "_NEQ", 4);
			// (V) - LHS: s - s' - s'' = s + (-1*s') + (-1*s'')
			lhs = factory.createArithmeticBinaryExpression();
			lhs.setOperator(ArithmeticBinaryOperator.ADD);
			lhs.setLhs(createVariableReference(factory, symbolicVariable)); // s
			ArithmeticBinaryExpression lhs_rhs = //
					factory.createArithmeticBinaryExpression(); // - s' - s'' = (-1*s') + (-1*s'')
			term = factory.createArithmeticBinaryExpression(); // (-1*s')
			term.setOperator(ArithmeticBinaryOperator.MULTIPLY);
			term.setLhs(createConstant(factory, -1.0));
			term.setRhs(createVariableReference(factory, s1));
			lhs_rhs.setLhs(term);
			term = factory.createArithmeticBinaryExpression(); // (-1*s'')
			term.setOperator(ArithmeticBinaryOperator.MULTIPLY);
			term.setLhs(createConstant(factory, -1.0));
			term.setRhs(createVariableReference(factory, s2));
			lhs_rhs.setRhs(term);
			lhs.setRhs(lhs_rhs);
			// (V) - RHS: 0
			shortRHS = createConstant(factory, 0.0);
			// (V) - Combine: s + (-1*s') + (-1*s'') == 0
			relation = factory.createRelationalExpression();
			relation.setOperator(RelationalOperator.EQUAL);
			relation.setLhs(lhs);
			relation.setRhs(shortRHS);
			c5.setExpression(relation);
			c5.setDepending(false);
			c5.setNegated(false);
			c5.setConstant(false);
			c5.getHelperVariables().add(s1);
			c5.getHelperVariables().add(s2);
			constraints.add(c5);
			return constraints;
		}
		default:
			throw new UnsupportedOperationException(
					"Linear equalities may not contain non-numeric values aka. complex objects.");
		}
	}

	static protected DoubleLiteral createEpsilon(GipsIntermediateFactory factory, boolean positive) {
		DoubleLiteral eps = factory.createDoubleLiteral();
		if (positive) {
			eps.setLiteral(EPSILON);
		} else {
			eps.setLiteral(-EPSILON);
		}
		return eps;
	}

	static protected DoubleLiteral createInf(GipsIntermediateFactory factory, boolean positive) {
		DoubleLiteral inf = factory.createDoubleLiteral();
		if (positive) {
			inf.setLiteral(INF);
		} else {
			inf.setLiteral(-INF);
		}
		return inf;
	}

	static protected DoubleLiteral createConstant(GipsIntermediateFactory factory, double value) {
		DoubleLiteral constant = factory.createDoubleLiteral();
		constant.setLiteral(value);
		return constant;
	}

	static protected Constraint createSubstituteConstraint(GipsIntermediateFactory factory,
			final GipsTransformationData data, final Constraint constraint, String prefix, int index) {
		if (constraint instanceof MappingConstraint mConstraint) {
			MappingConstraint substitute = factory.createMappingConstraint();
			substitute.setName(mConstraint.getName() + prefix + index);
			substitute.setMapping(mConstraint.getMapping());
			return substitute;
		} else if (constraint instanceof PatternConstraint pConstraint) {
			PatternConstraint substitute = factory.createPatternConstraint();
			substitute.setName(pConstraint.getName() + prefix + index);
			substitute.setPattern(pConstraint.getPattern());
			return substitute;
		} else if (constraint instanceof RuleConstraint rConstraint) {
			RuleConstraint substitute = factory.createRuleConstraint();
			substitute.setName(rConstraint.getName() + prefix + index);
			substitute.setRule(rConstraint.getRule());
			return substitute;
		} else if (constraint instanceof TypeConstraint tConstraint) {
			TypeConstraint substitute = factory.createTypeConstraint();
			substitute.setName(tConstraint.getName() + prefix + index);
			substitute.setType(tConstraint.getType());
			return substitute;
		} else {
			Constraint substitute = factory.createConstraint();
			substitute.setGlobal(true);
			substitute.setName(constraint.getName() + prefix + index);
			return substitute;
		}
	}

	static Variable createBinaryVariable(final GipsTransformationData data, final GipsIntermediateFactory factory,
			final String name) {
		Variable var = factory.createVariable();
		data.model().getVariables().add(var);
		var.setType(VariableType.BINARY);
		var.setName(name);
		var.setUpperBound(1.0);
		var.setLowerBound(0.0);
		return var;
	}

	static Variable createIntegerVariable(final GipsTransformationData data, final GipsIntermediateFactory factory,
			final String name) {
		Variable var = factory.createVariable();
		data.model().getVariables().add(var);
		var.setType(VariableType.INTEGER);
		var.setName(name);
		var.setUpperBound(INF - EPSILON);
		var.setLowerBound(-INF + EPSILON);
		return var;
	}

	static Variable createRealVariable(final GipsTransformationData data, final GipsIntermediateFactory factory,
			final String name) {
		Variable var = factory.createVariable();
		data.model().getVariables().add(var);
		var.setType(VariableType.REAL);
		var.setName(name);
		var.setUpperBound(INF - EPSILON);
		var.setLowerBound(-INF + EPSILON);
		return var;
	}

	static VariableReference createVariableReference(final GipsIntermediateFactory factory, Variable variable) {
		VariableReference ref = factory.createVariableReference();
		ref.setVariable(variable);
		ref.setLocal(true);
		return ref;
	}
}
