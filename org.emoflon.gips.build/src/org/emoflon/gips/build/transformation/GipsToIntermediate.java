package org.emoflon.gips.build.transformation;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.emoflon.gips.build.transformation.helper.ArithmeticExpressionType;
import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils;
import org.emoflon.gips.build.transformation.transformer.ArithmeticExpressionTransformer;
import org.emoflon.gips.build.transformation.transformer.BooleanExpressionTransformer;
import org.emoflon.gips.build.transformation.transformer.TransformerFactory;
import org.emoflon.gips.gipsl.gipsl.EditorGTFile;
import org.emoflon.gips.gipsl.gipsl.GipsConfig;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunction;
import org.emoflon.gips.gipsl.gipsl.GipsMapping;
import org.emoflon.gips.gipsl.gipsl.GipsMappingVariable;
import org.emoflon.gips.gipsl.gipsl.GipsObjective;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticBinaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticBinaryOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Constraint;
import org.emoflon.gips.intermediate.GipsIntermediate.DoubleLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateFactory;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel;
import org.emoflon.gips.intermediate.GipsIntermediate.Goal;
import org.emoflon.gips.intermediate.GipsIntermediate.LinearFunction;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingFunction;
import org.emoflon.gips.intermediate.GipsIntermediate.Objective;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternFunction;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternMapping;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.RuleConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.RuleFunction;
import org.emoflon.gips.intermediate.GipsIntermediate.RuleMapping;
import org.emoflon.gips.intermediate.GipsIntermediate.RuleParameterVariable;
import org.emoflon.gips.intermediate.GipsIntermediate.SolverConfig;
import org.emoflon.gips.intermediate.GipsIntermediate.SolverType;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeFunction;
import org.emoflon.gips.intermediate.GipsIntermediate.Variable;
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.utils.GTEditorPatternUtils;
import org.emoflon.ibex.gt.transformations.EditorToIBeXPatternTransformation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;
import org.logicng.formulas.Formula;
import org.logicng.formulas.Literal;

public class GipsToIntermediate {

	protected GipsIntermediateFactory factory = GipsIntermediateFactory.eINSTANCE;
	final protected GipsTransformationData data;
	final protected TransformerFactory transformationFactory;
	protected int constraintCounter = 0;

	public GipsToIntermediate(final EditorGTFile gipslFile) {
		data = new GipsTransformationData(factory.createGipsIntermediateModel(), gipslFile);
		this.transformationFactory = new TransformerFactory(data);
	}

	public GipsIntermediateModel transform() throws Exception {
		// transform GT to IBeXPatterns
		preprocessGipslFile();

		// transform Gips components
		transformConfig();
		transformMappings();
		transformConstraints();
		transformLinearFunctions();
		transformObjective();

		// add all required data types
		data.model().getRequiredPatterns().addAll(data.ePattern2pattern().values());
		data.model().getRequiredRules().addAll(data.ePattern2rule().values());
		data.model().getRequiredTypes().addAll(data.requiredTypes());

		return data.model();
	}

	protected void preprocessGipslFile() {
		EditorToIBeXPatternTransformation ibexTransformer = new EditorToIBeXPatternTransformation();
		data.model().setIbexModel(ibexTransformer.transform(data.gipslFile()));

		Set<org.emoflon.ibex.gt.editor.gT.EditorGTFile> foreignFiles = new HashSet<>();
		data.gipslFile().getImportedPattern().forEach(
				p -> foreignFiles.add((org.emoflon.ibex.gt.editor.gT.EditorGTFile) p.getPattern().eContainer()));
		for (org.emoflon.ibex.gt.editor.gT.EditorGTFile foreignFile : foreignFiles) {
			ibexTransformer = new EditorToIBeXPatternTransformation();
			IBeXModel foreignModel = ibexTransformer.transform(foreignFile);
			IBeXModel model = data.model().getIbexModel();
			// TODO: For now lets just import everything -> this might lead to naming
			// collisions and must be performed more selectively in the future.
			model.getNodeSet().getNodes().addAll(foreignModel.getNodeSet().getNodes());
			model.getEdgeSet().getEdges().addAll(foreignModel.getEdgeSet().getEdges());
			model.getPatternSet().getContextPatterns().addAll(foreignModel.getPatternSet().getContextPatterns());
			model.getRuleSet().getRules().addAll(foreignModel.getRuleSet().getRules());
		}

		mapGT2IBeXElements();
	}

