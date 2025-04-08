package org.emoflon.gips.core.trace;

import java.nio.file.Path;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.util.URI;
import org.emoflon.gips.core.milp.SolverConfig;
import org.emoflon.gips.eclipse.api.IRemoteEclipseService;
import org.emoflon.gips.eclipse.trace.TraceMap;
import org.emoflon.gips.eclipse.trace.TraceModelLink;
import org.emoflon.gips.eclipse.trace.resolver.ResolveEcore2Id;
import org.emoflon.gips.eclipse.trace.resolver.ResolveIdentity2Id;

public class EclipseIntegration {

	private final EclipseIntegrationConfig config = new EclipseIntegrationConfig();
	private final SolverConfig solverConfig;

	private String modelIdIntermediate;
	private String modelIdLp;

	private final Map<String, String> storedILPValues = new HashMap<>();

	public EclipseIntegration(SolverConfig solverConfig) {
		this.solverConfig = Objects.requireNonNull(solverConfig, "solverConfig");
	}

	public EclipseIntegrationConfig getConfig() {
		return config;
	}

	/**
	 * Computes the model id for the intermediate model.
	 * 
	 * @param modelUri to the intermediate xmi file
	 */
	public void computeIntermediateModelId(URI modelUri) {
		Path modelPath;
		if (modelUri.isPlatform())
			modelPath = Path.of(modelUri.toPlatformString(true));
		else
			modelPath = Path.of(modelUri.toFileString());

		String modelId = computeModelIdFromPath(modelPath);
		setIntermediateModelId(modelId);
	}

	/**
	 * Computes the model id for the lp model.
	 * 
	 * @param path to the lp file
	 */
	private void computeLpModelId(String path) {
		Path modelPath = Path.of(path);
		String modelId = computeModelIdFromPath(modelPath);
		setLpModelId(modelId);
	}

	/**
	 * Sets the lp model id
	 * 
	 * @param modelId
	 */
	private void setLpModelId(String modelId) {
		modelIdLp = modelId;
	}

	/**
	 * Sets the intermediate model id.
	 * 
	 * @param modelId
	 */
	private void setIntermediateModelId(String modelId) {
		modelIdIntermediate = modelId;
	}

	private String computeModelIdFromPath(Path modelPath) {
		return computeModelIdFromPath(getWorkingDirectory(), modelPath);
	}

	private String computeModelIdFromPath(Path root, Path modelPath) {
		if (!modelPath.isAbsolute())
			modelPath = root.resolve(modelPath).normalize();

		Path relativePath = root.relativize(modelPath); // we use the root (workspae) relative path as id
		String id = StreamSupport.stream(relativePath.spliterator(), false).map(Path::toString)
				.collect(Collectors.joining("/")); // use '/' like IPath.toString
		return id;
	}

	private boolean isLpPathNotValid() {
		if (!solverConfig.isEnableLpOutput() || solverConfig.getLpPath() == null) {
			System.err.println(
					"Unable to send data to IDE. LP output is disabled or lp path is null. A valid LP output path is required");
			return true;
		}

		return false;
	}

	public void sendTraceToIDE(Intermediate2IlpTracer tracer) {
		if (isLpPathNotValid())
			return;

		computeLpModelId(solverConfig.getLpPath());

		if (!config.isTracingEnabled())
			return;

		TraceMap<String, String> mapping = TraceMap.normalize(tracer.getMapping(), ResolveEcore2Id.INSTANCE,
				ResolveIdentity2Id.INSTANCE);
		TraceModelLink link = new TraceModelLink(getModelIdForIntermediateModel(), getModelIdForLpModel(), mapping);

		try {
			IRemoteEclipseService service = getRemoteService();
			service.updateTraceModel(getContextId(), link);
		} catch (RemoteException e) {
			System.err.println("Unable to send trace to IDE. Reason:\n");
			e.printStackTrace();
		}
	}

	public void sendSolutionValuesToIDE() {
		if (isLpPathNotValid())
			return;

		computeLpModelId(solverConfig.getLpPath());

		if (!config.isSolutionValuesCodeMiningEnabled())
			return;

		try {
			IRemoteEclipseService service = getRemoteService();
			service.updateModelValues(getContextId(), getModelIdForLpModel(), storedILPValues);
		} catch (RemoteException e) {
			System.err.println("Unable to send solution values to IDE. Reason:\n");
			e.printStackTrace();
		}
	}

	public void storeSolutionValues(Map<String, Number> values) {
		storedILPValues.clear();

		if (!config.isSolutionValuesCodeMiningEnabled())
			return;

		for (var entry : values.entrySet()) {
			if (entry.getValue() != null) {
				storedILPValues.put(entry.getKey(), entry.getValue().toString());
			}
		}
	}

	private Path getWorkingDirectory() {
		return Path.of("").toAbsolutePath();
	}

	private String getContextId() {
		// this should, in theory, be the eclipse project name
		String contextId = getWorkingDirectory().getFileName().toString();
		return contextId;
	}

	private IRemoteEclipseService getRemoteService() throws RemoteException {
		try {
			return (IRemoteEclipseService) LocateRegistry.getRegistry(config.getServicePort())
					.lookup(IRemoteEclipseService.SERVICE_NAME);
		} catch (NotBoundException e) {
			throw new RemoteException("Service Name '" + IRemoteEclipseService.SERVICE_NAME + "' Not bound", e);
		}
	}

	public String getModelIdForIntermediateModel() {
		return modelIdIntermediate;
	}

	public String getModelIdForLpModel() {
		return modelIdLp;
	}

}
