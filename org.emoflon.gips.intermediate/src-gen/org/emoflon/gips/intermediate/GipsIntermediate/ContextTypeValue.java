/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Context
 * Type Value</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeValue#getTypeContext
 * <em>Type Context</em>}</li>
 * </ul>
 *
 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getContextTypeValue()
 * @model
 * @generated
 */
public interface ContextTypeValue extends ValueExpression {
	/**
	 * Returns the value of the '<em><b>Type Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Type Context</em>' reference.
	 * @see #setTypeContext(Type)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getContextTypeValue_TypeContext()
	 * @model
	 * @generated
	 */
	Type getTypeContext();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeValue#getTypeContext
	 * <em>Type Context</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Type Context</em>' reference.
	 * @see #getTypeContext()
	 * @generated
	 */
	void setTypeContext(Type value);

} // ContextTypeValue
