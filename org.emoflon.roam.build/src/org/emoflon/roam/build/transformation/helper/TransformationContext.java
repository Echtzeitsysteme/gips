package org.emoflon.roam.build.transformation.helper;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.roam.build.transformation.transformer.TransformerFactory;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateFactory;

public abstract class TransformationContext <T extends EObject>{
	final protected RoamTransformationData data;
	final protected T context;
	final protected TransformerFactory transformerFactory;
	final protected RoamIntermediateFactory factory = RoamIntermediateFactory.eINSTANCE;
	
	protected TransformationContext(final RoamTransformationData data, final T context, final TransformerFactory factory) {
		this.data = data;
		this.context = context;
		this.transformerFactory = factory;
	}
}
