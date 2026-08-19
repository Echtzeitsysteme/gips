package org.emoflon.gips.gipsl.validation;

import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.gips.gipsl.gipsl.GipsVariable;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;

public class GipslVariableValidator {
	private GipslVariableValidator() {

	}

	public static void checkVariable(final GipsVariable variable) {
		if (GipslValidator.DISABLE_VALIDATOR)
			return;

		if (variable == null)
			return;

		checkBounds(variable);
	}

	private static void checkBounds(GipsVariable variable) {
		if (variable.getInterval() == null)
			return;

		if (variable.getType() == EcorePackage.Literals.EBOOLEAN) {
			GipslValidator.err(GipslValidatorUtil.VARIABLE_BOUNDS_SUPPORT_ERROR, //
					variable, //
					GipslPackage.Literals.GIPS_VARIABLE__INTERVAL);
		}
	}

}
