package org.emoflon.gips.debugger.api;

import java.util.Objects;

public class TraceModelNotFound extends Exception {

	private static final long serialVersionUID = -7965914712842445716L;

	private final String modelId;

	public TraceModelNotFound(String modelId, String message) {
		super(message);
		this.modelId = Objects.requireNonNull(modelId);
	}

	public TraceModelNotFound(String modelId) {
		this(modelId, "Model '" + modelId + "' not found.");
	}

	public String getModelId() {
		return modelId;
	}
}
