package org.emoflon.gips.debugger.api;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.debugger.trace.EcoreWriter;
import org.emoflon.gips.debugger.trace.TraceGraph;
import org.emoflon.gips.debugger.trace.TraceMap;
import org.emoflon.gips.debugger.trace.TraceModelLink;
import org.emoflon.gips.debugger.trace.TransformGraph2Ecore;
import org.emoflon.gips.debugger.trace.resolver.ResolveEcore2Id;
import org.emoflon.gips.debugger.trace.resolver.ResolveIdentity2Id;

//TODO: move this to org.emoflon.gips.core
//TODO: export trace dependency
//TODO: debugger shouldn't be needed as a dependency to run gips
public class Gips2IlpTraceHelper {

	private final TraceMap<EObject, String> gips2intern = new TraceMap<>();
	private final TraceMap<String, String> intern2lp = new TraceMap<>();

	@Deprecated
	private Path saveLocation;
	private String gipsModelId;
	private String ilpModelId;

	public Gips2IlpTraceHelper() {

	}

	public TraceMap<EObject, String> getMapping() {
		return gips2intern;
	}

	public void gips2intern(final EObject src, final String dst) {
		gips2intern.map(src, dst);
	}

	public void finalizeTrace() {
		var mapping = TraceMap.normalize(gips2intern, ResolveEcore2Id.INSTANCE, ResolveIdentity2Id.INSTANCE);
		var link = new TraceModelLink(gipsModelId, ilpModelId, mapping);

		try {
			ITraceRemoteService service = (ITraceRemoteService) LocateRegistry.getRegistry(2842)
					.lookup("ITraceRemoteService");

			// this should, in theory, be the eclipse project name
			var workingDirectoryName = Paths.get("").toAbsolutePath().getFileName().toString();
			service.updateTraceModel(workingDirectoryName, link);
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private TraceGraph buildGraph() {
		var graph = new TraceGraph();
		var mapping = TraceMap.normalize(gips2intern, ResolveEcore2Id.INSTANCE, ResolveIdentity2Id.INSTANCE);
		var link = new TraceModelLink(gipsModelId, ilpModelId, mapping);
		graph.addOrReplaceTraceLink(link);
		return graph;
	}

	public void saveGraph(Path filePath) {
		var graph = buildGraph();

		var root = TransformGraph2Ecore.buildModelFromGraph(graph);
		var uri = URI.createFileURI(filePath.toAbsolutePath().toString());
		EcoreWriter.saveModel(root, uri);
	}

	@Deprecated
	public void setOutputLocation(Path filePath) {
		this.saveLocation = filePath;
	}

	public void setGipsModelId(String id) {
		this.gipsModelId = id;
	}

	public void setIlpModelId(String id) {
		this.ilpModelId = id;
	}

	public void computeGipsModelId(URI modelUri) {
		String modelId = modelUri.trimFileExtension().lastSegment();
		setGipsModelId(modelId);
	}

	public void computeLpModelId(String lpPath) {
		Path path = Path.of(lpPath);
		String modelId = path.getFileName().toString();
		setIlpModelId(modelId);
	}

}
