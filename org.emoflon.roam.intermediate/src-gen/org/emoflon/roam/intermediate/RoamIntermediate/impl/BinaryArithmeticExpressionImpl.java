/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticOperator;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Binary
 * Arithmetic Expression</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.BinaryArithmeticExpressionImpl#getLhs
 * <em>Lhs</em>}</li>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.BinaryArithmeticExpressionImpl#getRhs
 * <em>Rhs</em>}</li>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.BinaryArithmeticExpressionImpl#getOperator
 * <em>Operator</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BinaryArithmeticExpressionImpl extends ArithmeticExpressionImpl implements BinaryArithmeticExpression {
	/**
	 * The cached value of the '{@link #getLhs() <em>Lhs</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLhs()
	 * @generated
	 * @ordered
	 */
	protected ArithmeticExpression lhs;

	/**
	 * The cached value of the '{@link #getRhs() <em>Rhs</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRhs()
	 * @generated
	 * @ordered
	 */
	protected ArithmeticExpression rhs;

	/**
	 * The default value of the '{@link #getOperator() <em>Operator</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected static final BinaryArithmeticOperator OPERATOR_EDEFAULT = BinaryArithmeticOperator.ADD;

	/**
	 * The cached value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected BinaryArithmeticOperator operator = OPERATOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected BinaryArithmeticExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RoamIntermediatePackage.Literals.BINARY_ARITHMETIC_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ArithmeticExpression getLhs() {
		return lhs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetLhs(ArithmeticExpression newLhs, NotificationChain msgs) {
		ArithmeticExpression oldLhs = lhs;
		lhs = newLhs;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.BINARY_ARITHMETIC_EXPRESSION__LHS, oldLhs, newLhs);
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
	public void setLhs(ArithmeticExpression newLhs) {
		if (newLhs != lhs) {
			NotificationChain msgs = null;
			if (lhs != null)
				msgs = ((InternalEObject) lhs).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.BINARY_ARITHMETIC_EXPRESSION__LHS, null, msgs);
			if (newLhs != null)
				msgs = ((InternalEObject) newLhs).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.BINARY_ARITHMETIC_EXPRESSION__LHS, null, msgs);
			msgs = basicSetLhs(newLhs, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.BINARY_ARITHMETIC_EXPRESSION__LHS, newLhs, newLhs));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ArithmeticExpression getRhs() {
		return rhs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetRhs(ArithmeticExpression newRhs, NotificationChain msgs) {
		ArithmeticExpression oldRhs = rhs;
		rhs = newRhs;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.BINARY_ARITHMETIC_EXPRESSION__RHS, oldRhs, newRhs);
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
	public void setRhs(ArithmeticExpression newRhs) {
		if (newRhs != rhs) {
			NotificationChain msgs = null;
			if (rhs != null)
				msgs = ((InternalEObject) rhs).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.BINARY_ARITHMETIC_EXPRESSION__RHS, null, msgs);
			if (newRhs != null)
				msgs = ((InternalEObject) newRhs).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.BINARY_ARITHMETIC_EXPRESSION__RHS, null, msgs);
			msgs = basicSetRhs(newRhs, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.BINARY_ARITHMETIC_EXPRESSION__RHS, newRhs, newRhs));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public BinaryArithmeticOperator getOperator() {
		return operator;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setOperator(BinaryArithmeticOperator newOperator) {
		BinaryArithmeticOperator oldOperator = operator;
		operator = newOperator == null ? OPERATOR_EDEFAULT : newOperator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.BINARY_ARITHMETIC_EXPRESSION__OPERATOR, oldOperator, operator));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case RoamIntermediatePackage.BINARY_ARITHMETIC_EXPRESSION__LHS:
			return basicSetLhs(null, msgs);
		case RoamIntermediatePackage.BINARY_ARITHMETIC_EXPRESSION__RHS:
			return basicSetRhs(null, msgs);
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
		case RoamIntermediatePackage.BINARY_ARITHMETIC_EXPRESSION__LHS:
			return getLhs();
		case RoamIntermediatePackage.BINARY_ARITHMETIC_EXPRESSION__RHS:
			return getRhs();
		case RoamIntermediatePackage.BINARY_ARITHMETIC_EXPRESSION__OPERATOR:
			return getOperator();
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
		case RoamIntermediatePackage.BINARY_ARITHMETIC_EXPRESSION__LHS:
			setLhs((ArithmeticExpression) newValue);
			return;
		case RoamIntermediatePackage.BINARY_ARITHMETIC_EXPRESSION__RHS:
			setRhs((ArithmeticExpression) newValue);
			return;
		case RoamIntermediatePackage.BINARY_ARITHMETIC_EXPRESSION__OPERATOR:
			setOperator((BinaryArithmeticOperator) newValue);
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
		case RoamIntermediatePackage.BINARY_ARITHMETIC_EXPRESSION__LHS:
			setLhs((ArithmeticExpression) null);
			return;
		case RoamIntermediatePackage.BINARY_ARITHMETIC_EXPRESSION__RHS:
			setRhs((ArithmeticExpression) null);
			return;
		case RoamIntermediatePackage.BINARY_ARITHMETIC_EXPRESSION__OPERATOR:
			setOperator(OPERATOR_EDEFAULT);
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
		case RoamIntermediatePackage.BINARY_ARITHMETIC_EXPRESSION__LHS:
			return lhs != null;
		case RoamIntermediatePackage.BINARY_ARITHMETIC_EXPRESSION__RHS:
			return rhs != null;
		case RoamIntermediatePackage.BINARY_ARITHMETIC_EXPRESSION__OPERATOR:
			return operator != OPERATOR_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
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

} // BinaryArithmeticExpressionImpl
