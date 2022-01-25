/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.emoflon.roam.intermediate.RoamIntermediate.FeatureExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.FeatureLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Feature Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.FeatureExpressionImpl#getCurrent <em>Current</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.FeatureExpressionImpl#getChild <em>Child</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FeatureExpressionImpl extends MinimalEObjectImpl.Container implements FeatureExpression {
	/**
	 * The cached value of the '{@link #getCurrent() <em>Current</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrent()
	 * @generated
	 * @ordered
	 */
	protected FeatureLiteral current;

	/**
	 * The cached value of the '{@link #getChild() <em>Child</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChild()
	 * @generated
	 * @ordered
	 */
	protected FeatureExpression child;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FeatureExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RoamIntermediatePackage.Literals.FEATURE_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureLiteral getCurrent() {
		if (current != null && current.eIsProxy()) {
			InternalEObject oldCurrent = (InternalEObject) current;
			current = (FeatureLiteral) eResolveProxy(oldCurrent);
			if (current != oldCurrent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							RoamIntermediatePackage.FEATURE_EXPRESSION__CURRENT, oldCurrent, current));
			}
		}
		return current;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureLiteral basicGetCurrent() {
		return current;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCurrent(FeatureLiteral newCurrent) {
		FeatureLiteral oldCurrent = current;
		current = newCurrent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RoamIntermediatePackage.FEATURE_EXPRESSION__CURRENT,
					oldCurrent, current));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureExpression getChild() {
		if (child != null && child.eIsProxy()) {
			InternalEObject oldChild = (InternalEObject) child;
			child = (FeatureExpression) eResolveProxy(oldChild);
			if (child != oldChild) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							RoamIntermediatePackage.FEATURE_EXPRESSION__CHILD, oldChild, child));
			}
		}
		return child;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureExpression basicGetChild() {
		return child;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChild(FeatureExpression newChild) {
		FeatureExpression oldChild = child;
		child = newChild;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RoamIntermediatePackage.FEATURE_EXPRESSION__CHILD,
					oldChild, child));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case RoamIntermediatePackage.FEATURE_EXPRESSION__CURRENT:
			if (resolve)
				return getCurrent();
			return basicGetCurrent();
		case RoamIntermediatePackage.FEATURE_EXPRESSION__CHILD:
			if (resolve)
				return getChild();
			return basicGetChild();
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
		case RoamIntermediatePackage.FEATURE_EXPRESSION__CURRENT:
			setCurrent((FeatureLiteral) newValue);
			return;
		case RoamIntermediatePackage.FEATURE_EXPRESSION__CHILD:
			setChild((FeatureExpression) newValue);
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
		case RoamIntermediatePackage.FEATURE_EXPRESSION__CURRENT:
			setCurrent((FeatureLiteral) null);
			return;
		case RoamIntermediatePackage.FEATURE_EXPRESSION__CHILD:
			setChild((FeatureExpression) null);
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
		case RoamIntermediatePackage.FEATURE_EXPRESSION__CURRENT:
			return current != null;
		case RoamIntermediatePackage.FEATURE_EXPRESSION__CHILD:
			return child != null;
		}
		return super.eIsSet(featureID);
	}

} //FeatureExpressionImpl
