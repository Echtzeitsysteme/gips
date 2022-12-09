/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingVariablesReference;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Context
 * Mapping Variables Reference</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextMappingVariablesReferenceImpl#getVar
 * <em>Var</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextMappingVariablesReferenceImpl#getMappingContext
 * <em>Mapping Context</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ContextMappingVariablesReferenceImpl extends ValueExpressionImpl
		implements ContextMappingVariablesReference {
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
	 * The cached value of the '{@link #getMappingContext() <em>Mapping
	 * Context</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMappingContext()
	 * @generated
	 * @ordered
	 */
	protected Mapping mappingContext;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ContextMappingVariablesReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GipsIntermediatePackage.Literals.CONTEXT_MAPPING_VARIABLES_REFERENCE;
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
					GipsIntermediatePackage.CONTEXT_MAPPING_VARIABLES_REFERENCE__VAR, oldVar, newVar);
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
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.CONTEXT_MAPPING_VARIABLES_REFERENCE__VAR, null,
						msgs);
			if (newVar != null)
				msgs = ((InternalEObject) newVar).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.CONTEXT_MAPPING_VARIABLES_REFERENCE__VAR, null,
						msgs);
			msgs = basicSetVar(newVar, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.CONTEXT_MAPPING_VARIABLES_REFERENCE__VAR, newVar, newVar));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Mapping getMappingContext() {
		if (mappingContext != null && mappingContext.eIsProxy()) {
			InternalEObject oldMappingContext = (InternalEObject) mappingContext;
			mappingContext = (Mapping) eResolveProxy(oldMappingContext);
			if (mappingContext != oldMappingContext) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							GipsIntermediatePackage.CONTEXT_MAPPING_VARIABLES_REFERENCE__MAPPING_CONTEXT,
							oldMappingContext, mappingContext));
			}
		}
		return mappingContext;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Mapping basicGetMappingContext() {
		return mappingContext;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setMappingContext(Mapping newMappingContext) {
		Mapping oldMappingContext = mappingContext;
		mappingContext = newMappingContext;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.CONTEXT_MAPPING_VARIABLES_REFERENCE__MAPPING_CONTEXT, oldMappingContext,
					mappingContext));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GipsIntermediatePackage.CONTEXT_MAPPING_VARIABLES_REFERENCE__VAR:
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
		case GipsIntermediatePackage.CONTEXT_MAPPING_VARIABLES_REFERENCE__VAR:
			return getVar();
		case GipsIntermediatePackage.CONTEXT_MAPPING_VARIABLES_REFERENCE__MAPPING_CONTEXT:
			if (resolve)
				return getMappingContext();
			return basicGetMappingContext();
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
		case GipsIntermediatePackage.CONTEXT_MAPPING_VARIABLES_REFERENCE__VAR:
			setVar((VariableReference) newValue);
			return;
		case GipsIntermediatePackage.CONTEXT_MAPPING_VARIABLES_REFERENCE__MAPPING_CONTEXT:
			setMappingContext((Mapping) newValue);
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
		case GipsIntermediatePackage.CONTEXT_MAPPING_VARIABLES_REFERENCE__VAR:
			setVar((VariableReference) null);
			return;
		case GipsIntermediatePackage.CONTEXT_MAPPING_VARIABLES_REFERENCE__MAPPING_CONTEXT:
			setMappingContext((Mapping) null);
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
		case GipsIntermediatePackage.CONTEXT_MAPPING_VARIABLES_REFERENCE__VAR:
			return var != null;
		case GipsIntermediatePackage.CONTEXT_MAPPING_VARIABLES_REFERENCE__MAPPING_CONTEXT:
			return mappingContext != null;
		}
		return super.eIsSet(featureID);
	}

} // ContextMappingVariablesReferenceImpl
