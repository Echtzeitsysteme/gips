/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Stream Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamOperation#getOperator <em>Operator</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamOperation#getPredicate <em>Predicate</em>}</li>
 * </ul>
 *
 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getStreamOperation()
 * @model
 * @generated
 */
public interface StreamOperation extends StreamExpression {
	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link org.emoflon.roam.intermediate.RoamIntermediate.StreamOperator}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.StreamOperator
	 * @see #setOperator(StreamOperator)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getStreamOperation_Operator()
	 * @model
	 * @generated
	 */
	StreamOperator getOperator();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamOperation#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.StreamOperator
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(StreamOperator value);

	/**
	 * Returns the value of the '<em><b>Predicate</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Predicate</em>' containment reference.
	 * @see #setPredicate(BoolExpression)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getStreamOperation_Predicate()
	 * @model containment="true"
	 * @generated
	 */
	BoolExpression getPredicate();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamOperation#getPredicate <em>Predicate</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Predicate</em>' containment reference.
	 * @see #getPredicate()
	 * @generated
	 */
	void setPredicate(BoolExpression value);

} // StreamOperation
