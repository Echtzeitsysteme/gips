/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.SetOperation;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.SumExpression;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Sum
 * Expression</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.SumExpressionImpl#getOperandName
 * <em>Operand Name</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.SumExpressionImpl#getExpression
 * <em>Expression</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.SumExpressionImpl#getFilter
 * <em>Filter</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class SumExpressionImpl extends ValueExpressionImpl implements SumExpression {
	/**
	 * The default value of the '{@link #getOperandName() <em>Operand Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOperandName()
	 * @generated
	 * @ordered
	 */
	protected static final String OPERAND_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOperandName() <em>Operand Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOperandName()
	 * @generated
	 * @ordered
	 */
	protected String operandName = OPERAND_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getExpression() <em>Expression</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
	protected ArithmeticExpression expression;

	/**
	 * The cached value of the '{@link #getFilter() <em>Filter</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFilter()
	 * @generated
	 * @ordered
	 */
	protected StreamExpression filter;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected SumExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GipsIntermediatePackage.Literals.SUM_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getOperandName() {
		return operandName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setOperandName(String newOperandName) {
		String oldOperandName = operandName;
		operandName = newOperandName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.SUM_EXPRESSION__OPERAND_NAME,
					oldOperandName, operandName));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ArithmeticExpression getExpression() {
		return expression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetExpression(ArithmeticExpression newExpression, NotificationChain msgs) {
		ArithmeticExpression oldExpression = expression;
		expression = newExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.SUM_EXPRESSION__EXPRESSION, oldExpression, newExpression);
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
	public void setExpression(ArithmeticExpression newExpression) {
		if (newExpression != expression) {
			NotificationChain msgs = null;
			if (expression != null)
				msgs = ((InternalEObject) expression).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.SUM_EXPRESSION__EXPRESSION, null, msgs);
			if (newExpression != null)
				msgs = ((InternalEObject) newExpression).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.SUM_EXPRESSION__EXPRESSION, null, msgs);
			msgs = basicSetExpression(newExpression, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.SUM_EXPRESSION__EXPRESSION,
					newExpression, newExpression));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public StreamExpression getFilter() {
		return filter;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetFilter(StreamExpression newFilter, NotificationChain msgs) {
		StreamExpression oldFilter = filter;
		filter = newFilter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.SUM_EXPRESSION__FILTER, oldFilter, newFilter);
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
	public void setFilter(StreamExpression newFilter) {
		if (newFilter != filter) {
			NotificationChain msgs = null;
			if (filter != null)
				msgs = ((InternalEObject) filter).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.SUM_EXPRESSION__FILTER, null, msgs);
			if (newFilter != null)
				msgs = ((InternalEObject) newFilter).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.SUM_EXPRESSION__FILTER, null, msgs);
			msgs = basicSetFilter(newFilter, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.SUM_EXPRESSION__FILTER,
					newFilter, newFilter));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GipsIntermediatePackage.SUM_EXPRESSION__EXPRESSION:
			return basicSetExpression(null, msgs);
		case GipsIntermediatePackage.SUM_EXPRESSION__FILTER:
			return basicSetFilter(null, msgs);
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
		case GipsIntermediatePackage.SUM_EXPRESSION__OPERAND_NAME:
			return getOperandName();
		case GipsIntermediatePackage.SUM_EXPRESSION__EXPRESSION:
			return getExpression();
		case GipsIntermediatePackage.SUM_EXPRESSION__FILTER:
			return getFilter();
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
		case GipsIntermediatePackage.SUM_EXPRESSION__OPERAND_NAME:
			setOperandName((String) newValue);
			return;
		case GipsIntermediatePackage.SUM_EXPRESSION__EXPRESSION:
			setExpression((ArithmeticExpression) newValue);
			return;
		case GipsIntermediatePackage.SUM_EXPRESSION__FILTER:
			setFilter((StreamExpression) newValue);
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
		case GipsIntermediatePackage.SUM_EXPRESSION__OPERAND_NAME:
			setOperandName(OPERAND_NAME_EDEFAULT);
			return;
		case GipsIntermediatePackage.SUM_EXPRESSION__EXPRESSION:
			setExpression((ArithmeticExpression) null);
			return;
		case GipsIntermediatePackage.SUM_EXPRESSION__FILTER:
			setFilter((StreamExpression) null);
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
		case GipsIntermediatePackage.SUM_EXPRESSION__OPERAND_NAME:
			return OPERAND_NAME_EDEFAULT == null ? operandName != null : !OPERAND_NAME_EDEFAULT.equals(operandName);
		case GipsIntermediatePackage.SUM_EXPRESSION__EXPRESSION:
			return expression != null;
		case GipsIntermediatePackage.SUM_EXPRESSION__FILTER:
			return filter != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == SetOperation.class) {
			switch (derivedFeatureID) {
			case GipsIntermediatePackage.SUM_EXPRESSION__OPERAND_NAME:
				return GipsIntermediatePackage.SET_OPERATION__OPERAND_NAME;
			default:
				return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == SetOperation.class) {
			switch (baseFeatureID) {
			case GipsIntermediatePackage.SET_OPERATION__OPERAND_NAME:
				return GipsIntermediatePackage.SUM_EXPRESSION__OPERAND_NAME;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (operandName: ");
		result.append(operandName);
		result.append(')');
		return result.toString();
	}

} // SumExpressionImpl
