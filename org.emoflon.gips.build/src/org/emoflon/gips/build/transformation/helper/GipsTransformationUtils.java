package org.emoflon.gips.build.transformation.helper;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.gips.gipsl.gipsl.GipsFeatureExpr;
import org.emoflon.gips.gipsl.gipsl.GipsFeatureLit;
import org.emoflon.gips.gipsl.gipsl.GipsFeatureNavigation;
import org.emoflon.gips.gipsl.gipsl.GipsStreamExpr;
import org.emoflon.gips.gipsl.gipsl.GipsStreamNavigation;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticValue;
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolBinaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolStreamExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolUnaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNode;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNodeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNode;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNodeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextSumExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeValue;
import org.emoflon.gips.intermediate.GipsIntermediate.FeatureExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.FeatureLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateFactory;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingVariableValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeValue;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingSumExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Objective;
import org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveFunctionValue;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternSumExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamContainsOperation;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamFilterOperation;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeSumExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference;
import org.emoflon.gips.intermediate.GipsIntermediate.VariableSet;
import org.emoflon.gips.intermediate.GipsIntermediate.VariableType;

public final class GipsTransformationUtils {
	
	public static VariableType typeToVariableType(EClassifier type) {
		if(type == EcorePackage.Literals.EINT || type == EcorePackage.Literals.ESHORT || type == EcorePackage.Literals.ELONG || type == EcorePackage.Literals.EBYTE) {
			return VariableType.INTEGER;
		} else if(type == EcorePackage.Literals.EFLOAT || type == EcorePackage.Literals.EDOUBLE) {
			return VariableType.REAL;
		} else if(type == EcorePackage.Literals.EBOOLEAN) {
			return VariableType.BINARY;
		} else {
			throw new UnsupportedOperationException("Unsupported ilp variable type: " + type);
		}
	}

	public static GipsStreamExpr getTerminalStreamExpression(final GipsStreamExpr expr) {
		if (expr instanceof GipsStreamNavigation nav) {
			return getTerminalStreamExpression(nav.getRight());
		} else {
			return expr;
		}
	}

	public static FeatureExpression transformFeatureExpression(final GipsFeatureExpr eFeature) {
		FeatureExpression feature = GipsIntermediateFactory.eINSTANCE.createFeatureExpression();
		if (eFeature instanceof GipsFeatureNavigation nav) {
			feature.setCurrent(createFeatureLiteral((GipsFeatureLit) nav.getLeft()));
			feature.setChild(transformFeatureExpression(nav.getRight()));
		} else {
			feature.setCurrent(createFeatureLiteral((GipsFeatureLit) eFeature));
		}
		return feature;
	}

	public static FeatureLiteral createFeatureLiteral(final GipsFeatureLit eFeature) {
		FeatureLiteral lit = GipsIntermediateFactory.eINSTANCE.createFeatureLiteral();
		lit.setFeature(eFeature.getFeature());
		return lit;
	}

	public static void flipOperator(final RelationalExpression expr) {
		switch (expr.getOperator()) {
		case EQUAL -> {
		}
		case GREATER -> {
			expr.setOperator(RelationalOperator.LESS);
		}
		case GREATER_OR_EQUAL -> {
			expr.setOperator(RelationalOperator.LESS_OR_EQUAL);
		}
		case LESS -> {
			expr.setOperator(RelationalOperator.GREATER);
		}
		case LESS_OR_EQUAL -> {
			expr.setOperator(RelationalOperator.LESS_OR_EQUAL);
		}
		case NOT_EQUAL -> {
		}
		default -> {
			throw new UnsupportedOperationException("Unknown relational operator: " + expr.getOperator());
		}
		}
	}

