package org.emoflon.gips.debugger.api;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.debugger.trace.EcoreWriter;
import org.emoflon.gips.debugger.trace.TraceGraph;
import org.emoflon.gips.debugger.trace.TraceMap;
import org.emoflon.gips.debugger.trace.TraceModelLink;
import org.emoflon.gips.debugger.trace.TransformGraph2Ecore;
import org.emoflon.gips.debugger.trace.resolver.ResolveEcore2Id;
import org.emoflon.gips.debugger.trace.resolver.ResolveIdentity2Id;

public class Intermediate2IlpTracer {

	private final TraceMap<EObject, String> mappings = new TraceMap<>();

	private int rmiServicePort = 2842;

	private String intermediateModelId;
	private String lpModelId;
	private boolean tracingEnabled;

	public Intermediate2IlpTracer() {

	}

	public TraceMap<EObject, String> getMapping() {
		return mappings;
	}

	public void map(final EObject src, final String dst) {
		mappings.map(src, dst);
	}

	public void setRMIPort(int port) {
		this.rmiServicePort = port;
	}

	public void postTraceToRMIService() {
		Path workingDirectory = Paths.get("").toAbsolutePath();
		// this should, in theory, be the eclipse project name
		String contextId = workingDirectory.getFileName().toString();

		TraceMap<String, String> mapping = TraceMap.normalize(mappings, ResolveEcore2Id.INSTANCE,
				ResolveIdentity2Id.INSTANCE);
		TraceModelLink link = new TraceModelLink(intermediateModelId, lpModelId, mapping);

		try {
			ITraceRemoteService service = (ITraceRemoteService) LocateRegistry.getRegistry(rmiServicePort)
					.lookup(ITraceRemoteService.SERVICE_NAME);
			service.updateTraceModel(contextId, link);
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void computeGipsModelId(URI modelUri) {
		Path intermediatePath;
		if (modelUri.isPlatform())
			intermediatePath = Path.of(modelUri.toPlatformString(true));
		else
			intermediatePath = Path.of(modelUri.toFileString());

		intermediateModelId = computeModelIdFromPath(intermediatePath);
	}

	public void computeLpModelId(String lpPath) {
		lpModelId = computeModelIdFromPath(Path.of(lpPath));
	}

	public void enableTracing(boolean enableTracing) {
		this.tracingEnabled = enableTracing;
	}

	public boolean isTracingEnabled() {
		return this.tracingEnabled;
	}

	public void saveTraceAsGraph(Path filePath) {
		var graph = buildGraph();
		var root = TransformGraph2Ecore.buildModelFromGraph(graph);
		var uri = URI.createFileURI(filePath.toAbsolutePath().toString());
		EcoreWriter.saveModel(root, uri);
	}

	private String computeModelIdFromPath(Path modelPath) {
		return computeModelIdFromPath(Paths.get("").toAbsolutePath(), modelPath);
	}

	private String computeModelIdFromPath(Path root, Path modelPath) {
		if (!modelPath.isAbsolute())
			modelPath = root.resolve(modelPath).normalize();

		Path relativePath = root.relativize(modelPath); // we use the relative path as id
		String id = StreamSupport.stream(relativePath.spliterator(), false).map(Path::toString)
				.collect(Collectors.joining("/")); // use '/' like IPath.toString
		return id;
	}

	private TraceGraph buildGraph() {
		var graph = new TraceGraph();
		var mapping = TraceMap.normalize(mappings, ResolveEcore2Id.INSTANCE, ResolveIdentity2Id.INSTANCE);
		var link = new TraceModelLink(intermediateModelId, lpModelId, mapping);
		graph.addOrReplaceTraceLink(link);
		return graph;
	}

}
