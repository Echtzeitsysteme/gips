package org.emoflon.gips.core.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.emoflon.gips.core.api.GipsEngineAPI;
import org.emoflon.gips.core.gt.GTMapper;
import org.emoflon.ibex.gt.api.GraphTransformationMatch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Utility class for match-related methods.
 */
public class MatchUtil {

	/**
	 * Match property record for the JSON representation of match properties.
	 */
	public record MatchProperty(String name, long value) {
	}

	/**
	 * Record for a list of properties together with the (M)ILP variable value
	 * calculated for this specific match.
	 */
	public record Match2SolutionMapping(List<MatchProperty> properties, int selected) {
	}

	/**
	 * Output record that contains a list of the match 2 solution mappings. This
	 * gets used by the JSON export.
	 */
	public record MatchOutputRecord(List<Match2SolutionMapping> match2Solution) {
	}

	/**
	 * Interface that describes a method to convert a given GT match into a List of
	 * match properties.
	 */
	public interface IMatchConverter {
		@SuppressWarnings("rawtypes")
		public List<MatchProperty> convertMatch(final GraphTransformationMatch match);
	}

	/**
	 * Converts all matches of a given GT mapper to a JSON string representation
	 * using the provided match converter and the GIPS engine API.
	 * 
	 * @param mapper    GT mapper that is used to select the correct pattern in the
	 *                  API.
	 * @param converter Match converter that is used to convert the GT matches to
	 *                  their property representation.
	 * @param api       GIPS engine API which provides the GT matches.
	 * @return JSON representation as string.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String matchesSolutionToJson(final GTMapper<?, ?, ?> mapper, IMatchConverter converter,
			final GipsEngineAPI api) {
		final Map<GraphTransformationMatch, Boolean> matches = api.getMatchesWithSolutionOf(mapper);

		final List<Match2SolutionMapping> match2SolutionJson = new LinkedList<>();
		matches.forEach((m, b) -> {
			match2SolutionJson.add(new Match2SolutionMapping(converter.convertMatch(m), b ? 1 : 0));
		});

		final Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(new MatchOutputRecord(match2SolutionJson));
	}

}
