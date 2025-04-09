package org.emoflon.gips.eclipse.view.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import org.emoflon.gips.eclipse.TracePlugin;
import org.emoflon.gips.eclipse.api.event.ITraceContextListener;
import org.emoflon.gips.eclipse.api.event.TraceContextEvent;
import org.emoflon.gips.eclipse.service.ContextManager;

public class RootNode implements INode {

	private final ITraceContextListener contextListener = this::onContextUpdate;

	private final Consumer<INode> refreshNode;
	private final Map<String, ContextNode> childs = new HashMap<>();

	public RootNode(Consumer<INode> refreshNode) {
		this.refreshNode = Objects.requireNonNull(refreshNode, "refreshNode");

		TracePlugin.getInstance().getContextManager().addListener(contextListener);
	}

	private void onContextUpdate(TraceContextEvent event) {
		refreshNode(this);
	}

	@Override
	public INode getParent() {
		return null;
	}

	protected void refreshNode(INode node) {
		refreshNode.accept(node);
	}

	@Override
	public boolean hasChilds() {
		return !TracePlugin.getInstance().getContextManager().getAvailableContextIds().isEmpty();
	}

	public void updateChilds() {
		ContextManager contextManager = TracePlugin.getInstance().getContextManager();
		Set<String> availableIds = contextManager.getAvailableContextIds();
		List<String> removed = childs.keySet().stream().filter(id -> !availableIds.contains(id)).toList();

		for (String id : removed)
			childs.get(id).dispose();
		childs.keySet().removeAll(removed);

		for (String id : availableIds)
			childs.computeIfAbsent(id, key -> new ContextNode(this, key));
	}

	@Override
	public ContextNode[] getChilds() {
		updateChilds();
		return childs.values().stream().sorted().toArray(s -> new ContextNode[s]);
	}

	@Override
	public void dispose() {
		TracePlugin.getInstance().getContextManager().removeListener(contextListener);
		for (INode child : childs.values())
			child.dispose();
		childs.clear();
	}

}