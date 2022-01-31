package org.emoflon.roam.build.transformation.helper;

import org.emoflon.roam.intermediate.RoamIntermediate.Constraint;
import org.emoflon.roam.build.transformation.RoamTransformationData;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamBoolExpr;

public class BooleanInConstraintTransformer extends TransformationContext<Constraint> implements BooleanExpressionTransformer {

	protected BooleanInConstraintTransformer(RoamTransformationData data, Constraint context, TransformerFactory factory) {
		super(data, context, factory);
	}

	@Override
	public BoolExpression transform(RoamBoolExpr eBool) {
		// TODO Auto-generated method stub
		return null;
	}

}
