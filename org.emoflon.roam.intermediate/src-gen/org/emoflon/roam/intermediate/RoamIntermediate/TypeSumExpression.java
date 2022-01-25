/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Sum Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.TypeSumExpression#getObjType <em>Obj Type</em>}</li>
 * </ul>
 *
 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getTypeSumExpression()
 * @model
 * @generated
 */
public interface TypeSumExpression extends SumExpression {
	/**
	 * Returns the value of the '<em><b>Obj Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Obj Type</em>' reference.
	 * @see #setObjType(EClass)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getTypeSumExpression_ObjType()
	 * @model required="true"
	 * @generated
	 */
	EClass getObjType();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.TypeSumExpression#getObjType <em>Obj Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Obj Type</em>' reference.
	 * @see #getObjType()
	 * @generated
	 */
	void setObjType(EClass value);

} // TypeSumExpression
