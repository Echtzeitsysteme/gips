/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Context
 * Mapping Node</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNode#getMappingContext <em>Mapping Context</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNode#getNode <em>Node</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getContextMappingNode()
 * @model
 * @generated
 */
public interface ContextMappingNode extends ValueExpression {
	/**
	 * Returns the value of the '<em><b>Mapping Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Mapping Context</em>' reference.
	 * @see #setMappingContext(Mapping)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getContextMappingNode_MappingContext()
	 * @model
	 * @generated
	 */
	Mapping getMappingContext();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNode#getMappingContext <em>Mapping Context</em>}' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Mapping Context</em>' reference.
	 * @see #getMappingContext()
	 * @generated
	 */
	void setMappingContext(Mapping value);

	/**
	 * Returns the value of the '<em><b>Node</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Node</em>' reference.
	 * @see #setNode(IBeXNode)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getContextMappingNode_Node()
	 * @model
	 * @generated
	 */
	IBeXNode getNode();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNode#getNode <em>Node</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Node</em>' reference.
	 * @see #getNode()
	 * @generated
	 */
	void setNode(IBeXNode value);

} // ContextMappingNode
