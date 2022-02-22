package org.emoflon.roam.build.transformation.transformer;

import org.emoflon.roam.build.transformation.helper.RoamTransformationData;
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticValue;
import org.emoflon.roam.intermediate.RoamIntermediate.GlobalObjective;
import org.emoflon.roam.intermediate.RoamIntermediate.ObjectiveFunctionValue;
import org.emoflon.roam.roamslang.roamSLang.RoamAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamObjectiveExpression;

public class ArithmeticInGlobalObjectiveTransformer extends ArithmeticExpressionTransformer<GlobalObjective> {

	protected ArithmeticInGlobalObjectiveTransformer(RoamTransformationData data, GlobalObjective context, TransformerFactory factory) {
		super(data, context, factory);
	}
	
	@Override
	public ArithmeticExpression transform(final RoamObjectiveExpression objExpr) throws Exception {
		ArithmeticValue val = factory.createArithmeticValue();
		ObjectiveFunctionValue objVal = factory.createObjectiveFunctionValue();
		val.setValue(objVal);
		objVal.setObjective(data.eObjective2Objective().get(objExpr.getObjective()));
		return val;
	}
	
	@Override
	public ArithmeticExpression transform(final RoamAttributeExpr objExpr) throws Exception {
		throw new UnsupportedOperationException("References to objects, object attributes or ILP variables are not permitted within a global objective function.");
	}

}
