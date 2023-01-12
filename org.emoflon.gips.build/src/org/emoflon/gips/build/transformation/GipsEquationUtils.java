package org.emoflon.gips.build.transformation;

import org.emoflon.gips.build.transformation.helper.ArithmeticExpressionType;
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticValue;
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.DoubleLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateFactory;
import org.emoflon.gips.intermediate.GipsIntermediate.IntegerLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.SumExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticOperator;

public final class GipsEquationUtils {

	static public RelationalExpression rewriteMoveConstantTerms(final GipsIntermediateFactory factory,
			final RelationalExpression relExpr) {
		boolean isLhsConst = GipsTransformationUtils
				.isConstantExpression(relExpr.getLhs()) == ArithmeticExpressionType.constant ? true : false;

		ArithmeticExpression constExpr;
		ArithmeticExpression variableExpr;
		if (isLhsConst) {
			constExpr = relExpr.getLhs();
			variableExpr = relExpr.getRhs();
		} else {
			constExpr = relExpr.getRhs();
			variableExpr = relExpr.getLhs();
		}

		if (variableExpr instanceof BinaryArithmeticExpression binaryExpr) {
			if (binaryExpr.getOperator() == BinaryArithmeticOperator.ADD) {
				boolean isVarLhsConst = GipsTransformationUtils
						.isConstantExpression(binaryExpr.getLhs()) == ArithmeticExpressionType.constant ? true : false;
				boolean isVarRhsConst = GipsTransformationUtils
						.isConstantExpression(binaryExpr.getRhs()) == ArithmeticExpressionType.constant ? true : false;

				if (!isVarLhsConst && !isVarRhsConst) {
					return relExpr;
				}

				RelationalExpression rewrite = factory.createRelationalExpression();
				rewrite.setOperator(relExpr.getOperator());
				BinaryArithmeticExpression newConst = factory.createBinaryArithmeticExpression();
				rewrite.setLhs(newConst);

				newConst.setOperator(BinaryArithmeticOperator.SUBTRACT);
				newConst.setLhs(constExpr);

				if (isVarLhsConst) {
					newConst.setRhs(binaryExpr.getLhs());
					rewrite.setRhs(binaryExpr.getRhs());
				} else {
					newConst.setRhs(binaryExpr.getRhs());
					rewrite.setRhs(binaryExpr.getLhs());
				}
				return rewriteMoveConstantTerms(factory, rewrite);
			} else if (binaryExpr.getOperator() == BinaryArithmeticOperator.SUBTRACT) {
				boolean isVarLhsConst = GipsTransformationUtils
						.isConstantExpression(binaryExpr.getLhs()) == ArithmeticExpressionType.constant ? true : false;
				boolean isVarRhsConst = GipsTransformationUtils
						.isConstantExpression(binaryExpr.getRhs()) == ArithmeticExpressionType.constant ? true : false;

				if (!isVarLhsConst && !isVarRhsConst) {
					return relExpr;
				}

				RelationalExpression rewrite = factory.createRelationalExpression();
				rewrite.setOperator(relExpr.getOperator());
				BinaryArithmeticExpression newConst = factory.createBinaryArithmeticExpression();
				rewrite.setLhs(newConst);
				newConst.setLhs(constExpr);

				if (isVarLhsConst) {
					newConst.setOperator(BinaryArithmeticOperator.SUBTRACT);
					newConst.setRhs(binaryExpr.getLhs());
					ArithmeticExpression invertedRhs = invertSign(factory, binaryExpr.getRhs());
					rewrite.setRhs(invertedRhs);
				} else {
					newConst.setOperator(BinaryArithmeticOperator.ADD);
					newConst.setRhs(binaryExpr.getRhs());
					rewrite.setRhs(binaryExpr.getLhs());
				}
				return rewriteMoveConstantTerms(factory, rewrite);
			} else {
				return relExpr;
			}
		} else if (variableExpr instanceof UnaryArithmeticExpression unaryExpr) {
			return relExpr;
		} else if (variableExpr instanceof ArithmeticValue valExpr) {
			return relExpr;
		} else {
			return relExpr;
		}
	}

