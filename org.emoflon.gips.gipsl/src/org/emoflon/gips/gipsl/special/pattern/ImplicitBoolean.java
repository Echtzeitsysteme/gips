package org.emoflon.gips.gipsl.special.pattern;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpression;
import org.emoflon.gips.gipsl.gipsl.GipsAttributeExpression;
import org.emoflon.gips.gipsl.gipsl.GipsAttributeLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanBracket;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanConjunction;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanDisjunction;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanImplication;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanNegation;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsLocalContextExpression;
import org.emoflon.gips.gipsl.gipsl.GipsNodeExpression;
import org.emoflon.gips.gipsl.gipsl.GipsSetElementExpression;
import org.emoflon.gips.gipsl.gipsl.GipsValueExpression;
import org.emoflon.gips.gipsl.gipsl.GipsVariable;
import org.emoflon.gips.gipsl.gipsl.GipsVariableReferenceExpression;
import org.emoflon.gips.gipsl.special.AbstractPatternMatcher;
import org.emoflon.gips.gipsl.validation.GipslExpressionValidator;
import org.emoflon.gips.gipsl.validation.GipslExpressionValidator.ExpressionData;
import org.emoflon.gips.gipsl.validation.GipslExpressionValidator.ExpressionType;

public class ImplicitBoolean extends AbstractPatternMatcher {

	public GipsArithmeticExpression nodeA;

	@Override
	protected void resetMatch() {
		nodeA = null;
	}

	@Override
	protected boolean hasMatch() {
		return nodeA != null;
	}

	@Override
	public Collection<String> patterns() {
		return Collections.singleton("A");
	}

	@Override
	protected void tryMatchPattern(GipsBooleanExpression expression) {

		var container = expression.eContainer();
		if (!(container instanceof GipsBooleanNegation || //
				container instanceof GipsBooleanConjunction || //
				container instanceof GipsBooleanDisjunction || //
				container instanceof GipsBooleanImplication || //
				container instanceof GipsBooleanBracket || //
				container instanceof GipsConstraint))
			return;

		if (!(expression instanceof GipsValueExpression valueExpression))
			return;

		ExpressionData expressionData = GipslExpressionValidator.evaluate(valueExpression, new ArrayList<>());
		if (expressionData.isType(ExpressionType.Boolean) && expressionData.isVariable() && !expressionData.isMany())
			nodeA = valueExpression;

	}

	private boolean isOfBooleanType(GipsValueExpression expression) {
		if (expression.getSetExpression() != null)
			return false;

		return switch (expression.getValue()) {
		case GipsLocalContextExpression localContext -> isOfBooleanType(localContext);
		case GipsSetElementExpression setContext -> isOfBooleanType(setContext);
		case null, default -> false;
		};
	}

	private boolean isOfBooleanType(GipsSetElementExpression expression) {
		return switch (expression.getExpression()) {
		case GipsNodeExpression node -> isOfBooleanType(node);
		case GipsVariableReferenceExpression variable -> isOfBooleanType(variable);
		case GipsAttributeExpression attribute -> isOfBooleanType(attribute);
		case null, default -> false;
		};
	}

	private boolean isOfBooleanType(GipsLocalContextExpression expression) {
		return switch (expression.getExpression()) {
		case GipsNodeExpression node -> isOfBooleanType(node);
		case GipsVariableReferenceExpression variable -> isOfBooleanType(variable);
		case GipsAttributeExpression attribute -> isOfBooleanType(attribute);
		case null, default -> false;
		};
	}

	private boolean isOfBooleanType(GipsNodeExpression expression) {
		return switch (expression.getExpression()) {
		case GipsVariableReferenceExpression variable -> isOfBooleanType(variable);
		case GipsAttributeExpression attribute -> isOfBooleanType(attribute);
		case null, default -> false;
		};
	}

	private boolean isOfBooleanType(GipsVariableReferenceExpression expression) {
		if (expression.isIsMappingValue())
			return true;

		if (expression.isIsGenericValue() && expression.getVariable() != null)
			return isOfBooleanType(expression.getVariable());

		return false;
	}

	private boolean isOfBooleanType(GipsAttributeExpression expression) {
		if (expression.getAttribute() == null)
			return false;
		return isOfBooleanType(expression.getAttribute());
	}

	private boolean isOfBooleanType(GipsAttributeLiteral attribute) {
		if (attribute.getLiteral() == null)
			return false;
		return isOfBooleanType(attribute.getLiteral().getEType());
	}

	private boolean isOfBooleanType(GipsVariable variable) {
		return isOfBooleanType(variable.getType());
	}

	private boolean isOfBooleanType(EClassifier eType) {
		return EcorePackage.Literals.EBOOLEAN.equals(eType);
	}

}
