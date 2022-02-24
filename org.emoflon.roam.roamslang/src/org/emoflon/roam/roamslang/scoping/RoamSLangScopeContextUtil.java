package org.emoflon.roam.roamslang.scoping;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.roam.roamslang.roamSLang.RoamAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamConstraint;
import org.emoflon.roam.roamslang.roamSLang.RoamContextExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamFeatureExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamFeatureLit;
import org.emoflon.roam.roamslang.roamSLang.RoamFeatureNavigation;
import org.emoflon.roam.roamslang.roamSLang.RoamLambdaAttributeExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamMapping;
import org.emoflon.roam.roamslang.roamSLang.RoamMappingAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamMappingContext;
import org.emoflon.roam.roamslang.roamSLang.RoamMatchContext;
import org.emoflon.roam.roamslang.roamSLang.RoamNodeAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamSLangPackage;
import org.emoflon.roam.roamslang.roamSLang.RoamSelect;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamTypeCast;
import org.emoflon.roam.roamslang.roamSLang.RoamTypeContext;
import org.emoflon.roam.roamslang.roamSLang.impl.RoamConstraintImpl;
import org.emoflon.roam.roamslang.roamSLang.impl.RoamContextExprImpl;
import org.emoflon.roam.roamslang.roamSLang.impl.RoamMappingAttributeExprImpl;
import org.emoflon.roam.roamslang.roamSLang.impl.RoamObjectiveImpl;
import org.emoflon.roam.roamslang.roamSLang.impl.RoamSelectImpl;
import org.emoflon.roam.roamslang.roamSLang.impl.RoamStreamArithmeticImpl;
import org.emoflon.roam.roamslang.roamSLang.impl.RoamStreamNavigationImpl;
import org.emoflon.roam.roamslang.roamSLang.impl.RoamStreamSetImpl;

public final class RoamSLangScopeContextUtil {
	
	public static boolean isRoamMapping(final EObject context, final EReference reference) {
		return context instanceof RoamMapping;
	}
	
	public static boolean isRoamMatchContext(EObject context, EReference reference) {
		return context instanceof RoamMatchContext;
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
	
	public static boolean isRoamTypeCast(final EObject context, final EReference reference) {
		return context instanceof RoamTypeCast;
	}
	
	public static Object getContainer(EObject node, Set<Class<?>> classes) {
		EObject current = node;
		do {
			current = current.eContainer();
		} while (current !=null && !(classes.contains(current.getClass())));
		
		return current;
	}
	
	public static EObject getContextType(final RoamContextExpr expr) {
		Set<Class<?>> classes = Set.of(RoamConstraintImpl.class, RoamObjectiveImpl.class);
		EObject root = (EObject) RoamSLangScopeContextUtil.getContainer(expr, classes);
		if(root instanceof RoamConstraint constr) {
			return constr.getContext();
		} else if(root instanceof RoamObjectiveImpl obj) {
			return obj.getContext();
		} else {
			return null;
		}
	}
	
	public static RoamFeatureLit findLeafExpression(final RoamFeatureExpr expr) {
		if(expr instanceof RoamFeatureLit lit) {
			return lit;
		} else if (expr instanceof RoamFeatureNavigation nav) {
			return findLeafExpression(nav.getRight());
		} else {
			return null;
		}
	}
	
	public static RoamAttributeExpr getStreamRootContainer(RoamLambdaAttributeExpression context) {
		Set<Class<?>> classes = Set.of(RoamContextExprImpl.class, RoamMappingAttributeExprImpl.class);
		return (RoamAttributeExpr) RoamSLangScopeContextUtil.getContainer(context, classes);
	}
	
	public static RoamStreamExpr getStreamIteratorContainer(RoamLambdaAttributeExpression context) {
		Set<Class<?>> classes = Set.of(RoamStreamNavigationImpl.class, RoamStreamSetImpl.class, RoamSelectImpl.class,
				RoamStreamArithmeticImpl.class);
		return (RoamStreamExpr) RoamSLangScopeContextUtil.getContainer(context, classes);
	}
	
	public static RoamStreamExpr getStreamIteratorNavigationRoot(RoamLambdaAttributeExpression context) {
		Set<Class<?>> classes = Set.of(RoamStreamNavigationImpl.class);
		RoamStreamExpr root = (RoamStreamExpr) RoamSLangScopeContextUtil.getContainer(context, classes);
		if(root != null)
			return root;
		
		classes.add(RoamStreamSetImpl.class);
		classes.add(RoamSelectImpl.class);
		classes.add(RoamStreamArithmeticImpl.class);
		return (RoamStreamExpr) RoamSLangScopeContextUtil.getContainer(context, classes);
	}
	
	public static EObject getStreamContainer(RoamLambdaAttributeExpression context) {
		Set<Class<?>> classes = Set.of(RoamContextExprImpl.class, RoamMappingAttributeExprImpl.class,
				RoamStreamNavigationImpl.class, RoamStreamSetImpl.class, RoamSelectImpl.class,
				RoamStreamArithmeticImpl.class);
		return (EObject) RoamSLangScopeContextUtil.getContainer(context, classes);
	}

	
}
