package org.emoflon.gips.build.transformation.transformer;

import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.build.transformation.helper.TransformationContext;
import org.emoflon.gips.gipsl.gipsl.GipsAndBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsBracketBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsExpressionOperand;
import org.emoflon.gips.gipsl.gipsl.GipsImplicationBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsNotBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsOrBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsRelExpr;
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryBoolOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolBinaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolUnaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolValue;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.UnaryBoolOperator;

public class BooleanInStreamTransformer extends TransformationContext<StreamExpression>
		implements BooleanExpressionTransformer {

	protected BooleanInStreamTransformer(GipsTransformationData data, StreamExpression context,
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
		} else if (eBool instanceof GipsImplicationBoolExpr eBinBool) {
			// A => B <-> !A | B
			BoolBinaryExpression implSubstituteBool = factory.createBoolBinaryExpression();
			implSubstituteBool.setOperator(BinaryBoolOperator.OR);
			BoolUnaryExpression notSubstituteBool = factory.createBoolUnaryExpression();
			notSubstituteBool.setOperator(UnaryBoolOperator.NOT);
			notSubstituteBool.setExpression(transform(eBinBool.getLeft()));
			implSubstituteBool.setLhs(notSubstituteBool);
			implSubstituteBool.setRhs(transform(eBinBool.getRight()));
			return implSubstituteBool;
		} else if (eBool instanceof GipsOrBoolExpr eBinBool) {
			BoolBinaryExpression binaryBool = factory.createBoolBinaryExpression();
			switch (eBinBool.getOperator()) {
			case OR:
				binaryBool.setOperator(BinaryBoolOperator.OR);
				break;
			case XOR:
				// Note: Java only supports Bitwise-XOR operations. But in our case, this is
				// sufficiently safe since we ensure that only boolean values can be part of
				// boolean operations.
				binaryBool.setOperator(BinaryBoolOperator.XOR);
				break;
			default:
				throw new UnsupportedOperationException("Unknown bool operator: " + eBinBool.getOperator());
			}
			binaryBool.setLhs(transform(eBinBool.getLeft()));
			binaryBool.setRhs(transform(eBinBool.getRight()));
			return binaryBool;
		} else if (eBool instanceof GipsAndBoolExpr eBinBool) {
			BoolBinaryExpression binaryBool = factory.createBoolBinaryExpression();
			switch (eBinBool.getOperator()) {
			case AND:
				binaryBool.setOperator(BinaryBoolOperator.AND);
				break;
			default:
				throw new UnsupportedOperationException("Unknown bool operator: " + eBinBool.getOperator());
			}
			binaryBool.setLhs(transform(eBinBool.getLeft()));
			binaryBool.setRhs(transform(eBinBool.getRight()));
			return binaryBool;
		} else if (eBool instanceof GipsNotBoolExpr eBinBool) {
			BoolUnaryExpression unaryBool = factory.createBoolUnaryExpression();
			unaryBool.setOperator(UnaryBoolOperator.NOT);
			unaryBool.setExpression(transform(eBinBool.getOperand()));
			return unaryBool;
		} else if (eBool instanceof GipsBracketBoolExpr eBinBool) {
			BoolUnaryExpression unaryBool = factory.createBoolUnaryExpression();
			unaryBool.setOperator(UnaryBoolOperator.BRACKET);
			unaryBool.setExpression(transform(eBinBool.getOperand()));
			return unaryBool;
		} else {
			throw new UnsupportedOperationException("Unknown bool expression: " + eBool);
		}
	}

}
