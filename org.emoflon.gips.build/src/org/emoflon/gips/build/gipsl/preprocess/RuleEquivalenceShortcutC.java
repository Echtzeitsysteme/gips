package org.emoflon.gips.build.gipsl.preprocess;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.gips.gipsl.generator.GeneratorUtil;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsProductOperator;
import org.emoflon.gips.gipsl.gipsl.GipsSumOperator;
import org.emoflon.gips.gipsl.gipsl.GipslFactory;
import org.emoflon.gips.gipsl.gipsl.RelationalOperator;
import org.emoflon.gips.gipsl.special.pattern.EquivalenceShortcutC;

/**
 * 
 * Input:
 * <ul>
 * <li>A == 1 <-> B == 1 | C == 1 (| D == 1 | ...)
 * </ul>
 * 
 * With:
 * <ul>
 * <li>A, B, C, ... in [0, 1]
 * </ul>
 * 
 * Output:
 * <ul>
 * <li>A <= B + C (+ D + ...)
 * <li>B + C (+ D + ...) <= A*n
 * </ul>
 * 
 */
public class RuleEquivalenceShortcutC implements PreprocessorRule {

	private final EquivalenceShortcutC pattern = new EquivalenceShortcutC();

	@Override
	public GipsBooleanExpression tryRule(GipslFactory factory, GipsBooleanExpression expression) {
		if (!pattern.matchPattern(expression))
			return null;

		List<GipsArithmeticExpression> summands = new ArrayList<>(pattern.otherNodes.size());
		for (var element : pattern.otherNodes)
			summands.add(EcoreUtil.copy(element));
		// B + C + D + ...
		var sum = GeneratorUtil.sum(factory, GipsSumOperator.PLUS, summands);

		// A <= B + C (+ D + ...)
		var conjunctOne = factory.createGipsRelationalExpression();
		conjunctOne.setOperator(RelationalOperator.SMALLER_OR_EQUAL);
		conjunctOne.setLeft(EcoreUtil.copy(pattern.nodeA));
		conjunctOne.setRight(sum);

		// A * n
		var product = factory.createGipsArithmeticProduct();
		product.setOperator(GipsProductOperator.MULT);
		product.setLeft(EcoreUtil.copy(pattern.nodeA));
		product.setRight(GeneratorUtil.createArithmeticLiteral(factory, Integer.toString(pattern.otherNodes.size())));

		// B + C (+ D + ...) <= A*n
		var conjunctTwo = factory.createGipsRelationalExpression();
		conjunctTwo.setOperator(RelationalOperator.SMALLER_OR_EQUAL);
		conjunctTwo.setLeft(EcoreUtil.copy(sum));
		conjunctTwo.setRight(product);

		return GeneratorUtil.conjunction(factory, conjunctOne, conjunctTwo);
	}

}
