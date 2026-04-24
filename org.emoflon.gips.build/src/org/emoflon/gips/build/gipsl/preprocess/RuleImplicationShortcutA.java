package org.emoflon.gips.build.gipsl.preprocess;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipslFactory;
import org.emoflon.gips.gipsl.gipsl.RelationalOperator;
import org.emoflon.gips.gipsl.special.pattern.ImplicationShortcutA;

/**
 * 
 * Input:
 * <ul>
 * <li>A == 1 -> B == 1
 * </ul>
 * 
 * Output:
 * <ul>
 * <li>A <= B
 * </ul>
 * 
 */
public class RuleImplicationShortcutA implements PreprocessorRule {

	private final ImplicationShortcutA pattern = new ImplicationShortcutA();

	@Override
	public GipsBooleanExpression tryRule(GipslFactory factory, GipsBooleanExpression expression) {
		if (!pattern.matchPattern(expression))
			return null;

		// A <= B
		var relational = factory.createGipsRelationalExpression();
		relational.setOperator(RelationalOperator.SMALLER_OR_EQUAL);
		relational.setLeft(EcoreUtil.copy(pattern.nodeA));
		relational.setRight(EcoreUtil.copy(pattern.nodeB));

		return relational;
	}

}
