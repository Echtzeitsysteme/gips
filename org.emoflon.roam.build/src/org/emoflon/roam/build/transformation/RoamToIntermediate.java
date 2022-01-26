package org.emoflon.roam.build.transformation;

import java.util.Collection;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.utils.GTEditorPatternUtils;
import org.emoflon.ibex.gt.transformations.EditorToIBeXPatternTransformation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;
import org.emoflon.roam.intermediate.RoamIntermediate.Constraint;
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingConstraint;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateFactory;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel;
import org.emoflon.roam.intermediate.RoamIntermediate.TypeConstraint;
import org.emoflon.roam.roamslang.roamSLang.EditorGTFile;
import org.emoflon.roam.roamslang.roamSLang.RoamAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamBoolExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamBooleanLiteral;
import org.emoflon.roam.roamslang.roamSLang.RoamConstraint;
import org.emoflon.roam.roamslang.roamSLang.RoamContextExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamMappingAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamMappingContext;
import org.emoflon.roam.roamslang.roamSLang.RoamRelExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamBoolExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamNavigation;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamNoArgOperator;
import org.emoflon.roam.roamslang.roamSLang.RoamTypeContext;

public class RoamToIntermediate {
	protected RoamIntermediateFactory factory = RoamIntermediateFactory.eINSTANCE;
	final protected RoamTransformationData data;
	
	public RoamToIntermediate(final EditorGTFile roamSlangFile) {
		data = new RoamTransformationData(factory.createRoamIntermediateModel(), roamSlangFile);
	}
	
	public RoamIntermediateModel transform() throws Exception {
		//transform GT to IBeXPatterns
		EditorToIBeXPatternTransformation ibexTransformer = new EditorToIBeXPatternTransformation();
		data.model().setIbexModel(ibexTransformer.transform(data.roamSlangFile()));
		mapGT2IBeXElements();
		
		//transform Roam components
		transformMappings();
		transformConstraints();
		
		return data.model();
	}
	
	protected void transformMappings() {
		data.roamSlangFile().getMappings().forEach(eMapping -> {
			Mapping mapping = factory.createMapping();
			mapping.setName(eMapping.getName());
			mapping.setRule(data.ePattern2Rule().get(eMapping.getRule()));
			data.model().getVariables().add(mapping);
			data.eMapping2Mapping().put(eMapping, mapping);
		});
	}
	
	protected void transformConstraints() throws Exception {
		RoamConstraintSplitter splitter = new RoamConstraintSplitter(data);
		int constraintCounter = 0;
		for(RoamConstraint eConstraint : data.roamSlangFile().getConstraints()) {
			if(eConstraint.getExpr() == null || eConstraint.getExpr().getExpr() == null) {
				continue;
			}
			Collection<RoamConstraint> eConstraints = splitter.split(eConstraint);
			for(RoamConstraint eSubConstraint : eConstraints) {
				// check primitive or impossible expressions
				RoamBoolExpr boolExpr = eSubConstraint.getExpr().getExpr();
				if(boolExpr instanceof RoamBooleanLiteral lit) {
					if(lit.isLiteral()) {
						// Ignore this constraint, since it will always be satisfied
						continue;
					} else {
						// This constraint will never be satisfied, hence this is an impossible to solve problem
						throw new IllegalArgumentException("Optimization problem is impossible to solve: One ore more constraints return false by definition.");
					}
				}
				
				Constraint constraint = createConstraint(eSubConstraint, constraintCounter);
				data.model().getConstraints().add(constraint);
				
				RoamRelExpr relExpr = (RoamRelExpr) boolExpr;
				// Check whether this is a simple boolean result of some attribute or stream expression, or a true relational expression, which compares two numeric values.
				if(relExpr.getRight() == null) {
					if(relExpr.getLeft() instanceof RoamAttributeExpr eAttributeExpr) {
						if(eAttributeExpr instanceof RoamMappingAttributeExpr eMappingAttribute && eMappingAttribute.getExpr() != null) {
							setConstraintCondition(constraint, eMappingAttribute.getExpr());
						} else if(eAttributeExpr instanceof RoamContextExpr eContextAttribute) {
							//TODO
						} else {
							throw new IllegalArgumentException("Some constrains contain invalid values within boolean expressions, e.g., arithmetic values instead of boolean values.");
						}
					} else {
						throw new IllegalArgumentException("Some constrains contain invalid values within boolean expressions, e.g., arithmetic values instead of boolean values.");
					}
				} else {
					//TODO
				}
			}
		}
	}
	
	protected Constraint createConstraint(final RoamConstraint eConstraint, int counter) {
		if(eConstraint.getContext() instanceof RoamMappingContext mapping) {
			MappingConstraint constraint = factory.createMappingConstraint();
			constraint.setName("MappingConstraint"+counter+"On"+mapping.getMapping().getName());
			constraint.setMapping(data.eMapping2Mapping().get(mapping.getMapping()));
			return constraint;
		} else {
			RoamTypeContext type = (RoamTypeContext) eConstraint.getContext();
			TypeConstraint constraint = factory.createTypeConstraint();
			constraint.setName("TypeConstraint"+counter+"On"+type.getType().getName());
			constraint.setModelType((EClass) type.getType());
			return constraint;
		}
	}
	
	protected void setConstraintCondition(final Constraint constraint, final RoamStreamExpr streamExpr) throws Exception{
		RoamStreamExpr terminalExpr = getTerminalStreamExpression(streamExpr);
		if(terminalExpr instanceof RoamStreamBoolExpr streamBool) {
			if(streamBool.getOperator() == RoamStreamNoArgOperator.COUNT) {
				throw new IllegalArgumentException("Some constrains contain invalid values within boolean expressions, e.g., arithmetic values instead of boolean values.");
			} else {
				//TODO
			}
		} else {
			throw new IllegalArgumentException("Some constrains contain invalid values within boolean expressions, e.g., arithmetic values instead of boolean values.");
		}
	}
	
	protected RoamStreamExpr getTerminalStreamExpression(final RoamStreamExpr expr) {
		if(expr instanceof RoamStreamNavigation nav) {
			return getTerminalStreamExpression(nav.getRight());
		} else {
			return expr;
		}
	}
	
	protected void mapGT2IBeXElements() {
		for(EditorPattern ePattern : data.roamSlangFile().getPatterns().stream()
				.filter(pattern -> GTEditorPatternUtils.containsCreatedOrDeletedElements(pattern))
				.collect(Collectors.toList())) {
			for(IBeXRule rule : data.model().getIbexModel().getRuleSet().getRules()) {
				if(rule.getName().equals(ePattern.getName())) {
					data.ePattern2Rule().put(ePattern, rule);
					for(EditorNode eNode : ePattern.getNodes()) {
						for(IBeXNode node : toContextPattern(rule.getLhs()).getLocalNodes()) {
							if(eNode.getName().equals(node.getName())) {
								data.eNode2Node().put(eNode, node);
							}
						}
					}
				}
			}
		}
	}
	
	public static IBeXContextPattern toContextPattern(final IBeXContext context) {
		if(context instanceof IBeXContextAlternatives alt) {
			return alt.getContext();
		} else {
			return (IBeXContextPattern)context;
		}
	}
}
