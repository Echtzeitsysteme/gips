/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Context
 * Pattern Node</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNode#getPatternContext <em>Pattern Context</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNode#getNode <em>Node</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getContextPatternNode()
 * @model
 * @generated
 */
public interface ContextPatternNode extends ValueExpression {
	/**
	 * Returns the value of the '<em><b>Pattern Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Pattern Context</em>' reference.
	 * @see #setPatternContext(Pattern)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getContextPatternNode_PatternContext()
	 * @model
	 * @generated
	 */
	Pattern getPatternContext();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNode#getPatternContext <em>Pattern Context</em>}' reference.
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
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getContextPatternNode_Node()
	 * @model
	 * @generated
	 */
	IBeXNode getNode();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNode#getNode <em>Node</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Node</em>' reference.
	 * @see #getNode()
	 * @generated
	 */
	void setNode(IBeXNode value);

} // ContextPatternNode
