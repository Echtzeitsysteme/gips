package org.emoflon.gips.eclipse.api.event;

import java.util.Objects;

import org.emoflon.gips.eclipse.api.ITraceManager;

public final class TraceManagerEvent {

	public static enum EventType {
		NEW, DELETED
	}

	private final ITraceManager source;
	private final EventType eventType;
	private final String contextId;

	public TraceManagerEvent(ITraceManager source, EventType eventType, String contextId) {
		this.source = Objects.requireNonNull(source, "source");
		this.eventType = Objects.requireNonNull(eventType, "eventType");
		this.contextId = Objects.requireNonNull(contextId, "contextId");
	}

	public ITraceManager getSource() {
		return source;
	}

	public EventType getEventType() {
		return eventType;
	}

	public String getContextId() {
		return contextId;
	}

}