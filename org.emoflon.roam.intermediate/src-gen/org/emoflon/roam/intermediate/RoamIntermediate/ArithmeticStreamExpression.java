/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Arithmetic Stream Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticStreamExpression#getStream <em>Stream</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticStreamExpression#getOperator <em>Operator</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticStreamExpression#getOperation <em>Operation</em>}</li>
 * </ul>
 *
 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getArithmeticStreamExpression()
 * @model
 * @generated
 */
public interface ArithmeticStreamExpression extends ArithmeticExpression {
	/**
	 * Returns the value of the '<em><b>Stream</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stream</em>' containment reference.
	 * @see #setStream(StreamExpression)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getArithmeticStreamExpression_Stream()
	 * @model containment="true"
	 * @generated
	 */
	StreamExpression getStream();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticStreamExpression#getStream <em>Stream</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Stream</em>' containment reference.
	 * @see #getStream()
	 * @generated
	 */
	void setStream(StreamExpression value);

	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link org.emoflon.roam.intermediate.RoamIntermediate.StreamArithmeticOperator}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.StreamArithmeticOperator
	 * @see #setOperator(StreamArithmeticOperator)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getArithmeticStreamExpression_Operator()
	 * @model
	 * @generated
	 */
	StreamArithmeticOperator getOperator();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticStreamExpression#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.StreamArithmeticOperator
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(StreamArithmeticOperator value);

	/**
	 * Returns the value of the '<em><b>Operation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operation</em>' containment reference.
	 * @see #setOperation(ArithmeticExpression)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getArithmeticStreamExpression_Operation()
	 * @model containment="true"
	 * @generated
	 */
	ArithmeticExpression getOperation();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticStreamExpression#getOperation <em>Operation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operation</em>' containment reference.
	 * @see #getOperation()
	 * @generated
	 */
	void setOperation(ArithmeticExpression value);

} // ArithmeticStreamExpression
