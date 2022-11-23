/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.gips.intermediate.GipsIntermediate.GTParameterVariable;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXParameter;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>GT
 * Parameter Variable</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.GTParameterVariableImpl#getParameter
 * <em>Parameter</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.GTParameterVariableImpl#getRule
 * <em>Rule</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GTParameterVariableImpl extends VariableImpl implements GTParameterVariable {
	/**
	 * The cached value of the '{@link #getParameter() <em>Parameter</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getParameter()
	 * @generated
	 * @ordered
	 */
	protected IBeXParameter parameter;

	/**
	 * The cached value of the '{@link #getRule() <em>Rule</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getRule()
	 * @generated
	 * @ordered
	 */
	protected IBeXRule rule;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected GTParameterVariableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GipsIntermediatePackage.Literals.GT_PARAMETER_VARIABLE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IBeXParameter getParameter() {
		if (parameter != null && parameter.eIsProxy()) {
			InternalEObject oldParameter = (InternalEObject) parameter;
			parameter = (IBeXParameter) eResolveProxy(oldParameter);
			if (parameter != oldParameter) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							GipsIntermediatePackage.GT_PARAMETER_VARIABLE__PARAMETER, oldParameter, parameter));
			}
		}
		return parameter;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IBeXParameter basicGetParameter() {
		return parameter;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setParameter(IBeXParameter newParameter) {
		IBeXParameter oldParameter = parameter;
		parameter = newParameter;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.GT_PARAMETER_VARIABLE__PARAMETER, oldParameter, parameter));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IBeXRule getRule() {
		if (rule != null && rule.eIsProxy()) {
			InternalEObject oldRule = (InternalEObject) rule;
			rule = (IBeXRule) eResolveProxy(oldRule);
			if (rule != oldRule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							GipsIntermediatePackage.GT_PARAMETER_VARIABLE__RULE, oldRule, rule));
			}
		}
		return rule;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IBeXRule basicGetRule() {
		return rule;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setRule(IBeXRule newRule) {
		IBeXRule oldRule = rule;
		rule = newRule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.GT_PARAMETER_VARIABLE__RULE,
					oldRule, rule));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case GipsIntermediatePackage.GT_PARAMETER_VARIABLE__PARAMETER:
			if (resolve)
				return getParameter();
			return basicGetParameter();
		case GipsIntermediatePackage.GT_PARAMETER_VARIABLE__RULE:
			if (resolve)
				return getRule();
			return basicGetRule();
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
		case GipsIntermediatePackage.GT_PARAMETER_VARIABLE__PARAMETER:
			setParameter((IBeXParameter) newValue);
			return;
		case GipsIntermediatePackage.GT_PARAMETER_VARIABLE__RULE:
			setRule((IBeXRule) newValue);
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
		case GipsIntermediatePackage.GT_PARAMETER_VARIABLE__PARAMETER:
			setParameter((IBeXParameter) null);
			return;
		case GipsIntermediatePackage.GT_PARAMETER_VARIABLE__RULE:
			setRule((IBeXRule) null);
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
		case GipsIntermediatePackage.GT_PARAMETER_VARIABLE__PARAMETER:
			return parameter != null;
		case GipsIntermediatePackage.GT_PARAMETER_VARIABLE__RULE:
			return rule != null;
		}
		return super.eIsSet(featureID);
	}

} // GTParameterVariableImpl
