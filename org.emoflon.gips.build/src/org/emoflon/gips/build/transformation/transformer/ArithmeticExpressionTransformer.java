package org.emoflon.gips.build.transformation.transformer;

import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.build.transformation.helper.TransformationContext;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticBracket;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticConstant;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExponential;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpression;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticProduct;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticSum;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticUnary;
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunctionReference;
import org.emoflon.gips.gipsl.gipsl.GipsValueExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticBinaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticBinaryOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticUnaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticUnaryOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.Constant;
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.Context;
import org.emoflon.gips.intermediate.GipsIntermediate.DoubleLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.IntegerLiteral;

public class ArithmeticExpressionTransformer extends TransformationContext {
	protected ArithmeticExpressionTransformer(GipsTransformationData data, Context localContext,
			TransformerFactory factory) {
		super(data, localContext, factory);
	}

	protected ArithmeticExpressionTransformer(GipsTransformationData data, Context localContext, Context setContext,
			TransformerFactory factory) {
		super(data, localContext, setContext, factory);
	}

	public ArithmeticExpression transform(final GipsArithmeticExpression eExpression) throws Exception {
		if (eExpression instanceof GipsArithmeticSum sum) {
			return transform(sum);
		} else if (eExpression instanceof GipsArithmeticProduct product) {
			return transform(product);
		} else if (eExpression instanceof GipsArithmeticExponential exponential) {
			return transform(exponential);
		} else if (eExpression instanceof GipsArithmeticUnary unary) {
			return transform(unary);
		} else if (eExpression instanceof GipsArithmeticBracket bracket) {
			return transform(bracket);
		} else if (eExpression instanceof GipsArithmeticLiteral literal) {
			return transform(literal);
		} else if (eExpression instanceof GipsArithmeticConstant constant) {
			return transform(constant);
		} else if (eExpression instanceof GipsLinearFunctionReference function) {
			return transform(function);
		} else {
			return transform((GipsValueExpression) eExpression);
		}
	}

	public ArithmeticExpression transform(final GipsArithmeticSum sum) throws Exception {
		ArithmeticBinaryExpression binExpr = factory.createArithmeticBinaryExpression();
		switch (sum.getOperator()) {
		case MINUS -> {
			binExpr.setOperator(ArithmeticBinaryOperator.SUBTRACT);
		}
		case PLUS -> {
			binExpr.setOperator(ArithmeticBinaryOperator.ADD);
		}
		default -> {
			throw new UnsupportedOperationException("Unknown arithmetic operator: " + sum.getOperator());
		}
		}
		binExpr.setLhs(transform(sum.getLeft()));
		binExpr.setRhs(transform(sum.getRight()));
		return binExpr;
	}

	public ArithmeticExpression transform(final GipsArithmeticProduct product) throws Exception {
		ArithmeticBinaryExpression binExpr = factory.createArithmeticBinaryExpression();
		switch (product.getOperator()) {
		case MULT -> {
			binExpr.setOperator(ArithmeticBinaryOperator.MULTIPLY);
		}
		case DIV -> {
			binExpr.setOperator(ArithmeticBinaryOperator.DIVIDE);
		}
		default -> {
			throw new UnsupportedOperationException("Unknown arithmetic operator: " + product.getOperator());
		}
		}
		binExpr.setLhs(transform(product.getLeft()));
		binExpr.setRhs(transform(product.getRight()));
		return binExpr;
	}

	public ArithmeticExpression transform(final GipsArithmeticExponential exponential) throws Exception {
		ArithmeticBinaryExpression binExpr = factory.createArithmeticBinaryExpression();
		switch (exponential.getOperator()) {
		case POW -> {
			binExpr.setOperator(ArithmeticBinaryOperator.POW);
		}
		case LOG -> {
			binExpr.setOperator(ArithmeticBinaryOperator.LOG);
		}
		default -> {
			throw new UnsupportedOperationException("Unknown arithmetic operator: " + exponential.getOperator());
		}
		}
		binExpr.setLhs(transform(exponential.getLeft()));
		binExpr.setRhs(transform(exponential.getRight()));
		return binExpr;
	}

	public ArithmeticExpression transform(final GipsArithmeticUnary unaryExpr) throws Exception {
		ArithmeticUnaryExpression unExpr = factory.createArithmeticUnaryExpression();
		switch (unaryExpr.getOperator()) {
		case ABS -> {
			unExpr.setOperator(ArithmeticUnaryOperator.ABSOLUTE);
		}
		case COS -> {
			unExpr.setOperator(ArithmeticUnaryOperator.COSINE);
		}
		case NEG -> {
			unExpr.setOperator(ArithmeticUnaryOperator.NEGATE);
		}
		case SIN -> {
			unExpr.setOperator(ArithmeticUnaryOperator.SINE);
		}
		case SQRT -> {
			unExpr.setOperator(ArithmeticUnaryOperator.SQRT);
		}
		default -> {
			throw new UnsupportedOperationException("Unknown arithmetic operator: " + unaryExpr.getOperator());
		}
		}
		unExpr.setOperand(transform(unaryExpr.getOperand()));
		return unExpr;
	}

	public ArithmeticExpression transform(final GipsArithmeticBracket bracket) throws Exception {
		ArithmeticUnaryExpression unExpr = factory.createArithmeticUnaryExpression();
		unExpr.setOperator(ArithmeticUnaryOperator.BRACKET);
		unExpr.setOperand(transform(bracket.getOperand()));
		return unExpr;
	}

	public ArithmeticExpression transform(final GipsArithmeticLiteral literal) throws Exception {
		try {
			int value = Integer.parseInt(literal.getValue());
			IntegerLiteral intLit = factory.createIntegerLiteral();
			intLit.setLiteral(value);
			return intLit;
		} catch (Exception e) {
			try {
				double dValue = Double.parseDouble(literal.getValue());
				DoubleLiteral doubleLit = factory.createDoubleLiteral();
				doubleLit.setLiteral(dValue);
				return doubleLit;
			} catch (Exception e2) {
				throw new IllegalArgumentException(
						"Value <" + literal.getValue() + "> can't be parsed to neither integer nor double value.");
			}
		}
	}

	public ArithmeticExpression transform(final GipsArithmeticConstant constant) throws Exception {
		ConstantLiteral constantLiteral = factory.createConstantLiteral();
		switch (constant.getValue()) {
		case E -> {
			constantLiteral.setConstant(Constant.E);
		}
		case NULL -> {
			constantLiteral.setConstant(Constant.NULL);
		}
		case PI -> {
			constantLiteral.setConstant(Constant.PI);
		}
		default -> {
			throw new IllegalArgumentException("Unknown constant type " + constant.getValue());
		}
		}
		;
		return constantLiteral;
	}

	public ArithmeticExpression transform(final GipsLinearFunctionReference function) throws Exception {
		throw new UnsupportedOperationException(
				"References to objective function values not permitted within constraints or objective functions.");
	}

	public ArithmeticExpression transform(final GipsValueExpression eValue) throws Exception {
		ValueExpressionTransformer transformer = null;
		if (setContext == null) {
			transformer = transformerFactory.createValueTransformer(localContext);
		} else {
			transformer = transformerFactory.createValueTransformer(localContext, setContext);
		}
		return transformer.transform(eValue);
	}
}
