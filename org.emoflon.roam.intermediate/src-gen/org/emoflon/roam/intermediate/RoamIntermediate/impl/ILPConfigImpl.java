/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.emoflon.roam.intermediate.RoamIntermediate.ILPConfig;
import org.emoflon.roam.intermediate.RoamIntermediate.ILPSolverType;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ILP Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ILPConfigImpl#getSolver <em>Solver</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ILPConfigImpl#getSolverHomeDir <em>Solver Home Dir</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ILPConfigImpl#getSolverLicenseFile <em>Solver License File</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ILPConfigImpl#getIlpTimeLimit <em>Ilp Time Limit</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ILPConfigImpl#isEnableRndSeed <em>Enable Rnd Seed</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ILPConfigImpl#getIlpRndSeed <em>Ilp Rnd Seed</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ILPConfigImpl#isPresolve <em>Presolve</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ILPConfigImpl#isEnablePresolve <em>Enable Presolve</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ILPConfigImpl#isEnableDebugOutput <em>Enable Debug Output</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ILPConfigImpl extends MinimalEObjectImpl.Container implements ILPConfig {
	/**
	 * The default value of the '{@link #getSolver() <em>Solver</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSolver()
	 * @generated
	 * @ordered
	 */
	protected static final ILPSolverType SOLVER_EDEFAULT = ILPSolverType.GUROBI;

	/**
	 * The cached value of the '{@link #getSolver() <em>Solver</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSolver()
	 * @generated
	 * @ordered
	 */
	protected ILPSolverType solver = SOLVER_EDEFAULT;

	/**
	 * The default value of the '{@link #getSolverHomeDir() <em>Solver Home Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSolverHomeDir()
	 * @generated
	 * @ordered
	 */
	protected static final String SOLVER_HOME_DIR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSolverHomeDir() <em>Solver Home Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSolverHomeDir()
	 * @generated
	 * @ordered
	 */
	protected String solverHomeDir = SOLVER_HOME_DIR_EDEFAULT;

	/**
	 * The default value of the '{@link #getSolverLicenseFile() <em>Solver License File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSolverLicenseFile()
	 * @generated
	 * @ordered
	 */
	protected static final String SOLVER_LICENSE_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSolverLicenseFile() <em>Solver License File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSolverLicenseFile()
	 * @generated
	 * @ordered
	 */
	protected String solverLicenseFile = SOLVER_LICENSE_FILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getIlpTimeLimit() <em>Ilp Time Limit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIlpTimeLimit()
	 * @generated
	 * @ordered
	 */
	protected static final double ILP_TIME_LIMIT_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getIlpTimeLimit() <em>Ilp Time Limit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIlpTimeLimit()
	 * @generated
	 * @ordered
	 */
	protected double ilpTimeLimit = ILP_TIME_LIMIT_EDEFAULT;

	/**
	 * The default value of the '{@link #isEnableRndSeed() <em>Enable Rnd Seed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnableRndSeed()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ENABLE_RND_SEED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isEnableRndSeed() <em>Enable Rnd Seed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnableRndSeed()
	 * @generated
	 * @ordered
	 */
	protected boolean enableRndSeed = ENABLE_RND_SEED_EDEFAULT;

	/**
	 * The default value of the '{@link #getIlpRndSeed() <em>Ilp Rnd Seed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIlpRndSeed()
	 * @generated
	 * @ordered
	 */
	protected static final int ILP_RND_SEED_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getIlpRndSeed() <em>Ilp Rnd Seed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIlpRndSeed()
	 * @generated
	 * @ordered
	 */
	protected int ilpRndSeed = ILP_RND_SEED_EDEFAULT;

	/**
	 * The default value of the '{@link #isPresolve() <em>Presolve</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPresolve()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PRESOLVE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPresolve() <em>Presolve</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPresolve()
	 * @generated
	 * @ordered
	 */
	protected boolean presolve = PRESOLVE_EDEFAULT;

	/**
	 * The default value of the '{@link #isEnablePresolve() <em>Enable Presolve</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnablePresolve()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ENABLE_PRESOLVE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isEnablePresolve() <em>Enable Presolve</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnablePresolve()
	 * @generated
	 * @ordered
	 */
	protected boolean enablePresolve = ENABLE_PRESOLVE_EDEFAULT;

	/**
	 * The default value of the '{@link #isEnableDebugOutput() <em>Enable Debug Output</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnableDebugOutput()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ENABLE_DEBUG_OUTPUT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isEnableDebugOutput() <em>Enable Debug Output</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnableDebugOutput()
	 * @generated
	 * @ordered
	 */
	protected boolean enableDebugOutput = ENABLE_DEBUG_OUTPUT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ILPConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RoamIntermediatePackage.Literals.ILP_CONFIG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ILPSolverType getSolver() {
		return solver;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSolver(ILPSolverType newSolver) {
		ILPSolverType oldSolver = solver;
		solver = newSolver == null ? SOLVER_EDEFAULT : newSolver;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RoamIntermediatePackage.ILP_CONFIG__SOLVER, oldSolver,
					solver));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSolverHomeDir() {
		return solverHomeDir;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSolverHomeDir(String newSolverHomeDir) {
		String oldSolverHomeDir = solverHomeDir;
		solverHomeDir = newSolverHomeDir;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RoamIntermediatePackage.ILP_CONFIG__SOLVER_HOME_DIR,
					oldSolverHomeDir, solverHomeDir));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSolverLicenseFile() {
		return solverLicenseFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSolverLicenseFile(String newSolverLicenseFile) {
		String oldSolverLicenseFile = solverLicenseFile;
		solverLicenseFile = newSolverLicenseFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.ILP_CONFIG__SOLVER_LICENSE_FILE, oldSolverLicenseFile, solverLicenseFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getIlpTimeLimit() {
		return ilpTimeLimit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIlpTimeLimit(double newIlpTimeLimit) {
		double oldIlpTimeLimit = ilpTimeLimit;
		ilpTimeLimit = newIlpTimeLimit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RoamIntermediatePackage.ILP_CONFIG__ILP_TIME_LIMIT,
					oldIlpTimeLimit, ilpTimeLimit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isEnableRndSeed() {
		return enableRndSeed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnableRndSeed(boolean newEnableRndSeed) {
		boolean oldEnableRndSeed = enableRndSeed;
		enableRndSeed = newEnableRndSeed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RoamIntermediatePackage.ILP_CONFIG__ENABLE_RND_SEED,
					oldEnableRndSeed, enableRndSeed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getIlpRndSeed() {
		return ilpRndSeed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIlpRndSeed(int newIlpRndSeed) {
		int oldIlpRndSeed = ilpRndSeed;
		ilpRndSeed = newIlpRndSeed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RoamIntermediatePackage.ILP_CONFIG__ILP_RND_SEED,
					oldIlpRndSeed, ilpRndSeed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPresolve() {
		return presolve;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPresolve(boolean newPresolve) {
		boolean oldPresolve = presolve;
		presolve = newPresolve;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RoamIntermediatePackage.ILP_CONFIG__PRESOLVE,
					oldPresolve, presolve));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isEnablePresolve() {
		return enablePresolve;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnablePresolve(boolean newEnablePresolve) {
		boolean oldEnablePresolve = enablePresolve;
		enablePresolve = newEnablePresolve;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RoamIntermediatePackage.ILP_CONFIG__ENABLE_PRESOLVE,
					oldEnablePresolve, enablePresolve));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isEnableDebugOutput() {
		return enableDebugOutput;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnableDebugOutput(boolean newEnableDebugOutput) {
		boolean oldEnableDebugOutput = enableDebugOutput;
		enableDebugOutput = newEnableDebugOutput;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					RoamIntermediatePackage.ILP_CONFIG__ENABLE_DEBUG_OUTPUT, oldEnableDebugOutput, enableDebugOutput));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case RoamIntermediatePackage.ILP_CONFIG__SOLVER:
			return getSolver();
		case RoamIntermediatePackage.ILP_CONFIG__SOLVER_HOME_DIR:
			return getSolverHomeDir();
		case RoamIntermediatePackage.ILP_CONFIG__SOLVER_LICENSE_FILE:
			return getSolverLicenseFile();
		case RoamIntermediatePackage.ILP_CONFIG__ILP_TIME_LIMIT:
			return getIlpTimeLimit();
		case RoamIntermediatePackage.ILP_CONFIG__ENABLE_RND_SEED:
			return isEnableRndSeed();
		case RoamIntermediatePackage.ILP_CONFIG__ILP_RND_SEED:
			return getIlpRndSeed();
		case RoamIntermediatePackage.ILP_CONFIG__PRESOLVE:
			return isPresolve();
		case RoamIntermediatePackage.ILP_CONFIG__ENABLE_PRESOLVE:
			return isEnablePresolve();
		case RoamIntermediatePackage.ILP_CONFIG__ENABLE_DEBUG_OUTPUT:
			return isEnableDebugOutput();
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
		case RoamIntermediatePackage.ILP_CONFIG__SOLVER:
			setSolver((ILPSolverType) newValue);
			return;
		case RoamIntermediatePackage.ILP_CONFIG__SOLVER_HOME_DIR:
			setSolverHomeDir((String) newValue);
			return;
		case RoamIntermediatePackage.ILP_CONFIG__SOLVER_LICENSE_FILE:
			setSolverLicenseFile((String) newValue);
			return;
		case RoamIntermediatePackage.ILP_CONFIG__ILP_TIME_LIMIT:
			setIlpTimeLimit((Double) newValue);
			return;
		case RoamIntermediatePackage.ILP_CONFIG__ENABLE_RND_SEED:
			setEnableRndSeed((Boolean) newValue);
			return;
		case RoamIntermediatePackage.ILP_CONFIG__ILP_RND_SEED:
			setIlpRndSeed((Integer) newValue);
			return;
		case RoamIntermediatePackage.ILP_CONFIG__PRESOLVE:
			setPresolve((Boolean) newValue);
			return;
		case RoamIntermediatePackage.ILP_CONFIG__ENABLE_PRESOLVE:
			setEnablePresolve((Boolean) newValue);
			return;
		case RoamIntermediatePackage.ILP_CONFIG__ENABLE_DEBUG_OUTPUT:
			setEnableDebugOutput((Boolean) newValue);
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
		case RoamIntermediatePackage.ILP_CONFIG__SOLVER:
			setSolver(SOLVER_EDEFAULT);
			return;
		case RoamIntermediatePackage.ILP_CONFIG__SOLVER_HOME_DIR:
			setSolverHomeDir(SOLVER_HOME_DIR_EDEFAULT);
			return;
		case RoamIntermediatePackage.ILP_CONFIG__SOLVER_LICENSE_FILE:
			setSolverLicenseFile(SOLVER_LICENSE_FILE_EDEFAULT);
			return;
		case RoamIntermediatePackage.ILP_CONFIG__ILP_TIME_LIMIT:
			setIlpTimeLimit(ILP_TIME_LIMIT_EDEFAULT);
			return;
		case RoamIntermediatePackage.ILP_CONFIG__ENABLE_RND_SEED:
			setEnableRndSeed(ENABLE_RND_SEED_EDEFAULT);
			return;
		case RoamIntermediatePackage.ILP_CONFIG__ILP_RND_SEED:
			setIlpRndSeed(ILP_RND_SEED_EDEFAULT);
			return;
		case RoamIntermediatePackage.ILP_CONFIG__PRESOLVE:
			setPresolve(PRESOLVE_EDEFAULT);
			return;
		case RoamIntermediatePackage.ILP_CONFIG__ENABLE_PRESOLVE:
			setEnablePresolve(ENABLE_PRESOLVE_EDEFAULT);
			return;
		case RoamIntermediatePackage.ILP_CONFIG__ENABLE_DEBUG_OUTPUT:
			setEnableDebugOutput(ENABLE_DEBUG_OUTPUT_EDEFAULT);
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
		case RoamIntermediatePackage.ILP_CONFIG__SOLVER:
			return solver != SOLVER_EDEFAULT;
		case RoamIntermediatePackage.ILP_CONFIG__SOLVER_HOME_DIR:
			return SOLVER_HOME_DIR_EDEFAULT == null ? solverHomeDir != null
					: !SOLVER_HOME_DIR_EDEFAULT.equals(solverHomeDir);
		case RoamIntermediatePackage.ILP_CONFIG__SOLVER_LICENSE_FILE:
			return SOLVER_LICENSE_FILE_EDEFAULT == null ? solverLicenseFile != null
					: !SOLVER_LICENSE_FILE_EDEFAULT.equals(solverLicenseFile);
		case RoamIntermediatePackage.ILP_CONFIG__ILP_TIME_LIMIT:
			return ilpTimeLimit != ILP_TIME_LIMIT_EDEFAULT;
		case RoamIntermediatePackage.ILP_CONFIG__ENABLE_RND_SEED:
			return enableRndSeed != ENABLE_RND_SEED_EDEFAULT;
		case RoamIntermediatePackage.ILP_CONFIG__ILP_RND_SEED:
			return ilpRndSeed != ILP_RND_SEED_EDEFAULT;
		case RoamIntermediatePackage.ILP_CONFIG__PRESOLVE:
			return presolve != PRESOLVE_EDEFAULT;
		case RoamIntermediatePackage.ILP_CONFIG__ENABLE_PRESOLVE:
			return enablePresolve != ENABLE_PRESOLVE_EDEFAULT;
		case RoamIntermediatePackage.ILP_CONFIG__ENABLE_DEBUG_OUTPUT:
			return enableDebugOutput != ENABLE_DEBUG_OUTPUT_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (solver: ");
		result.append(solver);
		result.append(", solverHomeDir: ");
		result.append(solverHomeDir);
		result.append(", solverLicenseFile: ");
		result.append(solverLicenseFile);
		result.append(", ilpTimeLimit: ");
		result.append(ilpTimeLimit);
		result.append(", enableRndSeed: ");
		result.append(enableRndSeed);
		result.append(", ilpRndSeed: ");
		result.append(ilpRndSeed);
		result.append(", presolve: ");
		result.append(presolve);
		result.append(", enablePresolve: ");
		result.append(enablePresolve);
		result.append(", enableDebugOutput: ");
		result.append(enableDebugOutput);
		result.append(')');
		return result.toString();
	}

} //ILPConfigImpl
