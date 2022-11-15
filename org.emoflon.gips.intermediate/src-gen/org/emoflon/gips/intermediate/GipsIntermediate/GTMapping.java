/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GT Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.GTMapping#getRule <em>Rule</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getGTMapping()
 * @model
 * @generated
 */
public interface GTMapping extends Mapping {
	/**
	 * Returns the value of the '<em><b>Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule</em>' reference.
	 * @see #setRule(GTRule)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getGTMapping_Rule()
	 * @model required="true"
	 * @generated
	 */
	GTRule getRule();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.GTMapping#getRule <em>Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule</em>' reference.
	 * @see #getRule()
	 * @generated
	 */
	void setRule(GTRule value);

} // GTMapping
