package org.emoflon.gips.core.gt;

import java.util.Collection;
import java.util.List;

import org.emoflon.ibex.gt.api.GraphTransformationMatch;

public interface PatternMatch2MappingSorter {

	Collection<GraphTransformationMatch<?, ?>> sort(GipsPatternMapper<?, ?, ?> mapper,
			List<GraphTransformationMatch<?, ?>> matches);

}
