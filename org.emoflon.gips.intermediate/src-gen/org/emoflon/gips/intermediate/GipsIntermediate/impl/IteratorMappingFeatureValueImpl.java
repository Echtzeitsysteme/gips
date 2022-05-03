/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.gips.intermediate.GipsIntermediate.FeatureExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Iterator;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.SetOperation;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Iterator Mapping Feature Value</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorMappingFeatureValueImpl#getStream <em>Stream</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorMappingFeatureValueImpl#getMappingContext <em>Mapping Context</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorMappingFeatureValueImpl#getFeatureExpression <em>Feature Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IteratorMappingFeatureValueImpl extends ValueExpressionImpl implements IteratorMappingFeatureValue {
	/**
	 * The cached value of the '{@link #getStream() <em>Stream</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getStream()
	 * @generated
	 * @ordered
	 */
	protected SetOperation stream;

	/**
	 * The cached value of the '{@link #getMappingContext() <em>Mapping Context</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getMappingContext()
	 * @generated
	 * @ordered
	 */
	protected Mapping mappingContext;

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
	protected IteratorMappingFeatureValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GipsIntermediatePackage.Literals.ITERATOR_MAPPING_FEATURE_VALUE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public SetOperation getStream() {
		if (stream != null && stream.eIsProxy()) {
			InternalEObject oldStream = (InternalEObject) stream;
			stream = (SetOperation) eResolveProxy(oldStream);
			if (stream != oldStream) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							GipsIntermediatePackage.ITERATOR_MAPPING_FEATURE_VALUE__STREAM, oldStream, stream));
			}
		}
		return stream;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public SetOperation basicGetStream() {
		return stream;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setStream(SetOperation newStream) {
		SetOperation oldStream = stream;
		stream = newStream;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.ITERATOR_MAPPING_FEATURE_VALUE__STREAM, oldStream, stream));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Mapping getMappingContext() {
		if (mappingContext != null && mappingContext.eIsProxy()) {
			InternalEObject oldMappingContext = (InternalEObject) mappingContext;
			mappingContext = (Mapping) eResolveProxy(oldMappingContext);
			if (mappingContext != oldMappingContext) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							GipsIntermediatePackage.ITERATOR_MAPPING_FEATURE_VALUE__MAPPING_CONTEXT, oldMappingContext,
							mappingContext));
			}
		}
		return mappingContext;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Mapping basicGetMappingContext() {
		return mappingContext;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setMappingContext(Mapping newMappingContext) {
		Mapping oldMappingContext = mappingContext;
		mappingContext = newMappingContext;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.ITERATOR_MAPPING_FEATURE_VALUE__MAPPING_CONTEXT, oldMappingContext,
					mappingContext));
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
					GipsIntermediatePackage.ITERATOR_MAPPING_FEATURE_VALUE__FEATURE_EXPRESSION, oldFeatureExpression,
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
	 * @generated
	 */
	public void setFeatureExpression(FeatureExpression newFeatureExpression) {
		if (newFeatureExpression != featureExpression) {
			NotificationChain msgs = null;
			if (featureExpression != null)
				msgs = ((InternalEObject) featureExpression).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- GipsIntermediatePackage.ITERATOR_MAPPING_FEATURE_VALUE__FEATURE_EXPRESSION,
						null, msgs);
			if (newFeatureExpression != null)
				msgs = ((InternalEObject) newFeatureExpression).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- GipsIntermediatePackage.ITERATOR_MAPPING_FEATURE_VALUE__FEATURE_EXPRESSION,
						null, msgs);
			msgs = basicSetFeatureExpression(newFeatureExpression, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.ITERATOR_MAPPING_FEATURE_VALUE__FEATURE_EXPRESSION, newFeatureExpression,
					newFeatureExpression));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GipsIntermediatePackage.ITERATOR_MAPPING_FEATURE_VALUE__FEATURE_EXPRESSION:
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
		case GipsIntermediatePackage.ITERATOR_MAPPING_FEATURE_VALUE__STREAM:
			if (resolve)
				return getStream();
			return basicGetStream();
		case GipsIntermediatePackage.ITERATOR_MAPPING_FEATURE_VALUE__MAPPING_CONTEXT:
			if (resolve)
				return getMappingContext();
			return basicGetMappingContext();
		case GipsIntermediatePackage.ITERATOR_MAPPING_FEATURE_VALUE__FEATURE_EXPRESSION:
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
		case GipsIntermediatePackage.ITERATOR_MAPPING_FEATURE_VALUE__STREAM:
			setStream((SetOperation) newValue);
			return;
		case GipsIntermediatePackage.ITERATOR_MAPPING_FEATURE_VALUE__MAPPING_CONTEXT:
			setMappingContext((Mapping) newValue);
			return;
		case GipsIntermediatePackage.ITERATOR_MAPPING_FEATURE_VALUE__FEATURE_EXPRESSION:
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
		case GipsIntermediatePackage.ITERATOR_MAPPING_FEATURE_VALUE__STREAM:
			setStream((SetOperation) null);
			return;
		case GipsIntermediatePackage.ITERATOR_MAPPING_FEATURE_VALUE__MAPPING_CONTEXT:
			setMappingContext((Mapping) null);
			return;
		case GipsIntermediatePackage.ITERATOR_MAPPING_FEATURE_VALUE__FEATURE_EXPRESSION:
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
		case GipsIntermediatePackage.ITERATOR_MAPPING_FEATURE_VALUE__STREAM:
			return stream != null;
		case GipsIntermediatePackage.ITERATOR_MAPPING_FEATURE_VALUE__MAPPING_CONTEXT:
			return mappingContext != null;
		case GipsIntermediatePackage.ITERATOR_MAPPING_FEATURE_VALUE__FEATURE_EXPRESSION:
			return featureExpression != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == Iterator.class) {
			switch (derivedFeatureID) {
			case GipsIntermediatePackage.ITERATOR_MAPPING_FEATURE_VALUE__STREAM:
				return GipsIntermediatePackage.ITERATOR__STREAM;
			default:
				return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == Iterator.class) {
			switch (baseFeatureID) {
			case GipsIntermediatePackage.ITERATOR__STREAM:
				return GipsIntermediatePackage.ITERATOR_MAPPING_FEATURE_VALUE__STREAM;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} // IteratorMappingFeatureValueImpl
