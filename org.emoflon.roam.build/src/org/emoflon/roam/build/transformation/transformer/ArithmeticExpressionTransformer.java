package org.emoflon.roam.build.transformation.transformer;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.roam.build.transformation.helper.RoamTransformationData;
import org.emoflon.roam.build.transformation.helper.TransformationContext;
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticValue;
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticOperator;
import org.emoflon.roam.intermediate.RoamIntermediate.DoubleLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.IntegerLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticOperator;
import org.emoflon.roam.roamslang.roamSLang.RoamArithmeticExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamArithmeticLiteral;
import org.emoflon.roam.roamslang.roamSLang.RoamAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamBracketExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamExpArithmeticExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamObjectiveExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamProductArithmeticExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamSumArithmeticExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamUnaryArithmeticExpr;

public class ArithmeticExpressionTransformer<T extends EObject> extends TransformationContext<T>{
	protected ArithmeticExpressionTransformer(RoamTransformationData data, T context, TransformerFactory factory) {
		super(data, context, factory);
	}

	public ArithmeticExpression transform(final RoamArithmeticExpr eArithmetic) throws Exception {
		if(eArithmetic instanceof RoamSumArithmeticExpr sumExpr) {
			return transform(sumExpr);
		} else if(eArithmetic instanceof RoamProductArithmeticExpr prodExpr) {
			return transform(prodExpr);
		} else if(eArithmetic instanceof RoamExpArithmeticExpr expExpr) {
			return transform(expExpr);
		} else if(eArithmetic instanceof RoamUnaryArithmeticExpr unaryExpr) {
			return transform(unaryExpr);
		} else if(eArithmetic instanceof RoamBracketExpr bracketExpr) {
			return transform(bracketExpr);
		} else if(eArithmetic instanceof RoamArithmeticLiteral lit) {
			return transform(lit);
		} else if(eArithmetic instanceof RoamObjectiveExpression objExpr) {
			return transform(objExpr);
		} else {
			return transform((RoamAttributeExpr) eArithmetic);
		}
	}
	
	public ArithmeticExpression transform(final RoamSumArithmeticExpr sumExpr) throws Exception {
		BinaryArithmeticExpression binExpr = factory.createBinaryArithmeticExpression();
		switch(sumExpr.getOperator()) {
			case MINUS -> {
				binExpr.setOperator(BinaryArithmeticOperator.SUBTRACT);
			}
			case PLUS -> {
				binExpr.setOperator(BinaryArithmeticOperator.ADD);
			}
			default -> {
				throw new UnsupportedOperationException("Unknown arithmetic operator: "+sumExpr.getOperator());
			}
		}
		binExpr.setLhs(transform(sumExpr.getLeft()));
		binExpr.setRhs(transform(sumExpr.getRight()));
		return binExpr;
	}
	
	public ArithmeticExpression transform(final RoamProductArithmeticExpr prodExpr) throws Exception {
		BinaryArithmeticExpression binExpr = factory.createBinaryArithmeticExpression();
		switch(prodExpr.getOperator()) {
			case MULT -> {
				binExpr.setOperator(BinaryArithmeticOperator.MULTIPLY);
			}
			case DIV -> {
				binExpr.setOperator(BinaryArithmeticOperator.DIVIDE);
			}
			default -> {
				throw new UnsupportedOperationException("Unknown arithmetic operator: "+prodExpr.getOperator());
			}
		}
		binExpr.setLhs(transform(prodExpr.getLeft()));
		binExpr.setRhs(transform(prodExpr.getRight()));
		return binExpr;
	}
	
	public ArithmeticExpression transform(final RoamExpArithmeticExpr expExpr) throws Exception {
		BinaryArithmeticExpression binExpr = factory.createBinaryArithmeticExpression();
		switch(expExpr.getOperator()) {
			case POW -> {
				binExpr.setOperator(BinaryArithmeticOperator.POW);
			}
			default -> {
				throw new UnsupportedOperationException("Unknown arithmetic operator: "+expExpr.getOperator());
			}
		}
		binExpr.setLhs(transform(expExpr.getLeft()));
		binExpr.setRhs(transform(expExpr.getRight()));
		return binExpr;
	}
	
	public ArithmeticExpression transform(final RoamUnaryArithmeticExpr unaryExpr) throws Exception {
		UnaryArithmeticExpression unExpr = factory.createUnaryArithmeticExpression();
		switch(unaryExpr.getOperator()) {
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
				throw new UnsupportedOperationException("Unknown arithmetic operator: "+unaryExpr.getOperator());
			}
		}
		unExpr.setExpression(transform(unaryExpr.getOperand()));
		return unExpr;
	}
	
	public ArithmeticExpression transform(final RoamBracketExpr bracketExpr) throws Exception {
		UnaryArithmeticExpression unExpr = factory.createUnaryArithmeticExpression();
		unExpr.setOperator(UnaryArithmeticOperator.BRACKET);
		unExpr.setExpression(transform(bracketExpr.getOperand()));
		return unExpr;
	}
	
	public ArithmeticExpression transform(final RoamArithmeticLiteral lit) throws Exception {
		try {
			int value = Integer.parseInt(lit.getValue());
			IntegerLiteral intLit = factory.createIntegerLiteral();
			intLit.setLiteral(value);
			return intLit;
		} catch(Exception e) {
			try {
				double dValue = Double.parseDouble(lit.getValue());
				DoubleLiteral doubleLit = factory.createDoubleLiteral();
				doubleLit.setLiteral(dValue);
				return doubleLit;
			} catch(Exception e2) {
				throw new IllegalArgumentException("Value <"+lit.getValue()+"> can't be parsed to neither integer nor double value.");
			}
		} 
	}
	
	public ArithmeticExpression transform(final RoamObjectiveExpression objExpr) throws Exception {
		throw new UnsupportedOperationException("References to objective function values not permitted within constraints or objective functions.");
	}
	
	public ArithmeticExpression transform(final RoamAttributeExpr objExpr) throws Exception {
		ArithmeticValue aValue = factory.createArithmeticValue();
		AttributeExpressionTransformer transformer = transformerFactory.createAttributeTransformer(context);
		aValue.setValue(transformer.transform(objExpr));
		return aValue;
	}
}