	protected void transformConfig() {
		GipsConfig eConfig = data.gipslFile().getConfig();
		SolverConfig config = factory.createSolverConfig();
		switch (eConfig.getSolver()) {
		case GUROBI -> {
			config.setSolver(SolverType.GUROBI);
		}
		case GLPK -> {
			config.setSolver(SolverType.GLPK);
		}
		case CPLEX -> {
			config.setSolver(SolverType.CPLEX);
		}
		default -> {
			throw new IllegalArgumentException("Unsupported solver type: " + eConfig.getSolver());
		}
		}

		// If no home folder is provided, use 'null' instead
		if (eConfig.getHome() != null) {
			config.setSolverHomeDir(eConfig.getHome().replace("\"", ""));
		} else {
			config.setSolverHomeDir(null);
		}

		// If no license folder is provided, use 'null' instead
		if (eConfig.getLicense() != null) {
			config.setSolverLicenseFile(eConfig.getLicense().replace("\"", ""));
		} else {
			config.setSolverLicenseFile(null);
		}

		config.setBuildLaunchConfig(eConfig.isEnableLaunchConfig());
		if (eConfig.isEnableLaunchConfig())
			config.setMainFile(eConfig.getMainLoc().replace("\"", ""));

		config.setEnableDebugOutput(eConfig.isEnableDebugOutput());
		config.setEnablePresolve(eConfig.isEnablePresolve());

		config.setEnableRndSeed(eConfig.isEnableSeed());
		if (eConfig.isEnableSeed()) {
			config.setIlpRndSeed(eConfig.getRndSeed());
		}

		config.setEnableTimeLimit(eConfig.isEnableLimit());
		if (eConfig.isEnableLimit()) {
			config.setIlpTimeLimit(eConfig.getTimeLimit());
		}

		config.setEnableCustomTolerance(eConfig.isEnableTolerance());
		if (eConfig.isEnableTolerance()) {
			config.setTolerance(eConfig.getTolerance());
		}

		config.setEnableLpOutput(eConfig.isEnableLpOutput());
		if (eConfig.isEnableLpOutput()) {
			config.setLpPath(eConfig.getPath().replace("\"", ""));
		}

		config.setThreadCountEnabled(eConfig.isEnableThreadCount());
		if (eConfig.isEnableThreadCount()) {
			config.setThreadCount(eConfig.getThreads());
		}

		data.model().setConfig(config);
	}

	protected void transformMappings() {
		data.gipslFile().getMappings().forEach(eMapping -> {
			Mapping mapping = null;
			if (GTEditorPatternUtils.containsCreatedOrDeletedElements(eMapping.getPattern())) {
				RuleMapping ruleMapping = factory.createRuleMapping();
				ruleMapping.setRule(data.getRule(eMapping.getPattern()));
				if (ruleMapping.getRule().getLhs() instanceof IBeXContextAlternatives alt) {
					ruleMapping.setContextPattern(alt.getContext());
				} else {
					ruleMapping.setContextPattern((IBeXContextPattern) ruleMapping.getRule().getLhs());
				}
				mapping = ruleMapping;
				transformMappingVariables(eMapping, ruleMapping);
			} else {
				PatternMapping pmMapping = factory.createPatternMapping();
				mapping = pmMapping;

				IBeXPattern pattern = data.getPattern(eMapping.getPattern());
				if (pattern instanceof IBeXContextAlternatives alt) {
					pmMapping.setContextPattern(alt.getContext());
				} else {
					pmMapping.setContextPattern((IBeXContextPattern) pattern);
				}
				pmMapping.setPattern(pattern);
				transformMappingVariables(eMapping, pmMapping);
			}

			mapping.setName(eMapping.getName());
			Variable mappingVariable = GipsConstraintUtils.createBinaryVariable(data, factory, eMapping.getName());
			mapping.setMappingVariable(mappingVariable);
			data.eVariable2Variable().put(eMapping, mappingVariable);

			data.model().getMappings().add(mapping);
			data.eMapping2Mapping().put(eMapping, mapping);
		});
	}

