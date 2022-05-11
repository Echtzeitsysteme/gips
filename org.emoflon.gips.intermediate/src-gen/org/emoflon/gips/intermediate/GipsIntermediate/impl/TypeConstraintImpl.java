/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.emoflon.gips.intermediate.GipsIntermediate.Constraint;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Type;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeConstraint;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Type
 * Constraint</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.TypeConstraintImpl#getName
 * <em>Name</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.TypeConstraintImpl#isElementwise
 * <em>Elementwise</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.TypeConstraintImpl#getExpression
 * <em>Expression</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.TypeConstraintImpl#isConstant
 * <em>Constant</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.TypeConstraintImpl#getModelType
 * <em>Model Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TypeConstraintImpl extends MinimalEObjectImpl.Container implements TypeConstraint {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isElementwise() <em>Elementwise</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isElementwise()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ELEMENTWISE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isElementwise() <em>Elementwise</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isElementwise()
	 * @generated
	 * @ordered
	 */
	protected boolean elementwise = ELEMENTWISE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getExpression() <em>Expression</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
	protected RelationalExpression expression;

	/**
	 * The default value of the '{@link #isConstant() <em>Constant</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isConstant()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CONSTANT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isConstant() <em>Constant</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isConstant()
	 * @generated
	 * @ordered
	 */
	protected boolean constant = CONSTANT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getModelType() <em>Model Type</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getModelType()
	 * @generated
	 * @ordered
	 */
	protected Type modelType;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TypeConstraintImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GipsIntermediatePackage.Literals.TYPE_CONSTRAINT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.TYPE_CONSTRAINT__NAME,
					oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isElementwise() {
		return elementwise;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setElementwise(boolean newElementwise) {
		boolean oldElementwise = elementwise;
		elementwise = newElementwise;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.TYPE_CONSTRAINT__ELEMENTWISE,
					oldElementwise, elementwise));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public RelationalExpression getExpression() {
		return expression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetExpression(RelationalExpression newExpression, NotificationChain msgs) {
		RelationalExpression oldExpression = expression;
		expression = newExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.TYPE_CONSTRAINT__EXPRESSION, oldExpression, newExpression);
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
	public void setExpression(RelationalExpression newExpression) {
		if (newExpression != expression) {
			NotificationChain msgs = null;
			if (expression != null)
				msgs = ((InternalEObject) expression).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.TYPE_CONSTRAINT__EXPRESSION, null, msgs);
			if (newExpression != null)
				msgs = ((InternalEObject) newExpression).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.TYPE_CONSTRAINT__EXPRESSION, null, msgs);
			msgs = basicSetExpression(newExpression, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.TYPE_CONSTRAINT__EXPRESSION,
					newExpression, newExpression));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isConstant() {
		return constant;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setConstant(boolean newConstant) {
		boolean oldConstant = constant;
		constant = newConstant;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.TYPE_CONSTRAINT__CONSTANT,
					oldConstant, constant));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Type getModelType() {
		if (modelType != null && modelType.eIsProxy()) {
			InternalEObject oldModelType = (InternalEObject) modelType;
			modelType = (Type) eResolveProxy(oldModelType);
			if (modelType != oldModelType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							GipsIntermediatePackage.TYPE_CONSTRAINT__MODEL_TYPE, oldModelType, modelType));
			}
		}
		return modelType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Type basicGetModelType() {
		return modelType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setModelType(Type newModelType) {
		Type oldModelType = modelType;
		modelType = newModelType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.TYPE_CONSTRAINT__MODEL_TYPE,
					oldModelType, modelType));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GipsIntermediatePackage.TYPE_CONSTRAINT__EXPRESSION:
			return basicSetExpression(null, msgs);
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
		case GipsIntermediatePackage.TYPE_CONSTRAINT__NAME:
			return getName();
		case GipsIntermediatePackage.TYPE_CONSTRAINT__ELEMENTWISE:
			return isElementwise();
		case GipsIntermediatePackage.TYPE_CONSTRAINT__EXPRESSION:
			return getExpression();
		case GipsIntermediatePackage.TYPE_CONSTRAINT__CONSTANT:
			return isConstant();
		case GipsIntermediatePackage.TYPE_CONSTRAINT__MODEL_TYPE:
			if (resolve)
				return getModelType();
			return basicGetModelType();
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
		case GipsIntermediatePackage.TYPE_CONSTRAINT__NAME:
			setName((String) newValue);
			return;
		case GipsIntermediatePackage.TYPE_CONSTRAINT__ELEMENTWISE:
			setElementwise((Boolean) newValue);
			return;
		case GipsIntermediatePackage.TYPE_CONSTRAINT__EXPRESSION:
			setExpression((RelationalExpression) newValue);
			return;
		case GipsIntermediatePackage.TYPE_CONSTRAINT__CONSTANT:
			setConstant((Boolean) newValue);
			return;
		case GipsIntermediatePackage.TYPE_CONSTRAINT__MODEL_TYPE:
			setModelType((Type) newValue);
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
		case GipsIntermediatePackage.TYPE_CONSTRAINT__NAME:
			setName(NAME_EDEFAULT);
			return;
		case GipsIntermediatePackage.TYPE_CONSTRAINT__ELEMENTWISE:
			setElementwise(ELEMENTWISE_EDEFAULT);
			return;
		case GipsIntermediatePackage.TYPE_CONSTRAINT__EXPRESSION:
			setExpression((RelationalExpression) null);
			return;
		case GipsIntermediatePackage.TYPE_CONSTRAINT__CONSTANT:
			setConstant(CONSTANT_EDEFAULT);
			return;
		case GipsIntermediatePackage.TYPE_CONSTRAINT__MODEL_TYPE:
			setModelType((Type) null);
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
		case GipsIntermediatePackage.TYPE_CONSTRAINT__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case GipsIntermediatePackage.TYPE_CONSTRAINT__ELEMENTWISE:
			return elementwise != ELEMENTWISE_EDEFAULT;
		case GipsIntermediatePackage.TYPE_CONSTRAINT__EXPRESSION:
			return expression != null;
		case GipsIntermediatePackage.TYPE_CONSTRAINT__CONSTANT:
			return constant != CONSTANT_EDEFAULT;
		case GipsIntermediatePackage.TYPE_CONSTRAINT__MODEL_TYPE:
			return modelType != null;
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
		if (baseClass == Constraint.class) {
			switch (derivedFeatureID) {
			case GipsIntermediatePackage.TYPE_CONSTRAINT__NAME:
				return GipsIntermediatePackage.CONSTRAINT__NAME;
			case GipsIntermediatePackage.TYPE_CONSTRAINT__ELEMENTWISE:
				return GipsIntermediatePackage.CONSTRAINT__ELEMENTWISE;
			case GipsIntermediatePackage.TYPE_CONSTRAINT__EXPRESSION:
				return GipsIntermediatePackage.CONSTRAINT__EXPRESSION;
			case GipsIntermediatePackage.TYPE_CONSTRAINT__CONSTANT:
				return GipsIntermediatePackage.CONSTRAINT__CONSTANT;
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
		if (baseClass == Constraint.class) {
			switch (baseFeatureID) {
			case GipsIntermediatePackage.CONSTRAINT__NAME:
				return GipsIntermediatePackage.TYPE_CONSTRAINT__NAME;
			case GipsIntermediatePackage.CONSTRAINT__ELEMENTWISE:
				return GipsIntermediatePackage.TYPE_CONSTRAINT__ELEMENTWISE;
			case GipsIntermediatePackage.CONSTRAINT__EXPRESSION:
				return GipsIntermediatePackage.TYPE_CONSTRAINT__EXPRESSION;
			case GipsIntermediatePackage.CONSTRAINT__CONSTANT:
				return GipsIntermediatePackage.TYPE_CONSTRAINT__CONSTANT;
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
		result.append(" (name: ");
		result.append(name);
		result.append(", elementwise: ");
		result.append(elementwise);
		result.append(", constant: ");
		result.append(constant);
		result.append(')');
		return result.toString();
	}

} // TypeConstraintImpl
