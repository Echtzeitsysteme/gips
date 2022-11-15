/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variable Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.VariableSet#getName <em>Name</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.VariableSet#getUpperBound <em>Upper Bound</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.VariableSet#getLowerBound <em>Lower Bound</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getVariableSet()
 * @model abstract="true"
 * @generated
 */
public interface VariableSet extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getVariableSet_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.VariableSet#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Upper Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Upper Bound</em>' attribute.
	 * @see #setUpperBound(double)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getVariableSet_UpperBound()
	 * @model
	 * @generated
	 */
	double getUpperBound();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.VariableSet#getUpperBound <em>Upper Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Upper Bound</em>' attribute.
	 * @see #getUpperBound()
	 * @generated
	 */
	void setUpperBound(double value);

	/**
	 * Returns the value of the '<em><b>Lower Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lower Bound</em>' attribute.
	 * @see #setLowerBound(double)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getVariableSet_LowerBound()
	 * @model
	 * @generated
	 */
	double getLowerBound();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.VariableSet#getLowerBound <em>Lower Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lower Bound</em>' attribute.
	 * @see #getLowerBound()
	 * @generated
	 */
	void setLowerBound(double value);

} // VariableSet
