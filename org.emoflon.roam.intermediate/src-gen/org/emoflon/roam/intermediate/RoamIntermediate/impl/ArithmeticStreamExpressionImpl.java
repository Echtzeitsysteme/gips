/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticStreamExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamArithmeticOperator;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Arithmetic Stream Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ArithmeticStreamExpressionImpl#getStream <em>Stream</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ArithmeticStreamExpressionImpl#getOperator <em>Operator</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ArithmeticStreamExpressionImpl#getOperation <em>Operation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ArithmeticStreamExpressionImpl extends ArithmeticExpressionImpl implements ArithmeticStreamExpression {
	/**
	 * The cached value of the '{@link #getStream() <em>Stream</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStream()
	 * @generated
	 * @ordered
	 */
	protected StreamExpression stream;

	/**
	 * The default value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected static final StreamArithmeticOperator OPERATOR_EDEFAULT = StreamArithmeticOperator.COUNT;

	/**
	 * The cached value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected StreamArithmeticOperator operator = OPERATOR_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOperation() <em>Operation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperation()
	 * @generated
	 * @ordered
	 */
	protected ArithmeticExpression operation;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArithmeticStreamExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RoamIntermediatePackage.Literals.ARITHMETIC_STREAM_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StreamExpression getStream() {
		return stream;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStream(StreamExpression newStream, NotificationChain msgs) {
		StreamExpression oldStream = stream;
		stream = newStream;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.ARITHMETIC_STREAM_EXPRESSION__STREAM, oldStream, newStream);
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
	public void setStream(StreamExpression newStream) {
		if (newStream != stream) {
			NotificationChain msgs = null;
			if (stream != null)
				msgs = ((InternalEObject) stream).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.ARITHMETIC_STREAM_EXPRESSION__STREAM, null,
						msgs);
			if (newStream != null)
				msgs = ((InternalEObject) newStream).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.ARITHMETIC_STREAM_EXPRESSION__STREAM, null,
						msgs);
			msgs = basicSetStream(newStream, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.ARITHMETIC_STREAM_EXPRESSION__STREAM, newStream, newStream));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StreamArithmeticOperator getOperator() {
		return operator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperator(StreamArithmeticOperator newOperator) {
		StreamArithmeticOperator oldOperator = operator;
		operator = newOperator == null ? OPERATOR_EDEFAULT : newOperator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.ARITHMETIC_STREAM_EXPRESSION__OPERATOR, oldOperator, operator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArithmeticExpression getOperation() {
		return operation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOperation(ArithmeticExpression newOperation, NotificationChain msgs) {
		ArithmeticExpression oldOperation = operation;
		operation = newOperation;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.ARITHMETIC_STREAM_EXPRESSION__OPERATION, oldOperation, newOperation);
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
	public void setOperation(ArithmeticExpression newOperation) {
		if (newOperation != operation) {
			NotificationChain msgs = null;
			if (operation != null)
				msgs = ((InternalEObject) operation).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.ARITHMETIC_STREAM_EXPRESSION__OPERATION, null,
						msgs);
			if (newOperation != null)
				msgs = ((InternalEObject) newOperation).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.ARITHMETIC_STREAM_EXPRESSION__OPERATION, null,
						msgs);
			msgs = basicSetOperation(newOperation, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.ARITHMETIC_STREAM_EXPRESSION__OPERATION, newOperation, newOperation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case RoamIntermediatePackage.ARITHMETIC_STREAM_EXPRESSION__STREAM:
			return basicSetStream(null, msgs);
		case RoamIntermediatePackage.ARITHMETIC_STREAM_EXPRESSION__OPERATION:
			return basicSetOperation(null, msgs);
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
		case RoamIntermediatePackage.ARITHMETIC_STREAM_EXPRESSION__STREAM:
			return getStream();
		case RoamIntermediatePackage.ARITHMETIC_STREAM_EXPRESSION__OPERATOR:
			return getOperator();
		case RoamIntermediatePackage.ARITHMETIC_STREAM_EXPRESSION__OPERATION:
			return getOperation();
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
		case RoamIntermediatePackage.ARITHMETIC_STREAM_EXPRESSION__STREAM:
			setStream((StreamExpression) newValue);
			return;
		case RoamIntermediatePackage.ARITHMETIC_STREAM_EXPRESSION__OPERATOR:
			setOperator((StreamArithmeticOperator) newValue);
			return;
		case RoamIntermediatePackage.ARITHMETIC_STREAM_EXPRESSION__OPERATION:
			setOperation((ArithmeticExpression) newValue);
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
		case RoamIntermediatePackage.ARITHMETIC_STREAM_EXPRESSION__STREAM:
			setStream((StreamExpression) null);
			return;
		case RoamIntermediatePackage.ARITHMETIC_STREAM_EXPRESSION__OPERATOR:
			setOperator(OPERATOR_EDEFAULT);
			return;
		case RoamIntermediatePackage.ARITHMETIC_STREAM_EXPRESSION__OPERATION:
			setOperation((ArithmeticExpression) null);
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
		case RoamIntermediatePackage.ARITHMETIC_STREAM_EXPRESSION__STREAM:
			return stream != null;
		case RoamIntermediatePackage.ARITHMETIC_STREAM_EXPRESSION__OPERATOR:
			return operator != OPERATOR_EDEFAULT;
		case RoamIntermediatePackage.ARITHMETIC_STREAM_EXPRESSION__OPERATION:
			return operation != null;
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

} //ArithmeticStreamExpressionImpl
