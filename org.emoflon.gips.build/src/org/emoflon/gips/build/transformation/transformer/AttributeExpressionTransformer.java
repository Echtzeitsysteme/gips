package org.emoflon.gips.build.transformation.transformer;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils;
import org.emoflon.gips.build.transformation.helper.TransformationContext;
import org.emoflon.gips.gipsl.gipsl.GipsAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsContains;
import org.emoflon.gips.gipsl.gipsl.GipsContextExpr;
import org.emoflon.gips.gipsl.gipsl.GipsFeatureExpr;
import org.emoflon.gips.gipsl.gipsl.GipsFeatureLit;
import org.emoflon.gips.gipsl.gipsl.GipsLambdaAttributeExpression;
import org.emoflon.gips.gipsl.gipsl.GipsLambdaSelfExpression;
import org.emoflon.gips.gipsl.gipsl.GipsMapping;
import org.emoflon.gips.gipsl.gipsl.GipsMappingAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsMappingCheckValue;
import org.emoflon.gips.gipsl.gipsl.GipsMappingContext;
import org.emoflon.gips.gipsl.gipsl.GipsMappingValue;
import org.emoflon.gips.gipsl.gipsl.GipsNodeAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsPatternAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsPatternContext;
import org.emoflon.gips.gipsl.gipsl.GipsStreamArithmetic;
import org.emoflon.gips.gipsl.gipsl.GipsStreamBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsStreamExpr;
import org.emoflon.gips.gipsl.gipsl.GipsTypeAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsTypeContext;
import org.emoflon.gips.gipsl.gipsl.GipsVariableOperationExpression;
import org.emoflon.gips.gipsl.scoping.GipslScopeContextUtil;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNode;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNodeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNode;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNodeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.FeatureExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingVariableValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeValue;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.gips.intermediate.GipsIntermediate.Pattern;
import org.emoflon.gips.intermediate.GipsIntermediate.Type;
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression;
import org.emoflon.ibex.gt.gtl.gTL.SlimRule;

public abstract class AttributeExpressionTransformer<T extends EObject> extends TransformationContext<T> {
	protected AttributeExpressionTransformer(GipsTransformationData data, T context, TransformerFactory factory) {
		super(data, context, factory);
	}

	public ValueExpression transform(final GipsAttributeExpr eAttribute) throws Exception {
		if (eAttribute instanceof GipsMappingAttributeExpr eMapping) {
			return transform(eMapping);
		} else if (eAttribute instanceof GipsTypeAttributeExpr eType) {
			return transform(eType);
		} else if (eAttribute instanceof GipsPatternAttributeExpr ePattern) {
			return transform(ePattern);
		} else if (eAttribute instanceof GipsContextExpr eContext) {
			return transform(eContext);
		} else if (eAttribute instanceof GipsLambdaAttributeExpression eLambdaExpression) {
			return transform(eLambdaExpression);
		} else if (eAttribute instanceof GipsLambdaSelfExpression eLambdaSelf) {
			return transform(eLambdaSelf);
		} else {
			throw new UnsupportedOperationException("Unkown value expression: " + eAttribute);
		}
	}

	protected abstract ValueExpression transform(final GipsMappingAttributeExpr eMapping) throws Exception;

	protected ValueExpression transform(final GipsTypeAttributeExpr eType) throws Exception {
		GipsStreamExpr terminalExpr = GipsTransformationUtils.getTerminalStreamExpression(eType.getExpr());
		if (terminalExpr instanceof GipsStreamBoolExpr streamBool) {
			switch (streamBool.getOperator()) {
			case COUNT -> {
				SumExpressionTransformer transformer = transformerFactory.createSumTransformer(context);
				return transformer.transform(eType);
			}
			case NOT_EMPTY -> {
				throw new IllegalArgumentException(
						"Some constrains contain invalid values within arithmetic expressions, e.g., boolean values instead of arithmetic values.");
			}
			default -> {
				throw new UnsupportedOperationException("Unknown stream operator: " + streamBool.getOperator());
			}
			}
		} else if (terminalExpr instanceof GipsStreamArithmetic streamArithmetic) {
			SumExpressionTransformer transformer = transformerFactory.createSumTransformer(context);
			return transformer.transform(eType, streamArithmetic);
		} else {
			throw new UnsupportedOperationException(
					"Some constrains contain invalid values within arithmetic expressions, e.g., objects or streams of objects instead of arithmetic values.");
		}
	}

