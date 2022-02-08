package org.emoflon.roam.core;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.roam.core.ilp.ILPConstraint;
import org.emoflon.roam.core.ilp.ILPTerm;
import org.emoflon.roam.intermediate.RoamIntermediate.TypeConstraint;

public abstract class RoamTypeConstraint extends RoamConstraint<TypeConstraint, EObject, Integer> {
	
	final protected TypeIndexer indexer;
	final protected EClass type;

	public RoamTypeConstraint(RoamEngine engine, TypeConstraint constraint) {
		super(engine, constraint);
		indexer = engine.getIndexer();
		type = constraint.getModelType().getType();
	}

	@Override
	public void buildConstraints() {
		for(EObject context : indexer.getObjectsOfType(type)) {
			ilpConstraints.put(context, buildConstraint(context));
		}
	}
	
	@Override
	public ILPConstraint<Integer> buildConstraint(final EObject context) {
		Integer constTerm = buildConstantTerm(context);
		List<ILPTerm<Integer>> terms = buildVariableTerms(context);
		return new ILPConstraint<Integer>(constTerm, constraint.getExpression().getOperator(), terms);
	}

}
