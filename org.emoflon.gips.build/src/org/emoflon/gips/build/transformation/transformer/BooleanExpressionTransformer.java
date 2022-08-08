package org.emoflon.gips.build.transformation.transformer;

import org.emoflon.gips.gipsl.gipsl.GipsBoolExpr;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolExpression;

public interface BooleanExpressionTransformer {
	public BoolExpression transform(GipsBoolExpr eBool) throws Exception;
}
