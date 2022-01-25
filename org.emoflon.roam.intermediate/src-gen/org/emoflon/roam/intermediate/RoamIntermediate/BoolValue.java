/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bool Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolValue#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getBoolValue()
 * @model
 * @generated
 */
public interface BoolValue extends BoolExpression {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' containment reference.
	 * @see #setValue(ValueExpression)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getBoolValue_Value()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ValueExpression getValue();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolValue#getValue <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(ValueExpression value);

} // BoolValue
