package org.emoflon.gips.build.transformation.transformer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.gips.build.transformation.helper.ExpressionReturnType;
import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils;
import org.emoflon.gips.build.transformation.helper.TransformationContext;
import org.emoflon.gips.gipsl.gipsl.GipsAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsContains;
import org.emoflon.gips.gipsl.gipsl.GipsContextExpr;
import org.emoflon.gips.gipsl.gipsl.GipsMappingAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsMappingCheckValue;
import org.emoflon.gips.gipsl.gipsl.GipsMappingContext;
import org.emoflon.gips.gipsl.gipsl.GipsMappingValue;
import org.emoflon.gips.gipsl.gipsl.GipsNodeAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsPatternAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsPatternContext;
import org.emoflon.gips.gipsl.gipsl.GipsRelExpr;
import org.emoflon.gips.gipsl.gipsl.GipsStreamBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsStreamExpr;
import org.emoflon.gips.gipsl.gipsl.GipsTypeAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsTypeContext;
import org.emoflon.gips.gipsl.gipsl.GipsVariableOperationExpression;
import org.emoflon.gips.gipsl.scoping.GipslScopeContextUtil;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticValue;
import org.emoflon.gips.intermediate.GipsIntermediate.Constraint;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingValue;
import org.emoflon.gips.intermediate.GipsIntermediate.DoubleLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;

