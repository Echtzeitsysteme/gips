package org.emoflon.gips.core.ilp;

public record ILPSolverConfig(boolean timeLimitEnabled, double timeLimit, boolean rndSeedEnabled, int randomSeed,
		boolean enablePresolve, boolean enableOutput, boolean enableTolerance, double tolerance, boolean lpOutput,
		String lpPath, boolean threadCount, int threads) {

	/**
	 * Returns a new ILPSolverConfig record with the given number of threads.
	 * 
	 * @param newThreads Number of threads to be used by the ILP solver.
	 * @return New ILPSolverConfig record with the given number of threads.
	 */
	public ILPSolverConfig withThreadCount(final int newThreads) {
		if (newThreads <= 0) {
			throw new IllegalArgumentException("Given number of ILP solver threads is smaller or equal to 0.");
		}

		return new ILPSolverConfig( //
				timeLimitEnabled, timeLimit, //
				rndSeedEnabled, randomSeed, //
				enablePresolve, //
				enableOutput, //
				enableTolerance, tolerance, //
				lpOutput, lpPath, //
				true, newThreads);
	}

}
