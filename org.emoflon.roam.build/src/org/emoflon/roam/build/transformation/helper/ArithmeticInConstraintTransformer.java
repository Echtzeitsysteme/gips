package org.emoflon.roam.build.transformation.helper;

import org.emoflon.roam.build.transformation.RoamTransformationData;
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.Constraint;
import org.emoflon.roam.roamslang.roamSLang.RoamArithmeticExpr;

public class ArithmeticInConstraintTransformer extends TransformationContext<Constraint>
		implements ArithmeticExpressionTransformer {

	protected ArithmeticInConstraintTransformer(RoamTransformationData data, Constraint context, TransformerFactory factory) {
		super(data, context, factory);
	}

	@Override
	public ArithmeticExpression transform(RoamArithmeticExpr aArithmetic) {
		// TODO Auto-generated method stub
		return null;
	}

}
