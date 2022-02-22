package org.emoflon.roam.build.transformation.transformer;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.roam.build.transformation.helper.RoamTransformationData;
import org.emoflon.roam.build.transformation.helper.TransformationContext;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamFilterOperation;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamOperation;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamSelectOperation;
import org.emoflon.roam.roamslang.roamSLang.RoamSelect;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamNavigation;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamSet;

public class StreamExpressionTransformer<T extends EObject> extends TransformationContext<T> {

	protected StreamExpressionTransformer(RoamTransformationData data, T context,
			TransformerFactory factory) {
		super(data, context, factory);
	}

	public StreamExpression transform(RoamStreamExpr eStream) throws Exception{
		return createFilterForSum(eStream);
	}
	
	protected StreamExpression createFilterForSum(final RoamStreamExpr streamExpr) throws Exception{
		StreamExpression expr = factory.createStreamExpression();
		data.eStream2SetOp().put(streamExpr, expr);
		if(streamExpr instanceof RoamStreamNavigation nav) {
			expr.setCurrent(createStreamFilterOperation(expr, nav.getLeft()));
			if(!(nav.getRight() instanceof RoamSelect || nav.getRight() instanceof RoamStreamSet)) {
//				throw new IllegalArgumentException("Stream expression <"+nav.getRight()+"> is not a valid filter operation.");
				return expr;
			} else {
				expr.setChild(createFilterForSum(nav.getRight()));
				return expr;
			}
		} else if(streamExpr instanceof RoamSelect select) {
			expr.setCurrent(createStreamFilterOperation(expr, select));
			return expr;
		} else {
			RoamStreamSet set = (RoamStreamSet) streamExpr;
			expr.setCurrent(createStreamFilterOperation(expr, set));
			return expr;
		}
	}
	
	protected StreamOperation createStreamFilterOperation(final StreamExpression parent, final RoamStreamExpr streamExpr) throws Exception{
		if(streamExpr instanceof RoamSelect select) {
			StreamSelectOperation op = factory.createStreamSelectOperation();
			op.setType((EClass) select.getType());
			return op;
		} else if (streamExpr instanceof RoamStreamSet set){
			StreamFilterOperation op = factory.createStreamFilterOperation();
			BooleanExpressionTransformer transformer = transformerFactory.createBooleanTransformer(parent);
			op.setPredicate(transformer.transform(set.getLambda().getExpr()));
			return op;
		} else {
			throw new IllegalArgumentException("Stream expression <"+streamExpr+"> is not a valid filter operation.");
		}
	}

}
