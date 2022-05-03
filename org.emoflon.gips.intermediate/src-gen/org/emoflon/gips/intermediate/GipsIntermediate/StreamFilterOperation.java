/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Stream
 * Filter Operation</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamFilterOperation#getPredicate <em>Predicate</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getStreamFilterOperation()
 * @model
 * @generated
 */
public interface StreamFilterOperation extends StreamOperation {
	/**
	 * Returns the value of the '<em><b>Predicate</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Predicate</em>' containment reference.
	 * @see #setPredicate(BoolExpression)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getStreamFilterOperation_Predicate()
	 * @model containment="true"
	 * @generated
	 */
	BoolExpression getPredicate();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamFilterOperation#getPredicate <em>Predicate</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Predicate</em>' containment reference.
	 * @see #getPredicate()
	 * @generated
	 */
	void setPredicate(BoolExpression value);

} // StreamFilterOperation
