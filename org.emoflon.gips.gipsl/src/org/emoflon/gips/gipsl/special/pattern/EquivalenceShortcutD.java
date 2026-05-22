package org.emoflon.gips.gipsl.special.pattern;

import static org.emoflon.gips.gipsl.special.PatternHelper.peelBrackets;

import java.util.Collection;
import java.util.Collections;

import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanImplication;
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

	private GipsArithmeticExpression nodeA;
	private GipsArithmeticExpression nodeB;

	private final ImplicitBoolean isImplicitBool = new ImplicitBoolean();
	private final ValueConstantRelation isRelationalPair = new ValueConstantRelation( //
			false, //
			RelationalOperator.EQUAL, //
			c -> "0".equals(c) || "1".equals(c));

	public GipsArithmeticExpression getNodeA() {
		return nodeA;
	}

	public GipsArithmeticExpression getNodeB() {
		return nodeB;
	}

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

		if (!hasMatch()) {
			nodeA = matchNode(implication.getLeft(), "0");
			nodeB = matchNode(implication.getRight(), "1");
			clearPartialMatch();
		}

		if (!hasMatch()) {
			nodeA = matchNode(implication.getRight(), "0");
			nodeB = matchNode(implication.getLeft(), "1");
			clearPartialMatch();
		}
	}

	private GipsArithmeticExpression matchNode(GipsBooleanExpression expression, String expectedConstant) {
		expression = peelBrackets(expression);

		// A == c
		if (isRelationalPair.matchPattern(expression)
				&& expectedConstant.equals(isRelationalPair.getLiteral().getValue()))
			return isRelationalPair.getNodeA();

		boolean mustBeNegated = "0".equals(expectedConstant);

		// A / !A
		if (isImplicitBool.matchPattern(expression) && mustBeNegated == isImplicitBool.isNegated())
			return isImplicitBool.getNodeA();

		return null;
	}

	@Override
	public Collection<String> patterns() {
		return Collections.singleton("A == 0 <-> B == 1");
	}

}
