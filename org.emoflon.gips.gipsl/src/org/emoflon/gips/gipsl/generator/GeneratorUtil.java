package org.emoflon.gips.gipsl.generator;

import java.util.List;

import org.emoflon.gips.gipsl.gipsl.ConjunctionOperator;
import org.emoflon.gips.gipsl.gipsl.DisjunctionOperator;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpression;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticSum;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanConjunction;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanDisjunction;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsSumOperator;
import org.emoflon.gips.gipsl.gipsl.GipslFactory;

public final class GeneratorUtil {
	private GeneratorUtil() {

	}

	public static GipsArithmeticLiteral createArithmeticLiteral(GipslFactory factory, String value) {
		GipsArithmeticLiteral literal = factory.createGipsArithmeticLiteral();
		literal.setValue(value);
		return literal;
	}

	public static GipsBooleanExpression conjunction(GipslFactory factory, GipsBooleanExpression lhs,
			GipsBooleanExpression rhs) {

		if (lhs == null)
			return rhs;
		if (rhs == null)
			return lhs;

		GipsBooleanConjunction element = factory.createGipsBooleanConjunction();
		element.setOperator(ConjunctionOperator.AND);
		element.setLeft(lhs);
		element.setRight(rhs);
		return element;
	}

	public static GipsBooleanExpression conjunction(GipslFactory factory, List<GipsBooleanExpression> expressions) {
		if (expressions.size() == 0) {
			return null;
		} else if (expressions.size() == 1) {
			return expressions.getFirst();
		} else {
			// join all pairings
			GipsBooleanConjunction root = factory.createGipsBooleanConjunction();
			root.setOperator(ConjunctionOperator.AND);
			root.setLeft(expressions.getFirst());

			GipsBooleanConjunction chain = root;
			for (int i = 1; i < expressions.size() - 1; ++i) {
				GipsBooleanConjunction tmp = factory.createGipsBooleanConjunction();
				tmp.setOperator(ConjunctionOperator.AND);
				tmp.setLeft(expressions.get(i));
				chain.setRight(tmp);
				chain = tmp;
			}

			chain.setRight(expressions.getLast());

			return root;
		}
	}

	public static GipsBooleanExpression conjunction(GipslFactory factory, DisjunctionOperator operator,
			List<GipsBooleanExpression> expressions) {
		if (expressions.size() == 0) {
			return null;
		} else if (expressions.size() == 1) {
			return expressions.getFirst();
		} else {
			// join all pairings
			GipsBooleanDisjunction root = factory.createGipsBooleanDisjunction();
			root.setOperator(operator);
			root.setLeft(expressions.getFirst());

			GipsBooleanDisjunction chain = root;
			for (int i = 1; i < expressions.size() - 1; ++i) {
				GipsBooleanDisjunction tmp = factory.createGipsBooleanDisjunction();
				tmp.setOperator(operator);
				tmp.setLeft(expressions.get(i));
				chain.setRight(tmp);
				chain = tmp;
			}

			chain.setRight(expressions.getLast());

			return root;
		}
	}

	public static GipsArithmeticExpression sum(GipslFactory factory, GipsSumOperator operator,
			List<GipsArithmeticExpression> expressions) {
		if (expressions.size() == 0) {
			return null;
		} else if (expressions.size() == 1) {
			return expressions.getFirst();
		} else {
			// join all pairings
			GipsArithmeticSum root = factory.createGipsArithmeticSum();
			root.setOperator(operator);
			root.setLeft(expressions.getFirst());

			GipsArithmeticSum chain = root;
			for (int i = 1; i < expressions.size() - 1; ++i) {
				GipsArithmeticSum tmp = factory.createGipsArithmeticSum();
				tmp.setOperator(operator);
				tmp.setLeft(expressions.get(i));
				chain.setRight(tmp);
				chain = tmp;
			}

			chain.setRight(expressions.getLast());

			return root;
		}
	}

}
