/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.roam.intermediate.RoamIntermediate.BoolExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamOperation;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamOperator;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Stream Operation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.StreamOperationImpl#getOperator <em>Operator</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.StreamOperationImpl#getPredicate <em>Predicate</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StreamOperationImpl extends StreamExpressionImpl implements StreamOperation {
	/**
	 * The default value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected static final StreamOperator OPERATOR_EDEFAULT = StreamOperator.FILTER;

	/**
	 * The cached value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected StreamOperator operator = OPERATOR_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPredicate() <em>Predicate</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPredicate()
	 * @generated
	 * @ordered
	 */
	protected BoolExpression predicate;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StreamOperationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RoamIntermediatePackage.Literals.STREAM_OPERATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StreamOperator getOperator() {
		return operator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperator(StreamOperator newOperator) {
		StreamOperator oldOperator = operator;
		operator = newOperator == null ? OPERATOR_EDEFAULT : newOperator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RoamIntermediatePackage.STREAM_OPERATION__OPERATOR,
					oldOperator, operator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BoolExpression getPredicate() {
		return predicate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPredicate(BoolExpression newPredicate, NotificationChain msgs) {
		BoolExpression oldPredicate = predicate;
		predicate = newPredicate;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.STREAM_OPERATION__PREDICATE, oldPredicate, newPredicate);
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
	public void setPredicate(BoolExpression newPredicate) {
		if (newPredicate != predicate) {
			NotificationChain msgs = null;
			if (predicate != null)
				msgs = ((InternalEObject) predicate).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.STREAM_OPERATION__PREDICATE, null, msgs);
			if (newPredicate != null)
				msgs = ((InternalEObject) newPredicate).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.STREAM_OPERATION__PREDICATE, null, msgs);
			msgs = basicSetPredicate(newPredicate, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RoamIntermediatePackage.STREAM_OPERATION__PREDICATE,
					newPredicate, newPredicate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case RoamIntermediatePackage.STREAM_OPERATION__PREDICATE:
			return basicSetPredicate(null, msgs);
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
		case RoamIntermediatePackage.STREAM_OPERATION__OPERATOR:
			return getOperator();
		case RoamIntermediatePackage.STREAM_OPERATION__PREDICATE:
			return getPredicate();
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
		case RoamIntermediatePackage.STREAM_OPERATION__OPERATOR:
			setOperator((StreamOperator) newValue);
			return;
		case RoamIntermediatePackage.STREAM_OPERATION__PREDICATE:
			setPredicate((BoolExpression) newValue);
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
		case RoamIntermediatePackage.STREAM_OPERATION__OPERATOR:
			setOperator(OPERATOR_EDEFAULT);
			return;
		case RoamIntermediatePackage.STREAM_OPERATION__PREDICATE:
			setPredicate((BoolExpression) null);
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
		case RoamIntermediatePackage.STREAM_OPERATION__OPERATOR:
			return operator != OPERATOR_EDEFAULT;
		case RoamIntermediatePackage.STREAM_OPERATION__PREDICATE:
			return predicate != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (operator: ");
		result.append(operator);
		result.append(')');
		return result.toString();
	}

} //StreamOperationImpl
