package org.emoflon.gips.core.gt;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsMapper;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.gips.intermediate.GipsIntermediate.RuleMapping;
import org.emoflon.ibex.gt.api.GraphTransformationMatch;
import org.emoflon.ibex.gt.api.GraphTransformationRule;

public abstract class GipsRuleMapper<GTM extends GipsGTMapping<M, R>, M extends GraphTransformationMatch<M, R>, R extends GraphTransformationRule<M, R>>
		extends GipsMapper<GTM> {

	final protected R rule;
	final protected Map<M, GTM> match2Mappings = Collections.synchronizedMap(new HashMap<>());
	private int mappingCounter = 0;
	private Map<String, String> internalVarToParamName = new HashMap<>();

	final protected Consumer<M> appearConsumer = this::addMapping;
	final protected Consumer<M> disappearConsumer = this::removeMapping;

	public GipsRuleMapper(final GipsEngine engine, final Mapping mapping, final R rule) {
		super(engine, mapping);
		this.rule = rule;
		this.init();
		final RuleMapping typedMapping = ((RuleMapping) mapping);

		if (typedMapping.getBoundVariables() != null && !typedMapping.getBoundVariables().isEmpty()) {
			typedMapping.getBoundVariables()
					.forEach(v -> internalVarToParamName.put(v.getName(), v.getParameter().getName()));
		}
	}

	public R getGTRule() {
		return rule;
	}

	public Collection<Optional<M>> applyNonZeroMappings() {
		return applyMappings(getNonZeroVariableMappings(), m -> rule.apply(m.match));
	}

	public Collection<Optional<M>> applyMappings(Function<Integer, Boolean> predicate) {
		return applyMappings(getMappings(predicate), m -> rule.apply(m.match));
	}

	public Collection<Optional<M>> applyNonZeroMappings(final boolean doUpdate) {
		return applyMappings(getNonZeroVariableMappings(), m -> rule.apply(m.match, doUpdate));
	}

	public Collection<Optional<M>> applyMappings(final Function<Integer, Boolean> predicate, final boolean doUpdate) {
		return applyMappings(getMappings(predicate), m -> rule.apply(m.match, doUpdate));
	}

	private Collection<Optional<M>> applyMappings(Collection<GTM> selectedMappings,
			Function<GTM, Optional<M>> ruleApplication) {

		if (engine.getTracer().isTracingEnabled())
			ruleApplication = wrapRuleApplicationForTracing(ruleApplication);

		return selectedMappings.stream() //
				.map(this::updateRuleParametersForMapping) //
				.map(ruleApplication) //
				.collect(Collectors.toSet());
	}

	private GTM updateRuleParametersForMapping(GTM mapping) {
		if (mapping.hasBoundVariables()) {
			final Map<String, Object> parameters = rule.getParameters();
			mapping.getBoundVariables().forEach((name, var) -> {
				parameters.put(internalVarToParamName.get(name), var.getValue());
			});
		}
		return mapping;
	}

	private Function<GTM, Optional<M>> wrapRuleApplicationForTracing(Function<GTM, Optional<M>> ruleApplication) {
		return mapping -> {
			Optional<M> matchAfterRule = ruleApplication.apply(mapping);
			if (matchAfterRule.isPresent())
				engine.getTracer().mapLpVariable2Output(mapping.getName(), matchAfterRule.get().toIMatch());
			return matchAfterRule;
		};
	}

	protected abstract GTM convertMatch(final String milpVariable, final M match);

	protected void addMapping(final M match) {
		if (match2Mappings.containsKey(match))
			return;

		GTM mapping = convertMatch(this.mapping.getName() + "#" + mappingCounter++, match);
		match2Mappings.put(match, mapping);
		super.putMapping(mapping);
	}

	protected void removeMapping(final M match) {
		GTM mapping = match2Mappings.get(match);
		if (mapping == null)
			return;

		match2Mappings.remove(match);
		super.removeMapping(mapping);
	}

	protected void init() {
		rule.subscribeAppearing(appearConsumer);
		rule.subscribeDisappearing(disappearConsumer);
	}

	@Override
	protected void terminate() {
		rule.unsubscribeAppearing(appearConsumer);
		rule.unsubscribeDisappearing(disappearConsumer);
	}
}
