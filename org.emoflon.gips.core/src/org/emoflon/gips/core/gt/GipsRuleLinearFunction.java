package org.emoflon.gips.core.gt;

import java.util.Collections;
import java.util.LinkedList;

import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsLinearFunction;
import org.emoflon.gips.core.milp.model.LinearFunction;
import org.emoflon.gips.intermediate.GipsIntermediate.RuleFunction;
import org.emoflon.ibex.gt.engine.IBeXGTCoMatch;
import org.emoflon.ibex.gt.engine.IBeXGTCoPattern;
import org.emoflon.ibex.gt.engine.IBeXGTMatch;
import org.emoflon.ibex.gt.engine.IBeXGTPattern;
import org.emoflon.ibex.gt.engine.IBeXGTRule;

public abstract class GipsRuleLinearFunction<ENGINE extends GipsEngine, R extends IBeXGTRule<R, P, M, CP, CM>, P extends IBeXGTPattern<P, M>, M extends IBeXGTMatch<M, P>, CP extends IBeXGTCoPattern<CP, CM, R, P, M>, CM extends IBeXGTCoMatch<CM, CP, R, P, M>>
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
		rule.getMatches(false).stream().forEach(context -> buildTerms(context));
		milpLinearFunction = new LinearFunction(terms, constantTerms);
	}

}
