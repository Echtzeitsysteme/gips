/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Stream
 * Select Operation</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamSelectOperation#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getStreamSelectOperation()
 * @model
 * @generated
 */
public interface StreamSelectOperation extends StreamOperation {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Type</em>' containment reference.
	 * @see #setType(EClass)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getStreamSelectOperation_Type()
	 * @model containment="true"
	 * @generated
	 */
	EClass getType();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamSelectOperation#getType <em>Type</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' containment reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EClass value);

} // StreamSelectOperation
