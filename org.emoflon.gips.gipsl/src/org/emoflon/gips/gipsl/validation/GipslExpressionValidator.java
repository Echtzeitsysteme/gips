package org.emoflon.gips.gipsl.validation;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticBracket;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticConstant;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExponential;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpression;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticProduct;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticSum;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticUnary;
import org.emoflon.gips.gipsl.gipsl.GipsAttributeExpression;
import org.emoflon.gips.gipsl.gipsl.GipsAttributeLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanBracket;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanConjunction;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanDisjunction;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanImplication;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanNegation;
import org.emoflon.gips.gipsl.gipsl.GipsConcatenationOperation;
import org.emoflon.gips.gipsl.gipsl.GipsConstant;
import org.emoflon.gips.gipsl.gipsl.GipsConstantLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsConstantReference;
import org.emoflon.gips.gipsl.gipsl.GipsFilterOperation;
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunction;
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunctionReference;
import org.emoflon.gips.gipsl.gipsl.GipsLocalContextExpression;
import org.emoflon.gips.gipsl.gipsl.GipsMapping;
import org.emoflon.gips.gipsl.gipsl.GipsMappingExpression;
import org.emoflon.gips.gipsl.gipsl.GipsNodeExpression;
import org.emoflon.gips.gipsl.gipsl.GipsObjective;
import org.emoflon.gips.gipsl.gipsl.GipsPatternExpression;
import org.emoflon.gips.gipsl.gipsl.GipsProductOperator;
import org.emoflon.gips.gipsl.gipsl.GipsReduceOperation;
import org.emoflon.gips.gipsl.gipsl.GipsRelationalExpression;
import org.emoflon.gips.gipsl.gipsl.GipsRuleExpression;
import org.emoflon.gips.gipsl.gipsl.GipsSetElementExpression;
import org.emoflon.gips.gipsl.gipsl.GipsSetExpression;
import org.emoflon.gips.gipsl.gipsl.GipsSetOperation;
import org.emoflon.gips.gipsl.gipsl.GipsSimpleQuery;
import org.emoflon.gips.gipsl.gipsl.GipsSimpleSelect;
import org.emoflon.gips.gipsl.gipsl.GipsSortOperation;
import org.emoflon.gips.gipsl.gipsl.GipsSortPredicate;
import org.emoflon.gips.gipsl.gipsl.GipsSumOperation;
import org.emoflon.gips.gipsl.gipsl.GipsTransformOperation;
import org.emoflon.gips.gipsl.gipsl.GipsTypeExpression;
import org.emoflon.gips.gipsl.gipsl.GipsUnaryOperator;
import org.emoflon.gips.gipsl.gipsl.GipsValueExpression;
import org.emoflon.gips.gipsl.gipsl.GipsVariable;
import org.emoflon.gips.gipsl.gipsl.GipsVariableReferenceExpression;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;
import org.emoflon.gips.gipsl.gipsl.QueryOperator;
import org.emoflon.gips.gipsl.gipsl.RelationalOperator;
import org.emoflon.gips.gipsl.gipsl.impl.GipsConstantImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsObjectiveImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsSetExpressionImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsSumOperationImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsValueExpressionImpl;
import org.emoflon.gips.gipsl.scoping.GipslScopeContextUtil;

public final class GipslExpressionValidator {

	public static class ExpressionData {
		private final ExpressionMutability mutability;
		private final ExpressionType type;
		private final boolean isMany;

		public static ExpressionData asUnknown() {
			return new ExpressionData(ExpressionMutability.Unknown, ExpressionType.Unknown, false);
		}

		public static ExpressionData asError() {
			return new ExpressionData(ExpressionMutability.Unknown, ExpressionType.Error, false);
		}

		public static ExpressionData asSingleConstant(ExpressionType type) {
			return new ExpressionData(ExpressionMutability.Constant, type, false);
		}

		public static ExpressionData asSingleVariable(ExpressionType type) {
			return new ExpressionData(ExpressionMutability.Variable, type, false);
		}

		public static ExpressionData asManyConstant(ExpressionType type) {
			return new ExpressionData(ExpressionMutability.Constant, type, true);
		}

		public ExpressionData(ExpressionMutability mutability, ExpressionType type, boolean isMany) {
			this.mutability = mutability;
			this.type = type;
			this.isMany = isMany;
		}

		public ExpressionData with(ExpressionMutability mutability) {
			return new ExpressionData(mutability, type, isMany);
		}

		public ExpressionMutability getMutability() {
			return mutability;
		}

		public ExpressionType getType() {
			return type;
		}

		public boolean isVariable() {
			return mutability == ExpressionMutability.Variable;
		}

		public boolean isConstant() {
			return mutability == ExpressionMutability.Constant;
		}

		public boolean isMany() {
			return isMany;
		}

		public boolean isError() {
			return type == ExpressionType.Error;
		}

		public boolean isUnknown() {
			return type == ExpressionType.Unknown || mutability == ExpressionMutability.Unknown;
		}

	}

