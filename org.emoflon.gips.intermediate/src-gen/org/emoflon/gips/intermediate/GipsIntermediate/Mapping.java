/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Mapping</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.Mapping#getRule
 * <em>Rule</em>}</li>
 * </ul>
 *
 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getMapping()
 * @model
 * @generated
 */
public interface Mapping extends VariableSet {
	/**
	 * Returns the value of the '<em><b>Rule</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Rule</em>' reference.
	 * @see #setRule(IBeXRule)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getMapping_Rule()
	 * @model required="true"
	 * @generated
	 */
	IBeXRule getRule();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.roam.intermediate.RoamIntermediate.Mapping#getRule
	 * <em>Rule</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Rule</em>' reference.
	 * @see #getRule()
	 * @generated
	 */
	void setRule(IBeXRule value);

} // Mapping
