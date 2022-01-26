package org.emoflon.roam.build.transformation;

import java.util.HashMap;
import java.util.Map;

import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel;
import org.emoflon.roam.roamslang.roamSLang.EditorGTFile;
import org.emoflon.roam.roamslang.roamSLang.RoamMapping;

public record RoamTransformationData(RoamIntermediateModel model, EditorGTFile roamSlangFile, Map<EditorPattern, IBeXRule> ePattern2Rule, 
		Map<EditorNode, IBeXNode> eNode2Node, Map<RoamMapping, Mapping> eMapping2Mapping) {
	
	public RoamTransformationData(final RoamIntermediateModel model, final EditorGTFile roamSlangFile) {
		this(model, roamSlangFile, new HashMap<>(), new HashMap<>(), new HashMap<>());
	}
}
