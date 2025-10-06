package org.emoflon.gips.core.gt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsMapper;
import org.emoflon.gips.core.api.GipsEngineAPI;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.ibex.gt.api.GraphTransformationMatch;
import org.emoflon.ibex.gt.api.GraphTransformationPattern;

public abstract class GipsPatternMapper<PM extends GipsGTMapping<M, P>, M extends GraphTransformationMatch<M, P>, P extends GraphTransformationPattern<M, P>>
		extends GipsMapper<PM> {

	final protected P pattern;
	final protected Map<M, PM> match2Mappings = Collections.synchronizedMap(new HashMap<>());
	protected int mappingCounter = 0;

	final protected List<M> unsortedMatches = Collections.synchronizedList(new ArrayList<>());
	protected boolean enableMatchSorting;

	protected Consumer<M> appearConsumer = this::addMapping;
	final protected Consumer<M> disappearConsumer = this::removeMapping;

	public GipsPatternMapper(GipsEngine engine, Mapping mapping, P pattern) {
		super(engine, mapping);
		this.pattern = pattern;
		this.init();
	}

	protected abstract PM convertMatch(final String milpVariable, final M match);

	protected void addMatchForSorting(M match) {
		if (match2Mappings.containsKey(match))
			return;
		unsortedMatches.add(match);
	}

	protected void addMapping(M match) {
		if (match2Mappings.containsKey(match))
			return;

		PM mapping = convertMatch(this.mapping.getName() + "#" + mappingCounter++, match);
		match2Mappings.put(match, mapping);
		super.putMapping(mapping);
	}

	protected void removeMapping(M match) {
		PM mapping = match2Mappings.remove(match);
		if (mapping != null)
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

	public void enableMatchSorting(boolean enableMatchSorting) {
		if (enableMatchSorting == this.enableMatchSorting)
			return;

		if (enableMatchSorting) {
			pattern.unsubscribeAppearing(appearConsumer);
			appearConsumer = this::addMatchForSorting;
			pattern.subscribeAppearing(appearConsumer);
		} else {
			pattern.unsubscribeAppearing(appearConsumer);
			appearConsumer = this::addMapping;
			pattern.subscribeAppearing(appearConsumer);
		}
		this.enableMatchSorting = enableMatchSorting;
	}

	@SuppressWarnings("rawtypes")
	public void sortMatchesAndCreateMappings() {
		if (unsortedMatches.isEmpty())
			return;

		Collection<M> sortedMatches = ((GipsEngineAPI) engine).getMatchSorter().sort(this, unsortedMatches);
		for (M match : sortedMatches)
			addMapping(match);

		unsortedMatches.clear();
	}

}
