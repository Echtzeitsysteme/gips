package org.emoflon.gips.build.transformation.helper;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.gips.gipsl.gipsl.GipsVariable;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticBinaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticUnaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.AttributeExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.AttributeReference;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanBinaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanUnaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantReference;
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantValue;
import org.emoflon.gips.intermediate.GipsIntermediate.LinearFunction;
import org.emoflon.gips.intermediate.GipsIntermediate.LinearFunctionReference;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingReference;
import org.emoflon.gips.intermediate.GipsIntermediate.NodeReference;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternReference;
import org.emoflon.gips.intermediate.GipsIntermediate.QueryOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.RuleReference;
import org.emoflon.gips.intermediate.GipsIntermediate.SetConcatenation;
import org.emoflon.gips.intermediate.GipsIntermediate.SetElementQuery;
import org.emoflon.gips.intermediate.GipsIntermediate.SetExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.SetFilter;
import org.emoflon.gips.intermediate.GipsIntermediate.SetOperation;
import org.emoflon.gips.intermediate.GipsIntermediate.SetReduce;
import org.emoflon.gips.intermediate.GipsIntermediate.SetSimpleQuery;
import org.emoflon.gips.intermediate.GipsIntermediate.SetSimpleSelect;
import org.emoflon.gips.intermediate.GipsIntermediate.SetSort;
import org.emoflon.gips.intermediate.GipsIntermediate.SetSummation;
import org.emoflon.gips.intermediate.GipsIntermediate.SetTransformation;
import org.emoflon.gips.intermediate.GipsIntermediate.SetTypeQuery;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeReference;
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Variable;
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference;
import org.emoflon.gips.intermediate.GipsIntermediate.VariableType;

public final class GipsTransformationUtils {

	public static VariableType typeToVariableType(EClassifier type) {
		if (type == EcorePackage.Literals.EINT || type == EcorePackage.Literals.ESHORT
				|| type == EcorePackage.Literals.ELONG || type == EcorePackage.Literals.EBYTE) {
			return VariableType.INTEGER;
		} else if (type == EcorePackage.Literals.EFLOAT || type == EcorePackage.Literals.EDOUBLE) {
			return VariableType.REAL;
		} else if (type == EcorePackage.Literals.EBOOLEAN) {
			return VariableType.BINARY;
		} else {
			throw new UnsupportedOperationException("Unsupported (M)ILP variable type: " + type);
		}
	}

	public static void flipOperator(final RelationalExpression expr) {
		switch (expr.getOperator()) {
		case EQUAL -> {
		}
		case GREATER -> {
			expr.setOperator(RelationalOperator.LESS);
		}
		case GREATER_OR_EQUAL -> {
			expr.setOperator(RelationalOperator.LESS_OR_EQUAL);
		}
		case LESS -> {
			expr.setOperator(RelationalOperator.GREATER);
		}
		case LESS_OR_EQUAL -> {
			expr.setOperator(RelationalOperator.GREATER_OR_EQUAL);
		}
		case NOT_EQUAL -> {
		}
		default -> {
			throw new UnsupportedOperationException("Unknown relational operator: " + expr.getOperator());
		}
		}
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
		} else if (expression instanceof NodeReference) {
			expressionType = ArithmeticExpressionType.constant;
		} else if (expression instanceof AttributeReference) {
			expressionType = ArithmeticExpressionType.constant;
		} else {
			// Case: VariableReference
			expressionType = ArithmeticExpressionType.variableValue;
		}

		if (expression.getSetExpression() != null) {
			expressionType = isConstantExpression(expression.getSetExpression());
		}
		return expressionType;
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

