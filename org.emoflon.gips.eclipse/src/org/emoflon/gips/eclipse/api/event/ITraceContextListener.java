package org.emoflon.gips.eclipse.api.event;

import java.util.EventListener;

@FunctionalInterface
public interface ITraceContextListener extends EventListener {

	void contextChanged(TraceContextEvent event);

}
