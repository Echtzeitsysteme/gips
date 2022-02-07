package org.emoflon.roam.build.generator;

import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel;

public class RoamCodeGenerator {

	final protected TemplateData data;
	
	public RoamCodeGenerator(final RoamIntermediateModel model) {
		data = new TemplateData(model);
	}
}
