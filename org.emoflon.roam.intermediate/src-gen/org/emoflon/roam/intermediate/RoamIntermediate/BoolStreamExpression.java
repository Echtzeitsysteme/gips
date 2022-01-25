/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bool Stream Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolStreamExpression#getStream <em>Stream</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolStreamExpression#getOperator <em>Operator</em>}</li>
 * </ul>
 *
 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getBoolStreamExpression()
 * @model
 * @generated
 */
public interface BoolStreamExpression extends BoolExpression {
	/**
	 * Returns the value of the '<em><b>Stream</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stream</em>' containment reference.
	 * @see #setStream(StreamExpression)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getBoolStreamExpression_Stream()
	 * @model containment="true" required="true"
	 * @generated
	 */
	StreamExpression getStream();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolStreamExpression#getStream <em>Stream</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Stream</em>' containment reference.
	 * @see #getStream()
	 * @generated
	 */
	void setStream(StreamExpression value);

	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link org.emoflon.roam.intermediate.RoamIntermediate.StreamBoolOperator}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.StreamBoolOperator
	 * @see #setOperator(StreamBoolOperator)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getBoolStreamExpression_Operator()
	 * @model
	 * @generated
	 */
	StreamBoolOperator getOperator();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolStreamExpression#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.StreamBoolOperator
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(StreamBoolOperator value);

} // BoolStreamExpression
