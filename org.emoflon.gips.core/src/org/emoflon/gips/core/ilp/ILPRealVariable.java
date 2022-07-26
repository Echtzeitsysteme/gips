package org.emoflon.gips.core.ilp;

public class ILPRealVariable implements ILPVariable<Double> {

	final protected String name;
	protected double value;

	public ILPRealVariable(final String name) {
		this.name = name;
		value = 0.0d;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Double getValue() {
		return value;
	}

	@Override
	public void setValue(Double value) {
		this.value = value;
	}

}