	protected void transformMappingVariables(GipsMapping mapping, RuleMapping ruleMapping) {
		if (mapping.getVariables() == null || mapping.getVariables().isEmpty())
			return;

		for (GipsMappingVariable gipsVar : mapping.getVariables()) {
			if (gipsVar.isBound()) {
				RuleParameterVariable var = factory.createRuleParameterVariable();
				var.setType(GipsTransformationUtils.typeToVariableType(gipsVar.getType()));
				IBeXRule rule = data.getRule(mapping.getPattern());
				var.setRule(rule);
				var.setParameter(rule.getParameters().stream()
						.filter(param -> param.getName().equals(gipsVar.getParameter().getName())).findFirst().get());
				var.setName(gipsVar.getName());
				var.setLowerBound(GipsTransformationUtils.getLowerBound(gipsVar, var.getType()));
				var.setUpperBound(GipsTransformationUtils.getUpperBound(gipsVar, var.getType()));
				ruleMapping.getBoundVariables().add(var);
				data.model().getVariables().add(var);
				data.eVariable2Variable().put(gipsVar, var);
			} else {
				Variable var = factory.createVariable();
				var.setType(GipsTransformationUtils.typeToVariableType(gipsVar.getType()));
				var.setName(gipsVar.getName());
				var.setLowerBound(GipsTransformationUtils.getLowerBound(gipsVar, var.getType()));
				var.setUpperBound(GipsTransformationUtils.getUpperBound(gipsVar, var.getType()));
				ruleMapping.getFreeVariables().add(var);
				data.model().getVariables().add(var);
				data.eVariable2Variable().put(gipsVar, var);
			}
		}
	}

	protected void transformMappingVariables(GipsMapping mapping, PatternMapping patternMapping) {
		if (mapping.getVariables() == null || mapping.getVariables().isEmpty())
			return;

		for (GipsMappingVariable gipsVar : mapping.getVariables()) {
			Variable var = factory.createVariable();
			var.setType(GipsTransformationUtils.typeToVariableType(gipsVar.getType()));
			var.setName(gipsVar.getName());
			var.setLowerBound(GipsTransformationUtils.getLowerBound(gipsVar, var.getType()));
			var.setUpperBound(GipsTransformationUtils.getUpperBound(gipsVar, var.getType()));
			patternMapping.getFreeVariables().add(var);
			data.model().getVariables().add(var);
			data.eVariable2Variable().put(gipsVar, var);
		}
	}