	public static Set<Variable> extractVariable(final ArithmeticExpression expression) {
		if (expression instanceof ArithmeticBinaryExpression bin) {
			Set<Variable> variables = new HashSet<>();
			variables.addAll(extractVariable(bin.getLhs()));
			variables.addAll(extractVariable(bin.getRhs()));
			return variables;
		} else if (expression instanceof ArithmeticUnaryExpression unary) {
			return extractVariable(unary.getOperand());
		} else if (expression instanceof ArithmeticLiteral) {
			return new HashSet<>();
		} else if (expression instanceof ConstantLiteral) {
			return new HashSet<>();
		} else if (expression instanceof ConstantReference reference) {
			if (reference.getSetExpression() != null) {
				return extractVariable(reference.getSetExpression());
			} else {
				return new HashSet<>();
			}
		} else if (expression instanceof LinearFunctionReference var) {
			return extractVariable(var.getFunction().getExpression());
		} else {
			return extractVariable((ValueExpression) expression);
		}
	}

	public static Set<Variable> extractVariable(final ValueExpression expression) {
		Set<Variable> variables = new HashSet<>();

		if (expression instanceof VariableReference variable) {
			variables.add(variable.getVariable());
		}
		// Else-Case: Sets of Types, Matches, Mappings

		if (expression.getSetExpression() != null) {
			variables.addAll(extractVariable(expression.getSetExpression()));
		}
		return variables;
	}

	public static Set<Variable> extractVariable(final SetExpression expression) {
		Set<Variable> variables = new HashSet<>();
		if (expression != null) {
			if (expression.getSetReduce() != null && expression.getSetReduce() instanceof SetSummation sum) {
				variables.addAll(extractVariable(sum.getExpression()));
			}
		}
		return variables;
	}

	public static Set<Variable> extractVariable(final BooleanExpression expression) {
		if (expression instanceof BooleanBinaryExpression bin) {
			Set<Variable> variables = new HashSet<>();
			variables.addAll(extractVariable(bin.getLhs()));
			variables.addAll(extractVariable(bin.getRhs()));
			return variables;
		} else if (expression instanceof BooleanUnaryExpression unary) {
			return extractVariable(unary.getOperand());
		} else if (expression instanceof BooleanLiteral) {
			return new HashSet<>();
		} else if (expression instanceof ConstantLiteral) {
			return new HashSet<>();
		} else if (expression instanceof ConstantReference reference) {
			if (reference.getSetExpression() != null) {
				return extractVariable(reference.getSetExpression());
			} else {
				return new HashSet<>();
			}
		} else if (expression instanceof ArithmeticExpression arithmetic) {
			return extractVariable(arithmetic);
		} else {
			return extractVariable((RelationalExpression) expression);
		}
	}

	public static Set<Variable> extractVariable(final RelationalExpression relExpr) {
		Set<Variable> variables = new HashSet<>();
		if (relExpr.getLhs() instanceof ArithmeticExpression arithmetic) {
			variables.addAll(extractVariable(arithmetic));
		} else {
			variables.addAll(extractVariable((BooleanExpression) relExpr.getLhs()));
		}

		if (relExpr.getRhs() instanceof ArithmeticExpression arithmetic) {
			variables.addAll(extractVariable(arithmetic));
		} else {
			variables.addAll(extractVariable((BooleanExpression) relExpr.getRhs()));
		}
		return variables;
	}

	public static Set<VariableReference> extractVariableReference(final ArithmeticExpression expression) {
		if (expression instanceof ArithmeticBinaryExpression bin) {
			Set<VariableReference> variables = new HashSet<>();
			variables.addAll(extractVariableReference(bin.getLhs()));
			variables.addAll(extractVariableReference(bin.getRhs()));
			return variables;
		} else if (expression instanceof ArithmeticUnaryExpression unary) {
			return extractVariableReference(unary.getOperand());
		} else if (expression instanceof ArithmeticLiteral) {
			return new HashSet<>();
		} else if (expression instanceof ConstantLiteral) {
			return new HashSet<>();
		} else if (expression instanceof ConstantReference reference) {
			if (reference.getSetExpression() != null) {
				return extractVariableReference(reference.getSetExpression());
			} else {
				return new HashSet<>();
			}
		} else if (expression instanceof LinearFunctionReference var) {
			return extractVariableReference(var.getFunction().getExpression());
		} else {
			return extractVariableReference((ValueExpression) expression);
		}
	}

