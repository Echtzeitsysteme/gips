package org.emoflon.gips.debugger.trace;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Helper class for converting {@link TraceModelPackage Ecore trace models} to
 * {@link TraceGraph TraceGraphs}
 * 
 * @see TransformGraph2Ecore
 */
public final class TransformEcore2Graph {
	private TransformEcore2Graph() {

	}

	public static TraceGraph buildGraphFromModel(Root root) {
		var graph = new TraceGraph();
		return addModelToGraph(root, graph);
	}

	public static TraceGraph addModelToGraph(Root root, TraceGraph graph) {
		for (var srcRef : root.getModels()) {
			var links = createTraceLinks(srcRef);
			for (var link : links) {
				graph.addOrReplaceTraceLink(link);
			}
		}
		return graph;
	}

	public static Collection<TraceModelLink> createTraceLinks(ModelReference ref) {
		var list = new ArrayList<TraceModelLink>(ref.getTransformsTo().size());
		for (var modelTransformation : ref.getTransformsTo()) {
			var link = createTraceLink(modelTransformation);
			list.add(link);
		}
		return list;
	}

	public static TraceModelLink createTraceLink(ModelTransformation transformation) {
		var nodeMapping = createTraceMap(transformation);
		return new TraceModelLink(transformation.getSource().getModelId(), transformation.getTarget().getModelId(),
				nodeMapping);
	}

	public static TraceMap<String, String> createTraceMap(ModelTransformation transformation) {
		var nodeMapping = new TraceMap<String, String>();
		for (var nodeTransformation : transformation.getTransformations()) {
			var srcNodeId = nodeTransformation.getSource().getElementId();
			var dstNodeId = nodeTransformation.getTarget().getElementId();
			nodeMapping.map(srcNodeId, dstNodeId);
		}
		return nodeMapping;
	}

}
