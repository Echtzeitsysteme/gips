/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage
 * @generated
 */
public interface RoamIntermediateFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RoamIntermediateFactory eINSTANCE = org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediateFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Model</em>'.
	 * @generated
	 */
	RoamIntermediateModel createRoamIntermediateModel();

	/**
	 * Returns a new object of class '<em>Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Type</em>'.
	 * @generated
	 */
	Type createType();

	/**
	 * Returns a new object of class '<em>Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mapping</em>'.
	 * @generated
	 */
	Mapping createMapping();

	/**
	 * Returns a new object of class '<em>Type Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Type Constraint</em>'.
	 * @generated
	 */
	TypeConstraint createTypeConstraint();

	/**
	 * Returns a new object of class '<em>Mapping Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mapping Constraint</em>'.
	 * @generated
	 */
	MappingConstraint createMappingConstraint();

	/**
	 * Returns a new object of class '<em>Arithmetic Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Arithmetic Expression</em>'.
	 * @generated
	 */
	ArithmeticExpression createArithmeticExpression();

	/**
	 * Returns a new object of class '<em>Binary Arithmetic Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Binary Arithmetic Expression</em>'.
	 * @generated
	 */
	BinaryArithmeticExpression createBinaryArithmeticExpression();

	/**
	 * Returns a new object of class '<em>Unary Arithmetic Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Unary Arithmetic Expression</em>'.
	 * @generated
	 */
	UnaryArithmeticExpression createUnaryArithmeticExpression();

	/**
	 * Returns a new object of class '<em>Arithmetic Stream Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Arithmetic Stream Expression</em>'.
	 * @generated
	 */
	ArithmeticStreamExpression createArithmeticStreamExpression();

	/**
	 * Returns a new object of class '<em>Mapping Sum Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mapping Sum Expression</em>'.
	 * @generated
	 */
	MappingSumExpression createMappingSumExpression();

	/**
	 * Returns a new object of class '<em>Type Sum Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Type Sum Expression</em>'.
	 * @generated
	 */
	TypeSumExpression createTypeSumExpression();

	/**
	 * Returns a new object of class '<em>Integer Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Integer Literal</em>'.
	 * @generated
	 */
	IntegerLiteral createIntegerLiteral();

	/**
	 * Returns a new object of class '<em>Double Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Double Literal</em>'.
	 * @generated
	 */
	DoubleLiteral createDoubleLiteral();

	/**
	 * Returns a new object of class '<em>Bool Binary Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bool Binary Expression</em>'.
	 * @generated
	 */
	BoolBinaryExpression createBoolBinaryExpression();

	/**
	 * Returns a new object of class '<em>Bool Unary Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bool Unary Expression</em>'.
	 * @generated
	 */
	BoolUnaryExpression createBoolUnaryExpression();

	/**
	 * Returns a new object of class '<em>Bool Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bool Value</em>'.
	 * @generated
	 */
	BoolValue createBoolValue();

	/**
	 * Returns a new object of class '<em>Bool Stream Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bool Stream Expression</em>'.
	 * @generated
	 */
	BoolStreamExpression createBoolStreamExpression();

	/**
	 * Returns a new object of class '<em>Relational Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Relational Expression</em>'.
	 * @generated
	 */
	RelationalExpression createRelationalExpression();

	/**
	 * Returns a new object of class '<em>Bool Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bool Literal</em>'.
	 * @generated
	 */
	BoolLiteral createBoolLiteral();

	/**
	 * Returns a new object of class '<em>Context Type Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Context Type Value</em>'.
	 * @generated
	 */
	ContextTypeValue createContextTypeValue();

	/**
	 * Returns a new object of class '<em>Context Mapping Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Context Mapping Node</em>'.
	 * @generated
	 */
	ContextMappingNode createContextMappingNode();

	/**
	 * Returns a new object of class '<em>Iterator Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Iterator Value</em>'.
	 * @generated
	 */
	IteratorValue createIteratorValue();

	/**
	 * Returns a new object of class '<em>Feature Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Feature Expression</em>'.
	 * @generated
	 */
	FeatureExpression createFeatureExpression();

	/**
	 * Returns a new object of class '<em>Feature Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Feature Literal</em>'.
	 * @generated
	 */
	FeatureLiteral createFeatureLiteral();

	/**
	 * Returns a new object of class '<em>Context Type Feature Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Context Type Feature Value</em>'.
	 * @generated
	 */
	ContextTypeFeatureValue createContextTypeFeatureValue();

	/**
	 * Returns a new object of class '<em>Context Mapping Node Feature Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Context Mapping Node Feature Value</em>'.
	 * @generated
	 */
	ContextMappingNodeFeatureValue createContextMappingNodeFeatureValue();

	/**
	 * Returns a new object of class '<em>Iterator Feature Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Iterator Feature Value</em>'.
	 * @generated
	 */
	IteratorFeatureValue createIteratorFeatureValue();

	/**
	 * Returns a new object of class '<em>Stream Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Stream Expression</em>'.
	 * @generated
	 */
	StreamExpression createStreamExpression();

	/**
	 * Returns a new object of class '<em>Stream Operation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Stream Operation</em>'.
	 * @generated
	 */
	StreamOperation createStreamOperation();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	RoamIntermediatePackage getRoamIntermediatePackage();

} //RoamIntermediateFactory
