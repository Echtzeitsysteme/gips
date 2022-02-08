package org.emoflon.roam.core;

import java.util.HashMap;
import java.util.Map;

import org.emoflon.ibex.gt.api.GraphTransformationAPI;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel;

public class RoamEngine {
	
	final protected GraphTransformationAPI eMoflonAPI;
	final protected RoamIntermediateModel roamModel;
	final protected Map<String, RoamMapper<?>> mappers = new HashMap<>();
	final protected TypeIndexer indexer;
	final protected Map<String, RoamConstraint> constraints = new HashMap<>();
	
	public RoamEngine(final GraphTransformationAPI eMoflonAPI, final RoamIntermediateModel roamModel) {
		this.eMoflonAPI = eMoflonAPI;
		this.roamModel = roamModel;
		this.indexer = new TypeIndexer(eMoflonAPI, roamModel);
	}
	
	public void addMapper(final RoamMapper<?> mapper) {
		mappers.put(mapper.getName(), mapper);
	}
	
	public RoamMapper<?> getMapper(final String mappingName) {
		return mappers.get(mappingName);
	}
	
	public Map<String, RoamMapper<?>> getMappers() {
		return mappers;
	}
	
	public void addConstraint(final RoamConstraint constraint) {
		constraints.put(constraint.getName(), constraint);
	}
	
	public Map<String, RoamConstraint> getConstraints() {
		return constraints;
	}
	
	public TypeIndexer getIndexer() {
		return indexer;
	}
	
	public void terminate() {
		indexer.terminate();
		mappers.forEach((name, mapper) -> mapper.terminate());
	}
}
