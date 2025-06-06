package org.emoflon.gips.core.milp.model;

import org.emoflon.gips.build.transformation.GipsConstraintUtils;

public class IntegerVariable implements Variable<Integer> {

	final public String name;
	protected int value;
	protected int upperBound = (int) GipsConstraintUtils.INF;
	protected int lowerBound = -(int) GipsConstraintUtils.INF;

	public IntegerVariable(final String name) {
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
