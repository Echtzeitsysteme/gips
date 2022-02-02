package org.emoflon.roam.build.transformation.helper;

import org.emoflon.roam.build.transformation.RoamTransformationData;
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticValue;
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticOperator;
import org.emoflon.roam.intermediate.RoamIntermediate.DoubleLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.IntegerLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.Objective;
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

public class ArithmeticInObjectiveTransformer extends TransformationContext<Objective>
		implements ArithmeticExpressionTransformer {

	protected ArithmeticInObjectiveTransformer(RoamTransformationData data, Objective context, TransformerFactory factory) {
		super(data, context, factory);
	}

	@Override
	public ArithmeticExpression transform(RoamArithmeticExpr eArithmetic) throws Exception {
		if(eArithmetic instanceof RoamSumArithmeticExpr sumExpr) {
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
		} else if(eArithmetic instanceof RoamProductArithmeticExpr prodExpr) {
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
		} else if(eArithmetic instanceof RoamExpArithmeticExpr expExpr) {
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
		} else if(eArithmetic instanceof RoamUnaryArithmeticExpr unaryExpr) {
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
		} else if(eArithmetic instanceof RoamBracketExpr bracketExpr) {
			UnaryArithmeticExpression unExpr = factory.createUnaryArithmeticExpression();
			unExpr.setOperator(UnaryArithmeticOperator.BRACKET);
			unExpr.setExpression(transform(bracketExpr.getOperand()));
			return unExpr;
		} else { // CASE: RoamExpressionOperand -> i.e., a literal from the perspective of an arithmetic expression
			if(eArithmetic instanceof RoamArithmeticLiteral lit) {
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
 			} else if(eArithmetic instanceof RoamObjectiveExpression objExpr) {
				throw new UnsupportedOperationException("References to objective function values not permitted within objective functions.");
			} else {
				ArithmeticValue aValue = factory.createArithmeticValue();
				AttributeExpressionTransformer transformer = transformerFactory.createAttributeTransformer(context);
				aValue.setValue(transformer.transform((RoamAttributeExpr) eArithmetic));
				return aValue;
			}
		}
	}

}
