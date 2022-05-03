/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Arithmetic Value</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticValue#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getArithmeticValue()
 * @model
 * @generated
 */
public interface ArithmeticValue extends ArithmeticValueExpression {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Value</em>' containment reference.
	 * @see #setValue(ValueExpression)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getArithmeticValue_Value()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ValueExpression getValue();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticValue#getValue <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(ValueExpression value);

} // ArithmeticValue
