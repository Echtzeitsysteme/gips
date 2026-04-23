package org.emoflon.gips.gipsl.special;

import java.util.List;

import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanBracket;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanConjunction;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanDisjunction;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsRelationalExpression;

public final class MultiPatternHelper {
	private MultiPatternHelper() {
	}

	@FunctionalInterface
	public static interface CheckForMatch {
		void checkAndAddMatch(GipsRelationalExpression relational, List<GipsArithmeticExpression> matches);
	}

	public static enum SearchType {
		Conjunction, Disjunction
	}

	public static void searchExpressionTree(GipsBooleanExpression expression, SearchType searchType,
			List<GipsArithmeticExpression> matches, CheckForMatch checkForMatch) {

		var prevMatches = matches.size();

		switch (expression) {
		case GipsBooleanBracket bracket:
			searchExpressionTree(bracket.getOperand(), searchType, matches, checkForMatch);
			break;
		case GipsBooleanConjunction conjunction when searchType == SearchType.Conjunction:
			searchExpressionTree(conjunction.getLeft(), searchType, matches, checkForMatch);
			searchExpressionTree(conjunction.getRight(), searchType, matches, checkForMatch);
			break;
		case GipsBooleanDisjunction disjunction when searchType == SearchType.Disjunction:
			searchExpressionTree(disjunction.getLeft(), searchType, matches, checkForMatch);
			searchExpressionTree(disjunction.getRight(), searchType, matches, checkForMatch);
			break;
		case GipsRelationalExpression relational:
			checkForMatch.checkAndAddMatch(relational, matches);
			break;
		default:
			// non-matching element
			matches.clear();
			break;
		}

		// pattern incomplete
		if (prevMatches >= matches.size())
			matches.clear();

	}

}
