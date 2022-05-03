package org.emoflon.gips.build.transformation.transformer;

import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.gipsl.gipsl.GipsRelExpr;

public interface RelationalExpressionTransformer {
	public RelationalExpression transform(final GipsRelExpr eRelational) throws Exception;
}
