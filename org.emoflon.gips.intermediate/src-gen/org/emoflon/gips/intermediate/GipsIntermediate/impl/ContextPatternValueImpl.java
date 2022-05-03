/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternValue;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.Pattern;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Context
 * Pattern Value</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextPatternValueImpl#getPatternContext
 * <em>Pattern Context</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ContextPatternValueImpl extends ValueExpressionImpl implements ContextPatternValue {
	/**
	 * The cached value of the '{@link #getPatternContext() <em>Pattern
	 * Context</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPatternContext()
	 * @generated
	 * @ordered
	 */
	protected Pattern patternContext;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ContextPatternValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GipsIntermediatePackage.Literals.CONTEXT_PATTERN_VALUE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Pattern getPatternContext() {
		if (patternContext != null && patternContext.eIsProxy()) {
			InternalEObject oldPatternContext = (InternalEObject) patternContext;
			patternContext = (Pattern) eResolveProxy(oldPatternContext);
			if (patternContext != oldPatternContext) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							GipsIntermediatePackage.CONTEXT_PATTERN_VALUE__PATTERN_CONTEXT, oldPatternContext,
							patternContext));
			}
		}
		return patternContext;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Pattern basicGetPatternContext() {
		return patternContext;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setPatternContext(Pattern newPatternContext) {
		Pattern oldPatternContext = patternContext;
		patternContext = newPatternContext;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.CONTEXT_PATTERN_VALUE__PATTERN_CONTEXT, oldPatternContext, patternContext));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case GipsIntermediatePackage.CONTEXT_PATTERN_VALUE__PATTERN_CONTEXT:
			if (resolve)
				return getPatternContext();
			return basicGetPatternContext();
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
		case GipsIntermediatePackage.CONTEXT_PATTERN_VALUE__PATTERN_CONTEXT:
			setPatternContext((Pattern) newValue);
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
		case GipsIntermediatePackage.CONTEXT_PATTERN_VALUE__PATTERN_CONTEXT:
			setPatternContext((Pattern) null);
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
		case GipsIntermediatePackage.CONTEXT_PATTERN_VALUE__PATTERN_CONTEXT:
			return patternContext != null;
		}
		return super.eIsSet(featureID);
	}

} // ContextPatternValueImpl
