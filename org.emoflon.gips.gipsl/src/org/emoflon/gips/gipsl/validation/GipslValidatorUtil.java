package org.emoflon.gips.gipsl.validation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticBracket;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticConstant;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExponential;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpression;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticProduct;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticSum;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticUnary;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanBracket;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanConjunction;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanDisjunction;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanImplication;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanNegation;
import org.emoflon.gips.gipsl.gipsl.GipsConstantReference;
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunctionReference;
import org.emoflon.gips.gipsl.gipsl.GipsLocalContextExpression;
import org.emoflon.gips.gipsl.gipsl.GipsMapping;
import org.emoflon.gips.gipsl.gipsl.GipsMappingExpression;
import org.emoflon.gips.gipsl.gipsl.GipsRelationalExpression;
import org.emoflon.gips.gipsl.gipsl.GipsValueExpression;
import org.emoflon.gips.gipsl.scoping.GipslScopeContextUtil;

/**
 * Utilities for the GIPSL validator.
 */
public class GipslValidatorUtil {

	/**
	 * Enumeration for the type of the leaf. This represents the output type of an
	 * evaluation.
	 */
	protected enum EvalType {
		BOOLEAN, // GipsBooleanLiteral
		INTEGER, //
		LONG, //
		FLOAT, //
		DOUBLE, //
		STRING, //
		NULL, //
		SET, // Sets like output of a filter
		OBJECTIVE, // GipsObjective
		MAPPING, // GipsMapping
		STREAM, // GipsStream
		ECLASS, // EClass for casts
		CONTEXT, // Context, e.g.: 'match::xy'
		ERROR // If leaf type can not be evaluated, e.g.: '1 + true'
	}

	/**
	 * Enumeration for the context (self) type.
	 */
	protected enum ContextType {
		MAPPING, //
		PATTERN, //
		TYPE, //
		GLOBAL, //
		ERROR //
	}

	/**
	 * The list of invalid mapping/objective names. Will be filled in the static
	 * block.
	 */
	public static final Set<String> INVALID_NAMES = new HashSet<String>();

	static {
		final String[] invalidNames = new String[] { "clone", "equals", "finalize", "getClass", "hashCode", "notify",
				"notifyAll", "toString", "wait", "abstract", "assert", "boolean", "break", "byte", "case", "catch",
				"char", "class", "const", "continue", "default", "do", "double", "EAttribute", "EBoolean", "EDataType",
				"EClass", "EClassifier", "EDouble", "EFloat", "EInt", "else", "enum", "EPackage", "EReference",
				"EString", "extends", "final", "finally", "float", "for", "goto", "if", "implements", "import",
				"instanceof", "int", "interface", "long", "native", "new", "package", "private", "protected", "public",
				"return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws",
				"transient", "try", "var", "void", "volatile", "while",

				// New values
				"mapping", "objective", "global objective", "global", "min", "max", "constraint" };
		GipslValidatorUtil.INVALID_NAMES.addAll(Arrays.asList(invalidNames));
	}

	static final String CODE_PREFIX = "org.emoflon.gips.gipsl.";
	// General errors for named elements.
	public static final String NAME_BLOCKED = CODE_PREFIX + "name.blocked";
	public static final String NAME_EXPECT_CAMEL_CASE = GipslValidatorUtil.CODE_PREFIX + "name.expectCamelCase";
	public static final String NAME_EXPECT_LOWER_CASE = GipslValidatorUtil.CODE_PREFIX + "name.expectLowerCase";
	public static final String NAME_EXPECT_UNIQUE = GipslValidatorUtil.CODE_PREFIX + "name.expectUnique";

	public static final String OBJECTIVE_IS_NULL_MESSAGE = "No specification of a global objective found but local objectives are used.";
	public static final String OBJECTIVE_IS_OPTIONAL_MESSAGE = "The global objective is optional if no local objective is defined.";
	public static final String OBJECTIVE_DOES_NOT_CONTAIN_LOCAL_OBJECTIVE_MESSAGE = "Global objective does not contain any reference to a local objective.";
	public static final String OBJECTIVE_EVAL_NOT_NUMBER_MESSAGE = "Objective does not evaluate to an integer, double or variable.";

	public static final String MAPPING_NAME_MULTIPLE_DECLARATIONS_MESSAGE = "Mapping, Pattern, Rule or Type '%s' must not be declared '%s'.";
	public static final String MAPPING_NAME_FORBIDDEN_MESSAGE = "Mappings cannot be be named '%s'. Use a different name.";
	public static final String MAPPING_NAME_CONTAINS_UNDERSCORES_MESSAGE = "Mapping name '%s' contains underscores. Use camelCase instead.";
	public static final String MAPPING_NAME_STARTS_WITH_LOWER_CASE_MESSAGE = "Mapping '%s' should start with a lower case character.";
	public static final String MAPPING_W_O_CONSTRAINTS_MESSAGE = "Mapping '%s' is not subject to any constraints.";
	public static final String MAPPING_W_O_CONSTRAINTS_AND_OBJECTIVE_MESSAGE = "Mapping '%s' is not subject to any constraints and not part of any objective function.";
	public static final String MAPPING_VARIABLE_NAME_MULTIPLE_DECLARATIONS_MESSAGE = "Mapping variable name '%s' must not be declared more than once.";
	public static final String RULE_HAS_MULTIPLE_MAPPINGS = "Multiple mappings on rule: <'%s'>";
	public static final String RULE_IS_ABSTRACT = "Mapping '%s' uses an abstract rule which is not possible.";

