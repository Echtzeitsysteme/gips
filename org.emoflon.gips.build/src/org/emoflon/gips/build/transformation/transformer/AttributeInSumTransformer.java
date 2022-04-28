package org.emoflon.gips.build.transformation.transformer;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeValue;
import org.emoflon.gips.intermediate.GipsIntermediate.Pattern;
import org.emoflon.gips.intermediate.GipsIntermediate.SumExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Type;
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression;
import org.emoflon.gips.gipsl.gipsl.GipsContextExpr;
import org.emoflon.gips.gipsl.gipsl.GipsContextOperationExpression;
import org.emoflon.gips.gipsl.gipsl.GipsMappingAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsMatchContext;
import org.emoflon.gips.gipsl.gipsl.GipsStreamExpr;
import org.emoflon.gips.gipsl.gipsl.GipsTypeContext;

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
		} else if (contextType instanceof GipsMatchContext matchContext) {
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
	protected ValueExpression transformVariableStreamOperation(GipsContextOperationExpression eContextOp,
			GipsMappingAttributeExpr eMappingAttribute, GipsStreamExpr streamIteratorContainer) throws Exception {
		throw new UnsupportedOperationException("ILP variable access ist not allowed in stream expressions.");
	}

}
