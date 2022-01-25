package org.emoflon.roam.build.transformation;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.utils.GTEditorPatternUtils;
import org.emoflon.ibex.gt.transformations.EditorToIBeXPatternTransformation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateFactory;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel;
import org.emoflon.roam.roamslang.roamSLang.EditorGTFile;

public class RoamToIntermediate {
	protected RoamIntermediateFactory factory = RoamIntermediateFactory.eINSTANCE;
	final protected RoamIntermediateModel model = factory.createRoamIntermediateModel();
	final protected EditorGTFile roamSlangFile;
	
	protected Map<EditorPattern, IBeXRule> ePattern2Rule;
	protected Map<EditorNode, IBeXNode> eNode2Node;
	
	public RoamToIntermediate(final EditorGTFile roamSlangFile) {
		this.roamSlangFile = roamSlangFile;
	}
	
	public RoamIntermediateModel transform() {
		//transform GT to IBeXPatterns
		EditorToIBeXPatternTransformation ibexTransformer = new EditorToIBeXPatternTransformation();
		model.setIbexModel(ibexTransformer.transform(roamSlangFile));
		mapGT2IBeXElements();
		
		return model;
	}
	
	protected void transformMappings() {
		roamSlangFile.getMappings().forEach(eMapping -> {
			Mapping mapping = factory.createMapping();
			mapping.setName(eMapping.getName());
			mapping.setRule(ePattern2Rule.get(eMapping.getRule()));
			model.getVariables().add(mapping);
		});
	}
	
	protected void mapGT2IBeXElements() {
		ePattern2Rule = new HashMap<>();
		for(EditorPattern ePattern : roamSlangFile.getPatterns().stream()
				.filter(pattern -> GTEditorPatternUtils.containsCreatedOrDeletedElements(pattern))
				.collect(Collectors.toList())) {
			for(IBeXRule rule : model.getIbexModel().getRuleSet().getRules()) {
				if(rule.getName().equals(ePattern.getName())) {
					ePattern2Rule.put(ePattern, rule);
					for(EditorNode eNode : ePattern.getNodes()) {
						for(IBeXNode node : toContextPattern(rule.getLhs()).getLocalNodes()) {
							if(eNode.getName().equals(node.getName())) {
								eNode2Node.put(eNode, node);
							}
						}
					}
				}
			}
		}
	}
	
	public static IBeXContextPattern toContextPattern(final IBeXContext context) {
		if(context instanceof IBeXContextAlternatives alt) {
			return alt.getContext();
		} else {
			return (IBeXContextPattern)context;
		}
	}
}
