/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Iterator Pattern Feature Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternFeatureValue#getPatternContext <em>Pattern Context</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternFeatureValue#getFeatureExpression <em>Feature Expression</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getIteratorPatternFeatureValue()
 * @model
 * @generated
 */
public interface IteratorPatternFeatureValue extends ValueExpression, Iterator {
	/**
	 * Returns the value of the '<em><b>Pattern Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pattern Context</em>' reference.
	 * @see #setPatternContext(Pattern)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getIteratorPatternFeatureValue_PatternContext()
	 * @model
	 * @generated
	 */
	Pattern getPatternContext();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternFeatureValue#getPatternContext <em>Pattern Context</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pattern Context</em>' reference.
	 * @see #getPatternContext()
	 * @generated
	 */
	void setPatternContext(Pattern value);

	/**
	 * Returns the value of the '<em><b>Feature Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature Expression</em>' containment reference.
	 * @see #setFeatureExpression(FeatureExpression)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getIteratorPatternFeatureValue_FeatureExpression()
	 * @model containment="true"
	 * @generated
	 */
	FeatureExpression getFeatureExpression();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternFeatureValue#getFeatureExpression <em>Feature Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feature Expression</em>' containment reference.
	 * @see #getFeatureExpression()
	 * @generated
	 */
	void setFeatureExpression(FeatureExpression value);

} // IteratorPatternFeatureValue
