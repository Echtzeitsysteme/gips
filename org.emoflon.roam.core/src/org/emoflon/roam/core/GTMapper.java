package org.emoflon.roam.core;

import org.emoflon.ibex.gt.api.GraphTransformationMatch;
import org.emoflon.ibex.gt.api.GraphTransformationPattern;
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping;

public abstract class GTMapper <GTM extends GTMapping<M,P>, M extends GraphTransformationMatch<M, P>, P extends GraphTransformationPattern<M, P>> extends RoamMapper {
	
	public GTMapper(final RoamEngine engine, final Mapping mapping) {
		super(engine, mapping);
	}

}