	protected void transformConstraints() throws Exception {

		for (GipsConstraint eConstraint : data.gipslFile().getConstraints()) {
			if (eConstraint.getExpression() == null) {
				continue;
			}

			GipsConstraintSplitter splitter = new GipsConstraintSplitter(data, eConstraint);
			Collection<GipsAnnotatedConstraint> eConstraints = splitter.split();

			for (GipsAnnotatedConstraint eSubConstraint : eConstraints) {
				switch (eSubConstraint.type()) {
				case DISJUNCTION_OF_LITERALS -> {
					Constraint disjunction = createDisjunctConstraint(eSubConstraint, constraintCounter);
					data.model().getConstraints().add(disjunction);
					constraintCounter++;

					Map<GipsConstraint, Collection<Constraint>> transformed = new HashMap<>();
					Map<GipsConstraint, Variable> constraint2Symbolic = new HashMap<>();
					for (GipsConstraint subConstraint : eSubConstraint.result().values()) {
						Variable symbolicVariable = GipsConstraintUtils.createBinaryVariable(data, factory,
								disjunction.getName() + "_symbolic" + constraint2Symbolic.size());
						disjunction.getHelperVariables().add(symbolicVariable);
						constraint2Symbolic.put(subConstraint, symbolicVariable);
						Collection<Constraint> constraints = transformConstraint(subConstraint, false,
								symbolicVariable);
						constraints.forEach(c -> c.setSymbolicVariable(symbolicVariable));
						transformed.put(subConstraint, constraints);
						disjunction.getDependencies().addAll(constraints);
					}

					// Create substitute relational constraint representing a logic disjunction of
					// N-K negated or K non-negated variables, s.t.: var_1 + ... + var_K +
					// (1-var_K+1) + ... + (1-var_N) >= 1
					RelationalExpression substituteRelation = factory.createRelationalExpression();
					substituteRelation.setOperator(RelationalOperator.GREATER_OR_EQUAL);
					DoubleLiteral d1 = factory.createDoubleLiteral();
					d1.setLiteral(1.0);
					substituteRelation.setRhs(d1);

					ArithmeticBinaryExpression substituteSum = factory.createArithmeticBinaryExpression();
					substituteSum.setOperator(ArithmeticBinaryOperator.ADD);

					ArithmeticBinaryExpression currentSum = substituteSum;
					LinkedList<Formula> subformulas = new LinkedList<>(eSubConstraint.formula().stream().toList());
					while (!subformulas.isEmpty()) {
						// Retrieve corresponding orginal and transformed constraint as well as slack
						// variables
						Formula subformula = subformulas.poll();
						GipsConstraint splitConstraint = eSubConstraint.result().get(subformula);
						Variable symbolicVariable = constraint2Symbolic.get(splitConstraint);

						VariableReference varRef = factory.createVariableReference();
						varRef.setVariable(symbolicVariable);
						if (subformula instanceof Literal lit && lit.phase()) {
							if (currentSum.getLhs() == null && !subformulas.isEmpty()) {
								currentSum.setLhs(varRef);
							} else if (currentSum.getLhs() != null && !subformulas.isEmpty()) {
								ArithmeticBinaryExpression subSum = factory.createArithmeticBinaryExpression();
								subSum.setOperator(ArithmeticBinaryOperator.ADD);
								currentSum.setRhs(subSum);
								currentSum = subSum;
								subSum.setLhs(varRef);
							} else if (currentSum.getLhs() != null && subformulas.isEmpty()) {
								currentSum.setRhs(varRef);
							} else {
								throw new UnsupportedOperationException(
										"Disjunction of boolean literals must have more than one literal.");
							}
						} else if (subformula instanceof Literal lit && !lit.phase()) {
							ArithmeticBinaryExpression negSum = factory.createArithmeticBinaryExpression();
							negSum.setOperator(ArithmeticBinaryOperator.ADD);
							DoubleLiteral d2 = factory.createDoubleLiteral();
							d2.setLiteral(-1.0);
							negSum.setRhs(d2);
							negSum.setLhs((ArithmeticExpression) substituteRelation.getRhs());
							substituteRelation.setRhs(negSum);

							ArithmeticBinaryExpression negation = factory.createArithmeticBinaryExpression();
							negation.setOperator(ArithmeticBinaryOperator.MULTIPLY);
							DoubleLiteral d3 = factory.createDoubleLiteral();
							d3.setLiteral(-1.0);
							negation.setLhs(d3);
							negation.setRhs(varRef);

							if (currentSum.getLhs() == null && !subformulas.isEmpty()) {
								currentSum.setLhs(negation);
							} else if (currentSum.getLhs() != null && !subformulas.isEmpty()) {
								ArithmeticBinaryExpression subSum = factory.createArithmeticBinaryExpression();
								subSum.setOperator(ArithmeticBinaryOperator.ADD);
								currentSum.setRhs(subSum);
								currentSum = subSum;
								subSum.setLhs(negation);
							} else if (currentSum.getLhs() != null && subformulas.isEmpty()) {
								currentSum.setRhs(negation);
							} else {
								throw new UnsupportedOperationException(
										"Disjunction of boolean literals must have more than one literal.");
							}
						} else {
							throw new UnsupportedOperationException(
									"Literals in a boolean expression in CNF-form should not be boolean expressions themselves.");
						}
					}

					substituteRelation.setLhs(substituteSum);
					disjunction.setExpression(substituteRelation);
				}
				case LITERAL -> {
					transformConstraint(eSubConstraint.result().values().iterator().next(), false, null);
				}
				case NEGATED_LITERAL -> {
					transformConstraint(eSubConstraint.result().values().iterator().next(), true, null);
				}
				default -> {
					throw new IllegalArgumentException("Unknown constraint annotation type.");
				}

				}

			}
		}
	}

