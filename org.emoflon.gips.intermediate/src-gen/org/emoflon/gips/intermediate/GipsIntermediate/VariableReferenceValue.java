/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Variable
 * Reference Value</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.VariableReferenceValue#getVar
 * <em>Var</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getVariableReferenceValue()
 * @model
 * @generated
 */
public interface VariableReferenceValue extends ValueExpression {
	/**
	 * Returns the value of the '<em><b>Var</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Var</em>' containment reference.
	 * @see #setVar(VariableReference)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getVariableReferenceValue_Var()
	 * @model containment="true" required="true"
	 * @generated
	 */
	VariableReference getVar();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.VariableReferenceValue#getVar
	 * <em>Var</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Var</em>' containment reference.
	 * @see #getVar()
	 * @generated
	 */
	void setVar(VariableReference value);

} // VariableReferenceValue
