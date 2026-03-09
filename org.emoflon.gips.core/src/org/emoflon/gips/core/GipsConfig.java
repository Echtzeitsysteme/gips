package org.emoflon.gips.core;

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

}
