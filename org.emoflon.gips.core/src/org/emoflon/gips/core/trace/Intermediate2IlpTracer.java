package org.emoflon.gips.core.trace;

import java.nio.file.Path;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.core.GipsConstraint;
import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsLinearFunction;
import org.emoflon.gips.core.GipsMapper;
import org.emoflon.gips.core.milp.SolverConfig;
import org.emoflon.gips.core.milp.model.Constraint;
import org.emoflon.gips.core.milp.model.Term;
import org.emoflon.gips.core.milp.model.Variable;
import org.emoflon.gips.eclipse.api.ILPTraceKeywords;
import org.emoflon.gips.eclipse.api.ITraceRemoteService;
import org.emoflon.gips.eclipse.trace.TraceMap;
import org.emoflon.gips.eclipse.trace.TraceModelLink;
import org.emoflon.gips.eclipse.trace.resolver.ResolveEcore2Id;
import org.emoflon.gips.eclipse.trace.resolver.ResolveIdentity2Id;

public class Intermediate2IlpTracer {

	private final TraceMap<EObject, String> mappings = new TraceMap<>();
	private final SolverConfig config;

	private int rmiServicePort = ITraceRemoteService.DEFAULT_PORT;
	private String intermediateModelId;
	private String lpModelId;

	/**
	 * Needs to be enabled via api call
	 */
	private boolean tracingEnabled;

	public Intermediate2IlpTracer(SolverConfig config) {
		this.config = Objects.requireNonNull(config, "config");
	}

	public String getIntermediateModelId() {
		return intermediateModelId;
	}

	public String getLPModelId() {
		return lpModelId;
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

	private void sendTraceToRMIService() {
		// this should, in theory, be the eclipse project name
		String contextId = getWorkingDirectory().getFileName().toString();

		TraceMap<String, String> mapping = TraceMap.normalize(mappings, ResolveEcore2Id.INSTANCE,
				ResolveIdentity2Id.INSTANCE);
		TraceModelLink link = new TraceModelLink(intermediateModelId, lpModelId, mapping);

		try {
			ITraceRemoteService service = (ITraceRemoteService) LocateRegistry.getRegistry(rmiServicePort)
					.lookup(ITraceRemoteService.SERVICE_NAME);
			service.updateTraceModel(contextId, link);
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			System.err.println("Unable to send trace to IDE. Reason:\n");
			e.printStackTrace();
		}
	}

	private Path getWorkingDirectory() {
		return Path.of("").toAbsolutePath();
	}

	/**
	 * Computes the model id for the intermediate model.
	 * 
	 * @param modelUri to the intermediate xmi file
	 */
	public void computeGipsModelId(URI modelUri) {
		Objects.requireNonNull(modelUri, "Intermediate model URI is null");

		Path intermediatePath;
		if (modelUri.isPlatform())
			intermediatePath = Path.of(modelUri.toPlatformString(true));
		else
			intermediatePath = Path.of(modelUri.toFileString());

		setIntermediateModelId(computeModelIdFromPath(intermediatePath));
	}

	/**
	 * Computes the model id for the lp model.
	 * 
	 * @param path to the lp file
	 */
	private void computeLpModelId(String path) {
		Objects.requireNonNull(path, "lp file path is null");
		setLpModelId(computeModelIdFromPath(Path.of(path)));
	}

	/**
	 * Sets the lp model id
	 * 
	 * @param modelId
	 */
	private void setLpModelId(String modelId) {
		lpModelId = modelId;
	}

	/**
	 * Sets the intermediate model id.
	 * 
	 * @param modelId
	 */
	private void setIntermediateModelId(String modelId) {
		intermediateModelId = modelId;
	}

	public void enableTracing(boolean enableTracing) {
		this.tracingEnabled = enableTracing;
	}

	public boolean isTracingEnabled() {
		return this.tracingEnabled;
	}

	private String computeModelIdFromPath(Path modelPath) {
		return computeModelIdFromPath(getWorkingDirectory(), modelPath);
	}

	private String computeModelIdFromPath(Path root, Path modelPath) {
		if (!modelPath.isAbsolute())
			modelPath = root.resolve(modelPath).normalize();

		Path relativePath = root.relativize(modelPath); // we use the relative path as id
		String id = StreamSupport.stream(relativePath.spliterator(), false).map(Path::toString)
				.collect(Collectors.joining("/")); // use '/' like IPath.toString
		return id;
	}

	public void buildTraceGraphAndSendToIDE(GipsEngine gipsEngine) {
		if (!config.isEnableLpOutput() || config.getLpPath() == null) {
			System.err.println(
					"Unable to create trace for lp file. LP output is disabled or lp path is null. A valid LP output path is required");
			return;
		}

		computeLpModelId(config.getLpPath());

		// try to build a bridge between ILP model and ILP text file

		for (GipsMapper<?> mapper : gipsEngine.getMappers().values()) {
			map(mapper.getMapping(), ILPTraceKeywords.buildElementId(ILPTraceKeywords.TYPE_MAPPING, mapper.getName()));
		}

		for (GipsConstraint<?, ?, ?> constraint : gipsEngine.getConstraints().values()) {
			map(constraint.getIntermediateConstraint(),
					ILPTraceKeywords.buildElementId(ILPTraceKeywords.TYPE_CONSTRAINT, constraint.getName()));
			for (Constraint ilpConstraint : constraint.getConstraints()) {
				for (Term ilpTerm : ilpConstraint.lhsTerms()) {
					map(constraint.getIntermediateConstraint().getExpression(), ILPTraceKeywords
							.buildElementId(ILPTraceKeywords.TYPE_CONSTRAINT_VAR, ilpTerm.variable().getName()));
				}
			}

			for (Variable<?> variables : constraint.getAdditionalVariables()) {
				map(constraint.getIntermediateConstraint().getExpression(),
						ILPTraceKeywords.buildElementId(ILPTraceKeywords.TYPE_CONSTRAINT_VAR, variables.getName()));
			}
		}

		for (GipsLinearFunction<?, ?, ?> function : gipsEngine.getLinearFunctions().values()) {
			map(function.getIntermediateLinearFunction(),
					ILPTraceKeywords.buildElementId(ILPTraceKeywords.TYPE_FUNCTION, function.getName()));
			for (Term term : function.getLinearFunctionFunction().terms()) {
				map(function.getIntermediateLinearFunction(),
						ILPTraceKeywords.buildElementId(ILPTraceKeywords.TYPE_FUNCTION_VAR, term.variable().getName()));
			}
		}

		if (gipsEngine.getObjective() != null) {
			map(gipsEngine.getObjective().getIntermediateObjective(),
					ILPTraceKeywords.buildElementId(ILPTraceKeywords.TYPE_OBJECTIVE, ""));
		}

		sendTraceToRMIService();
	}

}
