/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Arithmetic Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression#getReturnType <em>Return Type</em>}</li>
 * </ul>
 *
 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getArithmeticExpression()
 * @model abstract="true"
 * @generated
 */
public interface ArithmeticExpression extends EObject {
	/**
	 * Returns the value of the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Return Type</em>' reference.
	 * @see #setReturnType(EDataType)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getArithmeticExpression_ReturnType()
	 * @model required="true"
	 * @generated
	 */
	EDataType getReturnType();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression#getReturnType <em>Return Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Return Type</em>' reference.
	 * @see #getReturnType()
	 * @generated
	 */
	void setReturnType(EDataType value);

} // ArithmeticExpression