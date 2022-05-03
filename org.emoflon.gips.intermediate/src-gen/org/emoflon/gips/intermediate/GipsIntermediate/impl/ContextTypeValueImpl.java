/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeValue;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.Type;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Context
 * Type Value</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextTypeValueImpl#getTypeContext <em>Type Context</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ContextTypeValueImpl extends ValueExpressionImpl implements ContextTypeValue {
	/**
	 * The cached value of the '{@link #getTypeContext() <em>Type Context</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTypeContext()
	 * @generated
	 * @ordered
	 */
	protected Type typeContext;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ContextTypeValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GipsIntermediatePackage.Literals.CONTEXT_TYPE_VALUE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Type getTypeContext() {
		if (typeContext != null && typeContext.eIsProxy()) {
			InternalEObject oldTypeContext = (InternalEObject) typeContext;
			typeContext = (Type) eResolveProxy(oldTypeContext);
			if (typeContext != oldTypeContext) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							GipsIntermediatePackage.CONTEXT_TYPE_VALUE__TYPE_CONTEXT, oldTypeContext, typeContext));
			}
		}
		return typeContext;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Type basicGetTypeContext() {
		return typeContext;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setTypeContext(Type newTypeContext) {
		Type oldTypeContext = typeContext;
		typeContext = newTypeContext;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.CONTEXT_TYPE_VALUE__TYPE_CONTEXT, oldTypeContext, typeContext));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case GipsIntermediatePackage.CONTEXT_TYPE_VALUE__TYPE_CONTEXT:
			if (resolve)
				return getTypeContext();
			return basicGetTypeContext();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case GipsIntermediatePackage.CONTEXT_TYPE_VALUE__TYPE_CONTEXT:
			setTypeContext((Type) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case GipsIntermediatePackage.CONTEXT_TYPE_VALUE__TYPE_CONTEXT:
			setTypeContext((Type) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case GipsIntermediatePackage.CONTEXT_TYPE_VALUE__TYPE_CONTEXT:
			return typeContext != null;
		}
		return super.eIsSet(featureID);
	}

} // ContextTypeValueImpl
