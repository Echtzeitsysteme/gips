package org.emoflon.gips.build.gipsl.preprocess;

import org.emoflon.gips.gipsl.gipsl.GipsBooleanBracket;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipslFactory;

public interface PreprocessorRule {

	GipsBooleanExpression tryRule(GipslFactory factory, GipsBooleanExpression expression);

	default GipsBooleanExpression skipBrackets(GipsBooleanExpression expression) {
		if (expression instanceof GipsBooleanBracket bracket)
			return skipBrackets(bracket.getOperand());
		return expression;
	}

}
