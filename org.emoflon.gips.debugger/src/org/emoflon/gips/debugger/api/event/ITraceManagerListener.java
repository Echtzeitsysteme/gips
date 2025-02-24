package org.emoflon.gips.debugger.api.event;

import java.util.EventListener;

@FunctionalInterface
public interface ITraceManagerListener extends EventListener {

	void contextChanged(TraceManagerEvent event);

}
