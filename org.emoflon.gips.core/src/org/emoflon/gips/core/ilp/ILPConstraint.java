package org.emoflon.gips.core.ilp;

import java.util.List;

import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;

public record ILPConstraint(List<ILPTerm> lhsTerms, RelationalOperator operator, Double rhsConstantTerm) {

}
