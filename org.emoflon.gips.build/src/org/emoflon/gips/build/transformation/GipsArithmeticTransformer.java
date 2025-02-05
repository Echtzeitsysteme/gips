package org.emoflon.gips.build.transformation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.build.transformation.helper.ArithmeticExpressionType;
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticBinaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticBinaryOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticUnaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticUnaryOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.AttributeExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.AttributeReference;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanBinaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanBinaryOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanUnaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanUnaryOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.ConcatenationOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantReference;
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextReference;
import org.emoflon.gips.intermediate.GipsIntermediate.DoubleLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateFactory;
import org.emoflon.gips.intermediate.GipsIntermediate.IntegerLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.LinearFunctionReference;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingReference;
import org.emoflon.gips.intermediate.GipsIntermediate.NodeReference;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternReference;
import org.emoflon.gips.intermediate.GipsIntermediate.QueryOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.RuleReference;
import org.emoflon.gips.intermediate.GipsIntermediate.SelectOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.SetConcatenation;
import org.emoflon.gips.intermediate.GipsIntermediate.SetElementQuery;
import org.emoflon.gips.intermediate.GipsIntermediate.SetExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.SetFilter;
import org.emoflon.gips.intermediate.GipsIntermediate.SetOperation;
import org.emoflon.gips.intermediate.GipsIntermediate.SetOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.SetReduce;
import org.emoflon.gips.intermediate.GipsIntermediate.SetSimpleOperation;
import org.emoflon.gips.intermediate.GipsIntermediate.SetSimpleQuery;
import org.emoflon.gips.intermediate.GipsIntermediate.SetSimpleSelect;
import org.emoflon.gips.intermediate.GipsIntermediate.SetSort;
import org.emoflon.gips.intermediate.GipsIntermediate.SetSummation;
import org.emoflon.gips.intermediate.GipsIntermediate.SetTransformation;
import org.emoflon.gips.intermediate.GipsIntermediate.SetTypeQuery;
import org.emoflon.gips.intermediate.GipsIntermediate.SetTypeSelect;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeReference;
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference;
import org.logicng.io.parsers.ParserException;

public class GipsArithmeticTransformer {
	final protected GipsIntermediateFactory factory;

	public GipsArithmeticTransformer(final GipsIntermediateFactory factory) {
		this.factory = factory;
	}

	// Transforms a relational expression into a form that is accepted by ilp
	// solvers.
	// (1) Intended normalized ILP-form: lhs := <varExpr> op := <operator> rhs :=
	// <constExpr>
	// (2) If lhs and rhs of the expression are constant, nothing has to be done,
	// since it will not be used as input for an ilp solver anyways.
	//
	public RelationalExpression normalize(final RelationalExpression expression) throws ParserException {
		boolean isLhsConst;
		boolean isRhsConst;
		if (expression.getLhs() instanceof ArithmeticExpression arithmetic) {
			isLhsConst = (GipsTransformationUtils.isConstantExpression(arithmetic) == ArithmeticExpressionType.constant)
					? true
					: false;
		} else {
			isLhsConst = (GipsTransformationUtils
					.isConstantExpression((BooleanExpression) expression.getLhs()) == ArithmeticExpressionType.constant)
							? true
							: false;
		}

		if (expression.getRhs() instanceof ArithmeticExpression arithmetic) {
			isRhsConst = (GipsTransformationUtils.isConstantExpression(arithmetic) == ArithmeticExpressionType.constant)
					? true
					: false;
		} else {
			isRhsConst = (GipsTransformationUtils
					.isConstantExpression((BooleanExpression) expression.getRhs()) == ArithmeticExpressionType.constant)
							? true
							: false;
		}

		RelationalExpression modified = factory.createRelationalExpression();
		modified.setOperator(expression.getOperator());
		if (isLhsConst && isRhsConst) {
			// Both sides constant -> nothing to do!
			return expression;
		} else if (!isLhsConst && isRhsConst) {
			// Rhs is already constant -> (1) normalize and expand lhs, (2) move const terms
			// of lhs to rhs
			modified.setRhs(cloneExpression(factory, (ArithmeticExpression) expression.getRhs()));
			modified.setLhs(normalizeAndExpand((ArithmeticExpression) expression.getLhs()));
		} else if (isLhsConst && !isRhsConst) {
			// Rhs is not constant and Lhs is constant -> (1) Flip lhs with rhs, (2) flip
			// relational operator, (3) normalize and expand lhs, (4) move const terms of
			// lhs to rhs
			GipsTransformationUtils.flipOperator(modified);
			modified.setRhs(cloneExpression(factory, (ArithmeticExpression) expression.getLhs()));
			modified.setLhs(normalizeAndExpand((ArithmeticExpression) expression.getRhs()));
		} else {
			// Rhs and Lhs are both not constant -> (1) Move rhs to lhs, (2) set rhs to 0,
			// (3) normalize and expand lhs, (4) move const terms of
			// lhs to rhs
			ArithmeticBinaryExpression move = factory.createArithmeticBinaryExpression();
			move.setOperator(ArithmeticBinaryOperator.SUBTRACT);
			move.setLhs(cloneExpression(factory, (ArithmeticExpression) expression.getLhs()));
			move.setRhs(cloneExpression(factory, (ArithmeticExpression) expression.getRhs()));
			DoubleLiteral zero = factory.createDoubleLiteral();
			zero.setLiteral(0.0);
			modified.setLhs(normalizeAndExpand(move));
			modified.setRhs(zero);
		}

		// -> Move const terms over to rhs!
		modified = moveConstTerms(modified);

		return modified;
	}

	public RelationalExpression moveConstTerms(final RelationalExpression expression) {
		RelationalExpression modified = factory.createRelationalExpression();
		modified.setOperator(expression.getOperator());

		HashSet<ArithmeticExpression> constTerms = new LinkedHashSet<>();
		HashSet<ArithmeticExpression> varTerms = new LinkedHashSet<>();

		if (expression.getLhs() instanceof ArithmeticExpression arithmetic) {
			splitIntoConstAndVarTerms(arithmetic, constTerms, varTerms);
		} else {
			throw new IllegalArgumentException(
					"There must be no boolean expression in arithmetic expressions nor in comparisons between numeric values: "
							+ expression.getLhs());
		}

		if (constTerms.isEmpty()) {
			// No constant terms in var expression -> do nothing
			return expression;
		} else {
			ArithmeticExpression lhs = foldAndAddTerms(varTerms);
			modified.setLhs(lhs);

			ArithmeticExpression constTerm = foldAndAddTerms(constTerms);

			ArithmeticBinaryExpression subtraction = factory.createArithmeticBinaryExpression();
			subtraction.setOperator(ArithmeticBinaryOperator.SUBTRACT);
			subtraction.setLhs(cloneExpression(factory, (ArithmeticExpression) expression.getRhs()));
			ArithmeticUnaryExpression bracket = factory.createArithmeticUnaryExpression();
			bracket.setOperator(ArithmeticUnaryOperator.BRACKET);
			bracket.setOperand(constTerm);
			subtraction.setRhs(bracket);

			modified.setRhs(subtraction);
		}

		return modified;
	}

