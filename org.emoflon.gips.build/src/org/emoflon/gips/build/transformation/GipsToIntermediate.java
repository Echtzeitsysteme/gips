package org.emoflon.gips.build.transformation;

import java.util.Collection;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.build.transformation.helper.ArithmeticExpressionType;
import org.emoflon.gips.build.transformation.helper.GipsTransformationData;
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils;
import org.emoflon.gips.build.transformation.transformer.ArithmeticExpressionTransformer;
import org.emoflon.gips.build.transformation.transformer.RelationalExpressionTransformer;
import org.emoflon.gips.build.transformation.transformer.TransformerFactory;
import org.emoflon.gips.gipsl.gipsl.EditorGTFile;
import org.emoflon.gips.gipsl.gipsl.GipsBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsConfig;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsGlobalObjective;
import org.emoflon.gips.gipsl.gipsl.GipsMappingContext;
import org.emoflon.gips.gipsl.gipsl.GipsObjective;
import org.emoflon.gips.gipsl.gipsl.GipsPatternContext;
import org.emoflon.gips.gipsl.gipsl.GipsRelExpr;
import org.emoflon.gips.gipsl.gipsl.GipsTypeContext;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticValue;
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.Constraint;
import org.emoflon.gips.intermediate.GipsIntermediate.DoubleLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateFactory;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel;
import org.emoflon.gips.intermediate.GipsIntermediate.GlobalConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.GlobalObjective;
import org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig;
import org.emoflon.gips.intermediate.GipsIntermediate.ILPSolverType;
import org.emoflon.gips.intermediate.GipsIntermediate.IntegerLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingObjective;
import org.emoflon.gips.intermediate.GipsIntermediate.Objective;
import org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveTarget;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternObjective;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.SumExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Type;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeObjective;
import org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticOperator;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.utils.GTEditorPatternUtils;
import org.emoflon.ibex.gt.transformations.EditorToIBeXPatternTransformation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;

public class GipsToIntermediate {
	protected GipsIntermediateFactory factory = GipsIntermediateFactory.eINSTANCE;
	final protected GipsTransformationData data;
	final protected TransformerFactory transformationFactory;

	public GipsToIntermediate(final EditorGTFile gipsSlangFile) {
		data = new GipsTransformationData(factory.createGipsIntermediateModel(), gipsSlangFile);
		this.transformationFactory = new TransformerFactory(data);
	}

	public GipsIntermediateModel transform() throws Exception {
		// transform GT to IBeXPatterns
		EditorToIBeXPatternTransformation ibexTransformer = new EditorToIBeXPatternTransformation();
		data.model().setIbexModel(ibexTransformer.transform(data.gipsSlangFile()));
		mapGT2IBeXElements();

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
		default -> {
			throw new IllegalArgumentException("Unsupported solver type: " + eConfig.getSolver());
		}
		}
		config.setSolverHomeDir(eConfig.getHome().replace("\"", ""));
		config.setSolverLicenseFile(eConfig.getLicense().replace("\"", ""));
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

