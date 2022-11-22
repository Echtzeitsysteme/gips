package org.emoflon.gips.build.transformation;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.gips.build.transformation.helper.ArithmeticExpressionType;
import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.Constraint;
import org.emoflon.gips.intermediate.GipsIntermediate.DoubleLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateFactory;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.Variable;
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference;
import org.emoflon.gips.intermediate.GipsIntermediate.VariableType;

public final class GipsConstraintUtils {
	final public static double EPSILON = 0.0001d;
	final public static double INF = 10_000;

	static protected VariableTuple insertSlackVariables(final GipsTransformationData data,
			final GipsIntermediateFactory factory, final Constraint dependingConstraint, final Constraint constraint) {
		// Add substitute variable to produce negation!
		RelationalExpression originalRelation = (RelationalExpression) constraint.getExpression();
		RelationalExpression originalRelationBackup = EcoreUtil.copy(originalRelation);
		Variable realVarPos = createRealVariable(data, factory, constraint.getName() + "_slackVPos");
		dependingConstraint.getHelperVariables().add(realVarPos);

		Variable realVarNeg = createRealVariable(data, factory, constraint.getName() + "_slackVNeg");
		dependingConstraint.getHelperVariables().add(realVarNeg);

		insertSubstituteRealVariable(factory, originalRelation, realVarPos);
		RelationalExpression invRelation = createInvertedRelationAndSlack(factory, originalRelationBackup, realVarNeg);
		dependingConstraint.getHelperConstraints().add(invRelation);

		return new VariableTuple(realVarPos, realVarNeg);
	}

	static protected void insertSubstituteRealVariable(final GipsIntermediateFactory factory,
			final RelationalExpression relation, final Variable realVar) {
		ArithmeticExpression varSide = null;
		boolean leftIsConst = true;

		if (GipsTransformationUtils.isConstantExpression(relation.getLhs()) == ArithmeticExpressionType.constant) {
			leftIsConst = true;
			varSide = relation.getRhs();
		} else {
			leftIsConst = false;
			varSide = relation.getLhs();
		}

		BinaryArithmeticExpression sum = factory.createBinaryArithmeticExpression();
		sum.setOperator(BinaryArithmeticOperator.ADD);
		sum.setLhs(varSide);

		VariableReference realVarRef = factory.createVariableReference();
		realVarRef.setVariable(realVar);

		// Check whether the slack variable will become positive or negative
		switch (relation.getOperator()) {
		case EQUAL -> {
			throw new UnsupportedOperationException(
					"Currently relational expressions containing the '==' operator can not be part of boolean expressions containing operators other than '&' ");
		}
		case GREATER, GREATER_OR_EQUAL -> {
			if (leftIsConst) {
				BinaryArithmeticExpression negation = factory.createBinaryArithmeticExpression();
				negation.setOperator(BinaryArithmeticOperator.MULTIPLY);
				DoubleLiteral negOne = factory.createDoubleLiteral();
				negOne.setLiteral(-1.0);
				negation.setLhs(negOne);
				negation.setRhs(realVarRef);
				sum.setRhs(negation);
			} else {
				sum.setRhs(realVarRef);
			}
		}
		case LESS, LESS_OR_EQUAL -> {
			if (leftIsConst) {
				sum.setRhs(realVarRef);
			} else {
				BinaryArithmeticExpression negation = factory.createBinaryArithmeticExpression();
				negation.setOperator(BinaryArithmeticOperator.MULTIPLY);
				DoubleLiteral negOne = factory.createDoubleLiteral();
				negOne.setLiteral(-1.0);
				negation.setLhs(negOne);
				negation.setRhs(realVarRef);
				sum.setRhs(negation);
			}
		}
		case NOT_EQUAL -> {
			throw new UnsupportedOperationException(
					"Currently relational expressions containing the '!=' operator can not be part of boolean expressions containing operators other than '&' ");
		}

		default -> {
			throw new UnsupportedOperationException("Unknown relational operator: " + relation.getOperator());
		}

		}

		if (leftIsConst) {
			relation.setRhs(sum);
		} else {
			relation.setLhs(sum);
		}

	}

