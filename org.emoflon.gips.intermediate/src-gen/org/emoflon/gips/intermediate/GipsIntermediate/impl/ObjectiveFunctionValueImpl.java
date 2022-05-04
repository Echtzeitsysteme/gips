/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.Objective;
import org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveFunctionValue;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Objective Function Value</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ObjectiveFunctionValueImpl#getObjective
 * <em>Objective</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ObjectiveFunctionValueImpl extends ValueExpressionImpl implements ObjectiveFunctionValue {
	/**
	 * The cached value of the '{@link #getObjective() <em>Objective</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getObjective()
	 * @generated
	 * @ordered
	 */
	protected Objective objective;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ObjectiveFunctionValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GipsIntermediatePackage.Literals.OBJECTIVE_FUNCTION_VALUE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Objective getObjective() {
		if (objective != null && objective.eIsProxy()) {
			InternalEObject oldObjective = (InternalEObject) objective;
			objective = (Objective) eResolveProxy(oldObjective);
			if (objective != oldObjective) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							GipsIntermediatePackage.OBJECTIVE_FUNCTION_VALUE__OBJECTIVE, oldObjective, objective));
			}
		}
		return objective;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Objective basicGetObjective() {
		return objective;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setObjective(Objective newObjective) {
		Objective oldObjective = objective;
		objective = newObjective;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.OBJECTIVE_FUNCTION_VALUE__OBJECTIVE, oldObjective, objective));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case GipsIntermediatePackage.OBJECTIVE_FUNCTION_VALUE__OBJECTIVE:
			if (resolve)
				return getObjective();
			return basicGetObjective();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case GipsIntermediatePackage.OBJECTIVE_FUNCTION_VALUE__OBJECTIVE:
			setObjective((Objective) newValue);
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
		case GipsIntermediatePackage.OBJECTIVE_FUNCTION_VALUE__OBJECTIVE:
			setObjective((Objective) null);
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
		case GipsIntermediatePackage.OBJECTIVE_FUNCTION_VALUE__OBJECTIVE:
			return objective != null;
		}
		return super.eIsSet(featureID);
	}

} // ObjectiveFunctionValueImpl