	protected Collection<Constraint> transformConstraint(final GipsConstraint subConstraint, boolean isNegated,
			Variable symbolicVariable) throws Exception {
		Constraint constraint = createConstraint(subConstraint, constraintCounter);
		constraint.setDepending(false);
		constraint.setNegated(false);

		data.model().getConstraints().add(constraint);
		data.eConstraint2Constraints().put(subConstraint, new LinkedList<>(List.of(constraint)));
		constraintCounter++;

		BooleanExpressionTransformer transformer = transformationFactory.createBooleanTransformer(constraint);
		constraint.setExpression(transformer.transform(subConstraint.getExpression()));

		if (GipsTransformationUtils
				.isConstantExpression(constraint.getExpression()) == ArithmeticExpressionType.constant) {
			// Check whether this constraint is constant at ILP problem build time. If true
			// -> return
			constraint.setConstant(true);
			constraint.setNegated(isNegated);
			return new LinkedList<>(List.of(constraint));
		}

		if (!(constraint.getExpression() instanceof RelationalExpression)) {
			throw new UnsupportedOperationException(
					"Transformed non-constant sub-constraints may not contain boolean expressions or boolean literals.");
		}

		GipsArithmeticTransformer arithmeticTransformer = new GipsArithmeticTransformer(factory);
		constraint.setExpression(arithmeticTransformer.normalize((RelationalExpression) constraint.getExpression()));

		// Normalize the relational expression operator such that it conforms to most
		// ILP solver's requirements (i.e., no !=, > and < operators allowed).
		Collection<Constraint> substitutes = new LinkedList<>();
		if (symbolicVariable == null) {
			substitutes = GipsConstraintUtils.normalizeOperator(data, factory, constraint, isNegated);
		} else {
			substitutes = GipsConstraintUtils.normalizeOperator(data, factory, constraint, symbolicVariable);
		}

		if (substitutes.isEmpty()) {
			return new LinkedList<>(List.of(constraint));
		}

		data.model().getConstraints().remove(constraint);
		data.model().getConstraints().addAll(substitutes);
		data.eConstraint2Constraints().get(subConstraint).clear();
		data.eConstraint2Constraints().get(subConstraint).addAll(substitutes);

		return substitutes;
	}

	protected void transformLinearFunctions() throws Exception {
		for (GipsLinearFunction eLinearFunction : data.gipslFile().getFunctions()) {
			if (eLinearFunction.getExpression() == null) {
				continue;
			}

			LinearFunction linearFunction = createLinearFunction(eLinearFunction);
			data.model().getFunctions().add(linearFunction);
			data.eFunction2Function().put(eLinearFunction, linearFunction);

			ArithmeticExpressionTransformer transformer = transformationFactory
					.createArithmeticTransformer(linearFunction);
			linearFunction.setExpression(transformer.transform(eLinearFunction.getExpression()));
			// Rewrite the expression, which will be translated into ILP-Terms, into a sum
			// of products.
			linearFunction.setExpression(
					new GipsArithmeticTransformer(factory).normalizeAndExpand(linearFunction.getExpression()));
		}
	}

	protected void transformObjective() throws Exception {
		GipsObjective eObjective = data.gipslFile().getObjective();
		if (eObjective == null) {
			return;
		}

		Objective objective = factory.createObjective();
		data.model().setObjective(objective);

		switch (eObjective.getGoal()) {
		case MAX -> {
			objective.setGoal(Goal.MAX);
		}
		case MIN -> {
			objective.setGoal(Goal.MIN);
		}
		default -> {
			throw new IllegalArgumentException("Unknown global objective function goal: " + eObjective.getGoal());
		}
		}

		ArithmeticExpressionTransformer transformer = transformationFactory.createArithmeticTransformer(objective);
		objective.setExpression(transformer.transform(eObjective.getExpression()));
		// Rewrite the expression, which will be translated into ILP-Terms, into a sum
		// of products.
		objective.setExpression(new GipsArithmeticTransformer(factory).normalizeAndExpand(objective.getExpression()));
	}

