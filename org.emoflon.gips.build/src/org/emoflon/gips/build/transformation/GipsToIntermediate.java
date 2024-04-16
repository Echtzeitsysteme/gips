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
import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.build.transformation.helper.ArithmeticExpressionType;
import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils;
import org.emoflon.gips.build.transformation.transformer.ArithmeticExpressionTransformer;
import org.emoflon.gips.build.transformation.transformer.BooleanExpressionTransformer;
import org.emoflon.gips.build.transformation.transformer.TransformerFactory;
import org.emoflon.gips.gipsl.gipsl.EditorGTFile;
import org.emoflon.gips.gipsl.gipsl.GipsConfig;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsGlobalObjective;
import org.emoflon.gips.gipsl.gipsl.GipsMapping;
import org.emoflon.gips.gipsl.gipsl.GipsMappingContext;
import org.emoflon.gips.gipsl.gipsl.GipsMappingVariable;
import org.emoflon.gips.gipsl.gipsl.GipsObjective;
import org.emoflon.gips.gipsl.gipsl.GipsPatternContext;
import org.emoflon.gips.gipsl.gipsl.GipsTypeContext;
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.Constraint;
import org.emoflon.gips.intermediate.GipsIntermediate.DoubleLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.GTMapping;
import org.emoflon.gips.intermediate.GipsIntermediate.GTParameterVariable;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateFactory;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel;
import org.emoflon.gips.intermediate.GipsIntermediate.GlobalConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.GlobalObjective;
import org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig;
import org.emoflon.gips.intermediate.GipsIntermediate.ILPSolverType;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingObjective;
import org.emoflon.gips.intermediate.GipsIntermediate.Objective;
import org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveTarget;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternMapping;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternObjective;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.Type;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeObjective;
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
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;
import org.logicng.formulas.Formula;
import org.logicng.formulas.Literal;

public class GipsToIntermediate {

	protected GipsIntermediateFactory factory = GipsIntermediateFactory.eINSTANCE;
	final protected GipsTransformationData data;
	final protected TransformerFactory transformationFactory;
	protected int constraintCounter = 0;

	public GipsToIntermediate(final EditorGTFile gipsSlangFile) {
		data = new GipsTransformationData(factory.createGipsIntermediateModel(), gipsSlangFile);
		this.transformationFactory = new TransformerFactory(data);
	}

	public GipsIntermediateModel transform() throws Exception {
		// transform GT to IBeXPatterns
		preprocessGipslFile();

		// transform Gips components
		transformConfig();
		transformMappings();
		transformConstraints();
		transformObjectives();
		transformGlobalObjective();

		// add all required data types
		data.model().getVariables().addAll(data.eType2Type().values());
		data.model().getVariables().addAll(data.ePattern2Pattern().values());

		return data.model();
	}

