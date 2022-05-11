package org.emoflon.gips.core.validation;

public enum GipsValidationEventType {
	CONST_CONSTRAINT_VIOLATION("Constant_Constraint_Violation"),
	VAR_CONSTRAINT_VIOLATION("Variable_Constraint_Violation"), CONSTRAINT_INFO("Constraint_Info");

	private final String name;

	private GipsValidationEventType(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Constraint validator event type: <" + name + ">";
	}
}
