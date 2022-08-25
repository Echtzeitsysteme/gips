package org.emoflon.gips.build.transformation.transformer;

import org.emoflon.gips.build.transformation.helper.ExpressionReturnType;
import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils;
import org.emoflon.gips.build.transformation.helper.TransformationContext;
import org.emoflon.gips.gipsl.gipsl.GipsRelExpr;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamExpression;

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

		ExpressionReturnType lhsReturn = GipsTransformationUtils.extractReturnType(relExpr.getLhs());
		ExpressionReturnType rhsReturn = GipsTransformationUtils.extractReturnType(relExpr.getRhs());

		if (lhsReturn == ExpressionReturnType.object && rhsReturn == ExpressionReturnType.object) {
			if (relExpr.getOperator() == RelationalOperator.EQUAL) {
				relExpr.setOperator(RelationalOperator.OBJECT_EQUAL);
				return relExpr;
			} else if (relExpr.getOperator() == RelationalOperator.NOT_EQUAL) {
				relExpr.setOperator(RelationalOperator.OBJECT_NOT_EQUAL);
				return relExpr;
			} else {
				throw new UnsupportedOperationException("Unsupported comparison operation between type (" + lhsReturn
						+ " " + relExpr.getOperator() + " " + rhsReturn + ")");
			}
		} else if (lhsReturn == ExpressionReturnType.number && rhsReturn == ExpressionReturnType.number) {
			return relExpr;
		} else if (lhsReturn == ExpressionReturnType.bool && rhsReturn == ExpressionReturnType.bool) {
			return relExpr;
		} else {
			throw new IllegalArgumentException("Comparison of incompatible types (" + lhsReturn + " "
					+ relExpr.getOperator() + " " + rhsReturn + ")");
		}

	}

}
