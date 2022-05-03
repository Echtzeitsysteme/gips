/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Mapping
 * Constraint</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint#getMapping <em>Mapping</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getMappingConstraint()
 * @model
 * @generated
 */
public interface MappingConstraint extends Context, Constraint {
	/**
	 * Returns the value of the '<em><b>Mapping</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Mapping</em>' reference.
	 * @see #setMapping(Mapping)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getMappingConstraint_Mapping()
	 * @model required="true"
	 * @generated
	 */
	Mapping getMapping();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint#getMapping <em>Mapping</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mapping</em>' reference.
	 * @see #getMapping()
	 * @generated
	 */
	void setMapping(Mapping value);

} // MappingConstraint
