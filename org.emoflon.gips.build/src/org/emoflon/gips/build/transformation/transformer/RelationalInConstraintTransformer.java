package org.emoflon.gips.build.transformation.transformer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils;
import org.emoflon.gips.build.transformation.helper.TransformationContext;
import org.emoflon.gips.gipsl.gipsl.GipsAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsContextExpr;
import org.emoflon.gips.gipsl.gipsl.GipsContextOperationExpression;
import org.emoflon.gips.gipsl.gipsl.GipsMappingAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsMappingContext;
import org.emoflon.gips.gipsl.gipsl.GipsRelExpr;
import org.emoflon.gips.gipsl.gipsl.GipsStreamBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsStreamExpr;
import org.emoflon.gips.gipsl.gipsl.GipsTypeContext;
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
				} else if (eAttributeExpr instanceof GipsContextExpr eContextAttribute) {
					if (eContextAttribute.getExpr() == null && eContextAttribute.getStream() == null) {
						throw new IllegalArgumentException(
								"Some constrains contain invalid values within boolean expressions, e.g., entire matches, ILP variables or objects.");
					} else if (eContextAttribute.getExpr() != null && eContextAttribute.getStream() == null) {
						EObject contextType = GipslScopeContextUtil.getContextType(eContextAttribute);
						if (contextType instanceof GipsMappingContext mappingContext) {
							if (eContextAttribute
									.getExpr() instanceof GipsContextOperationExpression contextOperation) {
								return createUnaryConstraintCondition(contextOperation);
							} else {
								// TODO: It is conceptually possible to define simple boolean expressions that
								// can be evaluated during ILP build time.
								// -> Since constraints of this kind should be checked using patterns, we'll
								// implement this feature some time in the future.
								throw new UnsupportedOperationException(
										"Checking model preconditions within constraints is not yet supported. Instead, rules or patterns should be used for this purpose.");
							}
						} else if (contextType instanceof GipsTypeContext typeContext) {
							// TODO: It is conceptually possible to define simple boolean expressions that
							// can be evaluated during ILP build time.
							// -> Since constraints of this kind should be checked using patterns, we'll
							// implement this feature some time in the future.
							throw new UnsupportedOperationException(
									"Checking model preconditions within constraints is not yet supported. Instead, rules or patterns should be used for this purpose.");
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
			return expr;
		}
	}

	protected RelationalExpression createUnaryConstraintCondition(final GipsContextOperationExpression contextOperation)
			throws Exception {
		switch (contextOperation.getOperation()) {
		case MAPPED -> {
			RelationalExpression expr = factory.createRelationalExpression();
			DoubleLiteral constOne = factory.createDoubleLiteral();
			constOne.setLiteral(1);
			expr.setLhs(constOne);
			expr.setOperator(RelationalOperator.EQUAL);
			ArithmeticValue val = factory.createArithmeticValue();
			expr.setRhs(val);
			ContextMappingValue mapVal = factory.createContextMappingValue();
			val.setValue(mapVal);
			val.setReturnType(EcorePackage.Literals.EINT);
			mapVal.setMappingContext(((MappingConstraint) context).getMapping());
			return expr;
		}
		case VALUE -> {
			throw new UnsupportedOperationException(
					"Some constrains contain invalid values within boolean expressions, e.g., arithmetic values instead of boolean values.");
		}
		default -> {
			throw new UnsupportedOperationException("Unknown context operation: " + contextOperation.getOperation());
		}
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
			case EXISTS -> {
				RelationalExpression expr = factory.createRelationalExpression();
				DoubleLiteral constZero = factory.createDoubleLiteral();
				constZero.setLiteral(0);
				expr.setLhs(constZero);
				expr.setOperator(RelationalOperator.GREATER);
				SumExpressionTransformer transformer = transformerFactory.createSumTransformer(context);
				ArithmeticValue val = factory.createArithmeticValue();
				val.setValue(transformer.transform(eMappingAttribute));
				expr.setRhs(val);
				return expr;
			}
			case NOTEXISTS -> {
				RelationalExpression expr = factory.createRelationalExpression();
				DoubleLiteral constZero = factory.createDoubleLiteral();
				constZero.setLiteral(0);
				expr.setLhs(constZero);
				expr.setOperator(RelationalOperator.EQUAL);
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
		} else {
			throw new IllegalArgumentException(
					"Some constrains contain invalid values within boolean expressions, e.g., arithmetic values instead of boolean values.");
		}
	}

}
