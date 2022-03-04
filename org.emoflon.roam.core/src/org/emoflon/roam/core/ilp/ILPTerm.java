package org.emoflon.roam.core.ilp;

public record ILPTerm<V extends Number, W extends Number> (ILPVariable<V> variable, W weight) {

}