	static protected RelationalExpression createInvertedRelationAndSlack(final GipsIntermediateFactory factory,
			final RelationalExpression relation, final Variable realVar) {
		RelationalExpression invRelation = EcoreUtil.copy(relation);

		ArithmeticExpression varSide = null;
		ArithmeticExpression constSide = null;
		boolean leftIsConst = true;

		if (GipsTransformationUtils.isConstantExpression(invRelation.getLhs()) == ArithmeticExpressionType.constant) {
			leftIsConst = true;
			varSide = invRelation.getRhs();
			constSide = invRelation.getLhs();
		} else {
			leftIsConst = false;
			varSide = invRelation.getLhs();
			constSide = invRelation.getRhs();
		}

		BinaryArithmeticExpression sum = factory.createBinaryArithmeticExpression();
		sum.setOperator(BinaryArithmeticOperator.ADD);
		sum.setLhs(varSide);
		VariableReference realVarRef = factory.createVariableReference();
		realVarRef.setVariable(realVar);

		if (leftIsConst) {
			invRelation.setRhs(sum);
		} else {
			invRelation.setLhs(sum);
		}
		varSide = sum;

		switch (invRelation.getOperator()) {
		case EQUAL -> {
			throw new UnsupportedOperationException(
					"Currently relational expressions containing the '==' operator can not be part of boolean expressions containing operators other than '&' ");
		}
		case GREATER -> { // lhs > rhs => lhs <= rhs
			invRelation.setOperator(RelationalOperator.LESS_OR_EQUAL);

			if (leftIsConst) {
				sum.setRhs(realVarRef);
			} else {
				BinaryArithmeticExpression negation = factory.createBinaryArithmeticExpression();
				negation.setOperator(BinaryArithmeticOperator.MULTIPLY);
				DoubleLiteral negOne = factory.createDoubleLiteral();
				negOne.setLiteral(-1.0);
				negation.setLhs(negOne);
				negation.setRhs(realVarRef);
				sum.setRhs(negation);
			}
		}
		case GREATER_OR_EQUAL -> { // lhs >= rhs => lhs < rhs
			// In case of "true" lesser, we do not need an additional epsilon
			invRelation.setOperator(RelationalOperator.LESS_OR_EQUAL);

			if (leftIsConst) {
				sum.setRhs(realVarRef);
			} else {
				BinaryArithmeticExpression negation = factory.createBinaryArithmeticExpression();
				negation.setOperator(BinaryArithmeticOperator.MULTIPLY);
				DoubleLiteral negOne = factory.createDoubleLiteral();
				negOne.setLiteral(-1.0);
				negation.setLhs(negOne);
				negation.setRhs(realVarRef);
				sum.setRhs(negation);
			}

			BinaryArithmeticExpression constSum = factory.createBinaryArithmeticExpression();
			constSum.setOperator(BinaryArithmeticOperator.ADD);
			DoubleLiteral negEps = factory.createDoubleLiteral();
			negEps.setLiteral(-EPSILON);

			if (leftIsConst) { // c - eps <= varExpr
				constSum.setLhs(constSide);
				constSum.setRhs(negEps);
				invRelation.setLhs(constSum);
			} else { // varExpr <= c - eps
				constSum.setLhs(constSide);
				constSum.setRhs(negEps);
				invRelation.setRhs(constSum);
			}
		}
		case LESS -> { // lhs < rhs => lhs >= rhs
			invRelation.setOperator(RelationalOperator.GREATER_OR_EQUAL);

			if (leftIsConst) {
				BinaryArithmeticExpression negation = factory.createBinaryArithmeticExpression();
				negation.setOperator(BinaryArithmeticOperator.MULTIPLY);
				DoubleLiteral negOne = factory.createDoubleLiteral();
				negOne.setLiteral(-1.0);
				negation.setLhs(negOne);
				negation.setRhs(realVarRef);
				sum.setRhs(negation);
			} else {
				sum.setRhs(realVarRef);
			}
		}
		case LESS_OR_EQUAL -> { // lhs <= rhs => lhs > rhs
			// In case of "true" greater, we do not need an additional epsilon
			invRelation.setOperator(RelationalOperator.GREATER_OR_EQUAL);

			if (leftIsConst) {
				BinaryArithmeticExpression negation = factory.createBinaryArithmeticExpression();
				negation.setOperator(BinaryArithmeticOperator.MULTIPLY);
				DoubleLiteral negOne = factory.createDoubleLiteral();
				negOne.setLiteral(-1.0);
				negation.setLhs(negOne);
				negation.setRhs(realVarRef);
				sum.setRhs(negation);
			} else {
				sum.setRhs(realVarRef);
			}

			BinaryArithmeticExpression constSum = factory.createBinaryArithmeticExpression();
			constSum.setOperator(BinaryArithmeticOperator.ADD);
			DoubleLiteral eps = factory.createDoubleLiteral();
			eps.setLiteral(EPSILON);

			if (leftIsConst) { // c + eps >= varExpr
				constSum.setLhs(constSide);
				constSum.setRhs(eps);
				invRelation.setLhs(constSum);
			} else { // varExpr >= c + eps
				constSum.setLhs(constSide);
				constSum.setRhs(eps);
				invRelation.setRhs(constSum);
			}
		}
		case NOT_EQUAL -> {
			throw new UnsupportedOperationException(
					"Currently relational expressions containing the '!=' operator can not be part of boolean expressions containing operators other than '&' ");
		}
		default -> {
			throw new UnsupportedOperationException("Unknown relational operator: " + invRelation.getOperator());
		}

		}

		return invRelation;
	}

