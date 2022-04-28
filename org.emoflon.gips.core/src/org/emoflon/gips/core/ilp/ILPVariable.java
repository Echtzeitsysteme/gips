package org.emoflon.gips.core.ilp;

public interface ILPVariable<T extends Number> {

	public String getName();

	public T getValue();

	public void setValue(final T value);
}