	public static ArithmeticExpressionType isConstantExpression(final BoolExpression expr) {
		if (expr instanceof BoolBinaryExpression bin) {
			ArithmeticExpressionType lhsType = isConstantExpression(bin.getLhs());
			ArithmeticExpressionType rhsType = isConstantExpression(bin.getRhs());
			if (lhsType == ArithmeticExpressionType.variableVector
					|| rhsType == ArithmeticExpressionType.variableVector) {
				return ArithmeticExpressionType.variableVector;
			} else if (lhsType == ArithmeticExpressionType.variableValue
					|| rhsType == ArithmeticExpressionType.variableValue) {
				return ArithmeticExpressionType.variableValue;
			} else if (lhsType == ArithmeticExpressionType.variableScalar
					|| rhsType == ArithmeticExpressionType.variableScalar) {
				return ArithmeticExpressionType.variableScalar;
			} else {
				return ArithmeticExpressionType.constant;
			}
		} else if (expr instanceof BoolUnaryExpression unary) {
			return isConstantExpression(unary.getExpression());
		} else if (expr instanceof BoolLiteral) {
			return ArithmeticExpressionType.constant;
		} else if (expr instanceof RelationalExpression relExpr) {
			return isConstantExpression(relExpr);
		} else if (expr instanceof BoolStreamExpression streamExpr) {
			return isConstantExpression(streamExpr.getStream());
		} else {
			BoolValue value = (BoolValue) expr;
			return isConstantExpression(value.getValue());
		}
	}

	public static ArithmeticExpressionType isConstantExpression(final RelationalExpression relExpr) {
		if (relExpr.getRhs() == null) {
			return isConstantExpression(relExpr.getLhs());
		} else {
			ArithmeticExpressionType lhsType = isConstantExpression(relExpr.getLhs());
			ArithmeticExpressionType rhsType = isConstantExpression(relExpr.getRhs());
			if (lhsType == ArithmeticExpressionType.variableVector
					|| rhsType == ArithmeticExpressionType.variableVector) {
				return ArithmeticExpressionType.variableVector;
			} else if (lhsType == ArithmeticExpressionType.variableValue
					|| rhsType == ArithmeticExpressionType.variableValue) {
				return ArithmeticExpressionType.variableValue;
			} else if (lhsType == ArithmeticExpressionType.variableScalar
					|| rhsType == ArithmeticExpressionType.variableScalar) {
				return ArithmeticExpressionType.variableScalar;
			} else {
				return ArithmeticExpressionType.constant;
			}
		}
	}

	public static ArithmeticExpressionType isConstantExpression(final ArithmeticExpression expr) {
		if (expr instanceof BinaryArithmeticExpression bin) {
			ArithmeticExpressionType lhsType = isConstantExpression(bin.getLhs());
			ArithmeticExpressionType rhsType = isConstantExpression(bin.getRhs());
			if (lhsType == ArithmeticExpressionType.variableVector
					|| rhsType == ArithmeticExpressionType.variableVector) {
				return ArithmeticExpressionType.variableVector;
			} else if (lhsType == ArithmeticExpressionType.variableValue
					|| rhsType == ArithmeticExpressionType.variableValue) {
				return ArithmeticExpressionType.variableValue;
			} else if (lhsType == ArithmeticExpressionType.variableScalar
					|| rhsType == ArithmeticExpressionType.variableScalar) {
				return ArithmeticExpressionType.variableScalar;
			} else {
				return ArithmeticExpressionType.constant;
			}
		} else if (expr instanceof UnaryArithmeticExpression unary) {
			return isConstantExpression(unary.getExpression());
		} else if (expr instanceof ArithmeticLiteral) {
			return ArithmeticExpressionType.constant;
		} else if (expr instanceof VariableReference) {
			return ArithmeticExpressionType.variableValue;
		} else {
			ArithmeticValue value = (ArithmeticValue) expr;
			return isConstantExpression(value.getValue());
		}
	}

