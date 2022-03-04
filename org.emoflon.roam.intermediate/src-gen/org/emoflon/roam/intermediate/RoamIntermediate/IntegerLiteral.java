/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Integer
 * Literal</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.IntegerLiteral#getLiteral
 * <em>Literal</em>}</li>
 * </ul>
 *
 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getIntegerLiteral()
 * @model
 * @generated
 */
public interface IntegerLiteral extends ArithmeticLiteral {
	/**
	 * Returns the value of the '<em><b>Literal</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Literal</em>' attribute.
	 * @see #setLiteral(int)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getIntegerLiteral_Literal()
	 * @model required="true"
	 * @generated
	 */
	int getLiteral();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.roam.intermediate.RoamIntermediate.IntegerLiteral#getLiteral
	 * <em>Literal</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Literal</em>' attribute.
	 * @see #getLiteral()
	 * @generated
	 */
	void setLiteral(int value);

} // IntegerLiteral
