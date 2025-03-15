package org.emoflon.gips.eclipse.api.event;

@FunctionalInterface
public interface ITraceUpdateListener {
	public void updatedModels(TraceUpdateEvent event);
}
