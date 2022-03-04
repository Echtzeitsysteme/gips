package org.emoflon.roam.build.transformation.helper;

import java.util.HashSet;
import java.util.Set;

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
import org.emoflon.roam.intermediate.RoamIntermediate.ContextPatternNode;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextPatternNodeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextPatternValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeValue;
import org.emoflon.roam.intermediate.RoamIntermediate.FeatureExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.FeatureLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorPatternFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorPatternNodeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorPatternNodeValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorPatternValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorTypeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorTypeValue;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingSumExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.Objective;
import org.emoflon.roam.intermediate.RoamIntermediate.ObjectiveFunctionValue;
import org.emoflon.roam.intermediate.RoamIntermediate.RelationalExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateFactory;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamFilterOperation;
import org.emoflon.roam.intermediate.RoamIntermediate.TypeSumExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.ValueExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.VariableSet;
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
		} else if(expr instanceof ContextPatternNodeFeatureValue) {
			return ArithmeticExpressionType.constant;
		} else if(expr instanceof ContextMappingNodeFeatureValue) {
			return ArithmeticExpressionType.variableScalar;
		} else if(expr instanceof ContextMappingNode) {
			return ArithmeticExpressionType.variableScalar;
		} else if(expr instanceof ContextMappingValue) {
			return ArithmeticExpressionType.variableValue;
		} else if(expr instanceof ContextPatternNode) {
			return ArithmeticExpressionType.constant;
		} else if(expr instanceof ContextPatternValue) {
			return ArithmeticExpressionType.constant;
		} else if(expr instanceof ObjectiveFunctionValue) {
			return ArithmeticExpressionType.variableVector;
		} else if(expr instanceof IteratorMappingValue) {
			return ArithmeticExpressionType.variableValue;
		}  else if(expr instanceof IteratorMappingFeatureValue 
				|| expr instanceof IteratorMappingNodeValue || expr instanceof IteratorMappingNodeFeatureValue) {
			return ArithmeticExpressionType.variableScalar;
		} else if(expr instanceof IteratorPatternValue || expr instanceof IteratorPatternFeatureValue 
				|| expr instanceof IteratorPatternNodeValue || expr instanceof IteratorPatternNodeFeatureValue) {
			return ArithmeticExpressionType.constant;
		} else if(expr instanceof IteratorTypeValue || expr instanceof IteratorTypeFeatureValue ) {
			return ArithmeticExpressionType.constant;
		} else {
			throw new IllegalArgumentException("Unknown value expression Type: " + expr);
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
	
	public static boolean containsContextExpression(final ArithmeticExpression expr) {
		if(expr instanceof BinaryArithmeticExpression bin) {
			boolean lhsCheck = containsContextExpression(bin.getLhs());
			boolean rhsCheck = containsContextExpression(bin.getRhs());
			return lhsCheck || rhsCheck;
		} else if(expr instanceof UnaryArithmeticExpression unary) {
			return containsContextExpression(unary.getExpression());
		} else if(expr instanceof ArithmeticLiteral) {
			return false;
		} else {
			ArithmeticValue value = (ArithmeticValue) expr;
			return containsContextExpression(value.getValue());
		}
	}
	
	public static boolean containsContextExpression(final BoolExpression expr) {
		if(expr instanceof BoolBinaryExpression bin) {
			boolean lhsCheck = containsContextExpression(bin.getLhs());
			boolean rhsCheck = containsContextExpression(bin.getRhs());
			return lhsCheck || rhsCheck;
		} else if(expr instanceof BoolUnaryExpression unary) {
			return containsContextExpression(unary.getExpression());
		} else if(expr instanceof BoolLiteral) {
			return false;
		} else if(expr instanceof RelationalExpression relExpr) {
			return containsContextExpression(relExpr);
		} else if(expr instanceof BoolStreamExpression streamExpr) {
			return containsContextExpression(streamExpr.getStream());
		} else {
			BoolValue value = (BoolValue) expr;
			return containsContextExpression(value.getValue());
		}
	}
	
	public static boolean containsContextExpression(final RelationalExpression relExpr) {
		if(relExpr.getRhs() == null) {
			return containsContextExpression(relExpr.getLhs());
		} else {
			boolean checkLhs = containsContextExpression(relExpr.getLhs());
			boolean checkRhs = containsContextExpression(relExpr.getRhs());
			return checkLhs || checkRhs;
		}
	}
	
	public static boolean containsContextExpression(final StreamExpression expr) {
		if(expr.getChild() == null) {
			if(expr.getCurrent() instanceof StreamFilterOperation filterOp) {
				return containsContextExpression(filterOp.getPredicate());
			} else {
				return false;
			}
		} else {
			boolean currentExpr = false;
			if(expr.getCurrent() instanceof StreamFilterOperation filterOp) {
				currentExpr = containsContextExpression(filterOp.getPredicate());
			}
			boolean childExpr = containsContextExpression(expr.getChild());
			return currentExpr || childExpr;
		}
	}
	
	public static boolean containsContextExpression(final ValueExpression expr) {
		if(expr instanceof MappingSumExpression mapSum) {
			return containsContextExpression(mapSum.getFilter()) || containsContextExpression(mapSum.getExpression());
		} else if(expr instanceof TypeSumExpression typeSum) {
			return containsContextExpression(typeSum.getFilter()) || containsContextExpression(typeSum.getExpression());
		} else if(expr instanceof ContextTypeFeatureValue) {
			return true;
		} else if(expr instanceof ContextTypeValue) {
			return true;
		} else if(expr instanceof ContextPatternNodeFeatureValue) {
			return true;
		} else if(expr instanceof ContextMappingNodeFeatureValue) {
			return true;
		} else if(expr instanceof ContextMappingNode) {
			return true;
		} else if(expr instanceof ContextMappingValue) {
			return true;
		} else if(expr instanceof ContextPatternNode) {
			return true;
		} else if(expr instanceof ContextPatternValue) {
			return true;
		} else if(expr instanceof ObjectiveFunctionValue) {
			return false;
		} else if(expr instanceof IteratorMappingValue || expr instanceof IteratorMappingFeatureValue 
				|| expr instanceof IteratorMappingNodeValue || expr instanceof IteratorMappingNodeFeatureValue) {
			return false;
		} else if(expr instanceof IteratorTypeValue || expr instanceof IteratorTypeFeatureValue ) {
			return false;
		} else if(expr instanceof IteratorPatternValue || expr instanceof IteratorPatternFeatureValue 
				|| expr instanceof IteratorPatternNodeValue || expr instanceof IteratorPatternNodeFeatureValue) {
			return false;
		} else {
			throw new IllegalArgumentException("Unknown value expression Type: " + expr);
		}
	}
	
	public static Set<VariableSet> extractVariable(final ArithmeticExpression expr) {
		if(expr instanceof BinaryArithmeticExpression bin) {
			Set<VariableSet> variables = new HashSet<>();
			variables.addAll(extractVariable(bin.getLhs()));
			variables.addAll(extractVariable(bin.getRhs()));
			return variables;
		} else if(expr instanceof UnaryArithmeticExpression unary) {
			return extractVariable(unary.getExpression());
		} else if(expr instanceof ArithmeticLiteral) {
			return new HashSet<>();
		} else {
			ArithmeticValue value = (ArithmeticValue) expr;
			return extractVariable(value.getValue());
		}
	}
	
	public static Set<VariableSet> extractVariable(final ValueExpression expr) {
		Set<VariableSet> variables = new HashSet<>();
		if(expr instanceof MappingSumExpression mapSum) {
			variables.add(mapSum.getMapping());
		} else if(expr instanceof TypeSumExpression typeSum) {
			variables.addAll(extractVariable(typeSum.getExpression()));
			variables.addAll(extractVariable(typeSum.getFilter()));
		} else if(expr instanceof ContextMappingNodeFeatureValue val) {
			variables.add(val.getMappingContext());
		} else if(expr instanceof ContextMappingNode val) {
			variables.add(val.getMappingContext());
		} else if(expr instanceof ContextMappingValue val) {
			variables.add(val.getMappingContext());
		} else if(expr instanceof IteratorMappingValue val) {
			variables.add(val.getMappingContext());
		} else if(expr instanceof IteratorMappingFeatureValue val) {
			variables.add(val.getMappingContext());
		} else if(expr instanceof IteratorMappingNodeValue val) {
			variables.add(val.getMappingContext());
		}  else if(expr instanceof IteratorMappingNodeFeatureValue val) {
			variables.add(val.getMappingContext());
		}
		return variables;
	}
	
	public static Set<VariableSet> extractVariable(final StreamExpression expr) {
		if(expr.getChild() == null) {
			if(expr.getCurrent() instanceof StreamFilterOperation filterOp) {
				return extractVariable(filterOp.getPredicate());
			} else {
				return new HashSet<>();
			}
		} else {
			Set<VariableSet> variables = new HashSet<>();
			if(expr.getCurrent() instanceof StreamFilterOperation filterOp) {
				variables.addAll(extractVariable(filterOp.getPredicate()));
			}
			variables.addAll(extractVariable(expr.getChild()));
			return variables;
		}
	}
	
	public static Set<VariableSet> extractVariable(final BoolExpression expr) {
		if(expr instanceof BoolBinaryExpression bin) {
			Set<VariableSet> variables = new HashSet<>();
			variables.addAll(extractVariable(bin.getLhs()));
			variables.addAll(extractVariable(bin.getRhs()));
			return variables;
		} else if(expr instanceof BoolUnaryExpression unary) {
			return extractVariable(unary.getExpression());
		} else if(expr instanceof BoolLiteral) {
			return new HashSet<>();
		} else if(expr instanceof RelationalExpression relExpr) {
			return extractVariable(relExpr);
		} else if(expr instanceof BoolStreamExpression streamExpr) {
			return extractVariable(streamExpr.getStream());
		} else {
			BoolValue value = (BoolValue) expr;
			return extractVariable(value.getValue());
		}
	}
	
	public static Set<VariableSet> extractVariable(final RelationalExpression relExpr) {
		if(relExpr.getRhs() == null) {
			return extractVariable(relExpr.getLhs());
		} else {
			Set<VariableSet> variables = new HashSet<>();
			variables.addAll(extractVariable(relExpr.getLhs()));
			variables.addAll(extractVariable(relExpr.getRhs()));
			return variables;
		}
	}
	
	public static Set<Objective> extractObjective(final ArithmeticExpression expr) {
		if(expr instanceof BinaryArithmeticExpression bin) {
			Set<Objective> objectives = new HashSet<>();
			objectives.addAll(extractObjective(bin.getLhs()));
			objectives.addAll(extractObjective(bin.getRhs()));
			return objectives;
		} else if(expr instanceof UnaryArithmeticExpression unary) {
			return extractObjective(unary.getExpression());
		} else if(expr instanceof ArithmeticLiteral) {
			return new HashSet<>();
		} else {
			ArithmeticValue value = (ArithmeticValue) expr;
			return extractObjective(value.getValue());
		}
	}
	
	public static Set<Objective> extractObjective(final ValueExpression expr) {
		Set<Objective> objectives = new HashSet<>();
		if(expr instanceof ObjectiveFunctionValue objVal) {
			objectives.add(objVal.getObjective());
		}
		return objectives;
	}
	
	public static Set<Objective> extractObjective(final StreamExpression expr) {
		if(expr.getChild() == null) {
			if(expr.getCurrent() instanceof StreamFilterOperation filterOp) {
				return extractObjective(filterOp.getPredicate());
			} else {
				return new HashSet<>();
			}
		} else {
			Set<Objective> objective = new HashSet<>();
			if(expr.getCurrent() instanceof StreamFilterOperation filterOp) {
				objective.addAll(extractObjective(filterOp.getPredicate()));
			}
			objective.addAll(extractObjective(expr.getChild()));
			return objective;
		}
	}
	
	public static Set<Objective> extractObjective(final BoolExpression expr) {
		if(expr instanceof BoolBinaryExpression bin) {
			Set<Objective> objectives = new HashSet<>();
			objectives.addAll(extractObjective(bin.getLhs()));
			objectives.addAll(extractObjective(bin.getRhs()));
			return objectives;
		} else if(expr instanceof BoolUnaryExpression unary) {
			return extractObjective(unary.getExpression());
		} else if(expr instanceof BoolLiteral) {
			return new HashSet<>();
		} else if(expr instanceof RelationalExpression relExpr) {
			return extractObjective(relExpr);
		} else if(expr instanceof BoolStreamExpression streamExpr) {
			return extractObjective(streamExpr.getStream());
		} else {
			BoolValue value = (BoolValue) expr;
			return extractObjective(value.getValue());
		}
	}
	
	public static Set<Objective> extractObjective(final RelationalExpression relExpr) {
		if(relExpr.getRhs() == null) {
			return extractObjective(relExpr.getLhs());
		} else {
			Set<Objective> objectives = new HashSet<>();
			objectives.addAll(extractObjective(relExpr.getLhs()));
			objectives.addAll(extractObjective(relExpr.getRhs()));
			return objectives;
		}
	}
	
	//
}
