package org.emoflon.gips.core;

import org.emoflon.gips.core.ilp.ILPVariable;

public abstract class GipsMapping implements ILPVariable<Integer> {
	final public String ilpVariable;
	protected int value = 0;

	protected GipsMapping(final String ilpVariable) {
		this.ilpVariable = ilpVariable;
	}

	@Override
	public String getName() {
		return ilpVariable;
	}

	@Override
	public Integer getValue() {
		return value;
	}

	@Override
	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		return ilpVariable.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GipsMapping other) {
			return ilpVariable.equals(other.ilpVariable);
		} else {
			return false;
		}
	}
}
