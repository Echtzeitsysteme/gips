package org.emoflon.gips.build.transformation.transformer;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.build.transformation.helper.TransformationContext;
import org.emoflon.gips.gipsl.gipsl.EditorFile;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticConstant;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanBracket;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanConjunction;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanDisjunction;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanImplication;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanNegation;
import org.emoflon.gips.gipsl.gipsl.GipsConstantLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsConstantReference;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunction;
import org.emoflon.gips.gipsl.gipsl.GipsObjective;
import org.emoflon.gips.gipsl.gipsl.GipsRelationalExpression;
import org.emoflon.gips.gipsl.gipsl.GipsValueExpression;
import org.emoflon.gips.gipsl.gipsl.impl.EditorFileImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsConstraintImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsLinearFunctionImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsObjectiveImpl;
import org.emoflon.gips.gipsl.scoping.GipslScopeContextUtil;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanBinaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanUnaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantReference;
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantValue;
import org.emoflon.gips.intermediate.GipsIntermediate.Context;
import org.emoflon.gips.intermediate.GipsIntermediate.SetOperation;

public class BooleanExpressionTransformer extends TransformationContext {
	protected BooleanExpressionTransformer(GipsTransformationData data, Context localContext,
			TransformerFactory factory) {
		super(data, localContext, factory);
	}

	protected BooleanExpressionTransformer(GipsTransformationData data, Context localContext, Context setContext,
			TransformerFactory factory) {
		super(data, localContext, setContext, factory);
	}

	public BooleanExpression transform(GipsBooleanExpression eBool) throws Exception {
		if (eBool instanceof GipsBooleanImplication implication) {
			throw new IllegalArgumentException("Illegal boolean operation: " + implication);
		} else if (eBool instanceof GipsBooleanDisjunction disjunction) {
			BooleanBinaryExpression binary = factory.createBooleanBinaryExpression();
			binary.setOperator(switch (disjunction.getOperator()) {
			case OR -> org.emoflon.gips.intermediate.GipsIntermediate.BooleanBinaryOperator.OR;
			case XOR -> org.emoflon.gips.intermediate.GipsIntermediate.BooleanBinaryOperator.XOR;
			});
			binary.setLhs(transform(disjunction.getLeft()));
			binary.setRhs(transform(disjunction.getRight()));
			return binary;
		} else if (eBool instanceof GipsBooleanConjunction conjunction) {
			BooleanBinaryExpression binary = factory.createBooleanBinaryExpression();
			binary.setOperator(switch (conjunction.getOperator()) {
			case AND -> org.emoflon.gips.intermediate.GipsIntermediate.BooleanBinaryOperator.AND;
			});
			binary.setLhs(transform(conjunction.getLeft()));
			binary.setRhs(transform(conjunction.getRight()));
			return binary;
		} else if (eBool instanceof GipsBooleanNegation negation) {
			BooleanUnaryExpression unary = factory.createBooleanUnaryExpression();
			unary.setOperator(org.emoflon.gips.intermediate.GipsIntermediate.BooleanUnaryOperator.NOT);
			unary.setOperand(transform(negation.getOperand()));
			return unary;
		} else if (eBool instanceof GipsBooleanBracket bracket) {
			BooleanUnaryExpression unary = factory.createBooleanUnaryExpression();
			unary.setOperator(org.emoflon.gips.intermediate.GipsIntermediate.BooleanUnaryOperator.BRACKET);
			unary.setOperand(transform(bracket.getOperand()));
			return unary;
		} else if (eBool instanceof GipsBooleanLiteral eLiteral) {
			BooleanLiteral literal = factory.createBooleanLiteral();
			literal.setLiteral(eLiteral.isLiteral());
			return literal;
		} else if (eBool instanceof GipsArithmeticExpression arithmetic) {
			if (arithmetic instanceof GipsValueExpression eValue) {
				ValueExpressionTransformer transformer = //
						transformerFactory.createValueTransformer(localContext, setContext);
				return transformer.transform(eValue);
			} else if (arithmetic instanceof GipsArithmeticConstant eConstant) {
				if (eConstant.getValue() != GipsConstantLiteral.NULL) {
					throw new IllegalArgumentException("Illegal boolean expression type: " + eConstant);
				}
				ConstantLiteral constant = factory.createConstantLiteral();
				constant.setConstant(ConstantValue.NULL);
				return constant;
			} else if (arithmetic instanceof GipsConstantReference eReference) {
				EObject container = (EObject) GipslScopeContextUtil.getContainer(eReference,
						Set.of(EditorFileImpl.class, GipsConstraintImpl.class, GipsLinearFunctionImpl.class,
								GipsObjectiveImpl.class));

				ConstantReference reference = factory.createConstantReference();
				if (container instanceof GipsConstraint eConstraint) {
					reference.setConstant(data.getConstant(eConstraint, eReference.getConstant()));
				} else if (container instanceof GipsLinearFunction eFunction) {
					reference.setConstant(data.getConstant(eFunction, eReference.getConstant()));
				} else if (container instanceof GipsObjective eObjective) {
					reference.setConstant(data.getConstant(eObjective, eReference.getConstant()));
				} else {
					// Case: Container instanceof EditorGTFile
					reference.setConstant(data.getConstant((EditorFile) container, eReference.getConstant()));
				}

				if (eReference.getSetExpression() != null) {
					ValueExpressionTransformer transformer = null;
					if (reference.getConstant().getExpression() instanceof SetOperation setOp) {
						transformer = transformerFactory.createValueTransformer(localContext, setOp);
					} else {
						transformer = transformerFactory.createValueTransformer(localContext);
					}
					reference.setSetExpression(transformer.transform(eReference.getSetExpression()));
				}

				return reference;
			} else {
				throw new IllegalArgumentException("Illegal boolean expression type: " + arithmetic);
			}
		} else if (eBool instanceof GipsRelationalExpression relation) {
			RelationalExpressionTransformer transformer = //
					transformerFactory.createRelationalTransformer(localContext, setContext);
			return transformer.transform(relation);
		} else {
			throw new IllegalArgumentException("Illegal boolean expression type: " + eBool);
		}
	}
}
