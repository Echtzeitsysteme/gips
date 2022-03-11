package org.emoflon.roam.core;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.roam.core.ilp.ILPConstraint;
import org.emoflon.roam.core.ilp.ILPTerm;
import org.emoflon.roam.intermediate.RoamIntermediate.TypeConstraint;

public abstract class RoamTypeConstraint<CONTEXT extends EObject>
		extends RoamConstraint<TypeConstraint, CONTEXT, Integer> {

	final protected EClass type;

	public RoamTypeConstraint(RoamEngine engine, TypeConstraint constraint) {
		super(engine, constraint);
		type = constraint.getModelType().getType();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void buildConstraints() {
		indexer.getObjectsOfType(type).parallelStream().forEach(context -> {
			ilpConstraints.put((CONTEXT) context, buildConstraint((CONTEXT) context));
		});
	}

	@Override
	public ILPConstraint<Integer> buildConstraint(final CONTEXT context) {
		double constTerm = buildConstantTerm(context);
		List<ILPTerm<Integer, Double>> terms = buildVariableTerms(context);
		return new ILPConstraint<>(terms, constraint.getExpression().getOperator(), constTerm);
	}

}
