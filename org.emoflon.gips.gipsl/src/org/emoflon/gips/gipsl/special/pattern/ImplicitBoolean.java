package org.emoflon.gips.gipsl.special.pattern;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanBracket;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanConjunction;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanDisjunction;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanImplication;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanNegation;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunction;
import org.emoflon.gips.gipsl.gipsl.GipsObjective;
import org.emoflon.gips.gipsl.gipsl.GipsValueExpression;
import org.emoflon.gips.gipsl.special.AbstractPatternMatcher;
import org.emoflon.gips.gipsl.special.PatternHelper;
import org.emoflon.gips.gipsl.validation.GipslExpressionValidator;
import org.emoflon.gips.gipsl.validation.GipslExpressionValidator.ExpressionData;
import org.emoflon.gips.gipsl.validation.GipslExpressionValidator.ExpressionType;

/**
 * This pattern matches binary value expressions if and only if they are within
 * a boolean expression, not a set and not part of a relational expression.
 * Examples are:
 * <ul>
 * <li>A (will match A)
 * <li>A & B == 1 (will match A, not B) *
 * <li>A -> B (will match A and B)
 * </ul>
 * 
 * Negative examples are:
 * <ul>
 * <li>A <= 1 (will not match A)
 * <li>A->sum (will not match A)
 * <li>A + B (will not match A or B)
 * </ul>
 */
public class ImplicitBoolean extends AbstractPatternMatcher {

	private GipsArithmeticExpression nodeA;
	private boolean isNegated;

	public GipsArithmeticExpression getNodeA() {
		return nodeA;
	}

	public boolean isNegated() {
		return isNegated;
	}

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

		if (!(expression instanceof GipsValueExpression valueExpression))
			return;

		// ignore cascading brackets, e.g. ![[[A]]]
		var container = PatternHelper.peelBracketsContainer(expression);
		if (!(container instanceof GipsBooleanNegation || //
				container instanceof GipsBooleanConjunction || //
				container instanceof GipsBooleanDisjunction || //
				container instanceof GipsBooleanImplication || //
				container instanceof GipsBooleanBracket || //
				container instanceof GipsConstraint || //
				container instanceof GipsLinearFunction || //
				container instanceof GipsObjective))
			return;

		isNegated = container instanceof GipsBooleanNegation;

		ExpressionData expressionData = GipslExpressionValidator.evaluate(valueExpression, new ArrayList<>());
		if (expressionData.isType(ExpressionType.Boolean) && expressionData.isVariable() && !expressionData.isMany())
			nodeA = valueExpression;

	}

}
