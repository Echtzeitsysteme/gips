package org.emoflon.roam.core;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.roam.core.ilp.ILPObjectiveFunction;
import org.emoflon.roam.core.ilp.ILPTerm;
import org.emoflon.roam.intermediate.RoamIntermediate.TypeObjective;

public abstract class RoamTypeObjective extends RoamObjective<TypeObjective, EObject, Integer> {

	final protected TypeIndexer indexer;
	final protected EClass type;
	
	public RoamTypeObjective(RoamEngine engine, TypeObjective objective) {
		super(engine, objective);
		indexer = engine.getIndexer();
		type = objective.getModelType().getType();
	}

	@Override
	public void buildObjectiveFunction() {
		List<ILPTerm<Integer, Double>> terms = indexer.getObjectsOfType(type).parallelStream().map(context -> buildTerm(context)).collect(Collectors.toList());
		ilpObjective = new ILPObjectiveFunction<Integer>(terms);
	}

}
