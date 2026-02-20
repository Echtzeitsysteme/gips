package org.emoflon.gips.core;

/**
 * Generic GIPS framework configuration parameters. These configurations are not
 * specific to the MILP solver, the tracer, etc.
 */
public class GipsConfig {

	/**
	 * If true, GIPS will remove duplicate constraints per GIPSL constraints (group)
	 * before translating them to the MILP solver.
	 */
	private boolean removeDuplicateConstraints = true;

	/**
	 * If true, GIPS will print the total number of redundant constraints it removed
	 * and the runtime.
	 */
	private boolean printDuplicateStats = true;

	/**
	 * Sets the "remove duplicate constraint" option to the given parameter.
	 * 
	 * @param remove If true, GIPS will remove duplicate constraints per GIPSL
	 *               constraint (group).
	 */
	public void setRemoveDuplicateConstraints(final boolean remove) {
		this.removeDuplicateConstraints = remove;
	}

	/**
	 * Returns true if GIPS is configured to remove duplicate constraints per GIPSL
	 * constraint (group).
	 * 
	 * @return True if GIPS is configured to remove duplicate constraints.
	 */
	public boolean removeDuplicateConstraints() {
		return removeDuplicateConstraints;
	}

	/**
	 * Sets the print duplicate statistics value to the given value.
	 * 
	 * @param print If true, the configuration object is configured to enable GIPS
	 *              to print statistics of the duplicate constraint removal process.
	 */
	public void setPrintDuplicateStats(final boolean print) {
		this.printDuplicateStats = print;
	}

	/**
	 * Returns true if GIPS is configured to print statistics of the duplicate
	 * constraint removal process.
	 * 
	 * @return True if TIPS is configured to print statistics of the duplicate
	 *         constraint removal process.
	 */
	public boolean printDuplicateStats() {
		return printDuplicateStats;
	}

}
