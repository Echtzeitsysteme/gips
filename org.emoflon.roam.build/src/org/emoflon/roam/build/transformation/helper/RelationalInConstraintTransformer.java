package org.emoflon.roam.build.transformation.helper;

import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.roam.build.transformation.RoamTransformationData;
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticValue;
import org.emoflon.roam.intermediate.RoamIntermediate.Constraint;
import org.emoflon.roam.intermediate.RoamIntermediate.IntegerLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingValue;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingConstraint;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingSumExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.RelationalExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.RelationalOperator;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamContextExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamMappingAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamRelExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamSelect;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamBoolExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamNavigation;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamSet;

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
					return createUnaryConstraintCondition(eMappingAttribute.getExpr());
				} else if(eAttributeExpr instanceof RoamContextExpr eContextAttribute) {
					//TODO
					return null;
				} else {
					throw new IllegalArgumentException("Some constrains contain invalid values within boolean expressions, e.g., arithmetic values instead of boolean values.");
				}
			} else {
				throw new IllegalArgumentException("Some constrains contain invalid values within boolean expressions, e.g., arithmetic values instead of boolean values.");
			}
		} else {
			//TODO
			return null;
		}
	}
	
	/*	Translates a simple unary boolean operation (i.e., <stream>.exists() and <stream>.notExists()), 
	 *  which was defined on a stream of mapping variables, into an ILP constraint. 
	 *  E.g.: <stream>.notExists() ==> Sum(Set of Variables) = 0 
	 */
	protected RelationalExpression createUnaryConstraintCondition(final RoamStreamExpr streamExpr) throws Exception{
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
					expr.setRhs(createSumFromStreamExpression(streamExpr));
					return expr;
				}
				case NOTEXISTS -> {
					RelationalExpression expr = factory.createRelationalExpression();
					IntegerLiteral constZero = factory.createIntegerLiteral();
					constZero.setLiteral(0);
					expr.setLhs(constZero);
					expr.setOperator(RelationalOperator.EQUAL);
					expr.setRhs(createSumFromStreamExpression(streamExpr));
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
	
	protected MappingSumExpression createSumFromStreamExpression(final RoamStreamExpr streamExpr) throws Exception {
		MappingSumExpression mapSum = factory.createMappingSumExpression();
		data.eStream2SetOp().put(streamExpr, mapSum);
		mapSum.setMapping(((MappingConstraint) context).getMapping());
		mapSum.setReturnType(EcorePackage.Literals.EINT);
		// Simple expression: Just add all filtered (!) mapping variable values v={0,1}
		ArithmeticValue val = factory.createArithmeticValue();
		IteratorMappingValue itr = factory.createIteratorMappingValue();
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
