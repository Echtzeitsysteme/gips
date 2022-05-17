package org.emoflon.gips.build.transformation.transformer;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils;
import org.emoflon.gips.gipsl.gipsl.GipsContextExpr;
import org.emoflon.gips.gipsl.gipsl.GipsMappingAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsPatternContext;
import org.emoflon.gips.gipsl.gipsl.GipsStreamArithmetic;
import org.emoflon.gips.gipsl.gipsl.GipsStreamBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsStreamExpr;
import org.emoflon.gips.gipsl.gipsl.GipsTypeContext;
import org.emoflon.gips.intermediate.GipsIntermediate.Constraint;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeValue;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression;

public class AttributeInConstraintTransformer extends AttributeExpressionTransformer<Constraint> {

	protected AttributeInConstraintTransformer(GipsTransformationData data, Constraint context,
			TransformerFactory factory) {
		super(data, context, factory);
	}

	@Override
	protected ValueExpression transform(GipsMappingAttributeExpr eMapping) throws Exception {
		if (context instanceof MappingConstraint)
			throw new UnsupportedOperationException("Other mappings are not accessible from within mapping contexts!");

		if (eMapping.getExpr() != null) {
			GipsStreamExpr terminalExpr = GipsTransformationUtils.getTerminalStreamExpression(eMapping.getExpr());
			if (terminalExpr instanceof GipsStreamBoolExpr streamBool) {
				switch (streamBool.getOperator()) {
				case COUNT -> {
					SumExpressionTransformer transformer = transformerFactory.createSumTransformer(context);
					return transformer.transform(eMapping);
				}
				case NOT_EMPTY -> {
					throw new IllegalArgumentException(
							"Some constrains contain invalid values within arithmetic expressions, e.g., boolean values instead of arithmetic values.");
				}
				default -> {
					throw new UnsupportedOperationException("Unknown stream operator: " + streamBool.getOperator());
				}
				}
			} else if (terminalExpr instanceof GipsStreamArithmetic streamArithmetic) {
				SumExpressionTransformer transformer = transformerFactory.createSumTransformer(context);
				return transformer.transform(eMapping, streamArithmetic);
			} else {
				throw new UnsupportedOperationException(
						"Some constrains contain invalid values within arithmetic expressions, e.g., objects or streams of objects instead of arithmetic values.");
			}
		} else {
			throw new UnsupportedOperationException(
					"Some constrains contain invalid values within arithmetic expressions, e.g., objects or streams of objects instead of arithmetic values.");
		}
	}

	@Override
	protected ValueExpression transformNoExprAndNoStream(GipsContextExpr eContext, EObject contextType)
			throws Exception {
		if (contextType instanceof GipsTypeContext typeContext) {
			TypeConstraint tc = (TypeConstraint) context;
			ContextTypeValue typeValue = factory.createContextTypeValue();
			typeValue.setReturnType(tc.getModelType().getType());
			typeValue.setTypeContext(tc.getModelType());
			return typeValue;
		} else if (contextType instanceof GipsPatternContext matchContext) {
			PatternConstraint pc = (PatternConstraint) context;
			ContextPatternValue patternValue = factory.createContextPatternValue();
			patternValue.setReturnType(pc.getPattern().getPattern().eClass());
			patternValue.setPatternContext(pc.getPattern());
			return patternValue;
		} else {
			throw new UnsupportedOperationException(
					"Using sets of mapping variables as operands in boolean or arithmetic expressions is not allowed.");
		}
	}

}
