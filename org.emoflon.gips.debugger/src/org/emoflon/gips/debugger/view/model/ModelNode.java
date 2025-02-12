package org.emoflon.gips.debugger.view.model;

import java.util.Objects;

public class ModelNode implements INode {

	public final ContextNode parent;
	public final String modelId;

	public ModelNode(ContextNode parent, String modelId) {
		this.parent = Objects.requireNonNull(parent, "parent");
		this.modelId = Objects.requireNonNull(modelId, "modelId");
	}

	@Override
	public String toString() {
		return modelId;
	}

	public String getContextId() {
		return parent.getContextId();
	}
}