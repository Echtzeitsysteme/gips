package org.emoflon.gips.core;

import java.util.Collections;
import java.util.LinkedList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.core.milp.model.LinearFunction;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeFunction;

public abstract class GipsTypeLinearFunction<ENGINE extends GipsEngine, CONTEXT extends EObject>
		extends GipsLinearFunction<ENGINE, TypeFunction, CONTEXT> {

	final protected EClass type;

	public GipsTypeLinearFunction(ENGINE engine, TypeFunction linearFunction) {
		super(engine, linearFunction);
		type = linearFunction.getType();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void buildLinearFunction() {
		terms = Collections.synchronizedList(new LinkedList<>());
		constantTerms = Collections.synchronizedList(new LinkedList<>());
		// TODO: stream() -> parallelStream() once GIPS is based on the new shiny GT
		// language
		indexer.getObjectsOfType(type).stream().forEach(context -> buildTerms((CONTEXT) context));
		milpLinearFunction = new LinearFunction(terms, constantTerms);
	}

}