	protected ValueExpression transform(final GipsPatternAttributeExpr ePattern) throws Exception {
		GipsStreamExpr terminalExpr = GipsTransformationUtils.getTerminalStreamExpression(ePattern.getExpr());
		if (terminalExpr instanceof GipsStreamBoolExpr streamBool) {
			switch (streamBool.getOperator()) {
			case COUNT -> {
				SumExpressionTransformer transformer = transformerFactory.createSumTransformer(context);
				return transformer.transform(ePattern);
			}
			case NOT_EMPTY -> {
				throw new IllegalArgumentException(
						"Some constrains contain invalid values within arithmetic expressions, e.g., boolean values instead of arithmetic values.");
			}
			default -> {
				throw new UnsupportedOperationException("Unknown stream operator: " + streamBool.getOperator());
			}
			}
		} else if (terminalExpr instanceof GipsStreamArithmetic streamArithmetic) {
			SumExpressionTransformer transformer = transformerFactory.createSumTransformer(context);
			return transformer.transform(ePattern, streamArithmetic);
		} else {
			throw new UnsupportedOperationException(
					"Some constrains contain invalid values within arithmetic expressions, e.g., objects or streams of objects instead of arithmetic values.");
		}
	}

	protected ValueExpression transform(final GipsContextExpr eContext) throws Exception {
		EObject contextType = GipslScopeContextUtil.getContextType(eContext);
		if (eContext.getExpr() == null && eContext.getStream() == null) {
			return transformNoExprAndNoStream(eContext, contextType);
		} else if (eContext.getExpr() != null && eContext.getStream() == null) {
			return transformExprAndNoStream(eContext, contextType);
		} else {
			return transformExprAndStream(eContext, contextType);
		}
	}

