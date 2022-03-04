package org.emoflon.roam.build.transformation;

import java.util.Collection;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.utils.GTEditorPatternUtils;
import org.emoflon.ibex.gt.transformations.EditorToIBeXPatternTransformation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;
import org.emoflon.roam.build.transformation.helper.ArithmeticExpressionType;
import org.emoflon.roam.build.transformation.helper.RoamTransformationData;
import org.emoflon.roam.build.transformation.helper.RoamTransformationUtils;
import org.emoflon.roam.build.transformation.transformer.ArithmeticExpressionTransformer;
import org.emoflon.roam.build.transformation.transformer.RelationalExpressionTransformer;
import org.emoflon.roam.build.transformation.transformer.TransformerFactory;
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticValue;
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticOperator;
import org.emoflon.roam.intermediate.RoamIntermediate.Constraint;
import org.emoflon.roam.intermediate.RoamIntermediate.DoubleLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.GlobalObjective;
import org.emoflon.roam.intermediate.RoamIntermediate.IntegerLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingConstraint;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingObjective;
import org.emoflon.roam.intermediate.RoamIntermediate.Objective;
import org.emoflon.roam.intermediate.RoamIntermediate.ObjectiveTarget;
import org.emoflon.roam.intermediate.RoamIntermediate.PatternConstraint;
import org.emoflon.roam.intermediate.RoamIntermediate.PatternObjective;
import org.emoflon.roam.intermediate.RoamIntermediate.RelationalExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateFactory;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel;
import org.emoflon.roam.intermediate.RoamIntermediate.SumExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.Type;
import org.emoflon.roam.intermediate.RoamIntermediate.TypeConstraint;
import org.emoflon.roam.intermediate.RoamIntermediate.TypeObjective;
import org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticOperator;
import org.emoflon.roam.roamslang.roamSLang.EditorGTFile;
import org.emoflon.roam.roamslang.roamSLang.RoamBoolExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamBooleanLiteral;
import org.emoflon.roam.roamslang.roamSLang.RoamConstraint;
import org.emoflon.roam.roamslang.roamSLang.RoamGlobalObjective;
import org.emoflon.roam.roamslang.roamSLang.RoamMappingContext;
import org.emoflon.roam.roamslang.roamSLang.RoamMatchContext;
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
		// transform GT to IBeXPatterns
		EditorToIBeXPatternTransformation ibexTransformer = new EditorToIBeXPatternTransformation();
		data.model().setIbexModel(ibexTransformer.transform(data.roamSlangFile()));
		mapGT2IBeXElements();

		// transform Roam components
		transformMappings();
		transformConstraints();
		transformObjectives();
		transformGlobalObjective();

		// add all required data types
		data.model().getVariables().addAll(data.eType2Type().values());
		data.model().getVariables().addAll(data.ePattern2Pattern().values());
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
		for (RoamConstraint eConstraint : data.roamSlangFile().getConstraints()) {
			if (eConstraint.getExpr() == null || eConstraint.getExpr().getExpr() == null) {
				continue;
			}
			Collection<RoamConstraint> eConstraints = splitter.split(eConstraint);
			for (RoamConstraint eSubConstraint : eConstraints) {
				// check primitive or impossible expressions
				RoamBoolExpr boolExpr = eSubConstraint.getExpr().getExpr();
				if (boolExpr instanceof RoamBooleanLiteral lit) {
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
				// TODO: For now we'll generate a constraint for each context element. In the
				// future, this should be optimized.
				constraint.setElementwise(true);
				data.model().getConstraints().add(constraint);
				data.eConstraint2Constraint().put(eSubConstraint, constraint);

				RelationalExpressionTransformer transformer = transformationFactory
						.createRelationalTransformer(constraint);
				constraint.setExpression(transformer.transform((RoamRelExpr) boolExpr));
				if (RoamTransformationUtils.isConstantExpression(constraint.getExpression()) == ArithmeticExpressionType.constant)
					throw new UnsupportedOperationException(
							"Expressions that can be evaluated statically at ILP problem build time are currently not allowed.");
				
				boolean isLhsConst = (RoamTransformationUtils
						.isConstantExpression(constraint.getExpression().getLhs()) == ArithmeticExpressionType.constant) ? true : false;
				boolean isRhsConst = (RoamTransformationUtils
						.isConstantExpression(constraint.getExpression().getRhs()) == ArithmeticExpressionType.constant) ? true : false;
				if (!isLhsConst && !isRhsConst) {
					// Fix this malformed constraint by subtracting the lhs from the rhs.
					// E.g.: c: x < y is transformed to  c: 0 < y - x
					BinaryArithmeticExpression rewrite = factory.createBinaryArithmeticExpression();
					rewrite.setOperator(BinaryArithmeticOperator.SUBTRACT);
					rewrite.setLhs(constraint.getExpression().getRhs());
					rewrite.setRhs(constraint.getExpression().getLhs());
					DoubleLiteral lit = factory.createDoubleLiteral();
					lit.setLiteral(0);
					constraint.getExpression().setLhs(lit);
					constraint.getExpression().setRhs(rewrite);
				}
				
				isLhsConst = (RoamTransformationUtils
						.isConstantExpression(constraint.getExpression().getLhs()) == ArithmeticExpressionType.constant) ? true : false;
				
				//Rewrite the non-constant expression, which will be translated into ILP-Terms, into a sum of products.
				if(isLhsConst) {
					constraint.getExpression().setRhs(rewriteToSumOfProducts(constraint.getExpression().getRhs(), null, null));
				} else {
					constraint.getExpression().setLhs(rewriteToSumOfProducts(constraint.getExpression().getLhs(), null, null));
				}
				// Move constant terms from the sum of products to the constant side of the relational constraint.
				constraint.setExpression(rewriteMoveConstantTerms(constraint.getExpression()));
				
				// Remove subtractions, e.g.: a - b becomes a + -b
				if(isLhsConst) {
					constraint.getExpression().setRhs(rewriteRemoveSubtractions(constraint.getExpression().getRhs()));
				} else {
					constraint.getExpression().setLhs(rewriteRemoveSubtractions(constraint.getExpression().getLhs()));
				}
				
				// Final check: Was the context used?
				if(!RoamTransformationUtils.containsContextExpression(constraint.getExpression().getRhs()) 
						&& !RoamTransformationUtils.containsContextExpression(constraint.getExpression().getLhs())) {
					throw new IllegalArgumentException(
							"Context must be used at least once per constraint.");
				}
			}
		}
	}

	protected void transformObjectives() throws Exception {
		for (RoamObjective eObjective : data.roamSlangFile().getObjectives()) {
			if (eObjective.getExpr() == null) {
				continue;
			}

			Objective objective = createObjective(eObjective);
			objective.setElementwise(true);
			data.model().getObjectives().add(objective);
			data.eObjective2Objective().put(eObjective, objective);

			ArithmeticExpressionTransformer<? extends EObject> transformer = transformationFactory.createArithmeticTransformer(objective);
			objective.setExpression(transformer.transform(eObjective.getExpr()));
			//Rewrite the expression, which will be translated into ILP-Terms, into a sum of products.
			objective.setExpression(rewriteToSumOfProducts(objective.getExpression(), null, null));
			// Remove subtractions, e.g.: a - b becomes a + -b
			objective.setExpression(rewriteRemoveSubtractions(objective.getExpression()));
			// Final check: Was the context used?
			if(!RoamTransformationUtils.containsContextExpression(objective.getExpression())) {
				throw new IllegalArgumentException(
						"Context must be used at least once per objective.");
			}
		}
	}

	protected void transformGlobalObjective() throws Exception {
		RoamGlobalObjective eGlobalObj = data.roamSlangFile().getGlobalObjective();
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

		ArithmeticExpressionTransformer<? extends EObject> transformer = transformationFactory.createArithmeticTransformer(globalObj);
		globalObj.setExpression(transformer.transform(eGlobalObj.getExpr()));
		//Rewrite the expression, which will be translated into ILP-Terms, into a sum of products.
		globalObj.setExpression(rewriteToSumOfProducts(globalObj.getExpression(), null, null));
		// Remove subtractions, e.g.: a - b becomes a + -b
		globalObj.setExpression(rewriteRemoveSubtractions(globalObj.getExpression()));
	}

	protected Constraint createConstraint(final RoamConstraint eConstraint, int counter) {
		if (eConstraint.getContext() instanceof RoamMappingContext mapping) {
			MappingConstraint constraint = factory.createMappingConstraint();
			constraint.setName("MappingConstraint" + counter + "On" + mapping.getMapping().getName());
			constraint.setMapping(data.eMapping2Mapping().get(mapping.getMapping()));
			return constraint;
		} else if(eConstraint.getContext() instanceof RoamMatchContext pattern) {
			PatternConstraint constraint = factory.createPatternConstraint();
			constraint.setName("PatternConstraint" + counter + "On" + pattern.getPattern().getName());
			constraint.setPattern(data.getPattern(pattern.getPattern()));
			return constraint;
		} else {
			RoamTypeContext type = (RoamTypeContext) eConstraint.getContext();
			TypeConstraint constraint = factory.createTypeConstraint();
			constraint.setName("TypeConstraint" + counter + "On" + type.getType().getName());
			Type varType = data.getType((EClass) type.getType());
			constraint.setModelType(varType);
			return constraint;
		}
	}

	protected Objective createObjective(final RoamObjective eObjective) {
		if (eObjective.getContext() instanceof RoamMappingContext mapping) {
			MappingObjective objective = factory.createMappingObjective();
			objective.setName(eObjective.getName());
			objective.setMapping(data.eMapping2Mapping().get(mapping.getMapping()));
			return objective;
		} else if(eObjective.getContext() instanceof RoamMatchContext pattern) {
			PatternObjective constraint = factory.createPatternObjective();
			constraint.setName(eObjective.getName());
			constraint.setPattern(data.getPattern(pattern.getPattern()));
			return constraint;
		} else {
			RoamTypeContext type = (RoamTypeContext) eObjective.getContext();
			TypeObjective objective = factory.createTypeObjective();
			objective.setName(eObjective.getName());
			Type varType = data.getType((EClass) type.getType());
			objective.setModelType(varType);
			return objective;
		}
	}

	protected void mapGT2IBeXElements() {
		for (EditorPattern ePattern : data.roamSlangFile().getPatterns().stream()
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
		
		for (EditorPattern ePattern : data.roamSlangFile().getPatterns()) {
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
	
	protected ArithmeticExpression rewriteToSumOfProducts(final ArithmeticExpression expr, final ArithmeticExpression factor, final BinaryArithmeticOperator operator) {
		if(expr instanceof BinaryArithmeticExpression binaryExpr) {
			if(binaryExpr.getOperator() == BinaryArithmeticOperator.ADD || binaryExpr.getOperator() == BinaryArithmeticOperator.SUBTRACT) {
				if(factor == null) {
					ArithmeticExpression newLhs = rewriteToSumOfProducts(binaryExpr.getLhs(), null, null);
					ArithmeticExpression newRhs = rewriteToSumOfProducts(binaryExpr.getRhs(), null, null);
					binaryExpr.setLhs(newLhs);
					binaryExpr.setRhs(newRhs);
					return binaryExpr;
				} else {
					if(RoamTransformationUtils.isConstantExpression(binaryExpr.getLhs()) == ArithmeticExpressionType.constant) {
						BinaryArithmeticExpression rewriteLhs = factory.createBinaryArithmeticExpression();
						rewriteLhs.setOperator(operator);
						rewriteLhs.setLhs(binaryExpr.getLhs());
						rewriteLhs.setRhs(factor);
						binaryExpr.setLhs(rewriteLhs);
					} else {
						ArithmeticExpression newLhs = rewriteToSumOfProducts(binaryExpr.getLhs(), factor, operator);
						binaryExpr.setLhs(newLhs);
					}
					if(RoamTransformationUtils.isConstantExpression(binaryExpr.getRhs()) == ArithmeticExpressionType.constant) {
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
			} else if(binaryExpr.getOperator() == BinaryArithmeticOperator.DIVIDE || binaryExpr.getOperator() == BinaryArithmeticOperator.MULTIPLY) {
				boolean isLhsConst = switch(RoamTransformationUtils.isConstantExpression(binaryExpr.getLhs())) {
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
				boolean isRhsConst = switch(RoamTransformationUtils.isConstantExpression(binaryExpr.getRhs())) {
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
				
				if(!isLhsConst && !isRhsConst) {
					throw new IllegalArgumentException("A product may not contain more than one mapping expression.");
				}
				
				if(factor == null) {
					if(isLhsConst) {
						return rewriteToSumOfProducts(binaryExpr.getRhs(), binaryExpr.getLhs(), binaryExpr.getOperator());
					} else {
						return rewriteToSumOfProducts(binaryExpr.getLhs(), binaryExpr.getRhs(), binaryExpr.getOperator());
					}
				} else {
					if(isLhsConst) {
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
				ArithmeticExpressionType lhsType = RoamTransformationUtils.isConstantExpression(binaryExpr.getLhs());
				ArithmeticExpressionType rhsType = RoamTransformationUtils.isConstantExpression(binaryExpr.getRhs());
				if(lhsType == ArithmeticExpressionType.variableValue || lhsType == ArithmeticExpressionType.variableVector) {
					throw new IllegalArgumentException("An exponential expression must not contain any mapping expressions.");
				}
				if(rhsType == ArithmeticExpressionType.variableValue || rhsType == ArithmeticExpressionType.variableVector) {
					throw new IllegalArgumentException("An exponential expression must not contain any mapping expressions.");
				}
				
				if(factor == null) {
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
		} else if(expr instanceof UnaryArithmeticExpression unaryExpr) {
			if(unaryExpr.getOperator() == UnaryArithmeticOperator.BRACKET) {
				boolean isConst = RoamTransformationUtils.isConstantExpression(unaryExpr.getExpression()) == ArithmeticExpressionType.constant ? true : false;
				if(factor == null) {
					if(isConst) {
						return unaryExpr.getExpression();
					} else {
						return rewriteToSumOfProducts(unaryExpr.getExpression(), null, null);
					}
				} else {
					if(isConst) {
						BinaryArithmeticExpression rewrite = factory.createBinaryArithmeticExpression();
						rewrite.setOperator(operator);
						rewrite.setRhs(factor);
						rewrite.setLhs(unaryExpr.getExpression());
						return rewrite;
					} else {
						return rewriteToSumOfProducts(unaryExpr.getExpression(), factor, operator);
					}
					
				}
			} else if(unaryExpr.getOperator() == UnaryArithmeticOperator.NEGATE) {
				if(factor == null) {
					DoubleLiteral constNegOne = factory.createDoubleLiteral();
					constNegOne.setLiteral(-1);
					return rewriteToSumOfProducts(unaryExpr.getExpression(), constNegOne, BinaryArithmeticOperator.MULTIPLY);
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
				ArithmeticExpressionType expressionType = RoamTransformationUtils.isConstantExpression(unaryExpr.getExpression());
				if(expressionType == ArithmeticExpressionType.variableValue ||  expressionType == ArithmeticExpressionType.variableVector) {
					throw new IllegalArgumentException("Absolute, square-root, sine or cosine expressions must not contain any mapping expressions.");
				}
				
				if(factor == null) {
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
		} else if(expr instanceof ArithmeticValue valExpr) {
			if(factor == null ) {
				return expr;
			} else {
				ArithmeticExpressionType expressionType = RoamTransformationUtils.isConstantExpression(valExpr.getValue());
				if(expressionType  == ArithmeticExpressionType.constant || expressionType  == ArithmeticExpressionType.variableScalar) {
					BinaryArithmeticExpression rewrite = factory.createBinaryArithmeticExpression();
					rewrite.setOperator(operator);
					rewrite.setLhs(valExpr);
					rewrite.setRhs(factor);
					return rewrite;
				} else {
					if(valExpr.getValue() instanceof SumExpression sumExpr) {
						ArithmeticExpression rewrite = rewriteToSumOfProducts(sumExpr.getExpression(), factor, operator);
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
			if(factor == null ) {
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
		boolean isLhsConst = RoamTransformationUtils
				.isConstantExpression(relExpr.getLhs()) == ArithmeticExpressionType.constant ? true : false;
		
		ArithmeticExpression constExpr;
		ArithmeticExpression variableExpr;
		if(isLhsConst) {
			constExpr = relExpr.getLhs();
			variableExpr = relExpr.getRhs();
		} else {
			constExpr = relExpr.getRhs();
			variableExpr = relExpr.getLhs();
		}
		
		if(variableExpr instanceof BinaryArithmeticExpression binaryExpr) {
			if(binaryExpr.getOperator() == BinaryArithmeticOperator.ADD) {
				boolean isVarLhsConst = RoamTransformationUtils
						.isConstantExpression(binaryExpr.getLhs()) == ArithmeticExpressionType.constant ? true : false;
				boolean isVarRhsConst = RoamTransformationUtils
						.isConstantExpression(binaryExpr.getRhs()) == ArithmeticExpressionType.constant ? true : false;
				
				if(!isVarLhsConst && !isVarRhsConst) {
					return relExpr;
				}
				
				RelationalExpression rewrite = factory.createRelationalExpression();
				rewrite.setOperator(relExpr.getOperator());
				BinaryArithmeticExpression newConst = factory.createBinaryArithmeticExpression();
				rewrite.setLhs(newConst);
				
				newConst.setOperator(BinaryArithmeticOperator.SUBTRACT);
				newConst.setLhs(constExpr);
				
				if(isVarLhsConst) {
					newConst.setRhs(binaryExpr.getLhs());
					rewrite.setRhs(binaryExpr.getRhs());
				} else {
					newConst.setRhs(binaryExpr.getRhs());
					rewrite.setRhs(binaryExpr.getLhs());
				}
				return rewriteMoveConstantTerms(rewrite);
			} else if(binaryExpr.getOperator() == BinaryArithmeticOperator.SUBTRACT) {
				boolean isVarLhsConst = RoamTransformationUtils
						.isConstantExpression(binaryExpr.getLhs()) == ArithmeticExpressionType.constant ? true : false;
				boolean isVarRhsConst = RoamTransformationUtils
						.isConstantExpression(binaryExpr.getRhs()) == ArithmeticExpressionType.constant ? true : false;
				
				if(!isVarLhsConst && !isVarRhsConst) {
					return relExpr;
				}
				
				RelationalExpression rewrite = factory.createRelationalExpression();
				rewrite.setOperator(relExpr.getOperator());
				BinaryArithmeticExpression newConst = factory.createBinaryArithmeticExpression();
				rewrite.setLhs(newConst);
				newConst.setLhs(constExpr);
				
				if(isVarLhsConst) {
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
		} else if(variableExpr instanceof UnaryArithmeticExpression unaryExpr) {
			return relExpr;
		} else if(variableExpr instanceof ArithmeticValue valExpr) {
			return relExpr;
		} else {
			return relExpr;
		}
	}
	
	protected ArithmeticExpression rewriteRemoveSubtractions(final ArithmeticExpression expr) {
		if(expr instanceof BinaryArithmeticExpression binaryExpr) {
			if(binaryExpr.getOperator() == BinaryArithmeticOperator.SUBTRACT) {
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
		} else if(expr instanceof UnaryArithmeticExpression unaryExpr) {
			ArithmeticExpression rewrite = rewriteRemoveSubtractions(unaryExpr.getExpression());
			unaryExpr.setExpression(rewrite);
			return unaryExpr;
		} else if(expr instanceof ArithmeticValue valExpr) {
			if(valExpr.getValue() instanceof SumExpression sumExpr) {
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
		if(expr instanceof BinaryArithmeticExpression binaryExpr) {
			if(binaryExpr.getOperator() == BinaryArithmeticOperator.ADD) {
				binaryExpr.setLhs(invertSign(binaryExpr.getLhs()));
				binaryExpr.setOperator(BinaryArithmeticOperator.SUBTRACT);
				return binaryExpr;
			} else if(binaryExpr.getOperator() == BinaryArithmeticOperator.SUBTRACT) {
				binaryExpr.setLhs(invertSign(binaryExpr.getLhs()));
				binaryExpr.setOperator(BinaryArithmeticOperator.ADD);
				return binaryExpr;
			} else {
				binaryExpr.setLhs(invertSign(binaryExpr.getLhs()));
				return binaryExpr;
			}
		} else if(expr instanceof UnaryArithmeticExpression unaryExpr) {
			if(unaryExpr.getOperator() == UnaryArithmeticOperator.NEGATE) {
				return unaryExpr.getExpression();
			} else if(unaryExpr.getOperator() == UnaryArithmeticOperator.BRACKET) {
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
		} else if(expr instanceof ArithmeticValue valExpr) {
			if(valExpr.getValue() instanceof SumExpression sumExpr) {
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
		} else if(expr instanceof IntegerLiteral lit) {
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
