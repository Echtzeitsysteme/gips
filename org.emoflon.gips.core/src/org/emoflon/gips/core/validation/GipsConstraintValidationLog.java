package org.emoflon.gips.core.validation;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GipsConstraintValidationLog {
	protected List<GipsValidationEvent> events = Collections.synchronizedList(new LinkedList<>());
	protected boolean containsInvalidConstraints = false;

	public synchronized void addValidatorEvent(final GipsValidationEventType type, final Class<?> origin,
			final String cause) {
		events.add(new GipsValidationEvent(type, origin, cause));
		containsInvalidConstraints |= switch (type) {
		case CONSTRAINT_INFO -> {
			yield false;
		}
		case CONST_CONSTRAINT_VIOLATION -> {
			yield true;
		}
		case VAR_CONSTRAINT_VIOLATION -> {
			yield true;
		}
		default -> {
			throw new IllegalArgumentException("Unknown validator event type.");
		}
		};
	}

	public boolean isNotValid() {
		return containsInvalidConstraints;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Constraint builder validation log:\n");
		for (GipsValidationEvent event : events) {
			sb.append(event);
			sb.append("\n");
		}
		return super.toString();
	}

}
