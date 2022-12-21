package org.emoflon.gips.core.ilp;

import org.emoflon.gips.build.transformation.GipsConstraintUtils;

public class ILPRealVariable implements ILPVariable<Double> {

	final protected String name;
	protected double value;
	// Increased real variable bound to allow larger ILP models
	protected double upperBound = GipsConstraintUtils.INF * 100;
	protected double lowerBound = -GipsConstraintUtils.INF * 100;

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

	@Override
	public Double getUpperBound() {
		return upperBound;
	}

	@Override
	public void setUpperBound(Double bound) {
		upperBound = bound;
	}

	@Override
	public Double getLowerBound() {
		return lowerBound;
	}

	@Override
	public void setLowerBound(Double bound) {
		lowerBound = bound;
	}
}
