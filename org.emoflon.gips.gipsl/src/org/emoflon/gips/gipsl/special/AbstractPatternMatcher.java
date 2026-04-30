package org.emoflon.gips.gipsl.special;

import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;

public abstract class AbstractPatternMatcher implements PatternMatcher {

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
