package org.emoflon.gips.eclipse.api.event;

import java.util.EventListener;

@FunctionalInterface
public interface ITraceManagerListener extends EventListener {

	void contextChanged(TraceManagerEvent event);

}