	public void splitIntoConstAndVarTerms(final ArithmeticExpression expression,
			final Collection<ArithmeticExpression> constTerms, final Collection<ArithmeticExpression> varTerms) {
		if (expression instanceof ArithmeticBinaryExpression binaryExpr) {
			switch (binaryExpr.getOperator()) {
			case ADD -> {
				splitIntoConstAndVarTerms(binaryExpr.getLhs(), constTerms, varTerms);
				splitIntoConstAndVarTerms(binaryExpr.getRhs(), constTerms, varTerms);
			}
			case LOG -> {
				// Log is always constant
				constTerms.add(expression);
			}
			case MULTIPLY -> {
				boolean isConst = (GipsTransformationUtils
						.isConstantExpression(expression) == ArithmeticExpressionType.constant) ? true : false;
				if (isConst) {
					constTerms.add(expression);
				} else {
					varTerms.add(expression);
				}
			}
			case POW -> {
				// Pow is always constant
				constTerms.add(expression);
			}
			default -> {
				throw new UnsupportedOperationException(
						"Unknown or unsupported arithmetic operator in normalized term: " + binaryExpr.getOperator());
			}
			}
		} else if (expression instanceof ArithmeticUnaryExpression unaryExpr) {
			switch (unaryExpr.getOperator()) {
			case ABSOLUTE -> {
				// Abs is always constant
				constTerms.add(expression);
			}
			case COSINE -> {
				// Cos is always constant
				constTerms.add(expression);
			}
			case SINE -> {
				// Sin is always constant
				constTerms.add(expression);
			}
			case SQRT -> {
				// Sqrt is always constant
				constTerms.add(expression);
			}
			default -> {
				throw new UnsupportedOperationException(
						"Unknown or unsupported arithmetic operator in normalized term: " + unaryExpr.getOperator());
			}
			}

		} else if (expression instanceof ConstantReference reference) {
			splitIntoConstAndVarTerms(reference, constTerms, varTerms);
		} else if (expression instanceof ValueExpression value) {
			splitIntoConstAndVarTerms(value, constTerms, varTerms);
		} else if (expression instanceof LinearFunctionReference function) {
			throw new IllegalArgumentException(
					"There must be no linear function references in constraints: " + function);
		} else {
			// Case: Literals, Constants and Constant references
			constTerms.add(expression);
		}
	}

	public void splitIntoConstAndVarTerms(final ConstantReference expression,
			final Collection<ArithmeticExpression> constTerms, final Collection<ArithmeticExpression> varTerms) {
		if (expression.getSetExpression() == null) {
			constTerms.add(expression);
			return;
		}

		if (expression.getSetExpression().getSetReduce() == null) {
			constTerms.add(expression);
			return;
		}

		if (expression.getSetExpression().getSetReduce() instanceof SetSummation sum) {
			if (GipsTransformationUtils
					.isConstantExpression(sum.getExpression()) == ArithmeticExpressionType.constant) {
				constTerms.add(expression);
			} else {
				varTerms.add(expression);
			}
		} else {
			constTerms.add(expression);
		}
	}

	public void splitIntoConstAndVarTerms(final ValueExpression expression,
			final Collection<ArithmeticExpression> constTerms, final Collection<ArithmeticExpression> varTerms) {
		if (expression instanceof VariableReference) {
			varTerms.add(expression);
			return;
		}

		if (expression.getSetExpression() == null) {
			constTerms.add(expression);
			return;
		}

		if (expression.getSetExpression().getSetReduce() == null) {
			constTerms.add(expression);
			return;
		}

		if (expression.getSetExpression().getSetReduce() instanceof SetSummation sum) {
			if (GipsTransformationUtils
					.isConstantExpression(sum.getExpression()) == ArithmeticExpressionType.constant) {
				constTerms.add(expression);
			} else {
				varTerms.add(expression);
			}
		} else {
			constTerms.add(expression);
		}
	}

	protected ArithmeticExpression foldAndAddTerms(Collection<ArithmeticExpression> t) {
		LinkedList<ArithmeticExpression> terms = new LinkedList<>(t);
		if (terms.size() == 1) {
			return cloneExpression(factory, terms.poll());
		}

		ArithmeticBinaryExpression root = factory.createArithmeticBinaryExpression();
		root.setOperator(ArithmeticBinaryOperator.ADD);
		ArithmeticBinaryExpression current = root;

		while (!terms.isEmpty()) {
			ArithmeticExpression lhs = terms.poll();
			current.setLhs(cloneExpression(factory, lhs));
			if (terms.size() > 1) {
				ArithmeticBinaryExpression next = factory.createArithmeticBinaryExpression();
				next.setOperator(ArithmeticBinaryOperator.ADD);
				current.setRhs(next);
				current = next;
			} else {
				ArithmeticExpression rhs = terms.poll();
				current.setRhs(cloneExpression(factory, rhs));
			}
		}

		return root;
	}

	public ArithmeticExpression normalizeAndExpand(final ArithmeticExpression expression) throws ParserException {
		ArithmeticExpression modified = removeSubtractions(expression);
		modified = expandArithmeticExpressions(modified);
		return modified;
	}

