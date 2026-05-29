package org.emoflon.gips.build.gipsl.preprocess;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.gips.build.preference.PluginPreferences;
import org.emoflon.gips.gipsl.generator.GeneratorUtil;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsProductOperator;
import org.emoflon.gips.gipsl.gipsl.GipsSumOperator;
import org.emoflon.gips.gipsl.gipsl.GipslFactory;
import org.emoflon.gips.gipsl.gipsl.RelationalOperator;
import org.emoflon.gips.gipsl.special.pattern.EquivalenceShortcutA;

/**
 * 
 * Input:
 * <ul>
 * <li>A >= 1 <-> B == 1 (& C == 1 & ...)
 * </ul>
 * 
 * With:
 * <ul>
 * <li>M >= 1
 * <li>A in [0, M]
 * <li>B, C, ... in [0, 1]
 * </ul>
 * 
 * Output:
 * <ul>
 * <li>B (+ C + ...) + (1-n) <= A
 * <li>A <= B*M
 * <li>A <= C*M
 * <li>...
 * </ul>
 * 
 */
public class RuleEquivalenceShortcutA implements PreprocessorRule {

	private final EquivalenceShortcutA pattern = new EquivalenceShortcutA();

	@Override
	public GipsBooleanExpression tryRule(GipslFactory factory, GipsBooleanExpression expression) {
		if (!pattern.matchPattern(expression))
			return null;

		List<GipsBooleanExpression> conjuncts = new LinkedList<>();

		if (pattern.getOtherNodes().size() == 1) {
			// B <= A
			var relational = factory.createGipsRelationalExpression();
			relational.setOperator(RelationalOperator.SMALLER_OR_EQUAL);
			relational.setLeft(EcoreUtil.copy(pattern.getOtherNodes().getFirst()));
			relational.setRight(EcoreUtil.copy(pattern.getNodeA()));
			conjuncts.add(relational);

		} else {
			List<GipsArithmeticExpression> summands = new ArrayList<>(conjuncts.size() + 2);

			for (var element : pattern.getOtherNodes())
				summands.add(EcoreUtil.copy(element));

			var minusN = factory.createGipsArithmeticLiteral();
			minusN.setValue(Integer.toString(1 - pattern.getOtherNodes().size()));
			summands.add(minusN);

			// B + C + ... + (1-n)
			var sum = GeneratorUtil.sum(factory, GipsSumOperator.PLUS, summands);

			// B + C + ... + (1-n) <= A
			var relational = factory.createGipsRelationalExpression();
			relational.setOperator(RelationalOperator.SMALLER_OR_EQUAL);
			relational.setLeft(sum);
			relational.setRight(EcoreUtil.copy(pattern.getNodeA()));
			conjuncts.add(relational);
		}

		// A <= B*M
		// ...
		for (var element : pattern.getOtherNodes()) {
			var bigM = GeneratorUtil.createArithmeticLiteral(factory,
					Double.toString(PluginPreferences.getBigMValue()));

			var bigMProduct = factory.createGipsArithmeticProduct();
			bigMProduct.setOperator(GipsProductOperator.MULT);
			bigMProduct.setLeft(EcoreUtil.copy(element));
			bigMProduct.setRight(bigM);

			var relational = factory.createGipsRelationalExpression();
			relational.setOperator(RelationalOperator.SMALLER_OR_EQUAL);
			relational.setLeft(EcoreUtil.copy(pattern.getNodeA()));
			relational.setRight(bigMProduct);
			conjuncts.add(relational);
		}

		return GeneratorUtil.conjunction(factory, conjuncts);
	}

}
