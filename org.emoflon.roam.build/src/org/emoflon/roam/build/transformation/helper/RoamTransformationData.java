package org.emoflon.roam.build.transformation.helper;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;
import org.emoflon.roam.intermediate.RoamIntermediate.Constraint;
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping;
import org.emoflon.roam.intermediate.RoamIntermediate.Objective;
import org.emoflon.roam.intermediate.RoamIntermediate.Pattern;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateFactory;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel;
import org.emoflon.roam.intermediate.RoamIntermediate.SetOperation;
import org.emoflon.roam.intermediate.RoamIntermediate.Type;
import org.emoflon.roam.roamslang.roamSLang.EditorGTFile;
import org.emoflon.roam.roamslang.roamSLang.RoamConstraint;
import org.emoflon.roam.roamslang.roamSLang.RoamMapping;
import org.emoflon.roam.roamslang.roamSLang.RoamObjective;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamExpr;

public record RoamTransformationData(
		RoamIntermediateModel model, 							//
		EditorGTFile roamSlangFile, 							//
		Map<EditorPattern, IBeXRule> ePattern2Rule, 			//
		Map<EditorPattern, IBeXContext> ePattern2Context,		//
		Map<EditorPattern, Pattern> ePattern2Pattern,			//
		Map<EditorNode, IBeXNode> eNode2Node, 					//
		Map<RoamMapping, Mapping> eMapping2Mapping, 			//
		Map<RoamConstraint, Constraint> eConstraint2Constraint,	//
		Map<RoamStreamExpr, SetOperation> eStream2SetOp, 		//
		Map<EClass, Type> eType2Type, 							//
		Map<RoamObjective, Objective> eObjective2Objective) {
	
	public RoamTransformationData(final RoamIntermediateModel model, final EditorGTFile roamSlangFile) {
		this(model, roamSlangFile, new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), 
				new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());
	}
	
	public Type getType(final EClass eType) {
		Type type = eType2Type.get(eType);
		if(type == null) {
			type = RoamIntermediateFactory.eINSTANCE.createType();
			type.setName(eType.getName());
			type.setType(eType);
			eType2Type.put(eType, type);
		}
		return type;
	}
	
	public Pattern getPattern(final EditorPattern pattern) {
		Pattern p = ePattern2Pattern.get(pattern);
		if(p == null) {
			p = RoamIntermediateFactory.eINSTANCE.createPattern();
			p.setName(pattern.getName());
			p.setPattern(ePattern2Context.get(pattern));
			ePattern2Pattern.put(pattern, p);
		}
		return p;
	}
}
