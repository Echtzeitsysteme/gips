package org.emoflon.gips.gipsl.scoping;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.gips.gipsl.gipsl.GipsAndBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpr;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsBracketBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBracketExpr;
import org.emoflon.gips.gipsl.gipsl.GipsConstant;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsContains;
import org.emoflon.gips.gipsl.gipsl.GipsContextExpr;
import org.emoflon.gips.gipsl.gipsl.GipsExpArithmeticExpr;
import org.emoflon.gips.gipsl.gipsl.GipsFeatureExpr;
import org.emoflon.gips.gipsl.gipsl.GipsFeatureLit;
import org.emoflon.gips.gipsl.gipsl.GipsFeatureNavigation;
import org.emoflon.gips.gipsl.gipsl.GipsImplicationBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsLambdaAttributeExpression;
import org.emoflon.gips.gipsl.gipsl.GipsLambdaSelfExpression;
import org.emoflon.gips.gipsl.gipsl.GipsMapping;
import org.emoflon.gips.gipsl.gipsl.GipsMappingAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsMappingContext;
import org.emoflon.gips.gipsl.gipsl.GipsNodeAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsNotBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsObjectiveExpression;
import org.emoflon.gips.gipsl.gipsl.GipsOrBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsPatternAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsPatternContext;
import org.emoflon.gips.gipsl.gipsl.GipsProductArithmeticExpr;
import org.emoflon.gips.gipsl.gipsl.GipsRelExpr;
import org.emoflon.gips.gipsl.gipsl.GipsSelect;
import org.emoflon.gips.gipsl.gipsl.GipsStreamArithmetic;
import org.emoflon.gips.gipsl.gipsl.GipsStreamBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsStreamExpr;
import org.emoflon.gips.gipsl.gipsl.GipsStreamNavigation;
import org.emoflon.gips.gipsl.gipsl.GipsStreamSet;
import org.emoflon.gips.gipsl.gipsl.GipsSumArithmeticExpr;
import org.emoflon.gips.gipsl.gipsl.GipsTypeAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsTypeCast;
import org.emoflon.gips.gipsl.gipsl.GipsTypeContext;
import org.emoflon.gips.gipsl.gipsl.GipsUnaryArithmeticExpr;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;
import org.emoflon.gips.gipsl.gipsl.ImportedPattern;
import org.emoflon.gips.gipsl.gipsl.impl.GipsConstraintImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsContainsImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsContextExprImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsMappingAttributeExprImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsObjectiveImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsPatternAttributeExprImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsSelectImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsStreamArithmeticImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsStreamNavigationImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsStreamSetImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsTypeAttributeExprImpl;

public final class GipslScopeContextUtil {

	public static boolean isPatternImportUri(final EObject context, final EReference reference) {
		return context instanceof ImportedPattern && reference == GipslPackage.Literals.IMPORTED_PATTERN__FILE;
	}

	public static boolean isPatternImportPattern(final EObject context, final EReference reference) {
		return context instanceof ImportedPattern && reference == GipslPackage.Literals.IMPORTED_PATTERN__PATTERN;
	}

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

	public static boolean isGipsPatternAttributeExprMapping(final EObject context, final EReference reference) {
		return context instanceof GipsPatternAttributeExpr
				&& reference == GipslPackage.Literals.GIPS_PATTERN_ATTRIBUTE_EXPR__PATTERN;
	}

