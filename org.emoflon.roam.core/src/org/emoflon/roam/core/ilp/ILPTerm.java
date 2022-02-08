package org.emoflon.roam.core.ilp;

public record ILPTerm <T extends Number>(ILPVariable<T> variable, T weight){
	
}
