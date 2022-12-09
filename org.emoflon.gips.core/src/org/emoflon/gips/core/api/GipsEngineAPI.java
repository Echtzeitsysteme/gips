package org.emoflon.gips.core.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsGlobalObjective;
import org.emoflon.gips.core.TypeIndexer;
import org.emoflon.gips.core.ilp.ILPSolver;
import org.emoflon.gips.core.ilp.ILPSolverConfig;
import org.emoflon.gips.core.validation.GipsConstraintValidationLog;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.ibex.gt.api.GraphTransformationAPI;
import org.emoflon.ibex.gt.api.GraphTransformationApp;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;

public abstract class GipsEngineAPI<EMOFLON_APP extends GraphTransformationApp<EMOFLON_API>, EMOFLON_API extends GraphTransformationAPI>
		extends GipsEngine {

	final protected EMOFLON_APP eMoflonApp;
	protected EMOFLON_API eMoflonAPI;
	protected GipsIntermediateModel gipsModel;
	final protected Map<String, Mapping> name2Mapping = new HashMap<>();
	protected ILPSolverConfig solverConfig;
	protected GipsMapperFactory<EMOFLON_API> mapperFactory;
	protected GipsConstraintFactory<? extends GipsEngineAPI<EMOFLON_APP, EMOFLON_API>, EMOFLON_API> constraintFactory;
	protected GipsObjectiveFactory<? extends GipsEngineAPI<EMOFLON_APP, EMOFLON_API>, EMOFLON_API> objectiveFactory;

	protected GipsEngineAPI(final EMOFLON_APP eMoflonApp) {
		this.eMoflonApp = eMoflonApp;
	}

	public void setSolverConfig(final ILPSolverConfig solverConfig) {
		this.solverConfig = solverConfig;
	}

	public ILPSolverConfig getSolverConfig() {
		return solverConfig;
	}

	public EMOFLON_APP getEMoflonApp() {
		return eMoflonApp;
	}

	public EMOFLON_API getEMoflonAPI() {
		return eMoflonAPI;
	}

	@Override
	public void update() {
		eMoflonAPI.updateMatches();
	}

	@Override
	public void saveResult() throws IOException {
		eMoflonApp.getModel().getResources().get(0).save(null);
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
		// Hand model back to owner
		eMoflonApp.getModel().getResources().get(0).getContents().addAll(r.getContents());
	}

	@Override
	protected void initTypeIndexer() {
		indexer = new TypeIndexer(eMoflonAPI, gipsModel);
	}

	public abstract void init(final URI modelUri);

	public abstract void init(final URI gipsModelURI, final URI modelUri);
	
	/**
	 * Initializes the API with a given resource set as model.
	 * 
	 * @param model Resource set to set as model.
	 */
	public abstract void init(final ResourceSet model);
	
	/**
	 * Returns the resource set from the eMoflon API.
	 * @return Resource set from the eMoflon API.
	 */
	public ResourceSet getResourceSet() {
		return this.eMoflonAPI.getModel();
	}

	protected void setSolverConfig(final ILPConfig config) {
		solverConfig = new ILPSolverConfig(config.isEnableTimeLimit(), config.getIlpTimeLimit(), //
				config.isEnableRndSeed(), config.getIlpRndSeed(), //
				config.isEnablePresolve(), //
				config.isEnableDebugOutput(), //
				config.isEnableCustomTolerance(), config.getTolerance(), //
				config.isEnableLpOutput(), config.getLpPath());
	}

	protected void initInternal(final URI gipsModelURI, final URI modelUri) {
		eMoflonApp.registerMetaModels();
		eMoflonApp.loadModel(modelUri);
		eMoflonAPI = eMoflonApp.initAPI();
		loadIntermediateModel(gipsModelURI);
		initTypeIndexer();
		validationLog = new GipsConstraintValidationLog();
		setSolverConfig(gipsModel.getConfig());
		initMapperFactory();
		createMappers();
		initConstraintFactory();
		createConstraints();
		initObjectiveFactory();
		createObjectives();

		if (gipsModel.getGlobalObjective() != null)
			setGlobalObjective(createGlobalObjective());

		setILPSolver(createSolver());
	}
	
	/**
	 * Internal initialization that uses an already existing resource set as a model.
	 * 
	 * @param gipsModelURI URL to the GIPS model.
	 * @param model Resource set that contains the already existing model instance.
	 */
	protected void initInternal(final URI gipsModelURI, final ResourceSet model) {
		eMoflonApp.registerMetaModels();
		eMoflonApp.setModel(model);
		eMoflonAPI = eMoflonApp.initAPI();
		loadIntermediateModel(gipsModelURI);
		initTypeIndexer();
		validationLog = new GipsConstraintValidationLog();
		setSolverConfig(gipsModel.getConfig());
		initMapperFactory();
		createMappers();
		initConstraintFactory();
		createConstraints();
		initObjectiveFactory();
		createObjectives();

		if (gipsModel.getGlobalObjective() != null)
			setGlobalObjective(createGlobalObjective());

		setILPSolver(createSolver());
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

		gipsModel.getVariables().stream().filter(var -> var instanceof Mapping).map(var -> (Mapping) var)
				.forEach(mapping -> name2Mapping.put(mapping.getName(), mapping));
	}

	protected abstract void createMappers();

	protected void createConstraints() {
		gipsModel.getConstraints().stream()
				.forEach(constraint -> addConstraint(constraintFactory.createConstraint(constraint)));
	}

	protected void createObjectives() {
		gipsModel.getObjectives().stream()
				.forEach(objective -> addObjective(objectiveFactory.createObjective(objective)));
	}

	protected abstract void initMapperFactory();

	protected abstract void initConstraintFactory();

	protected abstract void initObjectiveFactory();

	protected abstract GipsGlobalObjective createGlobalObjective();

	protected abstract ILPSolver createSolver();

}
