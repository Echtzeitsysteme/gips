/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Variable
 * Set</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.VariableSet#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getVariableSet()
 * @model abstract="true"
 * @generated
 */
public interface VariableSet extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getVariableSet_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.VariableSet#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // VariableSet
