package org.emoflon.gips.debugger.connector;

import java.util.Collection;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.ui.IEditorPart;
import org.emoflon.gips.debugger.api.ITraceContext;

public interface IHighlightStrategy<T extends IEditorPart> {

	IStatus computeEditorHighlights(T editor, ITraceContext context, String localModelId, String remoteModelId,
			Collection<String> remoteElementIds, SubMonitor monitor);

	IStatus removeEditorHighlights(T editor, SubMonitor monitor);

}