	public static Set<VariableReference> extractVariableReference(final ValueExpression expression) {
		Set<VariableReference> variables = new HashSet<>();

		if (expression instanceof VariableReference variable) {
			variables.add(variable);
		}
		// Else-Case: Sets of Types, Matches, Mappings

		if (expression.getSetExpression() != null) {
			variables.addAll(extractVariableReference(expression.getSetExpression()));
		}
		return variables;
	}

	public static Set<VariableReference> extractVariableReference(final SetExpression expression) {
		Set<VariableReference> variables = new HashSet<>();
		if (expression != null) {
			if (expression.getSetReduce() != null && expression.getSetReduce() instanceof SetSummation sum) {
				variables.addAll(extractVariableReference(sum.getExpression()));
			}
		}
		return variables;
	}

	public static Set<VariableReference> extractVariableReference(final BooleanExpression expression) {
		if (expression instanceof BooleanBinaryExpression bin) {
			Set<VariableReference> variables = new HashSet<>();
			variables.addAll(extractVariableReference(bin.getLhs()));
			variables.addAll(extractVariableReference(bin.getRhs()));
			return variables;
		} else if (expression instanceof BooleanUnaryExpression unary) {
			return extractVariableReference(unary.getOperand());
		} else if (expression instanceof BooleanLiteral) {
			return new HashSet<>();
		} else if (expression instanceof ConstantLiteral) {
			return new HashSet<>();
		} else if (expression instanceof ConstantReference reference) {
			if (reference.getSetExpression() != null) {
				return extractVariableReference(reference.getSetExpression());
			} else {
				return new HashSet<>();
			}
		} else if (expression instanceof ArithmeticExpression arithmetic) {
			return extractVariableReference(arithmetic);
		} else {
			return extractVariableReference((RelationalExpression) expression);
		}
	}

	public static Set<VariableReference> extractVariableReference(final RelationalExpression relExpr) {
		Set<VariableReference> variables = new HashSet<>();
		if (relExpr.getLhs() instanceof ArithmeticExpression arithmetic) {
			variables.addAll(extractVariableReference(arithmetic));
		} else {
			variables.addAll(extractVariableReference((BooleanExpression) relExpr.getLhs()));
		}

		if (relExpr.getRhs() instanceof ArithmeticExpression arithmetic) {
			variables.addAll(extractVariableReference(arithmetic));
		} else {
			variables.addAll(extractVariableReference((BooleanExpression) relExpr.getRhs()));
		}
		return variables;
	}

	public static Set<LinearFunction> extractLinearFunction(final ArithmeticExpression expression) {
		if (expression instanceof ArithmeticBinaryExpression bin) {
			Set<LinearFunction> functions = new HashSet<>();
			functions.addAll(extractLinearFunction(bin.getLhs()));
			functions.addAll(extractLinearFunction(bin.getRhs()));
			return functions;
		} else if (expression instanceof ArithmeticUnaryExpression unary) {
			return extractLinearFunction(unary.getOperand());
		} else if (expression instanceof ArithmeticLiteral) {
			return new HashSet<>();
		} else if (expression instanceof ConstantLiteral) {
			return new HashSet<>();
		} else if (expression instanceof LinearFunctionReference variable) {
			Set<LinearFunction> functions = new HashSet<>();
			functions.add(variable.getFunction());
			return functions;
		} else {
			return new HashSet<>();
		}
	}