	protected Constraint createConstraint(final GipsConstraint eConstraint, int counter) {
		if (eConstraint.getContext() instanceof GipsMapping mapping) {
			MappingConstraint constraint = factory.createMappingConstraint();
			constraint.setName("MappingConstraint" + counter + "On" + mapping.getName());
			constraint.setMapping(data.eMapping2Mapping().get(mapping));
			return constraint;
		} else if (eConstraint.getContext() instanceof EditorPattern ePattern) {
			if (GTEditorPatternUtils.containsCreatedOrDeletedElements(ePattern)) {
				IBeXRule rule = data.getRule(ePattern);
				RuleConstraint constraint = factory.createRuleConstraint();
				constraint.setName("RuleConstraint" + counter + "On" + rule.getName());
				constraint.setRule(rule);
				if (rule.getLhs() instanceof IBeXContextAlternatives alt) {
					constraint.setContextPattern(alt.getContext());
				} else {
					constraint.setContextPattern((IBeXContextPattern) rule.getLhs());
				}
				constraint.setGlobal(false);
				return constraint;
			} else {
				IBeXPattern pattern = data.getPattern(ePattern);
				PatternConstraint constraint = factory.createPatternConstraint();
				constraint.setName("PatternConstraint" + counter + "On" + pattern.getName());
				constraint.setPattern(pattern);
				if (pattern instanceof IBeXContextAlternatives alt) {
					constraint.setContextPattern(alt.getContext());
				} else {
					constraint.setContextPattern((IBeXContextPattern) pattern);
				}
				constraint.setGlobal(false);
				return constraint;
			}
		} else if (eConstraint.getContext() instanceof EClass type) {
			TypeConstraint constraint = factory.createTypeConstraint();
			constraint.setName("TypeConstraint" + counter + "On" + type.getName());
			constraint.setType(type);
			constraint.setGlobal(false);
			data.requiredTypes().add(type);
			return constraint;
		} else {
			Constraint constraint = factory.createConstraint();
			constraint.setName("Constraint" + counter);
			constraint.setGlobal(true);
			return constraint;
		}
	}

	protected Constraint createDisjunctConstraint(final GipsAnnotatedConstraint eConstraint, int counter) {
		if (eConstraint.input().getContext() instanceof GipsMapping mapping) {
			MappingConstraint constraint = factory.createMappingConstraint();
			constraint.setName("DisjunctMappingConstraint" + counter + "On" + mapping.getName());
			constraint.setMapping(data.eMapping2Mapping().get(mapping));
			constraint.setDepending(true);
			constraint.setGlobal(false);
			return constraint;
		} else if (eConstraint.input().getContext() instanceof EditorPattern ePattern) {
			if (GTEditorPatternUtils.containsCreatedOrDeletedElements(ePattern)) {
				IBeXRule rule = data.getRule(ePattern);
				RuleConstraint constraint = factory.createRuleConstraint();
				constraint.setName("DisjunctRuleConstraint" + counter + "On" + rule.getName());
				constraint.setRule(rule);
				if (rule.getLhs() instanceof IBeXContextAlternatives alt) {
					constraint.setContextPattern(alt.getContext());
				} else {
					constraint.setContextPattern((IBeXContextPattern) rule.getLhs());
				}
				constraint.setDepending(true);
				constraint.setGlobal(false);
				return constraint;
			} else {
				IBeXPattern pattern = data.getPattern(ePattern);
				PatternConstraint constraint = factory.createPatternConstraint();
				constraint.setName("DisjunctPatternConstraint" + counter + "On" + pattern.getName());
				constraint.setPattern(pattern);
				if (pattern instanceof IBeXContextAlternatives alt) {
					constraint.setContextPattern(alt.getContext());
				} else {
					constraint.setContextPattern((IBeXContextPattern) pattern);
				}
				constraint.setDepending(true);
				constraint.setGlobal(false);
				return constraint;
			}
		} else if (eConstraint.input().getContext() instanceof EClass type) {
			TypeConstraint constraint = factory.createTypeConstraint();
			constraint.setName("DisjunctTypeConstraint" + counter + "On" + type.getName());
			constraint.setDepending(true);
			constraint.setType(type);
			constraint.setGlobal(false);
			data.requiredTypes().add(type);
			return constraint;
		} else {
			Constraint constraint = factory.createConstraint();
			constraint.setName("DisjunctConstraint" + counter);
			constraint.setDepending(true);
			constraint.setGlobal(true);
			return constraint;
		}
	}

