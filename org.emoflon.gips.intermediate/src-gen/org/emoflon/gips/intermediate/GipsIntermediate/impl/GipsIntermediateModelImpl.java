/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.emoflon.gips.intermediate.GipsIntermediate.Constraint;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.GlobalObjective;
import org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig;
import org.emoflon.gips.intermediate.GipsIntermediate.Objective;
import org.emoflon.gips.intermediate.GipsIntermediate.VariableSet;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Model</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediateModelImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediateModelImpl#getVariables <em>Variables</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediateModelImpl#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediateModelImpl#getObjectives <em>Objectives</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediateModelImpl#getGlobalObjective <em>Global Objective</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediateModelImpl#getIbexModel <em>Ibex Model</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediateModelImpl#getConfig <em>Config</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GipsIntermediateModelImpl extends MinimalEObjectImpl.Container implements GipsIntermediateModel {
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
	 * The cached value of the '{@link #getVariables() <em>Variables</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<VariableSet> variables;

	/**
	 * The cached value of the '{@link #getConstraints() <em>Constraints</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<Constraint> constraints;

	/**
	 * The cached value of the '{@link #getObjectives() <em>Objectives</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getObjectives()
	 * @generated
	 * @ordered
	 */
	protected EList<Objective> objectives;

	/**
	 * The cached value of the '{@link #getGlobalObjective() <em>Global Objective</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getGlobalObjective()
	 * @generated
	 * @ordered
	 */
	protected GlobalObjective globalObjective;

	/**
	 * The cached value of the '{@link #getIbexModel() <em>Ibex Model</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getIbexModel()
	 * @generated
	 * @ordered
	 */
	protected IBeXModel ibexModel;

	/**
	 * The cached value of the '{@link #getConfig() <em>Config</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getConfig()
	 * @generated
	 * @ordered
	 */
	protected ILPConfig config;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected GipsIntermediateModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GipsIntermediatePackage.Literals.GIPS_INTERMEDIATE_MODEL;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__NAME,
					oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<VariableSet> getVariables() {
		if (variables == null) {
			variables = new EObjectContainmentEList<VariableSet>(VariableSet.class, this,
					GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__VARIABLES);
		}
		return variables;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Constraint> getConstraints() {
		if (constraints == null) {
			constraints = new EObjectContainmentEList<Constraint>(Constraint.class, this,
					GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__CONSTRAINTS);
		}
		return constraints;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Objective> getObjectives() {
		if (objectives == null) {
			objectives = new EObjectContainmentEList<Objective>(Objective.class, this,
					GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__OBJECTIVES);
		}
		return objectives;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public GlobalObjective getGlobalObjective() {
		return globalObjective;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGlobalObjective(GlobalObjective newGlobalObjective, NotificationChain msgs) {
		GlobalObjective oldGlobalObjective = globalObjective;
		globalObjective = newGlobalObjective;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__GLOBAL_OBJECTIVE, oldGlobalObjective,
					newGlobalObjective);
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
	public void setGlobalObjective(GlobalObjective newGlobalObjective) {
		if (newGlobalObjective != globalObjective) {
			NotificationChain msgs = null;
			if (globalObjective != null)
				msgs = ((InternalEObject) globalObjective).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__GLOBAL_OBJECTIVE,
						null, msgs);
			if (newGlobalObjective != null)
				msgs = ((InternalEObject) newGlobalObjective).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__GLOBAL_OBJECTIVE,
						null, msgs);
			msgs = basicSetGlobalObjective(newGlobalObjective, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__GLOBAL_OBJECTIVE, newGlobalObjective,
					newGlobalObjective));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXModel getIbexModel() {
		return ibexModel;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIbexModel(IBeXModel newIbexModel, NotificationChain msgs) {
		IBeXModel oldIbexModel = ibexModel;
		ibexModel = newIbexModel;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__IBEX_MODEL, oldIbexModel, newIbexModel);
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
	public void setIbexModel(IBeXModel newIbexModel) {
		if (newIbexModel != ibexModel) {
			NotificationChain msgs = null;
			if (ibexModel != null)
				msgs = ((InternalEObject) ibexModel).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__IBEX_MODEL, null,
						msgs);
			if (newIbexModel != null)
				msgs = ((InternalEObject) newIbexModel).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__IBEX_MODEL, null,
						msgs);
			msgs = basicSetIbexModel(newIbexModel, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__IBEX_MODEL, newIbexModel, newIbexModel));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ILPConfig getConfig() {
		return config;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConfig(ILPConfig newConfig, NotificationChain msgs) {
		ILPConfig oldConfig = config;
		config = newConfig;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__CONFIG, oldConfig, newConfig);
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
	public void setConfig(ILPConfig newConfig) {
		if (newConfig != config) {
			NotificationChain msgs = null;
			if (config != null)
				msgs = ((InternalEObject) config).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__CONFIG, null, msgs);
			if (newConfig != null)
				msgs = ((InternalEObject) newConfig).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__CONFIG, null, msgs);
			msgs = basicSetConfig(newConfig, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__CONFIG, newConfig, newConfig));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__VARIABLES:
			return ((InternalEList<?>) getVariables()).basicRemove(otherEnd, msgs);
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__CONSTRAINTS:
			return ((InternalEList<?>) getConstraints()).basicRemove(otherEnd, msgs);
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__OBJECTIVES:
			return ((InternalEList<?>) getObjectives()).basicRemove(otherEnd, msgs);
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__GLOBAL_OBJECTIVE:
			return basicSetGlobalObjective(null, msgs);
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__IBEX_MODEL:
			return basicSetIbexModel(null, msgs);
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__CONFIG:
			return basicSetConfig(null, msgs);
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
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__NAME:
			return getName();
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__VARIABLES:
			return getVariables();
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__CONSTRAINTS:
			return getConstraints();
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__OBJECTIVES:
			return getObjectives();
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__GLOBAL_OBJECTIVE:
			return getGlobalObjective();
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__IBEX_MODEL:
			return getIbexModel();
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__CONFIG:
			return getConfig();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__NAME:
			setName((String) newValue);
			return;
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__VARIABLES:
			getVariables().clear();
			getVariables().addAll((Collection<? extends VariableSet>) newValue);
			return;
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__CONSTRAINTS:
			getConstraints().clear();
			getConstraints().addAll((Collection<? extends Constraint>) newValue);
			return;
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__OBJECTIVES:
			getObjectives().clear();
			getObjectives().addAll((Collection<? extends Objective>) newValue);
			return;
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__GLOBAL_OBJECTIVE:
			setGlobalObjective((GlobalObjective) newValue);
			return;
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__IBEX_MODEL:
			setIbexModel((IBeXModel) newValue);
			return;
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__CONFIG:
			setConfig((ILPConfig) newValue);
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
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__NAME:
			setName(NAME_EDEFAULT);
			return;
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__VARIABLES:
			getVariables().clear();
			return;
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__CONSTRAINTS:
			getConstraints().clear();
			return;
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__OBJECTIVES:
			getObjectives().clear();
			return;
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__GLOBAL_OBJECTIVE:
			setGlobalObjective((GlobalObjective) null);
			return;
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__IBEX_MODEL:
			setIbexModel((IBeXModel) null);
			return;
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__CONFIG:
			setConfig((ILPConfig) null);
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
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__VARIABLES:
			return variables != null && !variables.isEmpty();
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__CONSTRAINTS:
			return constraints != null && !constraints.isEmpty();
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__OBJECTIVES:
			return objectives != null && !objectives.isEmpty();
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__GLOBAL_OBJECTIVE:
			return globalObjective != null;
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__IBEX_MODEL:
			return ibexModel != null;
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL__CONFIG:
			return config != null;
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} // GipsIntermediateModelImpl
