package org.emoflon.gips.gipsl.special.pattern;

import static org.emoflon.gips.gipsl.special.PatternHelper.peelBrackets;
import static org.emoflon.gips.gipsl.special.PatternHelper.searchBooleanTree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanImplication;
import org.emoflon.gips.gipsl.gipsl.ImplicationOperator;
import org.emoflon.gips.gipsl.gipsl.RelationalOperator;
import org.emoflon.gips.gipsl.special.AbstractPatternMatcher;
import org.emoflon.gips.gipsl.special.PatternHelper.JunctionType;

/**
 * 
 * Matches:
 * <ul>
 * <li>A >= 1 <-> B == 1 (& C == 1 & ...)
 * </ul>
 * 
 */
public class EquivalenceShortcutA extends AbstractPatternMatcher {

	private GipsArithmeticExpression nodeA;
	private final List<GipsArithmeticExpression> otherNodes = new ArrayList<>();

	private final ValueConstantRelation isGreaterEqualPair = new ValueConstantRelation( //
			false, //
			RelationalOperator.GREATER_OR_EQUAL, //
			c -> "1".equals(c));

	private final ValueConstantRelation isEqualPair = new ValueConstantRelation( //
			false, //
			RelationalOperator.EQUAL, //
			c -> "1".equals(c));

	private final ImplicitBoolean isImplicitBool = new ImplicitBoolean();

	public GipsArithmeticExpression getNodeA() {
		return nodeA;
	}

	public List<GipsArithmeticExpression> getOtherNodes() {
		return otherNodes;
	}

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
			if (matchShortSide(implication.getLeft()))
				matchLongSide(implication.getRight());
			clearPartialMatch();
		}

		if (!hasMatch()) { // B ... <-> A
			if (matchShortSide(implication.getRight()))
				matchLongSide(implication.getLeft());
			clearPartialMatch();
		}
	}

	private boolean matchShortSide(GipsBooleanExpression expression) {
		expression = peelBrackets(expression);
		// A >= 1
		if (isGreaterEqualPair.matchPattern(expression))
			this.nodeA = isGreaterEqualPair.getNodeA();

		return this.nodeA != null;
	}

	private boolean matchLongSide(GipsBooleanExpression expression) {
		int checkedElements = searchBooleanTree(expression, JunctionType.Conjunction, exp -> {
			GipsArithmeticExpression pair = matchPair(exp);
			if (pair != null)
				otherNodes.add(pair);
		});
		// clear partial match
		if (checkedElements > otherNodes.size())
			otherNodes.clear();

		return !otherNodes.isEmpty();
	}

	private GipsArithmeticExpression matchPair(GipsBooleanExpression expression) {
		// A == 1
		if (isEqualPair.matchPattern(expression))
			return isEqualPair.getNodeA();

		// A
		if (isImplicitBool.matchPattern(expression) && !isImplicitBool.isNegated())
			return isImplicitBool.getNodeA();

		return null;
	}

	@Override
	public Collection<String> patterns() {
		return Collections.singleton("A >= 1 <-> B == 1 (& C == 1 & ...)");
	}

}
