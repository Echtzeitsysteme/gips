/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Stream
 * Contains Operation</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamContainsOperation#getExpr
 * <em>Expr</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getStreamContainsOperation()
 * @model
 * @generated
 */
public interface StreamContainsOperation extends StreamOperation {
	/**
	 * Returns the value of the '<em><b>Expr</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Expr</em>' containment reference.
	 * @see #setExpr(ValueExpression)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getStreamContainsOperation_Expr()
	 * @model containment="true"
	 * @generated
	 */
	ValueExpression getExpr();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamContainsOperation#getExpr
	 * <em>Expr</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Expr</em>' containment reference.
	 * @see #getExpr()
	 * @generated
	 */
	void setExpr(ValueExpression value);

} // StreamContainsOperation
