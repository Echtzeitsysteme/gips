package org.emoflon.roam.build.transformation;

import org.emoflon.ibex.gt.transformations.EditorToIBeXPatternTransformation;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateFactory;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel;
import org.emoflon.roam.roamslang.roamSLang.EditorGTFile;

public class RoamToIntermediate {
	protected RoamIntermediateFactory factory = RoamIntermediateFactory.eINSTANCE;
	protected RoamIntermediateModel model;
	
	public RoamIntermediateModel transform(final EditorGTFile roamSlangFile) {
		model = factory.createRoamIntermediateModel();
		//transform GT to IBeXPatterns
		EditorToIBeXPatternTransformation ibexTransformer = new EditorToIBeXPatternTransformation();
		model.setIbexModel(ibexTransformer.transform(roamSlangFile));
		
		
		return model;
	}
}
