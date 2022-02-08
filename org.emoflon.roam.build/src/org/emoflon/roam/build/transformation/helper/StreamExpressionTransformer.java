package org.emoflon.roam.build.transformation.helper;

import org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamExpr;

public interface StreamExpressionTransformer {
	public StreamExpression transform(final RoamStreamExpr eStream) throws Exception;
}
