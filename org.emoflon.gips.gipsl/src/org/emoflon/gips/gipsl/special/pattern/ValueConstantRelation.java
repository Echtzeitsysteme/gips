package org.emoflon.gips.gipsl.special.pattern;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Predicate;

import org.emoflon.gips.gipsl.gipsl.GipsArithmeticLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsRelationalExpression;
import org.emoflon.gips.gipsl.gipsl.GipsValueExpression;
import org.emoflon.gips.gipsl.gipsl.RelationalOperator;
import org.emoflon.gips.gipsl.special.AbstractPatternMatcher;
import org.emoflon.gips.gipsl.special.PatternHelper;
import org.emoflon.gips.gipsl.validation.GipslExpressionValidator;
import org.emoflon.gips.gipsl.validation.GipslExpressionValidator.ExpressionType;

public class ValueConstantRelation extends AbstractPatternMatcher {

	private final RelationalOperator matchOperator;
	private final RelationalOperator reversedmatchOperator;
	private final Predicate<String> matchConstant;

	private GipsValueExpression nodeA;
	private GipsArithmeticLiteral literal;

	public ValueConstantRelation(RelationalOperator matchOperator, Predicate<String> matchConstant) {
		this.matchOperator = Objects.requireNonNull(matchOperator);
		this.matchConstant = Objects.requireNonNull(matchConstant);
		this.reversedmatchOperator = switch (matchOperator) {
		case EQUAL -> RelationalOperator.EQUAL;
		case UNEQUAL -> RelationalOperator.UNEQUAL;
		case GREATER -> RelationalOperator.SMALLER;
		case SMALLER -> RelationalOperator.GREATER;
		case GREATER_OR_EQUAL -> RelationalOperator.SMALLER_OR_EQUAL;
		case SMALLER_OR_EQUAL -> RelationalOperator.GREATER_OR_EQUAL;
		};
	}

	public GipsArithmeticLiteral getLiteral() {
		return literal;
	}

	public GipsValueExpression getNodeA() {
		return nodeA;
	}

	@Override
	protected void resetMatch() {
		nodeA = null;
		literal = null;
	}

	@Override
	protected boolean hasMatch() {
		return nodeA != null && literal != null;
	}

	@Override
	public Collection<String> patterns() {
		return Arrays.asList("A (==,<=,<,>=,>,!=) c");
	}

	@Override
	protected void tryMatchPattern(GipsBooleanExpression expression) {
		if (!(expression instanceof GipsRelationalExpression relational))
			return;

		var leftNode = PatternHelper.peelBrackets(relational.getLeft());
		var rightNode = PatternHelper.peelBrackets(relational.getRight());

		if (matchOperator == relational.getOperator()) {
			if (leftNode instanceof GipsValueExpression exp //
					&& rightNode instanceof GipsArithmeticLiteral literal //
					&& matchConstant.test(literal.getValue())) {

				matchPair(exp, literal);
			}

		} else if (reversedmatchOperator == relational.getOperator()) {
			if (leftNode instanceof GipsArithmeticLiteral literal //
					&& rightNode instanceof GipsValueExpression exp //
					&& matchConstant.test(literal.getValue())) {

				matchPair(exp, literal);
			}

		}
	}

	protected void matchPair(GipsValueExpression exp, GipsArithmeticLiteral literal) {
		var type = GipslExpressionValidator.evaluate(exp, new LinkedList<>());
		if (type.isType(ExpressionType.Boolean) && type.isScalar()) {
			// A . c
			this.nodeA = exp;
			this.literal = literal;
		}
	}

}