	protected ValueExpression transform(final GipsLambdaAttributeExpression eLambda) throws Exception {
		GipsAttributeExpr streamRoot = GipslScopeContextUtil.getStreamRootContainer(eLambda);
		GipsStreamExpr streamIteratorContainer = GipslScopeContextUtil.getStreamIteratorNavigationRoot(eLambda);

		if (streamRoot == null) {
			throw new UnsupportedOperationException("Unknown stream lhs-operand.");
		}

		if (streamRoot instanceof GipsLambdaAttributeExpression) {
			throw new UnsupportedOperationException("Nested stream operations not yet supported.");
		}

		if (eLambda.getExpr() instanceof GipsNodeAttributeExpr eNodeAttribute) {
			if (streamRoot instanceof GipsMappingAttributeExpr eMappingAttribute) {
				if (eNodeAttribute.getExpr() == null) {
					return transformIteratorMappingNodeValue(eNodeAttribute, eMappingAttribute.getMapping(),
							streamIteratorContainer);
				} else {
					return transformIteratorMappingNodeFeatureValue(eNodeAttribute, eMappingAttribute.getMapping(),
							eNodeAttribute.getExpr(), streamIteratorContainer);
				}
			} else if (streamRoot instanceof GipsPatternAttributeExpr ePatternAttribute) {
				if (eNodeAttribute.getExpr() == null) {
					return transformIteratorPatternNodeValue(eNodeAttribute, ePatternAttribute.getPattern(),
							streamIteratorContainer);
				} else {
					return transformIteratorPatternNodeFeatureValue(eNodeAttribute, ePatternAttribute.getPattern(),
							eNodeAttribute.getExpr(), streamIteratorContainer);
				}
			} else if (streamRoot instanceof GipsTypeAttributeExpr eTypeAttribute) {
				return transformIteratorTypeValue(streamIteratorContainer, eTypeAttribute.getType());
			} else if (streamRoot instanceof GipsContextExpr eContextExpr) {
				// CASE: streamRoot is an instance of GipsContextExpression
				EObject contextType = GipslScopeContextUtil.getContextType(eContextExpr);
				if (contextType instanceof GipsPatternContext eMatchContext) {
					if (eNodeAttribute.getExpr() == null) {
						return transformIteratorPatternNodeValue(eNodeAttribute, eMatchContext,
								streamIteratorContainer);
					} else {
						return transformIteratorPatternNodeFeatureValue(eNodeAttribute, eMatchContext,
								eNodeAttribute.getExpr(), streamIteratorContainer);
					}
				} else if (contextType instanceof GipsMappingContext eMappingContext) {
					if (eNodeAttribute.getExpr() == null) {
						return transformIteratorMappingNodeValue(eNodeAttribute, eMappingContext.getMapping(),
								streamIteratorContainer);
					} else {
						return transformIteratorMappingNodeFeatureValue(eNodeAttribute, eMappingContext.getMapping(),
								eNodeAttribute.getExpr(), streamIteratorContainer);
					}
				} else {
					// Case: The root expression is a context expression, i.e., .self, that is
					// invoked upon mapping or type contexts.
					// Either way, this case makes it impossible to iterate over a set of matches
					// and, hence, prohibits the access of match nodes.
					throw new UnsupportedOperationException(
							"Match node access operations are not defined on model objects.");
				}
			} else {
				throw new UnsupportedOperationException("Nested stream expressions are not yet allowed!");
			}
		} else if (eLambda.getExpr() instanceof GipsVariableOperationExpression eContextOp) {
			if (streamRoot instanceof GipsMappingAttributeExpr eMappingAttribute) {
				return transformVariableStreamOperation(eContextOp, eMappingAttribute, streamIteratorContainer);
			} else {
				// Case: The root expression is a context expression, i.e., .self, that is
				// invoked upon mapping or type contexts.
				// Either way, this case makes it impossible to iterate over a set of variables
				// and, hence, prohibits access to ILP variable values.
				throw new UnsupportedOperationException(
						"ILP variable value access operations are not defined on model objects.");
			}
		} else {
			// Case: Access the object represented by the iterator or its (nested)
			// attributes.
			GipsFeatureExpr eFeature = (GipsFeatureExpr) eLambda.getExpr();
			if (streamRoot instanceof GipsContextExpr eContext) {
				EObject contextType = GipslScopeContextUtil.getContextType(eContext);
				if (contextType instanceof GipsMappingContext eMappingContext
						&& eContext.getExpr() instanceof GipsNodeAttributeExpr eNodeExpr
						&& eNodeExpr.getExpr() != null) {
					return transformIteratorMappingFeatureValue(streamIteratorContainer, eMappingContext, eNodeExpr,
							eFeature);
				} else if (contextType instanceof GipsPatternContext ePatternContext
						&& eContext.getExpr() instanceof GipsNodeAttributeExpr eNodeExpr
						&& eNodeExpr.getExpr() != null) {
					return transformIteratorPatternFeatureValue(streamIteratorContainer, ePatternContext, eNodeExpr,
							eFeature);
				} else if (contextType instanceof GipsTypeContext eTypeContext
						&& eContext.getExpr() instanceof GipsFeatureExpr eRootFeature) {
					return transformIteratorTypeFeatureValue(streamIteratorContainer, eTypeContext, eFeature);
				} else {
					throw new UnsupportedOperationException(
							"Unsuited context expression type for iterator feature access.");
				}
			} else if (streamRoot instanceof GipsTypeAttributeExpr typeExpr) {
				return transformIteratorTypeFeatureValue(streamIteratorContainer, typeExpr.getType(), eFeature);
			} else {
				// Case: The root expression is a mapping expression, i.e., mappings.
				// Either way, this case makes it impossible to iterate over a model elements
				// without accessing nodes first.
				throw new UnsupportedOperationException(
						"Matches / ILP variables do not have directly accessible features.");
			}
		}
	}

	protected ValueExpression transform(final GipsLambdaSelfExpression eLambda) throws Exception {
		GipsAttributeExpr streamRoot = GipslScopeContextUtil.getStreamRootContainer(eLambda);
		GipsStreamExpr streamIteratorContainer = GipslScopeContextUtil.getStreamIteratorNavigationRoot(eLambda);

		if (streamRoot == null) {
			throw new UnsupportedOperationException("Unknown stream lhs-operand.");
		}

		if (streamRoot instanceof GipsLambdaAttributeExpression) {
			throw new UnsupportedOperationException("Nested stream operations not yet supported.");
		}

		if (streamRoot instanceof GipsMappingAttributeExpr eMappingAttribute) {
			return transformIteratorMappingValue(eMappingAttribute.getMapping(), streamIteratorContainer);
		} else if (streamRoot instanceof GipsPatternAttributeExpr ePatternAttribute) {
			return transformIteratorPatternValue(ePatternAttribute.getPattern(), streamIteratorContainer);
		} else if (streamRoot instanceof GipsTypeAttributeExpr eTypeAttribute) {
			return transformIteratorTypeValue(streamIteratorContainer, eTypeAttribute.getType());
		} else if (streamRoot instanceof GipsContextExpr eContext) {
			// TODO: This is probably not correct -> Fix me! Semantically we want to
			// iterate over some collection that is described by a feature. But
			// here we iterate over something and access a feature. This is the wrong way
			// around!
			EObject contextType = GipslScopeContextUtil.getContextType(eContext);
			if (contextType instanceof GipsMappingContext eMappingContext
					&& eContext.getExpr() instanceof GipsNodeAttributeExpr eNodeExpr && eNodeExpr.getExpr() != null) {
				return transformIteratorMappingValue(eMappingContext.getMapping(), streamIteratorContainer);
			} else if (contextType instanceof GipsPatternContext ePatternContext
					&& eContext.getExpr() instanceof GipsNodeAttributeExpr eNodeExpr && eNodeExpr.getExpr() != null) {
				return transformIteratorPatternValue(ePatternContext.getPattern(), streamIteratorContainer);
			} else if (contextType instanceof GipsTypeContext eTypeContext
					&& eContext.getExpr() instanceof GipsFeatureExpr eRootFeature) {
				return transformIteratorTypeValue(streamIteratorContainer, (EClass) eTypeContext.getType());
			} else {
				throw new UnsupportedOperationException(
						"Unsuited context expression type for iterator feature access.");
			}
		} else {
			throw new UnsupportedOperationException("Nested stream expressions are not yet allowed!");
		}
	}

