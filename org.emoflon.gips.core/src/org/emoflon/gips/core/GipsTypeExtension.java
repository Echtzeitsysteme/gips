package org.emoflon.gips.core;

import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.core.milp.model.Variable;

public abstract class GipsTypeExtension<CONTEXT extends EObject> {

	final private CONTEXT context;
	final private Map<String, Variable<?>> milpVariables;

	public GipsTypeExtension(CONTEXT context, Map<String, Variable<?>> milpVariables) {
		this.context = Objects.requireNonNull(context);
		this.milpVariables = Objects.requireNonNull(milpVariables);
	}

	public CONTEXT getContext() {
		return context;
	}

	public Map<String, Variable<?>> getVariables() {
		return milpVariables;
	}

	@SuppressWarnings("unchecked")
	public <T extends Variable<?>> T getVariable(String name) {
		return (T) milpVariables.get(name);
	}

	public abstract void setVariableValue(final String valName, final double value);

}
