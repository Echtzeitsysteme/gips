package org.emoflon.gips.core.ilp;

public class ILPBinaryVariable implements ILPVariable<Integer> {

	final protected String name;
	protected boolean value;
	protected int upperBound = 1;
	protected int lowerBound = 0;

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

	@Override
	public Integer getUpperBound() {
		return upperBound;
	}

	@Override
	public void setUpperBound(Integer bound) {
		upperBound = bound;
	}

	@Override
	public Integer getLowerBound() {
		return lowerBound;
	}

	@Override
	public void setLowerBound(Integer bound) {
		lowerBound = bound;
	}

}
