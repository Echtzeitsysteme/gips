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
import org.emoflon.gips.intermediate.GipsIntermediate.VariableExpression;

public final class IsConstantExpressionResolver {
	private IsConstantExpressionResolver() {
	}

	public static ArithmeticExpressionType isConstantExpression(final BooleanExpression expression) {
		if (expression instanceof BooleanBinaryExpression bin) {
			ArithmeticExpressionType lhsType = isConstantExpression(bin.getLhs());
			ArithmeticExpressionType rhsType = isConstantExpression(bin.getRhs());
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
			return isConstantExpression(unary.getOperand());
		} else if (expression instanceof BooleanLiteral) {
			return ArithmeticExpressionType.constant;
		} else if (expression instanceof ConstantLiteral) {
			return ArithmeticExpressionType.constant;
		} else if (expression instanceof ConstantReference reference) {
			if (reference.getSetExpression() == null) {
				return isConstantExpression((BooleanExpression) reference.getConstant().getExpression());
			} else {
				return isConstantExpression(reference.getSetExpression());
			}
		} else if (expression instanceof ArithmeticExpression arithmetic) {
			return isConstantExpression(arithmetic);
		} else {
			return isConstantExpression((RelationalExpression) expression);
		}
	}

	public static ArithmeticExpressionType isConstantExpression(final RelationalExpression relational) {
		ArithmeticExpressionType lhsType = null;
		ArithmeticExpressionType rhsType = null;
		if (relational.getLhs() instanceof ArithmeticExpression arithmetic) {
			lhsType = isConstantExpression(arithmetic);
		} else {
			lhsType = isConstantExpression((BooleanExpression) relational.getLhs());
		}
		if (relational.getRhs() instanceof ArithmeticExpression arithmetic) {
			rhsType = isConstantExpression(arithmetic);
		} else {
			rhsType = isConstantExpression((BooleanExpression) relational.getRhs());
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

	public static ArithmeticExpressionType isConstantExpression(final ArithmeticExpression expression) {
		if (expression instanceof ArithmeticBinaryExpression bin) {
			ArithmeticExpressionType lhsType = isConstantExpression(bin.getLhs());
			ArithmeticExpressionType rhsType = isConstantExpression(bin.getRhs());
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
			return isConstantExpression(unary.getOperand());
		} else if (expression instanceof ArithmeticLiteral) {
			return ArithmeticExpressionType.constant;
		} else if (expression instanceof ConstantLiteral) {
			return ArithmeticExpressionType.constant;
		} else if (expression instanceof LinearFunctionReference) {
			return ArithmeticExpressionType.variableVector;
		} else if (expression instanceof ConstantReference reference) {
			if (reference.getSetExpression() == null) {
				return isConstantExpression((ArithmeticExpression) reference.getConstant().getExpression());
			} else {
				return isConstantExpression(reference.getSetExpression());
			}
		} else {
			return isConstantExpression((ValueExpression) expression);
		}
	}

	public static ArithmeticExpressionType isConstantExpression(final ValueExpression expression) {
		ArithmeticExpressionType expressionType = null;
		if (expression instanceof MappingReference) {
			expressionType = ArithmeticExpressionType.constant;
		} else if (expression instanceof TypeReference) {
			expressionType = ArithmeticExpressionType.constant;
		} else if (expression instanceof PatternReference) {
			expressionType = ArithmeticExpressionType.constant;
		} else if (expression instanceof RuleReference) {
			expressionType = ArithmeticExpressionType.constant;
//		} else if (expression instanceof NodeReference nodeReference) {
//			if (nodeReference.getNext() != null)
//				expressionType = isConstantExpression(nodeReference.getNext());
//			else
//				expressionType = ArithmeticExpressionType.constant;
		} else if (expression instanceof MemberReference memberRef) {
			expressionType = isConstantExpression(memberRef.getMember());
//		} else if (expression instanceof VariableReference) {
//			expressionType = ArithmeticExpressionType.variableValue;
		} else {
			throw new IllegalArgumentException("Unknown expression type: " + expression);
		}

		if (expression.getSetExpression() != null) {
			expressionType = isConstantExpression(expression.getSetExpression());
		}
		return expressionType;
	}

	public static ArithmeticExpressionType isConstantExpression(MemberExpression expression) {
		return switch (expression) {
		case VariableExpression variable -> ArithmeticExpressionType.variableValue;

		case AttributeExpression attribute ->
			attribute.getNext() == null ? ArithmeticExpressionType.constant : isConstantExpression(attribute.getNext());

		case NodeExpression node ->
			node.getNext() == null ? ArithmeticExpressionType.constant : isConstantExpression(node.getNext());

		default -> throw new IllegalArgumentException("Unexpected value: " + expression);
		};
	}

	public static ArithmeticExpressionType isConstantExpression(final SetExpression expression) {
		if (expression != null) {
			if (expression.getSetReduce() != null && expression.getSetReduce() instanceof SetSummation sum) {
				ArithmeticExpressionType expressionType = isConstantExpression(sum.getExpression());
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
