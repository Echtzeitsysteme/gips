/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Constraint</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getName
 * <em>Name</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#isDepending
 * <em>Depending</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getExpression
 * <em>Expression</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#isConstant
 * <em>Constant</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#isNegated
 * <em>Negated</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getDependencies
 * <em>Dependencies</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getReferencedBy
 * <em>Referenced By</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getSymbolicVariable
 * <em>Symbolic Variable</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getHelperVariables
 * <em>Helper Variables</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getHelperConstraints
 * <em>Helper Constraints</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getConstraint()
 * @model abstract="true"
 * @generated
 */
public interface Constraint extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getConstraint_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getName
	 * <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Depending</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Depending</em>' attribute.
	 * @see #setDepending(boolean)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getConstraint_Depending()
	 * @model
	 * @generated
	 */
	boolean isDepending();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#isDepending
	 * <em>Depending</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Depending</em>' attribute.
	 * @see #isDepending()
	 * @generated
	 */
	void setDepending(boolean value);

	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(BoolExpression)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getConstraint_Expression()
	 * @model containment="true" required="true"
	 * @generated
	 */
	BoolExpression getExpression();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getExpression
	 * <em>Expression</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Expression</em>' containment
	 *              reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(BoolExpression value);

	/**
	 * Returns the value of the '<em><b>Constant</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Constant</em>' attribute.
	 * @see #setConstant(boolean)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getConstraint_Constant()
	 * @model
	 * @generated
	 */
	boolean isConstant();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#isConstant
	 * <em>Constant</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Constant</em>' attribute.
	 * @see #isConstant()
	 * @generated
	 */
	void setConstant(boolean value);

	/**
	 * Returns the value of the '<em><b>Negated</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Negated</em>' attribute.
	 * @see #setNegated(boolean)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getConstraint_Negated()
	 * @model
	 * @generated
	 */
	boolean isNegated();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#isNegated
	 * <em>Negated</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Negated</em>' attribute.
	 * @see #isNegated()
	 * @generated
	 */
	void setNegated(boolean value);

	/**
	 * Returns the value of the '<em><b>Dependencies</b></em>' reference list. The
	 * list contents are of type
	 * {@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint}. It is
	 * bidirectional and its opposite is
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getReferencedBy
	 * <em>Referenced By</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Dependencies</em>' reference list.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getConstraint_Dependencies()
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getReferencedBy
	 * @model opposite="referencedBy"
	 * @generated
	 */
	EList<Constraint> getDependencies();

	/**
	 * Returns the value of the '<em><b>Referenced By</b></em>' reference. It is
	 * bidirectional and its opposite is
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getDependencies
	 * <em>Dependencies</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Referenced By</em>' reference.
	 * @see #setReferencedBy(Constraint)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getConstraint_ReferencedBy()
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getDependencies
	 * @model opposite="dependencies"
	 * @generated
	 */
	Constraint getReferencedBy();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getReferencedBy
	 * <em>Referenced By</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Referenced By</em>' reference.
	 * @see #getReferencedBy()
	 * @generated
	 */
	void setReferencedBy(Constraint value);

	/**
	 * Returns the value of the '<em><b>Symbolic Variable</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Symbolic Variable</em>' reference.
	 * @see #setSymbolicVariable(Variable)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getConstraint_SymbolicVariable()
	 * @model
	 * @generated
	 */
	Variable getSymbolicVariable();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getSymbolicVariable
	 * <em>Symbolic Variable</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Symbolic Variable</em>' reference.
	 * @see #getSymbolicVariable()
	 * @generated
	 */
	void setSymbolicVariable(Variable value);

	/**
	 * Returns the value of the '<em><b>Helper Variables</b></em>' reference list.
	 * The list contents are of type
	 * {@link org.emoflon.gips.intermediate.GipsIntermediate.Variable}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Helper Variables</em>' reference list.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getConstraint_HelperVariables()
	 * @model
	 * @generated
	 */
	EList<Variable> getHelperVariables();

	/**
	 * Returns the value of the '<em><b>Helper Constraints</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Helper Constraints</em>' containment reference
	 *         list.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getConstraint_HelperConstraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<RelationalExpression> getHelperConstraints();

} // Constraint
