/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.gips.intermediate.GipsIntermediate.Constraint;
import org.emoflon.gips.intermediate.GipsIntermediate.DependencyConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Dependency Constraint</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.DependencyConstraintImpl#getDependencies
 * <em>Dependencies</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.DependencyConstraintImpl#getRealVarCorrectnessConstraints
 * <em>Real Var Correctness Constraints</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.DependencyConstraintImpl#getBinaryVarCorrectnessConstraints
 * <em>Binary Var Correctness Constraints</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DependencyConstraintImpl extends ConstraintImpl implements DependencyConstraint {
	/**
	 * The cached value of the '{@link #getDependencies() <em>Dependencies</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDependencies()
	 * @generated
	 * @ordered
	 */
	protected EList<Constraint> dependencies;

	/**
	 * The cached value of the '{@link #getRealVarCorrectnessConstraints() <em>Real
	 * Var Correctness Constraints</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRealVarCorrectnessConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<RelationalExpression> realVarCorrectnessConstraints;

	/**
	 * The cached value of the '{@link #getBinaryVarCorrectnessConstraints()
	 * <em>Binary Var Correctness Constraints</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getBinaryVarCorrectnessConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<RelationalExpression> binaryVarCorrectnessConstraints;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected DependencyConstraintImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GipsIntermediatePackage.Literals.DEPENDENCY_CONSTRAINT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Constraint> getDependencies() {
		if (dependencies == null) {
			dependencies = new EObjectContainmentEList<Constraint>(Constraint.class, this,
					GipsIntermediatePackage.DEPENDENCY_CONSTRAINT__DEPENDENCIES);
		}
		return dependencies;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<RelationalExpression> getRealVarCorrectnessConstraints() {
		if (realVarCorrectnessConstraints == null) {
			realVarCorrectnessConstraints = new EObjectContainmentEList<RelationalExpression>(
					RelationalExpression.class, this,
					GipsIntermediatePackage.DEPENDENCY_CONSTRAINT__REAL_VAR_CORRECTNESS_CONSTRAINTS);
		}
		return realVarCorrectnessConstraints;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<RelationalExpression> getBinaryVarCorrectnessConstraints() {
		if (binaryVarCorrectnessConstraints == null) {
			binaryVarCorrectnessConstraints = new EObjectContainmentEList<RelationalExpression>(
					RelationalExpression.class, this,
					GipsIntermediatePackage.DEPENDENCY_CONSTRAINT__BINARY_VAR_CORRECTNESS_CONSTRAINTS);
		}
		return binaryVarCorrectnessConstraints;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GipsIntermediatePackage.DEPENDENCY_CONSTRAINT__DEPENDENCIES:
			return ((InternalEList<?>) getDependencies()).basicRemove(otherEnd, msgs);
		case GipsIntermediatePackage.DEPENDENCY_CONSTRAINT__REAL_VAR_CORRECTNESS_CONSTRAINTS:
			return ((InternalEList<?>) getRealVarCorrectnessConstraints()).basicRemove(otherEnd, msgs);
		case GipsIntermediatePackage.DEPENDENCY_CONSTRAINT__BINARY_VAR_CORRECTNESS_CONSTRAINTS:
			return ((InternalEList<?>) getBinaryVarCorrectnessConstraints()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case GipsIntermediatePackage.DEPENDENCY_CONSTRAINT__DEPENDENCIES:
			return getDependencies();
		case GipsIntermediatePackage.DEPENDENCY_CONSTRAINT__REAL_VAR_CORRECTNESS_CONSTRAINTS:
			return getRealVarCorrectnessConstraints();
		case GipsIntermediatePackage.DEPENDENCY_CONSTRAINT__BINARY_VAR_CORRECTNESS_CONSTRAINTS:
			return getBinaryVarCorrectnessConstraints();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case GipsIntermediatePackage.DEPENDENCY_CONSTRAINT__DEPENDENCIES:
			getDependencies().clear();
			getDependencies().addAll((Collection<? extends Constraint>) newValue);
			return;
		case GipsIntermediatePackage.DEPENDENCY_CONSTRAINT__REAL_VAR_CORRECTNESS_CONSTRAINTS:
			getRealVarCorrectnessConstraints().clear();
			getRealVarCorrectnessConstraints().addAll((Collection<? extends RelationalExpression>) newValue);
			return;
		case GipsIntermediatePackage.DEPENDENCY_CONSTRAINT__BINARY_VAR_CORRECTNESS_CONSTRAINTS:
			getBinaryVarCorrectnessConstraints().clear();
			getBinaryVarCorrectnessConstraints().addAll((Collection<? extends RelationalExpression>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case GipsIntermediatePackage.DEPENDENCY_CONSTRAINT__DEPENDENCIES:
			getDependencies().clear();
			return;
		case GipsIntermediatePackage.DEPENDENCY_CONSTRAINT__REAL_VAR_CORRECTNESS_CONSTRAINTS:
			getRealVarCorrectnessConstraints().clear();
			return;
		case GipsIntermediatePackage.DEPENDENCY_CONSTRAINT__BINARY_VAR_CORRECTNESS_CONSTRAINTS:
			getBinaryVarCorrectnessConstraints().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case GipsIntermediatePackage.DEPENDENCY_CONSTRAINT__DEPENDENCIES:
			return dependencies != null && !dependencies.isEmpty();
		case GipsIntermediatePackage.DEPENDENCY_CONSTRAINT__REAL_VAR_CORRECTNESS_CONSTRAINTS:
			return realVarCorrectnessConstraints != null && !realVarCorrectnessConstraints.isEmpty();
		case GipsIntermediatePackage.DEPENDENCY_CONSTRAINT__BINARY_VAR_CORRECTNESS_CONSTRAINTS:
			return binaryVarCorrectnessConstraints != null && !binaryVarCorrectnessConstraints.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // DependencyConstraintImpl
