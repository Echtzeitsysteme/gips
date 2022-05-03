/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.gips.intermediate.GipsIntermediate.ContextSumExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.FeatureExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.VariableSet;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Context Sum Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextSumExpressionImpl#getContext <em>Context</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextSumExpressionImpl#getNode <em>Node</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextSumExpressionImpl#getFeature <em>Feature</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ContextSumExpressionImpl extends SumExpressionImpl implements ContextSumExpression {
	/**
	 * The cached value of the '{@link #getContext() <em>Context</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContext()
	 * @generated
	 * @ordered
	 */
	protected VariableSet context;

	/**
	 * The cached value of the '{@link #getNode() <em>Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNode()
	 * @generated
	 * @ordered
	 */
	protected IBeXNode node;

	/**
	 * The cached value of the '{@link #getFeature() <em>Feature</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeature()
	 * @generated
	 * @ordered
	 */
	protected FeatureExpression feature;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ContextSumExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GipsIntermediatePackage.Literals.CONTEXT_SUM_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VariableSet getContext() {
		if (context != null && context.eIsProxy()) {
			InternalEObject oldContext = (InternalEObject) context;
			context = (VariableSet) eResolveProxy(oldContext);
			if (context != oldContext) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							GipsIntermediatePackage.CONTEXT_SUM_EXPRESSION__CONTEXT, oldContext, context));
			}
		}
		return context;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VariableSet basicGetContext() {
		return context;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContext(VariableSet newContext) {
		VariableSet oldContext = context;
		context = newContext;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.CONTEXT_SUM_EXPRESSION__CONTEXT, oldContext, context));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXNode getNode() {
		if (node != null && node.eIsProxy()) {
			InternalEObject oldNode = (InternalEObject) node;
			node = (IBeXNode) eResolveProxy(oldNode);
			if (node != oldNode) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							GipsIntermediatePackage.CONTEXT_SUM_EXPRESSION__NODE, oldNode, node));
			}
		}
		return node;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXNode basicGetNode() {
		return node;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNode(IBeXNode newNode) {
		IBeXNode oldNode = node;
		node = newNode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.CONTEXT_SUM_EXPRESSION__NODE,
					oldNode, node));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureExpression getFeature() {
		return feature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFeature(FeatureExpression newFeature, NotificationChain msgs) {
		FeatureExpression oldFeature = feature;
		feature = newFeature;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.CONTEXT_SUM_EXPRESSION__FEATURE, oldFeature, newFeature);
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
	public void setFeature(FeatureExpression newFeature) {
		if (newFeature != feature) {
			NotificationChain msgs = null;
			if (feature != null)
				msgs = ((InternalEObject) feature).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.CONTEXT_SUM_EXPRESSION__FEATURE, null, msgs);
			if (newFeature != null)
				msgs = ((InternalEObject) newFeature).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.CONTEXT_SUM_EXPRESSION__FEATURE, null, msgs);
			msgs = basicSetFeature(newFeature, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.CONTEXT_SUM_EXPRESSION__FEATURE, newFeature, newFeature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GipsIntermediatePackage.CONTEXT_SUM_EXPRESSION__FEATURE:
			return basicSetFeature(null, msgs);
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
		case GipsIntermediatePackage.CONTEXT_SUM_EXPRESSION__CONTEXT:
			if (resolve)
				return getContext();
			return basicGetContext();
		case GipsIntermediatePackage.CONTEXT_SUM_EXPRESSION__NODE:
			if (resolve)
				return getNode();
			return basicGetNode();
		case GipsIntermediatePackage.CONTEXT_SUM_EXPRESSION__FEATURE:
			return getFeature();
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
		case GipsIntermediatePackage.CONTEXT_SUM_EXPRESSION__CONTEXT:
			setContext((VariableSet) newValue);
			return;
		case GipsIntermediatePackage.CONTEXT_SUM_EXPRESSION__NODE:
			setNode((IBeXNode) newValue);
			return;
		case GipsIntermediatePackage.CONTEXT_SUM_EXPRESSION__FEATURE:
			setFeature((FeatureExpression) newValue);
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
		case GipsIntermediatePackage.CONTEXT_SUM_EXPRESSION__CONTEXT:
			setContext((VariableSet) null);
			return;
		case GipsIntermediatePackage.CONTEXT_SUM_EXPRESSION__NODE:
			setNode((IBeXNode) null);
			return;
		case GipsIntermediatePackage.CONTEXT_SUM_EXPRESSION__FEATURE:
			setFeature((FeatureExpression) null);
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
		case GipsIntermediatePackage.CONTEXT_SUM_EXPRESSION__CONTEXT:
			return context != null;
		case GipsIntermediatePackage.CONTEXT_SUM_EXPRESSION__NODE:
			return node != null;
		case GipsIntermediatePackage.CONTEXT_SUM_EXPRESSION__FEATURE:
			return feature != null;
		}
		return super.eIsSet(featureID);
	}

} //ContextSumExpressionImpl
