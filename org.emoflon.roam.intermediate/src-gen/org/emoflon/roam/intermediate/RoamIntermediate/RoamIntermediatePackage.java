/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateFactory
 * @model kind="package"
 * @generated
 */
public interface RoamIntermediatePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "RoamIntermediate";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/resource/org.emoflon.roam.intermediate/model/RoamIntermediate.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "RoamIntermediate";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RoamIntermediatePackage eINSTANCE = org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl
			.init();

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediateModelImpl <em>Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediateModelImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getRoamIntermediateModel()
	 * @generated
	 */
	int ROAM_INTERMEDIATE_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROAM_INTERMEDIATE_MODEL__NAME = 0;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROAM_INTERMEDIATE_MODEL__VARIABLES = 1;

	/**
	 * The feature id for the '<em><b>Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROAM_INTERMEDIATE_MODEL__CONSTRAINTS = 2;

	/**
	 * The feature id for the '<em><b>Objectives</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROAM_INTERMEDIATE_MODEL__OBJECTIVES = 3;

	/**
	 * The feature id for the '<em><b>Global Objective</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROAM_INTERMEDIATE_MODEL__GLOBAL_OBJECTIVE = 4;

	/**
	 * The feature id for the '<em><b>Ibex Model</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROAM_INTERMEDIATE_MODEL__IBEX_MODEL = 5;

	/**
	 * The number of structural features of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROAM_INTERMEDIATE_MODEL_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROAM_INTERMEDIATE_MODEL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.VariableSetImpl <em>Variable Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.VariableSetImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getVariableSet()
	 * @generated
	 */
	int VARIABLE_SET = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_SET__NAME = 0;

	/**
	 * The number of structural features of the '<em>Variable Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_SET_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Variable Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_SET_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.TypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.TypeImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getType()
	 * @generated
	 */
	int TYPE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__NAME = VARIABLE_SET__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__TYPE = VARIABLE_SET_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_FEATURE_COUNT = VARIABLE_SET_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_OPERATION_COUNT = VARIABLE_SET_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.MappingImpl <em>Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.MappingImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getMapping()
	 * @generated
	 */
	int MAPPING = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING__NAME = VARIABLE_SET__NAME;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING__RULE = VARIABLE_SET_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_FEATURE_COUNT = VARIABLE_SET_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_OPERATION_COUNT = VARIABLE_SET_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ConstraintImpl <em>Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.ConstraintImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getConstraint()
	 * @generated
	 */
	int CONSTRAINT = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Elementwise</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__ELEMENTWISE = 1;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__EXPRESSION = 2;

	/**
	 * The number of structural features of the '<em>Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ObjectiveImpl <em>Objective</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.ObjectiveImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getObjective()
	 * @generated
	 */
	int OBJECTIVE = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECTIVE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Elementwise</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECTIVE__ELEMENTWISE = 1;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECTIVE__EXPRESSION = 2;

	/**
	 * The number of structural features of the '<em>Objective</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECTIVE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Objective</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECTIVE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.GlobalObjectiveImpl <em>Global Objective</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.GlobalObjectiveImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getGlobalObjective()
	 * @generated
	 */
	int GLOBAL_OBJECTIVE = 6;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_OBJECTIVE__EXPRESSION = 0;

	/**
	 * The number of structural features of the '<em>Global Objective</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_OBJECTIVE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Global Objective</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_OBJECTIVE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.Context <em>Context</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.Context
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getContext()
	 * @generated
	 */
	int CONTEXT = 7;

	/**
	 * The number of structural features of the '<em>Context</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Context</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.TypeConstraintImpl <em>Type Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.TypeConstraintImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getTypeConstraint()
	 * @generated
	 */
	int TYPE_CONSTRAINT = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_CONSTRAINT__NAME = CONTEXT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Elementwise</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_CONSTRAINT__ELEMENTWISE = CONTEXT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_CONSTRAINT__EXPRESSION = CONTEXT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Model Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_CONSTRAINT__MODEL_TYPE = CONTEXT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Type Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_CONSTRAINT_FEATURE_COUNT = CONTEXT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Type Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_CONSTRAINT_OPERATION_COUNT = CONTEXT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.MappingConstraintImpl <em>Mapping Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.MappingConstraintImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getMappingConstraint()
	 * @generated
	 */
	int MAPPING_CONSTRAINT = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_CONSTRAINT__NAME = CONTEXT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Elementwise</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_CONSTRAINT__ELEMENTWISE = CONTEXT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_CONSTRAINT__EXPRESSION = CONTEXT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_CONSTRAINT__MAPPING = CONTEXT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Mapping Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_CONSTRAINT_FEATURE_COUNT = CONTEXT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Mapping Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_CONSTRAINT_OPERATION_COUNT = CONTEXT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.MappingObjectiveImpl <em>Mapping Objective</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.MappingObjectiveImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getMappingObjective()
	 * @generated
	 */
	int MAPPING_OBJECTIVE = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_OBJECTIVE__NAME = CONTEXT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Elementwise</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_OBJECTIVE__ELEMENTWISE = CONTEXT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_OBJECTIVE__EXPRESSION = CONTEXT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_OBJECTIVE__MAPPING = CONTEXT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Mapping Objective</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_OBJECTIVE_FEATURE_COUNT = CONTEXT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Mapping Objective</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_OBJECTIVE_OPERATION_COUNT = CONTEXT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.TypeObjectiveImpl <em>Type Objective</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.TypeObjectiveImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getTypeObjective()
	 * @generated
	 */
	int TYPE_OBJECTIVE = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_OBJECTIVE__NAME = CONTEXT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Elementwise</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_OBJECTIVE__ELEMENTWISE = CONTEXT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_OBJECTIVE__EXPRESSION = CONTEXT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Model Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_OBJECTIVE__MODEL_TYPE = CONTEXT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Type Objective</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_OBJECTIVE_FEATURE_COUNT = CONTEXT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Type Objective</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_OBJECTIVE_OPERATION_COUNT = CONTEXT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ArithmeticExpressionImpl <em>Arithmetic Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.ArithmeticExpressionImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getArithmeticExpression()
	 * @generated
	 */
	int ARITHMETIC_EXPRESSION = 12;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_EXPRESSION__RETURN_TYPE = 0;

	/**
	 * The number of structural features of the '<em>Arithmetic Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_EXPRESSION_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Arithmetic Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_EXPRESSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.BinaryArithmeticExpressionImpl <em>Binary Arithmetic Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.BinaryArithmeticExpressionImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getBinaryArithmeticExpression()
	 * @generated
	 */
	int BINARY_ARITHMETIC_EXPRESSION = 13;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_ARITHMETIC_EXPRESSION__RETURN_TYPE = ARITHMETIC_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_ARITHMETIC_EXPRESSION__LHS = ARITHMETIC_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_ARITHMETIC_EXPRESSION__RHS = ARITHMETIC_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_ARITHMETIC_EXPRESSION__OPERATOR = ARITHMETIC_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Binary Arithmetic Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_ARITHMETIC_EXPRESSION_FEATURE_COUNT = ARITHMETIC_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Binary Arithmetic Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINARY_ARITHMETIC_EXPRESSION_OPERATION_COUNT = ARITHMETIC_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.UnaryArithmeticExpressionImpl <em>Unary Arithmetic Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.UnaryArithmeticExpressionImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getUnaryArithmeticExpression()
	 * @generated
	 */
	int UNARY_ARITHMETIC_EXPRESSION = 14;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_ARITHMETIC_EXPRESSION__RETURN_TYPE = ARITHMETIC_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_ARITHMETIC_EXPRESSION__EXPRESSION = ARITHMETIC_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_ARITHMETIC_EXPRESSION__OPERATOR = ARITHMETIC_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Unary Arithmetic Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_ARITHMETIC_EXPRESSION_FEATURE_COUNT = ARITHMETIC_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Unary Arithmetic Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_ARITHMETIC_EXPRESSION_OPERATION_COUNT = ARITHMETIC_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ArithmeticStreamExpressionImpl <em>Arithmetic Stream Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.ArithmeticStreamExpressionImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getArithmeticStreamExpression()
	 * @generated
	 */
	int ARITHMETIC_STREAM_EXPRESSION = 15;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_STREAM_EXPRESSION__RETURN_TYPE = ARITHMETIC_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Stream</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_STREAM_EXPRESSION__STREAM = ARITHMETIC_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_STREAM_EXPRESSION__OPERATOR = ARITHMETIC_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Operation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_STREAM_EXPRESSION__OPERATION = ARITHMETIC_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Arithmetic Stream Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_STREAM_EXPRESSION_FEATURE_COUNT = ARITHMETIC_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Arithmetic Stream Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_STREAM_EXPRESSION_OPERATION_COUNT = ARITHMETIC_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.SetOperation <em>Set Operation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.SetOperation
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getSetOperation()
	 * @generated
	 */
	int SET_OPERATION = 16;

	/**
	 * The number of structural features of the '<em>Set Operation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SET_OPERATION_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Set Operation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SET_OPERATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.SumExpressionImpl <em>Sum Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.SumExpressionImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getSumExpression()
	 * @generated
	 */
	int SUM_EXPRESSION = 31;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.MappingSumExpressionImpl <em>Mapping Sum Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.MappingSumExpressionImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getMappingSumExpression()
	 * @generated
	 */
	int MAPPING_SUM_EXPRESSION = 32;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.TypeSumExpressionImpl <em>Type Sum Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.TypeSumExpressionImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getTypeSumExpression()
	 * @generated
	 */
	int TYPE_SUM_EXPRESSION = 33;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ArithmeticValueExpressionImpl <em>Arithmetic Value Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.ArithmeticValueExpressionImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getArithmeticValueExpression()
	 * @generated
	 */
	int ARITHMETIC_VALUE_EXPRESSION = 17;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_VALUE_EXPRESSION__RETURN_TYPE = ARITHMETIC_EXPRESSION__RETURN_TYPE;

	/**
	 * The number of structural features of the '<em>Arithmetic Value Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_VALUE_EXPRESSION_FEATURE_COUNT = ARITHMETIC_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Arithmetic Value Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_VALUE_EXPRESSION_OPERATION_COUNT = ARITHMETIC_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ArithmeticValueImpl <em>Arithmetic Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.ArithmeticValueImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getArithmeticValue()
	 * @generated
	 */
	int ARITHMETIC_VALUE = 18;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_VALUE__RETURN_TYPE = ARITHMETIC_VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_VALUE__VALUE = ARITHMETIC_VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Arithmetic Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_VALUE_FEATURE_COUNT = ARITHMETIC_VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Arithmetic Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_VALUE_OPERATION_COUNT = ARITHMETIC_VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ArithmeticLiteralImpl <em>Arithmetic Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.ArithmeticLiteralImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getArithmeticLiteral()
	 * @generated
	 */
	int ARITHMETIC_LITERAL = 19;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_LITERAL__RETURN_TYPE = ARITHMETIC_VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The number of structural features of the '<em>Arithmetic Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_LITERAL_FEATURE_COUNT = ARITHMETIC_VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Arithmetic Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_LITERAL_OPERATION_COUNT = ARITHMETIC_VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.IntegerLiteralImpl <em>Integer Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.IntegerLiteralImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getIntegerLiteral()
	 * @generated
	 */
	int INTEGER_LITERAL = 20;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_LITERAL__RETURN_TYPE = ARITHMETIC_LITERAL__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Literal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_LITERAL__LITERAL = ARITHMETIC_LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Integer Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_LITERAL_FEATURE_COUNT = ARITHMETIC_LITERAL_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Integer Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_LITERAL_OPERATION_COUNT = ARITHMETIC_LITERAL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.DoubleLiteralImpl <em>Double Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.DoubleLiteralImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getDoubleLiteral()
	 * @generated
	 */
	int DOUBLE_LITERAL = 21;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LITERAL__RETURN_TYPE = ARITHMETIC_LITERAL__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Literal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LITERAL__LITERAL = ARITHMETIC_LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Double Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LITERAL_FEATURE_COUNT = ARITHMETIC_LITERAL_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Double Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LITERAL_OPERATION_COUNT = ARITHMETIC_LITERAL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolExpressionImpl <em>Bool Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolExpressionImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getBoolExpression()
	 * @generated
	 */
	int BOOL_EXPRESSION = 22;

	/**
	 * The number of structural features of the '<em>Bool Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_EXPRESSION_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Bool Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_EXPRESSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolBinaryExpressionImpl <em>Bool Binary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolBinaryExpressionImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getBoolBinaryExpression()
	 * @generated
	 */
	int BOOL_BINARY_EXPRESSION = 23;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_BINARY_EXPRESSION__LHS = BOOL_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_BINARY_EXPRESSION__RHS = BOOL_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_BINARY_EXPRESSION__OPERATOR = BOOL_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Bool Binary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_BINARY_EXPRESSION_FEATURE_COUNT = BOOL_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Bool Binary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_BINARY_EXPRESSION_OPERATION_COUNT = BOOL_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolUnaryExpressionImpl <em>Bool Unary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolUnaryExpressionImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getBoolUnaryExpression()
	 * @generated
	 */
	int BOOL_UNARY_EXPRESSION = 24;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_UNARY_EXPRESSION__EXPRESSION = BOOL_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_UNARY_EXPRESSION__OPERATOR = BOOL_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Bool Unary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_UNARY_EXPRESSION_FEATURE_COUNT = BOOL_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Bool Unary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_UNARY_EXPRESSION_OPERATION_COUNT = BOOL_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolValueExpressionImpl <em>Bool Value Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolValueExpressionImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getBoolValueExpression()
	 * @generated
	 */
	int BOOL_VALUE_EXPRESSION = 25;

	/**
	 * The number of structural features of the '<em>Bool Value Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_VALUE_EXPRESSION_FEATURE_COUNT = BOOL_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Bool Value Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_VALUE_EXPRESSION_OPERATION_COUNT = BOOL_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolValueImpl <em>Bool Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolValueImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getBoolValue()
	 * @generated
	 */
	int BOOL_VALUE = 26;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_VALUE__VALUE = BOOL_VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Bool Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_VALUE_FEATURE_COUNT = BOOL_VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Bool Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_VALUE_OPERATION_COUNT = BOOL_VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolStreamExpressionImpl <em>Bool Stream Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolStreamExpressionImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getBoolStreamExpression()
	 * @generated
	 */
	int BOOL_STREAM_EXPRESSION = 27;

	/**
	 * The feature id for the '<em><b>Stream</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_STREAM_EXPRESSION__STREAM = BOOL_VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_STREAM_EXPRESSION__OPERATOR = BOOL_VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Bool Stream Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_STREAM_EXPRESSION_FEATURE_COUNT = BOOL_VALUE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Bool Stream Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_STREAM_EXPRESSION_OPERATION_COUNT = BOOL_VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.RelationalExpressionImpl <em>Relational Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RelationalExpressionImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getRelationalExpression()
	 * @generated
	 */
	int RELATIONAL_EXPRESSION = 28;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATIONAL_EXPRESSION__OPERATOR = BOOL_VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATIONAL_EXPRESSION__LHS = BOOL_VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATIONAL_EXPRESSION__RHS = BOOL_VALUE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Relational Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATIONAL_EXPRESSION_FEATURE_COUNT = BOOL_VALUE_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Relational Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATIONAL_EXPRESSION_OPERATION_COUNT = BOOL_VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolLiteralImpl <em>Bool Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolLiteralImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getBoolLiteral()
	 * @generated
	 */
	int BOOL_LITERAL = 29;

	/**
	 * The feature id for the '<em><b>Literal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_LITERAL__LITERAL = BOOL_VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Bool Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_LITERAL_FEATURE_COUNT = BOOL_VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Bool Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_LITERAL_OPERATION_COUNT = BOOL_VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ValueExpressionImpl <em>Value Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.ValueExpressionImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getValueExpression()
	 * @generated
	 */
	int VALUE_EXPRESSION = 30;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_EXPRESSION__RETURN_TYPE = 0;

	/**
	 * The number of structural features of the '<em>Value Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_EXPRESSION_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Value Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_EXPRESSION_OPERATION_COUNT = 0;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUM_EXPRESSION__RETURN_TYPE = VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUM_EXPRESSION__EXPRESSION = VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUM_EXPRESSION__FILTER = VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Sum Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUM_EXPRESSION_FEATURE_COUNT = VALUE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Sum Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUM_EXPRESSION_OPERATION_COUNT = VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_SUM_EXPRESSION__RETURN_TYPE = SUM_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_SUM_EXPRESSION__EXPRESSION = SUM_EXPRESSION__EXPRESSION;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_SUM_EXPRESSION__FILTER = SUM_EXPRESSION__FILTER;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_SUM_EXPRESSION__MAPPING = SUM_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Mapping Sum Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_SUM_EXPRESSION_FEATURE_COUNT = SUM_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Mapping Sum Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_SUM_EXPRESSION_OPERATION_COUNT = SUM_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_SUM_EXPRESSION__RETURN_TYPE = SUM_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_SUM_EXPRESSION__EXPRESSION = SUM_EXPRESSION__EXPRESSION;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_SUM_EXPRESSION__FILTER = SUM_EXPRESSION__FILTER;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_SUM_EXPRESSION__TYPE = SUM_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Type Sum Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_SUM_EXPRESSION_FEATURE_COUNT = SUM_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Type Sum Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_SUM_EXPRESSION_OPERATION_COUNT = SUM_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ContextTypeValueImpl <em>Context Type Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.ContextTypeValueImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getContextTypeValue()
	 * @generated
	 */
	int CONTEXT_TYPE_VALUE = 34;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_TYPE_VALUE__RETURN_TYPE = VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Type Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_TYPE_VALUE__TYPE_CONTEXT = VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Context Type Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_TYPE_VALUE_FEATURE_COUNT = VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Context Type Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_TYPE_VALUE_OPERATION_COUNT = VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ContextMappingValueImpl <em>Context Mapping Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.ContextMappingValueImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getContextMappingValue()
	 * @generated
	 */
	int CONTEXT_MAPPING_VALUE = 35;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_VALUE__RETURN_TYPE = VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Mapping Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_VALUE__MAPPING_CONTEXT = VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Context Mapping Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_VALUE_FEATURE_COUNT = VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Context Mapping Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_VALUE_OPERATION_COUNT = VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ContextMappingNodeImpl <em>Context Mapping Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.ContextMappingNodeImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getContextMappingNode()
	 * @generated
	 */
	int CONTEXT_MAPPING_NODE = 36;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_NODE__RETURN_TYPE = VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Mapping Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_NODE__MAPPING_CONTEXT = VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_NODE__NODE = VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Context Mapping Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_NODE_FEATURE_COUNT = VALUE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Context Mapping Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_NODE_OPERATION_COUNT = VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.FeatureExpressionImpl <em>Feature Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.FeatureExpressionImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getFeatureExpression()
	 * @generated
	 */
	int FEATURE_EXPRESSION = 37;

	/**
	 * The feature id for the '<em><b>Current</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_EXPRESSION__CURRENT = 0;

	/**
	 * The feature id for the '<em><b>Child</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_EXPRESSION__CHILD = 1;

	/**
	 * The number of structural features of the '<em>Feature Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_EXPRESSION_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Feature Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_EXPRESSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.FeatureLiteralImpl <em>Feature Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.FeatureLiteralImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getFeatureLiteral()
	 * @generated
	 */
	int FEATURE_LITERAL = 38;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_LITERAL__FEATURE = 0;

	/**
	 * The number of structural features of the '<em>Feature Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_LITERAL_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Feature Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_LITERAL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ContextTypeFeatureValueImpl <em>Context Type Feature Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.ContextTypeFeatureValueImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getContextTypeFeatureValue()
	 * @generated
	 */
	int CONTEXT_TYPE_FEATURE_VALUE = 39;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_TYPE_FEATURE_VALUE__RETURN_TYPE = CONTEXT_TYPE_VALUE__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Type Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_TYPE_FEATURE_VALUE__TYPE_CONTEXT = CONTEXT_TYPE_VALUE__TYPE_CONTEXT;

	/**
	 * The feature id for the '<em><b>Feature Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_TYPE_FEATURE_VALUE__FEATURE_EXPRESSION = CONTEXT_TYPE_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Context Type Feature Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_TYPE_FEATURE_VALUE_FEATURE_COUNT = CONTEXT_TYPE_VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Context Type Feature Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_TYPE_FEATURE_VALUE_OPERATION_COUNT = CONTEXT_TYPE_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ContextMappingNodeFeatureValueImpl <em>Context Mapping Node Feature Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.ContextMappingNodeFeatureValueImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getContextMappingNodeFeatureValue()
	 * @generated
	 */
	int CONTEXT_MAPPING_NODE_FEATURE_VALUE = 40;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_NODE_FEATURE_VALUE__RETURN_TYPE = CONTEXT_MAPPING_NODE__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Mapping Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_NODE_FEATURE_VALUE__MAPPING_CONTEXT = CONTEXT_MAPPING_NODE__MAPPING_CONTEXT;

	/**
	 * The feature id for the '<em><b>Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_NODE_FEATURE_VALUE__NODE = CONTEXT_MAPPING_NODE__NODE;

	/**
	 * The feature id for the '<em><b>Feature Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_NODE_FEATURE_VALUE__FEATURE_EXPRESSION = CONTEXT_MAPPING_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Context Mapping Node Feature Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_NODE_FEATURE_VALUE_FEATURE_COUNT = CONTEXT_MAPPING_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Context Mapping Node Feature Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_NODE_FEATURE_VALUE_OPERATION_COUNT = CONTEXT_MAPPING_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorImpl <em>Iterator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getIterator()
	 * @generated
	 */
	int ITERATOR = 41;

	/**
	 * The feature id for the '<em><b>Stream</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR__STREAM = 0;

	/**
	 * The number of structural features of the '<em>Iterator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Iterator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorMappingValueImpl <em>Iterator Mapping Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorMappingValueImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getIteratorMappingValue()
	 * @generated
	 */
	int ITERATOR_MAPPING_VALUE = 42;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_VALUE__RETURN_TYPE = VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Stream</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_VALUE__STREAM = VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Mapping Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_VALUE__MAPPING_CONTEXT = VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Iterator Mapping Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_VALUE_FEATURE_COUNT = VALUE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Iterator Mapping Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_VALUE_OPERATION_COUNT = VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorMappingFeatureValueImpl <em>Iterator Mapping Feature Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorMappingFeatureValueImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getIteratorMappingFeatureValue()
	 * @generated
	 */
	int ITERATOR_MAPPING_FEATURE_VALUE = 43;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_FEATURE_VALUE__RETURN_TYPE = VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Stream</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_FEATURE_VALUE__STREAM = VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Mapping Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_FEATURE_VALUE__MAPPING_CONTEXT = VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Feature Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_FEATURE_VALUE__FEATURE_EXPRESSION = VALUE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Iterator Mapping Feature Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_FEATURE_VALUE_FEATURE_COUNT = VALUE_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Iterator Mapping Feature Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_FEATURE_VALUE_OPERATION_COUNT = VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorMappingNodeValueImpl <em>Iterator Mapping Node Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorMappingNodeValueImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getIteratorMappingNodeValue()
	 * @generated
	 */
	int ITERATOR_MAPPING_NODE_VALUE = 44;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_NODE_VALUE__RETURN_TYPE = VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Stream</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_NODE_VALUE__STREAM = VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Mapping Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_NODE_VALUE__MAPPING_CONTEXT = VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_NODE_VALUE__NODE = VALUE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Iterator Mapping Node Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_NODE_VALUE_FEATURE_COUNT = VALUE_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Iterator Mapping Node Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_NODE_VALUE_OPERATION_COUNT = VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorMappingNodeFeatureValueImpl <em>Iterator Mapping Node Feature Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorMappingNodeFeatureValueImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getIteratorMappingNodeFeatureValue()
	 * @generated
	 */
	int ITERATOR_MAPPING_NODE_FEATURE_VALUE = 45;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_NODE_FEATURE_VALUE__RETURN_TYPE = ITERATOR_MAPPING_NODE_VALUE__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Stream</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_NODE_FEATURE_VALUE__STREAM = ITERATOR_MAPPING_NODE_VALUE__STREAM;

	/**
	 * The feature id for the '<em><b>Mapping Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_NODE_FEATURE_VALUE__MAPPING_CONTEXT = ITERATOR_MAPPING_NODE_VALUE__MAPPING_CONTEXT;

	/**
	 * The feature id for the '<em><b>Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_NODE_FEATURE_VALUE__NODE = ITERATOR_MAPPING_NODE_VALUE__NODE;

	/**
	 * The feature id for the '<em><b>Feature Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_NODE_FEATURE_VALUE__FEATURE_EXPRESSION = ITERATOR_MAPPING_NODE_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Iterator Mapping Node Feature Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_NODE_FEATURE_VALUE_FEATURE_COUNT = ITERATOR_MAPPING_NODE_VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Iterator Mapping Node Feature Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_NODE_FEATURE_VALUE_OPERATION_COUNT = ITERATOR_MAPPING_NODE_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorTypeValueImpl <em>Iterator Type Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorTypeValueImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getIteratorTypeValue()
	 * @generated
	 */
	int ITERATOR_TYPE_VALUE = 46;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_TYPE_VALUE__RETURN_TYPE = VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Stream</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_TYPE_VALUE__STREAM = VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_TYPE_VALUE__TYPE_CONTEXT = VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Iterator Type Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_TYPE_VALUE_FEATURE_COUNT = VALUE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Iterator Type Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_TYPE_VALUE_OPERATION_COUNT = VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorTypeFeatureValueImpl <em>Iterator Type Feature Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorTypeFeatureValueImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getIteratorTypeFeatureValue()
	 * @generated
	 */
	int ITERATOR_TYPE_FEATURE_VALUE = 47;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_TYPE_FEATURE_VALUE__RETURN_TYPE = ITERATOR_TYPE_VALUE__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Stream</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_TYPE_FEATURE_VALUE__STREAM = ITERATOR_TYPE_VALUE__STREAM;

	/**
	 * The feature id for the '<em><b>Type Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_TYPE_FEATURE_VALUE__TYPE_CONTEXT = ITERATOR_TYPE_VALUE__TYPE_CONTEXT;

	/**
	 * The feature id for the '<em><b>Feature Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_TYPE_FEATURE_VALUE__FEATURE_EXPRESSION = ITERATOR_TYPE_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Iterator Type Feature Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_TYPE_FEATURE_VALUE_FEATURE_COUNT = ITERATOR_TYPE_VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Iterator Type Feature Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITERATOR_TYPE_FEATURE_VALUE_OPERATION_COUNT = ITERATOR_TYPE_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.StreamExpressionImpl <em>Stream Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.StreamExpressionImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getStreamExpression()
	 * @generated
	 */
	int STREAM_EXPRESSION = 48;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_EXPRESSION__RETURN_TYPE = SET_OPERATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Current</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_EXPRESSION__CURRENT = SET_OPERATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Child</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_EXPRESSION__CHILD = SET_OPERATION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Stream Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_EXPRESSION_FEATURE_COUNT = SET_OPERATION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Stream Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_EXPRESSION_OPERATION_COUNT = SET_OPERATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamOperation <em>Stream Operation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.StreamOperation
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getStreamOperation()
	 * @generated
	 */
	int STREAM_OPERATION = 49;

	/**
	 * The number of structural features of the '<em>Stream Operation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_OPERATION_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Stream Operation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_OPERATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.StreamFilterOperationImpl <em>Stream Filter Operation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.StreamFilterOperationImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getStreamFilterOperation()
	 * @generated
	 */
	int STREAM_FILTER_OPERATION = 50;

	/**
	 * The feature id for the '<em><b>Predicate</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_FILTER_OPERATION__PREDICATE = STREAM_OPERATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Stream Filter Operation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_FILTER_OPERATION_FEATURE_COUNT = STREAM_OPERATION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Stream Filter Operation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_FILTER_OPERATION_OPERATION_COUNT = STREAM_OPERATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.StreamSelectOperationImpl <em>Stream Select Operation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.StreamSelectOperationImpl
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getStreamSelectOperation()
	 * @generated
	 */
	int STREAM_SELECT_OPERATION = 51;

	/**
	 * The feature id for the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_SELECT_OPERATION__TYPE = STREAM_OPERATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Stream Select Operation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_SELECT_OPERATION_FEATURE_COUNT = STREAM_OPERATION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Stream Select Operation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STREAM_SELECT_OPERATION_OPERATION_COUNT = STREAM_OPERATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.RelationalOperator <em>Relational Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RelationalOperator
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getRelationalOperator()
	 * @generated
	 */
	int RELATIONAL_OPERATOR = 52;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticOperator <em>Binary Arithmetic Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticOperator
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getBinaryArithmeticOperator()
	 * @generated
	 */
	int BINARY_ARITHMETIC_OPERATOR = 53;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticOperator <em>Unary Arithmetic Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticOperator
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getUnaryArithmeticOperator()
	 * @generated
	 */
	int UNARY_ARITHMETIC_OPERATOR = 54;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamArithmeticOperator <em>Stream Arithmetic Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.StreamArithmeticOperator
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getStreamArithmeticOperator()
	 * @generated
	 */
	int STREAM_ARITHMETIC_OPERATOR = 55;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.BinaryBoolOperator <em>Binary Bool Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BinaryBoolOperator
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getBinaryBoolOperator()
	 * @generated
	 */
	int BINARY_BOOL_OPERATOR = 56;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.UnaryBoolOperator <em>Unary Bool Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.UnaryBoolOperator
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getUnaryBoolOperator()
	 * @generated
	 */
	int UNARY_BOOL_OPERATOR = 57;

	/**
	 * The meta object id for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamBoolOperator <em>Stream Bool Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.StreamBoolOperator
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getStreamBoolOperator()
	 * @generated
	 */
	int STREAM_BOOL_OPERATOR = 58;

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel
	 * @generated
	 */
	EClass getRoamIntermediateModel();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel#getName()
	 * @see #getRoamIntermediateModel()
	 * @generated
	 */
	EAttribute getRoamIntermediateModel_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel#getVariables <em>Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Variables</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel#getVariables()
	 * @see #getRoamIntermediateModel()
	 * @generated
	 */
	EReference getRoamIntermediateModel_Variables();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel#getConstraints <em>Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Constraints</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel#getConstraints()
	 * @see #getRoamIntermediateModel()
	 * @generated
	 */
	EReference getRoamIntermediateModel_Constraints();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel#getObjectives <em>Objectives</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Objectives</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel#getObjectives()
	 * @see #getRoamIntermediateModel()
	 * @generated
	 */
	EReference getRoamIntermediateModel_Objectives();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel#getGlobalObjective <em>Global Objective</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Global Objective</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel#getGlobalObjective()
	 * @see #getRoamIntermediateModel()
	 * @generated
	 */
	EReference getRoamIntermediateModel_GlobalObjective();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel#getIbexModel <em>Ibex Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Ibex Model</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel#getIbexModel()
	 * @see #getRoamIntermediateModel()
	 * @generated
	 */
	EReference getRoamIntermediateModel_IbexModel();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.VariableSet <em>Variable Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable Set</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.VariableSet
	 * @generated
	 */
	EClass getVariableSet();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.roam.intermediate.RoamIntermediate.VariableSet#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.VariableSet#getName()
	 * @see #getVariableSet()
	 * @generated
	 */
	EAttribute getVariableSet_Name();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.Type <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.Type
	 * @generated
	 */
	EClass getType();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.Type#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.Type#getType()
	 * @see #getType()
	 * @generated
	 */
	EReference getType_Type();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.Mapping <em>Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mapping</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.Mapping
	 * @generated
	 */
	EClass getMapping();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.Mapping#getRule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Rule</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.Mapping#getRule()
	 * @see #getMapping()
	 * @generated
	 */
	EReference getMapping_Rule();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.Constraint <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constraint</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.Constraint
	 * @generated
	 */
	EClass getConstraint();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.roam.intermediate.RoamIntermediate.Constraint#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.Constraint#getName()
	 * @see #getConstraint()
	 * @generated
	 */
	EAttribute getConstraint_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.roam.intermediate.RoamIntermediate.Constraint#isElementwise <em>Elementwise</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Elementwise</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.Constraint#isElementwise()
	 * @see #getConstraint()
	 * @generated
	 */
	EAttribute getConstraint_Elementwise();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.Constraint#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.Constraint#getExpression()
	 * @see #getConstraint()
	 * @generated
	 */
	EReference getConstraint_Expression();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.Objective <em>Objective</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Objective</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.Objective
	 * @generated
	 */
	EClass getObjective();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.roam.intermediate.RoamIntermediate.Objective#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.Objective#getName()
	 * @see #getObjective()
	 * @generated
	 */
	EAttribute getObjective_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.roam.intermediate.RoamIntermediate.Objective#isElementwise <em>Elementwise</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Elementwise</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.Objective#isElementwise()
	 * @see #getObjective()
	 * @generated
	 */
	EAttribute getObjective_Elementwise();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.Objective#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.Objective#getExpression()
	 * @see #getObjective()
	 * @generated
	 */
	EReference getObjective_Expression();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.GlobalObjective <em>Global Objective</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Global Objective</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.GlobalObjective
	 * @generated
	 */
	EClass getGlobalObjective();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.GlobalObjective#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.GlobalObjective#getExpression()
	 * @see #getGlobalObjective()
	 * @generated
	 */
	EReference getGlobalObjective_Expression();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.Context <em>Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Context</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.Context
	 * @generated
	 */
	EClass getContext();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.TypeConstraint <em>Type Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Constraint</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.TypeConstraint
	 * @generated
	 */
	EClass getTypeConstraint();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.TypeConstraint#getModelType <em>Model Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Model Type</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.TypeConstraint#getModelType()
	 * @see #getTypeConstraint()
	 * @generated
	 */
	EReference getTypeConstraint_ModelType();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.MappingConstraint <em>Mapping Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mapping Constraint</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.MappingConstraint
	 * @generated
	 */
	EClass getMappingConstraint();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.MappingConstraint#getMapping <em>Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Mapping</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.MappingConstraint#getMapping()
	 * @see #getMappingConstraint()
	 * @generated
	 */
	EReference getMappingConstraint_Mapping();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.MappingObjective <em>Mapping Objective</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mapping Objective</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.MappingObjective
	 * @generated
	 */
	EClass getMappingObjective();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.MappingObjective#getMapping <em>Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Mapping</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.MappingObjective#getMapping()
	 * @see #getMappingObjective()
	 * @generated
	 */
	EReference getMappingObjective_Mapping();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.TypeObjective <em>Type Objective</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Objective</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.TypeObjective
	 * @generated
	 */
	EClass getTypeObjective();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.TypeObjective#getModelType <em>Model Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Model Type</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.TypeObjective#getModelType()
	 * @see #getTypeObjective()
	 * @generated
	 */
	EReference getTypeObjective_ModelType();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression <em>Arithmetic Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Arithmetic Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression
	 * @generated
	 */
	EClass getArithmeticExpression();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression#getReturnType <em>Return Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Return Type</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression#getReturnType()
	 * @see #getArithmeticExpression()
	 * @generated
	 */
	EReference getArithmeticExpression_ReturnType();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticExpression <em>Binary Arithmetic Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Binary Arithmetic Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticExpression
	 * @generated
	 */
	EClass getBinaryArithmeticExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticExpression#getLhs <em>Lhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Lhs</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticExpression#getLhs()
	 * @see #getBinaryArithmeticExpression()
	 * @generated
	 */
	EReference getBinaryArithmeticExpression_Lhs();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticExpression#getRhs <em>Rhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rhs</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticExpression#getRhs()
	 * @see #getBinaryArithmeticExpression()
	 * @generated
	 */
	EReference getBinaryArithmeticExpression_Rhs();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticExpression#getOperator()
	 * @see #getBinaryArithmeticExpression()
	 * @generated
	 */
	EAttribute getBinaryArithmeticExpression_Operator();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticExpression <em>Unary Arithmetic Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unary Arithmetic Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticExpression
	 * @generated
	 */
	EClass getUnaryArithmeticExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticExpression#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticExpression#getExpression()
	 * @see #getUnaryArithmeticExpression()
	 * @generated
	 */
	EReference getUnaryArithmeticExpression_Expression();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticExpression#getOperator()
	 * @see #getUnaryArithmeticExpression()
	 * @generated
	 */
	EAttribute getUnaryArithmeticExpression_Operator();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticStreamExpression <em>Arithmetic Stream Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Arithmetic Stream Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticStreamExpression
	 * @generated
	 */
	EClass getArithmeticStreamExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticStreamExpression#getStream <em>Stream</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Stream</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticStreamExpression#getStream()
	 * @see #getArithmeticStreamExpression()
	 * @generated
	 */
	EReference getArithmeticStreamExpression_Stream();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticStreamExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticStreamExpression#getOperator()
	 * @see #getArithmeticStreamExpression()
	 * @generated
	 */
	EAttribute getArithmeticStreamExpression_Operator();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticStreamExpression#getOperation <em>Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Operation</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticStreamExpression#getOperation()
	 * @see #getArithmeticStreamExpression()
	 * @generated
	 */
	EReference getArithmeticStreamExpression_Operation();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.SetOperation <em>Set Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Set Operation</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.SetOperation
	 * @generated
	 */
	EClass getSetOperation();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.SumExpression <em>Sum Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sum Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.SumExpression
	 * @generated
	 */
	EClass getSumExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.SumExpression#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.SumExpression#getExpression()
	 * @see #getSumExpression()
	 * @generated
	 */
	EReference getSumExpression_Expression();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.SumExpression#getFilter <em>Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Filter</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.SumExpression#getFilter()
	 * @see #getSumExpression()
	 * @generated
	 */
	EReference getSumExpression_Filter();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.MappingSumExpression <em>Mapping Sum Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mapping Sum Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.MappingSumExpression
	 * @generated
	 */
	EClass getMappingSumExpression();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.MappingSumExpression#getMapping <em>Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Mapping</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.MappingSumExpression#getMapping()
	 * @see #getMappingSumExpression()
	 * @generated
	 */
	EReference getMappingSumExpression_Mapping();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.TypeSumExpression <em>Type Sum Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Sum Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.TypeSumExpression
	 * @generated
	 */
	EClass getTypeSumExpression();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.TypeSumExpression#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.TypeSumExpression#getType()
	 * @see #getTypeSumExpression()
	 * @generated
	 */
	EReference getTypeSumExpression_Type();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticValueExpression <em>Arithmetic Value Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Arithmetic Value Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticValueExpression
	 * @generated
	 */
	EClass getArithmeticValueExpression();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticValue <em>Arithmetic Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Arithmetic Value</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticValue
	 * @generated
	 */
	EClass getArithmeticValue();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticValue#getValue()
	 * @see #getArithmeticValue()
	 * @generated
	 */
	EReference getArithmeticValue_Value();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticLiteral <em>Arithmetic Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Arithmetic Literal</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticLiteral
	 * @generated
	 */
	EClass getArithmeticLiteral();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.IntegerLiteral <em>Integer Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Integer Literal</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.IntegerLiteral
	 * @generated
	 */
	EClass getIntegerLiteral();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.roam.intermediate.RoamIntermediate.IntegerLiteral#getLiteral <em>Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Literal</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.IntegerLiteral#getLiteral()
	 * @see #getIntegerLiteral()
	 * @generated
	 */
	EAttribute getIntegerLiteral_Literal();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.DoubleLiteral <em>Double Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Double Literal</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.DoubleLiteral
	 * @generated
	 */
	EClass getDoubleLiteral();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.roam.intermediate.RoamIntermediate.DoubleLiteral#getLiteral <em>Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Literal</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.DoubleLiteral#getLiteral()
	 * @see #getDoubleLiteral()
	 * @generated
	 */
	EAttribute getDoubleLiteral_Literal();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolExpression <em>Bool Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bool Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BoolExpression
	 * @generated
	 */
	EClass getBoolExpression();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolBinaryExpression <em>Bool Binary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bool Binary Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BoolBinaryExpression
	 * @generated
	 */
	EClass getBoolBinaryExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolBinaryExpression#getLhs <em>Lhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Lhs</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BoolBinaryExpression#getLhs()
	 * @see #getBoolBinaryExpression()
	 * @generated
	 */
	EReference getBoolBinaryExpression_Lhs();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolBinaryExpression#getRhs <em>Rhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rhs</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BoolBinaryExpression#getRhs()
	 * @see #getBoolBinaryExpression()
	 * @generated
	 */
	EReference getBoolBinaryExpression_Rhs();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolBinaryExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BoolBinaryExpression#getOperator()
	 * @see #getBoolBinaryExpression()
	 * @generated
	 */
	EAttribute getBoolBinaryExpression_Operator();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolUnaryExpression <em>Bool Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bool Unary Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BoolUnaryExpression
	 * @generated
	 */
	EClass getBoolUnaryExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolUnaryExpression#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BoolUnaryExpression#getExpression()
	 * @see #getBoolUnaryExpression()
	 * @generated
	 */
	EReference getBoolUnaryExpression_Expression();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolUnaryExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BoolUnaryExpression#getOperator()
	 * @see #getBoolUnaryExpression()
	 * @generated
	 */
	EAttribute getBoolUnaryExpression_Operator();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolValueExpression <em>Bool Value Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bool Value Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BoolValueExpression
	 * @generated
	 */
	EClass getBoolValueExpression();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolValue <em>Bool Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bool Value</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BoolValue
	 * @generated
	 */
	EClass getBoolValue();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BoolValue#getValue()
	 * @see #getBoolValue()
	 * @generated
	 */
	EReference getBoolValue_Value();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolStreamExpression <em>Bool Stream Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bool Stream Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BoolStreamExpression
	 * @generated
	 */
	EClass getBoolStreamExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolStreamExpression#getStream <em>Stream</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Stream</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BoolStreamExpression#getStream()
	 * @see #getBoolStreamExpression()
	 * @generated
	 */
	EReference getBoolStreamExpression_Stream();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolStreamExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BoolStreamExpression#getOperator()
	 * @see #getBoolStreamExpression()
	 * @generated
	 */
	EAttribute getBoolStreamExpression_Operator();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.RelationalExpression <em>Relational Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Relational Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RelationalExpression
	 * @generated
	 */
	EClass getRelationalExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.roam.intermediate.RoamIntermediate.RelationalExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RelationalExpression#getOperator()
	 * @see #getRelationalExpression()
	 * @generated
	 */
	EAttribute getRelationalExpression_Operator();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.RelationalExpression#getLhs <em>Lhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Lhs</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RelationalExpression#getLhs()
	 * @see #getRelationalExpression()
	 * @generated
	 */
	EReference getRelationalExpression_Lhs();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.RelationalExpression#getRhs <em>Rhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rhs</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RelationalExpression#getRhs()
	 * @see #getRelationalExpression()
	 * @generated
	 */
	EReference getRelationalExpression_Rhs();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolLiteral <em>Bool Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bool Literal</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BoolLiteral
	 * @generated
	 */
	EClass getBoolLiteral();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolLiteral#isLiteral <em>Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Literal</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BoolLiteral#isLiteral()
	 * @see #getBoolLiteral()
	 * @generated
	 */
	EAttribute getBoolLiteral_Literal();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.ValueExpression <em>Value Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Value Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ValueExpression
	 * @generated
	 */
	EClass getValueExpression();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.ValueExpression#getReturnType <em>Return Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Return Type</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ValueExpression#getReturnType()
	 * @see #getValueExpression()
	 * @generated
	 */
	EReference getValueExpression_ReturnType();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeValue <em>Context Type Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Context Type Value</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeValue
	 * @generated
	 */
	EClass getContextTypeValue();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeValue#getTypeContext <em>Type Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type Context</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeValue#getTypeContext()
	 * @see #getContextTypeValue()
	 * @generated
	 */
	EReference getContextTypeValue_TypeContext();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingValue <em>Context Mapping Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Context Mapping Value</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingValue
	 * @generated
	 */
	EClass getContextMappingValue();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingValue#getMappingContext <em>Mapping Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Mapping Context</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingValue#getMappingContext()
	 * @see #getContextMappingValue()
	 * @generated
	 */
	EReference getContextMappingValue_MappingContext();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNode <em>Context Mapping Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Context Mapping Node</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNode
	 * @generated
	 */
	EClass getContextMappingNode();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNode#getMappingContext <em>Mapping Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Mapping Context</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNode#getMappingContext()
	 * @see #getContextMappingNode()
	 * @generated
	 */
	EReference getContextMappingNode_MappingContext();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNode#getNode <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Node</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNode#getNode()
	 * @see #getContextMappingNode()
	 * @generated
	 */
	EReference getContextMappingNode_Node();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.FeatureExpression <em>Feature Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.FeatureExpression
	 * @generated
	 */
	EClass getFeatureExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.FeatureExpression#getCurrent <em>Current</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Current</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.FeatureExpression#getCurrent()
	 * @see #getFeatureExpression()
	 * @generated
	 */
	EReference getFeatureExpression_Current();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.FeatureExpression#getChild <em>Child</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Child</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.FeatureExpression#getChild()
	 * @see #getFeatureExpression()
	 * @generated
	 */
	EReference getFeatureExpression_Child();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.FeatureLiteral <em>Feature Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature Literal</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.FeatureLiteral
	 * @generated
	 */
	EClass getFeatureLiteral();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.FeatureLiteral#getFeature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Feature</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.FeatureLiteral#getFeature()
	 * @see #getFeatureLiteral()
	 * @generated
	 */
	EReference getFeatureLiteral_Feature();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeFeatureValue <em>Context Type Feature Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Context Type Feature Value</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeFeatureValue
	 * @generated
	 */
	EClass getContextTypeFeatureValue();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeFeatureValue#getFeatureExpression <em>Feature Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Feature Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeFeatureValue#getFeatureExpression()
	 * @see #getContextTypeFeatureValue()
	 * @generated
	 */
	EReference getContextTypeFeatureValue_FeatureExpression();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNodeFeatureValue <em>Context Mapping Node Feature Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Context Mapping Node Feature Value</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNodeFeatureValue
	 * @generated
	 */
	EClass getContextMappingNodeFeatureValue();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNodeFeatureValue#getFeatureExpression <em>Feature Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Feature Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNodeFeatureValue#getFeatureExpression()
	 * @see #getContextMappingNodeFeatureValue()
	 * @generated
	 */
	EReference getContextMappingNodeFeatureValue_FeatureExpression();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.Iterator <em>Iterator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Iterator</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.Iterator
	 * @generated
	 */
	EClass getIterator();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.Iterator#getStream <em>Stream</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Stream</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.Iterator#getStream()
	 * @see #getIterator()
	 * @generated
	 */
	EReference getIterator_Stream();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingValue <em>Iterator Mapping Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Iterator Mapping Value</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingValue
	 * @generated
	 */
	EClass getIteratorMappingValue();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingValue#getMappingContext <em>Mapping Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Mapping Context</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingValue#getMappingContext()
	 * @see #getIteratorMappingValue()
	 * @generated
	 */
	EReference getIteratorMappingValue_MappingContext();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingFeatureValue <em>Iterator Mapping Feature Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Iterator Mapping Feature Value</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingFeatureValue
	 * @generated
	 */
	EClass getIteratorMappingFeatureValue();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingFeatureValue#getMappingContext <em>Mapping Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Mapping Context</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingFeatureValue#getMappingContext()
	 * @see #getIteratorMappingFeatureValue()
	 * @generated
	 */
	EReference getIteratorMappingFeatureValue_MappingContext();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingFeatureValue#getFeatureExpression <em>Feature Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Feature Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingFeatureValue#getFeatureExpression()
	 * @see #getIteratorMappingFeatureValue()
	 * @generated
	 */
	EReference getIteratorMappingFeatureValue_FeatureExpression();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeValue <em>Iterator Mapping Node Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Iterator Mapping Node Value</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeValue
	 * @generated
	 */
	EClass getIteratorMappingNodeValue();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeValue#getMappingContext <em>Mapping Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Mapping Context</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeValue#getMappingContext()
	 * @see #getIteratorMappingNodeValue()
	 * @generated
	 */
	EReference getIteratorMappingNodeValue_MappingContext();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeValue#getNode <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Node</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeValue#getNode()
	 * @see #getIteratorMappingNodeValue()
	 * @generated
	 */
	EReference getIteratorMappingNodeValue_Node();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeFeatureValue <em>Iterator Mapping Node Feature Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Iterator Mapping Node Feature Value</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeFeatureValue
	 * @generated
	 */
	EClass getIteratorMappingNodeFeatureValue();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeFeatureValue#getFeatureExpression <em>Feature Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Feature Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeFeatureValue#getFeatureExpression()
	 * @see #getIteratorMappingNodeFeatureValue()
	 * @generated
	 */
	EReference getIteratorMappingNodeFeatureValue_FeatureExpression();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.IteratorTypeValue <em>Iterator Type Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Iterator Type Value</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.IteratorTypeValue
	 * @generated
	 */
	EClass getIteratorTypeValue();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.IteratorTypeValue#getTypeContext <em>Type Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type Context</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.IteratorTypeValue#getTypeContext()
	 * @see #getIteratorTypeValue()
	 * @generated
	 */
	EReference getIteratorTypeValue_TypeContext();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.IteratorTypeFeatureValue <em>Iterator Type Feature Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Iterator Type Feature Value</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.IteratorTypeFeatureValue
	 * @generated
	 */
	EClass getIteratorTypeFeatureValue();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.IteratorTypeFeatureValue#getFeatureExpression <em>Feature Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Feature Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.IteratorTypeFeatureValue#getFeatureExpression()
	 * @see #getIteratorTypeFeatureValue()
	 * @generated
	 */
	EReference getIteratorTypeFeatureValue_FeatureExpression();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression <em>Stream Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Stream Expression</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression
	 * @generated
	 */
	EClass getStreamExpression();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression#getReturnType <em>Return Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Return Type</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression#getReturnType()
	 * @see #getStreamExpression()
	 * @generated
	 */
	EReference getStreamExpression_ReturnType();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression#getCurrent <em>Current</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Current</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression#getCurrent()
	 * @see #getStreamExpression()
	 * @generated
	 */
	EReference getStreamExpression_Current();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression#getChild <em>Child</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Child</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression#getChild()
	 * @see #getStreamExpression()
	 * @generated
	 */
	EReference getStreamExpression_Child();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamOperation <em>Stream Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Stream Operation</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.StreamOperation
	 * @generated
	 */
	EClass getStreamOperation();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamFilterOperation <em>Stream Filter Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Stream Filter Operation</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.StreamFilterOperation
	 * @generated
	 */
	EClass getStreamFilterOperation();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamFilterOperation#getPredicate <em>Predicate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Predicate</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.StreamFilterOperation#getPredicate()
	 * @see #getStreamFilterOperation()
	 * @generated
	 */
	EReference getStreamFilterOperation_Predicate();

	/**
	 * Returns the meta object for class '{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamSelectOperation <em>Stream Select Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Stream Select Operation</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.StreamSelectOperation
	 * @generated
	 */
	EClass getStreamSelectOperation();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamSelectOperation#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Type</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.StreamSelectOperation#getType()
	 * @see #getStreamSelectOperation()
	 * @generated
	 */
	EReference getStreamSelectOperation_Type();

	/**
	 * Returns the meta object for enum '{@link org.emoflon.roam.intermediate.RoamIntermediate.RelationalOperator <em>Relational Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Relational Operator</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RelationalOperator
	 * @generated
	 */
	EEnum getRelationalOperator();

	/**
	 * Returns the meta object for enum '{@link org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticOperator <em>Binary Arithmetic Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Binary Arithmetic Operator</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticOperator
	 * @generated
	 */
	EEnum getBinaryArithmeticOperator();

	/**
	 * Returns the meta object for enum '{@link org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticOperator <em>Unary Arithmetic Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Unary Arithmetic Operator</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticOperator
	 * @generated
	 */
	EEnum getUnaryArithmeticOperator();

	/**
	 * Returns the meta object for enum '{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamArithmeticOperator <em>Stream Arithmetic Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Stream Arithmetic Operator</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.StreamArithmeticOperator
	 * @generated
	 */
	EEnum getStreamArithmeticOperator();

	/**
	 * Returns the meta object for enum '{@link org.emoflon.roam.intermediate.RoamIntermediate.BinaryBoolOperator <em>Binary Bool Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Binary Bool Operator</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BinaryBoolOperator
	 * @generated
	 */
	EEnum getBinaryBoolOperator();

	/**
	 * Returns the meta object for enum '{@link org.emoflon.roam.intermediate.RoamIntermediate.UnaryBoolOperator <em>Unary Bool Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Unary Bool Operator</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.UnaryBoolOperator
	 * @generated
	 */
	EEnum getUnaryBoolOperator();

	/**
	 * Returns the meta object for enum '{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamBoolOperator <em>Stream Bool Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Stream Bool Operator</em>'.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.StreamBoolOperator
	 * @generated
	 */
	EEnum getStreamBoolOperator();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	RoamIntermediateFactory getRoamIntermediateFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediateModelImpl <em>Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediateModelImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getRoamIntermediateModel()
		 * @generated
		 */
		EClass ROAM_INTERMEDIATE_MODEL = eINSTANCE.getRoamIntermediateModel();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROAM_INTERMEDIATE_MODEL__NAME = eINSTANCE.getRoamIntermediateModel_Name();

		/**
		 * The meta object literal for the '<em><b>Variables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROAM_INTERMEDIATE_MODEL__VARIABLES = eINSTANCE.getRoamIntermediateModel_Variables();

		/**
		 * The meta object literal for the '<em><b>Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROAM_INTERMEDIATE_MODEL__CONSTRAINTS = eINSTANCE.getRoamIntermediateModel_Constraints();

		/**
		 * The meta object literal for the '<em><b>Objectives</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROAM_INTERMEDIATE_MODEL__OBJECTIVES = eINSTANCE.getRoamIntermediateModel_Objectives();

		/**
		 * The meta object literal for the '<em><b>Global Objective</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROAM_INTERMEDIATE_MODEL__GLOBAL_OBJECTIVE = eINSTANCE.getRoamIntermediateModel_GlobalObjective();

		/**
		 * The meta object literal for the '<em><b>Ibex Model</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROAM_INTERMEDIATE_MODEL__IBEX_MODEL = eINSTANCE.getRoamIntermediateModel_IbexModel();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.VariableSetImpl <em>Variable Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.VariableSetImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getVariableSet()
		 * @generated
		 */
		EClass VARIABLE_SET = eINSTANCE.getVariableSet();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIABLE_SET__NAME = eINSTANCE.getVariableSet_Name();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.TypeImpl <em>Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.TypeImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getType()
		 * @generated
		 */
		EClass TYPE = eINSTANCE.getType();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE__TYPE = eINSTANCE.getType_Type();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.MappingImpl <em>Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.MappingImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getMapping()
		 * @generated
		 */
		EClass MAPPING = eINSTANCE.getMapping();

		/**
		 * The meta object literal for the '<em><b>Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING__RULE = eINSTANCE.getMapping_Rule();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ConstraintImpl <em>Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.ConstraintImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getConstraint()
		 * @generated
		 */
		EClass CONSTRAINT = eINSTANCE.getConstraint();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTRAINT__NAME = eINSTANCE.getConstraint_Name();

		/**
		 * The meta object literal for the '<em><b>Elementwise</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTRAINT__ELEMENTWISE = eINSTANCE.getConstraint_Elementwise();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINT__EXPRESSION = eINSTANCE.getConstraint_Expression();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ObjectiveImpl <em>Objective</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.ObjectiveImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getObjective()
		 * @generated
		 */
		EClass OBJECTIVE = eINSTANCE.getObjective();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBJECTIVE__NAME = eINSTANCE.getObjective_Name();

		/**
		 * The meta object literal for the '<em><b>Elementwise</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBJECTIVE__ELEMENTWISE = eINSTANCE.getObjective_Elementwise();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OBJECTIVE__EXPRESSION = eINSTANCE.getObjective_Expression();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.GlobalObjectiveImpl <em>Global Objective</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.GlobalObjectiveImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getGlobalObjective()
		 * @generated
		 */
		EClass GLOBAL_OBJECTIVE = eINSTANCE.getGlobalObjective();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GLOBAL_OBJECTIVE__EXPRESSION = eINSTANCE.getGlobalObjective_Expression();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.Context <em>Context</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.Context
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getContext()
		 * @generated
		 */
		EClass CONTEXT = eINSTANCE.getContext();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.TypeConstraintImpl <em>Type Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.TypeConstraintImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getTypeConstraint()
		 * @generated
		 */
		EClass TYPE_CONSTRAINT = eINSTANCE.getTypeConstraint();

		/**
		 * The meta object literal for the '<em><b>Model Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE_CONSTRAINT__MODEL_TYPE = eINSTANCE.getTypeConstraint_ModelType();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.MappingConstraintImpl <em>Mapping Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.MappingConstraintImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getMappingConstraint()
		 * @generated
		 */
		EClass MAPPING_CONSTRAINT = eINSTANCE.getMappingConstraint();

		/**
		 * The meta object literal for the '<em><b>Mapping</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING_CONSTRAINT__MAPPING = eINSTANCE.getMappingConstraint_Mapping();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.MappingObjectiveImpl <em>Mapping Objective</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.MappingObjectiveImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getMappingObjective()
		 * @generated
		 */
		EClass MAPPING_OBJECTIVE = eINSTANCE.getMappingObjective();

		/**
		 * The meta object literal for the '<em><b>Mapping</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING_OBJECTIVE__MAPPING = eINSTANCE.getMappingObjective_Mapping();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.TypeObjectiveImpl <em>Type Objective</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.TypeObjectiveImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getTypeObjective()
		 * @generated
		 */
		EClass TYPE_OBJECTIVE = eINSTANCE.getTypeObjective();

		/**
		 * The meta object literal for the '<em><b>Model Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE_OBJECTIVE__MODEL_TYPE = eINSTANCE.getTypeObjective_ModelType();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ArithmeticExpressionImpl <em>Arithmetic Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.ArithmeticExpressionImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getArithmeticExpression()
		 * @generated
		 */
		EClass ARITHMETIC_EXPRESSION = eINSTANCE.getArithmeticExpression();

		/**
		 * The meta object literal for the '<em><b>Return Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARITHMETIC_EXPRESSION__RETURN_TYPE = eINSTANCE.getArithmeticExpression_ReturnType();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.BinaryArithmeticExpressionImpl <em>Binary Arithmetic Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.BinaryArithmeticExpressionImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getBinaryArithmeticExpression()
		 * @generated
		 */
		EClass BINARY_ARITHMETIC_EXPRESSION = eINSTANCE.getBinaryArithmeticExpression();

		/**
		 * The meta object literal for the '<em><b>Lhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINARY_ARITHMETIC_EXPRESSION__LHS = eINSTANCE.getBinaryArithmeticExpression_Lhs();

		/**
		 * The meta object literal for the '<em><b>Rhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINARY_ARITHMETIC_EXPRESSION__RHS = eINSTANCE.getBinaryArithmeticExpression_Rhs();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BINARY_ARITHMETIC_EXPRESSION__OPERATOR = eINSTANCE.getBinaryArithmeticExpression_Operator();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.UnaryArithmeticExpressionImpl <em>Unary Arithmetic Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.UnaryArithmeticExpressionImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getUnaryArithmeticExpression()
		 * @generated
		 */
		EClass UNARY_ARITHMETIC_EXPRESSION = eINSTANCE.getUnaryArithmeticExpression();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UNARY_ARITHMETIC_EXPRESSION__EXPRESSION = eINSTANCE.getUnaryArithmeticExpression_Expression();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNARY_ARITHMETIC_EXPRESSION__OPERATOR = eINSTANCE.getUnaryArithmeticExpression_Operator();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ArithmeticStreamExpressionImpl <em>Arithmetic Stream Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.ArithmeticStreamExpressionImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getArithmeticStreamExpression()
		 * @generated
		 */
		EClass ARITHMETIC_STREAM_EXPRESSION = eINSTANCE.getArithmeticStreamExpression();

		/**
		 * The meta object literal for the '<em><b>Stream</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARITHMETIC_STREAM_EXPRESSION__STREAM = eINSTANCE.getArithmeticStreamExpression_Stream();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARITHMETIC_STREAM_EXPRESSION__OPERATOR = eINSTANCE.getArithmeticStreamExpression_Operator();

		/**
		 * The meta object literal for the '<em><b>Operation</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARITHMETIC_STREAM_EXPRESSION__OPERATION = eINSTANCE.getArithmeticStreamExpression_Operation();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.SetOperation <em>Set Operation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.SetOperation
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getSetOperation()
		 * @generated
		 */
		EClass SET_OPERATION = eINSTANCE.getSetOperation();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.SumExpressionImpl <em>Sum Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.SumExpressionImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getSumExpression()
		 * @generated
		 */
		EClass SUM_EXPRESSION = eINSTANCE.getSumExpression();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUM_EXPRESSION__EXPRESSION = eINSTANCE.getSumExpression_Expression();

		/**
		 * The meta object literal for the '<em><b>Filter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUM_EXPRESSION__FILTER = eINSTANCE.getSumExpression_Filter();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.MappingSumExpressionImpl <em>Mapping Sum Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.MappingSumExpressionImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getMappingSumExpression()
		 * @generated
		 */
		EClass MAPPING_SUM_EXPRESSION = eINSTANCE.getMappingSumExpression();

		/**
		 * The meta object literal for the '<em><b>Mapping</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING_SUM_EXPRESSION__MAPPING = eINSTANCE.getMappingSumExpression_Mapping();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.TypeSumExpressionImpl <em>Type Sum Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.TypeSumExpressionImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getTypeSumExpression()
		 * @generated
		 */
		EClass TYPE_SUM_EXPRESSION = eINSTANCE.getTypeSumExpression();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE_SUM_EXPRESSION__TYPE = eINSTANCE.getTypeSumExpression_Type();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ArithmeticValueExpressionImpl <em>Arithmetic Value Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.ArithmeticValueExpressionImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getArithmeticValueExpression()
		 * @generated
		 */
		EClass ARITHMETIC_VALUE_EXPRESSION = eINSTANCE.getArithmeticValueExpression();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ArithmeticValueImpl <em>Arithmetic Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.ArithmeticValueImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getArithmeticValue()
		 * @generated
		 */
		EClass ARITHMETIC_VALUE = eINSTANCE.getArithmeticValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARITHMETIC_VALUE__VALUE = eINSTANCE.getArithmeticValue_Value();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ArithmeticLiteralImpl <em>Arithmetic Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.ArithmeticLiteralImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getArithmeticLiteral()
		 * @generated
		 */
		EClass ARITHMETIC_LITERAL = eINSTANCE.getArithmeticLiteral();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.IntegerLiteralImpl <em>Integer Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.IntegerLiteralImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getIntegerLiteral()
		 * @generated
		 */
		EClass INTEGER_LITERAL = eINSTANCE.getIntegerLiteral();

		/**
		 * The meta object literal for the '<em><b>Literal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTEGER_LITERAL__LITERAL = eINSTANCE.getIntegerLiteral_Literal();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.DoubleLiteralImpl <em>Double Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.DoubleLiteralImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getDoubleLiteral()
		 * @generated
		 */
		EClass DOUBLE_LITERAL = eINSTANCE.getDoubleLiteral();

		/**
		 * The meta object literal for the '<em><b>Literal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOUBLE_LITERAL__LITERAL = eINSTANCE.getDoubleLiteral_Literal();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolExpressionImpl <em>Bool Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolExpressionImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getBoolExpression()
		 * @generated
		 */
		EClass BOOL_EXPRESSION = eINSTANCE.getBoolExpression();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolBinaryExpressionImpl <em>Bool Binary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolBinaryExpressionImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getBoolBinaryExpression()
		 * @generated
		 */
		EClass BOOL_BINARY_EXPRESSION = eINSTANCE.getBoolBinaryExpression();

		/**
		 * The meta object literal for the '<em><b>Lhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BOOL_BINARY_EXPRESSION__LHS = eINSTANCE.getBoolBinaryExpression_Lhs();

		/**
		 * The meta object literal for the '<em><b>Rhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BOOL_BINARY_EXPRESSION__RHS = eINSTANCE.getBoolBinaryExpression_Rhs();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOL_BINARY_EXPRESSION__OPERATOR = eINSTANCE.getBoolBinaryExpression_Operator();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolUnaryExpressionImpl <em>Bool Unary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolUnaryExpressionImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getBoolUnaryExpression()
		 * @generated
		 */
		EClass BOOL_UNARY_EXPRESSION = eINSTANCE.getBoolUnaryExpression();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BOOL_UNARY_EXPRESSION__EXPRESSION = eINSTANCE.getBoolUnaryExpression_Expression();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOL_UNARY_EXPRESSION__OPERATOR = eINSTANCE.getBoolUnaryExpression_Operator();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolValueExpressionImpl <em>Bool Value Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolValueExpressionImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getBoolValueExpression()
		 * @generated
		 */
		EClass BOOL_VALUE_EXPRESSION = eINSTANCE.getBoolValueExpression();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolValueImpl <em>Bool Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolValueImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getBoolValue()
		 * @generated
		 */
		EClass BOOL_VALUE = eINSTANCE.getBoolValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BOOL_VALUE__VALUE = eINSTANCE.getBoolValue_Value();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolStreamExpressionImpl <em>Bool Stream Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolStreamExpressionImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getBoolStreamExpression()
		 * @generated
		 */
		EClass BOOL_STREAM_EXPRESSION = eINSTANCE.getBoolStreamExpression();

		/**
		 * The meta object literal for the '<em><b>Stream</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BOOL_STREAM_EXPRESSION__STREAM = eINSTANCE.getBoolStreamExpression_Stream();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOL_STREAM_EXPRESSION__OPERATOR = eINSTANCE.getBoolStreamExpression_Operator();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.RelationalExpressionImpl <em>Relational Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RelationalExpressionImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getRelationalExpression()
		 * @generated
		 */
		EClass RELATIONAL_EXPRESSION = eINSTANCE.getRelationalExpression();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RELATIONAL_EXPRESSION__OPERATOR = eINSTANCE.getRelationalExpression_Operator();

		/**
		 * The meta object literal for the '<em><b>Lhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RELATIONAL_EXPRESSION__LHS = eINSTANCE.getRelationalExpression_Lhs();

		/**
		 * The meta object literal for the '<em><b>Rhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RELATIONAL_EXPRESSION__RHS = eINSTANCE.getRelationalExpression_Rhs();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolLiteralImpl <em>Bool Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.BoolLiteralImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getBoolLiteral()
		 * @generated
		 */
		EClass BOOL_LITERAL = eINSTANCE.getBoolLiteral();

		/**
		 * The meta object literal for the '<em><b>Literal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOL_LITERAL__LITERAL = eINSTANCE.getBoolLiteral_Literal();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ValueExpressionImpl <em>Value Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.ValueExpressionImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getValueExpression()
		 * @generated
		 */
		EClass VALUE_EXPRESSION = eINSTANCE.getValueExpression();

		/**
		 * The meta object literal for the '<em><b>Return Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VALUE_EXPRESSION__RETURN_TYPE = eINSTANCE.getValueExpression_ReturnType();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ContextTypeValueImpl <em>Context Type Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.ContextTypeValueImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getContextTypeValue()
		 * @generated
		 */
		EClass CONTEXT_TYPE_VALUE = eINSTANCE.getContextTypeValue();

		/**
		 * The meta object literal for the '<em><b>Type Context</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTEXT_TYPE_VALUE__TYPE_CONTEXT = eINSTANCE.getContextTypeValue_TypeContext();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ContextMappingValueImpl <em>Context Mapping Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.ContextMappingValueImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getContextMappingValue()
		 * @generated
		 */
		EClass CONTEXT_MAPPING_VALUE = eINSTANCE.getContextMappingValue();

		/**
		 * The meta object literal for the '<em><b>Mapping Context</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTEXT_MAPPING_VALUE__MAPPING_CONTEXT = eINSTANCE.getContextMappingValue_MappingContext();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ContextMappingNodeImpl <em>Context Mapping Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.ContextMappingNodeImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getContextMappingNode()
		 * @generated
		 */
		EClass CONTEXT_MAPPING_NODE = eINSTANCE.getContextMappingNode();

		/**
		 * The meta object literal for the '<em><b>Mapping Context</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTEXT_MAPPING_NODE__MAPPING_CONTEXT = eINSTANCE.getContextMappingNode_MappingContext();

		/**
		 * The meta object literal for the '<em><b>Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTEXT_MAPPING_NODE__NODE = eINSTANCE.getContextMappingNode_Node();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.FeatureExpressionImpl <em>Feature Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.FeatureExpressionImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getFeatureExpression()
		 * @generated
		 */
		EClass FEATURE_EXPRESSION = eINSTANCE.getFeatureExpression();

		/**
		 * The meta object literal for the '<em><b>Current</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_EXPRESSION__CURRENT = eINSTANCE.getFeatureExpression_Current();

		/**
		 * The meta object literal for the '<em><b>Child</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_EXPRESSION__CHILD = eINSTANCE.getFeatureExpression_Child();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.FeatureLiteralImpl <em>Feature Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.FeatureLiteralImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getFeatureLiteral()
		 * @generated
		 */
		EClass FEATURE_LITERAL = eINSTANCE.getFeatureLiteral();

		/**
		 * The meta object literal for the '<em><b>Feature</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_LITERAL__FEATURE = eINSTANCE.getFeatureLiteral_Feature();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ContextTypeFeatureValueImpl <em>Context Type Feature Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.ContextTypeFeatureValueImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getContextTypeFeatureValue()
		 * @generated
		 */
		EClass CONTEXT_TYPE_FEATURE_VALUE = eINSTANCE.getContextTypeFeatureValue();

		/**
		 * The meta object literal for the '<em><b>Feature Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTEXT_TYPE_FEATURE_VALUE__FEATURE_EXPRESSION = eINSTANCE
				.getContextTypeFeatureValue_FeatureExpression();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.ContextMappingNodeFeatureValueImpl <em>Context Mapping Node Feature Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.ContextMappingNodeFeatureValueImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getContextMappingNodeFeatureValue()
		 * @generated
		 */
		EClass CONTEXT_MAPPING_NODE_FEATURE_VALUE = eINSTANCE.getContextMappingNodeFeatureValue();

		/**
		 * The meta object literal for the '<em><b>Feature Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTEXT_MAPPING_NODE_FEATURE_VALUE__FEATURE_EXPRESSION = eINSTANCE
				.getContextMappingNodeFeatureValue_FeatureExpression();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorImpl <em>Iterator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getIterator()
		 * @generated
		 */
		EClass ITERATOR = eINSTANCE.getIterator();

		/**
		 * The meta object literal for the '<em><b>Stream</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITERATOR__STREAM = eINSTANCE.getIterator_Stream();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorMappingValueImpl <em>Iterator Mapping Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorMappingValueImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getIteratorMappingValue()
		 * @generated
		 */
		EClass ITERATOR_MAPPING_VALUE = eINSTANCE.getIteratorMappingValue();

		/**
		 * The meta object literal for the '<em><b>Mapping Context</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITERATOR_MAPPING_VALUE__MAPPING_CONTEXT = eINSTANCE.getIteratorMappingValue_MappingContext();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorMappingFeatureValueImpl <em>Iterator Mapping Feature Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorMappingFeatureValueImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getIteratorMappingFeatureValue()
		 * @generated
		 */
		EClass ITERATOR_MAPPING_FEATURE_VALUE = eINSTANCE.getIteratorMappingFeatureValue();

		/**
		 * The meta object literal for the '<em><b>Mapping Context</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITERATOR_MAPPING_FEATURE_VALUE__MAPPING_CONTEXT = eINSTANCE
				.getIteratorMappingFeatureValue_MappingContext();

		/**
		 * The meta object literal for the '<em><b>Feature Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITERATOR_MAPPING_FEATURE_VALUE__FEATURE_EXPRESSION = eINSTANCE
				.getIteratorMappingFeatureValue_FeatureExpression();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorMappingNodeValueImpl <em>Iterator Mapping Node Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorMappingNodeValueImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getIteratorMappingNodeValue()
		 * @generated
		 */
		EClass ITERATOR_MAPPING_NODE_VALUE = eINSTANCE.getIteratorMappingNodeValue();

		/**
		 * The meta object literal for the '<em><b>Mapping Context</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITERATOR_MAPPING_NODE_VALUE__MAPPING_CONTEXT = eINSTANCE
				.getIteratorMappingNodeValue_MappingContext();

		/**
		 * The meta object literal for the '<em><b>Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITERATOR_MAPPING_NODE_VALUE__NODE = eINSTANCE.getIteratorMappingNodeValue_Node();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorMappingNodeFeatureValueImpl <em>Iterator Mapping Node Feature Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorMappingNodeFeatureValueImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getIteratorMappingNodeFeatureValue()
		 * @generated
		 */
		EClass ITERATOR_MAPPING_NODE_FEATURE_VALUE = eINSTANCE.getIteratorMappingNodeFeatureValue();

		/**
		 * The meta object literal for the '<em><b>Feature Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITERATOR_MAPPING_NODE_FEATURE_VALUE__FEATURE_EXPRESSION = eINSTANCE
				.getIteratorMappingNodeFeatureValue_FeatureExpression();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorTypeValueImpl <em>Iterator Type Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorTypeValueImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getIteratorTypeValue()
		 * @generated
		 */
		EClass ITERATOR_TYPE_VALUE = eINSTANCE.getIteratorTypeValue();

		/**
		 * The meta object literal for the '<em><b>Type Context</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITERATOR_TYPE_VALUE__TYPE_CONTEXT = eINSTANCE.getIteratorTypeValue_TypeContext();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorTypeFeatureValueImpl <em>Iterator Type Feature Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.IteratorTypeFeatureValueImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getIteratorTypeFeatureValue()
		 * @generated
		 */
		EClass ITERATOR_TYPE_FEATURE_VALUE = eINSTANCE.getIteratorTypeFeatureValue();

		/**
		 * The meta object literal for the '<em><b>Feature Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITERATOR_TYPE_FEATURE_VALUE__FEATURE_EXPRESSION = eINSTANCE
				.getIteratorTypeFeatureValue_FeatureExpression();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.StreamExpressionImpl <em>Stream Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.StreamExpressionImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getStreamExpression()
		 * @generated
		 */
		EClass STREAM_EXPRESSION = eINSTANCE.getStreamExpression();

		/**
		 * The meta object literal for the '<em><b>Return Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STREAM_EXPRESSION__RETURN_TYPE = eINSTANCE.getStreamExpression_ReturnType();

		/**
		 * The meta object literal for the '<em><b>Current</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STREAM_EXPRESSION__CURRENT = eINSTANCE.getStreamExpression_Current();

		/**
		 * The meta object literal for the '<em><b>Child</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STREAM_EXPRESSION__CHILD = eINSTANCE.getStreamExpression_Child();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamOperation <em>Stream Operation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.StreamOperation
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getStreamOperation()
		 * @generated
		 */
		EClass STREAM_OPERATION = eINSTANCE.getStreamOperation();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.StreamFilterOperationImpl <em>Stream Filter Operation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.StreamFilterOperationImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getStreamFilterOperation()
		 * @generated
		 */
		EClass STREAM_FILTER_OPERATION = eINSTANCE.getStreamFilterOperation();

		/**
		 * The meta object literal for the '<em><b>Predicate</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STREAM_FILTER_OPERATION__PREDICATE = eINSTANCE.getStreamFilterOperation_Predicate();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.impl.StreamSelectOperationImpl <em>Stream Select Operation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.StreamSelectOperationImpl
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getStreamSelectOperation()
		 * @generated
		 */
		EClass STREAM_SELECT_OPERATION = eINSTANCE.getStreamSelectOperation();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STREAM_SELECT_OPERATION__TYPE = eINSTANCE.getStreamSelectOperation_Type();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.RelationalOperator <em>Relational Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.RelationalOperator
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getRelationalOperator()
		 * @generated
		 */
		EEnum RELATIONAL_OPERATOR = eINSTANCE.getRelationalOperator();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticOperator <em>Binary Arithmetic Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticOperator
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getBinaryArithmeticOperator()
		 * @generated
		 */
		EEnum BINARY_ARITHMETIC_OPERATOR = eINSTANCE.getBinaryArithmeticOperator();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticOperator <em>Unary Arithmetic Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticOperator
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getUnaryArithmeticOperator()
		 * @generated
		 */
		EEnum UNARY_ARITHMETIC_OPERATOR = eINSTANCE.getUnaryArithmeticOperator();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamArithmeticOperator <em>Stream Arithmetic Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.StreamArithmeticOperator
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getStreamArithmeticOperator()
		 * @generated
		 */
		EEnum STREAM_ARITHMETIC_OPERATOR = eINSTANCE.getStreamArithmeticOperator();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.BinaryBoolOperator <em>Binary Bool Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.BinaryBoolOperator
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getBinaryBoolOperator()
		 * @generated
		 */
		EEnum BINARY_BOOL_OPERATOR = eINSTANCE.getBinaryBoolOperator();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.UnaryBoolOperator <em>Unary Bool Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.UnaryBoolOperator
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getUnaryBoolOperator()
		 * @generated
		 */
		EEnum UNARY_BOOL_OPERATOR = eINSTANCE.getUnaryBoolOperator();

		/**
		 * The meta object literal for the '{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamBoolOperator <em>Stream Bool Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.StreamBoolOperator
		 * @see org.emoflon.roam.intermediate.RoamIntermediate.impl.RoamIntermediatePackageImpl#getStreamBoolOperator()
		 * @generated
		 */
		EEnum STREAM_BOOL_OPERATOR = eINSTANCE.getStreamBoolOperator();

	}

} //RoamIntermediatePackage
