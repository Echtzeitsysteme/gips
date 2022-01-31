package org.emoflon.roam.build.transformation;

import java.util.Collection;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.utils.GTEditorPatternUtils;
import org.emoflon.ibex.gt.transformations.EditorToIBeXPatternTransformation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticValue;
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticOperator;
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryBoolOperator;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolBinaryExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolUnaryExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolValue;
import org.emoflon.roam.intermediate.RoamIntermediate.Constraint;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNode;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNodeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeValue;
import org.emoflon.roam.intermediate.RoamIntermediate.DoubleLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.FeatureExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.FeatureLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.IntegerLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorTypeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingConstraint;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingSumExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.RelationalExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.RelationalOperator;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateFactory;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamFilterOperation;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamOperation;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamSelectOperation;
import org.emoflon.roam.intermediate.RoamIntermediate.TypeConstraint;
import org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticOperator;
import org.emoflon.roam.intermediate.RoamIntermediate.UnaryBoolOperator;
import org.emoflon.roam.intermediate.RoamIntermediate.ValueExpression;
import org.emoflon.roam.roamslang.roamSLang.EditorGTFile;
import org.emoflon.roam.roamslang.roamSLang.RoamArithmeticExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamArithmeticLiteral;
import org.emoflon.roam.roamslang.roamSLang.RoamAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamBinaryBoolExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamBoolExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamBooleanLiteral;
import org.emoflon.roam.roamslang.roamSLang.RoamBracketExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamConstraint;
import org.emoflon.roam.roamslang.roamSLang.RoamContextExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamContextOperationExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamExpArithmeticExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamExpressionOperand;
import org.emoflon.roam.roamslang.roamSLang.RoamFeatureExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamFeatureLit;
import org.emoflon.roam.roamslang.roamSLang.RoamFeatureNavigation;
import org.emoflon.roam.roamslang.roamSLang.RoamLambdaAttributeExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamMappingAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamMappingContext;
import org.emoflon.roam.roamslang.roamSLang.RoamNodeAttributeExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamObjectiveExpression;
import org.emoflon.roam.roamslang.roamSLang.RoamProductArithmeticExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamRelExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamSelect;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamBoolExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamNavigation;
import org.emoflon.roam.roamslang.roamSLang.RoamStreamSet;
import org.emoflon.roam.roamslang.roamSLang.RoamSumArithmeticExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamTypeContext;
import org.emoflon.roam.roamslang.roamSLang.RoamUnaryArithmeticExpr;
import org.emoflon.roam.roamslang.roamSLang.RoamUnaryBoolExpr;
import org.emoflon.roam.roamslang.scoping.RoamSLangScopeContextUtil;

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
				data.eConstraint2Constraint().put(eConstraint, constraint);
				
				RoamRelExpr relExpr = (RoamRelExpr) boolExpr;
				// Check whether this is a simple boolean result of some attribute or stream expression, or a true relational expression, which compares two numeric values.
				if(relExpr.getRight() == null) {
					if(relExpr.getLeft() instanceof RoamAttributeExpr eAttributeExpr) {
						if(eAttributeExpr instanceof RoamMappingAttributeExpr eMappingAttribute && eMappingAttribute.getExpr() != null) {
							setUnaryConstraintCondition((MappingConstraint) constraint, eMappingAttribute.getExpr());
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
	
	/*	Translates a simple unary boolean operation (i.e., <stream>.exists() and <stream>.notExists()), 
	 *  which was defined on a stream of mapping variables, into an ILP constraint. 
	 *  E.g.: <stream>.notExists() ==> Sum(Set of Variables) = 0 
	 */
	protected void setUnaryConstraintCondition(final MappingConstraint constraint, final RoamStreamExpr streamExpr) throws Exception{
		RoamStreamExpr terminalExpr = getTerminalStreamExpression(streamExpr);
		if(terminalExpr instanceof RoamStreamBoolExpr streamBool) {
			switch(streamBool.getOperator()) {
				case COUNT -> {
					throw new IllegalArgumentException("Some constrains contain invalid values within boolean expressions, e.g., arithmetic values instead of boolean values.");
				}
				case EXISTS -> {
					
				}
				case NOTEXISTS -> {
					constraint.setElementwise(true);
					IntegerLiteral constZero = factory.createIntegerLiteral();
					constZero.setLiteral(0);
					constraint.setLhsConstant(constZero);
					constraint.setOperator(RelationalOperator.EQUAL);
					constraint.setRhsExpression(createSumFromStreamExpression(constraint, streamExpr));
				}
				default -> {
					throw new UnsupportedOperationException("Unknown stream operator: "+streamBool.getOperator());
				}
			}
		} else {
			throw new IllegalArgumentException("Some constrains contain invalid values within boolean expressions, e.g., arithmetic values instead of boolean values.");
		}
	}
	
	protected MappingSumExpression createSumFromStreamExpression(final MappingConstraint constraint, final RoamStreamExpr streamExpr) throws Exception {
		MappingSumExpression mapSum = factory.createMappingSumExpression();
		data.eStream2SetOp().put(streamExpr, mapSum);
		mapSum.setMapping(constraint.getMapping());
		mapSum.setReturnType(EcorePackage.Literals.EINT);
		// Simple expression: Just add all filtered (!) mapping variable values v={0,1}
		ArithmeticValue val = factory.createArithmeticValue();
		IteratorMappingValue itr = factory.createIteratorMappingValue();
		itr.setMappingContext(constraint);
		itr.setStream(mapSum);
		val.setValue(itr);
		val.setReturnType(EcorePackage.Literals.EINT);
		mapSum.setExpression(val);
		// Create filter expression
		mapSum.setFilter(createFilterFromStream(streamExpr));
		return mapSum;
	}
	
	protected StreamExpression createFilterFromStream(final RoamStreamExpr streamExpr) throws Exception{
		StreamExpression expr = factory.createStreamExpression();
		data.eStream2SetOp().put(streamExpr, expr);
		if(streamExpr instanceof RoamStreamNavigation nav) {
			expr.setCurrent(createStreamFilterOperation(nav.getLeft()));
			if(!(nav.getRight() instanceof RoamSelect || nav.getRight() instanceof RoamStreamSet)) {
				throw new IllegalArgumentException("Stream expression <"+nav.getRight()+"> is not a valid filter operation.");
			} else {
				expr.setChild(createFilterFromStream(nav.getRight()));
				return expr;
			}
		} else if(streamExpr instanceof RoamSelect select) {
			expr.setCurrent(createStreamFilterOperation(select));
			return expr;
		} else {
			RoamStreamSet set = (RoamStreamSet) streamExpr;
			expr.setCurrent(createStreamFilterOperation(set));
			return expr;
		}
	}
	
	protected StreamOperation createStreamFilterOperation(final RoamStreamExpr streamExpr) throws Exception{
		if(streamExpr instanceof RoamSelect select) {
			StreamSelectOperation op = factory.createStreamSelectOperation();
			op.setType((EClass) select.getType());
			return op;
		} else if (streamExpr instanceof RoamStreamSet set){
			StreamFilterOperation op = factory.createStreamFilterOperation();
			op.setPredicate(transformBoolExpression(set.getLambda().getExpr()));
			return op;
		} else {
			throw new IllegalArgumentException("Stream expression <"+streamExpr+"> is not a valid filter operation.");
		}
	}
	
	protected BoolExpression transformBoolExpression(final RoamBoolExpr eBool) throws Exception{
		if(eBool instanceof RoamBooleanLiteral eLitBool) {
			BoolLiteral literal = factory.createBoolLiteral();
			literal.setLiteral(eLitBool.isLiteral());
			return literal;
		} else if(eBool instanceof RoamRelExpr eRelBool) {
			if(eRelBool.getRight() == null) {
				BoolValue value = factory.createBoolValue();
				if(eRelBool.getLeft() instanceof RoamExpressionOperand && eRelBool.getLeft() instanceof RoamAttributeExpr eOperand) {
					value.setValue(transformAttributeToValueExpression(eOperand));
				} else {
					throw new IllegalArgumentException("Boolean expression must not contain any numeric values!");
				}
				return value;
			} else {
				RelationalExpression relExpr = factory.createRelationalExpression();
				switch(eRelBool.getOperator()) {
				case EQUAL:
					relExpr.setOperator(RelationalOperator.EQUAL);
					break;
				case GREATER:
					relExpr.setOperator(RelationalOperator.GREATER);
					break;
				case GREATER_OR_EQUAL:
					relExpr.setOperator(RelationalOperator.GREATER_OR_EQUAL);
					break;
				case SMALLER:
					relExpr.setOperator(RelationalOperator.LESS);
					break;
				case SMALLER_OR_EQUAL:
					relExpr.setOperator(RelationalOperator.LESS_OR_EQUAL);
					break;
				case UNEQUAL:
					relExpr.setOperator(RelationalOperator.NOT_EQUAL);
					break;
				default:
					throw new UnsupportedOperationException("Unknown bool operator: "+eRelBool.getOperator());
				}
				relExpr.setLhs(transformArithmeticExpression(eRelBool.getLeft()));
				relExpr.setRhs(transformArithmeticExpression(eRelBool.getRight()));
				return relExpr;
			}
		} else if(eBool instanceof RoamBinaryBoolExpr eBinBool) {
			BoolBinaryExpression binaryBool = factory.createBoolBinaryExpression();
			switch(eBinBool.getOperator()) {
			case AND:
				binaryBool.setOperator(BinaryBoolOperator.AND);
				break;
			case OR:
				binaryBool.setOperator(BinaryBoolOperator.OR);
				break;
			default :
				throw new UnsupportedOperationException("Unknown bool operator: "+eBinBool.getOperator());
			}
			binaryBool.setLhs(transformBoolExpression(eBinBool.getLeft()));
			binaryBool.setRhs(transformBoolExpression(eBinBool.getRight()));
			return binaryBool;
		} else {
			RoamUnaryBoolExpr eUnBool = (RoamUnaryBoolExpr) eBool;
			BoolUnaryExpression unaryBool = factory.createBoolUnaryExpression();
			switch(eUnBool.getOperator()) {
			case NOT:
				unaryBool.setOperator(UnaryBoolOperator.NOT);
				break;
			default:
				throw new UnsupportedOperationException("Unknown bool operator: "+eUnBool.getOperator());
			}
			unaryBool.setExpression(transformBoolExpression(eUnBool.getOperand()));
			return unaryBool;
		}
	}
	
	protected ArithmeticExpression transformArithmeticExpression(final RoamArithmeticExpr eArithmetic) throws Exception{
		if(eArithmetic instanceof RoamSumArithmeticExpr sumExpr) {
			BinaryArithmeticExpression binExpr = factory.createBinaryArithmeticExpression();
			switch(sumExpr.getOperator()) {
				case MINUS -> {
					binExpr.setOperator(BinaryArithmeticOperator.SUBTRACT);
				}
				case PLUS -> {
					binExpr.setOperator(BinaryArithmeticOperator.ADD);
				}
				default -> {
					throw new UnsupportedOperationException("Unknown arithmetic operator: "+sumExpr.getOperator());
				}
			}
			binExpr.setLhs(transformArithmeticExpression(sumExpr.getLeft()));
			binExpr.setRhs(transformArithmeticExpression(sumExpr.getRight()));
			return binExpr;
		} else if(eArithmetic instanceof RoamProductArithmeticExpr prodExpr) {
			BinaryArithmeticExpression binExpr = factory.createBinaryArithmeticExpression();
			switch(prodExpr.getOperator()) {
				case MULT -> {
					binExpr.setOperator(BinaryArithmeticOperator.MULTIPLY);
				}
				case DIV -> {
					binExpr.setOperator(BinaryArithmeticOperator.DIVIDE);
				}
				default -> {
					throw new UnsupportedOperationException("Unknown arithmetic operator: "+prodExpr.getOperator());
				}
			}
			binExpr.setLhs(transformArithmeticExpression(prodExpr.getLeft()));
			binExpr.setRhs(transformArithmeticExpression(prodExpr.getRight()));
			return binExpr;
		} else if(eArithmetic instanceof RoamExpArithmeticExpr expExpr) {
			BinaryArithmeticExpression binExpr = factory.createBinaryArithmeticExpression();
			switch(expExpr.getOperator()) {
				case POW -> {
					binExpr.setOperator(BinaryArithmeticOperator.POW);
				}
				default -> {
					throw new UnsupportedOperationException("Unknown arithmetic operator: "+expExpr.getOperator());
				}
			}
			binExpr.setLhs(transformArithmeticExpression(expExpr.getLeft()));
			binExpr.setRhs(transformArithmeticExpression(expExpr.getRight()));
			return binExpr;
		} else if(eArithmetic instanceof RoamUnaryArithmeticExpr unaryExpr) {
			UnaryArithmeticExpression unExpr = factory.createUnaryArithmeticExpression();
			switch(unaryExpr.getOperator()) {
				case ABS -> {
					unExpr.setOperator(UnaryArithmeticOperator.ABSOLUTE);
				}
				case COS -> {
					unExpr.setOperator(UnaryArithmeticOperator.COSINE);
				}
				case NEG -> {
					unExpr.setOperator(UnaryArithmeticOperator.NEGATE);
				}
				case SIN -> {
					unExpr.setOperator(UnaryArithmeticOperator.SINE);
				}
				case SQRT -> {
					unExpr.setOperator(UnaryArithmeticOperator.SQRT);
				}
				default -> {
					throw new UnsupportedOperationException("Unknown arithmetic operator: "+unaryExpr.getOperator());
				}
			}
			unExpr.setExpression(transformArithmeticExpression(unaryExpr.getOperand()));
			return unExpr;
		} else if(eArithmetic instanceof RoamBracketExpr bracketExpr) {
			UnaryArithmeticExpression unExpr = factory.createUnaryArithmeticExpression();
			unExpr.setOperator(UnaryArithmeticOperator.BRACKET);
			unExpr.setExpression(transformArithmeticExpression(bracketExpr.getOperand()));
			return unExpr;
		} else { // CASE: RoamExpressionOperand -> i.e., a literal from the perspective of an arithmetic expression
			if(eArithmetic instanceof RoamArithmeticLiteral lit) {
				try {
					int value = Integer.parseInt(lit.getValue());
					IntegerLiteral intLit = factory.createIntegerLiteral();
					intLit.setLiteral(value);
					return intLit;
				} catch(Exception e) {
					try {
						double dValue = Double.parseDouble(lit.getValue());
						DoubleLiteral doubleLit = factory.createDoubleLiteral();
						doubleLit.setLiteral(dValue);
						return doubleLit;
					} catch(Exception e2) {
						throw new IllegalArgumentException("Value <"+lit.getValue()+"> can't be parsed to neither integer nor double value.");
					}
				} 
 			} else if(eArithmetic instanceof RoamObjectiveExpression objExpr) {
				//TODO: Objectives and Objective Expressions currently not modeled
				throw new UnsupportedOperationException("References to objective function values not yet implemented.");
			} else {
				ArithmeticValue aValue = factory.createArithmeticValue();
				aValue.setValue(transformAttributeToValueExpression((RoamAttributeExpr) eArithmetic));
				return aValue;
			}
		}
	}
	
	protected ValueExpression transformAttributeToValueExpression(final RoamAttributeExpr eAttribute) throws Exception {
		if(eAttribute instanceof RoamMappingAttributeExpr eMapping) {
			//TODO: We could support this to a limited degree in the future.
			throw new UnsupportedOperationException("Nested Mapping access operations not yet supported.");
		} else if(eAttribute instanceof RoamContextExpr eContext) {
			EObject contextType = RoamSLangScopeContextUtil.getContextType(eContext);
			Constraint constraint = data.eConstraint2Constraint().get(contextType.eContainer());
			if(eContext.getExpr() == null && eContext.getStream() == null) {
				if(contextType instanceof RoamTypeContext typeContext) {
					TypeConstraint tc = (TypeConstraint) constraint;
					ContextTypeValue typeValue = factory.createContextTypeValue();
					typeValue.setType(tc.getModelType());
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
						featureValue.setType(tc.getModelType());
						featureValue.setFeatureExpression(translateFeatureExpression(eFeature));
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
							nodeValue.setType(nodeValue.getNode().getType());
							return nodeValue;
						} else {
							ContextMappingNodeFeatureValue featureValue = factory.createContextMappingNodeFeatureValue();
							featureValue.setMappingContext(mc);
							featureValue.setNode(data.eNode2Node().get(eNodeExpr.getNode()));
							featureValue.setType(featureValue.getNode().getType());
							featureValue.setFeatureExpression(translateFeatureExpression(eNodeExpr.getExpr()));
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
				//TODO: We could support this to a limited degree in the future.
				//Case: The context expression is followed by some stream expression
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
						mappingNode.setType(mappingNode.getNode().getType());
						mappingNode.setMappingContext((MappingConstraint) data.eMapping2Mapping().get(eMappingAttribute.getMapping()));
						mappingNode.setStream(data.eStream2SetOp().get(streamIteratorContainer));
						return mappingNode;
					} else {
						IteratorMappingNodeFeatureValue mappingFeature = factory.createIteratorMappingNodeFeatureValue();
						mappingFeature.setNode(data.eNode2Node().get(eNodeAttribute.getNode()));
						mappingFeature.setType(mappingFeature.getNode().getType());
						mappingFeature.setMappingContext((MappingConstraint) data.eMapping2Mapping().get(eMappingAttribute.getMapping()));
						mappingFeature.setStream(data.eStream2SetOp().get(streamIteratorContainer));
						mappingFeature.setFeatureExpression(translateFeatureExpression(eNodeAttribute.getExpr()));
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
							// TODO:
							// On a serious note: Accessing ILP variable values should not be allowed in filter stream expressions since it is impractical.
							IteratorMappingValue mappingValue = factory.createIteratorMappingValue();
							mappingValue.setMappingContext((MappingConstraint) data.eMapping2Mapping().get(eMappingAttribute.getMapping()));
							mappingValue.setStream(data.eStream2SetOp().get(streamIteratorContainer));
							mappingValue.setType(EcorePackage.Literals.EINT);
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
						mappingFeatureValue.setMappingContext((MappingConstraint) data.eConstraint2Constraint().get(eMappingContext.eContainer()));
						RoamFeatureLit rootFeatureType = (RoamFeatureLit) RoamSLangScopeContextUtil.findLeafExpression(eNodeExpr.getExpr());
						mappingFeatureValue.setType(rootFeatureType.getFeature().getEType());
						mappingFeatureValue.setFeatureExpression(translateFeatureExpression(eFeature));
						return mappingFeatureValue;
					} else if(contextType instanceof RoamTypeContext eTypeContext && eContext.getExpr() instanceof RoamFeatureExpr eRootFeature) {
						IteratorTypeFeatureValue typeFeatureValue = factory.createIteratorTypeFeatureValue();
						typeFeatureValue.setTypeContext((TypeConstraint) data.eConstraint2Constraint().get(eTypeContext.eContainer()));
						RoamFeatureLit rootFeatureType = (RoamFeatureLit) RoamSLangScopeContextUtil.findLeafExpression(eRootFeature);
						typeFeatureValue.setType(rootFeatureType.getFeature().getEType());
						typeFeatureValue.setStream(data.eStream2SetOp().get(streamIteratorContainer));
						typeFeatureValue.setFeatureExpression(translateFeatureExpression(eFeature));
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
	
	protected FeatureExpression translateFeatureExpression(final RoamFeatureExpr eFeature) {
		FeatureExpression feature = factory.createFeatureExpression();
		if(eFeature instanceof RoamFeatureNavigation nav) {
			feature.setCurrent(createFeatureLiteral((RoamFeatureLit) nav.getLeft()));
			feature.setChild(translateFeatureExpression(nav.getRight()));
		} else {
			feature.setCurrent(createFeatureLiteral((RoamFeatureLit) eFeature));
		}
		return feature;
	}
	
	protected FeatureLiteral createFeatureLiteral(final RoamFeatureLit eFeature) {
		FeatureLiteral lit = factory.createFeatureLiteral();
		lit.setFeature(eFeature.getFeature());
		return lit;
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
