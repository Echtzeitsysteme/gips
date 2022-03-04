/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.emoflon.roam.intermediate.RoamIntermediate.FeatureExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.Iterator;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorPatternFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.Pattern;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage;
import org.emoflon.roam.intermediate.RoamIntermediate.SetOperation;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Iterator Pattern Feature Value</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorPatternFeatureValueImpl#getStream
 * <em>Stream</em>}</li>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorPatternFeatureValueImpl#getPatternContext
 * <em>Pattern Context</em>}</li>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorPatternFeatureValueImpl#getFeatureExpression
 * <em>Feature Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IteratorPatternFeatureValueImpl extends ValueExpressionImpl implements IteratorPatternFeatureValue {
	/**
	 * The cached value of the '{@link #getStream() <em>Stream</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getStream()
	 * @generated
	 * @ordered
	 */
	protected SetOperation stream;

	/**
	 * The cached value of the '{@link #getPatternContext() <em>Pattern
	 * Context</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getPatternContext()
	 * @generated
	 * @ordered
	 */
	protected Pattern patternContext;

	/**
	 * The cached value of the '{@link #getFeatureExpression() <em>Feature
	 * Expression</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see #getFeatureExpression()
	 * @generated
	 * @ordered
	 */
	protected FeatureExpression featureExpression;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected IteratorPatternFeatureValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RoamIntermediatePackage.Literals.ITERATOR_PATTERN_FEATURE_VALUE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public SetOperation getStream() {
		if (stream != null && stream.eIsProxy()) {
			InternalEObject oldStream = (InternalEObject) stream;
			stream = (SetOperation) eResolveProxy(oldStream);
			if (stream != oldStream) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							RoamIntermediatePackage.ITERATOR_PATTERN_FEATURE_VALUE__STREAM, oldStream, stream));
			}
		}
		return stream;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public SetOperation basicGetStream() {
		return stream;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setStream(SetOperation newStream) {
		SetOperation oldStream = stream;
		stream = newStream;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.ITERATOR_PATTERN_FEATURE_VALUE__STREAM, oldStream, stream));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Pattern getPatternContext() {
		if (patternContext != null && patternContext.eIsProxy()) {
			InternalEObject oldPatternContext = (InternalEObject) patternContext;
			patternContext = (Pattern) eResolveProxy(oldPatternContext);
			if (patternContext != oldPatternContext) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							RoamIntermediatePackage.ITERATOR_PATTERN_FEATURE_VALUE__PATTERN_CONTEXT, oldPatternContext,
							patternContext));
			}
		}
		return patternContext;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public Pattern basicGetPatternContext() {
		return patternContext;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setPatternContext(Pattern newPatternContext) {
		Pattern oldPatternContext = patternContext;
		patternContext = newPatternContext;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.ITERATOR_PATTERN_FEATURE_VALUE__PATTERN_CONTEXT, oldPatternContext,
					patternContext));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public FeatureExpression getFeatureExpression() {
		return featureExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetFeatureExpression(FeatureExpression newFeatureExpression, NotificationChain msgs) {
		FeatureExpression oldFeatureExpression = featureExpression;
		featureExpression = newFeatureExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.ITERATOR_PATTERN_FEATURE_VALUE__FEATURE_EXPRESSION, oldFeatureExpression,
					newFeatureExpression);
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
	@Override
	public void setFeatureExpression(FeatureExpression newFeatureExpression) {
		if (newFeatureExpression != featureExpression) {
			NotificationChain msgs = null;
			if (featureExpression != null)
				msgs = ((InternalEObject) featureExpression).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- RoamIntermediatePackage.ITERATOR_PATTERN_FEATURE_VALUE__FEATURE_EXPRESSION,
						null, msgs);
			if (newFeatureExpression != null)
				msgs = ((InternalEObject) newFeatureExpression).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- RoamIntermediatePackage.ITERATOR_PATTERN_FEATURE_VALUE__FEATURE_EXPRESSION,
						null, msgs);
			msgs = basicSetFeatureExpression(newFeatureExpression, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.ITERATOR_PATTERN_FEATURE_VALUE__FEATURE_EXPRESSION, newFeatureExpression,
					newFeatureExpression));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case RoamIntermediatePackage.ITERATOR_PATTERN_FEATURE_VALUE__FEATURE_EXPRESSION:
			return basicSetFeatureExpression(null, msgs);
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
		case RoamIntermediatePackage.ITERATOR_PATTERN_FEATURE_VALUE__STREAM:
			if (resolve)
				return getStream();
			return basicGetStream();
		case RoamIntermediatePackage.ITERATOR_PATTERN_FEATURE_VALUE__PATTERN_CONTEXT:
			if (resolve)
				return getPatternContext();
			return basicGetPatternContext();
		case RoamIntermediatePackage.ITERATOR_PATTERN_FEATURE_VALUE__FEATURE_EXPRESSION:
			return getFeatureExpression();
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
		case RoamIntermediatePackage.ITERATOR_PATTERN_FEATURE_VALUE__STREAM:
			setStream((SetOperation) newValue);
			return;
		case RoamIntermediatePackage.ITERATOR_PATTERN_FEATURE_VALUE__PATTERN_CONTEXT:
			setPatternContext((Pattern) newValue);
			return;
		case RoamIntermediatePackage.ITERATOR_PATTERN_FEATURE_VALUE__FEATURE_EXPRESSION:
			setFeatureExpression((FeatureExpression) newValue);
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
		case RoamIntermediatePackage.ITERATOR_PATTERN_FEATURE_VALUE__STREAM:
			setStream((SetOperation) null);
			return;
		case RoamIntermediatePackage.ITERATOR_PATTERN_FEATURE_VALUE__PATTERN_CONTEXT:
			setPatternContext((Pattern) null);
			return;
		case RoamIntermediatePackage.ITERATOR_PATTERN_FEATURE_VALUE__FEATURE_EXPRESSION:
			setFeatureExpression((FeatureExpression) null);
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
		case RoamIntermediatePackage.ITERATOR_PATTERN_FEATURE_VALUE__STREAM:
			return stream != null;
		case RoamIntermediatePackage.ITERATOR_PATTERN_FEATURE_VALUE__PATTERN_CONTEXT:
			return patternContext != null;
		case RoamIntermediatePackage.ITERATOR_PATTERN_FEATURE_VALUE__FEATURE_EXPRESSION:
			return featureExpression != null;
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
		if (baseClass == Iterator.class) {
			switch (derivedFeatureID) {
			case RoamIntermediatePackage.ITERATOR_PATTERN_FEATURE_VALUE__STREAM:
				return RoamIntermediatePackage.ITERATOR__STREAM;
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
		if (baseClass == Iterator.class) {
			switch (baseFeatureID) {
			case RoamIntermediatePackage.ITERATOR__STREAM:
				return RoamIntermediatePackage.ITERATOR_PATTERN_FEATURE_VALUE__STREAM;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} // IteratorPatternFeatureValueImpl
