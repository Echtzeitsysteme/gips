/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Simple
 * Variable Set</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.SimpleVariableSet#getVariables
 * <em>Variables</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getSimpleVariableSet()
 * @model
 * @generated
 */
public interface SimpleVariableSet extends VariableSet {
	/**
	 * Returns the value of the '<em><b>Variables</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Variables</em>' reference.
	 * @see #setVariables(Variable)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getSimpleVariableSet_Variables()
	 * @model
	 * @generated
	 */
	EList<Variable> getVariables();

} // SimpleVariableSet
