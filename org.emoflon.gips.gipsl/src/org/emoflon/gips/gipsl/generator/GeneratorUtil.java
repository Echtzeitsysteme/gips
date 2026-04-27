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

	public static GipsBooleanExpression conjunction(GipslFactory factory, GipsBooleanExpression... conjuncts) {
		if (conjuncts.length == 0)
			return null;

		if (conjuncts.length == 1)
			return conjuncts[0];

		// join all pairings
		GipsBooleanConjunction root = factory.createGipsBooleanConjunction();
		root.setOperator(ConjunctionOperator.AND);
		root.setLeft(conjuncts[0]);

		GipsBooleanConjunction chain = root;
		for (int i = 1; i < conjuncts.length - 1; ++i) {
			GipsBooleanConjunction tmp = factory.createGipsBooleanConjunction();
			tmp.setOperator(ConjunctionOperator.AND);
			tmp.setLeft(conjuncts[i]);
			chain.setRight(tmp);
			chain = tmp;
		}

		chain.setRight(conjuncts[conjuncts.length - 1]);

		return root;
	}

	public static GipsBooleanExpression conjunction(GipslFactory factory, List<GipsBooleanExpression> expressions) {
		if (expressions.size() == 0)
			return null;

		if (expressions.size() == 1)
			return expressions.getFirst();

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

	public static GipsBooleanExpression disjunction(GipslFactory factory, DisjunctionOperator operator,
			List<GipsBooleanExpression> expressions) {
		if (expressions.size() == 0)
			return null;

		if (expressions.size() == 1)
			return expressions.getFirst();

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

	public static GipsArithmeticExpression sum(GipslFactory factory, GipsSumOperator operator,
			GipsArithmeticExpression... expressions) {

		if (expressions.length == 0)
			return null;

		if (expressions.length == 1)
			return expressions[0];

		GipsArithmeticSum root = factory.createGipsArithmeticSum();
		root.setOperator(operator);
		root.setLeft(expressions[0]);

		GipsArithmeticSum chain = root;
		for (int i = 1; i < expressions.length - 1; ++i) {
			GipsArithmeticSum tmp = factory.createGipsArithmeticSum();
			tmp.setOperator(operator);
			tmp.setLeft(expressions[i]);
			chain.setRight(tmp);
			chain = tmp;
		}

		chain.setRight(expressions[expressions.length - 1]);

		return root;

	}

	public static GipsArithmeticExpression sum(GipslFactory factory, GipsSumOperator operator,
			List<GipsArithmeticExpression> expressions) {
		if (expressions.size() == 0)
			return null;

		if (expressions.size() == 1)
			return expressions.getFirst();

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