	public static boolean isGipsTypeAttributeExprMapping(final EObject context, final EReference reference) {
		return context instanceof GipsTypeAttributeExpr
				&& reference == GipslPackage.Literals.GIPS_TYPE_ATTRIBUTE_EXPR__TYPE;
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

	public static boolean isGipsLambdaSelfExpressionVariable(final EObject context, final EReference reference) {
		return context instanceof GipsLambdaSelfExpression
				&& reference == GipslPackage.Literals.GIPS_LAMBDA_SELF_EXPRESSION__VAR;
	}

	public static boolean isGipsLambdaAttributeExpression(final EObject context, final EReference reference) {
		return context instanceof GipsLambdaAttributeExpression
				&& reference == GipslPackage.Literals.GIPS_FEATURE_LIT__FEATURE;
	}

	public static boolean isGipsSelect(final EObject context, final EReference reference) {
		return context instanceof GipsSelect;
	}

	public static boolean isGipsContains(final EObject context, final EReference reference) {
		return context instanceof GipsContains;
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
		Set<Class<?>> classes = Set.of(GipsContextExprImpl.class, GipsMappingAttributeExprImpl.class,
				GipsPatternAttributeExprImpl.class, GipsTypeAttributeExprImpl.class);
		return (GipsAttributeExpr) GipslScopeContextUtil.getContainer(context, classes);
	}

	public static GipsStreamExpr getStreamIteratorContainer(GipsLambdaAttributeExpression context) {
		Set<Class<?>> classes = Set.of(GipsStreamNavigationImpl.class, GipsStreamSetImpl.class, GipsSelectImpl.class,
				GipsStreamArithmeticImpl.class, GipsContainsImpl.class);
		return (GipsStreamExpr) GipslScopeContextUtil.getContainer(context, classes);
	}

	public static GipsAttributeExpr getStreamRootContainer(GipsLambdaSelfExpression context) {
		Set<Class<?>> classes = Set.of(GipsContextExprImpl.class, GipsMappingAttributeExprImpl.class,
				GipsPatternAttributeExprImpl.class, GipsTypeAttributeExprImpl.class);
		return (GipsAttributeExpr) GipslScopeContextUtil.getContainer(context, classes);
	}

	public static GipsStreamExpr getStreamIteratorContainer(GipsLambdaSelfExpression context) {
		Set<Class<?>> classes = Set.of(GipsStreamNavigationImpl.class, GipsStreamSetImpl.class, GipsSelectImpl.class,
				GipsStreamArithmeticImpl.class, GipsContainsImpl.class);
		return (GipsStreamExpr) GipslScopeContextUtil.getContainer(context, classes);
	}

	public static GipsStreamExpr getStreamIteratorNavigationRoot(GipsLambdaAttributeExpression context) {
		final Set<Class<?>> classes = new HashSet<>();
		classes.add(GipsStreamNavigationImpl.class);
		GipsStreamExpr root = (GipsStreamExpr) GipslScopeContextUtil.getContainer(context, classes);
		if (root != null)
			return root;

		classes.add(GipsStreamSetImpl.class);
		classes.add(GipsSelectImpl.class);
		classes.add(GipsStreamArithmeticImpl.class);
		classes.add(GipsContainsImpl.class);
		return (GipsStreamExpr) GipslScopeContextUtil.getContainer(context, classes);
	}

	public static GipsStreamExpr getStreamIteratorNavigationRoot(GipsLambdaSelfExpression context) {
		Set<Class<?>> classes = Set.of(GipsStreamNavigationImpl.class);
		GipsStreamExpr root = (GipsStreamExpr) GipslScopeContextUtil.getContainer(context, classes);
		if (root != null)
			return root;

		classes.add(GipsStreamSetImpl.class);
		classes.add(GipsSelectImpl.class);
		classes.add(GipsStreamArithmeticImpl.class);
		classes.add(GipsContainsImpl.class);
		return (GipsStreamExpr) GipslScopeContextUtil.getContainer(context, classes);
	}

	public static EObject getStreamContainer(GipsLambdaAttributeExpression context) {
		Set<Class<?>> classes = Set.of(GipsContextExprImpl.class, GipsMappingAttributeExprImpl.class,
				GipsPatternAttributeExprImpl.class, GipsTypeAttributeExprImpl.class, GipsStreamNavigationImpl.class,
				GipsStreamSetImpl.class, GipsSelectImpl.class, GipsStreamArithmeticImpl.class, GipsContainsImpl.class);
		return (EObject) GipslScopeContextUtil.getContainer(context, classes);
	}

	public static Set<GipsMapping> extractMappings(final GipsBoolExpr expr) {
		Set<GipsMapping> mappings = new HashSet<>();

		if (expr == null)
			return mappings;

		if (expr instanceof GipsImplicationBoolExpr implication) {
			mappings.addAll(extractMappings(implication.getLeft()));
			mappings.addAll(extractMappings(implication.getRight()));
		} else if (expr instanceof GipsOrBoolExpr or) {
			mappings.addAll(extractMappings(or.getLeft()));
			mappings.addAll(extractMappings(or.getRight()));
		} else if (expr instanceof GipsAndBoolExpr and) {
			mappings.addAll(extractMappings(and.getLeft()));
			mappings.addAll(extractMappings(and.getRight()));
		} else if (expr instanceof GipsNotBoolExpr not) {
			mappings.addAll(extractMappings(not.getOperand()));
		} else if (expr instanceof GipsBracketBoolExpr bracket) {
			mappings.addAll(extractMappings(bracket.getOperand()));
		} else if (expr instanceof GipsBooleanLiteral) {
			return mappings;
		} else if (expr instanceof GipsRelExpr relExpr) {
			mappings.addAll(extractMappings(relExpr));
		} else {
			throw new UnsupportedOperationException("Unknown boolean expression type: " + expr);
		}

		return mappings;

	}

	public static Set<GipsMapping> extractMappings(final GipsRelExpr expr) {
		Set<GipsMapping> mappings = new HashSet<>();

		if (expr == null)
			return mappings;

		if (expr.getRight() == null) {
			mappings.addAll(extractMappings(expr.getLeft()));
		} else {
			mappings.addAll(extractMappings(expr.getLeft()));
			mappings.addAll(extractMappings(expr.getRight()));
		}

		return mappings;
	}

	public static Set<GipsMapping> extractMappings(final GipsArithmeticExpr expr) {
		Set<GipsMapping> mappings = new HashSet<>();

		if (expr == null)
			return mappings;

		if (expr instanceof GipsSumArithmeticExpr sum) {
			mappings.addAll(extractMappings(sum.getLeft()));
			mappings.addAll(extractMappings(sum.getRight()));
		} else if (expr instanceof GipsProductArithmeticExpr product) {
			mappings.addAll(extractMappings(product.getLeft()));
			mappings.addAll(extractMappings(product.getRight()));
		} else if (expr instanceof GipsExpArithmeticExpr exp) {
			mappings.addAll(extractMappings(exp.getLeft()));
			mappings.addAll(extractMappings(exp.getRight()));
		} else if (expr instanceof GipsUnaryArithmeticExpr un) {
			mappings.addAll(extractMappings(un.getOperand()));
		} else if (expr instanceof GipsBracketExpr bracket) {
			mappings.addAll(extractMappings(bracket.getOperand()));
		} else if (expr instanceof GipsAttributeExpr atr) {
			mappings.addAll(extractMappings(atr));
		} else if (expr instanceof GipsObjectiveExpression) {
			return mappings;
		} else if (expr instanceof GipsArithmeticLiteral) {
			return mappings;
		} else if (expr instanceof GipsConstant) {
			return mappings;
		} else {
			throw new UnsupportedOperationException("Unknown arithmetic expression type: " + expr);
		}

		return mappings;
	}

	public static Set<GipsMapping> extractMappings(final GipsAttributeExpr expr) {
		Set<GipsMapping> mappings = new HashSet<>();

		if (expr == null)
			return mappings;

		if (expr instanceof GipsMappingAttributeExpr mappingAtr) {
			mappings.add(mappingAtr.getMapping());
			mappings.addAll(extractMappings(mappingAtr.getExpr()));
		} else if (expr instanceof GipsTypeAttributeExpr typeAtr) {
			mappings.addAll(extractMappings(typeAtr.getExpr()));
		} else if (expr instanceof GipsPatternAttributeExpr patternAtr) {
			mappings.addAll(extractMappings(patternAtr.getExpr()));
		} else if (expr instanceof GipsContextExpr context) {
			EObject contextType = getContextType(context);
			if (contextType instanceof GipsMappingContext mappingContext) {
				mappings.add(mappingContext.getMapping());
			}

			if (context.getStream() != null) {
				mappings.addAll(extractMappings(context.getStream()));
			}
		} else if (expr instanceof GipsLambdaAttributeExpression lambdaAtr) {
			return mappings;
		} else if (expr instanceof GipsLambdaSelfExpression lambdaSelf) {
			return mappings;
		} else {
			throw new UnsupportedOperationException("Unknown attribute expression type: " + expr);
		}

		return mappings;
	}

	public static Set<GipsMapping> extractMappings(final GipsStreamExpr expr) {
		Set<GipsMapping> mappings = new HashSet<>();

		if (expr == null)
			return mappings;

		if (expr instanceof GipsSelect select) {
			return mappings;
		} else if (expr instanceof GipsStreamSet set) {
			mappings.addAll(extractMappings(set.getLambda().getExpr()));
		} else if (expr instanceof GipsStreamArithmetic arithmetic) {
			mappings.addAll(extractMappings(arithmetic.getLambda().getExpr()));
		} else if (expr instanceof GipsContains contains) {
			mappings.addAll(extractMappings(contains.getExpr()));
		} else if (expr instanceof GipsStreamBoolExpr bool) {
			return mappings;
		} else if (expr instanceof GipsStreamNavigation navigation) {
			if (navigation.getRight() == null) {
				mappings.addAll(extractMappings(navigation.getLeft()));
			} else {
				mappings.addAll(extractMappings(navigation.getLeft()));
				mappings.addAll(extractMappings(navigation.getRight()));
			}
		} else {
			throw new UnsupportedOperationException("Unknown stream expression type: " + expr);
		}

		return mappings;
	}

	public static void gatherGTFiles(Collection<File> gtFiles, File root) {
		if (root.isDirectory() && root.exists()) {
			for (File subFile : root.listFiles()) {
				gatherGTFiles(gtFiles, subFile);
			}
			return;
		} else if (!root.isDirectory() && root.exists()) {
			if (root.getName().endsWith(".gt")) {
				gtFiles.add(root);
				return;
			}
		} else {
			return;
		}
	}

}
