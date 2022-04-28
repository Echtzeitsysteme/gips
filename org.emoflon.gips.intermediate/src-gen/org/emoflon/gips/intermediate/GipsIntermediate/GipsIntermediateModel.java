/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Model</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel#getName
 * <em>Name</em>}</li>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel#getVariables
 * <em>Variables</em>}</li>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel#getConstraints
 * <em>Constraints</em>}</li>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel#getObjectives
 * <em>Objectives</em>}</li>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel#getGlobalObjective
 * <em>Global Objective</em>}</li>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel#getIbexModel
 * <em>Ibex Model</em>}</li>
 * <li>{@link org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel#getConfig
 * <em>Config</em>}</li>
 * </ul>
 *
 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getRoamIntermediateModel()
 * @model
 * @generated
 */
public interface RoamIntermediateModel extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getRoamIntermediateModel_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel#getName
	 * <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Variables</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link org.emoflon.roam.intermediate.RoamIntermediate.VariableSet}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Variables</em>' containment reference list.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getRoamIntermediateModel_Variables()
	 * @model containment="true"
	 * @generated
	 */
	EList<VariableSet> getVariables();

	/**
	 * Returns the value of the '<em><b>Constraints</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link org.emoflon.roam.intermediate.RoamIntermediate.Constraint}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Constraints</em>' containment reference list.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getRoamIntermediateModel_Constraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<Constraint> getConstraints();

	/**
	 * Returns the value of the '<em><b>Objectives</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link org.emoflon.roam.intermediate.RoamIntermediate.Objective}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Objectives</em>' containment reference list.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getRoamIntermediateModel_Objectives()
	 * @model containment="true"
	 * @generated
	 */
	EList<Objective> getObjectives();

	/**
	 * Returns the value of the '<em><b>Global Objective</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Global Objective</em>' containment reference.
	 * @see #setGlobalObjective(GlobalObjective)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getRoamIntermediateModel_GlobalObjective()
	 * @model containment="true"
	 * @generated
	 */
	GlobalObjective getGlobalObjective();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel#getGlobalObjective
	 * <em>Global Objective</em>}' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Global Objective</em>' containment
	 *              reference.
	 * @see #getGlobalObjective()
	 * @generated
	 */
	void setGlobalObjective(GlobalObjective value);

	/**
	 * Returns the value of the '<em><b>Ibex Model</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Ibex Model</em>' containment reference.
	 * @see #setIbexModel(IBeXModel)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getRoamIntermediateModel_IbexModel()
	 * @model containment="true" required="true"
	 * @generated
	 */
	IBeXModel getIbexModel();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel#getIbexModel
	 * <em>Ibex Model</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Ibex Model</em>' containment
	 *              reference.
	 * @see #getIbexModel()
	 * @generated
	 */
	void setIbexModel(IBeXModel value);

	/**
	 * Returns the value of the '<em><b>Config</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Config</em>' containment reference.
	 * @see #setConfig(ILPConfig)
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getRoamIntermediateModel_Config()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ILPConfig getConfig();

	/**
	 * Sets the value of the
	 * '{@link org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel#getConfig
	 * <em>Config</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Config</em>' containment reference.
	 * @see #getConfig()
	 * @generated
	 */
	void setConfig(ILPConfig value);

} // RoamIntermediateModel
