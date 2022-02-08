package org.emoflon.roam.build.transformation.helper;

import org.emoflon.roam.build.transformation.RoamTransformationData;
import org.emoflon.roam.intermediate.RoamIntermediate.RelationalExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.RelationalOperator;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamRelExpr;

public class RelationalInStreamTransformer extends TransformationContext<StreamExpression> implements RelationalExpressionTransformer{

	protected RelationalInStreamTransformer(RoamTransformationData data, StreamExpression context, TransformerFactory factory) {
		super(data, context, factory);
	}

	@Override
	public RelationalExpression transform(RoamRelExpr eRelational) throws Exception {
		RelationalExpression relExpr = factory.createRelationalExpression();
		switch(eRelational.getOperator()) {
		case EQUAL:
			relExpr.setOperator(RelationalOperator.EQUAL);
			break;
		case GREATER:
			relExpr.setOperator(RelationalOperator.GREATER);
			break;
		case GREATER_OR_EQUAL:
			relExpr.setOperator(RelationalOperator.GREATER_OR_EQUAL);
			break;
		case SMALLER:
			relExpr.setOperator(RelationalOperator.LESS);
			break;
		case SMALLER_OR_EQUAL:
			relExpr.setOperator(RelationalOperator.LESS_OR_EQUAL);
			break;
		case UNEQUAL:
			relExpr.setOperator(RelationalOperator.NOT_EQUAL);
			break;
		default:
			throw new UnsupportedOperationException("Unknown bool operator: "+eRelational.getOperator());
		}
		ArithmeticExpressionTransformer transformer = transformerFactory.createArithmeticTransformer(context);
		relExpr.setLhs(transformer.transform(eRelational.getLeft()));
		relExpr.setRhs(transformer.transform(eRelational.getRight()));
		return relExpr;
	}

}
