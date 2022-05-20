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
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getDependencyConstraint()
 * @model
 * @generated
 */
public interface DependencyConstraint extends Constraint {
	/**
	 * Returns the value of the '<em><b>Dependencies</b></em>' reference list. The
	 * list contents are of type
	 * {@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Dependencies</em>' reference list.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getDependencyConstraint_Dependencies()
	 * @model
	 * @generated
	 */
	EList<Constraint> getDependencies();

} // DependencyConstraint
