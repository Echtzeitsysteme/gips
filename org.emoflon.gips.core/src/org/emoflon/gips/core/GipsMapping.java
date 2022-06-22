package org.emoflon.gips.core;

import org.emoflon.gips.core.ilp.ILPBinaryVariable;

public abstract class GipsMapping extends ILPBinaryVariable {

	protected GipsMapping(final String ilpVariable) {
		super(ilpVariable);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GipsMapping other) {
			return name.equals(other.name);
		} else {
			return false;
		}
	}
}
