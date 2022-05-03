package org.emoflon.gips.build.transformation.transformer;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.build.transformation.helper.TransformationContext;
import org.emoflon.gips.gipsl.gipsl.GipsAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBinaryBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsExpressionOperand;
import org.emoflon.gips.gipsl.gipsl.GipsRelExpr;
import org.emoflon.gips.gipsl.gipsl.GipsUnaryBoolExpr;
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryBoolOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolBinaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolUnaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolValue;
import org.emoflon.gips.intermediate.GipsIntermediate.UnaryBoolOperator;

public class BooleanExpressionTransformer<T extends EObject> extends TransformationContext<T> {

	protected BooleanExpressionTransformer(GipsTransformationData data, T context, TransformerFactory factory) {
		super(data, context, factory);
	}

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
		} else if (eBool instanceof GipsBinaryBoolExpr eBinBool) {
			BoolBinaryExpression binaryBool = factory.createBoolBinaryExpression();
			switch (eBinBool.getOperator()) {
			case AND:
				binaryBool.setOperator(BinaryBoolOperator.AND);
				break;
			case OR:
				binaryBool.setOperator(BinaryBoolOperator.OR);
				break;
			default:
				throw new UnsupportedOperationException("Unknown bool operator: " + eBinBool.getOperator());
			}
			binaryBool.setLhs(transform(eBinBool.getLeft()));
			binaryBool.setRhs(transform(eBinBool.getRight()));
			return binaryBool;
		} else {
			GipsUnaryBoolExpr eUnBool = (GipsUnaryBoolExpr) eBool;
			BoolUnaryExpression unaryBool = factory.createBoolUnaryExpression();
			switch (eUnBool.getOperator()) {
			case NOT:
				unaryBool.setOperator(UnaryBoolOperator.NOT);
				break;
			default:
				throw new UnsupportedOperationException("Unknown bool operator: " + eUnBool.getOperator());
			}
			unaryBool.setExpression(transform(eUnBool.getOperand()));
			return unaryBool;
		}
	}

}
