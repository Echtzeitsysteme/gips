/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticValue;
import org.emoflon.roam.intermediate.RoamIntermediate.Constraint;
import org.emoflon.roam.intermediate.RoamIntermediate.RelationalOperator;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage;
import org.emoflon.roam.intermediate.RoamIntermediate.TypeConstraint;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type Constraint</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.TypeConstraintImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.TypeConstraintImpl#getLhsConstant <em>Lhs Constant</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.TypeConstraintImpl#getRhsExpression <em>Rhs Expression</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.TypeConstraintImpl#getOperator <em>Operator</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.TypeConstraintImpl#isElementwise <em>Elementwise</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.TypeConstraintImpl#getModelType <em>Model Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TypeConstraintImpl extends MinimalEObjectImpl.Container implements TypeConstraint {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLhsConstant() <em>Lhs Constant</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLhsConstant()
	 * @generated
	 * @ordered
	 */
	protected ArithmeticValue lhsConstant;

	/**
	 * The cached value of the '{@link #getRhsExpression() <em>Rhs Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRhsExpression()
	 * @generated
	 * @ordered
	 */
	protected ArithmeticExpression rhsExpression;

	/**
	 * The default value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected static final RelationalOperator OPERATOR_EDEFAULT = RelationalOperator.LESS;

	/**
	 * The cached value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected RelationalOperator operator = OPERATOR_EDEFAULT;

	/**
	 * The default value of the '{@link #isElementwise() <em>Elementwise</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isElementwise()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ELEMENTWISE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isElementwise() <em>Elementwise</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isElementwise()
	 * @generated
	 * @ordered
	 */
	protected boolean elementwise = ELEMENTWISE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getModelType() <em>Model Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModelType()
	 * @generated
	 * @ordered
	 */
	protected EClass modelType;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypeConstraintImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RoamIntermediatePackage.Literals.TYPE_CONSTRAINT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RoamIntermediatePackage.TYPE_CONSTRAINT__NAME,
					oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArithmeticValue getLhsConstant() {
		return lhsConstant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLhsConstant(ArithmeticValue newLhsConstant, NotificationChain msgs) {
		ArithmeticValue oldLhsConstant = lhsConstant;
		lhsConstant = newLhsConstant;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.TYPE_CONSTRAINT__LHS_CONSTANT, oldLhsConstant, newLhsConstant);
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
	public void setLhsConstant(ArithmeticValue newLhsConstant) {
		if (newLhsConstant != lhsConstant) {
			NotificationChain msgs = null;
			if (lhsConstant != null)
				msgs = ((InternalEObject) lhsConstant).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.TYPE_CONSTRAINT__LHS_CONSTANT, null, msgs);
			if (newLhsConstant != null)
				msgs = ((InternalEObject) newLhsConstant).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.TYPE_CONSTRAINT__LHS_CONSTANT, null, msgs);
			msgs = basicSetLhsConstant(newLhsConstant, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RoamIntermediatePackage.TYPE_CONSTRAINT__LHS_CONSTANT,
					newLhsConstant, newLhsConstant));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArithmeticExpression getRhsExpression() {
		return rhsExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRhsExpression(ArithmeticExpression newRhsExpression, NotificationChain msgs) {
		ArithmeticExpression oldRhsExpression = rhsExpression;
		rhsExpression = newRhsExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.TYPE_CONSTRAINT__RHS_EXPRESSION, oldRhsExpression, newRhsExpression);
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
	public void setRhsExpression(ArithmeticExpression newRhsExpression) {
		if (newRhsExpression != rhsExpression) {
			NotificationChain msgs = null;
			if (rhsExpression != null)
				msgs = ((InternalEObject) rhsExpression).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.TYPE_CONSTRAINT__RHS_EXPRESSION, null, msgs);
			if (newRhsExpression != null)
				msgs = ((InternalEObject) newRhsExpression).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - RoamIntermediatePackage.TYPE_CONSTRAINT__RHS_EXPRESSION, null, msgs);
			msgs = basicSetRhsExpression(newRhsExpression, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.TYPE_CONSTRAINT__RHS_EXPRESSION, newRhsExpression, newRhsExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RelationalOperator getOperator() {
		return operator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperator(RelationalOperator newOperator) {
		RelationalOperator oldOperator = operator;
		operator = newOperator == null ? OPERATOR_EDEFAULT : newOperator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RoamIntermediatePackage.TYPE_CONSTRAINT__OPERATOR,
					oldOperator, operator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isElementwise() {
		return elementwise;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setElementwise(boolean newElementwise) {
		boolean oldElementwise = elementwise;
		elementwise = newElementwise;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RoamIntermediatePackage.TYPE_CONSTRAINT__ELEMENTWISE,
					oldElementwise, elementwise));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getModelType() {
		if (modelType != null && modelType.eIsProxy()) {
			InternalEObject oldModelType = (InternalEObject) modelType;
			modelType = (EClass) eResolveProxy(oldModelType);
			if (modelType != oldModelType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							RoamIntermediatePackage.TYPE_CONSTRAINT__MODEL_TYPE, oldModelType, modelType));
			}
		}
		return modelType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass basicGetModelType() {
		return modelType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModelType(EClass newModelType) {
		EClass oldModelType = modelType;
		modelType = newModelType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RoamIntermediatePackage.TYPE_CONSTRAINT__MODEL_TYPE,
					oldModelType, modelType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case RoamIntermediatePackage.TYPE_CONSTRAINT__LHS_CONSTANT:
			return basicSetLhsConstant(null, msgs);
		case RoamIntermediatePackage.TYPE_CONSTRAINT__RHS_EXPRESSION:
			return basicSetRhsExpression(null, msgs);
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
		case RoamIntermediatePackage.TYPE_CONSTRAINT__NAME:
			return getName();
		case RoamIntermediatePackage.TYPE_CONSTRAINT__LHS_CONSTANT:
			return getLhsConstant();
		case RoamIntermediatePackage.TYPE_CONSTRAINT__RHS_EXPRESSION:
			return getRhsExpression();
		case RoamIntermediatePackage.TYPE_CONSTRAINT__OPERATOR:
			return getOperator();
		case RoamIntermediatePackage.TYPE_CONSTRAINT__ELEMENTWISE:
			return isElementwise();
		case RoamIntermediatePackage.TYPE_CONSTRAINT__MODEL_TYPE:
			if (resolve)
				return getModelType();
			return basicGetModelType();
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
		case RoamIntermediatePackage.TYPE_CONSTRAINT__NAME:
			setName((String) newValue);
			return;
		case RoamIntermediatePackage.TYPE_CONSTRAINT__LHS_CONSTANT:
			setLhsConstant((ArithmeticValue) newValue);
			return;
		case RoamIntermediatePackage.TYPE_CONSTRAINT__RHS_EXPRESSION:
			setRhsExpression((ArithmeticExpression) newValue);
			return;
		case RoamIntermediatePackage.TYPE_CONSTRAINT__OPERATOR:
			setOperator((RelationalOperator) newValue);
			return;
		case RoamIntermediatePackage.TYPE_CONSTRAINT__ELEMENTWISE:
			setElementwise((Boolean) newValue);
			return;
		case RoamIntermediatePackage.TYPE_CONSTRAINT__MODEL_TYPE:
			setModelType((EClass) newValue);
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
		case RoamIntermediatePackage.TYPE_CONSTRAINT__NAME:
			setName(NAME_EDEFAULT);
			return;
		case RoamIntermediatePackage.TYPE_CONSTRAINT__LHS_CONSTANT:
			setLhsConstant((ArithmeticValue) null);
			return;
		case RoamIntermediatePackage.TYPE_CONSTRAINT__RHS_EXPRESSION:
			setRhsExpression((ArithmeticExpression) null);
			return;
		case RoamIntermediatePackage.TYPE_CONSTRAINT__OPERATOR:
			setOperator(OPERATOR_EDEFAULT);
			return;
		case RoamIntermediatePackage.TYPE_CONSTRAINT__ELEMENTWISE:
			setElementwise(ELEMENTWISE_EDEFAULT);
			return;
		case RoamIntermediatePackage.TYPE_CONSTRAINT__MODEL_TYPE:
			setModelType((EClass) null);
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
		case RoamIntermediatePackage.TYPE_CONSTRAINT__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case RoamIntermediatePackage.TYPE_CONSTRAINT__LHS_CONSTANT:
			return lhsConstant != null;
		case RoamIntermediatePackage.TYPE_CONSTRAINT__RHS_EXPRESSION:
			return rhsExpression != null;
		case RoamIntermediatePackage.TYPE_CONSTRAINT__OPERATOR:
			return operator != OPERATOR_EDEFAULT;
		case RoamIntermediatePackage.TYPE_CONSTRAINT__ELEMENTWISE:
			return elementwise != ELEMENTWISE_EDEFAULT;
		case RoamIntermediatePackage.TYPE_CONSTRAINT__MODEL_TYPE:
			return modelType != null;
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
		if (baseClass == Constraint.class) {
			switch (derivedFeatureID) {
			case RoamIntermediatePackage.TYPE_CONSTRAINT__NAME:
				return RoamIntermediatePackage.CONSTRAINT__NAME;
			case RoamIntermediatePackage.TYPE_CONSTRAINT__LHS_CONSTANT:
				return RoamIntermediatePackage.CONSTRAINT__LHS_CONSTANT;
			case RoamIntermediatePackage.TYPE_CONSTRAINT__RHS_EXPRESSION:
				return RoamIntermediatePackage.CONSTRAINT__RHS_EXPRESSION;
			case RoamIntermediatePackage.TYPE_CONSTRAINT__OPERATOR:
				return RoamIntermediatePackage.CONSTRAINT__OPERATOR;
			case RoamIntermediatePackage.TYPE_CONSTRAINT__ELEMENTWISE:
				return RoamIntermediatePackage.CONSTRAINT__ELEMENTWISE;
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
		if (baseClass == Constraint.class) {
			switch (baseFeatureID) {
			case RoamIntermediatePackage.CONSTRAINT__NAME:
				return RoamIntermediatePackage.TYPE_CONSTRAINT__NAME;
			case RoamIntermediatePackage.CONSTRAINT__LHS_CONSTANT:
				return RoamIntermediatePackage.TYPE_CONSTRAINT__LHS_CONSTANT;
			case RoamIntermediatePackage.CONSTRAINT__RHS_EXPRESSION:
				return RoamIntermediatePackage.TYPE_CONSTRAINT__RHS_EXPRESSION;
			case RoamIntermediatePackage.CONSTRAINT__OPERATOR:
				return RoamIntermediatePackage.TYPE_CONSTRAINT__OPERATOR;
			case RoamIntermediatePackage.CONSTRAINT__ELEMENTWISE:
				return RoamIntermediatePackage.TYPE_CONSTRAINT__ELEMENTWISE;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", operator: ");
		result.append(operator);
		result.append(", elementwise: ");
		result.append(elementwise);
		result.append(')');
		return result.toString();
	}

} //TypeConstraintImpl
