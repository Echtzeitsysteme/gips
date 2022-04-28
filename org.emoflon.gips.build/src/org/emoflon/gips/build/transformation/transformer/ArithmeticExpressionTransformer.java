package org.emoflon.gips.build.transformation.transformer;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.build.transformation.helper.TransformationContext;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticValue;
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.DoubleLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.IntegerLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticOperator;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpr;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBracketExpr;
import org.emoflon.gips.gipsl.gipsl.GipsExpArithmeticExpr;
import org.emoflon.gips.gipsl.gipsl.GipsObjectiveExpression;
import org.emoflon.gips.gipsl.gipsl.GipsProductArithmeticExpr;
import org.emoflon.gips.gipsl.gipsl.GipsSumArithmeticExpr;
import org.emoflon.gips.gipsl.gipsl.GipsUnaryArithmeticExpr;

public class ArithmeticExpressionTransformer<T extends EObject> extends TransformationContext<T> {
	protected ArithmeticExpressionTransformer(GipsTransformationData data, T context, TransformerFactory factory) {
		super(data, context, factory);
	}

	public ArithmeticExpression transform(final GipsArithmeticExpr eArithmetic) throws Exception {
		if (eArithmetic instanceof GipsSumArithmeticExpr sumExpr) {
			return transform(sumExpr);
		} else if (eArithmetic instanceof GipsProductArithmeticExpr prodExpr) {
			return transform(prodExpr);
		} else if (eArithmetic instanceof GipsExpArithmeticExpr expExpr) {
			return transform(expExpr);
		} else if (eArithmetic instanceof GipsUnaryArithmeticExpr unaryExpr) {
			return transform(unaryExpr);
		} else if (eArithmetic instanceof GipsBracketExpr bracketExpr) {
			return transform(bracketExpr);
		} else if (eArithmetic instanceof GipsArithmeticLiteral lit) {
			return transform(lit);
		} else if (eArithmetic instanceof GipsObjectiveExpression objExpr) {
			return transform(objExpr);
		} else {
			return transform((GipsAttributeExpr) eArithmetic);
		}
	}

	public ArithmeticExpression transform(final GipsSumArithmeticExpr sumExpr) throws Exception {
		BinaryArithmeticExpression binExpr = factory.createBinaryArithmeticExpression();
		switch (sumExpr.getOperator()) {
		case MINUS -> {
			binExpr.setOperator(BinaryArithmeticOperator.SUBTRACT);
		}
		case PLUS -> {
			binExpr.setOperator(BinaryArithmeticOperator.ADD);
		}
		default -> {
			throw new UnsupportedOperationException("Unknown arithmetic operator: " + sumExpr.getOperator());
		}
		}
		binExpr.setLhs(transform(sumExpr.getLeft()));
		binExpr.setRhs(transform(sumExpr.getRight()));
		return binExpr;
	}

	public ArithmeticExpression transform(final GipsProductArithmeticExpr prodExpr) throws Exception {
		BinaryArithmeticExpression binExpr = factory.createBinaryArithmeticExpression();
		switch (prodExpr.getOperator()) {
		case MULT -> {
			binExpr.setOperator(BinaryArithmeticOperator.MULTIPLY);
		}
		case DIV -> {
			binExpr.setOperator(BinaryArithmeticOperator.DIVIDE);
		}
		default -> {
			throw new UnsupportedOperationException("Unknown arithmetic operator: " + prodExpr.getOperator());
		}
		}
		binExpr.setLhs(transform(prodExpr.getLeft()));
		binExpr.setRhs(transform(prodExpr.getRight()));
		return binExpr;
	}

	public ArithmeticExpression transform(final GipsExpArithmeticExpr expExpr) throws Exception {
		BinaryArithmeticExpression binExpr = factory.createBinaryArithmeticExpression();
		switch (expExpr.getOperator()) {
		case POW -> {
			binExpr.setOperator(BinaryArithmeticOperator.POW);
		}
		default -> {
			throw new UnsupportedOperationException("Unknown arithmetic operator: " + expExpr.getOperator());
		}
		}
		binExpr.setLhs(transform(expExpr.getLeft()));
		binExpr.setRhs(transform(expExpr.getRight()));
		return binExpr;
	}

	public ArithmeticExpression transform(final GipsUnaryArithmeticExpr unaryExpr) throws Exception {
		UnaryArithmeticExpression unExpr = factory.createUnaryArithmeticExpression();
		switch (unaryExpr.getOperator()) {
		case ABS -> {
			unExpr.setOperator(UnaryArithmeticOperator.ABSOLUTE);
		}
		case COS -> {
			unExpr.setOperator(UnaryArithmeticOperator.COSINE);
		}
		case NEG -> {
			unExpr.setOperator(UnaryArithmeticOperator.NEGATE);
		}
		case SIN -> {
			unExpr.setOperator(UnaryArithmeticOperator.SINE);
		}
		case SQRT -> {
			unExpr.setOperator(UnaryArithmeticOperator.SQRT);
		}
		default -> {
			throw new UnsupportedOperationException("Unknown arithmetic operator: " + unaryExpr.getOperator());
		}
		}
		unExpr.setExpression(transform(unaryExpr.getOperand()));
		return unExpr;
	}

	public ArithmeticExpression transform(final GipsBracketExpr bracketExpr) throws Exception {
		UnaryArithmeticExpression unExpr = factory.createUnaryArithmeticExpression();
		unExpr.setOperator(UnaryArithmeticOperator.BRACKET);
		unExpr.setExpression(transform(bracketExpr.getOperand()));
		return unExpr;
	}

	public ArithmeticExpression transform(final GipsArithmeticLiteral lit) throws Exception {
		try {
			int value = Integer.parseInt(lit.getValue());
			IntegerLiteral intLit = factory.createIntegerLiteral();
			intLit.setLiteral(value);
			return intLit;
		} catch (Exception e) {
			try {
				double dValue = Double.parseDouble(lit.getValue());
				DoubleLiteral doubleLit = factory.createDoubleLiteral();
				doubleLit.setLiteral(dValue);
				return doubleLit;
			} catch (Exception e2) {
				throw new IllegalArgumentException(
						"Value <" + lit.getValue() + "> can't be parsed to neither integer nor double value.");
			}
		}
	}

	public ArithmeticExpression transform(final GipsObjectiveExpression objExpr) throws Exception {
		throw new UnsupportedOperationException(
				"References to objective function values not permitted within constraints or objective functions.");
	}

	public ArithmeticExpression transform(final GipsAttributeExpr objExpr) throws Exception {
		ArithmeticValue aValue = factory.createArithmeticValue();
		AttributeExpressionTransformer transformer = transformerFactory.createAttributeTransformer(context);
		aValue.setValue(transformer.transform(objExpr));
		return aValue;
	}
}
