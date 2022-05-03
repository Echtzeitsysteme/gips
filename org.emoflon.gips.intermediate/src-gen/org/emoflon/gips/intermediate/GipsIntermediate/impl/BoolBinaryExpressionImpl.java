/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.gips.intermediate.GipsIntermediate.BinaryBoolOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolBinaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Bool
 * Binary Expression</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolBinaryExpressionImpl#getLhs <em>Lhs</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolBinaryExpressionImpl#getRhs <em>Rhs</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolBinaryExpressionImpl#getOperator <em>Operator</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BoolBinaryExpressionImpl extends BoolExpressionImpl implements BoolBinaryExpression {
	/**
	 * The cached value of the '{@link #getLhs() <em>Lhs</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLhs()
	 * @generated
	 * @ordered
	 */
	protected BoolExpression lhs;

	/**
	 * The cached value of the '{@link #getRhs() <em>Rhs</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getRhs()
	 * @generated
	 * @ordered
	 */
	protected BoolExpression rhs;

	/**
	 * The default value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected static final BinaryBoolOperator OPERATOR_EDEFAULT = BinaryBoolOperator.AND;

	/**
	 * The cached value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected BinaryBoolOperator operator = OPERATOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected BoolBinaryExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GipsIntermediatePackage.Literals.BOOL_BINARY_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public BoolExpression getLhs() {
		return lhs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLhs(BoolExpression newLhs, NotificationChain msgs) {
		BoolExpression oldLhs = lhs;
		lhs = newLhs;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.BOOL_BINARY_EXPRESSION__LHS, oldLhs, newLhs);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setLhs(BoolExpression newLhs) {
		if (newLhs != lhs) {
			NotificationChain msgs = null;
			if (lhs != null)
				msgs = ((InternalEObject) lhs).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.BOOL_BINARY_EXPRESSION__LHS, null, msgs);
			if (newLhs != null)
				msgs = ((InternalEObject) newLhs).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.BOOL_BINARY_EXPRESSION__LHS, null, msgs);
			msgs = basicSetLhs(newLhs, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.BOOL_BINARY_EXPRESSION__LHS,
					newLhs, newLhs));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public BoolExpression getRhs() {
		return rhs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRhs(BoolExpression newRhs, NotificationChain msgs) {
		BoolExpression oldRhs = rhs;
		rhs = newRhs;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.BOOL_BINARY_EXPRESSION__RHS, oldRhs, newRhs);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setRhs(BoolExpression newRhs) {
		if (newRhs != rhs) {
			NotificationChain msgs = null;
			if (rhs != null)
				msgs = ((InternalEObject) rhs).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.BOOL_BINARY_EXPRESSION__RHS, null, msgs);
			if (newRhs != null)
				msgs = ((InternalEObject) newRhs).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.BOOL_BINARY_EXPRESSION__RHS, null, msgs);
			msgs = basicSetRhs(newRhs, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.BOOL_BINARY_EXPRESSION__RHS,
					newRhs, newRhs));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public BinaryBoolOperator getOperator() {
		return operator;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperator(BinaryBoolOperator newOperator) {
		BinaryBoolOperator oldOperator = operator;
		operator = newOperator == null ? OPERATOR_EDEFAULT : newOperator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.BOOL_BINARY_EXPRESSION__OPERATOR, oldOperator, operator));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GipsIntermediatePackage.BOOL_BINARY_EXPRESSION__LHS:
			return basicSetLhs(null, msgs);
		case GipsIntermediatePackage.BOOL_BINARY_EXPRESSION__RHS:
			return basicSetRhs(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case GipsIntermediatePackage.BOOL_BINARY_EXPRESSION__LHS:
			return getLhs();
		case GipsIntermediatePackage.BOOL_BINARY_EXPRESSION__RHS:
			return getRhs();
		case GipsIntermediatePackage.BOOL_BINARY_EXPRESSION__OPERATOR:
			return getOperator();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case GipsIntermediatePackage.BOOL_BINARY_EXPRESSION__LHS:
			setLhs((BoolExpression) newValue);
			return;
		case GipsIntermediatePackage.BOOL_BINARY_EXPRESSION__RHS:
			setRhs((BoolExpression) newValue);
			return;
		case GipsIntermediatePackage.BOOL_BINARY_EXPRESSION__OPERATOR:
			setOperator((BinaryBoolOperator) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case GipsIntermediatePackage.BOOL_BINARY_EXPRESSION__LHS:
			setLhs((BoolExpression) null);
			return;
		case GipsIntermediatePackage.BOOL_BINARY_EXPRESSION__RHS:
			setRhs((BoolExpression) null);
			return;
		case GipsIntermediatePackage.BOOL_BINARY_EXPRESSION__OPERATOR:
			setOperator(OPERATOR_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case GipsIntermediatePackage.BOOL_BINARY_EXPRESSION__LHS:
			return lhs != null;
		case GipsIntermediatePackage.BOOL_BINARY_EXPRESSION__RHS:
			return rhs != null;
		case GipsIntermediatePackage.BOOL_BINARY_EXPRESSION__OPERATOR:
			return operator != OPERATOR_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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

} // BoolBinaryExpressionImpl
