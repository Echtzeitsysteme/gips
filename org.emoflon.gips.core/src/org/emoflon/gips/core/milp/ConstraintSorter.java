package org.emoflon.gips.core.milp;

import java.util.List;

import org.emoflon.gips.core.GipsConstraint;

public interface ConstraintSorter {

	/**
	 * Performs a sorting operation right before the constraints are added to the
	 * solver. The order of the elements in the returned list may affect the order
	 * in which the constraints appear in the output (this depends on the solver).
	 * <p>
	 * <b>Note</b>: This method receives a list of constraints. This list will never
	 * be null or contain any null elements. The received list is a copy and can be
	 * safely sorted and returned
	 * 
	 * @param constraints a list of constraints, not null, contains no null elements
	 * 
	 * @return a sorted list of constraints, not null, contains no null elements
	 */
	List<GipsConstraint<?, ?, ?>> sort(List<GipsConstraint<?, ?, ?>> constraints);

}
