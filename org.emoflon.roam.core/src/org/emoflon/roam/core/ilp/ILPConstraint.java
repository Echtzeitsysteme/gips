package org.emoflon.roam.core.ilp;

import java.util.List;

import org.emoflon.roam.intermediate.RoamIntermediate.RelationalOperator;

public record ILPConstraint <T extends Number> (T constantTerm, RelationalOperator operator, List<ILPTerm<T>> terms){

}
