package org.emoflon.roam.build.transformation.helper;

import org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.UnaryBoolOperator;
import org.emoflon.roam.build.transformation.RoamTransformationData;
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryBoolOperator;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolBinaryExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolUnaryExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolValue;
import org.emoflon.roam.roamslang.roamSLang.RoamAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamBinaryBoolExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamBoolExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamBooleanLiteral;
import org.emoflon.roam.roamslang.roamSLang.RoamExpressionOperand;
import org.emoflon.roam.roamslang.roamSLang.RoamRelExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamUnaryBoolExpr;

public class BooleanInStreamTransformer extends TransformationContext<StreamExpression> implements BooleanExpressionTransformer {

	protected BooleanInStreamTransformer(RoamTransformationData data, StreamExpression context, TransformerFactory factory) {
		super(data, context, factory);
	}

	@Override
	public BoolExpression transform(RoamBoolExpr eBool) throws Exception{
		if(eBool instanceof RoamBooleanLiteral eLitBool) {
			BoolLiteral literal = factory.createBoolLiteral();
			literal.setLiteral(eLitBool.isLiteral());
			return literal;
		} else if(eBool instanceof RoamRelExpr eRelBool) {
			if(eRelBool.getRight() == null) {
				BoolValue value = factory.createBoolValue();
				if(eRelBool.getLeft() instanceof RoamExpressionOperand && eRelBool.getLeft() instanceof RoamAttributeExpr eOperand) {
					AttributeExpressionTransformer transformer = transformerFactory.createAttributeTransformer(context);
					value.setValue(transformer.transform(eOperand));
				} else {
					throw new IllegalArgumentException("Boolean expression must not contain any numeric values!");
				}
				return value;
			} else {
				RelationalExpressionTransformer transformer = transformerFactory.createRelationalTransformer(context);
				return transformer.transform(eRelBool);
			}
		} else if(eBool instanceof RoamBinaryBoolExpr eBinBool) {
			BoolBinaryExpression binaryBool = factory.createBoolBinaryExpression();
			switch(eBinBool.getOperator()) {
			case AND:
				binaryBool.setOperator(BinaryBoolOperator.AND);
				break;
			case OR:
				binaryBool.setOperator(BinaryBoolOperator.OR);
				break;
			default :
				throw new UnsupportedOperationException("Unknown bool operator: "+eBinBool.getOperator());
			}
			binaryBool.setLhs(transform(eBinBool.getLeft()));
			binaryBool.setRhs(transform(eBinBool.getRight()));
			return binaryBool;
		} else {
			RoamUnaryBoolExpr eUnBool = (RoamUnaryBoolExpr) eBool;
			BoolUnaryExpression unaryBool = factory.createBoolUnaryExpression();
			switch(eUnBool.getOperator()) {
			case NOT:
				unaryBool.setOperator(UnaryBoolOperator.NOT);
				break;
			default:
				throw new UnsupportedOperationException("Unknown bool operator: "+eUnBool.getOperator());
			}
			unaryBool.setExpression(transform(eUnBool.getOperand()));
			return unaryBool;
		}
	}

}
