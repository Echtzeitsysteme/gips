package org.emoflon.gips.gipsl.validation;

import org.emoflon.gips.gipsl.gipsl.GipsAttributeExpression;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;

public class GipslAttributeValidator {
	private GipslAttributeValidator() {

	}

	public static void checkAttributeExpression(final GipsAttributeExpression expression) {
		if (GipslValidator.DISABLE_VALIDATOR)
			return;

		if (expression == null)
			return;

		checkAttributeChaining(expression);
	}

	private static void checkAttributeChaining(GipsAttributeExpression expression) {
		if (expression.getAttribute() == null || expression.getAttribute().getLiteral() == null)
			return;

		if (expression.getAttribute().getLiteral().isMany() && expression.getRight() != null) {
			// Unable to chain expressions on many references
			// e.g., manyAttribute.singleAttribute

			GipslValidator.err( //
					String.format(GipslValidatorUtil.ATTRIBUTE_ACCESS_ON_MANY,
							expression.getAttribute().getLiteral().getName()), //
					expression, //
					GipslPackage.Literals.GIPS_ATTRIBUTE_EXPRESSION__RIGHT //
			);
		}

	}
}
