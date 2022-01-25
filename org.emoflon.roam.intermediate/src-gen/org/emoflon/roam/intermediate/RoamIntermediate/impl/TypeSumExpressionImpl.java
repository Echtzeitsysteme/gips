/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage;
import org.emoflon.roam.intermediate.RoamIntermediate.TypeSumExpression;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type Sum Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.TypeSumExpressionImpl#getObjType <em>Obj Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TypeSumExpressionImpl extends SumExpressionImpl implements TypeSumExpression {
	/**
	 * The cached value of the '{@link #getObjType() <em>Obj Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjType()
	 * @generated
	 * @ordered
	 */
	protected EClass objType;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypeSumExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RoamIntermediatePackage.Literals.TYPE_SUM_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getObjType() {
		if (objType != null && objType.eIsProxy()) {
			InternalEObject oldObjType = (InternalEObject) objType;
			objType = (EClass) eResolveProxy(oldObjType);
			if (objType != oldObjType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							RoamIntermediatePackage.TYPE_SUM_EXPRESSION__OBJ_TYPE, oldObjType, objType));
			}
		}
		return objType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass basicGetObjType() {
		return objType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setObjType(EClass newObjType) {
		EClass oldObjType = objType;
		objType = newObjType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RoamIntermediatePackage.TYPE_SUM_EXPRESSION__OBJ_TYPE,
					oldObjType, objType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case RoamIntermediatePackage.TYPE_SUM_EXPRESSION__OBJ_TYPE:
			if (resolve)
				return getObjType();
			return basicGetObjType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case RoamIntermediatePackage.TYPE_SUM_EXPRESSION__OBJ_TYPE:
			setObjType((EClass) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case RoamIntermediatePackage.TYPE_SUM_EXPRESSION__OBJ_TYPE:
			setObjType((EClass) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case RoamIntermediatePackage.TYPE_SUM_EXPRESSION__OBJ_TYPE:
			return objType != null;
		}
		return super.eIsSet(featureID);
	}

} //TypeSumExpressionImpl
