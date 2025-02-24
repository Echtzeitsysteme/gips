package org.emoflon.gips.debugger.api.event;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.emoflon.gips.debugger.api.ITraceContext;

public final class TraceUpdateEvent {

	private final ITraceContext context;
	private final Collection<String> modelIds;

	public TraceUpdateEvent(ITraceContext context, Collection<String> modelIds) {
		this.context = Objects.requireNonNull(context, "context");
		this.modelIds = Collections.unmodifiableCollection(Objects.requireNonNull(modelIds, "modelIds"));
	}

	public ITraceContext getContext() {
		return context;
	}

	public Collection<String> getModelIds() {
		return modelIds;
	}

}
