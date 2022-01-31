package org.emoflon.roam.build.transformation.helper;

import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamArithmeticExpr;

public interface ArithmeticExpressionTransformer {
	public ArithmeticExpression transform(final RoamArithmeticExpr aArithmetic) throws Exception;
}