	protected abstract ValueExpression transformNoExprAndNoStream(final GipsContextExpr eContext,
			final EObject contextType) throws Exception;

	protected ValueExpression transformExprAndNoStream(final GipsContextExpr eContext, final EObject contextType)
			throws Exception {
		if (contextType instanceof GipsTypeContext typeContext) {
			if (eContext.getExpr() instanceof GipsFeatureExpr eFeature) {
				Type tc = data.getType((EClass) typeContext.getType());
				ContextTypeFeatureValue featureValue = factory.createContextTypeFeatureValue();
				featureValue.setTypeContext(tc);
				featureValue.setReturnType(tc.getType());
				featureValue.setFeatureExpression(GipsTransformationUtils.transformFeatureExpression(eFeature));
				return featureValue;
			} else {
				throw new UnsupportedOperationException(
						"Node and ILP variable (e.g., .value(), .isMapped()) expressions are not applicable to model objects.");
			}
		} else if (contextType instanceof GipsPatternContext matchContext) {
			if (eContext.getExpr() instanceof GipsNodeAttributeExpr eNodeExpr) {
				Pattern pc = data.getPattern(matchContext.getPattern());
				if (eNodeExpr.getExpr() == null) {
					ContextPatternNode nodeValue = factory.createContextPatternNode();
					nodeValue.setPatternContext(pc);
					nodeValue.setNode(data.eNode2Node().get(eNodeExpr.getNode()));
					nodeValue.setReturnType(nodeValue.getNode().getType());
					return nodeValue;
				} else {
					ContextPatternNodeFeatureValue featureValue = factory.createContextPatternNodeFeatureValue();
					featureValue.setPatternContext(pc);
					featureValue.setNode(data.eNode2Node().get(eNodeExpr.getNode()));
					featureValue.setFeatureExpression(
							GipsTransformationUtils.transformFeatureExpression(eNodeExpr.getExpr()));
					return featureValue;
				}
			} else {
				throw new UnsupportedOperationException(
						"Node and ILP variable (e.g., .value(), .isMapped()) expressions are not applicable to model objects.");
			}
		} else {
			GipsMappingContext mc = (GipsMappingContext) contextType;
			if (eContext.getExpr() instanceof GipsNodeAttributeExpr eNodeExpr) {
				if (eNodeExpr.getExpr() == null) {
					ContextMappingNode nodeValue = factory.createContextMappingNode();
					nodeValue.setMappingContext(data.eMapping2Mapping().get(mc.getMapping()));
					nodeValue.setNode(data.eNode2Node().get(eNodeExpr.getNode()));
					nodeValue.setReturnType(nodeValue.getNode().getType());
					return nodeValue;
				} else {
					ContextMappingNodeFeatureValue featureValue = factory.createContextMappingNodeFeatureValue();
					featureValue.setMappingContext(data.eMapping2Mapping().get(mc.getMapping()));
					featureValue.setNode(data.eNode2Node().get(eNodeExpr.getNode()));
					featureValue.setReturnType(featureValue.getNode().getType());
					featureValue.setFeatureExpression(
							GipsTransformationUtils.transformFeatureExpression(eNodeExpr.getExpr()));
					return featureValue;
				}
			} else if (eContext.getExpr() instanceof GipsMappingCheckValue mappingCountOp) {
				throw new UnsupportedOperationException(
						"Operation isMapped() is not allowed within nested (stream) expressions.");
			} else if (eContext.getExpr() instanceof GipsMappingValue mappingValOp) {
				ContextMappingValue value = factory.createContextMappingValue();
				value.setMappingContext(data.eMapping2Mapping().get(mc.getMapping()));
				return value;
			} else {
				throw new UnsupportedOperationException(
						"Feature expressions can not be invoked directly upon mapping variables.");
			}
		}
	}

