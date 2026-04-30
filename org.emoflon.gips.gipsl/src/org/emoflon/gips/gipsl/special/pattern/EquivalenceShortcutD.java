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
 * Matches:
 * <ul>
 * <li>A == 0 <-> B == 1
 * </ul>
 * 
 */
public class EquivalenceShortcutD extends AbstractPatternMatcher {

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

		if (implication.getOperator() != ImplicationOperator.SC_EQUIVALENCE)
			return;

		if (!(skipBrackets(implication.getLeft()) instanceof GipsRelationalExpression leftRelational
				&& skipBrackets(implication.getRight()) instanceof GipsRelationalExpression rightRelational))
			return;

		if (!hasMatch()) {
			nodeA = matchNode(leftRelational, "1");
			nodeB = matchNode(rightRelational, "0");
			clearPartialMatch();
		}

		if (!hasMatch()) {
			nodeA = matchNode(rightRelational, "1");
			nodeB = matchNode(leftRelational, "0");
			clearPartialMatch();
		}
	}

	private GipsArithmeticExpression matchNode(GipsRelationalExpression relational, String expectedConstant) {
		if (relational.getOperator() == RelationalOperator.EQUAL) {
			if (skipBrackets(relational.getLeft()) instanceof GipsValueExpression exp
					&& skipBrackets(relational.getRight()) instanceof GipsArithmeticLiteral literal
					&& expectedConstant.equals(literal.getValue())) {
				// N == c
				return exp;
			} else if (skipBrackets(relational.getRight()) instanceof GipsValueExpression exp
					&& skipBrackets(relational.getLeft()) instanceof GipsArithmeticLiteral literal
					&& expectedConstant.equals(literal.getValue())) {
				// c == N
				return exp;
			}
		}

		return null;
	}

	@Override
	public Collection<String> patterns() {
		return Collections.singleton("A == 0 <-> B == 1");
	}

}