	public static Set<ConstantReference> extractConstantReference(final ArithmeticExpression expression) {
		if (expression instanceof ArithmeticBinaryExpression bin) {
			Set<ConstantReference> constants = new HashSet<>();
			constants.addAll(extractConstantReference(bin.getLhs()));
			constants.addAll(extractConstantReference(bin.getRhs()));
			return constants;
		} else if (expression instanceof ArithmeticUnaryExpression unary) {
			return extractConstantReference(unary.getOperand());
		} else if (expression instanceof ArithmeticLiteral) {
			return new HashSet<>();
		} else if (expression instanceof ConstantLiteral) {
			return new HashSet<>();
		} else if (expression instanceof LinearFunctionReference var) {
			return extractConstantReference(var.getFunction().getExpression());
		} else if (expression instanceof ConstantReference ref) {
			Set<ConstantReference> constants = new HashSet<>();
			constants.add(ref);
			return constants;
		} else {
			return extractConstantReference((ValueExpression) expression);
		}
	}

	public static Set<ConstantReference> extractConstantReference(final ValueExpression expression) {
		Set<ConstantReference> constants = new HashSet<>();
		if (expression.getSetExpression() != null) {
			if (expression.getSetExpression().getSetOperation() != null) {
				constants.addAll(extractConstantReference(expression.getSetExpression().getSetOperation()));
			}
			if (expression.getSetExpression().getSetReduce() != null) {
				constants.addAll(extractConstantReference(expression.getSetExpression().getSetReduce()));
			}
		}
		return constants;
	}

	public static Set<ConstantReference> extractConstantReference(final SetOperation expression) {
		Set<ConstantReference> constants = new HashSet<>();
		if (expression instanceof SetFilter filter) {
			constants.addAll(extractConstantReference(filter.getExpression()));
		} else if (expression instanceof SetSort sort) {
			constants.addAll(extractConstantReference(sort.getPredicate()));
		} else if (expression instanceof SetConcatenation cat) {
			constants.addAll(extractConstantReference(cat.getOther()));
		} else if (expression instanceof SetTransformation transform) {
			constants.addAll(extractConstantReference(transform.getExpression()));
		}

		if (expression.getNext() != null) {
			constants.addAll(extractConstantReference(expression.getNext()));
		}

		return constants;
	}

	public static Set<ConstantReference> extractConstantReference(final SetReduce expression) {
		Set<ConstantReference> constants = new HashSet<>();
		if (expression instanceof SetSummation sum) {
			constants.addAll(extractConstantReference(sum.getExpression()));
		} else if (expression instanceof SetElementQuery query) {
			constants.addAll(extractConstantReference(query.getElement()));
		}

		return constants;
	}

	public static Set<ConstantReference> extractConstantReference(final BooleanExpression expression) {
		if (expression instanceof BooleanBinaryExpression bin) {
			Set<ConstantReference> constants = new HashSet<>();
			constants.addAll(extractConstantReference(bin.getLhs()));
			constants.addAll(extractConstantReference(bin.getRhs()));
			return constants;
		} else if (expression instanceof BooleanUnaryExpression unary) {
			return extractConstantReference(unary.getOperand());
		} else if (expression instanceof BooleanLiteral) {
			return new HashSet<>();
		} else if (expression instanceof ConstantLiteral) {
			return new HashSet<>();
		} else if (expression instanceof ConstantReference ref) {
			Set<ConstantReference> constants = new HashSet<>();
			constants.add(ref);
			return constants;
		} else if (expression instanceof ArithmeticExpression arithmetic) {
			return extractConstantReference(arithmetic);
		} else {
			return extractConstantReference((RelationalExpression) expression);
		}
	}