	public static ArithmeticExpressionType isConstantExpression(final ValueExpression expr) {
		if (expr instanceof MappingSumExpression) {
			return ArithmeticExpressionType.variableVector;
		} else if (expr instanceof TypeSumExpression typeSum) {
			ArithmeticExpressionType exprType = isConstantExpression(typeSum.getExpression());
			ArithmeticExpressionType filterType = isConstantExpression(typeSum.getFilter());
			if (exprType == ArithmeticExpressionType.variableVector
					|| filterType == ArithmeticExpressionType.variableVector) {
				return ArithmeticExpressionType.variableVector;
			} else if (exprType == ArithmeticExpressionType.variableValue
					|| filterType == ArithmeticExpressionType.variableValue) {
				return ArithmeticExpressionType.variableValue;
			} else if (exprType == ArithmeticExpressionType.variableScalar
					|| filterType == ArithmeticExpressionType.variableScalar) {
				return ArithmeticExpressionType.variableScalar;
			} else {
				return ArithmeticExpressionType.constant;
			}
		} else if (expr instanceof PatternSumExpression patternSum) {
			ArithmeticExpressionType exprType = isConstantExpression(patternSum.getExpression());
			ArithmeticExpressionType filterType = isConstantExpression(patternSum.getFilter());
			if (exprType == ArithmeticExpressionType.variableVector
					|| filterType == ArithmeticExpressionType.variableVector) {
				return ArithmeticExpressionType.variableVector;
			} else if (exprType == ArithmeticExpressionType.variableValue
					|| filterType == ArithmeticExpressionType.variableValue) {
				return ArithmeticExpressionType.variableValue;
			} else if (exprType == ArithmeticExpressionType.variableScalar
					|| filterType == ArithmeticExpressionType.variableScalar) {
				return ArithmeticExpressionType.variableScalar;
			} else {
				return ArithmeticExpressionType.constant;
			}
		} else if (expr instanceof ContextSumExpression contextSum) {
			if (contextSum.getContext() instanceof Mapping) {
				return ArithmeticExpressionType.variableVector;
			} else {
				ArithmeticExpressionType exprType = isConstantExpression(contextSum.getExpression());
				ArithmeticExpressionType filterType = isConstantExpression(contextSum.getFilter());
				if (exprType == ArithmeticExpressionType.variableVector
						|| filterType == ArithmeticExpressionType.variableVector) {
					return ArithmeticExpressionType.variableVector;
				} else if (exprType == ArithmeticExpressionType.variableValue
						|| filterType == ArithmeticExpressionType.variableValue) {
					return ArithmeticExpressionType.variableValue;
				} else if (exprType == ArithmeticExpressionType.variableScalar
						|| filterType == ArithmeticExpressionType.variableScalar) {
					return ArithmeticExpressionType.variableScalar;
				} else {
					return ArithmeticExpressionType.constant;
				}
			}
		} else if (expr instanceof ContextTypeFeatureValue) {
			return ArithmeticExpressionType.constant;
		} else if (expr instanceof ContextTypeValue) {
			return ArithmeticExpressionType.constant;
		} else if (expr instanceof ContextPatternNodeFeatureValue) {
			return ArithmeticExpressionType.constant;
		} else if (expr instanceof ContextMappingNodeFeatureValue) {
			return ArithmeticExpressionType.variableScalar;
		} else if (expr instanceof ContextMappingNode) {
			return ArithmeticExpressionType.variableScalar;
		} else if (expr instanceof ContextMappingValue) {
			return ArithmeticExpressionType.variableValue;
		} else if (expr instanceof ContextPatternNode) {
			return ArithmeticExpressionType.constant;
		} else if (expr instanceof ContextPatternValue) {
			return ArithmeticExpressionType.constant;
		} else if (expr instanceof ObjectiveFunctionValue) {
			return ArithmeticExpressionType.variableVector;
		} else if (expr instanceof IteratorMappingValue) {
			return ArithmeticExpressionType.variableScalar;
		} else if (expr instanceof IteratorMappingVariableValue) {
			return ArithmeticExpressionType.variableValue;
		} else if (expr instanceof IteratorMappingFeatureValue || expr instanceof IteratorMappingNodeValue
				|| expr instanceof IteratorMappingNodeFeatureValue) {
			return ArithmeticExpressionType.variableScalar;
		} else if (expr instanceof IteratorPatternValue || expr instanceof IteratorPatternFeatureValue
				|| expr instanceof IteratorPatternNodeValue || expr instanceof IteratorPatternNodeFeatureValue) {
			return ArithmeticExpressionType.constant;
		} else if (expr instanceof IteratorTypeValue || expr instanceof IteratorTypeFeatureValue) {
			return ArithmeticExpressionType.constant;
		} else {
			throw new IllegalArgumentException("Unknown value expression Type: " + expr);
		}
	}

