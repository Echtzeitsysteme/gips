package org.emoflon.roam.core.api;

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
import org.emoflon.roam.core.RoamMapper;
import org.emoflon.roam.core.ilp.ILPSolver;
import org.emoflon.roam.core.ilp.ILPSolverConfig;
import org.emoflon.roam.core.ilp.ILPSolverOutput;
import org.emoflon.roam.intermediate.RoamIntermediate.ILPConfig;
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage;

public abstract class RoamEngineAPI<EMOFLON_APP extends GraphTransformationApp<EMOFLON_API>, EMOFLON_API extends GraphTransformationAPI> {

	final protected EMOFLON_APP eMoflonApp;
	protected EMOFLON_API eMoflonAPI;
	protected RoamIntermediateModel roamModel;
	protected RoamEngine roamEngine;
	protected ILPSolverConfig solverConfig;
	protected RoamMapperFactory mapperFactory;
	protected RoamConstraintFactory constraintFactory;
	protected RoamObjectiveFactory objectiveFactory;

	protected RoamEngineAPI(final EMOFLON_APP eMoflonApp) {
		this.eMoflonApp = eMoflonApp;
	}

	public abstract void init(final URI modelUri);
	
	public void setSolverConfig(final ILPSolverConfig solverConfig) {
		this.solverConfig = solverConfig;
	}
	
	protected void setSolverConfig(final ILPConfig config) {
		solverConfig = new ILPSolverConfig(config.getIlpTimeLimit(), config.isEnableRndSeed(), 
				config.getIlpRndSeed(), config.isEnablePresolve(), config.isEnableDebugOutput());
	}
	
	public void update() {
		roamEngine.update();
	}
	
	public void buildILPProblem(boolean doUpdate) {
		roamEngine.buildILPProblem(doUpdate);
	}
	
	public ILPSolverOutput solveILPProblem() {
		return roamEngine.solveILPProblem();
	}

	protected abstract void initMapperFactory();

	protected abstract void initConstraintFactory();

	protected abstract void initObjectiveFactory();

	protected abstract RoamGlobalObjective createGlobalObjective();
	
	protected abstract ILPSolver createSolver();

	protected void init(final URI roamModelURI, final URI modelUri) {
		eMoflonApp.registerMetaModels();
		eMoflonApp.loadModel(modelUri);
		eMoflonAPI = eMoflonApp.initAPI();
		loadIntermediateModel(roamModelURI);
		this.roamEngine = new RoamEngine(eMoflonAPI, roamModel);
		setSolverConfig(roamModel.getConfig());
		initMapperFactory();
		createMappers();
		initConstraintFactory();
		createConstraints();
		initObjectiveFactory();
		createObjectives();

		if (roamModel.getGlobalObjective() != null)
			roamEngine.setGlobalObjective(createGlobalObjective());
		
		roamEngine.setILPSolver(createSolver());
	}

	protected void loadIntermediateModel(final URI roamModelURI) {
		ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		// Init GT-Metamodel
		IBeXPatternModelPackage.eINSTANCE.eClass();
		// Init RoamIntermediate-Metamodel
		RoamIntermediatePackage.eINSTANCE.eClass();
		Resource model = rs.getResource(roamModelURI, false);
		roamModel = (RoamIntermediateModel) model.getContents().get(0);
	}

	protected void createMappers() {
		roamModel.getVariables().stream().filter(var -> var instanceof Mapping).map(var -> (Mapping) var)
				.forEach(mapping -> {
					RoamMapper<?> mapper = mapperFactory.createMapper(mapping);
					roamEngine.addMapper(mapper);
				});
	}

	protected void createConstraints() {
		roamModel.getConstraints().stream()
				.forEach(constraint -> roamEngine.addConstraint(constraintFactory.createConstraint(constraint)));
	}

	protected void createObjectives() {
		roamModel.getObjectives().stream()
				.forEach(objective -> roamEngine.addObjective(objectiveFactory.createObjective(objective)));
	}

	public void terminate() {
		roamEngine.terminate();
	}

}
