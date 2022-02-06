package org.emoflon.roam.core.gt;

import org.emoflon.ibex.gt.api.GraphTransformationMatch;
import org.emoflon.ibex.gt.api.GraphTransformationPattern;
import org.emoflon.roam.core.RoamEngine;
import org.emoflon.roam.core.RoamMapper;
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping;

public abstract class GTMapper <GTM extends GTMapping<M,P>, M extends GraphTransformationMatch<M, P>, P extends GraphTransformationPattern<M, P>> extends RoamMapper<GTM> {
	
	public GTMapper(final RoamEngine engine, final Mapping mapping) {
		super(engine, mapping);
	}

	protected abstract GTM convertMatch(final M match);
	
	
}