	public static ArithmeticExpressionType isConstantExpression(final StreamExpression expr) {
		if (expr.getChild() == null) {
			if (expr.getCurrent() instanceof StreamFilterOperation filterOp) {
				return isConstantExpression(filterOp.getPredicate());
			} else {
				return ArithmeticExpressionType.constant;
			}
		} else {
			ArithmeticExpressionType currentExpr = null;
			if (expr.getCurrent() instanceof StreamFilterOperation filterOp) {
				currentExpr = isConstantExpression(filterOp.getPredicate());
			} else {
				currentExpr = ArithmeticExpressionType.constant;
			}
			ArithmeticExpressionType childExpr = isConstantExpression(expr.getChild());

			if (currentExpr == ArithmeticExpressionType.variableVector
					|| childExpr == ArithmeticExpressionType.variableVector) {
				return ArithmeticExpressionType.variableVector;
			} else if (currentExpr == ArithmeticExpressionType.variableValue
					|| childExpr == ArithmeticExpressionType.variableValue) {
				return ArithmeticExpressionType.variableValue;
			} else if (currentExpr == ArithmeticExpressionType.variableScalar
					|| childExpr == ArithmeticExpressionType.variableScalar) {
				return ArithmeticExpressionType.variableScalar;
			} else {
				return ArithmeticExpressionType.constant;
			}
		}
	}

	public static boolean containsContextExpression(final ArithmeticExpression expr) {
		if (expr instanceof BinaryArithmeticExpression bin) {
			boolean lhsCheck = containsContextExpression(bin.getLhs());
			boolean rhsCheck = containsContextExpression(bin.getRhs());
			return lhsCheck || rhsCheck;
		} else if (expr instanceof UnaryArithmeticExpression unary) {
			return containsContextExpression(unary.getExpression());
		} else if (expr instanceof ArithmeticLiteral) {
			return false;
		} else if (expr instanceof VariableReference) {
			return false;
		} else {
			ArithmeticValue value = (ArithmeticValue) expr;
			return containsContextExpression(value.getValue());
		}
	}

	public static boolean containsContextExpression(final BoolExpression expr) {
		if (expr instanceof BoolBinaryExpression bin) {
			boolean lhsCheck = containsContextExpression(bin.getLhs());
			boolean rhsCheck = containsContextExpression(bin.getRhs());
			return lhsCheck || rhsCheck;
		} else if (expr instanceof BoolUnaryExpression unary) {
			return containsContextExpression(unary.getExpression());
		} else if (expr instanceof BoolLiteral) {
			return false;
		} else if (expr instanceof RelationalExpression relExpr) {
			return containsContextExpression(relExpr);
		} else if (expr instanceof BoolStreamExpression streamExpr) {
			return containsContextExpression(streamExpr.getStream());
		} else {
			BoolValue value = (BoolValue) expr;
			return containsContextExpression(value.getValue());
		}
	}

	public static boolean containsContextExpression(final RelationalExpression relExpr) {
		if (relExpr.getRhs() == null) {
			return containsContextExpression(relExpr.getLhs());
		} else {
			boolean checkLhs = containsContextExpression(relExpr.getLhs());
			boolean checkRhs = containsContextExpression(relExpr.getRhs());
			return checkLhs || checkRhs;
		}
	}

