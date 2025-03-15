package org.emoflon.gips.eclipse.view.model;

import java.util.Objects;

import org.emoflon.gips.eclipse.api.event.ITraceUpdateListener;

public class ContextNode implements INode, Comparable<ContextNode> {

	public final RootNode parent;
	public final String contextId;
	public ITraceUpdateListener listener;

	public ContextNode(RootNode parent, String contextId) {
		this.parent = Objects.requireNonNull(parent, "parent");
		this.contextId = Objects.requireNonNull(contextId, "contextId");
	}

	@Override
	public String toString() {
		return contextId;
	}

	@Override
	public int compareTo(ContextNode o) {
		return String.CASE_INSENSITIVE_ORDER.compare(contextId, o.contextId);
	}

	public String getContextId() {
		return contextId;
	}
}