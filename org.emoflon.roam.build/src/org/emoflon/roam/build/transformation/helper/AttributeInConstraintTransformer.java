package org.emoflon.roam.build.transformation.helper;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.roam.build.transformation.RoamTransformationData;
import org.emoflon.roam.intermediate.RoamIntermediate.Constraint;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNode;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNodeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorTypeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingConstraint;
import org.emoflon.roam.intermediate.RoamIntermediate.TypeConstraint;
import org.emoflon.roam.intermediate.RoamIntermediate.ValueExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamContextExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamContextOperationExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamFeatureExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamFeatureLit;
import org.emoflon.roam.roamslang.roamSLang.RoamLambdaAttributeExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamMappingAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamMappingContext;
import org.emoflon.roam.roamslang.roamSLang.RoamNodeAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamArithmetic;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamBoolExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamTypeContext;
import org.emoflon.roam.roamslang.scoping.RoamSLangScopeContextUtil;

public class AttributeInConstraintTransformer extends TransformationContext<Constraint>
		implements AttributeExpressionTransformer {

	protected AttributeInConstraintTransformer(RoamTransformationData data, Constraint context,
			TransformerFactory factory) {
		super(data, context, factory);
	}

	@Override
	public ValueExpression transform(RoamAttributeExpr eAttribute) throws Exception {
		if(eAttribute instanceof RoamMappingAttributeExpr eMapping) {
			if(eMapping.getExpr() != null) {
				RoamStreamExpr terminalExpr = RoamTransformationUtils.getTerminalStreamExpression(eMapping.getExpr());
				if(terminalExpr instanceof RoamStreamBoolExpr streamBool) {
					switch(streamBool.getOperator()) {
						case COUNT -> {
							SumExpressionTransformer transformer = transformerFactory.createSumTransformer(context);
							return transformer.transform(eMapping);
						}
						case EXISTS -> {
							throw new IllegalArgumentException("Some constrains contain invalid values within arithmetic expressions, e.g., boolean values instead of arithmetic values.");
						}
						case NOTEXISTS -> {
							throw new IllegalArgumentException("Some constrains contain invalid values within arithmetic expressions, e.g., boolean values instead of arithmetic values.");
						}
						default -> {
							throw new UnsupportedOperationException("Unknown stream operator: "+streamBool.getOperator());
						}
					}
				} else if(terminalExpr instanceof RoamStreamArithmetic streamArithmetic){
					SumExpressionTransformer transformer = transformerFactory.createSumTransformer(context);
					return transformer.transform(eMapping, streamArithmetic);
				} else {
					throw new UnsupportedOperationException("Some constrains contain invalid values within arithmetic expressions, e.g., objects or streams of objects instead of arithmetic values.");
				}
			} else  {
				throw new UnsupportedOperationException("Some constrains contain invalid values within arithmetic expressions, e.g., objects or streams of objects instead of arithmetic values.");
			}
		} else if(eAttribute instanceof RoamContextExpr eContext) {
			EObject contextType = RoamSLangScopeContextUtil.getContextType(eContext);
			Constraint constraint = data.eConstraint2Constraint().get(contextType.eContainer());
			if(eContext.getExpr() == null && eContext.getStream() == null) {
				if(contextType instanceof RoamTypeContext typeContext) {
					TypeConstraint tc = (TypeConstraint) constraint;
					ContextTypeValue typeValue = factory.createContextTypeValue();
					typeValue.setReturnType(tc.getModelType().getType());
					typeValue.setTypeContext(tc);
					return typeValue;
				} else {
					throw new UnsupportedOperationException("Using sets of mapping variables as operands in boolean or arithmetic expressions is not allowed.");
				}
			} else if(eContext.getExpr() != null && eContext.getStream() == null) {
				if(contextType instanceof RoamTypeContext typeContext) {
					if(eContext.getExpr() instanceof RoamFeatureExpr eFeature) {
						TypeConstraint tc = (TypeConstraint) constraint;
						ContextTypeFeatureValue featureValue = factory.createContextTypeFeatureValue();
						featureValue.setTypeContext(tc);
						featureValue.setReturnType(tc.getModelType().getType());
						featureValue.setFeatureExpression(RoamTransformationUtils.transformFeatureExpression(eFeature));
						return featureValue;
					} else {
						throw new UnsupportedOperationException("Node and ILP variable (e.g., .value(), .isMapped()) expressions are not applicable to model objects.");
					}
				} else {
					MappingConstraint mc = (MappingConstraint) constraint;
					if(eContext.getExpr() instanceof RoamNodeAttributeExpr eNodeExpr) {
						if(eNodeExpr.getExpr() == null) {
							ContextMappingNode nodeValue = factory.createContextMappingNode();
							nodeValue.setMappingContext(mc);
							nodeValue.setNode(data.eNode2Node().get(eNodeExpr.getNode()));
							nodeValue.setReturnType(nodeValue.getNode().getType());
							return nodeValue;
						} else {
							ContextMappingNodeFeatureValue featureValue = factory.createContextMappingNodeFeatureValue();
							featureValue.setMappingContext(mc);
							featureValue.setNode(data.eNode2Node().get(eNodeExpr.getNode()));
							featureValue.setReturnType(featureValue.getNode().getType());
							featureValue.setFeatureExpression(RoamTransformationUtils.transformFeatureExpression(eNodeExpr.getExpr()));
							return featureValue;
						}
					} else if(eContext.getExpr() instanceof RoamContextOperationExpression eContextOp) {
						switch(eContextOp.getOperation()) {
							case MAPPED -> {
								throw new UnsupportedOperationException("Operation isMapped() is not allowed within arithmetic expressions.");
							}
							case VALUE -> {
								ContextMappingValue value = factory.createContextMappingValue();
								value.setMappingContext(mc);
								return value;
							}
							default -> {
								throw new UnsupportedOperationException("Unkown operation: "+eContextOp.getOperation());
							}
						}
					} else {
						throw new UnsupportedOperationException("Feature expressions can not be invoked directly upon mapping variables.");
					}
				}
			} else {
				//Case: The context expression is followed by some stream expression
				//TODO: This should be allowed in arithemtic Expressions, hence, TODO but on a later date!
				throw new UnsupportedOperationException("Nested stream operations not yet supported.");
			}
		} else {
			RoamLambdaAttributeExpression eLambda = (RoamLambdaAttributeExpression) eAttribute;
			RoamAttributeExpr streamRoot = RoamSLangScopeContextUtil.getStreamRootContainer(eLambda);
			RoamStreamExpr streamIteratorContainer = RoamSLangScopeContextUtil.getStreamIteratorContainer(eLambda);
			
			if(streamRoot == null) {
				throw new UnsupportedOperationException("Unknown stream lhs-operand.");
			}
			if(streamRoot instanceof RoamLambdaAttributeExpression) {
				throw new UnsupportedOperationException("Nested stream operations not yet supported.");
			}
			
			if(eLambda.getExpr() instanceof RoamNodeAttributeExpr eNodeAttribute) {
				if(streamRoot instanceof RoamMappingAttributeExpr eMappingAttribute) {
					if(eNodeAttribute.getExpr() == null) {
						IteratorMappingNodeValue mappingNode = factory.createIteratorMappingNodeValue();
						mappingNode.setNode(data.eNode2Node().get(eNodeAttribute.getNode()));
						mappingNode.setReturnType(mappingNode.getNode().getType());
						mappingNode.setMappingContext(data.eMapping2Mapping().get(eMappingAttribute.getMapping()));
						mappingNode.setStream(data.eStream2SetOp().get(streamIteratorContainer));
						return mappingNode;
					} else {
						IteratorMappingNodeFeatureValue mappingFeature = factory.createIteratorMappingNodeFeatureValue();
						mappingFeature.setNode(data.eNode2Node().get(eNodeAttribute.getNode()));
						mappingFeature.setReturnType(mappingFeature.getNode().getType());
						mappingFeature.setMappingContext(data.eMapping2Mapping().get(eMappingAttribute.getMapping()));
						mappingFeature.setStream(data.eStream2SetOp().get(streamIteratorContainer));
						mappingFeature.setFeatureExpression(RoamTransformationUtils.transformFeatureExpression(eNodeAttribute.getExpr()));
						return mappingFeature;
					}
				} else {
					//	Case: The root expression is a context expression, i.e., .self, that is invoked upon mapping or type contexts.
					//	Either way, this case makes it impossible to iterate over a set of matches and, hence, prohibits the access of match nodes.
					throw new UnsupportedOperationException("Match node access operations are not defined on model objects.");
				}
			} else if(eLambda.getExpr() instanceof RoamContextOperationExpression eContextOp) {
				if(streamRoot instanceof RoamMappingAttributeExpr eMappingAttribute) {
					switch(eContextOp.getOperation()) {
						case MAPPED -> {
							throw new UnsupportedOperationException("Operation isMapped() is not allowed within nested (stream) expressions.");
						}
						case VALUE -> {
							// TODO: Review this feature!
							// On a serious note: Accessing ILP variable values should not be allowed in filter stream expressions since it is impractical.
							IteratorMappingValue mappingValue = factory.createIteratorMappingValue();
							mappingValue.setMappingContext(data.eMapping2Mapping().get(eMappingAttribute.getMapping()));
							mappingValue.setStream(data.eStream2SetOp().get(streamIteratorContainer));
							mappingValue.setReturnType(EcorePackage.Literals.EINT);
							return mappingValue;
						}
						default -> {
							throw new UnsupportedOperationException("Unkown operation: "+eContextOp.getOperation());
						}
					}
				} else {
					//	Case: The root expression is a context expression, i.e., .self, that is invoked upon mapping or type contexts.
					//	Either way, this case makes it impossible to iterate over a set of variables and, hence, prohibits access to ILP variable values.
					throw new UnsupportedOperationException("ILP variable value access operations are not defined on model objects.");
				}
			} else {
				// Case: Access the object represented by the iterator or its (nested) attributes.
				RoamFeatureExpr eFeature = (RoamFeatureExpr) eLambda.getExpr();
				if(streamRoot instanceof RoamContextExpr eContext) {
					EObject contextType = RoamSLangScopeContextUtil.getContextType(eContext);
					if(contextType instanceof RoamMappingContext eMappingContext && eContext.getExpr() instanceof RoamNodeAttributeExpr eNodeExpr && eNodeExpr.getExpr() != null) {
						IteratorMappingFeatureValue mappingFeatureValue = factory.createIteratorMappingFeatureValue();
						mappingFeatureValue.setStream(data.eStream2SetOp().get(streamIteratorContainer));
						mappingFeatureValue.setMappingContext(data.eMapping2Mapping().get(eMappingContext.getMapping()));
						RoamFeatureLit rootFeatureType = (RoamFeatureLit) RoamSLangScopeContextUtil.findLeafExpression(eNodeExpr.getExpr());
						mappingFeatureValue.setReturnType(rootFeatureType.getFeature().getEType());
						mappingFeatureValue.setFeatureExpression(RoamTransformationUtils.transformFeatureExpression(eFeature));
						return mappingFeatureValue;
					} else if(contextType instanceof RoamTypeContext eTypeContext && eContext.getExpr() instanceof RoamFeatureExpr eRootFeature) {
						IteratorTypeFeatureValue typeFeatureValue = factory.createIteratorTypeFeatureValue();
						typeFeatureValue.setTypeContext(data.getType((EClass) eTypeContext.getType()));
						RoamFeatureLit rootFeatureType = (RoamFeatureLit) RoamSLangScopeContextUtil.findLeafExpression(eRootFeature);
						typeFeatureValue.setReturnType(rootFeatureType.getFeature().getEType());
						typeFeatureValue.setStream(data.eStream2SetOp().get(streamIteratorContainer));
						typeFeatureValue.setFeatureExpression(RoamTransformationUtils.transformFeatureExpression(eFeature));
						return typeFeatureValue;
					} else {
						throw new UnsupportedOperationException("Unsuited context expression type for iterator feature access.");
					}
				} else {
					//	Case: The root expression is a mapping expression, i.e., mappings.
					//	Either way, this case makes it impossible to iterate over a model elements without accessing nodes first.
					throw new UnsupportedOperationException("Matches / ILP variables do not have directly accessible features.");
				}
			}
		}
	}

}
