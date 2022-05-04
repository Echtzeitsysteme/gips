/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.Iterator;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeValue;
import org.emoflon.gips.intermediate.GipsIntermediate.Pattern;
import org.emoflon.gips.intermediate.GipsIntermediate.SetOperation;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Iterator Pattern Node Value</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorPatternNodeValueImpl#getStream
 * <em>Stream</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorPatternNodeValueImpl#getPatternContext
 * <em>Pattern Context</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorPatternNodeValueImpl#getNode
 * <em>Node</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IteratorPatternNodeValueImpl extends ValueExpressionImpl implements IteratorPatternNodeValue {
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
	 * The cached value of the '{@link #getNode() <em>Node</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getNode()
	 * @generated
	 * @ordered
	 */
	protected IBeXNode node;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected IteratorPatternNodeValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GipsIntermediatePackage.Literals.ITERATOR_PATTERN_NODE_VALUE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public SetOperation getStream() {
		if (stream != null && stream.eIsProxy()) {
			InternalEObject oldStream = (InternalEObject) stream;
			stream = (SetOperation) eResolveProxy(oldStream);
			if (stream != oldStream) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							GipsIntermediatePackage.ITERATOR_PATTERN_NODE_VALUE__STREAM, oldStream, stream));
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
	public void setStream(SetOperation newStream) {
		SetOperation oldStream = stream;
		stream = newStream;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.ITERATOR_PATTERN_NODE_VALUE__STREAM, oldStream, stream));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Pattern getPatternContext() {
		if (patternContext != null && patternContext.eIsProxy()) {
			InternalEObject oldPatternContext = (InternalEObject) patternContext;
			patternContext = (Pattern) eResolveProxy(oldPatternContext);
			if (patternContext != oldPatternContext) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							GipsIntermediatePackage.ITERATOR_PATTERN_NODE_VALUE__PATTERN_CONTEXT, oldPatternContext,
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
	public void setPatternContext(Pattern newPatternContext) {
		Pattern oldPatternContext = patternContext;
		patternContext = newPatternContext;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.ITERATOR_PATTERN_NODE_VALUE__PATTERN_CONTEXT, oldPatternContext,
					patternContext));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IBeXNode getNode() {
		if (node != null && node.eIsProxy()) {
			InternalEObject oldNode = (InternalEObject) node;
			node = (IBeXNode) eResolveProxy(oldNode);
			if (node != oldNode) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							GipsIntermediatePackage.ITERATOR_PATTERN_NODE_VALUE__NODE, oldNode, node));
			}
		}
		return node;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IBeXNode basicGetNode() {
		return node;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setNode(IBeXNode newNode) {
		IBeXNode oldNode = node;
		node = newNode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.ITERATOR_PATTERN_NODE_VALUE__NODE, oldNode, node));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case GipsIntermediatePackage.ITERATOR_PATTERN_NODE_VALUE__STREAM:
			if (resolve)
				return getStream();
			return basicGetStream();
		case GipsIntermediatePackage.ITERATOR_PATTERN_NODE_VALUE__PATTERN_CONTEXT:
			if (resolve)
				return getPatternContext();
			return basicGetPatternContext();
		case GipsIntermediatePackage.ITERATOR_PATTERN_NODE_VALUE__NODE:
			if (resolve)
				return getNode();
			return basicGetNode();
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
		case GipsIntermediatePackage.ITERATOR_PATTERN_NODE_VALUE__STREAM:
			setStream((SetOperation) newValue);
			return;
		case GipsIntermediatePackage.ITERATOR_PATTERN_NODE_VALUE__PATTERN_CONTEXT:
			setPatternContext((Pattern) newValue);
			return;
		case GipsIntermediatePackage.ITERATOR_PATTERN_NODE_VALUE__NODE:
			setNode((IBeXNode) newValue);
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
		case GipsIntermediatePackage.ITERATOR_PATTERN_NODE_VALUE__STREAM:
			setStream((SetOperation) null);
			return;
		case GipsIntermediatePackage.ITERATOR_PATTERN_NODE_VALUE__PATTERN_CONTEXT:
			setPatternContext((Pattern) null);
			return;
		case GipsIntermediatePackage.ITERATOR_PATTERN_NODE_VALUE__NODE:
			setNode((IBeXNode) null);
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
		case GipsIntermediatePackage.ITERATOR_PATTERN_NODE_VALUE__STREAM:
			return stream != null;
		case GipsIntermediatePackage.ITERATOR_PATTERN_NODE_VALUE__PATTERN_CONTEXT:
			return patternContext != null;
		case GipsIntermediatePackage.ITERATOR_PATTERN_NODE_VALUE__NODE:
			return node != null;
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
			case GipsIntermediatePackage.ITERATOR_PATTERN_NODE_VALUE__STREAM:
				return GipsIntermediatePackage.ITERATOR__STREAM;
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
			case GipsIntermediatePackage.ITERATOR__STREAM:
				return GipsIntermediatePackage.ITERATOR_PATTERN_NODE_VALUE__STREAM;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} // IteratorPatternNodeValueImpl
