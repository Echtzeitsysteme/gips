package org.emoflon.gips.core.milp.model;

import java.util.List;

import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;

public record Constraint(List<Term> lhsTerms, RelationalOperator operator, Double rhsConstantTerm) {

}
