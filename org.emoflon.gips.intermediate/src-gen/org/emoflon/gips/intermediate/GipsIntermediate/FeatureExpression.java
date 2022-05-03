/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Feature
 * Expression</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.FeatureExpression#getCurrent
 * <em>Current</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.FeatureExpression#getChild
 * <em>Child</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getFeatureExpression()
 * @model
 * @generated
 */
public interface FeatureExpression extends EObject {
	/**
	 * Returns the value of the '<em><b>Current</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Current</em>' containment reference.
	 * @see #setCurrent(FeatureLiteral)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getFeatureExpression_Current()
	 * @model containment="true" required="true"
	 * @generated
	 */
	FeatureLiteral getCurrent();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.FeatureExpression#getCurrent
	 * <em>Current</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Current</em>' containment reference.
	 * @see #getCurrent()
	 * @generated
	 */
	void setCurrent(FeatureLiteral value);

	/**
	 * Returns the value of the '<em><b>Child</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Child</em>' containment reference.
	 * @see #setChild(FeatureExpression)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getFeatureExpression_Child()
	 * @model containment="true" required="true"
	 * @generated
	 */
	FeatureExpression getChild();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.FeatureExpression#getChild
	 * <em>Child</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Child</em>' containment reference.
	 * @see #getChild()
	 * @generated
	 */
	void setChild(FeatureExpression value);

} // FeatureExpression
