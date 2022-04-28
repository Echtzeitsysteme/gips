/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.emoflon.roam.intermediate.RoamIntermediate.FeatureExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.FeatureLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Feature
 * Expression</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.FeatureExpressionImpl#getCurrent
 * <em>Current</em>}</li>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.FeatureExpressionImpl#getChild
 * <em>Child</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FeatureExpressionImpl extends MinimalEObjectImpl.Container implements FeatureExpression {
	/**
	 * The cached value of the '{@link #getCurrent() <em>Current</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCurrent()
	 * @generated
	 * @ordered
	 */
	protected FeatureLiteral current;

	/**
	 * The cached value of the '{@link #getChild() <em>Child</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getChild()
	 * @generated
	 * @ordered
	 */
	protected FeatureExpression child;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected FeatureExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RoamIntermediatePackage.Literals.FEATURE_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public FeatureLiteral getCurrent() {
		return current;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetCurrent(FeatureLiteral newCurrent, NotificationChain msgs) {
		FeatureLiteral oldCurrent = current;
		current = newCurrent;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.FEATURE_EXPRESSION__CURRENT, oldCurrent, newCurrent);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setCurrent(FeatureLiteral newCurrent) {
		if (newCurrent != current) {
			NotificationChain msgs = null;
			if (current != null)
				msgs = ((InternalEObject) current).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.FEATURE_EXPRESSION__CURRENT, null, msgs);
			if (newCurrent != null)
				msgs = ((InternalEObject) newCurrent).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.FEATURE_EXPRESSION__CURRENT, null, msgs);
			msgs = basicSetCurrent(newCurrent, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RoamIntermediatePackage.FEATURE_EXPRESSION__CURRENT,
					newCurrent, newCurrent));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public FeatureExpression getChild() {
		return child;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetChild(FeatureExpression newChild, NotificationChain msgs) {
		FeatureExpression oldChild = child;
		child = newChild;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.FEATURE_EXPRESSION__CHILD, oldChild, newChild);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setChild(FeatureExpression newChild) {
		if (newChild != child) {
			NotificationChain msgs = null;
			if (child != null)
				msgs = ((InternalEObject) child).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.FEATURE_EXPRESSION__CHILD, null, msgs);
			if (newChild != null)
				msgs = ((InternalEObject) newChild).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.FEATURE_EXPRESSION__CHILD, null, msgs);
			msgs = basicSetChild(newChild, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RoamIntermediatePackage.FEATURE_EXPRESSION__CHILD,
					newChild, newChild));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case RoamIntermediatePackage.FEATURE_EXPRESSION__CURRENT:
			return basicSetCurrent(null, msgs);
		case RoamIntermediatePackage.FEATURE_EXPRESSION__CHILD:
			return basicSetChild(null, msgs);
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
		case RoamIntermediatePackage.FEATURE_EXPRESSION__CURRENT:
			return getCurrent();
		case RoamIntermediatePackage.FEATURE_EXPRESSION__CHILD:
			return getChild();
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
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

} // FeatureExpressionImpl