	public static Set<ConstantReference> extractConstantReference(final RelationalExpression relExpr) {
		Set<ConstantReference> constants = new HashSet<>();
		if (relExpr.getLhs() instanceof ArithmeticExpression arithmetic) {
			constants.addAll(extractConstantReference(arithmetic));
		} else {
			constants.addAll(extractConstantReference((BooleanExpression) relExpr.getLhs()));
		}

		if (relExpr.getRhs() instanceof ArithmeticExpression arithmetic) {
			constants.addAll(extractConstantReference(arithmetic));
		} else {
			constants.addAll(extractConstantReference((BooleanExpression) relExpr.getRhs()));
		}
		return constants;
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
		ExpressionReturnType expressionType = null;
		if (expression instanceof MappingReference) {
			expressionType = ExpressionReturnType.object;
		} else if (expression instanceof TypeReference) {
			expressionType = ExpressionReturnType.object;
		} else if (expression instanceof PatternReference) {
			expressionType = ExpressionReturnType.object;
		} else if (expression instanceof RuleReference) {
			expressionType = ExpressionReturnType.object;
		} else if (expression instanceof NodeReference node) {
			expressionType = extractReturnType(node);
		} else if (expression instanceof AttributeReference attribute) {
			expressionType = extractReturnType(attribute);
		} else if (expression instanceof VariableReference) {
			expressionType = ExpressionReturnType.number;
		} else {
			// Case: ContextReference
			expressionType = ExpressionReturnType.object;
		}

		if (expression.getSetExpression() != null && expression.getSetExpression().getSetReduce() != null) {
			expressionType = extractReturnType(expression.getSetExpression());
		}
		return expressionType;
	}

	public static ExpressionReturnType extractReturnType(final SetExpression expression) {
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

	public static ExpressionReturnType extractReturnType(final NodeReference expression) {
		if (expression.getAttribute() == null) {
			return ExpressionReturnType.object;
		} else {
			return extractReturnType(expression.getAttribute());
		}
	}

	public static ExpressionReturnType extractReturnType(final AttributeReference expression) {
		return extractReturnType(expression.getAttribute());
	}

	public static ExpressionReturnType extractReturnType(final AttributeExpression expression) {
		if (expression.getNext() == null) {
			if (expression.getFeature().getEType() == EcorePackage.Literals.EBOOLEAN) {
				return ExpressionReturnType.bool;
			} else if (expression.getFeature().getEType() == EcorePackage.Literals.EDOUBLE) {
				return ExpressionReturnType.number;
			} else if (expression.getFeature().getEType() == EcorePackage.Literals.EFLOAT) {
				return ExpressionReturnType.number;
			} else if (expression.getFeature().getEType() == EcorePackage.Literals.EBYTE) {
				return ExpressionReturnType.number;
			} else if (expression.getFeature().getEType() == EcorePackage.Literals.ESHORT) {
				return ExpressionReturnType.number;
			} else if (expression.getFeature().getEType() == EcorePackage.Literals.EINT) {
				return ExpressionReturnType.number;
			} else if (expression.getFeature().getEType() == EcorePackage.Literals.ELONG) {
				return ExpressionReturnType.number;
			} else if (expression.getFeature().getEType() == EcorePackage.Literals.ESTRING) {
				return ExpressionReturnType.object;
			} else if (expression.getFeature().getEType() instanceof EClass) {
				return ExpressionReturnType.object;
			} else if (expression.getFeature().getEType() instanceof EEnum) {
				return ExpressionReturnType.object;
			} else {
				throw new IllegalArgumentException("Unsupported data type: " + expression.getFeature().getEType());
			}
		} else {
			return extractReturnType(expression.getNext());
		}
	}

	public static double getUpperBound(final GipsVariable gipsVar, final VariableType type) {
		if (type == VariableType.BINARY) {
			return 1;
		} else if (type == VariableType.INTEGER) {
			return Integer.MAX_VALUE;
		} else if (type == VariableType.REAL) {
			return Double.MAX_VALUE;
		}

		throw new UnsupportedOperationException();
	}

	public static double getLowerBound(final GipsVariable gipsVar, final VariableType type) {
		if (type == VariableType.BINARY) {
			return 0;
		} else if (type == VariableType.INTEGER) {
			return Integer.MIN_VALUE;
		} else if (type == VariableType.REAL) {
			return -Double.MAX_VALUE;
		}

		throw new UnsupportedOperationException();
	}
}
