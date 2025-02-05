package org.emoflon.gips.gipsl.validation;

import org.emoflon.gips.gipsl.gipsl.EditorFile;
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunction;
import org.emoflon.gips.gipsl.gipsl.GipsObjective;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;
import org.emoflon.gips.gipsl.gipsl.impl.EditorFileImpl;
import org.emoflon.gips.gipsl.util.GipslResourceManager;
import org.emoflon.ibex.common.slimgt.util.SlimGTModelUtil;

public class GipslObjectiveValidator {

	final protected GipslResourceManager gipslManager;

	public GipslObjectiveValidator(final GipslResourceManager gipslManager) {
		this.gipslManager = gipslManager;
	}

	/**
	 * Checks the global objective regarding the use of dynamic sub types like
	 * 'self.value()' in non-linear mathematical expressions.
	 * 
	 * @param obj Gips objective to validate/check.
	 */
	public static void checkObjective(final GipsObjective obj) {
		if (GipslValidator.DISABLE_VALIDATOR) {
			return;
		}

		if (obj == null) {
			return;
		}

		// Check arithmetic expression and spool errors
		GipslExpressionValidator.checkArithmeticExpression(obj.getExpression()).forEach(err -> err.run());
	}

	/**
	 * Runs all checks for a given linear function.
	 * 
	 * @param function Gips linear function to check.
	 */
	public void checkLinearFunction(final GipsLinearFunction function) {
		if (GipslValidator.DISABLE_VALIDATOR) {
			return;
		}

		if (function == null) {
			return;
		}

		// Check for bad names
		checkObjectiveNameValid(function);

		// Check uniqueness of name
		checkObjectiveNameUnique(function);

		// Check arithmetic expression and spool errors
		GipslExpressionValidator.checkArithmeticExpression(function.getExpression()).forEach(err -> err.run());
	}

	/**
	 * Checks for validity of an linear function name. The name must not be on the
	 * list of invalid names, the name should be in lowerCamelCase, and the name
	 * should start with a lower case character.
	 * 
	 * @param function Gips linear function to check.
	 */
	public static void checkObjectiveNameValid(final GipsLinearFunction function) {
		if (function == null || function.getName() == null) {
			return;
		}

		if (GipslValidatorUtil.INVALID_NAMES.contains(function.getName())) {
			GipslValidator.err( //
					String.format(GipslValidatorUtil.FUNCTION_NAME_FORBIDDEN_MESSAGE, function.getName()), //
					GipslPackage.Literals.GIPS_LINEAR_FUNCTION__NAME, GipslValidatorUtil.NAME_BLOCKED);
		} else {
			// The objective name should be lowerCamelCase.
			if (function.getName().contains("_")) {
				GipslValidator.warn( //
						String.format(GipslValidatorUtil.FUNCTION_NAME_CONTAINS_UNDERSCORES_MESSAGE,
								function.getName()), //
						GipslPackage.Literals.GIPS_LINEAR_FUNCTION__NAME, GipslValidatorUtil.NAME_BLOCKED);
			} else {
				// The objective name should start with a lower case character.
				if (!Character.isLowerCase(function.getName().charAt(0))) {
					GipslValidator.warn( //
							String.format(GipslValidatorUtil.FUNCTION_NAME_STARTS_WITH_LOWER_CASE_MESSAGE,
									function.getName()), //
							GipslPackage.Literals.GIPS_LINEAR_FUNCTION__NAME);
				}
			}
		}
	}

	/**
	 * Checks the uniqueness of the name of a given Gips linear function.
	 * 
	 * @param function Gips linear function to check uniqueness of the name for.
	 */
	public void checkObjectiveNameUnique(final GipsLinearFunction function) {
		if (function == null || function.eContainer() == null) {
			return;
		}

		EditorFile editorFile = (EditorFile) SlimGTModelUtil.getContainer(function, EditorFileImpl.class);
		final long count = gipslManager.getAllFunctionsInScope(editorFile).stream()
				.filter(o -> o.getName() != null && o.getName().equals(function.getName())).count();
		if (count != 1) {
			GipslValidator.err( //
					String.format(GipslValidatorUtil.FUNCTION_NAME_MULTIPLE_DECLARATIONS_MESSAGE, function.getName(),
							GipslValidatorUtil.getTimes((int) count)), //
					GipslPackage.Literals.GIPS_LINEAR_FUNCTION__NAME);
		}
	}

}
