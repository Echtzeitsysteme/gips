package org.emoflon.gips.core.ilp;

public class ILPIntegerVariable implements ILPVariable<Integer> {

	final protected String name;
	protected int value;

	public ILPIntegerVariable(final String name) {
		this.name = name;
		value = 0;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Integer getValue() {
		return value;
	}

	@Override
	public void setValue(Integer value) {
		this.value = value;
	}

}
