package org.emoflon.gips.gipsl.special;

import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;

public interface PatternFinder {

	boolean tryToFindPattern(GipsBooleanExpression expression);

}
