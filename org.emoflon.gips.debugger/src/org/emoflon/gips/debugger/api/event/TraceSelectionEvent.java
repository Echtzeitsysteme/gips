package org.emoflon.gips.debugger.api.event;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.emoflon.gips.debugger.api.ITraceContext;

public final class TraceSelectionEvent {

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

	/**
	 * Returns the context in which the event was raised
	 * 
	 * @return a {@link ITraceContext}, never null
	 */
	public ITraceContext getContext() {
		return context;
	}

	/**
	 * Returns the model that triggered the event
	 * 
	 * @return a model id, never null
	 */
	public String getModelId() {
		return modelId;
	}

	/**
	 * Returns the elements that have been selected on {@link #getElementIds()}
	 * 
	 * @return a collection of ids, never null
	 */
	public Collection<String> getElementIds() {
		return elementIds;
	}

}
