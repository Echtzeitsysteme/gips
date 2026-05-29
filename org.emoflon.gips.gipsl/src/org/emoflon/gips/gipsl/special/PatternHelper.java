package org.emoflon.gips.gipsl.special;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanBracket;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanConjunction;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanDisjunction;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanNegation;
import org.emoflon.gips.gipsl.gipsl.GipsRelationalExpression;

public final class PatternHelper {
	private PatternHelper() {
	}

	@FunctionalInterface
	public static interface CheckForMatch {
		void checkAndAddMatch(GipsBooleanExpression expression);
	}

	public static enum JunctionType {
		Conjunction, Disjunction
	}

	public static int searchBooleanTree(GipsBooleanExpression expression, JunctionType followType,
			CheckForMatch checkForMatch) {

		var checkedElements = 0;

		switch (expression) {
		case GipsBooleanBracket bracket:
			checkedElements += searchBooleanTree(bracket.getOperand(), followType, checkForMatch);
			break;
		case GipsBooleanConjunction conjunction when followType == JunctionType.Conjunction:
			checkedElements += searchBooleanTree(conjunction.getLeft(), followType, checkForMatch);
			checkedElements += searchBooleanTree(conjunction.getRight(), followType, checkForMatch);
			break;
		case GipsBooleanDisjunction disjunction when followType == JunctionType.Disjunction:
			checkedElements += searchBooleanTree(disjunction.getLeft(), followType, checkForMatch);
			checkedElements += searchBooleanTree(disjunction.getRight(), followType, checkForMatch);
			break;
		case GipsBooleanNegation negation:
			checkForMatch.checkAndAddMatch(negation);
			checkedElements += 1;
			break;
		case GipsRelationalExpression relational:
			checkForMatch.checkAndAddMatch(relational);
			checkedElements += 1;
			break;
		case GipsArithmeticExpression arithmetic:
			checkForMatch.checkAndAddMatch(arithmetic);
			checkedElements += 1;
			break;

		case null: // sometimes the AST is not complete
		default:
			// non-matching element
			checkedElements += 1;
			break;
		}

		return checkedElements;
	}

	public static <T> T asType(GipsBooleanExpression expression, Class<? extends T> clazz) {
		expression = peelBrackets(expression);
		if (clazz.isInstance(expression))
			return clazz.cast(expression);
		return null;
	}

	/**
	 * @param expression
	 * @return the first non-bracket expression
	 */
	public static GipsBooleanExpression peelBrackets(GipsBooleanExpression expression) {
		if (expression instanceof GipsBooleanBracket bracket)
			return peelBrackets(bracket.getOperand());
		return expression;
	}

	/**
	 * @param expression
	 * @return the first non-bracket container
	 */
	public static EObject peelBracketsContainer(GipsBooleanExpression expression) {
		var container = expression.eContainer();
		if (container instanceof GipsBooleanBracket bracket)
			return peelBracketsContainer(bracket);
		return container;
	}

}
