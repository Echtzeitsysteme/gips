package org.emoflon.gips.core.gt;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsMapper;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.gips.intermediate.GipsIntermediate.RuleMapping;
import org.emoflon.ibex.gt.engine.IBeXGTCoMatch;
import org.emoflon.ibex.gt.engine.IBeXGTCoPattern;
import org.emoflon.ibex.gt.engine.IBeXGTMatch;
import org.emoflon.ibex.gt.engine.IBeXGTPattern;
import org.emoflon.ibex.gt.engine.IBeXGTRule;

public abstract class GipsRuleMapper<GTM extends GipsGTMapping<M, P>, R extends IBeXGTRule<R, P, M, CP, CM>, P extends IBeXGTPattern<P, M>, M extends IBeXGTMatch<M, P>, CP extends IBeXGTCoPattern<CP, CM, R, P, M>, CM extends IBeXGTCoMatch<CM, CP, R, P, M>>
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

	public Collection<CM> applyNonZeroMappings() {
		return getNonZeroVariableMappings().stream().map(m -> {
			if (m.hasBoundVariables()) {
				Map<String, Object> parameters = rule.getParameters();
				m.getBoundVariables().forEach((name, var) -> {
					parameters.put(internalVarToParamName.get(name), var.getValue());
				});
			}
			return m;
		}).map(m -> rule.apply(m.match)).collect(Collectors.toSet());
	}

	public Collection<CM> applyMappings(Function<Integer, Boolean> predicate) {
		return getMappings(predicate).stream().map(m -> {
			if (m.hasBoundVariables()) {
				Map<String, Object> parameters = rule.getParameters();
				m.getBoundVariables().forEach((name, var) -> {
					parameters.put(internalVarToParamName.get(name), var.getValue());
				});
			}
			return m;
		}).map(m -> rule.apply(m.match)).collect(Collectors.toSet());
	}

	public Collection<CM> applyNonZeroMappings(final boolean doUpdate) {
		return getNonZeroVariableMappings().stream().map(m -> {
			if (m.hasBoundVariables()) {
				final Map<String, Object> parameters = rule.getParameters();
				m.getBoundVariables().forEach((name, var) -> {
					parameters.put(internalVarToParamName.get(name), var.getValue());
				});
			}
			return m;
		}).map(m -> rule.apply(m.match, doUpdate)).collect(Collectors.toSet());
	}

	public Collection<CM> applyMappings(final Function<Integer, Boolean> predicate, final boolean doUpdate) {
		return getMappings(predicate).stream().map(m -> {
			if (m.hasBoundVariables()) {
				final Map<String, Object> parameters = rule.getParameters();
				m.getBoundVariables().forEach((name, var) -> {
					parameters.put(internalVarToParamName.get(name), var.getValue());
				});
			}
			return m;
		}).map(m -> rule.apply(m.match, doUpdate)).collect(Collectors.toSet());
	}

	protected abstract GTM convertMatch(final String ilpVariable, final M match);

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
