package org.emoflon.gips.build.transformation.transformer;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.build.transformation.helper.ExpressionReturnType;
import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils;
import org.emoflon.gips.build.transformation.helper.TransformationContext;
import org.emoflon.gips.gipsl.gipsl.GipsAttributeExpression;
import org.emoflon.gips.gipsl.gipsl.GipsConcatenationOperation;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsElementQuery;
import org.emoflon.gips.gipsl.gipsl.GipsFilterOperation;
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
import org.emoflon.gips.intermediate.GipsIntermediate.AttributeReference;
import org.emoflon.gips.intermediate.GipsIntermediate.Context;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextReference;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingReference;
import org.emoflon.gips.intermediate.GipsIntermediate.NodeReference;
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
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern;

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
			pValue.setPattern((GTPattern) data.getPattern(ePattern.getPattern()));
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

		if (eValue.getSetExperession() != null) {
			value.setSetExpression(transform(eValue.getSetExperession()));
		}
		return value;
	}

	public ValueExpression transform(final GipsLocalContextExpression eValue) throws Exception {
		ValueExpression value = null;
		if (eValue.getExpression() instanceof GipsNodeExpression node) {
			value = transform(node, localContext);
		} else if (eValue.getExpression() instanceof GipsVariableReferenceExpression variable) {
			VariableReference vValue = factory.createVariableReference();
			if (variable.getVariable() != null) {
				vValue.setVariable(data.eVariable2Variable().get(variable.getVariable()));
			} else {
				EObject context = GipslScopeContextUtil.getLocalContext(eValue);
				if (context instanceof GipsConstraint constraint) {
					vValue.setVariable(data.eVariable2Variable().get(constraint.getContext()));
				} else if (context instanceof GipsLinearFunction fun) {
					vValue.setVariable(data.eVariable2Variable().get(fun.getContext()));
				} else if (context instanceof GipsMapping map) {
					vValue.setVariable(data.eVariable2Variable().get(map));
				} else {
					vValue.setVariable(null);
				}
			}
			vValue.setLocal(true);
			value = vValue;
		} else if (eValue.getExpression() instanceof GipsAttributeExpression eAttribute) {
			value = transform(eAttribute, localContext);
		} else if (eValue.getExpression() == null) {
			ContextReference cValue = factory.createContextReference();
			cValue.setLocal(true);
			value = cValue;
		} else {
			throw new UnsupportedOperationException("Unkown local value expression: " + eValue.getExpression());
		}
		return value;
	}

	public ValueExpression transform(final GipsSetElementExpression eValue) throws Exception {
		ValueExpression value = null;
		if (eValue.getExpression() instanceof GipsNodeExpression node) {
			value = transform(node, setContext);
		} else if (eValue.getExpression() instanceof GipsVariableReferenceExpression variable) {
			VariableReference vValue = factory.createVariableReference();
			if (variable.getVariable() != null) {
				vValue.setVariable(data.eVariable2Variable().get(variable.getVariable()));
			} else {
				vValue.setVariable(getMappingVariable(eValue));
			}
			vValue.setLocal(false);
			value = vValue;
		} else if (eValue.getExpression() instanceof GipsAttributeExpression eAttribute) {
			value = transform(eAttribute, setContext);
		} else if (eValue.getExpression() == null) {
			ContextReference cValue = factory.createContextReference();
			cValue.setLocal(false);
			value = cValue;
		} else {
			throw new UnsupportedOperationException("Unkown local value expression: " + eValue.getExpression());
		}
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

	public ValueExpression transform(final GipsNodeExpression node, final Context context) {
		NodeReference nValue = factory.createNodeReference();
		nValue.setNode(data.eNode2Node().get(node.getNode()));
		if (node.getAttributeExpression() != null) {
			nValue.setAttribute(transform(node.getAttributeExpression()));
		}
		if (context.equals(localContext)) {
			nValue.setLocal(true);
		} else {
			nValue.setLocal(false);
		}
		return nValue;
	}

	public ValueExpression transform(final GipsAttributeExpression eAttribute, final Context context) {
		AttributeReference aValue = factory.createAttributeReference();
		aValue.setAttribute(transform(eAttribute));
		if (context.equals(localContext)) {
			aValue.setLocal(true);
		} else {
			aValue.setLocal(false);
		}
		return aValue;
	}

	public AttributeExpression transform(final GipsAttributeExpression eAttribute) {
		AttributeExpression attribute = factory.createAttributeExpression();
		if (eAttribute.getRight() != null) {
			attribute.setNext(transform(eAttribute.getRight()));
		}
		attribute.setFeature(eAttribute.getAttribute().getLiteral());
		return attribute;
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
		if (eReduce instanceof GipsSumOperation eSum) {
			SetSummation sum = factory.createSetSummation();
			ArithmeticExpressionTransformer transformer = //
					transformerFactory.createArithmeticTransformer(localContext, sum);
			sum.setExpression(transformer.transform(eSum.getExpression()));
			return sum;
		} else if (eReduce instanceof GipsSimpleSelect eSelect) {
			SetSimpleSelect select = factory.createSetSimpleSelect();
			select.setOperator(switch (eSelect.getOperator()) {
			case FIRST -> org.emoflon.gips.intermediate.GipsIntermediate.SelectOperator.FIRST;
			case LAST -> org.emoflon.gips.intermediate.GipsIntermediate.SelectOperator.LAST;
			case ANY -> org.emoflon.gips.intermediate.GipsIntermediate.SelectOperator.ANY;
			});
			return select;
		} else if (eReduce instanceof GipsTypeQuery eTypeQ) {
			SetTypeQuery query = factory.createSetTypeQuery();
			query.setType((EClass) eTypeQ.getType());
			return query;
		} else if (eReduce instanceof GipsElementQuery eElementQ) {
			SetElementQuery query = factory.createSetElementQuery();
			ValueExpressionTransformer transformer = //
					transformerFactory.createValueTransformer(localContext, query);
			query.setElement(transformer.transform(eElementQ.getElement()));
			return query;
		} else if (eReduce instanceof GipsSimpleQuery eSimpleQ) {
			SetSimpleQuery query = factory.createSetSimpleQuery();
			query.setOperator(switch (eSimpleQ.getOperator()) {
			case EMPTY -> org.emoflon.gips.intermediate.GipsIntermediate.QueryOperator.EMPTY;
			case NOT_EMPTY -> org.emoflon.gips.intermediate.GipsIntermediate.QueryOperator.NOT_EMPTY;
			case COUNT -> org.emoflon.gips.intermediate.GipsIntermediate.QueryOperator.COUNT;
			});
			return query;
		} else {
			throw new UnsupportedOperationException("Unkown set reduce expression type: " + eReduce);
		}
	}

	public SetOperation transform(final GipsSetOperation eOperation) throws Exception {
		if (eOperation instanceof GipsFilterOperation eFilter) {
			SetFilter filter = factory.createSetFilter();
			BooleanExpressionTransformer transformer = //
					transformerFactory.createBooleanTransformer(localContext, filter);
			filter.setExpression(transformer.transform(eFilter.getExpression()));
			return filter;
		} else if (eOperation instanceof GipsTypeSelect eSelect) {
			SetTypeSelect select = factory.createSetTypeSelect();
			select.setType((EClass) eSelect.getType());
			return select;
		} else if (eOperation instanceof GipsSortOperation eSort) {
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
				relation.setLhs(transformer.transform(node, sort));
			} else if (eSort.getPredicate().getE1() instanceof GipsAttributeExpression attribute) {
				relation.setLhs(transformer.transform(attribute, sort));
			} else {
				throw new IllegalArgumentException(
						"Illegal operand type in sort predicate: " + eSort.getPredicate().getE1());
			}

			if (eSort.getPredicate().getE2() instanceof GipsNodeExpression node) {
				relation.setRhs(transformer.transform(node, sort));
			} else if (eSort.getPredicate().getE2() instanceof GipsAttributeExpression attribute) {
				relation.setRhs(transformer.transform(attribute, sort));
			} else {
				throw new IllegalArgumentException(
						"Illegal operand type in sort predicate: " + eSort.getPredicate().getE2());
			}

			ExpressionReturnType lhsType = GipsTransformationUtils
					.extractReturnType((ValueExpression) relation.getLhs());
			ExpressionReturnType rhsType = GipsTransformationUtils
					.extractReturnType((ValueExpression) relation.getRhs());

			if (lhsType == ExpressionReturnType.object || rhsType == ExpressionReturnType.object) {
				relation.setRequiresComparables(true);
			} else {
				relation.setRequiresComparables(false);
			}
			return sort;
		} else if (eOperation instanceof GipsSimpleAlgorithm eAlgo) {
			SetSimpleOperation operation = factory.createSetSimpleOperation();
			operation.setOperator(switch (eAlgo.getOperator()) {
			case UNIQUE -> org.emoflon.gips.intermediate.GipsIntermediate.SetOperator.UNIQUE;
			});
			return operation;
		} else if (eOperation instanceof GipsConcatenationOperation eCat) {
			SetConcatenation concat = factory.createSetConcatenation();
			concat.setOperator(switch (eCat.getOperator()) {
			case PREPEND -> org.emoflon.gips.intermediate.GipsIntermediate.ConcatenationOperator.PREPEND;
			case APPEND -> org.emoflon.gips.intermediate.GipsIntermediate.ConcatenationOperator.APPEND;
			});
			ValueExpressionTransformer transformer //
					= transformerFactory.createValueTransformer(localContext, concat);
			concat.setOther(transformer.transform(eCat.getValue()));
			return concat;
		} else if (eOperation instanceof GipsTransformOperation eTransform) {
			SetTransformation transform = factory.createSetTransformation();
			ValueExpressionTransformer transformer //
					= transformerFactory.createValueTransformer(localContext, transform);
			transform.setExpression(transformer.transform(eTransform.getExpression()));
			return transform;
		} else {
			throw new UnsupportedOperationException("Unkown set operation expression type: " + eOperation);
		}
	}

}
