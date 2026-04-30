package org.emoflon.gips.build.gipsl.preprocess;

import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipslFactory;

public interface PreprocessorRule {

	/**
	 * Attempts to apply the given rule to the expression. The original expression
	 * will not be modified. Instead, this method will return a replacement if the
	 * expression can be modified by the rule.
	 * 
	 * @param factory
	 * @param expression which should be 'modified' by this rule
	 * @return the replacement expression, if the rule was applicable
	 */
	GipsBooleanExpression tryRule(GipslFactory factory, GipsBooleanExpression expression);

}
