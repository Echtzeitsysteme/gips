/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticValue;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Arithmetic Value</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ArithmeticValueImpl#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ArithmeticValueImpl extends ArithmeticValueExpressionImpl implements ArithmeticValue {
	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected ValueExpression value;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ArithmeticValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GipsIntermediatePackage.Literals.ARITHMETIC_VALUE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ValueExpression getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetValue(ValueExpression newValue, NotificationChain msgs) {
		ValueExpression oldValue = value;
		value = newValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.ARITHMETIC_VALUE__VALUE, oldValue, newValue);
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
	public void setValue(ValueExpression newValue) {
		if (newValue != value) {
			NotificationChain msgs = null;
			if (value != null)
				msgs = ((InternalEObject) value).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.ARITHMETIC_VALUE__VALUE, null, msgs);
			if (newValue != null)
				msgs = ((InternalEObject) newValue).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.ARITHMETIC_VALUE__VALUE, null, msgs);
			msgs = basicSetValue(newValue, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.ARITHMETIC_VALUE__VALUE,
					newValue, newValue));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GipsIntermediatePackage.ARITHMETIC_VALUE__VALUE:
			return basicSetValue(null, msgs);
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
		case GipsIntermediatePackage.ARITHMETIC_VALUE__VALUE:
			return getValue();
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
		case GipsIntermediatePackage.ARITHMETIC_VALUE__VALUE:
			setValue((ValueExpression) newValue);
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
		case GipsIntermediatePackage.ARITHMETIC_VALUE__VALUE:
			setValue((ValueExpression) null);
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
		case GipsIntermediatePackage.ARITHMETIC_VALUE__VALUE:
			return value != null;
		}
		return super.eIsSet(featureID);
	}

} // ArithmeticValueImpl
