package org.emoflon.gips.core.milp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;

public record Constraint(List<Term> lhsTerms, RelationalOperator operator, Double rhsConstantTerm) {

	/**
	 * Custom implementation of the `equals` method to ignore the ordering of the
	 * LHS terms' list. We consider to constraints to be equal if ...
	 * <ul>
	 * <li>the RHS' constant value is the same,</li>
	 * <li>the relational operator is the same, and</li>
	 * <li>the list of LHS terms is the same while ignoring the ordering of the
	 * terms.</li>
	 * </ul>
	 * 
	 * @param other Another object to be compared to this object.
	 * @return True if the other object is equal to this object.
	 */
	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		}

		if (other == null) {
			return false;
		}

		if (!(other instanceof Constraint)) {
			return false;
		}

		final Constraint c = (Constraint) other;
		if (this.rhsConstantTerm.equals(c.rhsConstantTerm) && this.operator.equals(c.operator)
				&& lhsTermsEqualIgnoreOrder(c.lhsTerms)) {
			return true;
		}

		return false;
	}

	/**
	 * Custom implementation of the `hashCode` method to ignore the ordering of the
	 * LHS terms' list. Therefore, it sorts the LHS' terms lexicographically.
	 * 
	 * @return hash code of this object as integer.
	 */
	@Override
	public int hashCode() {
		final ArrayList<Term> sortedLhsTerms = new ArrayList<Term>(lhsTerms.size());
		sortedLhsTerms.addAll(lhsTerms);
		sortedLhsTerms.sort(null);
		return Objects.hash(sortedLhsTerms, operator, rhsConstantTerm);
	}

	/**
	 * Utility method to compare another list of terms with the LHS terms of this
	 * constraint while ignoring the ordering.
	 * 
	 * @param otherTerms List of other terms.
	 * @return True if the given list of other terms is equal to this object's list
	 *         of LHS terms while ignoring the ordering.
	 */
	private boolean lhsTermsEqualIgnoreOrder(final List<Term> otherTerms) {
		if (this.lhsTerms == otherTerms) {
			return true;
		}

		if (otherTerms == null) {
			return false;
		}

		if (this.lhsTerms.size() != otherTerms.size()) {
			return false;
		}

		return this.lhsTerms.containsAll(otherTerms) && otherTerms.containsAll(this.lhsTerms);
	}

}
