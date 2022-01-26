/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.Constraint#getName <em>Name</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.Constraint#getLhsConstant <em>Lhs Constant</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.Constraint#getRhsExpression <em>Rhs Expression</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.Constraint#getOperator <em>Operator</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.Constraint#isElementwise <em>Elementwise</em>}</li>
 * </ul>
 *
 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getConstraint()
 * @model abstract="true"
 * @generated
 */
public interface Constraint extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getConstraint_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.Constraint#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Lhs Constant</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lhs Constant</em>' containment reference.
	 * @see #setLhsConstant(ArithmeticValue)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getConstraint_LhsConstant()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ArithmeticValue getLhsConstant();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.Constraint#getLhsConstant <em>Lhs Constant</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lhs Constant</em>' containment reference.
	 * @see #getLhsConstant()
	 * @generated
	 */
	void setLhsConstant(ArithmeticValue value);

	/**
	 * Returns the value of the '<em><b>Rhs Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rhs Expression</em>' containment reference.
	 * @see #setRhsExpression(ArithmeticExpression)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getConstraint_RhsExpression()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ArithmeticExpression getRhsExpression();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.Constraint#getRhsExpression <em>Rhs Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rhs Expression</em>' containment reference.
	 * @see #getRhsExpression()
	 * @generated
	 */
	void setRhsExpression(ArithmeticExpression value);

	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link org.emoflon.roam.intermediate.RoamIntermediate.RelationalOperator}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RelationalOperator
	 * @see #setOperator(RelationalOperator)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getConstraint_Operator()
	 * @model
	 * @generated
	 */
	RelationalOperator getOperator();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.Constraint#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RelationalOperator
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(RelationalOperator value);

	/**
	 * Returns the value of the '<em><b>Elementwise</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Elementwise</em>' attribute.
	 * @see #setElementwise(boolean)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getConstraint_Elementwise()
	 * @model
	 * @generated
	 */
	boolean isElementwise();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.Constraint#isElementwise <em>Elementwise</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Elementwise</em>' attribute.
	 * @see #isElementwise()
	 * @generated
	 */
	void setElementwise(boolean value);

} // Constraint
