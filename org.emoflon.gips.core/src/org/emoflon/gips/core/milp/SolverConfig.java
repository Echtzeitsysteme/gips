package org.emoflon.gips.core.milp;

public record SolverConfig(boolean timeLimitEnabled, double timeLimit, boolean timeLimitIncludeInitTime,
		boolean rndSeedEnabled, int randomSeed, boolean enablePresolve, boolean enableOutput, boolean enableTolerance,
		double tolerance, boolean lpOutput, String lpPath, boolean threadCount, int threads) {

	/**
	 * Returns a new SolverConfig record with the given number of threads.
	 * 
	 * @param newThreads Number of threads to be used by the (M)ILP solver.
	 * @return New SolverConfig record with the given number of threads.
	 */
	public SolverConfig withThreadCount(final int newThreads) {
		if (newThreads <= 0) {
			throw new IllegalArgumentException("Given number of ILP solver threads is smaller or equal to 0.");
		}

		return new SolverConfig( //
				timeLimitEnabled, timeLimit, timeLimitIncludeInitTime, //
				rndSeedEnabled, randomSeed, //
				enablePresolve, //
				enableOutput, //
				enableTolerance, tolerance, //
				lpOutput, lpPath, //
				true, newThreads);
	}

	/**
	 * Returns a new SolverConfig record with a new time limit value.
	 * 
	 * @param newTimeLimit New time limit value.
	 * @return New SolverConfig record with the given new time limit.
	 */
	public SolverConfig withNewTimeLimit(final double newTimeLimit) {
		if (newTimeLimit <= 0) {
			throw new IllegalArgumentException("Given new time limit is smaller or equal to 0.");
		}

		return new SolverConfig( //
				timeLimitEnabled, newTimeLimit, timeLimitIncludeInitTime, //
				rndSeedEnabled, randomSeed, //
				enablePresolve, //
				enableOutput, //
				enableTolerance, tolerance, //
				lpOutput, lpPath, //
				threadCount, threads);
	}

}
