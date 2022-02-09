package org.emoflon.roam.core.ilp;

import java.util.List;

public record ILPObjectiveFunction <T extends Number> (List<ILPTerm<T, Double>> terms) {

}
