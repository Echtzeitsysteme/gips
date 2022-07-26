package org.emoflon.gips.core.ilp;

public class ILPBinaryVariable implements ILPVariable<Integer> {

	final protected String name;
	protected boolean value;

	public ILPBinaryVariable(final String name) {
		this.name = name;
		value = false;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Integer getValue() {
		return (value) ? 1 : 0;
	}

	@Override
	public void setValue(Integer value) {
		this.value = (value != 0) ? true : false;
	}

}
