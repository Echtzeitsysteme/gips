package org.emoflon.gips.build.transformation.transformer;

import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.build.transformation.helper.TransformationContext;
import org.emoflon.gips.gipsl.gipsl.GipsAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsRelExpr;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolValue;
import org.emoflon.gips.intermediate.GipsIntermediate.Constraint;

public class BooleanInConstraintTransformer extends TransformationContext<Constraint>
		implements BooleanExpressionTransformer {

	protected BooleanInConstraintTransformer(GipsTransformationData data, Constraint context,
			TransformerFactory factory) {
		super(data, context, factory);
	}

	/*
	 * This Method transforms GIPSL-based Boolean expressions into Boolean
	 * expressions that conform to the intermediate model. Here, all Boolean
	 * expressions are transformed under the assumption that they are the root
	 * expressions of a Constraint. This assumption is ensured due to the prior
	 * transformational steps that transformed a possibly complex Boolean expression
	 * into its CNF, split the literals/disjunctions of the conjunction at the
	 * &-Operator, split the nested literals of the disjunctions at the |-operator
	 * and moving each literal into its own constraint. The latter case is corrected
	 * after this Method is executed, by generating a substitute expression
	 * mimicking the semantics of the original disjunction and inserting slack
	 * variables into the original literals that constituted the original
	 * disjunction. Said "literals" are either (1) actual Boolean value literals,
	 * (2) Boolean values returned by attribute/path expressions or (3) relational
	 * expressions that evaluate to a Boolean value.
	 * 
	 */
	@Override
	public BoolExpression transform(GipsBoolExpr eBool) throws Exception {
		// Case 1: eBool models a Boolean value literal
		if (eBool instanceof GipsBooleanLiteral eLitBool) {
			BoolLiteral literal = factory.createBoolLiteral();
			literal.setLiteral(eLitBool.isLiteral());
			return literal;
		} else if (eBool instanceof GipsRelExpr eRelBool) {
			// Case 2: eRelBool does not have a RHS and, thus, contains an attribute/path
			// expression that evaluates to a Boolean value at ILP-compile-time.
			if (eRelBool.getRight() == null) {
				BoolValue value = factory.createBoolValue();
				if (eRelBool.getLeft() instanceof GipsAttributeExpr eOperand) {
					AttributeExpressionTransformer transformer = transformerFactory.createAttributeTransformer(context);
					value.setValue(transformer.transform(eOperand));
				} else {
					throw new IllegalArgumentException(
							"Boolean expressions may not contain numeric values. Expression typ was: " + eBool);
				}
				return value;
			} else { // Case 3: eRelBool is an actual relational expression that can be transformed
						// into an ILP-conform linear equality.
				RelationalExpressionTransformer transformer = transformerFactory.createRelationalTransformer(context);
				return transformer.transform(eRelBool);
			}
		} else {
			throw new UnsupportedOperationException(
					"Unknown or unexpected boolean expression type encountered as root expression of constraint: "
							+ eBool);
		}
	}

}
