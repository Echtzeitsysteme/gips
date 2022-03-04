/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticValueExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticOperator;
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryBoolOperator;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolBinaryExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolStreamExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolUnaryExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolValue;
import org.emoflon.roam.intermediate.RoamIntermediate.BoolValueExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.Constraint;
import org.emoflon.roam.intermediate.RoamIntermediate.Context;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNode;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNodeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextPatternNode;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextPatternNodeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextPatternValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeValue;
import org.emoflon.roam.intermediate.RoamIntermediate.DoubleLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.FeatureExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.FeatureLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.GlobalObjective;
import org.emoflon.roam.intermediate.RoamIntermediate.IntegerLiteral;
import org.emoflon.roam.intermediate.RoamIntermediate.Iterator;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorPatternFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorPatternNodeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorPatternNodeValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorPatternValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorTypeFeatureValue;
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorTypeValue;
import org.emoflon.roam.intermediate.RoamIntermediate.Mapping;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingConstraint;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingObjective;
import org.emoflon.roam.intermediate.RoamIntermediate.MappingSumExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.Objective;
import org.emoflon.roam.intermediate.RoamIntermediate.ObjectiveFunctionValue;
import org.emoflon.roam.intermediate.RoamIntermediate.ObjectiveTarget;
import org.emoflon.roam.intermediate.RoamIntermediate.Pattern;
import org.emoflon.roam.intermediate.RoamIntermediate.PatternConstraint;
import org.emoflon.roam.intermediate.RoamIntermediate.PatternObjective;
import org.emoflon.roam.intermediate.RoamIntermediate.RelationalExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.RelationalOperator;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateFactory;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel;
import org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage;
import org.emoflon.roam.intermediate.RoamIntermediate.SetOperation;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamArithmeticOperator;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamBoolOperator;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamFilterOperation;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamOperation;
import org.emoflon.roam.intermediate.RoamIntermediate.StreamSelectOperation;
import org.emoflon.roam.intermediate.RoamIntermediate.SumExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.Type;
import org.emoflon.roam.intermediate.RoamIntermediate.TypeConstraint;
import org.emoflon.roam.intermediate.RoamIntermediate.TypeObjective;
import org.emoflon.roam.intermediate.RoamIntermediate.TypeSumExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticOperator;
import org.emoflon.roam.intermediate.RoamIntermediate.UnaryBoolOperator;
import org.emoflon.roam.intermediate.RoamIntermediate.ValueExpression;
import org.emoflon.roam.intermediate.RoamIntermediate.VariableSet;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 *
 * @generated
 */
