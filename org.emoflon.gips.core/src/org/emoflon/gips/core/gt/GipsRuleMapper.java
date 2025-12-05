package org.emoflon.gips.core.gt;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.gips.intermediate.GipsIntermediate.RuleMapping;
import org.emoflon.ibex.gt.api.GraphTransformationMatch;
import org.emoflon.ibex.gt.api.GraphTransformationRule;

public abstract class GipsRuleMapper<PM extends GipsGTMapping<M, R>, M extends GraphTransformationMatch<M, R>, R extends GraphTransformationRule<M, R>>
		extends GipsPatternMapper<PM, M, R> {

	private Map<String, String> internalVarToParamName = new HashMap<>();

	public GipsRuleMapper(final GipsEngine engine, final Mapping mapping, final R rule) {
		super(engine, mapping, rule);

		final RuleMapping typedMapping = ((RuleMapping) mapping);
		if (typedMapping.getBoundVariables() != null && !typedMapping.getBoundVariables().isEmpty()) {
			typedMapping.getBoundVariables()
					.forEach(v -> internalVarToParamName.put(v.getName(), v.getParameter().getName()));
		}
	}

	public R getGTRule() {
		return this.pattern;
	}

	public Collection<Optional<M>> applyNonZeroMappings() {
		return applyMappings(getNonZeroVariableMappings(), m -> getGTRule().apply(m.match));
	}

	public Collection<Optional<M>> applyMappings(Function<Integer, Boolean> predicate) {
		return applyMappings(getMappings(predicate), m -> getGTRule().apply(m.match));
	}

	public Collection<Optional<M>> applyNonZeroMappings(final boolean doUpdate) {
		return applyMappings(getNonZeroVariableMappings(), m -> getGTRule().apply(m.match, doUpdate));
	}

	public Collection<Optional<M>> applyMappings(final Function<Integer, Boolean> predicate, final boolean doUpdate) {
		return applyMappings(getMappings(predicate), m -> getGTRule().apply(m.match, doUpdate));
	}

	private Collection<Optional<M>> applyMappings(Collection<PM> selectedMappings,
			Function<PM, Optional<M>> ruleApplication) {

		if (engine.getTracer().isTracingEnabled())
			ruleApplication = wrapRuleApplicationForTracing(ruleApplication);

		return selectedMappings.stream() //
				.map(this::updateRuleParametersForMapping) //
				.map(ruleApplication) //
				.collect(Collectors.toSet());
	}

	private PM updateRuleParametersForMapping(PM mapping) {
		if (mapping.hasBoundVariables()) {
			final Map<String, Object> parameters = getGTRule().getParameters();
			mapping.getBoundVariables().forEach((name, var) -> {
				parameters.put(internalVarToParamName.get(name), var.getValue());
			});
		}
		return mapping;
	}

	private Function<PM, Optional<M>> wrapRuleApplicationForTracing(Function<PM, Optional<M>> ruleApplication) {
		return mapping -> {
			Optional<M> matchAfterRule = ruleApplication.apply(mapping);
			if (matchAfterRule.isPresent())
				engine.getTracer().mapLpVariable2Output(mapping.getName(), matchAfterRule.get().toIMatch());
			return matchAfterRule;
		};
	}

}
