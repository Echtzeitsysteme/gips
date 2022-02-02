/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Stream Filter Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamFilterOperation#getPredicate <em>Predicate</em>}</li>
 * </ul>
 *
 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getStreamFilterOperation()
 * @model
 * @generated
 */
public interface StreamFilterOperation extends StreamOperation {
	/**
	 * Returns the value of the '<em><b>Predicate</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Predicate</em>' containment reference.
	 * @see #setPredicate(BoolExpression)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getStreamFilterOperation_Predicate()
	 * @model containment="true"
	 * @generated
	 */
	BoolExpression getPredicate();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamFilterOperation#getPredicate <em>Predicate</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Predicate</em>' containment reference.
	 * @see #getPredicate()
	 * @generated
	 */
	void setPredicate(BoolExpression value);

} // StreamFilterOperation
