package org.emoflon.roam.build.transformation.transformer;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.roam.build.transformation.helper.RoamTransformationData;
import org.emoflon.roam.build.transformation.helper.RoamTransformationUtils;
import org.emoflon.roam.intermediate.RoamIntermediate.Constraint;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextPatternValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeValue;
import org.emoflon.roam.intermediate.RoamIntermediate.PatternConstraint;
import org.emoflon.roam.intermediate.RoamIntermediate.TypeConstraint;
import org.emoflon.roam.intermediate.RoamIntermediate.ValueExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamContextExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamMappingAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamMatchContext;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamArithmetic;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamBoolExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamTypeContext;

public class AttributeInConstraintTransformer extends AttributeExpressionTransformer<Constraint> {

	protected AttributeInConstraintTransformer(RoamTransformationData data, Constraint context,
			TransformerFactory factory) {
		super(data, context, factory);
	}

	@Override
	protected ValueExpression transform(RoamMappingAttributeExpr eMapping) throws Exception {
		if(eMapping.getExpr() != null) {
			RoamStreamExpr terminalExpr = RoamTransformationUtils.getTerminalStreamExpression(eMapping.getExpr());
			if(terminalExpr instanceof RoamStreamBoolExpr streamBool) {
				switch(streamBool.getOperator()) {
					case COUNT -> {
						SumExpressionTransformer transformer = transformerFactory.createSumTransformer(context);
						return transformer.transform(eMapping);
					}
					case EXISTS -> {
						throw new IllegalArgumentException("Some constrains contain invalid values within arithmetic expressions, e.g., boolean values instead of arithmetic values.");
					}
					case NOTEXISTS -> {
						throw new IllegalArgumentException("Some constrains contain invalid values within arithmetic expressions, e.g., boolean values instead of arithmetic values.");
					}
					default -> {
						throw new UnsupportedOperationException("Unknown stream operator: "+streamBool.getOperator());
					}
				}
			} else if(terminalExpr instanceof RoamStreamArithmetic streamArithmetic){
				SumExpressionTransformer transformer = transformerFactory.createSumTransformer(context);
				return transformer.transform(eMapping, streamArithmetic);
			} else {
				throw new UnsupportedOperationException("Some constrains contain invalid values within arithmetic expressions, e.g., objects or streams of objects instead of arithmetic values.");
			}
		} else  {
			throw new UnsupportedOperationException("Some constrains contain invalid values within arithmetic expressions, e.g., objects or streams of objects instead of arithmetic values.");
		}
	}

	@Override
	protected ValueExpression transformNoExprAndNoStream(RoamContextExpr eContext, EObject contextType)
			throws Exception {
		if(contextType instanceof RoamTypeContext typeContext) {
			TypeConstraint tc = (TypeConstraint) context;
			ContextTypeValue typeValue = factory.createContextTypeValue();
			typeValue.setReturnType(tc.getModelType().getType());
			typeValue.setTypeContext(tc.getModelType());
			return typeValue;
		} else if(contextType instanceof RoamMatchContext matchContext) { 
			PatternConstraint pc = (PatternConstraint) context;
			ContextPatternValue patternValue = factory.createContextPatternValue();
			patternValue.setReturnType(pc.getPattern().getPattern().eClass());
			patternValue.setPatternContext(pc.getPattern());
			return patternValue;
		} else {
			throw new UnsupportedOperationException("Using sets of mapping variables as operands in boolean or arithmetic expressions is not allowed.");
		}
	}

}
