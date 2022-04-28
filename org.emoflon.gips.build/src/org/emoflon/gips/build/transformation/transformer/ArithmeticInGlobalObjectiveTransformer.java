package org.emoflon.gips.build.transformation.transformer;

import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticValue;
import org.emoflon.gips.intermediate.GipsIntermediate.GlobalObjective;
import org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveFunctionValue;
import org.emoflon.gips.gipsl.gipsl.GipsAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsObjectiveExpression;

public class ArithmeticInGlobalObjectiveTransformer extends ArithmeticExpressionTransformer<GlobalObjective> {

	protected ArithmeticInGlobalObjectiveTransformer(GipsTransformationData data, GlobalObjective context,
			TransformerFactory factory) {
		super(data, context, factory);
	}

	@Override
	public ArithmeticExpression transform(final GipsObjectiveExpression objExpr) throws Exception {
		ArithmeticValue val = factory.createArithmeticValue();
		ObjectiveFunctionValue objVal = factory.createObjectiveFunctionValue();
		val.setValue(objVal);
		objVal.setObjective(data.eObjective2Objective().get(objExpr.getObjective()));
		return val;
	}

	@Override
	public ArithmeticExpression transform(final GipsAttributeExpr objExpr) throws Exception {
		throw new UnsupportedOperationException(
				"References to objects, object attributes or ILP variables are not permitted within a global objective function.");
	}

}
