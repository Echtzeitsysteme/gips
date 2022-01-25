/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sum Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.SumExpression#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.SumExpression#getCondition <em>Condition</em>}</li>
 * </ul>
 *
 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getSumExpression()
 * @model abstract="true"
 * @generated
 */
public interface SumExpression extends Iterator, ArithmeticExpression {
	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(ArithmeticExpression)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getSumExpression_Expression()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ArithmeticExpression getExpression();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.SumExpression#getExpression <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(ArithmeticExpression value);

	/**
	 * Returns the value of the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition</em>' containment reference.
	 * @see #setCondition(BoolExpression)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getSumExpression_Condition()
	 * @model containment="true"
	 * @generated
	 */
	BoolExpression getCondition();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.SumExpression#getCondition <em>Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition</em>' containment reference.
	 * @see #getCondition()
	 * @generated
	 */
	void setCondition(BoolExpression value);

} // SumExpression
