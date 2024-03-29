package org.emoflon.gips.build.transformation.transformer;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.gipsl.gipsl.GipsContextExpr;
import org.emoflon.gips.gipsl.gipsl.GipsMappingAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsMappingValue;
import org.emoflon.gips.gipsl.gipsl.GipsPatternContext;
import org.emoflon.gips.gipsl.gipsl.GipsStreamExpr;
import org.emoflon.gips.gipsl.gipsl.GipsTypeContext;
import org.emoflon.gips.gipsl.gipsl.GipsVariableOperationExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingVariableValue;
import org.emoflon.gips.intermediate.GipsIntermediate.Pattern;
import org.emoflon.gips.intermediate.GipsIntermediate.SumExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Type;
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression;

public class AttributeInSumTransformer extends AttributeExpressionTransformer<SumExpression> {

	protected AttributeInSumTransformer(GipsTransformationData data, SumExpression context,
			TransformerFactory factory) {
		super(data, context, factory);
	}

	@Override
	protected ValueExpression transform(GipsMappingAttributeExpr eMapping) throws Exception {
		// TODO: We could support this to a limited degree in the future.
		throw new UnsupportedOperationException("Nested Mapping access operations not yet supported.");
	}

	@Override
	protected ValueExpression transformNoExprAndNoStream(GipsContextExpr eContext, EObject contextType)
			throws Exception {
		if (contextType instanceof GipsTypeContext typeContext) {
			Type tc = data.getType((EClass) typeContext.getType());
			ContextTypeValue typeValue = factory.createContextTypeValue();
			typeValue.setReturnType(tc.getType());
			typeValue.setTypeContext(tc);
			return typeValue;
		} else if (contextType instanceof GipsPatternContext matchContext) {
			Pattern pc = data.getPattern(matchContext.getPattern());
			ContextPatternValue patternValue = factory.createContextPatternValue();
			patternValue.setPatternContext(pc);
			return patternValue;
		} else {
			throw new UnsupportedOperationException(
					"Using sets of mapping variables as operands in boolean or arithmetic expressions is not allowed.");
		}
	}

	@Override
	protected ValueExpression transformVariableStreamOperation(GipsVariableOperationExpression eContextOp,
			GipsMappingAttributeExpr eMappingAttribute, GipsStreamExpr streamIteratorContainer) throws Exception {
		if (eContextOp instanceof GipsMappingValue mpValue) {
			IteratorMappingVariableValue mappingValue = factory.createIteratorMappingVariableValue();
			mappingValue.setMappingContext(data.eMapping2Mapping().get(eMappingAttribute.getMapping()));
			mappingValue.setStream(data.eStream2SetOp().get(streamIteratorContainer));
			mappingValue.setReturnType(EcorePackage.Literals.EINT);
			return mappingValue;
		} else {
			throw new UnsupportedOperationException(
					"Nested ILP variable constraint expressions are not allowed in stream expressions.");
		}
	}

}
