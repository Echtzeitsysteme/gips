package org.emoflon.gips.core.milp;

import java.util.Comparator;
import java.util.List;

import org.emoflon.gips.core.GipsConstraint;

public class ConstraintSorterByName implements ConstraintSorter {

	@Override
	public List<GipsConstraint<?, ?, ?>> sort(List<GipsConstraint<?, ?, ?>> constraints) {
		constraints.sort(Comparator.comparing(GipsConstraint::getName));
		return constraints;
	}

}