	static public ArithmeticExpression rewriteRemoveSubtractions(final GipsIntermediateFactory factory,
			final ArithmeticExpression expr) {
		if (expr instanceof BinaryArithmeticExpression binaryExpr) {
			if (binaryExpr.getOperator() == BinaryArithmeticOperator.SUBTRACT) {
				ArithmeticExpression rewriteRHS = invertSign(factory, binaryExpr.getRhs());
				rewriteRHS = rewriteRemoveSubtractions(factory, rewriteRHS);
				ArithmeticExpression rewriteLHS = rewriteRemoveSubtractions(factory, binaryExpr.getLhs());
				BinaryArithmeticExpression rewrite = factory.createBinaryArithmeticExpression();
				rewrite.setOperator(BinaryArithmeticOperator.ADD);
				rewrite.setLhs(rewriteLHS);
				rewrite.setRhs(rewriteRHS);
				return rewrite;
			} else {
				ArithmeticExpression rewriteLHS = rewriteRemoveSubtractions(factory, binaryExpr.getLhs());
				ArithmeticExpression rewriteRHS = rewriteRemoveSubtractions(factory, binaryExpr.getRhs());
				binaryExpr.setLhs(rewriteLHS);
				binaryExpr.setRhs(rewriteRHS);
				return binaryExpr;
			}
		} else if (expr instanceof UnaryArithmeticExpression unaryExpr) {
			ArithmeticExpression rewrite = rewriteRemoveSubtractions(factory, unaryExpr.getExpression());
			unaryExpr.setExpression(rewrite);
			return unaryExpr;
		} else if (expr instanceof ArithmeticValue valExpr) {
			if (valExpr.getValue() instanceof SumExpression sumExpr) {
				ArithmeticExpression rewrite = rewriteRemoveSubtractions(factory, sumExpr.getExpression());
				sumExpr.setExpression(rewrite);
				return valExpr;
			} else {
				return valExpr;
			}
		} else {
			return expr;
		}
	}

	static public ArithmeticExpression invertSign(final GipsIntermediateFactory factory,
			final ArithmeticExpression expr) {
		if (expr instanceof BinaryArithmeticExpression binaryExpr) {
			if (binaryExpr.getOperator() == BinaryArithmeticOperator.ADD) {
				binaryExpr.setLhs(invertSign(factory, binaryExpr.getLhs()));
				binaryExpr.setOperator(BinaryArithmeticOperator.SUBTRACT);
				return binaryExpr;
			} else if (binaryExpr.getOperator() == BinaryArithmeticOperator.SUBTRACT) {
				binaryExpr.setLhs(invertSign(factory, binaryExpr.getLhs()));
				binaryExpr.setOperator(BinaryArithmeticOperator.ADD);
				return binaryExpr;
			} else {
				binaryExpr.setLhs(invertSign(factory, binaryExpr.getLhs()));
				return binaryExpr;
			}
		} else if (expr instanceof UnaryArithmeticExpression unaryExpr) {
			if (unaryExpr.getOperator() == UnaryArithmeticOperator.NEGATE) {
				return unaryExpr.getExpression();
			} else if (unaryExpr.getOperator() == UnaryArithmeticOperator.BRACKET) {
				return invertSign(factory, unaryExpr.getExpression());
			} else {
				BinaryArithmeticExpression rewrite = factory.createBinaryArithmeticExpression();
				rewrite.setOperator(BinaryArithmeticOperator.MULTIPLY);
				DoubleLiteral constNegOne = factory.createDoubleLiteral();
				constNegOne.setLiteral(-1);
				rewrite.setLhs(constNegOne);
				rewrite.setRhs(unaryExpr);
				return rewrite;
			}
		} else if (expr instanceof ArithmeticValue valExpr) {
			if (valExpr.getValue() instanceof SumExpression sumExpr) {
				sumExpr.setExpression(invertSign(factory, sumExpr.getExpression()));
				return valExpr;
			} else {
				BinaryArithmeticExpression rewrite = factory.createBinaryArithmeticExpression();
				DoubleLiteral constNegOne = factory.createDoubleLiteral();
				constNegOne.setLiteral(-1);
				rewrite.setOperator(BinaryArithmeticOperator.MULTIPLY);
				rewrite.setLhs(constNegOne);
				rewrite.setRhs(valExpr);
				return rewrite;
			}
		} else if (expr instanceof IntegerLiteral lit) {
			lit.setLiteral(-lit.getLiteral());
			return lit;
		} else {
			DoubleLiteral lit = (DoubleLiteral) expr;
			lit.setLiteral(-lit.getLiteral());
			return lit;
		}
	}

}
