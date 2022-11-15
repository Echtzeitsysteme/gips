/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pattern Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.PatternMapping#getPattern <em>Pattern</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getPatternMapping()
 * @model
 * @generated
 */
public interface PatternMapping extends Mapping {
	/**
	 * Returns the value of the '<em><b>Pattern</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pattern</em>' reference.
	 * @see #setPattern(GTPattern)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getPatternMapping_Pattern()
	 * @model required="true"
	 * @generated
	 */
	GTPattern getPattern();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.PatternMapping#getPattern <em>Pattern</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pattern</em>' reference.
	 * @see #getPattern()
	 * @generated
	 */
	void setPattern(GTPattern value);

} // PatternMapping
