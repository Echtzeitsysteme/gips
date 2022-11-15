/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.Variable#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getVariable()
 * @model
 * @generated
 */
public interface Variable extends VariableSet {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.emoflon.gips.intermediate.GipsIntermediate.VariableType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.VariableType
	 * @see #setType(VariableType)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getVariable_Type()
	 * @model
	 * @generated
	 */
	VariableType getType();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.Variable#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.VariableType
	 * @see #getType()
	 * @generated
	 */
	void setType(VariableType value);

} // Variable
