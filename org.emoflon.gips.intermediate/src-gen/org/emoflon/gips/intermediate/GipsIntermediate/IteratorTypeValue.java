/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Iterator
 * Type Value</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeValue#getTypeContext <em>Type Context</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getIteratorTypeValue()
 * @model
 * @generated
 */
public interface IteratorTypeValue extends ValueExpression, Iterator {
	/**
	 * Returns the value of the '<em><b>Type Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Type Context</em>' reference.
	 * @see #setTypeContext(Type)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getIteratorTypeValue_TypeContext()
	 * @model
	 * @generated
	 */
	Type getTypeContext();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeValue#getTypeContext
	 * <em>Type Context</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Type Context</em>' reference.
	 * @see #getTypeContext()
	 * @generated
	 */
	void setTypeContext(Type value);

} // IteratorTypeValue
