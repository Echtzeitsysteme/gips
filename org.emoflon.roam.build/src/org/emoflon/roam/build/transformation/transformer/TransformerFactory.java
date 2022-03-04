package org.emoflon.roam.build.transformation.transformer;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.roam.build.transformation.helper.RoamTransformationData;
import org.emoflon.roam.intermediate.RoamIntermediate.Constraint;
import org.emoflon.roam.intermediate.RoamIntermediate.GlobalObjective;
import org.emoflon.roam.intermediate.RoamIntermediate.Objective;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.SumExpression;

public class TransformerFactory {
	final protected RoamTransformationData data;

	public TransformerFactory(final RoamTransformationData data) {
		this.data = data;
	}

	public BooleanExpressionTransformer<? extends EObject> createBooleanTransformer(final EObject context)
			throws Exception {
		if (context instanceof StreamExpression streamExpr) {
			return new BooleanExpressionTransformer<>(data, streamExpr, this);
		} else {
			throw new IllegalArgumentException(
					"Transforming boolean expressions within the given context is undefined. Context: " + context);
		}
	}

	public RelationalExpressionTransformer createRelationalTransformer(final EObject context) throws Exception {
		if (context instanceof Constraint constraint) {
			return new RelationalInConstraintTransformer(data, constraint, this);
		} else if (context instanceof StreamExpression streamExpr) {
			return new RelationalInStreamTransformer(data, streamExpr, this);
		} else {
			throw new IllegalArgumentException(
					"Transforming relational expressions within the given context is undefined. Context: " + context);
		}
	}

	public ArithmeticExpressionTransformer<? extends EObject> createArithmeticTransformer(final EObject context)
			throws Exception {
		if (context instanceof Constraint constraint) {
			return new ArithmeticExpressionTransformer<>(data, constraint, this);
		} else if (context instanceof StreamExpression streamExpr) {
			return new ArithmeticExpressionTransformer<>(data, streamExpr, this);
		} else if (context instanceof SumExpression sumExpr) {
			return new ArithmeticExpressionTransformer<>(data, sumExpr, this);
		} else if (context instanceof Objective objective) {
			return new ArithmeticExpressionTransformer<>(data, objective, this);
		} else if (context instanceof GlobalObjective objective) {
			return new ArithmeticInGlobalObjectiveTransformer(data, objective, this);
		} else {
			throw new IllegalArgumentException(
					"Transforming arithmetic expressions within the given context is undefined. Context: " + context);
		}
	}

	public StreamExpressionTransformer<? extends EObject> createStreamTransformer(final EObject context)
			throws Exception {
		if (context instanceof Constraint constraint) {
			throw new IllegalArgumentException(
					"Transforming stream expressions within the given context is undefined. Context: " + context);
		} else if (context instanceof SumExpression sumExpr) {
			return new StreamExpressionTransformer<>(data, sumExpr, this);
		} else {
			throw new IllegalArgumentException(
					"Transforming stream expressions within the given context is undefined. Context: " + context);
		}
	}

	public AttributeExpressionTransformer<? extends EObject> createAttributeTransformer(final EObject context)
			throws Exception {
		if (context instanceof Constraint constraint) {
			return new AttributeInConstraintTransformer(data, constraint, this);
		} else if (context instanceof StreamExpression streamExpr) {
			return new AttributeInStreamTransformer(data, streamExpr, this);
		} else if (context instanceof SumExpression sumExpr) {
			return new AttributeInSumTransformer(data, sumExpr, this);
		} else if (context instanceof Objective objective) {
			return new AttributeInObjectiveTransformer(data, objective, this);
		} else {
			throw new IllegalArgumentException(
					"Transforming arithmetic expressions within the given context is undefined. Context: " + context);
		}
	}

	public SumExpressionTransformer<? extends EObject> createSumTransformer(final EObject context) throws Exception {
		if (context instanceof Constraint constraint) {
			return new SumExpressionTransformer<>(data, constraint, this);
		} else {
			throw new IllegalArgumentException(
					"Transforming arithmetic expressions within the given context is undefined. Context: " + context);
		}
	}

}
