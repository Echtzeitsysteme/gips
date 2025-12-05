package org.emoflon.gips.core.gt;

import java.util.List;

import org.emoflon.ibex.gt.api.GraphTransformationMatch;

public interface PatternMatch2MappingSorter {

	/**
	 * Performs a sorting operation just before the matches are
	 * {@link GipsPatternMapper#addMapping(GraphTransformationMatch) added} to the
	 * {@link GipsPatternMapper mapper}. The order of the elements affects the
	 * variable names assigned to them and may affect their order of appearance in
	 * any output.
	 * <p>
	 * <b>Note</b>:
	 * <ul>
	 * <li>The list of matches provided can be safely sorted and returned
	 * <li>Any returned match will be added to the mapper
	 * <li>Mapper is provided for context reason
	 * </ul>
	 * 
	 * @param mapper  which provides the matches, not null
	 * @param matches a list of matches, not null, contains no null elements
	 * @return a sorted list of matches, not null, contains no null elements
	 */
	<M extends GraphTransformationMatch<M, ?>> List<M> sort(GipsPatternMapper<?, M, ?> mapper, List<M> matches);

}
