package org.emoflon.roam.build.transformation.helper;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.roam.build.transformation.RoamTransformationData;
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticValue;
import org.emoflon.roam.intermediate.RoamIntermediate.Constraint;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IntegerLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingValue;
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingConstraint;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingSumExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.RelationalExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.RelationalOperator;
import org.emoflon.roam.roamslang.roamSLang.RoamAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamContextExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamContextOperationExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamMappingAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamMappingContext;
import org.emoflon.roam.roamslang.roamSLang.RoamRelExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamSLangFactory;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamBoolExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamTypeContext;
import org.emoflon.roam.roamslang.scoping.RoamSLangScopeContextUtil;

public class RelationalInConstraintTransformer extends TransformationContext<Constraint> implements RelationalExpressionTransformer{

	protected RelationalInConstraintTransformer(RoamTransformationData data, Constraint context, TransformerFactory factory) {
		super(data, context, factory);
	}

	@Override
	public RelationalExpression transform(RoamRelExpr eRelational) throws Exception {
		// Check whether this is a simple boolean result of some attribute or stream expression, or a true relational expression, which compares two numeric values.
		if(eRelational.getRight() == null) {
			if(eRelational.getLeft() instanceof RoamAttributeExpr eAttributeExpr) {
				if(eAttributeExpr instanceof RoamMappingAttributeExpr eMappingAttribute && eMappingAttribute.getExpr() != null) {
					//TODO: This solution is not that elegant. We'll make this nicer later...
					RoamMappingContext mappingContext = RoamSLangFactory.eINSTANCE.createRoamMappingContext();
					mappingContext.setMapping(eMappingAttribute.getMapping());
					return createUnaryConstraintCondition(mappingContext, eMappingAttribute.getExpr());
				} else if(eAttributeExpr instanceof RoamContextExpr eContextAttribute) {
					if(eContextAttribute.getExpr() == null && eContextAttribute.getStream() == null) {
						throw new IllegalArgumentException("Some constrains contain invalid values within boolean expressions, e.g., entire matches, ILP variables or objects.");
					} else if(eContextAttribute.getExpr() != null && eContextAttribute.getStream() == null) {
						EObject contextType = RoamSLangScopeContextUtil.getContextType(eContextAttribute);
						if(contextType instanceof RoamMappingContext mappingContext) {
							if(eContextAttribute.getExpr() instanceof RoamContextOperationExpression contextOperation) {
								return createUnaryConstraintCondition(contextOperation);
							} else {
								// TODO: It is conceptually possible to define simple boolean expressions that can be evaluated during ILP build time.
								// -> Since constraints of this kind should be checked using patterns, we'll implement this feature some time in the future.
								throw new UnsupportedOperationException("Checking model preconditions within constraints is not yet supported. Instead, rules or patterns should be used for this purpose.");
							}
						} else if(contextType instanceof RoamTypeContext typeContext){
							// TODO: It is conceptually possible to define simple boolean expressions that can be evaluated during ILP build time.
							// -> Since constraints of this kind should be checked using patterns, we'll implement this feature some time in the future.
							throw new UnsupportedOperationException("Checking model preconditions within constraints is not yet supported. Instead, rules or patterns should be used for this purpose.");
						} else {
							throw new UnsupportedOperationException("Unknown context type: "+contextType);
						}
					} else {
						// CASE: Some stream expression invoked upon model elements, either through type context or through match nodes of a mapping context.
						// TODO: This only makes sense if we're allowing nested stream expressions. Otherwise, one can not define a meaningful invariant on ILP variables, 
						// only simple boolean expressions that can be evaluated during ILP build time. -> Since constraints of this kind should be checked using patterns, 
						// we'll implement this feature some time in the future.
						throw new UnsupportedOperationException("Nested stream expressions and checking model preconditions within constraints are not yet supported. Instead, rules or patterns should be used for checking model preconditions.");
					}
				} else {
					throw new IllegalArgumentException("Some constrains contain invalid values within boolean expressions, e.g., arithmetic values instead of boolean values.");
				}
			} else {
				throw new IllegalArgumentException("Some constrains contain invalid values within boolean expressions, e.g., arithmetic values instead of boolean values.");
			}
		} else {
			RelationalExpression expr = factory.createRelationalExpression();
			switch(eRelational.getOperator()) {
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
					throw new UnsupportedOperationException("Unknown bool operator: "+eRelational.getOperator());
			}
			ArithmeticExpressionTransformer transformer = transformerFactory.createArithmeticTransformer(context);
			expr.setLhs(transformer.transform(eRelational.getLeft()));
			expr.setRhs(transformer.transform(eRelational.getRight()));
			return expr;
		}
	}
	
	protected RelationalExpression createUnaryConstraintCondition(final RoamContextOperationExpression contextOperation) throws Exception{
		switch(contextOperation.getOperation()) {
			case MAPPED -> {
				RelationalExpression expr = factory.createRelationalExpression();
				IntegerLiteral constOne = factory.createIntegerLiteral();
				constOne.setLiteral(1);
				expr.setLhs(constOne);
				expr.setOperator(RelationalOperator.EQUAL);
				ArithmeticValue val = factory.createArithmeticValue();
				expr.setRhs(val);
				ContextMappingValue mapVal = factory.createContextMappingValue();
				val.setValue(mapVal);
				val.setReturnType(EcorePackage.Literals.EINT);
				mapVal.setMappingContext((MappingConstraint) context);
				return expr;
			}
			case VALUE -> {
				throw new UnsupportedOperationException("Some constrains contain invalid values within boolean expressions, e.g., arithmetic values instead of boolean values.");
			}	
			default -> {
				throw new UnsupportedOperationException("Unknown context operation: "+contextOperation.getOperation());
			}
		}
		
	}
	
