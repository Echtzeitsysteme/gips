package org.emoflon.roam.roamslang.scoping;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.roam.roamslang.roamSLang.RoamMappingContext;
import org.emoflon.roam.roamslang.roamSLang.RoamNodeAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamTypeContext;
import org.emoflon.roam.roamslang.roamSLang.RoamFeatureLit;
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
	
	public static boolean isRoamMappingAttributeExpr(final EObject context, final EReference reference) {
		return context instanceof RoamMappingAttributeExpr;
	}
	
	public static boolean isRoamNodeAttributeExpr(final EObject context, final EReference reference) {
		return context instanceof RoamNodeAttributeExpr;
	}
	
	public static boolean isRoamFeatureLit(final EObject context, final EReference reference) {
		return context instanceof RoamFeatureLit;
	}
}
