package org.emoflon.gips.build.transformation.transformer;

import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunctionReference;
import org.emoflon.gips.gipsl.gipsl.GipsValueExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.LinearFunctionReference;

public class ArithmeticInObjectiveTransformer extends ArithmeticExpressionTransformer {

	protected ArithmeticInObjectiveTransformer(GipsTransformationData data, TransformerFactory factory) {
		super(data, null, factory);
	}

	@Override
	public ArithmeticExpression transform(final GipsLinearFunctionReference function) throws Exception {
		LinearFunctionReference reference = factory.createLinearFunctionReference();
		reference.setFunction(data.eFunction2Function().get(function.getFunction()));
		return reference;
	}

	@Override
	public ArithmeticExpression transform(final GipsValueExpression value) throws Exception {
		throw new UnsupportedOperationException(
				"References to objects, object attributes or ILP variables are not permitted within a global objective function.");
	}

}
