/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Binary
 * Arithmetic Expression</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression#getLhs
 * <em>Lhs</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression#getRhs
 * <em>Rhs</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression#getOperator
 * <em>Operator</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getBinaryArithmeticExpression()
 * @model
 * @generated
 */
public interface BinaryArithmeticExpression extends ArithmeticExpression {
	/**
	 * Returns the value of the '<em><b>Lhs</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Lhs</em>' containment reference.
	 * @see #setLhs(ArithmeticExpression)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getBinaryArithmeticExpression_Lhs()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ArithmeticExpression getLhs();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression#getLhs
	 * <em>Lhs</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Lhs</em>' containment reference.
	 * @see #getLhs()
	 * @generated
	 */
	void setLhs(ArithmeticExpression value);

	/**
	 * Returns the value of the '<em><b>Rhs</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Rhs</em>' containment reference.
	 * @see #setRhs(ArithmeticExpression)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getBinaryArithmeticExpression_Rhs()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ArithmeticExpression getRhs();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression#getRhs
	 * <em>Rhs</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Rhs</em>' containment reference.
	 * @see #getRhs()
	 * @generated
	 */
	void setRhs(ArithmeticExpression value);

	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute. The literals
	 * are from the enumeration
	 * {@link org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticOperator}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticOperator
	 * @see #setOperator(BinaryArithmeticOperator)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getBinaryArithmeticExpression_Operator()
	 * @model
	 * @generated
	 */
	BinaryArithmeticOperator getOperator();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression#getOperator
	 * <em>Operator</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticOperator
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(BinaryArithmeticOperator value);

} // BinaryArithmeticExpression
