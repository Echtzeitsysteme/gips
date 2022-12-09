/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.emoflon.gips.intermediate.GipsIntermediate.GTParameterVariable;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.gips.intermediate.GipsIntermediate.Variable;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Mapping</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.MappingImpl#getContextPattern
 * <em>Context Pattern</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.MappingImpl#getFreeVariables
 * <em>Free Variables</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.MappingImpl#getBoundVariables
 * <em>Bound Variables</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class MappingImpl extends VariableSetImpl implements Mapping {
	/**
	 * The cached value of the '{@link #getContextPattern() <em>Context
	 * Pattern</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getContextPattern()
	 * @generated
	 * @ordered
	 */
	protected IBeXContextPattern contextPattern;

	/**
	 * The cached value of the '{@link #getFreeVariables() <em>Free Variables</em>}'
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFreeVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<Variable> freeVariables;

	/**
	 * The cached value of the '{@link #getBoundVariables() <em>Bound
	 * Variables</em>}' reference list. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see #getBoundVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<GTParameterVariable> boundVariables;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected MappingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GipsIntermediatePackage.Literals.MAPPING;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IBeXContextPattern getContextPattern() {
		if (contextPattern != null && contextPattern.eIsProxy()) {
			InternalEObject oldContextPattern = (InternalEObject) contextPattern;
			contextPattern = (IBeXContextPattern) eResolveProxy(oldContextPattern);
			if (contextPattern != oldContextPattern) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							GipsIntermediatePackage.MAPPING__CONTEXT_PATTERN, oldContextPattern, contextPattern));
			}
		}
		return contextPattern;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IBeXContextPattern basicGetContextPattern() {
		return contextPattern;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setContextPattern(IBeXContextPattern newContextPattern) {
		IBeXContextPattern oldContextPattern = contextPattern;
		contextPattern = newContextPattern;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.MAPPING__CONTEXT_PATTERN,
					oldContextPattern, contextPattern));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Variable> getFreeVariables() {
		if (freeVariables == null) {
			freeVariables = new EObjectResolvingEList<Variable>(Variable.class, this,
					GipsIntermediatePackage.MAPPING__FREE_VARIABLES);
		}
		return freeVariables;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<GTParameterVariable> getBoundVariables() {
		if (boundVariables == null) {
			boundVariables = new EObjectResolvingEList<GTParameterVariable>(GTParameterVariable.class, this,
					GipsIntermediatePackage.MAPPING__BOUND_VARIABLES);
		}
		return boundVariables;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case GipsIntermediatePackage.MAPPING__CONTEXT_PATTERN:
			if (resolve)
				return getContextPattern();
			return basicGetContextPattern();
		case GipsIntermediatePackage.MAPPING__FREE_VARIABLES:
			return getFreeVariables();
		case GipsIntermediatePackage.MAPPING__BOUND_VARIABLES:
			return getBoundVariables();
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
		case GipsIntermediatePackage.MAPPING__CONTEXT_PATTERN:
			setContextPattern((IBeXContextPattern) newValue);
			return;
		case GipsIntermediatePackage.MAPPING__FREE_VARIABLES:
			getFreeVariables().clear();
			getFreeVariables().addAll((Collection<? extends Variable>) newValue);
			return;
		case GipsIntermediatePackage.MAPPING__BOUND_VARIABLES:
			getBoundVariables().clear();
			getBoundVariables().addAll((Collection<? extends GTParameterVariable>) newValue);
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
		case GipsIntermediatePackage.MAPPING__CONTEXT_PATTERN:
			setContextPattern((IBeXContextPattern) null);
			return;
		case GipsIntermediatePackage.MAPPING__FREE_VARIABLES:
			getFreeVariables().clear();
			return;
		case GipsIntermediatePackage.MAPPING__BOUND_VARIABLES:
			getBoundVariables().clear();
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
		case GipsIntermediatePackage.MAPPING__CONTEXT_PATTERN:
			return contextPattern != null;
		case GipsIntermediatePackage.MAPPING__FREE_VARIABLES:
			return freeVariables != null && !freeVariables.isEmpty();
		case GipsIntermediatePackage.MAPPING__BOUND_VARIABLES:
			return boundVariables != null && !boundVariables.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // MappingImpl
