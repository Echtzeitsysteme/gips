package org.emoflon.roam.core;

import java.util.HashSet;
import java.util.Set;

import org.emoflon.ibex.gt.api.GraphTransformationAPI;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel;

public class RoamEngine {
	
	final protected GraphTransformationAPI eMoflonAPI;
	final protected RoamIntermediateModel roamModel;
	final protected Set<RoamMapper> mappers = new HashSet<>();
	final protected TypeIndexer indexer;
	
	protected RoamEngine(final GraphTransformationAPI eMoflonAPI, final RoamIntermediateModel roamModel) {
		this.eMoflonAPI = eMoflonAPI;
		this.roamModel = roamModel;
		this.indexer = new TypeIndexer(eMoflonAPI, roamModel);
	}
	
	protected void addMapper(final RoamMapper mapper) {
		mappers.add(mapper);
	}
	
	protected void terminate() {
		indexer.terminate();
	}
}
