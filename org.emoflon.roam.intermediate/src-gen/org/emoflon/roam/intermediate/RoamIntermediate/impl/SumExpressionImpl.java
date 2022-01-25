/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage;
import org.emoflon.roam.intermediate.RoamIntermediate.SumExpression;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sum Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.SumExpressionImpl#getReturnType <em>Return Type</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.SumExpressionImpl#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.SumExpressionImpl#getCondition <em>Condition</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class SumExpressionImpl extends MinimalEObjectImpl.Container implements SumExpression {
	/**
	 * The cached value of the '{@link #getReturnType() <em>Return Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnType()
	 * @generated
	 * @ordered
	 */
	protected EDataType returnType;

	/**
	 * The cached value of the '{@link #getExpression() <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
	protected ArithmeticExpression expression;

	/**
	 * The cached value of the '{@link #getCondition() <em>Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCondition()
	 * @generated
	 * @ordered
	 */
	protected BoolExpression condition;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SumExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RoamIntermediatePackage.Literals.SUM_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getReturnType() {
		if (returnType != null && returnType.eIsProxy()) {
			InternalEObject oldReturnType = (InternalEObject) returnType;
			returnType = (EDataType) eResolveProxy(oldReturnType);
			if (returnType != oldReturnType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							RoamIntermediatePackage.SUM_EXPRESSION__RETURN_TYPE, oldReturnType, returnType));
			}
		}
		return returnType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType basicGetReturnType() {
		return returnType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReturnType(EDataType newReturnType) {
		EDataType oldReturnType = returnType;
		returnType = newReturnType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RoamIntermediatePackage.SUM_EXPRESSION__RETURN_TYPE,
					oldReturnType, returnType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArithmeticExpression getExpression() {
		return expression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExpression(ArithmeticExpression newExpression, NotificationChain msgs) {
		ArithmeticExpression oldExpression = expression;
		expression = newExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.SUM_EXPRESSION__EXPRESSION, oldExpression, newExpression);
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
	public void setExpression(ArithmeticExpression newExpression) {
		if (newExpression != expression) {
			NotificationChain msgs = null;
			if (expression != null)
				msgs = ((InternalEObject) expression).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.SUM_EXPRESSION__EXPRESSION, null, msgs);
			if (newExpression != null)
				msgs = ((InternalEObject) newExpression).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.SUM_EXPRESSION__EXPRESSION, null, msgs);
			msgs = basicSetExpression(newExpression, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RoamIntermediatePackage.SUM_EXPRESSION__EXPRESSION,
					newExpression, newExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BoolExpression getCondition() {
		return condition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCondition(BoolExpression newCondition, NotificationChain msgs) {
		BoolExpression oldCondition = condition;
		condition = newCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.SUM_EXPRESSION__CONDITION, oldCondition, newCondition);
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
	public void setCondition(BoolExpression newCondition) {
		if (newCondition != condition) {
			NotificationChain msgs = null;
			if (condition != null)
				msgs = ((InternalEObject) condition).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.SUM_EXPRESSION__CONDITION, null, msgs);
			if (newCondition != null)
				msgs = ((InternalEObject) newCondition).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.SUM_EXPRESSION__CONDITION, null, msgs);
			msgs = basicSetCondition(newCondition, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RoamIntermediatePackage.SUM_EXPRESSION__CONDITION,
					newCondition, newCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case RoamIntermediatePackage.SUM_EXPRESSION__EXPRESSION:
			return basicSetExpression(null, msgs);
		case RoamIntermediatePackage.SUM_EXPRESSION__CONDITION:
			return basicSetCondition(null, msgs);
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
		case RoamIntermediatePackage.SUM_EXPRESSION__RETURN_TYPE:
			if (resolve)
				return getReturnType();
			return basicGetReturnType();
		case RoamIntermediatePackage.SUM_EXPRESSION__EXPRESSION:
			return getExpression();
		case RoamIntermediatePackage.SUM_EXPRESSION__CONDITION:
			return getCondition();
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
		case RoamIntermediatePackage.SUM_EXPRESSION__RETURN_TYPE:
			setReturnType((EDataType) newValue);
			return;
		case RoamIntermediatePackage.SUM_EXPRESSION__EXPRESSION:
			setExpression((ArithmeticExpression) newValue);
			return;
		case RoamIntermediatePackage.SUM_EXPRESSION__CONDITION:
			setCondition((BoolExpression) newValue);
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
		case RoamIntermediatePackage.SUM_EXPRESSION__RETURN_TYPE:
			setReturnType((EDataType) null);
			return;
		case RoamIntermediatePackage.SUM_EXPRESSION__EXPRESSION:
			setExpression((ArithmeticExpression) null);
			return;
		case RoamIntermediatePackage.SUM_EXPRESSION__CONDITION:
			setCondition((BoolExpression) null);
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
		case RoamIntermediatePackage.SUM_EXPRESSION__RETURN_TYPE:
			return returnType != null;
		case RoamIntermediatePackage.SUM_EXPRESSION__EXPRESSION:
			return expression != null;
		case RoamIntermediatePackage.SUM_EXPRESSION__CONDITION:
			return condition != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == ArithmeticExpression.class) {
			switch (derivedFeatureID) {
			case RoamIntermediatePackage.SUM_EXPRESSION__RETURN_TYPE:
				return RoamIntermediatePackage.ARITHMETIC_EXPRESSION__RETURN_TYPE;
			default:
				return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == ArithmeticExpression.class) {
			switch (baseFeatureID) {
			case RoamIntermediatePackage.ARITHMETIC_EXPRESSION__RETURN_TYPE:
				return RoamIntermediatePackage.SUM_EXPRESSION__RETURN_TYPE;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //SumExpressionImpl
