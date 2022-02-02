package org.emoflon.roam.build.transformation.helper;

import org.emoflon.roam.intermediate.RoamIntermediate.SumExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamMappingAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamArithmetic;

public interface SumExpressionTransformer {
	public SumExpression transform(final RoamMappingAttributeExpr eRoamMapping) throws Exception;
	public SumExpression transform(final RoamMappingAttributeExpr eRoamMapping, final RoamStreamArithmetic streamArithmetic) throws Exception;
}
