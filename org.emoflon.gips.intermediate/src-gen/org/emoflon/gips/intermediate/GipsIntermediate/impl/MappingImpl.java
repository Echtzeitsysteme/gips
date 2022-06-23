/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Mapping</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.MappingImpl#getContextPattern
 * <em>Context Pattern</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class MappingImpl extends VariableSetImpl implements Mapping {
	/**
	 * The cached value of the '{@link #getContextPattern() <em>Context
	 * Pattern</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getContextPattern()
	 * @generated
	 * @ordered
	 */
	protected IBeXContextPattern contextPattern;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected MappingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GipsIntermediatePackage.Literals.MAPPING;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IBeXContextPattern getContextPattern() {
		if (contextPattern != null && contextPattern.eIsProxy()) {
			InternalEObject oldContextPattern = (InternalEObject) contextPattern;
			contextPattern = (IBeXContextPattern) eResolveProxy(oldContextPattern);
			if (contextPattern != oldContextPattern) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							GipsIntermediatePackage.MAPPING__CONTEXT_PATTERN, oldContextPattern, contextPattern));
			}
		}
		return contextPattern;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IBeXContextPattern basicGetContextPattern() {
		return contextPattern;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setContextPattern(IBeXContextPattern newContextPattern) {
		IBeXContextPattern oldContextPattern = contextPattern;
		contextPattern = newContextPattern;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.MAPPING__CONTEXT_PATTERN,
					oldContextPattern, contextPattern));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case GipsIntermediatePackage.MAPPING__CONTEXT_PATTERN:
			if (resolve)
				return getContextPattern();
			return basicGetContextPattern();
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
		case GipsIntermediatePackage.MAPPING__CONTEXT_PATTERN:
			setContextPattern((IBeXContextPattern) newValue);
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
		case GipsIntermediatePackage.MAPPING__CONTEXT_PATTERN:
			setContextPattern((IBeXContextPattern) null);
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
		case GipsIntermediatePackage.MAPPING__CONTEXT_PATTERN:
			return contextPattern != null;
		}
		return super.eIsSet(featureID);
	}

} // MappingImpl
