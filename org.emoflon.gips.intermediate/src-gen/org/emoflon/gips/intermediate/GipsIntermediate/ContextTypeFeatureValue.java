/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Context
 * Type Feature Value</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeFeatureValue#getFeatureExpression
 * <em>Feature Expression</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getContextTypeFeatureValue()
 * @model
 * @generated
 */
public interface ContextTypeFeatureValue extends ContextTypeValue {
	/**
	 * Returns the value of the '<em><b>Feature Expression</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Feature Expression</em>' containment reference.
	 * @see #setFeatureExpression(FeatureExpression)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getContextTypeFeatureValue_FeatureExpression()
	 * @model containment="true"
	 * @generated
	 */
	FeatureExpression getFeatureExpression();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeFeatureValue#getFeatureExpression
	 * <em>Feature Expression</em>}' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Feature Expression</em>' containment
	 *              reference.
	 * @see #getFeatureExpression()
	 * @generated
	 */
	void setFeatureExpression(FeatureExpression value);

} // ContextTypeFeatureValue
