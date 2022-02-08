package org.emoflon.roam.build.generator;

import org.emoflon.ibex.gt.codegen.EClassifiersManager;
import org.emoflon.roam.build.RoamAPIData;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel;

public class TemplateData {
	final public RoamIntermediateModel model;
	final public RoamAPIData apiData;
	final public EClassifiersManager classToPackage;
	
	public String roamApiClassName;
	public String mapperFactoryClassName;
	public String constraintFactoryClassName;
	public String objectiveFactoryClassName;
	
	public TemplateData(final RoamIntermediateModel model, final RoamAPIData apiData, final EClassifiersManager classToPackage) {
		this.model = model;
		this.apiData = apiData;
		this.classToPackage = classToPackage;
		init();
	}
	
	private void init() {
		roamApiClassName = apiData.apiClassNamePrefix+"RoamAPI";
		mapperFactoryClassName = apiData.apiClassNamePrefix+"RoamMapperFactory";
		constraintFactoryClassName = apiData.apiClassNamePrefix+"RoamConstraintFactory";
		objectiveFactoryClassName = apiData.apiClassNamePrefix+"RoamObjectiveFactory";
	}
}
