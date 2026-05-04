package org.emoflon.gips.build.gipsl.preprocess;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.gips.gipsl.generator.GeneratorUtil;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsProductOperator;
import org.emoflon.gips.gipsl.gipsl.GipsSumOperator;
import org.emoflon.gips.gipsl.gipsl.GipslFactory;
import org.emoflon.gips.gipsl.gipsl.RelationalOperator;
import org.emoflon.gips.gipsl.special.pattern.EquivalenceShortcutB;

/**
 * 
 * Input:
 * <ul>
 * <li>A == 1 <-> B == 1 (& C == 1 & ...)
 * </ul>
 * 
 * With:
 * <ul>
 * <li>A in [0, 1]
 * <li>B in [0, 1]
 * <li>C in [0, 1]
 * <li>... in [0, 1]
 * </ul>
 * 
 * Output:
 * <ul>
 * <li>B (+ C + ...) + (1-n) <= A
 * <li>n * A <= B + C + ...
 * </ul>
 * 
 */
public class RuleEquivalenceShortcutB implements PreprocessorRule {

	private final EquivalenceShortcutB pattern = new EquivalenceShortcutB();

	@Override
	public GipsBooleanExpression tryRule(GipslFactory factory, GipsBooleanExpression expression) {
		if (!pattern.matchPattern(expression))
			return null;

		List<GipsBooleanExpression> conjuncts = new LinkedList<>();

		// Most simple case: only one other node
		// Simplification:
		// B <= A
		// A >= B
		// This can further be simplified to:
		// A = B
		if (pattern.otherNodes.size() == 1) {
			// A = B
			var relational = factory.createGipsRelationalExpression();
			relational.setOperator(RelationalOperator.EQUAL);
			relational.setLeft(EcoreUtil.copy(pattern.nodeA));
			relational.setRight(EcoreUtil.copy(pattern.otherNodes.get(0)));
			conjuncts.add(relational);
		} else {
			// Normal case

			// B + C + ... + (1-n) <= A
			{
				List<GipsArithmeticExpression> summands = new ArrayList<>(conjuncts.size() + 2);

				for (var element : pattern.otherNodes)
					summands.add(EcoreUtil.copy(element));

				var minusN = factory.createGipsArithmeticLiteral();
				minusN.setValue(Integer.toString(1 - pattern.otherNodes.size()));
				summands.add(minusN);

				// B + C + ... + (1-n)
				var sum = GeneratorUtil.sum(factory, GipsSumOperator.PLUS, summands);

				// B + C + ... + (1-n) <= A
				var relational = factory.createGipsRelationalExpression();
				relational.setOperator(RelationalOperator.SMALLER_OR_EQUAL);
				relational.setLeft(sum);
				relational.setRight(EcoreUtil.copy(pattern.nodeA));
				conjuncts.add(relational);
			}

			// n * A <= B + C + ...
			{
				// B + C + ...
				List<GipsArithmeticExpression> summands = new ArrayList<>(conjuncts.size());
				for (var element : pattern.otherNodes)
					summands.add(EcoreUtil.copy(element));

				var sum = GeneratorUtil.sum(factory, GipsSumOperator.PLUS, summands);

				// n
				var n = GeneratorUtil.createArithmeticLiteral(factory, Double.toString(summands.size()));

				// n * A
				var nProduct = factory.createGipsArithmeticProduct();
				nProduct.setOperator(GipsProductOperator.MULT);
				nProduct.setLeft(EcoreUtil.copy(pattern.nodeA));
				nProduct.setRight(n);

				// n * A <= B + C + ...
				var relational = factory.createGipsRelationalExpression();
				relational.setOperator(RelationalOperator.SMALLER_OR_EQUAL);
				relational.setLeft(nProduct);
				relational.setRight(sum);
				conjuncts.add(relational);
			}
		}

		return GeneratorUtil.conjunction(factory, conjuncts);
	}

}
