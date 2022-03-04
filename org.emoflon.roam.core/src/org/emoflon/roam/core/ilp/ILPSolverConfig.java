package org.emoflon.roam.core.ilp;

public record ILPSolverConfig(int timeLimit, int randomSeed, boolean enablePresolve, boolean enableOutput) {

}
