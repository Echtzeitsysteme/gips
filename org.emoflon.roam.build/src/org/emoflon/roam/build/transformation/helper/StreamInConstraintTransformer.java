package org.emoflon.roam.build.transformation.helper;

import org.emoflon.roam.build.transformation.RoamTransformationData;
import org.emoflon.roam.intermediate.RoamIntermediate.Constraint;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamExpr;

public class StreamInConstraintTransformer extends TransformationContext<Constraint> implements StreamExpressionTransformer {

	protected StreamInConstraintTransformer(RoamTransformationData data, Constraint context,
			TransformerFactory factory) {
		super(data, context, factory);
	}

	@Override
	public StreamExpression transform(RoamStreamExpr eStream) {
		// TODO Auto-generated method stub
		return null;
	}

}