	protected ArithmeticExpression removeSubtractions(final ArithmeticExpression expression) {
		ArithmeticExpression modified = null;
		if (expression instanceof ArithmeticBinaryExpression binaryExpr) {
			ArithmeticBinaryExpression mbe = factory.createArithmeticBinaryExpression();
			modified = mbe;
			mbe.setOperator(binaryExpr.getOperator());
			switch (binaryExpr.getOperator()) {
			case ADD -> {
				mbe.setLhs(removeSubtractions(binaryExpr.getLhs()));
				mbe.setRhs(removeSubtractions(binaryExpr.getRhs()));
			}
			case DIVIDE -> {
				mbe.setLhs(removeSubtractions(binaryExpr.getLhs()));
				mbe.setRhs(removeSubtractions(binaryExpr.getRhs()));
			}
			case LOG -> {
				mbe.setLhs(removeSubtractions(binaryExpr.getLhs()));
				mbe.setRhs(removeSubtractions(binaryExpr.getRhs()));
			}
			case MULTIPLY -> {
				mbe.setLhs(removeSubtractions(binaryExpr.getLhs()));
				mbe.setRhs(removeSubtractions(binaryExpr.getRhs()));
			}
			case POW -> {
				mbe.setLhs(removeSubtractions(binaryExpr.getLhs()));
				mbe.setRhs(removeSubtractions(binaryExpr.getRhs()));
			}
			case SUBTRACT -> {
				mbe.setLhs(removeSubtractions(binaryExpr.getLhs()));
				mbe.setOperator(ArithmeticBinaryOperator.ADD);

				ArithmeticBinaryExpression negation = factory.createArithmeticBinaryExpression();
				negation.setOperator(ArithmeticBinaryOperator.MULTIPLY);
				DoubleLiteral negOne = factory.createDoubleLiteral();
				negOne.setLiteral(-1.0);
				negation.setLhs(negOne);
				negation.setRhs(removeSubtractions(binaryExpr.getRhs()));
				mbe.setRhs(negation);
			}
			default -> {
				throw new UnsupportedOperationException("Unknown arithmetic operator: " + binaryExpr.getOperator());
			}
			}
		} else if (expression instanceof ArithmeticUnaryExpression unaryExpr) {
			ArithmeticUnaryExpression mue = factory.createArithmeticUnaryExpression();
			mue.setOperator(unaryExpr.getOperator());
			modified = mue;
			switch (unaryExpr.getOperator()) {
			case ABSOLUTE -> {
				mue.setOperand(removeSubtractions(unaryExpr.getOperand()));
			}
			case BRACKET -> {
				// Brackets can be removed, since the arithmetic expression tree has already
				// been parsed correctly.
				modified = removeSubtractions(unaryExpr.getOperand());
			}
			case COSINE -> {
				mue.setOperand(removeSubtractions(unaryExpr.getOperand()));
			}
			case NEGATE -> {
				ArithmeticBinaryExpression negation = factory.createArithmeticBinaryExpression();
				negation.setOperator(ArithmeticBinaryOperator.MULTIPLY);
				DoubleLiteral negOne = factory.createDoubleLiteral();
				negOne.setLiteral(-1.0);
				negation.setLhs(negOne);
				negation.setRhs(removeSubtractions(unaryExpr.getOperand()));
				modified = negation;
			}
			case SINE -> {
				mue.setOperand(removeSubtractions(unaryExpr.getOperand()));
			}
			case SQRT -> {
				mue.setOperand(removeSubtractions(unaryExpr.getOperand()));
			}
			default -> {
				throw new UnsupportedOperationException("Unknown arithmetic operator: " + unaryExpr.getOperator());
			}
			}

		} else if (expression instanceof ConstantReference reference) {
			// CASE: SUM-Expressions
			if (reference.getSetExpression() != null && reference.getSetExpression().getSetReduce() != null
					&& reference.getSetExpression().getSetReduce() instanceof SetSummation) {
				// TODO: This might be a critical point in case nested sums are used.
				ConstantReference cr = (ConstantReference) cloneExpression(factory, (EObject) reference);
				SetSummation mss = (SetSummation) cr.getSetExpression().getSetReduce();
				mss.setExpression(removeSubtractions(mss.getExpression()));
				modified = cr;
			} else {
				modified = cloneExpression(factory, (ArithmeticExpression) reference);
			}
		} else if (expression instanceof ValueExpression valExpr) {
			// CASE: SUM-Expressions
			if (valExpr.getSetExpression() != null && valExpr.getSetExpression().getSetReduce() != null
					&& valExpr.getSetExpression().getSetReduce() instanceof SetSummation) {
				// TODO: This might be a critical point in case nested sums are used.
				ValueExpression mve = (ValueExpression) cloneExpression(factory, valExpr);
				SetSummation mss = (SetSummation) mve.getSetExpression().getSetReduce();
				mss.setExpression(removeSubtractions(mss.getExpression()));
				modified = mve;
			} else {
				modified = cloneExpression(factory, valExpr);
			}
		} else if (expression instanceof LinearFunctionReference function) {
			return function;
		} else {
			// CASE: Literals, Constants and Constant references
			modified = cloneExpression(factory, expression);
		}

		return modified;
	}

