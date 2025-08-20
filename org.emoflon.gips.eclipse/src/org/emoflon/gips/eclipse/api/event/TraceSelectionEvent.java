package org.emoflon.gips.eclipse.api.event;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.emoflon.gips.eclipse.api.ITraceContext;
import org.emoflon.gips.eclipse.api.ITraceSelection;

public final class TraceSelectionEvent implements ITraceSelection {

	private final ITraceContext context;
	private final String modelId;
	private final Collection<String> elementIds;

	/**
	 * @param context    the context in which the event was raised, may not be null
	 * @param modelId    the model that triggered the event, may not be null
	 * @param elementIds the elements that have been selected on {@code modelId}.
	 *                   Will be stored as an unmodifiable collection and may not be
	 *                   null
	 */
	public TraceSelectionEvent(ITraceContext context, String modelId, Collection<String> elementIds) {
		this.context = Objects.requireNonNull(context, "context");
		this.modelId = Objects.requireNonNull(modelId, "modelId");
		this.elementIds = Collections.unmodifiableCollection(Objects.requireNonNull(elementIds, "elementIds"));
	}

	@Override
	public ITraceContext getContext() {
		return context;
	}

	@Override
	public String getModelId() {
		return modelId;
	}

	@Override
	public Collection<String> getElementIds() {
		return elementIds;
	}

}
