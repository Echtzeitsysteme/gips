package org.emoflon.gips.core.gt;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.gt.api.GraphTransformationMatch;
import org.emoflon.ibex.gt.api.GraphTransformationPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;

/**
 * Sorts a list of matches based on the
 * {@link EcoreUtil#getRelativeURIFragmentPath(EObject, EObject) relative URI}
 * of each node within a match.
 * <p>
 * Matches are found in a non-deterministic order. This means that, for multiple
 * runs with no change in the input, a match can have different variable names.
 * While this is not a problem, it makes it harder for humans to understand the
 * relationship between two different outputs with the same input.
 * <p>
 * The idea behind this sorter is to sort matches based on their 'location'
 * within the tree. A match is composed of one or more nodes. The 'location' of
 * a single node is defined by its relative path from the root.
 * <p>
 * To compare two matches, we compare the locations of their nodes. The order in
 * which the nodes are compared is defined by the names of the nodes, as defined
 * by the pattern on which a match is based.
 * <p>
 * Steps:
 * <ul>
 * <li>All of the matches provided belong to the same pattern.
 * <li>Retrieve the node names from the pattern and
 * {@link String#compareTo(String) sort} them.
 * <li>Compare the matches pairwise; iterate through the nodes in the given
 * order and {@link String#compareTo(String) compare} them based on their
 * relative URIs.
 * <li>If two relative URIs are the same, continue with the next node.
 * <li>Otherwise sort both matches based on the result.
 * <li>Repeat until matches are sorted.
 * </ul>
 */
public class PatternMatch2MappingSorterByURI implements PatternMatch2MappingSorter {

	@Override
	public <M extends GraphTransformationMatch<M, ?>> List<M> sort(GipsPatternMapper<?, M, ?> mapper, List<M> matches) {

		if (matches.isEmpty())
			return matches;

		GraphTransformationPattern<M, ?> pattern = mapper.getGTPattern();
		Collection<String> nodeOrder = pattern.getPatternSet().getContextPatterns().stream() //
				.filter(p -> pattern.getPatternName().equals(p.getName())) //
				.filter(IBeXContextPattern.class::isInstance) //
				.map(p -> (IBeXContextPattern) p) //
				.flatMap(p -> p.getSignatureNodes().stream()) //
				.map(IBeXNode::getName) //
				.distinct() //
				.sorted() //
				.toList();

		if (nodeOrder.isEmpty())
			return matches;

		Map<EObject, String> uriCache = new HashMap<>();

		matches.sort((a, b) -> {
			IMatch matchA = a.toIMatch();
			IMatch matchB = b.toIMatch();

			for (String nodeName : nodeOrder) {
				EObject nodeA = (EObject) matchA.get(nodeName);
				EObject nodeB = (EObject) matchB.get(nodeName);

				if (nodeA == nodeB)
					continue;

				if (nodeA != null && nodeB != null) {
					String pathA = uriCache.computeIfAbsent(nodeA,
							node -> EcoreUtil.getRelativeURIFragmentPath(null, node));
					String pathB = uriCache.computeIfAbsent(nodeB,
							node -> EcoreUtil.getRelativeURIFragmentPath(null, node));

					if (pathA == pathB) {
						continue;
					} else if (pathB == null) {
						return -1; // nodeA != null && nodeB == null
					} else if (pathA == null) {
						return 1; // nodeA == null && nodeB != null
					}

					var result = pathA.compareTo(pathB);
					if (result != 0)
						return result;

				} else if (nodeA != null) {
					return -1; // nodeA != null && nodeB == null
				} else {
					return 1; // nodeA == null && nodeB != null
				}
			}

			return 0;
		});

		return matches;
	}

}
