/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Global
 * Objective</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.GlobalObjective#getExpression
 * <em>Expression</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.GlobalObjective#getTarget
 * <em>Target</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getGlobalObjective()
 * @model
 * @generated
 */
public interface GlobalObjective extends EObject {
	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(ArithmeticExpression)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getGlobalObjective_Expression()
	 * @model containment="true"
	 * @generated
	 */
	ArithmeticExpression getExpression();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.GlobalObjective#getExpression
	 * <em>Expression</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Expression</em>' containment
	 *              reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(ArithmeticExpression value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' attribute. The literals are
	 * from the enumeration
	 * {@link org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveTarget}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Target</em>' attribute.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveTarget
	 * @see #setTarget(ObjectiveTarget)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getGlobalObjective_Target()
	 * @model
	 * @generated
	 */
	ObjectiveTarget getTarget();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.GlobalObjective#getTarget
	 * <em>Target</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Target</em>' attribute.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveTarget
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(ObjectiveTarget value);

} // GlobalObjective
