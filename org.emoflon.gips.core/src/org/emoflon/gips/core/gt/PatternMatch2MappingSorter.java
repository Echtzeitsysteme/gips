package org.emoflon.gips.core.gt;

import java.util.Collection;
import java.util.List;

import org.emoflon.ibex.gt.api.GraphTransformationMatch;

public interface PatternMatch2MappingSorter {

	/**
	 * 
	 * @param <M>
	 * @param mapper
	 * @param matches
	 * @return
	 */
	<M extends GraphTransformationMatch<M, ?>> Collection<M> sort(GipsPatternMapper<?, M, ?> mapper, List<M> matches);

}
