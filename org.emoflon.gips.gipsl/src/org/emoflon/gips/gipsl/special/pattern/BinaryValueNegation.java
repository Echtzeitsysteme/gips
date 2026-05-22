package org.emoflon.gips.gipsl.special.pattern;

import java.util.Arrays;
import java.util.Collection;

import org.emoflon.gips.gipsl.gipsl.GipsArithmeticLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanNegation;
import org.emoflon.gips.gipsl.gipsl.GipsRelationalExpression;
import org.emoflon.gips.gipsl.gipsl.GipsValueExpression;
import org.emoflon.gips.gipsl.gipsl.RelationalOperator;
import org.emoflon.gips.gipsl.special.AbstractPatternMatcher;
import org.emoflon.gips.gipsl.special.PatternHelper;

/**
 * This pattern matches a negated relational expression where one side is a
 * binary value expression and the other is a constant value of 1 or 0. Examples
 * are:
 * <ul>
 * <li>!(A == 1)
 * <li>!(A == 0)
 * </ul>
 * 
 * Negative examples are:
 * <ul>
 * <li>!(A > 0) (will not match A)
 * <li>!(A == 2) (will not match A)
 * </ul>
 */
public class BinaryValueNegation extends AbstractPatternMatcher {

	private final ValueConstantRelation pattern = new ValueConstantRelation( //
			RelationalOperator.EQUAL, //
			this::matchLiteral);

	public GipsArithmeticLiteral getLiteral() {
		return pattern.getLiteral();
	}

	public GipsValueExpression getNodeA() {
		return pattern.getNodeA();
	}

	@Override
	public Collection<String> patterns() {
		return Arrays.asList("!(A == 1)", "!(A == 0)");
	}

	@Override
	protected void resetMatch() {
		pattern.resetMatch();
	}

	@Override
	protected boolean hasMatch() {
		return pattern.hasMatch();
	}

	@Override
	protected void tryMatchPattern(GipsBooleanExpression expression) {
		if (!(expression instanceof GipsBooleanNegation negation))
			return;

		GipsBooleanExpression innerExpression = PatternHelper.peelBrackets(negation.getOperand());
		if (innerExpression instanceof GipsRelationalExpression relational)
			matchNodes(relational);

	}

	private boolean matchLiteral(String literal) {
		return "1".equals(literal) || "0".equals(literal);
	}

	private void matchNodes(GipsRelationalExpression expression) {
		if (expression.getOperator() != RelationalOperator.EQUAL)
			return;

		pattern.matchPattern(expression);
	}

}
