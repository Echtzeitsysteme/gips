package org.emoflon.roam.core.gt;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.emoflon.ibex.gt.api.GraphTransformationMatch;
import org.emoflon.ibex.gt.api.GraphTransformationPattern;
import org.emoflon.roam.core.RoamEngine;
import org.emoflon.roam.core.RoamMapper;
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping;

public abstract class GTMapper<GTM extends GTMapping<M, P>, M extends GraphTransformationMatch<M, P>, P extends GraphTransformationPattern<M, P>>
		extends RoamMapper<GTM> {

	final protected Map<M, GTM> match2Mappings = Collections.synchronizedMap(new HashMap<>());
	private int mappingCounter = 0;

	public GTMapper(final RoamEngine engine, final Mapping mapping) {
		super(engine, mapping);
	}

	protected abstract GTM convertMatch(final String ilpVariable, final M match);

	public void addMapping(final M match) {
		if (match2Mappings.containsKey(match))
			return;

		GTM mapping = convertMatch(this.mapping.getName() + "#" + mappingCounter++, match);
		match2Mappings.put(match, mapping);
		super.putMapping(mapping);
	}

	public void removeMapping(final M match) {
		GTM mapping = match2Mappings.get(match);
		if (mapping == null)
			return;

		match2Mappings.remove(match);
		super.removeMapping(mapping);
	}

}
