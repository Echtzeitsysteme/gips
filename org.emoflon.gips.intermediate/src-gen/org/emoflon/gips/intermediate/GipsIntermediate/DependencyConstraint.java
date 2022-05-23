/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Dependency Constraint</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.DependencyConstraint#getDependencies
 * <em>Dependencies</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.DependencyConstraint#getRealVarCorrectnessConstraints
 * <em>Real Var Correctness Constraints</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.DependencyConstraint#getBinaryVarCorrectnessConstraints
 * <em>Binary Var Correctness Constraints</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getDependencyConstraint()
 * @model
 * @generated
 */
public interface DependencyConstraint extends Constraint {
	/**
	 * Returns the value of the '<em><b>Dependencies</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Dependencies</em>' containment reference list.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getDependencyConstraint_Dependencies()
	 * @model containment="true"
	 * @generated
	 */
	EList<Constraint> getDependencies();

	/**
	 * Returns the value of the '<em><b>Real Var Correctness Constraints</b></em>'
	 * containment reference list. The list contents are of type
	 * {@link org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Real Var Correctness Constraints</em>'
	 *         containment reference list.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getDependencyConstraint_RealVarCorrectnessConstraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<RelationalExpression> getRealVarCorrectnessConstraints();

	/**
	 * Returns the value of the '<em><b>Binary Var Correctness Constraints</b></em>'
	 * containment reference list. The list contents are of type
	 * {@link org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Binary Var Correctness Constraints</em>'
	 *         containment reference list.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getDependencyConstraint_BinaryVarCorrectnessConstraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<RelationalExpression> getBinaryVarCorrectnessConstraints();

} // DependencyConstraint
