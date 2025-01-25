package org.emoflon.gips.build.transformation.transformer;

import org.emoflon.gips.build.transformation.helper.ExpressionReturnType;
import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils;
import org.emoflon.gips.build.transformation.helper.TransformationContext;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsRelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Context;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;

public class RelationalExpressionTransformer extends TransformationContext {

	protected RelationalExpressionTransformer(GipsTransformationData data, Context localContext,
			TransformerFactory factory) {
		super(data, localContext, factory);
	}

	protected RelationalExpressionTransformer(GipsTransformationData data, Context localContext, Context setContext,
			TransformerFactory factory) {
		super(data, localContext, setContext, factory);
	}

	public RelationalExpression transform(final GipsRelationalExpression eRelational) throws Exception {
		RelationalExpression relation = factory.createRelationalExpression();

		relation.setOperator(switch (eRelational.getOperator()) {
		case GREATER -> org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator.GREATER;
		case GREATER_OR_EQUAL -> org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator.GREATER_OR_EQUAL;
		case SMALLER -> org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator.LESS;
		case SMALLER_OR_EQUAL -> org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator.LESS_OR_EQUAL;
		case EQUAL -> org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator.EQUAL;
		case UNEQUAL -> org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator.NOT_EQUAL;
		default -> throw new IllegalArgumentException(
				"Illegal relational operator in sort predicate: " + eRelational.getOperator());

		});

		ExpressionReturnType lhsType = null;
		ExpressionReturnType rhsType = null;

		if (eRelational.getLeft() instanceof GipsArithmeticExpression arithmetic) {
			ArithmeticExpressionTransformer transformer //
					= transformerFactory.createArithmeticTransformer(localContext, setContext);
			ArithmeticExpression lhs = transformer.transform(arithmetic);
			relation.setLhs(lhs);
			lhsType = GipsTransformationUtils.extractReturnType(lhs);
		} else {
			BooleanExpressionTransformer transformer //
					= transformerFactory.createBooleanTransformer(localContext, setContext);
			BooleanExpression lhs = transformer.transform((GipsBooleanExpression) eRelational.getLeft());
			relation.setLhs(lhs);
			lhsType = GipsTransformationUtils.extractReturnType(lhs);
		}

		if (eRelational.getRight() instanceof GipsArithmeticExpression arithmetic) {
			ArithmeticExpressionTransformer transformer //
					= transformerFactory.createArithmeticTransformer(localContext, setContext);
			ArithmeticExpression rhs = transformer.transform(arithmetic);
			relation.setRhs(rhs);
			rhsType = GipsTransformationUtils.extractReturnType(rhs);
		} else {
			BooleanExpressionTransformer transformer //
					= transformerFactory.createBooleanTransformer(localContext, setContext);
			BooleanExpression rhs = transformer.transform((GipsBooleanExpression) eRelational.getRight());
			relation.setRhs(rhs);
			rhsType = GipsTransformationUtils.extractReturnType(rhs);
		}

		if (lhsType != rhsType) {
			throw new IllegalArgumentException("Incompatible relational operand types: " + eRelational);
		}

		if (lhsType == ExpressionReturnType.object || rhsType == ExpressionReturnType.object) {
			relation.setRequiresComparables(true);
		} else {
			relation.setRequiresComparables(false);
		}

		return relation;
	}
}
