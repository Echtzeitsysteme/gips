package org.emoflon.gips.core.gt;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.gt.api.GraphTransformationMatch;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;

public class PatternMatch2MappingURISorter implements PatternMatch2MappingSorter {

	@Override
	public <M extends GraphTransformationMatch<M, ?>> Collection<M> sort(GipsPatternMapper<?, M, ?> mapper,
			List<M> matches) {

		if (matches.isEmpty())
			return matches;

		var pattern = mapper.getGTPattern();
		IBeXContextPattern ibexContext = pattern.getPatternSet().getContextPatterns().stream() //
				.filter(p -> pattern.getPatternName().equals(p.getName())) //
				.filter(IBeXContextPattern.class::isInstance) //
				.map(p -> (IBeXContextPattern) p) //
				.findFirst().orElse(null);

		if (ibexContext == null)
			return matches;

		Collection<String> nodeOrder = ibexContext.getSignatureNodes().stream() //
				.map(IBeXNode::getName) //
				.sorted() //
				.toList();

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
					} else if (pathA == null) {
						return 1;
					} else if (pathB == null) {
						return -1;
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
