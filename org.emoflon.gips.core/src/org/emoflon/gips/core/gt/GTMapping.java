package org.emoflon.gips.core.gt;

import java.util.Objects;

import org.emoflon.gips.core.GipsMapping;
import org.emoflon.ibex.gt.api.GraphTransformationMatch;
import org.emoflon.ibex.gt.api.GraphTransformationPattern;

public abstract class GTMapping<M extends GraphTransformationMatch<M, P>, P extends GraphTransformationPattern<M, P>>
		extends GipsMapping {

	final protected M match;

	protected GTMapping(final String ilpVariable, final M match) {
		super(ilpVariable);
		this.match = match;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ilpVariable, match);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GTMapping<?, ?> other) {
			if (ilpVariable.equals(other.ilpVariable) && match.equals(other.match)) {
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
}
