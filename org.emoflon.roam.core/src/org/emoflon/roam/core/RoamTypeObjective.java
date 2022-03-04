package org.emoflon.roam.core;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.roam.core.ilp.ILPLinearFunction;
import org.emoflon.roam.core.ilp.ILPTerm;
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
		List<ILPTerm<Integer, Double>> terms = indexer.getObjectsOfType(type).parallelStream()
				.flatMap(context -> buildTerms((CONTEXT) context).parallelStream()).collect(Collectors.toList());
		ilpObjective = new ILPLinearFunction<>(terms, new LinkedList<>());
	}

}
