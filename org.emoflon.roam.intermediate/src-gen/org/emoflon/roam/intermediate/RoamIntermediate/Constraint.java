/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Constraint</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.Constraint#getName
 * <em>Name</em>}</li>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.Constraint#isElementwise
 * <em>Elementwise</em>}</li>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.Constraint#getExpression
 * <em>Expression</em>}</li>
 * </ul>
 *
 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getConstraint()
 * @model abstract="true"
 * @generated
 */
public interface Constraint extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getConstraint_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.roam.intermediate.RoamIntermediate.Constraint#getName
	 * <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Elementwise</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Elementwise</em>' attribute.
	 * @see #setElementwise(boolean)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getConstraint_Elementwise()
	 * @model
	 * @generated
	 */
	boolean isElementwise();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.roam.intermediate.RoamIntermediate.Constraint#isElementwise
	 * <em>Elementwise</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @param value the new value of the '<em>Elementwise</em>' attribute.
	 * @see #isElementwise()
	 * @generated
	 */
	void setElementwise(boolean value);

	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(RelationalExpression)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getConstraint_Expression()
	 * @model containment="true" required="true"
	 * @generated
	 */
	RelationalExpression getExpression();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.roam.intermediate.RoamIntermediate.Constraint#getExpression
	 * <em>Expression</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @param value the new value of the '<em>Expression</em>' containment
	 *              reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(RelationalExpression value);

} // Constraint
