package org.emoflon.gips.build.transformation;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
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
import org.emoflon.gips.intermediate.GipsIntermediate.GTMapping;
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
import org.emoflon.gips.intermediate.GipsIntermediate.PatternMapping;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternObjective;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.SumExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Type;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeObjective;
import org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.Variable;
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference;
import org.emoflon.gips.intermediate.GipsIntermediate.VariableType;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.utils.GTEditorPatternUtils;
import org.emoflon.ibex.gt.transformations.EditorToIBeXPatternTransformation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;
import org.logicng.formulas.Formula;
import org.logicng.formulas.Literal;

public class GipsToIntermediate {
	final public static double EPSILON = 0.000001d;

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
			}

			mapping.setName(eMapping.getName());
			data.model().getVariables().add(mapping);
			data.eMapping2Mapping().put(eMapping, mapping);
		});
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
				case CONJUCTION_OF_LITERALS -> {
					Constraint dConstraint = createDependencyConstraint(eSubConstraint, constraintCounter);
					data.model().getConstraints().add(dConstraint);
					constraintCounter++;

					Map<GipsConstraint, Constraint> transformed = new HashMap<>();
					Map<GipsConstraint, Variable> constraint2real = new HashMap<>();
					Map<GipsConstraint, Variable> constraint2binary = new HashMap<>();
					for (GipsConstraint subConstraint : eSubConstraint.result().values()) {
						Constraint constraint = transformConstraint(subConstraint);
						data.model().getConstraints().add(constraint);
						transformed.put(subConstraint, constraint);
						dConstraint.getDependencies().add(constraint);

						// TODO: We'll ignore build time constant constraints for now -> This must be
						// fixed at some point in time.
						if (constraint.isConstant()) {
							throw new UnsupportedOperationException(
									"Negation for constraints that are constant at build time is currently not supported");
						}

						if (!(constraint.getExpression() instanceof RelationalExpression)) {
							throw new UnsupportedOperationException(
									"Negation for constraints that are constant at build time is currently not supported");
						}

						// Add substitute variable to relation
						RelationalExpression orginalRelation = (RelationalExpression) constraint.getExpression();
						Variable realVar = factory.createVariable();
						realVar.setType(VariableType.REAL);
						realVar.setName(constraint.getName() + "_substitute_variable");
						data.model().getVariables().add(realVar);
						dConstraint.getHelperVariables().add(realVar);
						constraint2real.put(subConstraint, realVar);
						boolean realVarNegative = insertSubstituteRealVariable(constraint, orginalRelation, realVar);
						// Fix relation s.t. the variable side does not contain constant expressions
						constraint.setExpression(
								rewriteMoveConstantTerms((RelationalExpression) constraint.getExpression()));

						Variable binaryVar = factory.createVariable();
						binaryVar.setType(VariableType.BINARY);
						binaryVar.setName(constraint.getName() + "_symbolic_variable");
						data.model().getVariables().add(binaryVar);
						dConstraint.getHelperVariables().add(binaryVar);
						constraint2binary.put(subConstraint, binaryVar);

						// Add semantic correctness constraints
						// (1) Force symbolic variable correctness
						insertBinaryVaribaleCorrectnessConstraint(dConstraint, realVar, binaryVar, realVarNegative);
						// (2) Force substitute variable correctness (via quasi-sos1 constraint)
						Variable sos1var = insertRealVaribaleCorrectnessConstraint(dConstraint, constraint, realVar,
								binaryVar, realVarNegative);
						dConstraint.getHelperVariables().add(sos1var);
					}

					// Create substitute relational constraint
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
						Formula subformula = subformulas.poll();
						GipsConstraint splitConstraint = eSubConstraint.result().get(subformula);
						Variable symbolicVar = constraint2binary.get(splitConstraint);
						VariableReference varRef = factory.createVariableReference();
						varRef.setVariable(symbolicVar);
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

					// Move constant terms from the non-constant to the constant side
					dConstraint.setExpression(
							rewriteMoveConstantTerms((RelationalExpression) dConstraint.getExpression()));
				}
				case LITERAL -> {
					transformConstraint(eSubConstraint.result().values().iterator().next());
				}
				case NEGATED_LITERAL -> {
					Constraint dConstraint = createDependencyConstraint(eSubConstraint, constraintCounter);
					data.model().getConstraints().add(dConstraint);
					constraintCounter++;

					Constraint constraint = transformConstraint(eSubConstraint.result().values().iterator().next());
					// TODO: We'll ignore build time constant constraints for now -> This must be
					// fixed at some point in time.
					if (constraint.isConstant()) {
						throw new UnsupportedOperationException(
								"Negation for constraints that are constant at build time is currently not supported");
					}

					if (!(constraint.getExpression() instanceof RelationalExpression)) {
						throw new UnsupportedOperationException(
								"Negation for constraints that are constant at build time is currently not supported");
					}

					data.model().getConstraints().add(constraint);
					dConstraint.getDependencies().add(constraint);

					// Add substitute variable to produce negation!
					RelationalExpression orginalRelation = (RelationalExpression) constraint.getExpression();
					Variable realVar = factory.createVariable();
					realVar.setType(VariableType.REAL);
					realVar.setName(constraint.getName() + "_substitute_variable");
					data.model().getVariables().add(realVar);
					dConstraint.getHelperVariables().add(realVar);
					boolean realVarNegative = insertSubstituteRealVariable(constraint, orginalRelation, realVar);

					// Add negation constraint
					Variable binaryVar = insertNegationConstraint(dConstraint, constraint);
					dConstraint.getHelperVariables().add(binaryVar);
					// Move constant terms from the non-constant to the constant side
					dConstraint.setExpression(
							rewriteMoveConstantTerms((RelationalExpression) dConstraint.getExpression()));

					// Add semantic correctness constraints
					// (1) Force symbolic variable correctness
					insertBinaryVaribaleCorrectnessConstraint(dConstraint, realVar, binaryVar, realVarNegative);
					// (2) Force substitute variable correctness (via quasi-sos1 constraint)
					Variable sos1Var = insertRealVaribaleCorrectnessConstraint(dConstraint, constraint, realVar,
							binaryVar, realVarNegative);
					dConstraint.getHelperVariables().add(sos1Var);
				}
				default -> {
					throw new IllegalArgumentException("Unknown constraint annotation type.");
				}

				}

			}
		}
	}

	protected boolean insertSubstituteRealVariable(final Constraint constraint,
			final RelationalExpression originalRelation, final Variable realVar) {
		boolean varNegativeReal = false;
		ArithmeticExpression varSide = null;
		boolean leftIsConst = true;

		if (GipsTransformationUtils
				.isConstantExpression(originalRelation.getLhs()) == ArithmeticExpressionType.constant) {
			// RHS -> contains variable
			varSide = originalRelation.getRhs();
			if (originalRelation.getOperator() == RelationalOperator.LESS
					|| originalRelation.getOperator() == RelationalOperator.LESS_OR_EQUAL
					|| originalRelation.getOperator() == RelationalOperator.EQUAL
					|| originalRelation.getOperator() == RelationalOperator.NOT_EQUAL) {
				varNegativeReal = false;
			} else {
				varNegativeReal = true;
			}
		} else {
			// LHS -> constains variable
			varSide = originalRelation.getLhs();
			if (originalRelation.getOperator() == RelationalOperator.LESS
					|| originalRelation.getOperator() == RelationalOperator.LESS_OR_EQUAL
					|| originalRelation.getOperator() == RelationalOperator.EQUAL
					|| originalRelation.getOperator() == RelationalOperator.NOT_EQUAL) {
				varNegativeReal = true;
			} else {
				varNegativeReal = false;
			}
			leftIsConst = false;
		}

		BinaryArithmeticExpression sum = factory.createBinaryArithmeticExpression();
		sum.setOperator(BinaryArithmeticOperator.ADD);
		sum.setLhs(varSide);
		VariableReference realVarRef = factory.createVariableReference();
		realVarRef.setVariable(realVar);
		sum.setRhs(realVarRef);
		if (leftIsConst) {
			originalRelation.setRhs(sum);
		} else {
			originalRelation.setLhs(sum);
		}

		return varNegativeReal;
	}

	protected Variable insertNegationConstraint(final Constraint dConstraint, final Constraint constraint) {
		RelationalExpression negationRelation = factory.createRelationalExpression();
		negationRelation.setOperator(RelationalOperator.GREATER_OR_EQUAL);
		DoubleLiteral dl = factory.createDoubleLiteral();
		dl.setLiteral(1.0);
		negationRelation.setRhs(dl);
		BinaryArithmeticExpression sum = factory.createBinaryArithmeticExpression();
		sum.setOperator(BinaryArithmeticOperator.ADD);
		DoubleLiteral dl2 = factory.createDoubleLiteral();
		dl2.setLiteral(1.0);
		sum.setLhs(dl2);
		BinaryArithmeticExpression prod = factory.createBinaryArithmeticExpression();
		prod.setOperator(BinaryArithmeticOperator.MULTIPLY);
		DoubleLiteral dl3 = factory.createDoubleLiteral();
		dl3.setLiteral(-1.0);
		prod.setLhs(dl3);
		Variable binaryVar = factory.createVariable();
		data.model().getVariables().add(binaryVar);
		binaryVar.setType(VariableType.BINARY);
		binaryVar.setName(constraint.getName() + "_symbolic_variable");
		VariableReference binaryVarRef = factory.createVariableReference();
		binaryVarRef.setVariable(binaryVar);
		prod.setRhs(binaryVarRef);
		sum.setRhs(prod);
		negationRelation.setLhs(sum);
		dConstraint.setExpression(negationRelation);

		return binaryVar;
	}

	protected void insertBinaryVaribaleCorrectnessConstraint(final Constraint dConstraint, final Variable realVar,
			final Variable binaryVar, boolean negativeReal) {
		RelationalExpression relation = factory.createRelationalExpression();
		relation.setOperator(RelationalOperator.GREATER_OR_EQUAL);
		DoubleLiteral dl = factory.createDoubleLiteral();
		if (negativeReal) {
			dl.setLiteral(-EPSILON);
		} else {
			dl.setLiteral(EPSILON);
		}
		relation.setRhs(dl);

		VariableReference realVarRef = factory.createVariableReference();
		realVarRef.setVariable(realVar);
		VariableReference binaryVarRef = factory.createVariableReference();
		binaryVarRef.setVariable(binaryVar);

		BinaryArithmeticExpression sum = factory.createBinaryArithmeticExpression();
		sum.setOperator(BinaryArithmeticOperator.ADD);
		if (negativeReal) {
			BinaryArithmeticExpression prod = factory.createBinaryArithmeticExpression();
			prod.setOperator(BinaryArithmeticOperator.MULTIPLY);
			DoubleLiteral dl2 = factory.createDoubleLiteral();
			dl2.setLiteral(-1.0);
			prod.setLhs(dl2);
			prod.setRhs(binaryVarRef);
			sum.setLhs(prod);
		} else {
			sum.setLhs(binaryVarRef);
		}
		sum.setRhs(realVarRef);
		relation.setLhs(sum);
		dConstraint.getBinaryVarCorrectnessConstraints().add(relation);
	}

	protected Variable insertRealVaribaleCorrectnessConstraint(final Constraint dConstraint,
			final Constraint constraint, final Variable realVar, final Variable binaryVar, boolean negativeReal) {
		Variable symbolicSos1Var = factory.createVariable();
		symbolicSos1Var.setType(VariableType.BINARY);
		symbolicSos1Var.setName(constraint.getName() + "_symbolic_sos1_variable");
		data.model().getVariables().add(symbolicSos1Var);

		// Part 1. of the quasi-sos1 constraint
		RelationalExpression relation = factory.createRelationalExpression();
		relation.setOperator(RelationalOperator.GREATER_OR_EQUAL);
		DoubleLiteral dl = factory.createDoubleLiteral();
		if (negativeReal) {
			dl.setLiteral(-EPSILON);
		} else {
			dl.setLiteral(EPSILON);
		}
		relation.setRhs(dl);

		BinaryArithmeticExpression sum = factory.createBinaryArithmeticExpression();
		sum.setOperator(BinaryArithmeticOperator.ADD);

		BinaryArithmeticExpression prod1 = factory.createBinaryArithmeticExpression();
		prod1.setOperator(BinaryArithmeticOperator.MULTIPLY);
		DoubleLiteral d2 = factory.createDoubleLiteral();
		if (negativeReal) {
			d2.setLiteral(-Double.MAX_VALUE);
		} else {
			d2.setLiteral(Double.MAX_VALUE);
		}
		prod1.setLhs(d2);
		VariableReference symbolicVarRef = factory.createVariableReference();
		symbolicVarRef.setVariable(symbolicSos1Var);
		prod1.setRhs(symbolicVarRef);
		sum.setLhs(prod1);

		BinaryArithmeticExpression prod2 = factory.createBinaryArithmeticExpression();
		prod2.setOperator(BinaryArithmeticOperator.MULTIPLY);
		DoubleLiteral d3 = factory.createDoubleLiteral();
		d3.setLiteral(-1.0);
		prod2.setLhs(d3);
		VariableReference realVarRef = factory.createVariableReference();
		realVarRef.setVariable(realVar);
		prod2.setRhs(realVarRef);
		sum.setRhs(prod2);
		relation.setLhs(sum);
		dConstraint.getRealVarCorrectnessConstraints().add(relation);

		// Part 2. of the quasi-sos1 constraint
		RelationalExpression sos1Relation = factory.createRelationalExpression();
		sos1Relation.setOperator(RelationalOperator.EQUAL);
		DoubleLiteral d4 = factory.createDoubleLiteral();
		d4.setLiteral(1.0);
		sos1Relation.setRhs(d4);

		BinaryArithmeticExpression sum2 = factory.createBinaryArithmeticExpression();
		sum2.setOperator(BinaryArithmeticOperator.ADD);

		VariableReference symbolicVarRef2 = factory.createVariableReference();
		symbolicVarRef2.setVariable(symbolicSos1Var);
		sum2.setLhs(symbolicVarRef2);

		VariableReference binaryVarRef = factory.createVariableReference();
		binaryVarRef.setVariable(binaryVar);
		sum2.setRhs(binaryVarRef);

		sos1Relation.setLhs(sum2);
		dConstraint.getRealVarCorrectnessConstraints().add(sos1Relation);

		return symbolicSos1Var;
	}

	protected Constraint transformConstraint(final GipsConstraint subConstraint) throws Exception {
		// check primitive or impossible expressions
		GipsBoolExpr boolExpr = subConstraint.getExpr().getExpr();
		if (boolExpr instanceof GipsBooleanLiteral lit) {
			if (lit.isLiteral()) {
				// Ignore this constraint, since it will always be satisfied
				return null;
			} else {
				// This constraint will never be satisfied, hence this is an impossible to solve
				// problem
				throw new IllegalArgumentException(
						"Optimization problem is impossible to solve: One ore more constraints return false by definition.");
			}
		}

		Constraint constraint = createConstraint(subConstraint, constraintCounter);
		constraintCounter++;
		constraint.setDepending(false);
		data.model().getConstraints().add(constraint);
		data.eConstraint2Constraint().put(subConstraint, constraint);

		RelationalExpressionTransformer transformer = transformationFactory.createRelationalTransformer(constraint);
		constraint.setExpression(transformer.transform((GipsRelExpr) boolExpr));
		if (GipsTransformationUtils
				.isConstantExpression(constraint.getExpression()) == ArithmeticExpressionType.constant) {
			// Check whether this constraint is constant at ILP problem build time. If true
			// -> return
			constraint.setConstant(true);
			return constraint;
		}

		boolean isLhsConst = (GipsTransformationUtils.isConstantExpression(
				((RelationalExpression) constraint.getExpression()).getLhs()) == ArithmeticExpressionType.constant)
						? true
						: false;
		boolean isRhsConst = (GipsTransformationUtils.isConstantExpression(
				((RelationalExpression) constraint.getExpression()).getRhs()) == ArithmeticExpressionType.constant)
						? true
						: false;
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

		isLhsConst = (GipsTransformationUtils.isConstantExpression(
				((RelationalExpression) constraint.getExpression()).getLhs()) == ArithmeticExpressionType.constant)
						? true
						: false;

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
			((RelationalExpression) constraint.getExpression()).setLhs(
					rewriteToSumOfProducts(((RelationalExpression) constraint.getExpression()).getLhs(), null, null));
		}
		// Move constant terms from the sum of products to the constant side of the
		// relational constraint.
		constraint.setExpression(rewriteMoveConstantTerms((RelationalExpression) constraint.getExpression()));

		// Remove subtractions, e.g.: a - b becomes a + -b
		if (isLhsConst) {
			((RelationalExpression) constraint.getExpression())
					.setRhs(rewriteRemoveSubtractions(((RelationalExpression) constraint.getExpression()).getRhs()));
		} else {
			((RelationalExpression) constraint.getExpression())
					.setLhs(rewriteRemoveSubtractions(((RelationalExpression) constraint.getExpression()).getLhs()));
		}

		// Final check: Was the context used?
		if (!GipsTransformationUtils
				.containsContextExpression(((RelationalExpression) constraint.getExpression()).getRhs())
				&& !GipsTransformationUtils
						.containsContextExpression(((RelationalExpression) constraint.getExpression()).getLhs())
				&& !(constraint instanceof GlobalConstraint)) {
			throw new IllegalArgumentException("Context must be used at least once per non-global constraint.");
		}

		return constraint;
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