	public static boolean containsContextExpression(final StreamExpression expr) {
		if (expr.getChild() == null) {
			if (expr.getCurrent() instanceof StreamFilterOperation filterOp) {
				return containsContextExpression(filterOp.getPredicate());
			} else if (expr.getCurrent() instanceof StreamContainsOperation containsOp) {
				return containsContextExpression(containsOp.getExpr());
			} else {
				return false;
			}
		} else {
			boolean currentExpr = false;
			if (expr.getCurrent() instanceof StreamFilterOperation filterOp) {
				currentExpr = containsContextExpression(filterOp.getPredicate());
			} else if (expr.getCurrent() instanceof StreamContainsOperation containsOp) {
				currentExpr = containsContextExpression(containsOp.getExpr());
			}
			boolean childExpr = containsContextExpression(expr.getChild());
			return currentExpr || childExpr;
		}
	}

	public static boolean containsContextExpression(final ValueExpression expr) {
		if (expr instanceof MappingSumExpression mapSum) {
			return containsContextExpression(mapSum.getFilter()) || containsContextExpression(mapSum.getExpression());
		} else if (expr instanceof TypeSumExpression typeSum) {
			return containsContextExpression(typeSum.getFilter()) || containsContextExpression(typeSum.getExpression());
		} else if (expr instanceof PatternSumExpression patternSum) {
			return containsContextExpression(patternSum.getFilter())
					|| containsContextExpression(patternSum.getExpression());
		} else if (expr instanceof ContextSumExpression) {
			return true;
		} else if (expr instanceof ContextTypeFeatureValue) {
			return true;
		} else if (expr instanceof ContextTypeValue) {
			return true;
		} else if (expr instanceof ContextPatternNodeFeatureValue) {
			return true;
		} else if (expr instanceof ContextMappingNodeFeatureValue) {
			return true;
		} else if (expr instanceof ContextMappingNode) {
			return true;
		} else if (expr instanceof ContextMappingValue) {
			return true;
		} else if (expr instanceof ContextPatternNode) {
			return true;
		} else if (expr instanceof ContextPatternValue) {
			return true;
		} else if (expr instanceof ObjectiveFunctionValue) {
			return false;
		} else if (expr instanceof IteratorMappingValue || expr instanceof IteratorMappingVariableValue
				|| expr instanceof IteratorMappingFeatureValue || expr instanceof IteratorMappingNodeValue
				|| expr instanceof IteratorMappingNodeFeatureValue) {
			return false;
		} else if (expr instanceof IteratorTypeValue || expr instanceof IteratorTypeFeatureValue) {
			return false;
		} else if (expr instanceof IteratorPatternValue || expr instanceof IteratorPatternFeatureValue
				|| expr instanceof IteratorPatternNodeValue || expr instanceof IteratorPatternNodeFeatureValue) {
			return false;
		} else {
			throw new IllegalArgumentException("Unknown value expression Type: " + expr);
		}
	}

	public static Set<VariableSet> extractVariable(final ArithmeticExpression expr) {
		if (expr instanceof BinaryArithmeticExpression bin) {
			Set<VariableSet> variables = new HashSet<>();
			variables.addAll(extractVariable(bin.getLhs()));
			variables.addAll(extractVariable(bin.getRhs()));
			return variables;
		} else if (expr instanceof UnaryArithmeticExpression unary) {
			return extractVariable(unary.getExpression());
		} else if (expr instanceof ArithmeticLiteral) {
			return new HashSet<>();
		} else if (expr instanceof VariableReference varRef) {
			Set<VariableSet> variables = new HashSet<>();
			variables.add(varRef.getVariable());
			return variables;
		} else {
			ArithmeticValue value = (ArithmeticValue) expr;
			return extractVariable(value.getValue());
		}
	}

