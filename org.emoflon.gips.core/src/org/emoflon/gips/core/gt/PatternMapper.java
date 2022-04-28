package org.emoflon.gips.core.gt;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.emoflon.ibex.gt.api.GraphTransformationMatch;
import org.emoflon.ibex.gt.api.GraphTransformationPattern;
import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsMapper;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;

public abstract class PatternMapper<GTM extends GTMapping<M, P>, M extends GraphTransformationMatch<M, P>, P extends GraphTransformationPattern<M, P>>
		extends GipsMapper<GTM> {

	final protected P pattern;
	final protected Map<M, GTM> match2Mappings = Collections.synchronizedMap(new HashMap<>());
	private int mappingCounter = 0;

	public PatternMapper(final GipsEngine engine, final Mapping mapping, final P pattern) {
		super(engine, mapping);
		this.pattern = pattern;
		// TODO: this.init() see GTMapper.java
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

	public P getGTPattern() {
		return pattern;
	}

}
