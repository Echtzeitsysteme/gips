package org.emoflon.gips.build.transformation.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.build.transformation.helper.TransformationContext;
import org.emoflon.gips.gipsl.gipsl.EditorGTFile;
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
import org.emoflon.gips.gipsl.gipsl.GipsJoinAllOperation;
import org.emoflon.gips.gipsl.gipsl.GipsJoinBySelectionOperation;
import org.emoflon.gips.gipsl.gipsl.GipsJoinPairSelection;
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunction;
import org.emoflon.gips.gipsl.gipsl.GipsMapping;
import org.emoflon.gips.gipsl.gipsl.GipsMappingExpression;
import org.emoflon.gips.gipsl.gipsl.GipsObjective;
import org.emoflon.gips.gipsl.gipsl.GipsPatternExpression;
import org.emoflon.gips.gipsl.gipsl.GipsRelationalExpression;
import org.emoflon.gips.gipsl.gipsl.GipsRuleExpression;
import org.emoflon.gips.gipsl.gipsl.GipsTypeExpression;
import org.emoflon.gips.gipsl.gipsl.GipsValueExpression;
import org.emoflon.gips.gipsl.gipsl.impl.EditorGTFileImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsConstraintImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsLinearFunctionImpl;
import org.emoflon.gips.gipsl.gipsl.impl.GipsObjectiveImpl;
import org.emoflon.gips.gipsl.scoping.GipslScopeContextUtil;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanBinaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanBinaryOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanUnaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantReference;
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantValue;
import org.emoflon.gips.intermediate.GipsIntermediate.Context;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextReference;
import org.emoflon.gips.intermediate.GipsIntermediate.NodeReference;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.SetOperation;
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;

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
		return switch (eBool) {
		case GipsBooleanImplication implication ->
			throw new IllegalArgumentException("Illegal boolean operation: " + implication);
		case GipsBooleanDisjunction disjunction -> transform(disjunction);
		case GipsBooleanConjunction conjunction -> transform(conjunction);
		case GipsBooleanNegation negation -> transform(negation);
		case GipsBooleanBracket bracket -> transform(bracket);
		case GipsBooleanLiteral eLiteral -> transform(eLiteral);
		case GipsArithmeticExpression arithmetic -> transform(arithmetic);
		case GipsRelationalExpression relation -> transform(relation);
		case null, default -> throw new IllegalArgumentException("Illegal boolean expression type: " + eBool);
		};
	}

	private BooleanExpression transform(GipsBooleanDisjunction disjunction) throws Exception {
		BooleanBinaryExpression binary = factory.createBooleanBinaryExpression();
		binary.setOperator(switch (disjunction.getOperator()) {
		case OR -> org.emoflon.gips.intermediate.GipsIntermediate.BooleanBinaryOperator.OR;
		case XOR -> org.emoflon.gips.intermediate.GipsIntermediate.BooleanBinaryOperator.XOR;
		});
		binary.setLhs(transform(disjunction.getLeft()));
		binary.setRhs(transform(disjunction.getRight()));
		return binary;
	}

	private BooleanExpression transform(GipsBooleanConjunction conjunction) throws Exception {
		BooleanBinaryExpression binary = factory.createBooleanBinaryExpression();
		binary.setOperator(switch (conjunction.getOperator()) {
		case AND -> org.emoflon.gips.intermediate.GipsIntermediate.BooleanBinaryOperator.AND;
		});
		binary.setLhs(transform(conjunction.getLeft()));
		binary.setRhs(transform(conjunction.getRight()));
		return binary;
	}

	private BooleanExpression transform(GipsBooleanNegation negation) throws Exception {
		BooleanUnaryExpression unary = factory.createBooleanUnaryExpression();
		unary.setOperator(org.emoflon.gips.intermediate.GipsIntermediate.BooleanUnaryOperator.NOT);
		unary.setOperand(transform(negation.getOperand()));
		return unary;
	}

	private BooleanExpression transform(GipsBooleanBracket bracket) throws Exception {
		BooleanUnaryExpression unary = factory.createBooleanUnaryExpression();
		unary.setOperator(org.emoflon.gips.intermediate.GipsIntermediate.BooleanUnaryOperator.BRACKET);
		unary.setOperand(transform(bracket.getOperand()));
		return unary;
	}

	private BooleanExpression transform(GipsBooleanLiteral eLiteral) {
		BooleanLiteral literal = factory.createBooleanLiteral();
		literal.setLiteral(eLiteral.isLiteral());
		return literal;
	}

	private BooleanExpression transform(GipsArithmeticExpression arithmetic) throws Exception {
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
			EObject container = (EObject) GipslScopeContextUtil.getContainer(eReference, Set.of(EditorGTFileImpl.class,
					GipsConstraintImpl.class, GipsLinearFunctionImpl.class, GipsObjectiveImpl.class));

			ConstantReference reference = factory.createConstantReference();
			if (container instanceof GipsConstraint eConstraint) {
				reference.setConstant(data.getConstant(eConstraint, eReference.getConstant()));
			} else if (container instanceof GipsLinearFunction eFunction) {
				reference.setConstant(data.getConstant(eFunction, eReference.getConstant()));
			} else if (container instanceof GipsObjective eObjective) {
				reference.setConstant(data.getConstant(eObjective, eReference.getConstant()));
			} else {
				// Case: Container instanceof EditorGTFile
				reference.setConstant(data.getConstant((EditorGTFile) container, eReference.getConstant()));
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
	}

	private BooleanExpression transform(GipsRelationalExpression relation) throws Exception {
		RelationalExpressionTransformer transformer = //
				transformerFactory.createRelationalTransformer(localContext, setContext);
		return transformer.transform(relation);
	}

	public BooleanExpression transform(GipsJoinAllOperation eJoinAll) {
		EObject localContext = GipslScopeContextUtil.getLocalContext(eJoinAll);
		EObject setContext = GipslScopeContextUtil.getSetContext(eJoinAll);

		if (setContext instanceof GipsMappingExpression && localContext instanceof GipsMapping || //
				setContext instanceof GipsRuleExpression && localContext instanceof EditorPattern || //
				setContext instanceof GipsPatternExpression && localContext instanceof EditorPattern || //
				setContext instanceof GipsTypeExpression && localContext instanceof EClass) {
			// -> element == context
			ContextReference lhs = factory.createContextReference();
			lhs.setLocal(false);
			ContextReference rhs = factory.createContextReference();
			rhs.setLocal(true);

			RelationalExpression relation = factory.createRelationalExpression();
			relation.setOperator(RelationalOperator.EQUAL);
			relation.setRequiresComparables(true);
			relation.setLhs(lhs);
			relation.setRhs(rhs);

			// element == context
			return relation;
		}

		EditorPattern patternRef = GipslScopeContextUtil.getPatternOrRuleOf(localContext);
		Collection<EditorNode> nodes = GipslScopeContextUtil.getNonCreatedEditorNodes(patternRef);
		List<RelationalExpression> comparisons = new ArrayList<>(nodes.size());
		for (EditorNode node : nodes) {
			NodeReference lhs = factory.createNodeReference();
			lhs.setLocal(false);
			lhs.setNode(data.eNode2Node().get(node));

			NodeReference rhs = factory.createNodeReference();
			rhs.setLocal(true);
			rhs.setNode(data.eNode2Node().get(node));

			RelationalExpression relation = factory.createRelationalExpression();
			relation.setOperator(RelationalOperator.EQUAL);
			relation.setRequiresComparables(true);
			relation.setLhs(lhs);
			relation.setRhs(rhs);
			comparisons.add(relation);
		}

		return concatRelationalExpressions(comparisons, BooleanBinaryOperator.AND);
	}

	public BooleanExpression transform(GipsJoinBySelectionOperation eJoin) throws Exception {
		ValueExpressionTransformer transformer = //
				transformerFactory.createValueTransformer(localContext, setContext);

		if (eJoin.getSingleJoin() != null) {
			ValueExpression lhs;
			ValueExpression rhs;

			if (GipslScopeContextUtil.getSetContext(eJoin) instanceof GipsTypeExpression) {
				// -> element == context.node.x
				ContextReference contextRef = factory.createContextReference();
				contextRef.setLocal(false);
				lhs = contextRef;

				rhs = transformer.transform(eJoin.getSingleJoin().getNode(), localContext);
			} else {
				// -> element.node.x == context
				lhs = transformer.transform(eJoin.getSingleJoin().getNode(), setContext);

				ContextReference contextRef = factory.createContextReference();
				contextRef.setLocal(true);
				rhs = contextRef;
			}

			RelationalExpression relation = factory.createRelationalExpression();
			relation.setOperator(RelationalOperator.EQUAL);
			relation.setRequiresComparables(true);
			relation.setLhs(lhs);
			relation.setRhs(rhs);

			return relation;
		} else {
			// build all pairings
			List<RelationalExpression> comparisons = new ArrayList<>(eJoin.getPairJoin().size());
			for (GipsJoinPairSelection selection : eJoin.getPairJoin()) {
				RelationalExpression relation = factory.createRelationalExpression();
				relation.setOperator(RelationalOperator.EQUAL);
				relation.setRequiresComparables(true);
				relation.setLhs(transformer.transform(selection.getLeftNode(), setContext));
				relation.setRhs(transformer.transform(selection.getRightNode(), localContext));
				comparisons.add(relation);
			}

			return concatRelationalExpressions(comparisons, BooleanBinaryOperator.AND);
		}
	}

	private BooleanExpression concatRelationalExpressions(List<RelationalExpression> relationals,
			BooleanBinaryOperator operator) {
		if (relationals.size() == 0) {
			BooleanLiteral literal = factory.createBooleanLiteral();
			literal.setLiteral(true);
			return literal;
		} else if (relationals.size() == 1) {
			return relationals.getFirst();
		} else {
			// join all pairings
			BooleanBinaryExpression root = factory.createBooleanBinaryExpression();
			root.setOperator(operator);
			root.setLhs(relationals.getFirst());

			BooleanBinaryExpression chain = root;
			for (int i = 1; i < relationals.size() - 1; ++i) {
				BooleanBinaryExpression tmp = factory.createBooleanBinaryExpression();
				tmp.setOperator(operator);
				tmp.setLhs(relationals.get(i));

				chain.setRhs(tmp);
				chain = tmp;
			}

			chain.setRhs(relationals.getLast());

			return root;
		}
	}
}
