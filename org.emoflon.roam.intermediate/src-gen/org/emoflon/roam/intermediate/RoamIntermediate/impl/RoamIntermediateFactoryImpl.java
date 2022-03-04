/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticValue;
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticOperator;
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryBoolOperator;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolBinaryExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolStreamExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolUnaryExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNode;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNodeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextPatternNode;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextPatternNodeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextPatternValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeValue;
import org.emoflon.roam.intermediate.RoamIntermediate.DoubleLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.FeatureExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.FeatureLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.GlobalObjective;
import org.emoflon.roam.intermediate.RoamIntermediate.IntegerLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorPatternFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorPatternNodeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorPatternNodeValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorPatternValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorTypeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorTypeValue;
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingConstraint;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingObjective;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingSumExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.Objective;
import org.emoflon.roam.intermediate.RoamIntermediate.ObjectiveFunctionValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ObjectiveTarget;
import org.emoflon.roam.intermediate.RoamIntermediate.Pattern;
import org.emoflon.roam.intermediate.RoamIntermediate.PatternConstraint;
import org.emoflon.roam.intermediate.RoamIntermediate.PatternObjective;
import org.emoflon.roam.intermediate.RoamIntermediate.RelationalExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.RelationalOperator;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateFactory;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamArithmeticOperator;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamBoolOperator;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamFilterOperation;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamSelectOperation;
import org.emoflon.roam.intermediate.RoamIntermediate.Type;
import org.emoflon.roam.intermediate.RoamIntermediate.TypeConstraint;
import org.emoflon.roam.intermediate.RoamIntermediate.TypeObjective;
import org.emoflon.roam.intermediate.RoamIntermediate.TypeSumExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticOperator;
import org.emoflon.roam.intermediate.RoamIntermediate.UnaryBoolOperator;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 *
 * @generated
 */
