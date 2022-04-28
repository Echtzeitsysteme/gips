/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Bool
 * Unary Expression</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolUnaryExpression#getExpression
 * <em>Expression</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolUnaryExpression#getOperator
 * <em>Operator</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getBoolUnaryExpression()
 * @model
 * @generated
 */
public interface BoolUnaryExpression extends BoolExpression {
	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(BoolExpression)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getBoolUnaryExpression_Expression()
	 * @model containment="true" required="true"
	 * @generated
	 */
	BoolExpression getExpression();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolUnaryExpression#getExpression
	 * <em>Expression</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Expression</em>' containment
	 *              reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(BoolExpression value);

	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute. The literals
	 * are from the enumeration
	 * {@link org.emoflon.gips.intermediate.GipsIntermediate.UnaryBoolOperator}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.UnaryBoolOperator
	 * @see #setOperator(UnaryBoolOperator)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getBoolUnaryExpression_Operator()
	 * @model
	 * @generated
	 */
	UnaryBoolOperator getOperator();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolUnaryExpression#getOperator
	 * <em>Operator</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.UnaryBoolOperator
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(UnaryBoolOperator value);

} // BoolUnaryExpression
