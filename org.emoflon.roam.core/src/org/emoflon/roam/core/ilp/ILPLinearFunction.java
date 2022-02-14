package org.emoflon.roam.core.ilp;

import java.util.List;

public record ILPLinearFunction <T extends Number> (List<ILPTerm<T, Double>> terms, List<ILPConstant<Double>> constants) {

}
