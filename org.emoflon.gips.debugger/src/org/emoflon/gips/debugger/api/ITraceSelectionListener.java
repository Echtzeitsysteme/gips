package org.emoflon.gips.debugger.api;

import java.util.Collection;

public interface ITraceSelectionListener {
	public void selectedByModel(ITraceContext context, String modelId, Collection<String> elementIds);
}
