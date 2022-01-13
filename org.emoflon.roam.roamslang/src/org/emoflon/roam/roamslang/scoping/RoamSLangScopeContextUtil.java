package org.emoflon.roam.roamslang.scoping;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.roam.roamslang.roamSLang.RoamMappingContext;
import org.emoflon.roam.roamslang.roamSLang.RoamNodeAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamSLangPackage;
import org.emoflon.roam.roamslang.roamSLang.RoamSelect;
import org.emoflon.roam.roamslang.roamSLang.RoamTypeContext;
import org.emoflon.roam.roamslang.roamSLang.RoamContextExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamFeatureLit;
import org.emoflon.roam.roamslang.roamSLang.RoamFeatureNavigation;
import org.emoflon.roam.roamslang.roamSLang.RoamLambdaAttributeExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamMapping;
import org.emoflon.roam.roamslang.roamSLang.RoamMappingAttributeExpr;

public final class RoamSLangScopeContextUtil {
	
	public static boolean isRoamMapping(final EObject context, final EReference reference) {
		return context instanceof RoamMapping;
	}
	
	public static boolean isRoamMappingContext(final EObject context, final EReference reference) {
		return context instanceof RoamMappingContext;
	}
	
	public static boolean isRoamTypeContext(final EObject context, final EReference reference) {
		return context instanceof RoamTypeContext;
	}
	
	public static boolean isRoamMappingAttributeExprMapping(final EObject context, final EReference reference) {
		return context instanceof RoamMappingAttributeExpr && reference == RoamSLangPackage.Literals.ROAM_MAPPING_ATTRIBUTE_EXPR__MAPPING;
	}
	
	public static boolean isRoamMappingAttributeExprNode(final EObject context, final EReference reference) {
		return context instanceof RoamMappingAttributeExpr && reference == RoamSLangPackage.Literals.ROAM_NODE_ATTRIBUTE_EXPR__NODE;
	}
	
	public static boolean isRoamContextExprNode(final EObject context, final EReference reference) {
		return context instanceof RoamContextExpr && reference == RoamSLangPackage.Literals.ROAM_NODE_ATTRIBUTE_EXPR__NODE;
	}
	
	public static boolean isRoamContextExprFeature(final EObject context, final EReference reference) {
		return context instanceof RoamContextExpr && reference == RoamSLangPackage.Literals.ROAM_FEATURE_LIT__FEATURE;
	}
	
	public static boolean isRoamNodeAttributeExprNode(final EObject context, final EReference reference) {
		return context instanceof RoamNodeAttributeExpr && reference == RoamSLangPackage.Literals.ROAM_NODE_ATTRIBUTE_EXPR__NODE;
	}
	
	public static boolean isRoamNodeAttributeExprFeature(final EObject context, final EReference reference) {
		return context instanceof RoamNodeAttributeExpr && reference == RoamSLangPackage.Literals.ROAM_FEATURE_LIT__FEATURE;
	}
	
	public static boolean isRoamLambdaAttributeExpression(final EObject context, final EReference reference) {
		return context instanceof RoamLambdaAttributeExpression && reference == RoamSLangPackage.Literals.ROAM_FEATURE_LIT__FEATURE;
	}
	
	public static boolean isRoamSelect(final EObject context, final EReference reference) {
		return context instanceof RoamSelect;
	}
	
	public static boolean isRoamFeatureNavigationFeature(final EObject context, final EReference reference) {
		return context instanceof RoamFeatureNavigation && reference == RoamSLangPackage.Literals.ROAM_FEATURE_LIT__FEATURE;
	}
	
	public static boolean isRoamFeatureLit(final EObject context, final EReference reference) {
		return context instanceof RoamFeatureLit;
	}
	
	
	public static Object getContainer(EObject node, Set<Class<?>> classes) {
		EObject current = node;
		do {
			current = current.eContainer();
		} while (current !=null && !(classes.contains(current.getClass())));
		
		return current;
	}
}
