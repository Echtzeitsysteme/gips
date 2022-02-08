package org.emoflon.roam.build.generator;

import org.emoflon.ibex.gt.codegen.EClassifiersManager;
import org.emoflon.roam.build.RoamAPIData;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel;

public class RoamCodeGenerator {

	final protected TemplateData data;
	
	public RoamCodeGenerator(final RoamIntermediateModel model, final RoamAPIData apiData, final EClassifiersManager classToPackage) {
		data = new TemplateData(model, apiData, classToPackage);
	}
}
