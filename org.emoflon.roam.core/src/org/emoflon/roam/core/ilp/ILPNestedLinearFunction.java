package org.emoflon.roam.core.ilp;

import java.util.List;

public record ILPNestedLinearFunction <T extends Number> (List<ILPLinearFunction<T>> linearFunctions, List<ILPConstant<Double>> constants){

}
