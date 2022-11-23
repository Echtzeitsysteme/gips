/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference;
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReferenceValue;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Variable Reference Value</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.VariableReferenceValueImpl#getVar
 * <em>Var</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VariableReferenceValueImpl extends ValueExpressionImpl implements VariableReferenceValue {
	/**
	 * The cached value of the '{@link #getVar() <em>Var</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getVar()
	 * @generated
	 * @ordered
	 */
	protected VariableReference var;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected VariableReferenceValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GipsIntermediatePackage.Literals.VARIABLE_REFERENCE_VALUE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public VariableReference getVar() {
		return var;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetVar(VariableReference newVar, NotificationChain msgs) {
		VariableReference oldVar = var;
		var = newVar;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.VARIABLE_REFERENCE_VALUE__VAR, oldVar, newVar);
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
	public void setVar(VariableReference newVar) {
		if (newVar != var) {
			NotificationChain msgs = null;
			if (var != null)
				msgs = ((InternalEObject) var).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.VARIABLE_REFERENCE_VALUE__VAR, null, msgs);
			if (newVar != null)
				msgs = ((InternalEObject) newVar).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.VARIABLE_REFERENCE_VALUE__VAR, null, msgs);
			msgs = basicSetVar(newVar, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.VARIABLE_REFERENCE_VALUE__VAR,
					newVar, newVar));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GipsIntermediatePackage.VARIABLE_REFERENCE_VALUE__VAR:
			return basicSetVar(null, msgs);
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
		case GipsIntermediatePackage.VARIABLE_REFERENCE_VALUE__VAR:
			return getVar();
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
		case GipsIntermediatePackage.VARIABLE_REFERENCE_VALUE__VAR:
			setVar((VariableReference) newValue);
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
		case GipsIntermediatePackage.VARIABLE_REFERENCE_VALUE__VAR:
			setVar((VariableReference) null);
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
		case GipsIntermediatePackage.VARIABLE_REFERENCE_VALUE__VAR:
			return var != null;
		}
		return super.eIsSet(featureID);
	}

} // VariableReferenceValueImpl
