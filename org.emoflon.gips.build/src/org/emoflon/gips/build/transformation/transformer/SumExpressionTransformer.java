package org.emoflon.gips.build.transformation.transformer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.gips.build.transformation.helper.ArithmeticExpressionType;
import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils;
import org.emoflon.gips.build.transformation.helper.TransformationContext;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingValue;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingSumExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.SumExpression;
import org.emoflon.gips.gipsl.gipsl.GipsMappingAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsRelExpr;
import org.emoflon.gips.gipsl.gipsl.GipsStreamArithmetic;
import org.emoflon.gips.gipsl.gipsl.GipsStreamExpr;

public class SumExpressionTransformer<T extends EObject> extends TransformationContext<T> {

	protected SumExpressionTransformer(GipsTransformationData data, T context, TransformerFactory factory) {
		super(data, context, factory);
	}

	public SumExpression transform(GipsMappingAttributeExpr eGipsMapping) throws Exception {
		MappingSumExpression mapSum = factory.createMappingSumExpression();
		Mapping mapping = data.eMapping2Mapping().get(eGipsMapping.getMapping());
		GipsStreamExpr streamExpr = eGipsMapping.getExpr();
		data.eStream2SetOp().put(streamExpr, mapSum);
		mapSum.setMapping(mapping);
		mapSum.setReturnType(EcorePackage.Literals.EINT);
		// Simple expression: Just add all filtered (!) mapping variable values v={0,1}
		ArithmeticValue val = factory.createArithmeticValue();
		IteratorMappingValue itr = factory.createIteratorMappingValue();
//		TODO: Is this next line necessary?
		itr.setMappingContext(mapping);
		itr.setStream(mapSum);
		val.setValue(itr);
		val.setReturnType(EcorePackage.Literals.EINT);
		mapSum.setExpression(val);
		// Create filter expression
		StreamExpressionTransformer transformer = transformerFactory.createStreamTransformer(mapSum);
		mapSum.setFilter(transformer.transform(streamExpr));
		// Check for valid ilp semantics
		ArithmeticExpressionType filterExpressionType = GipsTransformationUtils
				.isConstantExpression(mapSum.getExpression());
//		TODO: rethink this check...
//		if(filterExpressionType == ArithmeticExpressionType.variableVector || filterExpressionType == ArithmeticExpressionType.variableValue)
//			throw new UnsupportedOperationException("Variable simultaneous value access in filter and arithmetic expression is forbidden.");

		return mapSum;
	}

	public SumExpression transform(final GipsMappingAttributeExpr eGipsMapping,
			final GipsStreamArithmetic streamArithmetic) throws Exception {
		MappingSumExpression mapSum = factory.createMappingSumExpression();
		Mapping mapping = data.eMapping2Mapping().get(eGipsMapping.getMapping());
		GipsStreamExpr streamExpr = eGipsMapping.getExpr();
		data.eStream2SetOp().put(streamExpr, mapSum);
		mapSum.setMapping(mapping);
		mapSum.setReturnType(EcorePackage.Literals.EINT);

		StreamExpressionTransformer streamTransformer = transformerFactory.createStreamTransformer(mapSum);
		mapSum.setFilter(streamTransformer.transform(streamExpr));
		ArithmeticExpressionType filterExpressionType = GipsTransformationUtils
				.isConstantExpression(mapSum.getFilter());

		ArithmeticExpressionTransformer arithmeticTransformer = transformerFactory.createArithmeticTransformer(mapSum);
		if (streamArithmetic.getLambda().getExpr() instanceof GipsRelExpr relExpr && relExpr.getRight() == null) {
			mapSum.setExpression(arithmeticTransformer.transform(relExpr.getLeft()));

			ArithmeticExpressionType arithmeticExpressionType = GipsTransformationUtils
					.isConstantExpression(mapSum.getExpression());
//			TODO: rethink this check...
//			if((filterExpressionType == ArithmeticExpressionType.variableVector || filterExpressionType == ArithmeticExpressionType.variableValue) &&
//					(arithmeticExpressionType == ArithmeticExpressionType.variableVector || arithmeticExpressionType == ArithmeticExpressionType.variableValue)) {
//				throw new UnsupportedOperationException("Variable simultaneous value access in filter and arithmetic expression is forbidden.");
//			}
			return mapSum;
		} else {
			throw new IllegalArgumentException(
					"A sum stream expression may only contain arithmetic expressions or arithmetic values.");
		}

	}

}
