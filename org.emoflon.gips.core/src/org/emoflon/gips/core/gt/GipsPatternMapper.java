package org.emoflon.gips.core.gt;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsMapper;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.ibex.gt.api.GraphTransformationMatch;
import org.emoflon.ibex.gt.api.GraphTransformationPattern;

public abstract class GipsPatternMapper<PM extends GipsGTMapping<M, P>, M extends GraphTransformationMatch<M, P>, P extends GraphTransformationPattern<M, P>>
		extends GipsMapper<PM> {

	final protected P pattern;
	final protected Map<M, PM> match2Mappings = Collections.synchronizedMap(new HashMap<>());
	private int mappingCounter = 0;

	final protected Consumer<M> appearConsumer = this::addMapping;
	final protected Consumer<M> disappearConsumer = this::removeMapping;

	public GipsPatternMapper(final GipsEngine engine, final Mapping mapping, final P pattern) {
		super(engine, mapping);
		this.pattern = pattern;
		this.init();
	}

	protected abstract PM convertMatch(final String milpVariable, final M match);

	protected void addMapping(final M match) {
		if (match2Mappings.containsKey(match))
			return;

		PM mapping = convertMatch(this.mapping.getName() + "#" + mappingCounter++, match);
		match2Mappings.put(match, mapping);
		super.putMapping(mapping);
	}

	protected void removeMapping(final M match) {
		PM mapping = match2Mappings.get(match);
		if (mapping == null)
			return;

		match2Mappings.remove(match);
		super.removeMapping(mapping);
	}

	public P getGTPattern() {
		return pattern;
	}

	protected void init() {
		pattern.subscribeAppearing(appearConsumer);
		pattern.subscribeDisappearing(disappearConsumer);
	}

	@Override
	protected void terminate() {
		pattern.unsubscribeAppearing(appearConsumer);
		pattern.unsubscribeDisappearing(disappearConsumer);
	}

}
