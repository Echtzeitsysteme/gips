package org.emoflon.gips.core.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsObjective;
import org.emoflon.gips.core.TypeIndexer;
import org.emoflon.gips.core.gt.GipsRuleMapper;
import org.emoflon.gips.core.milp.Solver;
import org.emoflon.gips.core.milp.SolverConfig;
import org.emoflon.gips.core.trace.EclipseIntegration;
import org.emoflon.gips.core.trace.GipsTracer;
import org.emoflon.gips.core.validation.GipsConstraintValidationLog;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.ibex.gt.api.GraphTransformationAPI;
import org.emoflon.ibex.gt.api.GraphTransformationApp;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;

public abstract class GipsEngineAPI<EMOFLON_APP extends GraphTransformationApp<EMOFLON_API>, EMOFLON_API extends GraphTransformationAPI>
		extends GipsEngine {

	final protected EMOFLON_APP eMoflonApp;
	protected EMOFLON_API eMoflonAPI;
	protected ResourceSet model;
	protected GipsIntermediateModel gipsModel;
	final protected Map<String, Mapping> name2Mapping = new HashMap<>();
	final protected SolverConfig solverConfig = new SolverConfig();
	protected GipsMapperFactory<EMOFLON_API> mapperFactory;
	protected GipsConstraintFactory<? extends GipsEngineAPI<EMOFLON_APP, EMOFLON_API>, EMOFLON_API> constraintFactory;
	protected GipsLinearFunctionFactory<? extends GipsEngineAPI<EMOFLON_APP, EMOFLON_API>, EMOFLON_API> functionFactory;

	protected GipsEngineAPI(final EMOFLON_APP eMoflonApp) {
		this.eMoflonApp = eMoflonApp;
	}

	public void reinitializeSolver() {
		try {
			solver.reinitialize();
		} catch (final Exception e) {
			throw new InternalError("Solver re-initialization failed: " + e);
		}
	}

	public SolverConfig getSolverConfig() {
		return solverConfig;
	}

	/**
	 * Overwrites the previously configured number of ILP solver threads with the
	 * given parameter's value.
	 * 
	 * @param numberOfThreads Number of ILP solver threads to set.
	 * @deprecated Use {@link #getSolverConfig()} and
	 *             {@link SolverConfig#setThreadCount(int)}
	 */
	@Deprecated
	public void setIlpSolverThreads(final int numberOfThreads) {
		getSolverConfig().setThreadCount(numberOfThreads);
	}

	/**
	 * Overwrite the previously configured time limit with the given parameter's
	 * value.
	 * 
	 * @param newTimeLimit New time limit to set.
	 * @deprecated Use {@link #getSolverConfig()} and
	 *             {@link SolverConfig#setTimeLimit(double)}
	 */
	@Deprecated
	public void setTimeLimit(final double newTimeLimit) {
		getSolverConfig().setTimeLimit(newTimeLimit);
	}

	/**
	 * Overwrite the previously configured callback file path with the given
	 * parameter's value.
	 * 
	 * @param callbackPath New callback file path to set.
	 * @deprecated Use {@link #getSolverConfig()} and
	 *             {@link SolverConfig#setCallbackPath(String)}
	 */
	@Deprecated
	public void setCallbackPath(final String callbackPath) {
		Objects.requireNonNull(callbackPath);
		getSolverConfig().setEnableCallbackPath(true);
		getSolverConfig().setCallbackPath(callbackPath);
	}

	/**
	 * Overwrite the previously configured solver parameter path with the given
	 * parameter's value.
	 * 
	 * @param newParameterPath New parameter path to set.
	 * @deprecated Use {@link #getSolverConfig()} and
	 *             {@link SolverConfig#setParameterPath(String)}
	 */
	@Deprecated
	public void setSolverParameterPath(final String newParameterPath) {
		getSolverConfig().setParameterPath(newParameterPath);
	}

	public EMOFLON_APP getEMoflonApp() {
		return eMoflonApp;
	}

	public EMOFLON_API getEMoflonAPI() {
		if (eMoflonAPI == null) {
			throw new UnsupportedOperationException(
					"EMoflon API does not exist since there are no patterns defined in the ibex model.");
		} else {
			return eMoflonAPI;
		}
	}

	@Override
	public void update() {
		if (eMoflonAPI != null) {
			eMoflonAPI.updateMatches();
		}
	}

	@Override
	public void saveResult() throws IOException {
		Resource r = eMoflonApp.getModel().getResources().get(0);
		r.save(null);

		if (getEclipseIntegration().getConfig().isTracingEnabled()) {
			getEclipseIntegration().computeOutputModelId(r.getURI());
			getEclipseIntegration().sendOutputTraceToIde(getTracer());
		}
	}

	@Override
	public void saveResult(final String path) throws IOException {
		// Create new model for saving
		ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.putAll(eMoflonApp.getModel().getResourceFactoryRegistry().getExtensionToFactoryMap());
		Resource r = rs.createResource(URI.createFileURI(path));
		// Fetch model contents from eMoflon
		r.getContents().addAll(eMoflonApp.getModel().getResources().get(0).getContents());
		r.save(null);

		if (getEclipseIntegration().getConfig().isTracingEnabled()) {
			getEclipseIntegration().computeOutputModelId(path);
			getEclipseIntegration().sendOutputTraceToIde(getTracer());
		}

		// Hand model back to owner
		eMoflonApp.getModel().getResources().get(0).getContents().addAll(r.getContents());
	}

	@Override
	protected void initTypeIndexer() {
		indexer = new TypeIndexer(model, gipsModel);
	}

	/**
	 * Initializes the GIPS engine API with the given model URI.
	 * 
	 * @param modelUri Model URI to load.
	 */
	public abstract void init(final URI modelUri);

	/**
	 * Initializes the GIPS engine API with the given GIPS intermediate model URI
	 * and the model URI.
	 * 
	 * @param gipsModelURI GIPS intermediate model URI to load.
	 * @param modelUri     Model URI to load.
	 */
	public abstract void init(final URI gipsModelURI, final URI modelUri);

	/**
	 * Initializes the GIPS engine API with a given GIPS intermediate model URI, a
	 * model URI, and the IBeX pattern path to avoid using hard-coded paths in IBeX.
	 * 
	 * @param gipsModelURI    GIPS intermediate model URI to load.
	 * @param modelUri        Model URI to load.
	 * @param ibexPatternPath IBeX pattern path to load.
	 */
	public abstract void init(final URI gipsModelURI, final URI modelUri, final URI ibexPatternPath);

	/**
	 * Initializes the API with a given resource set as model.
	 * 
	 * @param model Resource set as model.
	 */
	public abstract void init(final ResourceSet model);

	/**
	 * Initializes the GIPS engine API with the given GIPS intermediate model URI
	 * and a resource set as model.
	 * 
	 * @param gipsModelUri GIPS intermediate model URI to load.
	 * @param model        Resource set as model.
	 */
	public abstract void init(final URI gipsModelUri, final ResourceSet model);

	/**
	 * Initializes the GIPS engine API with the given GIPS intermediate model URI, a
	 * resource set as model, and the IBeX pattern path to avoid using hard-coded
	 * paths in IBeX.
	 * 
	 * @param gipsModelUri    GIPS intermediate model URI to load.
	 * @param model           Resource set as model.
	 * @param ibexPatternPath IBeX pattern path to load.
	 */
	public abstract void init(final URI gipsModelUri, final ResourceSet model, final URI ibexPatternPath);

	/**
	 * Returns the resource set from the eMoflon API.
	 * 
	 * @return Resource set from the eMoflon API.
	 */
	public ResourceSet getResourceSet() {
		return model;
	}

	/**
	 * Terminates the GipsEngine (super class) and the eMoflon::IBeX engine
	 * (including the pattern matcher).
	 */
	@Override
	public void terminate() {
		// Terminate the GipsEngine
		super.terminate();

		// Terminate the eMoflon::IBeX engine (including HiPE)
		if (eMoflonAPI != null)
			eMoflonAPI.terminate();
	}

	protected void setSolverConfig(final org.emoflon.gips.intermediate.GipsIntermediate.SolverConfig config) {
		solverConfig.setEnableTimeLimit(config.isEnableTimeLimit());
		if (config.isEnableTimeLimit())
			solverConfig.setTimeLimit(config.getTimeLimit());
		solverConfig.setTimeLimitIncludeInitTime(config.isTimeLimitIncludeInitTime());

		solverConfig.setEnabledRandomSeed(config.isEnableRndSeed());
		solverConfig.setRandomSeed(config.getIlpRndSeed());

		solverConfig.setPresolve(config.getPresolve());
		solverConfig.setEnableOutput(config.isEnableDebugOutput());

		solverConfig.setEnableTolerance(config.isEnableCustomTolerance());
		solverConfig.setTolerance(config.getTolerance());

		solverConfig.setEnableLpOutput(config.isEnableLpOutput());
		solverConfig.setLpPath(config.getLpPath());

		solverConfig.setEnableThreadCount(config.isThreadCountEnabled());
		if (config.isThreadCountEnabled())
			solverConfig.setThreadCount(config.getThreadCount());

		if (config.isEnableSolverCallback()) {
			solverConfig.setEnableCallbackPath(true);
			solverConfig.setCallbackPath(config.getSolverCallbackPath());
		}
	}

	/**
	 * Initializes the GIPS engine API with a given GIPS intermediate model URI and
	 * a model URI.
	 * 
	 * @param gipsModelURI GIPS intermediate model URI to load.
	 * @param modelUri     Model URI to load.
	 */
	protected void initInternal(final URI gipsModelURI, final URI modelUri) {
		tickInit();
		loadIntermediateModel(gipsModelURI);
		eMoflonApp.registerMetaModels();
		eMoflonApp.loadModel(modelUri);
		model = eMoflonApp.getModel();

		// init eMoflon only if there are patterns present in the ibex model
		if (!gipsModel.getIbexModel().getPatternSet().getContextPatterns().isEmpty()
				|| !gipsModel.getIbexModel().getRuleSet().getRules().isEmpty()) {
			eMoflonAPI = eMoflonApp.initAPI();
		}

		initInternalCommon(gipsModelURI);
		eclipseIntegration.computeInputModelId(modelUri);
	}

	/**
	 * Initializes the GIPS engine API with a given GIPS intermediate model URI, a
	 * model URI, and the IBeX pattern path to avoid using hard-coded paths in IBeX
	 * internally.
	 * 
	 * @param gipsModelURI    GIPS intermediate model URI to load.
	 * @param modelUri        Model URI to load.
	 * @param ibexPatternPath IBeX pattern path to load.
	 */
	protected void initInternal(final URI gipsModelURI, final URI modelUri, final URI ibexPatternPath) {
		tickInit();
		loadIntermediateModel(gipsModelURI);
		eMoflonApp.registerMetaModels();
		eMoflonApp.loadModel(modelUri);
		model = eMoflonApp.getModel();

		// init eMoflon only if there are patterns present in the ibex model
		if (!gipsModel.getIbexModel().getPatternSet().getContextPatterns().isEmpty()
				|| !gipsModel.getIbexModel().getRuleSet().getRules().isEmpty()) {
			eMoflonAPI = eMoflonApp.initAPI(ibexPatternPath);
		}

		initInternalCommon(gipsModelURI);
		eclipseIntegration.computeInputModelId(modelUri);
	}

	/**
	 * Internal initialization that uses an already existing resource set as a
	 * model.
	 * 
	 * @param gipsModelURI URL to the GIPS model.
	 * @param model        Resource set that contains the already existing model
	 *                     instance.
	 */
	protected void initInternal(final URI gipsModelURI, final ResourceSet model) {
		tickInit();
		loadIntermediateModel(gipsModelURI);
		eMoflonApp.registerMetaModels();
		eMoflonApp.setModel(model);
		this.model = eMoflonApp.getModel();

		// init eMoflon only if there are patterns present in the ibex model
		if (!gipsModel.getIbexModel().getPatternSet().getContextPatterns().isEmpty()
				|| !gipsModel.getIbexModel().getRuleSet().getRules().isEmpty()) {
			eMoflonAPI = eMoflonApp.initAPI();
		}

		initInternalCommon(gipsModelURI);
	}

	/**
	 * Internal initialization that uses an already existing resource set as a model
	 * and a given IBeX pattern path to avoid loading of hard-coded XMI models in
	 * IBeX.
	 * 
	 * @param gipsModelURI    URL to the GIPS model.
	 * @param model           Resource set that contains the already existing model
	 *                        instance.
	 * @param ibexPatternPath IBeX pattern path to load.
	 */
	protected void initInternal(final URI gipsModelURI, final ResourceSet model, final URI ibexPatternPath) {
		tickInit();
		loadIntermediateModel(gipsModelURI);
		eMoflonApp.registerMetaModels();
		eMoflonApp.setModel(model);
		this.model = eMoflonApp.getModel();

		// init eMoflon only if there are patterns present in the ibex model
		if (!gipsModel.getIbexModel().getPatternSet().getContextPatterns().isEmpty()
				|| !gipsModel.getIbexModel().getRuleSet().getRules().isEmpty()) {
			eMoflonAPI = eMoflonApp.initAPI(ibexPatternPath);
		}

		initInternalCommon(gipsModelURI);
	}

	/**
	 * Initializes the internal GIPS components (e.g., intermediate model, type
	 * indexer, etc.). This method is common for all internal initialization
	 * methods.
	 * 
	 * @param gipsModelURI URI to the GIPS (intermediate) model.
	 */
	private void initInternalCommon(final URI gipsModelURI) {
		initTypeIndexer();
		validationLog = new GipsConstraintValidationLog();
		setSolverConfig(gipsModel.getConfig());
		initEclipseIntegration();
		initMapperFactory();
		createMappers();
		initConstraintFactory();
		createConstraints();
		initLinearFunctionFactory();
		createObjectives();

		if (gipsModel.getObjective() != null)
			setObjective(createObjective());

		setSolver(createSolver());
	}

	protected void loadIntermediateModel(final URI gipsModelURI) {
		ResourceSet rs = new ResourceSetImpl();
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION,
				new XMIResourceFactoryImpl());

		rs.getPackageRegistry().put(GipsIntermediatePackage.eNS_URI, GipsIntermediatePackage.eINSTANCE);
		rs.getPackageRegistry().put(IBeXPatternModelPackage.eNS_URI, IBeXPatternModelPackage.eINSTANCE);

		Resource model = null;
		try {
			model = rs.getResource(gipsModelURI, true);
		} catch (final Exception ex) {
			ex.printStackTrace();
		}

		gipsModel = (GipsIntermediateModel) model.getContents().get(0);

		gipsModel.getMappings().forEach(mapping -> name2Mapping.put(mapping.getName(), mapping));
	}

	protected void initEclipseIntegration() {
		eclipseIntegration = new EclipseIntegration(getSolverConfig());
		eclipseIntegration.computeIntermediateModelId(gipsModel.eResource().getURI());
		tracer = new GipsTracer(eclipseIntegration);
	}

	protected abstract void createMappers();

	protected void createConstraints() {
		gipsModel.getConstraints().stream()
				.forEach(constraint -> addConstraint(constraintFactory.createConstraint(constraint)));
	}

	protected void createObjectives() {
		gipsModel.getFunctions().stream().forEach(fn -> addLinearFunction(functionFactory.createLinearFunction(fn)));
	}

	protected abstract void initMapperFactory();

	protected abstract void initConstraintFactory();

	protected abstract void initLinearFunctionFactory();

	protected abstract GipsObjective createObjective();

	protected abstract Solver createSolver();

	/**
	 * Applies all non-zero mappings across all defined (GT rule) mappers while
	 * updating the pattern matcher. Calling this method is equivalent to running
	 * `api.getXMapping().applyNonZeroMappings(true)` for all mappings defined on GT
	 * rules.
	 */
	public void applyAllNonZeroMappings() {
		applyAllNonZeroMappings(true);
	}

	/**
	 * Applies all non-zero mappings across all defined (GT rule) mappers while
	 * updating the pattern matcher according to the given parameter `doUpdate`.
	 * Calling this method is equivalent to running
	 * `api.getXMapping().applyNonZeroMappings(doUpdate)` for all mappings defined
	 * on GT rules.
	 * 
	 * @param doUpdate If true, the pattern matcher should be updated throughout the
	 *                 mapper application.
	 */
	public void applyAllNonZeroMappings(final boolean doUpdate) {
		this.mappers.values().forEach(m -> {
			if (m instanceof GipsRuleMapper ruleMapper) {
				ruleMapper.applyNonZeroMappings(doUpdate);
			}
		});
	}

}
