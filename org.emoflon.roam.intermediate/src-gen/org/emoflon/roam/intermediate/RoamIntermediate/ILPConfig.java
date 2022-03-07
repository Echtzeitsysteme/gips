/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ILP Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.ILPConfig#getSolver <em>Solver</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.ILPConfig#getSolverHomeDir <em>Solver Home Dir</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.ILPConfig#getSolverLicenseFile <em>Solver License File</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.ILPConfig#getIlpTimeLimit <em>Ilp Time Limit</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.ILPConfig#isEnableRndSeed <em>Enable Rnd Seed</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.ILPConfig#getIlpRndSeed <em>Ilp Rnd Seed</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.ILPConfig#isPresolve <em>Presolve</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.ILPConfig#isEnablePresolve <em>Enable Presolve</em>}</li>
 *   <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.ILPConfig#isEnableDebugOutput <em>Enable Debug Output</em>}</li>
 * </ul>
 *
 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getILPConfig()
 * @model
 * @generated
 */
public interface ILPConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Solver</b></em>' attribute.
	 * The literals are from the enumeration {@link org.emoflon.roam.intermediate.RoamIntermediate.ILPSolverType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Solver</em>' attribute.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ILPSolverType
	 * @see #setSolver(ILPSolverType)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getILPConfig_Solver()
	 * @model
	 * @generated
	 */
	ILPSolverType getSolver();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.ILPConfig#getSolver <em>Solver</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Solver</em>' attribute.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ILPSolverType
	 * @see #getSolver()
	 * @generated
	 */
	void setSolver(ILPSolverType value);

	/**
	 * Returns the value of the '<em><b>Solver Home Dir</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Solver Home Dir</em>' attribute.
	 * @see #setSolverHomeDir(String)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getILPConfig_SolverHomeDir()
	 * @model
	 * @generated
	 */
	String getSolverHomeDir();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.ILPConfig#getSolverHomeDir <em>Solver Home Dir</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Solver Home Dir</em>' attribute.
	 * @see #getSolverHomeDir()
	 * @generated
	 */
	void setSolverHomeDir(String value);

	/**
	 * Returns the value of the '<em><b>Solver License File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Solver License File</em>' attribute.
	 * @see #setSolverLicenseFile(String)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getILPConfig_SolverLicenseFile()
	 * @model
	 * @generated
	 */
	String getSolverLicenseFile();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.ILPConfig#getSolverLicenseFile <em>Solver License File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Solver License File</em>' attribute.
	 * @see #getSolverLicenseFile()
	 * @generated
	 */
	void setSolverLicenseFile(String value);

	/**
	 * Returns the value of the '<em><b>Ilp Time Limit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ilp Time Limit</em>' attribute.
	 * @see #setIlpTimeLimit(double)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getILPConfig_IlpTimeLimit()
	 * @model
	 * @generated
	 */
	double getIlpTimeLimit();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.ILPConfig#getIlpTimeLimit <em>Ilp Time Limit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ilp Time Limit</em>' attribute.
	 * @see #getIlpTimeLimit()
	 * @generated
	 */
	void setIlpTimeLimit(double value);

	/**
	 * Returns the value of the '<em><b>Enable Rnd Seed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enable Rnd Seed</em>' attribute.
	 * @see #setEnableRndSeed(boolean)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getILPConfig_EnableRndSeed()
	 * @model
	 * @generated
	 */
	boolean isEnableRndSeed();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.ILPConfig#isEnableRndSeed <em>Enable Rnd Seed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enable Rnd Seed</em>' attribute.
	 * @see #isEnableRndSeed()
	 * @generated
	 */
	void setEnableRndSeed(boolean value);

	/**
	 * Returns the value of the '<em><b>Ilp Rnd Seed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ilp Rnd Seed</em>' attribute.
	 * @see #setIlpRndSeed(int)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getILPConfig_IlpRndSeed()
	 * @model
	 * @generated
	 */
	int getIlpRndSeed();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.ILPConfig#getIlpRndSeed <em>Ilp Rnd Seed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ilp Rnd Seed</em>' attribute.
	 * @see #getIlpRndSeed()
	 * @generated
	 */
	void setIlpRndSeed(int value);

	/**
	 * Returns the value of the '<em><b>Presolve</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Presolve</em>' attribute.
	 * @see #setPresolve(boolean)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getILPConfig_Presolve()
	 * @model
	 * @generated
	 */
	boolean isPresolve();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.ILPConfig#isPresolve <em>Presolve</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Presolve</em>' attribute.
	 * @see #isPresolve()
	 * @generated
	 */
	void setPresolve(boolean value);

	/**
	 * Returns the value of the '<em><b>Enable Presolve</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enable Presolve</em>' attribute.
	 * @see #setEnablePresolve(boolean)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getILPConfig_EnablePresolve()
	 * @model
	 * @generated
	 */
	boolean isEnablePresolve();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.ILPConfig#isEnablePresolve <em>Enable Presolve</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enable Presolve</em>' attribute.
	 * @see #isEnablePresolve()
	 * @generated
	 */
	void setEnablePresolve(boolean value);

	/**
	 * Returns the value of the '<em><b>Enable Debug Output</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enable Debug Output</em>' attribute.
	 * @see #setEnableDebugOutput(boolean)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getILPConfig_EnableDebugOutput()
	 * @model
	 * @generated
	 */
	boolean isEnableDebugOutput();

	/**
	 * Sets the value of the '{@link org.emoflon.roam.intermediate.RoamIntermediate.ILPConfig#isEnableDebugOutput <em>Enable Debug Output</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enable Debug Output</em>' attribute.
	 * @see #isEnableDebugOutput()
	 * @generated
	 */
	void setEnableDebugOutput(boolean value);

} // ILPConfig