	protected ValueExpression transformExprAndStream(final GipsContextExpr eContext, final EObject contextType)
			throws Exception {
		if (contextType instanceof GipsTypeContext typeContext) {
			if (eContext.getExpr() instanceof GipsFeatureExpr eFeature) {
				Type tc = data.getType((EClass) typeContext.getType());
				FeatureExpression fe = GipsTransformationUtils.transformFeatureExpression(eFeature);
				GipsStreamExpr terminalExpr = GipsTransformationUtils.getTerminalStreamExpression(eContext.getStream());
				if (terminalExpr instanceof GipsStreamBoolExpr streamBool) {
					switch (streamBool.getOperator()) {
					case COUNT -> {
						SumExpressionTransformer transformer = transformerFactory.createSumTransformer(context);
						return transformer.transform(tc, fe, eContext.getStream());
					}
					case NOT_EMPTY -> {
						throw new IllegalArgumentException(
								"Some constrains contain invalid values within arithmetic expressions, e.g., boolean values instead of arithmetic values.");
					}
					default -> {
						throw new UnsupportedOperationException("Unknown stream operator: " + streamBool.getOperator());
					}
					}
				} else if (terminalExpr instanceof GipsStreamArithmetic streamArithmetic) {
					SumExpressionTransformer transformer = transformerFactory.createSumTransformer(context);
					return transformer.transform(tc, fe, eContext.getStream(), streamArithmetic);
				} else if (terminalExpr instanceof GipsContains streamContains) {
					throw new IllegalArgumentException(
							"Some constrains contain invalid values within arithmetic expressions, e.g., boolean values instead of arithmetic values.");
				} else {
					throw new UnsupportedOperationException(
							"Some constrains contain invalid values within arithmetic expressions, e.g., objects or streams of objects instead of arithmetic values.");
				}
			} else {
				throw new UnsupportedOperationException(
						"Node and ILP variable (e.g., .value(), .isMapped()) expressions are not applicable to model objects.");
			}
		} else if (contextType instanceof GipsPatternContext matchContext) {
			if (eContext.getExpr() instanceof GipsNodeAttributeExpr eNodeExpr) {
				Pattern pc = data.getPattern(matchContext.getPattern());
				if (eNodeExpr.getExpr() == null) {
					throw new UnsupportedOperationException(
							"Filter expressions are not applicable to non-container objects.");
				} else {
					FeatureExpression fe = GipsTransformationUtils.transformFeatureExpression(eNodeExpr.getExpr());
					GipsStreamExpr terminalExpr = GipsTransformationUtils
							.getTerminalStreamExpression(eContext.getStream());
					if (terminalExpr instanceof GipsStreamBoolExpr streamBool) {
						switch (streamBool.getOperator()) {
						case COUNT -> {
							SumExpressionTransformer transformer = transformerFactory.createSumTransformer(context);
							return transformer.transform(pc, data.eNode2Node().get(eNodeExpr.getNode()), fe,
									eContext.getStream());
						}
						case NOT_EMPTY -> {
							throw new IllegalArgumentException(
									"Some constrains contain invalid values within arithmetic expressions, e.g., boolean values instead of arithmetic values.");
						}
						default -> {
							throw new UnsupportedOperationException(
									"Unknown stream operator: " + streamBool.getOperator());
						}
						}
					} else if (terminalExpr instanceof GipsStreamArithmetic streamArithmetic) {
						SumExpressionTransformer transformer = transformerFactory.createSumTransformer(context);
						return transformer.transform(pc, data.eNode2Node().get(eNodeExpr.getNode()), fe,
								eContext.getStream(), streamArithmetic);
					} else if (terminalExpr instanceof GipsContains streamContains) {
						throw new IllegalArgumentException(
								"Some constrains contain invalid values within arithmetic expressions, e.g., boolean values instead of arithmetic values.");
					} else {
						throw new UnsupportedOperationException(
								"Some constrains contain invalid values within arithmetic expressions, e.g., objects or streams of objects instead of arithmetic values.");
					}
				}
			} else {
				throw new UnsupportedOperationException(
						"Node and ILP variable (e.g., .value(), .isMapped()) expressions are not applicable to model objects.");
			}
		} else {
			GipsMappingContext mc = (GipsMappingContext) contextType;
			if (eContext.getExpr() instanceof GipsNodeAttributeExpr eNodeExpr) {
				if (eNodeExpr.getExpr() == null) {
					throw new UnsupportedOperationException(
							"Filter expressions are not applicable to non-container objects.");
				} else {
					Mapping mapping = data.eMapping2Mapping().get(mc.getMapping());
					FeatureExpression fe = GipsTransformationUtils.transformFeatureExpression(eNodeExpr.getExpr());
					GipsStreamExpr terminalExpr = GipsTransformationUtils
							.getTerminalStreamExpression(eContext.getStream());
					if (terminalExpr instanceof GipsStreamBoolExpr streamBool) {
						switch (streamBool.getOperator()) {
						case COUNT -> {
							SumExpressionTransformer transformer = transformerFactory.createSumTransformer(context);
							return transformer.transform(mapping, data.eNode2Node().get(eNodeExpr.getNode()), fe,
									eContext.getStream());
						}
						case NOT_EMPTY -> {
							throw new IllegalArgumentException(
									"Some constrains contain invalid values within arithmetic expressions, e.g., boolean values instead of arithmetic values.");
						}
						default -> {
							throw new UnsupportedOperationException(
									"Unknown stream operator: " + streamBool.getOperator());
						}
						}
					} else if (terminalExpr instanceof GipsStreamArithmetic streamArithmetic) {
						SumExpressionTransformer transformer = transformerFactory.createSumTransformer(context);
						return transformer.transform(mapping, data.eNode2Node().get(eNodeExpr.getNode()), fe,
								eContext.getStream(), streamArithmetic);
					} else if (terminalExpr instanceof GipsContains streamContains) {
						throw new IllegalArgumentException(
								"Some constrains contain invalid values within arithmetic expressions, e.g., boolean values instead of arithmetic values.");
					} else {
						throw new UnsupportedOperationException(
								"Some constrains contain invalid values within arithmetic expressions, e.g., objects or streams of objects instead of arithmetic values.");
					}
				}
			} else if (eContext.getExpr() instanceof GipsVariableOperationExpression eContextOp) {
				throw new UnsupportedOperationException(
						"Node and ILP variable (e.g., .value(), .isMapped()) expressions may not followed by stream expressions.");
			} else {
				throw new UnsupportedOperationException(
						"Feature expressions can not be invoked directly upon mapping variables.");
			}
		}
	}

