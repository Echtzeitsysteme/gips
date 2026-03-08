package org.emoflon.gips.build.transformation.transformer;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.emoflon.gips.build.transformation.helper.ExpressionReturnType;
import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils;
import org.emoflon.gips.build.transformation.helper.TransformationContext;
import org.emoflon.gips.gipsl.gipsl.GipsAttributeExpression;
import org.emoflon.gips.gipsl.gipsl.GipsConcatenationOperation;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsElementQuery;
import org.emoflon.gips.gipsl.gipsl.GipsFilterOperation;
import org.emoflon.gips.gipsl.gipsl.GipsJoinAllOperation;
import org.emoflon.gips.gipsl.gipsl.GipsJoinBySelectionOperation;
import org.emoflon.gips.gipsl.gipsl.GipsJoinOperation;
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunction;
import org.emoflon.gips.gipsl.gipsl.GipsLocalContextExpression;
import org.emoflon.gips.gipsl.gipsl.GipsMapping;
import org.emoflon.gips.gipsl.gipsl.GipsMappingExpression;
import org.emoflon.gips.gipsl.gipsl.GipsNodeExpression;
import org.emoflon.gips.gipsl.gipsl.GipsPatternExpression;
import org.emoflon.gips.gipsl.gipsl.GipsReduceOperation;
import org.emoflon.gips.gipsl.gipsl.GipsRuleExpression;
import org.emoflon.gips.gipsl.gipsl.GipsSetElementExpression;
import org.emoflon.gips.gipsl.gipsl.GipsSetExpression;
import org.emoflon.gips.gipsl.gipsl.GipsSetOperation;
import org.emoflon.gips.gipsl.gipsl.GipsSimpleAlgorithm;
import org.emoflon.gips.gipsl.gipsl.GipsSimpleQuery;
import org.emoflon.gips.gipsl.gipsl.GipsSimpleSelect;
import org.emoflon.gips.gipsl.gipsl.GipsSortOperation;
import org.emoflon.gips.gipsl.gipsl.GipsSumOperation;
import org.emoflon.gips.gipsl.gipsl.GipsTransformOperation;
import org.emoflon.gips.gipsl.gipsl.GipsTypeExpression;
import org.emoflon.gips.gipsl.gipsl.GipsTypeQuery;
import org.emoflon.gips.gipsl.gipsl.GipsTypeSelect;
import org.emoflon.gips.gipsl.gipsl.GipsValueExpression;
import org.emoflon.gips.gipsl.gipsl.GipsVariableReferenceExpression;
import org.emoflon.gips.gipsl.scoping.GipslScopeContextUtil;
import org.emoflon.gips.intermediate.GipsIntermediate.AttributeExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Context;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextReference;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingReference;
import org.emoflon.gips.intermediate.GipsIntermediate.MemberExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.MemberReference;
import org.emoflon.gips.intermediate.GipsIntermediate.NodeExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternReference;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.RuleReference;
import org.emoflon.gips.intermediate.GipsIntermediate.SetConcatenation;
import org.emoflon.gips.intermediate.GipsIntermediate.SetElementQuery;
import org.emoflon.gips.intermediate.GipsIntermediate.SetExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.SetFilter;
import org.emoflon.gips.intermediate.GipsIntermediate.SetOperation;
import org.emoflon.gips.intermediate.GipsIntermediate.SetReduce;
import org.emoflon.gips.intermediate.GipsIntermediate.SetSimpleOperation;
import org.emoflon.gips.intermediate.GipsIntermediate.SetSimpleQuery;
import org.emoflon.gips.intermediate.GipsIntermediate.SetSimpleSelect;
import org.emoflon.gips.intermediate.GipsIntermediate.SetSort;
import org.emoflon.gips.intermediate.GipsIntermediate.SetSummation;
import org.emoflon.gips.intermediate.GipsIntermediate.SetTransformation;
import org.emoflon.gips.intermediate.GipsIntermediate.SetTypeQuery;
import org.emoflon.gips.intermediate.GipsIntermediate.SetTypeSelect;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeReference;
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Variable;
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference;
import org.emoflon.ibex.gt.editor.gT.EditorNode;

