package org.emoflon.gips.gipsl.validation;

import org.eclipse.xtext.validation.Check;
import org.emoflon.gips.gipsl.gipsl.EditorGTFile;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpr;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsBracketExpr;
import org.emoflon.gips.gipsl.gipsl.GipsExpArithmeticExpr;
import org.emoflon.gips.gipsl.gipsl.GipsExpressionOperand;
import org.emoflon.gips.gipsl.gipsl.GipsGlobalObjective;
import org.emoflon.gips.gipsl.gipsl.GipsObjective;
import org.emoflon.gips.gipsl.gipsl.GipsObjectiveExpression;
import org.emoflon.gips.gipsl.gipsl.GipsProductArithmeticExpr;
import org.emoflon.gips.gipsl.gipsl.GipsSumArithmeticExpr;
import org.emoflon.gips.gipsl.gipsl.GipsUnaryArithmeticExpr;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;
import org.emoflon.gips.gipsl.validation.GipslValidatorUtils.ContextType;
import org.emoflon.gips.gipsl.validation.GipslValidatorUtils.EvalType;

public class GipslObjectiveValidator {

	private GipslObjectiveValidator() {
	}

	/**
	 * Checks if a global objective is specified in a given file. This must hold if
	 * there is any local objective defined. Furthermore, it displays a warning if
	 * the user specified a global objective but there is no local objective in the
	 * given file.
	 * 
	 * @param file File to check existence of a global objective for.
	 */
	@Check
	public static void checkGlobalObjectiveNotNull(final EditorGTFile file) {
		if (GipslValidator.DISABLE_VALIDATOR) {
			return;
		}

		if (file == null) {
			return;
		}

		if (file.getObjectives() != null && !file.getObjectives().isEmpty() && file.getGlobalObjective() == null) {
			GipslValidator.err( //
					GipslValidatorUtils.GLOBAL_OBJECTIVE_IS_NULL_MESSAGE, //
					// TODO: Change scope of the warning:
					GipslPackage.Literals.EDITOR_GT_FILE__GLOBAL_OBJECTIVE, //
					GipslValidatorUtils.GLOBAL_OBJECTIVE_DOES_NOT_EXIST //
			);
		} else if (file.getObjectives() != null && file.getObjectives().isEmpty()
				&& file.getGlobalObjective() != null) {
			GipslValidator.warn( //
					GipslValidatorUtils.GLOBAL_OBJECTIVE_IS_OPTIONAL_MESSAGE, //
					GipslPackage.Literals.EDITOR_GT_FILE__GLOBAL_OBJECTIVE //
			);
		}
	}

	/**
	 * Checks the global objective regarding the use of dynamic sub types like
	 * 'self.value()' in non-linear mathematical expressions.
	 * 
	 * @param globObj Gips global objective to validate/check.
	 */
	@Check
	public static void checkGlobalObjective(final GipsGlobalObjective globObj) {
		if (GipslValidator.DISABLE_VALIDATOR) {
			return;
		}

		if (globObj == null) {
			return;
		}

		// Validate expression regarding dynamic uses (like self.value())
		GipslValidator.validateArithExprDynamic(globObj.getExpr());

		// Check if global objective contains any reference to a local objective
		// If this is not the case, display a warning that the global objective is
		// constant
		if (!containsLocalObjectiveCall(globObj.getExpr())) {
			GipslValidator.warn( //
					GipslValidatorUtils.GLOBAL_OBJECTIVE_DOES_NOT_CONTAIN_LOCAL_OBJECTIVE_MESSAGE, //
					GipslPackage.Literals.GIPS_GLOBAL_OBJECTIVE__EXPR //
			);
		}
	}

	/**
	 * Returns true if the given arithmetic expression contains at least one call to
	 * a local Gips objective.
	 * 
	 * @param expr Gips arithmetic expression to check local objective call
	 *             existence for.
	 * @return True if the given arithmetic expression contains at least one call to
	 *         a local Gips objective.
	 */
	public static boolean containsLocalObjectiveCall(final GipsArithmeticExpr expr) {
		if (expr == null) {
			return false;
		}

		if (expr instanceof GipsBracketExpr bracketExpr) {
			return containsLocalObjectiveCall(bracketExpr.getOperand());
		} else if (expr instanceof GipsExpArithmeticExpr expExpr) {
			return containsLocalObjectiveCall(expExpr.getLeft()) || containsLocalObjectiveCall(expExpr.getRight());
		} else if (expr instanceof GipsExpressionOperand exprOp) {
			return exprOp instanceof GipsObjectiveExpression;
		} else if (expr instanceof GipsProductArithmeticExpr prodExpr) {
			return containsLocalObjectiveCall(prodExpr.getLeft()) || containsLocalObjectiveCall(prodExpr.getRight());
		} else if (expr instanceof GipsSumArithmeticExpr sumExpr) {
			return containsLocalObjectiveCall(sumExpr.getLeft()) || containsLocalObjectiveCall(sumExpr.getRight());
		} else if (expr instanceof GipsUnaryArithmeticExpr unExpr) {
			return containsLocalObjectiveCall(unExpr.getOperand());
		}

		throw new UnsupportedOperationException(GipslValidatorUtils.NOT_IMPLEMENTED_EXCEPTION_MESSAGE);
	}

