package org.emoflon.gips.gipsl.special.pattern;

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
 * <li>A == 0 <-> B == 1
 * <li>A == 0 <-> 1 == B
 * <li>0 == A <-> B == 1
 * <li>0 == A <-> 1 == B
 * <li>B == 1 <-> A == 0
 * <li>B == 1 <-> 0 == A
 * <li>1 == B <-> A == 0
 * <li>1 == B <-> 0 == A
 * </ul>
 * 
 */
public class EquivalenceShortcutC extends AbstractPatternMatcher {

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
			matchNodeA(leftRelational);
			matchNodeB(rightRelational);
			clearPartialMatch();
		}

		if (!hasMatch()) {
			matchNodeA(rightRelational);
			matchNodeB(leftRelational);
			clearPartialMatch();
		}
	}

	private void matchNodeA(GipsRelationalExpression relational) {
		if (relational.getOperator() == RelationalOperator.EQUAL) {
			if (skipBrackets(relational.getLeft()) instanceof GipsValueExpression exp
					&& skipBrackets(relational.getRight()) instanceof GipsArithmeticLiteral literal
					&& "0".equals(literal.getValue())) {
				nodeA = exp;
			} else if (skipBrackets(relational.getRight()) instanceof GipsValueExpression exp
					&& skipBrackets(relational.getLeft()) instanceof GipsArithmeticLiteral literal
					&& "0".equals(literal.getValue())) {
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

}
