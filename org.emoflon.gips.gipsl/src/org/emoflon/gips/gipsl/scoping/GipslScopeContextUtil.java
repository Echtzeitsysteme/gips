package org.emoflon.gips.gipsl.scoping;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.gips.gipsl.gipsl.GipsAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsContextExpr;
import org.emoflon.gips.gipsl.gipsl.GipsFeatureExpr;
import org.emoflon.gips.gipsl.gipsl.GipsFeatureLit;
import org.emoflon.gips.gipsl.gipsl.GipsFeatureNavigation;
import org.emoflon.gips.gipsl.gipsl.GipsLambdaAttributeExpression;
import org.emoflon.gips.gipsl.gipsl.GipsMapping;
import org.emoflon.gips.gipsl.gipsl.GipsMappingAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsMappingContext;
import org.emoflon.gips.gipsl.gipsl.GipsNodeAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsPatternContext;
import org.emoflon.gips.gipsl.gipsl.GipsSelect;
import org.emoflon.gips.gipsl.gipsl.GipsStreamExpr;
import org.emoflon.gips.gipsl.gipsl.GipsTypeCast;
import org.emoflon.gips.gipsl.gipsl.GipsTypeContext;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;
import org.emoflon.gips.gipsl.gipsl.impl.GipsConstraintImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsContextExprImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsMappingAttributeExprImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsObjectiveImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsSelectImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsStreamArithmeticImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsStreamNavigationImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsStreamSetImpl;

public final class GipslScopeContextUtil {

	public static boolean isGipsMapping(final EObject context, final EReference reference) {
		return context instanceof GipsMapping;
	}

	public static boolean isGipsPatternContext(EObject context, EReference reference) {
		return context instanceof GipsPatternContext;
	}

	public static boolean isGipsMappingContext(final EObject context, final EReference reference) {
		return context instanceof GipsMappingContext;
	}

	public static boolean isGipsTypeContext(final EObject context, final EReference reference) {
		return context instanceof GipsTypeContext;
	}

	public static boolean isGipsMappingAttributeExprMapping(final EObject context, final EReference reference) {
		return context instanceof GipsMappingAttributeExpr
				&& reference == GipslPackage.Literals.GIPS_MAPPING_ATTRIBUTE_EXPR__MAPPING;
	}

	public static boolean isGipsMappingAttributeExprNode(final EObject context, final EReference reference) {
		return context instanceof GipsMappingAttributeExpr
				&& reference == GipslPackage.Literals.GIPS_NODE_ATTRIBUTE_EXPR__NODE;
	}

	public static boolean isGipsContextExprNode(final EObject context, final EReference reference) {
		return context instanceof GipsContextExpr && reference == GipslPackage.Literals.GIPS_NODE_ATTRIBUTE_EXPR__NODE;
	}

	public static boolean isGipsContextExprFeature(final EObject context, final EReference reference) {
		return context instanceof GipsContextExpr && reference == GipslPackage.Literals.GIPS_FEATURE_LIT__FEATURE;
	}

	public static boolean isGipsNodeAttributeExprNode(final EObject context, final EReference reference) {
		return context instanceof GipsNodeAttributeExpr
				&& reference == GipslPackage.Literals.GIPS_NODE_ATTRIBUTE_EXPR__NODE;
	}

	public static boolean isGipsNodeAttributeExprFeature(final EObject context, final EReference reference) {
		return context instanceof GipsNodeAttributeExpr && reference == GipslPackage.Literals.GIPS_FEATURE_LIT__FEATURE;
	}

	public static boolean isGipsLambdaAttributeExpressionVariable(final EObject context, final EReference reference) {
		return context instanceof GipsLambdaAttributeExpression
				&& reference == GipslPackage.Literals.GIPS_LAMBDA_ATTRIBUTE_EXPRESSION__VAR;
	}

	public static boolean isGipsLambdaAttributeExpression(final EObject context, final EReference reference) {
		return context instanceof GipsLambdaAttributeExpression
				&& reference == GipslPackage.Literals.GIPS_FEATURE_LIT__FEATURE;
	}

	public static boolean isGipsSelect(final EObject context, final EReference reference) {
		return context instanceof GipsSelect;
	}

	public static boolean isGipsFeatureNavigationFeature(final EObject context, final EReference reference) {
		return context instanceof GipsFeatureNavigation && reference == GipslPackage.Literals.GIPS_FEATURE_LIT__FEATURE;
	}

	public static boolean isGipsFeatureLit(final EObject context, final EReference reference) {
		return context instanceof GipsFeatureLit;
	}

	public static boolean isGipsTypeCast(final EObject context, final EReference reference) {
		return context instanceof GipsTypeCast;
	}

	public static Object getContainer(EObject node, Set<Class<?>> classes) {
		EObject current = node;
		do {
			current = current.eContainer();
		} while (current != null && !(classes.contains(current.getClass())));

		return current;
	}

	public static EObject getContextType(final GipsContextExpr expr) {
		Set<Class<?>> classes = Set.of(GipsConstraintImpl.class, GipsObjectiveImpl.class);
		EObject root = (EObject) GipslScopeContextUtil.getContainer(expr, classes);
		if (root instanceof GipsConstraint constr) {
			return constr.getContext();
		} else if (root instanceof GipsObjectiveImpl obj) {
			return obj.getContext();
		} else {
			return null;
		}
	}

	public static GipsFeatureLit findLeafExpression(final GipsFeatureExpr expr) {
		if (expr instanceof GipsFeatureLit lit) {
			return lit;
		} else if (expr instanceof GipsFeatureNavigation nav) {
			return findLeafExpression(nav.getRight());
		} else {
			return null;
		}
	}

	public static GipsAttributeExpr getStreamRootContainer(GipsLambdaAttributeExpression context) {
		Set<Class<?>> classes = Set.of(GipsContextExprImpl.class, GipsMappingAttributeExprImpl.class);
		return (GipsAttributeExpr) GipslScopeContextUtil.getContainer(context, classes);
	}

	public static GipsStreamExpr getStreamIteratorContainer(GipsLambdaAttributeExpression context) {
		Set<Class<?>> classes = Set.of(GipsStreamNavigationImpl.class, GipsStreamSetImpl.class, GipsSelectImpl.class,
				GipsStreamArithmeticImpl.class);
		return (GipsStreamExpr) GipslScopeContextUtil.getContainer(context, classes);
	}

	public static GipsStreamExpr getStreamIteratorNavigationRoot(GipsLambdaAttributeExpression context) {
		Set<Class<?>> classes = Set.of(GipsStreamNavigationImpl.class);
		GipsStreamExpr root = (GipsStreamExpr) GipslScopeContextUtil.getContainer(context, classes);
		if (root != null)
			return root;

		classes.add(GipsStreamSetImpl.class);
		classes.add(GipsSelectImpl.class);
		classes.add(GipsStreamArithmeticImpl.class);
		return (GipsStreamExpr) GipslScopeContextUtil.getContainer(context, classes);
	}

	public static EObject getStreamContainer(GipsLambdaAttributeExpression context) {
		Set<Class<?>> classes = Set.of(GipsContextExprImpl.class, GipsMappingAttributeExprImpl.class,
				GipsStreamNavigationImpl.class, GipsStreamSetImpl.class, GipsSelectImpl.class,
				GipsStreamArithmeticImpl.class);
		return (EObject) GipslScopeContextUtil.getContainer(context, classes);
	}

}
