package org.emoflon.gips.gipsl.validation;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.gipsl.gipsl.GipsLocalContextExpression;
import org.emoflon.gips.gipsl.gipsl.GipsMapping;
import org.emoflon.gips.gipsl.gipsl.GipsMappingExpression;
import org.emoflon.gips.gipsl.gipsl.GipsSetElementExpression;
import org.emoflon.gips.gipsl.gipsl.GipsVariableReferenceExpression;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;
import org.emoflon.gips.gipsl.scoping.GipslScopeContextUtil;

public class GipslVariableReferenceValidator {
	private GipslVariableReferenceValidator() {

	}

	public static void checkVariableReference(final GipsVariableReferenceExpression expression) {
		if (GipslValidator.DISABLE_VALIDATOR)
			return;

		if (expression == null)
			return;

		checkValueReference(expression);
		checkVariablesReference(expression);
	}

	private static void checkValueReference(GipsVariableReferenceExpression expression) {
		if (!expression.isIsMappingValue())
			return;

		checkValueIsOnlyUsedWithMappings(expression);
	}

	private static void checkValueIsOnlyUsedWithMappings(GipsVariableReferenceExpression expression) {
		boolean validUsage = false;

		EObject container = expression.eContainer();

		if (container instanceof GipsLocalContextExpression) {
			EObject localContext = GipslScopeContextUtil.getLocalContext(expression);
			validUsage = (localContext instanceof GipsMapping);
		} else if (container instanceof GipsSetElementExpression) {
			EObject setContext = GipslScopeContextUtil.getSetContext(expression);
			validUsage = (setContext instanceof GipsMappingExpression);
		}

		if (!validUsage) {
			GipslValidator.err( //
					GipslValidatorUtil.MAPPING_VALUE_MISUSE, expression, //
					GipslPackage.Literals.GIPS_VARIABLE_REFERENCE_EXPRESSION__IS_MAPPING_VALUE //
			);
		}
	}

	private static void checkVariablesReference(GipsVariableReferenceExpression expression) {
		if (!expression.isIsGenericValue())
			return;

	}
}