	protected ValueExpression transformIteratorMappingValue(GipsMapping eMapping,
			final GipsStreamExpr streamIteratorContainer) throws Exception {
		IteratorMappingValue mappingValue = factory.createIteratorMappingValue();
		mappingValue.setMappingContext(data.eMapping2Mapping().get(eMapping));
		mappingValue.setStream(data.eStream2SetOp().get(streamIteratorContainer));
		return mappingValue;
	}

	protected ValueExpression transformIteratorMappingNodeValue(final GipsNodeAttributeExpr eNodeAttribute,
			GipsMapping eMapping, final GipsStreamExpr streamIteratorContainer) throws Exception {
		IteratorMappingNodeValue mappingNode = factory.createIteratorMappingNodeValue();
		mappingNode.setNode(data.eNode2Node().get(eNodeAttribute.getNode()));
		mappingNode.setReturnType(mappingNode.getNode().getType());
		mappingNode.setMappingContext(data.eMapping2Mapping().get(eMapping));
		mappingNode.setStream(data.eStream2SetOp().get(streamIteratorContainer));
		return mappingNode;
	}

	protected ValueExpression transformIteratorMappingNodeFeatureValue(final GipsNodeAttributeExpr eNodeAttribute,
			GipsMapping eMapping, GipsFeatureExpr featureExpr, final GipsStreamExpr streamIteratorContainer)
			throws Exception {
		IteratorMappingNodeFeatureValue mappingFeature = factory.createIteratorMappingNodeFeatureValue();
		mappingFeature.setNode(data.eNode2Node().get(eNodeAttribute.getNode()));
		mappingFeature.setReturnType(mappingFeature.getNode().getType());
		mappingFeature.setMappingContext(data.eMapping2Mapping().get(eMapping));
		mappingFeature.setStream(data.eStream2SetOp().get(streamIteratorContainer));
		mappingFeature
				.setFeatureExpression(GipsTransformationUtils.transformFeatureExpression(eNodeAttribute.getExpr()));
		return mappingFeature;
	}

