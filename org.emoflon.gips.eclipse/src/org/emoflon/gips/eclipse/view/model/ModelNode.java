package org.emoflon.gips.eclipse.view.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.emoflon.gips.eclipse.TracePlugin;
import org.emoflon.gips.eclipse.service.ProjectContext;

public class ModelNode implements INode, Comparable<ModelNode> {

	private final ContextNode parent;
	private final String modelId;

	public ModelNode(ContextNode parent, String modelId) {
		this.parent = Objects.requireNonNull(parent, "parent");
		this.modelId = Objects.requireNonNull(modelId, "modelId");
	}

	@Override
	public String toString() {
		return getModelId();
	}

	public String getContextId() {
		return parent.getContextId();
	}

	@Override
	public ContextNode getParent() {
		return parent;
	}

	public String getModelId() {
		return modelId;
	}

	@Override
	public boolean hasChilds() {
		ProjectContext context = TracePlugin.getInstance().getContextManager().getContext(getContextId());

		boolean hasIncomingTransformations = !context.getSourceModels(getModelId()).isEmpty();
		boolean hasOutgoingTransformations = !context.getTargetModels(getModelId()).isEmpty();
		boolean hasData = !context.getModelValues(getModelId()).isEmpty();

		return hasIncomingTransformations || hasOutgoingTransformations || hasData;
	}

	@Override
	public INode[] getChilds() {
		ProjectContext context = TracePlugin.getInstance().getContextManager().getContext(getContextId());
		Collection<INode> childs = new ArrayList<>();

		var incoming = context.getSourceModels(getModelId()).stream() //
				.sorted(String.CASE_INSENSITIVE_ORDER)
				.map(id -> new LinkModelNode(this, id, LinkModelNode.Direction.BACKWARD)) //
				.toList();
		childs.addAll(incoming);

		var outgoing = context.getTargetModels(getModelId()).stream() //
				.sorted(String.CASE_INSENSITIVE_ORDER) //
				.map(id -> new LinkModelNode(this, id, LinkModelNode.Direction.FORWARD)) //
				.toList();
		childs.addAll(outgoing);

		if (!context.getModelValues(getModelId()).isEmpty()) {
			childs.add(new ValueNode(this));
		}

		return childs.toArray(s -> new INode[s]);
	}

	@Override
	public int compareTo(ModelNode other) {
		return String.CASE_INSENSITIVE_ORDER.compare(this.getModelId(), other.getModelId());
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
}