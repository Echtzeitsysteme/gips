package org.emoflon.gips.build.transformation.transformer;

import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.build.transformation.helper.TransformationContext;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamExpression;
import org.emoflon.gips.gipsl.gipsl.GipsRelExpr;

public class RelationalInStreamTransformer extends TransformationContext<StreamExpression>
		implements RelationalExpressionTransformer {

	protected RelationalInStreamTransformer(GipsTransformationData data, StreamExpression context,
			TransformerFactory factory) {
		super(data, context, factory);
	}

	@Override
	public RelationalExpression transform(GipsRelExpr eRelational) throws Exception {
		RelationalExpression relExpr = factory.createRelationalExpression();
		switch (eRelational.getOperator()) {
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
			throw new UnsupportedOperationException("Unknown bool operator: " + eRelational.getOperator());
		}
		ArithmeticExpressionTransformer transformer = transformerFactory.createArithmeticTransformer(context);
		relExpr.setLhs(transformer.transform(eRelational.getLeft()));
		relExpr.setRhs(transformer.transform(eRelational.getRight()));
		return relExpr;
	}

}
