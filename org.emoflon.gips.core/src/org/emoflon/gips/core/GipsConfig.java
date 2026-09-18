package org.emoflon.gips.core;

import java.time.Duration;

import org.emoflon.gips.core.api.TimeoutException;
import org.emoflon.gips.core.milp.SolverConfig;

/**
 * Generic GIPS framework configuration parameters. These configurations are not
 * specific to the MILP solver, the tracer, etc.
 */
public class GipsConfig {

	/**
	 * If true, GIPS will remove duplicate constraints and trivial constraints per
	 * GIPSL constraints (group) before translating them to the MILP solver.
	 */
	private boolean removeUselessConstraints = true;

	/**
	 * If true, GIPS will print the total number of redundant and trivial
	 * constraints it removed and the runtime.
	 */
	private boolean printUselessConstraintsStats = true;

	private Duration buildTimeLimit = Duration.ZERO;

	/**
	 * Sets the "remove useless constraint" option to the given parameter.
	 * 
	 * @param remove If true, GIPS will remove duplicate and trivial constraints per
	 *               GIPSL constraint (group).
	 */
	public void setUselessDuplicateConstraints(final boolean remove) {
		this.removeUselessConstraints = remove;
	}

	/**
	 * Returns true if GIPS is configured to remove duplicate and trivial
	 * constraints per GIPSL constraint (group).
	 * 
	 * @return True if GIPS is configured to remove duplicate and trivial
	 *         constraints.
	 */
	public boolean removeUselessConstraints() {
		return removeUselessConstraints;
	}

	/**
	 * Sets the print duplicate and trivial constraint statistics value to the given
	 * value.
	 * 
	 * @param print If true, the configuration object is configured to enable GIPS
	 *              to print statistics of the duplicate and trivial constraint
	 *              removal process.
	 */
	public void setPrintUselessConstraintsStats(final boolean print) {
		this.printUselessConstraintsStats = print;
	}

	/**
	 * Returns true if GIPS is configured to print statistics of the duplicate and
	 * trivial constraint removal process.
	 * 
	 * @return True if GIPS is configured to print statistics of the duplicate and
	 *         trivial constraint removal process.
	 */
	public boolean printUselessConstraintsStats() {
		return printUselessConstraintsStats;
	}

	public Duration getBuildTimeLimit() {
		return buildTimeLimit;
	}

	/**
	 * Limits the time allowed to build the (M)ILP problem. <br>
	 * If the transformation exceeds this duration, the process is aborted and
	 * throws a {@link TimeoutException}.<br>
	 * A value of {@code 0} or less disables the timeout.
	 * </p>
	 * Not to be confused with {@link SolverConfig#setTimeLimit(double)}
	 *
	 * @param buildTimeLimit the upper time bound.
	 * @see java.time.Duration
	 */
	public void setBuildTimeLimit(Duration buildTimeLimit) {
		this.buildTimeLimit = buildTimeLimit == null ? Duration.ZERO : buildTimeLimit;
	}

	/**
	 * Limits the time allowed to build the (M)ILP problem. <br>
	 * If the transformation exceeds this duration, the process is aborted and
	 * throws a {@link TimeoutException}.<br>
	 * A value of {@code 0} or less disables the timeout.
	 * </p>
	 * Not to be confused with {@link SolverConfig#setTimeLimit(double)}
	 *
	 * @param seconds the upper time bound in seconds.
	 */
	public void setBuildTimeLimit(long seconds) {
		this.buildTimeLimit = seconds <= 0 ? Duration.ZERO : Duration.ofSeconds(seconds);
	}

}