public class RoamIntermediatePackageImpl extends EPackageImpl implements RoamIntermediatePackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass roamIntermediateModelEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass variableSetEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass patternEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass typeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass mappingEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass constraintEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass objectiveEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass globalObjectiveEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass contextEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass patternConstraintEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass typeConstraintEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass mappingConstraintEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass patternObjectiveEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass mappingObjectiveEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass typeObjectiveEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass arithmeticExpressionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass binaryArithmeticExpressionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass unaryArithmeticExpressionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass setOperationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass arithmeticValueExpressionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass arithmeticValueEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass arithmeticLiteralEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass integerLiteralEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass doubleLiteralEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass boolExpressionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass boolBinaryExpressionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass boolUnaryExpressionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass boolValueExpressionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass boolValueEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass boolStreamExpressionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass relationalExpressionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass boolLiteralEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass valueExpressionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass sumExpressionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass mappingSumExpressionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass typeSumExpressionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass contextTypeValueEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass contextPatternValueEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass contextPatternNodeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass contextMappingValueEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass contextMappingNodeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass objectiveFunctionValueEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass featureExpressionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass featureLiteralEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass contextTypeFeatureValueEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass contextPatternNodeFeatureValueEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass contextMappingNodeFeatureValueEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass iteratorEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass iteratorPatternValueEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass iteratorPatternFeatureValueEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass iteratorPatternNodeValueEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass iteratorPatternNodeFeatureValueEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass iteratorMappingValueEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass iteratorMappingFeatureValueEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass iteratorMappingNodeValueEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass iteratorMappingNodeFeatureValueEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass iteratorTypeValueEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass iteratorTypeFeatureValueEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass streamExpressionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass streamOperationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass streamFilterOperationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass streamSelectOperationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EEnum objectiveTargetEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EEnum relationalOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EEnum binaryArithmeticOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EEnum unaryArithmeticOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EEnum streamArithmeticOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EEnum binaryBoolOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EEnum unaryBoolOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EEnum streamBoolOperatorEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
	 * package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method
	 * {@link #init init()}, which also performs initialization of the package, or
	 * returns the registered package, if one already exists. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private RoamIntermediatePackageImpl() {
		super(eNS_URI, RoamIntermediateFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and
	 * for any others upon which it depends.
	 *
	 * <p>
	 * This method is used to initialize {@link RoamIntermediatePackage#eINSTANCE}
	 * when that field is accessed. Clients should not invoke it directly. Instead,
	 * they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static RoamIntermediatePackage init() {
		if (isInited)
			return (RoamIntermediatePackage) EPackage.Registry.INSTANCE.getEPackage(RoamIntermediatePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredRoamIntermediatePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		RoamIntermediatePackageImpl theRoamIntermediatePackage = registeredRoamIntermediatePackage instanceof RoamIntermediatePackageImpl
				? (RoamIntermediatePackageImpl) registeredRoamIntermediatePackage
				: new RoamIntermediatePackageImpl();

		isInited = true;

		// Initialize simple dependencies
		IBeXPatternModelPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theRoamIntermediatePackage.createPackageContents();

		// Initialize created meta-data
		theRoamIntermediatePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theRoamIntermediatePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(RoamIntermediatePackage.eNS_URI, theRoamIntermediatePackage);
		return theRoamIntermediatePackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getRoamIntermediateModel() {
		return roamIntermediateModelEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getRoamIntermediateModel_Name() {
		return (EAttribute) roamIntermediateModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getRoamIntermediateModel_Variables() {
		return (EReference) roamIntermediateModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getRoamIntermediateModel_Constraints() {
		return (EReference) roamIntermediateModelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getRoamIntermediateModel_Objectives() {
		return (EReference) roamIntermediateModelEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getRoamIntermediateModel_GlobalObjective() {
		return (EReference) roamIntermediateModelEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getRoamIntermediateModel_IbexModel() {
		return (EReference) roamIntermediateModelEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getVariableSet() {
		return variableSetEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getVariableSet_Name() {
		return (EAttribute) variableSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getPattern() {
		return patternEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getPattern_Pattern() {
		return (EReference) patternEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getPattern_IsRule() {
		return (EAttribute) patternEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getType() {
		return typeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getType_Type() {
		return (EReference) typeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getMapping() {
		return mappingEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getMapping_Rule() {
		return (EReference) mappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getConstraint() {
		return constraintEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getConstraint_Name() {
		return (EAttribute) constraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getConstraint_Elementwise() {
		return (EAttribute) constraintEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getConstraint_Expression() {
		return (EReference) constraintEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getObjective() {
		return objectiveEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getObjective_Name() {
		return (EAttribute) objectiveEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getObjective_Elementwise() {
		return (EAttribute) objectiveEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getObjective_Expression() {
		return (EReference) objectiveEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getGlobalObjective() {
		return globalObjectiveEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getGlobalObjective_Expression() {
		return (EReference) globalObjectiveEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getGlobalObjective_Target() {
		return (EAttribute) globalObjectiveEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getContext() {
		return contextEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getPatternConstraint() {
		return patternConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getPatternConstraint_Pattern() {
		return (EReference) patternConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getTypeConstraint() {
		return typeConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getTypeConstraint_ModelType() {
		return (EReference) typeConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getMappingConstraint() {
		return mappingConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getMappingConstraint_Mapping() {
		return (EReference) mappingConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getPatternObjective() {
		return patternObjectiveEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getPatternObjective_Pattern() {
		return (EReference) patternObjectiveEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getMappingObjective() {
		return mappingObjectiveEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getMappingObjective_Mapping() {
		return (EReference) mappingObjectiveEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getTypeObjective() {
		return typeObjectiveEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getTypeObjective_ModelType() {
		return (EReference) typeObjectiveEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getArithmeticExpression() {
		return arithmeticExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getArithmeticExpression_ReturnType() {
		return (EReference) arithmeticExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getBinaryArithmeticExpression() {
		return binaryArithmeticExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getBinaryArithmeticExpression_Lhs() {
		return (EReference) binaryArithmeticExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getBinaryArithmeticExpression_Rhs() {
		return (EReference) binaryArithmeticExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getBinaryArithmeticExpression_Operator() {
		return (EAttribute) binaryArithmeticExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getUnaryArithmeticExpression() {
		return unaryArithmeticExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getUnaryArithmeticExpression_Expression() {
		return (EReference) unaryArithmeticExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getUnaryArithmeticExpression_Operator() {
		return (EAttribute) unaryArithmeticExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getSetOperation() {
		return setOperationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getSetOperation_OperandName() {
		return (EAttribute) setOperationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getArithmeticValueExpression() {
		return arithmeticValueExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getArithmeticValue() {
		return arithmeticValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getArithmeticValue_Value() {
		return (EReference) arithmeticValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getArithmeticLiteral() {
		return arithmeticLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getIntegerLiteral() {
		return integerLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getIntegerLiteral_Literal() {
		return (EAttribute) integerLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getDoubleLiteral() {
		return doubleLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getDoubleLiteral_Literal() {
		return (EAttribute) doubleLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getBoolExpression() {
		return boolExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getBoolBinaryExpression() {
		return boolBinaryExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getBoolBinaryExpression_Lhs() {
		return (EReference) boolBinaryExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getBoolBinaryExpression_Rhs() {
		return (EReference) boolBinaryExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getBoolBinaryExpression_Operator() {
		return (EAttribute) boolBinaryExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getBoolUnaryExpression() {
		return boolUnaryExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getBoolUnaryExpression_Expression() {
		return (EReference) boolUnaryExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getBoolUnaryExpression_Operator() {
		return (EAttribute) boolUnaryExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getBoolValueExpression() {
		return boolValueExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getBoolValue() {
		return boolValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getBoolValue_Value() {
		return (EReference) boolValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getBoolStreamExpression() {
		return boolStreamExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getBoolStreamExpression_Stream() {
		return (EReference) boolStreamExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getBoolStreamExpression_Operator() {
		return (EAttribute) boolStreamExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getRelationalExpression() {
		return relationalExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getRelationalExpression_Operator() {
		return (EAttribute) relationalExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getRelationalExpression_Lhs() {
		return (EReference) relationalExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getRelationalExpression_Rhs() {
		return (EReference) relationalExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getBoolLiteral() {
		return boolLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getBoolLiteral_Literal() {
		return (EAttribute) boolLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getValueExpression() {
		return valueExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getValueExpression_ReturnType() {
		return (EReference) valueExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getSumExpression() {
		return sumExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getSumExpression_Expression() {
		return (EReference) sumExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getSumExpression_Filter() {
		return (EReference) sumExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getMappingSumExpression() {
		return mappingSumExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getMappingSumExpression_Mapping() {
		return (EReference) mappingSumExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getTypeSumExpression() {
		return typeSumExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getTypeSumExpression_Type() {
		return (EReference) typeSumExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getContextTypeValue() {
		return contextTypeValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getContextTypeValue_TypeContext() {
		return (EReference) contextTypeValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getContextPatternValue() {
		return contextPatternValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getContextPatternValue_PatternContext() {
		return (EReference) contextPatternValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getContextPatternNode() {
		return contextPatternNodeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getContextPatternNode_PatternContext() {
		return (EReference) contextPatternNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getContextPatternNode_Node() {
		return (EReference) contextPatternNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getContextMappingValue() {
		return contextMappingValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getContextMappingValue_MappingContext() {
		return (EReference) contextMappingValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getContextMappingNode() {
		return contextMappingNodeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getContextMappingNode_MappingContext() {
		return (EReference) contextMappingNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getContextMappingNode_Node() {
		return (EReference) contextMappingNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getObjectiveFunctionValue() {
		return objectiveFunctionValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getObjectiveFunctionValue_Objective() {
		return (EReference) objectiveFunctionValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getFeatureExpression() {
		return featureExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getFeatureExpression_Current() {
		return (EReference) featureExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getFeatureExpression_Child() {
		return (EReference) featureExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getFeatureLiteral() {
		return featureLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getFeatureLiteral_Feature() {
		return (EReference) featureLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getContextTypeFeatureValue() {
		return contextTypeFeatureValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getContextTypeFeatureValue_FeatureExpression() {
		return (EReference) contextTypeFeatureValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getContextPatternNodeFeatureValue() {
		return contextPatternNodeFeatureValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getContextPatternNodeFeatureValue_FeatureExpression() {
		return (EReference) contextPatternNodeFeatureValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getContextMappingNodeFeatureValue() {
		return contextMappingNodeFeatureValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getContextMappingNodeFeatureValue_FeatureExpression() {
		return (EReference) contextMappingNodeFeatureValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getIterator() {
		return iteratorEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getIterator_Stream() {
		return (EReference) iteratorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getIteratorPatternValue() {
		return iteratorPatternValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getIteratorPatternValue_PatternContext() {
		return (EReference) iteratorPatternValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getIteratorPatternFeatureValue() {
		return iteratorPatternFeatureValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getIteratorPatternFeatureValue_PatternContext() {
		return (EReference) iteratorPatternFeatureValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getIteratorPatternFeatureValue_FeatureExpression() {
		return (EReference) iteratorPatternFeatureValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getIteratorPatternNodeValue() {
		return iteratorPatternNodeValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getIteratorPatternNodeValue_PatternContext() {
		return (EReference) iteratorPatternNodeValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getIteratorPatternNodeValue_Node() {
		return (EReference) iteratorPatternNodeValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getIteratorPatternNodeFeatureValue() {
		return iteratorPatternNodeFeatureValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getIteratorPatternNodeFeatureValue_FeatureExpression() {
		return (EReference) iteratorPatternNodeFeatureValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getIteratorMappingValue() {
		return iteratorMappingValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getIteratorMappingValue_MappingContext() {
		return (EReference) iteratorMappingValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getIteratorMappingFeatureValue() {
		return iteratorMappingFeatureValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getIteratorMappingFeatureValue_MappingContext() {
		return (EReference) iteratorMappingFeatureValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getIteratorMappingFeatureValue_FeatureExpression() {
		return (EReference) iteratorMappingFeatureValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getIteratorMappingNodeValue() {
		return iteratorMappingNodeValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getIteratorMappingNodeValue_MappingContext() {
		return (EReference) iteratorMappingNodeValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getIteratorMappingNodeValue_Node() {
		return (EReference) iteratorMappingNodeValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getIteratorMappingNodeFeatureValue() {
		return iteratorMappingNodeFeatureValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getIteratorMappingNodeFeatureValue_FeatureExpression() {
		return (EReference) iteratorMappingNodeFeatureValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getIteratorTypeValue() {
		return iteratorTypeValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getIteratorTypeValue_TypeContext() {
		return (EReference) iteratorTypeValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getIteratorTypeFeatureValue() {
		return iteratorTypeFeatureValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getIteratorTypeFeatureValue_FeatureExpression() {
		return (EReference) iteratorTypeFeatureValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getStreamExpression() {
		return streamExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getStreamExpression_ReturnType() {
		return (EReference) streamExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getStreamExpression_Current() {
		return (EReference) streamExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getStreamExpression_Child() {
		return (EReference) streamExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getStreamOperation() {
		return streamOperationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getStreamFilterOperation() {
		return streamFilterOperationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getStreamFilterOperation_Predicate() {
		return (EReference) streamFilterOperationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getStreamSelectOperation() {
		return streamSelectOperationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getStreamSelectOperation_Type() {
		return (EReference) streamSelectOperationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EEnum getObjectiveTarget() {
		return objectiveTargetEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EEnum getRelationalOperator() {
		return relationalOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EEnum getBinaryArithmeticOperator() {
		return binaryArithmeticOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EEnum getUnaryArithmeticOperator() {
		return unaryArithmeticOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EEnum getStreamArithmeticOperator() {
		return streamArithmeticOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EEnum getBinaryBoolOperator() {
		return binaryBoolOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EEnum getUnaryBoolOperator() {
		return unaryBoolOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EEnum getStreamBoolOperator() {
		return streamBoolOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public RoamIntermediateFactory getRoamIntermediateFactory() {
		return (RoamIntermediateFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is guarded to
	 * have no affect on any invocation but its first. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		roamIntermediateModelEClass = createEClass(ROAM_INTERMEDIATE_MODEL);
		createEAttribute(roamIntermediateModelEClass, ROAM_INTERMEDIATE_MODEL__NAME);
		createEReference(roamIntermediateModelEClass, ROAM_INTERMEDIATE_MODEL__VARIABLES);
		createEReference(roamIntermediateModelEClass, ROAM_INTERMEDIATE_MODEL__CONSTRAINTS);
		createEReference(roamIntermediateModelEClass, ROAM_INTERMEDIATE_MODEL__OBJECTIVES);
		createEReference(roamIntermediateModelEClass, ROAM_INTERMEDIATE_MODEL__GLOBAL_OBJECTIVE);
		createEReference(roamIntermediateModelEClass, ROAM_INTERMEDIATE_MODEL__IBEX_MODEL);

		variableSetEClass = createEClass(VARIABLE_SET);
		createEAttribute(variableSetEClass, VARIABLE_SET__NAME);

		patternEClass = createEClass(PATTERN);
		createEReference(patternEClass, PATTERN__PATTERN);
		createEAttribute(patternEClass, PATTERN__IS_RULE);

		typeEClass = createEClass(TYPE);
		createEReference(typeEClass, TYPE__TYPE);

		mappingEClass = createEClass(MAPPING);
		createEReference(mappingEClass, MAPPING__RULE);

		constraintEClass = createEClass(CONSTRAINT);
		createEAttribute(constraintEClass, CONSTRAINT__NAME);
		createEAttribute(constraintEClass, CONSTRAINT__ELEMENTWISE);
		createEReference(constraintEClass, CONSTRAINT__EXPRESSION);

		objectiveEClass = createEClass(OBJECTIVE);
		createEAttribute(objectiveEClass, OBJECTIVE__NAME);
		createEAttribute(objectiveEClass, OBJECTIVE__ELEMENTWISE);
		createEReference(objectiveEClass, OBJECTIVE__EXPRESSION);

		globalObjectiveEClass = createEClass(GLOBAL_OBJECTIVE);
		createEReference(globalObjectiveEClass, GLOBAL_OBJECTIVE__EXPRESSION);
		createEAttribute(globalObjectiveEClass, GLOBAL_OBJECTIVE__TARGET);

		contextEClass = createEClass(CONTEXT);

		patternConstraintEClass = createEClass(PATTERN_CONSTRAINT);
		createEReference(patternConstraintEClass, PATTERN_CONSTRAINT__PATTERN);

		typeConstraintEClass = createEClass(TYPE_CONSTRAINT);
		createEReference(typeConstraintEClass, TYPE_CONSTRAINT__MODEL_TYPE);

		mappingConstraintEClass = createEClass(MAPPING_CONSTRAINT);
		createEReference(mappingConstraintEClass, MAPPING_CONSTRAINT__MAPPING);

		patternObjectiveEClass = createEClass(PATTERN_OBJECTIVE);
		createEReference(patternObjectiveEClass, PATTERN_OBJECTIVE__PATTERN);

		typeObjectiveEClass = createEClass(TYPE_OBJECTIVE);
		createEReference(typeObjectiveEClass, TYPE_OBJECTIVE__MODEL_TYPE);

		mappingObjectiveEClass = createEClass(MAPPING_OBJECTIVE);
		createEReference(mappingObjectiveEClass, MAPPING_OBJECTIVE__MAPPING);

		arithmeticExpressionEClass = createEClass(ARITHMETIC_EXPRESSION);
		createEReference(arithmeticExpressionEClass, ARITHMETIC_EXPRESSION__RETURN_TYPE);

		binaryArithmeticExpressionEClass = createEClass(BINARY_ARITHMETIC_EXPRESSION);
		createEReference(binaryArithmeticExpressionEClass, BINARY_ARITHMETIC_EXPRESSION__LHS);
		createEReference(binaryArithmeticExpressionEClass, BINARY_ARITHMETIC_EXPRESSION__RHS);
		createEAttribute(binaryArithmeticExpressionEClass, BINARY_ARITHMETIC_EXPRESSION__OPERATOR);

		unaryArithmeticExpressionEClass = createEClass(UNARY_ARITHMETIC_EXPRESSION);
		createEReference(unaryArithmeticExpressionEClass, UNARY_ARITHMETIC_EXPRESSION__EXPRESSION);
		createEAttribute(unaryArithmeticExpressionEClass, UNARY_ARITHMETIC_EXPRESSION__OPERATOR);

		setOperationEClass = createEClass(SET_OPERATION);
		createEAttribute(setOperationEClass, SET_OPERATION__OPERAND_NAME);

		arithmeticValueExpressionEClass = createEClass(ARITHMETIC_VALUE_EXPRESSION);

		arithmeticValueEClass = createEClass(ARITHMETIC_VALUE);
		createEReference(arithmeticValueEClass, ARITHMETIC_VALUE__VALUE);

		arithmeticLiteralEClass = createEClass(ARITHMETIC_LITERAL);

		integerLiteralEClass = createEClass(INTEGER_LITERAL);
		createEAttribute(integerLiteralEClass, INTEGER_LITERAL__LITERAL);

		doubleLiteralEClass = createEClass(DOUBLE_LITERAL);
		createEAttribute(doubleLiteralEClass, DOUBLE_LITERAL__LITERAL);

		boolExpressionEClass = createEClass(BOOL_EXPRESSION);

		boolBinaryExpressionEClass = createEClass(BOOL_BINARY_EXPRESSION);
		createEReference(boolBinaryExpressionEClass, BOOL_BINARY_EXPRESSION__LHS);
		createEReference(boolBinaryExpressionEClass, BOOL_BINARY_EXPRESSION__RHS);
		createEAttribute(boolBinaryExpressionEClass, BOOL_BINARY_EXPRESSION__OPERATOR);

		boolUnaryExpressionEClass = createEClass(BOOL_UNARY_EXPRESSION);
		createEReference(boolUnaryExpressionEClass, BOOL_UNARY_EXPRESSION__EXPRESSION);
		createEAttribute(boolUnaryExpressionEClass, BOOL_UNARY_EXPRESSION__OPERATOR);

		boolValueExpressionEClass = createEClass(BOOL_VALUE_EXPRESSION);

		boolValueEClass = createEClass(BOOL_VALUE);
		createEReference(boolValueEClass, BOOL_VALUE__VALUE);

		boolStreamExpressionEClass = createEClass(BOOL_STREAM_EXPRESSION);
		createEReference(boolStreamExpressionEClass, BOOL_STREAM_EXPRESSION__STREAM);
		createEAttribute(boolStreamExpressionEClass, BOOL_STREAM_EXPRESSION__OPERATOR);

		relationalExpressionEClass = createEClass(RELATIONAL_EXPRESSION);
		createEAttribute(relationalExpressionEClass, RELATIONAL_EXPRESSION__OPERATOR);
		createEReference(relationalExpressionEClass, RELATIONAL_EXPRESSION__LHS);
		createEReference(relationalExpressionEClass, RELATIONAL_EXPRESSION__RHS);

		boolLiteralEClass = createEClass(BOOL_LITERAL);
		createEAttribute(boolLiteralEClass, BOOL_LITERAL__LITERAL);

		valueExpressionEClass = createEClass(VALUE_EXPRESSION);
		createEReference(valueExpressionEClass, VALUE_EXPRESSION__RETURN_TYPE);

		sumExpressionEClass = createEClass(SUM_EXPRESSION);
		createEReference(sumExpressionEClass, SUM_EXPRESSION__EXPRESSION);
		createEReference(sumExpressionEClass, SUM_EXPRESSION__FILTER);

		mappingSumExpressionEClass = createEClass(MAPPING_SUM_EXPRESSION);
		createEReference(mappingSumExpressionEClass, MAPPING_SUM_EXPRESSION__MAPPING);

		typeSumExpressionEClass = createEClass(TYPE_SUM_EXPRESSION);
		createEReference(typeSumExpressionEClass, TYPE_SUM_EXPRESSION__TYPE);

		contextTypeValueEClass = createEClass(CONTEXT_TYPE_VALUE);
		createEReference(contextTypeValueEClass, CONTEXT_TYPE_VALUE__TYPE_CONTEXT);

		contextPatternValueEClass = createEClass(CONTEXT_PATTERN_VALUE);
		createEReference(contextPatternValueEClass, CONTEXT_PATTERN_VALUE__PATTERN_CONTEXT);

		contextPatternNodeEClass = createEClass(CONTEXT_PATTERN_NODE);
		createEReference(contextPatternNodeEClass, CONTEXT_PATTERN_NODE__PATTERN_CONTEXT);
		createEReference(contextPatternNodeEClass, CONTEXT_PATTERN_NODE__NODE);

		contextMappingValueEClass = createEClass(CONTEXT_MAPPING_VALUE);
		createEReference(contextMappingValueEClass, CONTEXT_MAPPING_VALUE__MAPPING_CONTEXT);

		contextMappingNodeEClass = createEClass(CONTEXT_MAPPING_NODE);
		createEReference(contextMappingNodeEClass, CONTEXT_MAPPING_NODE__MAPPING_CONTEXT);
		createEReference(contextMappingNodeEClass, CONTEXT_MAPPING_NODE__NODE);

		objectiveFunctionValueEClass = createEClass(OBJECTIVE_FUNCTION_VALUE);
		createEReference(objectiveFunctionValueEClass, OBJECTIVE_FUNCTION_VALUE__OBJECTIVE);

		featureExpressionEClass = createEClass(FEATURE_EXPRESSION);
		createEReference(featureExpressionEClass, FEATURE_EXPRESSION__CURRENT);
		createEReference(featureExpressionEClass, FEATURE_EXPRESSION__CHILD);

		featureLiteralEClass = createEClass(FEATURE_LITERAL);
		createEReference(featureLiteralEClass, FEATURE_LITERAL__FEATURE);

		contextTypeFeatureValueEClass = createEClass(CONTEXT_TYPE_FEATURE_VALUE);
		createEReference(contextTypeFeatureValueEClass, CONTEXT_TYPE_FEATURE_VALUE__FEATURE_EXPRESSION);

		contextPatternNodeFeatureValueEClass = createEClass(CONTEXT_PATTERN_NODE_FEATURE_VALUE);
		createEReference(contextPatternNodeFeatureValueEClass, CONTEXT_PATTERN_NODE_FEATURE_VALUE__FEATURE_EXPRESSION);

		contextMappingNodeFeatureValueEClass = createEClass(CONTEXT_MAPPING_NODE_FEATURE_VALUE);
		createEReference(contextMappingNodeFeatureValueEClass, CONTEXT_MAPPING_NODE_FEATURE_VALUE__FEATURE_EXPRESSION);

		iteratorEClass = createEClass(ITERATOR);
		createEReference(iteratorEClass, ITERATOR__STREAM);

		iteratorPatternValueEClass = createEClass(ITERATOR_PATTERN_VALUE);
		createEReference(iteratorPatternValueEClass, ITERATOR_PATTERN_VALUE__PATTERN_CONTEXT);

		iteratorPatternFeatureValueEClass = createEClass(ITERATOR_PATTERN_FEATURE_VALUE);
		createEReference(iteratorPatternFeatureValueEClass, ITERATOR_PATTERN_FEATURE_VALUE__PATTERN_CONTEXT);
		createEReference(iteratorPatternFeatureValueEClass, ITERATOR_PATTERN_FEATURE_VALUE__FEATURE_EXPRESSION);

		iteratorPatternNodeValueEClass = createEClass(ITERATOR_PATTERN_NODE_VALUE);
		createEReference(iteratorPatternNodeValueEClass, ITERATOR_PATTERN_NODE_VALUE__PATTERN_CONTEXT);
		createEReference(iteratorPatternNodeValueEClass, ITERATOR_PATTERN_NODE_VALUE__NODE);

		iteratorPatternNodeFeatureValueEClass = createEClass(ITERATOR_PATTERN_NODE_FEATURE_VALUE);
		createEReference(iteratorPatternNodeFeatureValueEClass,
				ITERATOR_PATTERN_NODE_FEATURE_VALUE__FEATURE_EXPRESSION);

		iteratorMappingValueEClass = createEClass(ITERATOR_MAPPING_VALUE);
		createEReference(iteratorMappingValueEClass, ITERATOR_MAPPING_VALUE__MAPPING_CONTEXT);

		iteratorMappingFeatureValueEClass = createEClass(ITERATOR_MAPPING_FEATURE_VALUE);
		createEReference(iteratorMappingFeatureValueEClass, ITERATOR_MAPPING_FEATURE_VALUE__MAPPING_CONTEXT);
		createEReference(iteratorMappingFeatureValueEClass, ITERATOR_MAPPING_FEATURE_VALUE__FEATURE_EXPRESSION);

		iteratorMappingNodeValueEClass = createEClass(ITERATOR_MAPPING_NODE_VALUE);
		createEReference(iteratorMappingNodeValueEClass, ITERATOR_MAPPING_NODE_VALUE__MAPPING_CONTEXT);
		createEReference(iteratorMappingNodeValueEClass, ITERATOR_MAPPING_NODE_VALUE__NODE);

		iteratorMappingNodeFeatureValueEClass = createEClass(ITERATOR_MAPPING_NODE_FEATURE_VALUE);
		createEReference(iteratorMappingNodeFeatureValueEClass,
				ITERATOR_MAPPING_NODE_FEATURE_VALUE__FEATURE_EXPRESSION);

		iteratorTypeValueEClass = createEClass(ITERATOR_TYPE_VALUE);
		createEReference(iteratorTypeValueEClass, ITERATOR_TYPE_VALUE__TYPE_CONTEXT);

		iteratorTypeFeatureValueEClass = createEClass(ITERATOR_TYPE_FEATURE_VALUE);
		createEReference(iteratorTypeFeatureValueEClass, ITERATOR_TYPE_FEATURE_VALUE__FEATURE_EXPRESSION);

		streamExpressionEClass = createEClass(STREAM_EXPRESSION);
		createEReference(streamExpressionEClass, STREAM_EXPRESSION__RETURN_TYPE);
		createEReference(streamExpressionEClass, STREAM_EXPRESSION__CURRENT);
		createEReference(streamExpressionEClass, STREAM_EXPRESSION__CHILD);

		streamOperationEClass = createEClass(STREAM_OPERATION);

		streamFilterOperationEClass = createEClass(STREAM_FILTER_OPERATION);
		createEReference(streamFilterOperationEClass, STREAM_FILTER_OPERATION__PREDICATE);

		streamSelectOperationEClass = createEClass(STREAM_SELECT_OPERATION);
		createEReference(streamSelectOperationEClass, STREAM_SELECT_OPERATION__TYPE);

		// Create enums
		objectiveTargetEEnum = createEEnum(OBJECTIVE_TARGET);
		relationalOperatorEEnum = createEEnum(RELATIONAL_OPERATOR);
		binaryArithmeticOperatorEEnum = createEEnum(BINARY_ARITHMETIC_OPERATOR);
		unaryArithmeticOperatorEEnum = createEEnum(UNARY_ARITHMETIC_OPERATOR);
		streamArithmeticOperatorEEnum = createEEnum(STREAM_ARITHMETIC_OPERATOR);
		binaryBoolOperatorEEnum = createEEnum(BINARY_BOOL_OPERATOR);
		unaryBoolOperatorEEnum = createEEnum(UNARY_BOOL_OPERATOR);
		streamBoolOperatorEEnum = createEEnum(STREAM_BOOL_OPERATOR);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This method is
	 * guarded to have no affect on any invocation but its first. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		IBeXPatternModelPackage theIBeXPatternModelPackage = (IBeXPatternModelPackage) EPackage.Registry.INSTANCE
				.getEPackage(IBeXPatternModelPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		patternEClass.getESuperTypes().add(this.getVariableSet());
		typeEClass.getESuperTypes().add(this.getVariableSet());
		mappingEClass.getESuperTypes().add(this.getVariableSet());
		patternConstraintEClass.getESuperTypes().add(this.getContext());
		patternConstraintEClass.getESuperTypes().add(this.getConstraint());
		typeConstraintEClass.getESuperTypes().add(this.getContext());
		typeConstraintEClass.getESuperTypes().add(this.getConstraint());
		mappingConstraintEClass.getESuperTypes().add(this.getContext());
		mappingConstraintEClass.getESuperTypes().add(this.getConstraint());
		patternObjectiveEClass.getESuperTypes().add(this.getContext());
		patternObjectiveEClass.getESuperTypes().add(this.getObjective());
		typeObjectiveEClass.getESuperTypes().add(this.getContext());
		typeObjectiveEClass.getESuperTypes().add(this.getObjective());
		mappingObjectiveEClass.getESuperTypes().add(this.getContext());
		mappingObjectiveEClass.getESuperTypes().add(this.getObjective());
		binaryArithmeticExpressionEClass.getESuperTypes().add(this.getArithmeticExpression());
		unaryArithmeticExpressionEClass.getESuperTypes().add(this.getArithmeticExpression());
		arithmeticValueExpressionEClass.getESuperTypes().add(this.getArithmeticExpression());
		arithmeticValueEClass.getESuperTypes().add(this.getArithmeticValueExpression());
		arithmeticLiteralEClass.getESuperTypes().add(this.getArithmeticValueExpression());
		integerLiteralEClass.getESuperTypes().add(this.getArithmeticLiteral());
		doubleLiteralEClass.getESuperTypes().add(this.getArithmeticLiteral());
		boolBinaryExpressionEClass.getESuperTypes().add(this.getBoolExpression());
		boolUnaryExpressionEClass.getESuperTypes().add(this.getBoolExpression());
		boolValueExpressionEClass.getESuperTypes().add(this.getBoolExpression());
		boolValueEClass.getESuperTypes().add(this.getBoolValueExpression());
		boolStreamExpressionEClass.getESuperTypes().add(this.getBoolValueExpression());
		relationalExpressionEClass.getESuperTypes().add(this.getBoolValueExpression());
		boolLiteralEClass.getESuperTypes().add(this.getBoolValueExpression());
		sumExpressionEClass.getESuperTypes().add(this.getValueExpression());
		sumExpressionEClass.getESuperTypes().add(this.getSetOperation());
		mappingSumExpressionEClass.getESuperTypes().add(this.getSumExpression());
		typeSumExpressionEClass.getESuperTypes().add(this.getSumExpression());
		contextTypeValueEClass.getESuperTypes().add(this.getValueExpression());
		contextPatternValueEClass.getESuperTypes().add(this.getValueExpression());
		contextPatternNodeEClass.getESuperTypes().add(this.getValueExpression());
		contextMappingValueEClass.getESuperTypes().add(this.getValueExpression());
		contextMappingNodeEClass.getESuperTypes().add(this.getValueExpression());
		objectiveFunctionValueEClass.getESuperTypes().add(this.getValueExpression());
		contextTypeFeatureValueEClass.getESuperTypes().add(this.getContextTypeValue());
		contextPatternNodeFeatureValueEClass.getESuperTypes().add(this.getContextPatternNode());
		contextMappingNodeFeatureValueEClass.getESuperTypes().add(this.getContextMappingNode());
		iteratorPatternValueEClass.getESuperTypes().add(this.getValueExpression());
		iteratorPatternValueEClass.getESuperTypes().add(this.getIterator());
		iteratorPatternFeatureValueEClass.getESuperTypes().add(this.getValueExpression());
		iteratorPatternFeatureValueEClass.getESuperTypes().add(this.getIterator());
		iteratorPatternNodeValueEClass.getESuperTypes().add(this.getValueExpression());
		iteratorPatternNodeValueEClass.getESuperTypes().add(this.getIterator());
		iteratorPatternNodeFeatureValueEClass.getESuperTypes().add(this.getIteratorPatternNodeValue());
		iteratorMappingValueEClass.getESuperTypes().add(this.getValueExpression());
		iteratorMappingValueEClass.getESuperTypes().add(this.getIterator());
		iteratorMappingFeatureValueEClass.getESuperTypes().add(this.getValueExpression());
		iteratorMappingFeatureValueEClass.getESuperTypes().add(this.getIterator());
		iteratorMappingNodeValueEClass.getESuperTypes().add(this.getValueExpression());
		iteratorMappingNodeValueEClass.getESuperTypes().add(this.getIterator());
		iteratorMappingNodeFeatureValueEClass.getESuperTypes().add(this.getIteratorMappingNodeValue());
		iteratorTypeValueEClass.getESuperTypes().add(this.getValueExpression());
		iteratorTypeValueEClass.getESuperTypes().add(this.getIterator());
		iteratorTypeFeatureValueEClass.getESuperTypes().add(this.getIteratorTypeValue());
		streamExpressionEClass.getESuperTypes().add(this.getSetOperation());
		streamFilterOperationEClass.getESuperTypes().add(this.getStreamOperation());
		streamSelectOperationEClass.getESuperTypes().add(this.getStreamOperation());

		// Initialize classes, features, and operations; add parameters
		initEClass(roamIntermediateModelEClass, RoamIntermediateModel.class, "RoamIntermediateModel", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRoamIntermediateModel_Name(), ecorePackage.getEString(), "name", null, 0, 1,
				RoamIntermediateModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRoamIntermediateModel_Variables(), this.getVariableSet(), null, "variables", null, 0, -1,
				RoamIntermediateModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRoamIntermediateModel_Constraints(), this.getConstraint(), null, "constraints", null, 0, -1,
				RoamIntermediateModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRoamIntermediateModel_Objectives(), this.getObjective(), null, "objectives", null, 0, -1,
				RoamIntermediateModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRoamIntermediateModel_GlobalObjective(), this.getGlobalObjective(), null, "globalObjective",
				null, 0, 1, RoamIntermediateModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRoamIntermediateModel_IbexModel(), theIBeXPatternModelPackage.getIBeXModel(), null,
				"ibexModel", null, 1, 1, RoamIntermediateModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(variableSetEClass, VariableSet.class, "VariableSet", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVariableSet_Name(), ecorePackage.getEString(), "name", null, 0, 1, VariableSet.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(patternEClass, Pattern.class, "Pattern", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPattern_Pattern(), theIBeXPatternModelPackage.getIBeXContext(), null, "pattern", null, 1, 1,
				Pattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPattern_IsRule(), ecorePackage.getEBoolean(), "isRule", null, 0, 1, Pattern.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(typeEClass, Type.class, "Type", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getType_Type(), ecorePackage.getEClass(), null, "type", null, 1, 1, Type.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(mappingEClass, Mapping.class, "Mapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMapping_Rule(), theIBeXPatternModelPackage.getIBeXRule(), null, "rule", null, 1, 1,
				Mapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(constraintEClass, Constraint.class, "Constraint", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConstraint_Name(), ecorePackage.getEString(), "name", null, 0, 1, Constraint.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConstraint_Elementwise(), ecorePackage.getEBoolean(), "elementwise", null, 0, 1,
				Constraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getConstraint_Expression(), this.getRelationalExpression(), null, "expression", null, 1, 1,
				Constraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(objectiveEClass, Objective.class, "Objective", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getObjective_Name(), ecorePackage.getEString(), "name", null, 0, 1, Objective.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getObjective_Elementwise(), ecorePackage.getEBoolean(), "elementwise", null, 0, 1,
				Objective.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getObjective_Expression(), this.getArithmeticExpression(), null, "expression", null, 0, 1,
				Objective.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(globalObjectiveEClass, GlobalObjective.class, "GlobalObjective", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGlobalObjective_Expression(), this.getArithmeticExpression(), null, "expression", null, 0, 1,
				GlobalObjective.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGlobalObjective_Target(), this.getObjectiveTarget(), "target", null, 0, 1,
				GlobalObjective.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(contextEClass, Context.class, "Context", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(patternConstraintEClass, PatternConstraint.class, "PatternConstraint", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPatternConstraint_Pattern(), this.getPattern(), null, "pattern", null, 0, 1,
				PatternConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(typeConstraintEClass, TypeConstraint.class, "TypeConstraint", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTypeConstraint_ModelType(), this.getType(), null, "modelType", null, 0, 1,
				TypeConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mappingConstraintEClass, MappingConstraint.class, "MappingConstraint", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMappingConstraint_Mapping(), this.getMapping(), null, "mapping", null, 1, 1,
				MappingConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(patternObjectiveEClass, PatternObjective.class, "PatternObjective", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPatternObjective_Pattern(), this.getPattern(), null, "pattern", null, 0, 1,
				PatternObjective.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(typeObjectiveEClass, TypeObjective.class, "TypeObjective", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTypeObjective_ModelType(), this.getType(), null, "modelType", null, 0, 1, TypeObjective.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mappingObjectiveEClass, MappingObjective.class, "MappingObjective", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMappingObjective_Mapping(), this.getMapping(), null, "mapping", null, 1, 1,
				MappingObjective.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(arithmeticExpressionEClass, ArithmeticExpression.class, "ArithmeticExpression", IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getArithmeticExpression_ReturnType(), ecorePackage.getEDataType(), null, "returnType", null, 1,
				1, ArithmeticExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(binaryArithmeticExpressionEClass, BinaryArithmeticExpression.class, "BinaryArithmeticExpression",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBinaryArithmeticExpression_Lhs(), this.getArithmeticExpression(), null, "lhs", null, 1, 1,
				BinaryArithmeticExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBinaryArithmeticExpression_Rhs(), this.getArithmeticExpression(), null, "rhs", null, 1, 1,
				BinaryArithmeticExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBinaryArithmeticExpression_Operator(), this.getBinaryArithmeticOperator(), "operator", null,
				0, 1, BinaryArithmeticExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(unaryArithmeticExpressionEClass, UnaryArithmeticExpression.class, "UnaryArithmeticExpression",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getUnaryArithmeticExpression_Expression(), this.getArithmeticExpression(), null, "expression",
				null, 1, 1, UnaryArithmeticExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUnaryArithmeticExpression_Operator(), this.getUnaryArithmeticOperator(), "operator", null, 0,
				1, UnaryArithmeticExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(setOperationEClass, SetOperation.class, "SetOperation", IS_ABSTRACT, IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSetOperation_OperandName(), ecorePackage.getEString(), "operandName", null, 0, 1,
				SetOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(arithmeticValueExpressionEClass, ArithmeticValueExpression.class, "ArithmeticValueExpression",
				IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(arithmeticValueEClass, ArithmeticValue.class, "ArithmeticValue", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getArithmeticValue_Value(), this.getValueExpression(), null, "value", null, 1, 1,
				ArithmeticValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(arithmeticLiteralEClass, ArithmeticLiteral.class, "ArithmeticLiteral", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(integerLiteralEClass, IntegerLiteral.class, "IntegerLiteral", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIntegerLiteral_Literal(), ecorePackage.getEInt(), "literal", null, 1, 1, IntegerLiteral.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(doubleLiteralEClass, DoubleLiteral.class, "DoubleLiteral", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDoubleLiteral_Literal(), ecorePackage.getEDouble(), "literal", null, 0, 1,
				DoubleLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(boolExpressionEClass, BoolExpression.class, "BoolExpression", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(boolBinaryExpressionEClass, BoolBinaryExpression.class, "BoolBinaryExpression", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBoolBinaryExpression_Lhs(), this.getBoolExpression(), null, "lhs", null, 1, 1,
				BoolBinaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBoolBinaryExpression_Rhs(), this.getBoolExpression(), null, "rhs", null, 1, 1,
				BoolBinaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBoolBinaryExpression_Operator(), this.getBinaryBoolOperator(), "operator", null, 0, 1,
				BoolBinaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(boolUnaryExpressionEClass, BoolUnaryExpression.class, "BoolUnaryExpression", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBoolUnaryExpression_Expression(), this.getBoolExpression(), null, "expression", null, 1, 1,
				BoolUnaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBoolUnaryExpression_Operator(), this.getUnaryBoolOperator(), "operator", null, 0, 1,
				BoolUnaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(boolValueExpressionEClass, BoolValueExpression.class, "BoolValueExpression", IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(boolValueEClass, BoolValue.class, "BoolValue", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBoolValue_Value(), this.getValueExpression(), null, "value", null, 1, 1, BoolValue.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(boolStreamExpressionEClass, BoolStreamExpression.class, "BoolStreamExpression", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBoolStreamExpression_Stream(), this.getStreamExpression(), null, "stream", null, 1, 1,
				BoolStreamExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBoolStreamExpression_Operator(), this.getStreamBoolOperator(), "operator", null, 0, 1,
				BoolStreamExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(relationalExpressionEClass, RelationalExpression.class, "RelationalExpression", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRelationalExpression_Operator(), this.getRelationalOperator(), "operator", null, 0, 1,
				RelationalExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRelationalExpression_Lhs(), this.getArithmeticExpression(), null, "lhs", null, 1, 1,
				RelationalExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRelationalExpression_Rhs(), this.getArithmeticExpression(), null, "rhs", null, 1, 1,
				RelationalExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(boolLiteralEClass, BoolLiteral.class, "BoolLiteral", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBoolLiteral_Literal(), ecorePackage.getEBoolean(), "literal", null, 0, 1, BoolLiteral.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(valueExpressionEClass, ValueExpression.class, "ValueExpression", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getValueExpression_ReturnType(), ecorePackage.getEClassifier(), null, "returnType", null, 0, 1,
				ValueExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sumExpressionEClass, SumExpression.class, "SumExpression", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSumExpression_Expression(), this.getArithmeticExpression(), null, "expression", null, 1, 1,
				SumExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSumExpression_Filter(), this.getStreamExpression(), null, "filter", null, 0, 1,
				SumExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mappingSumExpressionEClass, MappingSumExpression.class, "MappingSumExpression", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMappingSumExpression_Mapping(), this.getMapping(), null, "mapping", null, 1, 1,
				MappingSumExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(typeSumExpressionEClass, TypeSumExpression.class, "TypeSumExpression", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTypeSumExpression_Type(), this.getType(), null, "type", null, 1, 1, TypeSumExpression.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(contextTypeValueEClass, ContextTypeValue.class, "ContextTypeValue", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getContextTypeValue_TypeContext(), this.getType(), null, "typeContext", null, 0, 1,
				ContextTypeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(contextPatternValueEClass, ContextPatternValue.class, "ContextPatternValue", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getContextPatternValue_PatternContext(), this.getPattern(), null, "patternContext", null, 0, 1,
				ContextPatternValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(contextPatternNodeEClass, ContextPatternNode.class, "ContextPatternNode", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getContextPatternNode_PatternContext(), this.getPattern(), null, "patternContext", null, 0, 1,
				ContextPatternNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getContextPatternNode_Node(), theIBeXPatternModelPackage.getIBeXNode(), null, "node", null, 0, 1,
				ContextPatternNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(contextMappingValueEClass, ContextMappingValue.class, "ContextMappingValue", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getContextMappingValue_MappingContext(), this.getMapping(), null, "mappingContext", null, 0, 1,
				ContextMappingValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(contextMappingNodeEClass, ContextMappingNode.class, "ContextMappingNode", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getContextMappingNode_MappingContext(), this.getMapping(), null, "mappingContext", null, 0, 1,
				ContextMappingNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getContextMappingNode_Node(), theIBeXPatternModelPackage.getIBeXNode(), null, "node", null, 0, 1,
				ContextMappingNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(objectiveFunctionValueEClass, ObjectiveFunctionValue.class, "ObjectiveFunctionValue", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getObjectiveFunctionValue_Objective(), this.getObjective(), null, "objective", null, 1, 1,
				ObjectiveFunctionValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(featureExpressionEClass, FeatureExpression.class, "FeatureExpression", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFeatureExpression_Current(), this.getFeatureLiteral(), null, "current", null, 1, 1,
				FeatureExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFeatureExpression_Child(), this.getFeatureExpression(), null, "child", null, 1, 1,
				FeatureExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(featureLiteralEClass, FeatureLiteral.class, "FeatureLiteral", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFeatureLiteral_Feature(), ecorePackage.getEStructuralFeature(), null, "feature", null, 0, 1,
				FeatureLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(contextTypeFeatureValueEClass, ContextTypeFeatureValue.class, "ContextTypeFeatureValue",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getContextTypeFeatureValue_FeatureExpression(), this.getFeatureExpression(), null,
				"featureExpression", null, 0, 1, ContextTypeFeatureValue.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(contextPatternNodeFeatureValueEClass, ContextPatternNodeFeatureValue.class,
				"ContextPatternNodeFeatureValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getContextPatternNodeFeatureValue_FeatureExpression(), this.getFeatureExpression(), null,
				"featureExpression", null, 0, 1, ContextPatternNodeFeatureValue.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(contextMappingNodeFeatureValueEClass, ContextMappingNodeFeatureValue.class,
				"ContextMappingNodeFeatureValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getContextMappingNodeFeatureValue_FeatureExpression(), this.getFeatureExpression(), null,
				"featureExpression", null, 0, 1, ContextMappingNodeFeatureValue.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iteratorEClass, Iterator.class, "Iterator", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIterator_Stream(), this.getSetOperation(), null, "stream", null, 0, 1, Iterator.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iteratorPatternValueEClass, IteratorPatternValue.class, "IteratorPatternValue", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIteratorPatternValue_PatternContext(), this.getPattern(), null, "patternContext", null, 0, 1,
				IteratorPatternValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iteratorPatternFeatureValueEClass, IteratorPatternFeatureValue.class, "IteratorPatternFeatureValue",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIteratorPatternFeatureValue_PatternContext(), this.getPattern(), null, "patternContext", null,
				0, 1, IteratorPatternFeatureValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIteratorPatternFeatureValue_FeatureExpression(), this.getFeatureExpression(), null,
				"featureExpression", null, 0, 1, IteratorPatternFeatureValue.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iteratorPatternNodeValueEClass, IteratorPatternNodeValue.class, "IteratorPatternNodeValue",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIteratorPatternNodeValue_PatternContext(), this.getPattern(), null, "patternContext", null, 0,
				1, IteratorPatternNodeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIteratorPatternNodeValue_Node(), theIBeXPatternModelPackage.getIBeXNode(), null, "node", null,
				0, 1, IteratorPatternNodeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iteratorPatternNodeFeatureValueEClass, IteratorPatternNodeFeatureValue.class,
				"IteratorPatternNodeFeatureValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIteratorPatternNodeFeatureValue_FeatureExpression(), this.getFeatureExpression(), null,
				"featureExpression", null, 0, 1, IteratorPatternNodeFeatureValue.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iteratorMappingValueEClass, IteratorMappingValue.class, "IteratorMappingValue", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIteratorMappingValue_MappingContext(), this.getMapping(), null, "mappingContext", null, 0, 1,
				IteratorMappingValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iteratorMappingFeatureValueEClass, IteratorMappingFeatureValue.class, "IteratorMappingFeatureValue",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIteratorMappingFeatureValue_MappingContext(), this.getMapping(), null, "mappingContext", null,
				0, 1, IteratorMappingFeatureValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIteratorMappingFeatureValue_FeatureExpression(), this.getFeatureExpression(), null,
				"featureExpression", null, 0, 1, IteratorMappingFeatureValue.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iteratorMappingNodeValueEClass, IteratorMappingNodeValue.class, "IteratorMappingNodeValue",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIteratorMappingNodeValue_MappingContext(), this.getMapping(), null, "mappingContext", null, 0,
				1, IteratorMappingNodeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIteratorMappingNodeValue_Node(), theIBeXPatternModelPackage.getIBeXNode(), null, "node", null,
				0, 1, IteratorMappingNodeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iteratorMappingNodeFeatureValueEClass, IteratorMappingNodeFeatureValue.class,
				"IteratorMappingNodeFeatureValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIteratorMappingNodeFeatureValue_FeatureExpression(), this.getFeatureExpression(), null,
				"featureExpression", null, 0, 1, IteratorMappingNodeFeatureValue.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iteratorTypeValueEClass, IteratorTypeValue.class, "IteratorTypeValue", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIteratorTypeValue_TypeContext(), this.getType(), null, "typeContext", null, 0, 1,
				IteratorTypeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iteratorTypeFeatureValueEClass, IteratorTypeFeatureValue.class, "IteratorTypeFeatureValue",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIteratorTypeFeatureValue_FeatureExpression(), this.getFeatureExpression(), null,
				"featureExpression", null, 0, 1, IteratorTypeFeatureValue.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(streamExpressionEClass, StreamExpression.class, "StreamExpression", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStreamExpression_ReturnType(), ecorePackage.getEClassifier(), null, "returnType", null, 0, 1,
				StreamExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStreamExpression_Current(), this.getStreamOperation(), null, "current", null, 1, 1,
				StreamExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStreamExpression_Child(), this.getStreamExpression(), null, "child", null, 1, 1,
				StreamExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(streamOperationEClass, StreamOperation.class, "StreamOperation", IS_ABSTRACT, IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(streamFilterOperationEClass, StreamFilterOperation.class, "StreamFilterOperation", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStreamFilterOperation_Predicate(), this.getBoolExpression(), null, "predicate", null, 0, 1,
				StreamFilterOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(streamSelectOperationEClass, StreamSelectOperation.class, "StreamSelectOperation", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStreamSelectOperation_Type(), ecorePackage.getEClass(), null, "type", null, 0, 1,
				StreamSelectOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(objectiveTargetEEnum, ObjectiveTarget.class, "ObjectiveTarget");
		addEEnumLiteral(objectiveTargetEEnum, ObjectiveTarget.MIN);
		addEEnumLiteral(objectiveTargetEEnum, ObjectiveTarget.MAX);

		initEEnum(relationalOperatorEEnum, RelationalOperator.class, "RelationalOperator");
		addEEnumLiteral(relationalOperatorEEnum, RelationalOperator.LESS);
		addEEnumLiteral(relationalOperatorEEnum, RelationalOperator.LESS_OR_EQUAL);
		addEEnumLiteral(relationalOperatorEEnum, RelationalOperator.EQUAL);
		addEEnumLiteral(relationalOperatorEEnum, RelationalOperator.GREATER_OR_EQUAL);
		addEEnumLiteral(relationalOperatorEEnum, RelationalOperator.GREATER);
		addEEnumLiteral(relationalOperatorEEnum, RelationalOperator.NOT_EQUAL);

		initEEnum(binaryArithmeticOperatorEEnum, BinaryArithmeticOperator.class, "BinaryArithmeticOperator");
		addEEnumLiteral(binaryArithmeticOperatorEEnum, BinaryArithmeticOperator.ADD);
		addEEnumLiteral(binaryArithmeticOperatorEEnum, BinaryArithmeticOperator.SUBTRACT);
		addEEnumLiteral(binaryArithmeticOperatorEEnum, BinaryArithmeticOperator.MULTIPLY);
		addEEnumLiteral(binaryArithmeticOperatorEEnum, BinaryArithmeticOperator.DIVIDE);
		addEEnumLiteral(binaryArithmeticOperatorEEnum, BinaryArithmeticOperator.POW);

		initEEnum(unaryArithmeticOperatorEEnum, UnaryArithmeticOperator.class, "UnaryArithmeticOperator");
		addEEnumLiteral(unaryArithmeticOperatorEEnum, UnaryArithmeticOperator.BRACKET);
		addEEnumLiteral(unaryArithmeticOperatorEEnum, UnaryArithmeticOperator.NEGATE);
		addEEnumLiteral(unaryArithmeticOperatorEEnum, UnaryArithmeticOperator.ABSOLUTE);
		addEEnumLiteral(unaryArithmeticOperatorEEnum, UnaryArithmeticOperator.SINE);
		addEEnumLiteral(unaryArithmeticOperatorEEnum, UnaryArithmeticOperator.COSINE);
		addEEnumLiteral(unaryArithmeticOperatorEEnum, UnaryArithmeticOperator.SQRT);

		initEEnum(streamArithmeticOperatorEEnum, StreamArithmeticOperator.class, "StreamArithmeticOperator");
		addEEnumLiteral(streamArithmeticOperatorEEnum, StreamArithmeticOperator.COUNT);

		initEEnum(binaryBoolOperatorEEnum, BinaryBoolOperator.class, "BinaryBoolOperator");
		addEEnumLiteral(binaryBoolOperatorEEnum, BinaryBoolOperator.AND);
		addEEnumLiteral(binaryBoolOperatorEEnum, BinaryBoolOperator.OR);

		initEEnum(unaryBoolOperatorEEnum, UnaryBoolOperator.class, "UnaryBoolOperator");
		addEEnumLiteral(unaryBoolOperatorEEnum, UnaryBoolOperator.NOT);

		initEEnum(streamBoolOperatorEEnum, StreamBoolOperator.class, "StreamBoolOperator");
		addEEnumLiteral(streamBoolOperatorEEnum, StreamBoolOperator.EXISTS);
		addEEnumLiteral(streamBoolOperatorEEnum, StreamBoolOperator.NOTEXISTS);

		// Create resource
		createResource(eNS_URI);
	}

} // RoamIntermediatePackageImpl
