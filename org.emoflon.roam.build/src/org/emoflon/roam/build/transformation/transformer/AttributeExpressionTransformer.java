package org.emoflon.roam.build.transformation.transformer;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.roam.build.transformation.helper.RoamTransformationData;
import org.emoflon.roam.build.transformation.helper.RoamTransformationUtils;
import org.emoflon.roam.build.transformation.helper.TransformationContext;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNode;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNodeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextPatternNode;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextPatternNodeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorPatternFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorPatternNodeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorPatternNodeValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorTypeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.Pattern;
import org.emoflon.roam.intermediate.RoamIntermediate.Type;
import org.emoflon.roam.intermediate.RoamIntermediate.ValueExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamContextExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamContextOperationExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamFeatureExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamFeatureLit;
import org.emoflon.roam.roamslang.roamSLang.RoamLambdaAttributeExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamMapping;
import org.emoflon.roam.roamslang.roamSLang.RoamMappingAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamMappingContext;
import org.emoflon.roam.roamslang.roamSLang.RoamMatchContext;
import org.emoflon.roam.roamslang.roamSLang.RoamNodeAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamTypeContext;
import org.emoflon.roam.roamslang.scoping.RoamSLangScopeContextUtil;

public abstract class AttributeExpressionTransformer<T extends EObject> extends TransformationContext<T> {
	protected AttributeExpressionTransformer(RoamTransformationData data, T context, TransformerFactory factory) {
		super(data, context, factory);
	}

	public ValueExpression transform(final RoamAttributeExpr eAttribute) throws Exception {
		if(eAttribute instanceof RoamMappingAttributeExpr eMapping) {
			return transform(eMapping);
		} else if(eAttribute instanceof RoamContextExpr eContext) {
			return transform(eContext);
		} else {
			return transform((RoamLambdaAttributeExpression)eAttribute);
		}
	}
	
	protected abstract ValueExpression transform(final RoamMappingAttributeExpr eMapping) throws Exception;
	
	protected ValueExpression transform(final RoamContextExpr eContext) throws Exception {
		EObject contextType = RoamSLangScopeContextUtil.getContextType(eContext);
		if(eContext.getExpr() == null && eContext.getStream() == null) {
			return transformNoExprAndNoStream(eContext, contextType);
		} else if(eContext.getExpr() != null && eContext.getStream() == null) {
			return transformExprAndNoStream(eContext, contextType);
		} else {
			return transformExprAndStream(eContext, contextType);
		}
	}
	
