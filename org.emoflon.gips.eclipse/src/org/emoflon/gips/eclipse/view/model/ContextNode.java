package org.emoflon.gips.eclipse.view.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.emoflon.gips.eclipse.TracePlugin;
import org.emoflon.gips.eclipse.api.ITraceManager;
import org.emoflon.gips.eclipse.api.event.ITraceUpdateListener;
import org.emoflon.gips.eclipse.api.event.TraceUpdateEvent;
import org.emoflon.gips.eclipse.api.event.TraceUpdateEvent.EventType;
import org.emoflon.gips.eclipse.service.ContextManager;
import org.emoflon.gips.eclipse.service.ProjectContext;

public class ContextNode implements INode, Comparable<ContextNode> {

	private final ITraceUpdateListener traceUpdateListener = this::onTraceUpdate;

	private final RootNode parent;
	private final String contextId;

	private final Map<String, ModelNode> childs = new HashMap<>();

	public ContextNode(RootNode parent, String contextId) {
		this.parent = Objects.requireNonNull(parent, "parent");
		this.contextId = Objects.requireNonNull(contextId, "contextId");

		ProjectContext context = TracePlugin.getInstance().getContextManager().getContext(getContextId());
		context.addListener(traceUpdateListener);
	}

	private void onTraceUpdate(TraceUpdateEvent event) {
		boolean refreshNode = false;

		if (event.getEventType() == EventType.TRACE) {
			refreshNode = true;
		} else if (event.getEventType() == EventType.VALUES) {
			if (childs.keySet().containsAll(event.getModelIds())) {
				boolean needsRefresh = event.getModelIds().stream().anyMatch(id -> !childs.get(id).hasChilds());
				if (needsRefresh) {
					refreshNode = true;
				} else {
					for (var modelId : event.getModelIds()) {
						ModelNode child = childs.get(modelId);
						getParent().refreshNode(child);
					}
				}
			} else {
				refreshNode = true;
			}
		}

		if (refreshNode)
			getParent().refreshNode(this);
	}

	@Override
	public RootNode getParent() {
		return parent;
	}

	public String getContextId() {
		return contextId;
	}

	private Set<String> getAllModels() {
		return ITraceManager.getInstance().getContext(getContextId()).getAllModels();
	}

	@Override
	public boolean hasChilds() {
		return !getAllModels().isEmpty();
	}

	@Override
	public INode[] getChilds() {
		updateChilds();
		return childs.values().stream().sorted().toArray(s -> new ModelNode[s]);
	}

	private void updateChilds() {
		Set<String> availableIds = getAllModels();
		List<String> removed = childs.keySet().stream().filter(id -> !availableIds.contains(id)).toList();

		for (String id : removed)
			childs.get(id).dispose();
		childs.keySet().removeAll(removed);

		for (String id : availableIds)
			childs.computeIfAbsent(id, key -> new ModelNode(this, key));
	}

	@Override
	public String toString() {
		return contextId;
	}

	@Override
	public int compareTo(ContextNode other) {
		return String.CASE_INSENSITIVE_ORDER.compare(this.getContextId(), other.getContextId());
	}

	@Override
	public void dispose() {
		ContextManager contextManager = TracePlugin.getInstance().getContextManager();
		if (contextManager.doesContextExist(getContextId())) {
			try {
				ProjectContext context = contextManager.getContext(getContextId());
				context.removeListener(traceUpdateListener);
			} catch (Exception e) {

			}
		}

		for (INode child : childs.values())
			child.dispose();
		childs.clear();
	}

}