		config.setEnableCustomTolerance(eConfig.isEnableTolernace());
		if (eConfig.isEnableTolernace()) {
			config.setTolerance(eConfig.getTolerance());
		}
		data.model().setConfig(config);
	}

	protected void transformMappings() {
		data.gipsSlangFile().getMappings().forEach(eMapping -> {
			Mapping mapping = factory.createMapping();
			mapping.setName(eMapping.getName());
			mapping.setRule(data.ePattern2Rule().get(eMapping.getPattern()));
			data.model().getVariables().add(mapping);
			data.eMapping2Mapping().put(eMapping, mapping);
		});
	}

	protected void transformConstraints() throws Exception {
		GipsConstraintSplitter splitter = new GipsConstraintSplitter(data);
		int constraintCounter = 0;
		for (GipsConstraint eConstraint : data.gipsSlangFile().getConstraints()) {
			if (eConstraint.getExpr() == null || eConstraint.getExpr().getExpr() == null) {
				continue;
			}
			Collection<GipsConstraint> eConstraints = splitter.split(eConstraint);
			for (GipsConstraint eSubConstraint : eConstraints) {
				// check primitive or impossible expressions
				GipsBoolExpr boolExpr = eSubConstraint.getExpr().getExpr();
				if (boolExpr instanceof GipsBooleanLiteral lit) {
					if (lit.isLiteral()) {
						// Ignore this constraint, since it will always be satisfied
						continue;
					} else {
						// This constraint will never be satisfied, hence this is an impossible to solve
						// problem
						throw new IllegalArgumentException(
								"Optimization problem is impossible to solve: One ore more constraints return false by definition.");
					}
				}

				Constraint constraint = createConstraint(eSubConstraint, constraintCounter);
				constraintCounter++;
				constraint.setElementwise(true);
				data.model().getConstraints().add(constraint);
				data.eConstraint2Constraint().put(eSubConstraint, constraint);

				RelationalExpressionTransformer transformer = transformationFactory
						.createRelationalTransformer(constraint);
				constraint.setExpression(transformer.transform((GipsRelExpr) boolExpr));
				if (GipsTransformationUtils
						.isConstantExpression(constraint.getExpression()) == ArithmeticExpressionType.constant) {
					// Check whether this constraint is constant at ILP problem build time. If true
					// -> return
					constraint.setConstant(true);
					continue;
				}

				boolean isLhsConst = (GipsTransformationUtils
						.isConstantExpression(((RelationalExpression) constraint.getExpression())
								.getLhs()) == ArithmeticExpressionType.constant) ? true : false;
				boolean isRhsConst = (GipsTransformationUtils
						.isConstantExpression(((RelationalExpression) constraint.getExpression())
								.getRhs()) == ArithmeticExpressionType.constant) ? true : false;
				if (!isLhsConst && !isRhsConst) {
					// Fix this malformed constraint by subtracting the rhs from the lhs.
					// E.g.: c: x < y is transformed to c: x - y < 0
					BinaryArithmeticExpression rewrite = factory.createBinaryArithmeticExpression();
					rewrite.setOperator(BinaryArithmeticOperator.SUBTRACT);
					rewrite.setLhs(((RelationalExpression) constraint.getExpression()).getLhs());
					rewrite.setRhs(((RelationalExpression) constraint.getExpression()).getRhs());
					DoubleLiteral lit = factory.createDoubleLiteral();
					lit.setLiteral(0);
					((RelationalExpression) constraint.getExpression()).setLhs(rewrite);
					((RelationalExpression) constraint.getExpression()).setRhs(lit);
				}

				isLhsConst = (GipsTransformationUtils
						.isConstantExpression(((RelationalExpression) constraint.getExpression())
								.getLhs()) == ArithmeticExpressionType.constant) ? true : false;

				// Rewrite the non-constant expression, which will be translated into ILP-Terms,
				// into a sum of products.
				if (isLhsConst) {
					ArithmeticExpression rhs = rewriteToSumOfProducts(
							((RelationalExpression) constraint.getExpression()).getRhs(), null, null);
					((RelationalExpression) constraint.getExpression())
							.setRhs(((RelationalExpression) constraint.getExpression()).getLhs());
					((RelationalExpression) constraint.getExpression()).setLhs(rhs);
					GipsTransformationUtils.flipOperator((RelationalExpression) constraint.getExpression());
				} else {
					((RelationalExpression) constraint.getExpression()).setLhs(rewriteToSumOfProducts(
							((RelationalExpression) constraint.getExpression()).getLhs(), null, null));
				}
				// Move constant terms from the sum of products to the constant side of the
				// relational constraint.
				constraint.setExpression(rewriteMoveConstantTerms((RelationalExpression) constraint.getExpression()));

				// Remove subtractions, e.g.: a - b becomes a + -b
				if (isLhsConst) {
					((RelationalExpression) constraint.getExpression()).setRhs(
							rewriteRemoveSubtractions(((RelationalExpression) constraint.getExpression()).getRhs()));
				} else {
					((RelationalExpression) constraint.getExpression()).setLhs(
							rewriteRemoveSubtractions(((RelationalExpression) constraint.getExpression()).getLhs()));
				}

				// Final check: Was the context used?
				if (!GipsTransformationUtils
						.containsContextExpression(((RelationalExpression) constraint.getExpression()).getRhs())
						&& !GipsTransformationUtils
								.containsContextExpression(((RelationalExpression) constraint.getExpression()).getLhs())
						&& !(constraint instanceof GlobalConstraint)) {
					throw new IllegalArgumentException("Context must be used at least once per non-global constraint.");
				}
			}
		}
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
			objective.setExpression(rewriteToSumOfProducts(objective.getExpression(), null, null));
			// Remove subtractions, e.g.: a - b becomes a + -b
			objective.setExpression(rewriteRemoveSubtractions(objective.getExpression()));
			// Final check: Was the context used?
//			if (!GipsTransformationUtils.containsContextExpression(objective.getExpression())) {
//				throw new IllegalArgumentException("Context must be used at least once per objective.");
//			}
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
		globalObj.setExpression(rewriteToSumOfProducts(globalObj.getExpression(), null, null));
		// Remove subtractions, e.g.: a - b becomes a + -b
		globalObj.setExpression(rewriteRemoveSubtractions(globalObj.getExpression()));
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
		for (EditorPattern ePattern : data.gipsSlangFile().getPatterns().stream()
				.filter(pattern -> GTEditorPatternUtils.containsCreatedOrDeletedElements(pattern))
				.collect(Collectors.toList())) {
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

		for (EditorPattern ePattern : data.gipsSlangFile().getPatterns()) {
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

	protected ArithmeticExpression rewriteToSumOfProducts(final ArithmeticExpression expr,
			final ArithmeticExpression factor, final BinaryArithmeticOperator operator) {
		if (expr instanceof BinaryArithmeticExpression binaryExpr) {
			if (binaryExpr.getOperator() == BinaryArithmeticOperator.ADD
					|| binaryExpr.getOperator() == BinaryArithmeticOperator.SUBTRACT) {
				if (factor == null) {
					ArithmeticExpression newLhs = rewriteToSumOfProducts(binaryExpr.getLhs(), null, null);
					ArithmeticExpression newRhs = rewriteToSumOfProducts(binaryExpr.getRhs(), null, null);
					binaryExpr.setLhs(newLhs);
					binaryExpr.setRhs(newRhs);
					return binaryExpr;
				} else {
					if (GipsTransformationUtils
							.isConstantExpression(binaryExpr.getLhs()) == ArithmeticExpressionType.constant) {
						BinaryArithmeticExpression rewriteLhs = factory.createBinaryArithmeticExpression();
						rewriteLhs.setOperator(operator);
						rewriteLhs.setLhs(binaryExpr.getLhs());
						rewriteLhs.setRhs(factor);
						binaryExpr.setLhs(rewriteLhs);
					} else {
						ArithmeticExpression newLhs = rewriteToSumOfProducts(binaryExpr.getLhs(), factor, operator);
						binaryExpr.setLhs(newLhs);
					}
					if (GipsTransformationUtils
							.isConstantExpression(binaryExpr.getRhs()) == ArithmeticExpressionType.constant) {
						BinaryArithmeticExpression rewriteRhs = factory.createBinaryArithmeticExpression();
						rewriteRhs.setOperator(operator);
						rewriteRhs.setLhs(binaryExpr.getRhs());
						rewriteRhs.setRhs(factor);
						binaryExpr.setRhs(rewriteRhs);
					} else {
						ArithmeticExpression newRhs = rewriteToSumOfProducts(binaryExpr.getRhs(), factor, operator);
						binaryExpr.setRhs(newRhs);
					}
					return binaryExpr;
				}
			} else if (binaryExpr.getOperator() == BinaryArithmeticOperator.DIVIDE
					|| binaryExpr.getOperator() == BinaryArithmeticOperator.MULTIPLY) {
				boolean isLhsConst = switch (GipsTransformationUtils.isConstantExpression(binaryExpr.getLhs())) {
				case constant -> {
					yield true;
				}
				case variableScalar -> {
					yield true;
				}
				case variableValue -> {
					yield false;
				}
				case variableVector -> {
					yield false;
				}
				};
				boolean isRhsConst = switch (GipsTransformationUtils.isConstantExpression(binaryExpr.getRhs())) {
				case constant -> {
					yield true;
				}
				case variableScalar -> {
					yield true;
				}
				case variableValue -> {
					yield false;
				}
				case variableVector -> {
					yield false;
				}
				};

				if (!isLhsConst && !isRhsConst) {
					throw new IllegalArgumentException("A product may not contain more than one mapping expression.");
				}

				if (factor == null) {
					if (isLhsConst) {
						return rewriteToSumOfProducts(binaryExpr.getRhs(), binaryExpr.getLhs(),
								binaryExpr.getOperator());
					} else {
						return rewriteToSumOfProducts(binaryExpr.getLhs(), binaryExpr.getRhs(),
								binaryExpr.getOperator());
					}
				} else {
					if (isLhsConst) {
						BinaryArithmeticExpression rewriteLhs = factory.createBinaryArithmeticExpression();
						rewriteLhs.setOperator(operator);
						rewriteLhs.setLhs(binaryExpr.getLhs());
						rewriteLhs.setRhs(factor);
						return rewriteToSumOfProducts(binaryExpr.getRhs(), rewriteLhs, operator);
					} else {
						BinaryArithmeticExpression rewriteRhs = factory.createBinaryArithmeticExpression();
						rewriteRhs.setOperator(operator);
						rewriteRhs.setLhs(binaryExpr.getRhs());
						rewriteRhs.setRhs(factor);
						return rewriteToSumOfProducts(binaryExpr.getLhs(), rewriteRhs, operator);
					}
				}
			} else {
				// CASE: POW -> It is impossible to refactor exponentials into a sum-product
				ArithmeticExpressionType lhsType = GipsTransformationUtils.isConstantExpression(binaryExpr.getLhs());
				ArithmeticExpressionType rhsType = GipsTransformationUtils.isConstantExpression(binaryExpr.getRhs());
				if (lhsType == ArithmeticExpressionType.variableValue
						|| lhsType == ArithmeticExpressionType.variableVector
						|| rhsType == ArithmeticExpressionType.variableValue
						|| rhsType == ArithmeticExpressionType.variableVector) {
					throw new IllegalArgumentException(
							"An exponential expression must not contain any mapping expressions.");
				}

				if (factor == null) {
					ArithmeticExpression lhsRewrite = rewriteToSumOfProducts(binaryExpr.getLhs(), null, null);
					ArithmeticExpression rhsRewrite = rewriteToSumOfProducts(binaryExpr.getRhs(), null, null);
					binaryExpr.setLhs(lhsRewrite);
					binaryExpr.setRhs(rhsRewrite);
					return binaryExpr;
				} else {
					BinaryArithmeticExpression rewrite = factory.createBinaryArithmeticExpression();
					rewrite.setOperator(operator);
					ArithmeticExpression lhsRewrite = rewriteToSumOfProducts(binaryExpr.getLhs(), null, null);
					ArithmeticExpression rhsRewrite = rewriteToSumOfProducts(binaryExpr.getRhs(), null, null);
					binaryExpr.setLhs(lhsRewrite);
					binaryExpr.setRhs(rhsRewrite);
					rewrite.setLhs(binaryExpr);
					rewrite.setRhs(factor);
					return rewrite;
				}

			}
		} else if (expr instanceof UnaryArithmeticExpression unaryExpr) {
			if (unaryExpr.getOperator() == UnaryArithmeticOperator.BRACKET) {
				boolean isConst = GipsTransformationUtils
						.isConstantExpression(unaryExpr.getExpression()) == ArithmeticExpressionType.constant ? true
								: false;
				if (factor == null) {
					if (isConst) {
						return unaryExpr.getExpression();
					} else {
						return rewriteToSumOfProducts(unaryExpr.getExpression(), null, null);
					}
				} else {
					if (isConst) {
						BinaryArithmeticExpression rewrite = factory.createBinaryArithmeticExpression();
						rewrite.setOperator(operator);
						rewrite.setRhs(factor);
						rewrite.setLhs(unaryExpr.getExpression());
						return rewrite;
					} else {
						return rewriteToSumOfProducts(unaryExpr.getExpression(), factor, operator);
					}

				}
			} else if (unaryExpr.getOperator() == UnaryArithmeticOperator.NEGATE) {
				if (factor == null) {
					DoubleLiteral constNegOne = factory.createDoubleLiteral();
					constNegOne.setLiteral(-1);
					return rewriteToSumOfProducts(unaryExpr.getExpression(), constNegOne,
							BinaryArithmeticOperator.MULTIPLY);
				} else {
					DoubleLiteral constNegOne = factory.createDoubleLiteral();
					constNegOne.setLiteral(-1);
					BinaryArithmeticExpression rewrite = factory.createBinaryArithmeticExpression();
					rewrite.setOperator(BinaryArithmeticOperator.MULTIPLY);
					rewrite.setRhs(constNegOne);
					rewrite.setLhs(unaryExpr.getExpression());
					return rewriteToSumOfProducts(rewrite, factor, operator);
				}
			} else {
				ArithmeticExpressionType expressionType = GipsTransformationUtils
						.isConstantExpression(unaryExpr.getExpression());
				if (expressionType == ArithmeticExpressionType.variableValue
						|| expressionType == ArithmeticExpressionType.variableVector) {
					throw new IllegalArgumentException(
							"Absolute, square-root, sine or cosine expressions must not contain any mapping expressions.");
				}

				if (factor == null) {
					ArithmeticExpression rewrite = rewriteToSumOfProducts(unaryExpr.getExpression(), null, null);
					unaryExpr.setExpression(rewrite);
					return unaryExpr;
				} else {
					BinaryArithmeticExpression rewrite = factory.createBinaryArithmeticExpression();
					rewrite.setOperator(operator);
					ArithmeticExpression innerRewrite = rewriteToSumOfProducts(unaryExpr.getExpression(), null, null);
					unaryExpr.setExpression(innerRewrite);
					rewrite.setLhs(unaryExpr);
					rewrite.setRhs(factor);
					return rewrite;
				}
			}
		} else if (expr instanceof ArithmeticValue valExpr) {
			if (factor == null) {
				return expr;
			} else {
				ArithmeticExpressionType expressionType = GipsTransformationUtils
						.isConstantExpression(valExpr.getValue());
				if (expressionType == ArithmeticExpressionType.constant
						|| expressionType == ArithmeticExpressionType.variableScalar) {
					BinaryArithmeticExpression rewrite = factory.createBinaryArithmeticExpression();
					rewrite.setOperator(operator);
					rewrite.setLhs(valExpr);
					rewrite.setRhs(factor);
					return rewrite;
				} else {
					if (valExpr.getValue() instanceof SumExpression sumExpr) {
						ArithmeticExpression rewrite = rewriteToSumOfProducts(sumExpr.getExpression(), factor,
								operator);
						sumExpr.setExpression(rewrite);
						return valExpr;
					} else {
						BinaryArithmeticExpression rewrite = factory.createBinaryArithmeticExpression();
						rewrite.setOperator(operator);
						rewrite.setLhs(valExpr);
						rewrite.setRhs(factor);
						return rewrite;
					}
				}
			}
		} else {
			// CASE: Literals
			if (factor == null) {
				return expr;
			} else {
				BinaryArithmeticExpression rewrite = factory.createBinaryArithmeticExpression();
				rewrite.setOperator(operator);
				rewrite.setLhs(expr);
				rewrite.setRhs(factor);
				return rewrite;
			}
		}
	}

	protected RelationalExpression rewriteMoveConstantTerms(final RelationalExpression relExpr) {
		boolean isLhsConst = GipsTransformationUtils
				.isConstantExpression(relExpr.getLhs()) == ArithmeticExpressionType.constant ? true : false;

		ArithmeticExpression constExpr;
		ArithmeticExpression variableExpr;
		if (isLhsConst) {
			constExpr = relExpr.getLhs();
			variableExpr = relExpr.getRhs();
		} else {
			constExpr = relExpr.getRhs();
			variableExpr = relExpr.getLhs();
		}

		if (variableExpr instanceof BinaryArithmeticExpression binaryExpr) {
			if (binaryExpr.getOperator() == BinaryArithmeticOperator.ADD) {
				boolean isVarLhsConst = GipsTransformationUtils
						.isConstantExpression(binaryExpr.getLhs()) == ArithmeticExpressionType.constant ? true : false;
				boolean isVarRhsConst = GipsTransformationUtils
						.isConstantExpression(binaryExpr.getRhs()) == ArithmeticExpressionType.constant ? true : false;

				if (!isVarLhsConst && !isVarRhsConst) {
					return relExpr;
				}

				RelationalExpression rewrite = factory.createRelationalExpression();
				rewrite.setOperator(relExpr.getOperator());
				BinaryArithmeticExpression newConst = factory.createBinaryArithmeticExpression();
				rewrite.setLhs(newConst);

				newConst.setOperator(BinaryArithmeticOperator.SUBTRACT);
				newConst.setLhs(constExpr);

				if (isVarLhsConst) {
					newConst.setRhs(binaryExpr.getLhs());
					rewrite.setRhs(binaryExpr.getRhs());
				} else {
					newConst.setRhs(binaryExpr.getRhs());
					rewrite.setRhs(binaryExpr.getLhs());
				}
				return rewriteMoveConstantTerms(rewrite);
			} else if (binaryExpr.getOperator() == BinaryArithmeticOperator.SUBTRACT) {
				boolean isVarLhsConst = GipsTransformationUtils
						.isConstantExpression(binaryExpr.getLhs()) == ArithmeticExpressionType.constant ? true : false;
				boolean isVarRhsConst = GipsTransformationUtils
						.isConstantExpression(binaryExpr.getRhs()) == ArithmeticExpressionType.constant ? true : false;

				if (!isVarLhsConst && !isVarRhsConst) {
					return relExpr;
				}

				RelationalExpression rewrite = factory.createRelationalExpression();
				rewrite.setOperator(relExpr.getOperator());
				BinaryArithmeticExpression newConst = factory.createBinaryArithmeticExpression();
				rewrite.setLhs(newConst);
				newConst.setLhs(constExpr);

				if (isVarLhsConst) {
					newConst.setOperator(BinaryArithmeticOperator.SUBTRACT);
					newConst.setRhs(binaryExpr.getLhs());
					ArithmeticExpression invertedRhs = invertSign(binaryExpr.getRhs());
					rewrite.setRhs(invertedRhs);
				} else {
					newConst.setOperator(BinaryArithmeticOperator.ADD);
					newConst.setRhs(binaryExpr.getRhs());
					rewrite.setRhs(binaryExpr.getLhs());
				}
				return rewriteMoveConstantTerms(rewrite);
			} else {
				return relExpr;
			}
		} else if (variableExpr instanceof UnaryArithmeticExpression unaryExpr) {
			return relExpr;
		} else if (variableExpr instanceof ArithmeticValue valExpr) {
			return relExpr;
		} else {
			return relExpr;
		}
	}

	protected ArithmeticExpression rewriteRemoveSubtractions(final ArithmeticExpression expr) {
		if (expr instanceof BinaryArithmeticExpression binaryExpr) {
			if (binaryExpr.getOperator() == BinaryArithmeticOperator.SUBTRACT) {
				ArithmeticExpression rewriteRHS = invertSign(binaryExpr.getRhs());
				rewriteRHS = rewriteRemoveSubtractions(rewriteRHS);
				ArithmeticExpression rewriteLHS = rewriteRemoveSubtractions(binaryExpr.getLhs());
				BinaryArithmeticExpression rewrite = factory.createBinaryArithmeticExpression();
				rewrite.setOperator(BinaryArithmeticOperator.ADD);
				rewrite.setLhs(rewriteLHS);
				rewrite.setRhs(rewriteRHS);
				return rewrite;
			} else {
				ArithmeticExpression rewriteLHS = rewriteRemoveSubtractions(binaryExpr.getLhs());
				ArithmeticExpression rewriteRHS = rewriteRemoveSubtractions(binaryExpr.getRhs());
				binaryExpr.setLhs(rewriteLHS);
				binaryExpr.setRhs(rewriteRHS);
				return binaryExpr;
			}
		} else if (expr instanceof UnaryArithmeticExpression unaryExpr) {
			ArithmeticExpression rewrite = rewriteRemoveSubtractions(unaryExpr.getExpression());
			unaryExpr.setExpression(rewrite);
			return unaryExpr;
		} else if (expr instanceof ArithmeticValue valExpr) {
			if (valExpr.getValue() instanceof SumExpression sumExpr) {
				ArithmeticExpression rewrite = rewriteRemoveSubtractions(sumExpr.getExpression());
				sumExpr.setExpression(rewrite);
				return valExpr;
			} else {
				return valExpr;
			}
		} else {
			return expr;
		}
	}

	protected ArithmeticExpression invertSign(final ArithmeticExpression expr) {
		if (expr instanceof BinaryArithmeticExpression binaryExpr) {
			if (binaryExpr.getOperator() == BinaryArithmeticOperator.ADD) {
				binaryExpr.setLhs(invertSign(binaryExpr.getLhs()));
				binaryExpr.setOperator(BinaryArithmeticOperator.SUBTRACT);
				return binaryExpr;
			} else if (binaryExpr.getOperator() == BinaryArithmeticOperator.SUBTRACT) {
				binaryExpr.setLhs(invertSign(binaryExpr.getLhs()));
				binaryExpr.setOperator(BinaryArithmeticOperator.ADD);
				return binaryExpr;
			} else {
				binaryExpr.setLhs(invertSign(binaryExpr.getLhs()));
				return binaryExpr;
			}
		} else if (expr instanceof UnaryArithmeticExpression unaryExpr) {
			if (unaryExpr.getOperator() == UnaryArithmeticOperator.NEGATE) {
				return unaryExpr.getExpression();
			} else if (unaryExpr.getOperator() == UnaryArithmeticOperator.BRACKET) {
				return invertSign(unaryExpr.getExpression());
			} else {
				BinaryArithmeticExpression rewrite = factory.createBinaryArithmeticExpression();
				rewrite.setOperator(BinaryArithmeticOperator.MULTIPLY);
				DoubleLiteral constNegOne = factory.createDoubleLiteral();
				constNegOne.setLiteral(-1);
				rewrite.setLhs(constNegOne);
				rewrite.setRhs(unaryExpr);
				return rewrite;
			}
		} else if (expr instanceof ArithmeticValue valExpr) {
			if (valExpr.getValue() instanceof SumExpression sumExpr) {
				sumExpr.setExpression(invertSign(sumExpr.getExpression()));
				return valExpr;
			} else {
				BinaryArithmeticExpression rewrite = factory.createBinaryArithmeticExpression();
				DoubleLiteral constNegOne = factory.createDoubleLiteral();
				constNegOne.setLiteral(-1);
				rewrite.setOperator(BinaryArithmeticOperator.MULTIPLY);
				rewrite.setLhs(constNegOne);
				rewrite.setRhs(valExpr);
				return rewrite;
			}
		} else if (expr instanceof IntegerLiteral lit) {
			lit.setLiteral(-lit.getLiteral());
			return lit;
		} else {
			DoubleLiteral lit = (DoubleLiteral) expr;
			lit.setLiteral(-lit.getLiteral());
			return lit;
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
