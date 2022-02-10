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
import org.emoflon.roam.build.transformation.helper.ArithmeticExpressionTransformer;
import org.emoflon.roam.build.transformation.helper.RelationalExpressionTransformer;
import org.emoflon.roam.build.transformation.helper.RoamTransformationUtils;
import org.emoflon.roam.build.transformation.helper.TransformerFactory;
import org.emoflon.roam.intermediate.RoamIntermediate.Constraint;
import org.emoflon.roam.intermediate.RoamIntermediate.GlobalObjective;
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingConstraint;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingObjective;
import org.emoflon.roam.intermediate.RoamIntermediate.Objective;
import org.emoflon.roam.intermediate.RoamIntermediate.ObjectiveTarget;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateFactory;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel;
import org.emoflon.roam.intermediate.RoamIntermediate.Type;
import org.emoflon.roam.intermediate.RoamIntermediate.TypeConstraint;
import org.emoflon.roam.intermediate.RoamIntermediate.TypeObjective;
import org.emoflon.roam.roamslang.roamSLang.EditorGTFile;
import org.emoflon.roam.roamslang.roamSLang.RoamBoolExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamBooleanLiteral;
import org.emoflon.roam.roamslang.roamSLang.RoamConstraint;
import org.emoflon.roam.roamslang.roamSLang.RoamGlobalObjective;
import org.emoflon.roam.roamslang.roamSLang.RoamMappingContext;
import org.emoflon.roam.roamslang.roamSLang.RoamObjective;
import org.emoflon.roam.roamslang.roamSLang.RoamRelExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamTypeContext;

public class RoamToIntermediate {
	protected RoamIntermediateFactory factory = RoamIntermediateFactory.eINSTANCE;
	final protected RoamTransformationData data;
	final protected TransformerFactory transformationFactory;
	
	public RoamToIntermediate(final EditorGTFile roamSlangFile) {
		data = new RoamTransformationData(factory.createRoamIntermediateModel(), roamSlangFile);
		this.transformationFactory = new TransformerFactory(data);
	}
	
	public RoamIntermediateModel transform() throws Exception {
		//transform GT to IBeXPatterns
		EditorToIBeXPatternTransformation ibexTransformer = new EditorToIBeXPatternTransformation();
		data.model().setIbexModel(ibexTransformer.transform(data.roamSlangFile()));
		mapGT2IBeXElements();
		
		//transform Roam components
		transformMappings();
		transformConstraints();
		transformObjectives();
		transformGlobalObjective();
		
		//add all required data types
		data.model().getVariables().addAll(data.eType2Type().values());
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
				constraintCounter++;
				//TODO: For now we'll generate a constraint for each context element. In the future, this should be optimized.
				constraint.setElementwise(true);
				data.model().getConstraints().add(constraint);
				data.eConstraint2Constraint().put(eSubConstraint, constraint);
				
				RelationalExpressionTransformer transformer = transformationFactory.createRelationalTransformer(constraint);
				constraint.setExpression(transformer.transform((RoamRelExpr) boolExpr));
				if(constraint.getExpression().getRhs() == null) {
					if(RoamTransformationUtils.isConstantExpression(constraint.getExpression())) {
						throw new UnsupportedOperationException("Expressions that can be evaluated statically at ILP problem build time are currently not allowed.");
					}
				} else {
					if(RoamTransformationUtils.isConstantExpression(constraint.getExpression())) {
						throw new UnsupportedOperationException("Expressions that can be evaluated statically at ILP problem build time are currently not allowed.");
					} else {
						boolean isLhsConst = RoamTransformationUtils.isConstantExpression(constraint.getExpression().getLhs());
						boolean isRhsConst = RoamTransformationUtils.isConstantExpression(constraint.getExpression().getRhs());
						if(!isLhsConst && !isRhsConst) {
							throw new UnsupportedOperationException("Constraints on mapping variables of a certain type may not depend on the value of mapping variables of other types.");
						}
					}
				}
			}
		}
	}
	
	protected void transformObjectives() throws Exception {
		for(RoamObjective eObjective : data.roamSlangFile().getObjectives()) {
			if(eObjective.getExpr() == null) {
				continue;
			}
			
			Objective objective = createObjective(eObjective);
			objective.setElementwise(true);
			data.model().getObjectives().add(objective);
			data.eObjective2Objective().put(eObjective, objective);
			
			ArithmeticExpressionTransformer transformer = transformationFactory.createArithmeticTransformer(objective);
			objective.setExpression(transformer.transform(eObjective.getExpr()));
		}
	}
	
	protected void transformGlobalObjective() throws Exception {
		RoamGlobalObjective eGlobalObj = data.roamSlangFile().getGlobalObjective();
		if(eGlobalObj == null) {
			return;
		}
		
		GlobalObjective globalObj = factory.createGlobalObjective();
		data.model().setGlobalObjective(globalObj);
		
		switch(eGlobalObj.getObjectiveGoal()) {
			case MAX -> {
				globalObj.setTarget(ObjectiveTarget.MAX);
			}
			case MIN -> {
				globalObj.setTarget(ObjectiveTarget.MIN);
			}
			default -> {
				throw new IllegalArgumentException("Unknown global objective function goal: "+eGlobalObj.getObjectiveGoal());
			}
		}
		
		ArithmeticExpressionTransformer transformer = transformationFactory.createArithmeticTransformer(globalObj);
		globalObj.setExpression(transformer.transform(eGlobalObj.getExpr()));
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
			Type varType = data.getType((EClass) type.getType());
			constraint.setModelType(varType);
			return constraint;
		}
	}
	
	protected Objective createObjective(final RoamObjective eObjective) {
		if(eObjective.getContext() instanceof RoamMappingContext mapping) {
			MappingObjective objective = factory.createMappingObjective();
			objective.setName(eObjective.getName());
			objective.setMapping(data.eMapping2Mapping().get(mapping.getMapping()));
			return objective;
		} else  {
			RoamTypeContext type = (RoamTypeContext) eObjective.getContext();
			TypeObjective objective = factory.createTypeObjective();
			objective.setName(eObjective.getName());
			Type varType = data.getType((EClass) type.getType());
			objective.setModelType(varType);
			return objective;
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
						for(IBeXNode node : toContextPattern(rule.getLhs()).getSignatureNodes()) {
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
