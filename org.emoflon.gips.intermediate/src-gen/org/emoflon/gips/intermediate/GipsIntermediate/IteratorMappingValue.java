/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Iterator
 * Mapping Value</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingValue#getMappingContext
 * <em>Mapping Context</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getIteratorMappingValue()
 * @model
 * @generated
 */
public interface IteratorMappingValue extends ValueExpression, Iterator {
	/**
	 * Returns the value of the '<em><b>Mapping Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Mapping Context</em>' reference.
	 * @see #setMappingContext(Mapping)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getIteratorMappingValue_MappingContext()
	 * @model
	 * @generated
	 */
	Mapping getMappingContext();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingValue#getMappingContext
	 * <em>Mapping Context</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Mapping Context</em>' reference.
	 * @see #getMappingContext()
	 * @generated
	 */
	void setMappingContext(Mapping value);

} // IteratorMappingValue
