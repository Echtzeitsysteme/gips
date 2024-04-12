package org.emoflon.gips.build.transformation.transformer;

import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.build.transformation.helper.TransformationContext;
import org.emoflon.gips.gipsl.gipsl.GipsAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsExpressionOperand;
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

	@Override
	public BoolExpression transform(GipsBoolExpr eBool) throws Exception {
		if (eBool instanceof GipsBooleanLiteral eLitBool) {
			BoolLiteral literal = factory.createBoolLiteral();
			literal.setLiteral(eLitBool.isLiteral());
			return literal;
		} else if (eBool instanceof GipsRelExpr eRelBool) {
			if (eRelBool.getRight() == null) {
				BoolValue value = factory.createBoolValue();
				if (eRelBool.getLeft() instanceof GipsExpressionOperand
						&& eRelBool.getLeft() instanceof GipsAttributeExpr eOperand) {
					AttributeExpressionTransformer transformer = transformerFactory.createAttributeTransformer(context);
					value.setValue(transformer.transform(eOperand));
				} else {
					throw new IllegalArgumentException("Boolean expression must not contain any numeric values!");
				}
				return value;
			} else {
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