	public static Collection<Runnable> checkBooleanExpression(final GipsBooleanExpression expression) {
		Collection<Runnable> errors = Collections.synchronizedCollection(new LinkedList<>());
		ExpressionData result = evaluate(expression, errors);

		if (result.isUnknown()) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.BOOL_EXPR_CONTAINS_UNKNOWNS, //
						expression, //
						null //
				);
			});
		}

		if (result.isError()) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.BOOL_EXPR_CONTAINS_ERRORS, //
						expression, //
						null //
				);
			});
		}

		return errors;
	}

	public static ExpressionData evaluate(final GipsBooleanExpression expression, Collection<Runnable> errors) {
		return switch (expression) {
		case GipsBooleanImplication implication -> evaluate(implication, errors);
		case GipsBooleanDisjunction disjunction -> evaluate(disjunction, errors);
		case GipsBooleanConjunction conjunction -> evaluate(conjunction, errors);
		case GipsBooleanNegation negation -> evaluate(negation, errors);
		case GipsBooleanBracket bracket -> evaluate(bracket, errors);
		case GipsBooleanLiteral literal -> evaluate(literal, errors);
		case GipsRelationalExpression relation -> evaluate(relation, errors);
		case GipsArithmeticExpression arithmetic -> evaluate(arithmetic, errors);
		case null, default -> {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.BOOL_EXPR_EVAL_ERROR_MESSAGE, //
						expression, //
						null //
				);
			});
			yield ExpressionData.asError();
		}
		};
	}

	public static ExpressionData evaluate(final GipsBooleanLiteral implication, Collection<Runnable> errors) {
		return ExpressionData.asSingleConstant(ExpressionType.Boolean);
	}

	public static ExpressionData evaluate(final GipsBooleanImplication implication, Collection<Runnable> errors) {
		ExpressionData lhs = evaluate(implication.getLeft(), errors);
		ExpressionData rhs = evaluate(implication.getRight(), errors);

		if (lhs.getType() != ExpressionType.Boolean) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.BOOL_EXPR_EVAL_ERROR_MESSAGE, //
						implication, //
						GipslPackage.Literals.GIPS_BOOLEAN_IMPLICATION__LEFT //
				);
			});
		}

		if (rhs.getType() != ExpressionType.Boolean) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.BOOL_EXPR_EVAL_ERROR_MESSAGE, //
						implication, //
						GipslPackage.Literals.GIPS_BOOLEAN_IMPLICATION__RIGHT //
				);
			});
		}

		if (lhs.getType() != ExpressionType.Boolean || rhs.getType() != ExpressionType.Boolean)
			return ExpressionData.asError();

		return ExpressionData.asSingleConstant(ExpressionType.Boolean);
	}

	public static ExpressionData evaluate(final GipsBooleanDisjunction disjunction, Collection<Runnable> errors) {
		ExpressionData lhs = evaluate(disjunction.getLeft(), errors);
		ExpressionData rhs = evaluate(disjunction.getRight(), errors);

		if (lhs.getType() != ExpressionType.Boolean) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.BOOL_EXPR_EVAL_ERROR_MESSAGE, //
						disjunction, //
						GipslPackage.Literals.GIPS_BOOLEAN_DISJUNCTION__LEFT //
				);
			});
		}

		if (rhs.getType() != ExpressionType.Boolean) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.BOOL_EXPR_EVAL_ERROR_MESSAGE, //
						disjunction, //
						GipslPackage.Literals.GIPS_BOOLEAN_DISJUNCTION__RIGHT //
				);
			});
		}

		if (lhs.getType() != ExpressionType.Boolean || rhs.getType() != ExpressionType.Boolean)
			return ExpressionData.asError();

		return ExpressionData.asSingleConstant(ExpressionType.Boolean);
	}

	public static ExpressionData evaluate(final GipsBooleanConjunction conjunction, Collection<Runnable> errors) {
		ExpressionData lhs = evaluate(conjunction.getLeft(), errors);
		ExpressionData rhs = evaluate(conjunction.getRight(), errors);

		if (lhs.getType() != ExpressionType.Boolean) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.BOOL_EXPR_EVAL_ERROR_MESSAGE, //
						conjunction, //
						GipslPackage.Literals.GIPS_BOOLEAN_CONJUNCTION__LEFT //
				);
			});
		}

		if (rhs.getType() != ExpressionType.Boolean) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.BOOL_EXPR_EVAL_ERROR_MESSAGE, //
						conjunction, //
						GipslPackage.Literals.GIPS_BOOLEAN_CONJUNCTION__RIGHT //
				);
			});
		}

		if (lhs.getType() != ExpressionType.Boolean || rhs.getType() != ExpressionType.Boolean)
			return ExpressionData.asError();

		return ExpressionData.asSingleConstant(ExpressionType.Boolean);
	}

	public static ExpressionData evaluate(final GipsBooleanNegation negation, Collection<Runnable> errors) {
		ExpressionData operand = evaluate(negation.getOperand(), errors);

		if (operand.getType() != ExpressionType.Boolean) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.BOOL_EXPR_EVAL_ERROR_MESSAGE, //
						negation, //
						GipslPackage.Literals.GIPS_BOOLEAN_NEGATION__OPERAND //
				);
			});
			return ExpressionData.asError();
		}

		return ExpressionData.asSingleConstant(ExpressionType.Boolean);
	}

	public static Collection<Runnable> checkRelationalExpression(final GipsRelationalExpression expression) {
		Collection<Runnable> errors = Collections.synchronizedCollection(new LinkedList<>());
		ExpressionData result = evaluate(expression, errors);

		if (result.isUnknown()) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.REL_EXPR_CONTAINS_UNKNOWNS, //
						expression, //
						null //
				);
			});
		}

		if (result.isError()) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.REL_EXPR_CONTAINS_ERRORS, //
						expression, //
						null //
				);
			});
		}

		return errors;
	}

	public static ExpressionData evaluate(final GipsRelationalExpression expression, Collection<Runnable> errors) {
		ExpressionData lhs = evaluate(expression.getLeft(), errors);
		ExpressionData rhs = evaluate(expression.getRight(), errors);

		if (lhs.isUnknown() || lhs.isError() || lhs.isMany() || rhs.isUnknown() || rhs.isError() || rhs.isMany()) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.REL_EXPR_EVAL_ERROR_MESSAGE, //
						expression, //
						GipslPackage.Literals.GIPS_RELATIONAL_EXPRESSION__OPERATOR //
				);
			});
			return ExpressionData.asError();
		}

		if (lhs.getType() != rhs.getType()) {
			errors.add(() -> {
				GipslValidator.err( //
						String.format(GipslValidatorUtil.REL_EXPR_MISMATCH_ERROR_MESSAGE, lhs.getType(), rhs.getType()), //
						expression, //
						GipslPackage.Literals.GIPS_RELATIONAL_EXPRESSION__OPERATOR //
				);
			});
			return ExpressionData.asError();
		}

		return ExpressionData.asSingleConstant(ExpressionType.Boolean);
	}

	public static Collection<Runnable> checkArithmeticExpression(final GipsArithmeticExpression expression) {
		Collection<Runnable> errors = Collections.synchronizedCollection(new LinkedList<>());
		ExpressionData result = evaluate(expression, errors);

		if (result.isUnknown()) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_CONTAINS_UNKNOWNS, //
						expression, //
						null //
				);
			});
		}
		if (result.isError()) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_CONTAINS_ERRORS, //
						expression, //
						null //
				);
			});
		}

		if (expression == null || expression.eContainer() == null)
			return errors;

		if (expression.eContainer() instanceof GipsObjective obj && result.getType() != ExpressionType.Number) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.OBJECTIVE_EVAL_NOT_NUMBER_MESSAGE, //
						obj, //
						GipslPackage.Literals.GIPS_OBJECTIVE__EXPRESSION //
				);
			});
		}

		if (expression.eContainer() instanceof GipsLinearFunction fun && result.getType() != ExpressionType.Number) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.FUNCTION_EVAL_NOT_NUMBER_MESSAGE, //
						fun, //
						GipslPackage.Literals.GIPS_LINEAR_FUNCTION__EXPRESSION//
				);
			});
		}

		return errors;
	}

	public static ExpressionType evaluate(final GipsArithmeticExpression expression, Collection<Runnable> errors) {
		if (expression instanceof GipsArithmeticSum sum) {
			return evaluate(sum, errors);
		} else if (expression instanceof GipsArithmeticProduct product) {
			return evaluate(product, errors);
		} else if (expression instanceof GipsArithmeticExponential exponential) {
			return evaluate(exponential, errors);
		} else if (expression instanceof GipsArithmeticUnary unary) {
			return evaluate(unary, errors);
		} else if (expression instanceof GipsArithmeticBracket bracket) {
			return evaluate(bracket.getOperand(), errors);
		} else if (expression instanceof GipsValueExpression value) {
			return evaluate(value, errors);
		} else if (expression instanceof GipsLinearFunctionReference lfr) {
			// Check if the overall context is the objective function. The usage of an LFR
			// is not allowed in other contexts.
			Object container = GipslScopeContextUtil.getContainer(lfr, Set.of(GipsObjectiveImpl.class));
			if (container == null) {
				errors.add(() -> {
					GipslValidator.err( //
							GipslValidatorUtil.ARITH_EXPR_LFR_ERROR_MESSAGE, //
							lfr, //
							GipslPackage.Literals.GIPS_LINEAR_FUNCTION_REFERENCE__FUNCTION //
					);
				});
			}
			// Check if LFR is used in a set operation, which is not allowed.
			container = GipslScopeContextUtil.getContainer(lfr, Set.of(GipsSetExpressionImpl.class));

			if (container != null) {
				errors.add(() -> {
					GipslValidator.err( //
							GipslValidatorUtil.ARITH_EXPR_LFR_ERROR_MESSAGE, //
							lfr, //
							GipslPackage.Literals.GIPS_LINEAR_FUNCTION_REFERENCE__FUNCTION //
					);
				});
			}

			// For simplicity reasons we treat linear functions and variables as
			// functionally the same, since they share many constraints.
			return ExpressionType.Variable;
		} else if (expression instanceof GipsArithmeticLiteral) {
			return ExpressionType.Number;
		} else if (expression instanceof GipsArithmeticConstant constant) {
			if (constant.getValue() == GipsConstantLiteral.NULL) {
				return ExpressionType.Object;
			} else {
				return ExpressionType.Number;
			}
		} else if (expression instanceof GipsConstantReference reference) {
			if (reference.getConstant() == null)
				return ExpressionType.Unknown;

			if (reference.getConstant().getExpression() == null)
				return ExpressionType.Unknown;

			// Check for forbidden expressions, i.e., variable references, errors or
			// unknowns.
			ExpressionType type = evaluate(reference.getConstant().getExpression(), errors);
			if (type == ExpressionType.Variable) {
				errors.add(() -> {
					GipslValidator.err( //
							GipslValidatorUtil.CONSTANT_CONTAINS_VARIABLE, //
							reference, //
							GipslPackage.Literals.GIPS_CONSTANT_REFERENCE__CONSTANT //
					);
				});
				return ExpressionType.Variable;
			}
			if (type == ExpressionType.Error || type == ExpressionType.Unknown) {
				errors.add(() -> {
					GipslValidator.err( //
							GipslValidatorUtil.CONSTANT_CONTAINS_ERRORS, //
							reference, //
							GipslPackage.Literals.GIPS_CONSTANT_REFERENCE__CONSTANT //
					);
				});
				return ExpressionType.Error;
			}
			// Check if reference is not used in another constant definition
			GipsConstant root = (GipsConstant) GipslScopeContextUtil.getContainer(reference,
					Set.of(GipsConstantImpl.class));
			if (root != null) {
				errors.add(() -> {
					GipslValidator.err( //
							GipslValidatorUtil.CONSTANT_CONTAINS_CONSTANT, //
							root, //
							GipslPackage.Literals.GIPS_CONSTANT__EXPRESSION //
					);
				});
				return ExpressionType.Error;
			}

			if (reference.getSetExpression() != null) {
				type = evaluate(reference.getSetExpression(), errors);
			}

			return type;
		} else {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_EVAL_ERROR_MESSAGE, //
						expression, //
						null //
				);
			});
			return ExpressionType.Unknown;
		}
	}

	private static ExpressionType evaluate(GipsArithmeticUnary unary, Collection<Runnable> errors) {
		ExpressionType operand = evaluate(unary.getOperand(), errors);

		if (operand == ExpressionType.Number) {
			return ExpressionType.Number;
		}

		// Only negation is allowed for variables
		if (operand == ExpressionType.Variable && unary.getOperator() == GipsUnaryOperator.NEG) {
			return ExpressionType.Variable;
		}

		errors.add(() -> {
			GipslValidator.err( //
					GipslValidatorUtil.ARITH_EXPR_EVAL_ERROR_MESSAGE, //
					unary, //
					GipslPackage.Literals.GIPS_ARITHMETIC_UNARY__OPERAND //
			);
		});

		return ExpressionType.Error;
	}

	private static ExpressionType evaluate(GipsArithmeticExponential exponential, Collection<Runnable> errors) {
		ExpressionType lhs = evaluate(exponential.getLeft(), errors);
		ExpressionType rhs = evaluate(exponential.getRight(), errors);

		if (lhs != ExpressionType.Number) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_EVAL_ERROR_MESSAGE, //
						exponential, //
						GipslPackage.Literals.GIPS_ARITHMETIC_EXPONENTIAL__LEFT //
				);
			});
		}
		if (rhs != ExpressionType.Number) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_EVAL_ERROR_MESSAGE, //
						exponential, //
						GipslPackage.Literals.GIPS_ARITHMETIC_EXPONENTIAL__RIGHT //
				);
			});
		}

		// Check for non-linear expressions with variables.
		if (lhs == ExpressionType.Variable || rhs == ExpressionType.Variable) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_NONLINEAR_ERROR_MESSAGE, //
						exponential, //
						GipslPackage.Literals.GIPS_ARITHMETIC_EXPONENTIAL__OPERATOR //
				);
			});
			return ExpressionType.Error;
		}

		if (lhs == rhs && lhs == ExpressionType.Number) {
			return lhs;
		} else {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_MISMATCH_ERROR_MESSAGE, //
						exponential, //
						GipslPackage.Literals.GIPS_ARITHMETIC_EXPONENTIAL__OPERATOR //
				);
			});
			return ExpressionType.Error;
		}
	}

	private static ExpressionType evaluate(GipsArithmeticProduct product, Collection<Runnable> errors) {
		ExpressionType lhs = evaluate(product.getLeft(), errors);
		ExpressionType rhs = evaluate(product.getRight(), errors);

		if (lhs != ExpressionType.Number && lhs != ExpressionType.Variable) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_EVAL_ERROR_MESSAGE, //
						product, //
						GipslPackage.Literals.GIPS_ARITHMETIC_PRODUCT__LEFT //
				);
			});
		}
		if (rhs != ExpressionType.Number && rhs != ExpressionType.Variable) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_EVAL_ERROR_MESSAGE, //
						product, //
						GipslPackage.Literals.GIPS_ARITHMETIC_PRODUCT__RIGHT //
				);
			});
		}

		// Check for non-linear expressions with variable products.
		if (lhs == ExpressionType.Variable && rhs == ExpressionType.Variable) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_NONLINEAR_ERROR_MESSAGE, //
						product, //
						GipslPackage.Literals.GIPS_ARITHMETIC_PRODUCT__LEFT //
				);
			});
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_NONLINEAR_ERROR_MESSAGE, //
						product, //
						GipslPackage.Literals.GIPS_ARITHMETIC_PRODUCT__RIGHT //
				);
			});
			return ExpressionType.Error;
		}

		// Check for non-linear expressions with variable as divisor.
		if (product.getOperator() == GipsProductOperator.DIV && rhs == ExpressionType.Variable) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_NONLINEAR_ERROR_MESSAGE, //
						product, //
						GipslPackage.Literals.GIPS_ARITHMETIC_PRODUCT__OPERATOR //
				);
			});
			return ExpressionType.Error;
		}

		if (lhs == rhs && (lhs == ExpressionType.Number || lhs == ExpressionType.Variable)) {
			return lhs;
		} else if (lhs == ExpressionType.Number && rhs == ExpressionType.Variable) {
			return ExpressionType.Variable;
		} else if (lhs == ExpressionType.Variable && rhs == ExpressionType.Number) {
			return ExpressionType.Variable;
		} else {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_MISMATCH_ERROR_MESSAGE, //
						product, //
						GipslPackage.Literals.GIPS_ARITHMETIC_PRODUCT__OPERATOR //
				);
			});
			return ExpressionType.Error;
		}
	}

	private static ExpressionType evaluate(GipsArithmeticSum sum, Collection<Runnable> errors) {
		ExpressionType lhs = evaluate(sum.getLeft(), errors);
		ExpressionType rhs = evaluate(sum.getRight(), errors);

		if (lhs != ExpressionType.String && lhs != ExpressionType.Number && lhs != ExpressionType.Variable) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_EVAL_ERROR_MESSAGE, //
						sum, //
						GipslPackage.Literals.GIPS_ARITHMETIC_SUM__LEFT //
				);
			});
		}
		if (rhs != ExpressionType.String && rhs != ExpressionType.Number && rhs != ExpressionType.Variable) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_EVAL_ERROR_MESSAGE, //
						sum, //
						GipslPackage.Literals.GIPS_ARITHMETIC_SUM__RIGHT //
				);
			});
		}

		if (lhs == rhs && (lhs == ExpressionType.Number || lhs == ExpressionType.Variable)) {
			return lhs;
		} else if (lhs == ExpressionType.Number && rhs == ExpressionType.Variable) {
			return ExpressionType.Variable;
		} else if (lhs == ExpressionType.Variable && rhs == ExpressionType.Number) {
			return ExpressionType.Variable;
		} else {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_MISMATCH_ERROR_MESSAGE, //
						sum, //
						GipslPackage.Literals.GIPS_ARITHMETIC_SUM__OPERATOR //
				);
			});
			return ExpressionType.Error;
		}
	}

	public static Collection<Runnable> checkValueExpression(final GipsValueExpression expression) {
		Collection<Runnable> errors = Collections.synchronizedCollection(new LinkedList<>());
		ExpressionType result = evaluate(expression, errors);

		if (result == ExpressionType.Unknown) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.VALUE_EXPR_CONTAINS_UNKNOWNS, //
						expression, //
						null //
				);
			});
		}
		if (result == ExpressionType.Error) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.VALUE_EXPR_CONTAINS_ERRORS, //
						expression, //
						null //
				);
			});
		}

		return errors;
	}

	public static ExpressionData evaluate(final GipsValueExpression expression, Collection<Runnable> errors) {
		ExpressionData valueType = null;
		if (expression.getValue() instanceof GipsMappingExpression) {
			valueType = new ExpressionData(ExpressionMutability.Constant, ExpressionType.Object, true);
		} else if (expression.getValue() instanceof GipsTypeExpression) {
			valueType = new ExpressionData(ExpressionMutability.Constant, ExpressionType.Object, true);
		} else if (expression.getValue() instanceof GipsPatternExpression) {
			valueType = new ExpressionData(ExpressionMutability.Constant, ExpressionType.Object, true);
		} else if (expression.getValue() instanceof GipsRuleExpression) {
			valueType = new ExpressionData(ExpressionMutability.Constant, ExpressionType.Object, true);
		} else if (expression.getValue() instanceof GipsLocalContextExpression local) {
			valueType = evaluate(local, errors);
		} else if (expression.getValue() instanceof GipsSetElementExpression set) {
			valueType = evaluate(set, errors);
		} else {
			valueType = new ExpressionData(ExpressionMutability.Constant, ExpressionType.Unknown, false);
		}

		if (expression.getSetExpression() != null) {
			return evaluate(expression.getSetExpression(), errors);
		} else {
			return valueType;
		}
	}

	public static ExpressionData evaluate(final GipsLocalContextExpression expression, Collection<Runnable> errors) {
		ExpressionData valueType = null;
		EObject localContext = GipslScopeContextUtil.getLocalContext(expression);

		if (expression.getExpression() instanceof GipsVariableReferenceExpression variableReferenceExpression) {
			valueType = evaluate(variableReferenceExpression, errors);

			if (!(localContext instanceof GipsMappingExpression || localContext instanceof GipsMapping
					|| localContext instanceof GipsTypeExpression || localContext instanceof EClass)) {
				errors.add(() -> {
					GipslValidator.err( //
							GipslValidatorUtil.ARITH_EXPR_VAR_REF_ERROR_MESSAGE, //
							expression.getExpression(), //
							GipslPackage.Literals.GIPS_VARIABLE_REFERENCE_EXPRESSION__VARIABLE //
					);
				});
			}
			// Variable references may only be used in "plain" arithmetic expressions and
			// Sum Operations
			Object container = GipslScopeContextUtil.getContainer(expression,
					Set.of(GipsSetExpressionImpl.class, GipsSumOperationImpl.class));
			if (container instanceof GipsSetExpression) {
				errors.add(() -> {
					GipslValidator.err( //
							GipslValidatorUtil.ARITH_EXPR_VAR_USE_ERROR_MESSAGE, //
							expression.getExpression(), //
							GipslPackage.Literals.GIPS_VARIABLE_REFERENCE_EXPRESSION__VARIABLE //
					);
				});
			}
			// Variable references may not be used in nested set operations
			container = GipslScopeContextUtil.getContainer(expression.eContainer(),
					Set.of(GipsValueExpressionImpl.class));
			if (GipslScopeContextUtil.getContainer((EObject) container,
					Set.of(GipsValueExpressionImpl.class)) != null) {
				errors.add(() -> {
					GipslValidator.err( //
							GipslValidatorUtil.ARITH_EXPR_VAR_USE_ERROR_MESSAGE, //
							expression.getExpression(), //
							GipslPackage.Literals.GIPS_VARIABLE_REFERENCE_EXPRESSION__VARIABLE //
					);
				});
			}
			// Variable references may not be used to define a constant
			final GipsConstant constant = (GipsConstant) GipslScopeContextUtil.getContainer(expression.eContainer(),
					Set.of(GipsConstantImpl.class));
			if (constant != null) {
				errors.add(() -> {
					GipslValidator.err( //
							GipslValidatorUtil.CONSTANT_CONTAINS_VARIABLE, //
							constant, //
							GipslPackage.Literals.GIPS_CONSTANT__EXPRESSION //
					);
				});
			}

		} else if (expression.getExpression() instanceof GipsNodeExpression node) {
			if (localContext instanceof EClass || localContext instanceof GipsTypeExpression
					|| localContext instanceof GipsAttributeExpression
					|| (localContext instanceof GipsLocalContextExpression lce
							&& lce.getExpression() instanceof GipsAttributeExpression)
					|| (localContext instanceof GipsLocalContextExpression lce
							&& lce.getExpression() instanceof GipsVariableReferenceExpression)
					|| (localContext instanceof GipsSetElementExpression see
							&& see.getExpression() instanceof GipsAttributeExpression)
					|| (localContext instanceof GipsSetElementExpression see
							&& see.getExpression() instanceof GipsVariableReferenceExpression)) {
				errors.add(() -> {
					GipslValidator.err( //
							GipslValidatorUtil.TYPE_DOES_NOT_CONTAIN_NODES, //
							expression.getExpression(), //
							GipslPackage.Literals.GIPS_NODE_EXPRESSION__NODE //
					);
				});
				valueType = ExpressionData.asError();
			} else {
				valueType = evaluate(node, errors);
			}
		} else if (expression.getExpression() instanceof GipsAttributeExpression attribute) {
			if (!(localContext instanceof EClass || localContext instanceof GipsTypeExpression
					|| (localContext instanceof GipsNodeExpression ne && ne.getExpression() != null)
					|| localContext instanceof GipsAttributeExpression
					|| (localContext instanceof GipsLocalContextExpression lce
							&& lce.getExpression() instanceof GipsAttributeExpression)
					|| (localContext instanceof GipsLocalContextExpression lce
							&& (lce.getExpression() instanceof GipsNodeExpression ne && ne.getExpression() != null))
					|| (localContext instanceof GipsSetElementExpression see
							&& see.getExpression() instanceof GipsAttributeExpression)
					|| (localContext instanceof GipsSetElementExpression see
							&& (see.getExpression() instanceof GipsNodeExpression ne && ne.getExpression() != null)))) {
				errors.add(() -> {
					GipslValidator.err( //
							GipslValidatorUtil.TYPE_CONTAINS_ATTRIBUTES, //
							expression.getExpression(), //
							GipslPackage.Literals.GIPS_ATTRIBUTE_EXPRESSION__ATTRIBUTE //
					);
				});
				valueType = ExpressionData.asError();
			} else {
				valueType = evaluate(attribute, errors);

			}
		} else {
			// Case: expression.getExpression() == null, which is a reference to the plain
			// context object
			valueType = new ExpressionData(ExpressionMutability.Constant, ExpressionType.Object, false);
		}

		return valueType;
	}

	public static ExpressionData evaluate(final GipsSetElementExpression expression, Collection<Runnable> errors) {
		ExpressionData valueType = null;
		EObject setContext = GipslScopeContextUtil.getSetContext(expression);

		if (expression.getExpression() instanceof GipsVariableReferenceExpression variableReferenceExpression) {
			valueType = evaluate(variableReferenceExpression, errors);

			if (!(setContext instanceof GipsMappingExpression || setContext instanceof GipsMapping //
					|| setContext instanceof GipsTypeExpression || setContext instanceof EClass //
					|| setContext instanceof GipsLocalContextExpression
					|| setContext instanceof GipsAttributeExpression)) {
				errors.add(() -> {
					GipslValidator.err( //
							GipslValidatorUtil.ARITH_EXPR_VAR_REF_ERROR_MESSAGE, //
							expression.getExpression(), //
							GipslPackage.Literals.GIPS_VARIABLE_REFERENCE_EXPRESSION__VARIABLE //
					);
				});
			}

			// Variable references may only be used in Sum Operations
			Object container = GipslScopeContextUtil.getContainer(expression, Set.of(GipsSumOperationImpl.class));
			if (container == null) {
				errors.add(() -> {
					GipslValidator.err( //
							GipslValidatorUtil.ARITH_EXPR_VAR_USE_ERROR_MESSAGE, //
							expression.getExpression(), //
							GipslPackage.Literals.GIPS_VARIABLE_REFERENCE_EXPRESSION__VARIABLE //
					);
				});
			}

			// Variable references may not be used in nested set operations
			container = GipslScopeContextUtil.getContainer(expression.eContainer(),
					Set.of(GipsValueExpressionImpl.class));
			if (GipslScopeContextUtil.getContainer((EObject) container,
					Set.of(GipsValueExpressionImpl.class)) != null) {
				errors.add(() -> {
					GipslValidator.err( //
							GipslValidatorUtil.ARITH_EXPR_VAR_USE_ERROR_MESSAGE, //
							expression.getExpression(), //
							GipslPackage.Literals.GIPS_VARIABLE_REFERENCE_EXPRESSION__VARIABLE //
					);
				});
			}

			// Variable references may not be used to define a constant
			final GipsConstant constant = (GipsConstant) GipslScopeContextUtil.getContainer(expression.eContainer(),
					Set.of(GipsConstantImpl.class));
			if (constant != null) {
				errors.add(() -> {
					GipslValidator.err( //
							GipslValidatorUtil.CONSTANT_CONTAINS_VARIABLE, //
							constant, //
							GipslPackage.Literals.GIPS_CONSTANT__EXPRESSION //
					);
				});
			}

		} else if (expression.getExpression() instanceof GipsNodeExpression node) {
			if (setContext instanceof EClass || setContext instanceof GipsTypeExpression
					|| setContext instanceof GipsAttributeExpression
					|| (setContext instanceof GipsLocalContextExpression lce
							&& lce.getExpression() instanceof GipsAttributeExpression)
					|| (setContext instanceof GipsLocalContextExpression lce
							&& lce.getExpression() instanceof GipsVariableReferenceExpression)
					|| (setContext instanceof GipsSetElementExpression see
							&& see.getExpression() instanceof GipsAttributeExpression)
					|| (setContext instanceof GipsSetElementExpression see
							&& see.getExpression() instanceof GipsVariableReferenceExpression)) {
				errors.add(() -> {
					GipslValidator.err( //
							GipslValidatorUtil.TYPE_DOES_NOT_CONTAIN_NODES, //
							expression.getExpression(), //
							GipslPackage.Literals.GIPS_NODE_EXPRESSION__NODE //
					);
				});
				valueType = ExpressionData.asError();

			} else {
				valueType = evaluate(node, errors);
			}

		} else if (expression.getExpression() instanceof GipsAttributeExpression attribute) {
			if (!(setContext instanceof EClass || setContext instanceof GipsTypeExpression
					|| (setContext instanceof GipsNodeExpression ne && ne.getExpression() != null)
					|| setContext instanceof GipsAttributeExpression
					|| (setContext instanceof GipsLocalContextExpression lce
							&& lce.getExpression() instanceof GipsAttributeExpression)
					|| (setContext instanceof GipsLocalContextExpression lce
							&& (lce.getExpression() instanceof GipsNodeExpression ne && ne.getExpression() != null))
					|| (setContext instanceof GipsSetElementExpression see
							&& see.getExpression() instanceof GipsAttributeExpression)
					|| (setContext instanceof GipsSetElementExpression see
							&& (see.getExpression() instanceof GipsNodeExpression ne && ne.getExpression() != null)))) {
				errors.add(() -> {
					GipslValidator.err( //
							GipslValidatorUtil.TYPE_CONTAINS_ATTRIBUTES, //
							expression.getExpression(), //
							GipslPackage.Literals.GIPS_ATTRIBUTE_EXPRESSION__ATTRIBUTE //
					);
				});
				valueType = ExpressionData.asError();

			} else {
				valueType = evaluate(attribute, errors);
			}
		} else {
			// Case: expression.getExpression() == null, which is a reference to the plain
			// set element
			valueType = new ExpressionData(ExpressionMutability.Constant, ExpressionType.Object, false);
		}

		return valueType;
	}

	public static ExpressionData evaluate(final GipsVariableReferenceExpression expression,
			Collection<Runnable> errors) {
		return evaluate(expression.getVariable(), errors);
	}

	public static ExpressionData evaluate(GipsVariable variable, Collection<Runnable> errors) {
		ExpressionType type = evaluateType(variable.getType(), errors);
		return new ExpressionData(ExpressionMutability.Variable, type, false);
	}

	public static ExpressionData evaluate(final GipsNodeExpression expression, Collection<Runnable> errors) {
		if (expression.getExpression() == null)
			return new ExpressionData(ExpressionMutability.Constant, ExpressionType.Object, false);

		if (expression.getExpression() instanceof GipsAttributeExpression attribute) {
			return evaluate(attribute, errors);
		} else if (expression.getExpression() instanceof GipsVariableReferenceExpression variableReferenceExpression) {
			return evaluate(variableReferenceExpression, errors);
		}

		return ExpressionData.asError();
	}

	public static ExpressionData evaluate(final GipsAttributeExpression expression, Collection<Runnable> errors) {
		if (expression.getAttribute() == null || expression.getAttribute().getLiteral() == null)
			return ExpressionData.asUnknown();

		return switch (expression.getRight()) {
		case null -> evaluate(expression.getAttribute(), errors);
		case GipsAttributeExpression attribute -> evaluate(attribute, errors);
		case GipsVariableReferenceExpression variable -> evaluate(variable, errors);
		default -> ExpressionData.asUnknown();
		};
	}

	public static ExpressionData evaluate(final GipsAttributeLiteral attribute, Collection<Runnable> errors) {
		return evaluate(attribute.getLiteral(), errors).with(ExpressionMutability.Constant);
	}

	public static ExpressionData evaluate(final EStructuralFeature feature, Collection<Runnable> errors) {
		if (feature == null)
			return ExpressionData.asError();

		ExpressionType type = evaluateType(feature.getEType(), errors);
		return new ExpressionData(ExpressionMutability.Unknown, type, feature.isMany());
	}

	public static ExpressionType evaluateType(final EClassifier type, Collection<Runnable> errors) {
		if (type == EcorePackage.Literals.EINT || type == EcorePackage.Literals.ESHORT
				|| type == EcorePackage.Literals.ELONG || type == EcorePackage.Literals.EBYTE) {
			return ExpressionType.Number;
		} else if (type == EcorePackage.Literals.EFLOAT || type == EcorePackage.Literals.EDOUBLE) {
			return ExpressionType.Number;
		} else if (type == EcorePackage.Literals.EBOOLEAN) {
			return ExpressionType.Boolean;
		} else if (type == EcorePackage.Literals.ECHAR || type == EcorePackage.Literals.ESTRING) {
			return ExpressionType.String;
		} else if (type instanceof EClass) {
			return ExpressionType.Object;
		} else if (type instanceof EEnum) {
			return ExpressionType.Enum;
		}

		return ExpressionType.Unknown;
	}

	public static Collection<Runnable> checkSetExpression(final GipsValueExpression context,
			final GipsSetExpression expression) {
		Collection<Runnable> errors = Collections.synchronizedCollection(new LinkedList<>());
		ExpressionData result = evaluate(expression, errors);

		if (result == ExpressionType.Unknown) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.SET_EXPR_CONTAINS_UNKNOWNS, //
						expression, //
						null //
				);
			});
		}
		if (result == ExpressionType.Error) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.SET_EXPR_CONTAINS_ERRORS, //
						expression, //
						null //
				);
			});
		}

		return errors;
	}

	public static ExpressionData evaluate(final GipsSetExpression expression, Collection<Runnable> errors) {
		if (expression.getOperation() == null && expression.getRight() == null) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.SET_OPERATION_MISSING, //
						expression, //
						GipslPackage.Literals.GIPS_SET_EXPRESSION__OPERATION //
				);
			});
			return ExpressionData.asError();
		}
		if (expression.getOperation() instanceof GipsSetOperation && expression.getRight() == null) {
			GipsConstant container = (GipsConstant) GipslScopeContextUtil.getContainer(expression,
					Set.of(GipsConstantImpl.class));
			if (container == null) {
				errors.add(() -> {
					GipslValidator.err( //
							GipslValidatorUtil.SET_OPERATION_MISSING, //
							expression, //
							GipslPackage.Literals.GIPS_SET_EXPRESSION__OPERATION //
					);
				});
				return ExpressionData.asError();
			}

		}

		if (expression.getRight() == null && expression.getOperation() instanceof GipsReduceOperation reduce) {
			if (reduce instanceof GipsSumOperation sum) {
				return evaluate(sum.getExpression(), errors);
			} else if (reduce instanceof GipsSimpleSelect) {
				EObject setContext = GipslScopeContextUtil.getSetContext(reduce);
				if (setContext instanceof EClass) {
					return new ExpressionData(ExpressionMutability.Constant, ExpressionType.Object, false);
				} else if (setContext instanceof GipsSetElementExpression set) {
					return evaluate(set, errors);
				} else if (setContext instanceof GipsLocalContextExpression local) {
					return evaluate(local, errors);
				} else if (expression instanceof GipsMappingExpression) {
					return new ExpressionData(ExpressionMutability.Constant, ExpressionType.Object, true);
				} else if (expression instanceof GipsTypeExpression) {
					return new ExpressionData(ExpressionMutability.Constant, ExpressionType.Object, true);
				} else if (expression instanceof GipsPatternExpression) {
					return new ExpressionData(ExpressionMutability.Constant, ExpressionType.Object, true);
				} else if (expression instanceof GipsRuleExpression) {
					return new ExpressionData(ExpressionMutability.Constant, ExpressionType.Object, true);
				} else {
					return new ExpressionData(ExpressionMutability.Constant, ExpressionType.Unknown, false);
				}
			} else if (reduce instanceof GipsSimpleQuery simple && simple.getOperator() == QueryOperator.COUNT) {
				return new ExpressionData(ExpressionMutability.Constant, ExpressionType.Number, false);
			} else {
				return new ExpressionData(ExpressionMutability.Constant, ExpressionType.Boolean, false);
			}
		} else if (expression.getRight() == null && expression.getOperation() instanceof GipsSetOperation) {
			return new ExpressionData(ExpressionMutability.Constant, ExpressionType.Object, true);
		} else {
			if (expression.getOperation() instanceof GipsFilterOperation filter) {
				ExpressionData type = evaluate(filter.getExpression(), errors);
				if (type.getType() != ExpressionType.Boolean) {
					errors.add(() -> {
						GipslValidator.err( //
								GipslValidatorUtil.SET_FILTER_ERROR, //
								filter, //
								GipslPackage.Literals.GIPS_FILTER_OPERATION__EXPRESSION //
						);
					});
				}
			} else if (expression.getOperation() instanceof GipsSortOperation sort) {
				GipsSortPredicate predicate = sort.getPredicate();
				if (predicate.getRelation() == RelationalOperator.EQUAL
						|| predicate.getRelation() == RelationalOperator.UNEQUAL) {
					errors.add(() -> {
						GipslValidator.err( //
								GipslValidatorUtil.SET_SORT_PREDICATE_RELATION_ERROR, //
								predicate, //
								GipslPackage.Literals.GIPS_SORT_PREDICATE__RELATION //
						);
					});
				}
				ExpressionData lhs = null;
				ExpressionData rhs = null;
				if (predicate.getE1() instanceof GipsNodeExpression node) {
					lhs = evaluate(node, errors);
				} else if (predicate.getE1() instanceof GipsAttributeExpression attribute) {
					lhs = evaluate(attribute, errors);
				}
				if (predicate.getE2() instanceof GipsNodeExpression node) {
					rhs = evaluate(node, errors);
				} else if (predicate.getE2() instanceof GipsAttributeExpression attribute) {
					rhs = evaluate(attribute, errors);
				}

				if (lhs == ExpressionType.Unknown || lhs == ExpressionType.Error || lhs == ExpressionType.Set
						|| rhs == ExpressionType.Unknown || rhs == ExpressionType.Error || rhs == ExpressionType.Set) {
					errors.add(() -> {
						GipslValidator.err( //
								GipslValidatorUtil.REL_EXPR_EVAL_ERROR_MESSAGE, //
								sort, //
								GipslPackage.Literals.GIPS_SORT_OPERATION__PREDICATE //
						);
					});
				}

				if (lhs.getType() != rhs.getType()) {
					errors.add(() -> {
						GipslValidator.err( //
								String.format(GipslValidatorUtil.REL_EXPR_MISMATCH_ERROR_MESSAGE, lhs.getType(),
										rhs.getType()), //
								sort, //
								GipslPackage.Literals.GIPS_SORT_OPERATION__PREDICATE //
						);
					});
				}
			} else if (expression.getOperation() instanceof GipsConcatenationOperation cat) {
				ExpressionData type = evaluate(cat.getValue(), errors);
				if (type != ExpressionType.Set) {
					errors.add(() -> {
						GipslValidator.err( //
								GipslValidatorUtil.SET_CONCAT_ERROR, //
								expression, //
								GipslPackage.Literals.GIPS_SET_EXPRESSION__OPERATION //
						);
					});
				}
			} else if (expression.getOperation() instanceof GipsTransformOperation transform) {
				ExpressionData type = evaluate(transform.getExpression(), errors);
				if (type == ExpressionType.Unknown || type != ExpressionType.Error) {
					errors.add(() -> {
						GipslValidator.err( //
								GipslValidatorUtil.SET_OPERATION_FAULTY, //
								expression, //
								GipslPackage.Literals.GIPS_SET_EXPRESSION__OPERATION //
						);
					});
				}
			}

			return evaluate(expression.getRight(), errors);
		}
	}

}

enum ExpressionType {
	Object, String, Boolean, Number, Enum, Set, Variable, Unknown, Error;
}

enum ExpressionMutability {
	Variable, Constant, Unknown
}
