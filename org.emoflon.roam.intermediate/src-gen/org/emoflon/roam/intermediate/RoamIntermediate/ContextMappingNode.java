/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Context Mapping Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNode#getMappingContext <em>Mapping Context</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNode#getNode <em>Node</em>}</li>
 * </ul>
 *
 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getContextMappingNode()
 * @model
 * @generated
 */
public interface ContextMappingNode extends ValueExpression {
	/**
	 * Returns the value of the '<em><b>Mapping Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapping Context</em>' reference.
	 * @see #setMappingContext(MappingConstraint)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getContextMappingNode_MappingContext()
	 * @model
	 * @generated
	 */
	MappingConstraint getMappingContext();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNode#getMappingContext <em>Mapping Context</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mapping Context</em>' reference.
	 * @see #getMappingContext()
	 * @generated
	 */
	void setMappingContext(MappingConstraint value);

	/**
	 * Returns the value of the '<em><b>Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Node</em>' reference.
	 * @see #setNode(IBeXNode)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getContextMappingNode_Node()
	 * @model
	 * @generated
	 */
	IBeXNode getNode();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNode#getNode <em>Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Node</em>' reference.
	 * @see #getNode()
	 * @generated
	 */
	void setNode(IBeXNode value);

} // ContextMappingNode