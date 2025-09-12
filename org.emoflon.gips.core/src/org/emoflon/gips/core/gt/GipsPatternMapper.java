package org.emoflon.gips.core.gt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.gips.core.GipsEngine;
import org.emoflon.gips.core.GipsMapper;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.gt.api.GraphTransformationMatch;
import org.emoflon.ibex.gt.api.GraphTransformationPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;

public abstract class GipsPatternMapper<PM extends GipsGTMapping<M, P>, M extends GraphTransformationMatch<M, P>, P extends GraphTransformationPattern<M, P>>
		extends GipsMapper<PM> {

	final protected P pattern;
	final protected Map<M, PM> match2Mappings = Collections.synchronizedMap(new HashMap<>());
	final protected List<M> newMatches = Collections.synchronizedList(new ArrayList<>());
	protected int mappingCounter = 0;

	final protected Consumer<M> appearConsumer = this::addNewMatch;
	final protected Consumer<M> disappearConsumer = this::removeMapping;

	public GipsPatternMapper(GipsEngine engine, Mapping mapping, P pattern) {
		super(engine, mapping);
		this.pattern = pattern;
		this.init();
	}

	protected abstract PM convertMatch(final String milpVariable, final M match);

	protected void addNewMatch(M match) {
		if (match2Mappings.containsKey(match))
			return;

		newMatches.add(match);
	}

	protected void addMapping(M match) {
		if (match2Mappings.containsKey(match))
			return;

		PM mapping = convertMatch(this.mapping.getName() + "#" + mappingCounter++, match);
		match2Mappings.put(match, mapping);
		super.putMapping(mapping);
	}

	protected void removeMapping(M match) {
		PM mapping = match2Mappings.remove(match);
		if (mapping != null)
			super.removeMapping(mapping);
	}

	public P getGTPattern() {
		return pattern;
	}

	protected void init() {
		pattern.subscribeAppearing(appearConsumer);
		pattern.subscribeDisappearing(disappearConsumer);
	}

	@Override
	protected void terminate() {
		pattern.unsubscribeAppearing(appearConsumer);
		pattern.unsubscribeDisappearing(disappearConsumer);
	}

	public void addNewMatchesToMappings() {
		if (newMatches.isEmpty())
			return;

		var ibexContext = pattern.getPatternSet().getContextPatterns().stream() //
				.filter(p -> pattern.getPatternName().equals(p.getName())) //
				.filter(IBeXContextPattern.class::isInstance) //
				.map(p -> (IBeXContextPattern) p) //
				.findFirst().orElse(null);

		if (ibexContext != null) {
			Collection<String> nodeOrder = ibexContext.getSignatureNodes().stream() //
					.map(IBeXNode::getName) //
					.sorted() //
					.toList();

			Map<EObject, String> cache = new HashMap<>();

			newMatches.sort((a, b) -> {
				IMatch matchA = a.toIMatch();
				IMatch matchB = b.toIMatch();

				for (String nodeName : nodeOrder) {
					EObject nodeA = (EObject) matchA.get(nodeName);
					EObject nodeB = (EObject) matchB.get(nodeName);

					if (nodeA == nodeB)
						continue;

					if (nodeA != null && nodeB != null) {
						String pathA = cache.computeIfAbsent(nodeA,
								node -> EcoreUtil.getRelativeURIFragmentPath(null, node));
						String pathB = cache.computeIfAbsent(nodeB,
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
		}

		for (M match : newMatches)
			addMapping(match);
		newMatches.clear();
	}

}