public class RoamIntermediateFactoryImpl extends EFactoryImpl implements RoamIntermediateFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	public static RoamIntermediateFactory init() {
		try {
			RoamIntermediateFactory theRoamIntermediateFactory = (RoamIntermediateFactory) EPackage.Registry.INSTANCE
					.getEFactory(RoamIntermediatePackage.eNS_URI);
			if (theRoamIntermediateFactory != null) {
				return theRoamIntermediateFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new RoamIntermediateFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 */
	public RoamIntermediateFactoryImpl() {
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
		case RoamIntermediatePackage.ROAM_INTERMEDIATE_MODEL:
			return createRoamIntermediateModel();
		case RoamIntermediatePackage.PATTERN:
			return createPattern();
		case RoamIntermediatePackage.TYPE:
			return createType();
		case RoamIntermediatePackage.MAPPING:
			return createMapping();
		case RoamIntermediatePackage.OBJECTIVE:
			return createObjective();
		case RoamIntermediatePackage.GLOBAL_OBJECTIVE:
			return createGlobalObjective();
		case RoamIntermediatePackage.PATTERN_CONSTRAINT:
			return createPatternConstraint();
		case RoamIntermediatePackage.TYPE_CONSTRAINT:
			return createTypeConstraint();
		case RoamIntermediatePackage.MAPPING_CONSTRAINT:
			return createMappingConstraint();
		case RoamIntermediatePackage.PATTERN_OBJECTIVE:
			return createPatternObjective();
		case RoamIntermediatePackage.TYPE_OBJECTIVE:
			return createTypeObjective();
		case RoamIntermediatePackage.MAPPING_OBJECTIVE:
			return createMappingObjective();
		case RoamIntermediatePackage.BINARY_ARITHMETIC_EXPRESSION:
			return createBinaryArithmeticExpression();
		case RoamIntermediatePackage.UNARY_ARITHMETIC_EXPRESSION:
			return createUnaryArithmeticExpression();
		case RoamIntermediatePackage.ARITHMETIC_VALUE:
			return createArithmeticValue();
		case RoamIntermediatePackage.INTEGER_LITERAL:
			return createIntegerLiteral();
		case RoamIntermediatePackage.DOUBLE_LITERAL:
			return createDoubleLiteral();
		case RoamIntermediatePackage.BOOL_BINARY_EXPRESSION:
			return createBoolBinaryExpression();
		case RoamIntermediatePackage.BOOL_UNARY_EXPRESSION:
			return createBoolUnaryExpression();
		case RoamIntermediatePackage.BOOL_VALUE:
			return createBoolValue();
		case RoamIntermediatePackage.BOOL_STREAM_EXPRESSION:
			return createBoolStreamExpression();
		case RoamIntermediatePackage.RELATIONAL_EXPRESSION:
			return createRelationalExpression();
		case RoamIntermediatePackage.BOOL_LITERAL:
			return createBoolLiteral();
		case RoamIntermediatePackage.MAPPING_SUM_EXPRESSION:
			return createMappingSumExpression();
		case RoamIntermediatePackage.TYPE_SUM_EXPRESSION:
			return createTypeSumExpression();
		case RoamIntermediatePackage.CONTEXT_TYPE_VALUE:
			return createContextTypeValue();
		case RoamIntermediatePackage.CONTEXT_PATTERN_VALUE:
			return createContextPatternValue();
		case RoamIntermediatePackage.CONTEXT_PATTERN_NODE:
			return createContextPatternNode();
		case RoamIntermediatePackage.CONTEXT_MAPPING_VALUE:
			return createContextMappingValue();
		case RoamIntermediatePackage.CONTEXT_MAPPING_NODE:
			return createContextMappingNode();
		case RoamIntermediatePackage.OBJECTIVE_FUNCTION_VALUE:
			return createObjectiveFunctionValue();
		case RoamIntermediatePackage.FEATURE_EXPRESSION:
			return createFeatureExpression();
		case RoamIntermediatePackage.FEATURE_LITERAL:
			return createFeatureLiteral();
		case RoamIntermediatePackage.CONTEXT_TYPE_FEATURE_VALUE:
			return createContextTypeFeatureValue();
		case RoamIntermediatePackage.CONTEXT_PATTERN_NODE_FEATURE_VALUE:
			return createContextPatternNodeFeatureValue();
		case RoamIntermediatePackage.CONTEXT_MAPPING_NODE_FEATURE_VALUE:
			return createContextMappingNodeFeatureValue();
		case RoamIntermediatePackage.ITERATOR_PATTERN_VALUE:
			return createIteratorPatternValue();
		case RoamIntermediatePackage.ITERATOR_PATTERN_FEATURE_VALUE:
			return createIteratorPatternFeatureValue();
		case RoamIntermediatePackage.ITERATOR_PATTERN_NODE_VALUE:
			return createIteratorPatternNodeValue();
		case RoamIntermediatePackage.ITERATOR_PATTERN_NODE_FEATURE_VALUE:
			return createIteratorPatternNodeFeatureValue();
		case RoamIntermediatePackage.ITERATOR_MAPPING_VALUE:
			return createIteratorMappingValue();
		case RoamIntermediatePackage.ITERATOR_MAPPING_FEATURE_VALUE:
			return createIteratorMappingFeatureValue();
		case RoamIntermediatePackage.ITERATOR_MAPPING_NODE_VALUE:
			return createIteratorMappingNodeValue();
		case RoamIntermediatePackage.ITERATOR_MAPPING_NODE_FEATURE_VALUE:
			return createIteratorMappingNodeFeatureValue();
		case RoamIntermediatePackage.ITERATOR_TYPE_VALUE:
			return createIteratorTypeValue();
		case RoamIntermediatePackage.ITERATOR_TYPE_FEATURE_VALUE:
			return createIteratorTypeFeatureValue();
		case RoamIntermediatePackage.STREAM_EXPRESSION:
			return createStreamExpression();
		case RoamIntermediatePackage.STREAM_FILTER_OPERATION:
			return createStreamFilterOperation();
		case RoamIntermediatePackage.STREAM_SELECT_OPERATION:
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
		case RoamIntermediatePackage.OBJECTIVE_TARGET:
			return createObjectiveTargetFromString(eDataType, initialValue);
		case RoamIntermediatePackage.RELATIONAL_OPERATOR:
			return createRelationalOperatorFromString(eDataType, initialValue);
		case RoamIntermediatePackage.BINARY_ARITHMETIC_OPERATOR:
			return createBinaryArithmeticOperatorFromString(eDataType, initialValue);
		case RoamIntermediatePackage.UNARY_ARITHMETIC_OPERATOR:
			return createUnaryArithmeticOperatorFromString(eDataType, initialValue);
		case RoamIntermediatePackage.STREAM_ARITHMETIC_OPERATOR:
			return createStreamArithmeticOperatorFromString(eDataType, initialValue);
		case RoamIntermediatePackage.BINARY_BOOL_OPERATOR:
			return createBinaryBoolOperatorFromString(eDataType, initialValue);
		case RoamIntermediatePackage.UNARY_BOOL_OPERATOR:
			return createUnaryBoolOperatorFromString(eDataType, initialValue);
		case RoamIntermediatePackage.STREAM_BOOL_OPERATOR:
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
		case RoamIntermediatePackage.OBJECTIVE_TARGET:
			return convertObjectiveTargetToString(eDataType, instanceValue);
		case RoamIntermediatePackage.RELATIONAL_OPERATOR:
			return convertRelationalOperatorToString(eDataType, instanceValue);
		case RoamIntermediatePackage.BINARY_ARITHMETIC_OPERATOR:
			return convertBinaryArithmeticOperatorToString(eDataType, instanceValue);
		case RoamIntermediatePackage.UNARY_ARITHMETIC_OPERATOR:
			return convertUnaryArithmeticOperatorToString(eDataType, instanceValue);
		case RoamIntermediatePackage.STREAM_ARITHMETIC_OPERATOR:
			return convertStreamArithmeticOperatorToString(eDataType, instanceValue);
		case RoamIntermediatePackage.BINARY_BOOL_OPERATOR:
			return convertBinaryBoolOperatorToString(eDataType, instanceValue);
		case RoamIntermediatePackage.UNARY_BOOL_OPERATOR:
			return convertUnaryBoolOperatorToString(eDataType, instanceValue);
		case RoamIntermediatePackage.STREAM_BOOL_OPERATOR:
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
	@Override
	public RoamIntermediateModel createRoamIntermediateModel() {
		RoamIntermediateModelImpl roamIntermediateModel = new RoamIntermediateModelImpl();
		return roamIntermediateModel;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Pattern createPattern() {
		PatternImpl pattern = new PatternImpl();
		return pattern;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Type createType() {
		TypeImpl type = new TypeImpl();
		return type;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Mapping createMapping() {
		MappingImpl mapping = new MappingImpl();
		return mapping;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Objective createObjective() {
		ObjectiveImpl objective = new ObjectiveImpl();
		return objective;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public GlobalObjective createGlobalObjective() {
		GlobalObjectiveImpl globalObjective = new GlobalObjectiveImpl();
		return globalObjective;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public PatternConstraint createPatternConstraint() {
		PatternConstraintImpl patternConstraint = new PatternConstraintImpl();
		return patternConstraint;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public TypeConstraint createTypeConstraint() {
		TypeConstraintImpl typeConstraint = new TypeConstraintImpl();
		return typeConstraint;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public MappingConstraint createMappingConstraint() {
		MappingConstraintImpl mappingConstraint = new MappingConstraintImpl();
		return mappingConstraint;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public PatternObjective createPatternObjective() {
		PatternObjectiveImpl patternObjective = new PatternObjectiveImpl();
		return patternObjective;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public MappingObjective createMappingObjective() {
		MappingObjectiveImpl mappingObjective = new MappingObjectiveImpl();
		return mappingObjective;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public TypeObjective createTypeObjective() {
		TypeObjectiveImpl typeObjective = new TypeObjectiveImpl();
		return typeObjective;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public BinaryArithmeticExpression createBinaryArithmeticExpression() {
		BinaryArithmeticExpressionImpl binaryArithmeticExpression = new BinaryArithmeticExpressionImpl();
		return binaryArithmeticExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public UnaryArithmeticExpression createUnaryArithmeticExpression() {
		UnaryArithmeticExpressionImpl unaryArithmeticExpression = new UnaryArithmeticExpressionImpl();
		return unaryArithmeticExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ArithmeticValue createArithmeticValue() {
		ArithmeticValueImpl arithmeticValue = new ArithmeticValueImpl();
		return arithmeticValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public IntegerLiteral createIntegerLiteral() {
		IntegerLiteralImpl integerLiteral = new IntegerLiteralImpl();
		return integerLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public DoubleLiteral createDoubleLiteral() {
		DoubleLiteralImpl doubleLiteral = new DoubleLiteralImpl();
		return doubleLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public BoolBinaryExpression createBoolBinaryExpression() {
		BoolBinaryExpressionImpl boolBinaryExpression = new BoolBinaryExpressionImpl();
		return boolBinaryExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public BoolUnaryExpression createBoolUnaryExpression() {
		BoolUnaryExpressionImpl boolUnaryExpression = new BoolUnaryExpressionImpl();
		return boolUnaryExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public BoolValue createBoolValue() {
		BoolValueImpl boolValue = new BoolValueImpl();
		return boolValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public BoolStreamExpression createBoolStreamExpression() {
		BoolStreamExpressionImpl boolStreamExpression = new BoolStreamExpressionImpl();
		return boolStreamExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public RelationalExpression createRelationalExpression() {
		RelationalExpressionImpl relationalExpression = new RelationalExpressionImpl();
		return relationalExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public BoolLiteral createBoolLiteral() {
		BoolLiteralImpl boolLiteral = new BoolLiteralImpl();
		return boolLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public MappingSumExpression createMappingSumExpression() {
		MappingSumExpressionImpl mappingSumExpression = new MappingSumExpressionImpl();
		return mappingSumExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public TypeSumExpression createTypeSumExpression() {
		TypeSumExpressionImpl typeSumExpression = new TypeSumExpressionImpl();
		return typeSumExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ContextTypeValue createContextTypeValue() {
		ContextTypeValueImpl contextTypeValue = new ContextTypeValueImpl();
		return contextTypeValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ContextPatternValue createContextPatternValue() {
		ContextPatternValueImpl contextPatternValue = new ContextPatternValueImpl();
		return contextPatternValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ContextPatternNode createContextPatternNode() {
		ContextPatternNodeImpl contextPatternNode = new ContextPatternNodeImpl();
		return contextPatternNode;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ContextMappingValue createContextMappingValue() {
		ContextMappingValueImpl contextMappingValue = new ContextMappingValueImpl();
		return contextMappingValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ContextMappingNode createContextMappingNode() {
		ContextMappingNodeImpl contextMappingNode = new ContextMappingNodeImpl();
		return contextMappingNode;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ObjectiveFunctionValue createObjectiveFunctionValue() {
		ObjectiveFunctionValueImpl objectiveFunctionValue = new ObjectiveFunctionValueImpl();
		return objectiveFunctionValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public FeatureExpression createFeatureExpression() {
		FeatureExpressionImpl featureExpression = new FeatureExpressionImpl();
		return featureExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public FeatureLiteral createFeatureLiteral() {
		FeatureLiteralImpl featureLiteral = new FeatureLiteralImpl();
		return featureLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ContextTypeFeatureValue createContextTypeFeatureValue() {
		ContextTypeFeatureValueImpl contextTypeFeatureValue = new ContextTypeFeatureValueImpl();
		return contextTypeFeatureValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ContextPatternNodeFeatureValue createContextPatternNodeFeatureValue() {
		ContextPatternNodeFeatureValueImpl contextPatternNodeFeatureValue = new ContextPatternNodeFeatureValueImpl();
		return contextPatternNodeFeatureValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ContextMappingNodeFeatureValue createContextMappingNodeFeatureValue() {
		ContextMappingNodeFeatureValueImpl contextMappingNodeFeatureValue = new ContextMappingNodeFeatureValueImpl();
		return contextMappingNodeFeatureValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public IteratorPatternValue createIteratorPatternValue() {
		IteratorPatternValueImpl iteratorPatternValue = new IteratorPatternValueImpl();
		return iteratorPatternValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public IteratorPatternFeatureValue createIteratorPatternFeatureValue() {
		IteratorPatternFeatureValueImpl iteratorPatternFeatureValue = new IteratorPatternFeatureValueImpl();
		return iteratorPatternFeatureValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public IteratorPatternNodeValue createIteratorPatternNodeValue() {
		IteratorPatternNodeValueImpl iteratorPatternNodeValue = new IteratorPatternNodeValueImpl();
		return iteratorPatternNodeValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public IteratorPatternNodeFeatureValue createIteratorPatternNodeFeatureValue() {
		IteratorPatternNodeFeatureValueImpl iteratorPatternNodeFeatureValue = new IteratorPatternNodeFeatureValueImpl();
		return iteratorPatternNodeFeatureValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public IteratorMappingValue createIteratorMappingValue() {
		IteratorMappingValueImpl iteratorMappingValue = new IteratorMappingValueImpl();
		return iteratorMappingValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public IteratorMappingFeatureValue createIteratorMappingFeatureValue() {
		IteratorMappingFeatureValueImpl iteratorMappingFeatureValue = new IteratorMappingFeatureValueImpl();
		return iteratorMappingFeatureValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public IteratorMappingNodeValue createIteratorMappingNodeValue() {
		IteratorMappingNodeValueImpl iteratorMappingNodeValue = new IteratorMappingNodeValueImpl();
		return iteratorMappingNodeValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public IteratorMappingNodeFeatureValue createIteratorMappingNodeFeatureValue() {
		IteratorMappingNodeFeatureValueImpl iteratorMappingNodeFeatureValue = new IteratorMappingNodeFeatureValueImpl();
		return iteratorMappingNodeFeatureValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public IteratorTypeValue createIteratorTypeValue() {
		IteratorTypeValueImpl iteratorTypeValue = new IteratorTypeValueImpl();
		return iteratorTypeValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public IteratorTypeFeatureValue createIteratorTypeFeatureValue() {
		IteratorTypeFeatureValueImpl iteratorTypeFeatureValue = new IteratorTypeFeatureValueImpl();
		return iteratorTypeFeatureValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public StreamExpression createStreamExpression() {
		StreamExpressionImpl streamExpression = new StreamExpressionImpl();
		return streamExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public StreamFilterOperation createStreamFilterOperation() {
		StreamFilterOperationImpl streamFilterOperation = new StreamFilterOperationImpl();
		return streamFilterOperation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public StreamSelectOperation createStreamSelectOperation() {
		StreamSelectOperationImpl streamSelectOperation = new StreamSelectOperationImpl();
		return streamSelectOperation;
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
	@Override
	public RoamIntermediatePackage getRoamIntermediatePackage() {
		return (RoamIntermediatePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static RoamIntermediatePackage getPackage() {
		return RoamIntermediatePackage.eINSTANCE;
	}

} // RoamIntermediateFactoryImpl
