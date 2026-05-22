package org.emoflon.gips.build.gipsl.preprocess;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.gips.gipsl.generator.GeneratorUtil;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipslFactory;
import org.emoflon.gips.gipsl.gipsl.RelationalOperator;
import org.emoflon.gips.gipsl.special.pattern.ImplicitBoolean;

public class RuleImplicitBooleans implements PreprocessorRule {

	private final ImplicitBoolean pattern = new ImplicitBoolean();

	@Override
	public GipsBooleanExpression tryRule(GipslFactory factory, GipsBooleanExpression expression) {
		if (!pattern.matchPattern(expression))
			return null;

		var relational = factory.createGipsRelationalExpression();
		relational.setOperator(RelationalOperator.EQUAL);
		relational.setLeft(EcoreUtil.copy(pattern.getNodeA()));
		relational.setRight(GeneratorUtil.createArithmeticLiteral(factory, "1"));

//		if (expression.eContainer() instanceof GipsBooleanNegation) {
//			var brackets = factory.createGipsBooleanBracket();
//			brackets.setOperand(relational);
//			return brackets;
//		}

		return relational;
	}

}