	static protected VariableTuple insertNegationConstraint(final GipsTransformationData data,
			final GipsIntermediateFactory factory, final Constraint dConstraint, final Constraint constraint) {
		Variable binaryVarPos = createBinaryVariable(data, factory, constraint.getName() + "_symVPos");
		dConstraint.getHelperVariables().add(binaryVarPos);

		Variable binaryVarNeg = createBinaryVariable(data, factory, constraint.getName() + "_symVNeg");
		dConstraint.getHelperVariables().add(binaryVarNeg);

		// 1 - symVPos >= 1 ==> -symVPos >= 0
		RelationalExpression negationRelation = factory.createRelationalExpression();
		negationRelation.setOperator(RelationalOperator.GREATER_OR_EQUAL);
		DoubleLiteral rhsZero = factory.createDoubleLiteral();
		rhsZero.setLiteral(0.0);
		negationRelation.setRhs(rhsZero);

		BinaryArithmeticExpression negativeVar = factory.createBinaryArithmeticExpression();
		negativeVar.setOperator(BinaryArithmeticOperator.MULTIPLY);
		DoubleLiteral constNegOne = factory.createDoubleLiteral();
		constNegOne.setLiteral(-1.0);
		negativeVar.setLhs(constNegOne);

		VariableReference binaryVarRef = factory.createVariableReference();
		binaryVarRef.setVariable(binaryVarPos);
		negativeVar.setRhs(binaryVarRef);

		negationRelation.setLhs(negativeVar);
		dConstraint.setExpression(negationRelation);

		// Force symbolic variables non-zero constraint
		// symVPos + symVNeg == 1
		insertSymbolicVariableNonZeroConstraint(factory, dConstraint, binaryVarPos, binaryVarNeg);

		return new VariableTuple(binaryVarPos, binaryVarNeg);
	}

	static protected void insertSymbolicVariableNonZeroConstraint(final GipsIntermediateFactory factory,
			final Constraint constraint, final Variable var1, final Variable var2) {
		// Force symbolic variables non-zero constraint
		// var1 + var2 == 1
		RelationalExpression nonZeroRelation = factory.createRelationalExpression();
		nonZeroRelation.setOperator(RelationalOperator.EQUAL);

		DoubleLiteral constOne = factory.createDoubleLiteral();
		constOne.setLiteral(1.0);
		nonZeroRelation.setRhs(constOne);

		BinaryArithmeticExpression varSum = factory.createBinaryArithmeticExpression();
		varSum.setOperator(BinaryArithmeticOperator.ADD);
		VariableReference posVarRef = factory.createVariableReference();
		posVarRef.setVariable(var1);
		VariableReference negVarRef = factory.createVariableReference();
		negVarRef.setVariable(var2);
		varSum.setLhs(posVarRef);
		varSum.setRhs(negVarRef);
		nonZeroRelation.setLhs(varSum);

		constraint.getHelperConstraints().add(nonZeroRelation);
	}

	/*
	 * TODO: I think this only works if the slack variable is greater or equal to
	 * zero. This must be set, depending on the relational operator that was used.
	 */
	static protected void insertSymbolicSlackLinkConstraint(final GipsIntermediateFactory factory,
			final Constraint constraint, final Variable symbolicVar, final Variable slackVar) {
		// symbolicVar + slackVar >= EPS
		RelationalExpression linkRelation = factory.createRelationalExpression();
		linkRelation.setOperator(RelationalOperator.GREATER_OR_EQUAL);

		BinaryArithmeticExpression varSum = factory.createBinaryArithmeticExpression();
		varSum.setOperator(BinaryArithmeticOperator.ADD);
		VariableReference symbolicVarRef = factory.createVariableReference();
		symbolicVarRef.setVariable(symbolicVar);
		VariableReference slackVarRef = factory.createVariableReference();
		slackVarRef.setVariable(slackVar);
		varSum.setLhs(symbolicVarRef);
		varSum.setRhs(slackVarRef);

		linkRelation.setLhs(varSum);

		DoubleLiteral eps = factory.createDoubleLiteral();
		eps.setLiteral(EPSILON);
		linkRelation.setRhs(eps);

		constraint.getHelperConstraints().add(linkRelation);
	}

