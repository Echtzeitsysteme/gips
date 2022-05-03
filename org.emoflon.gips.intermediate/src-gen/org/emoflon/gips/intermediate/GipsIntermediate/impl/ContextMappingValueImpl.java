/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingValue;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Context
 * Mapping Value</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextMappingValueImpl#getMappingContext <em>Mapping Context</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ContextMappingValueImpl extends ValueExpressionImpl implements ContextMappingValue {
	/**
	 * The cached value of the '{@link #getMappingContext() <em>Mapping Context</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getMappingContext()
	 * @generated
	 * @ordered
	 */
	protected Mapping mappingContext;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ContextMappingValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GipsIntermediatePackage.Literals.CONTEXT_MAPPING_VALUE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Mapping getMappingContext() {
		if (mappingContext != null && mappingContext.eIsProxy()) {
			InternalEObject oldMappingContext = (InternalEObject) mappingContext;
			mappingContext = (Mapping) eResolveProxy(oldMappingContext);
			if (mappingContext != oldMappingContext) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							GipsIntermediatePackage.CONTEXT_MAPPING_VALUE__MAPPING_CONTEXT, oldMappingContext,
							mappingContext));
			}
		}
		return mappingContext;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Mapping basicGetMappingContext() {
		return mappingContext;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setMappingContext(Mapping newMappingContext) {
		Mapping oldMappingContext = mappingContext;
		mappingContext = newMappingContext;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.CONTEXT_MAPPING_VALUE__MAPPING_CONTEXT, oldMappingContext, mappingContext));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case GipsIntermediatePackage.CONTEXT_MAPPING_VALUE__MAPPING_CONTEXT:
			if (resolve)
				return getMappingContext();
			return basicGetMappingContext();
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
		case GipsIntermediatePackage.CONTEXT_MAPPING_VALUE__MAPPING_CONTEXT:
			setMappingContext((Mapping) newValue);
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
		case GipsIntermediatePackage.CONTEXT_MAPPING_VALUE__MAPPING_CONTEXT:
			setMappingContext((Mapping) null);
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
		case GipsIntermediatePackage.CONTEXT_MAPPING_VALUE__MAPPING_CONTEXT:
			return mappingContext != null;
		}
		return super.eIsSet(featureID);
	}

} // ContextMappingValueImpl
