package org.emoflon.gips.build.transformation.transformer;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.build.transformation.helper.TransformationContext;
import org.emoflon.gips.gipsl.gipsl.EditorGTFile;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticBracket;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticConstant;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExponential;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpression;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticProduct;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticSum;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticUnary;
import org.emoflon.gips.gipsl.gipsl.GipsConstantReference;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunction;
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunctionReference;
import org.emoflon.gips.gipsl.gipsl.GipsObjective;
import org.emoflon.gips.gipsl.gipsl.GipsValueExpression;
import org.emoflon.gips.gipsl.gipsl.impl.EditorGTFileImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsConstraintImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsLinearFunctionImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsObjectiveImpl;
import org.emoflon.gips.gipsl.scoping.GipslScopeContextUtil;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticBinaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticBinaryOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticUnaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticUnaryOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantReference;
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantValue;
import org.emoflon.gips.intermediate.GipsIntermediate.Context;
import org.emoflon.gips.intermediate.GipsIntermediate.DoubleLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.IntegerLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.LinearFunctionReference;
import org.emoflon.gips.intermediate.GipsIntermediate.SetOperation;

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
		} else if (eExpression instanceof GipsConstantReference reference) {
			return transform(reference);
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
			constantLiteral.setConstant(ConstantValue.E);
		}
		case NULL -> {
			constantLiteral.setConstant(ConstantValue.NULL);
		}
		case PI -> {
			constantLiteral.setConstant(ConstantValue.PI);
		}
		default -> {
			throw new IllegalArgumentException("Unknown constant type " + constant.getValue());
		}
		}
		;
		return constantLiteral;
	}

	public ArithmeticExpression transform(final GipsLinearFunctionReference function) throws Exception {
		LinearFunctionReference reference = factory.createLinearFunctionReference();
		reference.setFunction(data.eFunction2Function().get(function.getFunction()));
		return reference;
	}

	public ArithmeticExpression transform(final GipsConstantReference eReference) throws Exception {
		EObject container = (EObject) GipslScopeContextUtil.getContainer(eReference, Set.of(EditorGTFileImpl.class,
				GipsConstraintImpl.class, GipsLinearFunctionImpl.class, GipsObjectiveImpl.class));

		ConstantReference reference = factory.createConstantReference();
		if (container instanceof GipsConstraint eConstraint) {
			reference.setConstant(data.getConstant(eConstraint, eReference.getConstant()));
		} else if (container instanceof GipsLinearFunction eFunction) {
			reference.setConstant(data.getConstant(eFunction, eReference.getConstant()));
		} else if (container instanceof GipsObjective eObjective) {
			reference.setConstant(data.getConstant(eObjective, eReference.getConstant()));
		} else {
			// Case: Container instanceof EditorGTFile
			reference.setConstant(data.getConstant((EditorGTFile) container, eReference.getConstant()));
		}

		if (eReference.getSetExpression() != null) {
			ValueExpressionTransformer transformer = null;
			if (reference.getConstant().getExpression() instanceof SetOperation setOp) {
				transformer = transformerFactory.createValueTransformer(localContext, setOp);
			} else {
				transformer = transformerFactory.createValueTransformer(localContext);
			}
			reference.setSetExpression(transformer.transform(eReference.getSetExpression()));
		}

		return reference;
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
