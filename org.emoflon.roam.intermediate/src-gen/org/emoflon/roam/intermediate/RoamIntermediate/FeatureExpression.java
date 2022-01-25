/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.FeatureExpression#getCurrent <em>Current</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.FeatureExpression#getChild <em>Child</em>}</li>
 * </ul>
 *
 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getFeatureExpression()
 * @model
 * @generated
 */
public interface FeatureExpression extends EObject {
	/**
	 * Returns the value of the '<em><b>Current</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Current</em>' reference.
	 * @see #setCurrent(FeatureLiteral)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getFeatureExpression_Current()
	 * @model required="true"
	 * @generated
	 */
	FeatureLiteral getCurrent();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.FeatureExpression#getCurrent <em>Current</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Current</em>' reference.
	 * @see #getCurrent()
	 * @generated
	 */
	void setCurrent(FeatureLiteral value);

	/**
	 * Returns the value of the '<em><b>Child</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Child</em>' reference.
	 * @see #setChild(FeatureExpression)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getFeatureExpression_Child()
	 * @model required="true"
	 * @generated
	 */
	FeatureExpression getChild();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.FeatureExpression#getChild <em>Child</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Child</em>' reference.
	 * @see #getChild()
	 * @generated
	 */
	void setChild(FeatureExpression value);

} // FeatureExpression