	public static Set<VariableSet> extractVariable(final ValueExpression expr) {
		Set<VariableSet> variables = new HashSet<>();
		if (expr instanceof MappingSumExpression mapSum) {
			variables.add(mapSum.getMapping());
		} else if (expr instanceof TypeSumExpression typeSum) {
			variables.addAll(extractVariable(typeSum.getExpression()));
			variables.addAll(extractVariable(typeSum.getFilter()));
		} else if (expr instanceof PatternSumExpression patternSum) {
			variables.addAll(extractVariable(patternSum.getExpression()));
			variables.addAll(extractVariable(patternSum.getFilter()));
		} else if (expr instanceof ContextSumExpression contextSum) {
			if (contextSum.getContext() instanceof Mapping mapping) {
				variables.add(mapping);
			}
			variables.addAll(extractVariable(contextSum.getExpression()));
			variables.addAll(extractVariable(contextSum.getFilter()));
		} else if (expr instanceof ContextMappingNodeFeatureValue val) {
			variables.add(val.getMappingContext());
		} else if (expr instanceof ContextMappingNode val) {
			variables.add(val.getMappingContext());
		} else if (expr instanceof ContextMappingValue val) {
			variables.add(val.getMappingContext());
		} else if (expr instanceof IteratorMappingValue val) {
			variables.add(val.getMappingContext());
		} else if (expr instanceof IteratorMappingVariableValue val) {
			variables.add(val.getMappingContext());
		} else if (expr instanceof IteratorMappingFeatureValue val) {
			variables.add(val.getMappingContext());
		} else if (expr instanceof IteratorMappingNodeValue val) {
			variables.add(val.getMappingContext());
		} else if (expr instanceof IteratorMappingNodeFeatureValue val) {
			variables.add(val.getMappingContext());
		}
		return variables;
	}

	public static Set<VariableSet> extractVariable(final StreamExpression expr) {
		if (expr.getChild() == null) {
			if (expr.getCurrent() instanceof StreamFilterOperation filterOp) {
				return extractVariable(filterOp.getPredicate());
			} else {
				return new HashSet<>();
			}
		} else {
			Set<VariableSet> variables = new HashSet<>();
			if (expr.getCurrent() instanceof StreamFilterOperation filterOp) {
				variables.addAll(extractVariable(filterOp.getPredicate()));
			}
			variables.addAll(extractVariable(expr.getChild()));
			return variables;
		}
	}

	public static Set<VariableSet> extractVariable(final BoolExpression expr) {
		if (expr instanceof BoolBinaryExpression bin) {
			Set<VariableSet> variables = new HashSet<>();
			variables.addAll(extractVariable(bin.getLhs()));
			variables.addAll(extractVariable(bin.getRhs()));
			return variables;
		} else if (expr instanceof BoolUnaryExpression unary) {
			return extractVariable(unary.getExpression());
		} else if (expr instanceof BoolLiteral) {
			return new HashSet<>();
		} else if (expr instanceof RelationalExpression relExpr) {
			return extractVariable(relExpr);
		} else if (expr instanceof BoolStreamExpression streamExpr) {
			return extractVariable(streamExpr.getStream());
		} else {
			BoolValue value = (BoolValue) expr;
			return extractVariable(value.getValue());
		}
	}

	public static Set<VariableSet> extractVariable(final RelationalExpression relExpr) {
		if (relExpr.getRhs() == null) {
			return extractVariable(relExpr.getLhs());
		} else {
			Set<VariableSet> variables = new HashSet<>();
			variables.addAll(extractVariable(relExpr.getLhs()));
			variables.addAll(extractVariable(relExpr.getRhs()));
			return variables;
		}
	}