public class ValueExpressionTransformer extends TransformationContext {

	protected ValueExpressionTransformer(final GipsTransformationData data, final Context localContext,
			final TransformerFactory factory) {
		super(data, localContext, factory);
	}

	protected ValueExpressionTransformer(final GipsTransformationData data, final Context localContext,
			final Context setContext, TransformerFactory factory) {
		super(data, localContext, setContext, factory);
	}

	public ValueExpression transform(final GipsValueExpression eValue) throws Exception {
		ValueExpression value = null;
		if (eValue.getValue() instanceof GipsMappingExpression eMapping) {
			MappingReference mValue = factory.createMappingReference();
			mValue.setMapping(data.eMapping2Mapping().get(eMapping.getMapping()));
			value = mValue;

		} else if (eValue.getValue() instanceof GipsTypeExpression eType) {
			TypeReference tValue = factory.createTypeReference();
			data.requiredTypes().add(eType.getType());
			tValue.setType(eType.getType());
			value = tValue;

		} else if (eValue.getValue() instanceof GipsPatternExpression ePattern) {
			PatternReference pValue = factory.createPatternReference();
			pValue.setPattern(data.getPattern(ePattern.getPattern()));
			value = pValue;

		} else if (eValue.getValue() instanceof GipsRuleExpression eRule) {
			RuleReference rValue = factory.createRuleReference();
			rValue.setRule(data.getRule(eRule.getRule()));
			value = rValue;

		} else if (eValue.getValue() instanceof GipsLocalContextExpression eContext) {
			value = transform(eContext);

		} else if (eValue.getValue() instanceof GipsSetElementExpression eSet) {
			value = transform(eSet);

		} else {
			throw new UnsupportedOperationException("Unkown value expression: " + eValue);

		}

		if (eValue.getSetExpression() != null)
			value.setSetExpression(transform(eValue.getSetExpression()));

		return value;
	}

	public ValueExpression transform(final GipsLocalContextExpression eValue) throws Exception {
		ContextReference value = null;

		if (eValue.getExpression() == null) {
			value = factory.createContextReference();

		} else if (eValue.getExpression() instanceof GipsVariableReferenceExpression eVariable) {
			VariableReference varRef = factory.createVariableReference();
			value = varRef;

			if (eVariable.getVariable() != null) {
				varRef.setVariable(data.eVariable2Variable().get(eVariable.getVariable()));

			} else {
				EObject context = GipslScopeContextUtil.getLocalContext(eValue);
				Variable variable = switch (context) {
				case GipsConstraint constraint -> data.eVariable2Variable().get(constraint.getContext());
				case GipsLinearFunction fun -> data.eVariable2Variable().get(fun.getContext());
				case GipsMapping map -> data.eVariable2Variable().get(map);
				default -> null;
				};
				varRef.setVariable(variable);
			}

		} else if (eValue.getExpression() instanceof GipsNodeExpression eNode) {
			value = transform(eNode);

		} else if (eValue.getExpression() instanceof GipsAttributeExpression eAttribute) {
			value = transform(eAttribute);

		} else {
			throw new UnsupportedOperationException("Unkown element value expression: " + eValue.getExpression());

		}

		value.setLocal(true);
		return value;
	}

	public ValueExpression transform(final GipsSetElementExpression eValue) throws Exception {
		ContextReference value = null;

		if (eValue.getExpression() == null) {
			value = factory.createContextReference();

		} else if (eValue.getExpression() instanceof GipsVariableReferenceExpression eVariable) {
			VariableReference varRef = factory.createVariableReference();
			value = varRef;

			if (eVariable.getVariable() != null) {
				varRef.setVariable(data.eVariable2Variable().get(eVariable.getVariable()));
			} else {
				varRef.setVariable(getMappingVariable(eValue));
			}

		} else if (eValue.getExpression() instanceof GipsNodeExpression eNode) {
			value = transform(eNode);

		} else if (eValue.getExpression() instanceof GipsAttributeExpression eAttribute) {
			value = transform(eAttribute);

		} else {
			throw new UnsupportedOperationException("Unkown element value expression: " + eValue.getExpression());

		}

		value.setLocal(false);
		return value;
	}