	protected ValueExpression transformIteratorPatternValue(SlimRule eMatchContext,
			final GipsStreamExpr streamIteratorContainer) throws Exception {
		IteratorPatternValue patternValue = factory.createIteratorPatternValue();
		patternValue.setPatternContext(data.ePattern2Pattern().get(eMatchContext));
		patternValue.setStream(data.eStream2SetOp().get(streamIteratorContainer));
		return patternValue;
	}

	protected ValueExpression transformIteratorPatternNodeValue(final GipsNodeAttributeExpr eNodeAttribute,
			GipsPatternContext eMatchContext, final GipsStreamExpr streamIteratorContainer) throws Exception {
		IteratorPatternNodeValue patternNode = factory.createIteratorPatternNodeValue();
		patternNode.setNode(data.eNode2Node().get(eNodeAttribute.getNode()));
		patternNode.setReturnType(patternNode.getNode().getType());
		patternNode.setPatternContext(data.ePattern2Pattern().get(eMatchContext.getPattern()));
		patternNode.setStream(data.eStream2SetOp().get(streamIteratorContainer));
		return patternNode;
	}

	protected ValueExpression transformIteratorPatternNodeValue(final GipsNodeAttributeExpr eNodeAttribute,
			final SlimRule pattern, final GipsStreamExpr streamIteratorContainer) throws Exception {
		IteratorPatternNodeValue patternNode = factory.createIteratorPatternNodeValue();
		patternNode.setNode(data.eNode2Node().get(eNodeAttribute.getNode()));
		patternNode.setReturnType(patternNode.getNode().getType());
		patternNode.setPatternContext(data.ePattern2Pattern().get(pattern));
		patternNode.setStream(data.eStream2SetOp().get(streamIteratorContainer));
		return patternNode;
	}

	protected ValueExpression transformIteratorPatternNodeFeatureValue(final GipsNodeAttributeExpr eNodeAttribute,
			final GipsPatternContext eMatchContext, final GipsFeatureExpr featureExpr,
			final GipsStreamExpr streamIteratorContainer) throws Exception {
		IteratorPatternNodeFeatureValue patternFeature = factory.createIteratorPatternNodeFeatureValue();
		patternFeature.setNode(data.eNode2Node().get(eNodeAttribute.getNode()));
		patternFeature.setPatternContext(data.ePattern2Pattern().get(eMatchContext.getPattern()));
		patternFeature.setStream(data.eStream2SetOp().get(streamIteratorContainer));
		patternFeature
				.setFeatureExpression(GipsTransformationUtils.transformFeatureExpression(eNodeAttribute.getExpr()));
		return patternFeature;
	}

	protected ValueExpression transformIteratorPatternNodeFeatureValue(final GipsNodeAttributeExpr eNodeAttribute,
			final SlimRule pattern, final GipsFeatureExpr featureExpr, final GipsStreamExpr streamIteratorContainer)
			throws Exception {
		IteratorPatternNodeFeatureValue patternFeature = factory.createIteratorPatternNodeFeatureValue();
		patternFeature.setNode(data.eNode2Node().get(eNodeAttribute.getNode()));
		patternFeature.setPatternContext(data.ePattern2Pattern().get(pattern));
		patternFeature.setStream(data.eStream2SetOp().get(streamIteratorContainer));
		patternFeature
				.setFeatureExpression(GipsTransformationUtils.transformFeatureExpression(eNodeAttribute.getExpr()));
		return patternFeature;
	}

	protected ValueExpression transformVariableStreamOperation(final GipsVariableOperationExpression eContextOp,
			final GipsMappingAttributeExpr eMappingAttribute, final GipsStreamExpr streamIteratorContainer)
			throws Exception {
		if (eContextOp instanceof GipsMappingValue mappingValueOp) {
			// TODO:
			// On a serious note: Accessing ILP variable values should not be allowed in
			// filter stream expressions since it is impractical.
			IteratorMappingVariableValue mappingValue = factory.createIteratorMappingVariableValue();
			mappingValue.setMappingContext(data.eMapping2Mapping().get(eMappingAttribute.getMapping()));
			mappingValue.setStream(data.eStream2SetOp().get(streamIteratorContainer));
			mappingValue.setReturnType(EcorePackage.Literals.EINT);
			return mappingValue;
		} else if (eContextOp instanceof GipsMappingCheckValue mappingCountOp) {
			throw new UnsupportedOperationException(
					"Operation isMapped() is not allowed within nested (stream) expressions.");
		} else {
			throw new UnsupportedOperationException("Unkown operation: " + eContextOp.eClass());
		}
	}

