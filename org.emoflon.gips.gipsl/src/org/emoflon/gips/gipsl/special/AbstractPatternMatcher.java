package org.emoflon.gips.gipsl.special;

import org.emoflon.gips.gipsl.gipsl.GipsBooleanBracket;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;

public abstract class AbstractPatternMatcher implements PatternMatcher {

	protected static GipsBooleanExpression skipBrackets(GipsBooleanExpression expression) {
		if (expression instanceof GipsBooleanBracket bracket)
			return skipBrackets(bracket.getOperand());
		return expression;
	}

	public final boolean matchPattern(GipsBooleanExpression expression) {
		resetMatch();
		tryMatchPattern(expression);
		return hasMatch();
	}

	protected void clearPartialMatch() {
		if (!hasMatch())
			resetMatch();
	}

	protected abstract void tryMatchPattern(GipsBooleanExpression expression);

	protected abstract void resetMatch();

	protected abstract boolean hasMatch();

}
