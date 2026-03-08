package org.emoflon.gips.build.transformation.helper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.gips.gipsl.gipsl.GipsVariable;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticBinaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticUnaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanBinaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanUnaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantReference;
import org.emoflon.gips.intermediate.GipsIntermediate.LinearFunction;
import org.emoflon.gips.intermediate.GipsIntermediate.LinearFunctionReference;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.SetConcatenation;
import org.emoflon.gips.intermediate.GipsIntermediate.SetElementQuery;
import org.emoflon.gips.intermediate.GipsIntermediate.SetExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.SetFilter;
import org.emoflon.gips.intermediate.GipsIntermediate.SetOperation;
import org.emoflon.gips.intermediate.GipsIntermediate.SetReduce;
import org.emoflon.gips.intermediate.GipsIntermediate.SetSort;
import org.emoflon.gips.intermediate.GipsIntermediate.SetSummation;
import org.emoflon.gips.intermediate.GipsIntermediate.SetTransformation;
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

	public static Set<Variable> extractVariable(final ArithmeticExpression expression) {
		return extractVariableReference(expression).stream().map(r -> r.getVariable()).collect(Collectors.toSet());
	}

	public static Set<Variable> extractVariable(final ValueExpression expression) {
		return extractVariableReference(expression).stream().map(e -> e.getVariable()).collect(Collectors.toSet());
	}

	public static Set<Variable> extractVariable(final SetExpression expression) {
		return extractVariableReference(expression).stream().map(e -> e.getVariable()).collect(Collectors.toSet());
	}

	public static Set<Variable> extractVariable(final BooleanExpression expression) {
		return extractVariableReference(expression).stream().map(e -> e.getVariable()).collect(Collectors.toSet());
	}

	public static Set<Variable> extractVariable(final RelationalExpression expression) {
		return extractVariableReference(expression).stream().map(e -> e.getVariable()).collect(Collectors.toSet());
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

		if (expression instanceof VariableReference varRef)
			variables.add(varRef);

		// Else-Case: Sets of Types, Matches, Mappings
		if (expression.getSetExpression() != null)
			variables.addAll(extractVariableReference(expression.getSetExpression()));

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

	public static Set<VariableReference> extractVariableReference(final RelationalExpression expression) {
		Set<VariableReference> variables = new HashSet<>();
		if (expression.getLhs() instanceof ArithmeticExpression arithmetic) {
			variables.addAll(extractVariableReference(arithmetic));
		} else {
			variables.addAll(extractVariableReference((BooleanExpression) expression.getLhs()));
		}

		if (expression.getRhs() instanceof ArithmeticExpression arithmetic) {
			variables.addAll(extractVariableReference(arithmetic));
		} else {
			variables.addAll(extractVariableReference((BooleanExpression) expression.getRhs()));
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

	public static ExpressionReturnType extractReturnType(BooleanExpression expression) {
		return ExpressionReturnTypeResolver.extractReturnType(expression);
	}

	public static ExpressionReturnType extractReturnType(ArithmeticExpression expression) {
		return ExpressionReturnTypeResolver.extractReturnType(expression);
	}

	public static ExpressionReturnType extractReturnType(ValueExpression expression) {
		return ExpressionReturnTypeResolver.extractReturnType(expression);
	}

	public static ArithmeticExpressionType getExpressionType(BooleanExpression expression) {
		return IsConstantExpressionResolver.getExpressionType(expression);
	}

	public static ArithmeticExpressionType getExpressionType(ArithmeticExpression expression) {
		return IsConstantExpressionResolver.getExpressionType(expression);
	}

	public static ArithmeticExpressionType getExpressionType(ValueExpression expression) {
		return IsConstantExpressionResolver.getExpressionType(expression);
	}

	public static boolean isConstantExpression(BooleanExpression expression) {
		return IsConstantExpressionResolver.getExpressionType(expression) == ArithmeticExpressionType.constant;
	}

	public static boolean isConstantExpression(ArithmeticExpression expression) {
		return IsConstantExpressionResolver.getExpressionType(expression) == ArithmeticExpressionType.constant;
	}

	public static boolean isConstantExpression(ValueExpression expression) {
		return IsConstantExpressionResolver.getExpressionType(expression) == ArithmeticExpressionType.constant;
	}
}
