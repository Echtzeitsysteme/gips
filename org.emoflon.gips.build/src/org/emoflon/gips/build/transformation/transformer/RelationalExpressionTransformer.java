package org.emoflon.gips.build.transformation.transformer;

import org.emoflon.gips.gipsl.gipsl.GipsRelExpr;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;

public interface RelationalExpressionTransformer {
	public RelationalExpression transform(final GipsRelExpr eRelational) throws Exception;
}
