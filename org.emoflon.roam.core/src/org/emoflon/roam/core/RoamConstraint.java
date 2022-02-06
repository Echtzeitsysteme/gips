package org.emoflon.roam.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.emoflon.roam.core.ilp.ILPTerm;
import org.emoflon.roam.intermediate.RoamIntermediate.Constraint;

public abstract class RoamConstraint {
	final protected RoamEngine engine;
	final protected Constraint constraint;
	final protected String name;
	final protected Map<String, RoamMapper<?>> relatedMappers = new HashMap<>();
	final protected Map<String, RoamMapping> relatedMappings = Collections.synchronizedMap(new HashMap<>());
	final protected List<ILPTerm<Integer>> terms = Collections.synchronizedList(new LinkedList<>());
	
	public RoamConstraint(final RoamEngine engine, final Constraint constraint) {
		this.engine = engine;
		this.constraint = constraint;
		this.name = constraint.getName();
	}
	
	public abstract void constructTerms();
	
	public String getName() {
		return name;
	}
}
