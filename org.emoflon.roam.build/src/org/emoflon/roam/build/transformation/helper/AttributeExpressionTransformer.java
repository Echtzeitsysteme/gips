package org.emoflon.roam.build.transformation.helper;

import org.emoflon.roam.intermediate.RoamIntermediate.ValueExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamAttributeExpr;

public interface AttributeExpressionTransformer {
	public ValueExpression transform(final RoamAttributeExpr eAttribute) throws Exception; 
}
