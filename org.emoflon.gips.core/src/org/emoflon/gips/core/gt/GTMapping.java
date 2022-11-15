package org.emoflon.gips.core.gt;

import java.util.Objects;

import org.emoflon.gips.core.GipsMapping;
import org.emoflon.ibex.gt.engine.IBeXGTMatch;
import org.emoflon.ibex.gt.engine.IBeXGTPattern;

public abstract class GTMapping<M extends IBeXGTMatch<M, P>, P extends IBeXGTPattern<P, M>> extends GipsMapping {

	final protected M match;

	protected GTMapping(final String ilpVariable, final M match) {
		super(ilpVariable);
		this.match = match;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, match);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GTMapping<?, ?> other) {
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
}
