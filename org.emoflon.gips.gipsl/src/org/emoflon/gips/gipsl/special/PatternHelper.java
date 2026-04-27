package org.emoflon.gips.gipsl.special;

import java.util.List;

import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanBracket;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanConjunction;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanDisjunction;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsRelationalExpression;

public final class PatternHelper {
	private PatternHelper() {
	}

	@FunctionalInterface
	public static interface CheckForMatch {
		void checkAndAddMatch(GipsRelationalExpression relational, List<GipsArithmeticExpression> matches);
	}

	public static enum JunctionType {
		Conjunction, Disjunction
	}

	public static void searchBooleanTree(GipsBooleanExpression expression, JunctionType followType,
			List<GipsArithmeticExpression> matches, CheckForMatch checkForMatch) {

		var prevMatches = matches.size();

		switch (expression) {
		case GipsBooleanBracket bracket:
			searchBooleanTree(bracket.getOperand(), followType, matches, checkForMatch);
			break;
		case GipsBooleanConjunction conjunction when followType == JunctionType.Conjunction:
			searchBooleanTree(conjunction.getLeft(), followType, matches, checkForMatch);
			searchBooleanTree(conjunction.getRight(), followType, matches, checkForMatch);
			break;
		case GipsBooleanDisjunction disjunction when followType == JunctionType.Disjunction:
			searchBooleanTree(disjunction.getLeft(), followType, matches, checkForMatch);
			searchBooleanTree(disjunction.getRight(), followType, matches, checkForMatch);
			break;
		case GipsRelationalExpression relational:
			checkForMatch.checkAndAddMatch(relational, matches);
			break;
		case null: // sometimes the AST is not complete
		default:
			// non-matching element
			matches.clear();
			break;
		}

		// pattern incomplete
		if (prevMatches >= matches.size())
			matches.clear();

	}

	public static GipsBooleanExpression skipBrackets(GipsBooleanExpression expression) {
		if (expression instanceof GipsBooleanBracket bracket)
			return skipBrackets(bracket.getOperand());
		return expression;
	}

}
