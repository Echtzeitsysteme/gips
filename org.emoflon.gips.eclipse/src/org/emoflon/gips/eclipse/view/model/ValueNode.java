package org.emoflon.gips.eclipse.view.model;

import java.util.Objects;

public class ValueNode implements INode {

	private final ModelNode parent;

	public ValueNode(ModelNode parent) {
		this.parent = Objects.requireNonNull(parent, "parent");
	}

	public String getContextId() {
		return getParent().getContextId();
	}

	public String getModelId() {
		return getParent().getModelId();
	}

	@Override
	public ModelNode getParent() {
		return parent;
	}

	@Override
	public String toString() {
		return "Values";
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