	protected ArithmeticExpression expandArithmeticExpressions(final ArithmeticExpression expression) {
		ArithmeticExpression modified = null;
		if (expression instanceof ArithmeticBinaryExpression binaryExpr) {
			ArithmeticBinaryExpression mbe = factory.createArithmeticBinaryExpression();
			modified = mbe;
			mbe.setOperator(binaryExpr.getOperator());

			ArithmeticExpressionType lhsType = GipsTransformationUtils.isConstantExpression(binaryExpr.getLhs());
			ArithmeticExpressionType rhsType = GipsTransformationUtils.isConstantExpression(binaryExpr.getRhs());

			boolean lhsConstant = (lhsType == ArithmeticExpressionType.constant) ? true : false;
			boolean rhsConstant = (rhsType == ArithmeticExpressionType.constant) ? true : false;

			switch (binaryExpr.getOperator()) {
			case ADD -> {
				mbe.setLhs(expandArithmeticExpressions(binaryExpr.getLhs()));
				mbe.setRhs(expandArithmeticExpressions(binaryExpr.getRhs()));
			}
			case DIVIDE -> {
				if (lhsConstant && rhsConstant || !lhsConstant && rhsConstant) {
					// If the divident contains a varibale
					// -> transform to multiplication and expand
					ArithmeticBinaryExpression inverse = factory.createArithmeticBinaryExpression();
					inverse.setOperator(ArithmeticBinaryOperator.POW);
					DoubleLiteral one = factory.createDoubleLiteral();
					one.setLiteral(-1.0);
					inverse.setLhs(cloneExpression(factory, binaryExpr.getRhs()));
					inverse.setRhs(one);
					mbe.setLhs(cloneExpression(factory, binaryExpr.getLhs()));
					mbe.setRhs(inverse);
					mbe.setOperator(ArithmeticBinaryOperator.MULTIPLY);
					modified = expandArithmeticExpressions(mbe);
				} else {
					// If the divisor contains a variable -> error
					throw new UnsupportedOperationException("Variable may not be part of a divisor.");
				}
			}
			case LOG -> {
				if (lhsConstant && rhsConstant) {
					// If both sub-expressions are constant -> do nothing
					mbe.setLhs(cloneExpression(factory, binaryExpr.getLhs()));
					mbe.setRhs(cloneExpression(factory, binaryExpr.getRhs()));
				} else {
					// If any of the sub-expression contain variables -> error
					throw new UnsupportedOperationException("Variable may not be part of a log-expression.");
				}
			}
			case MULTIPLY -> {
				if (lhsConstant && !rhsConstant || !lhsConstant && rhsConstant) {
					// If only one factor of the product contains variables
					if (isExpanded(binaryExpr, false)) {
						// Do nothing if already expanded or constant
						mbe.setLhs(cloneExpression(factory, binaryExpr.getLhs()));
						mbe.setRhs(cloneExpression(factory, binaryExpr.getRhs()));
					} else {
						// -> expand
						ArithmeticExpression current = binaryExpr;
						while (!isExpanded(current, false)) {
							current = expandProducts(current, new LinkedHashSet<>());
						}
						modified = current;
					}
				} else if (lhsConstant && rhsConstant) {
					// If both factors are constant, there is nothing else to do.
					mbe.setLhs(cloneExpression(factory, binaryExpr.getLhs()));
					mbe.setRhs(cloneExpression(factory, binaryExpr.getRhs()));
				} else {
					// If both factors of the product contain a variable each -> error
					throw new UnsupportedOperationException("Variables may not be multiplied!");
				}
			}
			case POW -> {
				if (lhsConstant && rhsConstant) {
					// If both sub-expressions are constant -> do nothing
					mbe.setLhs(cloneExpression(factory, binaryExpr.getLhs()));
					mbe.setRhs(cloneExpression(factory, binaryExpr.getRhs()));
				} else {
					// If any of the sub-expression contain variables -> error
					throw new UnsupportedOperationException("Variable may not be part of an exponential expression.");
				}
			}
			default -> {
				throw new UnsupportedOperationException("Unknown arithmetic operator: " + binaryExpr.getOperator());
			}
			}
		} else if (expression instanceof ArithmeticUnaryExpression unaryExpr) {
			ArithmeticUnaryExpression mue = factory.createArithmeticUnaryExpression();
			mue.setOperator(unaryExpr.getOperator());
			modified = mue;

			boolean constant = GipsTransformationUtils
					.isConstantExpression(unaryExpr.getOperand()) == ArithmeticExpressionType.constant ? true : false;
			switch (unaryExpr.getOperator()) {
			case ABSOLUTE -> {
				if (constant) {
					// If sub-expression is constant -> do nothing
					mue.setOperand(cloneExpression(factory, unaryExpr.getOperand()));
				} else {
					// If the sub-expression contains a variable -> error
					throw new UnsupportedOperationException("Variable may not be part of an abs-expression.");
				}
			}
			case BRACKET -> {
				// These should have been resolved (remove subtractions)
				// -> Throw exception in for debugging purposes
				throw new UnsupportedOperationException(
						"Error: Brackets have not been resolved prior to expanding arithmetic expressions.");
			}
			case COSINE -> {
				if (constant) {
					// If sub-expression is constant -> do nothing
					mue.setOperand(cloneExpression(factory, unaryExpr.getOperand()));
				} else {
					// If the sub-expression contains a variable -> error
					throw new UnsupportedOperationException("Variable may not be part of an cos-expression.");
				}
			}
			case NEGATE -> {
				// These should have been resolved (remove subtractions)
				// -> Throw exception in for debugging purposes
				throw new UnsupportedOperationException(
						"Error: Negations have not been resolved prior to expanding arithmetic expressions.");
			}
			case SINE -> {
				if (constant) {
					// If sub-expression is constant -> do nothing
					mue.setOperand(cloneExpression(factory, unaryExpr.getOperand()));
				} else {
					// If the sub-expression contains a variable -> error
					throw new UnsupportedOperationException("Variable may not be part of an sin-expression.");
				}
			}
			case SQRT -> {
				if (constant) {
					// If sub-expression is constant -> do nothing
					mue.setOperand(cloneExpression(factory, unaryExpr.getOperand()));
				} else {
					// If the sub-expression contains a variable -> error
					throw new UnsupportedOperationException("Variable may not be part of an sqrt-expression.");
				}
			}
			default -> {
				throw new UnsupportedOperationException("Unknown arithmetic operator: " + unaryExpr.getOperator());
			}
			}

		} else if (expression instanceof ConstantReference reference) {
			// CASE: SUM-Expressions
			if (reference.getSetExpression() != null && reference.getSetExpression().getSetReduce() != null
					&& reference.getSetExpression().getSetReduce() instanceof SetSummation) {
				// TODO: This might be a critical point in case nested sums are used.
				ConstantReference cr = (ConstantReference) cloneExpression(factory, (EObject) reference);
				SetSummation mss = (SetSummation) cr.getSetExpression().getSetReduce();
				modified = wrapTermsIntoNewSum(expandArithmeticExpressions(mss.getExpression()), cr);
			} else {
				modified = cloneExpression(factory, (ArithmeticExpression) reference);
			}
		} else if (expression instanceof ValueExpression valExpr) {
			// CASE: SUM-Expressions
			if (valExpr.getSetExpression() != null && valExpr.getSetExpression().getSetReduce() != null
					&& valExpr.getSetExpression().getSetReduce() instanceof SetSummation) {
				// TODO: This might be a critical point in case nested sums are used.
				ValueExpression mve = (ValueExpression) cloneExpression(factory, valExpr);
				SetSummation mss = (SetSummation) mve.getSetExpression().getSetReduce();
				modified = wrapTermsIntoNewSum(expandArithmeticExpressions(mss.getExpression()), mve);
			} else {
				modified = cloneExpression(factory, valExpr);
			}
		} else if (expression instanceof LinearFunctionReference function) {
			return function;
		} else {
			// CASE: Literals, Constants and Constant references
			modified = cloneExpression(factory, expression);
		}
		return modified;
	}

	protected ArithmeticExpression expandProducts(final ArithmeticExpression expression,
			Collection<ArithmeticExpression> factors) {
		if (expression instanceof ArithmeticBinaryExpression binaryExpr) {
			boolean lhsExpanded = isExpanded(binaryExpr.getLhs(), false);
			boolean rhsExpanded = isExpanded(binaryExpr.getRhs(), false);

			switch (binaryExpr.getOperator()) {
			case ADD -> {
				ArithmeticBinaryExpression expanded = factory.createArithmeticBinaryExpression();
				expanded.setOperator(ArithmeticBinaryOperator.ADD);

				if (lhsExpanded) {
					expanded.setLhs(foldAndMultiplyFactors(factors, cloneExpression(factory, binaryExpr.getLhs())));
				} else {
					expanded.setLhs(expandProducts(binaryExpr.getLhs(), factors));
				}

				if (rhsExpanded) {
					expanded.setRhs(foldAndMultiplyFactors(factors, cloneExpression(factory, binaryExpr.getRhs())));
				} else {
					expanded.setRhs(expandProducts(binaryExpr.getRhs(), factors));
				}
				return expanded;
			}
			case MULTIPLY -> {
				final boolean lhsConst = (GipsTransformationUtils
						.isConstantExpression(binaryExpr.getLhs()) == ArithmeticExpressionType.constant);
				final boolean rhsConst = (GipsTransformationUtils
						.isConstantExpression(binaryExpr.getRhs()) == ArithmeticExpressionType.constant);

				final boolean lhsSum = isSetSummation(binaryExpr.getLhs());
				final boolean rhsSum = isSetSummation(binaryExpr.getRhs());

				Set<ArithmeticExpression> currentFactors = new LinkedHashSet<>();
				currentFactors.addAll(factors);

				// Sanity Check: If both expressions are constant, there is nothing else to do:
				if (lhsConst && rhsConst)
					return foldAndMultiplyFactors(currentFactors, cloneExpression(factory, binaryExpr));

				// Sanity Check: If both expressions are non-constant throw error.
				if (!lhsConst && !rhsConst)
					throw new UnsupportedOperationException(
							"Two variable references may not be multiplied with each other.");

				// If lhs is constant and rhs is non-constant and not expanded or a summation ->
				// expand
				if (lhsConst && !rhsConst && (!rhsExpanded || rhsSum)) {
					currentFactors.add(binaryExpr.getLhs());
					return expandProducts(binaryExpr.getRhs(), currentFactors);
				}

				// If rhs is constant and lhs is non-constant and not expanded or a summation ->
				// expand
				if (rhsConst && !lhsConst && (!lhsExpanded || lhsSum)) {
					currentFactors.add(binaryExpr.getRhs());
					return expandProducts(binaryExpr.getLhs(), currentFactors);
				}

				// Else -> do nothing
				return foldAndMultiplyFactors(currentFactors, cloneExpression(factory, binaryExpr));
			}
			default -> {
				return foldAndMultiplyFactors(factors, cloneExpression(factory, binaryExpr));
			}
			}
		} else if (expression instanceof ArithmeticUnaryExpression unaryExpr) {
			return foldAndMultiplyFactors(factors, cloneExpression(factory, unaryExpr));
		} else if (expression instanceof ConstantReference reference) {
			if (reference.getSetExpression() != null && reference.getSetExpression().getSetReduce() != null
					&& reference.getSetExpression().getSetReduce() instanceof SetSummation) {
				// TODO: This might be a critical point in case nested sums are used.
				ConstantReference cr = (ConstantReference) cloneExpression(factory, (EObject) reference);
				SetSummation mss = (SetSummation) cr.getSetExpression().getSetReduce();
				return wrapTermsIntoNewSum(expandProducts(mss.getExpression(), factors), cr);
			} else {
				return foldAndMultiplyFactors(factors, cloneExpression(factory, (ArithmeticExpression) reference));
			}
		} else if (expression instanceof ValueExpression valExpr) {
			if (valExpr.getSetExpression() != null && valExpr.getSetExpression().getSetReduce() != null
					&& valExpr.getSetExpression().getSetReduce() instanceof SetSummation) {
				// TODO: This might be a critical point in case nested sums are used.
				ValueExpression mve = (ValueExpression) cloneExpression(factory, valExpr);
				SetSummation mss = (SetSummation) mve.getSetExpression().getSetReduce();
				return wrapTermsIntoNewSum(expandProducts(mss.getExpression(), factors), mve);
			} else {
				return foldAndMultiplyFactors(factors, cloneExpression(factory, valExpr));
			}
		} else if (expression instanceof LinearFunctionReference function) {
			throw new IllegalArgumentException(
					"There must be no linear function references in constraints: " + function);
		} else {
			// Case: Literals, Constants and Constant references
			return foldAndMultiplyFactors(factors, cloneExpression(factory, expression));
		}
	}

