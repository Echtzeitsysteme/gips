/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Pattern</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.Pattern#getPattern
 * <em>Pattern</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.Pattern#isIsRule
 * <em>Is Rule</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getPattern()
 * @model
 * @generated
 */
public interface Pattern extends VariableSet {
	/**
	 * Returns the value of the '<em><b>Pattern</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Pattern</em>' reference.
	 * @see #setPattern(IBeXContext)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getPattern_Pattern()
	 * @model required="true"
	 * @generated
	 */
	IBeXContext getPattern();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Pattern#getPattern
	 * <em>Pattern</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Pattern</em>' reference.
	 * @see #getPattern()
	 * @generated
	 */
	void setPattern(IBeXContext value);

	/**
	 * Returns the value of the '<em><b>Is Rule</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Is Rule</em>' attribute.
	 * @see #setIsRule(boolean)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getPattern_IsRule()
	 * @model
	 * @generated
	 */
	boolean isIsRule();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Pattern#isIsRule
	 * <em>Is Rule</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Is Rule</em>' attribute.
	 * @see #isIsRule()
	 * @generated
	 */
	void setIsRule(boolean value);

} // Pattern
