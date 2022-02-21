package org.emoflon.roam.build.transformation.helper;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.roam.build.transformation.RoamTransformationData;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextPatternValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeValue;
import org.emoflon.roam.intermediate.RoamIntermediate.Pattern;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.Type;
import org.emoflon.roam.intermediate.RoamIntermediate.ValueExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamContextExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamContextOperationExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamMappingAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamMatchContext;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamTypeContext;

public class AttributeInStreamTransformer extends AttributeExpressionTransformer<StreamExpression> {

	protected AttributeInStreamTransformer(RoamTransformationData data, StreamExpression context,
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
