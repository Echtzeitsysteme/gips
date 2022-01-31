package org.emoflon.roam.build.transformation.helper;

import org.emoflon.roam.build.transformation.RoamTransformationData;
import org.emoflon.roam.intermediate.RoamIntermediate.Constraint;
import org.emoflon.roam.intermediate.RoamIntermediate.ValueExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamAttributeExpr;

public class AttributeInConstraintTransformer extends TransformationContext<Constraint>
		implements AttributeExpressionTransformer {

	protected AttributeInConstraintTransformer(RoamTransformationData data, Constraint context,
			TransformerFactory factory) {
		super(data, context, factory);
	}

	@Override
	public ValueExpression transform(RoamAttributeExpr eAttribute) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
