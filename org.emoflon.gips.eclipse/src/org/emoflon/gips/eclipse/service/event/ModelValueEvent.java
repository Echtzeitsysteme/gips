package org.emoflon.gips.eclipse.service.event;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.emoflon.gips.eclipse.api.ITraceContext;
import org.emoflon.gips.eclipse.service.ProjectContext;

public final class ModelValueEvent {

	private final ProjectContext context;
	private final Collection<String> modelIds;

	/**
	 * @param context  the context in which the event was raised, may not be null
	 * @param modelIds the models that triggered the event, may not be null
	 */
	public ModelValueEvent(ProjectContext context, Collection<String> modelIds) {
		this.context = Objects.requireNonNull(context, "context");
		this.modelIds = Collections.unmodifiableCollection(Objects.requireNonNull(modelIds, "modelIds"));
	}

	/**
	 * Returns the context in which the event was raised
	 * 
	 * @return a {@link ITraceContext}, never null
	 */
	public ProjectContext getContext() {
		return context;
	}

	/**
	 * Returns the models that triggered the event
	 * 
	 * @return a collection of model ids, never null
	 */
	public Collection<String> getModelIds() {
		return modelIds;
	}

}
