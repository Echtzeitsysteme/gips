/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Iterator
 * Type Feature Value</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeFeatureValue#getFeatureExpression
 * <em>Feature Expression</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getIteratorTypeFeatureValue()
 * @model
 * @generated
 */
public interface IteratorTypeFeatureValue extends IteratorTypeValue {
	/**
	 * Returns the value of the '<em><b>Feature Expression</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Feature Expression</em>' containment reference.
	 * @see #setFeatureExpression(FeatureExpression)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getIteratorTypeFeatureValue_FeatureExpression()
	 * @model containment="true"
	 * @generated
	 */
	FeatureExpression getFeatureExpression();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeFeatureValue#getFeatureExpression
	 * <em>Feature Expression</em>}' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Feature Expression</em>' containment
	 *              reference.
	 * @see #getFeatureExpression()
	 * @generated
	 */
	void setFeatureExpression(FeatureExpression value);

} // IteratorTypeFeatureValue
