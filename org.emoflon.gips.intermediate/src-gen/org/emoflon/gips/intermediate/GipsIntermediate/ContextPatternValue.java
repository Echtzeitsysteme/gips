/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Context
 * Pattern Value</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternValue#getPatternContext <em>Pattern Context</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getContextPatternValue()
 * @model
 * @generated
 */
public interface ContextPatternValue extends ValueExpression {
	/**
	 * Returns the value of the '<em><b>Pattern Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Pattern Context</em>' reference.
	 * @see #setPatternContext(Pattern)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getContextPatternValue_PatternContext()
	 * @model
	 * @generated
	 */
	Pattern getPatternContext();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternValue#getPatternContext <em>Pattern Context</em>}' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Pattern Context</em>' reference.
	 * @see #getPatternContext()
	 * @generated
	 */
	void setPatternContext(Pattern value);

} // ContextPatternValue
