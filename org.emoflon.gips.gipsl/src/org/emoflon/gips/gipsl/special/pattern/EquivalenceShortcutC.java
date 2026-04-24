package org.emoflon.gips.gipsl.special.pattern;

import static org.emoflon.gips.gipsl.special.PatternHelper.searchBooleanTree;
import static org.emoflon.gips.gipsl.special.PatternHelper.skipBrackets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import org.emoflon.gips.gipsl.special.PatternHelper.JunctionType;

/**
 * Matches:
 * <ul>
 * <li>A == 1 <-> B == 1 (| C == 1 | D == 1 | ...)
 * </ul>
 * 
 */
public class EquivalenceShortcutC extends AbstractPatternMatcher {

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

		if (!hasMatch()) { // A <-> B ...
			matchNodeA(implication.getLeft(), "1");
			if (nodeA != null)
				matchOtherSide(implication.getRight(), "1");
			clearPartialMatch();
		}

		if (!hasMatch()) { // B ... <-> A
			matchNodeA(implication.getRight(), "1");
			if (nodeA != null)
				matchOtherSide(implication.getLeft(), "1");
			clearPartialMatch();
		}
	}

	private void matchNodeA(GipsBooleanExpression expression, String expectedConstant) {
		if (!(expression instanceof GipsRelationalExpression relational))
			return;

		if (relational.getOperator() == RelationalOperator.EQUAL) {
			if (skipBrackets(relational.getLeft()) instanceof GipsValueExpression exp
					&& skipBrackets(relational.getRight()) instanceof GipsArithmeticLiteral literal
					&& expectedConstant.equals(literal.getValue())) {
				nodeA = exp;
			} else if (skipBrackets(relational.getRight()) instanceof GipsValueExpression exp
					&& skipBrackets(relational.getLeft()) instanceof GipsArithmeticLiteral literal
					&& expectedConstant.equals(literal.getValue())) {
				nodeA = exp;
			}
		}
	}

	private void matchOtherSide(GipsBooleanExpression expression, String expectedConstant) {
		searchBooleanTree(expression, JunctionType.Disjunction, otherNodes,
				(r, m) -> checkForMatch(r, m, expectedConstant));
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

	@Override
	public Collection<String> patterns() {
		return Collections.singleton("A == 1 <-> B == 1 (| C == 1 | ...)");
	}

}
