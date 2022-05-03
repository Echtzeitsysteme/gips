package org.emoflon.gips.build.transformation.helper;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.build.transformation.transformer.TransformerFactory;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateFactory;

public abstract class TransformationContext<T extends EObject> {
	final protected GipsTransformationData data;
	final protected T context;
	final protected TransformerFactory transformerFactory;
	final protected GipsIntermediateFactory factory = GipsIntermediateFactory.eINSTANCE;

	protected TransformationContext(final GipsTransformationData data, final T context,
			final TransformerFactory factory) {
		this.data = data;
		this.context = context;
		this.transformerFactory = factory;
	}
}
