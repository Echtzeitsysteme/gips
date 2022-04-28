package org.emoflon.roam.build.transformation.transformer;

import org.emoflon.roam.intermediate.RoamIntermediate.RelationalExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamRelExpr;

public interface RelationalExpressionTransformer {
	public RelationalExpression transform(final RoamRelExpr eRelational) throws Exception;
}