	/*	Translates a simple unary boolean operation (i.e., <stream>.exists() and <stream>.notExists()), 
	 *  which was defined on a stream of mapping variables, into an ILP constraint. 
	 *  E.g.: <stream>.notExists() ==> Sum(Set of Variables) = 0 
	 */
	protected RelationalExpression createUnaryConstraintCondition(final EObject contextExpressionType, final RoamStreamExpr streamExpr) throws Exception{
		RoamStreamExpr terminalExpr = RoamTransformationUtils.getTerminalStreamExpression(streamExpr);
		if(terminalExpr instanceof RoamStreamBoolExpr streamBool) {
			switch(streamBool.getOperator()) {
				case COUNT -> {
					throw new IllegalArgumentException("Some constrains contain invalid values within boolean expressions, e.g., arithmetic values instead of boolean values.");
				}
				case EXISTS -> {
					RelationalExpression expr = factory.createRelationalExpression();
					IntegerLiteral constZero = factory.createIntegerLiteral();
					constZero.setLiteral(0);
					expr.setLhs(constZero);
					expr.setOperator(RelationalOperator.GREATER);
					if(contextExpressionType instanceof RoamMappingContext roamMapping) {
						expr.setRhs(createSumFromStreamExpression(roamMapping, streamExpr));
					} else if(contextExpressionType instanceof RoamTypeContext roamType) {
						// CASE: Some stream expression invoked upon model elements, either through type context or through match nodes of a mapping context.
						// TODO: This only makes sense if we're allowing nested stream expressions. Otherwise, one can not define a meaningful invariant on ILP variables, 
						// only simple boolean expressions that can be evaluated during ILP build time. -> Since constraints of this kind should be checked using patterns, 
						// we'll implement this feature some time in the future.
						throw new UnsupportedOperationException("Nested stream expressions and checking model preconditions within constraints are not yet supported. Instead, rules or patterns should be used for checking model preconditions.");
					} else {
						throw new UnsupportedOperationException("Unknown context type: "+contextExpressionType);
					}
					return expr;
				}
				case NOTEXISTS -> {
					RelationalExpression expr = factory.createRelationalExpression();
					IntegerLiteral constZero = factory.createIntegerLiteral();
					constZero.setLiteral(0);
					expr.setLhs(constZero);
					expr.setOperator(RelationalOperator.EQUAL);
					if(contextExpressionType instanceof RoamMappingContext roamMapping) {
						expr.setRhs(createSumFromStreamExpression(roamMapping, streamExpr));
					} else if(contextExpressionType instanceof RoamTypeContext roamType) {
						// CASE: Some stream expression invoked upon model elements, either through type context or through match nodes of a mapping context.
						// TODO: This only makes sense if we're allowing nested stream expressions. Otherwise, one can not define a meaningful invariant on ILP variables, 
						// only simple boolean expressions that can be evaluated during ILP build time. -> Since constraints of this kind should be checked using patterns, 
						// we'll implement this feature some time in the future.
						throw new UnsupportedOperationException("Nested stream expressions and checking model preconditions within constraints are not yet supported. Instead, rules or patterns should be used for checking model preconditions.");
					} else {
						throw new UnsupportedOperationException("Unknown context type: "+contextExpressionType);
					}
					return expr;
				}
				default -> {
					throw new UnsupportedOperationException("Unknown stream operator: "+streamBool.getOperator());
				}
			}
		} else {
			throw new IllegalArgumentException("Some constrains contain invalid values within boolean expressions, e.g., arithmetic values instead of boolean values.");
		}
	}
	
	protected MappingSumExpression createSumFromStreamExpression(final RoamMappingContext contextExpressionType, final RoamStreamExpr streamExpr) throws Exception {
		MappingSumExpression mapSum = factory.createMappingSumExpression();
		Mapping mapping = data.eMapping2Mapping().get(contextExpressionType.getMapping());
		data.eStream2SetOp().put(streamExpr, mapSum);
		mapSum.setMapping(mapping);
		mapSum.setReturnType(EcorePackage.Literals.EINT);
		// Simple expression: Just add all filtered (!) mapping variable values v={0,1}
		ArithmeticValue val = factory.createArithmeticValue();
		IteratorMappingValue itr = factory.createIteratorMappingValue();
//		TODO: Is this next line necessary?
		itr.setMappingContext(((MappingConstraint) context));
		itr.setStream(mapSum);
		val.setValue(itr);
		val.setReturnType(EcorePackage.Literals.EINT);
		mapSum.setExpression(val);
		// Create filter expression
		StreamExpressionTransformer transformer = transformerFactory.createStreamTransformer(mapSum);
		mapSum.setFilter(transformer.transform(streamExpr));
		return mapSum;
	}
	

}
