/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Context
 * Mapping Variables Reference</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingVariablesReference#getVar
 * <em>Var</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingVariablesReference#getMappingContext
 * <em>Mapping Context</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getContextMappingVariablesReference()
 * @model
 * @generated
 */
public interface ContextMappingVariablesReference extends ValueExpression {
	/**
	 * Returns the value of the '<em><b>Var</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Var</em>' containment reference.
	 * @see #setVar(VariableReference)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getContextMappingVariablesReference_Var()
	 * @model containment="true" required="true"
	 * @generated
	 */
	VariableReference getVar();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingVariablesReference#getVar
	 * <em>Var</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Var</em>' containment reference.
	 * @see #getVar()
	 * @generated
	 */
	void setVar(VariableReference value);

	/**
	 * Returns the value of the '<em><b>Mapping Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Mapping Context</em>' reference.
	 * @see #setMappingContext(Mapping)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getContextMappingVariablesReference_MappingContext()
	 * @model
	 * @generated
	 */
	Mapping getMappingContext();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingVariablesReference#getMappingContext
	 * <em>Mapping Context</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Mapping Context</em>' reference.
	 * @see #getMappingContext()
	 * @generated
	 */
	void setMappingContext(Mapping value);

} // ContextMappingVariablesReference
