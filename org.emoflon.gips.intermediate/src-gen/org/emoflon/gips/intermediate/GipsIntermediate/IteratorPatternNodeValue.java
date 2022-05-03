/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Iterator
 * Pattern Node Value</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeValue#getPatternContext <em>Pattern Context</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeValue#getNode <em>Node</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getIteratorPatternNodeValue()
 * @model
 * @generated
 */
public interface IteratorPatternNodeValue extends ValueExpression, Iterator {
	/**
	 * Returns the value of the '<em><b>Pattern Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Pattern Context</em>' reference.
	 * @see #setPatternContext(Pattern)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getIteratorPatternNodeValue_PatternContext()
	 * @model
	 * @generated
	 */
	Pattern getPatternContext();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeValue#getPatternContext <em>Pattern Context</em>}' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Pattern Context</em>' reference.
	 * @see #getPatternContext()
	 * @generated
	 */
	void setPatternContext(Pattern value);

	/**
	 * Returns the value of the '<em><b>Node</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Node</em>' reference.
	 * @see #setNode(IBeXNode)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getIteratorPatternNodeValue_Node()
	 * @model
	 * @generated
	 */
	IBeXNode getNode();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeValue#getNode <em>Node</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Node</em>' reference.
	 * @see #getNode()
	 * @generated
	 */
	void setNode(IBeXNode value);

} // IteratorPatternNodeValue
