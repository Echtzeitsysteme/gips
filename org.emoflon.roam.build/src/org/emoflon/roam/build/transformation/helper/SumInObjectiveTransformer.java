package org.emoflon.roam.build.transformation.helper;

import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.roam.build.transformation.RoamTransformationData;
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingValue;
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingSumExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.Objective;
import org.emoflon.roam.intermediate.RoamIntermediate.SumExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamMappingAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamRelExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamArithmetic;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamExpr;

public class SumInObjectiveTransformer extends TransformationContext<Objective> implements SumExpressionTransformer {

	protected SumInObjectiveTransformer(RoamTransformationData data, Objective context, TransformerFactory factory) {
		super(data, context, factory);
	}

	@Override
	public SumExpression transform(RoamMappingAttributeExpr eRoamMapping) throws Exception {
		MappingSumExpression mapSum = factory.createMappingSumExpression();
		Mapping mapping = data.eMapping2Mapping().get(eRoamMapping.getMapping());
		RoamStreamExpr streamExpr =eRoamMapping.getExpr();
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
		return mapSum;
	}
	
	@Override
	public SumExpression transform(final RoamMappingAttributeExpr eRoamMapping, final RoamStreamArithmetic streamArithmetic) throws Exception {
		MappingSumExpression mapSum = factory.createMappingSumExpression();
		Mapping mapping = data.eMapping2Mapping().get(eRoamMapping.getMapping());
		RoamStreamExpr streamExpr = eRoamMapping.getExpr();
		data.eStream2SetOp().put(streamExpr, mapSum);
		mapSum.setMapping(mapping);
		mapSum.setReturnType(EcorePackage.Literals.EINT);

		StreamExpressionTransformer streamTransformer = transformerFactory.createStreamTransformer(mapSum);
		mapSum.setFilter(streamTransformer.transform(streamExpr));
		
		ArithmeticExpressionTransformer arithmeticTransformer = transformerFactory.createArithmeticTransformer(mapSum);
		if(streamArithmetic.getLambda().getExpr() instanceof RoamRelExpr relExpr && relExpr.getRight() == null) {
			mapSum.setExpression(arithmeticTransformer.transform(relExpr.getLeft()));
			return mapSum;
		} else {
			throw new IllegalArgumentException("A sum stream expression may only contain arithmetic expressions or arithmetic values.");
		}
		
	}

}
