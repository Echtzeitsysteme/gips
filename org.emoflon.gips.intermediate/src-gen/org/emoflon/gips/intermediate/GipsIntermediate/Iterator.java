/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Iterator</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.Iterator#getStream <em>Stream</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getIterator()
 * @model abstract="true"
 * @generated
 */
public interface Iterator extends EObject {
	/**
	 * Returns the value of the '<em><b>Stream</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stream</em>' reference.
	 * @see #setStream(SetOperation)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getIterator_Stream()
	 * @model
	 * @generated
	 */
	SetOperation getStream();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.Iterator#getStream <em>Stream</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Stream</em>' reference.
	 * @see #getStream()
	 * @generated
	 */
	void setStream(SetOperation value);

} // Iterator
