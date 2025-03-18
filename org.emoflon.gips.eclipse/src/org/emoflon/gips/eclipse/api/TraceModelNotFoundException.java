package org.emoflon.gips.eclipse.api;

import java.util.Objects;

public final class TraceModelNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7965914712842445716L;

	private final String modelId;

	public TraceModelNotFoundException(String modelId, String message) {
		super(message);
		this.modelId = Objects.requireNonNull(modelId);
	}

	public TraceModelNotFoundException(String modelId) {
		this(modelId, "Model '" + modelId + "' not found.");
	}

	public String getModelId() {
		return modelId;
	}
}
