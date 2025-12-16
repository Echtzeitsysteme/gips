package org.emoflon.gips.core;

import java.util.Collection;
import java.util.Map;

import org.emoflon.gips.core.milp.model.BinaryVariable;
import org.emoflon.gips.core.milp.model.Variable;

public abstract class GipsMapping extends BinaryVariable {

	private final boolean hasBinaryVariable;

	protected GipsMapping(final String milpVariable, final boolean hasBinaryVariable) {
		super(milpVariable);
		this.hasBinaryVariable = hasBinaryVariable;
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

	public boolean hasBinaryVariable() {
		return hasBinaryVariable;
	}

	public abstract boolean hasAdditionalVariables();

	public abstract Collection<String> getAdditionalVariableNames();

	public abstract Map<String, Variable<?>> getAdditionalVariables();

	public abstract void setAdditionalVariableValue(final String valName, final double value);
}
