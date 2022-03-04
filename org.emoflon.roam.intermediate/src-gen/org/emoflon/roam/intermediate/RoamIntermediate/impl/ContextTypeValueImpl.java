/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeValue;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage;
import org.emoflon.roam.intermediate.RoamIntermediate.Type;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Context Type Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ContextTypeValueImpl#getTypeContext <em>Type Context</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ContextTypeValueImpl extends ValueExpressionImpl implements ContextTypeValue {
	/**
	 * The cached value of the '{@link #getTypeContext() <em>Type Context</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeContext()
	 * @generated
	 * @ordered
	 */
	protected Type typeContext;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ContextTypeValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RoamIntermediatePackage.Literals.CONTEXT_TYPE_VALUE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Type getTypeContext() {
		if (typeContext != null && typeContext.eIsProxy()) {
			InternalEObject oldTypeContext = (InternalEObject) typeContext;
			typeContext = (Type) eResolveProxy(oldTypeContext);
			if (typeContext != oldTypeContext) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							RoamIntermediatePackage.CONTEXT_TYPE_VALUE__TYPE_CONTEXT, oldTypeContext, typeContext));
			}
		}
		return typeContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Type basicGetTypeContext() {
		return typeContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTypeContext(Type newTypeContext) {
		Type oldTypeContext = typeContext;
		typeContext = newTypeContext;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.CONTEXT_TYPE_VALUE__TYPE_CONTEXT, oldTypeContext, typeContext));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case RoamIntermediatePackage.CONTEXT_TYPE_VALUE__TYPE_CONTEXT:
			if (resolve)
				return getTypeContext();
			return basicGetTypeContext();
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
		case RoamIntermediatePackage.CONTEXT_TYPE_VALUE__TYPE_CONTEXT:
			setTypeContext((Type) newValue);
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
		case RoamIntermediatePackage.CONTEXT_TYPE_VALUE__TYPE_CONTEXT:
			setTypeContext((Type) null);
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
		case RoamIntermediatePackage.CONTEXT_TYPE_VALUE__TYPE_CONTEXT:
			return typeContext != null;
		}
		return super.eIsSet(featureID);
	}

} //ContextTypeValueImpl
