package org.emoflon.gips.core.milp.model;

import java.util.Objects;

public record Term(Variable<?> variable, double weight) implements Comparable<Term> {

	/**
	 * Custom `compareTo` method to define a lexicographic order. Most significant
	 * order is the name of the variable (we assume a variable is identical if it
	 * has the same name). The second most significant order is the weight of the
	 * variable. This implementation has a slight performance benefit over, for
	 * example, comparing the resulting strings of both `Term` objects.
	 * 
	 * Examples for achieved comparisons:
	 * <ul>
	 * <li>1 * a < 1 * b</li>
	 * <li>1 * a < 2 * a</li>
	 * <li>1 * a = 1 * a</li>
	 * </ul>
	 */
	public int compareTo(final Term other) {
		Objects.requireNonNull(other);

		if (this == other) {
			return 0;
		}

		// If the variables are equal, use the weights
		if (variable.equals(other.variable)) {
			return (int) (other.weight - weight);
		}

		// Compare only the names of the variables (we assume a variable with the same
		// name is also the same variable)
		return variable.getName().compareTo(other.variable.getName());
	}

}