	protected ValueExpression transform(final RoamLambdaAttributeExpression eLambda) throws Exception {
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
					return transformIteratorMappingNodeValue(eNodeAttribute, eMappingAttribute.getMapping(), streamIteratorContainer);
				} else {
					return transformIteratorMappingNodeFeatureValue(eNodeAttribute, eMappingAttribute.getMapping(), eNodeAttribute.getExpr(), streamIteratorContainer);
				}
			} else if(streamRoot instanceof RoamContextExpr eContextExpr){
				// CASE: streamRoot is an instance of RoamContextExpression 
				EObject contextType = RoamSLangScopeContextUtil.getContextType(eContextExpr);
				if(contextType instanceof RoamMatchContext eMatchContext) {
					if(eNodeAttribute.getExpr() == null) {
						return transformIteratorPatternNodeValue(eNodeAttribute, eMatchContext, streamIteratorContainer);
					} else {
						return transformIteratorPatternNodeFeatureValue(eNodeAttribute, eMatchContext, eNodeAttribute.getExpr(), streamIteratorContainer);
					}
				} else if(contextType instanceof RoamMappingContext eMappingContext){
					if(eNodeAttribute.getExpr() == null) {
						return transformIteratorMappingNodeValue(eNodeAttribute, eMappingContext.getMapping(), streamIteratorContainer);
					} else {
						return transformIteratorMappingNodeFeatureValue(eNodeAttribute, eMappingContext.getMapping(), eNodeAttribute.getExpr(), streamIteratorContainer);
					}
				} else {
					//	Case: The root expression is a context expression, i.e., .self, that is invoked upon mapping or type contexts.
					//	Either way, this case makes it impossible to iterate over a set of matches and, hence, prohibits the access of match nodes.
					throw new UnsupportedOperationException("Match node access operations are not defined on model objects.");
				}
			} else {
				throw new UnsupportedOperationException("Nested stream expressions are not yet allowed!");
			}
		} else if(eLambda.getExpr() instanceof RoamContextOperationExpression eContextOp) {
			if(streamRoot instanceof RoamMappingAttributeExpr eMappingAttribute) {
				return transformVariableStreamOperation(eContextOp, eMappingAttribute, streamIteratorContainer);
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
				if(contextType instanceof RoamMappingContext eMappingContext && eContext.getExpr() instanceof RoamNodeAttributeExpr eNodeExpr 
						&& eNodeExpr.getExpr() != null) {
					return transformIteratorMappingFeatureValue(streamIteratorContainer, eMappingContext, eNodeExpr, eFeature);
				} else if(contextType instanceof RoamMatchContext ePatternContext && eContext.getExpr() instanceof RoamNodeAttributeExpr eNodeExpr 
						&& eNodeExpr.getExpr() != null) {
					return transformIteratorPatternFeatureValue(streamIteratorContainer, ePatternContext, eNodeExpr, eFeature);
				} else if(contextType instanceof RoamTypeContext eTypeContext && eContext.getExpr() instanceof RoamFeatureExpr eRootFeature) {
					return transformIteratorTypeFeatureValue(streamIteratorContainer, eTypeContext, eFeature);
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
	
	protected abstract ValueExpression transformNoExprAndNoStream(final RoamContextExpr eContext, final EObject contextType) throws Exception;
	
	protected ValueExpression transformExprAndNoStream(final RoamContextExpr eContext, final EObject contextType) throws Exception {
		if(contextType instanceof RoamTypeContext typeContext) {
			if(eContext.getExpr() instanceof RoamFeatureExpr eFeature) {
				Type tc = data.getType((EClass)typeContext.getType());
				ContextTypeFeatureValue featureValue = factory.createContextTypeFeatureValue();
				featureValue.setTypeContext(tc);
				featureValue.setReturnType(tc.getType());
				featureValue.setFeatureExpression(RoamTransformationUtils.transformFeatureExpression(eFeature));
				return featureValue;
			} else {
				throw new UnsupportedOperationException("Node and ILP variable (e.g., .value(), .isMapped()) expressions are not applicable to model objects.");
			}
		} else if(contextType instanceof RoamMatchContext matchContext) {
			if(eContext.getExpr() instanceof RoamNodeAttributeExpr eNodeExpr) {
				Pattern pc = data.getPattern(matchContext.getPattern());
				if(eNodeExpr.getExpr() == null) {
					ContextPatternNode nodeValue = factory.createContextPatternNode();
					nodeValue.setPatternContext(pc);
					nodeValue.setNode(data.eNode2Node().get(eNodeExpr.getNode()));
					nodeValue.setReturnType(nodeValue.getNode().getType());
					return nodeValue;
				} else {
					ContextPatternNodeFeatureValue featureValue = factory.createContextPatternNodeFeatureValue();
					featureValue.setPatternContext(pc);
					featureValue.setNode(data.eNode2Node().get(eNodeExpr.getNode()));
					featureValue.setFeatureExpression(RoamTransformationUtils.transformFeatureExpression(eNodeExpr.getExpr()));
					return featureValue;
				}
			} else {
				throw new UnsupportedOperationException("Node and ILP variable (e.g., .value(), .isMapped()) expressions are not applicable to model objects.");
			}
		} else {
			RoamMappingContext mc = (RoamMappingContext) contextType;
			if(eContext.getExpr() instanceof RoamNodeAttributeExpr eNodeExpr) {
				if(eNodeExpr.getExpr() == null) {
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
					featureValue.setFeatureExpression(RoamTransformationUtils.transformFeatureExpression(eNodeExpr.getExpr()));
					return featureValue;
				}
			} else if(eContext.getExpr() instanceof RoamContextOperationExpression eContextOp) {
				switch(eContextOp.getOperation()) {
					case MAPPED -> {
						throw new UnsupportedOperationException("Operation isMapped() is not allowed within nested (stream) expressions.");
					}
					case VALUE -> {
						//TODO:
						// On a serious note: Accessing ILP variable values should not be allowed in filter stream expressions since it is impractical.
						ContextMappingValue value = factory.createContextMappingValue();
						value.setMappingContext(data.eMapping2Mapping().get(mc.getMapping()));
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
	}
	
	protected ValueExpression transformExprAndStream(final RoamContextExpr eContext, final EObject contextType) throws Exception {
		//Case: The context expression is followed by some stream expression
		//TODO: This should be allowed in arithemtic Expressions, hence, TODO but on a later date!
		throw new UnsupportedOperationException("Nested stream operations not yet supported.");
	}
	
	protected ValueExpression transformIteratorMappingNodeValue(final RoamNodeAttributeExpr eNodeAttribute, RoamMapping eMapping, 
			final RoamStreamExpr streamIteratorContainer) throws Exception {
		IteratorMappingNodeValue mappingNode = factory.createIteratorMappingNodeValue();
		mappingNode.setNode(data.eNode2Node().get(eNodeAttribute.getNode()));
		mappingNode.setReturnType(mappingNode.getNode().getType());
		mappingNode.setMappingContext(data.eMapping2Mapping().get(eMapping));
		mappingNode.setStream(data.eStream2SetOp().get(streamIteratorContainer));
		return mappingNode;
	}
	
	protected ValueExpression transformIteratorMappingNodeFeatureValue(final RoamNodeAttributeExpr eNodeAttribute, RoamMapping eMapping, 
			RoamFeatureExpr featureExpr, final RoamStreamExpr streamIteratorContainer) throws Exception {
		IteratorMappingNodeFeatureValue mappingFeature = factory.createIteratorMappingNodeFeatureValue();
		mappingFeature.setNode(data.eNode2Node().get(eNodeAttribute.getNode()));
		mappingFeature.setReturnType(mappingFeature.getNode().getType());
		mappingFeature.setMappingContext(data.eMapping2Mapping().get(eMapping));
		mappingFeature.setStream(data.eStream2SetOp().get(streamIteratorContainer));
		mappingFeature.setFeatureExpression(RoamTransformationUtils.transformFeatureExpression(eNodeAttribute.getExpr()));
		return mappingFeature;
	}
	
	protected ValueExpression transformIteratorPatternNodeValue(final RoamNodeAttributeExpr eNodeAttribute, RoamMatchContext eMatchContext, 
			final RoamStreamExpr streamIteratorContainer) throws Exception {
		IteratorPatternNodeValue patternNode = factory.createIteratorPatternNodeValue();
		patternNode.setNode(data.eNode2Node().get(eNodeAttribute.getNode()));
		patternNode.setReturnType(patternNode.getNode().getType());
		patternNode.setPatternContext(data.ePattern2Pattern().get(eMatchContext.getPattern()));
		patternNode.setStream(data.eStream2SetOp().get(streamIteratorContainer));
		return patternNode;
	}
	
	protected ValueExpression transformIteratorPatternNodeFeatureValue(final RoamNodeAttributeExpr eNodeAttribute, RoamMatchContext eMatchContext, 
			RoamFeatureExpr featureExpr, final RoamStreamExpr streamIteratorContainer) throws Exception {
		IteratorPatternNodeFeatureValue patternFeature = factory.createIteratorPatternNodeFeatureValue();
		patternFeature.setNode(data.eNode2Node().get(eNodeAttribute.getNode()));
		patternFeature.setPatternContext(data.ePattern2Pattern().get(eMatchContext.getPattern()));
		patternFeature.setStream(data.eStream2SetOp().get(streamIteratorContainer));
		patternFeature.setFeatureExpression(RoamTransformationUtils.transformFeatureExpression(eNodeAttribute.getExpr()));
		return patternFeature;
	}
	
	protected ValueExpression transformVariableStreamOperation(final RoamContextOperationExpression eContextOp, final RoamMappingAttributeExpr eMappingAttribute, 
			final RoamStreamExpr streamIteratorContainer) throws Exception {
		switch(eContextOp.getOperation()) {
			case MAPPED -> {
				throw new UnsupportedOperationException("Operation isMapped() is not allowed within nested (stream) expressions.");
			}
			case VALUE -> {
				// TODO:
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
	}
	
	protected ValueExpression transformIteratorMappingFeatureValue(final RoamStreamExpr streamIteratorContainer, final RoamMappingContext eMappingContext,
			final RoamNodeAttributeExpr eNodeExpr, final RoamFeatureExpr eFeature) throws Exception {
		IteratorMappingFeatureValue mappingFeatureValue = factory.createIteratorMappingFeatureValue();
		mappingFeatureValue.setStream(data.eStream2SetOp().get(streamIteratorContainer));
		mappingFeatureValue.setMappingContext(data.eMapping2Mapping().get(eMappingContext.getMapping()));
		RoamFeatureLit rootFeatureType = (RoamFeatureLit) RoamSLangScopeContextUtil.findLeafExpression(eNodeExpr.getExpr());
		mappingFeatureValue.setReturnType(rootFeatureType.getFeature().getEType());
		mappingFeatureValue.setFeatureExpression(RoamTransformationUtils.transformFeatureExpression(eFeature));
		return mappingFeatureValue;
	}
	
	protected ValueExpression transformIteratorPatternFeatureValue(final RoamStreamExpr streamIteratorContainer, final RoamMatchContext eMatchContext,
			final RoamNodeAttributeExpr eNodeExpr, final RoamFeatureExpr eFeature) throws Exception {
		IteratorPatternFeatureValue patternFeatureValue = factory.createIteratorPatternFeatureValue();
		patternFeatureValue.setStream(data.eStream2SetOp().get(streamIteratorContainer));
		patternFeatureValue.setPatternContext(data.ePattern2Pattern().get(eMatchContext.getPattern()));
		RoamFeatureLit rootFeatureType = (RoamFeatureLit) RoamSLangScopeContextUtil.findLeafExpression(eNodeExpr.getExpr());
		patternFeatureValue.setReturnType(rootFeatureType.getFeature().getEType());
		patternFeatureValue.setFeatureExpression(RoamTransformationUtils.transformFeatureExpression(eFeature));
		return patternFeatureValue;
	}
	
	protected ValueExpression transformIteratorTypeFeatureValue(final RoamStreamExpr streamIteratorContainer, final RoamTypeContext eTypeContext,
			final RoamFeatureExpr eFeature) throws Exception {
		IteratorTypeFeatureValue typeFeatureValue = factory.createIteratorTypeFeatureValue();
		typeFeatureValue.setTypeContext(data.getType((EClass) eTypeContext.getType()));
		RoamFeatureLit rootFeatureType = (RoamFeatureLit) RoamSLangScopeContextUtil.findLeafExpression(eFeature);
		typeFeatureValue.setReturnType(rootFeatureType.getFeature().getEType());
		typeFeatureValue.setStream(data.eStream2SetOp().get(streamIteratorContainer));
		typeFeatureValue.setFeatureExpression(RoamTransformationUtils.transformFeatureExpression(eFeature));
		return typeFeatureValue;
	}
}
