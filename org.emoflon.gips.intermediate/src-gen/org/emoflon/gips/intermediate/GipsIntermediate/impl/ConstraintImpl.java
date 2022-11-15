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
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.gips.intermediate.GipsIntermediate.BoolExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Constraint;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Variable;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Constraint</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ConstraintImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ConstraintImpl#isDepending <em>Depending</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ConstraintImpl#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ConstraintImpl#isConstant <em>Constant</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ConstraintImpl#isNegated <em>Negated</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ConstraintImpl#getDependencies <em>Dependencies</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ConstraintImpl#getReferencedBy <em>Referenced By</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ConstraintImpl#getSymbolicVariable <em>Symbolic Variable</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ConstraintImpl#getHelperVariables <em>Helper Variables</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ConstraintImpl#getHelperConstraints <em>Helper Constraints</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ConstraintImpl extends MinimalEObjectImpl.Container implements Constraint {
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
	 * The default value of the '{@link #isDepending() <em>Depending</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDepending()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DEPENDING_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDepending() <em>Depending</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDepending()
	 * @generated
	 * @ordered
	 */
	protected boolean depending = DEPENDING_EDEFAULT;

	/**
	 * The cached value of the '{@link #getExpression() <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
	protected BoolExpression expression;

	/**
	 * The default value of the '{@link #isConstant() <em>Constant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConstant()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CONSTANT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isConstant() <em>Constant</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConstant()
	 * @generated
	 * @ordered
	 */
	protected boolean constant = CONSTANT_EDEFAULT;

	/**
	 * The default value of the '{@link #isNegated() <em>Negated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNegated()
	 * @generated
	 * @ordered
	 */
	protected static final boolean NEGATED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isNegated() <em>Negated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNegated()
	 * @generated
	 * @ordered
	 */
	protected boolean negated = NEGATED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDependencies() <em>Dependencies</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDependencies()
	 * @generated
	 * @ordered
	 */
	protected EList<Constraint> dependencies;

	/**
	 * The cached value of the '{@link #getReferencedBy() <em>Referenced By</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferencedBy()
	 * @generated
	 * @ordered
	 */
	protected Constraint referencedBy;

	/**
	 * The cached value of the '{@link #getSymbolicVariable() <em>Symbolic Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSymbolicVariable()
	 * @generated
	 * @ordered
	 */
	protected Variable symbolicVariable;

	/**
	 * The cached value of the '{@link #getHelperVariables() <em>Helper Variables</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHelperVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<Variable> helperVariables;

	/**
	 * The cached value of the '{@link #getHelperConstraints() <em>Helper Constraints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHelperConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<RelationalExpression> helperConstraints;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConstraintImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GipsIntermediatePackage.Literals.CONSTRAINT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.CONSTRAINT__NAME, oldName,
					name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDepending() {
		return depending;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BoolExpression getExpression() {
		return expression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExpression(BoolExpression newExpression, NotificationChain msgs) {
		BoolExpression oldExpression = expression;
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExpression(BoolExpression newExpression) {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isConstant() {
		return constant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isNegated() {
		return negated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNegated(boolean newNegated) {
		boolean oldNegated = negated;
		negated = newNegated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.CONSTRAINT__NEGATED,
					oldNegated, negated));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Constraint> getDependencies() {
		if (dependencies == null) {
			dependencies = new EObjectWithInverseResolvingEList<Constraint>(Constraint.class, this,
					GipsIntermediatePackage.CONSTRAINT__DEPENDENCIES,
					GipsIntermediatePackage.CONSTRAINT__REFERENCED_BY);
		}
		return dependencies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Constraint getReferencedBy() {
		if (referencedBy != null && referencedBy.eIsProxy()) {
			InternalEObject oldReferencedBy = (InternalEObject) referencedBy;
			referencedBy = (Constraint) eResolveProxy(oldReferencedBy);
			if (referencedBy != oldReferencedBy) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							GipsIntermediatePackage.CONSTRAINT__REFERENCED_BY, oldReferencedBy, referencedBy));
			}
		}
		return referencedBy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Constraint basicGetReferencedBy() {
		return referencedBy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReferencedBy(Constraint newReferencedBy, NotificationChain msgs) {
		Constraint oldReferencedBy = referencedBy;
		referencedBy = newReferencedBy;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.CONSTRAINT__REFERENCED_BY, oldReferencedBy, newReferencedBy);
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
	public void setReferencedBy(Constraint newReferencedBy) {
		if (newReferencedBy != referencedBy) {
			NotificationChain msgs = null;
			if (referencedBy != null)
				msgs = ((InternalEObject) referencedBy).eInverseRemove(this,
						GipsIntermediatePackage.CONSTRAINT__DEPENDENCIES, Constraint.class, msgs);
			if (newReferencedBy != null)
				msgs = ((InternalEObject) newReferencedBy).eInverseAdd(this,
						GipsIntermediatePackage.CONSTRAINT__DEPENDENCIES, Constraint.class, msgs);
			msgs = basicSetReferencedBy(newReferencedBy, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.CONSTRAINT__REFERENCED_BY,
					newReferencedBy, newReferencedBy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable getSymbolicVariable() {
		if (symbolicVariable != null && symbolicVariable.eIsProxy()) {
			InternalEObject oldSymbolicVariable = (InternalEObject) symbolicVariable;
			symbolicVariable = (Variable) eResolveProxy(oldSymbolicVariable);
			if (symbolicVariable != oldSymbolicVariable) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							GipsIntermediatePackage.CONSTRAINT__SYMBOLIC_VARIABLE, oldSymbolicVariable,
							symbolicVariable));
			}
		}
		return symbolicVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable basicGetSymbolicVariable() {
		return symbolicVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSymbolicVariable(Variable newSymbolicVariable) {
		Variable oldSymbolicVariable = symbolicVariable;
		symbolicVariable = newSymbolicVariable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.CONSTRAINT__SYMBOLIC_VARIABLE,
					oldSymbolicVariable, symbolicVariable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Variable> getHelperVariables() {
		if (helperVariables == null) {
			helperVariables = new EObjectResolvingEList<Variable>(Variable.class, this,
					GipsIntermediatePackage.CONSTRAINT__HELPER_VARIABLES);
		}
		return helperVariables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RelationalExpression> getHelperConstraints() {
		if (helperConstraints == null) {
			helperConstraints = new EObjectContainmentEList<RelationalExpression>(RelationalExpression.class, this,
					GipsIntermediatePackage.CONSTRAINT__HELPER_CONSTRAINTS);
		}
		return helperConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GipsIntermediatePackage.CONSTRAINT__DEPENDENCIES:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getDependencies()).basicAdd(otherEnd, msgs);
		case GipsIntermediatePackage.CONSTRAINT__REFERENCED_BY:
			if (referencedBy != null)
				msgs = ((InternalEObject) referencedBy).eInverseRemove(this,
						GipsIntermediatePackage.CONSTRAINT__DEPENDENCIES, Constraint.class, msgs);
			return basicSetReferencedBy((Constraint) otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GipsIntermediatePackage.CONSTRAINT__EXPRESSION:
			return basicSetExpression(null, msgs);
		case GipsIntermediatePackage.CONSTRAINT__DEPENDENCIES:
			return ((InternalEList<?>) getDependencies()).basicRemove(otherEnd, msgs);
		case GipsIntermediatePackage.CONSTRAINT__REFERENCED_BY:
			return basicSetReferencedBy(null, msgs);
		case GipsIntermediatePackage.CONSTRAINT__HELPER_CONSTRAINTS:
			return ((InternalEList<?>) getHelperConstraints()).basicRemove(otherEnd, msgs);
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
		case GipsIntermediatePackage.CONSTRAINT__NAME:
			return getName();
		case GipsIntermediatePackage.CONSTRAINT__DEPENDING:
			return isDepending();
		case GipsIntermediatePackage.CONSTRAINT__EXPRESSION:
			return getExpression();
		case GipsIntermediatePackage.CONSTRAINT__CONSTANT:
			return isConstant();
		case GipsIntermediatePackage.CONSTRAINT__NEGATED:
			return isNegated();
		case GipsIntermediatePackage.CONSTRAINT__DEPENDENCIES:
			return getDependencies();
		case GipsIntermediatePackage.CONSTRAINT__REFERENCED_BY:
			if (resolve)
				return getReferencedBy();
			return basicGetReferencedBy();
		case GipsIntermediatePackage.CONSTRAINT__SYMBOLIC_VARIABLE:
			if (resolve)
				return getSymbolicVariable();
			return basicGetSymbolicVariable();
		case GipsIntermediatePackage.CONSTRAINT__HELPER_VARIABLES:
			return getHelperVariables();
		case GipsIntermediatePackage.CONSTRAINT__HELPER_CONSTRAINTS:
			return getHelperConstraints();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
			setExpression((BoolExpression) newValue);
			return;
		case GipsIntermediatePackage.CONSTRAINT__CONSTANT:
			setConstant((Boolean) newValue);
			return;
		case GipsIntermediatePackage.CONSTRAINT__NEGATED:
			setNegated((Boolean) newValue);
			return;
		case GipsIntermediatePackage.CONSTRAINT__DEPENDENCIES:
			getDependencies().clear();
			getDependencies().addAll((Collection<? extends Constraint>) newValue);
			return;
		case GipsIntermediatePackage.CONSTRAINT__REFERENCED_BY:
			setReferencedBy((Constraint) newValue);
			return;
		case GipsIntermediatePackage.CONSTRAINT__SYMBOLIC_VARIABLE:
			setSymbolicVariable((Variable) newValue);
			return;
		case GipsIntermediatePackage.CONSTRAINT__HELPER_VARIABLES:
			getHelperVariables().clear();
			getHelperVariables().addAll((Collection<? extends Variable>) newValue);
			return;
		case GipsIntermediatePackage.CONSTRAINT__HELPER_CONSTRAINTS:
			getHelperConstraints().clear();
			getHelperConstraints().addAll((Collection<? extends RelationalExpression>) newValue);
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
		case GipsIntermediatePackage.CONSTRAINT__NAME:
			setName(NAME_EDEFAULT);
			return;
		case GipsIntermediatePackage.CONSTRAINT__DEPENDING:
			setDepending(DEPENDING_EDEFAULT);
			return;
		case GipsIntermediatePackage.CONSTRAINT__EXPRESSION:
			setExpression((BoolExpression) null);
			return;
		case GipsIntermediatePackage.CONSTRAINT__CONSTANT:
			setConstant(CONSTANT_EDEFAULT);
			return;
		case GipsIntermediatePackage.CONSTRAINT__NEGATED:
			setNegated(NEGATED_EDEFAULT);
			return;
		case GipsIntermediatePackage.CONSTRAINT__DEPENDENCIES:
			getDependencies().clear();
			return;
		case GipsIntermediatePackage.CONSTRAINT__REFERENCED_BY:
			setReferencedBy((Constraint) null);
			return;
		case GipsIntermediatePackage.CONSTRAINT__SYMBOLIC_VARIABLE:
			setSymbolicVariable((Variable) null);
			return;
		case GipsIntermediatePackage.CONSTRAINT__HELPER_VARIABLES:
			getHelperVariables().clear();
			return;
		case GipsIntermediatePackage.CONSTRAINT__HELPER_CONSTRAINTS:
			getHelperConstraints().clear();
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
		case GipsIntermediatePackage.CONSTRAINT__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case GipsIntermediatePackage.CONSTRAINT__DEPENDING:
			return depending != DEPENDING_EDEFAULT;
		case GipsIntermediatePackage.CONSTRAINT__EXPRESSION:
			return expression != null;
		case GipsIntermediatePackage.CONSTRAINT__CONSTANT:
			return constant != CONSTANT_EDEFAULT;
		case GipsIntermediatePackage.CONSTRAINT__NEGATED:
			return negated != NEGATED_EDEFAULT;
		case GipsIntermediatePackage.CONSTRAINT__DEPENDENCIES:
			return dependencies != null && !dependencies.isEmpty();
		case GipsIntermediatePackage.CONSTRAINT__REFERENCED_BY:
			return referencedBy != null;
		case GipsIntermediatePackage.CONSTRAINT__SYMBOLIC_VARIABLE:
			return symbolicVariable != null;
		case GipsIntermediatePackage.CONSTRAINT__HELPER_VARIABLES:
			return helperVariables != null && !helperVariables.isEmpty();
		case GipsIntermediatePackage.CONSTRAINT__HELPER_CONSTRAINTS:
			return helperConstraints != null && !helperConstraints.isEmpty();
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
		result.append(" (name: ");
		result.append(name);
		result.append(", depending: ");
		result.append(depending);
		result.append(", constant: ");
		result.append(constant);
		result.append(", negated: ");
		result.append(negated);
		result.append(')');
		return result.toString();
	}

} //ConstraintImpl