	protected void preprocessGipslFile() {
		EditorToIBeXPatternTransformation ibexTransformer = new EditorToIBeXPatternTransformation();
		data.model().setIbexModel(ibexTransformer.transform(data.gipsSlangFile()));

		Set<org.emoflon.ibex.gt.editor.gT.EditorGTFile> foreignFiles = new HashSet<>();
		data.gipsSlangFile().getImportedPattern().forEach(
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
		GipsConfig eConfig = data.gipsSlangFile().getConfig();
		ILPConfig config = factory.createILPConfig();
		switch (eConfig.getSolver()) {
		case GUROBI -> {
			config.setSolver(ILPSolverType.GUROBI);
		}
		case GLPK -> {
			config.setSolver(ILPSolverType.GLPK);
		}
		case CPLEX -> {
			config.setSolver(ILPSolverType.CPLEX);
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

		data.model().setConfig(config);
	}

	protected void transformMappings() {
		data.gipsSlangFile().getMappings().forEach(eMapping -> {
			Mapping mapping = null;
			if (GTEditorPatternUtils.containsCreatedOrDeletedElements(eMapping.getPattern())) {
				GTMapping gtMapping = factory.createGTMapping();
				gtMapping.setRule(data.ePattern2Rule().get(eMapping.getPattern()));
				if (gtMapping.getRule().getLhs() instanceof IBeXContextAlternatives alt) {
					gtMapping.setContextPattern(alt.getContext());
				} else {
					gtMapping.setContextPattern((IBeXContextPattern) gtMapping.getRule().getLhs());
				}
				mapping = gtMapping;
				transformMappingVariables(eMapping, gtMapping);
			} else {
				PatternMapping pmMapping = factory.createPatternMapping();
				mapping = pmMapping;

				IBeXContext context = data.ePattern2Context().get(eMapping.getPattern());
				if (context instanceof IBeXContextAlternatives alt) {
					pmMapping.setContextPattern(alt.getContext());
				} else {
					pmMapping.setContextPattern((IBeXContextPattern) context);
				}
				pmMapping.setPattern(context);
				transformMappingVariables(eMapping, pmMapping);
			}

			mapping.setName(eMapping.getName());
			mapping.setUpperBound(1.0);
			mapping.setLowerBound(0.0);
			data.model().getVariables().add(mapping);
			data.eMapping2Mapping().put(eMapping, mapping);
		});
	}

	protected void transformMappingVariables(GipsMapping mapping, GTMapping gtMapping) {
		if (mapping.getVariables() == null || mapping.getVariables().isEmpty())
			return;

		for (GipsMappingVariable gipsVar : mapping.getVariables()) {
			if (gipsVar.isBound()) {
				GTParameterVariable var = factory.createGTParameterVariable();
				var.setType(GipsTransformationUtils.typeToVariableType(gipsVar.getType()));
				IBeXRule rule = data.ePattern2Rule().get(mapping.getPattern());
				var.setRule(rule);
				var.setParameter(rule.getParameters().stream()
						.filter(param -> param.getName().equals(gipsVar.getParameter().getName())).findFirst().get());
				var.setName(gipsVar.getName());
				var.setLowerBound(GipsTransformationUtils.getLowerBound(gipsVar, var.getType()));
				var.setUpperBound(GipsTransformationUtils.getUpperBound(gipsVar, var.getType()));
				gtMapping.getBoundVariables().add(var);
				data.model().getVariables().add(var);
				data.eVariable2Variable().put(gipsVar, var);
			} else {
				Variable var = factory.createVariable();
				var.setType(GipsTransformationUtils.typeToVariableType(gipsVar.getType()));
				var.setName(gipsVar.getName());
				var.setLowerBound(GipsTransformationUtils.getLowerBound(gipsVar, var.getType()));
				var.setUpperBound(GipsTransformationUtils.getUpperBound(gipsVar, var.getType()));
				gtMapping.getFreeVariables().add(var);
				data.model().getVariables().add(var);
				data.eVariable2Variable().put(gipsVar, var);
			}

		}
	}

	protected void transformMappingVariables(GipsMapping mapping, PatternMapping gtMapping) {
		if (mapping.getVariables() == null || mapping.getVariables().isEmpty())
			return;

		for (GipsMappingVariable gipsVar : mapping.getVariables()) {
			Variable var = factory.createVariable();
			var.setType(GipsTransformationUtils.typeToVariableType(gipsVar.getType()));
			var.setName(gipsVar.getName());
			var.setLowerBound(GipsTransformationUtils.getLowerBound(gipsVar, var.getType()));
			var.setUpperBound(GipsTransformationUtils.getUpperBound(gipsVar, var.getType()));
			gtMapping.getFreeVariables().add(var);
			data.model().getVariables().add(var);
			data.eVariable2Variable().put(gipsVar, var);
		}
	}

	protected void transformConstraints() throws Exception {

		for (GipsConstraint eConstraint : data.gipsSlangFile().getConstraints()) {
			if (eConstraint.getExpr() == null || eConstraint.getExpr().getExpr() == null) {
				continue;
			}

			GipsConstraintSplitter splitter = new GipsConstraintSplitter(data, eConstraint);
			Collection<GipsAnnotatedConstraint> eConstraints = splitter.split();

			for (GipsAnnotatedConstraint eSubConstraint : eConstraints) {
				switch (eSubConstraint.type()) {
				// TODO: Fix -> Transform disjunction of rel-expressions according to new method
				// (#3), based on (#1 and #2).
				case DISJUNCTION_OF_LITERALS -> {
					Constraint dConstraint = createDependencyConstraint(eSubConstraint, constraintCounter);
					data.model().getConstraints().add(dConstraint);
					constraintCounter++;

					Map<GipsConstraint, Constraint> transformed = new HashMap<>();
					Map<GipsConstraint, VariableTuple> constraint2Slack = new HashMap<>();
					Map<GipsConstraint, VariableTuple> constraint2Symbolic = new HashMap<>();
					for (GipsConstraint subConstraint : eSubConstraint.result().values()) {
						Constraint constraint = transformConstraint(subConstraint);
						data.model().getConstraints().add(constraint);
						transformed.put(subConstraint, constraint);
						dConstraint.getDependencies().add(constraint);
						constraint.setNegated(false);

						if (!constraint.isConstant() && !(constraint.getExpression() instanceof RelationalExpression)) {
							throw new UnsupportedOperationException(
									"Negation for constraints that are constant at build time is currently not supported");
						}

						// Insert real-valued slack variables into non-inverted and inverted
						// non-build-time-constant constraint
						if (!constraint.isConstant()) {
							VariableTuple slackVars = GipsConstraintUtils.insertSlackVariables(data, factory,
									dConstraint, constraint);
							constraint2Slack.put(subConstraint, slackVars);
						}

					}

					// Create substitute relational constraint representing a logic disjunction of
					// N-K negated or K non-negated variables, s.t.: var_1 + ... + var_K +
					// (1-var_K+1) + ... + (1-var_N) >= 1
					RelationalExpression substituteRelation = factory.createRelationalExpression();
					substituteRelation.setOperator(RelationalOperator.GREATER_OR_EQUAL);
					DoubleLiteral d1 = factory.createDoubleLiteral();
					d1.setLiteral(1.0);
					substituteRelation.setRhs(d1);

					BinaryArithmeticExpression substituteSum = factory.createBinaryArithmeticExpression();
					substituteSum.setOperator(BinaryArithmeticOperator.ADD);

					BinaryArithmeticExpression currentSum = substituteSum;
					LinkedList<Formula> subformulas = new LinkedList<>(eSubConstraint.formula().stream().toList());
					while (!subformulas.isEmpty()) {
						// Retrieve corresponding orginal and transformed constraint as well as slack
						// variables
						Formula subformula = subformulas.poll();
						GipsConstraint splitConstraint = eSubConstraint.result().get(subformula);
						Constraint constraint = transformed.get(splitConstraint);
						VariableTuple slackVars = constraint2Slack.get(splitConstraint);

						Variable binaryVarPos = GipsConstraintUtils.createBinaryVariable(data, factory,
								constraint.getName() + "_symVPos");
						dConstraint.getHelperVariables().add(binaryVarPos);

						Variable binaryVarNeg = GipsConstraintUtils.createBinaryVariable(data, factory,
								constraint.getName() + "_symVNeg");
						dConstraint.getHelperVariables().add(binaryVarNeg);

						VariableTuple symbolicVars = new VariableTuple(binaryVarPos, binaryVarNeg);
						constraint2Symbolic.put(splitConstraint, symbolicVars);
						constraint.setSymbolicVariable(symbolicVars.nonInverted());

						// Force symbolic variables non-zero constraint
						// symVPos + symVNeg == 1
						GipsConstraintUtils.insertSymbolicVariableNonZeroConstraint(factory, dConstraint,
								symbolicVars.nonInverted(), symbolicVars.inverted());

						// Insert slack variable constraints only for non-build-time-constant
						// constraints
						if (slackVars != null) {
							// Link symbolic and slack variables
							GipsConstraintUtils.insertSymbolicSlackLinkConstraint(factory, dConstraint,
									symbolicVars.nonInverted(), slackVars.nonInverted());
							GipsConstraintUtils.insertSymbolicSlackLinkConstraint(factory, dConstraint,
									symbolicVars.inverted(), slackVars.inverted());

							// Sos1 constraint
							GipsConstraintUtils.insertSos1Constraint(data, factory, dConstraint,
									symbolicVars.nonInverted(), slackVars.nonInverted());
							GipsConstraintUtils.insertSos1Constraint(data, factory, dConstraint,
									symbolicVars.inverted(), slackVars.inverted());
						}

						VariableReference varRef = factory.createVariableReference();
						varRef.setVariable(symbolicVars.nonInverted());
						if (subformula instanceof Literal lit && lit.phase()) {
							if (currentSum.getLhs() == null && !subformulas.isEmpty()) {
								currentSum.setLhs(varRef);
							} else if (currentSum.getLhs() != null && !subformulas.isEmpty()) {
								BinaryArithmeticExpression subSum = factory.createBinaryArithmeticExpression();
								subSum.setOperator(BinaryArithmeticOperator.ADD);
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
							BinaryArithmeticExpression negSum = factory.createBinaryArithmeticExpression();
							negSum.setOperator(BinaryArithmeticOperator.ADD);
							DoubleLiteral d2 = factory.createDoubleLiteral();
							d2.setLiteral(-1.0);
							negSum.setRhs(d2);
							negSum.setLhs(substituteRelation.getRhs());
							substituteRelation.setRhs(negSum);

							BinaryArithmeticExpression negation = factory.createBinaryArithmeticExpression();
							negation.setOperator(BinaryArithmeticOperator.MULTIPLY);
							DoubleLiteral d3 = factory.createDoubleLiteral();
							d3.setLiteral(-1.0);
							negation.setLhs(d3);
							negation.setRhs(varRef);

							if (currentSum.getLhs() == null && !subformulas.isEmpty()) {
								currentSum.setLhs(negation);
							} else if (currentSum.getLhs() != null && !subformulas.isEmpty()) {
								BinaryArithmeticExpression subSum = factory.createBinaryArithmeticExpression();
								subSum.setOperator(BinaryArithmeticOperator.ADD);
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
					dConstraint.setExpression(substituteRelation);
				}
				// TODO: Fix -> Transform relational operators according to new method (#1)
				case LITERAL -> {
					transformConstraint(eSubConstraint.result().values().iterator().next());
				}
				// TODO: Fix -> Transform negation according to new method (#2) that ist based
				// on (#1)
				case NEGATED_LITERAL -> {
					Constraint constraint = transformConstraint(eSubConstraint.result().values().iterator().next());
					data.model().getConstraints().add(constraint);

					if (constraint.isConstant()) {
						constraint.setNegated(true);
						return;
					}

					if (!(constraint.getExpression() instanceof RelationalExpression)) {
						throw new UnsupportedOperationException(
								"Negation for constraints that are constant at build time is currently not supported");
					}

					Constraint dConstraint = createDependencyConstraint(eSubConstraint, constraintCounter);
					data.model().getConstraints().add(dConstraint);
					constraintCounter++;
					dConstraint.getDependencies().add(constraint);

					// Insert real-valued slack variables into non-inverted and inverted constraint
					VariableTuple slackVars = GipsConstraintUtils.insertSlackVariables(data, factory, dConstraint,
							constraint);

					// Add negation constraint
					VariableTuple symbolicVars = GipsConstraintUtils.insertNegationConstraint(data, factory,
							dConstraint, constraint);
					constraint.setSymbolicVariable(symbolicVars.nonInverted());

					// Link symbolic and slack variables
					GipsConstraintUtils.insertSymbolicSlackLinkConstraint(factory, dConstraint,
							symbolicVars.nonInverted(), slackVars.nonInverted());
					GipsConstraintUtils.insertSymbolicSlackLinkConstraint(factory, dConstraint, symbolicVars.inverted(),
							slackVars.inverted());

					// Sos1 constraint
					GipsConstraintUtils.insertSos1Constraint(data, factory, dConstraint, symbolicVars.nonInverted(),
							slackVars.nonInverted());
					GipsConstraintUtils.insertSos1Constraint(data, factory, dConstraint, symbolicVars.inverted(),
							slackVars.inverted());
				}
				default -> {
					throw new IllegalArgumentException("Unknown constraint annotation type.");
				}

				}

			}
		}
	}

	// TODO: Fix -> Transform relational operators according to new method (#1)
	protected Constraint transformConstraint(final GipsConstraint subConstraint) throws Exception {
		Constraint constraint = createConstraint(subConstraint, constraintCounter);
		constraint.setDepending(false);
		constraint.setNegated(false);

		data.model().getConstraints().add(constraint);
		data.eConstraint2Constraints().put(subConstraint, List.of(constraint));
		constraintCounter++;

		BooleanExpressionTransformer transformer = transformationFactory.createBooleanTransformer(constraint);
		constraint.setExpression(transformer.transform(subConstraint.getExpr().getExpr()));

		if (GipsTransformationUtils
				.isConstantExpression(constraint.getExpression()) == ArithmeticExpressionType.constant) {
			// Check whether this constraint is constant at ILP problem build time. If true
			// -> return
			constraint.setConstant(true);
			return constraint;
		}

		if (!(constraint.getExpression() instanceof RelationalExpression)) {
			throw new UnsupportedOperationException(
					"Transformed non-constant sub-constraints may not contain boolean expressions or boolean literals.");
		}

		GipsArithmeticTransformer arithmeticTransformer = new GipsArithmeticTransformer(factory);
		constraint.setExpression(arithmeticTransformer.normalize((RelationalExpression) constraint.getExpression()));

		// Final check: Was the context used?
		if (!GipsTransformationUtils
				.containsContextExpression(((RelationalExpression) constraint.getExpression()).getRhs())
				&& !GipsTransformationUtils
						.containsContextExpression(((RelationalExpression) constraint.getExpression()).getLhs())
				&& !(constraint instanceof GlobalConstraint)) {
			throw new IllegalArgumentException("Context must be used at least once per non-global constraint.");
		}

		// Normalize the relational expression operator such that it conforms to most
		// ILP solver's requirements (i.e., no !=, > and < operators allowed).
		Collection<Constraint> substitutes = GipsConstraintUtils.normalizeOperator(data, factory, constraint, false);
		if (substitutes.isEmpty()) {
			return constraint;
		}

		data.model().getConstraints().remove(constraint);
		data.model().getConstraints().addAll(substitutes);
		data.eConstraint2Constraints().get(subConstraint).clear();
		data.eConstraint2Constraints().get(subConstraint).addAll(substitutes);

		return null;
	}

	protected void transformObjectives() throws Exception {
		for (GipsObjective eObjective : data.gipsSlangFile().getObjectives()) {
			if (eObjective.getExpr() == null) {
				continue;
			}

			Objective objective = createObjective(eObjective);
			objective.setElementwise(true);
			data.model().getObjectives().add(objective);
			data.eObjective2Objective().put(eObjective, objective);

			ArithmeticExpressionTransformer<? extends EObject> transformer = transformationFactory
					.createArithmeticTransformer(objective);
			objective.setExpression(transformer.transform(eObjective.getExpr()));
			// Rewrite the expression, which will be translated into ILP-Terms, into a sum
			// of products.
			objective.setExpression(
					new GipsArithmeticTransformer(factory).normalizeAndExpand(objective.getExpression()));
		}
	}

	protected void transformGlobalObjective() throws Exception {
		GipsGlobalObjective eGlobalObj = data.gipsSlangFile().getGlobalObjective();
		if (eGlobalObj == null) {
			return;
		}

		GlobalObjective globalObj = factory.createGlobalObjective();
		data.model().setGlobalObjective(globalObj);

		switch (eGlobalObj.getObjectiveGoal()) {
		case MAX -> {
			globalObj.setTarget(ObjectiveTarget.MAX);
		}
		case MIN -> {
			globalObj.setTarget(ObjectiveTarget.MIN);
		}
		default -> {
			throw new IllegalArgumentException(
					"Unknown global objective function goal: " + eGlobalObj.getObjectiveGoal());
		}
		}

		ArithmeticExpressionTransformer<? extends EObject> transformer = transformationFactory
				.createArithmeticTransformer(globalObj);
		globalObj.setExpression(transformer.transform(eGlobalObj.getExpr()));
		// Rewrite the expression, which will be translated into ILP-Terms, into a sum
		// of products.
		globalObj.setExpression(new GipsArithmeticTransformer(factory).normalizeAndExpand(globalObj.getExpression()));
	}

	protected Constraint createConstraint(final GipsConstraint eConstraint, int counter) {
		if (eConstraint.getContext() instanceof GipsMappingContext mapping) {
			MappingConstraint constraint = factory.createMappingConstraint();
			constraint.setName("MappingConstraint" + counter + "On" + mapping.getMapping().getName());
			constraint.setMapping(data.eMapping2Mapping().get(mapping.getMapping()));
			return constraint;
		} else if (eConstraint.getContext() instanceof GipsPatternContext pattern) {
			PatternConstraint constraint = factory.createPatternConstraint();
			constraint.setName("PatternConstraint" + counter + "On" + pattern.getPattern().getName());
			constraint.setPattern(data.getPattern(pattern.getPattern()));
			return constraint;
		} else if (eConstraint.getContext() instanceof GipsTypeContext type) {
			TypeConstraint constraint = factory.createTypeConstraint();
			constraint.setName("TypeConstraint" + counter + "On" + type.getType().getName());
			Type varType = data.getType((EClass) type.getType());
			constraint.setModelType(varType);
			return constraint;
		} else {
			GlobalConstraint constraint = factory.createGlobalConstraint();
			constraint.setName("GlobalConstraint" + counter);
			return constraint;
		}
	}

	protected Constraint createDependencyConstraint(final GipsAnnotatedConstraint eConstraint, int counter) {
		if (eConstraint.input().getContext() instanceof GipsMappingContext mapping) {
			MappingConstraint constraint = factory.createMappingConstraint();
			constraint.setName("DependingMappingConstraint" + counter + "On" + mapping.getMapping().getName());
			constraint.setMapping(data.eMapping2Mapping().get(mapping.getMapping()));
			constraint.setDepending(true);
			return constraint;
		} else if (eConstraint.input().getContext() instanceof GipsPatternContext pattern) {
			PatternConstraint constraint = factory.createPatternConstraint();
			constraint.setName("DependingPatternConstraint" + counter + "On" + pattern.getPattern().getName());
			constraint.setPattern(data.getPattern(pattern.getPattern()));
			constraint.setDepending(true);
			return constraint;
		} else if (eConstraint.input().getContext() instanceof GipsTypeContext type) {
			TypeConstraint constraint = factory.createTypeConstraint();
			constraint.setName("DependingTypeConstraint" + counter + "On" + type.getType().getName());
			Type varType = data.getType((EClass) type.getType());
			constraint.setDepending(true);
			constraint.setModelType(varType);
			return constraint;
		} else {
			GlobalConstraint constraint = factory.createGlobalConstraint();
			constraint.setName("DependingGlobalConstraint" + counter);
			constraint.setDepending(true);
			return constraint;
		}
	}

	protected Objective createObjective(final GipsObjective eObjective) {
		if (eObjective.getContext() instanceof GipsMappingContext mapping) {
			MappingObjective objective = factory.createMappingObjective();
			objective.setName(eObjective.getName());
			objective.setMapping(data.eMapping2Mapping().get(mapping.getMapping()));
			return objective;
		} else if (eObjective.getContext() instanceof GipsPatternContext pattern) {
			PatternObjective constraint = factory.createPatternObjective();
			constraint.setName(eObjective.getName());
			constraint.setPattern(data.getPattern(pattern.getPattern()));
			return constraint;
		} else {
			GipsTypeContext type = (GipsTypeContext) eObjective.getContext();
			TypeObjective objective = factory.createTypeObjective();
			objective.setName(eObjective.getName());
			Type varType = data.getType((EClass) type.getType());
			objective.setModelType(varType);
			return objective;
		}
	}

	protected void mapGT2IBeXElements() {
		Set<EditorPattern> allEditorPatterns = new HashSet<>();
		allEditorPatterns.addAll(data.gipsSlangFile().getPatterns().stream()
				.filter(pattern -> GTEditorPatternUtils.containsCreatedOrDeletedElements(pattern))
				.collect(Collectors.toList()));
		allEditorPatterns.addAll(data.gipsSlangFile().getImportedPattern().stream().map(ip -> ip.getPattern())
				.filter(pattern -> GTEditorPatternUtils.containsCreatedOrDeletedElements(pattern))
				.collect(Collectors.toList()));

		for (EditorPattern ePattern : allEditorPatterns) {
			for (IBeXRule rule : data.model().getIbexModel().getRuleSet().getRules()) {
				if (rule.getName().equals(ePattern.getName())) {
					data.ePattern2Rule().put(ePattern, rule);
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
		allEditorPatterns.addAll(data.gipsSlangFile().getPatterns());
		allEditorPatterns.addAll(data.gipsSlangFile().getImportedPattern().stream().map(ip -> ip.getPattern())
				.collect(Collectors.toList()));

		for (EditorPattern ePattern : allEditorPatterns) {
			for (IBeXContext pattern : data.model().getIbexModel().getPatternSet().getContextPatterns()) {
				if (pattern.getName().equals(ePattern.getName())) {
					data.ePattern2Context().put(ePattern, pattern);

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