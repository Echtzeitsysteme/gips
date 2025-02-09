package org.emoflon.gips.debugger.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.emoflon.gips.debugger.api.event.ITraceUpdateListener;

public interface ITraceViewModel {

	public static class RootNode implements ITraceViewModel {

		public final Map<String, ContextNode> childs = new HashMap<>();

	}

	public static class ContextNode implements ITraceViewModel, Comparable<ContextNode> {

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

	public static class ModelNode implements ITraceViewModel {

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

	public static class LinkModelNode implements ITraceViewModel {

		public static enum Direction {
			FORWARD, BACKWARD
		}

		public final ModelNode parent;
		public final String modelId;
		public final Direction direction;

		LinkModelNode(ModelNode parent, String modelId, Direction direction) {
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

}