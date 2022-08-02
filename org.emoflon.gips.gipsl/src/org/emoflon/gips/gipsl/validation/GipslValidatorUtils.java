package org.emoflon.gips.gipsl.validation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Utilities for the GIPSL validator.
 */
public class GipslValidatorUtils {

	/**
	 * Enumeration for the type of the leaf. This represents the output type of an
	 * evaluation.
	 */
	protected enum EvalType {
		BOOLEAN, // GipsBooleanLiteral
		INTEGER, //
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
				"transient", "try", "void", "volatile", "while",

				// New values
				"mapping", "objective", "global objective", "global", "min", "max", "constraint" };
		GipslValidatorUtils.INVALID_NAMES.addAll(Arrays.asList(invalidNames));
	}

	static final String CODE_PREFIX = "org.emoflon.gips.gipsl.";
	// General errors for named elements.
	public static final String NAME_BLOCKED = CODE_PREFIX + "name.blocked";
	public static final String NAME_EXPECT_CAMEL_CASE = GipslValidatorUtils.CODE_PREFIX + "name.expectCamelCase";
	public static final String NAME_EXPECT_LOWER_CASE = GipslValidatorUtils.CODE_PREFIX + "name.expectLowerCase";
	public static final String NAME_EXPECT_UNIQUE = GipslValidatorUtils.CODE_PREFIX + "name.expectUnique";

	public static final String GLOBAL_OBJECTIVE_DOES_NOT_EXIST = GipslValidatorUtils.CODE_PREFIX
			+ "objective.global.doesNotExist";

	public static final String GLOBAL_OBJECTIVE_IS_NULL_MESSAGE = "No specification of a global objective found but local objectives are used.";
	public static final String GLOBAL_OBJECTIVE_IS_OPTIONAL_MESSAGE = "The global objective is optional if no local objective is defined.";
	public static final String GLOBAL_OBJECTIVE_DOES_NOT_CONTAIN_LOCAL_OBJECTIVE_MESSAGE = "Global objective does not contain any reference to a local objective.";

	public static final String MAPPING_NAME_MULTIPLE_DECLARATIONS_MESSAGE = "Mapping '%s' must not be declared '%s'.";
	public static final String MAPPING_NAME_FORBIDDEN_MESSAGE = "Mappings cannot be be named '%s'. Use a different name.";
	public static final String MAPPING_NAME_CONTAINS_UNDERSCORES_MESSAGE = "Mapping name '%s' contains underscores. Use camelCase instead.";
	public static final String MAPPING_NAME_STARTS_WITH_LOWER_CASE_MESSAGE = "Mapping '%s' should start with a lower case character.";
	public static final String MAPPING_W_O_CONSTRAINTS_MESSAGE = "Mapping '%s' is not subject to any constraints.";
	public static final String MAPPING_W_O_CONSTRAINTS_AND_OBJECTIVE_MESSAGE = "Mapping '%s' is not subject to any constraints and not part of any objective function.";
	public static final String RULE_HAS_MULTIPLE_MAPPINGS = "Multiple mappings on rule <'%s'>";

	public static final String OBJECTIVE_NAME_MULTIPLE_DECLARATIONS_MESSAGE = "Objective '%s' must not be declared '%s'";
	public static final String OBJECTIVE_NAME_FORBIDDEN_MESSAGE = "Objectives cannot be be named '%s'. Use a different name.";
	public static final String OBJECTIVE_NAME_CONTAINS_UNDERSCORES_MESSAGE = "Objective name '%s' contains underscores. Use camelCase instead.";
	public static final String OBJECTIVE_NAME_STARTS_WITH_LOWER_CASE_MESSAGE = "Objective '%s' should start with a lower case character.";

	// Other errors for types
	public static final String OBJECTIVE_VALUE_IS_ZERO_MESSAGE = "Objective '%s' can be removed because its value is 0.";

	public static final String CONSTRAINT_EMPTY_MESSAGE = "Constraint is empty.";
	public static final String CONSTRAINT_EVAL_NOT_BOOLEAN_MESSAGE = "Constraint does not evaluate to a boolean";
	public static final String CONSTRAINT_EVAL_LITERAL_MESSAGE = "Constraint is always '%s'.";

	public static final String OBJECTIVE_EVAL_NOT_NUMBER_MESSAGE = "Objective does not evaluate to an integer or double.";
	public static final String OBJECTIVE_CONTEXT_CLASS_MESSAGE = "Objectives can not have a class as context.";

	public static final String LITERAL_NOT_PARSABLE_MESSAGE = "Literal is not parsable.";

	public static final String LAMBDA_EXPR_EVAL_NOT_PRIMITIVE_MESSAGE = "Lambda expression does not evaluate to a primitve type.";
	public static final String LAMBDA_EXPR_EVAL_LITERAL_MESSAGE = "Lambda expression is always '%s'.";
	public static final String LAMBDA_EXPR_EVAL_TYPE_ERROR = "Type error in lambda expression.";

	public static final String CONSTRAINT_DEFINED_MULTIPLE_TIMES_MESSAGE = "Constraint defined multiple times.";
	public static final String CONSTRAINT_HAS_NO_CONSTANT_SIDE_MESSAGE = "Constraint has no constant side.";
	public static final String CONSTRAINT_HAS_TWO_CONSTANT_SIDES_MESSAGE = "Constraint has only constant sides. Use GT to express this condition.";

	public static final String STREAM_ON_NON_COLLECTION_TYPE_MESSAGE = "Stream used on non collection type.";

	public static final String EXP_EXPR_NOT_CONSTANT_MESSAGE = "Exponential expression must be constant due to ILP.";
	public static final String PRODUCT_EXPR_NOT_CONSTANT_MESSAGE = "Product expressions can only have one dynamic sub expression due to ILP.";
	public static final String UNARY_ARITH_EXPR_NOT_CONSTANT_MESSAGE = "Unary arithmetic expression with operator '%s' must be constant due to ILP.";

	public static final String BOOL_EXPR_EVAL_ERROR_MESSAGE = "Boolean expression does not evaluate to boolean.";
	public static final String ARITH_EXPR_EVAL_ERROR_MESSAGE = "Arithmetic expression does not evaluate to a primitive type.";

	public static final String TYPE_DOES_NOT_CONTAIN_SELF_MESSAGE = "'%s' does not contain any self reference.";

	public static final String MAPPING_IN_MAPPING_FORBIDDEN_MESSAGE = "Mapping access within mapping context is forbidden.";
	public static final String IS_MAPPED_CALL_IN_CONTEXT_FORBIDDEN_MESSAGE = "\"isMapped()\" call in non mapping context is not possible.";

	// Exception error messages
	public static final String NOT_IMPLEMENTED_EXCEPTION_MESSAGE = "Not yet implemented";
	public static final String CONSTRAINT_CONTEXT_UNKNOWN_EXCEPTION_MESSAGE = "Context is neither a GipsType nor a GipsMapping.";

	// Number error messages
	public static final String SQRT_VALUE_SMALLER_THAN_ZERO = "Value in SQRT is smaller than 0.";

	/**
	 * Returns true if the provided type is primitive (number or boolean).
	 * 
	 * @param input EvalType to check.
	 * @return True if given EvalType is primitive.
	 */
	public static boolean isPrimitiveType(final EvalType input) {
		return input == EvalType.BOOLEAN || input != EvalType.INTEGER || input != EvalType.FLOAT
				|| input != EvalType.DOUBLE;
	}

}
