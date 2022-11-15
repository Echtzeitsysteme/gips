/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bool Literal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolLiteral#isLiteral <em>Literal</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getBoolLiteral()
 * @model
 * @generated
 */
public interface BoolLiteral extends BoolValueExpression {
	/**
	 * Returns the value of the '<em><b>Literal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Literal</em>' attribute.
	 * @see #setLiteral(boolean)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getBoolLiteral_Literal()
	 * @model
	 * @generated
	 */
	boolean isLiteral();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolLiteral#isLiteral <em>Literal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Literal</em>' attribute.
	 * @see #isLiteral()
	 * @generated
	 */
	void setLiteral(boolean value);

} // BoolLiteral
