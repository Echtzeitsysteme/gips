/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.TypeConstraint#getModelType <em>Model Type</em>}</li>
 * </ul>
 *
 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getTypeConstraint()
 * @model
 * @generated
 */
public interface TypeConstraint extends Context, Constraint {
	/**
	 * Returns the value of the '<em><b>Model Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Model Type</em>' reference.
	 * @see #setModelType(EClass)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getTypeConstraint_ModelType()
	 * @model
	 * @generated
	 */
	EClass getModelType();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.TypeConstraint#getModelType <em>Model Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Model Type</em>' reference.
	 * @see #getModelType()
	 * @generated
	 */
	void setModelType(EClass value);

} // TypeConstraint
