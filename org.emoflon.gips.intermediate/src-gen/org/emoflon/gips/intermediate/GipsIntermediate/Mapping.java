/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.Mapping#getContextPattern <em>Context Pattern</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getMapping()
 * @model abstract="true"
 * @generated
 */
public interface Mapping extends VariableSet {
	/**
	 * Returns the value of the '<em><b>Context Pattern</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Context Pattern</em>' reference.
	 * @see #setContextPattern(GTPattern)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getMapping_ContextPattern()
	 * @model required="true"
	 * @generated
	 */
	GTPattern getContextPattern();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.Mapping#getContextPattern <em>Context Pattern</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Context Pattern</em>' reference.
	 * @see #getContextPattern()
	 * @generated
	 */
	void setContextPattern(GTPattern value);

} // Mapping
