package org.emoflon.gips.gipsl.special;

import org.emoflon.gips.gipsl.gipsl.GipsBooleanBracket;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;

public abstract class AbstractPatternFinder implements PatternFinder {

	protected static GipsBooleanExpression skipBrackets(GipsBooleanExpression expression) {
		if (expression instanceof GipsBooleanBracket bracket)
			return skipBrackets(bracket.getOperand());
		return expression;
	}

	public final boolean tryToFindPattern(GipsBooleanExpression expression) {
		resetMatch();
		findPattern(expression);
		return hasMatch();
	}

	protected void clearPartialMatch() {
		if (!hasMatch())
			resetMatch();
	}

	protected abstract void findPattern(GipsBooleanExpression expression);

	protected abstract void resetMatch();

	protected abstract boolean hasMatch();

}