	protected LinearFunction createLinearFunction(final GipsLinearFunction eLinearFunction) {
		if (eLinearFunction.getContext() instanceof GipsMapping mapping) {
			MappingFunction function = factory.createMappingFunction();
			function.setName(eLinearFunction.getName());
			function.setMapping(data.eMapping2Mapping().get(mapping));
			return function;
		} else if (eLinearFunction.getContext() instanceof EditorPattern ePattern) {
			if (GTEditorPatternUtils.containsCreatedOrDeletedElements(ePattern)) {
				IBeXRule rule = data.getRule(ePattern);
				RuleFunction function = factory.createRuleFunction();
				function.setRule(rule);
				if (rule.getLhs() instanceof IBeXContextAlternatives alt) {
					function.setContextPattern(alt.getContext());
				} else {
					function.setContextPattern((IBeXContextPattern) rule.getLhs());
				}
				return function;
			} else {
				IBeXPattern pattern = data.getPattern(ePattern);
				PatternFunction function = factory.createPatternFunction();
				function.setPattern(pattern);
				if (pattern instanceof IBeXContextAlternatives alt) {
					function.setContextPattern(alt.getContext());
				} else {
					function.setContextPattern((IBeXContextPattern) pattern);
				}
				return function;
			}
		} else {
			TypeFunction function = factory.createTypeFunction();
			function.setName(eLinearFunction.getName());
			function.setType((EClass) eLinearFunction.getContext());
			data.requiredTypes().add(function.getType());
			return function;
		}
	}

	protected void mapGT2IBeXElements() {
		Set<EditorPattern> allEditorPatterns = new HashSet<>();
		allEditorPatterns.addAll(data.gipslFile().getPatterns().stream()
				.filter(pattern -> GTEditorPatternUtils.containsCreatedOrDeletedElements(pattern))
				.collect(Collectors.toList()));
		allEditorPatterns.addAll(data.gipslFile().getImportedPattern().stream().map(ip -> ip.getPattern())
				.filter(pattern -> GTEditorPatternUtils.containsCreatedOrDeletedElements(pattern))
				.collect(Collectors.toList()));

		// Add all rules
		for (EditorPattern ePattern : allEditorPatterns) {
			for (IBeXRule rule : data.model().getIbexModel().getRuleSet().getRules()) {
				if (rule.getName().equals(ePattern.getName())) {
					data.addRule(ePattern, rule);
					for (EditorNode eNode : ePattern.getNodes()) {
						for (IBeXNode node : toContextPattern(rule.getLhs()).getSignatureNodes()) {
							if (eNode.getName().equals(node.getName())) {
								data.eNode2Node().put(eNode, node);
							}
						}
					}
				}
			}
		}

		allEditorPatterns = new HashSet<>();
		allEditorPatterns.addAll(data.gipslFile().getPatterns());
		allEditorPatterns.addAll(data.gipslFile().getImportedPattern().stream() //
				.map(ip -> ip.getPattern()).collect(Collectors.toList()));

		// Add all patterns
		for (EditorPattern ePattern : allEditorPatterns) {
			for (IBeXContext pattern : data.model().getIbexModel().getPatternSet().getContextPatterns()) {
				if (pattern.getName().equals(ePattern.getName())) {
					data.addPattern(ePattern, pattern);
					for (EditorNode eNode : ePattern.getNodes()) {
						for (IBeXNode node : toContextPattern(pattern).getSignatureNodes()) {
							if (eNode.getName().equals(node.getName())) {
								data.eNode2Node().putIfAbsent(eNode, node);
							}
						}
					}
				}
			}
		}
	}

	public static IBeXContextPattern toContextPattern(final IBeXContext context) {
		if (context instanceof IBeXContextAlternatives alt) {
			return alt.getContext();
		} else {
			return (IBeXContextPattern) context;
		}
	}
}