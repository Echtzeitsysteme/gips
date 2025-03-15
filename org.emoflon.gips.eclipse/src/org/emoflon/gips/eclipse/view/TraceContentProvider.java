package org.emoflon.gips.eclipse.view;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.emoflon.gips.eclipse.api.ITraceContext;
import org.emoflon.gips.eclipse.api.ITraceManager;
import org.emoflon.gips.eclipse.view.model.ContextNode;
import org.emoflon.gips.eclipse.view.model.INode;
import org.emoflon.gips.eclipse.view.model.LinkModelNode;
import org.emoflon.gips.eclipse.view.model.ModelNode;
import org.emoflon.gips.eclipse.view.model.RootNode;

final class TraceContentProvider implements ITreeContentProvider {

	private final Consumer<INode> refreshNode;

	public TraceContentProvider(Consumer<INode> refreshNode) {
		this.refreshNode = Objects.requireNonNull(refreshNode, "refreshNode");
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof RootNode rNode) {
			ITraceManager trace = ITraceManager.getInstance();
			var availableContextIds = trace.getAvailableContextIds();

			rNode.childs.keySet().removeIf(trackedContextId -> !availableContextIds.contains(trackedContextId));

			for (var contextId : availableContextIds) {
				rNode.childs.computeIfAbsent(contextId, id -> {
					var cNode = new ContextNode(rNode, id);
					cNode.listener = event -> refreshNode.accept(cNode);
					trace.getContext(id).addListener(cNode.listener);
					return cNode;
				});
			}

			return rNode.childs.values().stream().sorted().toArray();
		}

		if (parentElement instanceof ContextNode cNode) {
			ITraceContext context = ITraceManager.getInstance().getContext(cNode.getContextId());
			return context.getAllModels().stream().sorted(String.CASE_INSENSITIVE_ORDER)
					.map(id -> new ModelNode(cNode, id)).toArray();
		}

		if (parentElement instanceof ModelNode mNode) {
			ITraceContext context = ITraceManager.getInstance().getContext(mNode.getContextId());

			var incoming = context.getSourceModels(mNode.modelId).stream().sorted(String.CASE_INSENSITIVE_ORDER)
					.map(id -> new LinkModelNode(mNode, id, LinkModelNode.Direction.BACKWARD));
			var outgoing = context.getTargetModels(mNode.modelId).stream().sorted(String.CASE_INSENSITIVE_ORDER)
					.map(id -> new LinkModelNode(mNode, id, LinkModelNode.Direction.FORWARD));

			return Stream.concat(incoming, outgoing).toArray();
		}

		return null;
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof RootNode rNode)
			return null;

		if (element instanceof ContextNode cNode)
			return cNode.parent;

		if (element instanceof ModelNode mNode)
			return mNode.parent;

		if (element instanceof LinkModelNode lmNode)
			return lmNode.parent;

		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof RootNode rNode)
			return !ITraceManager.getInstance().getAvailableContextIds().isEmpty();

		if (element instanceof ContextNode cNode) {
			ITraceContext context = ITraceManager.getInstance().getContext(cNode.getContextId());
			return !context.getAllModels().isEmpty();
		}

		if (element instanceof ModelNode mNode) {
			ITraceContext context = ITraceManager.getInstance().getContext(mNode.getContextId());
			return !context.getSourceModels(mNode.modelId).isEmpty()
					|| !context.getTargetModels(mNode.modelId).isEmpty();
		}

		return false;
	}
}