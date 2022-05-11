package org.emoflon.gips.core.validation;

public record GipsValidationEvent(GipsValidationEventType eventType, Class<?> origin, String cause) {

}
