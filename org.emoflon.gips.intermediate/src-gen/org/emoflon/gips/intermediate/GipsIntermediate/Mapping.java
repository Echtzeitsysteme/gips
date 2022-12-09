/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

import org.eclipse.emf.common.util.EList;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Mapping</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.Mapping#getContextPattern
 * <em>Context Pattern</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.Mapping#getFreeVariables
 * <em>Free Variables</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.Mapping#getBoundVariables
 * <em>Bound Variables</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getMapping()
 * @model abstract="true"
 * @generated
 */
public interface Mapping extends VariableSet {
	/**
	 * Returns the value of the '<em><b>Context Pattern</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Context Pattern</em>' reference.
	 * @see #setContextPattern(IBeXContextPattern)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getMapping_ContextPattern()
	 * @model required="true"
	 * @generated
	 */
	IBeXContextPattern getContextPattern();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Mapping#getContextPattern
	 * <em>Context Pattern</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Context Pattern</em>' reference.
	 * @see #getContextPattern()
	 * @generated
	 */
	void setContextPattern(IBeXContextPattern value);

	/**
	 * Returns the value of the '<em><b>Free Variables</b></em>' reference list. The
	 * list contents are of type
	 * {@link org.emoflon.gips.intermediate.GipsIntermediate.Variable}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Free Variables</em>' reference list.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getMapping_FreeVariables()
	 * @model
	 * @generated
	 */
	EList<Variable> getFreeVariables();

	/**
	 * Returns the value of the '<em><b>Bound Variables</b></em>' reference list.
	 * The list contents are of type
	 * {@link org.emoflon.gips.intermediate.GipsIntermediate.GTParameterVariable}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Bound Variables</em>' reference list.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getMapping_BoundVariables()
	 * @model
	 * @generated
	 */
	EList<GTParameterVariable> getBoundVariables();

} // Mapping
