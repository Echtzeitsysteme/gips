/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.FeatureExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Context Type Feature Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ContextTypeFeatureValueImpl#getFeatureExpression <em>Feature Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ContextTypeFeatureValueImpl extends ContextTypeValueImpl implements ContextTypeFeatureValue {
	/**
	 * The cached value of the '{@link #getFeatureExpression() <em>Feature Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureExpression()
	 * @generated
	 * @ordered
	 */
	protected FeatureExpression featureExpression;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ContextTypeFeatureValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RoamIntermediatePackage.Literals.CONTEXT_TYPE_FEATURE_VALUE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureExpression getFeatureExpression() {
		return featureExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFeatureExpression(FeatureExpression newFeatureExpression, NotificationChain msgs) {
		FeatureExpression oldFeatureExpression = featureExpression;
		featureExpression = newFeatureExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.CONTEXT_TYPE_FEATURE_VALUE__FEATURE_EXPRESSION, oldFeatureExpression,
					newFeatureExpression);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeatureExpression(FeatureExpression newFeatureExpression) {
		if (newFeatureExpression != featureExpression) {
			NotificationChain msgs = null;
			if (featureExpression != null)
				msgs = ((InternalEObject) featureExpression).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.CONTEXT_TYPE_FEATURE_VALUE__FEATURE_EXPRESSION,
						null, msgs);
			if (newFeatureExpression != null)
				msgs = ((InternalEObject) newFeatureExpression).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.CONTEXT_TYPE_FEATURE_VALUE__FEATURE_EXPRESSION,
						null, msgs);
			msgs = basicSetFeatureExpression(newFeatureExpression, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.CONTEXT_TYPE_FEATURE_VALUE__FEATURE_EXPRESSION, newFeatureExpression,
					newFeatureExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case RoamIntermediatePackage.CONTEXT_TYPE_FEATURE_VALUE__FEATURE_EXPRESSION:
			return basicSetFeatureExpression(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case RoamIntermediatePackage.CONTEXT_TYPE_FEATURE_VALUE__FEATURE_EXPRESSION:
			return getFeatureExpression();
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
		case RoamIntermediatePackage.CONTEXT_TYPE_FEATURE_VALUE__FEATURE_EXPRESSION:
			setFeatureExpression((FeatureExpression) newValue);
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
		case RoamIntermediatePackage.CONTEXT_TYPE_FEATURE_VALUE__FEATURE_EXPRESSION:
			setFeatureExpression((FeatureExpression) null);
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
		case RoamIntermediatePackage.CONTEXT_TYPE_FEATURE_VALUE__FEATURE_EXPRESSION:
			return featureExpression != null;
		}
		return super.eIsSet(featureID);
	}

} //ContextTypeFeatureValueImpl