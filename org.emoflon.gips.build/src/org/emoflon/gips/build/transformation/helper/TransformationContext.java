package org.emoflon.gips.build.transformation.helper;

import org.emoflon.gips.build.transformation.transformer.TransformerFactory;
import org.emoflon.gips.intermediate.GipsIntermediate.Context;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateFactory;

public abstract class TransformationContext {
	final protected GipsTransformationData data;
	final protected Context localContext;
	final protected Context setContext;
	final protected TransformerFactory transformerFactory;
	final protected GipsIntermediateFactory factory = GipsIntermediateFactory.eINSTANCE;

	protected TransformationContext(final GipsTransformationData data, final Context localContext,
			final Context setContext, final TransformerFactory factory) {
		this.data = data;
		this.localContext = localContext;
		this.setContext = setContext;
		this.transformerFactory = factory;
	}

	protected TransformationContext(final GipsTransformationData data, final Context localContext,
			final TransformerFactory factory) {
		this.data = data;
		this.localContext = localContext;
		this.setContext = null;
		this.transformerFactory = factory;
	}
}
