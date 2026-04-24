package org.emoflon.gips.build.gipsl.preprocess;

import static org.emoflon.gips.gipsl.generator.GeneratorUtil.createArithmeticLiteral;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.gips.build.preference.PluginPreferences;
import org.emoflon.gips.gipsl.generator.GeneratorUtil;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsProductOperator;
import org.emoflon.gips.gipsl.gipsl.GipsSumOperator;
import org.emoflon.gips.gipsl.gipsl.GipslFactory;
import org.emoflon.gips.gipsl.gipsl.RelationalOperator;
import org.emoflon.gips.gipsl.special.pattern.EquivalenceShortcutD;

/**
 * 
 * Input:
 * <ul>
 * <li>A == 0 <-> B == 1
 * </ul>
 * 
 * With:
 * <ul>
 * <li>M >= 1
 * <li>A in [0, M]
 * <li>B in [0, 1]
 * </ul>
 * 
 * Output:
 * <ul>
 * <li>(1-B) <= A
 * <li>A <= (1-B) * M
 * </ul>
 * 
 */
public class RuleEquivalenceShortcutD implements PreprocessorRule {

	private final EquivalenceShortcutD pattern = new EquivalenceShortcutD();

	public GipsBooleanExpression tryRule(GipslFactory factory, GipsBooleanExpression expression) {
		if (!pattern.matchPattern(expression))
			return null;

		var one = createArithmeticLiteral(factory, "1");

		// 1-B
		var oneMinusB = factory.createGipsArithmeticSum();
		oneMinusB.setOperator(GipsSumOperator.MINUS);
		oneMinusB.setLeft(one);
		oneMinusB.setRight(EcoreUtil.copy(pattern.nodeB));

		// 1-B <= A
		var conjunctOne = factory.createGipsRelationalExpression();
		conjunctOne.setOperator(RelationalOperator.SMALLER_OR_EQUAL);
		conjunctOne.setLeft(EcoreUtil.copy(oneMinusB));
		conjunctOne.setRight(EcoreUtil.copy(pattern.nodeA));

		var bigM = createArithmeticLiteral(factory, Double.toString(PluginPreferences.getBigMValue()));

		// (1-B)*M
		var bigMProduct = factory.createGipsArithmeticProduct();
		bigMProduct.setOperator(GipsProductOperator.MULT);
		bigMProduct.setLeft(EcoreUtil.copy(oneMinusB));
		bigMProduct.setRight(bigM);

		// A <= (1-B)*M
		var conjunctTwo = factory.createGipsRelationalExpression();
		conjunctTwo.setOperator(RelationalOperator.SMALLER_OR_EQUAL);
		conjunctTwo.setLeft(EcoreUtil.copy(pattern.nodeA));
		conjunctTwo.setRight(bigMProduct);

		return GeneratorUtil.conjunction(factory, conjunctOne, conjunctTwo);
	}

}