	public static final String FUNCTION_NAME_MULTIPLE_DECLARATIONS_MESSAGE = "Objective '%s' must not be declared '%s'";
	public static final String FUNCTION_NAME_FORBIDDEN_MESSAGE = "Objectives cannot be be named '%s'. Use a different name.";
	public static final String FUNCTION_NAME_CONTAINS_UNDERSCORES_MESSAGE = "Objective name '%s' contains underscores. Use camelCase instead.";
	public static final String FUNCTION_NAME_STARTS_WITH_LOWER_CASE_MESSAGE = "Objective '%s' should start with a lower case character.";
	public static final String FUNCTION_EVAL_NOT_NUMBER_MESSAGE = "Linear function does not evaluate to an integer, double or variable.";

	// Other errors for types
	public static final String CONSTANT_NAME_UNIQUE = "Constant name is not unique in the current scope.";
	public static final String CONSTANT_NOT_ASSIGNED = "Constant has not been assigned any value.";
	public static final String CONSTANT_CONTAINS_CONSTANT = "Constant may not contain constant references.";
	public static final String CONSTANT_CONTAINS_VARIABLE = "Constant may not contain variable references.";
	public static final String CONSTANT_CONTAINS_ERRORS = "Constant definition contains errors.";
	public static final String CONSTRAINT_EMPTY_MESSAGE = "Constraint is empty.";

	public static final String BOOL_EXPR_CONTAINS_ERRORS = "Boolean expression contains errors.";
	public static final String BOOL_EXPR_CONTAINS_UNKNOWNS = "Boolean expression contains unknown/illegal expression types.";
	public static final String BOOL_EXPR_EVAL_ERROR_MESSAGE = "Boolean expression does not evaluate to boolean.";

	public static final String REL_EXPR_CONTAINS_ERRORS = "Relational expression contains errors.";
	public static final String REL_EXPR_CONTAINS_UNKNOWNS = "Relational expression contains unknown/illegal expression types.";
	public static final String REL_EXPR_EVAL_ERROR_MESSAGE = "Relational expression contains errors or unknown types.";
	public static final String REL_EXPR_MISMATCH_ERROR_MESSAGE = "Operands cannot be compared since their types do not match.";

	public static final String ARITH_EXPR_CONTAINS_ERRORS = "Arithmetic expression contains errors.";
	public static final String ARITH_EXPR_CONTAINS_UNKNOWNS = "Arithmetic expression contains unknown/illegal expression types.";
	public static final String ARITH_EXPR_EVAL_ERROR_MESSAGE = "Arithmetic expression does not evaluate to a primitive type.";
	public static final String ARITH_EXPR_NONLINEAR_ERROR_MESSAGE = "Combination of operands leads to non-linear expression.";
	public static final String ARITH_EXPR_MISMATCH_ERROR_MESSAGE = "Operands cannot be combined since their types do not match.";
	public static final String ARITH_EXPR_LFR_ERROR_MESSAGE = "Linear function references not allowed in set expressions and outside of the objective.";
	public static final String ARITH_EXPR_VAR_REF_ERROR_MESSAGE = "Variable references can only be used in combination with a mapping context.";
	public static final String ARITH_EXPR_VAR_USE_ERROR_MESSAGE = "Variable references can only be used \"plain\" arithmetic expressions or non-nested sum operations of sets.";

	public static final String VALUE_EXPR_CONTAINS_ERRORS = "Value expression contains errors.";
	public static final String VALUE_EXPR_CONTAINS_UNKNOWNS = "Value expression contains unknown/illegal expression types.";

	public static final String TYPE_DOES_NOT_CONTAIN_NODES = "Objects of Types/Classes do not contain nodes.";
	public static final String TYPE_CONTAINS_ATTRIBUTES = "Only objects of Types/Classes contain attributes.";

	public static final String SET_EXPR_CONTAINS_ERRORS = "Set expression contains errors.";
	public static final String SET_EXPR_CONTAINS_UNKNOWNS = "Set expression contains unknown/illegal expression types.";
	public static final String SET_OPERATION_MISSING = "Set expressions must end on an operation that reduces a set to a scalar value such as a sum operation.";
	public static final String SET_OPERATION_FAULTY = "Set operation does not seem to be correct and/or produce a proper set.";
	public static final String SET_CONCAT_ERROR = "Only sets of values can be concatenated.";
	public static final String SET_SORT_PREDICATE_RELATION_ERROR = "Sorting predicate must impose an order, which means == and != are invalid operators.";
	public static final String SET_FILTER_ERROR = "Filter operations on sets must be performed with Boolean predicates. Furthermore, these must be constant at (M)ILP compilation time, which means that they must never contain variable references.";