	/**
	 * Runs all checks for a given objective.
	 * 
	 * @param objective Gips objective to check.
	 */
	@Check
	public static void checkObjective(final GipsObjective objective) {
		if (GipslValidator.DISABLE_VALIDATOR) {
			return;
		}

		if (objective == null) {
			return;
		}

		// Check for bad names
		checkObjectiveNameValid(objective);

		// Check uniqueness of name
		checkObjectiveNameUnique(objective);

		// Check if value is 0
		checkObjectiveIsNotUseless(objective);

		// Validate arithmetic expression regarding non-linear expressions that are not
		// constant in ILP time
		GipslValidator.validateArithExprDynamic(objective.getExpr());

		// Check if objective contains a 'self' call -> If not, display a warning
		checkObjectiveHasSelf(objective);

		// Check expression evaluation type
		final EvalType eval = GipslValidator.getEvalTypeFromArithExpr(objective.getExpr());
		if (!GipslValidatorUtils.isNumber(eval)) {
			GipslValidator.err( //
					GipslValidatorUtils.OBJECTIVE_EVAL_NOT_NUMBER_MESSAGE, //
					GipslPackage.Literals.GIPS_OBJECTIVE__EXPR //
			);
		}
	}

	/**
	 * Checks that an objective has at least one 'self' reference.
	 * 
	 * @param objective Objective to validate.
	 */
	public static void checkObjectiveHasSelf(final GipsObjective objective) {
		if (objective == null || objective.getExpr() == null || objective.getContext() == null) {
			return;
		}

		final GipsArithmeticExpr expr = objective.getExpr();
		final GipslValidatorUtils.ContextType type = GipslValidator.getContextType(objective.getContext());

		// If context is global, no "self" must be used
		if (type == ContextType.GLOBAL) {
			return;
		}

		// Generate a warning if the objective does not contain 'self'
		if (!GipslValidator.containsSelf(expr, type)) {
			GipslValidator.warn( //
					String.format(GipslValidatorUtils.TYPE_DOES_NOT_CONTAIN_SELF_MESSAGE, "Objective"), //
					objective, //
					GipslPackage.Literals.GIPS_OBJECTIVE__EXPR //
			);
		}
	}

	/**
	 * Checks for validity of an objective name. The name must not be on the list of
	 * invalid names, the name should be in lowerCamelCase, and the name should
	 * start with a lower case character.
	 * 
	 * @param objective Gips objective to check.
	 */
	public static void checkObjectiveNameValid(final GipsObjective objective) {
		if (objective == null || objective.getName() == null) {
			return;
		}

		if (GipslValidatorUtils.INVALID_NAMES.contains(objective.getName())) {
			GipslValidator.err( //
					String.format(GipslValidatorUtils.OBJECTIVE_NAME_FORBIDDEN_MESSAGE, objective.getName()), //
					GipslPackage.Literals.GIPS_OBJECTIVE__NAME, GipslValidatorUtils.NAME_BLOCKED);
		} else {
			// The objective name should be lowerCamelCase.
			if (objective.getName().contains("_")) {
				GipslValidator.warn( //
						String.format(GipslValidatorUtils.OBJECTIVE_NAME_CONTAINS_UNDERSCORES_MESSAGE,
								objective.getName()), //
						GipslPackage.Literals.GIPS_OBJECTIVE__NAME, GipslValidatorUtils.NAME_BLOCKED);
			} else {
				// The objective name should start with a lower case character.
				if (!Character.isLowerCase(objective.getName().charAt(0))) {
					GipslValidator.warn( //
							String.format(GipslValidatorUtils.OBJECTIVE_NAME_STARTS_WITH_LOWER_CASE_MESSAGE,
									objective.getName()), //
							GipslPackage.Literals.GIPS_OBJECTIVE__NAME, //
							GipslValidator.NAME_EXPECT_LOWER_CASE //
					);
				}
			}
		}
	}

	/**
	 * Checks the uniqueness of the name of a given Gips objective.
	 * 
	 * @param objective Gips objective to check uniqueness of the name for.
	 */
	public static void checkObjectiveNameUnique(final GipsObjective objective) {
		if (objective == null || objective.eContainer() == null) {
			return;
		}

		final EditorGTFile container = (EditorGTFile) objective.eContainer();
		final long count = container.getObjectives().stream()
				.filter(o -> o.getName() != null && o.getName().equals(objective.getName())).count();
		if (count != 1) {
			GipslValidator.err( //
					String.format(GipslValidatorUtils.OBJECTIVE_NAME_MULTIPLE_DECLARATIONS_MESSAGE, objective.getName(),
							GipslValidator.getTimes((int) count)), //
					GipslPackage.Literals.GIPS_OBJECTIVE__NAME, //
					GipslValidator.NAME_EXPECT_UNIQUE //
			);
		}
	}

	/**
	 * Checks a given Gips objective for uselessness, i.e, if the objective is '0'.
	 * 
	 * @param objective Gips objective to check for uselessness.
	 */
	public static void checkObjectiveIsNotUseless(final GipsObjective objective) {
		if (objective == null) {
			return;
		}

		if (objective.getExpr() instanceof GipsArithmeticLiteral lit) {
			if (lit.getValue() != null && lit.getValue().equals("0")) {
				GipslValidator.warn( //
						String.format(GipslValidatorUtils.OBJECTIVE_VALUE_IS_ZERO_MESSAGE, objective.getName()), //
						GipslPackage.Literals.GIPS_OBJECTIVE__EXPR //
				);
			}
		}
	}

}
