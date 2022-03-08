/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Set
 * Operation</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.SetOperation#getOperandName
 * <em>Operand Name</em>}</li>
 * </ul>
 *
 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getSetOperation()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface SetOperation extends EObject {
	/**
	 * Returns the value of the '<em><b>Operand Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Operand Name</em>' attribute.
	 * @see #setOperandName(String)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getSetOperation_OperandName()
	 * @model
	 * @generated
	 */
	String getOperandName();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.roam.intermediate.RoamIntermediate.SetOperation#getOperandName
	 * <em>Operand Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Operand Name</em>' attribute.
	 * @see #getOperandName()
	 * @generated
	 */
	void setOperandName(String value);

} // SetOperation
