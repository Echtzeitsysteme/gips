package org.emoflon.gips.debugger.api.event;

@FunctionalInterface
public interface ITraceUpdateListener {
	public void updatedModels(TraceUpdateEvent event);
}
