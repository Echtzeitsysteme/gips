package org.emoflon.gips.gipsl.validation;

import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;

public class GipslConstraintValidator {

	private GipslConstraintValidator() {
	}

	/**
	 * Runs all checks for a given constraint.
	 * 
	 * @param constraint Gips constraint to check.
	 */
	public static void checkConstraint(final GipsConstraint constraint) {
		if (GipslValidator.DISABLE_VALIDATOR) {
			return;
		}

		if (constraint == null) {
			return;
		}

		if (constraint.getExpression() == null) {
			GipslValidator.err( //
					String.format(GipslValidatorUtil.CONSTRAINT_EMPTY_MESSAGE), //
					constraint, //
					GipslPackage.Literals.GIPS_CONSTRAINT__EXPRESSION //
			);
			return;
		}

		// Check boolean expression and spool errors
		GipslExpressionValidator.checkBooleanExpression(constraint.getExpression()).forEach(err -> err.run());
	}

}
