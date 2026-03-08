package org.emoflon.gips.build.transformation.helper;

import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticBinaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticUnaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.AttributeExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanBinaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanUnaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantReference;
import org.emoflon.gips.intermediate.GipsIntermediate.LinearFunctionReference;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingReference;
import org.emoflon.gips.intermediate.GipsIntermediate.MemberExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.MemberReference;
import org.emoflon.gips.intermediate.GipsIntermediate.NodeExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternReference;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.RuleReference;
import org.emoflon.gips.intermediate.GipsIntermediate.SetExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.SetSummation;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeReference;
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference;

public final class IsConstantExpressionResolver {
	private IsConstantExpressionResolver() {
	}

	public static ArithmeticExpressionType getExpressionType(BooleanExpression expression) {
		if (expression instanceof BooleanBinaryExpression bin) {
			ArithmeticExpressionType lhsType = getExpressionType(bin.getLhs());
			ArithmeticExpressionType rhsType = getExpressionType(bin.getRhs());
			if (lhsType == ArithmeticExpressionType.variableVector
					|| rhsType == ArithmeticExpressionType.variableVector) {
				return ArithmeticExpressionType.variableVector;
			} else if (lhsType == ArithmeticExpressionType.variableValue
					|| rhsType == ArithmeticExpressionType.variableValue) {
				return ArithmeticExpressionType.variableValue;
			} else {
				return ArithmeticExpressionType.constant;
			}
		} else if (expression instanceof BooleanUnaryExpression unary) {
			return getExpressionType(unary.getOperand());
		} else if (expression instanceof BooleanLiteral) {
			return ArithmeticExpressionType.constant;
		} else if (expression instanceof ConstantLiteral) {
			return ArithmeticExpressionType.constant;
		} else if (expression instanceof ConstantReference reference) {
			if (reference.getSetExpression() == null) {
				return getExpressionType((BooleanExpression) reference.getConstant().getExpression());
			} else {
				return getExpressionType(reference.getSetExpression());
			}
		} else if (expression instanceof ArithmeticExpression arithmetic) {
			return getExpressionType(arithmetic);
		} else {
			return getExpressionType((RelationalExpression) expression);
		}
	}

	public static ArithmeticExpressionType getExpressionType(RelationalExpression relational) {
		ArithmeticExpressionType lhsType = null;
		ArithmeticExpressionType rhsType = null;
		if (relational.getLhs() instanceof ArithmeticExpression arithmetic) {
			lhsType = getExpressionType(arithmetic);
		} else {
			lhsType = getExpressionType((BooleanExpression) relational.getLhs());
		}
		if (relational.getRhs() instanceof ArithmeticExpression arithmetic) {
			rhsType = getExpressionType(arithmetic);
		} else {
			rhsType = getExpressionType((BooleanExpression) relational.getRhs());
		}

		if (lhsType == ArithmeticExpressionType.variableVector || rhsType == ArithmeticExpressionType.variableVector) {
			return ArithmeticExpressionType.variableVector;
		} else if (lhsType == ArithmeticExpressionType.variableValue
				|| rhsType == ArithmeticExpressionType.variableValue) {
			return ArithmeticExpressionType.variableValue;
		} else {
			return ArithmeticExpressionType.constant;
		}
	}

	public static ArithmeticExpressionType getExpressionType(ArithmeticExpression expression) {
		if (expression instanceof ArithmeticBinaryExpression bin) {
			ArithmeticExpressionType lhsType = getExpressionType(bin.getLhs());
			ArithmeticExpressionType rhsType = getExpressionType(bin.getRhs());
			if (lhsType == ArithmeticExpressionType.variableVector
					|| rhsType == ArithmeticExpressionType.variableVector) {
				return ArithmeticExpressionType.variableVector;
			} else if (lhsType == ArithmeticExpressionType.variableValue
					|| rhsType == ArithmeticExpressionType.variableValue) {
				return ArithmeticExpressionType.variableValue;
			} else {
				return ArithmeticExpressionType.constant;
			}
		} else if (expression instanceof ArithmeticUnaryExpression unary) {
			return getExpressionType(unary.getOperand());
		} else if (expression instanceof ArithmeticLiteral) {
			return ArithmeticExpressionType.constant;
		} else if (expression instanceof ConstantLiteral) {
			return ArithmeticExpressionType.constant;
		} else if (expression instanceof LinearFunctionReference) {
			return ArithmeticExpressionType.variableVector;
		} else if (expression instanceof ConstantReference reference) {
			if (reference.getSetExpression() == null) {
				return getExpressionType((ArithmeticExpression) reference.getConstant().getExpression());
			} else {
				return getExpressionType(reference.getSetExpression());
			}
		} else {
			return getExpressionType((ValueExpression) expression);
		}
	}

	public static ArithmeticExpressionType getExpressionType(ValueExpression expression) {
		ArithmeticExpressionType expressionType = switch (expression) {
		case MappingReference mappingRef -> ArithmeticExpressionType.constant;
		case TypeReference typeRef -> ArithmeticExpressionType.constant;
		case PatternReference patternRef -> ArithmeticExpressionType.constant;
		case RuleReference ruleRef -> ArithmeticExpressionType.constant;
		case VariableReference varRef -> ArithmeticExpressionType.variableValue;
		case MemberReference memRef -> getExpressionType(memRef.getMember());
		default -> throw new IllegalArgumentException("Unexpected value: " + expression);
		};

		if (expression.getSetExpression() != null) {
			expressionType = getExpressionType(expression.getSetExpression());
		}
		return expressionType;
	}

	public static ArithmeticExpressionType getExpressionType(MemberExpression expression) {
		return switch (expression) {
		case AttributeExpression attribute ->
			attribute.getNext() == null ? ArithmeticExpressionType.constant : getExpressionType(attribute.getNext());

		case NodeExpression node ->
			node.getNext() == null ? ArithmeticExpressionType.constant : getExpressionType(node.getNext());

		default -> throw new IllegalArgumentException("Unexpected value: " + expression);
		};
	}

	public static ArithmeticExpressionType getExpressionType(SetExpression expression) {
		if (expression != null) {
			if (expression.getSetReduce() != null && expression.getSetReduce() instanceof SetSummation sum) {
				ArithmeticExpressionType expressionType = getExpressionType(sum.getExpression());
				if (expressionType == ArithmeticExpressionType.variableValue) {
					return ArithmeticExpressionType.variableVector;
				} else {
					return expressionType;
				}
			} else {
				return ArithmeticExpressionType.constant;
			}
		} else {
			return ArithmeticExpressionType.constant;
		}
	}
}
