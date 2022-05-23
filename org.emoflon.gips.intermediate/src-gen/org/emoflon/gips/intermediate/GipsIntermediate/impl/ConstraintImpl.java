/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.gips.intermediate.GipsIntermediate.BoolValueExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Constraint;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Constraint</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ConstraintImpl#getName
 * <em>Name</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ConstraintImpl#isDepending
 * <em>Depending</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ConstraintImpl#getExpression
 * <em>Expression</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ConstraintImpl#isConstant
 * <em>Constant</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ConstraintImpl#getDependencies
 * <em>Dependencies</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ConstraintImpl#getRealVarCorrectnessConstraints
 * <em>Real Var Correctness Constraints</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ConstraintImpl#getBinaryVarCorrectnessConstraints
 * <em>Binary Var Correctness Constraints</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ConstraintImpl extends MinimalEObjectImpl.Container implements Constraint {
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
	 * The default value of the '{@link #isDepending() <em>Depending</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isDepending()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DEPENDING_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDepending() <em>Depending</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isDepending()
	 * @generated
	 * @ordered
	 */
	protected boolean depending = DEPENDING_EDEFAULT;

	/**
	 * The cached value of the '{@link #getExpression() <em>Expression</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
	protected BoolValueExpression expression;

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
	 * The cached value of the '{@link #getDependencies() <em>Dependencies</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDependencies()
	 * @generated
	 * @ordered
	 */
	protected EList<Constraint> dependencies;

	/**
	 * The cached value of the '{@link #getRealVarCorrectnessConstraints() <em>Real
	 * Var Correctness Constraints</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRealVarCorrectnessConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<RelationalExpression> realVarCorrectnessConstraints;

	/**
	 * The cached value of the '{@link #getBinaryVarCorrectnessConstraints()
	 * <em>Binary Var Correctness Constraints</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getBinaryVarCorrectnessConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<RelationalExpression> binaryVarCorrectnessConstraints;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ConstraintImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GipsIntermediatePackage.Literals.CONSTRAINT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.CONSTRAINT__NAME, oldName,
					name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isDepending() {
		return depending;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setDepending(boolean newDepending) {
		boolean oldDepending = depending;
		depending = newDepending;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.CONSTRAINT__DEPENDING,
					oldDepending, depending));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public BoolValueExpression getExpression() {
		return expression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetExpression(BoolValueExpression newExpression, NotificationChain msgs) {
		BoolValueExpression oldExpression = expression;
		expression = newExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.CONSTRAINT__EXPRESSION, oldExpression, newExpression);
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
	public void setExpression(BoolValueExpression newExpression) {
		if (newExpression != expression) {
			NotificationChain msgs = null;
			if (expression != null)
				msgs = ((InternalEObject) expression).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.CONSTRAINT__EXPRESSION, null, msgs);
			if (newExpression != null)
				msgs = ((InternalEObject) newExpression).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.CONSTRAINT__EXPRESSION, null, msgs);
			msgs = basicSetExpression(newExpression, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.CONSTRAINT__EXPRESSION,
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
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.CONSTRAINT__CONSTANT,
					oldConstant, constant));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Constraint> getDependencies() {
		if (dependencies == null) {
			dependencies = new EObjectContainmentEList<Constraint>(Constraint.class, this,
					GipsIntermediatePackage.CONSTRAINT__DEPENDENCIES);
		}
		return dependencies;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<RelationalExpression> getRealVarCorrectnessConstraints() {
		if (realVarCorrectnessConstraints == null) {
			realVarCorrectnessConstraints = new EObjectContainmentEList<RelationalExpression>(
					RelationalExpression.class, this,
					GipsIntermediatePackage.CONSTRAINT__REAL_VAR_CORRECTNESS_CONSTRAINTS);
		}
		return realVarCorrectnessConstraints;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<RelationalExpression> getBinaryVarCorrectnessConstraints() {
		if (binaryVarCorrectnessConstraints == null) {
			binaryVarCorrectnessConstraints = new EObjectContainmentEList<RelationalExpression>(
					RelationalExpression.class, this,
					GipsIntermediatePackage.CONSTRAINT__BINARY_VAR_CORRECTNESS_CONSTRAINTS);
		}
		return binaryVarCorrectnessConstraints;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GipsIntermediatePackage.CONSTRAINT__EXPRESSION:
			return basicSetExpression(null, msgs);
		case GipsIntermediatePackage.CONSTRAINT__DEPENDENCIES:
			return ((InternalEList<?>) getDependencies()).basicRemove(otherEnd, msgs);
		case GipsIntermediatePackage.CONSTRAINT__REAL_VAR_CORRECTNESS_CONSTRAINTS:
			return ((InternalEList<?>) getRealVarCorrectnessConstraints()).basicRemove(otherEnd, msgs);
		case GipsIntermediatePackage.CONSTRAINT__BINARY_VAR_CORRECTNESS_CONSTRAINTS:
			return ((InternalEList<?>) getBinaryVarCorrectnessConstraints()).basicRemove(otherEnd, msgs);
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
		case GipsIntermediatePackage.CONSTRAINT__NAME:
			return getName();
		case GipsIntermediatePackage.CONSTRAINT__DEPENDING:
			return isDepending();
		case GipsIntermediatePackage.CONSTRAINT__EXPRESSION:
			return getExpression();
		case GipsIntermediatePackage.CONSTRAINT__CONSTANT:
			return isConstant();
		case GipsIntermediatePackage.CONSTRAINT__DEPENDENCIES:
			return getDependencies();
		case GipsIntermediatePackage.CONSTRAINT__REAL_VAR_CORRECTNESS_CONSTRAINTS:
			return getRealVarCorrectnessConstraints();
		case GipsIntermediatePackage.CONSTRAINT__BINARY_VAR_CORRECTNESS_CONSTRAINTS:
			return getBinaryVarCorrectnessConstraints();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case GipsIntermediatePackage.CONSTRAINT__NAME:
			setName((String) newValue);
			return;
		case GipsIntermediatePackage.CONSTRAINT__DEPENDING:
			setDepending((Boolean) newValue);
			return;
		case GipsIntermediatePackage.CONSTRAINT__EXPRESSION:
			setExpression((BoolValueExpression) newValue);
			return;
		case GipsIntermediatePackage.CONSTRAINT__CONSTANT:
			setConstant((Boolean) newValue);
			return;
		case GipsIntermediatePackage.CONSTRAINT__DEPENDENCIES:
			getDependencies().clear();
			getDependencies().addAll((Collection<? extends Constraint>) newValue);
			return;
		case GipsIntermediatePackage.CONSTRAINT__REAL_VAR_CORRECTNESS_CONSTRAINTS:
			getRealVarCorrectnessConstraints().clear();
			getRealVarCorrectnessConstraints().addAll((Collection<? extends RelationalExpression>) newValue);
			return;
		case GipsIntermediatePackage.CONSTRAINT__BINARY_VAR_CORRECTNESS_CONSTRAINTS:
			getBinaryVarCorrectnessConstraints().clear();
			getBinaryVarCorrectnessConstraints().addAll((Collection<? extends RelationalExpression>) newValue);
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
		case GipsIntermediatePackage.CONSTRAINT__NAME:
			setName(NAME_EDEFAULT);
			return;
		case GipsIntermediatePackage.CONSTRAINT__DEPENDING:
			setDepending(DEPENDING_EDEFAULT);
			return;
		case GipsIntermediatePackage.CONSTRAINT__EXPRESSION:
			setExpression((BoolValueExpression) null);
			return;
		case GipsIntermediatePackage.CONSTRAINT__CONSTANT:
			setConstant(CONSTANT_EDEFAULT);
			return;
		case GipsIntermediatePackage.CONSTRAINT__DEPENDENCIES:
			getDependencies().clear();
			return;
		case GipsIntermediatePackage.CONSTRAINT__REAL_VAR_CORRECTNESS_CONSTRAINTS:
			getRealVarCorrectnessConstraints().clear();
			return;
		case GipsIntermediatePackage.CONSTRAINT__BINARY_VAR_CORRECTNESS_CONSTRAINTS:
			getBinaryVarCorrectnessConstraints().clear();
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
		case GipsIntermediatePackage.CONSTRAINT__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case GipsIntermediatePackage.CONSTRAINT__DEPENDING:
			return depending != DEPENDING_EDEFAULT;
		case GipsIntermediatePackage.CONSTRAINT__EXPRESSION:
			return expression != null;
		case GipsIntermediatePackage.CONSTRAINT__CONSTANT:
			return constant != CONSTANT_EDEFAULT;
		case GipsIntermediatePackage.CONSTRAINT__DEPENDENCIES:
			return dependencies != null && !dependencies.isEmpty();
		case GipsIntermediatePackage.CONSTRAINT__REAL_VAR_CORRECTNESS_CONSTRAINTS:
			return realVarCorrectnessConstraints != null && !realVarCorrectnessConstraints.isEmpty();
		case GipsIntermediatePackage.CONSTRAINT__BINARY_VAR_CORRECTNESS_CONSTRAINTS:
			return binaryVarCorrectnessConstraints != null && !binaryVarCorrectnessConstraints.isEmpty();
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
		result.append(" (name: ");
		result.append(name);
		result.append(", depending: ");
		result.append(depending);
		result.append(", constant: ");
		result.append(constant);
		result.append(')');
		return result.toString();
	}

} // ConstraintImpl
