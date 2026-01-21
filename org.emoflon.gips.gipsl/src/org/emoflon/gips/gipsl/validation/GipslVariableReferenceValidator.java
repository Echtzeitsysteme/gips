package org.emoflon.gips.gipsl.validation;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.gipsl.gipsl.GipsLocalContextExpression;
import org.emoflon.gips.gipsl.gipsl.GipsMapping;
import org.emoflon.gips.gipsl.gipsl.GipsMappingExpression;
import org.emoflon.gips.gipsl.gipsl.GipsSetElementExpression;
import org.emoflon.gips.gipsl.gipsl.GipsVariableReferenceExpression;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;
import org.emoflon.gips.gipsl.gipsl.impl.GipsLocalContextExpressionImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsSetElementExpressionImpl;
import org.emoflon.gips.gipsl.scoping.GipslScopeContextUtil;
import org.emoflon.gips.intermediate.GipsIntermediate.AttributeExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.impl.AttributeExpressionImpl;

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
		boolean notUsedWithMapping = false;

		EObject container = (EObject) GipslScopeContextUtil.getContainer(expression,
				Set.of(AttributeExpressionImpl.class, GipsLocalContextExpressionImpl.class,
						GipsSetElementExpressionImpl.class));

		if (container instanceof AttributeExpression) {
			notUsedWithMapping = true; // value reference follows an attribute expression
		} else if (container instanceof GipsLocalContextExpression) {
			EObject localContext = GipslScopeContextUtil.getLocalContext(expression);
			notUsedWithMapping = !(localContext instanceof GipsMapping);
		} else if (container instanceof GipsSetElementExpression) {
			EObject setContext = GipslScopeContextUtil.getSetContext(expression);
			notUsedWithMapping = !(setContext instanceof GipsMappingExpression);
		}

		if (notUsedWithMapping) {
			GipslValidator.err( //
					"'value' can only be used with mappings", // TODO
					expression, //
					GipslPackage.Literals.GIPS_VARIABLE_REFERENCE_EXPRESSION__IS_MAPPING_VALUE //
			);
		}
	}

	private static void checkVariablesReference(GipsVariableReferenceExpression expression) {
		if (!expression.isIsGenericValue())
			return;

		EObject container = (EObject) GipslScopeContextUtil.getContainer(expression,
				Set.of(AttributeExpressionImpl.class, GipsLocalContextExpressionImpl.class,
						GipsSetElementExpressionImpl.class));

		// TODO
	}
}
