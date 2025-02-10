package org.emoflon.gips.debugger.view.model;

import java.util.Objects;

public class LinkModelNode  {

	public static enum Direction {
		FORWARD, BACKWARD
	}

	public final ModelNode parent;
	public final String modelId;
	public final Direction direction;

	public LinkModelNode(ModelNode parent, String modelId, Direction direction) {
		this.parent = Objects.requireNonNull(parent, "parent");
		this.modelId = Objects.requireNonNull(modelId, "modelId");
		this.direction = direction;
	}

	@Override
	public String toString() {
		return modelId;
	}

	public String getContextId() {
		return parent.getContextId();
	}
}