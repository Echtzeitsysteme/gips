package org.emoflon.gips.core.milp;

import java.util.List;

import org.emoflon.gips.core.GipsConstraint;

public interface ConstraintSorter {

	List<GipsConstraint<?, ?, ?>> sort(List<GipsConstraint<?, ?, ?>> constraints);

}
