/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.emoflon.roam.intermediate.RoamIntermediate.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RoamIntermediateFactoryImpl extends EFactoryImpl implements RoamIntermediateFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RoamIntermediateFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case RoamIntermediatePackage.ROAM_INTERMEDIATE_MODEL:
			return createRoamIntermediateModel();
		case RoamIntermediatePackage.TYPE:
			return createType();
		case RoamIntermediatePackage.MAPPING:
			return createMapping();
		case RoamIntermediatePackage.TYPE_CONSTRAINT:
			return createTypeConstraint();
		case RoamIntermediatePackage.MAPPING_CONSTRAINT:
			return createMappingConstraint();
		case RoamIntermediatePackage.BINARY_ARITHMETIC_EXPRESSION:
			return createBinaryArithmeticExpression();
		case RoamIntermediatePackage.UNARY_ARITHMETIC_EXPRESSION:
			return createUnaryArithmeticExpression();
		case RoamIntermediatePackage.ARITHMETIC_STREAM_EXPRESSION:
			return createArithmeticStreamExpression();
		case RoamIntermediatePackage.MAPPING_SUM_EXPRESSION:
			return createMappingSumExpression();
		case RoamIntermediatePackage.TYPE_SUM_EXPRESSION:
			return createTypeSumExpression();
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
		case RoamIntermediatePackage.CONTEXT_TYPE_VALUE:
			return createContextTypeValue();
		case RoamIntermediatePackage.CONTEXT_MAPPING_NODE:
			return createContextMappingNode();
		case RoamIntermediatePackage.ITERATOR_VALUE:
			return createIteratorValue();
		case RoamIntermediatePackage.FEATURE_EXPRESSION:
			return createFeatureExpression();
		case RoamIntermediatePackage.FEATURE_LITERAL:
			return createFeatureLiteral();
		case RoamIntermediatePackage.CONTEXT_TYPE_FEATURE_VALUE:
			return createContextTypeFeatureValue();
		case RoamIntermediatePackage.CONTEXT_MAPPING_NODE_FEATURE_VALUE:
			return createContextMappingNodeFeatureValue();
		case RoamIntermediatePackage.ITERATOR_FEATURE_VALUE:
			return createIteratorFeatureValue();
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RoamIntermediateModel createRoamIntermediateModel() {
		RoamIntermediateModelImpl roamIntermediateModel = new RoamIntermediateModelImpl();
		return roamIntermediateModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Type createType() {
		TypeImpl type = new TypeImpl();
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Mapping createMapping() {
		MappingImpl mapping = new MappingImpl();
		return mapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeConstraint createTypeConstraint() {
		TypeConstraintImpl typeConstraint = new TypeConstraintImpl();
		return typeConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MappingConstraint createMappingConstraint() {
		MappingConstraintImpl mappingConstraint = new MappingConstraintImpl();
		return mappingConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BinaryArithmeticExpression createBinaryArithmeticExpression() {
		BinaryArithmeticExpressionImpl binaryArithmeticExpression = new BinaryArithmeticExpressionImpl();
		return binaryArithmeticExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UnaryArithmeticExpression createUnaryArithmeticExpression() {
		UnaryArithmeticExpressionImpl unaryArithmeticExpression = new UnaryArithmeticExpressionImpl();
		return unaryArithmeticExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArithmeticStreamExpression createArithmeticStreamExpression() {
		ArithmeticStreamExpressionImpl arithmeticStreamExpression = new ArithmeticStreamExpressionImpl();
		return arithmeticStreamExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MappingSumExpression createMappingSumExpression() {
		MappingSumExpressionImpl mappingSumExpression = new MappingSumExpressionImpl();
		return mappingSumExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeSumExpression createTypeSumExpression() {
		TypeSumExpressionImpl typeSumExpression = new TypeSumExpressionImpl();
		return typeSumExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArithmeticValue createArithmeticValue() {
		ArithmeticValueImpl arithmeticValue = new ArithmeticValueImpl();
		return arithmeticValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegerLiteral createIntegerLiteral() {
		IntegerLiteralImpl integerLiteral = new IntegerLiteralImpl();
		return integerLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DoubleLiteral createDoubleLiteral() {
		DoubleLiteralImpl doubleLiteral = new DoubleLiteralImpl();
		return doubleLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BoolBinaryExpression createBoolBinaryExpression() {
		BoolBinaryExpressionImpl boolBinaryExpression = new BoolBinaryExpressionImpl();
		return boolBinaryExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BoolUnaryExpression createBoolUnaryExpression() {
		BoolUnaryExpressionImpl boolUnaryExpression = new BoolUnaryExpressionImpl();
		return boolUnaryExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BoolValue createBoolValue() {
		BoolValueImpl boolValue = new BoolValueImpl();
		return boolValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BoolStreamExpression createBoolStreamExpression() {
		BoolStreamExpressionImpl boolStreamExpression = new BoolStreamExpressionImpl();
		return boolStreamExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RelationalExpression createRelationalExpression() {
		RelationalExpressionImpl relationalExpression = new RelationalExpressionImpl();
		return relationalExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BoolLiteral createBoolLiteral() {
		BoolLiteralImpl boolLiteral = new BoolLiteralImpl();
		return boolLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContextTypeValue createContextTypeValue() {
		ContextTypeValueImpl contextTypeValue = new ContextTypeValueImpl();
		return contextTypeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContextMappingNode createContextMappingNode() {
		ContextMappingNodeImpl contextMappingNode = new ContextMappingNodeImpl();
		return contextMappingNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IteratorValue createIteratorValue() {
		IteratorValueImpl iteratorValue = new IteratorValueImpl();
		return iteratorValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureExpression createFeatureExpression() {
		FeatureExpressionImpl featureExpression = new FeatureExpressionImpl();
		return featureExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureLiteral createFeatureLiteral() {
		FeatureLiteralImpl featureLiteral = new FeatureLiteralImpl();
		return featureLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContextTypeFeatureValue createContextTypeFeatureValue() {
		ContextTypeFeatureValueImpl contextTypeFeatureValue = new ContextTypeFeatureValueImpl();
		return contextTypeFeatureValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContextMappingNodeFeatureValue createContextMappingNodeFeatureValue() {
		ContextMappingNodeFeatureValueImpl contextMappingNodeFeatureValue = new ContextMappingNodeFeatureValueImpl();
		return contextMappingNodeFeatureValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IteratorFeatureValue createIteratorFeatureValue() {
		IteratorFeatureValueImpl iteratorFeatureValue = new IteratorFeatureValueImpl();
		return iteratorFeatureValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StreamExpression createStreamExpression() {
		StreamExpressionImpl streamExpression = new StreamExpressionImpl();
		return streamExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StreamFilterOperation createStreamFilterOperation() {
		StreamFilterOperationImpl streamFilterOperation = new StreamFilterOperationImpl();
		return streamFilterOperation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StreamSelectOperation createStreamSelectOperation() {
		StreamSelectOperationImpl streamSelectOperation = new StreamSelectOperationImpl();
		return streamSelectOperation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRelationalOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertBinaryArithmeticOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertUnaryArithmeticOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertStreamArithmeticOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertBinaryBoolOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertUnaryBoolOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertStreamBoolOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RoamIntermediatePackage getRoamIntermediatePackage() {
		return (RoamIntermediatePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static RoamIntermediatePackage getPackage() {
		return RoamIntermediatePackage.eINSTANCE;
	}

} //RoamIntermediateFactoryImpl
