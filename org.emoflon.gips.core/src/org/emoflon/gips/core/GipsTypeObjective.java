package org.emoflon.gips.core;

import java.util.Collections;
import java.util.LinkedList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.core.ilp.ILPLinearFunction;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeObjective;

public abstract class GipsTypeObjective<CONTEXT extends EObject>
		extends GipsObjective<TypeObjective, CONTEXT, Integer> {

	final protected TypeIndexer indexer;
	final protected EClass type;

	public GipsTypeObjective(GipsEngine engine, TypeObjective objective) {
		super(engine, objective);
		indexer = engine.getIndexer();
		type = objective.getModelType().getType();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void buildObjectiveFunction() {
		terms = Collections.synchronizedList(new LinkedList<>());
		constantTerms = Collections.synchronizedList(new LinkedList<>());
		indexer.getObjectsOfType(type).parallelStream().forEach(context -> buildTerms((CONTEXT) context));
		ilpObjective = new ILPLinearFunction<>(terms, constantTerms);
	}

}
