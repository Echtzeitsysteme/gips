/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Bool
 * Binary Expression</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolBinaryExpression#getLhs
 * <em>Lhs</em>}</li>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolBinaryExpression#getRhs
 * <em>Rhs</em>}</li>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolBinaryExpression#getOperator
 * <em>Operator</em>}</li>
 * </ul>
 *
 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getBoolBinaryExpression()
 * @model
 * @generated
 */
public interface BoolBinaryExpression extends BoolExpression {
	/**
	 * Returns the value of the '<em><b>Lhs</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Lhs</em>' containment reference.
	 * @see #setLhs(BoolExpression)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getBoolBinaryExpression_Lhs()
	 * @model containment="true" required="true"
	 * @generated
	 */
	BoolExpression getLhs();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolBinaryExpression#getLhs
	 * <em>Lhs</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Lhs</em>' containment reference.
	 * @see #getLhs()
	 * @generated
	 */
	void setLhs(BoolExpression value);

	/**
	 * Returns the value of the '<em><b>Rhs</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Rhs</em>' containment reference.
	 * @see #setRhs(BoolExpression)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getBoolBinaryExpression_Rhs()
	 * @model containment="true" required="true"
	 * @generated
	 */
	BoolExpression getRhs();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolBinaryExpression#getRhs
	 * <em>Rhs</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Rhs</em>' containment reference.
	 * @see #getRhs()
	 * @generated
	 */
	void setRhs(BoolExpression value);

	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute. The literals
	 * are from the enumeration
	 * {@link org.emoflon.roam.intermediate.RoamIntermediate.BinaryBoolOperator}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BinaryBoolOperator
	 * @see #setOperator(BinaryBoolOperator)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getBoolBinaryExpression_Operator()
	 * @model
	 * @generated
	 */
	BinaryBoolOperator getOperator();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolBinaryExpression#getOperator
	 * <em>Operator</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BinaryBoolOperator
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(BinaryBoolOperator value);

} // BoolBinaryExpression
