/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.emoflon.gips.intermediate.GipsIntermediate.BoolExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamFilterOperation;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Stream
 * Filter Operation</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.StreamFilterOperationImpl#getPredicate
 * <em>Predicate</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StreamFilterOperationImpl extends MinimalEObjectImpl.Container implements StreamFilterOperation {
	/**
	 * The cached value of the '{@link #getPredicate() <em>Predicate</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPredicate()
	 * @generated
	 * @ordered
	 */
	protected BoolExpression predicate;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected StreamFilterOperationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GipsIntermediatePackage.Literals.STREAM_FILTER_OPERATION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public BoolExpression getPredicate() {
		return predicate;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetPredicate(BoolExpression newPredicate, NotificationChain msgs) {
		BoolExpression oldPredicate = predicate;
		predicate = newPredicate;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.STREAM_FILTER_OPERATION__PREDICATE, oldPredicate, newPredicate);
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
	public void setPredicate(BoolExpression newPredicate) {
		if (newPredicate != predicate) {
			NotificationChain msgs = null;
			if (predicate != null)
				msgs = ((InternalEObject) predicate).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.STREAM_FILTER_OPERATION__PREDICATE, null,
						msgs);
			if (newPredicate != null)
				msgs = ((InternalEObject) newPredicate).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.STREAM_FILTER_OPERATION__PREDICATE, null,
						msgs);
			msgs = basicSetPredicate(newPredicate, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.STREAM_FILTER_OPERATION__PREDICATE, newPredicate, newPredicate));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GipsIntermediatePackage.STREAM_FILTER_OPERATION__PREDICATE:
			return basicSetPredicate(null, msgs);
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
		case GipsIntermediatePackage.STREAM_FILTER_OPERATION__PREDICATE:
			return getPredicate();
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
		case GipsIntermediatePackage.STREAM_FILTER_OPERATION__PREDICATE:
			setPredicate((BoolExpression) newValue);
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
		case GipsIntermediatePackage.STREAM_FILTER_OPERATION__PREDICATE:
			setPredicate((BoolExpression) null);
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
		case GipsIntermediatePackage.STREAM_FILTER_OPERATION__PREDICATE:
			return predicate != null;
		}
		return super.eIsSet(featureID);
	}

} // StreamFilterOperationImpl
