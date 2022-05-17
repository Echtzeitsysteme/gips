package org.emoflon.gips.core.validation;

public record GipsValidationEvent(GipsValidationEventType eventType, Class<?> origin, String cause) {

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Validation event <");
		sb.append(eventType.name);
		sb.append("> thrown by <");
		sb.append(origin.getSimpleName());
		sb.append(">, reason: [");
		sb.append(cause);
		sb.append("]");
		return sb.toString();
	}
}
