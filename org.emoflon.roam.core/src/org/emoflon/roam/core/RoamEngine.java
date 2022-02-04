package org.emoflon.roam.core;

import org.emoflon.ibex.gt.api.GraphTransformationAPI;

public class RoamEngine {
	
	/**
	 * API for the graph transformation tool
	 */
	final protected GraphTransformationAPI eMoflonAPI;
	
	public RoamEngine(final GraphTransformationAPI eMoflonAPI) {
		this.eMoflonAPI = eMoflonAPI;
	}

}
