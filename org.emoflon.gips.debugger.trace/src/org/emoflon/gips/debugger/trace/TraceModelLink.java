package org.emoflon.gips.debugger.trace;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 
 */
public class TraceModelLink implements Serializable {

	private static final long serialVersionUID = 4324514417881650135L;
	public final String srcModelId;
	public final String dstModelId;
	public final TraceMap<String, String> mappings;

	public TraceModelLink(String srcModelId, String dstModelId, TraceMap<String, String> mappings) {
		this.srcModelId = Objects.requireNonNull(srcModelId, "srcModelId");
		this.dstModelId = Objects.requireNonNull(dstModelId, "dstModelId");
		this.mappings = Objects.requireNonNull(mappings, "mappings");
	}

	public String getSourceModel() {
		return srcModelId;
	}

	public String getTargetModel() {
		return dstModelId;
	}

	public Set<String> getSourceNodeIds() {
		return mappings.getAllSources();
	}

	public Set<String> getTargetNodeIds() {
		return mappings.getAllTargets();
	}

	public Set<String> resolveFromSrcToDst(String nodeId) {
		return resolveFromSrcToDst(Collections.singleton(nodeId));
	}

	public Set<String> resolveFromDstToSrc(String nodeId) {
		return resolveFromDstToSrc(Collections.singleton(nodeId));
	}

	public Set<String> resolveFromSrcToDst(Collection<String> nodeIds) {
		if (nodeIds.isEmpty()) {
			return Collections.emptySet();
		}

		if (nodeIds.size() == 1) {
			var nodeId = nodeIds.iterator().next();
			return mappings.getTargets(nodeId);
		} else {
			var createdNodes = new HashSet<String>();
			for (var nodeId : nodeIds) {
				var creates = mappings.getTargets(nodeId);
				createdNodes.addAll(creates);
			}
			return createdNodes;
		}
	}

	public Set<String> resolveFromDstToSrc(Collection<String> nodeIds) {
		if (nodeIds.isEmpty()) {
			return Collections.emptySet();
		}

		if (nodeIds.size() == 1) {
			var nodeId = nodeIds.iterator().next();
			return mappings.getSources(nodeId);
		} else {
			var createdBy = new HashSet<String>();
			for (var nodeId : nodeIds) {
				var tmp = mappings.getSources(nodeId);
				createdBy.addAll(tmp);
			}
			return createdBy;
		}
	}

}
