package org.emoflon.roam.build.transformation.transformer;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.roam.build.transformation.helper.RoamTransformationData;
import org.emoflon.roam.intermediate.RoamIntermediate.Constraint;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNode;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNodeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextPatternNode;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextPatternNodeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextPatternValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorPatternFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorPatternNodeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorPatternNodeValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorTypeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingConstraint;
import org.emoflon.roam.intermediate.RoamIntermediate.Pattern;
import org.emoflon.roam.intermediate.RoamIntermediate.SumExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.Type;
import org.emoflon.roam.intermediate.RoamIntermediate.TypeConstraint;
import org.emoflon.roam.intermediate.RoamIntermediate.ValueExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamContextExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamContextOperationExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamFeatureExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamFeatureLit;
import org.emoflon.roam.roamslang.roamSLang.RoamLambdaAttributeExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamMappingAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamMappingContext;
import org.emoflon.roam.roamslang.roamSLang.RoamMatchContext;
import org.emoflon.roam.roamslang.roamSLang.RoamNodeAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamTypeContext;
import org.emoflon.roam.roamslang.scoping.RoamSLangScopeContextUtil;

public class AttributeInSumTransformer extends AttributeExpressionTransformer<SumExpression> {

	protected AttributeInSumTransformer(RoamTransformationData data, SumExpression context,
			TransformerFactory factory) {
		super(data, context, factory);
	}

	@Override
	protected ValueExpression transform(RoamMappingAttributeExpr eMapping) throws Exception {
		//TODO: We could support this to a limited degree in the future.
		throw new UnsupportedOperationException("Nested Mapping access operations not yet supported.");
	}

	@Override
	protected ValueExpression transformNoExprAndNoStream(RoamContextExpr eContext, EObject contextType)
			throws Exception {
		if(contextType instanceof RoamTypeContext typeContext) {
			Type tc = data.getType((EClass)typeContext.getType());
			ContextTypeValue typeValue = factory.createContextTypeValue();
			typeValue.setReturnType(tc.getType());
			typeValue.setTypeContext(tc);
			return typeValue;
		} else if(contextType instanceof RoamMatchContext matchContext) { 
			Pattern pc = data.getPattern(matchContext.getPattern());
			ContextPatternValue patternValue = factory.createContextPatternValue();
			patternValue.setPatternContext(pc);
			return patternValue;
		} else {
			throw new UnsupportedOperationException("Using sets of mapping variables as operands in boolean or arithmetic expressions is not allowed.");
		}
	}

	@Override
	protected ValueExpression transformVariableStreamOperation(RoamContextOperationExpression eContextOp,
			RoamMappingAttributeExpr eMappingAttribute, RoamStreamExpr streamIteratorContainer) throws Exception {
		throw new UnsupportedOperationException("ILP variable access ist not allowed in stream expressions.");
	}

}
