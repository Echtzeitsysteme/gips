package org.emoflon.gips.gipsl.special.pattern;

import static org.emoflon.gips.gipsl.special.PatternHelper.peelBrackets;
import static org.emoflon.gips.gipsl.special.PatternHelper.searchBooleanTree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
 * <li>A == 1 <-> B == 1 (& C == 1 & D == 1 & ...)
 * <li>A == 1 <-> B == 1 (& C == 0 & D == 1 & ...)
 * </ul>
 * 
 */
public class EquivalenceShortcutE extends AbstractPatternMatcher {

	public static final class MatchInfo {
		public final GipsArithmeticExpression node;
		public final boolean equalsZero;

		public MatchInfo(GipsArithmeticExpression node, boolean equalsZero) {
			this.node = Objects.requireNonNull(node);
			this.equalsZero = equalsZero;
		}
	}

	private MatchInfo nodeA;
	private final List<MatchInfo> otherNodes = new ArrayList<>();

	private final ImplicitBoolean isImplicitBool = new ImplicitBoolean();

	/**
	 * Matches: A == 1
	 */
	private final ValueConstantRelation isShortSide = new ValueConstantRelation( //
			false, //
			RelationalOperator.EQUAL, //
			c -> 1 == c);

	/**
	 * Matches: A == 1 or A == 0
	 */
	private final ValueConstantRelation isLongSide = new ValueConstantRelation( //
			false, //
			RelationalOperator.EQUAL, //
			c -> 1 == c | 0 == c);

	public MatchInfo getNodeA() {
		return nodeA;
	}

	public List<MatchInfo> getOtherNodes() {
		return otherNodes;
	}

	protected void resetMatch() {
		nodeA = null;
		otherNodes.clear();
	}

	protected boolean hasMatch() {
		return nodeA != null && !otherNodes.isEmpty();
	}

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

		this.nodeA = null;

		if (isShortSide.matchPattern(expression)) {
			this.nodeA = new MatchInfo(isShortSide.getNodeA(), false);

		} else if (isImplicitBool.matchPattern(expression) && !isImplicitBool.isNegated()) {
			this.nodeA = new MatchInfo(isImplicitBool.getNodeA(), false);

		}

		return this.nodeA != null;
	}

	private boolean matchLongSide(GipsBooleanExpression expression) {
		int checkedElements = searchBooleanTree(expression, JunctionType.Conjunction, exp -> {
			if (isLongSide.matchPattern(exp)) {
				otherNodes.add(new MatchInfo(isLongSide.getNodeA(), isLongSide.getLiteral().getValue() == 0));

			} else if (isImplicitBool.matchPattern(exp) && !isImplicitBool.isNegated()) {
				otherNodes.add(new MatchInfo(isImplicitBool.getNodeA(), false));

			}
		});

		// clear partial match
		if (checkedElements > otherNodes.size())
			otherNodes.clear();

		return !otherNodes.isEmpty();
	}

	@Override
	public Collection<String> patterns() {
		return Collections.singleton("A == 1 <-> B == (0|1) (& C == (0|1) & ...)");
	}

}
