package org.emoflon.gips.build.transformation.helper;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
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
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextReference;
import org.emoflon.gips.intermediate.GipsIntermediate.LinearFunctionReference;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingReference;
import org.emoflon.gips.intermediate.GipsIntermediate.MemberExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.MemberReference;
import org.emoflon.gips.intermediate.GipsIntermediate.NodeExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternReference;
import org.emoflon.gips.intermediate.GipsIntermediate.QueryOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.RuleReference;
import org.emoflon.gips.intermediate.GipsIntermediate.SetElementQuery;
import org.emoflon.gips.intermediate.GipsIntermediate.SetExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.SetSimpleQuery;
import org.emoflon.gips.intermediate.GipsIntermediate.SetSimpleSelect;
import org.emoflon.gips.intermediate.GipsIntermediate.SetSummation;
import org.emoflon.gips.intermediate.GipsIntermediate.SetTypeQuery;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeReference;
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Variable;
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference;

public final class ExpressionReturnTypeResolver {
	private ExpressionReturnTypeResolver() {

	}

	public static ExpressionReturnType extractReturnType(final BooleanExpression expression) {
		if (expression instanceof BooleanBinaryExpression bin) {
			ExpressionReturnType lhs = extractReturnType(bin.getLhs());
			ExpressionReturnType rhs = extractReturnType(bin.getRhs());
			if (lhs != ExpressionReturnType.bool || rhs != ExpressionReturnType.bool || lhs != rhs)
				throw new UnsupportedOperationException("Boolean operator types are mismatching.");
			return lhs;
		} else if (expression instanceof BooleanUnaryExpression unary) {
			return extractReturnType(unary.getOperand());
		} else if (expression instanceof BooleanLiteral) {
			return ExpressionReturnType.bool;
		} else if (expression instanceof ConstantLiteral constant) {
			if (constant.getConstant() == ConstantValue.NULL) {
				return ExpressionReturnType.object;
			} else {
				return ExpressionReturnType.bool;
			}
		} else if (expression instanceof ConstantReference reference) {
			if (reference.getSetExpression() == null) {
				return extractReturnType((BooleanExpression) reference.getConstant().getExpression());
			} else {
				return extractReturnType(reference.getSetExpression());
			}
		} else if (expression instanceof ArithmeticExpression arithmetic) {
			return extractReturnType(arithmetic);
		} else {
			return ExpressionReturnType.bool;
		}
	}

	public static ExpressionReturnType extractReturnType(final ArithmeticExpression expression) {
		if (expression instanceof ArithmeticBinaryExpression bin) {
			ExpressionReturnType lhs = extractReturnType(bin.getLhs());
			ExpressionReturnType rhs = extractReturnType(bin.getRhs());
			if (lhs != rhs)
				throw new UnsupportedOperationException("Arithmetic operator types are mismatching.");

			return lhs;
		} else if (expression instanceof ArithmeticUnaryExpression unary) {
			return extractReturnType(unary.getOperand());
		} else if (expression instanceof ArithmeticLiteral) {
			return ExpressionReturnType.number;
		} else if (expression instanceof ConstantLiteral lit) {
			if (lit.getConstant() == ConstantValue.NULL) {
				return ExpressionReturnType.object;
			} else {
				return ExpressionReturnType.number;
			}
		} else if (expression instanceof LinearFunctionReference) {
			return ExpressionReturnType.number;
		} else if (expression instanceof ConstantReference reference) {
			if (reference.getSetExpression() == null) {
				return extractReturnType((ArithmeticExpression) reference.getConstant().getExpression());
			} else {
				return extractReturnType(reference.getSetExpression());
			}

		} else {
			return extractReturnType((ValueExpression) expression);
		}
	}

	public static ExpressionReturnType extractReturnType(final ValueExpression expression) {
		if (expression.getSetExpression() != null && expression.getSetExpression().getSetReduce() != null)
			return extractReturnType(expression.getSetExpression());

		return switch (expression) {
		case MappingReference mappingRef -> ExpressionReturnType.object;
		case TypeReference typeRef -> ExpressionReturnType.object;
		case PatternReference patternRef -> ExpressionReturnType.object;
		case RuleReference ruleRef -> ExpressionReturnType.object;
		case VariableReference varRef -> extractReturnType(varRef.getVariable());
		case MemberReference memRef -> extractReturnType(memRef.getMember());
		case ContextReference conRef -> ExpressionReturnType.object;
		default -> throw new IllegalArgumentException("Unexpected value: " + expression);
		};
	}

	private static ExpressionReturnType extractReturnType(final SetExpression expression) {
		if (expression != null && expression.getSetReduce() != null) {
			if (expression.getSetReduce() instanceof SetSummation) {
				return ExpressionReturnType.number;
			} else if (expression.getSetReduce() instanceof SetSimpleSelect) {
				return ExpressionReturnType.object;
			} else if (expression.getSetReduce() instanceof SetTypeQuery) {
				return ExpressionReturnType.bool;
			} else if (expression.getSetReduce() instanceof SetElementQuery) {
				return ExpressionReturnType.bool;
			} else if (expression.getSetReduce() instanceof SetSimpleQuery query) {
				if (query.getOperator() == QueryOperator.EMPTY || query.getOperator() == QueryOperator.NOT_EMPTY) {
					return ExpressionReturnType.bool;
				} else {
					// Case: Count
					return ExpressionReturnType.number;
				}
			} else {
				return ExpressionReturnType.object;
			}
		} else {
			return ExpressionReturnType.object;
		}
	}

	private static ExpressionReturnType extractReturnType(MemberExpression expression) {
		return switch (expression) {
		case AttributeExpression attribute -> attribute.getNext() == null ? extractReturnType(attribute.getFeature())
				: extractReturnType(attribute.getNext());

		case NodeExpression node ->
			node.getNext() == null ? ExpressionReturnType.object : extractReturnType(node.getNext());

		default -> throw new IllegalArgumentException("Unexpected value: " + expression);
		};
	}

	private static ExpressionReturnType extractReturnType(Variable variable) {
		return ExpressionReturnType.number;
	}

	private static ExpressionReturnType extractReturnType(final EStructuralFeature feature) {
		if (feature.getEType() == EcorePackage.Literals.EBOOLEAN) {
			return ExpressionReturnType.bool;
		} else if (feature.getEType() == EcorePackage.Literals.EDOUBLE) {
			return ExpressionReturnType.number;
		} else if (feature.getEType() == EcorePackage.Literals.EFLOAT) {
			return ExpressionReturnType.number;
		} else if (feature.getEType() == EcorePackage.Literals.EBYTE) {
			return ExpressionReturnType.number;
		} else if (feature.getEType() == EcorePackage.Literals.ESHORT) {
			return ExpressionReturnType.number;
		} else if (feature.getEType() == EcorePackage.Literals.EINT) {
			return ExpressionReturnType.number;
		} else if (feature.getEType() == EcorePackage.Literals.ELONG) {
			return ExpressionReturnType.number;
		} else if (feature.getEType() == EcorePackage.Literals.ESTRING) {
			return ExpressionReturnType.object;
		} else if (feature.getEType() instanceof EClass) {
			return ExpressionReturnType.object;
		} else if (feature.getEType() instanceof EEnum) {
			return ExpressionReturnType.object;
		} else {
			throw new IllegalArgumentException("Unsupported data type: " + feature.getEType());
		}
	}
}
