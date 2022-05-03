/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.gips.intermediate.GipsIntermediate.FeatureExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Iterator Pattern Node Feature Value</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorPatternNodeFeatureValueImpl#getFeatureExpression <em>Feature Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IteratorPatternNodeFeatureValueImpl extends IteratorPatternNodeValueImpl
		implements IteratorPatternNodeFeatureValue {
	/**
	 * The cached value of the '{@link #getFeatureExpression() <em>Feature Expression</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getFeatureExpression()
	 * @generated
	 * @ordered
	 */
	protected FeatureExpression featureExpression;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected IteratorPatternNodeFeatureValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GipsIntermediatePackage.Literals.ITERATOR_PATTERN_NODE_FEATURE_VALUE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureExpression getFeatureExpression() {
		return featureExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFeatureExpression(FeatureExpression newFeatureExpression, NotificationChain msgs) {
		FeatureExpression oldFeatureExpression = featureExpression;
		featureExpression = newFeatureExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.ITERATOR_PATTERN_NODE_FEATURE_VALUE__FEATURE_EXPRESSION,
					oldFeatureExpression, newFeatureExpression);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeatureExpression(FeatureExpression newFeatureExpression) {
		if (newFeatureExpression != featureExpression) {
			NotificationChain msgs = null;
			if (featureExpression != null)
				msgs = ((InternalEObject) featureExpression).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- GipsIntermediatePackage.ITERATOR_PATTERN_NODE_FEATURE_VALUE__FEATURE_EXPRESSION,
						null, msgs);
			if (newFeatureExpression != null)
				msgs = ((InternalEObject) newFeatureExpression).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- GipsIntermediatePackage.ITERATOR_PATTERN_NODE_FEATURE_VALUE__FEATURE_EXPRESSION,
						null, msgs);
			msgs = basicSetFeatureExpression(newFeatureExpression, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.ITERATOR_PATTERN_NODE_FEATURE_VALUE__FEATURE_EXPRESSION,
					newFeatureExpression, newFeatureExpression));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GipsIntermediatePackage.ITERATOR_PATTERN_NODE_FEATURE_VALUE__FEATURE_EXPRESSION:
			return basicSetFeatureExpression(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case GipsIntermediatePackage.ITERATOR_PATTERN_NODE_FEATURE_VALUE__FEATURE_EXPRESSION:
			return getFeatureExpression();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case GipsIntermediatePackage.ITERATOR_PATTERN_NODE_FEATURE_VALUE__FEATURE_EXPRESSION:
			setFeatureExpression((FeatureExpression) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case GipsIntermediatePackage.ITERATOR_PATTERN_NODE_FEATURE_VALUE__FEATURE_EXPRESSION:
			setFeatureExpression((FeatureExpression) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case GipsIntermediatePackage.ITERATOR_PATTERN_NODE_FEATURE_VALUE__FEATURE_EXPRESSION:
			return featureExpression != null;
		}
		return super.eIsSet(featureID);
	}

} // IteratorPatternNodeFeatureValueImpl
