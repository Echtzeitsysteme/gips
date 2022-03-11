package org.emoflon.roam.core.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emoflon.ibex.gt.api.GraphTransformationAPI;
import org.emoflon.ibex.gt.api.GraphTransformationApp;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;
import org.emoflon.roam.core.RoamEngine;
import org.emoflon.roam.core.RoamGlobalObjective;
import org.emoflon.roam.core.TypeIndexer;
import org.emoflon.roam.core.ilp.ILPSolver;
import org.emoflon.roam.core.ilp.ILPSolverConfig;
import org.emoflon.roam.intermediate.RoamIntermediate.ILPConfig;
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage;

public abstract class RoamEngineAPI<EMOFLON_APP extends GraphTransformationApp<EMOFLON_API>, EMOFLON_API extends GraphTransformationAPI>
		extends RoamEngine {

	final protected EMOFLON_APP eMoflonApp;
	protected EMOFLON_API eMoflonAPI;
	protected RoamIntermediateModel roamModel;
	final protected Map<String, Mapping> name2Mapping = new HashMap<>();
	protected ILPSolverConfig solverConfig;
	protected RoamMapperFactory<EMOFLON_API> mapperFactory;
	protected RoamConstraintFactory<EMOFLON_API> constraintFactory;
	protected RoamObjectiveFactory<EMOFLON_API> objectiveFactory;

	protected RoamEngineAPI(final EMOFLON_APP eMoflonApp) {
		this.eMoflonApp = eMoflonApp;
	}

	public void setSolverConfig(final ILPSolverConfig solverConfig) {
		this.solverConfig = solverConfig;
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
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		Resource r = rs.createResource(URI.createFileURI(path));
		// Fetch model contents from eMoflon
		r.getContents().addAll(rs.getResources().get(0).getContents());
		r.save(null);
		// Hand model back to owner
		rs.getResources().get(0).getContents().addAll(r.getContents());
	}

	@Override
	protected void initTypeIndexer() {
		indexer = new TypeIndexer(eMoflonAPI, roamModel);
	}

	public abstract void init(final URI modelUri);

	protected void setSolverConfig(final ILPConfig config) {
		solverConfig = new ILPSolverConfig(config.isEnableTimeLimit(), config.getIlpTimeLimit(),
				config.isEnableRndSeed(), config.getIlpRndSeed(), config.isEnablePresolve(),
				config.isEnableDebugOutput());
	}

	protected void init(final URI roamModelURI, final URI modelUri) {
		eMoflonApp.registerMetaModels();
		eMoflonApp.loadModel(modelUri);
		eMoflonAPI = eMoflonApp.initAPI();
		loadIntermediateModel(roamModelURI);
		initTypeIndexer();
		setSolverConfig(roamModel.getConfig());
		initMapperFactory();
		createMappers();
		initConstraintFactory();
		createConstraints();
		initObjectiveFactory();
		createObjectives();

		if (roamModel.getGlobalObjective() != null)
			setGlobalObjective(createGlobalObjective());

		setILPSolver(createSolver());
	}

	protected void loadIntermediateModel(final URI roamModelURI) {
		ResourceSet rs = new ResourceSetImpl();
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION,
				new XMIResourceFactoryImpl());

		rs.getPackageRegistry().put(RoamIntermediatePackage.eNS_URI, RoamIntermediatePackage.eINSTANCE);
		rs.getPackageRegistry().put(IBeXPatternModelPackage.eNS_URI, IBeXPatternModelPackage.eINSTANCE);

		Resource model = null;
		try {
			model = rs.getResource(roamModelURI, true);
		} catch (final Exception ex) {
			ex.printStackTrace();
		}

		roamModel = (RoamIntermediateModel) model.getContents().get(0);

		roamModel.getVariables().stream().filter(var -> var instanceof Mapping).map(var -> (Mapping) var)
				.forEach(mapping -> name2Mapping.put(mapping.getName(), mapping));
	}

	protected abstract void createMappers();

	protected void createConstraints() {
		roamModel.getConstraints().stream()
				.forEach(constraint -> addConstraint(constraintFactory.createConstraint(constraint)));
	}

	protected void createObjectives() {
		roamModel.getObjectives().stream()
				.forEach(objective -> addObjective(objectiveFactory.createObjective(objective)));
	}

	protected abstract void initMapperFactory();

	protected abstract void initConstraintFactory();

	protected abstract void initObjectiveFactory();

	protected abstract RoamGlobalObjective createGlobalObjective();

	protected abstract ILPSolver createSolver();

}
