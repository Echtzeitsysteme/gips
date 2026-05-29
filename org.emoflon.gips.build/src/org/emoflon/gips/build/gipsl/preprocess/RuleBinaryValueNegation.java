package org.emoflon.gips.build.gipsl.preprocess;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.gips.gipsl.generator.GeneratorUtil;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipslFactory;
import org.emoflon.gips.gipsl.gipsl.RelationalOperator;
import org.emoflon.gips.gipsl.special.pattern.BinaryValueNegation;

public class RuleBinaryValueNegation implements PreprocessorRule {

	private final BinaryValueNegation pattern = new BinaryValueNegation();

	@Override
	public GipsBooleanExpression tryRule(GipslFactory factory, GipsBooleanExpression expression) {
		if (!pattern.matchPattern(expression))
			return null;

		var relational = factory.createGipsRelationalExpression();
		relational.setOperator(RelationalOperator.EQUAL);
		relational.setLeft(EcoreUtil.copy(pattern.getNodeA()));

		if (pattern.getLiteral().getValue().equals("1")) {
			relational.setRight(GeneratorUtil.createArithmeticLiteral(factory, "0"));
		} else {
			relational.setRight(GeneratorUtil.createArithmeticLiteral(factory, "1"));
		}

		return relational;
	}

}
