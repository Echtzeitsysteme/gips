package org.emoflon.gips.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.emoflon.gips.core.ilp.ILPConstraint;
import org.emoflon.gips.core.ilp.ILPTerm;
import org.emoflon.gips.intermediate.GipsIntermediate.Constraint;

public abstract class GipsConstraint<CONSTR extends Constraint, CONTEXT extends Object, VARTYPE extends Number> {
	final protected GipsEngine engine;
	final protected TypeIndexer indexer;
	final protected CONSTR constraint;
	final protected String name;
	final protected Map<CONTEXT, ILPConstraint<VARTYPE>> ilpConstraints = Collections.synchronizedMap(new HashMap<>());

	public GipsConstraint(final GipsEngine engine, final CONSTR constraint) {
		this.engine = engine;
		this.indexer = engine.getIndexer();
		this.constraint = constraint;
		this.name = constraint.getName();
	}

	public abstract void buildConstraints();

	public String getName() {
		return name;
	}

	public Collection<ILPConstraint<VARTYPE>> getConstraints() {
		return ilpConstraints.values();
	}

	protected abstract ILPConstraint<VARTYPE> buildConstraint(final CONTEXT context);

	protected abstract double buildConstantTerm(final CONTEXT context);

	protected abstract List<ILPTerm<VARTYPE, Double>> buildVariableTerms(final CONTEXT context);
}
