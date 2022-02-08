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
import org.emoflon.roam.core.RoamMapper;
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage;

public abstract class RoamEngineAPI {

	final protected GraphTransformationApp<? extends GraphTransformationAPI> eMoflonApp;
	protected GraphTransformationAPI eMoflonAPI;
	protected RoamIntermediateModel roamModel;
	protected RoamEngine roamEngine;
	protected RoamMapperFactory mapperFactory;
	protected RoamConstraintFactory constraintFactory;
	
	protected RoamEngineAPI(final GraphTransformationApp<? extends GraphTransformationAPI> eMoflonApp) {
		this.eMoflonApp = eMoflonApp;
	}
	
	protected abstract void registerMetamodels();

	protected abstract void initMapperFactory();
	
	protected abstract void initConstraintFactory();

	protected void init(final URI roamModelURI, final URI modelUri) {
		registerMetamodels();
		eMoflonApp.loadModel(modelUri);
		eMoflonAPI = eMoflonApp.initAPI();
		loadIntermediateModel(roamModelURI);
		this.roamEngine = new RoamEngine(eMoflonAPI, roamModel);
		initMapperFactory();
		createMappers();
		initConstraintFactory();
		createConstraints();
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
		roamModel.getVariables().stream()
			.filter(var -> var instanceof Mapping)
			.map(var -> (Mapping) var)
			.forEach(mapping->{
				RoamMapper<?> mapper = mapperFactory.createMapper(mapping);
				roamEngine.addMapper(mapper);
			});
	}
	
	protected void createConstraints() {
		roamModel.getConstraints().stream()
		.forEach(constraint -> roamEngine.addConstraint(constraintFactory.createConstraint(constraint)));
	}
	
	public void terminate() {
		roamEngine.terminate();
	}

}
