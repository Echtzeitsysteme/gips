/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXParameter;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>GT
 * Parameter Variable</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.GTParameterVariable#getParameter
 * <em>Parameter</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.GTParameterVariable#getRule
 * <em>Rule</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getGTParameterVariable()
 * @model
 * @generated
 */
public interface GTParameterVariable extends Variable {
	/**
	 * Returns the value of the '<em><b>Parameter</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Parameter</em>' reference.
	 * @see #setParameter(IBeXParameter)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getGTParameterVariable_Parameter()
	 * @model required="true"
	 * @generated
	 */
	IBeXParameter getParameter();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.GTParameterVariable#getParameter
	 * <em>Parameter</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Parameter</em>' reference.
	 * @see #getParameter()
	 * @generated
	 */
	void setParameter(IBeXParameter value);

	/**
	 * Returns the value of the '<em><b>Rule</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Rule</em>' reference.
	 * @see #setRule(IBeXRule)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getGTParameterVariable_Rule()
	 * @model required="true"
	 * @generated
	 */
	IBeXRule getRule();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.GTParameterVariable#getRule
	 * <em>Rule</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Rule</em>' reference.
	 * @see #getRule()
	 * @generated
	 */
	void setRule(IBeXRule value);

} // GTParameterVariable
