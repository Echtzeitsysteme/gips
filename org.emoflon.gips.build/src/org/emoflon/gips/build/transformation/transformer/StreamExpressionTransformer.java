package org.emoflon.gips.build.transformation.transformer;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.build.transformation.helper.TransformationContext;
import org.emoflon.gips.gipsl.gipsl.GipsSelect;
import org.emoflon.gips.gipsl.gipsl.GipsStreamExpr;
import org.emoflon.gips.gipsl.gipsl.GipsStreamNavigation;
import org.emoflon.gips.gipsl.gipsl.GipsStreamSet;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamFilterOperation;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamOperation;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamSelectOperation;

public class StreamExpressionTransformer<T extends EObject> extends TransformationContext<T> {

	protected StreamExpressionTransformer(GipsTransformationData data, T context, TransformerFactory factory) {
		super(data, context, factory);
	}

	public StreamExpression transform(GipsStreamExpr eStream) throws Exception {
		return createFilterForSum(eStream);
	}

	protected StreamExpression createFilterForSum(final GipsStreamExpr streamExpr) throws Exception {
		StreamExpression expr = factory.createStreamExpression();
		data.eStream2SetOp().put(streamExpr, expr);
		if (streamExpr instanceof GipsStreamNavigation nav) {
			expr.setCurrent(createStreamFilterOperation(expr, nav.getLeft()));
			if (!(nav.getRight() instanceof GipsSelect || nav.getRight() instanceof GipsStreamSet)) {
//				throw new IllegalArgumentException("Stream expression <"+nav.getRight()+"> is not a valid filter operation.");
				return expr;
			} else {
				expr.setChild(createFilterForSum(nav.getRight()));
				return expr;
			}
		} else if (streamExpr instanceof GipsSelect select) {
			expr.setCurrent(createStreamFilterOperation(expr, select));
			return expr;
		} else if (streamExpr instanceof GipsStreamSet set) {
			expr.setCurrent(createStreamFilterOperation(expr, set));
			return expr;
		} else {
			expr.setCurrent(factory.createStreamNoOperation());
			return expr;
		}
	}

	protected StreamOperation createStreamFilterOperation(final StreamExpression parent,
			final GipsStreamExpr streamExpr) throws Exception {
		if (streamExpr instanceof GipsSelect select) {
			StreamSelectOperation op = factory.createStreamSelectOperation();
			op.setType((EClass) select.getType());
			return op;
		} else if (streamExpr instanceof GipsStreamSet set) {
			StreamFilterOperation op = factory.createStreamFilterOperation();
			BooleanExpressionTransformer transformer = transformerFactory.createBooleanTransformer(parent);
			op.setPredicate(transformer.transform(set.getLambda().getExpr()));
			return op;
		} else {
			throw new IllegalArgumentException(
					"Stream expression <" + streamExpr + "> is not a valid filter operation.");
		}
	}

}
