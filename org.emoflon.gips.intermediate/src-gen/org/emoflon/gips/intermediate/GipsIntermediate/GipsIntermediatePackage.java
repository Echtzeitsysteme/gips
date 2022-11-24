/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateFactory
 * @model kind="package"
 * @generated
 */
public interface GipsIntermediatePackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "GipsIntermediate";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "platform:/resource/org.emoflon.gips.intermediate/model/GipsIntermediate.ecore";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "GipsIntermediate";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	GipsIntermediatePackage eINSTANCE = org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl
			.init();

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediateModelImpl
	 * <em>Model</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediateModelImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getGipsIntermediateModel()
	 * @generated
	 */
	int GIPS_INTERMEDIATE_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GIPS_INTERMEDIATE_MODEL__NAME = 0;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GIPS_INTERMEDIATE_MODEL__VARIABLES = 1;

	/**
	 * The feature id for the '<em><b>Constraints</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GIPS_INTERMEDIATE_MODEL__CONSTRAINTS = 2;

	/**
	 * The feature id for the '<em><b>Objectives</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GIPS_INTERMEDIATE_MODEL__OBJECTIVES = 3;

	/**
	 * The feature id for the '<em><b>Global Objective</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GIPS_INTERMEDIATE_MODEL__GLOBAL_OBJECTIVE = 4;

	/**
	 * The feature id for the '<em><b>Ibex Model</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GIPS_INTERMEDIATE_MODEL__IBEX_MODEL = 5;

	/**
	 * The feature id for the '<em><b>Config</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GIPS_INTERMEDIATE_MODEL__CONFIG = 6;

	/**
	 * The number of structural features of the '<em>Model</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GIPS_INTERMEDIATE_MODEL_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Model</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GIPS_INTERMEDIATE_MODEL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ILPConfigImpl
	 * <em>ILP Config</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ILPConfigImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getILPConfig()
	 * @generated
	 */
	int ILP_CONFIG = 1;

	/**
	 * The feature id for the '<em><b>Solver</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ILP_CONFIG__SOLVER = 0;

	/**
	 * The feature id for the '<em><b>Solver Home Dir</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ILP_CONFIG__SOLVER_HOME_DIR = 1;

	/**
	 * The feature id for the '<em><b>Solver License File</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ILP_CONFIG__SOLVER_LICENSE_FILE = 2;

	/**
	 * The feature id for the '<em><b>Build Launch Config</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ILP_CONFIG__BUILD_LAUNCH_CONFIG = 3;

	/**
	 * The feature id for the '<em><b>Main File</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ILP_CONFIG__MAIN_FILE = 4;

	/**
	 * The feature id for the '<em><b>Enable Time Limit</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ILP_CONFIG__ENABLE_TIME_LIMIT = 5;

	/**
	 * The feature id for the '<em><b>Ilp Time Limit</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ILP_CONFIG__ILP_TIME_LIMIT = 6;

	/**
	 * The feature id for the '<em><b>Enable Rnd Seed</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ILP_CONFIG__ENABLE_RND_SEED = 7;

	/**
	 * The feature id for the '<em><b>Ilp Rnd Seed</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ILP_CONFIG__ILP_RND_SEED = 8;

	/**
	 * The feature id for the '<em><b>Presolve</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ILP_CONFIG__PRESOLVE = 9;

	/**
	 * The feature id for the '<em><b>Enable Presolve</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ILP_CONFIG__ENABLE_PRESOLVE = 10;

	/**
	 * The feature id for the '<em><b>Enable Debug Output</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ILP_CONFIG__ENABLE_DEBUG_OUTPUT = 11;

	/**
	 * The feature id for the '<em><b>Enable Custom Tolerance</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ILP_CONFIG__ENABLE_CUSTOM_TOLERANCE = 12;

	/**
	 * The feature id for the '<em><b>Tolerance</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ILP_CONFIG__TOLERANCE = 13;

	/**
	 * The feature id for the '<em><b>Enable Lp Output</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ILP_CONFIG__ENABLE_LP_OUTPUT = 14;

	/**
	 * The feature id for the '<em><b>Lp Path</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ILP_CONFIG__LP_PATH = 15;

	/**
	 * The number of structural features of the '<em>ILP Config</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ILP_CONFIG_FEATURE_COUNT = 16;

	/**
	 * The number of operations of the '<em>ILP Config</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ILP_CONFIG_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.VariableSetImpl
	 * <em>Variable Set</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.VariableSetImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getVariableSet()
	 * @generated
	 */
	int VARIABLE_SET = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_SET__NAME = 0;

	/**
	 * The feature id for the '<em><b>Upper Bound</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_SET__UPPER_BOUND = 1;

	/**
	 * The feature id for the '<em><b>Lower Bound</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_SET__LOWER_BOUND = 2;

	/**
	 * The number of structural features of the '<em>Variable Set</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_SET_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Variable Set</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_SET_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternImpl
	 * <em>Pattern</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getPattern()
	 * @generated
	 */
	int PATTERN = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN__NAME = VARIABLE_SET__NAME;

	/**
	 * The feature id for the '<em><b>Upper Bound</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN__UPPER_BOUND = VARIABLE_SET__UPPER_BOUND;

	/**
	 * The feature id for the '<em><b>Lower Bound</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN__LOWER_BOUND = VARIABLE_SET__LOWER_BOUND;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN__PATTERN = VARIABLE_SET_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Is Rule</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN__IS_RULE = VARIABLE_SET_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Pattern</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_FEATURE_COUNT = VARIABLE_SET_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Pattern</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_OPERATION_COUNT = VARIABLE_SET_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.TypeImpl
	 * <em>Type</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.TypeImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getType()
	 * @generated
	 */
	int TYPE = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE__NAME = VARIABLE_SET__NAME;

	/**
	 * The feature id for the '<em><b>Upper Bound</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE__UPPER_BOUND = VARIABLE_SET__UPPER_BOUND;

	/**
	 * The feature id for the '<em><b>Lower Bound</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE__LOWER_BOUND = VARIABLE_SET__LOWER_BOUND;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE__TYPE = VARIABLE_SET_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Type</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_FEATURE_COUNT = VARIABLE_SET_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Type</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_OPERATION_COUNT = VARIABLE_SET_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.MappingImpl
	 * <em>Mapping</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.MappingImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getMapping()
	 * @generated
	 */
	int MAPPING = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING__NAME = VARIABLE_SET__NAME;

	/**
	 * The feature id for the '<em><b>Upper Bound</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING__UPPER_BOUND = VARIABLE_SET__UPPER_BOUND;

	/**
	 * The feature id for the '<em><b>Lower Bound</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING__LOWER_BOUND = VARIABLE_SET__LOWER_BOUND;

	/**
	 * The feature id for the '<em><b>Context Pattern</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING__CONTEXT_PATTERN = VARIABLE_SET_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Mapping</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_FEATURE_COUNT = VARIABLE_SET_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Mapping</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_OPERATION_COUNT = VARIABLE_SET_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.GTMappingImpl
	 * <em>GT Mapping</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GTMappingImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getGTMapping()
	 * @generated
	 */
	int GT_MAPPING = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GT_MAPPING__NAME = MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Upper Bound</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GT_MAPPING__UPPER_BOUND = MAPPING__UPPER_BOUND;

	/**
	 * The feature id for the '<em><b>Lower Bound</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GT_MAPPING__LOWER_BOUND = MAPPING__LOWER_BOUND;

	/**
	 * The feature id for the '<em><b>Context Pattern</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GT_MAPPING__CONTEXT_PATTERN = MAPPING__CONTEXT_PATTERN;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GT_MAPPING__RULE = MAPPING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>GT Mapping</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GT_MAPPING_FEATURE_COUNT = MAPPING_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>GT Mapping</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GT_MAPPING_OPERATION_COUNT = MAPPING_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternMappingImpl
	 * <em>Pattern Mapping</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternMappingImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getPatternMapping()
	 * @generated
	 */
	int PATTERN_MAPPING = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_MAPPING__NAME = MAPPING__NAME;

	/**
	 * The feature id for the '<em><b>Upper Bound</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_MAPPING__UPPER_BOUND = MAPPING__UPPER_BOUND;

	/**
	 * The feature id for the '<em><b>Lower Bound</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_MAPPING__LOWER_BOUND = MAPPING__LOWER_BOUND;

	/**
	 * The feature id for the '<em><b>Context Pattern</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_MAPPING__CONTEXT_PATTERN = MAPPING__CONTEXT_PATTERN;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_MAPPING__PATTERN = MAPPING_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Pattern Mapping</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_MAPPING_FEATURE_COUNT = MAPPING_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Pattern Mapping</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_MAPPING_OPERATION_COUNT = MAPPING_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.VariableImpl
	 * <em>Variable</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.VariableImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getVariable()
	 * @generated
	 */
	int VARIABLE = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE__NAME = VARIABLE_SET__NAME;

	/**
	 * The feature id for the '<em><b>Upper Bound</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE__UPPER_BOUND = VARIABLE_SET__UPPER_BOUND;

	/**
	 * The feature id for the '<em><b>Lower Bound</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE__LOWER_BOUND = VARIABLE_SET__LOWER_BOUND;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE__TYPE = VARIABLE_SET_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Variable</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_FEATURE_COUNT = VARIABLE_SET_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Variable</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_OPERATION_COUNT = VARIABLE_SET_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ConstraintImpl
	 * <em>Constraint</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ConstraintImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getConstraint()
	 * @generated
	 */
	int CONSTRAINT = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Depending</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__DEPENDING = 1;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__EXPRESSION = 2;

	/**
	 * The feature id for the '<em><b>Constant</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__CONSTANT = 3;

	/**
	 * The feature id for the '<em><b>Negated</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__NEGATED = 4;

	/**
	 * The feature id for the '<em><b>Dependencies</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__DEPENDENCIES = 5;

	/**
	 * The feature id for the '<em><b>Referenced By</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__REFERENCED_BY = 6;

	/**
	 * The feature id for the '<em><b>Symbolic Variable</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__SYMBOLIC_VARIABLE = 7;

	/**
	 * The feature id for the '<em><b>Helper Variables</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__HELPER_VARIABLES = 8;

	/**
	 * The feature id for the '<em><b>Helper Constraints</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__HELPER_CONSTRAINTS = 9;

	/**
	 * The number of structural features of the '<em>Constraint</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_FEATURE_COUNT = 10;

	/**
	 * The number of operations of the '<em>Constraint</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.GlobalConstraintImpl
	 * <em>Global Constraint</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GlobalConstraintImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getGlobalConstraint()
	 * @generated
	 */
	int GLOBAL_CONSTRAINT = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONSTRAINT__NAME = CONSTRAINT__NAME;

	/**
	 * The feature id for the '<em><b>Depending</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONSTRAINT__DEPENDING = CONSTRAINT__DEPENDING;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONSTRAINT__EXPRESSION = CONSTRAINT__EXPRESSION;

	/**
	 * The feature id for the '<em><b>Constant</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONSTRAINT__CONSTANT = CONSTRAINT__CONSTANT;

	/**
	 * The feature id for the '<em><b>Negated</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONSTRAINT__NEGATED = CONSTRAINT__NEGATED;

	/**
	 * The feature id for the '<em><b>Dependencies</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONSTRAINT__DEPENDENCIES = CONSTRAINT__DEPENDENCIES;

	/**
	 * The feature id for the '<em><b>Referenced By</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONSTRAINT__REFERENCED_BY = CONSTRAINT__REFERENCED_BY;

	/**
	 * The feature id for the '<em><b>Symbolic Variable</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONSTRAINT__SYMBOLIC_VARIABLE = CONSTRAINT__SYMBOLIC_VARIABLE;

	/**
	 * The feature id for the '<em><b>Helper Variables</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONSTRAINT__HELPER_VARIABLES = CONSTRAINT__HELPER_VARIABLES;

	/**
	 * The feature id for the '<em><b>Helper Constraints</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONSTRAINT__HELPER_CONSTRAINTS = CONSTRAINT__HELPER_CONSTRAINTS;

	/**
	 * The number of structural features of the '<em>Global Constraint</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONSTRAINT_FEATURE_COUNT = CONSTRAINT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Global Constraint</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONSTRAINT_OPERATION_COUNT = CONSTRAINT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ObjectiveImpl
	 * <em>Objective</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ObjectiveImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getObjective()
	 * @generated
	 */
	int OBJECTIVE = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OBJECTIVE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Elementwise</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OBJECTIVE__ELEMENTWISE = 1;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OBJECTIVE__EXPRESSION = 2;

	/**
	 * The number of structural features of the '<em>Objective</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OBJECTIVE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Objective</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OBJECTIVE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.GlobalObjectiveImpl
	 * <em>Global Objective</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GlobalObjectiveImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getGlobalObjective()
	 * @generated
	 */
	int GLOBAL_OBJECTIVE = 12;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GLOBAL_OBJECTIVE__EXPRESSION = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GLOBAL_OBJECTIVE__TARGET = 1;

	/**
	 * The number of structural features of the '<em>Global Objective</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GLOBAL_OBJECTIVE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Global Objective</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GLOBAL_OBJECTIVE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Context
	 * <em>Context</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Context
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getContext()
	 * @generated
	 */
	int CONTEXT = 13;

	/**
	 * The number of structural features of the '<em>Context</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Context</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternConstraintImpl
	 * <em>Pattern Constraint</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternConstraintImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getPatternConstraint()
	 * @generated
	 */
	int PATTERN_CONSTRAINT = 14;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_CONSTRAINT__NAME = CONTEXT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Depending</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_CONSTRAINT__DEPENDING = CONTEXT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_CONSTRAINT__EXPRESSION = CONTEXT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Constant</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_CONSTRAINT__CONSTANT = CONTEXT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Negated</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_CONSTRAINT__NEGATED = CONTEXT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Dependencies</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_CONSTRAINT__DEPENDENCIES = CONTEXT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Referenced By</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_CONSTRAINT__REFERENCED_BY = CONTEXT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Symbolic Variable</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_CONSTRAINT__SYMBOLIC_VARIABLE = CONTEXT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Helper Variables</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_CONSTRAINT__HELPER_VARIABLES = CONTEXT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Helper Constraints</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_CONSTRAINT__HELPER_CONSTRAINTS = CONTEXT_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_CONSTRAINT__PATTERN = CONTEXT_FEATURE_COUNT + 10;

	/**
	 * The number of structural features of the '<em>Pattern Constraint</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_CONSTRAINT_FEATURE_COUNT = CONTEXT_FEATURE_COUNT + 11;

	/**
	 * The number of operations of the '<em>Pattern Constraint</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_CONSTRAINT_OPERATION_COUNT = CONTEXT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.TypeConstraintImpl
	 * <em>Type Constraint</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.TypeConstraintImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getTypeConstraint()
	 * @generated
	 */
	int TYPE_CONSTRAINT = 15;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_CONSTRAINT__NAME = CONTEXT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Depending</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_CONSTRAINT__DEPENDING = CONTEXT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_CONSTRAINT__EXPRESSION = CONTEXT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Constant</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_CONSTRAINT__CONSTANT = CONTEXT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Negated</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_CONSTRAINT__NEGATED = CONTEXT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Dependencies</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_CONSTRAINT__DEPENDENCIES = CONTEXT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Referenced By</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_CONSTRAINT__REFERENCED_BY = CONTEXT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Symbolic Variable</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_CONSTRAINT__SYMBOLIC_VARIABLE = CONTEXT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Helper Variables</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_CONSTRAINT__HELPER_VARIABLES = CONTEXT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Helper Constraints</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_CONSTRAINT__HELPER_CONSTRAINTS = CONTEXT_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Model Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_CONSTRAINT__MODEL_TYPE = CONTEXT_FEATURE_COUNT + 10;

	/**
	 * The number of structural features of the '<em>Type Constraint</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_CONSTRAINT_FEATURE_COUNT = CONTEXT_FEATURE_COUNT + 11;

	/**
	 * The number of operations of the '<em>Type Constraint</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_CONSTRAINT_OPERATION_COUNT = CONTEXT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.MappingConstraintImpl
	 * <em>Mapping Constraint</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.MappingConstraintImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getMappingConstraint()
	 * @generated
	 */
	int MAPPING_CONSTRAINT = 16;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_CONSTRAINT__NAME = CONTEXT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Depending</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_CONSTRAINT__DEPENDING = CONTEXT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_CONSTRAINT__EXPRESSION = CONTEXT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Constant</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_CONSTRAINT__CONSTANT = CONTEXT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Negated</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_CONSTRAINT__NEGATED = CONTEXT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Dependencies</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_CONSTRAINT__DEPENDENCIES = CONTEXT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Referenced By</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_CONSTRAINT__REFERENCED_BY = CONTEXT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Symbolic Variable</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_CONSTRAINT__SYMBOLIC_VARIABLE = CONTEXT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Helper Variables</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_CONSTRAINT__HELPER_VARIABLES = CONTEXT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Helper Constraints</b></em>' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_CONSTRAINT__HELPER_CONSTRAINTS = CONTEXT_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_CONSTRAINT__MAPPING = CONTEXT_FEATURE_COUNT + 10;

	/**
	 * The number of structural features of the '<em>Mapping Constraint</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_CONSTRAINT_FEATURE_COUNT = CONTEXT_FEATURE_COUNT + 11;

	/**
	 * The number of operations of the '<em>Mapping Constraint</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_CONSTRAINT_OPERATION_COUNT = CONTEXT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternObjectiveImpl
	 * <em>Pattern Objective</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternObjectiveImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getPatternObjective()
	 * @generated
	 */
	int PATTERN_OBJECTIVE = 17;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_OBJECTIVE__NAME = CONTEXT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Elementwise</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_OBJECTIVE__ELEMENTWISE = CONTEXT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_OBJECTIVE__EXPRESSION = CONTEXT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_OBJECTIVE__PATTERN = CONTEXT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Pattern Objective</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_OBJECTIVE_FEATURE_COUNT = CONTEXT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Pattern Objective</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_OBJECTIVE_OPERATION_COUNT = CONTEXT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.TypeObjectiveImpl
	 * <em>Type Objective</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.TypeObjectiveImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getTypeObjective()
	 * @generated
	 */
	int TYPE_OBJECTIVE = 18;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_OBJECTIVE__NAME = CONTEXT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Elementwise</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_OBJECTIVE__ELEMENTWISE = CONTEXT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_OBJECTIVE__EXPRESSION = CONTEXT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Model Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_OBJECTIVE__MODEL_TYPE = CONTEXT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Type Objective</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_OBJECTIVE_FEATURE_COUNT = CONTEXT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Type Objective</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_OBJECTIVE_OPERATION_COUNT = CONTEXT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.MappingObjectiveImpl
	 * <em>Mapping Objective</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.MappingObjectiveImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getMappingObjective()
	 * @generated
	 */
	int MAPPING_OBJECTIVE = 19;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_OBJECTIVE__NAME = CONTEXT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Elementwise</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_OBJECTIVE__ELEMENTWISE = CONTEXT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_OBJECTIVE__EXPRESSION = CONTEXT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_OBJECTIVE__MAPPING = CONTEXT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Mapping Objective</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_OBJECTIVE_FEATURE_COUNT = CONTEXT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Mapping Objective</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_OBJECTIVE_OPERATION_COUNT = CONTEXT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ArithmeticExpressionImpl
	 * <em>Arithmetic Expression</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ArithmeticExpressionImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getArithmeticExpression()
	 * @generated
	 */
	int ARITHMETIC_EXPRESSION = 20;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_EXPRESSION__RETURN_TYPE = 0;

	/**
	 * The number of structural features of the '<em>Arithmetic Expression</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_EXPRESSION_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Arithmetic Expression</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_EXPRESSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.BinaryArithmeticExpressionImpl
	 * <em>Binary Arithmetic Expression</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.BinaryArithmeticExpressionImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getBinaryArithmeticExpression()
	 * @generated
	 */
	int BINARY_ARITHMETIC_EXPRESSION = 21;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BINARY_ARITHMETIC_EXPRESSION__RETURN_TYPE = ARITHMETIC_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BINARY_ARITHMETIC_EXPRESSION__LHS = ARITHMETIC_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BINARY_ARITHMETIC_EXPRESSION__RHS = ARITHMETIC_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BINARY_ARITHMETIC_EXPRESSION__OPERATOR = ARITHMETIC_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Binary Arithmetic
	 * Expression</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BINARY_ARITHMETIC_EXPRESSION_FEATURE_COUNT = ARITHMETIC_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Binary Arithmetic Expression</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BINARY_ARITHMETIC_EXPRESSION_OPERATION_COUNT = ARITHMETIC_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.UnaryArithmeticExpressionImpl
	 * <em>Unary Arithmetic Expression</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.UnaryArithmeticExpressionImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getUnaryArithmeticExpression()
	 * @generated
	 */
	int UNARY_ARITHMETIC_EXPRESSION = 22;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UNARY_ARITHMETIC_EXPRESSION__RETURN_TYPE = ARITHMETIC_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UNARY_ARITHMETIC_EXPRESSION__EXPRESSION = ARITHMETIC_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UNARY_ARITHMETIC_EXPRESSION__OPERATOR = ARITHMETIC_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Unary Arithmetic
	 * Expression</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UNARY_ARITHMETIC_EXPRESSION_FEATURE_COUNT = ARITHMETIC_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Unary Arithmetic Expression</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int UNARY_ARITHMETIC_EXPRESSION_OPERATION_COUNT = ARITHMETIC_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.SetOperation <em>Set
	 * Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.SetOperation
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getSetOperation()
	 * @generated
	 */
	int SET_OPERATION = 23;

	/**
	 * The feature id for the '<em><b>Operand Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SET_OPERATION__OPERAND_NAME = 0;

	/**
	 * The number of structural features of the '<em>Set Operation</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SET_OPERATION_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Set Operation</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SET_OPERATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ArithmeticValueExpressionImpl
	 * <em>Arithmetic Value Expression</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ArithmeticValueExpressionImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getArithmeticValueExpression()
	 * @generated
	 */
	int ARITHMETIC_VALUE_EXPRESSION = 24;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_VALUE_EXPRESSION__RETURN_TYPE = ARITHMETIC_EXPRESSION__RETURN_TYPE;

	/**
	 * The number of structural features of the '<em>Arithmetic Value
	 * Expression</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_VALUE_EXPRESSION_FEATURE_COUNT = ARITHMETIC_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Arithmetic Value Expression</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_VALUE_EXPRESSION_OPERATION_COUNT = ARITHMETIC_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ArithmeticValueImpl
	 * <em>Arithmetic Value</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ArithmeticValueImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getArithmeticValue()
	 * @generated
	 */
	int ARITHMETIC_VALUE = 25;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_VALUE__RETURN_TYPE = ARITHMETIC_VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_VALUE__VALUE = ARITHMETIC_VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Arithmetic Value</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_VALUE_FEATURE_COUNT = ARITHMETIC_VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Arithmetic Value</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_VALUE_OPERATION_COUNT = ARITHMETIC_VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ArithmeticLiteralImpl
	 * <em>Arithmetic Literal</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ArithmeticLiteralImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getArithmeticLiteral()
	 * @generated
	 */
	int ARITHMETIC_LITERAL = 26;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_LITERAL__RETURN_TYPE = ARITHMETIC_VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The number of structural features of the '<em>Arithmetic Literal</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_LITERAL_FEATURE_COUNT = ARITHMETIC_VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Arithmetic Literal</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_LITERAL_OPERATION_COUNT = ARITHMETIC_VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.VariableReferenceImpl
	 * <em>Variable Reference</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.VariableReferenceImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getVariableReference()
	 * @generated
	 */
	int VARIABLE_REFERENCE = 27;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_REFERENCE__RETURN_TYPE = ARITHMETIC_VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_REFERENCE__VARIABLE = ARITHMETIC_VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Variable Reference</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_REFERENCE_FEATURE_COUNT = ARITHMETIC_VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Variable Reference</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VARIABLE_REFERENCE_OPERATION_COUNT = ARITHMETIC_VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ArithmeticNullLiteralImpl
	 * <em>Arithmetic Null Literal</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ArithmeticNullLiteralImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getArithmeticNullLiteral()
	 * @generated
	 */
	int ARITHMETIC_NULL_LITERAL = 28;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_NULL_LITERAL__RETURN_TYPE = ARITHMETIC_LITERAL__RETURN_TYPE;

	/**
	 * The number of structural features of the '<em>Arithmetic Null Literal</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_NULL_LITERAL_FEATURE_COUNT = ARITHMETIC_LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Arithmetic Null Literal</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ARITHMETIC_NULL_LITERAL_OPERATION_COUNT = ARITHMETIC_LITERAL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IntegerLiteralImpl
	 * <em>Integer Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.IntegerLiteralImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getIntegerLiteral()
	 * @generated
	 */
	int INTEGER_LITERAL = 29;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INTEGER_LITERAL__RETURN_TYPE = ARITHMETIC_LITERAL__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Literal</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INTEGER_LITERAL__LITERAL = ARITHMETIC_LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Integer Literal</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INTEGER_LITERAL_FEATURE_COUNT = ARITHMETIC_LITERAL_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Integer Literal</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INTEGER_LITERAL_OPERATION_COUNT = ARITHMETIC_LITERAL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.DoubleLiteralImpl
	 * <em>Double Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.DoubleLiteralImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getDoubleLiteral()
	 * @generated
	 */
	int DOUBLE_LITERAL = 30;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LITERAL__RETURN_TYPE = ARITHMETIC_LITERAL__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Literal</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LITERAL__LITERAL = ARITHMETIC_LITERAL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Double Literal</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LITERAL_FEATURE_COUNT = ARITHMETIC_LITERAL_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Double Literal</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DOUBLE_LITERAL_OPERATION_COUNT = ARITHMETIC_LITERAL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolExpressionImpl
	 * <em>Bool Expression</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolExpressionImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getBoolExpression()
	 * @generated
	 */
	int BOOL_EXPRESSION = 31;

	/**
	 * The number of structural features of the '<em>Bool Expression</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOL_EXPRESSION_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Bool Expression</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOL_EXPRESSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolBinaryExpressionImpl
	 * <em>Bool Binary Expression</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolBinaryExpressionImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getBoolBinaryExpression()
	 * @generated
	 */
	int BOOL_BINARY_EXPRESSION = 32;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOL_BINARY_EXPRESSION__LHS = BOOL_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOL_BINARY_EXPRESSION__RHS = BOOL_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOL_BINARY_EXPRESSION__OPERATOR = BOOL_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Bool Binary Expression</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOL_BINARY_EXPRESSION_FEATURE_COUNT = BOOL_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Bool Binary Expression</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOL_BINARY_EXPRESSION_OPERATION_COUNT = BOOL_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolUnaryExpressionImpl
	 * <em>Bool Unary Expression</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolUnaryExpressionImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getBoolUnaryExpression()
	 * @generated
	 */
	int BOOL_UNARY_EXPRESSION = 33;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOL_UNARY_EXPRESSION__EXPRESSION = BOOL_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOL_UNARY_EXPRESSION__OPERATOR = BOOL_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Bool Unary Expression</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOL_UNARY_EXPRESSION_FEATURE_COUNT = BOOL_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Bool Unary Expression</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOL_UNARY_EXPRESSION_OPERATION_COUNT = BOOL_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolValueExpressionImpl
	 * <em>Bool Value Expression</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolValueExpressionImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getBoolValueExpression()
	 * @generated
	 */
	int BOOL_VALUE_EXPRESSION = 34;

	/**
	 * The number of structural features of the '<em>Bool Value Expression</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOL_VALUE_EXPRESSION_FEATURE_COUNT = BOOL_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Bool Value Expression</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOL_VALUE_EXPRESSION_OPERATION_COUNT = BOOL_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolValueImpl
	 * <em>Bool Value</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolValueImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getBoolValue()
	 * @generated
	 */
	int BOOL_VALUE = 35;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOL_VALUE__VALUE = BOOL_VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Bool Value</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOL_VALUE_FEATURE_COUNT = BOOL_VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Bool Value</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOL_VALUE_OPERATION_COUNT = BOOL_VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolStreamExpressionImpl
	 * <em>Bool Stream Expression</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolStreamExpressionImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getBoolStreamExpression()
	 * @generated
	 */
	int BOOL_STREAM_EXPRESSION = 36;

	/**
	 * The feature id for the '<em><b>Stream</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOL_STREAM_EXPRESSION__STREAM = BOOL_VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOL_STREAM_EXPRESSION__OPERATOR = BOOL_VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Bool Stream Expression</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOL_STREAM_EXPRESSION_FEATURE_COUNT = BOOL_VALUE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Bool Stream Expression</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOL_STREAM_EXPRESSION_OPERATION_COUNT = BOOL_VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.RelationalExpressionImpl
	 * <em>Relational Expression</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.RelationalExpressionImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getRelationalExpression()
	 * @generated
	 */
	int RELATIONAL_EXPRESSION = 37;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RELATIONAL_EXPRESSION__OPERATOR = BOOL_VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RELATIONAL_EXPRESSION__LHS = BOOL_VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RELATIONAL_EXPRESSION__RHS = BOOL_VALUE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Relational Expression</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RELATIONAL_EXPRESSION_FEATURE_COUNT = BOOL_VALUE_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Relational Expression</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RELATIONAL_EXPRESSION_OPERATION_COUNT = BOOL_VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolLiteralImpl
	 * <em>Bool Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolLiteralImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getBoolLiteral()
	 * @generated
	 */
	int BOOL_LITERAL = 38;

	/**
	 * The feature id for the '<em><b>Literal</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOL_LITERAL__LITERAL = BOOL_VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Bool Literal</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOL_LITERAL_FEATURE_COUNT = BOOL_VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Bool Literal</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BOOL_LITERAL_OPERATION_COUNT = BOOL_VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ValueExpressionImpl
	 * <em>Value Expression</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ValueExpressionImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getValueExpression()
	 * @generated
	 */
	int VALUE_EXPRESSION = 39;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VALUE_EXPRESSION__RETURN_TYPE = 0;

	/**
	 * The number of structural features of the '<em>Value Expression</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VALUE_EXPRESSION_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Value Expression</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VALUE_EXPRESSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.SumExpressionImpl
	 * <em>Sum Expression</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.SumExpressionImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getSumExpression()
	 * @generated
	 */
	int SUM_EXPRESSION = 40;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUM_EXPRESSION__RETURN_TYPE = VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Operand Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUM_EXPRESSION__OPERAND_NAME = VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUM_EXPRESSION__EXPRESSION = VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUM_EXPRESSION__FILTER = VALUE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Sum Expression</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUM_EXPRESSION_FEATURE_COUNT = VALUE_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Sum Expression</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUM_EXPRESSION_OPERATION_COUNT = VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextSumExpressionImpl
	 * <em>Context Sum Expression</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextSumExpressionImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getContextSumExpression()
	 * @generated
	 */
	int CONTEXT_SUM_EXPRESSION = 41;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_SUM_EXPRESSION__RETURN_TYPE = SUM_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Operand Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_SUM_EXPRESSION__OPERAND_NAME = SUM_EXPRESSION__OPERAND_NAME;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_SUM_EXPRESSION__EXPRESSION = SUM_EXPRESSION__EXPRESSION;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_SUM_EXPRESSION__FILTER = SUM_EXPRESSION__FILTER;

	/**
	 * The feature id for the '<em><b>Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_SUM_EXPRESSION__CONTEXT = SUM_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Node</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_SUM_EXPRESSION__NODE = SUM_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_SUM_EXPRESSION__FEATURE = SUM_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Context Sum Expression</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_SUM_EXPRESSION_FEATURE_COUNT = SUM_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Context Sum Expression</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_SUM_EXPRESSION_OPERATION_COUNT = SUM_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.MappingSumExpressionImpl
	 * <em>Mapping Sum Expression</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.MappingSumExpressionImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getMappingSumExpression()
	 * @generated
	 */
	int MAPPING_SUM_EXPRESSION = 42;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_SUM_EXPRESSION__RETURN_TYPE = SUM_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Operand Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_SUM_EXPRESSION__OPERAND_NAME = SUM_EXPRESSION__OPERAND_NAME;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_SUM_EXPRESSION__EXPRESSION = SUM_EXPRESSION__EXPRESSION;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_SUM_EXPRESSION__FILTER = SUM_EXPRESSION__FILTER;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_SUM_EXPRESSION__MAPPING = SUM_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Mapping Sum Expression</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_SUM_EXPRESSION_FEATURE_COUNT = SUM_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Mapping Sum Expression</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_SUM_EXPRESSION_OPERATION_COUNT = SUM_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternSumExpressionImpl
	 * <em>Pattern Sum Expression</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternSumExpressionImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getPatternSumExpression()
	 * @generated
	 */
	int PATTERN_SUM_EXPRESSION = 43;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_SUM_EXPRESSION__RETURN_TYPE = SUM_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Operand Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_SUM_EXPRESSION__OPERAND_NAME = SUM_EXPRESSION__OPERAND_NAME;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_SUM_EXPRESSION__EXPRESSION = SUM_EXPRESSION__EXPRESSION;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_SUM_EXPRESSION__FILTER = SUM_EXPRESSION__FILTER;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_SUM_EXPRESSION__PATTERN = SUM_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Pattern Sum Expression</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_SUM_EXPRESSION_FEATURE_COUNT = SUM_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Pattern Sum Expression</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PATTERN_SUM_EXPRESSION_OPERATION_COUNT = SUM_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.TypeSumExpressionImpl
	 * <em>Type Sum Expression</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.TypeSumExpressionImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getTypeSumExpression()
	 * @generated
	 */
	int TYPE_SUM_EXPRESSION = 44;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_SUM_EXPRESSION__RETURN_TYPE = SUM_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Operand Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_SUM_EXPRESSION__OPERAND_NAME = SUM_EXPRESSION__OPERAND_NAME;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_SUM_EXPRESSION__EXPRESSION = SUM_EXPRESSION__EXPRESSION;

	/**
	 * The feature id for the '<em><b>Filter</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_SUM_EXPRESSION__FILTER = SUM_EXPRESSION__FILTER;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_SUM_EXPRESSION__TYPE = SUM_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Type Sum Expression</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_SUM_EXPRESSION_FEATURE_COUNT = SUM_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Type Sum Expression</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPE_SUM_EXPRESSION_OPERATION_COUNT = SUM_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextTypeValueImpl
	 * <em>Context Type Value</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextTypeValueImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getContextTypeValue()
	 * @generated
	 */
	int CONTEXT_TYPE_VALUE = 45;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_TYPE_VALUE__RETURN_TYPE = VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Type Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_TYPE_VALUE__TYPE_CONTEXT = VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Context Type Value</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_TYPE_VALUE_FEATURE_COUNT = VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Context Type Value</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_TYPE_VALUE_OPERATION_COUNT = VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextPatternValueImpl
	 * <em>Context Pattern Value</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextPatternValueImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getContextPatternValue()
	 * @generated
	 */
	int CONTEXT_PATTERN_VALUE = 46;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_PATTERN_VALUE__RETURN_TYPE = VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Pattern Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_PATTERN_VALUE__PATTERN_CONTEXT = VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Context Pattern Value</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_PATTERN_VALUE_FEATURE_COUNT = VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Context Pattern Value</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_PATTERN_VALUE_OPERATION_COUNT = VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextPatternNodeImpl
	 * <em>Context Pattern Node</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextPatternNodeImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getContextPatternNode()
	 * @generated
	 */
	int CONTEXT_PATTERN_NODE = 47;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_PATTERN_NODE__RETURN_TYPE = VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Pattern Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_PATTERN_NODE__PATTERN_CONTEXT = VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Node</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_PATTERN_NODE__NODE = VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Context Pattern Node</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_PATTERN_NODE_FEATURE_COUNT = VALUE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Context Pattern Node</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_PATTERN_NODE_OPERATION_COUNT = VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextMappingValueImpl
	 * <em>Context Mapping Value</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextMappingValueImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getContextMappingValue()
	 * @generated
	 */
	int CONTEXT_MAPPING_VALUE = 48;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_VALUE__RETURN_TYPE = VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Mapping Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_VALUE__MAPPING_CONTEXT = VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Context Mapping Value</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_VALUE_FEATURE_COUNT = VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Context Mapping Value</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_VALUE_OPERATION_COUNT = VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextMappingNodeImpl
	 * <em>Context Mapping Node</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextMappingNodeImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getContextMappingNode()
	 * @generated
	 */
	int CONTEXT_MAPPING_NODE = 49;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_NODE__RETURN_TYPE = VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Mapping Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_NODE__MAPPING_CONTEXT = VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Node</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_NODE__NODE = VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Context Mapping Node</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_NODE_FEATURE_COUNT = VALUE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Context Mapping Node</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_NODE_OPERATION_COUNT = VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ObjectiveFunctionValueImpl
	 * <em>Objective Function Value</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ObjectiveFunctionValueImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getObjectiveFunctionValue()
	 * @generated
	 */
	int OBJECTIVE_FUNCTION_VALUE = 50;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OBJECTIVE_FUNCTION_VALUE__RETURN_TYPE = VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Objective</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OBJECTIVE_FUNCTION_VALUE__OBJECTIVE = VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Objective Function Value</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OBJECTIVE_FUNCTION_VALUE_FEATURE_COUNT = VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Objective Function Value</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OBJECTIVE_FUNCTION_VALUE_OPERATION_COUNT = VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.FeatureExpressionImpl
	 * <em>Feature Expression</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.FeatureExpressionImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getFeatureExpression()
	 * @generated
	 */
	int FEATURE_EXPRESSION = 51;

	/**
	 * The feature id for the '<em><b>Current</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FEATURE_EXPRESSION__CURRENT = 0;

	/**
	 * The feature id for the '<em><b>Child</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FEATURE_EXPRESSION__CHILD = 1;

	/**
	 * The number of structural features of the '<em>Feature Expression</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FEATURE_EXPRESSION_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Feature Expression</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FEATURE_EXPRESSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.FeatureLiteralImpl
	 * <em>Feature Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.FeatureLiteralImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getFeatureLiteral()
	 * @generated
	 */
	int FEATURE_LITERAL = 52;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FEATURE_LITERAL__FEATURE = 0;

	/**
	 * The number of structural features of the '<em>Feature Literal</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FEATURE_LITERAL_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Feature Literal</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FEATURE_LITERAL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextTypeFeatureValueImpl
	 * <em>Context Type Feature Value</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextTypeFeatureValueImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getContextTypeFeatureValue()
	 * @generated
	 */
	int CONTEXT_TYPE_FEATURE_VALUE = 53;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_TYPE_FEATURE_VALUE__RETURN_TYPE = CONTEXT_TYPE_VALUE__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Type Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_TYPE_FEATURE_VALUE__TYPE_CONTEXT = CONTEXT_TYPE_VALUE__TYPE_CONTEXT;

	/**
	 * The feature id for the '<em><b>Feature Expression</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_TYPE_FEATURE_VALUE__FEATURE_EXPRESSION = CONTEXT_TYPE_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Context Type Feature
	 * Value</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_TYPE_FEATURE_VALUE_FEATURE_COUNT = CONTEXT_TYPE_VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Context Type Feature Value</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_TYPE_FEATURE_VALUE_OPERATION_COUNT = CONTEXT_TYPE_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextPatternNodeFeatureValueImpl
	 * <em>Context Pattern Node Feature Value</em>}' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextPatternNodeFeatureValueImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getContextPatternNodeFeatureValue()
	 * @generated
	 */
	int CONTEXT_PATTERN_NODE_FEATURE_VALUE = 54;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_PATTERN_NODE_FEATURE_VALUE__RETURN_TYPE = CONTEXT_PATTERN_NODE__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Pattern Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_PATTERN_NODE_FEATURE_VALUE__PATTERN_CONTEXT = CONTEXT_PATTERN_NODE__PATTERN_CONTEXT;

	/**
	 * The feature id for the '<em><b>Node</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_PATTERN_NODE_FEATURE_VALUE__NODE = CONTEXT_PATTERN_NODE__NODE;

	/**
	 * The feature id for the '<em><b>Feature Expression</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_PATTERN_NODE_FEATURE_VALUE__FEATURE_EXPRESSION = CONTEXT_PATTERN_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Context Pattern Node Feature
	 * Value</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_PATTERN_NODE_FEATURE_VALUE_FEATURE_COUNT = CONTEXT_PATTERN_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Context Pattern Node Feature Value</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_PATTERN_NODE_FEATURE_VALUE_OPERATION_COUNT = CONTEXT_PATTERN_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextMappingNodeFeatureValueImpl
	 * <em>Context Mapping Node Feature Value</em>}' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextMappingNodeFeatureValueImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getContextMappingNodeFeatureValue()
	 * @generated
	 */
	int CONTEXT_MAPPING_NODE_FEATURE_VALUE = 55;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_NODE_FEATURE_VALUE__RETURN_TYPE = CONTEXT_MAPPING_NODE__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Mapping Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_NODE_FEATURE_VALUE__MAPPING_CONTEXT = CONTEXT_MAPPING_NODE__MAPPING_CONTEXT;

	/**
	 * The feature id for the '<em><b>Node</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_NODE_FEATURE_VALUE__NODE = CONTEXT_MAPPING_NODE__NODE;

	/**
	 * The feature id for the '<em><b>Feature Expression</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_NODE_FEATURE_VALUE__FEATURE_EXPRESSION = CONTEXT_MAPPING_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Context Mapping Node Feature
	 * Value</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_NODE_FEATURE_VALUE_FEATURE_COUNT = CONTEXT_MAPPING_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Context Mapping Node Feature Value</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONTEXT_MAPPING_NODE_FEATURE_VALUE_OPERATION_COUNT = CONTEXT_MAPPING_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorImpl
	 * <em>Iterator</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getIterator()
	 * @generated
	 */
	int ITERATOR = 56;

	/**
	 * The feature id for the '<em><b>Stream</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR__STREAM = 0;

	/**
	 * The number of structural features of the '<em>Iterator</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Iterator</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorPatternValueImpl
	 * <em>Iterator Pattern Value</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorPatternValueImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getIteratorPatternValue()
	 * @generated
	 */
	int ITERATOR_PATTERN_VALUE = 57;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_PATTERN_VALUE__RETURN_TYPE = VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Stream</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_PATTERN_VALUE__STREAM = VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Pattern Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_PATTERN_VALUE__PATTERN_CONTEXT = VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Iterator Pattern Value</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_PATTERN_VALUE_FEATURE_COUNT = VALUE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Iterator Pattern Value</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_PATTERN_VALUE_OPERATION_COUNT = VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorPatternFeatureValueImpl
	 * <em>Iterator Pattern Feature Value</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorPatternFeatureValueImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getIteratorPatternFeatureValue()
	 * @generated
	 */
	int ITERATOR_PATTERN_FEATURE_VALUE = 58;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_PATTERN_FEATURE_VALUE__RETURN_TYPE = VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Stream</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_PATTERN_FEATURE_VALUE__STREAM = VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Pattern Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_PATTERN_FEATURE_VALUE__PATTERN_CONTEXT = VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Feature Expression</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_PATTERN_FEATURE_VALUE__FEATURE_EXPRESSION = VALUE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Iterator Pattern Feature
	 * Value</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_PATTERN_FEATURE_VALUE_FEATURE_COUNT = VALUE_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Iterator Pattern Feature Value</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_PATTERN_FEATURE_VALUE_OPERATION_COUNT = VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorPatternNodeValueImpl
	 * <em>Iterator Pattern Node Value</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorPatternNodeValueImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getIteratorPatternNodeValue()
	 * @generated
	 */
	int ITERATOR_PATTERN_NODE_VALUE = 59;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_PATTERN_NODE_VALUE__RETURN_TYPE = VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Stream</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_PATTERN_NODE_VALUE__STREAM = VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Pattern Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_PATTERN_NODE_VALUE__PATTERN_CONTEXT = VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Node</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_PATTERN_NODE_VALUE__NODE = VALUE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Iterator Pattern Node
	 * Value</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_PATTERN_NODE_VALUE_FEATURE_COUNT = VALUE_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Iterator Pattern Node Value</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_PATTERN_NODE_VALUE_OPERATION_COUNT = VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorPatternNodeFeatureValueImpl
	 * <em>Iterator Pattern Node Feature Value</em>}' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorPatternNodeFeatureValueImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getIteratorPatternNodeFeatureValue()
	 * @generated
	 */
	int ITERATOR_PATTERN_NODE_FEATURE_VALUE = 60;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_PATTERN_NODE_FEATURE_VALUE__RETURN_TYPE = ITERATOR_PATTERN_NODE_VALUE__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Stream</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_PATTERN_NODE_FEATURE_VALUE__STREAM = ITERATOR_PATTERN_NODE_VALUE__STREAM;

	/**
	 * The feature id for the '<em><b>Pattern Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_PATTERN_NODE_FEATURE_VALUE__PATTERN_CONTEXT = ITERATOR_PATTERN_NODE_VALUE__PATTERN_CONTEXT;

	/**
	 * The feature id for the '<em><b>Node</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_PATTERN_NODE_FEATURE_VALUE__NODE = ITERATOR_PATTERN_NODE_VALUE__NODE;

	/**
	 * The feature id for the '<em><b>Feature Expression</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_PATTERN_NODE_FEATURE_VALUE__FEATURE_EXPRESSION = ITERATOR_PATTERN_NODE_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Iterator Pattern Node Feature
	 * Value</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_PATTERN_NODE_FEATURE_VALUE_FEATURE_COUNT = ITERATOR_PATTERN_NODE_VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Iterator Pattern Node Feature
	 * Value</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_PATTERN_NODE_FEATURE_VALUE_OPERATION_COUNT = ITERATOR_PATTERN_NODE_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorMappingValueImpl
	 * <em>Iterator Mapping Value</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorMappingValueImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getIteratorMappingValue()
	 * @generated
	 */
	int ITERATOR_MAPPING_VALUE = 61;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_VALUE__RETURN_TYPE = VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Stream</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_VALUE__STREAM = VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Mapping Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_VALUE__MAPPING_CONTEXT = VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Iterator Mapping Value</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_VALUE_FEATURE_COUNT = VALUE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Iterator Mapping Value</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_VALUE_OPERATION_COUNT = VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorMappingVariableValueImpl
	 * <em>Iterator Mapping Variable Value</em>}' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorMappingVariableValueImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getIteratorMappingVariableValue()
	 * @generated
	 */
	int ITERATOR_MAPPING_VARIABLE_VALUE = 62;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_VARIABLE_VALUE__RETURN_TYPE = VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Stream</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_VARIABLE_VALUE__STREAM = VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Mapping Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_VARIABLE_VALUE__MAPPING_CONTEXT = VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Iterator Mapping Variable
	 * Value</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_VARIABLE_VALUE_FEATURE_COUNT = VALUE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Iterator Mapping Variable Value</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_VARIABLE_VALUE_OPERATION_COUNT = VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorMappingFeatureValueImpl
	 * <em>Iterator Mapping Feature Value</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorMappingFeatureValueImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getIteratorMappingFeatureValue()
	 * @generated
	 */
	int ITERATOR_MAPPING_FEATURE_VALUE = 63;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_FEATURE_VALUE__RETURN_TYPE = VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Stream</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_FEATURE_VALUE__STREAM = VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Mapping Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_FEATURE_VALUE__MAPPING_CONTEXT = VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Feature Expression</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_FEATURE_VALUE__FEATURE_EXPRESSION = VALUE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Iterator Mapping Feature
	 * Value</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_FEATURE_VALUE_FEATURE_COUNT = VALUE_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Iterator Mapping Feature Value</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_FEATURE_VALUE_OPERATION_COUNT = VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorMappingNodeValueImpl
	 * <em>Iterator Mapping Node Value</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorMappingNodeValueImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getIteratorMappingNodeValue()
	 * @generated
	 */
	int ITERATOR_MAPPING_NODE_VALUE = 64;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_NODE_VALUE__RETURN_TYPE = VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Stream</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_NODE_VALUE__STREAM = VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Mapping Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_NODE_VALUE__MAPPING_CONTEXT = VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Node</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_NODE_VALUE__NODE = VALUE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Iterator Mapping Node
	 * Value</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_NODE_VALUE_FEATURE_COUNT = VALUE_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Iterator Mapping Node Value</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_NODE_VALUE_OPERATION_COUNT = VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorMappingNodeFeatureValueImpl
	 * <em>Iterator Mapping Node Feature Value</em>}' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorMappingNodeFeatureValueImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getIteratorMappingNodeFeatureValue()
	 * @generated
	 */
	int ITERATOR_MAPPING_NODE_FEATURE_VALUE = 65;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_NODE_FEATURE_VALUE__RETURN_TYPE = ITERATOR_MAPPING_NODE_VALUE__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Stream</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_NODE_FEATURE_VALUE__STREAM = ITERATOR_MAPPING_NODE_VALUE__STREAM;

	/**
	 * The feature id for the '<em><b>Mapping Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_NODE_FEATURE_VALUE__MAPPING_CONTEXT = ITERATOR_MAPPING_NODE_VALUE__MAPPING_CONTEXT;

	/**
	 * The feature id for the '<em><b>Node</b></em>' reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_NODE_FEATURE_VALUE__NODE = ITERATOR_MAPPING_NODE_VALUE__NODE;

	/**
	 * The feature id for the '<em><b>Feature Expression</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_NODE_FEATURE_VALUE__FEATURE_EXPRESSION = ITERATOR_MAPPING_NODE_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Iterator Mapping Node Feature
	 * Value</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_NODE_FEATURE_VALUE_FEATURE_COUNT = ITERATOR_MAPPING_NODE_VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Iterator Mapping Node Feature
	 * Value</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_MAPPING_NODE_FEATURE_VALUE_OPERATION_COUNT = ITERATOR_MAPPING_NODE_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorTypeValueImpl
	 * <em>Iterator Type Value</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorTypeValueImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getIteratorTypeValue()
	 * @generated
	 */
	int ITERATOR_TYPE_VALUE = 66;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_TYPE_VALUE__RETURN_TYPE = VALUE_EXPRESSION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Stream</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_TYPE_VALUE__STREAM = VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_TYPE_VALUE__TYPE_CONTEXT = VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Iterator Type Value</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_TYPE_VALUE_FEATURE_COUNT = VALUE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Iterator Type Value</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_TYPE_VALUE_OPERATION_COUNT = VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorTypeFeatureValueImpl
	 * <em>Iterator Type Feature Value</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorTypeFeatureValueImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getIteratorTypeFeatureValue()
	 * @generated
	 */
	int ITERATOR_TYPE_FEATURE_VALUE = 67;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_TYPE_FEATURE_VALUE__RETURN_TYPE = ITERATOR_TYPE_VALUE__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Stream</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_TYPE_FEATURE_VALUE__STREAM = ITERATOR_TYPE_VALUE__STREAM;

	/**
	 * The feature id for the '<em><b>Type Context</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_TYPE_FEATURE_VALUE__TYPE_CONTEXT = ITERATOR_TYPE_VALUE__TYPE_CONTEXT;

	/**
	 * The feature id for the '<em><b>Feature Expression</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_TYPE_FEATURE_VALUE__FEATURE_EXPRESSION = ITERATOR_TYPE_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Iterator Type Feature
	 * Value</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_TYPE_FEATURE_VALUE_FEATURE_COUNT = ITERATOR_TYPE_VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Iterator Type Feature Value</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ITERATOR_TYPE_FEATURE_VALUE_OPERATION_COUNT = ITERATOR_TYPE_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.StreamExpressionImpl
	 * <em>Stream Expression</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.StreamExpressionImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getStreamExpression()
	 * @generated
	 */
	int STREAM_EXPRESSION = 68;

	/**
	 * The feature id for the '<em><b>Operand Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STREAM_EXPRESSION__OPERAND_NAME = SET_OPERATION__OPERAND_NAME;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STREAM_EXPRESSION__RETURN_TYPE = SET_OPERATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Current</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STREAM_EXPRESSION__CURRENT = SET_OPERATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Child</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STREAM_EXPRESSION__CHILD = SET_OPERATION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Stream Expression</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STREAM_EXPRESSION_FEATURE_COUNT = SET_OPERATION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Stream Expression</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STREAM_EXPRESSION_OPERATION_COUNT = SET_OPERATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamOperation
	 * <em>Stream Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.StreamOperation
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getStreamOperation()
	 * @generated
	 */
	int STREAM_OPERATION = 69;

	/**
	 * The number of structural features of the '<em>Stream Operation</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STREAM_OPERATION_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Stream Operation</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STREAM_OPERATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.StreamNoOperationImpl
	 * <em>Stream No Operation</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.StreamNoOperationImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getStreamNoOperation()
	 * @generated
	 */
	int STREAM_NO_OPERATION = 70;

	/**
	 * The number of structural features of the '<em>Stream No Operation</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STREAM_NO_OPERATION_FEATURE_COUNT = STREAM_OPERATION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Stream No Operation</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STREAM_NO_OPERATION_OPERATION_COUNT = STREAM_OPERATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.StreamFilterOperationImpl
	 * <em>Stream Filter Operation</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.StreamFilterOperationImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getStreamFilterOperation()
	 * @generated
	 */
	int STREAM_FILTER_OPERATION = 71;

	/**
	 * The feature id for the '<em><b>Predicate</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STREAM_FILTER_OPERATION__PREDICATE = STREAM_OPERATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Stream Filter Operation</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STREAM_FILTER_OPERATION_FEATURE_COUNT = STREAM_OPERATION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Stream Filter Operation</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STREAM_FILTER_OPERATION_OPERATION_COUNT = STREAM_OPERATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.StreamSelectOperationImpl
	 * <em>Stream Select Operation</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.StreamSelectOperationImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getStreamSelectOperation()
	 * @generated
	 */
	int STREAM_SELECT_OPERATION = 72;

	/**
	 * The feature id for the '<em><b>Type</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STREAM_SELECT_OPERATION__TYPE = STREAM_OPERATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Stream Select Operation</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STREAM_SELECT_OPERATION_FEATURE_COUNT = STREAM_OPERATION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Stream Select Operation</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STREAM_SELECT_OPERATION_OPERATION_COUNT = STREAM_OPERATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.StreamContainsOperationImpl
	 * <em>Stream Contains Operation</em>}' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.StreamContainsOperationImpl
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getStreamContainsOperation()
	 * @generated
	 */
	int STREAM_CONTAINS_OPERATION = 73;

	/**
	 * The feature id for the '<em><b>Expr</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STREAM_CONTAINS_OPERATION__EXPR = STREAM_OPERATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Stream Contains Operation</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STREAM_CONTAINS_OPERATION_FEATURE_COUNT = STREAM_OPERATION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Stream Contains Operation</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STREAM_CONTAINS_OPERATION_OPERATION_COUNT = STREAM_OPERATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPSolverType <em>ILP
	 * Solver Type</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ILPSolverType
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getILPSolverType()
	 * @generated
	 */
	int ILP_SOLVER_TYPE = 74;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.VariableType
	 * <em>Variable Type</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.VariableType
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getVariableType()
	 * @generated
	 */
	int VARIABLE_TYPE = 75;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveTarget
	 * <em>Objective Target</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveTarget
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getObjectiveTarget()
	 * @generated
	 */
	int OBJECTIVE_TARGET = 76;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator
	 * <em>Relational Operator</em>}' enum. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getRelationalOperator()
	 * @generated
	 */
	int RELATIONAL_OPERATOR = 77;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticOperator
	 * <em>Binary Arithmetic Operator</em>}' enum. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticOperator
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getBinaryArithmeticOperator()
	 * @generated
	 */
	int BINARY_ARITHMETIC_OPERATOR = 78;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticOperator
	 * <em>Unary Arithmetic Operator</em>}' enum. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticOperator
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getUnaryArithmeticOperator()
	 * @generated
	 */
	int UNARY_ARITHMETIC_OPERATOR = 79;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamArithmeticOperator
	 * <em>Stream Arithmetic Operator</em>}' enum. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.StreamArithmeticOperator
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getStreamArithmeticOperator()
	 * @generated
	 */
	int STREAM_ARITHMETIC_OPERATOR = 80;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BinaryBoolOperator
	 * <em>Binary Bool Operator</em>}' enum. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BinaryBoolOperator
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getBinaryBoolOperator()
	 * @generated
	 */
	int BINARY_BOOL_OPERATOR = 81;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.UnaryBoolOperator
	 * <em>Unary Bool Operator</em>}' enum. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.UnaryBoolOperator
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getUnaryBoolOperator()
	 * @generated
	 */
	int UNARY_BOOL_OPERATOR = 82;

	/**
	 * The meta object id for the
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamBoolOperator
	 * <em>Stream Bool Operator</em>}' enum. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.StreamBoolOperator
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getStreamBoolOperator()
	 * @generated
	 */
	int STREAM_BOOL_OPERATOR = 83;

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel
	 * <em>Model</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Model</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel
	 * @generated
	 */
	EClass getGipsIntermediateModel();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel#getName
	 * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel#getName()
	 * @see #getGipsIntermediateModel()
	 * @generated
	 */
	EAttribute getGipsIntermediateModel_Name();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel#getVariables
	 * <em>Variables</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Variables</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel#getVariables()
	 * @see #getGipsIntermediateModel()
	 * @generated
	 */
	EReference getGipsIntermediateModel_Variables();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel#getConstraints
	 * <em>Constraints</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Constraints</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel#getConstraints()
	 * @see #getGipsIntermediateModel()
	 * @generated
	 */
	EReference getGipsIntermediateModel_Constraints();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel#getObjectives
	 * <em>Objectives</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Objectives</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel#getObjectives()
	 * @see #getGipsIntermediateModel()
	 * @generated
	 */
	EReference getGipsIntermediateModel_Objectives();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel#getGlobalObjective
	 * <em>Global Objective</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Global
	 *         Objective</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel#getGlobalObjective()
	 * @see #getGipsIntermediateModel()
	 * @generated
	 */
	EReference getGipsIntermediateModel_GlobalObjective();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel#getIbexModel
	 * <em>Ibex Model</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Ibex Model</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel#getIbexModel()
	 * @see #getGipsIntermediateModel()
	 * @generated
	 */
	EReference getGipsIntermediateModel_IbexModel();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel#getConfig
	 * <em>Config</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Config</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel#getConfig()
	 * @see #getGipsIntermediateModel()
	 * @generated
	 */
	EReference getGipsIntermediateModel_Config();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig <em>ILP
	 * Config</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>ILP Config</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig
	 * @generated
	 */
	EClass getILPConfig();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getSolver
	 * <em>Solver</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Solver</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getSolver()
	 * @see #getILPConfig()
	 * @generated
	 */
	EAttribute getILPConfig_Solver();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getSolverHomeDir
	 * <em>Solver Home Dir</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Solver Home Dir</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getSolverHomeDir()
	 * @see #getILPConfig()
	 * @generated
	 */
	EAttribute getILPConfig_SolverHomeDir();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getSolverLicenseFile
	 * <em>Solver License File</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Solver License File</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getSolverLicenseFile()
	 * @see #getILPConfig()
	 * @generated
	 */
	EAttribute getILPConfig_SolverLicenseFile();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isBuildLaunchConfig
	 * <em>Build Launch Config</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Build Launch Config</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isBuildLaunchConfig()
	 * @see #getILPConfig()
	 * @generated
	 */
	EAttribute getILPConfig_BuildLaunchConfig();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getMainFile
	 * <em>Main File</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Main File</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getMainFile()
	 * @see #getILPConfig()
	 * @generated
	 */
	EAttribute getILPConfig_MainFile();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isEnableTimeLimit
	 * <em>Enable Time Limit</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Enable Time Limit</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isEnableTimeLimit()
	 * @see #getILPConfig()
	 * @generated
	 */
	EAttribute getILPConfig_EnableTimeLimit();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getIlpTimeLimit
	 * <em>Ilp Time Limit</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Ilp Time Limit</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getIlpTimeLimit()
	 * @see #getILPConfig()
	 * @generated
	 */
	EAttribute getILPConfig_IlpTimeLimit();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isEnableRndSeed
	 * <em>Enable Rnd Seed</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Enable Rnd Seed</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isEnableRndSeed()
	 * @see #getILPConfig()
	 * @generated
	 */
	EAttribute getILPConfig_EnableRndSeed();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getIlpRndSeed
	 * <em>Ilp Rnd Seed</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Ilp Rnd Seed</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getIlpRndSeed()
	 * @see #getILPConfig()
	 * @generated
	 */
	EAttribute getILPConfig_IlpRndSeed();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isPresolve
	 * <em>Presolve</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Presolve</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isPresolve()
	 * @see #getILPConfig()
	 * @generated
	 */
	EAttribute getILPConfig_Presolve();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isEnablePresolve
	 * <em>Enable Presolve</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Enable Presolve</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isEnablePresolve()
	 * @see #getILPConfig()
	 * @generated
	 */
	EAttribute getILPConfig_EnablePresolve();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isEnableDebugOutput
	 * <em>Enable Debug Output</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Enable Debug Output</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isEnableDebugOutput()
	 * @see #getILPConfig()
	 * @generated
	 */
	EAttribute getILPConfig_EnableDebugOutput();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isEnableCustomTolerance
	 * <em>Enable Custom Tolerance</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the attribute '<em>Enable Custom Tolerance</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isEnableCustomTolerance()
	 * @see #getILPConfig()
	 * @generated
	 */
	EAttribute getILPConfig_EnableCustomTolerance();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getTolerance
	 * <em>Tolerance</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Tolerance</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getTolerance()
	 * @see #getILPConfig()
	 * @generated
	 */
	EAttribute getILPConfig_Tolerance();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isEnableLpOutput
	 * <em>Enable Lp Output</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Enable Lp Output</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#isEnableLpOutput()
	 * @see #getILPConfig()
	 * @generated
	 */
	EAttribute getILPConfig_EnableLpOutput();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getLpPath
	 * <em>Lp Path</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Lp Path</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig#getLpPath()
	 * @see #getILPConfig()
	 * @generated
	 */
	EAttribute getILPConfig_LpPath();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.VariableSet
	 * <em>Variable Set</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Variable Set</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.VariableSet
	 * @generated
	 */
	EClass getVariableSet();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.VariableSet#getName
	 * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.VariableSet#getName()
	 * @see #getVariableSet()
	 * @generated
	 */
	EAttribute getVariableSet_Name();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.VariableSet#getUpperBound
	 * <em>Upper Bound</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Upper Bound</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.VariableSet#getUpperBound()
	 * @see #getVariableSet()
	 * @generated
	 */
	EAttribute getVariableSet_UpperBound();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.VariableSet#getLowerBound
	 * <em>Lower Bound</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Lower Bound</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.VariableSet#getLowerBound()
	 * @see #getVariableSet()
	 * @generated
	 */
	EAttribute getVariableSet_LowerBound();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Pattern
	 * <em>Pattern</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Pattern</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Pattern
	 * @generated
	 */
	EClass getPattern();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Pattern#getPattern
	 * <em>Pattern</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Pattern</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Pattern#getPattern()
	 * @see #getPattern()
	 * @generated
	 */
	EReference getPattern_Pattern();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Pattern#isIsRule
	 * <em>Is Rule</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Is Rule</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Pattern#isIsRule()
	 * @see #getPattern()
	 * @generated
	 */
	EAttribute getPattern_IsRule();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Type <em>Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Type</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Type
	 * @generated
	 */
	EClass getType();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Type#getType
	 * <em>Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Type#getType()
	 * @see #getType()
	 * @generated
	 */
	EReference getType_Type();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Mapping
	 * <em>Mapping</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Mapping</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Mapping
	 * @generated
	 */
	EClass getMapping();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Mapping#getContextPattern
	 * <em>Context Pattern</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Context Pattern</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Mapping#getContextPattern()
	 * @see #getMapping()
	 * @generated
	 */
	EReference getMapping_ContextPattern();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.GTMapping <em>GT
	 * Mapping</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>GT Mapping</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GTMapping
	 * @generated
	 */
	EClass getGTMapping();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.GTMapping#getRule
	 * <em>Rule</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Rule</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GTMapping#getRule()
	 * @see #getGTMapping()
	 * @generated
	 */
	EReference getGTMapping_Rule();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.PatternMapping
	 * <em>Pattern Mapping</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Pattern Mapping</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.PatternMapping
	 * @generated
	 */
	EClass getPatternMapping();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.PatternMapping#getPattern
	 * <em>Pattern</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Pattern</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.PatternMapping#getPattern()
	 * @see #getPatternMapping()
	 * @generated
	 */
	EReference getPatternMapping_Pattern();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Variable
	 * <em>Variable</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Variable</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Variable
	 * @generated
	 */
	EClass getVariable();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Variable#getType
	 * <em>Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Variable#getType()
	 * @see #getVariable()
	 * @generated
	 */
	EAttribute getVariable_Type();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint
	 * <em>Constraint</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Constraint</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Constraint
	 * @generated
	 */
	EClass getConstraint();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getName
	 * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getName()
	 * @see #getConstraint()
	 * @generated
	 */
	EAttribute getConstraint_Name();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#isDepending
	 * <em>Depending</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Depending</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Constraint#isDepending()
	 * @see #getConstraint()
	 * @generated
	 */
	EAttribute getConstraint_Depending();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getExpression
	 * <em>Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getExpression()
	 * @see #getConstraint()
	 * @generated
	 */
	EReference getConstraint_Expression();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#isConstant
	 * <em>Constant</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Constant</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Constraint#isConstant()
	 * @see #getConstraint()
	 * @generated
	 */
	EAttribute getConstraint_Constant();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#isNegated
	 * <em>Negated</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Negated</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Constraint#isNegated()
	 * @see #getConstraint()
	 * @generated
	 */
	EAttribute getConstraint_Negated();

	/**
	 * Returns the meta object for the reference list
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getDependencies
	 * <em>Dependencies</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Dependencies</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getDependencies()
	 * @see #getConstraint()
	 * @generated
	 */
	EReference getConstraint_Dependencies();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getReferencedBy
	 * <em>Referenced By</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Referenced By</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getReferencedBy()
	 * @see #getConstraint()
	 * @generated
	 */
	EReference getConstraint_ReferencedBy();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getSymbolicVariable
	 * <em>Symbolic Variable</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Symbolic Variable</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getSymbolicVariable()
	 * @see #getConstraint()
	 * @generated
	 */
	EReference getConstraint_SymbolicVariable();

	/**
	 * Returns the meta object for the reference list
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getHelperVariables
	 * <em>Helper Variables</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Helper Variables</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getHelperVariables()
	 * @see #getConstraint()
	 * @generated
	 */
	EReference getConstraint_HelperVariables();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getHelperConstraints
	 * <em>Helper Constraints</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Helper
	 *         Constraints</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Constraint#getHelperConstraints()
	 * @see #getConstraint()
	 * @generated
	 */
	EReference getConstraint_HelperConstraints();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.GlobalConstraint
	 * <em>Global Constraint</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Global Constraint</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GlobalConstraint
	 * @generated
	 */
	EClass getGlobalConstraint();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Objective
	 * <em>Objective</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Objective</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Objective
	 * @generated
	 */
	EClass getObjective();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Objective#getName
	 * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Objective#getName()
	 * @see #getObjective()
	 * @generated
	 */
	EAttribute getObjective_Name();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Objective#isElementwise
	 * <em>Elementwise</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Elementwise</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Objective#isElementwise()
	 * @see #getObjective()
	 * @generated
	 */
	EAttribute getObjective_Elementwise();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Objective#getExpression
	 * <em>Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Objective#getExpression()
	 * @see #getObjective()
	 * @generated
	 */
	EReference getObjective_Expression();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.GlobalObjective
	 * <em>Global Objective</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Global Objective</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GlobalObjective
	 * @generated
	 */
	EClass getGlobalObjective();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.GlobalObjective#getExpression
	 * <em>Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GlobalObjective#getExpression()
	 * @see #getGlobalObjective()
	 * @generated
	 */
	EReference getGlobalObjective_Expression();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.GlobalObjective#getTarget
	 * <em>Target</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Target</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GlobalObjective#getTarget()
	 * @see #getGlobalObjective()
	 * @generated
	 */
	EAttribute getGlobalObjective_Target();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Context
	 * <em>Context</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Context</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Context
	 * @generated
	 */
	EClass getContext();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint
	 * <em>Pattern Constraint</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Pattern Constraint</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint
	 * @generated
	 */
	EClass getPatternConstraint();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint#getPattern
	 * <em>Pattern</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Pattern</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint#getPattern()
	 * @see #getPatternConstraint()
	 * @generated
	 */
	EReference getPatternConstraint_Pattern();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.TypeConstraint
	 * <em>Type Constraint</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Type Constraint</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.TypeConstraint
	 * @generated
	 */
	EClass getTypeConstraint();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.TypeConstraint#getModelType
	 * <em>Model Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Model Type</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.TypeConstraint#getModelType()
	 * @see #getTypeConstraint()
	 * @generated
	 */
	EReference getTypeConstraint_ModelType();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint
	 * <em>Mapping Constraint</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Mapping Constraint</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint
	 * @generated
	 */
	EClass getMappingConstraint();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint#getMapping
	 * <em>Mapping</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Mapping</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint#getMapping()
	 * @see #getMappingConstraint()
	 * @generated
	 */
	EReference getMappingConstraint_Mapping();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.PatternObjective
	 * <em>Pattern Objective</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Pattern Objective</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.PatternObjective
	 * @generated
	 */
	EClass getPatternObjective();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.PatternObjective#getPattern
	 * <em>Pattern</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Pattern</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.PatternObjective#getPattern()
	 * @see #getPatternObjective()
	 * @generated
	 */
	EReference getPatternObjective_Pattern();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.TypeObjective <em>Type
	 * Objective</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Type Objective</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.TypeObjective
	 * @generated
	 */
	EClass getTypeObjective();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.TypeObjective#getModelType
	 * <em>Model Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Model Type</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.TypeObjective#getModelType()
	 * @see #getTypeObjective()
	 * @generated
	 */
	EReference getTypeObjective_ModelType();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.MappingObjective
	 * <em>Mapping Objective</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Mapping Objective</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.MappingObjective
	 * @generated
	 */
	EClass getMappingObjective();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.MappingObjective#getMapping
	 * <em>Mapping</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Mapping</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.MappingObjective#getMapping()
	 * @see #getMappingObjective()
	 * @generated
	 */
	EReference getMappingObjective_Mapping();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression
	 * <em>Arithmetic Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for class '<em>Arithmetic Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression
	 * @generated
	 */
	EClass getArithmeticExpression();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression#getReturnType
	 * <em>Return Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Return Type</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression#getReturnType()
	 * @see #getArithmeticExpression()
	 * @generated
	 */
	EReference getArithmeticExpression_ReturnType();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression
	 * <em>Binary Arithmetic Expression</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Binary Arithmetic Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression
	 * @generated
	 */
	EClass getBinaryArithmeticExpression();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression#getLhs
	 * <em>Lhs</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Lhs</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression#getLhs()
	 * @see #getBinaryArithmeticExpression()
	 * @generated
	 */
	EReference getBinaryArithmeticExpression_Lhs();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression#getRhs
	 * <em>Rhs</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Rhs</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression#getRhs()
	 * @see #getBinaryArithmeticExpression()
	 * @generated
	 */
	EReference getBinaryArithmeticExpression_Rhs();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression#getOperator
	 * <em>Operator</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression#getOperator()
	 * @see #getBinaryArithmeticExpression()
	 * @generated
	 */
	EAttribute getBinaryArithmeticExpression_Operator();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticExpression
	 * <em>Unary Arithmetic Expression</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Unary Arithmetic Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticExpression
	 * @generated
	 */
	EClass getUnaryArithmeticExpression();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticExpression#getExpression
	 * <em>Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticExpression#getExpression()
	 * @see #getUnaryArithmeticExpression()
	 * @generated
	 */
	EReference getUnaryArithmeticExpression_Expression();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticExpression#getOperator
	 * <em>Operator</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticExpression#getOperator()
	 * @see #getUnaryArithmeticExpression()
	 * @generated
	 */
	EAttribute getUnaryArithmeticExpression_Operator();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.SetOperation <em>Set
	 * Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Set Operation</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.SetOperation
	 * @generated
	 */
	EClass getSetOperation();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.SetOperation#getOperandName
	 * <em>Operand Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Operand Name</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.SetOperation#getOperandName()
	 * @see #getSetOperation()
	 * @generated
	 */
	EAttribute getSetOperation_OperandName();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticValueExpression
	 * <em>Arithmetic Value Expression</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Arithmetic Value Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticValueExpression
	 * @generated
	 */
	EClass getArithmeticValueExpression();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticValue
	 * <em>Arithmetic Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Arithmetic Value</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticValue
	 * @generated
	 */
	EClass getArithmeticValue();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticValue#getValue
	 * <em>Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticValue#getValue()
	 * @see #getArithmeticValue()
	 * @generated
	 */
	EReference getArithmeticValue_Value();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticLiteral
	 * <em>Arithmetic Literal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Arithmetic Literal</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticLiteral
	 * @generated
	 */
	EClass getArithmeticLiteral();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.VariableReference
	 * <em>Variable Reference</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Variable Reference</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.VariableReference
	 * @generated
	 */
	EClass getVariableReference();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.VariableReference#getVariable
	 * <em>Variable</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Variable</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.VariableReference#getVariable()
	 * @see #getVariableReference()
	 * @generated
	 */
	EReference getVariableReference_Variable();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticNullLiteral
	 * <em>Arithmetic Null Literal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for class '<em>Arithmetic Null Literal</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticNullLiteral
	 * @generated
	 */
	EClass getArithmeticNullLiteral();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IntegerLiteral
	 * <em>Integer Literal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Integer Literal</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IntegerLiteral
	 * @generated
	 */
	EClass getIntegerLiteral();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IntegerLiteral#getLiteral
	 * <em>Literal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Literal</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IntegerLiteral#getLiteral()
	 * @see #getIntegerLiteral()
	 * @generated
	 */
	EAttribute getIntegerLiteral_Literal();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.DoubleLiteral
	 * <em>Double Literal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Double Literal</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.DoubleLiteral
	 * @generated
	 */
	EClass getDoubleLiteral();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.DoubleLiteral#getLiteral
	 * <em>Literal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Literal</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.DoubleLiteral#getLiteral()
	 * @see #getDoubleLiteral()
	 * @generated
	 */
	EAttribute getDoubleLiteral_Literal();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolExpression
	 * <em>Bool Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Bool Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BoolExpression
	 * @generated
	 */
	EClass getBoolExpression();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolBinaryExpression
	 * <em>Bool Binary Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for class '<em>Bool Binary Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BoolBinaryExpression
	 * @generated
	 */
	EClass getBoolBinaryExpression();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolBinaryExpression#getLhs
	 * <em>Lhs</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Lhs</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BoolBinaryExpression#getLhs()
	 * @see #getBoolBinaryExpression()
	 * @generated
	 */
	EReference getBoolBinaryExpression_Lhs();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolBinaryExpression#getRhs
	 * <em>Rhs</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Rhs</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BoolBinaryExpression#getRhs()
	 * @see #getBoolBinaryExpression()
	 * @generated
	 */
	EReference getBoolBinaryExpression_Rhs();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolBinaryExpression#getOperator
	 * <em>Operator</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BoolBinaryExpression#getOperator()
	 * @see #getBoolBinaryExpression()
	 * @generated
	 */
	EAttribute getBoolBinaryExpression_Operator();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolUnaryExpression
	 * <em>Bool Unary Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for class '<em>Bool Unary Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BoolUnaryExpression
	 * @generated
	 */
	EClass getBoolUnaryExpression();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolUnaryExpression#getExpression
	 * <em>Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BoolUnaryExpression#getExpression()
	 * @see #getBoolUnaryExpression()
	 * @generated
	 */
	EReference getBoolUnaryExpression_Expression();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolUnaryExpression#getOperator
	 * <em>Operator</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BoolUnaryExpression#getOperator()
	 * @see #getBoolUnaryExpression()
	 * @generated
	 */
	EAttribute getBoolUnaryExpression_Operator();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolValueExpression
	 * <em>Bool Value Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for class '<em>Bool Value Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BoolValueExpression
	 * @generated
	 */
	EClass getBoolValueExpression();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolValue <em>Bool
	 * Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Bool Value</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BoolValue
	 * @generated
	 */
	EClass getBoolValue();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolValue#getValue
	 * <em>Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BoolValue#getValue()
	 * @see #getBoolValue()
	 * @generated
	 */
	EReference getBoolValue_Value();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolStreamExpression
	 * <em>Bool Stream Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for class '<em>Bool Stream Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BoolStreamExpression
	 * @generated
	 */
	EClass getBoolStreamExpression();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolStreamExpression#getStream
	 * <em>Stream</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Stream</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BoolStreamExpression#getStream()
	 * @see #getBoolStreamExpression()
	 * @generated
	 */
	EReference getBoolStreamExpression_Stream();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolStreamExpression#getOperator
	 * <em>Operator</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BoolStreamExpression#getOperator()
	 * @see #getBoolStreamExpression()
	 * @generated
	 */
	EAttribute getBoolStreamExpression_Operator();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression
	 * <em>Relational Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for class '<em>Relational Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression
	 * @generated
	 */
	EClass getRelationalExpression();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression#getOperator
	 * <em>Operator</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression#getOperator()
	 * @see #getRelationalExpression()
	 * @generated
	 */
	EAttribute getRelationalExpression_Operator();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression#getLhs
	 * <em>Lhs</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Lhs</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression#getLhs()
	 * @see #getRelationalExpression()
	 * @generated
	 */
	EReference getRelationalExpression_Lhs();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression#getRhs
	 * <em>Rhs</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Rhs</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression#getRhs()
	 * @see #getRelationalExpression()
	 * @generated
	 */
	EReference getRelationalExpression_Rhs();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolLiteral <em>Bool
	 * Literal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Bool Literal</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BoolLiteral
	 * @generated
	 */
	EClass getBoolLiteral();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolLiteral#isLiteral
	 * <em>Literal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Literal</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BoolLiteral#isLiteral()
	 * @see #getBoolLiteral()
	 * @generated
	 */
	EAttribute getBoolLiteral_Literal();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression
	 * <em>Value Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Value Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression
	 * @generated
	 */
	EClass getValueExpression();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression#getReturnType
	 * <em>Return Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Return Type</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression#getReturnType()
	 * @see #getValueExpression()
	 * @generated
	 */
	EReference getValueExpression_ReturnType();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.SumExpression <em>Sum
	 * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Sum Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.SumExpression
	 * @generated
	 */
	EClass getSumExpression();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.SumExpression#getExpression
	 * <em>Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.SumExpression#getExpression()
	 * @see #getSumExpression()
	 * @generated
	 */
	EReference getSumExpression_Expression();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.SumExpression#getFilter
	 * <em>Filter</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Filter</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.SumExpression#getFilter()
	 * @see #getSumExpression()
	 * @generated
	 */
	EReference getSumExpression_Filter();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextSumExpression
	 * <em>Context Sum Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for class '<em>Context Sum Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextSumExpression
	 * @generated
	 */
	EClass getContextSumExpression();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextSumExpression#getContext
	 * <em>Context</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Context</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextSumExpression#getContext()
	 * @see #getContextSumExpression()
	 * @generated
	 */
	EReference getContextSumExpression_Context();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextSumExpression#getNode
	 * <em>Node</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Node</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextSumExpression#getNode()
	 * @see #getContextSumExpression()
	 * @generated
	 */
	EReference getContextSumExpression_Node();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextSumExpression#getFeature
	 * <em>Feature</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Feature</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextSumExpression#getFeature()
	 * @see #getContextSumExpression()
	 * @generated
	 */
	EReference getContextSumExpression_Feature();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.MappingSumExpression
	 * <em>Mapping Sum Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for class '<em>Mapping Sum Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.MappingSumExpression
	 * @generated
	 */
	EClass getMappingSumExpression();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.MappingSumExpression#getMapping
	 * <em>Mapping</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Mapping</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.MappingSumExpression#getMapping()
	 * @see #getMappingSumExpression()
	 * @generated
	 */
	EReference getMappingSumExpression_Mapping();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.PatternSumExpression
	 * <em>Pattern Sum Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for class '<em>Pattern Sum Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.PatternSumExpression
	 * @generated
	 */
	EClass getPatternSumExpression();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.PatternSumExpression#getPattern
	 * <em>Pattern</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Pattern</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.PatternSumExpression#getPattern()
	 * @see #getPatternSumExpression()
	 * @generated
	 */
	EReference getPatternSumExpression_Pattern();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.TypeSumExpression
	 * <em>Type Sum Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Type Sum Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.TypeSumExpression
	 * @generated
	 */
	EClass getTypeSumExpression();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.TypeSumExpression#getType
	 * <em>Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.TypeSumExpression#getType()
	 * @see #getTypeSumExpression()
	 * @generated
	 */
	EReference getTypeSumExpression_Type();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeValue
	 * <em>Context Type Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Context Type Value</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeValue
	 * @generated
	 */
	EClass getContextTypeValue();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeValue#getTypeContext
	 * <em>Type Context</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Type Context</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeValue#getTypeContext()
	 * @see #getContextTypeValue()
	 * @generated
	 */
	EReference getContextTypeValue_TypeContext();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternValue
	 * <em>Context Pattern Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for class '<em>Context Pattern Value</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternValue
	 * @generated
	 */
	EClass getContextPatternValue();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternValue#getPatternContext
	 * <em>Pattern Context</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Pattern Context</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternValue#getPatternContext()
	 * @see #getContextPatternValue()
	 * @generated
	 */
	EReference getContextPatternValue_PatternContext();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNode
	 * <em>Context Pattern Node</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for class '<em>Context Pattern Node</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNode
	 * @generated
	 */
	EClass getContextPatternNode();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNode#getPatternContext
	 * <em>Pattern Context</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Pattern Context</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNode#getPatternContext()
	 * @see #getContextPatternNode()
	 * @generated
	 */
	EReference getContextPatternNode_PatternContext();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNode#getNode
	 * <em>Node</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Node</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNode#getNode()
	 * @see #getContextPatternNode()
	 * @generated
	 */
	EReference getContextPatternNode_Node();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingValue
	 * <em>Context Mapping Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for class '<em>Context Mapping Value</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingValue
	 * @generated
	 */
	EClass getContextMappingValue();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingValue#getMappingContext
	 * <em>Mapping Context</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Mapping Context</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingValue#getMappingContext()
	 * @see #getContextMappingValue()
	 * @generated
	 */
	EReference getContextMappingValue_MappingContext();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNode
	 * <em>Context Mapping Node</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for class '<em>Context Mapping Node</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNode
	 * @generated
	 */
	EClass getContextMappingNode();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNode#getMappingContext
	 * <em>Mapping Context</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Mapping Context</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNode#getMappingContext()
	 * @see #getContextMappingNode()
	 * @generated
	 */
	EReference getContextMappingNode_MappingContext();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNode#getNode
	 * <em>Node</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Node</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNode#getNode()
	 * @see #getContextMappingNode()
	 * @generated
	 */
	EReference getContextMappingNode_Node();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveFunctionValue
	 * <em>Objective Function Value</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Objective Function Value</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveFunctionValue
	 * @generated
	 */
	EClass getObjectiveFunctionValue();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveFunctionValue#getObjective
	 * <em>Objective</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Objective</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveFunctionValue#getObjective()
	 * @see #getObjectiveFunctionValue()
	 * @generated
	 */
	EReference getObjectiveFunctionValue_Objective();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.FeatureExpression
	 * <em>Feature Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Feature Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.FeatureExpression
	 * @generated
	 */
	EClass getFeatureExpression();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.FeatureExpression#getCurrent
	 * <em>Current</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Current</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.FeatureExpression#getCurrent()
	 * @see #getFeatureExpression()
	 * @generated
	 */
	EReference getFeatureExpression_Current();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.FeatureExpression#getChild
	 * <em>Child</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Child</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.FeatureExpression#getChild()
	 * @see #getFeatureExpression()
	 * @generated
	 */
	EReference getFeatureExpression_Child();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.FeatureLiteral
	 * <em>Feature Literal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Feature Literal</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.FeatureLiteral
	 * @generated
	 */
	EClass getFeatureLiteral();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.FeatureLiteral#getFeature
	 * <em>Feature</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Feature</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.FeatureLiteral#getFeature()
	 * @see #getFeatureLiteral()
	 * @generated
	 */
	EReference getFeatureLiteral_Feature();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeFeatureValue
	 * <em>Context Type Feature Value</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Context Type Feature Value</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeFeatureValue
	 * @generated
	 */
	EClass getContextTypeFeatureValue();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeFeatureValue#getFeatureExpression
	 * <em>Feature Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Feature
	 *         Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeFeatureValue#getFeatureExpression()
	 * @see #getContextTypeFeatureValue()
	 * @generated
	 */
	EReference getContextTypeFeatureValue_FeatureExpression();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNodeFeatureValue
	 * <em>Context Pattern Node Feature Value</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Context Pattern Node Feature
	 *         Value</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNodeFeatureValue
	 * @generated
	 */
	EClass getContextPatternNodeFeatureValue();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNodeFeatureValue#getFeatureExpression
	 * <em>Feature Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Feature
	 *         Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNodeFeatureValue#getFeatureExpression()
	 * @see #getContextPatternNodeFeatureValue()
	 * @generated
	 */
	EReference getContextPatternNodeFeatureValue_FeatureExpression();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNodeFeatureValue
	 * <em>Context Mapping Node Feature Value</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Context Mapping Node Feature
	 *         Value</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNodeFeatureValue
	 * @generated
	 */
	EClass getContextMappingNodeFeatureValue();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNodeFeatureValue#getFeatureExpression
	 * <em>Feature Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Feature
	 *         Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNodeFeatureValue#getFeatureExpression()
	 * @see #getContextMappingNodeFeatureValue()
	 * @generated
	 */
	EReference getContextMappingNodeFeatureValue_FeatureExpression();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Iterator
	 * <em>Iterator</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Iterator</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Iterator
	 * @generated
	 */
	EClass getIterator();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Iterator#getStream
	 * <em>Stream</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Stream</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Iterator#getStream()
	 * @see #getIterator()
	 * @generated
	 */
	EReference getIterator_Stream();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternValue
	 * <em>Iterator Pattern Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for class '<em>Iterator Pattern Value</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternValue
	 * @generated
	 */
	EClass getIteratorPatternValue();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternValue#getPatternContext
	 * <em>Pattern Context</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Pattern Context</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternValue#getPatternContext()
	 * @see #getIteratorPatternValue()
	 * @generated
	 */
	EReference getIteratorPatternValue_PatternContext();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternFeatureValue
	 * <em>Iterator Pattern Feature Value</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Iterator Pattern Feature Value</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternFeatureValue
	 * @generated
	 */
	EClass getIteratorPatternFeatureValue();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternFeatureValue#getPatternContext
	 * <em>Pattern Context</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Pattern Context</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternFeatureValue#getPatternContext()
	 * @see #getIteratorPatternFeatureValue()
	 * @generated
	 */
	EReference getIteratorPatternFeatureValue_PatternContext();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternFeatureValue#getFeatureExpression
	 * <em>Feature Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Feature
	 *         Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternFeatureValue#getFeatureExpression()
	 * @see #getIteratorPatternFeatureValue()
	 * @generated
	 */
	EReference getIteratorPatternFeatureValue_FeatureExpression();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeValue
	 * <em>Iterator Pattern Node Value</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Iterator Pattern Node Value</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeValue
	 * @generated
	 */
	EClass getIteratorPatternNodeValue();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeValue#getPatternContext
	 * <em>Pattern Context</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Pattern Context</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeValue#getPatternContext()
	 * @see #getIteratorPatternNodeValue()
	 * @generated
	 */
	EReference getIteratorPatternNodeValue_PatternContext();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeValue#getNode
	 * <em>Node</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Node</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeValue#getNode()
	 * @see #getIteratorPatternNodeValue()
	 * @generated
	 */
	EReference getIteratorPatternNodeValue_Node();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeFeatureValue
	 * <em>Iterator Pattern Node Feature Value</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Iterator Pattern Node Feature
	 *         Value</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeFeatureValue
	 * @generated
	 */
	EClass getIteratorPatternNodeFeatureValue();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeFeatureValue#getFeatureExpression
	 * <em>Feature Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Feature
	 *         Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeFeatureValue#getFeatureExpression()
	 * @see #getIteratorPatternNodeFeatureValue()
	 * @generated
	 */
	EReference getIteratorPatternNodeFeatureValue_FeatureExpression();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingValue
	 * <em>Iterator Mapping Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for class '<em>Iterator Mapping Value</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingValue
	 * @generated
	 */
	EClass getIteratorMappingValue();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingValue#getMappingContext
	 * <em>Mapping Context</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Mapping Context</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingValue#getMappingContext()
	 * @see #getIteratorMappingValue()
	 * @generated
	 */
	EReference getIteratorMappingValue_MappingContext();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingVariableValue
	 * <em>Iterator Mapping Variable Value</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Iterator Mapping Variable Value</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingVariableValue
	 * @generated
	 */
	EClass getIteratorMappingVariableValue();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingVariableValue#getMappingContext
	 * <em>Mapping Context</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Mapping Context</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingVariableValue#getMappingContext()
	 * @see #getIteratorMappingVariableValue()
	 * @generated
	 */
	EReference getIteratorMappingVariableValue_MappingContext();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingFeatureValue
	 * <em>Iterator Mapping Feature Value</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Iterator Mapping Feature Value</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingFeatureValue
	 * @generated
	 */
	EClass getIteratorMappingFeatureValue();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingFeatureValue#getMappingContext
	 * <em>Mapping Context</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Mapping Context</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingFeatureValue#getMappingContext()
	 * @see #getIteratorMappingFeatureValue()
	 * @generated
	 */
	EReference getIteratorMappingFeatureValue_MappingContext();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingFeatureValue#getFeatureExpression
	 * <em>Feature Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Feature
	 *         Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingFeatureValue#getFeatureExpression()
	 * @see #getIteratorMappingFeatureValue()
	 * @generated
	 */
	EReference getIteratorMappingFeatureValue_FeatureExpression();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeValue
	 * <em>Iterator Mapping Node Value</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Iterator Mapping Node Value</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeValue
	 * @generated
	 */
	EClass getIteratorMappingNodeValue();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeValue#getMappingContext
	 * <em>Mapping Context</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Mapping Context</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeValue#getMappingContext()
	 * @see #getIteratorMappingNodeValue()
	 * @generated
	 */
	EReference getIteratorMappingNodeValue_MappingContext();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeValue#getNode
	 * <em>Node</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Node</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeValue#getNode()
	 * @see #getIteratorMappingNodeValue()
	 * @generated
	 */
	EReference getIteratorMappingNodeValue_Node();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeFeatureValue
	 * <em>Iterator Mapping Node Feature Value</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Iterator Mapping Node Feature
	 *         Value</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeFeatureValue
	 * @generated
	 */
	EClass getIteratorMappingNodeFeatureValue();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeFeatureValue#getFeatureExpression
	 * <em>Feature Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Feature
	 *         Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeFeatureValue#getFeatureExpression()
	 * @see #getIteratorMappingNodeFeatureValue()
	 * @generated
	 */
	EReference getIteratorMappingNodeFeatureValue_FeatureExpression();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeValue
	 * <em>Iterator Type Value</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Iterator Type Value</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeValue
	 * @generated
	 */
	EClass getIteratorTypeValue();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeValue#getTypeContext
	 * <em>Type Context</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Type Context</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeValue#getTypeContext()
	 * @see #getIteratorTypeValue()
	 * @generated
	 */
	EReference getIteratorTypeValue_TypeContext();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeFeatureValue
	 * <em>Iterator Type Feature Value</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Iterator Type Feature Value</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeFeatureValue
	 * @generated
	 */
	EClass getIteratorTypeFeatureValue();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeFeatureValue#getFeatureExpression
	 * <em>Feature Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Feature
	 *         Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeFeatureValue#getFeatureExpression()
	 * @see #getIteratorTypeFeatureValue()
	 * @generated
	 */
	EReference getIteratorTypeFeatureValue_FeatureExpression();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamExpression
	 * <em>Stream Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Stream Expression</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.StreamExpression
	 * @generated
	 */
	EClass getStreamExpression();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamExpression#getReturnType
	 * <em>Return Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Return Type</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.StreamExpression#getReturnType()
	 * @see #getStreamExpression()
	 * @generated
	 */
	EReference getStreamExpression_ReturnType();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamExpression#getCurrent
	 * <em>Current</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Current</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.StreamExpression#getCurrent()
	 * @see #getStreamExpression()
	 * @generated
	 */
	EReference getStreamExpression_Current();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamExpression#getChild
	 * <em>Child</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Child</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.StreamExpression#getChild()
	 * @see #getStreamExpression()
	 * @generated
	 */
	EReference getStreamExpression_Child();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamOperation
	 * <em>Stream Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Stream Operation</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.StreamOperation
	 * @generated
	 */
	EClass getStreamOperation();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamNoOperation
	 * <em>Stream No Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Stream No Operation</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.StreamNoOperation
	 * @generated
	 */
	EClass getStreamNoOperation();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamFilterOperation
	 * <em>Stream Filter Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for class '<em>Stream Filter Operation</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.StreamFilterOperation
	 * @generated
	 */
	EClass getStreamFilterOperation();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamFilterOperation#getPredicate
	 * <em>Predicate</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Predicate</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.StreamFilterOperation#getPredicate()
	 * @see #getStreamFilterOperation()
	 * @generated
	 */
	EReference getStreamFilterOperation_Predicate();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamSelectOperation
	 * <em>Stream Select Operation</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for class '<em>Stream Select Operation</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.StreamSelectOperation
	 * @generated
	 */
	EClass getStreamSelectOperation();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamSelectOperation#getType
	 * <em>Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Type</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.StreamSelectOperation#getType()
	 * @see #getStreamSelectOperation()
	 * @generated
	 */
	EReference getStreamSelectOperation_Type();

	/**
	 * Returns the meta object for class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamContainsOperation
	 * <em>Stream Contains Operation</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Stream Contains Operation</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.StreamContainsOperation
	 * @generated
	 */
	EClass getStreamContainsOperation();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamContainsOperation#getExpr
	 * <em>Expr</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Expr</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.StreamContainsOperation#getExpr()
	 * @see #getStreamContainsOperation()
	 * @generated
	 */
	EReference getStreamContainsOperation_Expr();

	/**
	 * Returns the meta object for enum
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPSolverType <em>ILP
	 * Solver Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>ILP Solver Type</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ILPSolverType
	 * @generated
	 */
	EEnum getILPSolverType();

	/**
	 * Returns the meta object for enum
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.VariableType
	 * <em>Variable Type</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Variable Type</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.VariableType
	 * @generated
	 */
	EEnum getVariableType();

	/**
	 * Returns the meta object for enum
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveTarget
	 * <em>Objective Target</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Objective Target</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveTarget
	 * @generated
	 */
	EEnum getObjectiveTarget();

	/**
	 * Returns the meta object for enum
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator
	 * <em>Relational Operator</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Relational Operator</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator
	 * @generated
	 */
	EEnum getRelationalOperator();

	/**
	 * Returns the meta object for enum
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticOperator
	 * <em>Binary Arithmetic Operator</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Binary Arithmetic Operator</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticOperator
	 * @generated
	 */
	EEnum getBinaryArithmeticOperator();

	/**
	 * Returns the meta object for enum
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticOperator
	 * <em>Unary Arithmetic Operator</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Unary Arithmetic Operator</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticOperator
	 * @generated
	 */
	EEnum getUnaryArithmeticOperator();

	/**
	 * Returns the meta object for enum
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamArithmeticOperator
	 * <em>Stream Arithmetic Operator</em>}'. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Stream Arithmetic Operator</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.StreamArithmeticOperator
	 * @generated
	 */
	EEnum getStreamArithmeticOperator();

	/**
	 * Returns the meta object for enum
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BinaryBoolOperator
	 * <em>Binary Bool Operator</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for enum '<em>Binary Bool Operator</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BinaryBoolOperator
	 * @generated
	 */
	EEnum getBinaryBoolOperator();

	/**
	 * Returns the meta object for enum
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.UnaryBoolOperator
	 * <em>Unary Bool Operator</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Unary Bool Operator</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.UnaryBoolOperator
	 * @generated
	 */
	EEnum getUnaryBoolOperator();

	/**
	 * Returns the meta object for enum
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamBoolOperator
	 * <em>Stream Bool Operator</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for enum '<em>Stream Bool Operator</em>'.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.StreamBoolOperator
	 * @generated
	 */
	EEnum getStreamBoolOperator();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	GipsIntermediateFactory getGipsIntermediateFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each operation of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediateModelImpl
		 * <em>Model</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediateModelImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getGipsIntermediateModel()
		 * @generated
		 */
		EClass GIPS_INTERMEDIATE_MODEL = eINSTANCE.getGipsIntermediateModel();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute GIPS_INTERMEDIATE_MODEL__NAME = eINSTANCE.getGipsIntermediateModel_Name();

		/**
		 * The meta object literal for the '<em><b>Variables</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference GIPS_INTERMEDIATE_MODEL__VARIABLES = eINSTANCE.getGipsIntermediateModel_Variables();

		/**
		 * The meta object literal for the '<em><b>Constraints</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference GIPS_INTERMEDIATE_MODEL__CONSTRAINTS = eINSTANCE.getGipsIntermediateModel_Constraints();

		/**
		 * The meta object literal for the '<em><b>Objectives</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference GIPS_INTERMEDIATE_MODEL__OBJECTIVES = eINSTANCE.getGipsIntermediateModel_Objectives();

		/**
		 * The meta object literal for the '<em><b>Global Objective</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference GIPS_INTERMEDIATE_MODEL__GLOBAL_OBJECTIVE = eINSTANCE.getGipsIntermediateModel_GlobalObjective();

		/**
		 * The meta object literal for the '<em><b>Ibex Model</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference GIPS_INTERMEDIATE_MODEL__IBEX_MODEL = eINSTANCE.getGipsIntermediateModel_IbexModel();

		/**
		 * The meta object literal for the '<em><b>Config</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference GIPS_INTERMEDIATE_MODEL__CONFIG = eINSTANCE.getGipsIntermediateModel_Config();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ILPConfigImpl
		 * <em>ILP Config</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ILPConfigImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getILPConfig()
		 * @generated
		 */
		EClass ILP_CONFIG = eINSTANCE.getILPConfig();

		/**
		 * The meta object literal for the '<em><b>Solver</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ILP_CONFIG__SOLVER = eINSTANCE.getILPConfig_Solver();

		/**
		 * The meta object literal for the '<em><b>Solver Home Dir</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ILP_CONFIG__SOLVER_HOME_DIR = eINSTANCE.getILPConfig_SolverHomeDir();

		/**
		 * The meta object literal for the '<em><b>Solver License File</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ILP_CONFIG__SOLVER_LICENSE_FILE = eINSTANCE.getILPConfig_SolverLicenseFile();

		/**
		 * The meta object literal for the '<em><b>Build Launch Config</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ILP_CONFIG__BUILD_LAUNCH_CONFIG = eINSTANCE.getILPConfig_BuildLaunchConfig();

		/**
		 * The meta object literal for the '<em><b>Main File</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ILP_CONFIG__MAIN_FILE = eINSTANCE.getILPConfig_MainFile();

		/**
		 * The meta object literal for the '<em><b>Enable Time Limit</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ILP_CONFIG__ENABLE_TIME_LIMIT = eINSTANCE.getILPConfig_EnableTimeLimit();

		/**
		 * The meta object literal for the '<em><b>Ilp Time Limit</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ILP_CONFIG__ILP_TIME_LIMIT = eINSTANCE.getILPConfig_IlpTimeLimit();

		/**
		 * The meta object literal for the '<em><b>Enable Rnd Seed</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ILP_CONFIG__ENABLE_RND_SEED = eINSTANCE.getILPConfig_EnableRndSeed();

		/**
		 * The meta object literal for the '<em><b>Ilp Rnd Seed</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ILP_CONFIG__ILP_RND_SEED = eINSTANCE.getILPConfig_IlpRndSeed();

		/**
		 * The meta object literal for the '<em><b>Presolve</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ILP_CONFIG__PRESOLVE = eINSTANCE.getILPConfig_Presolve();

		/**
		 * The meta object literal for the '<em><b>Enable Presolve</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ILP_CONFIG__ENABLE_PRESOLVE = eINSTANCE.getILPConfig_EnablePresolve();

		/**
		 * The meta object literal for the '<em><b>Enable Debug Output</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ILP_CONFIG__ENABLE_DEBUG_OUTPUT = eINSTANCE.getILPConfig_EnableDebugOutput();

		/**
		 * The meta object literal for the '<em><b>Enable Custom Tolerance</b></em>'
		 * attribute feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ILP_CONFIG__ENABLE_CUSTOM_TOLERANCE = eINSTANCE.getILPConfig_EnableCustomTolerance();

		/**
		 * The meta object literal for the '<em><b>Tolerance</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ILP_CONFIG__TOLERANCE = eINSTANCE.getILPConfig_Tolerance();

		/**
		 * The meta object literal for the '<em><b>Enable Lp Output</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ILP_CONFIG__ENABLE_LP_OUTPUT = eINSTANCE.getILPConfig_EnableLpOutput();

		/**
		 * The meta object literal for the '<em><b>Lp Path</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ILP_CONFIG__LP_PATH = eINSTANCE.getILPConfig_LpPath();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.VariableSetImpl
		 * <em>Variable Set</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.VariableSetImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getVariableSet()
		 * @generated
		 */
		EClass VARIABLE_SET = eINSTANCE.getVariableSet();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VARIABLE_SET__NAME = eINSTANCE.getVariableSet_Name();

		/**
		 * The meta object literal for the '<em><b>Upper Bound</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VARIABLE_SET__UPPER_BOUND = eINSTANCE.getVariableSet_UpperBound();

		/**
		 * The meta object literal for the '<em><b>Lower Bound</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VARIABLE_SET__LOWER_BOUND = eINSTANCE.getVariableSet_LowerBound();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternImpl
		 * <em>Pattern</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getPattern()
		 * @generated
		 */
		EClass PATTERN = eINSTANCE.getPattern();

		/**
		 * The meta object literal for the '<em><b>Pattern</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PATTERN__PATTERN = eINSTANCE.getPattern_Pattern();

		/**
		 * The meta object literal for the '<em><b>Is Rule</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PATTERN__IS_RULE = eINSTANCE.getPattern_IsRule();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.TypeImpl
		 * <em>Type</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.TypeImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getType()
		 * @generated
		 */
		EClass TYPE = eINSTANCE.getType();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TYPE__TYPE = eINSTANCE.getType_Type();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.MappingImpl
		 * <em>Mapping</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.MappingImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getMapping()
		 * @generated
		 */
		EClass MAPPING = eINSTANCE.getMapping();

		/**
		 * The meta object literal for the '<em><b>Context Pattern</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MAPPING__CONTEXT_PATTERN = eINSTANCE.getMapping_ContextPattern();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.GTMappingImpl
		 * <em>GT Mapping</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GTMappingImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getGTMapping()
		 * @generated
		 */
		EClass GT_MAPPING = eINSTANCE.getGTMapping();

		/**
		 * The meta object literal for the '<em><b>Rule</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference GT_MAPPING__RULE = eINSTANCE.getGTMapping_Rule();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternMappingImpl
		 * <em>Pattern Mapping</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternMappingImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getPatternMapping()
		 * @generated
		 */
		EClass PATTERN_MAPPING = eINSTANCE.getPatternMapping();

		/**
		 * The meta object literal for the '<em><b>Pattern</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PATTERN_MAPPING__PATTERN = eINSTANCE.getPatternMapping_Pattern();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.VariableImpl
		 * <em>Variable</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.VariableImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getVariable()
		 * @generated
		 */
		EClass VARIABLE = eINSTANCE.getVariable();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute VARIABLE__TYPE = eINSTANCE.getVariable_Type();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ConstraintImpl
		 * <em>Constraint</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ConstraintImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getConstraint()
		 * @generated
		 */
		EClass CONSTRAINT = eINSTANCE.getConstraint();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CONSTRAINT__NAME = eINSTANCE.getConstraint_Name();

		/**
		 * The meta object literal for the '<em><b>Depending</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CONSTRAINT__DEPENDING = eINSTANCE.getConstraint_Depending();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONSTRAINT__EXPRESSION = eINSTANCE.getConstraint_Expression();

		/**
		 * The meta object literal for the '<em><b>Constant</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CONSTRAINT__CONSTANT = eINSTANCE.getConstraint_Constant();

		/**
		 * The meta object literal for the '<em><b>Negated</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CONSTRAINT__NEGATED = eINSTANCE.getConstraint_Negated();

		/**
		 * The meta object literal for the '<em><b>Dependencies</b></em>' reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONSTRAINT__DEPENDENCIES = eINSTANCE.getConstraint_Dependencies();

		/**
		 * The meta object literal for the '<em><b>Referenced By</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONSTRAINT__REFERENCED_BY = eINSTANCE.getConstraint_ReferencedBy();

		/**
		 * The meta object literal for the '<em><b>Symbolic Variable</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONSTRAINT__SYMBOLIC_VARIABLE = eINSTANCE.getConstraint_SymbolicVariable();

		/**
		 * The meta object literal for the '<em><b>Helper Variables</b></em>' reference
		 * list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONSTRAINT__HELPER_VARIABLES = eINSTANCE.getConstraint_HelperVariables();

		/**
		 * The meta object literal for the '<em><b>Helper Constraints</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @generated
		 */
		EReference CONSTRAINT__HELPER_CONSTRAINTS = eINSTANCE.getConstraint_HelperConstraints();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.GlobalConstraintImpl
		 * <em>Global Constraint</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GlobalConstraintImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getGlobalConstraint()
		 * @generated
		 */
		EClass GLOBAL_CONSTRAINT = eINSTANCE.getGlobalConstraint();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ObjectiveImpl
		 * <em>Objective</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ObjectiveImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getObjective()
		 * @generated
		 */
		EClass OBJECTIVE = eINSTANCE.getObjective();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute OBJECTIVE__NAME = eINSTANCE.getObjective_Name();

		/**
		 * The meta object literal for the '<em><b>Elementwise</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute OBJECTIVE__ELEMENTWISE = eINSTANCE.getObjective_Elementwise();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference OBJECTIVE__EXPRESSION = eINSTANCE.getObjective_Expression();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.GlobalObjectiveImpl
		 * <em>Global Objective</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GlobalObjectiveImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getGlobalObjective()
		 * @generated
		 */
		EClass GLOBAL_OBJECTIVE = eINSTANCE.getGlobalObjective();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference GLOBAL_OBJECTIVE__EXPRESSION = eINSTANCE.getGlobalObjective_Expression();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute GLOBAL_OBJECTIVE__TARGET = eINSTANCE.getGlobalObjective_Target();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Context
		 * <em>Context</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.Context
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getContext()
		 * @generated
		 */
		EClass CONTEXT = eINSTANCE.getContext();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternConstraintImpl
		 * <em>Pattern Constraint</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternConstraintImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getPatternConstraint()
		 * @generated
		 */
		EClass PATTERN_CONSTRAINT = eINSTANCE.getPatternConstraint();

		/**
		 * The meta object literal for the '<em><b>Pattern</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PATTERN_CONSTRAINT__PATTERN = eINSTANCE.getPatternConstraint_Pattern();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.TypeConstraintImpl
		 * <em>Type Constraint</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.TypeConstraintImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getTypeConstraint()
		 * @generated
		 */
		EClass TYPE_CONSTRAINT = eINSTANCE.getTypeConstraint();

		/**
		 * The meta object literal for the '<em><b>Model Type</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TYPE_CONSTRAINT__MODEL_TYPE = eINSTANCE.getTypeConstraint_ModelType();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.MappingConstraintImpl
		 * <em>Mapping Constraint</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.MappingConstraintImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getMappingConstraint()
		 * @generated
		 */
		EClass MAPPING_CONSTRAINT = eINSTANCE.getMappingConstraint();

		/**
		 * The meta object literal for the '<em><b>Mapping</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MAPPING_CONSTRAINT__MAPPING = eINSTANCE.getMappingConstraint_Mapping();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternObjectiveImpl
		 * <em>Pattern Objective</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternObjectiveImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getPatternObjective()
		 * @generated
		 */
		EClass PATTERN_OBJECTIVE = eINSTANCE.getPatternObjective();

		/**
		 * The meta object literal for the '<em><b>Pattern</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PATTERN_OBJECTIVE__PATTERN = eINSTANCE.getPatternObjective_Pattern();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.TypeObjectiveImpl
		 * <em>Type Objective</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.TypeObjectiveImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getTypeObjective()
		 * @generated
		 */
		EClass TYPE_OBJECTIVE = eINSTANCE.getTypeObjective();

		/**
		 * The meta object literal for the '<em><b>Model Type</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TYPE_OBJECTIVE__MODEL_TYPE = eINSTANCE.getTypeObjective_ModelType();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.MappingObjectiveImpl
		 * <em>Mapping Objective</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.MappingObjectiveImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getMappingObjective()
		 * @generated
		 */
		EClass MAPPING_OBJECTIVE = eINSTANCE.getMappingObjective();

		/**
		 * The meta object literal for the '<em><b>Mapping</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MAPPING_OBJECTIVE__MAPPING = eINSTANCE.getMappingObjective_Mapping();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ArithmeticExpressionImpl
		 * <em>Arithmetic Expression</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ArithmeticExpressionImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getArithmeticExpression()
		 * @generated
		 */
		EClass ARITHMETIC_EXPRESSION = eINSTANCE.getArithmeticExpression();

		/**
		 * The meta object literal for the '<em><b>Return Type</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ARITHMETIC_EXPRESSION__RETURN_TYPE = eINSTANCE.getArithmeticExpression_ReturnType();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.BinaryArithmeticExpressionImpl
		 * <em>Binary Arithmetic Expression</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.BinaryArithmeticExpressionImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getBinaryArithmeticExpression()
		 * @generated
		 */
		EClass BINARY_ARITHMETIC_EXPRESSION = eINSTANCE.getBinaryArithmeticExpression();

		/**
		 * The meta object literal for the '<em><b>Lhs</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference BINARY_ARITHMETIC_EXPRESSION__LHS = eINSTANCE.getBinaryArithmeticExpression_Lhs();

		/**
		 * The meta object literal for the '<em><b>Rhs</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference BINARY_ARITHMETIC_EXPRESSION__RHS = eINSTANCE.getBinaryArithmeticExpression_Rhs();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute BINARY_ARITHMETIC_EXPRESSION__OPERATOR = eINSTANCE.getBinaryArithmeticExpression_Operator();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.UnaryArithmeticExpressionImpl
		 * <em>Unary Arithmetic Expression</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.UnaryArithmeticExpressionImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getUnaryArithmeticExpression()
		 * @generated
		 */
		EClass UNARY_ARITHMETIC_EXPRESSION = eINSTANCE.getUnaryArithmeticExpression();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference UNARY_ARITHMETIC_EXPRESSION__EXPRESSION = eINSTANCE.getUnaryArithmeticExpression_Expression();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute UNARY_ARITHMETIC_EXPRESSION__OPERATOR = eINSTANCE.getUnaryArithmeticExpression_Operator();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.SetOperation <em>Set
		 * Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.SetOperation
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getSetOperation()
		 * @generated
		 */
		EClass SET_OPERATION = eINSTANCE.getSetOperation();

		/**
		 * The meta object literal for the '<em><b>Operand Name</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SET_OPERATION__OPERAND_NAME = eINSTANCE.getSetOperation_OperandName();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ArithmeticValueExpressionImpl
		 * <em>Arithmetic Value Expression</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ArithmeticValueExpressionImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getArithmeticValueExpression()
		 * @generated
		 */
		EClass ARITHMETIC_VALUE_EXPRESSION = eINSTANCE.getArithmeticValueExpression();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ArithmeticValueImpl
		 * <em>Arithmetic Value</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ArithmeticValueImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getArithmeticValue()
		 * @generated
		 */
		EClass ARITHMETIC_VALUE = eINSTANCE.getArithmeticValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ARITHMETIC_VALUE__VALUE = eINSTANCE.getArithmeticValue_Value();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ArithmeticLiteralImpl
		 * <em>Arithmetic Literal</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ArithmeticLiteralImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getArithmeticLiteral()
		 * @generated
		 */
		EClass ARITHMETIC_LITERAL = eINSTANCE.getArithmeticLiteral();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.VariableReferenceImpl
		 * <em>Variable Reference</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.VariableReferenceImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getVariableReference()
		 * @generated
		 */
		EClass VARIABLE_REFERENCE = eINSTANCE.getVariableReference();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference VARIABLE_REFERENCE__VARIABLE = eINSTANCE.getVariableReference_Variable();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ArithmeticNullLiteralImpl
		 * <em>Arithmetic Null Literal</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ArithmeticNullLiteralImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getArithmeticNullLiteral()
		 * @generated
		 */
		EClass ARITHMETIC_NULL_LITERAL = eINSTANCE.getArithmeticNullLiteral();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IntegerLiteralImpl
		 * <em>Integer Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.IntegerLiteralImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getIntegerLiteral()
		 * @generated
		 */
		EClass INTEGER_LITERAL = eINSTANCE.getIntegerLiteral();

		/**
		 * The meta object literal for the '<em><b>Literal</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute INTEGER_LITERAL__LITERAL = eINSTANCE.getIntegerLiteral_Literal();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.DoubleLiteralImpl
		 * <em>Double Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.DoubleLiteralImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getDoubleLiteral()
		 * @generated
		 */
		EClass DOUBLE_LITERAL = eINSTANCE.getDoubleLiteral();

		/**
		 * The meta object literal for the '<em><b>Literal</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute DOUBLE_LITERAL__LITERAL = eINSTANCE.getDoubleLiteral_Literal();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolExpressionImpl
		 * <em>Bool Expression</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolExpressionImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getBoolExpression()
		 * @generated
		 */
		EClass BOOL_EXPRESSION = eINSTANCE.getBoolExpression();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolBinaryExpressionImpl
		 * <em>Bool Binary Expression</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolBinaryExpressionImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getBoolBinaryExpression()
		 * @generated
		 */
		EClass BOOL_BINARY_EXPRESSION = eINSTANCE.getBoolBinaryExpression();

		/**
		 * The meta object literal for the '<em><b>Lhs</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference BOOL_BINARY_EXPRESSION__LHS = eINSTANCE.getBoolBinaryExpression_Lhs();

		/**
		 * The meta object literal for the '<em><b>Rhs</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference BOOL_BINARY_EXPRESSION__RHS = eINSTANCE.getBoolBinaryExpression_Rhs();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute BOOL_BINARY_EXPRESSION__OPERATOR = eINSTANCE.getBoolBinaryExpression_Operator();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolUnaryExpressionImpl
		 * <em>Bool Unary Expression</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolUnaryExpressionImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getBoolUnaryExpression()
		 * @generated
		 */
		EClass BOOL_UNARY_EXPRESSION = eINSTANCE.getBoolUnaryExpression();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference BOOL_UNARY_EXPRESSION__EXPRESSION = eINSTANCE.getBoolUnaryExpression_Expression();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute BOOL_UNARY_EXPRESSION__OPERATOR = eINSTANCE.getBoolUnaryExpression_Operator();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolValueExpressionImpl
		 * <em>Bool Value Expression</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolValueExpressionImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getBoolValueExpression()
		 * @generated
		 */
		EClass BOOL_VALUE_EXPRESSION = eINSTANCE.getBoolValueExpression();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolValueImpl
		 * <em>Bool Value</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolValueImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getBoolValue()
		 * @generated
		 */
		EClass BOOL_VALUE = eINSTANCE.getBoolValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference BOOL_VALUE__VALUE = eINSTANCE.getBoolValue_Value();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolStreamExpressionImpl
		 * <em>Bool Stream Expression</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolStreamExpressionImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getBoolStreamExpression()
		 * @generated
		 */
		EClass BOOL_STREAM_EXPRESSION = eINSTANCE.getBoolStreamExpression();

		/**
		 * The meta object literal for the '<em><b>Stream</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference BOOL_STREAM_EXPRESSION__STREAM = eINSTANCE.getBoolStreamExpression_Stream();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute BOOL_STREAM_EXPRESSION__OPERATOR = eINSTANCE.getBoolStreamExpression_Operator();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.RelationalExpressionImpl
		 * <em>Relational Expression</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.RelationalExpressionImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getRelationalExpression()
		 * @generated
		 */
		EClass RELATIONAL_EXPRESSION = eINSTANCE.getRelationalExpression();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute RELATIONAL_EXPRESSION__OPERATOR = eINSTANCE.getRelationalExpression_Operator();

		/**
		 * The meta object literal for the '<em><b>Lhs</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference RELATIONAL_EXPRESSION__LHS = eINSTANCE.getRelationalExpression_Lhs();

		/**
		 * The meta object literal for the '<em><b>Rhs</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference RELATIONAL_EXPRESSION__RHS = eINSTANCE.getRelationalExpression_Rhs();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolLiteralImpl
		 * <em>Bool Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.BoolLiteralImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getBoolLiteral()
		 * @generated
		 */
		EClass BOOL_LITERAL = eINSTANCE.getBoolLiteral();

		/**
		 * The meta object literal for the '<em><b>Literal</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute BOOL_LITERAL__LITERAL = eINSTANCE.getBoolLiteral_Literal();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ValueExpressionImpl
		 * <em>Value Expression</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ValueExpressionImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getValueExpression()
		 * @generated
		 */
		EClass VALUE_EXPRESSION = eINSTANCE.getValueExpression();

		/**
		 * The meta object literal for the '<em><b>Return Type</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference VALUE_EXPRESSION__RETURN_TYPE = eINSTANCE.getValueExpression_ReturnType();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.SumExpressionImpl
		 * <em>Sum Expression</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.SumExpressionImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getSumExpression()
		 * @generated
		 */
		EClass SUM_EXPRESSION = eINSTANCE.getSumExpression();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SUM_EXPRESSION__EXPRESSION = eINSTANCE.getSumExpression_Expression();

		/**
		 * The meta object literal for the '<em><b>Filter</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SUM_EXPRESSION__FILTER = eINSTANCE.getSumExpression_Filter();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextSumExpressionImpl
		 * <em>Context Sum Expression</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextSumExpressionImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getContextSumExpression()
		 * @generated
		 */
		EClass CONTEXT_SUM_EXPRESSION = eINSTANCE.getContextSumExpression();

		/**
		 * The meta object literal for the '<em><b>Context</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONTEXT_SUM_EXPRESSION__CONTEXT = eINSTANCE.getContextSumExpression_Context();

		/**
		 * The meta object literal for the '<em><b>Node</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONTEXT_SUM_EXPRESSION__NODE = eINSTANCE.getContextSumExpression_Node();

		/**
		 * The meta object literal for the '<em><b>Feature</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONTEXT_SUM_EXPRESSION__FEATURE = eINSTANCE.getContextSumExpression_Feature();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.MappingSumExpressionImpl
		 * <em>Mapping Sum Expression</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.MappingSumExpressionImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getMappingSumExpression()
		 * @generated
		 */
		EClass MAPPING_SUM_EXPRESSION = eINSTANCE.getMappingSumExpression();

		/**
		 * The meta object literal for the '<em><b>Mapping</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MAPPING_SUM_EXPRESSION__MAPPING = eINSTANCE.getMappingSumExpression_Mapping();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternSumExpressionImpl
		 * <em>Pattern Sum Expression</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.PatternSumExpressionImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getPatternSumExpression()
		 * @generated
		 */
		EClass PATTERN_SUM_EXPRESSION = eINSTANCE.getPatternSumExpression();

		/**
		 * The meta object literal for the '<em><b>Pattern</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PATTERN_SUM_EXPRESSION__PATTERN = eINSTANCE.getPatternSumExpression_Pattern();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.TypeSumExpressionImpl
		 * <em>Type Sum Expression</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.TypeSumExpressionImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getTypeSumExpression()
		 * @generated
		 */
		EClass TYPE_SUM_EXPRESSION = eINSTANCE.getTypeSumExpression();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TYPE_SUM_EXPRESSION__TYPE = eINSTANCE.getTypeSumExpression_Type();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextTypeValueImpl
		 * <em>Context Type Value</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextTypeValueImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getContextTypeValue()
		 * @generated
		 */
		EClass CONTEXT_TYPE_VALUE = eINSTANCE.getContextTypeValue();

		/**
		 * The meta object literal for the '<em><b>Type Context</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONTEXT_TYPE_VALUE__TYPE_CONTEXT = eINSTANCE.getContextTypeValue_TypeContext();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextPatternValueImpl
		 * <em>Context Pattern Value</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextPatternValueImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getContextPatternValue()
		 * @generated
		 */
		EClass CONTEXT_PATTERN_VALUE = eINSTANCE.getContextPatternValue();

		/**
		 * The meta object literal for the '<em><b>Pattern Context</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONTEXT_PATTERN_VALUE__PATTERN_CONTEXT = eINSTANCE.getContextPatternValue_PatternContext();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextPatternNodeImpl
		 * <em>Context Pattern Node</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextPatternNodeImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getContextPatternNode()
		 * @generated
		 */
		EClass CONTEXT_PATTERN_NODE = eINSTANCE.getContextPatternNode();

		/**
		 * The meta object literal for the '<em><b>Pattern Context</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONTEXT_PATTERN_NODE__PATTERN_CONTEXT = eINSTANCE.getContextPatternNode_PatternContext();

		/**
		 * The meta object literal for the '<em><b>Node</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONTEXT_PATTERN_NODE__NODE = eINSTANCE.getContextPatternNode_Node();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextMappingValueImpl
		 * <em>Context Mapping Value</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextMappingValueImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getContextMappingValue()
		 * @generated
		 */
		EClass CONTEXT_MAPPING_VALUE = eINSTANCE.getContextMappingValue();

		/**
		 * The meta object literal for the '<em><b>Mapping Context</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONTEXT_MAPPING_VALUE__MAPPING_CONTEXT = eINSTANCE.getContextMappingValue_MappingContext();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextMappingNodeImpl
		 * <em>Context Mapping Node</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextMappingNodeImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getContextMappingNode()
		 * @generated
		 */
		EClass CONTEXT_MAPPING_NODE = eINSTANCE.getContextMappingNode();

		/**
		 * The meta object literal for the '<em><b>Mapping Context</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONTEXT_MAPPING_NODE__MAPPING_CONTEXT = eINSTANCE.getContextMappingNode_MappingContext();

		/**
		 * The meta object literal for the '<em><b>Node</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONTEXT_MAPPING_NODE__NODE = eINSTANCE.getContextMappingNode_Node();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ObjectiveFunctionValueImpl
		 * <em>Objective Function Value</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ObjectiveFunctionValueImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getObjectiveFunctionValue()
		 * @generated
		 */
		EClass OBJECTIVE_FUNCTION_VALUE = eINSTANCE.getObjectiveFunctionValue();

		/**
		 * The meta object literal for the '<em><b>Objective</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference OBJECTIVE_FUNCTION_VALUE__OBJECTIVE = eINSTANCE.getObjectiveFunctionValue_Objective();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.FeatureExpressionImpl
		 * <em>Feature Expression</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.FeatureExpressionImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getFeatureExpression()
		 * @generated
		 */
		EClass FEATURE_EXPRESSION = eINSTANCE.getFeatureExpression();

		/**
		 * The meta object literal for the '<em><b>Current</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FEATURE_EXPRESSION__CURRENT = eINSTANCE.getFeatureExpression_Current();

		/**
		 * The meta object literal for the '<em><b>Child</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FEATURE_EXPRESSION__CHILD = eINSTANCE.getFeatureExpression_Child();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.FeatureLiteralImpl
		 * <em>Feature Literal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.FeatureLiteralImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getFeatureLiteral()
		 * @generated
		 */
		EClass FEATURE_LITERAL = eINSTANCE.getFeatureLiteral();

		/**
		 * The meta object literal for the '<em><b>Feature</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FEATURE_LITERAL__FEATURE = eINSTANCE.getFeatureLiteral_Feature();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextTypeFeatureValueImpl
		 * <em>Context Type Feature Value</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextTypeFeatureValueImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getContextTypeFeatureValue()
		 * @generated
		 */
		EClass CONTEXT_TYPE_FEATURE_VALUE = eINSTANCE.getContextTypeFeatureValue();

		/**
		 * The meta object literal for the '<em><b>Feature Expression</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONTEXT_TYPE_FEATURE_VALUE__FEATURE_EXPRESSION = eINSTANCE
				.getContextTypeFeatureValue_FeatureExpression();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextPatternNodeFeatureValueImpl
		 * <em>Context Pattern Node Feature Value</em>}' class. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextPatternNodeFeatureValueImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getContextPatternNodeFeatureValue()
		 * @generated
		 */
		EClass CONTEXT_PATTERN_NODE_FEATURE_VALUE = eINSTANCE.getContextPatternNodeFeatureValue();

		/**
		 * The meta object literal for the '<em><b>Feature Expression</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONTEXT_PATTERN_NODE_FEATURE_VALUE__FEATURE_EXPRESSION = eINSTANCE
				.getContextPatternNodeFeatureValue_FeatureExpression();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextMappingNodeFeatureValueImpl
		 * <em>Context Mapping Node Feature Value</em>}' class. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.ContextMappingNodeFeatureValueImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getContextMappingNodeFeatureValue()
		 * @generated
		 */
		EClass CONTEXT_MAPPING_NODE_FEATURE_VALUE = eINSTANCE.getContextMappingNodeFeatureValue();

		/**
		 * The meta object literal for the '<em><b>Feature Expression</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CONTEXT_MAPPING_NODE_FEATURE_VALUE__FEATURE_EXPRESSION = eINSTANCE
				.getContextMappingNodeFeatureValue_FeatureExpression();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorImpl
		 * <em>Iterator</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getIterator()
		 * @generated
		 */
		EClass ITERATOR = eINSTANCE.getIterator();

		/**
		 * The meta object literal for the '<em><b>Stream</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ITERATOR__STREAM = eINSTANCE.getIterator_Stream();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorPatternValueImpl
		 * <em>Iterator Pattern Value</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorPatternValueImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getIteratorPatternValue()
		 * @generated
		 */
		EClass ITERATOR_PATTERN_VALUE = eINSTANCE.getIteratorPatternValue();

		/**
		 * The meta object literal for the '<em><b>Pattern Context</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ITERATOR_PATTERN_VALUE__PATTERN_CONTEXT = eINSTANCE.getIteratorPatternValue_PatternContext();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorPatternFeatureValueImpl
		 * <em>Iterator Pattern Feature Value</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorPatternFeatureValueImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getIteratorPatternFeatureValue()
		 * @generated
		 */
		EClass ITERATOR_PATTERN_FEATURE_VALUE = eINSTANCE.getIteratorPatternFeatureValue();

		/**
		 * The meta object literal for the '<em><b>Pattern Context</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ITERATOR_PATTERN_FEATURE_VALUE__PATTERN_CONTEXT = eINSTANCE
				.getIteratorPatternFeatureValue_PatternContext();

		/**
		 * The meta object literal for the '<em><b>Feature Expression</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ITERATOR_PATTERN_FEATURE_VALUE__FEATURE_EXPRESSION = eINSTANCE
				.getIteratorPatternFeatureValue_FeatureExpression();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorPatternNodeValueImpl
		 * <em>Iterator Pattern Node Value</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorPatternNodeValueImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getIteratorPatternNodeValue()
		 * @generated
		 */
		EClass ITERATOR_PATTERN_NODE_VALUE = eINSTANCE.getIteratorPatternNodeValue();

		/**
		 * The meta object literal for the '<em><b>Pattern Context</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ITERATOR_PATTERN_NODE_VALUE__PATTERN_CONTEXT = eINSTANCE
				.getIteratorPatternNodeValue_PatternContext();

		/**
		 * The meta object literal for the '<em><b>Node</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ITERATOR_PATTERN_NODE_VALUE__NODE = eINSTANCE.getIteratorPatternNodeValue_Node();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorPatternNodeFeatureValueImpl
		 * <em>Iterator Pattern Node Feature Value</em>}' class. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorPatternNodeFeatureValueImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getIteratorPatternNodeFeatureValue()
		 * @generated
		 */
		EClass ITERATOR_PATTERN_NODE_FEATURE_VALUE = eINSTANCE.getIteratorPatternNodeFeatureValue();

		/**
		 * The meta object literal for the '<em><b>Feature Expression</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ITERATOR_PATTERN_NODE_FEATURE_VALUE__FEATURE_EXPRESSION = eINSTANCE
				.getIteratorPatternNodeFeatureValue_FeatureExpression();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorMappingValueImpl
		 * <em>Iterator Mapping Value</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorMappingValueImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getIteratorMappingValue()
		 * @generated
		 */
		EClass ITERATOR_MAPPING_VALUE = eINSTANCE.getIteratorMappingValue();

		/**
		 * The meta object literal for the '<em><b>Mapping Context</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ITERATOR_MAPPING_VALUE__MAPPING_CONTEXT = eINSTANCE.getIteratorMappingValue_MappingContext();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorMappingVariableValueImpl
		 * <em>Iterator Mapping Variable Value</em>}' class. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorMappingVariableValueImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getIteratorMappingVariableValue()
		 * @generated
		 */
		EClass ITERATOR_MAPPING_VARIABLE_VALUE = eINSTANCE.getIteratorMappingVariableValue();

		/**
		 * The meta object literal for the '<em><b>Mapping Context</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ITERATOR_MAPPING_VARIABLE_VALUE__MAPPING_CONTEXT = eINSTANCE
				.getIteratorMappingVariableValue_MappingContext();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorMappingFeatureValueImpl
		 * <em>Iterator Mapping Feature Value</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorMappingFeatureValueImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getIteratorMappingFeatureValue()
		 * @generated
		 */
		EClass ITERATOR_MAPPING_FEATURE_VALUE = eINSTANCE.getIteratorMappingFeatureValue();

		/**
		 * The meta object literal for the '<em><b>Mapping Context</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ITERATOR_MAPPING_FEATURE_VALUE__MAPPING_CONTEXT = eINSTANCE
				.getIteratorMappingFeatureValue_MappingContext();

		/**
		 * The meta object literal for the '<em><b>Feature Expression</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ITERATOR_MAPPING_FEATURE_VALUE__FEATURE_EXPRESSION = eINSTANCE
				.getIteratorMappingFeatureValue_FeatureExpression();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorMappingNodeValueImpl
		 * <em>Iterator Mapping Node Value</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorMappingNodeValueImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getIteratorMappingNodeValue()
		 * @generated
		 */
		EClass ITERATOR_MAPPING_NODE_VALUE = eINSTANCE.getIteratorMappingNodeValue();

		/**
		 * The meta object literal for the '<em><b>Mapping Context</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ITERATOR_MAPPING_NODE_VALUE__MAPPING_CONTEXT = eINSTANCE
				.getIteratorMappingNodeValue_MappingContext();

		/**
		 * The meta object literal for the '<em><b>Node</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ITERATOR_MAPPING_NODE_VALUE__NODE = eINSTANCE.getIteratorMappingNodeValue_Node();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorMappingNodeFeatureValueImpl
		 * <em>Iterator Mapping Node Feature Value</em>}' class. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorMappingNodeFeatureValueImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getIteratorMappingNodeFeatureValue()
		 * @generated
		 */
		EClass ITERATOR_MAPPING_NODE_FEATURE_VALUE = eINSTANCE.getIteratorMappingNodeFeatureValue();

		/**
		 * The meta object literal for the '<em><b>Feature Expression</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ITERATOR_MAPPING_NODE_FEATURE_VALUE__FEATURE_EXPRESSION = eINSTANCE
				.getIteratorMappingNodeFeatureValue_FeatureExpression();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorTypeValueImpl
		 * <em>Iterator Type Value</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorTypeValueImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getIteratorTypeValue()
		 * @generated
		 */
		EClass ITERATOR_TYPE_VALUE = eINSTANCE.getIteratorTypeValue();

		/**
		 * The meta object literal for the '<em><b>Type Context</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ITERATOR_TYPE_VALUE__TYPE_CONTEXT = eINSTANCE.getIteratorTypeValue_TypeContext();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorTypeFeatureValueImpl
		 * <em>Iterator Type Feature Value</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.IteratorTypeFeatureValueImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getIteratorTypeFeatureValue()
		 * @generated
		 */
		EClass ITERATOR_TYPE_FEATURE_VALUE = eINSTANCE.getIteratorTypeFeatureValue();

		/**
		 * The meta object literal for the '<em><b>Feature Expression</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ITERATOR_TYPE_FEATURE_VALUE__FEATURE_EXPRESSION = eINSTANCE
				.getIteratorTypeFeatureValue_FeatureExpression();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.StreamExpressionImpl
		 * <em>Stream Expression</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.StreamExpressionImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getStreamExpression()
		 * @generated
		 */
		EClass STREAM_EXPRESSION = eINSTANCE.getStreamExpression();

		/**
		 * The meta object literal for the '<em><b>Return Type</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference STREAM_EXPRESSION__RETURN_TYPE = eINSTANCE.getStreamExpression_ReturnType();

		/**
		 * The meta object literal for the '<em><b>Current</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference STREAM_EXPRESSION__CURRENT = eINSTANCE.getStreamExpression_Current();

		/**
		 * The meta object literal for the '<em><b>Child</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference STREAM_EXPRESSION__CHILD = eINSTANCE.getStreamExpression_Child();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamOperation
		 * <em>Stream Operation</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.StreamOperation
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getStreamOperation()
		 * @generated
		 */
		EClass STREAM_OPERATION = eINSTANCE.getStreamOperation();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.StreamNoOperationImpl
		 * <em>Stream No Operation</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.StreamNoOperationImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getStreamNoOperation()
		 * @generated
		 */
		EClass STREAM_NO_OPERATION = eINSTANCE.getStreamNoOperation();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.StreamFilterOperationImpl
		 * <em>Stream Filter Operation</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.StreamFilterOperationImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getStreamFilterOperation()
		 * @generated
		 */
		EClass STREAM_FILTER_OPERATION = eINSTANCE.getStreamFilterOperation();

		/**
		 * The meta object literal for the '<em><b>Predicate</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference STREAM_FILTER_OPERATION__PREDICATE = eINSTANCE.getStreamFilterOperation_Predicate();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.StreamSelectOperationImpl
		 * <em>Stream Select Operation</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.StreamSelectOperationImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getStreamSelectOperation()
		 * @generated
		 */
		EClass STREAM_SELECT_OPERATION = eINSTANCE.getStreamSelectOperation();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference STREAM_SELECT_OPERATION__TYPE = eINSTANCE.getStreamSelectOperation_Type();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.impl.StreamContainsOperationImpl
		 * <em>Stream Contains Operation</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.StreamContainsOperationImpl
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getStreamContainsOperation()
		 * @generated
		 */
		EClass STREAM_CONTAINS_OPERATION = eINSTANCE.getStreamContainsOperation();

		/**
		 * The meta object literal for the '<em><b>Expr</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference STREAM_CONTAINS_OPERATION__EXPR = eINSTANCE.getStreamContainsOperation_Expr();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPSolverType <em>ILP
		 * Solver Type</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.ILPSolverType
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getILPSolverType()
		 * @generated
		 */
		EEnum ILP_SOLVER_TYPE = eINSTANCE.getILPSolverType();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.VariableType
		 * <em>Variable Type</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.VariableType
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getVariableType()
		 * @generated
		 */
		EEnum VARIABLE_TYPE = eINSTANCE.getVariableType();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveTarget
		 * <em>Objective Target</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveTarget
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getObjectiveTarget()
		 * @generated
		 */
		EEnum OBJECTIVE_TARGET = eINSTANCE.getObjectiveTarget();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator
		 * <em>Relational Operator</em>}' enum. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getRelationalOperator()
		 * @generated
		 */
		EEnum RELATIONAL_OPERATOR = eINSTANCE.getRelationalOperator();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticOperator
		 * <em>Binary Arithmetic Operator</em>}' enum. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticOperator
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getBinaryArithmeticOperator()
		 * @generated
		 */
		EEnum BINARY_ARITHMETIC_OPERATOR = eINSTANCE.getBinaryArithmeticOperator();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticOperator
		 * <em>Unary Arithmetic Operator</em>}' enum. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticOperator
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getUnaryArithmeticOperator()
		 * @generated
		 */
		EEnum UNARY_ARITHMETIC_OPERATOR = eINSTANCE.getUnaryArithmeticOperator();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamArithmeticOperator
		 * <em>Stream Arithmetic Operator</em>}' enum. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.StreamArithmeticOperator
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getStreamArithmeticOperator()
		 * @generated
		 */
		EEnum STREAM_ARITHMETIC_OPERATOR = eINSTANCE.getStreamArithmeticOperator();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BinaryBoolOperator
		 * <em>Binary Bool Operator</em>}' enum. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.BinaryBoolOperator
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getBinaryBoolOperator()
		 * @generated
		 */
		EEnum BINARY_BOOL_OPERATOR = eINSTANCE.getBinaryBoolOperator();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.UnaryBoolOperator
		 * <em>Unary Bool Operator</em>}' enum. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.UnaryBoolOperator
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getUnaryBoolOperator()
		 * @generated
		 */
		EEnum UNARY_BOOL_OPERATOR = eINSTANCE.getUnaryBoolOperator();

		/**
		 * The meta object literal for the
		 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamBoolOperator
		 * <em>Stream Bool Operator</em>}' enum. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.StreamBoolOperator
		 * @see org.emoflon.gips.intermediate.GipsIntermediate.impl.GipsIntermediatePackageImpl#getStreamBoolOperator()
		 * @generated
		 */
		EEnum STREAM_BOOL_OPERATOR = eINSTANCE.getStreamBoolOperator();

	}

} // GipsIntermediatePackage
