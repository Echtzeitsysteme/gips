package org.emoflon.gips.build.gipsl.preprocess;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanImplication;
import org.emoflon.gips.gipsl.gipsl.GipslFactory;
import org.emoflon.gips.gipsl.gipsl.ImplicationOperator;

public class RuleImplicationShortcutRemover implements PreprocessorRule {

	public GipsBooleanExpression tryRule(GipslFactory factory, GipsBooleanExpression expression) {
		if (!(expression instanceof GipsBooleanImplication implication))
			return null;

		if (implication.getOperator() != ImplicationOperator.SC_IMPLICATION)
			return null;

		GipsBooleanImplication replacement = factory.createGipsBooleanImplication();
		replacement.setOperator(ImplicationOperator.IMPLICATION);
		replacement.setLeft(EcoreUtil.copy(implication.getLeft()));
		replacement.setRight(EcoreUtil.copy(implication.getRight()));
		return replacement;
	}

}
