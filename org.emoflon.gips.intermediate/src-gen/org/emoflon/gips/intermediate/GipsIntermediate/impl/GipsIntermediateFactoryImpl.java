/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticValue;
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryBoolOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolBinaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolStreamExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolUnaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNode;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNodeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNode;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNodeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeValue;
import org.emoflon.gips.intermediate.GipsIntermediate.DoubleLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.FeatureExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.FeatureLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateFactory;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.GlobalObjective;
import org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig;
import org.emoflon.gips.intermediate.GipsIntermediate.ILPSolverType;
import org.emoflon.gips.intermediate.GipsIntermediate.IntegerLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeValue;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingObjective;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingSumExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Objective;
import org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveFunctionValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveTarget;
import org.emoflon.gips.intermediate.GipsIntermediate.Pattern;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternObjective;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamArithmeticOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamBoolOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamFilterOperation;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamSelectOperation;
import org.emoflon.gips.intermediate.GipsIntermediate.Type;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeObjective;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeSumExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.UnaryBoolOperator;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class GipsIntermediateFactoryImpl extends EFactoryImpl implements GipsIntermediateFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static GipsIntermediateFactory init() {
		try {
			GipsIntermediateFactory theGipsIntermediateFactory = (GipsIntermediateFactory) EPackage.Registry.INSTANCE
					.getEFactory(GipsIntermediatePackage.eNS_URI);
			if (theGipsIntermediateFactory != null) {
				return theGipsIntermediateFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new GipsIntermediateFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public GipsIntermediateFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL:
			return createGipsIntermediateModel();
		case GipsIntermediatePackage.ILP_CONFIG:
			return createILPConfig();
		case GipsIntermediatePackage.PATTERN:
			return createPattern();
		case GipsIntermediatePackage.TYPE:
			return createType();
		case GipsIntermediatePackage.MAPPING:
			return createMapping();
		case GipsIntermediatePackage.OBJECTIVE:
			return createObjective();
		case GipsIntermediatePackage.GLOBAL_OBJECTIVE:
			return createGlobalObjective();
		case GipsIntermediatePackage.PATTERN_CONSTRAINT:
			return createPatternConstraint();
		case GipsIntermediatePackage.TYPE_CONSTRAINT:
			return createTypeConstraint();
		case GipsIntermediatePackage.MAPPING_CONSTRAINT:
			return createMappingConstraint();
		case GipsIntermediatePackage.PATTERN_OBJECTIVE:
			return createPatternObjective();
		case GipsIntermediatePackage.TYPE_OBJECTIVE:
			return createTypeObjective();
		case GipsIntermediatePackage.MAPPING_OBJECTIVE:
			return createMappingObjective();
		case GipsIntermediatePackage.BINARY_ARITHMETIC_EXPRESSION:
			return createBinaryArithmeticExpression();
		case GipsIntermediatePackage.UNARY_ARITHMETIC_EXPRESSION:
			return createUnaryArithmeticExpression();
		case GipsIntermediatePackage.ARITHMETIC_VALUE:
			return createArithmeticValue();
		case GipsIntermediatePackage.INTEGER_LITERAL:
			return createIntegerLiteral();
		case GipsIntermediatePackage.DOUBLE_LITERAL:
			return createDoubleLiteral();
		case GipsIntermediatePackage.BOOL_BINARY_EXPRESSION:
			return createBoolBinaryExpression();
		case GipsIntermediatePackage.BOOL_UNARY_EXPRESSION:
			return createBoolUnaryExpression();
		case GipsIntermediatePackage.BOOL_VALUE:
			return createBoolValue();
		case GipsIntermediatePackage.BOOL_STREAM_EXPRESSION:
			return createBoolStreamExpression();
		case GipsIntermediatePackage.RELATIONAL_EXPRESSION:
			return createRelationalExpression();
		case GipsIntermediatePackage.BOOL_LITERAL:
			return createBoolLiteral();
		case GipsIntermediatePackage.MAPPING_SUM_EXPRESSION:
			return createMappingSumExpression();
		case GipsIntermediatePackage.TYPE_SUM_EXPRESSION:
			return createTypeSumExpression();
		case GipsIntermediatePackage.CONTEXT_TYPE_VALUE:
			return createContextTypeValue();
		case GipsIntermediatePackage.CONTEXT_PATTERN_VALUE:
			return createContextPatternValue();
		case GipsIntermediatePackage.CONTEXT_PATTERN_NODE:
			return createContextPatternNode();
		case GipsIntermediatePackage.CONTEXT_MAPPING_VALUE:
			return createContextMappingValue();
		case GipsIntermediatePackage.CONTEXT_MAPPING_NODE:
			return createContextMappingNode();
		case GipsIntermediatePackage.OBJECTIVE_FUNCTION_VALUE:
			return createObjectiveFunctionValue();
		case GipsIntermediatePackage.FEATURE_EXPRESSION:
			return createFeatureExpression();
		case GipsIntermediatePackage.FEATURE_LITERAL:
			return createFeatureLiteral();
		case GipsIntermediatePackage.CONTEXT_TYPE_FEATURE_VALUE:
			return createContextTypeFeatureValue();
		case GipsIntermediatePackage.CONTEXT_PATTERN_NODE_FEATURE_VALUE:
			return createContextPatternNodeFeatureValue();
		case GipsIntermediatePackage.CONTEXT_MAPPING_NODE_FEATURE_VALUE:
			return createContextMappingNodeFeatureValue();
		case GipsIntermediatePackage.ITERATOR_PATTERN_VALUE:
			return createIteratorPatternValue();
		case GipsIntermediatePackage.ITERATOR_PATTERN_FEATURE_VALUE:
			return createIteratorPatternFeatureValue();
		case GipsIntermediatePackage.ITERATOR_PATTERN_NODE_VALUE:
			return createIteratorPatternNodeValue();
		case GipsIntermediatePackage.ITERATOR_PATTERN_NODE_FEATURE_VALUE:
			return createIteratorPatternNodeFeatureValue();
		case GipsIntermediatePackage.ITERATOR_MAPPING_VALUE:
			return createIteratorMappingValue();
		case GipsIntermediatePackage.ITERATOR_MAPPING_FEATURE_VALUE:
			return createIteratorMappingFeatureValue();
		case GipsIntermediatePackage.ITERATOR_MAPPING_NODE_VALUE:
			return createIteratorMappingNodeValue();
		case GipsIntermediatePackage.ITERATOR_MAPPING_NODE_FEATURE_VALUE:
			return createIteratorMappingNodeFeatureValue();
		case GipsIntermediatePackage.ITERATOR_TYPE_VALUE:
			return createIteratorTypeValue();
		case GipsIntermediatePackage.ITERATOR_TYPE_FEATURE_VALUE:
			return createIteratorTypeFeatureValue();
		case GipsIntermediatePackage.STREAM_EXPRESSION:
			return createStreamExpression();
		case GipsIntermediatePackage.STREAM_FILTER_OPERATION:
			return createStreamFilterOperation();
		case GipsIntermediatePackage.STREAM_SELECT_OPERATION:
			return createStreamSelectOperation();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
		case GipsIntermediatePackage.ILP_SOLVER_TYPE:
			return createILPSolverTypeFromString(eDataType, initialValue);
		case GipsIntermediatePackage.OBJECTIVE_TARGET:
			return createObjectiveTargetFromString(eDataType, initialValue);
		case GipsIntermediatePackage.RELATIONAL_OPERATOR:
			return createRelationalOperatorFromString(eDataType, initialValue);
		case GipsIntermediatePackage.BINARY_ARITHMETIC_OPERATOR:
			return createBinaryArithmeticOperatorFromString(eDataType, initialValue);
		case GipsIntermediatePackage.UNARY_ARITHMETIC_OPERATOR:
			return createUnaryArithmeticOperatorFromString(eDataType, initialValue);
		case GipsIntermediatePackage.STREAM_ARITHMETIC_OPERATOR:
			return createStreamArithmeticOperatorFromString(eDataType, initialValue);
		case GipsIntermediatePackage.BINARY_BOOL_OPERATOR:
			return createBinaryBoolOperatorFromString(eDataType, initialValue);
		case GipsIntermediatePackage.UNARY_BOOL_OPERATOR:
			return createUnaryBoolOperatorFromString(eDataType, initialValue);
		case GipsIntermediatePackage.STREAM_BOOL_OPERATOR:
			return createStreamBoolOperatorFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
		case GipsIntermediatePackage.ILP_SOLVER_TYPE:
			return convertILPSolverTypeToString(eDataType, instanceValue);
		case GipsIntermediatePackage.OBJECTIVE_TARGET:
			return convertObjectiveTargetToString(eDataType, instanceValue);
		case GipsIntermediatePackage.RELATIONAL_OPERATOR:
			return convertRelationalOperatorToString(eDataType, instanceValue);
		case GipsIntermediatePackage.BINARY_ARITHMETIC_OPERATOR:
			return convertBinaryArithmeticOperatorToString(eDataType, instanceValue);
		case GipsIntermediatePackage.UNARY_ARITHMETIC_OPERATOR:
			return convertUnaryArithmeticOperatorToString(eDataType, instanceValue);
		case GipsIntermediatePackage.STREAM_ARITHMETIC_OPERATOR:
			return convertStreamArithmeticOperatorToString(eDataType, instanceValue);
		case GipsIntermediatePackage.BINARY_BOOL_OPERATOR:
			return convertBinaryBoolOperatorToString(eDataType, instanceValue);
		case GipsIntermediatePackage.UNARY_BOOL_OPERATOR:
			return convertUnaryBoolOperatorToString(eDataType, instanceValue);
		case GipsIntermediatePackage.STREAM_BOOL_OPERATOR:
			return convertStreamBoolOperatorToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public GipsIntermediateModel createGipsIntermediateModel() {
		GipsIntermediateModelImpl gipsIntermediateModel = new GipsIntermediateModelImpl();
		return gipsIntermediateModel;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ILPConfig createILPConfig() {
		ILPConfigImpl ilpConfig = new ILPConfigImpl();
		return ilpConfig;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Pattern createPattern() {
		PatternImpl pattern = new PatternImpl();
		return pattern;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Type createType() {
		TypeImpl type = new TypeImpl();
		return type;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Mapping createMapping() {
		MappingImpl mapping = new MappingImpl();
		return mapping;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Objective createObjective() {
		ObjectiveImpl objective = new ObjectiveImpl();
		return objective;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public GlobalObjective createGlobalObjective() {
		GlobalObjectiveImpl globalObjective = new GlobalObjectiveImpl();
		return globalObjective;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public PatternConstraint createPatternConstraint() {
		PatternConstraintImpl patternConstraint = new PatternConstraintImpl();
		return patternConstraint;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TypeConstraint createTypeConstraint() {
		TypeConstraintImpl typeConstraint = new TypeConstraintImpl();
		return typeConstraint;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MappingConstraint createMappingConstraint() {
		MappingConstraintImpl mappingConstraint = new MappingConstraintImpl();
		return mappingConstraint;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public PatternObjective createPatternObjective() {
		PatternObjectiveImpl patternObjective = new PatternObjectiveImpl();
		return patternObjective;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TypeObjective createTypeObjective() {
		TypeObjectiveImpl typeObjective = new TypeObjectiveImpl();
		return typeObjective;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MappingObjective createMappingObjective() {
		MappingObjectiveImpl mappingObjective = new MappingObjectiveImpl();
		return mappingObjective;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public BinaryArithmeticExpression createBinaryArithmeticExpression() {
		BinaryArithmeticExpressionImpl binaryArithmeticExpression = new BinaryArithmeticExpressionImpl();
		return binaryArithmeticExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public UnaryArithmeticExpression createUnaryArithmeticExpression() {
		UnaryArithmeticExpressionImpl unaryArithmeticExpression = new UnaryArithmeticExpressionImpl();
		return unaryArithmeticExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ArithmeticValue createArithmeticValue() {
		ArithmeticValueImpl arithmeticValue = new ArithmeticValueImpl();
		return arithmeticValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IntegerLiteral createIntegerLiteral() {
		IntegerLiteralImpl integerLiteral = new IntegerLiteralImpl();
		return integerLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DoubleLiteral createDoubleLiteral() {
		DoubleLiteralImpl doubleLiteral = new DoubleLiteralImpl();
		return doubleLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public BoolBinaryExpression createBoolBinaryExpression() {
		BoolBinaryExpressionImpl boolBinaryExpression = new BoolBinaryExpressionImpl();
		return boolBinaryExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public BoolUnaryExpression createBoolUnaryExpression() {
		BoolUnaryExpressionImpl boolUnaryExpression = new BoolUnaryExpressionImpl();
		return boolUnaryExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public BoolValue createBoolValue() {
		BoolValueImpl boolValue = new BoolValueImpl();
		return boolValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public BoolStreamExpression createBoolStreamExpression() {
		BoolStreamExpressionImpl boolStreamExpression = new BoolStreamExpressionImpl();
		return boolStreamExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public RelationalExpression createRelationalExpression() {
		RelationalExpressionImpl relationalExpression = new RelationalExpressionImpl();
		return relationalExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public BoolLiteral createBoolLiteral() {
		BoolLiteralImpl boolLiteral = new BoolLiteralImpl();
		return boolLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MappingSumExpression createMappingSumExpression() {
		MappingSumExpressionImpl mappingSumExpression = new MappingSumExpressionImpl();
		return mappingSumExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TypeSumExpression createTypeSumExpression() {
		TypeSumExpressionImpl typeSumExpression = new TypeSumExpressionImpl();
		return typeSumExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ContextTypeValue createContextTypeValue() {
		ContextTypeValueImpl contextTypeValue = new ContextTypeValueImpl();
		return contextTypeValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ContextPatternValue createContextPatternValue() {
		ContextPatternValueImpl contextPatternValue = new ContextPatternValueImpl();
		return contextPatternValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ContextPatternNode createContextPatternNode() {
		ContextPatternNodeImpl contextPatternNode = new ContextPatternNodeImpl();
		return contextPatternNode;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ContextMappingValue createContextMappingValue() {
		ContextMappingValueImpl contextMappingValue = new ContextMappingValueImpl();
		return contextMappingValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ContextMappingNode createContextMappingNode() {
		ContextMappingNodeImpl contextMappingNode = new ContextMappingNodeImpl();
		return contextMappingNode;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ObjectiveFunctionValue createObjectiveFunctionValue() {
		ObjectiveFunctionValueImpl objectiveFunctionValue = new ObjectiveFunctionValueImpl();
		return objectiveFunctionValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public FeatureExpression createFeatureExpression() {
		FeatureExpressionImpl featureExpression = new FeatureExpressionImpl();
		return featureExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public FeatureLiteral createFeatureLiteral() {
		FeatureLiteralImpl featureLiteral = new FeatureLiteralImpl();
		return featureLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ContextTypeFeatureValue createContextTypeFeatureValue() {
		ContextTypeFeatureValueImpl contextTypeFeatureValue = new ContextTypeFeatureValueImpl();
		return contextTypeFeatureValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ContextPatternNodeFeatureValue createContextPatternNodeFeatureValue() {
		ContextPatternNodeFeatureValueImpl contextPatternNodeFeatureValue = new ContextPatternNodeFeatureValueImpl();
		return contextPatternNodeFeatureValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ContextMappingNodeFeatureValue createContextMappingNodeFeatureValue() {
		ContextMappingNodeFeatureValueImpl contextMappingNodeFeatureValue = new ContextMappingNodeFeatureValueImpl();
		return contextMappingNodeFeatureValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IteratorPatternValue createIteratorPatternValue() {
		IteratorPatternValueImpl iteratorPatternValue = new IteratorPatternValueImpl();
		return iteratorPatternValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IteratorPatternFeatureValue createIteratorPatternFeatureValue() {
		IteratorPatternFeatureValueImpl iteratorPatternFeatureValue = new IteratorPatternFeatureValueImpl();
		return iteratorPatternFeatureValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IteratorPatternNodeValue createIteratorPatternNodeValue() {
		IteratorPatternNodeValueImpl iteratorPatternNodeValue = new IteratorPatternNodeValueImpl();
		return iteratorPatternNodeValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IteratorPatternNodeFeatureValue createIteratorPatternNodeFeatureValue() {
		IteratorPatternNodeFeatureValueImpl iteratorPatternNodeFeatureValue = new IteratorPatternNodeFeatureValueImpl();
		return iteratorPatternNodeFeatureValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IteratorMappingValue createIteratorMappingValue() {
		IteratorMappingValueImpl iteratorMappingValue = new IteratorMappingValueImpl();
		return iteratorMappingValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IteratorMappingFeatureValue createIteratorMappingFeatureValue() {
		IteratorMappingFeatureValueImpl iteratorMappingFeatureValue = new IteratorMappingFeatureValueImpl();
		return iteratorMappingFeatureValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IteratorMappingNodeValue createIteratorMappingNodeValue() {
		IteratorMappingNodeValueImpl iteratorMappingNodeValue = new IteratorMappingNodeValueImpl();
		return iteratorMappingNodeValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IteratorMappingNodeFeatureValue createIteratorMappingNodeFeatureValue() {
		IteratorMappingNodeFeatureValueImpl iteratorMappingNodeFeatureValue = new IteratorMappingNodeFeatureValueImpl();
		return iteratorMappingNodeFeatureValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IteratorTypeValue createIteratorTypeValue() {
		IteratorTypeValueImpl iteratorTypeValue = new IteratorTypeValueImpl();
		return iteratorTypeValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IteratorTypeFeatureValue createIteratorTypeFeatureValue() {
		IteratorTypeFeatureValueImpl iteratorTypeFeatureValue = new IteratorTypeFeatureValueImpl();
		return iteratorTypeFeatureValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public StreamExpression createStreamExpression() {
		StreamExpressionImpl streamExpression = new StreamExpressionImpl();
		return streamExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public StreamFilterOperation createStreamFilterOperation() {
		StreamFilterOperationImpl streamFilterOperation = new StreamFilterOperationImpl();
		return streamFilterOperation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public StreamSelectOperation createStreamSelectOperation() {
		StreamSelectOperationImpl streamSelectOperation = new StreamSelectOperationImpl();
		return streamSelectOperation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ILPSolverType createILPSolverTypeFromString(EDataType eDataType, String initialValue) {
		ILPSolverType result = ILPSolverType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertILPSolverTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ObjectiveTarget createObjectiveTargetFromString(EDataType eDataType, String initialValue) {
		ObjectiveTarget result = ObjectiveTarget.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertObjectiveTargetToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public RelationalOperator createRelationalOperatorFromString(EDataType eDataType, String initialValue) {
		RelationalOperator result = RelationalOperator.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertRelationalOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public BinaryArithmeticOperator createBinaryArithmeticOperatorFromString(EDataType eDataType, String initialValue) {
		BinaryArithmeticOperator result = BinaryArithmeticOperator.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertBinaryArithmeticOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public UnaryArithmeticOperator createUnaryArithmeticOperatorFromString(EDataType eDataType, String initialValue) {
		UnaryArithmeticOperator result = UnaryArithmeticOperator.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertUnaryArithmeticOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public StreamArithmeticOperator createStreamArithmeticOperatorFromString(EDataType eDataType, String initialValue) {
		StreamArithmeticOperator result = StreamArithmeticOperator.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertStreamArithmeticOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public BinaryBoolOperator createBinaryBoolOperatorFromString(EDataType eDataType, String initialValue) {
		BinaryBoolOperator result = BinaryBoolOperator.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertBinaryBoolOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public UnaryBoolOperator createUnaryBoolOperatorFromString(EDataType eDataType, String initialValue) {
		UnaryBoolOperator result = UnaryBoolOperator.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertUnaryBoolOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public StreamBoolOperator createStreamBoolOperatorFromString(EDataType eDataType, String initialValue) {
		StreamBoolOperator result = StreamBoolOperator.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertStreamBoolOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public GipsIntermediatePackage getGipsIntermediatePackage() {
		return (GipsIntermediatePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static GipsIntermediatePackage getPackage() {
		return GipsIntermediatePackage.eINSTANCE;
	}

} // GipsIntermediateFactoryImpl