	public static Set<Objective> extractObjective(final ArithmeticExpression expr) {
		if (expr instanceof BinaryArithmeticExpression bin) {
			Set<Objective> objectives = new HashSet<>();
			objectives.addAll(extractObjective(bin.getLhs()));
			objectives.addAll(extractObjective(bin.getRhs()));
			return objectives;
		} else if (expr instanceof UnaryArithmeticExpression unary) {
			return extractObjective(unary.getExpression());
		} else if (expr instanceof ArithmeticLiteral) {
			return new HashSet<>();
		} else if (expr instanceof VariableReference) {
			return new HashSet<>();
		} else {
			ArithmeticValue value = (ArithmeticValue) expr;
			return extractObjective(value.getValue());
		}
	}

	public static Set<Objective> extractObjective(final ValueExpression expr) {
		Set<Objective> objectives = new HashSet<>();
		if (expr instanceof ObjectiveFunctionValue objVal) {
			objectives.add(objVal.getObjective());
		}
		return objectives;
	}

	public static Set<Objective> extractObjective(final StreamExpression expr) {
		if (expr.getChild() == null) {
			if (expr.getCurrent() instanceof StreamFilterOperation filterOp) {
				return extractObjective(filterOp.getPredicate());
			} else {
				return new HashSet<>();
			}
		} else {
			Set<Objective> objective = new HashSet<>();
			if (expr.getCurrent() instanceof StreamFilterOperation filterOp) {
				objective.addAll(extractObjective(filterOp.getPredicate()));
			}
			objective.addAll(extractObjective(expr.getChild()));
			return objective;
		}
	}

	public static Set<Objective> extractObjective(final BoolExpression expr) {
		if (expr instanceof BoolBinaryExpression bin) {
			Set<Objective> objectives = new HashSet<>();
			objectives.addAll(extractObjective(bin.getLhs()));
			objectives.addAll(extractObjective(bin.getRhs()));
			return objectives;
		} else if (expr instanceof BoolUnaryExpression unary) {
			return extractObjective(unary.getExpression());
		} else if (expr instanceof BoolLiteral) {
			return new HashSet<>();
		} else if (expr instanceof RelationalExpression relExpr) {
			return extractObjective(relExpr);
		} else if (expr instanceof BoolStreamExpression streamExpr) {
			return extractObjective(streamExpr.getStream());
		} else {
			BoolValue value = (BoolValue) expr;
			return extractObjective(value.getValue());
		}
	}

	public static Set<Objective> extractObjective(final RelationalExpression relExpr) {
		if (relExpr.getRhs() == null) {
			return extractObjective(relExpr.getLhs());
		} else {
			Set<Objective> objectives = new HashSet<>();
			objectives.addAll(extractObjective(relExpr.getLhs()));
			objectives.addAll(extractObjective(relExpr.getRhs()));
			return objectives;
		}
	}

	public static ExpressionReturnType extractReturnType(final ArithmeticExpression expr) {
		if (expr instanceof BinaryArithmeticExpression bin) {
			ExpressionReturnType lhs = extractReturnType(bin.getLhs());
			ExpressionReturnType rhs = extractReturnType(bin.getRhs());
			if (lhs != rhs)
				throw new UnsupportedOperationException("Arithmetic operator types are mismatching.");

			return lhs;
		} else if (expr instanceof UnaryArithmeticExpression unary) {
			return extractReturnType(unary.getExpression());
		} else if (expr instanceof ArithmeticLiteral) {
			return ExpressionReturnType.number;
		} else if (expr instanceof VariableReference) {
			return ExpressionReturnType.number;
		} else {
			ArithmeticValue value = (ArithmeticValue) expr;
			return extractReturnType(value.getValue());
		}
	}

