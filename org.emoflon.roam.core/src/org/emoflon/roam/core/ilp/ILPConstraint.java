package org.emoflon.roam.core.ilp;

import java.util.List;

import org.emoflon.roam.intermediate.RoamIntermediate.RelationalOperator;

public record ILPConstraint<T extends Number> (List<ILPTerm<T, Double>> lhsTerms, RelationalOperator operator,
		Double rhsConstantTerm) {

}
