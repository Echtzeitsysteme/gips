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
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.gips.intermediate.GipsIntermediate.BoolValueExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Constraint;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.Pattern;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Variable;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Pattern
 * Constraint</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternConstraintImpl#getName
 * <em>Name</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternConstraintImpl#isDepending
 * <em>Depending</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternConstraintImpl#getExpression
 * <em>Expression</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternConstraintImpl#isConstant
 * <em>Constant</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternConstraintImpl#getDependencies
 * <em>Dependencies</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternConstraintImpl#getHelperVariables
 * <em>Helper Variables</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternConstraintImpl#getHelperConstraints
 * <em>Helper Constraints</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternConstraintImpl#getPattern
 * <em>Pattern</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PatternConstraintImpl extends MinimalEObjectImpl.Container implements PatternConstraint {
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
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDependencies()
	 * @generated
	 * @ordered
	 */
	protected EList<Constraint> dependencies;

	/**
	 * The cached value of the '{@link #getHelperVariables() <em>Helper
	 * Variables</em>}' reference list. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see #getHelperVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<Variable> helperVariables;

	/**
	 * The cached value of the '{@link #getHelperConstraints() <em>Helper
	 * Constraints</em>}' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getHelperConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<RelationalExpression> helperConstraints;

	/**
	 * The cached value of the '{@link #getPattern() <em>Pattern</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPattern()
	 * @generated
	 * @ordered
	 */
	protected Pattern pattern;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected PatternConstraintImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GipsIntermediatePackage.Literals.PATTERN_CONSTRAINT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.PATTERN_CONSTRAINT__NAME,
					oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.PATTERN_CONSTRAINT__DEPENDING,
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
					GipsIntermediatePackage.PATTERN_CONSTRAINT__EXPRESSION, oldExpression, newExpression);
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
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.PATTERN_CONSTRAINT__EXPRESSION, null, msgs);
			if (newExpression != null)
				msgs = ((InternalEObject) newExpression).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.PATTERN_CONSTRAINT__EXPRESSION, null, msgs);
			msgs = basicSetExpression(newExpression, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.PATTERN_CONSTRAINT__EXPRESSION, newExpression, newExpression));
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
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.PATTERN_CONSTRAINT__CONSTANT,
					oldConstant, constant));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Constraint> getDependencies() {
		if (dependencies == null) {
			dependencies = new EObjectResolvingEList<Constraint>(Constraint.class, this,
					GipsIntermediatePackage.PATTERN_CONSTRAINT__DEPENDENCIES);
		}
		return dependencies;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Variable> getHelperVariables() {
		if (helperVariables == null) {
			helperVariables = new EObjectResolvingEList<Variable>(Variable.class, this,
					GipsIntermediatePackage.PATTERN_CONSTRAINT__HELPER_VARIABLES);
		}
		return helperVariables;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<RelationalExpression> getHelperConstraints() {
		if (helperConstraints == null) {
			helperConstraints = new EObjectContainmentEList<RelationalExpression>(RelationalExpression.class, this,
					GipsIntermediatePackage.PATTERN_CONSTRAINT__HELPER_CONSTRAINTS);
		}
		return helperConstraints;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Pattern getPattern() {
		if (pattern != null && pattern.eIsProxy()) {
			InternalEObject oldPattern = (InternalEObject) pattern;
			pattern = (Pattern) eResolveProxy(oldPattern);
			if (pattern != oldPattern) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							GipsIntermediatePackage.PATTERN_CONSTRAINT__PATTERN, oldPattern, pattern));
			}
		}
		return pattern;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Pattern basicGetPattern() {
		return pattern;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setPattern(Pattern newPattern) {
		Pattern oldPattern = pattern;
		pattern = newPattern;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.PATTERN_CONSTRAINT__PATTERN,
					oldPattern, pattern));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__EXPRESSION:
			return basicSetExpression(null, msgs);
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__HELPER_CONSTRAINTS:
			return ((InternalEList<?>) getHelperConstraints()).basicRemove(otherEnd, msgs);
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
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__NAME:
			return getName();
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__DEPENDING:
			return isDepending();
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__EXPRESSION:
			return getExpression();
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__CONSTANT:
			return isConstant();
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__DEPENDENCIES:
			return getDependencies();
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__HELPER_VARIABLES:
			return getHelperVariables();
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__HELPER_CONSTRAINTS:
			return getHelperConstraints();
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__PATTERN:
			if (resolve)
				return getPattern();
			return basicGetPattern();
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
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__NAME:
			setName((String) newValue);
			return;
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__DEPENDING:
			setDepending((Boolean) newValue);
			return;
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__EXPRESSION:
			setExpression((BoolValueExpression) newValue);
			return;
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__CONSTANT:
			setConstant((Boolean) newValue);
			return;
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__DEPENDENCIES:
			getDependencies().clear();
			getDependencies().addAll((Collection<? extends Constraint>) newValue);
			return;
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__HELPER_VARIABLES:
			getHelperVariables().clear();
			getHelperVariables().addAll((Collection<? extends Variable>) newValue);
			return;
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__HELPER_CONSTRAINTS:
			getHelperConstraints().clear();
			getHelperConstraints().addAll((Collection<? extends RelationalExpression>) newValue);
			return;
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__PATTERN:
			setPattern((Pattern) newValue);
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
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__NAME:
			setName(NAME_EDEFAULT);
			return;
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__DEPENDING:
			setDepending(DEPENDING_EDEFAULT);
			return;
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__EXPRESSION:
			setExpression((BoolValueExpression) null);
			return;
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__CONSTANT:
			setConstant(CONSTANT_EDEFAULT);
			return;
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__DEPENDENCIES:
			getDependencies().clear();
			return;
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__HELPER_VARIABLES:
			getHelperVariables().clear();
			return;
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__HELPER_CONSTRAINTS:
			getHelperConstraints().clear();
			return;
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__PATTERN:
			setPattern((Pattern) null);
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
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__DEPENDING:
			return depending != DEPENDING_EDEFAULT;
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__EXPRESSION:
			return expression != null;
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__CONSTANT:
			return constant != CONSTANT_EDEFAULT;
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__DEPENDENCIES:
			return dependencies != null && !dependencies.isEmpty();
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__HELPER_VARIABLES:
			return helperVariables != null && !helperVariables.isEmpty();
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__HELPER_CONSTRAINTS:
			return helperConstraints != null && !helperConstraints.isEmpty();
		case GipsIntermediatePackage.PATTERN_CONSTRAINT__PATTERN:
			return pattern != null;
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
			case GipsIntermediatePackage.PATTERN_CONSTRAINT__NAME:
				return GipsIntermediatePackage.CONSTRAINT__NAME;
			case GipsIntermediatePackage.PATTERN_CONSTRAINT__DEPENDING:
				return GipsIntermediatePackage.CONSTRAINT__DEPENDING;
			case GipsIntermediatePackage.PATTERN_CONSTRAINT__EXPRESSION:
				return GipsIntermediatePackage.CONSTRAINT__EXPRESSION;
			case GipsIntermediatePackage.PATTERN_CONSTRAINT__CONSTANT:
				return GipsIntermediatePackage.CONSTRAINT__CONSTANT;
			case GipsIntermediatePackage.PATTERN_CONSTRAINT__DEPENDENCIES:
				return GipsIntermediatePackage.CONSTRAINT__DEPENDENCIES;
			case GipsIntermediatePackage.PATTERN_CONSTRAINT__HELPER_VARIABLES:
				return GipsIntermediatePackage.CONSTRAINT__HELPER_VARIABLES;
			case GipsIntermediatePackage.PATTERN_CONSTRAINT__HELPER_CONSTRAINTS:
				return GipsIntermediatePackage.CONSTRAINT__HELPER_CONSTRAINTS;
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
				return GipsIntermediatePackage.PATTERN_CONSTRAINT__NAME;
			case GipsIntermediatePackage.CONSTRAINT__DEPENDING:
				return GipsIntermediatePackage.PATTERN_CONSTRAINT__DEPENDING;
			case GipsIntermediatePackage.CONSTRAINT__EXPRESSION:
				return GipsIntermediatePackage.PATTERN_CONSTRAINT__EXPRESSION;
			case GipsIntermediatePackage.CONSTRAINT__CONSTANT:
				return GipsIntermediatePackage.PATTERN_CONSTRAINT__CONSTANT;
			case GipsIntermediatePackage.CONSTRAINT__DEPENDENCIES:
				return GipsIntermediatePackage.PATTERN_CONSTRAINT__DEPENDENCIES;
			case GipsIntermediatePackage.CONSTRAINT__HELPER_VARIABLES:
				return GipsIntermediatePackage.PATTERN_CONSTRAINT__HELPER_VARIABLES;
			case GipsIntermediatePackage.CONSTRAINT__HELPER_CONSTRAINTS:
				return GipsIntermediatePackage.PATTERN_CONSTRAINT__HELPER_CONSTRAINTS;
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
		result.append(", depending: ");
		result.append(depending);
		result.append(", constant: ");
		result.append(constant);
		result.append(')');
		return result.toString();
	}

} // PatternConstraintImpl
