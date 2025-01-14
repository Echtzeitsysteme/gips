package org.emoflon.gips.debugger.api;

import java.util.Collection;

public interface ITraceUpdateListener {
	public void updatedModels(ITraceContext context, Collection<String> modelIds);
}
