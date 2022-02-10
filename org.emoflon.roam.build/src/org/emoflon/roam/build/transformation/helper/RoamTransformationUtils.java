package org.emoflon.roam.build.transformation.helper;

import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticValue;
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolBinaryExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolStreamExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolUnaryExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNode;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeValue;
import org.emoflon.roam.intermediate.RoamIntermediate.FeatureExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.FeatureLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingValue;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingSumExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.ObjectiveFunctionValue;
import org.emoflon.roam.intermediate.RoamIntermediate.RelationalExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateFactory;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamFilterOperation;
import org.emoflon.roam.intermediate.RoamIntermediate.TypeSumExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.ValueExpression;
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
	
	public static boolean isConstantExpression(final BoolExpression expr) {
		if(expr instanceof BoolBinaryExpression bin) {
			return isConstantExpression(bin.getLhs()) && isConstantExpression(bin.getRhs());
		} else if(expr instanceof BoolUnaryExpression unary) {
			return isConstantExpression(unary.getExpression());
		} else if(expr instanceof BoolLiteral) {
			return true;
		} else if(expr instanceof RelationalExpression relExpr) {
			return isConstantExpression(relExpr);
		} else if(expr instanceof BoolStreamExpression streamExpr) {
			return isConstantExpression(streamExpr.getStream());
		} else {
			BoolValue value = (BoolValue) expr;
			return isConstantExpression(value.getValue());
		}
	}
	
	public static boolean isConstantExpression(final RelationalExpression relExpr) {
		if(relExpr.getRhs() == null) {
			return isConstantExpression(relExpr.getLhs());
		} else {
			return isConstantExpression(relExpr.getLhs()) && isConstantExpression(relExpr.getRhs());
		}
	}
	
	public static boolean isConstantExpression(final ArithmeticExpression expr) {
		if(expr instanceof BinaryArithmeticExpression bin) {
			return isConstantExpression(bin.getLhs()) && isConstantExpression(bin.getRhs());
		} else if(expr instanceof UnaryArithmeticExpression unary) {
			return isConstantExpression(unary.getExpression());
		} else if(expr instanceof ArithmeticLiteral) {
			return true;
		} else {
			ArithmeticValue value = (ArithmeticValue) expr;
			return isConstantExpression(value.getValue());
		}
	}
	
	public static boolean isConstantExpression(final ValueExpression expr) {
		if(expr instanceof MappingSumExpression) {
			return false;
		} else if(expr instanceof TypeSumExpression typeSum) {
			return isConstantExpression(typeSum.getExpression()) && isConstantExpression(typeSum.getFilter());
		} else if(expr instanceof ContextTypeValue) {
			return true;
		} else if(expr instanceof ContextMappingValue || expr instanceof ContextMappingNode 
				|| expr instanceof ObjectiveFunctionValue) {
			return false;
		} else if(expr instanceof IteratorMappingValue || expr instanceof IteratorMappingFeatureValue 
				|| expr instanceof IteratorMappingNodeValue || expr instanceof IteratorMappingNodeFeatureValue) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean isConstantExpression(final StreamExpression expr) {
		if(expr.getChild() == null) {
			if(expr.getCurrent() instanceof StreamFilterOperation filterOp) {
				return isConstantExpression(filterOp.getPredicate());
			} else {
				return true;
			}
		} else {
			boolean currentExpr = false;
			if(expr.getCurrent() instanceof StreamFilterOperation filterOp) {
				currentExpr = isConstantExpression(filterOp.getPredicate());
			} else {
				currentExpr = true;
			}
			
			return currentExpr && isConstantExpression(expr.getChild()); 
		}
	}
}
