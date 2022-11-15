/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.Iterator;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeValue;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.gips.intermediate.GipsIntermediate.SetOperation;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Iterator Mapping Node Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorMappingNodeValueImpl#getStream <em>Stream</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorMappingNodeValueImpl#getMappingContext <em>Mapping Context</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorMappingNodeValueImpl#getNode <em>Node</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IteratorMappingNodeValueImpl extends ValueExpressionImpl implements IteratorMappingNodeValue {
	/**
	 * The cached value of the '{@link #getStream() <em>Stream</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStream()
	 * @generated
	 * @ordered
	 */
	protected SetOperation stream;

	/**
	 * The cached value of the '{@link #getMappingContext() <em>Mapping Context</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappingContext()
	 * @generated
	 * @ordered
	 */
	protected Mapping mappingContext;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IteratorMappingNodeValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GipsIntermediatePackage.Literals.ITERATOR_MAPPING_NODE_VALUE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SetOperation getStream() {
		if (stream != null && stream.eIsProxy()) {
			InternalEObject oldStream = (InternalEObject) stream;
			stream = (SetOperation) eResolveProxy(oldStream);
			if (stream != oldStream) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							GipsIntermediatePackage.ITERATOR_MAPPING_NODE_VALUE__STREAM, oldStream, stream));
			}
		}
		return stream;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SetOperation basicGetStream() {
		return stream;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStream(SetOperation newStream) {
		SetOperation oldStream = stream;
		stream = newStream;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.ITERATOR_MAPPING_NODE_VALUE__STREAM, oldStream, stream));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Mapping getMappingContext() {
		if (mappingContext != null && mappingContext.eIsProxy()) {
			InternalEObject oldMappingContext = (InternalEObject) mappingContext;
			mappingContext = (Mapping) eResolveProxy(oldMappingContext);
			if (mappingContext != oldMappingContext) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							GipsIntermediatePackage.ITERATOR_MAPPING_NODE_VALUE__MAPPING_CONTEXT, oldMappingContext,
							mappingContext));
			}
		}
		return mappingContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Mapping basicGetMappingContext() {
		return mappingContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMappingContext(Mapping newMappingContext) {
		Mapping oldMappingContext = mappingContext;
		mappingContext = newMappingContext;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.ITERATOR_MAPPING_NODE_VALUE__MAPPING_CONTEXT, oldMappingContext,
					mappingContext));
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
							GipsIntermediatePackage.ITERATOR_MAPPING_NODE_VALUE__NODE, oldNode, node));
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
			eNotify(new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.ITERATOR_MAPPING_NODE_VALUE__NODE, oldNode, node));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case GipsIntermediatePackage.ITERATOR_MAPPING_NODE_VALUE__STREAM:
			if (resolve)
				return getStream();
			return basicGetStream();
		case GipsIntermediatePackage.ITERATOR_MAPPING_NODE_VALUE__MAPPING_CONTEXT:
			if (resolve)
				return getMappingContext();
			return basicGetMappingContext();
		case GipsIntermediatePackage.ITERATOR_MAPPING_NODE_VALUE__NODE:
			if (resolve)
				return getNode();
			return basicGetNode();
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
		case GipsIntermediatePackage.ITERATOR_MAPPING_NODE_VALUE__STREAM:
			setStream((SetOperation) newValue);
			return;
		case GipsIntermediatePackage.ITERATOR_MAPPING_NODE_VALUE__MAPPING_CONTEXT:
			setMappingContext((Mapping) newValue);
			return;
		case GipsIntermediatePackage.ITERATOR_MAPPING_NODE_VALUE__NODE:
			setNode((IBeXNode) newValue);
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
		case GipsIntermediatePackage.ITERATOR_MAPPING_NODE_VALUE__STREAM:
			setStream((SetOperation) null);
			return;
		case GipsIntermediatePackage.ITERATOR_MAPPING_NODE_VALUE__MAPPING_CONTEXT:
			setMappingContext((Mapping) null);
			return;
		case GipsIntermediatePackage.ITERATOR_MAPPING_NODE_VALUE__NODE:
			setNode((IBeXNode) null);
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
		case GipsIntermediatePackage.ITERATOR_MAPPING_NODE_VALUE__STREAM:
			return stream != null;
		case GipsIntermediatePackage.ITERATOR_MAPPING_NODE_VALUE__MAPPING_CONTEXT:
			return mappingContext != null;
		case GipsIntermediatePackage.ITERATOR_MAPPING_NODE_VALUE__NODE:
			return node != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == Iterator.class) {
			switch (derivedFeatureID) {
			case GipsIntermediatePackage.ITERATOR_MAPPING_NODE_VALUE__STREAM:
				return GipsIntermediatePackage.ITERATOR__STREAM;
			default:
				return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == Iterator.class) {
			switch (baseFeatureID) {
			case GipsIntermediatePackage.ITERATOR__STREAM:
				return GipsIntermediatePackage.ITERATOR_MAPPING_NODE_VALUE__STREAM;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //IteratorMappingNodeValueImpl
