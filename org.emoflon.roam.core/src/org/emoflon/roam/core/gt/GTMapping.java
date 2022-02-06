package org.emoflon.roam.core.gt;

import org.emoflon.ibex.gt.api.GraphTransformationMatch;
import org.emoflon.ibex.gt.api.GraphTransformationPattern;
import org.emoflon.roam.core.RoamMapping;

public abstract class GTMapping <M extends GraphTransformationMatch<M, P>, P extends GraphTransformationPattern<M, P>> extends RoamMapping {

	final public M match;
	
	protected GTMapping(final String ilpVariable, final M match) {
		super(ilpVariable);
		this.match = match;
	}

}
