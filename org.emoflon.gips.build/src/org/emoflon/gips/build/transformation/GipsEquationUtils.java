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

	static private ArithmeticExpression rewriteToSumOfProducts(final GipsIntermediateFactory factory,
			final ArithmeticExpression expr, final ArithmeticExpression factor,
			final BinaryArithmeticOperator operator) {
		if (expr instanceof BinaryArithmeticExpression binaryExpr) {
			if (binaryExpr.getOperator() == BinaryArithmeticOperator.ADD
					|| binaryExpr.getOperator() == BinaryArithmeticOperator.SUBTRACT) {
				if (factor == null) {
					ArithmeticExpression newLhs = rewriteToSumOfProducts(factory, binaryExpr.getLhs(), null, null);
					ArithmeticExpression newRhs = rewriteToSumOfProducts(factory, binaryExpr.getRhs(), null, null);
					binaryExpr.setLhs(newLhs);
					binaryExpr.setRhs(newRhs);
					return binaryExpr;
				} else {
					if (GipsTransformationUtils
							.isConstantExpression(binaryExpr.getLhs()) == ArithmeticExpressionType.constant) {
						BinaryArithmeticExpression rewriteLhs = factory.createBinaryArithmeticExpression();
						rewriteLhs.setOperator(operator);
						rewriteLhs.setLhs(binaryExpr.getLhs());
						rewriteLhs.setRhs(factor);
						binaryExpr.setLhs(rewriteLhs);
					} else {
						ArithmeticExpression newLhs = rewriteToSumOfProducts(factory, binaryExpr.getLhs(), factor,
								operator);
						binaryExpr.setLhs(newLhs);
					}
					if (GipsTransformationUtils
							.isConstantExpression(binaryExpr.getRhs()) == ArithmeticExpressionType.constant) {
						BinaryArithmeticExpression rewriteRhs = factory.createBinaryArithmeticExpression();
						rewriteRhs.setOperator(operator);
						rewriteRhs.setLhs(binaryExpr.getRhs());
						rewriteRhs.setRhs(factor);
						binaryExpr.setRhs(rewriteRhs);
					} else {
						ArithmeticExpression newRhs = rewriteToSumOfProducts(factory, binaryExpr.getRhs(), factor,
								operator);
						binaryExpr.setRhs(newRhs);
					}
					return binaryExpr;
				}
			} else if (binaryExpr.getOperator() == BinaryArithmeticOperator.DIVIDE
					|| binaryExpr.getOperator() == BinaryArithmeticOperator.MULTIPLY) {
				boolean isLhsConst = switch (GipsTransformationUtils.isConstantExpression(binaryExpr.getLhs())) {
				case constant -> {
					yield true;
				}
				case variableValue -> {
					yield false;
				}
				case variableVector -> {
					yield false;
				}
				};
				boolean isRhsConst = switch (GipsTransformationUtils.isConstantExpression(binaryExpr.getRhs())) {
				case constant -> {
					yield true;
				}
				case variableValue -> {
					yield false;
				}
				case variableVector -> {
					yield false;
				}
				};

				if (!isLhsConst && !isRhsConst) {
					throw new IllegalArgumentException("A product may not contain more than one mapping expression.");
				}

				if (factor == null) {
					if (isLhsConst) {
						return rewriteToSumOfProducts(factory, binaryExpr.getRhs(), binaryExpr.getLhs(),
								binaryExpr.getOperator());
					} else {
						return rewriteToSumOfProducts(factory, binaryExpr.getLhs(), binaryExpr.getRhs(),
								binaryExpr.getOperator());
					}
				} else {
					if (isLhsConst) {
						BinaryArithmeticExpression rewriteLhs = factory.createBinaryArithmeticExpression();
						rewriteLhs.setOperator(operator);
						rewriteLhs.setLhs(binaryExpr.getLhs());
						rewriteLhs.setRhs(factor);
						return rewriteToSumOfProducts(factory, binaryExpr.getRhs(), rewriteLhs, operator);
					} else {
						BinaryArithmeticExpression rewriteRhs = factory.createBinaryArithmeticExpression();
						rewriteRhs.setOperator(operator);
						rewriteRhs.setLhs(binaryExpr.getRhs());
						rewriteRhs.setRhs(factor);
						return rewriteToSumOfProducts(factory, binaryExpr.getLhs(), rewriteRhs, operator);
					}
				}
			} else {
				// CASE: POW -> It is impossible to refactor exponentials into a sum-product
				ArithmeticExpressionType lhsType = GipsTransformationUtils.isConstantExpression(binaryExpr.getLhs());
				ArithmeticExpressionType rhsType = GipsTransformationUtils.isConstantExpression(binaryExpr.getRhs());
				if (lhsType == ArithmeticExpressionType.variableValue
						|| lhsType == ArithmeticExpressionType.variableVector
						|| rhsType == ArithmeticExpressionType.variableValue
						|| rhsType == ArithmeticExpressionType.variableVector) {
					throw new IllegalArgumentException(
							"An exponential expression must not contain any mapping expressions.");
				}

				if (factor == null) {
					ArithmeticExpression lhsRewrite = rewriteToSumOfProducts(factory, binaryExpr.getLhs(), null, null);
					ArithmeticExpression rhsRewrite = rewriteToSumOfProducts(factory, binaryExpr.getRhs(), null, null);
					binaryExpr.setLhs(lhsRewrite);
					binaryExpr.setRhs(rhsRewrite);
					return binaryExpr;
				} else {
					BinaryArithmeticExpression rewrite = factory.createBinaryArithmeticExpression();
					rewrite.setOperator(operator);
					ArithmeticExpression lhsRewrite = rewriteToSumOfProducts(factory, binaryExpr.getLhs(), null, null);
					ArithmeticExpression rhsRewrite = rewriteToSumOfProducts(factory, binaryExpr.getRhs(), null, null);
					binaryExpr.setLhs(lhsRewrite);
					binaryExpr.setRhs(rhsRewrite);
					rewrite.setLhs(binaryExpr);
					rewrite.setRhs(factor);
					return rewrite;
				}

			}
		} else if (expr instanceof UnaryArithmeticExpression unaryExpr) {
			if (unaryExpr.getOperator() == UnaryArithmeticOperator.BRACKET) {
				boolean isConst = GipsTransformationUtils
						.isConstantExpression(unaryExpr.getExpression()) == ArithmeticExpressionType.constant ? true
								: false;
				if (factor == null) {
					if (isConst) {
						return unaryExpr.getExpression();
					} else {
						return rewriteToSumOfProducts(factory, unaryExpr.getExpression(), null, null);
					}
				} else {
					if (isConst) {
						BinaryArithmeticExpression rewrite = factory.createBinaryArithmeticExpression();
						rewrite.setOperator(operator);
						rewrite.setRhs(factor);
						rewrite.setLhs(unaryExpr.getExpression());
						return rewrite;
					} else {
						return rewriteToSumOfProducts(factory, unaryExpr.getExpression(), factor, operator);
					}

				}
			} else if (unaryExpr.getOperator() == UnaryArithmeticOperator.NEGATE) {
				if (factor == null) {
					DoubleLiteral constNegOne = factory.createDoubleLiteral();
					constNegOne.setLiteral(-1);
					return rewriteToSumOfProducts(factory, unaryExpr.getExpression(), constNegOne,
							BinaryArithmeticOperator.MULTIPLY);
				} else {
					DoubleLiteral constNegOne = factory.createDoubleLiteral();
					constNegOne.setLiteral(-1);
					BinaryArithmeticExpression rewrite = factory.createBinaryArithmeticExpression();
					rewrite.setOperator(BinaryArithmeticOperator.MULTIPLY);
					rewrite.setRhs(constNegOne);
					rewrite.setLhs(unaryExpr.getExpression());
					return rewriteToSumOfProducts(factory, rewrite, factor, operator);
				}
			} else {
				ArithmeticExpressionType expressionType = GipsTransformationUtils
						.isConstantExpression(unaryExpr.getExpression());
				if (expressionType == ArithmeticExpressionType.variableValue
						|| expressionType == ArithmeticExpressionType.variableVector) {
					throw new IllegalArgumentException(
							"Absolute, square-root, sine or cosine expressions must not contain any mapping expressions.");
				}

				if (factor == null) {
					ArithmeticExpression rewrite = rewriteToSumOfProducts(factory, unaryExpr.getExpression(), null,
							null);
					unaryExpr.setExpression(rewrite);
					return unaryExpr;
				} else {
					BinaryArithmeticExpression rewrite = factory.createBinaryArithmeticExpression();
					rewrite.setOperator(operator);
					ArithmeticExpression innerRewrite = rewriteToSumOfProducts(factory, unaryExpr.getExpression(), null,
							null);
					unaryExpr.setExpression(innerRewrite);
					rewrite.setLhs(unaryExpr);
					rewrite.setRhs(factor);
					return rewrite;
				}
			}
		} else if (expr instanceof ArithmeticValue valExpr) {
			if (factor == null) {
				return expr;
			} else {
				ArithmeticExpressionType expressionType = GipsTransformationUtils
						.isConstantExpression(valExpr.getValue());
				if (expressionType == ArithmeticExpressionType.constant) {
					BinaryArithmeticExpression rewrite = factory.createBinaryArithmeticExpression();
					rewrite.setOperator(operator);
					rewrite.setLhs(valExpr);
					rewrite.setRhs(factor);
					return rewrite;
				} else {
					if (valExpr.getValue() instanceof SumExpression sumExpr) {
						ArithmeticExpression rewrite = rewriteToSumOfProducts(factory, sumExpr.getExpression(), factor,
								operator);
						sumExpr.setExpression(rewrite);
						return valExpr;
					} else {
						BinaryArithmeticExpression rewrite = factory.createBinaryArithmeticExpression();
						rewrite.setOperator(operator);
						rewrite.setLhs(valExpr);
						rewrite.setRhs(factor);
						return rewrite;
					}
				}
			}
		} else {
			// CASE: Literals
			if (factor == null) {
				return expr;
			} else {
				BinaryArithmeticExpression rewrite = factory.createBinaryArithmeticExpression();
				rewrite.setOperator(operator);
				rewrite.setLhs(expr);
				rewrite.setRhs(factor);
				return rewrite;
			}
		}
	}

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
