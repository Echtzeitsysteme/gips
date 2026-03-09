package org.emoflon.gips.core.milp;

public class ProblemStatistics {

	final public long mappings;
	final public long vars;
	final public long constraints;
	private long removedDuplicateConstraints = 0;
	private long removedTrivialConstraints = 0;

	public ProblemStatistics(final long mappings, final long vars, final long constraints) {
		if (mappings < 0 || vars < 0 || constraints < 0) {
			throw new IllegalArgumentException("Given value was smaller than 0, which is not supported.");
		}

		this.mappings = mappings;
		this.vars = vars;
		this.constraints = constraints;
	}

	public void setRemovedDuplicateConstraints(final long removedDuplicateConstraints) {
		if (removedDuplicateConstraints < 0) {
			throw new IllegalArgumentException("Given value was smaller than 0, which is not supported.");
		}

		this.removedDuplicateConstraints = removedDuplicateConstraints;
	}

	public long getRemovedDuplicateConstraints() {
		return this.removedDuplicateConstraints;
	}

	public void setRemovedTrivialConstraints(final long removedTrivialConstraints) {
		if (removedTrivialConstraints < 0) {
			throw new IllegalArgumentException("Given value was smaller than 0, which is not supported.");
		}

		this.removedTrivialConstraints = removedTrivialConstraints;
	}

	public long getRemovedTrivialConstraints() {
		return this.removedTrivialConstraints;
	}

	/**
	 * Returns the number of mappings of the problem. (Sustains backwards
	 * compatibility.)
	 * 
	 * @return Number of mappings of the problem.
	 */
	@Deprecated
	public long mappings() {
		return this.mappings;
	}

	/**
	 * Returns the number of variables of the problem. (Sustains backwards
	 * compatibility.)
	 * 
	 * @return Number of variables of the problem.
	 */
	@Deprecated
	public long vars() {
		return this.vars;
	}

	/**
	 * Returns the number of constraints of the problem. (Sustains backwards
	 * compatibility.)
	 * 
	 * @return Number of constraints of the problem.
	 */
	@Deprecated
	public long constraints() {
		return this.constraints;
	}

}
