/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate;

import org.eclipse.emf.ecore.EClassifier;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Stream
 * Expression</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression#getReturnType
 * <em>Return Type</em>}</li>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression#getCurrent
 * <em>Current</em>}</li>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression#getChild
 * <em>Child</em>}</li>
 * </ul>
 *
 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getStreamExpression()
 * @model
 * @generated
 */
public interface StreamExpression extends SetOperation {
	/**
	 * Returns the value of the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Return Type</em>' reference.
	 * @see #setReturnType(EClassifier)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getStreamExpression_ReturnType()
	 * @model
	 * @generated
	 */
	EClassifier getReturnType();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression#getReturnType
	 * <em>Return Type</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Return Type</em>' reference.
	 * @see #getReturnType()
	 * @generated
	 */
	void setReturnType(EClassifier value);

	/**
	 * Returns the value of the '<em><b>Current</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Current</em>' containment reference.
	 * @see #setCurrent(StreamOperation)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getStreamExpression_Current()
	 * @model containment="true" required="true"
	 * @generated
	 */
	StreamOperation getCurrent();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression#getCurrent
	 * <em>Current</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Current</em>' containment reference.
	 * @see #getCurrent()
	 * @generated
	 */
	void setCurrent(StreamOperation value);

	/**
	 * Returns the value of the '<em><b>Child</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Child</em>' containment reference.
	 * @see #setChild(StreamExpression)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getStreamExpression_Child()
	 * @model containment="true" required="true"
	 * @generated
	 */
	StreamExpression getChild();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression#getChild
	 * <em>Child</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Child</em>' containment reference.
	 * @see #getChild()
	 * @generated
	 */
	void setChild(StreamExpression value);

} // StreamExpression
