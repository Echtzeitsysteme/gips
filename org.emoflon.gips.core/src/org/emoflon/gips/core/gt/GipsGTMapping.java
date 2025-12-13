package org.emoflon.gips.core.gt;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import org.emoflon.gips.core.GipsMapping;
import org.emoflon.gips.core.milp.model.Variable;
import org.emoflon.ibex.gt.api.GraphTransformationMatch;
import org.emoflon.ibex.gt.api.GraphTransformationPattern;

public abstract class GipsGTMapping<M extends GraphTransformationMatch<M, P>, P extends GraphTransformationPattern<M, P>>
		extends GipsMapping {

	final protected M match;

	protected GipsGTMapping(final String milpVariable, final boolean hasBinaryVariable, final M match) {
		super(milpVariable, hasBinaryVariable);
		this.match = match;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, match);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GipsGTMapping<?, ?> other) {
			if (name.equals(other.name) && match.equals(other.match)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public M getMatch() {
		return match;
	}

	public abstract boolean hasFreeVariables();

	public abstract boolean hasBoundVariables();

	public abstract Collection<String> getFreeVariableNames();

	public abstract Map<String, Variable<?>> getFreeVariables();

	public abstract Collection<String> getBoundVariableNames();

	public abstract Map<String, Variable<?>> getBoundVariables();
}
