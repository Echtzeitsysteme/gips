/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Sum
 * Expression</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.SumExpression#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.SumExpression#getFilter <em>Filter</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getSumExpression()
 * @model abstract="true"
 * @generated
 */
public interface SumExpression extends ValueExpression, SetOperation {
	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(ArithmeticExpression)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getSumExpression_Expression()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ArithmeticExpression getExpression();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.SumExpression#getExpression <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(ArithmeticExpression value);

	/**
	 * Returns the value of the '<em><b>Filter</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Filter</em>' containment reference.
	 * @see #setFilter(StreamExpression)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getSumExpression_Filter()
	 * @model containment="true"
	 * @generated
	 */
	StreamExpression getFilter();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.SumExpression#getFilter <em>Filter</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Filter</em>' containment reference.
	 * @see #getFilter()
	 * @generated
	 */
	void setFilter(StreamExpression value);

} // SumExpression
