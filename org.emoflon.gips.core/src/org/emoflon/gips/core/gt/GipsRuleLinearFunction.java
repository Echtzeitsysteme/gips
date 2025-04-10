package org.emoflon.gips.core.gt;

import java.util.Collections;
import java.util.LinkedList;

import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsLinearFunction;
import org.emoflon.gips.core.milp.model.LinearFunction;
import org.emoflon.gips.intermediate.GipsIntermediate.RuleFunction;
import org.emoflon.ibex.gt.api.GraphTransformationMatch;
import org.emoflon.ibex.gt.api.GraphTransformationRule;

public abstract class GipsRuleLinearFunction<ENGINE extends GipsEngine, M extends GraphTransformationMatch<M, R>, R extends GraphTransformationRule<M, R>>
		extends GipsLinearFunction<ENGINE, RuleFunction, M> {

	final protected R rule;

	public GipsRuleLinearFunction(ENGINE engine, RuleFunction function, final R rule) {
		super(engine, function);
		this.rule = rule;
	}

	@Override
	public void buildLinearFunction() {
		terms = Collections.synchronizedList(new LinkedList<>());
		constantTerms = Collections.synchronizedList(new LinkedList<>());
		// TODO: stream() -> parallelStream() once GIPS is based on the new shiny GT
		// language
		rule.findMatches(false).stream().forEach(context -> buildTerms(context));
		milpLinearFunction = new LinearFunction(terms, constantTerms);
	}

}
