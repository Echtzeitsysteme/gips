package org.emoflon.roam.build.transformation.helper;

import org.emoflon.roam.intermediate.RoamIntermediate.FeatureExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.FeatureLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateFactory;
import org.emoflon.roam.roamslang.roamSLang.RoamFeatureExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamFeatureLit;
import org.emoflon.roam.roamslang.roamSLang.RoamFeatureNavigation;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamNavigation;

public final class RoamTransformationUtils {
	
	public static RoamStreamExpr getTerminalStreamExpression(final RoamStreamExpr expr) {
		if(expr instanceof RoamStreamNavigation nav) {
			return getTerminalStreamExpression(nav.getRight());
		} else {
			return expr;
		}
	}
	
	public static FeatureExpression transformFeatureExpression(final RoamFeatureExpr eFeature) {
		FeatureExpression feature = RoamIntermediateFactory.eINSTANCE.createFeatureExpression();
		if(eFeature instanceof RoamFeatureNavigation nav) {
			feature.setCurrent(createFeatureLiteral((RoamFeatureLit) nav.getLeft()));
			feature.setChild(transformFeatureExpression(nav.getRight()));
		} else {
			feature.setCurrent(createFeatureLiteral((RoamFeatureLit) eFeature));
		}
		return feature;
	}
	
	public static FeatureLiteral createFeatureLiteral(final RoamFeatureLit eFeature) {
		FeatureLiteral lit = RoamIntermediateFactory.eINSTANCE.createFeatureLiteral();
		lit.setFeature(eFeature.getFeature());
		return lit;
	}
}
