package org.emoflon.roam.build.transformation.helper;

import org.emoflon.roam.build.transformation.ArithmeticExpressionType;
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
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNodeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeFeatureValue;
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
	
	public static ArithmeticExpressionType isConstantExpression(final BoolExpression expr) {
		if(expr instanceof BoolBinaryExpression bin) {
			ArithmeticExpressionType lhsType = isConstantExpression(bin.getLhs());
			ArithmeticExpressionType rhsType = isConstantExpression(bin.getRhs());
			if(lhsType == ArithmeticExpressionType.variableVector || rhsType == ArithmeticExpressionType.variableVector) {
				return ArithmeticExpressionType.variableVector;
			} else if(lhsType == ArithmeticExpressionType.variableValue || rhsType == ArithmeticExpressionType.variableValue) {
				return ArithmeticExpressionType.variableValue;
			} else if(lhsType == ArithmeticExpressionType.variableScalar || rhsType == ArithmeticExpressionType.variableScalar) {
				return ArithmeticExpressionType.variableScalar;
			} else {
				return ArithmeticExpressionType.constant;
			}
		} else if(expr instanceof BoolUnaryExpression unary) {
			return isConstantExpression(unary.getExpression());
		} else if(expr instanceof BoolLiteral) {
			return ArithmeticExpressionType.constant;
		} else if(expr instanceof RelationalExpression relExpr) {
			return isConstantExpression(relExpr);
		} else if(expr instanceof BoolStreamExpression streamExpr) {
			return isConstantExpression(streamExpr.getStream());
		} else {
			BoolValue value = (BoolValue) expr;
			return isConstantExpression(value.getValue());
		}
	}
	
	public static ArithmeticExpressionType isConstantExpression(final RelationalExpression relExpr) {
		if(relExpr.getRhs() == null) {
			return isConstantExpression(relExpr.getLhs());
		} else {
			ArithmeticExpressionType lhsType = isConstantExpression(relExpr.getLhs());
			ArithmeticExpressionType rhsType = isConstantExpression(relExpr.getRhs());
			if(lhsType == ArithmeticExpressionType.variableVector || rhsType == ArithmeticExpressionType.variableVector) {
				return ArithmeticExpressionType.variableVector;
			} else if(lhsType == ArithmeticExpressionType.variableValue || rhsType == ArithmeticExpressionType.variableValue) {
				return ArithmeticExpressionType.variableValue;
			} else if(lhsType == ArithmeticExpressionType.variableScalar || rhsType == ArithmeticExpressionType.variableScalar) {
				return ArithmeticExpressionType.variableScalar;
			} else {
				return ArithmeticExpressionType.constant;
			}
		}
	}
	
	public static ArithmeticExpressionType isConstantExpression(final ArithmeticExpression expr) {
		if(expr instanceof BinaryArithmeticExpression bin) {
			ArithmeticExpressionType lhsType = isConstantExpression(bin.getLhs());
			ArithmeticExpressionType rhsType = isConstantExpression(bin.getRhs());
			if(lhsType == ArithmeticExpressionType.variableVector || rhsType == ArithmeticExpressionType.variableVector) {
				return ArithmeticExpressionType.variableVector;
			} else if(lhsType == ArithmeticExpressionType.variableValue || rhsType == ArithmeticExpressionType.variableValue) {
				return ArithmeticExpressionType.variableValue;
			} else if(lhsType == ArithmeticExpressionType.variableScalar || rhsType == ArithmeticExpressionType.variableScalar) {
				return ArithmeticExpressionType.variableScalar;
			} else {
				return ArithmeticExpressionType.constant;
			}
		} else if(expr instanceof UnaryArithmeticExpression unary) {
			return isConstantExpression(unary.getExpression());
		} else if(expr instanceof ArithmeticLiteral) {
			return ArithmeticExpressionType.constant;
		} else {
			ArithmeticValue value = (ArithmeticValue) expr;
			return isConstantExpression(value.getValue());
		}
	}
	
	public static ArithmeticExpressionType isConstantExpression(final ValueExpression expr) {
		if(expr instanceof MappingSumExpression) {
			return ArithmeticExpressionType.variableVector;
		} else if(expr instanceof TypeSumExpression typeSum) {
			ArithmeticExpressionType exprType = isConstantExpression(typeSum.getExpression());
			ArithmeticExpressionType filterType = isConstantExpression(typeSum.getFilter());
			if(exprType == ArithmeticExpressionType.variableVector || filterType == ArithmeticExpressionType.variableVector) {
				return ArithmeticExpressionType.variableVector;
			} else if(exprType == ArithmeticExpressionType.variableValue || filterType == ArithmeticExpressionType.variableValue) {
				return ArithmeticExpressionType.variableValue;
			} else if(exprType == ArithmeticExpressionType.variableScalar || filterType == ArithmeticExpressionType.variableScalar) {
				return ArithmeticExpressionType.variableScalar;
			} else {
				return ArithmeticExpressionType.constant;
			}
		} else if(expr instanceof ContextTypeFeatureValue) {
			return ArithmeticExpressionType.constant;
		} else if(expr instanceof ContextTypeValue) {
			return ArithmeticExpressionType.constant;
		} else if(expr instanceof ContextMappingNodeFeatureValue) {
			return ArithmeticExpressionType.variableScalar;
		} else if(expr instanceof ContextMappingNode) {
			return ArithmeticExpressionType.variableScalar;
		} else if(expr instanceof ContextMappingValue) {
			return ArithmeticExpressionType.variableValue;
		} else if(expr instanceof ObjectiveFunctionValue) {
			return ArithmeticExpressionType.variableVector;
		} else if(expr instanceof IteratorMappingValue) {
			return ArithmeticExpressionType.variableValue;
		}  else if(expr instanceof IteratorMappingFeatureValue 
				|| expr instanceof IteratorMappingNodeValue || expr instanceof IteratorMappingNodeFeatureValue) {
			return ArithmeticExpressionType.variableScalar;
		} else {
			// CASE: IteratorTypeValue or IteratorTypeFeatureValue 
			return ArithmeticExpressionType.constant;
		}
	}
	
	public static ArithmeticExpressionType isConstantExpression(final StreamExpression expr) {
		if(expr.getChild() == null) {
			if(expr.getCurrent() instanceof StreamFilterOperation filterOp) {
				return isConstantExpression(filterOp.getPredicate());
			} else {
				return ArithmeticExpressionType.constant;
			}
		} else {
			ArithmeticExpressionType currentExpr = null;
			if(expr.getCurrent() instanceof StreamFilterOperation filterOp) {
				currentExpr = isConstantExpression(filterOp.getPredicate());
			} else {
				currentExpr = ArithmeticExpressionType.constant;
			}
			ArithmeticExpressionType childExpr = isConstantExpression(expr.getChild());
			
			if(currentExpr == ArithmeticExpressionType.variableVector || childExpr == ArithmeticExpressionType.variableVector) {
				return ArithmeticExpressionType.variableVector;
			} else if(currentExpr == ArithmeticExpressionType.variableValue || childExpr == ArithmeticExpressionType.variableValue) {
				return ArithmeticExpressionType.variableValue;
			} else if(currentExpr == ArithmeticExpressionType.variableScalar || childExpr == ArithmeticExpressionType.variableScalar) {
				return ArithmeticExpressionType.variableScalar;
			} else {
				return ArithmeticExpressionType.constant;
			}
		}
	}
}