	protected boolean isSetSummation(final ArithmeticExpression expression) {
		if (expression instanceof ArithmeticBinaryExpression abe) {
			if (abe.getOperator() == ArithmeticBinaryOperator.ADD
					|| abe.getOperator() == ArithmeticBinaryOperator.SUBTRACT) {
				return true;
			}
		}

		if (expression instanceof ConstantReference cr && cr.getSetExpression() != null
				&& cr.getSetExpression().getSetReduce() != null
				&& cr.getSetExpression().getSetReduce() instanceof SetSummation) {
			return true;
		}

		return (expression instanceof ValueExpression ve && ve.getSetExpression() != null
				&& ve.getSetExpression().getSetReduce() != null
				&& ve.getSetExpression().getSetReduce() instanceof SetSummation);
	}

	protected ArithmeticExpression foldAndMultiplyFactors(Collection<ArithmeticExpression> factors,
			ArithmeticExpression expression) {
		if (factors.isEmpty())
			return expression;

		ArithmeticBinaryExpression root = factory.createArithmeticBinaryExpression();
		root.setOperator(ArithmeticBinaryOperator.MULTIPLY);
		root.setRhs(expression);

		ArithmeticBinaryExpression current = root;
		Iterator<ArithmeticExpression> factorItr = factors.iterator();

		while (factorItr.hasNext()) {
			ArithmeticBinaryExpression next = factory.createArithmeticBinaryExpression();
			next.setOperator(ArithmeticBinaryOperator.MULTIPLY);

			ArithmeticExpression factor = factorItr.next();
			if (factorItr.hasNext()) {
				current.setLhs(next);
				next.setRhs(cloneExpression(factory, factor));
				current = next;
			} else {
				current.setLhs(cloneExpression(factory, factor));
			}
		}

		return root;
	}

