package org.emoflon.gips.gipsl.special.pattern;

import static org.emoflon.gips.gipsl.special.PatternHelper.skipBrackets;

import java.util.Collection;
import java.util.Collections;

import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpression;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanImplication;
import org.emoflon.gips.gipsl.gipsl.GipsRelationalExpression;
import org.emoflon.gips.gipsl.gipsl.GipsValueExpression;
import org.emoflon.gips.gipsl.gipsl.ImplicationOperator;
import org.emoflon.gips.gipsl.gipsl.RelationalOperator;
import org.emoflon.gips.gipsl.special.AbstractPatternMatcher;

/**
 * 
 * Input:
 * <ul>
 * <li>A == 1 -> B == 1
 * </ul>
 * 
 */
public class ImplicationShortcutA extends AbstractPatternMatcher {

	public GipsArithmeticExpression nodeA;
	public GipsArithmeticExpression nodeB;

	protected void resetMatch() {
		nodeA = null;
		nodeB = null;
	}

	protected boolean hasMatch() {
		return nodeA != null && nodeB != null;
	}

	@Override
	public void tryMatchPattern(GipsBooleanExpression expression) {
		if (!(expression instanceof GipsBooleanImplication implication))
			return;

		if (implication.getOperator() != ImplicationOperator.SC_IMPLICATION)
			return;

		if (!(skipBrackets(implication.getLeft()) instanceof GipsRelationalExpression leftRelational
				&& skipBrackets(implication.getRight()) instanceof GipsRelationalExpression rightRelational))
			return;

		if (!hasMatch()) {
			matchNodeA(leftRelational);
			matchNodeB(rightRelational);
			clearPartialMatch();
		}
	}

	private void matchNodeA(GipsRelationalExpression relational) {
		if (relational.getOperator() == RelationalOperator.EQUAL) {
			if (skipBrackets(relational.getLeft()) instanceof GipsValueExpression exp
					&& skipBrackets(relational.getRight()) instanceof GipsArithmeticLiteral literal
					&& "1".equals(literal.getValue())) {
				nodeA = exp;
			} else if (skipBrackets(relational.getRight()) instanceof GipsValueExpression exp
					&& skipBrackets(relational.getLeft()) instanceof GipsArithmeticLiteral literal
					&& "1".equals(literal.getValue())) {
				nodeA = exp;
			}
		}
	}

	private void matchNodeB(GipsRelationalExpression relational) {
		if (relational.getOperator() == RelationalOperator.EQUAL) {
			if (skipBrackets(relational.getLeft()) instanceof GipsValueExpression exp
					&& skipBrackets(relational.getRight()) instanceof GipsArithmeticLiteral literal
					&& "1".equals(literal.getValue())) {
				nodeB = exp;
			} else if (skipBrackets(relational.getRight()) instanceof GipsValueExpression exp
					&& skipBrackets(relational.getLeft()) instanceof GipsArithmeticLiteral literal
					&& "1".equals(literal.getValue())) {
				nodeB = exp;
			}
		}
	}

	@Override
	public Collection<String> patterns() {
		return Collections.singleton("A == 1 -> B == 1");
	}

}
