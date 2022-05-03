/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.Pattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Pattern</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternImpl#getPattern <em>Pattern</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternImpl#isIsRule <em>Is Rule</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PatternImpl extends VariableSetImpl implements Pattern {
	/**
	 * The cached value of the '{@link #getPattern() <em>Pattern</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPattern()
	 * @generated
	 * @ordered
	 */
	protected IBeXContext pattern;

	/**
	 * The default value of the '{@link #isIsRule() <em>Is Rule</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isIsRule()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_RULE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsRule() <em>Is Rule</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isIsRule()
	 * @generated
	 * @ordered
	 */
	protected boolean isRule = IS_RULE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected PatternImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GipsIntermediatePackage.Literals.PATTERN;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXContext getPattern() {
		if (pattern != null && pattern.eIsProxy()) {
			InternalEObject oldPattern = (InternalEObject) pattern;
			pattern = (IBeXContext) eResolveProxy(oldPattern);
			if (pattern != oldPattern) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GipsIntermediatePackage.PATTERN__PATTERN,
							oldPattern, pattern));
			}
		}
		return pattern;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXContext basicGetPattern() {
		return pattern;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setPattern(IBeXContext newPattern) {
		IBeXContext oldPattern = pattern;
		pattern = newPattern;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.PATTERN__PATTERN, oldPattern,
					pattern));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsRule() {
		return isRule;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsRule(boolean newIsRule) {
		boolean oldIsRule = isRule;
		isRule = newIsRule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.PATTERN__IS_RULE, oldIsRule,
					isRule));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case GipsIntermediatePackage.PATTERN__PATTERN:
			if (resolve)
				return getPattern();
			return basicGetPattern();
		case GipsIntermediatePackage.PATTERN__IS_RULE:
			return isIsRule();
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
		case GipsIntermediatePackage.PATTERN__PATTERN:
			setPattern((IBeXContext) newValue);
			return;
		case GipsIntermediatePackage.PATTERN__IS_RULE:
			setIsRule((Boolean) newValue);
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
		case GipsIntermediatePackage.PATTERN__PATTERN:
			setPattern((IBeXContext) null);
			return;
		case GipsIntermediatePackage.PATTERN__IS_RULE:
			setIsRule(IS_RULE_EDEFAULT);
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
		case GipsIntermediatePackage.PATTERN__PATTERN:
			return pattern != null;
		case GipsIntermediatePackage.PATTERN__IS_RULE:
			return isRule != IS_RULE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (isRule: ");
		result.append(isRule);
		result.append(')');
		return result.toString();
	}

} // PatternImpl
