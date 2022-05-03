/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Type Sum
 * Expression</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.TypeSumExpression#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getTypeSumExpression()
 * @model
 * @generated
 */
public interface TypeSumExpression extends SumExpression {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(Type)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getTypeSumExpression_Type()
	 * @model required="true"
	 * @generated
	 */
	Type getType();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.TypeSumExpression#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(Type value);

} // TypeSumExpression
