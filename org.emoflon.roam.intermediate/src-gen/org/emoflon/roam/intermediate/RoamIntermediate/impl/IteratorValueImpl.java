/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.roam.intermediate.RoamIntermediate.Iterator;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorValue;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Iterator Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorValueImpl#getIterator <em>Iterator</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IteratorValueImpl extends ValueExpressionImpl implements IteratorValue {
	/**
	 * The cached value of the '{@link #getIterator() <em>Iterator</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIterator()
	 * @generated
	 * @ordered
	 */
	protected Iterator iterator;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IteratorValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RoamIntermediatePackage.Literals.ITERATOR_VALUE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Iterator getIterator() {
		if (iterator != null && iterator.eIsProxy()) {
			InternalEObject oldIterator = (InternalEObject) iterator;
			iterator = (Iterator) eResolveProxy(oldIterator);
			if (iterator != oldIterator) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							RoamIntermediatePackage.ITERATOR_VALUE__ITERATOR, oldIterator, iterator));
			}
		}
		return iterator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Iterator basicGetIterator() {
		return iterator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIterator(Iterator newIterator) {
		Iterator oldIterator = iterator;
		iterator = newIterator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RoamIntermediatePackage.ITERATOR_VALUE__ITERATOR,
					oldIterator, iterator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case RoamIntermediatePackage.ITERATOR_VALUE__ITERATOR:
			if (resolve)
				return getIterator();
			return basicGetIterator();
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
		case RoamIntermediatePackage.ITERATOR_VALUE__ITERATOR:
			setIterator((Iterator) newValue);
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
		case RoamIntermediatePackage.ITERATOR_VALUE__ITERATOR:
			setIterator((Iterator) null);
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
		case RoamIntermediatePackage.ITERATOR_VALUE__ITERATOR:
			return iterator != null;
		}
		return super.eIsSet(featureID);
	}

} //IteratorValueImpl
