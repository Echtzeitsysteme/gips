package org.emoflon.gips.gipsl.special.pattern;

import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpression;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanImplication;
import org.emoflon.gips.gipsl.gipsl.GipsRelationalExpression;
import org.emoflon.gips.gipsl.gipsl.GipsValueExpression;
import org.emoflon.gips.gipsl.gipsl.ImplicationOperator;
import org.emoflon.gips.gipsl.gipsl.RelationalOperator;
import org.emoflon.gips.gipsl.special.AbstractPatternFinder;

/**
 * 
 * Input:
 * <ul>
 * <li>A <= 1 <-> B == 1
 * <li>1 >= A <-> B == 1
 * <li>A <= 1 <-> 1 == B
 * <li>1 >= A <-> 1 == B
 * <li>B == 1 <-> A <= 1
 * <li>B == 1 <-> 1 >= A
 * <li>1 == B <-> A <= 1
 * <li>1 == B <-> 1 >= A
 * </ul>
 * 
 * With:
 * <ul>
 * <li>A, B as {@code GipsArithmeticExpression}
 * </ul>
 */
public class EquivalenceShortcutA extends AbstractPatternFinder {

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
	public void findPattern(GipsBooleanExpression expression) {
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
		if (relational.getOperator() == RelationalOperator.GREATER_OR_EQUAL) {
			if (skipBrackets(relational.getLeft()) instanceof GipsValueExpression exp
					&& skipBrackets(relational.getRight()) instanceof GipsArithmeticLiteral literal
					&& "1".equals(literal.getValue())) {
				nodeA = exp;
			}
		} else if (relational.getOperator() == RelationalOperator.SMALLER_OR_EQUAL) {
			if (skipBrackets(relational.getRight()) instanceof GipsValueExpression exp
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

}