	public static final String SET_JOIN_ALL_ERROR = "Operation can only be used if set and context is of the same type.";
	public static final String SET_JOIN_MISSMATCHING_TYPE_ERROR = "Incompatible operand types. %s seems to be unrelated to %s.";

	public static Set<GipsMapping> extractMappings(final GipsBooleanExpression expression) {
		Set<GipsMapping> mappings = new HashSet<>();

		if (expression == null)
			return mappings;

		if (expression instanceof GipsBooleanImplication implication) {
			mappings.addAll(extractMappings(implication.getLeft()));
			mappings.addAll(extractMappings(implication.getRight()));
		} else if (expression instanceof GipsBooleanDisjunction or) {
			mappings.addAll(extractMappings(or.getLeft()));
			mappings.addAll(extractMappings(or.getRight()));
		} else if (expression instanceof GipsBooleanConjunction and) {
			mappings.addAll(extractMappings(and.getLeft()));
			mappings.addAll(extractMappings(and.getRight()));
		} else if (expression instanceof GipsBooleanNegation not) {
			mappings.addAll(extractMappings(not.getOperand()));
		} else if (expression instanceof GipsBooleanBracket bracket) {
			mappings.addAll(extractMappings(bracket.getOperand()));
		} else if (expression instanceof GipsBooleanLiteral) {
			return mappings;
		} else if (expression instanceof GipsRelationalExpression relationalExpr) {
			mappings.addAll(extractMappings(relationalExpr));
		} else if (expression instanceof GipsArithmeticExpression arithmeticExpr) {
			mappings.addAll(extractMappings(arithmeticExpr));
		} else {
			throw new UnsupportedOperationException("Unknown boolean expression type: " + expression);
		}

		return mappings;

	}

	public static Set<GipsMapping> extractMappings(final GipsRelationalExpression expression) {
		Set<GipsMapping> mappings = new HashSet<>();

		if (expression == null)
			return mappings;

		mappings.addAll(extractMappings(expression.getLeft()));
		mappings.addAll(extractMappings(expression.getRight()));

		return mappings;
	}

	public static Set<GipsMapping> extractMappings(final GipsArithmeticExpression expression) {
		Set<GipsMapping> mappings = new HashSet<>();

		if (expression == null)
			return mappings;

		if (expression instanceof GipsArithmeticSum sum) {
			mappings.addAll(extractMappings(sum.getLeft()));
			mappings.addAll(extractMappings(sum.getRight()));
		} else if (expression instanceof GipsArithmeticProduct product) {
			mappings.addAll(extractMappings(product.getLeft()));
			mappings.addAll(extractMappings(product.getRight()));
		} else if (expression instanceof GipsArithmeticExponential exp) {
			mappings.addAll(extractMappings(exp.getLeft()));
			mappings.addAll(extractMappings(exp.getRight()));
		} else if (expression instanceof GipsArithmeticUnary un) {
			mappings.addAll(extractMappings(un.getOperand()));
		} else if (expression instanceof GipsArithmeticBracket bracket) {
			mappings.addAll(extractMappings(bracket.getOperand()));
		} else if (expression instanceof GipsValueExpression atr) {
			mappings.addAll(extractMappings(atr));
		} else if (expression instanceof GipsLinearFunctionReference) {
			return mappings;
		} else if (expression instanceof GipsArithmeticLiteral) {
			return mappings;
		} else if (expression instanceof GipsArithmeticConstant) {
			return mappings;
		} else if (expression instanceof GipsConstantReference reference) {
			if (reference.getConstant() == null) {
				return mappings;
			}
			if (reference.getConstant().getExpression() == null) {
				return mappings;
			}
			mappings.addAll(extractMappings(reference.getConstant().getExpression()));
		} else {
			throw new UnsupportedOperationException("Unknown arithmetic expression type: " + expression);
		}

		return mappings;
	}

	public static Set<GipsMapping> extractMappings(final GipsValueExpression expression) {
		Set<GipsMapping> mappings = new HashSet<>();

		if (expression == null)
			return mappings;

		if (expression.getValue() == null)
			return mappings;

		EObject value = expression.getValue();

		if (value instanceof GipsMappingExpression mapping) {
			mappings.add(mapping.getMapping());
		} else if (value instanceof GipsLocalContextExpression local) {
			EObject localContext = GipslScopeContextUtil.getLocalContext(local);
			if (localContext instanceof GipsMapping mapping) {
				mappings.add(mapping);
			}
		}

		return mappings;
	}

}
