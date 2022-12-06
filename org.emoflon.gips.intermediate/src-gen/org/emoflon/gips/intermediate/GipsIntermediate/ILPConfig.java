/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>ILP
 * Config</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getSolver
 * <em>Solver</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getSolverHomeDir
 * <em>Solver Home Dir</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getSolverLicenseFile
 * <em>Solver License File</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isBuildLaunchConfig
 * <em>Build Launch Config</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getMainFile
 * <em>Main File</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isEnableTimeLimit
 * <em>Enable Time Limit</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getIlpTimeLimit
 * <em>Ilp Time Limit</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isEnableRndSeed
 * <em>Enable Rnd Seed</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getIlpRndSeed
 * <em>Ilp Rnd Seed</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isPresolve
 * <em>Presolve</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isEnablePresolve
 * <em>Enable Presolve</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isEnableDebugOutput
 * <em>Enable Debug Output</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isEnableCustomTolerance
 * <em>Enable Custom Tolerance</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getTolerance
 * <em>Tolerance</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isEnableLpOutput
 * <em>Enable Lp Output</em>}</li>
 * <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getLpPath
 * <em>Lp Path</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getILPConfig()
 * @model
 * @generated
 */
public interface ILPConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Solver</b></em>' attribute. The literals are
	 * from the enumeration
	 * {@link org.emoflon.gips.intermediate.GipsIntermediate.ILPSolverType}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Solver</em>' attribute.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ILPSolverType
	 * @see #setSolver(ILPSolverType)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getILPConfig_Solver()
	 * @model
	 * @generated
	 */
	ILPSolverType getSolver();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getSolver
	 * <em>Solver</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Solver</em>' attribute.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ILPSolverType
	 * @see #getSolver()
	 * @generated
	 */
	void setSolver(ILPSolverType value);

	/**
	 * Returns the value of the '<em><b>Solver Home Dir</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Solver Home Dir</em>' attribute.
	 * @see #setSolverHomeDir(String)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getILPConfig_SolverHomeDir()
	 * @model
	 * @generated
	 */
	String getSolverHomeDir();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getSolverHomeDir
	 * <em>Solver Home Dir</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Solver Home Dir</em>' attribute.
	 * @see #getSolverHomeDir()
	 * @generated
	 */
	void setSolverHomeDir(String value);

	/**
	 * Returns the value of the '<em><b>Solver License File</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Solver License File</em>' attribute.
	 * @see #setSolverLicenseFile(String)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getILPConfig_SolverLicenseFile()
	 * @model
	 * @generated
	 */
	String getSolverLicenseFile();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getSolverLicenseFile
	 * <em>Solver License File</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Solver License File</em>' attribute.
	 * @see #getSolverLicenseFile()
	 * @generated
	 */
	void setSolverLicenseFile(String value);

	/**
	 * Returns the value of the '<em><b>Build Launch Config</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Build Launch Config</em>' attribute.
	 * @see #setBuildLaunchConfig(boolean)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getILPConfig_BuildLaunchConfig()
	 * @model
	 * @generated
	 */
	boolean isBuildLaunchConfig();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isBuildLaunchConfig
	 * <em>Build Launch Config</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Build Launch Config</em>' attribute.
	 * @see #isBuildLaunchConfig()
	 * @generated
	 */
	void setBuildLaunchConfig(boolean value);

	/**
	 * Returns the value of the '<em><b>Main File</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Main File</em>' attribute.
	 * @see #setMainFile(String)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getILPConfig_MainFile()
	 * @model
	 * @generated
	 */
	String getMainFile();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getMainFile
	 * <em>Main File</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Main File</em>' attribute.
	 * @see #getMainFile()
	 * @generated
	 */
	void setMainFile(String value);

	/**
	 * Returns the value of the '<em><b>Enable Time Limit</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Enable Time Limit</em>' attribute.
	 * @see #setEnableTimeLimit(boolean)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getILPConfig_EnableTimeLimit()
	 * @model
	 * @generated
	 */
	boolean isEnableTimeLimit();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isEnableTimeLimit
	 * <em>Enable Time Limit</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Enable Time Limit</em>' attribute.
	 * @see #isEnableTimeLimit()
	 * @generated
	 */
	void setEnableTimeLimit(boolean value);

	/**
	 * Returns the value of the '<em><b>Ilp Time Limit</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Ilp Time Limit</em>' attribute.
	 * @see #setIlpTimeLimit(double)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getILPConfig_IlpTimeLimit()
	 * @model
	 * @generated
	 */
	double getIlpTimeLimit();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getIlpTimeLimit
	 * <em>Ilp Time Limit</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Ilp Time Limit</em>' attribute.
	 * @see #getIlpTimeLimit()
	 * @generated
	 */
	void setIlpTimeLimit(double value);

	/**
	 * Returns the value of the '<em><b>Enable Rnd Seed</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Enable Rnd Seed</em>' attribute.
	 * @see #setEnableRndSeed(boolean)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getILPConfig_EnableRndSeed()
	 * @model
	 * @generated
	 */
	boolean isEnableRndSeed();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isEnableRndSeed
	 * <em>Enable Rnd Seed</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Enable Rnd Seed</em>' attribute.
	 * @see #isEnableRndSeed()
	 * @generated
	 */
	void setEnableRndSeed(boolean value);

	/**
	 * Returns the value of the '<em><b>Ilp Rnd Seed</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Ilp Rnd Seed</em>' attribute.
	 * @see #setIlpRndSeed(int)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getILPConfig_IlpRndSeed()
	 * @model
	 * @generated
	 */
	int getIlpRndSeed();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getIlpRndSeed
	 * <em>Ilp Rnd Seed</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Ilp Rnd Seed</em>' attribute.
	 * @see #getIlpRndSeed()
	 * @generated
	 */
	void setIlpRndSeed(int value);

	/**
	 * Returns the value of the '<em><b>Presolve</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Presolve</em>' attribute.
	 * @see #setPresolve(boolean)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getILPConfig_Presolve()
	 * @model
	 * @generated
	 */
	boolean isPresolve();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isPresolve
	 * <em>Presolve</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Presolve</em>' attribute.
	 * @see #isPresolve()
	 * @generated
	 */
	void setPresolve(boolean value);

	/**
	 * Returns the value of the '<em><b>Enable Presolve</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Enable Presolve</em>' attribute.
	 * @see #setEnablePresolve(boolean)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getILPConfig_EnablePresolve()
	 * @model
	 * @generated
	 */
	boolean isEnablePresolve();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isEnablePresolve
	 * <em>Enable Presolve</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Enable Presolve</em>' attribute.
	 * @see #isEnablePresolve()
	 * @generated
	 */
	void setEnablePresolve(boolean value);

	/**
	 * Returns the value of the '<em><b>Enable Debug Output</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Enable Debug Output</em>' attribute.
	 * @see #setEnableDebugOutput(boolean)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getILPConfig_EnableDebugOutput()
	 * @model
	 * @generated
	 */
	boolean isEnableDebugOutput();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isEnableDebugOutput
	 * <em>Enable Debug Output</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Enable Debug Output</em>' attribute.
	 * @see #isEnableDebugOutput()
	 * @generated
	 */
	void setEnableDebugOutput(boolean value);

	/**
	 * Returns the value of the '<em><b>Enable Custom Tolerance</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Enable Custom Tolerance</em>' attribute.
	 * @see #setEnableCustomTolerance(boolean)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getILPConfig_EnableCustomTolerance()
	 * @model
	 * @generated
	 */
	boolean isEnableCustomTolerance();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isEnableCustomTolerance
	 * <em>Enable Custom Tolerance</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Enable Custom Tolerance</em>'
	 *              attribute.
	 * @see #isEnableCustomTolerance()
	 * @generated
	 */
	void setEnableCustomTolerance(boolean value);

	/**
	 * Returns the value of the '<em><b>Tolerance</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Tolerance</em>' attribute.
	 * @see #setTolerance(double)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getILPConfig_Tolerance()
	 * @model
	 * @generated
	 */
	double getTolerance();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getTolerance
	 * <em>Tolerance</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Tolerance</em>' attribute.
	 * @see #getTolerance()
	 * @generated
	 */
	void setTolerance(double value);

	/**
	 * Returns the value of the '<em><b>Enable Lp Output</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Enable Lp Output</em>' attribute.
	 * @see #setEnableLpOutput(boolean)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getILPConfig_EnableLpOutput()
	 * @model
	 * @generated
	 */
	boolean isEnableLpOutput();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isEnableLpOutput
	 * <em>Enable Lp Output</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Enable Lp Output</em>' attribute.
	 * @see #isEnableLpOutput()
	 * @generated
	 */
	void setEnableLpOutput(boolean value);

	/**
	 * Returns the value of the '<em><b>Lp Path</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Lp Path</em>' attribute.
	 * @see #setLpPath(String)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getILPConfig_LpPath()
	 * @model
	 * @generated
	 */
	String getLpPath();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getLpPath
	 * <em>Lp Path</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Lp Path</em>' attribute.
	 * @see #getLpPath()
	 * @generated
	 */
	void setLpPath(String value);

} // ILPConfig
