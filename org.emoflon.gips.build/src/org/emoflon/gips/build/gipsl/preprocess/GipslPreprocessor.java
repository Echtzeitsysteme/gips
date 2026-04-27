package org.emoflon.gips.build.gipsl.preprocess;

import java.util.Objects;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanBracket;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanConjunction;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanDisjunction;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanImplication;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanNegation;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsRelationalExpression;
import org.emoflon.gips.gipsl.gipsl.GipslFactory;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;

public class GipslPreprocessor {

	private final static PreprocessorRule[] rules = new PreprocessorRule[] { //
			new RuleEquivalenceShortcutA(), //
			new RuleEquivalenceShortcutB(), //
			new RuleEquivalenceShortcutC(), //
			new RuleEquivalenceShortcutD(), //
			new RuleImplicationShortcutA() };

	private final GipslFactory factory;

	public GipslPreprocessor() {
		this(GipslPackage.eINSTANCE.getGipslFactory());
	}

	public GipslPreprocessor(GipslFactory factory) {
		this.factory = Objects.requireNonNull(factory, "factory");
	}

	public GipsBooleanExpression preprocess(GipsConstraint constraint) {
		GipsBooleanExpression expression = constraint.getExpression();

//		GipsBooleanExpression currentPass = applyRules(expression);
//		GipsBooleanExpression previousPass = null;
//
//		while (currentPass != null) {
//			previousPass = currentPass;
//			currentPass = applyRules(currentPass);
//		}
//
//		return previousPass != null ? previousPass : expression;

		GipsBooleanExpression newExpression = tryToApplyRules(expression);
		return newExpression != null ? newExpression : expression;
	}

	private GipsBooleanExpression tryToApplyRules(GipsBooleanExpression expression) {
		// depth first
		var newExpression = switch (expression) {
		case GipsBooleanImplication implication -> tryExpand(implication);
		case GipsBooleanConjunction conjunction -> tryExpand(conjunction);
		case GipsBooleanDisjunction disjunction -> tryExpand(disjunction);
		case GipsBooleanBracket bracket -> tryExpand(bracket);
		case GipsBooleanNegation negation -> tryExpand(negation);
		case GipsBooleanLiteral literal -> null;
		case GipsArithmeticExpression arithmetic -> null;
		case GipsRelationalExpression relational -> null;
		default -> throw new IllegalArgumentException("Unexpected value: " + expression);
		};

		if (newExpression != null) {
			var replacement = tryAllRules(newExpression);
			return replacement != null ? replacement : newExpression;
		}

		return tryAllRules(expression);
	}

	private GipsBooleanExpression tryExpand(GipsBooleanImplication expression) {
		var newLeft = tryToApplyRules(expression.getLeft());
		var newRight = tryToApplyRules(expression.getRight());
		if (newLeft == null && newRight == null)
			return null;

		var newExpression = factory.createGipsBooleanImplication();
		newExpression.setOperator(expression.getOperator());
		newExpression.setLeft(newLeft != null ? newLeft : EcoreUtil.copy(expression.getLeft()));
		newExpression.setRight(newRight != null ? newRight : EcoreUtil.copy(expression.getRight()));
		return newExpression;
	}

	private GipsBooleanExpression tryExpand(GipsBooleanConjunction expression) {
		var newLeft = tryToApplyRules(expression.getLeft());
		var newRight = tryToApplyRules(expression.getRight());
		if (newLeft == null && newRight == null)
			return null;

		var newExpression = factory.createGipsBooleanConjunction();
		newExpression.setLeft(newLeft != null ? newLeft : EcoreUtil.copy(expression.getLeft()));
		newExpression.setRight(newRight != null ? newRight : EcoreUtil.copy(expression.getRight()));
		return newExpression;
	}

	private GipsBooleanExpression tryExpand(GipsBooleanDisjunction expression) {
		var newLeft = tryToApplyRules(expression.getLeft());
		var newRight = tryToApplyRules(expression.getRight());
		if (newLeft == null && newRight == null)
			return null;

		var newExpression = factory.createGipsBooleanDisjunction();
		newExpression.setLeft(newLeft != null ? newLeft : EcoreUtil.copy(expression.getLeft()));
		newExpression.setRight(newRight != null ? newRight : EcoreUtil.copy(expression.getRight()));
		return newExpression;
	}

	private GipsBooleanExpression tryExpand(GipsBooleanBracket expression) {
		var newOperand = tryToApplyRules(expression.getOperand());
		if (newOperand == null)
			return null;

		var newExpression = factory.createGipsBooleanBracket();
		newExpression.setOperand(newOperand);
		return newExpression;
	}

	private GipsBooleanExpression tryExpand(GipsBooleanNegation expression) {
		var newOperand = tryToApplyRules(expression.getOperand());
		if (newOperand == null)
			return null;

		var newExpression = factory.createGipsBooleanNegation();
		newExpression.setOperand(newOperand);
		return newExpression;
	}

	private GipsBooleanExpression tryAllRules(GipsBooleanExpression expression) {
		for (var rule : rules) {
			GipsBooleanExpression newExpression = rule.tryRule(factory, expression);
			if (newExpression != null)
				return newExpression;
		}

		return null;
	}

}
