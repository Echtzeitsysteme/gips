/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Unary
 * Arithmetic Expression</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticExpression#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticExpression#getOperator <em>Operator</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getUnaryArithmeticExpression()
 * @model
 * @generated
 */
public interface UnaryArithmeticExpression extends ArithmeticExpression {
	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(ArithmeticExpression)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getUnaryArithmeticExpression_Expression()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ArithmeticExpression getExpression();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticExpression#getExpression <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(ArithmeticExpression value);

	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticOperator}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticOperator
	 * @see #setOperator(UnaryArithmeticOperator)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getUnaryArithmeticExpression_Operator()
	 * @model
	 * @generated
	 */
	UnaryArithmeticOperator getOperator();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticExpression#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticOperator
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(UnaryArithmeticOperator value);

} // UnaryArithmeticExpression
