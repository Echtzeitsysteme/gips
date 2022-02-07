/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamOperation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Stream Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.StreamExpressionImpl#getReturnType <em>Return Type</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.StreamExpressionImpl#getCurrent <em>Current</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.StreamExpressionImpl#getChild <em>Child</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StreamExpressionImpl extends MinimalEObjectImpl.Container implements StreamExpression {
	/**
	 * The cached value of the '{@link #getReturnType() <em>Return Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnType()
	 * @generated
	 * @ordered
	 */
	protected EClassifier returnType;

	/**
	 * The cached value of the '{@link #getCurrent() <em>Current</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrent()
	 * @generated
	 * @ordered
	 */
	protected StreamOperation current;

	/**
	 * The cached value of the '{@link #getChild() <em>Child</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChild()
	 * @generated
	 * @ordered
	 */
	protected StreamExpression child;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StreamExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RoamIntermediatePackage.Literals.STREAM_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClassifier getReturnType() {
		if (returnType != null && returnType.eIsProxy()) {
			InternalEObject oldReturnType = (InternalEObject) returnType;
			returnType = (EClassifier) eResolveProxy(oldReturnType);
			if (returnType != oldReturnType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							RoamIntermediatePackage.STREAM_EXPRESSION__RETURN_TYPE, oldReturnType, returnType));
			}
		}
		return returnType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClassifier basicGetReturnType() {
		return returnType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReturnType(EClassifier newReturnType) {
		EClassifier oldReturnType = returnType;
		returnType = newReturnType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.STREAM_EXPRESSION__RETURN_TYPE, oldReturnType, returnType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StreamOperation getCurrent() {
		return current;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCurrent(StreamOperation newCurrent, NotificationChain msgs) {
		StreamOperation oldCurrent = current;
		current = newCurrent;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.STREAM_EXPRESSION__CURRENT, oldCurrent, newCurrent);
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
	public void setCurrent(StreamOperation newCurrent) {
		if (newCurrent != current) {
			NotificationChain msgs = null;
			if (current != null)
				msgs = ((InternalEObject) current).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.STREAM_EXPRESSION__CURRENT, null, msgs);
			if (newCurrent != null)
				msgs = ((InternalEObject) newCurrent).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.STREAM_EXPRESSION__CURRENT, null, msgs);
			msgs = basicSetCurrent(newCurrent, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RoamIntermediatePackage.STREAM_EXPRESSION__CURRENT,
					newCurrent, newCurrent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StreamExpression getChild() {
		return child;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetChild(StreamExpression newChild, NotificationChain msgs) {
		StreamExpression oldChild = child;
		child = newChild;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.STREAM_EXPRESSION__CHILD, oldChild, newChild);
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
	public void setChild(StreamExpression newChild) {
		if (newChild != child) {
			NotificationChain msgs = null;
			if (child != null)
				msgs = ((InternalEObject) child).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.STREAM_EXPRESSION__CHILD, null, msgs);
			if (newChild != null)
				msgs = ((InternalEObject) newChild).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.STREAM_EXPRESSION__CHILD, null, msgs);
			msgs = basicSetChild(newChild, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RoamIntermediatePackage.STREAM_EXPRESSION__CHILD,
					newChild, newChild));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case RoamIntermediatePackage.STREAM_EXPRESSION__CURRENT:
			return basicSetCurrent(null, msgs);
		case RoamIntermediatePackage.STREAM_EXPRESSION__CHILD:
			return basicSetChild(null, msgs);
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
		case RoamIntermediatePackage.STREAM_EXPRESSION__RETURN_TYPE:
			if (resolve)
				return getReturnType();
			return basicGetReturnType();
		case RoamIntermediatePackage.STREAM_EXPRESSION__CURRENT:
			return getCurrent();
		case RoamIntermediatePackage.STREAM_EXPRESSION__CHILD:
			return getChild();
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
		case RoamIntermediatePackage.STREAM_EXPRESSION__RETURN_TYPE:
			setReturnType((EClassifier) newValue);
			return;
		case RoamIntermediatePackage.STREAM_EXPRESSION__CURRENT:
			setCurrent((StreamOperation) newValue);
			return;
		case RoamIntermediatePackage.STREAM_EXPRESSION__CHILD:
			setChild((StreamExpression) newValue);
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
		case RoamIntermediatePackage.STREAM_EXPRESSION__RETURN_TYPE:
			setReturnType((EClassifier) null);
			return;
		case RoamIntermediatePackage.STREAM_EXPRESSION__CURRENT:
			setCurrent((StreamOperation) null);
			return;
		case RoamIntermediatePackage.STREAM_EXPRESSION__CHILD:
			setChild((StreamExpression) null);
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
		case RoamIntermediatePackage.STREAM_EXPRESSION__RETURN_TYPE:
			return returnType != null;
		case RoamIntermediatePackage.STREAM_EXPRESSION__CURRENT:
			return current != null;
		case RoamIntermediatePackage.STREAM_EXPRESSION__CHILD:
			return child != null;
		}
		return super.eIsSet(featureID);
	}

} //StreamExpressionImpl
