package org.emoflon.roam.core.gt;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.emoflon.ibex.gt.api.GraphTransformationMatch;
import org.emoflon.ibex.gt.api.GraphTransformationRule;
import org.emoflon.roam.core.RoamEngine;
import org.emoflon.roam.core.RoamMapper;
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping;

public abstract class GTMapper<GTM extends GTMapping<M, R>, M extends GraphTransformationMatch<M, R>, R extends GraphTransformationRule<M, R>>
		extends RoamMapper<GTM> {

	final protected R rule;
	final protected Map<M, GTM> match2Mappings = Collections.synchronizedMap(new HashMap<>());
	private int mappingCounter = 0;

	public GTMapper(final RoamEngine engine, final Mapping mapping, final R rule) {
		super(engine, mapping);
		this.rule = rule;
	}

	public R getGTRule() {
		return rule;
	}

	public Collection<Optional<M>> applyNonZeroMappings() {
		return getNonZeroVariableMappings().stream().map(m -> rule.apply(m.match)).collect(Collectors.toSet());
	}

	public Collection<Optional<M>> applyMappings(Function<Integer, Boolean> predicate) {
		return getMappings(predicate).stream().map(m -> rule.apply(m.match)).collect(Collectors.toSet());
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

	@Override
	protected void init() {
		rule.subscribeAppearing(this::addMapping);
		rule.subscribeDisappearing(this::removeMapping);
	}

	@Override
	protected void terminate() {
		rule.unsubscribeAppearing(this::addMapping);
		rule.unsubscribeDisappearing(this::removeMapping);
	}
}