	public static ExpressionReturnType extractReturnType(final ValueExpression expr) {
		if (expr instanceof MappingSumExpression mapSum) {
			return ExpressionReturnType.number;
		} else if (expr instanceof TypeSumExpression typeSum) {
			return ExpressionReturnType.number;
		} else if (expr instanceof PatternSumExpression patternSum) {
			return ExpressionReturnType.number;
		} else if (expr instanceof ContextSumExpression) {
			return ExpressionReturnType.number;
		} else if (expr instanceof ContextTypeFeatureValue feature) {
			return extractReturnType(feature.getFeatureExpression());
		} else if (expr instanceof ContextTypeValue) {
			return ExpressionReturnType.object;
		} else if (expr instanceof ContextPatternNodeFeatureValue feature) {
			return extractReturnType(feature.getFeatureExpression());
		} else if (expr instanceof ContextMappingNodeFeatureValue feature) {
			return extractReturnType(feature.getFeatureExpression());
		} else if (expr instanceof ContextMappingNode) {
			return ExpressionReturnType.object;
		} else if (expr instanceof ContextMappingValue) {
			return ExpressionReturnType.object;
		} else if (expr instanceof ContextPatternNode) {
			return ExpressionReturnType.object;
		} else if (expr instanceof ContextPatternValue) {
			return ExpressionReturnType.object;
		} else if (expr instanceof ObjectiveFunctionValue) {
			return ExpressionReturnType.number;
		} else if (expr instanceof IteratorMappingValue) {
			return ExpressionReturnType.object;
		} else if (expr instanceof IteratorMappingVariableValue) {
			return ExpressionReturnType.number;
		} else if (expr instanceof IteratorMappingFeatureValue feature) {
			return extractReturnType(feature.getFeatureExpression());
		} else if (expr instanceof IteratorMappingNodeFeatureValue feature) {
			return extractReturnType(feature.getFeatureExpression());
		} else if (expr instanceof IteratorMappingNodeValue) {
			return ExpressionReturnType.object;
		} else if (expr instanceof IteratorTypeValue) {
			return ExpressionReturnType.object;
		} else if (expr instanceof IteratorTypeFeatureValue feature) {
			return extractReturnType(feature.getFeatureExpression());
		} else if (expr instanceof IteratorPatternValue) {
			return ExpressionReturnType.object;
		} else if (expr instanceof IteratorPatternFeatureValue feature) {
			return extractReturnType(feature.getFeatureExpression());
		} else if (expr instanceof IteratorPatternNodeValue) {
			return ExpressionReturnType.object;
		} else if (expr instanceof IteratorPatternNodeFeatureValue feature) {
			return extractReturnType(feature.getFeatureExpression());
		} else {
			throw new IllegalArgumentException("Unknown value expression Type: " + expr);
		}
	}

	public static ExpressionReturnType extractReturnType(final FeatureExpression expr) {
		if (expr.getChild() == null) {
			if (expr.getCurrent().getFeature().getEType() == EcorePackage.Literals.EBOOLEAN) {
				return ExpressionReturnType.bool;
			} else if (expr.getCurrent().getFeature().getEType() == EcorePackage.Literals.EDOUBLE) {
				return ExpressionReturnType.number;
			} else if (expr.getCurrent().getFeature().getEType() == EcorePackage.Literals.EFLOAT) {
				return ExpressionReturnType.number;
			} else if (expr.getCurrent().getFeature().getEType() == EcorePackage.Literals.EBYTE) {
				return ExpressionReturnType.number;
			} else if (expr.getCurrent().getFeature().getEType() == EcorePackage.Literals.ESHORT) {
				return ExpressionReturnType.number;
			} else if (expr.getCurrent().getFeature().getEType() == EcorePackage.Literals.EINT) {
				return ExpressionReturnType.number;
			} else if (expr.getCurrent().getFeature().getEType() == EcorePackage.Literals.ELONG) {
				return ExpressionReturnType.number;
			} else if (expr.getCurrent().getFeature().getEType() == EcorePackage.Literals.ESTRING) {
				return ExpressionReturnType.object;
			} else if (expr.getCurrent().getFeature().getEType() instanceof EClass) {
				return ExpressionReturnType.object;
			} else {
				throw new IllegalArgumentException(
						"Unsupported data type: " + expr.getCurrent().getFeature().getEType());
			}
		} else {
			return extractReturnType(expr.getChild());
		}
	}
}
