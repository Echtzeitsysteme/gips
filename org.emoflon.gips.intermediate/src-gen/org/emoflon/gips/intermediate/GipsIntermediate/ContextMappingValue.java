/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Context
 * Mapping Value</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingValue#getMappingContext <em>Mapping Context</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getContextMappingValue()
 * @model
 * @generated
 */
public interface ContextMappingValue extends ValueExpression {
	/**
	 * Returns the value of the '<em><b>Mapping Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Mapping Context</em>' reference.
	 * @see #setMappingContext(Mapping)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getContextMappingValue_MappingContext()
	 * @model
	 * @generated
	 */
	Mapping getMappingContext();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingValue#getMappingContext <em>Mapping Context</em>}' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Mapping Context</em>' reference.
	 * @see #getMappingContext()
	 * @generated
	 */
	void setMappingContext(Mapping value);

} // ContextMappingValue
