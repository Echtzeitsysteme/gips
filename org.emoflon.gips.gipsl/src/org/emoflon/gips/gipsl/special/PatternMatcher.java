package org.emoflon.gips.gipsl.special;

import java.util.Collection;

import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;

public interface PatternMatcher {

	boolean matchPattern(GipsBooleanExpression expression);

	Collection<String> patterns();

}