public class RelationalInConstraintTransformer extends TransformationContext<Constraint>
		implements RelationalExpressionTransformer {

	protected RelationalInConstraintTransformer(GipsTransformationData data, Constraint context,
			TransformerFactory factory) {
		super(data, context, factory);
	}

	@Override
	public RelationalExpression transform(GipsRelExpr eRelational) throws Exception {
		// Check whether this is a simple boolean result of some attribute or stream
		// expression, or a true relational expression, which compares two numeric
		// values.
		if (eRelational.getRight() == null) {
			if (eRelational.getLeft() instanceof GipsAttributeExpr eAttributeExpr) {
				if (eAttributeExpr instanceof GipsMappingAttributeExpr eMappingAttribute
						&& eMappingAttribute.getExpr() != null) {
					return createUnaryConstraintCondition(eMappingAttribute);
				} else if (eAttributeExpr instanceof GipsTypeAttributeExpr eTypeAttribute
						&& eTypeAttribute.getExpr() != null) {
					return createUnaryConstraintCondition(eTypeAttribute);
				} else if (eAttributeExpr instanceof GipsPatternAttributeExpr ePatternAttribute
						&& ePatternAttribute.getExpr() != null) {
					return createUnaryConstraintCondition(ePatternAttribute);
				} else if (eAttributeExpr instanceof GipsContextExpr eContextAttribute) {
					if (eContextAttribute.getExpr() == null && eContextAttribute.getStream() == null) {
						throw new IllegalArgumentException(
								"Some constrains contain invalid values within boolean expressions, e.g., entire matches, ILP variables or objects.");
					} else if (eContextAttribute.getExpr() != null && eContextAttribute.getStream() == null) {
						EObject contextType = GipslScopeContextUtil.getContextType(eContextAttribute);
						if (contextType instanceof GipsMappingContext mappingContext) {
							if (eContextAttribute
									.getExpr() instanceof GipsVariableOperationExpression contextOperation) {
								return createUnaryConstraintCondition(contextOperation);
							} else if (eContextAttribute
									.getExpr() instanceof GipsNodeAttributeExpr nodeAttributeExpression
									&& nodeAttributeExpression.getExpr() != null) {
								throw new UnsupportedOperationException("TODO...");
							} else {
								throw new UnsupportedOperationException(
										"Some constrains contain invalid values within boolean expressions, e.g., entire matches, ILP variables or objects.");
							}
						} else if (contextType instanceof GipsTypeContext typeContext) {
							// TODO: It is conceptually possible to define simple boolean expressions that
							// can be evaluated during ILP build time.
							// -> Since constraints of this kind should be checked using patterns, we'll
							// implement this feature some time in the future.
							throw new UnsupportedOperationException(
									"Checking model preconditions within constraints is not yet supported. Instead, rules or patterns should be used for this purpose.");
						} else if (contextType instanceof GipsPatternContext) {
							// TODO: Implement this
							throw new UnsupportedOperationException(
									"isMapped() usage on pattern context is not yet implemented.");
						} else {
							throw new UnsupportedOperationException("Unknown context type: " + contextType);
						}
					} else {
						// CASE: Some stream expression invoked upon model elements, either through type
						// context or through match nodes of a mapping context.
						// TODO: This only makes sense if we're allowing nested stream expressions.
						// Otherwise, one can not define a meaningful invariant on ILP variables,
						// only simple boolean expressions that can be evaluated during ILP build time.
						// -> Since constraints of this kind should be checked using patterns,
						// we'll implement this feature some time in the future.
						throw new UnsupportedOperationException(
								"Nested stream expressions and checking model preconditions within constraints are not yet supported. Instead, rules or patterns should be used for checking model preconditions.");
					}
				} else {
					throw new IllegalArgumentException(
							"Some constrains contain invalid values within boolean expressions, e.g., arithmetic values instead of boolean values.");
				}
			} else {
				throw new IllegalArgumentException(
						"Some constrains contain invalid values within boolean expressions, e.g., arithmetic values instead of boolean values.");
			}
		} else {
			RelationalExpression expr = factory.createRelationalExpression();
			switch (eRelational.getOperator()) {
			case EQUAL:
				expr.setOperator(RelationalOperator.EQUAL);
				break;
			case GREATER:
				expr.setOperator(RelationalOperator.GREATER);
				break;
			case GREATER_OR_EQUAL:
				expr.setOperator(RelationalOperator.GREATER_OR_EQUAL);
				break;
			case SMALLER:
				expr.setOperator(RelationalOperator.LESS);
				break;
			case SMALLER_OR_EQUAL:
				expr.setOperator(RelationalOperator.LESS_OR_EQUAL);
				break;
			case UNEQUAL:
				expr.setOperator(RelationalOperator.NOT_EQUAL);
				break;
			default:
				throw new UnsupportedOperationException("Unknown bool operator: " + eRelational.getOperator());
			}
			ArithmeticExpressionTransformer transformer = transformerFactory.createArithmeticTransformer(context);
			expr.setLhs(transformer.transform(eRelational.getLeft()));
			expr.setRhs(transformer.transform(eRelational.getRight()));
			ExpressionReturnType lhsReturn = GipsTransformationUtils.extractReturnType(expr.getLhs());
			ExpressionReturnType rhsReturn = GipsTransformationUtils.extractReturnType(expr.getRhs());

			if (lhsReturn == ExpressionReturnType.object && rhsReturn == ExpressionReturnType.object) {
				if (expr.getOperator() == RelationalOperator.EQUAL) {
					expr.setOperator(RelationalOperator.OBJECT_EQUAL);
					return expr;
				} else if (expr.getOperator() == RelationalOperator.NOT_EQUAL) {
					expr.setOperator(RelationalOperator.OBJECT_NOT_EQUAL);
					return expr;
				} else {
					throw new UnsupportedOperationException("Unsupported comparison operation between type ("
							+ lhsReturn + " " + expr.getOperator() + " " + rhsReturn + ")");
				}
			} else if (lhsReturn == ExpressionReturnType.number && rhsReturn == ExpressionReturnType.number) {
				return expr;
			} else if (lhsReturn == ExpressionReturnType.bool && rhsReturn == ExpressionReturnType.bool) {
				return expr;
			} else {
				throw new IllegalArgumentException("Comparison of incompatible types (" + lhsReturn + " "
						+ expr.getOperator() + " " + rhsReturn + ")");
			}
		}
	}

	protected RelationalExpression createUnaryConstraintCondition(
			final GipsVariableOperationExpression contextOperation) throws Exception {
		if (contextOperation instanceof GipsMappingValue mappingValueOp) {
			throw new UnsupportedOperationException(
					"Some constrains contain invalid values within boolean expressions, e.g., arithmetic values instead of boolean values.");
		} else if (contextOperation instanceof GipsMappingCheckValue mappingCountOp) {
			RelationalExpression expr = factory.createRelationalExpression();
			if (mappingCountOp.getCount() == null) {
				DoubleLiteral constOne = factory.createDoubleLiteral();
				constOne.setLiteral(1);
				expr.setLhs(constOne);
			} else {
				ArithmeticExpressionTransformer transformer = transformerFactory.createArithmeticTransformer(context);
				expr.setLhs(transformer.transform(mappingCountOp.getCount()));
			}
			expr.setOperator(RelationalOperator.EQUAL);
			ArithmeticValue val = factory.createArithmeticValue();
			expr.setRhs(val);
			ContextMappingValue mapVal = factory.createContextMappingValue();
			val.setValue(mapVal);
			val.setReturnType(EcorePackage.Literals.EDOUBLE);
			mapVal.setMappingContext(((MappingConstraint) context).getMapping());
			return expr;
		} else {
			throw new UnsupportedOperationException("Unkown context operation: " + contextOperation.eClass());
		}
	}

	/*
	 * Translates a simple unary boolean operation (i.e., <stream>.exists() and
	 * <stream>.notExists()), which was defined on a stream of mapping variables,
	 * into an ILP constraint. E.g.: <stream>.notExists() ==> Sum(Set of Variables)
	 * = 0
	 */
	protected RelationalExpression createUnaryConstraintCondition(final GipsMappingAttributeExpr eMappingAttribute)
			throws Exception {
		GipsStreamExpr terminalExpr = GipsTransformationUtils.getTerminalStreamExpression(eMappingAttribute.getExpr());
		if (terminalExpr instanceof GipsStreamBoolExpr streamBool) {
			switch (streamBool.getOperator()) {
			case COUNT -> {
				throw new IllegalArgumentException(
						"Some constrains contain invalid values within boolean expressions, e.g., arithmetic values instead of boolean values.");
			}
			case NOT_EMPTY -> {
				RelationalExpression expr = factory.createRelationalExpression();
				DoubleLiteral constOne = factory.createDoubleLiteral();
				constOne.setLiteral(1);
				expr.setLhs(constOne);
				expr.setOperator(RelationalOperator.GREATER_OR_EQUAL);
				SumExpressionTransformer transformer = transformerFactory.createSumTransformer(context);
				ArithmeticValue val = factory.createArithmeticValue();
				val.setValue(transformer.transform(eMappingAttribute));
				expr.setRhs(val);
				return expr;
			}
			default -> {
				throw new UnsupportedOperationException("Unknown stream operator: " + streamBool.getOperator());
			}
			}
		} else if (terminalExpr instanceof GipsContains streamContains) {
			RelationalExpression expr = factory.createRelationalExpression();
			DoubleLiteral constOne = factory.createDoubleLiteral();
			constOne.setLiteral(1);
			expr.setLhs(constOne);
			expr.setOperator(RelationalOperator.GREATER_OR_EQUAL);
			SumExpressionTransformer transformer = transformerFactory.createSumTransformer(context);
			ArithmeticValue val = factory.createArithmeticValue();
			val.setValue(transformer.transform(eMappingAttribute));
			expr.setRhs(val);
			return expr;
		} else {
			throw new IllegalArgumentException(
					"Some constrains contain invalid values within boolean expressions, e.g., arithmetic values instead of boolean values.");
		}
	}

	protected RelationalExpression createUnaryConstraintCondition(final GipsTypeAttributeExpr eTypeAttribute)
			throws Exception {
		GipsStreamExpr terminalExpr = GipsTransformationUtils.getTerminalStreamExpression(eTypeAttribute.getExpr());
		if (terminalExpr instanceof GipsStreamBoolExpr streamBool) {
			switch (streamBool.getOperator()) {
			case COUNT -> {
				throw new IllegalArgumentException(
						"Some constrains contain invalid values within boolean expressions, e.g., arithmetic values instead of boolean values.");
			}
			case NOT_EMPTY -> {
				RelationalExpression expr = factory.createRelationalExpression();
				DoubleLiteral constOne = factory.createDoubleLiteral();
				constOne.setLiteral(1);
				expr.setLhs(constOne);
				expr.setOperator(RelationalOperator.GREATER_OR_EQUAL);
				SumExpressionTransformer transformer = transformerFactory.createSumTransformer(context);
				ArithmeticValue val = factory.createArithmeticValue();
				val.setValue(transformer.transform(eTypeAttribute));
				expr.setRhs(val);
				return expr;
			}
			default -> {
				throw new UnsupportedOperationException("Unknown stream operator: " + streamBool.getOperator());
			}
			}
		} else if (terminalExpr instanceof GipsContains streamContains) {
			RelationalExpression expr = factory.createRelationalExpression();
			DoubleLiteral constOne = factory.createDoubleLiteral();
			constOne.setLiteral(1);
			expr.setLhs(constOne);
			expr.setOperator(RelationalOperator.GREATER_OR_EQUAL);
			SumExpressionTransformer transformer = transformerFactory.createSumTransformer(context);
			ArithmeticValue val = factory.createArithmeticValue();
			val.setValue(transformer.transform(eTypeAttribute));
			expr.setRhs(val);
			return expr;
		} else {
			throw new IllegalArgumentException(
					"Some constrains contain invalid values within boolean expressions, e.g., arithmetic values instead of boolean values.");
		}
	}

	protected RelationalExpression createUnaryConstraintCondition(final GipsPatternAttributeExpr ePatternAttribute)
			throws Exception {
		GipsStreamExpr terminalExpr = GipsTransformationUtils.getTerminalStreamExpression(ePatternAttribute.getExpr());
		if (terminalExpr instanceof GipsStreamBoolExpr streamBool) {
			switch (streamBool.getOperator()) {
			case COUNT -> {
				throw new IllegalArgumentException(
						"Some constrains contain invalid values within boolean expressions, e.g., arithmetic values instead of boolean values.");
			}
			case NOT_EMPTY -> {
				RelationalExpression expr = factory.createRelationalExpression();
				DoubleLiteral constOne = factory.createDoubleLiteral();
				constOne.setLiteral(1);
				expr.setLhs(constOne);
				expr.setOperator(RelationalOperator.GREATER_OR_EQUAL);
				SumExpressionTransformer transformer = transformerFactory.createSumTransformer(context);
				ArithmeticValue val = factory.createArithmeticValue();
				val.setValue(transformer.transform(ePatternAttribute));
				expr.setRhs(val);
				return expr;
			}
			default -> {
				throw new UnsupportedOperationException("Unknown stream operator: " + streamBool.getOperator());
			}
			}
		} else if (terminalExpr instanceof GipsContains streamContains) {
			RelationalExpression expr = factory.createRelationalExpression();
			DoubleLiteral constOne = factory.createDoubleLiteral();
			constOne.setLiteral(1);
			expr.setLhs(constOne);
			expr.setOperator(RelationalOperator.GREATER_OR_EQUAL);
			SumExpressionTransformer transformer = transformerFactory.createSumTransformer(context);
			ArithmeticValue val = factory.createArithmeticValue();
			val.setValue(transformer.transform(ePatternAttribute));
			expr.setRhs(val);
			return expr;
		} else {
			throw new IllegalArgumentException(
					"Some constrains contain invalid values within boolean expressions, e.g., arithmetic values instead of boolean values.");
		}
	}

}
