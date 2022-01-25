/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variable Sum Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.VariableSumExpression#getVariable <em>Variable</em>}</li>
 * </ul>
 *
 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getVariableSumExpression()
 * @model
 * @generated
 */
public interface VariableSumExpression extends SumExpression {
	/**
	 * Returns the value of the '<em><b>Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variable</em>' reference.
	 * @see #setVariable(VariableSet)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getVariableSumExpression_Variable()
	 * @model required="true"
	 * @generated
	 */
	VariableSet getVariable();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.VariableSumExpression#getVariable <em>Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Variable</em>' reference.
	 * @see #getVariable()
	 * @generated
	 */
	void setVariable(VariableSet value);

} // VariableSumExpression
