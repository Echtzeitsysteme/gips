package org.emoflon.gips.core.ilp;

import java.util.List;

import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;

public record ILPConstraint<T extends Number> (List<ILPTerm<T, Double>> lhsTerms, RelationalOperator operator,
		Double rhsConstantTerm) {

}
