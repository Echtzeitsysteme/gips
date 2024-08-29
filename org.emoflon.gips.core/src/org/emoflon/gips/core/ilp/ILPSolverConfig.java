package org.emoflon.gips.core.ilp;

public record ILPSolverConfig(boolean timeLimitEnabled, double timeLimit, boolean rndSeedEnabled, int randomSeed,
		boolean enablePresolve, boolean enableOutput, boolean enableTolerance, double tolerance, boolean lpOutput,
		String lpPath, boolean threadCount, int threads) {

}
