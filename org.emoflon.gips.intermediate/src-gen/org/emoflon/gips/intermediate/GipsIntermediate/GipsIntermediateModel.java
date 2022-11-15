/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel#getName <em>Name</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel#getVariables <em>Variables</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel#getObjectives <em>Objectives</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel#getGlobalObjective <em>Global Objective</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel#getIbexModel <em>Ibex Model</em>}</li>
 *   <li>{@link org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel#getConfig <em>Config</em>}</li>
 * </ul>
 *
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getGipsIntermediateModel()
 * @model
 * @generated
 */
public interface GipsIntermediateModel extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getGipsIntermediateModel_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Variables</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.gips.intermediate.GipsIntermediate.VariableSet}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variables</em>' containment reference list.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getGipsIntermediateModel_Variables()
	 * @model containment="true"
	 * @generated
	 */
	EList<VariableSet> getVariables();

	/**
	 * Returns the value of the '<em><b>Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraints</em>' containment reference list.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getGipsIntermediateModel_Constraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<Constraint> getConstraints();

	/**
	 * Returns the value of the '<em><b>Objectives</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.gips.intermediate.GipsIntermediate.Objective}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Objectives</em>' containment reference list.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getGipsIntermediateModel_Objectives()
	 * @model containment="true"
	 * @generated
	 */
	EList<Objective> getObjectives();

	/**
	 * Returns the value of the '<em><b>Global Objective</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Global Objective</em>' containment reference.
	 * @see #setGlobalObjective(GlobalObjective)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getGipsIntermediateModel_GlobalObjective()
	 * @model containment="true"
	 * @generated
	 */
	GlobalObjective getGlobalObjective();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel#getGlobalObjective <em>Global Objective</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Global Objective</em>' containment reference.
	 * @see #getGlobalObjective()
	 * @generated
	 */
	void setGlobalObjective(GlobalObjective value);

	/**
	 * Returns the value of the '<em><b>Ibex Model</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ibex Model</em>' containment reference.
	 * @see #setIbexModel(GTModel)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getGipsIntermediateModel_IbexModel()
	 * @model containment="true" required="true"
	 * @generated
	 */
	GTModel getIbexModel();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel#getIbexModel <em>Ibex Model</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ibex Model</em>' containment reference.
	 * @see #getIbexModel()
	 * @generated
	 */
	void setIbexModel(GTModel value);

	/**
	 * Returns the value of the '<em><b>Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Config</em>' containment reference.
	 * @see #setConfig(ILPConfig)
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getGipsIntermediateModel_Config()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ILPConfig getConfig();

	/**
	 * Sets the value of the '{@link org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel#getConfig <em>Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Config</em>' containment reference.
	 * @see #getConfig()
	 * @generated
	 */
	void setConfig(ILPConfig value);

} // GipsIntermediateModel