	protected ValueExpression transformIteratorMappingFeatureValue(final GipsStreamExpr streamIteratorContainer,
			final GipsMappingContext eMappingContext, final GipsNodeAttributeExpr eNodeExpr,
			final GipsFeatureExpr eFeature) throws Exception {
		IteratorMappingFeatureValue mappingFeatureValue = factory.createIteratorMappingFeatureValue();
		mappingFeatureValue.setStream(data.eStream2SetOp().get(streamIteratorContainer));
		mappingFeatureValue.setMappingContext(data.eMapping2Mapping().get(eMappingContext.getMapping()));
		GipsFeatureLit rootFeatureType = GipslScopeContextUtil.findLeafExpression(eNodeExpr.getExpr());
		mappingFeatureValue.setReturnType(rootFeatureType.getFeature().getEType());
		mappingFeatureValue.setFeatureExpression(GipsTransformationUtils.transformFeatureExpression(eFeature));
		return mappingFeatureValue;
	}

	protected ValueExpression transformIteratorPatternFeatureValue(final GipsStreamExpr streamIteratorContainer,
			final GipsPatternContext eMatchContext, final GipsNodeAttributeExpr eNodeExpr,
			final GipsFeatureExpr eFeature) throws Exception {
		IteratorPatternFeatureValue patternFeatureValue = factory.createIteratorPatternFeatureValue();
		patternFeatureValue.setStream(data.eStream2SetOp().get(streamIteratorContainer));
		patternFeatureValue.setPatternContext(data.ePattern2Pattern().get(eMatchContext.getPattern()));
		GipsFeatureLit rootFeatureType = GipslScopeContextUtil.findLeafExpression(eNodeExpr.getExpr());
		patternFeatureValue.setReturnType(rootFeatureType.getFeature().getEType());
		patternFeatureValue.setFeatureExpression(GipsTransformationUtils.transformFeatureExpression(eFeature));
		return patternFeatureValue;
	}

	protected ValueExpression transformIteratorTypeValue(final GipsStreamExpr streamIteratorContainer,
			final EClass eTypeContext) throws Exception {
		IteratorTypeValue typeValue = factory.createIteratorTypeValue();
		typeValue.setTypeContext(data.getType(eTypeContext));
		typeValue.setReturnType(eTypeContext);
		typeValue.setStream(data.eStream2SetOp().get(streamIteratorContainer));
		return typeValue;
	}

	protected ValueExpression transformIteratorTypeFeatureValue(final GipsStreamExpr streamIteratorContainer,
			final EClass eTypeContext, final GipsFeatureExpr eFeature) throws Exception {
		IteratorTypeFeatureValue typeFeatureValue = factory.createIteratorTypeFeatureValue();
		typeFeatureValue.setTypeContext(data.getType(eTypeContext));
		GipsFeatureLit rootFeatureType = GipslScopeContextUtil.findLeafExpression(eFeature);
		typeFeatureValue.setReturnType(rootFeatureType.getFeature().getEType());
		typeFeatureValue.setStream(data.eStream2SetOp().get(streamIteratorContainer));
		typeFeatureValue.setFeatureExpression(GipsTransformationUtils.transformFeatureExpression(eFeature));
		return typeFeatureValue;
	}

	protected ValueExpression transformIteratorTypeFeatureValue(final GipsStreamExpr streamIteratorContainer,
			final GipsTypeContext eTypeContext, final GipsFeatureExpr eFeature) throws Exception {
		IteratorTypeFeatureValue typeFeatureValue = factory.createIteratorTypeFeatureValue();
		typeFeatureValue.setTypeContext(data.getType((EClass) eTypeContext.getType()));
		GipsFeatureLit rootFeatureType = GipslScopeContextUtil.findLeafExpression(eFeature);
		typeFeatureValue.setReturnType(rootFeatureType.getFeature().getEType());
		typeFeatureValue.setStream(data.eStream2SetOp().get(streamIteratorContainer));
		typeFeatureValue.setFeatureExpression(GipsTransformationUtils.transformFeatureExpression(eFeature));
		return typeFeatureValue;
	}
}
