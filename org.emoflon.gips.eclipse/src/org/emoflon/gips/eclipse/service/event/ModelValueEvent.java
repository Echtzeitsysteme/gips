package org.emoflon.gips.eclipse.service.event;

import java.util.Objects;

public final class ModelValueEvent {

	private final String modelId;

	/**
	 * @param modelId the model that triggered the event, may not be null
	 */
	public ModelValueEvent(String modelId) {
		this.modelId = Objects.requireNonNull(modelId, "modelId");
	}

	/**
	 * Returns the model that triggered the event
	 * 
	 * @return a model id, never null
	 */
	public String getModelId() {
		return modelId;
	}

}
