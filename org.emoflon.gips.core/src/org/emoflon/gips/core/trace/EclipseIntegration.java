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
import org.emoflon.gips.eclipse.trace.resolver.ResolveElement2Id;
import org.emoflon.gips.eclipse.trace.resolver.ResolveIdentity2Id;

public class EclipseIntegration {

	private final EclipseIntegrationConfig config = new EclipseIntegrationConfig();
	private final SolverConfig solverConfig;

	private String modelIdIntermediate;
	private String modelIdLp;
	private String modelIdInput;
	private String modelIdOutput;

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
		String modelId = computeModelIdFromURI(modelUri);
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

	public void computeInputModelId(URI modelUri) {
		String modelId = computeModelIdFromURI(modelUri);
		setInputModelId(modelId);
	}

	public void computeOutputModelId(String path) {
		Path modelPath = Path.of(path);
		String modelId = computeModelIdFromPath(modelPath);
		setOutputModelId(modelId);
	}

	public void computeOutputModelId(URI modelUri) {
		String modelId = computeModelIdFromURI(modelUri);
		setOutputModelId(modelId);
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

	/**
	 * Sets the input model id.
	 * 
	 * @param modelId
	 */
	private void setInputModelId(String modelId) {
		modelIdInput = modelId;
	}

	/**
	 * Sets the output model id.
	 * 
	 * @param modelId
	 */
	public void setOutputModelId(String modelId) {
		modelIdOutput = modelId;
	}

	private String computeModelIdFromURI(URI modelUri) {
		Path modelPath;
		if (modelUri.isPlatform())
			modelPath = Path.of(modelUri.toPlatformString(true));
		else
			modelPath = Path.of(modelUri.toFileString());

		return computeModelIdFromPath(modelPath);
	}

	private String computeModelIdFromPath(Path modelPath) {
		return computeModelIdFromPath(getWorkingDirectory(), modelPath);
	}

	private String computeModelIdFromPath(Path root, Path modelPath) {
		if (!modelPath.isAbsolute())
			modelPath = root.resolve(modelPath).normalize();

		Path relativePath = root.relativize(modelPath); // we use the root (workspace) relative path as id
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

	public void sendLpTraceToIDE(GipsTracer tracer) {
		if (!config.isTracingEnabled())
			return;

		if (isLpPathNotValid())
			return;

		computeLpModelId(solverConfig.getLpPath());

		TraceModelLink linkIntermediate = buildModelLink(getModelIdForIntermediateModel(), getModelIdForLpModel(),
				tracer.getIntermediate2LpMapping(), ResolveEcore2Id.INSTANCE, ResolveIdentity2Id.INSTANCE);

		TraceModelLink linkInput = buildModelLink(getModelIdForInputModel(), getModelIdForLpModel(),
				tracer.getInput2LpMapping(), ResolveEcore2Id.INSTANCE, ResolveIdentity2Id.INSTANCE);

		tracer.resetIntermediate2LpMapping();
		tracer.resetInput2LpMapping();

		updateTraceModel(linkIntermediate, linkInput);
	}

	public void sendOutputTraceToIde(GipsTracer tracer) {
		if (!config.isTracingEnabled())
			return;

		if (isLpPathNotValid())
			return;

		computeLpModelId(solverConfig.getLpPath());

		TraceModelLink linkOutput = buildModelLink(getModelIdForLpModel(), getModelIdForOutputModel(),
				tracer.getLp2OutputMapping(), ResolveIdentity2Id.INSTANCE, ResolveEcore2Id.INSTANCE);

		tracer.resetLp2OutputMapping();

		updateTraceModel(linkOutput);
	}

	private <S, D> TraceModelLink buildModelLink(String srcModelId, String dstModelId, TraceMap<S, D> mapping,
			ResolveElement2Id<? super S> srcResolver, ResolveElement2Id<? super D> dstResolver) {

		if (srcModelId == null || dstModelId == null)
			return null;

		TraceMap<String, String> normalizedMapping = TraceMap.normalize(mapping, srcResolver, dstResolver);

		return new TraceModelLink(srcModelId, dstModelId, normalizedMapping);
	}

	private void updateTraceModel(TraceModelLink... links) {
		if (links == null || links.length == 0)
			return;

		try {
			IRemoteEclipseService service = getRemoteService();
			String contextId = getContextId();

			for (TraceModelLink link : links) {
				if (link != null)
					service.updateTraceModel(contextId, link);
			}

		} catch (RemoteException e) {
			System.err.println("Unable to send trace to IDE. Reason:\n");
			e.printStackTrace();
		}
	}

	public void sendSolutionValuesToIDE() {
		if (!config.isSolutionValuesSynchronizationEnabled())
			return;

		if (isLpPathNotValid())
			return;

		computeLpModelId(solverConfig.getLpPath());

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

	/**
	 * Returns the intermediate model id. This model id may be available after the
	 * initialization stage.
	 * 
	 * @return intermediate model id or null, if not available
	 */
	public String getModelIdForIntermediateModel() {
		return modelIdIntermediate;
	}

	/**
	 * Returns the lp model id. This model id may be available after the ilp build
	 * stage.
	 * 
	 * @return lp model id or null, if not available
	 */
	public String getModelIdForLpModel() {
		return modelIdLp;
	}

	/**
	 * Returns the input model id. This model id may be available after the
	 * initialization stage.
	 * 
	 * @return input model id or null, if not available
	 */
	public String getModelIdForInputModel() {
		return modelIdInput;
	}

	/**
	 * Returns the output model id. This model id may be available after the model
	 * is saved
	 * 
	 * @return input model id or null, if not available
	 */
	public String getModelIdForOutputModel() {
		return modelIdOutput;
	}

}
