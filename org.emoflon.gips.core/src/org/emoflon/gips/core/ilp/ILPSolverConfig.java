package org.emoflon.roam.core.ilp;

public record ILPSolverConfig(boolean timeLimitEnabled, double timeLimit, boolean rndSeedEnabled, int randomSeed,
		boolean enablePresolve, boolean enableOutput) {

}
