package org.emoflon.roam.core;

import java.util.Collections;
import java.util.LinkedList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.roam.core.ilp.ILPLinearFunction;
import org.emoflon.roam.intermediate.RoamIntermediate.TypeObjective;

public abstract class RoamTypeObjective<CONTEXT extends EObject>
		extends RoamObjective<TypeObjective, CONTEXT, Integer> {

	final protected TypeIndexer indexer;
	final protected EClass type;

	public RoamTypeObjective(RoamEngine engine, TypeObjective objective) {
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
