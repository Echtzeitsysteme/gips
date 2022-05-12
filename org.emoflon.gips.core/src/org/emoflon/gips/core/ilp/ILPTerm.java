package org.emoflon.gips.core.ilp;

public record ILPTerm<V extends Number, W extends Number> (ILPVariable<V> variable, W weight) {

}
