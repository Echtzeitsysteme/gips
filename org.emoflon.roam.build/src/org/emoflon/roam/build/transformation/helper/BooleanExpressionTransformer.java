package org.emoflon.roam.build.transformation.helper;

import org.emoflon.roam.intermediate.RoamIntermediate.BoolExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamBoolExpr;

public interface BooleanExpressionTransformer {
	public BoolExpression transform(final RoamBoolExpr eBool) throws Exception;
}
