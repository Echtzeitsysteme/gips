/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingObjective;
import org.emoflon.gips.intermediate.GipsIntermediate.Objective;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Mapping
 * Objective</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.MappingObjectiveImpl#getName
 * <em>Name</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.MappingObjectiveImpl#isElementwise
 * <em>Elementwise</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.MappingObjectiveImpl#getExpression
 * <em>Expression</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.MappingObjectiveImpl#getMapping
 * <em>Mapping</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MappingObjectiveImpl extends MinimalEObjectImpl.Container implements MappingObjective {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isElementwise() <em>Elementwise</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isElementwise()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ELEMENTWISE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isElementwise() <em>Elementwise</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isElementwise()
	 * @generated
	 * @ordered
	 */
	protected boolean elementwise = ELEMENTWISE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getExpression() <em>Expression</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
	protected ArithmeticExpression expression;

	/**
	 * The cached value of the '{@link #getMapping() <em>Mapping</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMapping()
	 * @generated
	 * @ordered
	 */
	protected Mapping mapping;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected MappingObjectiveImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GipsIntermediatePackage.Literals.MAPPING_OBJECTIVE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.MAPPING_OBJECTIVE__NAME,
					oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isElementwise() {
		return elementwise;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setElementwise(boolean newElementwise) {
		boolean oldElementwise = elementwise;
		elementwise = newElementwise;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.MAPPING_OBJECTIVE__ELEMENTWISE, oldElementwise, elementwise));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ArithmeticExpression getExpression() {
		return expression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetExpression(ArithmeticExpression newExpression, NotificationChain msgs) {
		ArithmeticExpression oldExpression = expression;
		expression = newExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.MAPPING_OBJECTIVE__EXPRESSION, oldExpression, newExpression);
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
	public void setExpression(ArithmeticExpression newExpression) {
		if (newExpression != expression) {
			NotificationChain msgs = null;
			if (expression != null)
				msgs = ((InternalEObject) expression).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.MAPPING_OBJECTIVE__EXPRESSION, null, msgs);
			if (newExpression != null)
				msgs = ((InternalEObject) newExpression).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.MAPPING_OBJECTIVE__EXPRESSION, null, msgs);
			msgs = basicSetExpression(newExpression, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.MAPPING_OBJECTIVE__EXPRESSION,
					newExpression, newExpression));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Mapping getMapping() {
		if (mapping != null && mapping.eIsProxy()) {
			InternalEObject oldMapping = (InternalEObject) mapping;
			mapping = (Mapping) eResolveProxy(oldMapping);
			if (mapping != oldMapping) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							GipsIntermediatePackage.MAPPING_OBJECTIVE__MAPPING, oldMapping, mapping));
			}
		}
		return mapping;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Mapping basicGetMapping() {
		return mapping;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setMapping(Mapping newMapping) {
		Mapping oldMapping = mapping;
		mapping = newMapping;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.MAPPING_OBJECTIVE__MAPPING,
					oldMapping, mapping));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GipsIntermediatePackage.MAPPING_OBJECTIVE__EXPRESSION:
			return basicSetExpression(null, msgs);
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
		case GipsIntermediatePackage.MAPPING_OBJECTIVE__NAME:
			return getName();
		case GipsIntermediatePackage.MAPPING_OBJECTIVE__ELEMENTWISE:
			return isElementwise();
		case GipsIntermediatePackage.MAPPING_OBJECTIVE__EXPRESSION:
			return getExpression();
		case GipsIntermediatePackage.MAPPING_OBJECTIVE__MAPPING:
			if (resolve)
				return getMapping();
			return basicGetMapping();
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
		case GipsIntermediatePackage.MAPPING_OBJECTIVE__NAME:
			setName((String) newValue);
			return;
		case GipsIntermediatePackage.MAPPING_OBJECTIVE__ELEMENTWISE:
			setElementwise((Boolean) newValue);
			return;
		case GipsIntermediatePackage.MAPPING_OBJECTIVE__EXPRESSION:
			setExpression((ArithmeticExpression) newValue);
			return;
		case GipsIntermediatePackage.MAPPING_OBJECTIVE__MAPPING:
			setMapping((Mapping) newValue);
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
		case GipsIntermediatePackage.MAPPING_OBJECTIVE__NAME:
			setName(NAME_EDEFAULT);
			return;
		case GipsIntermediatePackage.MAPPING_OBJECTIVE__ELEMENTWISE:
			setElementwise(ELEMENTWISE_EDEFAULT);
			return;
		case GipsIntermediatePackage.MAPPING_OBJECTIVE__EXPRESSION:
			setExpression((ArithmeticExpression) null);
			return;
		case GipsIntermediatePackage.MAPPING_OBJECTIVE__MAPPING:
			setMapping((Mapping) null);
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
		case GipsIntermediatePackage.MAPPING_OBJECTIVE__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case GipsIntermediatePackage.MAPPING_OBJECTIVE__ELEMENTWISE:
			return elementwise != ELEMENTWISE_EDEFAULT;
		case GipsIntermediatePackage.MAPPING_OBJECTIVE__EXPRESSION:
			return expression != null;
		case GipsIntermediatePackage.MAPPING_OBJECTIVE__MAPPING:
			return mapping != null;
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
		if (baseClass == Objective.class) {
			switch (derivedFeatureID) {
			case GipsIntermediatePackage.MAPPING_OBJECTIVE__NAME:
				return GipsIntermediatePackage.OBJECTIVE__NAME;
			case GipsIntermediatePackage.MAPPING_OBJECTIVE__ELEMENTWISE:
				return GipsIntermediatePackage.OBJECTIVE__ELEMENTWISE;
			case GipsIntermediatePackage.MAPPING_OBJECTIVE__EXPRESSION:
				return GipsIntermediatePackage.OBJECTIVE__EXPRESSION;
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
		if (baseClass == Objective.class) {
			switch (baseFeatureID) {
			case GipsIntermediatePackage.OBJECTIVE__NAME:
				return GipsIntermediatePackage.MAPPING_OBJECTIVE__NAME;
			case GipsIntermediatePackage.OBJECTIVE__ELEMENTWISE:
				return GipsIntermediatePackage.MAPPING_OBJECTIVE__ELEMENTWISE;
			case GipsIntermediatePackage.OBJECTIVE__EXPRESSION:
				return GipsIntermediatePackage.MAPPING_OBJECTIVE__EXPRESSION;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", elementwise: ");
		result.append(elementwise);
		result.append(')');
		return result.toString();
	}

} // MappingObjectiveImpl
