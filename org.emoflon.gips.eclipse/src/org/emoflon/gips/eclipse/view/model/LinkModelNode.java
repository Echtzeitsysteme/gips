package org.emoflon.gips.eclipse.view.model;

import java.util.Objects;

public class LinkModelNode implements INode {

	public static enum Direction {
		FORWARD, BACKWARD
	}

	private final ModelNode parent;
	private final String modelId;
	private final Direction direction;

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

	@Override
	public ModelNode getParent() {
		return parent;
	}

	public String getModelId() {
		return modelId;
	}

	public Direction getDirection() {
		return direction;
	}

	@Override
	public boolean hasChilds() {
		return false;
	}

	@Override
	public INode[] getChilds() {
		return null;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
}