	protected boolean isExpanded(final ArithmeticExpression expression, final boolean traversedProduct) {
		if (expression instanceof ArithmeticBinaryExpression binaryExpr) {
			switch (binaryExpr.getOperator()) {
			case ADD:
				if (traversedProduct) {
					return false;
				} else {
					return isExpanded(binaryExpr.getLhs(), traversedProduct)
							&& isExpanded(binaryExpr.getRhs(), traversedProduct);
				}
			case MULTIPLY:
				final boolean lhsConst = (GipsTransformationUtils
						.isConstantExpression(binaryExpr.getLhs()) == ArithmeticExpressionType.constant);
				final boolean rhsConst = (GipsTransformationUtils
						.isConstantExpression(binaryExpr.getRhs()) == ArithmeticExpressionType.constant);

				boolean lhsExpanded = isExpanded(binaryExpr.getLhs(), true);
				boolean rhsExpanded = isExpanded(binaryExpr.getRhs(), true);

				final boolean lhsSum = isSetSummation(binaryExpr.getLhs());
				final boolean rhsSum = isSetSummation(binaryExpr.getRhs());

				// Sanity Check: If both expressions are constant, there is nothing else to do:
				if (lhsConst && rhsConst)
					return true;

				// Sanity Check: If both expressions are non-constant throw error.
				if (!lhsConst && !rhsConst)
					throw new UnsupportedOperationException(
							"Two variable references may not be multiplied with each other.");

				// If lhs is constant and rhs is non-constant and not expanded or a summation ->
				// expand
				if (lhsConst && !rhsConst && (!rhsExpanded || rhsSum)) {
					return false;
				}

				// If rhs is constant and lhs is non-constant and not expanded or a summation ->
				// expand
				if (rhsConst && !lhsConst && (!lhsExpanded || lhsSum)) {
					return false;
				}

				// Else -> do nothing
				return true;
			// POW, DIVIDE, LOG can never be expanded
			case POW, DIVIDE, LOG:
				return traversedProduct;
			// SIN, COS, etc. will be handled by the next `else if`
			default:
				return isExpanded(binaryExpr.getLhs(), traversedProduct)
						&& isExpanded(binaryExpr.getRhs(), traversedProduct);
			}
		} else if (expression instanceof ArithmeticUnaryExpression) {
			return true;
		} else if (expression instanceof ConstantReference reference) {
			if (reference.getSetExpression() != null && reference.getSetExpression().getSetReduce() != null
					&& reference.getSetExpression().getSetReduce() instanceof SetSummation sum) {
				if (traversedProduct) {
					return false;
				} else {
					return isExpanded(sum.getExpression(), traversedProduct);
				}
			} else {
				return true;
			}
		} else if (expression instanceof ValueExpression valExpr) {
			if (valExpr.getSetExpression() != null && valExpr.getSetExpression().getSetReduce() != null
					&& valExpr.getSetExpression().getSetReduce() instanceof SetSummation sum) {
				if (traversedProduct) {
					return false;
				} else {
					return isExpanded(sum.getExpression(), traversedProduct);
				}
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	protected ArithmeticExpression wrapTermsIntoNewSum(final ArithmeticExpression transformed,
			final ValueExpression sumTemplate) {
		ArithmeticExpression clone = null;
		if (transformed instanceof ArithmeticBinaryExpression binary) {
			switch (binary.getOperator()) {
			case ADD:
			case SUBTRACT:
				ArithmeticBinaryExpression cb = factory.createArithmeticBinaryExpression();
				clone = cb;
				cb.setOperator(binary.getOperator());
				cb.setLhs(wrapTermsIntoNewSum(binary.getLhs(), sumTemplate));
				cb.setRhs(wrapTermsIntoNewSum(binary.getRhs(), sumTemplate));
				break;
			default:
				ValueExpression cs = (ValueExpression) cloneExpression(factory, sumTemplate);
				SetSummation sum = (SetSummation) cs.getSetExpression().getSetReduce();
				sum.setExpression(cloneExpression(factory, binary));
				clone = cs;
			}
		} else if (transformed instanceof ArithmeticUnaryExpression unary) {
			ValueExpression cs = (ValueExpression) cloneExpression(factory, sumTemplate);
			SetSummation sum = (SetSummation) cs.getSetExpression().getSetReduce();
			sum.setExpression(cloneExpression(factory, unary));
			clone = cs;
		} else if (transformed instanceof ArithmeticLiteral literal) {
			ValueExpression cs = (ValueExpression) cloneExpression(factory, sumTemplate);
			SetSummation sum = (SetSummation) cs.getSetExpression().getSetReduce();
			sum.setExpression(cloneExpression(factory, literal));
			clone = cs;
		} else if (transformed instanceof ConstantLiteral constant) {
			ValueExpression cs = (ValueExpression) cloneExpression(factory, sumTemplate);
			SetSummation sum = (SetSummation) cs.getSetExpression().getSetReduce();
			sum.setExpression(cloneExpression(factory, (ArithmeticExpression) constant));
			clone = cs;
		} else if (transformed instanceof ConstantReference reference) {
			ValueExpression cs = (ValueExpression) cloneExpression(factory, sumTemplate);
			SetSummation sum = (SetSummation) cs.getSetExpression().getSetReduce();
			sum.setExpression(cloneExpression(factory, (ArithmeticExpression) reference));
			clone = cs;
		} else if (transformed instanceof LinearFunctionReference function) {
			throw new IllegalArgumentException(
					"There must be no linear function references in constraints: " + function);
		} else if (transformed instanceof ValueExpression val) {
			ValueExpression cs = (ValueExpression) cloneExpression(factory, sumTemplate);
			SetSummation sum = (SetSummation) cs.getSetExpression().getSetReduce();
			sum.setExpression(cloneExpression(factory, val));
			clone = cs;
		} else {
			throw new UnsupportedOperationException("Unknown arithmetic expression type: " + transformed);
		}
		return clone;
	}

	protected ArithmeticExpression wrapTermsIntoNewSum(final ArithmeticExpression transformed,
			final ConstantReference sumTemplate) {
		ArithmeticExpression clone = null;
		if (transformed instanceof ArithmeticBinaryExpression binary) {
			switch (binary.getOperator()) {
			case ADD:
			case SUBTRACT:
				ArithmeticBinaryExpression cb = factory.createArithmeticBinaryExpression();
				clone = cb;
				cb.setOperator(binary.getOperator());
				cb.setLhs(wrapTermsIntoNewSum(binary.getLhs(), sumTemplate));
				cb.setRhs(wrapTermsIntoNewSum(binary.getRhs(), sumTemplate));
				break;
			default:
				ConstantReference cs = (ConstantReference) cloneExpression(factory, (EObject) sumTemplate);
				SetSummation sum = (SetSummation) cs.getSetExpression().getSetReduce();
				sum.setExpression(cloneExpression(factory, binary));
				clone = cs;
			}
		} else if (transformed instanceof ArithmeticUnaryExpression unary) {
			ConstantReference cs = (ConstantReference) cloneExpression(factory, (EObject) sumTemplate);
			SetSummation sum = (SetSummation) cs.getSetExpression().getSetReduce();
			sum.setExpression(cloneExpression(factory, unary));
			clone = cs;
		} else if (transformed instanceof ArithmeticLiteral literal) {
			ConstantReference cs = (ConstantReference) cloneExpression(factory, (EObject) sumTemplate);
			SetSummation sum = (SetSummation) cs.getSetExpression().getSetReduce();
			sum.setExpression(cloneExpression(factory, literal));
			clone = cs;
		} else if (transformed instanceof ConstantLiteral constant) {
			ConstantReference cs = (ConstantReference) cloneExpression(factory, (EObject) sumTemplate);
			SetSummation sum = (SetSummation) cs.getSetExpression().getSetReduce();
			sum.setExpression(cloneExpression(factory, (ArithmeticExpression) constant));
			clone = cs;
		} else if (transformed instanceof ConstantReference reference) {
			ConstantReference cs = (ConstantReference) cloneExpression(factory, (EObject) sumTemplate);
			SetSummation sum = (SetSummation) cs.getSetExpression().getSetReduce();
			sum.setExpression(cloneExpression(factory, (ArithmeticExpression) reference));
			clone = cs;
		} else if (transformed instanceof LinearFunctionReference function) {
			throw new IllegalArgumentException(
					"There must be no linear function references in constraints: " + function);
		} else if (transformed instanceof ValueExpression val) {
			ConstantReference cs = (ConstantReference) cloneExpression(factory, (EObject) sumTemplate);
			SetSummation sum = (SetSummation) cs.getSetExpression().getSetReduce();
			sum.setExpression(cloneExpression(factory, val));
			clone = cs;
		} else {
			throw new UnsupportedOperationException("Unknown arithmetic expression type: " + transformed);
		}
		return clone;
	}

	static public EObject cloneExpression(GipsIntermediateFactory factory, final EObject expr) {
		if (expr instanceof ArithmeticExpression ae) {
			return cloneExpression(factory, ae);
		} else {
			return cloneExpression(factory, (BooleanExpression) expr);
		}
	}

	static public BooleanExpression cloneExpression(GipsIntermediateFactory factory, final BooleanExpression expr) {
		if (expr instanceof BooleanBinaryExpression binary) {
			BooleanBinaryExpression clone = factory.createBooleanBinaryExpression();
			switch (binary.getOperator()) {
			case AND -> clone.setOperator(BooleanBinaryOperator.AND);
			case OR -> clone.setOperator(BooleanBinaryOperator.OR);
			case XOR -> clone.setOperator(BooleanBinaryOperator.XOR);
			}
			clone.setLhs(cloneExpression(factory, binary.getLhs()));
			clone.setRhs(cloneExpression(factory, binary.getRhs()));
			return clone;
		} else if (expr instanceof BooleanUnaryExpression unary) {
			BooleanUnaryExpression clone = factory.createBooleanUnaryExpression();
			switch (unary.getOperator()) {
			case BRACKET -> clone.setOperator(BooleanUnaryOperator.BRACKET);
			case NOT -> clone.setOperator(BooleanUnaryOperator.NOT);
			}
			clone.setOperand(cloneExpression(factory, unary.getOperand()));
			return clone;
		} else if (expr instanceof BooleanLiteral literal) {
			BooleanLiteral clone = factory.createBooleanLiteral();
			clone.setLiteral(literal.isLiteral());
			return clone;
		} else if (expr instanceof RelationalExpression relation) {
			return cloneExpression(factory, relation);
		} else if (expr instanceof ArithmeticExpression arithmetic) {
			return (BooleanExpression) cloneExpression(factory, arithmetic);
		} else {
			throw new UnsupportedOperationException("Unknown boolean expression type: " + expr);
		}
	}

	static public RelationalExpression cloneExpression(GipsIntermediateFactory factory,
			final RelationalExpression expr) {
		RelationalExpression clone = factory.createRelationalExpression();
		switch (expr.getOperator()) {
		case EQUAL -> clone.setOperator(RelationalOperator.EQUAL);
		case GREATER -> clone.setOperator(RelationalOperator.GREATER);
		case GREATER_OR_EQUAL -> clone.setOperator(RelationalOperator.GREATER_OR_EQUAL);
		case LESS -> clone.setOperator(RelationalOperator.LESS);
		case LESS_OR_EQUAL -> clone.setOperator(RelationalOperator.LESS_OR_EQUAL);
		case NOT_EQUAL -> clone.setOperator(RelationalOperator.NOT_EQUAL);
		}

		if (expr.getLhs() instanceof ArithmeticExpression arithmetic) {
			clone.setLhs(cloneExpression(factory, arithmetic));
		} else {
			clone.setLhs(cloneExpression(factory, (BooleanExpression) expr.getLhs()));
		}

		if (expr.getRhs() instanceof ArithmeticExpression arithmetic) {
			clone.setRhs(cloneExpression(factory, arithmetic));
		} else {
			clone.setRhs(cloneExpression(factory, (BooleanExpression) expr.getRhs()));
		}

		clone.setRequiresComparables(expr.isRequiresComparables());

		return clone;
	}

	static public ArithmeticExpression cloneExpression(GipsIntermediateFactory factory,
			final ArithmeticExpression expr) {
		ArithmeticExpression clone = null;
		if (expr instanceof ArithmeticBinaryExpression binary) {
			ArithmeticBinaryExpression cb = factory.createArithmeticBinaryExpression();
			clone = cb;
			cb.setOperator(binary.getOperator());
			cb.setLhs(cloneExpression(factory, binary.getLhs()));
			cb.setRhs(cloneExpression(factory, binary.getRhs()));
		} else if (expr instanceof ArithmeticUnaryExpression unary) {
			ArithmeticUnaryExpression cu = factory.createArithmeticUnaryExpression();
			clone = cu;
			cu.setOperator(unary.getOperator());
			cu.setOperand(cloneExpression(factory, unary.getOperand()));
		} else if (expr instanceof ArithmeticLiteral literal) {
			if (literal instanceof IntegerLiteral il) {
				IntegerLiteral ci = factory.createIntegerLiteral();
				clone = ci;
				ci.setLiteral(il.getLiteral());
			} else if (literal instanceof DoubleLiteral dl) {
				DoubleLiteral cd = factory.createDoubleLiteral();
				clone = cd;
				cd.setLiteral(dl.getLiteral());
			} else {
				throw new UnsupportedOperationException("Unknown arithmetic expression type: " + expr);
			}
		} else if (expr instanceof ConstantLiteral literal) {
			ConstantLiteral cl = factory.createConstantLiteral();
			switch (literal.getConstant()) {
			case E -> cl.setConstant(ConstantValue.E);
			case PI -> cl.setConstant(ConstantValue.PI);
			case NULL -> cl.setConstant(ConstantValue.NULL);
			}
			clone = cl;
		} else if (expr instanceof LinearFunctionReference function) {
			LinearFunctionReference reference = factory.createLinearFunctionReference();
			reference.setFunction(function.getFunction());
			clone = reference;
		} else if (expr instanceof ConstantReference constant) {
			ConstantReference reference = factory.createConstantReference();
			reference.setConstant(constant.getConstant());
			if (constant.getSetExpression() != null) {
				reference.setSetExpression(cloneExpression(factory, constant.getSetExpression()));
			}
			clone = reference;
		} else if (expr instanceof ValueExpression val) {
			clone = cloneExpression(factory, val);
		} else {
			throw new UnsupportedOperationException("Unknown arithmetic expression type: " + expr);
		}
		return clone;
	}

	static public ValueExpression cloneExpression(GipsIntermediateFactory factory, final ValueExpression value) {
		ValueExpression clone = null;
		if (value instanceof MappingReference mapping) {
			MappingReference ref = factory.createMappingReference();
			ref.setMapping(mapping.getMapping());
			clone = ref;
		} else if (value instanceof TypeReference type) {
			TypeReference ref = factory.createTypeReference();
			ref.setType(type.getType());
			clone = ref;
		} else if (value instanceof PatternReference pattern) {
			PatternReference ref = factory.createPatternReference();
			ref.setPattern(pattern.getPattern());
			clone = ref;
		} else if (value instanceof RuleReference rule) {
			RuleReference ref = factory.createRuleReference();
			ref.setRule(rule.getRule());
			ref.setContextPattern(rule.getContextPattern());
			clone = ref;
		} else if (value instanceof NodeReference node) {
			NodeReference ref = factory.createNodeReference();
			ref.setNode(node.getNode());
			if (node.getAttribute() != null) {
				ref.setAttribute(cloneExpression(factory, node.getAttribute()));
			}
			ref.setLocal(node.isLocal());
			clone = ref;
		} else if (value instanceof AttributeReference attribute) {
			AttributeReference ref = factory.createAttributeReference();
			ref.setAttribute(cloneExpression(factory, attribute.getAttribute()));
			ref.setLocal(attribute.isLocal());
			clone = ref;
		} else if (value instanceof VariableReference variable) {
			VariableReference ref = factory.createVariableReference();
			ref.setVariable(variable.getVariable());
			ref.setLocal(variable.isLocal());
			clone = ref;
		} else if (value instanceof ContextReference context) {
			ContextReference ref = factory.createContextReference();
			ref.setLocal(context.isLocal());
			clone = ref;
		} else {
			throw new UnsupportedOperationException("Unknown arithmetic expression type: " + value);
		}

		if (value.getSetExpression() != null) {
			clone.setSetExpression(cloneExpression(factory, value.getSetExpression()));
		}
		return clone;
	}

	static public AttributeExpression cloneExpression(GipsIntermediateFactory factory,
			final AttributeExpression attribute) {
		AttributeExpression clone = factory.createAttributeExpression();
		clone.setFeature(attribute.getFeature());
		if (attribute.getNext() != null) {
			clone.setNext(cloneExpression(factory, attribute.getNext()));
		}
		return clone;
	}

	static public SetExpression cloneExpression(GipsIntermediateFactory factory, final SetExpression set) {
		SetExpression clone = factory.createSetExpression();
		if (set.getSetOperation() != null) {
			clone.setSetOperation(cloneExpression(factory, set.getSetOperation()));
		}
		if (set.getSetReduce() != null) {
			clone.setSetReduce(cloneExpression(factory, set.getSetReduce()));
		}
		return clone;
	}

	static public SetOperation cloneExpression(GipsIntermediateFactory factory, final SetOperation set) {
		SetOperation clone = null;
		if (set instanceof SetFilter filter) {
			SetFilter fc = factory.createSetFilter();
			fc.setExpression(cloneExpression(factory, filter.getExpression()));
			clone = fc;
		} else if (set instanceof SetTypeSelect select) {
			SetTypeSelect stc = factory.createSetTypeSelect();
			stc.setType(select.getType());
			clone = stc;
		} else if (set instanceof SetSort sort) {
			SetSort ssc = factory.createSetSort();
			ssc.setPredicate(cloneExpression(factory, sort.getPredicate()));
			clone = ssc;
		} else if (set instanceof SetConcatenation cat) {
			SetConcatenation scc = factory.createSetConcatenation();
			scc.setOther(cloneExpression(factory, cat.getOther()));
			switch (cat.getOperator()) {
			case APPEND -> scc.setOperator(ConcatenationOperator.APPEND);
			case PREPEND -> scc.setOperator(ConcatenationOperator.PREPEND);
			}
			clone = scc;
		} else if (set instanceof SetSimpleOperation operation) {
			SetSimpleOperation ssc = factory.createSetSimpleOperation();
			switch (operation.getOperator()) {
			case UNIQUE -> ssc.setOperator(SetOperator.UNIQUE);
			}
			clone = ssc;
		} else if (set instanceof SetTransformation transform) {
			SetTransformation stc = factory.createSetTransformation();
			stc.setExpression(cloneExpression(factory, transform.getExpression()));
			clone = stc;
		} else {
			throw new UnsupportedOperationException("Unknown set operation type: " + set);
		}
		if (set.getNext() != null) {
			clone.setNext(cloneExpression(factory, set.getNext()));
		}
		return clone;
	}

	static public SetReduce cloneExpression(GipsIntermediateFactory factory, final SetReduce set) {
		SetReduce clone = null;
		if (set instanceof SetSummation sum) {
			SetSummation ssc = factory.createSetSummation();
			ssc.setExpression(cloneExpression(factory, sum.getExpression()));
			clone = ssc;
		} else if (set instanceof SetSimpleSelect select) {
			SetSimpleSelect ssc = factory.createSetSimpleSelect();
			switch (select.getOperator()) {
			case ANY -> ssc.setOperator(SelectOperator.ANY);
			case FIRST -> ssc.setOperator(SelectOperator.FIRST);
			case LAST -> ssc.setOperator(SelectOperator.LAST);
			}
			clone = ssc;
		} else if (set instanceof SetTypeQuery query) {
			SetTypeQuery stc = factory.createSetTypeQuery();
			stc.setType(query.getType());
			clone = stc;
		} else if (set instanceof SetElementQuery query) {
			SetElementQuery sec = factory.createSetElementQuery();
			sec.setElement(cloneExpression(factory, query.getElement()));
			clone = sec;
		} else if (set instanceof SetSimpleQuery query) {
			SetSimpleQuery ssc = factory.createSetSimpleQuery();
			switch (query.getOperator()) {
			case COUNT -> ssc.setOperator(QueryOperator.COUNT);
			case EMPTY -> ssc.setOperator(QueryOperator.EMPTY);
			case NOT_EMPTY -> ssc.setOperator(QueryOperator.NOT_EMPTY);
			}
			clone = ssc;
		} else {
			throw new UnsupportedOperationException("Unknown set reduction operation type: " + set);
		}
		return clone;
	}

	public static StringBuilder parseToString(final ArithmeticExpression expr) throws ParserException {
		StringBuilder sb = new StringBuilder();
		if (expr instanceof ArithmeticBinaryExpression binaryExpr) {
			switch (binaryExpr.getOperator()) {
			case ADD -> {
				sb.append(parseToString(binaryExpr.getLhs()));
				sb.append(" + ");
				sb.append(parseToString(binaryExpr.getRhs()));
			}
			case DIVIDE -> {
				sb.append(parseToString(binaryExpr.getLhs()));
				sb.append(" / (");
				sb.append(parseToString(binaryExpr.getRhs()) + ")");
			}
			case LOG -> {
				// Log-expressions will not be transformed
				sb.append(expr);
			}
			case MULTIPLY -> {
				sb.append(parseToString(binaryExpr.getLhs()));
				sb.append(" * ");
				sb.append(parseToString(binaryExpr.getRhs()));
			}
			case POW -> {
				// Exp-expressions will not be transformed
				sb.append(expr);
			}
			case SUBTRACT -> {
				sb.append(parseToString(binaryExpr.getLhs()));
				sb.append(" - (");
				sb.append(parseToString(binaryExpr.getRhs()) + ")");
			}
			default -> {
				throw new UnsupportedOperationException("Unknown arithmetic operator: " + binaryExpr.getOperator());
			}
			}
		} else if (expr instanceof ArithmeticUnaryExpression unaryExpr) {
			switch (unaryExpr.getOperator()) {
			case ABSOLUTE -> {
				// Abs-expressions will not be transformed
				sb.append("ABS_" + expr);
			}
			case BRACKET -> {
				sb.append("(" + parseToString(unaryExpr.getOperand()) + ")");
			}
			case COSINE -> {
				// cos-expressions will not be transformed
				sb.append("COS_" + expr);
			}
			case NEGATE -> {
				sb.append("~(" + parseToString(unaryExpr.getOperand()) + ")");
			}
			case SINE -> {
				// sin-expressions will not be transformed
				sb.append("SIN_" + expr);
			}
			case SQRT -> {
				// sqrt-expressions will not be transformed
				sb.append("SQRT_" + expr);
			}
			default -> {
				throw new UnsupportedOperationException("Unknown arithmetic operator: " + unaryExpr.getOperator());
			}
			}

		} else if (expr instanceof ValueExpression valExpr) {
			// CASE: SUM-Expressions
			if (valExpr.getSetExpression() != null && valExpr.getSetExpression().getSetReduce() != null
					&& valExpr.getSetExpression().getSetReduce() instanceof SetSummation sum) {
				sb.append("SUM_[" + parseToString(sum.getExpression()) + "]");
			} else {
				sb.append("VAL_" + valExpr.eClass().getName() + "(" + valExpr.hashCode() + ")");
			}
		} else if (expr instanceof ConstantLiteral literal) {
			switch (literal.getConstant()) {
			case E -> sb.append("E_Constant");
			case NULL -> sb.append("NULL_Constant");
			case PI -> sb.append("PI_Constant");
			}
		} else if (expr instanceof ConstantReference reference) {
			if (reference.getConstant().getExpression() instanceof ArithmeticExpression ae)
				sb.append(parseToString(ae));
		} else {
			// CASE: Literals
			if (expr instanceof DoubleLiteral d) {
				sb.append(d.getLiteral());
			} else if (expr instanceof IntegerLiteral i) {
				sb.append(i.getLiteral());
			} else {
				sb.append("LIT");
			}
		}

		return sb;
	}
}
