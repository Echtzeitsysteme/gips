package org.emoflon.gips.gipsl.validation;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
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

	public static enum ExpressionType {
		Object, String, Boolean, Number, Enum, Set, Variable, Unknown, Error;
	}

	public static enum ExpressionMutability {
		Variable, Constant
	}

	public static final class ExpressionData {

		private static final ExpressionData ERROR = new ExpressionData(ExpressionMutability.Constant,
				ExpressionType.Error, false);

		private static final ExpressionData UNKNOWN = new ExpressionData(ExpressionMutability.Constant,
				ExpressionType.Unknown, false);

		private final ExpressionMutability mutability;
		private final ExpressionType type;
		private final boolean isMany;

		public static ExpressionData asError() {
			return ERROR;
		}

		public static ExpressionData asUnknown() {
			return UNKNOWN;
		}

		public static ExpressionData asConstantScalar(ExpressionType type) {
			if (type == ExpressionType.Unknown)
				return ExpressionData.asUnknown();
			if (type == ExpressionType.Error)
				return ExpressionData.asError();
			return new ExpressionData(ExpressionMutability.Constant, type, false);
		}

		public static ExpressionData asConstantSet(ExpressionType type) {
			if (type == ExpressionType.Unknown)
				return ExpressionData.asUnknown();
			if (type == ExpressionType.Error)
				return ExpressionData.asError();
			return new ExpressionData(ExpressionMutability.Constant, type, true);
		}

		public static ExpressionData asConstant(ExpressionType type, boolean isMany) {
			if (type == ExpressionType.Unknown)
				return ExpressionData.asUnknown();
			if (type == ExpressionType.Error)
				return ExpressionData.asError();
			return new ExpressionData(ExpressionMutability.Constant, type, isMany);
		}

		public static ExpressionData asVariableScalar(ExpressionType type) {
			if (type == ExpressionType.Unknown)
				return ExpressionData.asUnknown();
			if (type == ExpressionType.Error)
				return ExpressionData.asError();
			return new ExpressionData(ExpressionMutability.Variable, type, false);
		}

		public ExpressionData(ExpressionMutability mutability, ExpressionType type, boolean isMany) {
			this.mutability = mutability;
			this.type = type;
			this.isMany = isMany;
		}

		public ExpressionMutability getMutability() {
			return mutability;
		}

		public ExpressionType getType() {
			return type;
		}

		public boolean isType(ExpressionType type, ExpressionType... orAny) {
			if (this.type == type)
				return true;

			if (orAny != null && orAny.length > 0) {
				for (var otherType : orAny)
					if (otherType == this.type)
						return true;
			}

			return false;
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

		public boolean isScalar() {
			return !isMany;
		}

		public boolean isError() {
			return type == ExpressionType.Error;
		}

		public boolean isUnknown() {
			return type == ExpressionType.Unknown;
		}

		@Override
		public int hashCode() {
			return Objects.hash(isMany, mutability, type);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ExpressionData other = (ExpressionData) obj;
			return isMany == other.isMany && mutability == other.mutability && type == other.type;
		}

		@Override
		public String toString() {
			return "ExpressionData [mutability=" + mutability + ", type=" + type + ", isMany=" + isMany + "]";
		}

	}

	public static Collection<Runnable> checkBooleanExpression(final GipsBooleanExpression expression) {
		Collection<Runnable> errors = Collections.synchronizedCollection(new LinkedList<>());
		ExpressionData result = evaluate(expression, errors);

		if (result.isUnknown() && errors.size() == 0) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.BOOL_EXPR_CONTAINS_UNKNOWNS, //
						expression, //
						null //
				);
			});
		}

		if (result.isError() && errors.size() == 0) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.BOOL_EXPR_CONTAINS_ERRORS, //
						expression, //
						null //
				);
			});
		}

		if (errors.size() == 0 && (result.getType() != ExpressionType.Boolean || result.isMany)) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.BOOL_EXPR_EVAL_ERROR_MESSAGE, //
						expression, //
						null //
				);
			});
		}

		return errors;
	}

	public static Collection<Runnable> checkSetExpression(final GipsValueExpression context,
			final GipsSetExpression expression) {
		Collection<Runnable> errors = Collections.synchronizedCollection(new LinkedList<>());
		ExpressionData result = evaluate(expression, errors);

		if (result.isUnknown() && errors.size() == 0) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.SET_EXPR_CONTAINS_UNKNOWNS, //
						expression, //
						null //
				);
			});
		}
		if (result.isError() && errors.size() == 0) {
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

	public static Collection<Runnable> checkArithmeticExpression(final GipsArithmeticExpression expression) {
		Collection<Runnable> errors = Collections.synchronizedCollection(new LinkedList<>());
		ExpressionData result = evaluate(expression, errors);

		if (result.isUnknown() && errors.size() == 0) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_CONTAINS_UNKNOWNS, //
						expression, //
						null //
				);
			});
		}
		if (result.isError() && errors.size() == 0) {
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

		if (expression.eContainer() instanceof GipsObjective obj && result.getType() != ExpressionType.Number
				&& errors.size() == 0) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.OBJECTIVE_EVAL_NOT_NUMBER_MESSAGE, //
						obj, //
						GipslPackage.Literals.GIPS_OBJECTIVE__EXPRESSION //
				);
			});
		}

		if (expression.eContainer() instanceof GipsLinearFunction fun && result.getType() != ExpressionType.Number
				&& errors.size() == 0) {
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

	public static Collection<Runnable> checkValueExpression(final GipsValueExpression expression) {
		Collection<Runnable> errors = Collections.synchronizedCollection(new LinkedList<>());
		ExpressionData result = evaluate(expression, errors);

		if (result.isUnknown() && errors.size() == 0) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.VALUE_EXPR_CONTAINS_UNKNOWNS, //
						expression, //
						null //
				);
			});
		}

		if (result.isError() && errors.size() == 0) {
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

	public static Collection<Runnable> checkRelationalExpression(final GipsRelationalExpression expression) {
		Collection<Runnable> errors = Collections.synchronizedCollection(new LinkedList<>());
		ExpressionData result = evaluate(expression, errors);

		if (result.isUnknown() && errors.size() == 0) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.REL_EXPR_CONTAINS_UNKNOWNS, //
						expression, //
						null //
				);
			});
		}

		if (result.isError() && errors.size() == 0) {
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

	private static ExpressionData evaluate(final GipsBooleanExpression expression, Collection<Runnable> errors) {
		return switch (expression) {
		case GipsBooleanImplication implication -> evaluate(implication, errors);
		case GipsBooleanDisjunction disjunction -> evaluate(disjunction, errors);
		case GipsBooleanConjunction conjunction -> evaluate(conjunction, errors);
		case GipsBooleanNegation negation -> evaluate(negation, errors);
		case GipsBooleanBracket bracket -> evaluate(bracket.getOperand(), errors);
		case GipsBooleanLiteral literal -> evaluate(literal, errors);
		case GipsRelationalExpression relation -> evaluate(relation, errors);
		case GipsArithmeticExpression arithmetic -> evaluate(arithmetic, errors);
		case null, default -> ExpressionData.asUnknown();
		};
	}

	private static ExpressionData evaluate(final GipsBooleanLiteral implication, Collection<Runnable> errors) {
		return ExpressionData.asConstantScalar(ExpressionType.Boolean);
	}

	private static ExpressionData evaluate(final GipsBooleanImplication implication, Collection<Runnable> errors) {
		ExpressionData lhs = evaluate(implication.getLeft(), errors);
		ExpressionData rhs = evaluate(implication.getRight(), errors);

		if (lhs.isError() || rhs.isError())
			return ExpressionData.asError();

		boolean hasError = false;

		if (lhs.getType() != ExpressionType.Boolean || lhs.isMany) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.BOOL_EXPR_EVAL_ERROR_MESSAGE, //
						implication, //
						GipslPackage.Literals.GIPS_BOOLEAN_IMPLICATION__LEFT //
				);
			});
			hasError = true;
		}

		if (rhs.getType() != ExpressionType.Boolean || rhs.isMany) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.BOOL_EXPR_EVAL_ERROR_MESSAGE, //
						implication, //
						GipslPackage.Literals.GIPS_BOOLEAN_IMPLICATION__RIGHT //
				);
			});
			hasError = true;
		}

		if (hasError)
			return ExpressionData.asError();

		if (lhs.isVariable() || rhs.isVariable())
			return ExpressionData.asVariableScalar(ExpressionType.Boolean);
		return ExpressionData.asConstantScalar(ExpressionType.Boolean);
	}

	private static ExpressionData evaluate(final GipsBooleanDisjunction disjunction, Collection<Runnable> errors) {
		ExpressionData lhs = evaluate(disjunction.getLeft(), errors);
		ExpressionData rhs = evaluate(disjunction.getRight(), errors);

		if (lhs.isError() || rhs.isError())
			return ExpressionData.asError();

		boolean hasError = false;

		if (lhs.getType() != ExpressionType.Boolean || lhs.isMany) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.BOOL_EXPR_EVAL_ERROR_MESSAGE, //
						disjunction, //
						GipslPackage.Literals.GIPS_BOOLEAN_DISJUNCTION__LEFT //
				);
			});
			hasError = true;
		}

		if (rhs.getType() != ExpressionType.Boolean || rhs.isMany) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.BOOL_EXPR_EVAL_ERROR_MESSAGE, //
						disjunction, //
						GipslPackage.Literals.GIPS_BOOLEAN_DISJUNCTION__RIGHT //
				);
			});
			hasError = true;
		}

		if (hasError)
			return ExpressionData.asError();

		if (lhs.isVariable() || rhs.isVariable())
			return ExpressionData.asVariableScalar(ExpressionType.Boolean);
		return ExpressionData.asConstantScalar(ExpressionType.Boolean);
	}

	private static ExpressionData evaluate(final GipsBooleanConjunction conjunction, Collection<Runnable> errors) {
		ExpressionData lhs = evaluate(conjunction.getLeft(), errors);
		ExpressionData rhs = evaluate(conjunction.getRight(), errors);

		if (lhs.isError() || rhs.isError())
			return ExpressionData.asError();

		boolean hasError = false;

		if (lhs.getType() != ExpressionType.Boolean || lhs.isMany) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.BOOL_EXPR_EVAL_ERROR_MESSAGE, //
						conjunction, //
						GipslPackage.Literals.GIPS_BOOLEAN_CONJUNCTION__LEFT //
				);
			});
			hasError = true;
		}

		if (rhs.getType() != ExpressionType.Boolean || rhs.isMany) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.BOOL_EXPR_EVAL_ERROR_MESSAGE, //
						conjunction, //
						GipslPackage.Literals.GIPS_BOOLEAN_CONJUNCTION__RIGHT //
				);
			});
			hasError = true;
		}

		if (hasError)
			return ExpressionData.asError();

		if (lhs.isVariable() || rhs.isVariable())
			return ExpressionData.asVariableScalar(ExpressionType.Boolean);
		return ExpressionData.asConstantScalar(ExpressionType.Boolean);
	}

	private static ExpressionData evaluate(final GipsBooleanNegation negation, Collection<Runnable> errors) {
		ExpressionData operand = evaluate(negation.getOperand(), errors);

		if (operand.isError())
			return ExpressionData.asError();

		if (operand.getType() != ExpressionType.Boolean || operand.isMany) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.BOOL_EXPR_EVAL_ERROR_MESSAGE, //
						negation, //
						GipslPackage.Literals.GIPS_BOOLEAN_NEGATION__OPERAND //
				);
			});
			return ExpressionData.asError();
		}

		if (operand.isVariable())
			return ExpressionData.asVariableScalar(ExpressionType.Boolean);
		return ExpressionData.asConstantScalar(ExpressionType.Boolean);
	}

	private static ExpressionData evaluate(final GipsRelationalExpression expression, Collection<Runnable> errors) {
		ExpressionData lhs = evaluate(expression.getLeft(), errors);
		ExpressionData rhs = evaluate(expression.getRight(), errors);

		if (lhs.isError() || rhs.isError())
			return ExpressionData.asError();

		if (lhs.isUnknown() || lhs.isMany() || rhs.isUnknown() || rhs.isMany()) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.REL_EXPR_EVAL_ERROR_MESSAGE, //
						expression, //
						GipslPackage.Literals.GIPS_RELATIONAL_EXPRESSION__OPERATOR //
				);
			});
			return ExpressionData.asError();
		}

		boolean isCombinationAllowed = (lhs.getType() == rhs.getType()) //
				|| (lhs.getType() == ExpressionType.Boolean && rhs.getType() == ExpressionType.Number) //
				|| (lhs.getType() == ExpressionType.Number && rhs.getType() == ExpressionType.Boolean);

		if (!isCombinationAllowed) {
			errors.add(() -> {
				GipslValidator.err( //
						String.format(GipslValidatorUtil.REL_EXPR_MISMATCH_ERROR_MESSAGE, lhs.getType(), rhs.getType()), //
						expression, //
						GipslPackage.Literals.GIPS_RELATIONAL_EXPRESSION__OPERATOR //
				);
			});
			return ExpressionData.asError();
		}

		if (lhs.isVariable() || rhs.isVariable())
			return ExpressionData.asVariableScalar(ExpressionType.Boolean);
		return ExpressionData.asConstantScalar(ExpressionType.Boolean);
	}

	private static ExpressionData evaluate(final GipsArithmeticExpression expression, Collection<Runnable> errors) {
		return switch (expression) {
		case GipsArithmeticSum sum -> evaluate(sum, errors);
		case GipsArithmeticProduct product -> evaluate(product, errors);
		case GipsArithmeticExponential exponential -> evaluate(exponential, errors);
		case GipsArithmeticUnary unary -> evaluate(unary, errors);
		case GipsArithmeticBracket bracket -> evaluate(bracket.getOperand(), errors);
		case GipsValueExpression value -> evaluate(value, errors);
		case GipsLinearFunctionReference lfr -> evaluate(lfr, errors);
		case GipsArithmeticLiteral literal -> evaluate(literal, errors);
		case GipsArithmeticConstant constant -> evaluate(constant, errors);
		case GipsConstantReference reference -> evaluate(reference, errors);
		case null, default -> ExpressionData.asUnknown();
		};
	}

	private static ExpressionData evaluate(GipsArithmeticLiteral literal, Collection<Runnable> errors) {
		return ExpressionData.asConstantScalar(ExpressionType.Number);
	}

	private static ExpressionData evaluate(GipsArithmeticConstant constant, Collection<Runnable> errors) {
		if (constant.getValue() == GipsConstantLiteral.NULL) {
			return ExpressionData.asConstantScalar(ExpressionType.Object);
		} else {
			return ExpressionData.asConstantScalar(ExpressionType.Number);
		}
	}

	private static ExpressionData evaluate(GipsConstantReference reference, Collection<Runnable> errors) {
		if (reference.getConstant() == null)
			return ExpressionData.asUnknown();

		if (reference.getConstant().getExpression() == null)
			return ExpressionData.asUnknown();

		// Check for forbidden expressions, i.e., variable references, errors or
		// unknowns.
		ExpressionData type = evaluate(reference.getConstant().getExpression(), errors);
		if (type.isError())
			ExpressionData.asError();

		if (type.isUnknown()) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.CONSTANT_CONTAINS_ERRORS, //
						reference, //
						GipslPackage.Literals.GIPS_CONSTANT_REFERENCE__CONSTANT //
				);
			});
			return ExpressionData.asError();
		}

		if (type.isVariable()) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.CONSTANT_CONTAINS_VARIABLE, //
						reference, //
						GipslPackage.Literals.GIPS_CONSTANT_REFERENCE__CONSTANT //
				);
			});
			return ExpressionData.asError();
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
			return ExpressionData.asError();
		}

		if (reference.getSetExpression() != null)
			type = evaluate(reference.getSetExpression(), errors);

		return type;
	}

	private static ExpressionData evaluate(GipsLinearFunctionReference lfr, Collection<Runnable> errors) {
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
		return ExpressionData.asVariableScalar(ExpressionType.Number);
	}

	private static ExpressionData evaluate(GipsArithmeticUnary unary, Collection<Runnable> errors) {
		ExpressionData operand = evaluate(unary.getOperand(), errors);

		if (operand.isError())
			return ExpressionData.asError();

		if (operand.isMany()) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_EVAL_ERROR_MESSAGE, //
						unary, //
						GipslPackage.Literals.GIPS_ARITHMETIC_UNARY__OPERAND //
				);
			});
			return ExpressionData.asError();
		}

		if (operand.isVariable() && unary.getOperator() != GipsUnaryOperator.NEG) {
			// negation is the only allowed unary operator for variables
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_EVAL_ERROR_MESSAGE, //
						unary, //
						GipslPackage.Literals.GIPS_ARITHMETIC_UNARY__OPERAND //
				);
			});
			return ExpressionData.asError();
		}

		if (!operand.isType(ExpressionType.Number, ExpressionType.Boolean)) {
			// must be a number/bool
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_EVAL_ERROR_MESSAGE, //
						unary, //
						GipslPackage.Literals.GIPS_ARITHMETIC_UNARY__OPERAND //
				);
			});
			return ExpressionData.asError();
		}

		return operand;
	}

	private static ExpressionData evaluate(GipsArithmeticExponential exponential, Collection<Runnable> errors) {
		ExpressionData lhs = evaluate(exponential.getLeft(), errors);
		ExpressionData rhs = evaluate(exponential.getRight(), errors);

		if (lhs.isError() || rhs.isError())
			return ExpressionData.asError();

		boolean hasError = false;

		if (lhs.getType() != ExpressionType.Number || lhs.isMany) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_EVAL_ERROR_MESSAGE, //
						exponential, //
						GipslPackage.Literals.GIPS_ARITHMETIC_EXPONENTIAL__LEFT //
				);
			});
			hasError = true;
		}

		if (rhs.getType() != ExpressionType.Number || lhs.isMany) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_EVAL_ERROR_MESSAGE, //
						exponential, //
						GipslPackage.Literals.GIPS_ARITHMETIC_EXPONENTIAL__RIGHT //
				);
			});
			hasError = true;
		}

		// Check for non-linear expressions with variables.
		if (lhs.isVariable() || rhs.isVariable()) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_NONLINEAR_ERROR_MESSAGE, //
						exponential, //
						GipslPackage.Literals.GIPS_ARITHMETIC_EXPONENTIAL__OPERATOR //
				);
			});
			hasError = true;
		}

		if (lhs.getType() != rhs.getType()) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_MISMATCH_ERROR_MESSAGE, //
						exponential, //
						GipslPackage.Literals.GIPS_ARITHMETIC_EXPONENTIAL__OPERATOR //
				);
			});
			hasError = true;
		}

		if (hasError)
			return ExpressionData.asError();

		if (lhs.isVariable() || rhs.isVariable())
			return ExpressionData.asVariableScalar(ExpressionType.Number);
		return ExpressionData.asConstantScalar(ExpressionType.Number);
	}

	private static ExpressionData evaluate(GipsArithmeticProduct product, Collection<Runnable> errors) {
		ExpressionData lhs = evaluate(product.getLeft(), errors);
		ExpressionData rhs = evaluate(product.getRight(), errors);

		if (lhs.isError() || rhs.isError())
			return ExpressionData.asError();

		boolean hasError = false;

		if (!lhs.isType(ExpressionType.Number, ExpressionType.Boolean) || lhs.isMany) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_EVAL_ERROR_MESSAGE, //
						product, //
						GipslPackage.Literals.GIPS_ARITHMETIC_PRODUCT__LEFT //
				);
			});

			hasError = true;
		}

		if (!rhs.isType(ExpressionType.Number, ExpressionType.Boolean) || rhs.isMany) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_EVAL_ERROR_MESSAGE, //
						product, //
						GipslPackage.Literals.GIPS_ARITHMETIC_PRODUCT__RIGHT //
				);
			});

			hasError = true;
		}

		// Check for non-linear expressions with variable products.
		if (lhs.isVariable() && rhs.isVariable()) {
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

			hasError = true;
		}

		// Check for non-linear expressions with variable as divisor.
		if (product.getOperator() == GipsProductOperator.DIV && rhs.isVariable()) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_NONLINEAR_ERROR_MESSAGE, //
						product, //
						GipslPackage.Literals.GIPS_ARITHMETIC_PRODUCT__OPERATOR //
				);
			});

			hasError = true;
		}

		if (hasError)
			return ExpressionData.asError();

		if (lhs.isVariable() || rhs.isVariable())
			return ExpressionData.asVariableScalar(ExpressionType.Number);
		return ExpressionData.asConstantScalar(ExpressionType.Number);
	}

	private static ExpressionData evaluate(GipsArithmeticSum sum, Collection<Runnable> errors) {
		ExpressionData lhs = evaluate(sum.getLeft(), errors);
		ExpressionData rhs = evaluate(sum.getRight(), errors);

		if (lhs.isError() || rhs.isError())
			return ExpressionData.asError();

		boolean hasError = false;

		if (!lhs.isType(ExpressionType.Number, ExpressionType.Boolean) || lhs.isMany) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_EVAL_ERROR_MESSAGE, //
						sum, //
						GipslPackage.Literals.GIPS_ARITHMETIC_SUM__LEFT //
				);
			});
			hasError = true;
		}

		if (!rhs.isType(ExpressionType.Number, ExpressionType.Boolean) || rhs.isMany) {
			errors.add(() -> {
				GipslValidator.err( //
						GipslValidatorUtil.ARITH_EXPR_EVAL_ERROR_MESSAGE, //
						sum, //
						GipslPackage.Literals.GIPS_ARITHMETIC_SUM__RIGHT //
				);
			});
			hasError = true;
		}

		if (hasError)
			return ExpressionData.asError();

		if (lhs.isVariable() || rhs.isVariable())
			return ExpressionData.asVariableScalar(ExpressionType.Number);
		return ExpressionData.asConstantScalar(ExpressionType.Number);
	}

	public static ExpressionData evaluate(final GipsValueExpression expression, Collection<Runnable> errors) {
		ExpressionData valueType = switch (expression.getValue()) {
		case GipsMappingExpression exp -> evaluate(exp, errors);
		case GipsTypeExpression exp -> evaluate(exp, errors);
		case GipsPatternExpression exp -> evaluate(exp, errors);
		case GipsRuleExpression exp -> evaluate(exp, errors);
		case GipsLocalContextExpression exp -> evaluate(exp, errors);
		case GipsSetElementExpression exp -> evaluate(exp, errors);
		case null, default -> ExpressionData.asUnknown();
		};

		if (expression.getSetExpression() != null)
			return evaluate(expression.getSetExpression(), errors);

		return valueType;
	}

	private static ExpressionData evaluate(final GipsMappingExpression expression, Collection<Runnable> errors) {
		return ExpressionData.asConstantSet(ExpressionType.Object);
	}

	private static ExpressionData evaluate(final GipsTypeExpression expression, Collection<Runnable> errors) {
		return ExpressionData.asConstantSet(ExpressionType.Object);
	}

	private static ExpressionData evaluate(final GipsPatternExpression expression, Collection<Runnable> errors) {
		return ExpressionData.asConstantSet(ExpressionType.Object);
	}

	private static ExpressionData evaluate(final GipsRuleExpression expression, Collection<Runnable> errors) {
		return ExpressionData.asConstantSet(ExpressionType.Object);
	}

	private static ExpressionData evaluate(final GipsLocalContextExpression expression, Collection<Runnable> errors) {
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
			valueType = ExpressionData.asConstantScalar(ExpressionType.Object);
		}

		return valueType;
	}

	private static ExpressionData evaluate(final GipsSetElementExpression expression, Collection<Runnable> errors) {
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
			valueType = ExpressionData.asConstantScalar(ExpressionType.Object);
		}

		return valueType;
	}

	private static ExpressionData evaluate(final GipsVariableReferenceExpression expression,
			Collection<Runnable> errors) {
		if (expression.isIsMappingValue())
			return ExpressionData.asVariableScalar(ExpressionType.Boolean);

		if (expression.isIsGenericValue()) {
			if (expression.getVariable() == null)
				return ExpressionData.asUnknown();
			return evaluate(expression.getVariable(), errors);
		}

		return ExpressionData.asUnknown();
	}

	private static ExpressionData evaluate(GipsVariable variable, Collection<Runnable> errors) {
		ExpressionType type = evaluateType(variable.getType(), errors);
		return ExpressionData.asVariableScalar(type);
	}

	private static ExpressionData evaluate(final GipsNodeExpression expression, Collection<Runnable> errors) {
		if (expression.getExpression() == null)
			return ExpressionData.asConstantScalar(ExpressionType.Object);

		if (expression.getExpression() instanceof GipsAttributeExpression attribute) {
			return evaluate(attribute, errors);
		} else if (expression.getExpression() instanceof GipsVariableReferenceExpression variableReferenceExpression) {
			return evaluate(variableReferenceExpression, errors);
		}

		return ExpressionData.asError();
	}

	private static ExpressionData evaluate(final GipsAttributeExpression expression, Collection<Runnable> errors) {
		if (expression.getAttribute() == null || expression.getAttribute().getLiteral() == null)
			return ExpressionData.asUnknown();

		return switch (expression.getRight()) {
		case null -> evaluate(expression.getAttribute(), errors);
		case GipsAttributeExpression attribute -> evaluate(attribute, errors);
		case GipsVariableReferenceExpression variable -> evaluate(variable, errors);
		default -> ExpressionData.asUnknown(); // TODO
		};
	}

	private static ExpressionData evaluate(final GipsAttributeLiteral attribute, Collection<Runnable> errors) {
		if (attribute.getLiteral() == null)
			return ExpressionData.asUnknown();

		ExpressionType type = evaluateType(attribute.getLiteral().getEType(), errors);
		return ExpressionData.asConstant(type, attribute.getLiteral().isMany());
	}

	private static ExpressionType evaluateType(final EClassifier type, Collection<Runnable> errors) {
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

	private static ExpressionData evaluate(final GipsSetExpression expression, Collection<Runnable> errors) {
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
				ExpressionData contentType = evaluate(sum.getExpression(), errors);
				if (contentType.isError())
					return ExpressionData.asError();

				// for now we allow everything
				if (contentType.isVariable())
					return ExpressionData.asVariableScalar(ExpressionType.Number);
				return ExpressionData.asConstantScalar(ExpressionType.Number);

			} else if (reduce instanceof GipsSimpleSelect) {
				EObject setContext = GipslScopeContextUtil.getSetContext(reduce);

				if (setContext instanceof EClass) {
					return ExpressionData.asConstantScalar(ExpressionType.Object);
				} else if (setContext instanceof GipsSetElementExpression set) {
					return evaluate(set, errors);
				} else if (setContext instanceof GipsLocalContextExpression local) {
					return evaluate(local, errors);
				} else if (expression instanceof GipsMappingExpression mappingExpression) {
					return evaluate(mappingExpression, errors);
				} else if (expression instanceof GipsTypeExpression typeExpression) {
					return evaluate(typeExpression, errors);
				} else if (expression instanceof GipsPatternExpression patternExpression) {
					return evaluate(patternExpression, errors);
				} else if (expression instanceof GipsRuleExpression ruleExpression) {
					return evaluate(ruleExpression, errors);
				} else {
					return ExpressionData.asUnknown();
				}

			} else if (reduce instanceof GipsSimpleQuery simple && simple.getOperator() == QueryOperator.COUNT) {
				return ExpressionData.asConstantScalar(ExpressionType.Number);
			} else {
				return ExpressionData.asConstantScalar(ExpressionType.Boolean);
			}

		} else if (expression.getRight() == null && expression.getOperation() instanceof GipsSetOperation) {
			return ExpressionData.asConstantSet(ExpressionType.Object);

		} else {
			if (expression.getOperation() instanceof GipsFilterOperation filter) {
				ExpressionData type = evaluate(filter.getExpression(), errors);
				if (type.getType() != ExpressionType.Boolean || !type.isConstant()) {
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

				if (lhs.isUnknown() || lhs.isError() || lhs.isMany() || rhs.isUnknown() || rhs.isError()
						|| rhs.isMany()) {
					errors.add(() -> {
						GipslValidator.err( //
								GipslValidatorUtil.REL_EXPR_EVAL_ERROR_MESSAGE, //
								sort, //
								GipslPackage.Literals.GIPS_SORT_OPERATION__PREDICATE //
						);
					});
				}

				if (lhs.getType() != rhs.getType()) {
					var errorMessage = String.format(GipslValidatorUtil.REL_EXPR_MISMATCH_ERROR_MESSAGE, lhs.getType(),
							rhs.getType());
					errors.add(() -> {
						GipslValidator.err( //
								errorMessage, //
								sort, //
								GipslPackage.Literals.GIPS_SORT_OPERATION__PREDICATE //
						);
					});
				}
			} else if (expression.getOperation() instanceof GipsConcatenationOperation cat) {
				ExpressionData type = evaluate(cat.getValue(), errors);
				if (!type.isMany()) {
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
				if (type.isUnknown() || !type.isError()) {
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