	static protected void insertSos1Constraint(final GipsTransformationData data, final GipsIntermediateFactory factory,
			final Constraint constraint, final Variable var1, final Variable var2) {
		Variable var1Sos = createBinaryVariable(data, factory, var1.getName() + "_sos_1");
		constraint.getHelperVariables().add(var1Sos);

		Variable var2Sos = createBinaryVariable(data, factory, var2.getName() + "_sos_1");
		constraint.getHelperVariables().add(var2Sos);

		// var1 <= MAX_Double * var1Sos ==> var1 - MAX_Double * var1Sos <= 0
		RelationalExpression var1Rel1 = factory.createRelationalExpression();
		var1Rel1.setOperator(RelationalOperator.LESS_OR_EQUAL);
		BinaryArithmeticExpression var1Sum1 = factory.createBinaryArithmeticExpression();
		var1Sum1.setOperator(BinaryArithmeticOperator.ADD);
		VariableReference var1Ref1 = factory.createVariableReference();
		var1Ref1.setVariable(var1);
		var1Sum1.setLhs(var1Ref1);
		BinaryArithmeticExpression var1Prod1 = factory.createBinaryArithmeticExpression();
		var1Prod1.setOperator(BinaryArithmeticOperator.MULTIPLY);
		DoubleLiteral MAX_Double1 = factory.createDoubleLiteral();
		MAX_Double1.setLiteral(-INF);
		var1Prod1.setLhs(MAX_Double1);
		VariableReference var1SosRef1 = factory.createVariableReference();
		var1SosRef1.setVariable(var1Sos);
		var1Prod1.setRhs(var1SosRef1);
		var1Sum1.setRhs(var1Prod1);
		var1Rel1.setLhs(var1Sum1);
		DoubleLiteral var1zero1 = factory.createDoubleLiteral();
		var1zero1.setLiteral(0.0);
		var1Rel1.setRhs(var1zero1);
		constraint.getHelperConstraints().add(var1Rel1);

		// var1 >= -MAX_Double * var1Sos ==> var1 + MAX_Double * var1Sos >= 0
		RelationalExpression var1Rel2 = factory.createRelationalExpression();
		var1Rel2.setOperator(RelationalOperator.GREATER_OR_EQUAL);
		BinaryArithmeticExpression var1Sum2 = factory.createBinaryArithmeticExpression();
		var1Sum2.setOperator(BinaryArithmeticOperator.ADD);
		VariableReference var1Ref2 = factory.createVariableReference();
		var1Ref2.setVariable(var1);
		var1Sum2.setLhs(var1Ref2);
		BinaryArithmeticExpression var1Prod2 = factory.createBinaryArithmeticExpression();
		var1Prod2.setOperator(BinaryArithmeticOperator.MULTIPLY);
		DoubleLiteral MAX_Double2 = factory.createDoubleLiteral();
		MAX_Double2.setLiteral(INF);
		var1Prod2.setLhs(MAX_Double2);
		VariableReference var1SosRef2 = factory.createVariableReference();
		var1SosRef2.setVariable(var1Sos);
		var1Prod2.setRhs(var1SosRef2);
		var1Sum2.setRhs(var1Prod2);
		var1Rel2.setLhs(var1Sum2);
		DoubleLiteral var1zero2 = factory.createDoubleLiteral();
		var1zero2.setLiteral(0.0);
		var1Rel2.setRhs(var1zero2);
		constraint.getHelperConstraints().add(var1Rel2);

		// var2 <= MAX_Double * var2Sos ==> var2 - MAX_Double * var2Sos <= 0
		RelationalExpression var2Rel1 = factory.createRelationalExpression();
		var2Rel1.setOperator(RelationalOperator.LESS_OR_EQUAL);
		BinaryArithmeticExpression var2Sum1 = factory.createBinaryArithmeticExpression();
		var2Sum1.setOperator(BinaryArithmeticOperator.ADD);
		VariableReference var2Ref1 = factory.createVariableReference();
		var2Ref1.setVariable(var2);
		var2Sum1.setLhs(var2Ref1);
		BinaryArithmeticExpression var2Prod1 = factory.createBinaryArithmeticExpression();
		var2Prod1.setOperator(BinaryArithmeticOperator.MULTIPLY);
		DoubleLiteral MAX_Double3 = factory.createDoubleLiteral();
		MAX_Double3.setLiteral(-INF);
		var2Prod1.setLhs(MAX_Double3);
		VariableReference var2SosRef1 = factory.createVariableReference();
		var2SosRef1.setVariable(var2Sos);
		var2Prod1.setRhs(var2SosRef1);
		var2Sum1.setRhs(var2Prod1);
		var2Rel1.setLhs(var2Sum1);
		DoubleLiteral var2zero1 = factory.createDoubleLiteral();
		var2zero1.setLiteral(0.0);
		var2Rel1.setRhs(var2zero1);
		constraint.getHelperConstraints().add(var2Rel1);

		// var2 >= -MAX_Double * var2Sos ==> var2 + MAX_Double * var2Sos >= 0
		RelationalExpression var2Rel2 = factory.createRelationalExpression();
		var2Rel2.setOperator(RelationalOperator.GREATER_OR_EQUAL);
		BinaryArithmeticExpression var2Sum2 = factory.createBinaryArithmeticExpression();
		var2Sum2.setOperator(BinaryArithmeticOperator.ADD);
		VariableReference var2Ref2 = factory.createVariableReference();
		var2Ref2.setVariable(var2);
		var2Sum2.setLhs(var2Ref2);
		BinaryArithmeticExpression var2Prod2 = factory.createBinaryArithmeticExpression();
		var2Prod2.setOperator(BinaryArithmeticOperator.MULTIPLY);
		DoubleLiteral MAX_Double4 = factory.createDoubleLiteral();
		MAX_Double4.setLiteral(INF);
		var2Prod2.setLhs(MAX_Double4);
		VariableReference var2SosRef2 = factory.createVariableReference();
		var2SosRef2.setVariable(var2Sos);
		var2Prod2.setRhs(var2SosRef2);
		var2Sum2.setRhs(var2Prod2);
		var2Rel2.setLhs(var2Sum2);
		DoubleLiteral var2zero2 = factory.createDoubleLiteral();
		var2zero2.setLiteral(0.0);
		var2Rel2.setRhs(var2zero2);
		constraint.getHelperConstraints().add(var2Rel2);

		// The actual sos constraint: var1Sos + var2Sos == 1
		RelationalExpression sosRel = factory.createRelationalExpression();
		sosRel.setOperator(RelationalOperator.EQUAL);
		BinaryArithmeticExpression varSosSum = factory.createBinaryArithmeticExpression();
		varSosSum.setOperator(BinaryArithmeticOperator.ADD);
		VariableReference var1SosRef3 = factory.createVariableReference();
		var1SosRef3.setVariable(var1Sos);
		VariableReference var2SosRef3 = factory.createVariableReference();
		var2SosRef3.setVariable(var2Sos);
		varSosSum.setLhs(var1SosRef3);
		varSosSum.setRhs(var2SosRef3);
		sosRel.setLhs(varSosSum);
		DoubleLiteral sosOne = factory.createDoubleLiteral();
		sosOne.setLiteral(1.0);
		sosRel.setRhs(sosOne);
		constraint.getHelperConstraints().add(sosRel);
	}

	static Variable createBinaryVariable(final GipsTransformationData data, final GipsIntermediateFactory factory,
			final String name) {
		Variable var = factory.createVariable();
		data.model().getVariables().add(var);
		var.setType(VariableType.BINARY);
		var.setName(name);
		var.setUpperBound(1.0);
		var.setLowerBound(0.0);
		return var;
	}

	static Variable createIntegerVariable(final GipsTransformationData data, final GipsIntermediateFactory factory,
			final String name) {
		Variable var = factory.createVariable();
		data.model().getVariables().add(var);
		var.setType(VariableType.INTEGER);
		var.setName(name);
		var.setUpperBound(INF);
		var.setLowerBound(-INF);
		return var;
	}

	static Variable createRealVariable(final GipsTransformationData data, final GipsIntermediateFactory factory,
			final String name) {
		Variable var = factory.createVariable();
		data.model().getVariables().add(var);
		var.setType(VariableType.REAL);
		var.setName(name);
		var.setUpperBound(INF);
		var.setLowerBound(-INF);
		return var;
	}
}
