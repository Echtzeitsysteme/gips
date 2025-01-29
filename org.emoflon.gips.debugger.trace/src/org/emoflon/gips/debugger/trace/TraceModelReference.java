package org.emoflon.gips.debugger.trace;

import java.io.Serializable;
import java.util.Objects;

public class TraceModelReference implements Serializable {

	private static final long serialVersionUID = 6457272488769375850L;
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
