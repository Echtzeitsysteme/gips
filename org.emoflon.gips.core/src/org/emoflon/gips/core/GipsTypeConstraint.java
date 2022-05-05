package org.emoflon.gips.core;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.core.ilp.ILPConstraint;
import org.emoflon.gips.core.ilp.ILPTerm;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeConstraint;

public abstract class GipsTypeConstraint<CONTEXT extends EObject>
		extends GipsConstraint<TypeConstraint, CONTEXT, Integer> {

	final protected EClass type;

	public GipsTypeConstraint(GipsEngine engine, TypeConstraint constraint) {
		super(engine, constraint);
		type = constraint.getModelType().getType();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void buildConstraints() {
		indexer.getObjectsOfType(type).parallelStream().forEach(context -> {
//			final ILPConstraint<Integer> candidate = buildConstraint((CONTEXT) context);
//			if (!candidate.lhsTerms().isEmpty()) {
			ilpConstraints.put((CONTEXT) context, buildConstraint((CONTEXT) context));
//			}
			// TODO: Throw an exception if the collection of LHS terms is empty (and the
			// presolver functionality is implemented.
		});
	}

	@Override
	public ILPConstraint<Integer> buildConstraint(final CONTEXT context) {
		double constTerm = buildConstantTerm(context);
		List<ILPTerm<Integer, Double>> terms = buildVariableTerms(context);
		return new ILPConstraint<>(terms, constraint.getExpression().getOperator(), constTerm);
	}

}
