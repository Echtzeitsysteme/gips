package org.emoflon.gips.gipsl.special.pattern;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpression;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanImplication;
import org.emoflon.gips.gipsl.gipsl.GipsRelationalExpression;
import org.emoflon.gips.gipsl.gipsl.GipsValueExpression;
import org.emoflon.gips.gipsl.gipsl.ImplicationOperator;
import org.emoflon.gips.gipsl.gipsl.RelationalOperator;
import org.emoflon.gips.gipsl.special.AbstractPatternMatcher;
import org.emoflon.gips.gipsl.special.MultiPatternHelper;
import org.emoflon.gips.gipsl.special.MultiPatternHelper.SearchType;

/**
 * Input:
 * <ul>
 * <li>A == 1 <-> B == 1 (& C == 1 & D == 1 & ...)
 * </ul>
 * 
 */
public class EquivalenceShortcutB extends AbstractPatternMatcher {

	public GipsArithmeticExpression nodeA;
	public List<GipsArithmeticExpression> otherNodes = new ArrayList<>();;

	protected void resetMatch() {
		nodeA = null;
		otherNodes.clear();
	}

	protected boolean hasMatch() {
		return nodeA != null && !otherNodes.isEmpty();
	}

	@Override
	public void tryMatchPattern(GipsBooleanExpression expression) {
		if (!(expression instanceof GipsBooleanImplication implication))
			return;

		if (implication.getOperator() != ImplicationOperator.SC_EQUIVALENCE)
			return;

		List<GipsArithmeticExpression> matchesLeft = new LinkedList<>();
		List<GipsArithmeticExpression> matchesRight = new LinkedList<>();

		MultiPatternHelper.searchExpressionTree(implication.getLeft(), SearchType.Conjunction, matchesLeft,
				(r, m) -> checkForMatch(r, m, "1"));
		MultiPatternHelper.searchExpressionTree(implication.getRight(), SearchType.Conjunction, matchesRight,
				(r, m) -> checkForMatch(r, m, "1"));

		if (matchesLeft.size() == 1 && matchesRight.size() >= 1) {
			nodeA = matchesLeft.get(0);
			otherNodes.addAll(matchesRight);
		} else if (matchesRight.size() == 1 && matchesLeft.size() >= 1) {
			nodeA = matchesRight.get(0);
			otherNodes.addAll(matchesLeft);
		}
	}

	private void checkForMatch(GipsRelationalExpression expression, List<GipsArithmeticExpression> matches,
			String expectedConstant) {

		if (expression.getOperator() != RelationalOperator.EQUAL)
			return;

		if (expression.getLeft() instanceof GipsValueExpression exp //
				&& expression.getRight() instanceof GipsArithmeticLiteral literal //
				&& expectedConstant.equals(literal.getValue())) {

			matches.add(exp);

		} else if (expression.getRight() instanceof GipsValueExpression exp //
				&& expression.getLeft() instanceof GipsArithmeticLiteral literal //
				&& expectedConstant.equals(literal.getValue())) {

			matches.add(exp);

		}
	}

}
