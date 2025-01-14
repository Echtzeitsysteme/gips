package org.emoflon.gips.debugger.trace;

import java.util.Objects;

public class TraceModelReference {

	private final String modelId;
	private String uri;

	public TraceModelReference(final String modelId) {
		super();
		this.modelId = Objects.requireNonNull(modelId);
	}

	public String getModelId() {
		return modelId;
	}

	public String getModelURI() {
		return uri;
	}

	public void setModelURI(String uri) {
		this.uri = uri;
	}

}
