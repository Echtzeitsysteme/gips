package org.emoflon.gips.build.gipsl.preprocess;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.gips.gipsl.gipsl.ConjunctionOperator;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpression;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanImplication;
import org.emoflon.gips.gipsl.gipsl.GipsRelationalExpression;
import org.emoflon.gips.gipsl.gipsl.GipsValueExpression;
import org.emoflon.gips.gipsl.gipsl.GipslFactory;
import org.emoflon.gips.gipsl.gipsl.ImplicationOperator;
import org.emoflon.gips.gipsl.gipsl.RelationalOperator;

/**
 * 
 * Input:
 * <ul>
 * <li>A == 1 <-> B == 1
 * <li>1 == A <-> B == 1
 * <li>A == 1 <-> 1 == B
 * <li>1 == A <-> 1 == B
 * </ul>
 * 
 * Output:
 * <ul>
 * <li>A <= B & A >= B
 * </ul>
 * 
 */
public class RuleEquivalenceShortcutB implements PreprocessorRule {

	public GipsBooleanExpression tryRule(GipslFactory factory, GipsBooleanExpression expression) {
		if (!(expression instanceof GipsBooleanImplication implication))
			return null;

		if (implication.getOperator() != ImplicationOperator.SC_EQUIVALENCE)
			return null;

		GipsArithmeticExpression targetA = null;
		GipsArithmeticExpression targetB = null;

		if (skipBrackets(implication.getLeft()) instanceof GipsRelationalExpression leftRelational
				&& skipBrackets(implication.getRight()) instanceof GipsRelationalExpression rightRelational) {

			if (targetA == null && targetB == null) {
				if (leftRelational.getOperator() == RelationalOperator.EQUAL) {
					if (skipBrackets(leftRelational.getLeft()) instanceof GipsValueExpression exp
							&& skipBrackets(leftRelational.getRight()) instanceof GipsArithmeticLiteral literal
							&& "1".equals(literal.getValue())) {
						targetA = exp;
					} else if (skipBrackets(leftRelational.getRight()) instanceof GipsValueExpression exp
							&& skipBrackets(leftRelational.getLeft()) instanceof GipsArithmeticLiteral literal
							&& "1".equals(literal.getValue())) {
						targetA = exp;
					}
				}

				if (rightRelational.getOperator() == RelationalOperator.EQUAL) {
					if (skipBrackets(rightRelational.getLeft()) instanceof GipsValueExpression exp
							&& skipBrackets(rightRelational.getRight()) instanceof GipsArithmeticLiteral literal
							&& "1".equals(literal.getValue())) {
						targetB = exp;
					} else if (skipBrackets(rightRelational.getRight()) instanceof GipsValueExpression exp
							&& skipBrackets(rightRelational.getLeft()) instanceof GipsArithmeticLiteral literal
							&& "1".equals(literal.getValue())) {
						targetB = exp;
					}
				}

				if (targetA == null || targetB == null) {
					// on partial match -> reset match
					targetA = null;
					targetB = null;
				}
			}

		}

		if (targetA != null && targetB != null) {
			var conjunctOne = factory.createGipsRelationalExpression();
			conjunctOne.setOperator(RelationalOperator.SMALLER_OR_EQUAL);
			conjunctOne.setLeft(EcoreUtil.copy(targetA));
			conjunctOne.setRight(EcoreUtil.copy(targetB));

			var conjunctTwo = factory.createGipsRelationalExpression();
			conjunctTwo.setOperator(RelationalOperator.GREATER_OR_EQUAL);
			conjunctTwo.setLeft(EcoreUtil.copy(targetA));
			conjunctTwo.setRight(EcoreUtil.copy(targetB));

			var conjunction = factory.createGipsBooleanConjunction();
			conjunction.setOperator(ConjunctionOperator.AND);
			conjunction.setLeft(conjunctOne);
			conjunction.setRight(conjunctTwo);

			return conjunction;
		}

		return null;
	}

}