	public Variable getMappingVariable(final GipsSetElementExpression eValue) {
		EObject context = GipslScopeContextUtil.getSetContext(eValue);
		if (context instanceof GipsConstraint constraint) {
			return data.eVariable2Variable().get(constraint.getContext());
		} else if (context instanceof GipsLinearFunction fun) {
			return data.eVariable2Variable().get(fun.getContext());
		} else if (context instanceof GipsMappingExpression map) {
			return data.eVariable2Variable().get(map.getMapping());
		} else if (context instanceof GipsLocalContextExpression lce) {
			EObject rootContext = GipslScopeContextUtil.getLocalContext(lce);
			if (rootContext instanceof GipsConstraint constraint) {
				return data.eVariable2Variable().get(constraint.getContext());
			} else if (rootContext instanceof GipsLinearFunction fun) {
				return data.eVariable2Variable().get(fun.getContext());
			} else {
				return null;
			}
		} else if (context instanceof GipsSetElementExpression set) {
			return getMappingVariable(set);
		} else if (context instanceof GipsValueExpression ve) {
			if (ve instanceof GipsMappingExpression map) {
				return data.eVariable2Variable().get(map.getMapping());
			} else if (ve instanceof GipsLocalContextExpression lce) {
				EObject rootContext = GipslScopeContextUtil.getLocalContext(lce);
				if (rootContext instanceof GipsConstraint constraint) {
					return data.eVariable2Variable().get(constraint.getContext());
				} else if (rootContext instanceof GipsLinearFunction fun) {
					return data.eVariable2Variable().get(fun.getContext());
				} else {
					return null;
				}
			} else if (ve instanceof GipsSetElementExpression set) {
				return getMappingVariable(set);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	private GipsVariableReferenceExpression getVariableReference(EObject eObject) {
		EObject next = eObject;
		while (next != null) {
			if (next instanceof GipsVariableReferenceExpression variable)
				return variable;

			next = switch (next) {
			case GipsAttributeExpression attribute -> attribute.getRight();
			default -> throw new IllegalArgumentException("Unexpected value: " + next);
			};
		}

		return null;
	}

	private ContextReference transform(GipsNodeExpression eNode) throws Exception {
		GipsVariableReferenceExpression endsWith = getVariableReference(eNode.getExpression());
		if (endsWith != null) {
			VariableReference varRef = transform(endsWith);
			varRef.setPrevious(transformMemberExpression(eNode));
			return varRef;
		}

		NodeExpression node = transform(eNode.getNode());
		node.setNext(transformMemberExpression(eNode.getExpression()));

		MemberReference memRef = factory.createMemberReference();
		memRef.setMember(node);

		return memRef;
	}

	private ContextReference transform(final GipsAttributeExpression eAttribute) throws Exception {
		GipsVariableReferenceExpression endsWith = getVariableReference(eAttribute.getRight());
		if (endsWith != null) {
			VariableReference varRef = transform(endsWith);
			varRef.setPrevious(transformMemberExpression(eAttribute));
			return varRef;
		}

		AttributeExpression attribute = transform(eAttribute.getAttribute().getLiteral());
		attribute.setNext(transformMemberExpression(eAttribute.getRight()));

		MemberReference memRef = factory.createMemberReference();
		memRef.setMember(attribute);

		return memRef;
	}

	private MemberExpression transformMemberExpression(EObject expression) throws Exception {
		return switch (expression) {
		case GipsAttributeExpression eAttribute -> {
			AttributeExpression attribute = transform(eAttribute.getAttribute().getLiteral());
			attribute.setNext(transformMemberExpression(eAttribute.getRight()));
			yield attribute;
		}

		case GipsNodeExpression eNode -> {
			NodeExpression node = transform(eNode.getNode());
			node.setNext(transformMemberExpression(eNode.getExpression()));
			yield node;
		}

		// ignore, the expression root should be set to VariableReference
		case GipsVariableReferenceExpression eVariable -> null;

		case null -> null;

		default -> throw new IllegalArgumentException("Unexpected value: " + expression);
		};
	}

	private VariableReference transform(GipsVariableReferenceExpression eVariable) {
		VariableReference varRef = factory.createVariableReference();
		Variable variable = data.eVariable2Variable().get(eVariable.getVariable());
		varRef.setVariable(variable);
		return varRef;
	}

	private NodeExpression transform(EditorNode eNode) {
		NodeExpression node = factory.createNodeExpression();
		node.setNode(data.eNode2Node().get(eNode));
		return node;
	}

	private AttributeExpression transform(EStructuralFeature feature) {
		AttributeExpression attribute = factory.createAttributeExpression();
		attribute.setFeature(feature);
		return attribute;
	}

	public ContextReference transformContextAware(GipsVariableReferenceExpression eVariable, EObject context)
			throws Exception {
		ContextReference value = transform(eVariable);
		value.setLocal(context.equals(localContext));
		return value;
	}

	public ContextReference transformContextAware(GipsNodeExpression eNode, EObject context) throws Exception {
		ContextReference value = transform(eNode);
		value.setLocal(context.equals(localContext));
		return value;
	}

	public ContextReference transformContextAware(GipsAttributeExpression eAttribute, EObject context)
			throws Exception {
		ContextReference value = transform(eAttribute);
		value.setLocal(context.equals(localContext));
		return value;
	}

	public MemberReference transformContextAware(EditorNode eNode, Context context) {
		MemberReference value = factory.createMemberReference();
		value.setMember(transform(eNode));
		value.setLocal(context.equals(localContext));
		return value;
	}

	public MemberReference transformContextAware(EStructuralFeature feature, Context context) {
		AttributeExpression attribute = factory.createAttributeExpression();
		attribute.setFeature(feature);

		MemberReference value = factory.createMemberReference();
		value.setMember(transform(feature));
		value.setLocal(context.equals(localContext));
		return value;
	}

	public SetExpression transform(final GipsSetExpression eSet) throws Exception {
		SetExpression set = factory.createSetExpression();
		transform(set, eSet);
		return set;
	}

	public void transform(final SetExpression set, final GipsSetExpression eSet) throws Exception {
		if (eSet.getRight() == null && eSet.getOperation() instanceof GipsReduceOperation eReduce) {
			set.setSetReduce(transform(eReduce));
		} else if (eSet.getRight() == null && eSet.getOperation() instanceof GipsSetOperation eOp) {
			if (set.getSetOperation() != null) {
				set.getSetOperation().setNext(transform(eOp));
			} else {
				set.setSetOperation(transform(eOp));
			}
		} else if (eSet.getOperation() instanceof GipsSetOperation eOp
				&& eSet.getRight() instanceof GipsSetExpression right) {
			if (set.getSetOperation() != null) {
				set.getSetOperation().setNext(transform(eOp));
			} else {
				set.setSetOperation(transform(eOp));
			}
			transform(set, right);
		} else {
			throw new UnsupportedOperationException("Unkown set expression type: " + eSet.getRight());
		}
	}

	public SetReduce transform(final GipsReduceOperation eReduce) throws Exception {
		return switch (eReduce) {
		case GipsSumOperation eSum -> transform(eSum);
		case GipsSimpleSelect eSelect -> transform(eSelect);
		case GipsTypeQuery eTypeQ -> transform(eTypeQ);
		case GipsElementQuery eElementQ -> transform(eElementQ);
		case GipsSimpleQuery eSimpleQ -> transform(eSimpleQ);
		case null, default -> throw new UnsupportedOperationException("Unkown set reduce expression type: " + eReduce);
		};
	}

	public SetReduce transform(final GipsSumOperation eSum) throws Exception {
		SetSummation sum = factory.createSetSummation();
		ArithmeticExpressionTransformer transformer = //
				transformerFactory.createArithmeticTransformer(localContext, sum);
		sum.setExpression(transformer.transform(eSum.getExpression()));
		return sum;
	}

	public SetReduce transform(GipsSimpleSelect eSelect) {
		SetSimpleSelect select = factory.createSetSimpleSelect();
		select.setOperator(switch (eSelect.getOperator()) {
		case FIRST -> org.emoflon.gips.intermediate.GipsIntermediate.SelectOperator.FIRST;
		case LAST -> org.emoflon.gips.intermediate.GipsIntermediate.SelectOperator.LAST;
		case ANY -> org.emoflon.gips.intermediate.GipsIntermediate.SelectOperator.ANY;
		});
		return select;
	}

	public SetReduce transform(GipsTypeQuery eTypeQ) {
		SetTypeQuery query = factory.createSetTypeQuery();
		query.setType((EClass) eTypeQ.getType());
		return query;
	}

	public SetReduce transform(GipsElementQuery eElementQ) throws Exception {
		SetElementQuery query = factory.createSetElementQuery();
		ValueExpressionTransformer transformer = //
				transformerFactory.createValueTransformer(localContext, query);
		query.setElement(transformer.transform(eElementQ.getElement()));
		return query;
	}

	public SetReduce transform(GipsSimpleQuery eSimpleQ) {
		SetSimpleQuery query = factory.createSetSimpleQuery();
		query.setOperator(switch (eSimpleQ.getOperator()) {
		case EMPTY -> org.emoflon.gips.intermediate.GipsIntermediate.QueryOperator.EMPTY;
		case NOT_EMPTY -> org.emoflon.gips.intermediate.GipsIntermediate.QueryOperator.NOT_EMPTY;
		case COUNT -> org.emoflon.gips.intermediate.GipsIntermediate.QueryOperator.COUNT;
		});
		return query;
	}

	public SetOperation transform(final GipsSetOperation eOperation) throws Exception {
		return switch (eOperation) {
		case GipsFilterOperation eFilter -> transform(eFilter);
		case GipsJoinOperation eJoin -> transform(eJoin);
		case GipsTypeSelect eSelect -> transform(eSelect);
		case GipsSortOperation eSort -> transform(eSort);
		case GipsSimpleAlgorithm eAlgo -> transform(eAlgo);
		case GipsConcatenationOperation eCat -> transform(eCat);
		case GipsTransformOperation eTransform -> transform(eTransform);
		case null, default ->
			throw new UnsupportedOperationException("Unkown set operation expression type: " + eOperation);
		};
	}

	public SetOperation transform(GipsFilterOperation eFilter) throws Exception {
		SetFilter filter = factory.createSetFilter();
		BooleanExpressionTransformer transformer = //
				transformerFactory.createBooleanTransformer(localContext, filter);
		filter.setExpression(transformer.transform(eFilter.getExpression()));
		return filter;
	}

	public SetOperation transform(GipsJoinOperation eJoin) throws Exception {
		return switch (eJoin) {
		case GipsJoinAllOperation eJoinAll -> transform(eJoinAll);
		case GipsJoinBySelectionOperation eJoinSelection -> transform(eJoinSelection);
		case null, default ->
			throw new UnsupportedOperationException("Unkown join operation expression type: " + eJoin);
		};
	}

	public SetOperation transform(GipsJoinAllOperation eJoinAll) throws Exception {
		SetFilter filter = factory.createSetFilter();
		BooleanExpressionTransformer transformer = //
				transformerFactory.createBooleanTransformer(localContext, filter);
		filter.setExpression(transformer.transform(eJoinAll));
		return filter;
	}

	public SetOperation transform(GipsJoinBySelectionOperation eJoinSelection) throws Exception {
		SetFilter filter = factory.createSetFilter();
		BooleanExpressionTransformer transformer = //
				transformerFactory.createBooleanTransformer(localContext, filter);
		filter.setExpression(transformer.transform(eJoinSelection));
		return filter;
	}

	public SetOperation transform(GipsTypeSelect eSelect) {
		SetTypeSelect select = factory.createSetTypeSelect();
		select.setType((EClass) eSelect.getType());
		return select;
	}

	public SetOperation transform(GipsSortOperation eSort) throws Exception {
		SetSort sort = factory.createSetSort();
		RelationalExpression relation = factory.createRelationalExpression();
		sort.setPredicate(relation);

		relation.setOperator(switch (eSort.getPredicate().getRelation()) {
		case GREATER -> org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator.GREATER;
		case GREATER_OR_EQUAL -> org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator.GREATER_OR_EQUAL;
		case SMALLER -> org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator.LESS;
		case SMALLER_OR_EQUAL -> org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator.LESS_OR_EQUAL;
		default -> throw new IllegalArgumentException(
				"Illegal relational operator in sort predicate: " + eSort.getPredicate().getRelation());

		});

		ValueExpressionTransformer transformer //
				= transformerFactory.createValueTransformer(localContext, sort);
		if (eSort.getPredicate().getE1() instanceof GipsNodeExpression node) {
			relation.setLhs(transformer.transformContextAware(node, sort));
		} else if (eSort.getPredicate().getE1() instanceof GipsAttributeExpression attribute) {
			relation.setLhs(transformer.transformContextAware(attribute, sort));
		} else {
			throw new IllegalArgumentException(
					"Illegal operand type in sort predicate: " + eSort.getPredicate().getE1());
		}

		if (eSort.getPredicate().getE2() instanceof GipsNodeExpression node) {
			relation.setRhs(transformer.transformContextAware(node, sort));
		} else if (eSort.getPredicate().getE2() instanceof GipsAttributeExpression attribute) {
			relation.setRhs(transformer.transformContextAware(attribute, sort));
		} else {
			throw new IllegalArgumentException(
					"Illegal operand type in sort predicate: " + eSort.getPredicate().getE2());
		}

		ExpressionReturnType lhsType = GipsTransformationUtils.extractReturnType((ValueExpression) relation.getLhs());
		ExpressionReturnType rhsType = GipsTransformationUtils.extractReturnType((ValueExpression) relation.getRhs());

		if (lhsType == ExpressionReturnType.object || rhsType == ExpressionReturnType.object) {
			relation.setRequiresComparables(true);
		} else {
			relation.setRequiresComparables(false);
		}
		return sort;
	}

	public SetOperation transform(GipsSimpleAlgorithm eAlgo) {
		SetSimpleOperation operation = factory.createSetSimpleOperation();
		operation.setOperator(switch (eAlgo.getOperator()) {
		case UNIQUE -> org.emoflon.gips.intermediate.GipsIntermediate.SetOperator.UNIQUE;
		});
		return operation;
	}

	public SetOperation transform(GipsConcatenationOperation eCat) throws Exception {
		SetConcatenation concat = factory.createSetConcatenation();
		concat.setOperator(switch (eCat.getOperator()) {
		case PREPEND -> org.emoflon.gips.intermediate.GipsIntermediate.ConcatenationOperator.PREPEND;
		case APPEND -> org.emoflon.gips.intermediate.GipsIntermediate.ConcatenationOperator.APPEND;
		});
		ValueExpressionTransformer transformer //
				= transformerFactory.createValueTransformer(localContext, concat);
		concat.setOther(transformer.transform(eCat.getValue()));
		return concat;
	}

	public SetOperation transform(GipsTransformOperation eTransform) throws Exception {
		SetTransformation transform = factory.createSetTransformation();
		ValueExpressionTransformer transformer //
				= transformerFactory.createValueTransformer(localContext, transform);
		transform.setExpression(transformer.transform(eTransform.getExpression()));
		return transform;
	}

}
