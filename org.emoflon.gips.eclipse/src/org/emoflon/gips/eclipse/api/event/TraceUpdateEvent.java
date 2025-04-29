package org.emoflon.gips.eclipse.api.event;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.emoflon.gips.eclipse.api.ITraceContext;

public final class TraceUpdateEvent {

	public static enum EventType {
		TRACE, VALUES
	}

	private final ITraceContext context;
	private final EventType eventType;
	private final Collection<String> modelIds;

	public TraceUpdateEvent(ITraceContext context, EventType eventType, Collection<String> modelIds) {
		this.context = Objects.requireNonNull(context, "context");
		this.eventType = Objects.requireNonNull(eventType, "eventType");
		this.modelIds = Collections.unmodifiableCollection(Objects.requireNonNull(modelIds, "modelIds"));
	}

	public ITraceContext getContext() {
		return context;
	}

	public Collection<String> getModelIds() {
		return modelIds;
	}

	public EventType getEventType() {
		return eventType;
	}

}
