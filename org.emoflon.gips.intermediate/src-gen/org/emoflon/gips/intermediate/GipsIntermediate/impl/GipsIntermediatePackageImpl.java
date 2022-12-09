/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticNullLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticValueExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryBoolOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolBinaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolStreamExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolUnaryExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolValue;
import org.emoflon.gips.intermediate.GipsIntermediate.BoolValueExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Constraint;
import org.emoflon.gips.intermediate.GipsIntermediate.Context;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNode;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNodeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingVariablesReference;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNode;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNodeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextSumExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeValue;
import org.emoflon.gips.intermediate.GipsIntermediate.DoubleLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.FeatureExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.FeatureLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.GTMapping;
import org.emoflon.gips.intermediate.GipsIntermediate.GTParameterVariable;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateFactory;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel;
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.GlobalConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.GlobalObjective;
import org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig;
import org.emoflon.gips.intermediate.GipsIntermediate.ILPSolverType;
import org.emoflon.gips.intermediate.GipsIntermediate.IntegerLiteral;
import org.emoflon.gips.intermediate.GipsIntermediate.Iterator;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingVariableValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingVariablesReference;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeFeatureValue;
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeValue;
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingObjective;
import org.emoflon.gips.intermediate.GipsIntermediate.MappingSumExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Objective;
import org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveFunctionValue;
import org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveTarget;
import org.emoflon.gips.intermediate.GipsIntermediate.Pattern;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternMapping;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternObjective;
import org.emoflon.gips.intermediate.GipsIntermediate.PatternSumExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.SetOperation;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamArithmeticOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamBoolOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamContainsOperation;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamFilterOperation;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamNoOperation;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamOperation;
import org.emoflon.gips.intermediate.GipsIntermediate.StreamSelectOperation;
import org.emoflon.gips.intermediate.GipsIntermediate.SumExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Type;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeConstraint;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeObjective;
import org.emoflon.gips.intermediate.GipsIntermediate.TypeSumExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.UnaryBoolOperator;
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression;
import org.emoflon.gips.intermediate.GipsIntermediate.Variable;
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference;
import org.emoflon.gips.intermediate.GipsIntermediate.VariableSet;
import org.emoflon.gips.intermediate.GipsIntermediate.VariableType;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class GipsIntermediatePackageImpl extends EPackageImpl implements GipsIntermediatePackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass gipsIntermediateModelEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass ilpConfigEClass = null;

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
	private EClass gtMappingEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass patternMappingEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass variableEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass gtParameterVariableEClass = null;

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
	private EClass globalConstraintEClass = null;

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
	private EClass typeObjectiveEClass = null;

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
	private EClass variableReferenceEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass arithmeticNullLiteralEClass = null;

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
	private EClass contextSumExpressionEClass = null;

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
	private EClass patternSumExpressionEClass = null;

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
	private EClass contextMappingVariablesReferenceEClass = null;

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
	private EClass iteratorMappingVariableValueEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass iteratorMappingVariablesReferenceEClass = null;

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
	private EClass streamNoOperationEClass = null;

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
	private EClass streamContainsOperationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum ilpSolverTypeEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum variableTypeEEnum = null;

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
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private GipsIntermediatePackageImpl() {
		super(eNS_URI, GipsIntermediateFactory.eINSTANCE);
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
	 * This method is used to initialize {@link GipsIntermediatePackage#eINSTANCE}
	 * when that field is accessed. Clients should not invoke it directly. Instead,
	 * they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static GipsIntermediatePackage init() {
		if (isInited)
			return (GipsIntermediatePackage) EPackage.Registry.INSTANCE.getEPackage(GipsIntermediatePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredGipsIntermediatePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		GipsIntermediatePackageImpl theGipsIntermediatePackage = registeredGipsIntermediatePackage instanceof GipsIntermediatePackageImpl
				? (GipsIntermediatePackageImpl) registeredGipsIntermediatePackage
				: new GipsIntermediatePackageImpl();

		isInited = true;

		// Initialize simple dependencies
		IBeXPatternModelPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theGipsIntermediatePackage.createPackageContents();

		// Initialize created meta-data
		theGipsIntermediatePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theGipsIntermediatePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(GipsIntermediatePackage.eNS_URI, theGipsIntermediatePackage);
		return theGipsIntermediatePackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getGipsIntermediateModel() {
		return gipsIntermediateModelEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getGipsIntermediateModel_Name() {
		return (EAttribute) gipsIntermediateModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getGipsIntermediateModel_Variables() {
		return (EReference) gipsIntermediateModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getGipsIntermediateModel_Constraints() {
		return (EReference) gipsIntermediateModelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getGipsIntermediateModel_Objectives() {
		return (EReference) gipsIntermediateModelEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getGipsIntermediateModel_GlobalObjective() {
		return (EReference) gipsIntermediateModelEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getGipsIntermediateModel_IbexModel() {
		return (EReference) gipsIntermediateModelEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getGipsIntermediateModel_Config() {
		return (EReference) gipsIntermediateModelEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getILPConfig() {
		return ilpConfigEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getILPConfig_Solver() {
		return (EAttribute) ilpConfigEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getILPConfig_SolverHomeDir() {
		return (EAttribute) ilpConfigEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getILPConfig_SolverLicenseFile() {
		return (EAttribute) ilpConfigEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getILPConfig_BuildLaunchConfig() {
		return (EAttribute) ilpConfigEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getILPConfig_MainFile() {
		return (EAttribute) ilpConfigEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getILPConfig_EnableTimeLimit() {
		return (EAttribute) ilpConfigEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getILPConfig_IlpTimeLimit() {
		return (EAttribute) ilpConfigEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getILPConfig_EnableRndSeed() {
		return (EAttribute) ilpConfigEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getILPConfig_IlpRndSeed() {
		return (EAttribute) ilpConfigEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getILPConfig_Presolve() {
		return (EAttribute) ilpConfigEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getILPConfig_EnablePresolve() {
		return (EAttribute) ilpConfigEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getILPConfig_EnableDebugOutput() {
		return (EAttribute) ilpConfigEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getILPConfig_EnableCustomTolerance() {
		return (EAttribute) ilpConfigEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getILPConfig_Tolerance() {
		return (EAttribute) ilpConfigEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getILPConfig_EnableLpOutput() {
		return (EAttribute) ilpConfigEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getILPConfig_LpPath() {
		return (EAttribute) ilpConfigEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getVariableSet() {
		return variableSetEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getVariableSet_Name() {
		return (EAttribute) variableSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getVariableSet_UpperBound() {
		return (EAttribute) variableSetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getVariableSet_LowerBound() {
		return (EAttribute) variableSetEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getPattern() {
		return patternEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getPattern_Pattern() {
		return (EReference) patternEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getPattern_IsRule() {
		return (EAttribute) patternEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getType() {
		return typeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getType_Type() {
		return (EReference) typeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getMapping() {
		return mappingEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getMapping_ContextPattern() {
		return (EReference) mappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getMapping_FreeVariables() {
		return (EReference) mappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getMapping_BoundVariables() {
		return (EReference) mappingEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getGTMapping() {
		return gtMappingEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getGTMapping_Rule() {
		return (EReference) gtMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getPatternMapping() {
		return patternMappingEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getPatternMapping_Pattern() {
		return (EReference) patternMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getVariable() {
		return variableEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getVariable_Type() {
		return (EAttribute) variableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getGTParameterVariable() {
		return gtParameterVariableEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getGTParameterVariable_Parameter() {
		return (EReference) gtParameterVariableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getGTParameterVariable_Rule() {
		return (EReference) gtParameterVariableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getConstraint() {
		return constraintEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getConstraint_Name() {
		return (EAttribute) constraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getConstraint_Depending() {
		return (EAttribute) constraintEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getConstraint_Expression() {
		return (EReference) constraintEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getConstraint_Constant() {
		return (EAttribute) constraintEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getConstraint_Negated() {
		return (EAttribute) constraintEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getConstraint_Dependencies() {
		return (EReference) constraintEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getConstraint_ReferencedBy() {
		return (EReference) constraintEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getConstraint_SymbolicVariable() {
		return (EReference) constraintEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getConstraint_HelperVariables() {
		return (EReference) constraintEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getConstraint_HelperConstraints() {
		return (EReference) constraintEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getGlobalConstraint() {
		return globalConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getObjective() {
		return objectiveEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getObjective_Name() {
		return (EAttribute) objectiveEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getObjective_Elementwise() {
		return (EAttribute) objectiveEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getObjective_Expression() {
		return (EReference) objectiveEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getGlobalObjective() {
		return globalObjectiveEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getGlobalObjective_Expression() {
		return (EReference) globalObjectiveEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getGlobalObjective_Target() {
		return (EAttribute) globalObjectiveEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getContext() {
		return contextEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getPatternConstraint() {
		return patternConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getPatternConstraint_Pattern() {
		return (EReference) patternConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTypeConstraint() {
		return typeConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTypeConstraint_ModelType() {
		return (EReference) typeConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getMappingConstraint() {
		return mappingConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getMappingConstraint_Mapping() {
		return (EReference) mappingConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getPatternObjective() {
		return patternObjectiveEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getPatternObjective_Pattern() {
		return (EReference) patternObjectiveEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTypeObjective() {
		return typeObjectiveEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTypeObjective_ModelType() {
		return (EReference) typeObjectiveEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getMappingObjective() {
		return mappingObjectiveEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getMappingObjective_Mapping() {
		return (EReference) mappingObjectiveEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getArithmeticExpression() {
		return arithmeticExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getArithmeticExpression_ReturnType() {
		return (EReference) arithmeticExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getBinaryArithmeticExpression() {
		return binaryArithmeticExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getBinaryArithmeticExpression_Lhs() {
		return (EReference) binaryArithmeticExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getBinaryArithmeticExpression_Rhs() {
		return (EReference) binaryArithmeticExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getBinaryArithmeticExpression_Operator() {
		return (EAttribute) binaryArithmeticExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getUnaryArithmeticExpression() {
		return unaryArithmeticExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getUnaryArithmeticExpression_Expression() {
		return (EReference) unaryArithmeticExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getUnaryArithmeticExpression_Operator() {
		return (EAttribute) unaryArithmeticExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getSetOperation() {
		return setOperationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getSetOperation_OperandName() {
		return (EAttribute) setOperationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getArithmeticValueExpression() {
		return arithmeticValueExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getArithmeticValue() {
		return arithmeticValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getArithmeticValue_Value() {
		return (EReference) arithmeticValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getArithmeticLiteral() {
		return arithmeticLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getVariableReference() {
		return variableReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getVariableReference_Variable() {
		return (EReference) variableReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getArithmeticNullLiteral() {
		return arithmeticNullLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getIntegerLiteral() {
		return integerLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getIntegerLiteral_Literal() {
		return (EAttribute) integerLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getDoubleLiteral() {
		return doubleLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getDoubleLiteral_Literal() {
		return (EAttribute) doubleLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getBoolExpression() {
		return boolExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getBoolBinaryExpression() {
		return boolBinaryExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getBoolBinaryExpression_Lhs() {
		return (EReference) boolBinaryExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getBoolBinaryExpression_Rhs() {
		return (EReference) boolBinaryExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getBoolBinaryExpression_Operator() {
		return (EAttribute) boolBinaryExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getBoolUnaryExpression() {
		return boolUnaryExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getBoolUnaryExpression_Expression() {
		return (EReference) boolUnaryExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getBoolUnaryExpression_Operator() {
		return (EAttribute) boolUnaryExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getBoolValueExpression() {
		return boolValueExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getBoolValue() {
		return boolValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getBoolValue_Value() {
		return (EReference) boolValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getBoolStreamExpression() {
		return boolStreamExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getBoolStreamExpression_Stream() {
		return (EReference) boolStreamExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getBoolStreamExpression_Operator() {
		return (EAttribute) boolStreamExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getRelationalExpression() {
		return relationalExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getRelationalExpression_Operator() {
		return (EAttribute) relationalExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getRelationalExpression_Lhs() {
		return (EReference) relationalExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getRelationalExpression_Rhs() {
		return (EReference) relationalExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getBoolLiteral() {
		return boolLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getBoolLiteral_Literal() {
		return (EAttribute) boolLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getValueExpression() {
		return valueExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getValueExpression_ReturnType() {
		return (EReference) valueExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getSumExpression() {
		return sumExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getSumExpression_Expression() {
		return (EReference) sumExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getSumExpression_Filter() {
		return (EReference) sumExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getContextSumExpression() {
		return contextSumExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getContextSumExpression_Context() {
		return (EReference) contextSumExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getContextSumExpression_Node() {
		return (EReference) contextSumExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getContextSumExpression_Feature() {
		return (EReference) contextSumExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getMappingSumExpression() {
		return mappingSumExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getMappingSumExpression_Mapping() {
		return (EReference) mappingSumExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getPatternSumExpression() {
		return patternSumExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getPatternSumExpression_Pattern() {
		return (EReference) patternSumExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTypeSumExpression() {
		return typeSumExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getTypeSumExpression_Type() {
		return (EReference) typeSumExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getContextTypeValue() {
		return contextTypeValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getContextTypeValue_TypeContext() {
		return (EReference) contextTypeValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getContextPatternValue() {
		return contextPatternValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getContextPatternValue_PatternContext() {
		return (EReference) contextPatternValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getContextPatternNode() {
		return contextPatternNodeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getContextPatternNode_PatternContext() {
		return (EReference) contextPatternNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getContextPatternNode_Node() {
		return (EReference) contextPatternNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getContextMappingValue() {
		return contextMappingValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getContextMappingValue_MappingContext() {
		return (EReference) contextMappingValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getContextMappingNode() {
		return contextMappingNodeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getContextMappingNode_MappingContext() {
		return (EReference) contextMappingNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getContextMappingNode_Node() {
		return (EReference) contextMappingNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getContextMappingVariablesReference() {
		return contextMappingVariablesReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getContextMappingVariablesReference_Var() {
		return (EReference) contextMappingVariablesReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getContextMappingVariablesReference_MappingContext() {
		return (EReference) contextMappingVariablesReferenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getObjectiveFunctionValue() {
		return objectiveFunctionValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getObjectiveFunctionValue_Objective() {
		return (EReference) objectiveFunctionValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getFeatureExpression() {
		return featureExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getFeatureExpression_Current() {
		return (EReference) featureExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getFeatureExpression_Child() {
		return (EReference) featureExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getFeatureLiteral() {
		return featureLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getFeatureLiteral_Feature() {
		return (EReference) featureLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getContextTypeFeatureValue() {
		return contextTypeFeatureValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getContextTypeFeatureValue_FeatureExpression() {
		return (EReference) contextTypeFeatureValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getContextPatternNodeFeatureValue() {
		return contextPatternNodeFeatureValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getContextPatternNodeFeatureValue_FeatureExpression() {
		return (EReference) contextPatternNodeFeatureValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getContextMappingNodeFeatureValue() {
		return contextMappingNodeFeatureValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getContextMappingNodeFeatureValue_FeatureExpression() {
		return (EReference) contextMappingNodeFeatureValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getIterator() {
		return iteratorEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getIterator_Stream() {
		return (EReference) iteratorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getIteratorPatternValue() {
		return iteratorPatternValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getIteratorPatternValue_PatternContext() {
		return (EReference) iteratorPatternValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getIteratorPatternFeatureValue() {
		return iteratorPatternFeatureValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getIteratorPatternFeatureValue_PatternContext() {
		return (EReference) iteratorPatternFeatureValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getIteratorPatternFeatureValue_FeatureExpression() {
		return (EReference) iteratorPatternFeatureValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getIteratorPatternNodeValue() {
		return iteratorPatternNodeValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getIteratorPatternNodeValue_PatternContext() {
		return (EReference) iteratorPatternNodeValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getIteratorPatternNodeValue_Node() {
		return (EReference) iteratorPatternNodeValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getIteratorPatternNodeFeatureValue() {
		return iteratorPatternNodeFeatureValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getIteratorPatternNodeFeatureValue_FeatureExpression() {
		return (EReference) iteratorPatternNodeFeatureValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getIteratorMappingValue() {
		return iteratorMappingValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getIteratorMappingValue_MappingContext() {
		return (EReference) iteratorMappingValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getIteratorMappingVariableValue() {
		return iteratorMappingVariableValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getIteratorMappingVariableValue_MappingContext() {
		return (EReference) iteratorMappingVariableValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getIteratorMappingVariablesReference() {
		return iteratorMappingVariablesReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getIteratorMappingVariablesReference_MappingContext() {
		return (EReference) iteratorMappingVariablesReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getIteratorMappingVariablesReference_Var() {
		return (EReference) iteratorMappingVariablesReferenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getIteratorMappingFeatureValue() {
		return iteratorMappingFeatureValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getIteratorMappingFeatureValue_MappingContext() {
		return (EReference) iteratorMappingFeatureValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getIteratorMappingFeatureValue_FeatureExpression() {
		return (EReference) iteratorMappingFeatureValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getIteratorMappingNodeValue() {
		return iteratorMappingNodeValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getIteratorMappingNodeValue_MappingContext() {
		return (EReference) iteratorMappingNodeValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getIteratorMappingNodeValue_Node() {
		return (EReference) iteratorMappingNodeValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getIteratorMappingNodeFeatureValue() {
		return iteratorMappingNodeFeatureValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getIteratorMappingNodeFeatureValue_FeatureExpression() {
		return (EReference) iteratorMappingNodeFeatureValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getIteratorTypeValue() {
		return iteratorTypeValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getIteratorTypeValue_TypeContext() {
		return (EReference) iteratorTypeValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getIteratorTypeFeatureValue() {
		return iteratorTypeFeatureValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getIteratorTypeFeatureValue_FeatureExpression() {
		return (EReference) iteratorTypeFeatureValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getStreamExpression() {
		return streamExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getStreamExpression_ReturnType() {
		return (EReference) streamExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getStreamExpression_Current() {
		return (EReference) streamExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getStreamExpression_Child() {
		return (EReference) streamExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getStreamOperation() {
		return streamOperationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getStreamNoOperation() {
		return streamNoOperationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getStreamFilterOperation() {
		return streamFilterOperationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getStreamFilterOperation_Predicate() {
		return (EReference) streamFilterOperationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getStreamSelectOperation() {
		return streamSelectOperationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getStreamSelectOperation_Type() {
		return (EReference) streamSelectOperationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getStreamContainsOperation() {
		return streamContainsOperationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getStreamContainsOperation_Expr() {
		return (EReference) streamContainsOperationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getILPSolverType() {
		return ilpSolverTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getVariableType() {
		return variableTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getObjectiveTarget() {
		return objectiveTargetEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getRelationalOperator() {
		return relationalOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getBinaryArithmeticOperator() {
		return binaryArithmeticOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getUnaryArithmeticOperator() {
		return unaryArithmeticOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getStreamArithmeticOperator() {
		return streamArithmeticOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getBinaryBoolOperator() {
		return binaryBoolOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getUnaryBoolOperator() {
		return unaryBoolOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getStreamBoolOperator() {
		return streamBoolOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public GipsIntermediateFactory getGipsIntermediateFactory() {
		return (GipsIntermediateFactory) getEFactoryInstance();
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
		gipsIntermediateModelEClass = createEClass(GIPS_INTERMEDIATE_MODEL);
		createEAttribute(gipsIntermediateModelEClass, GIPS_INTERMEDIATE_MODEL__NAME);
		createEReference(gipsIntermediateModelEClass, GIPS_INTERMEDIATE_MODEL__VARIABLES);
		createEReference(gipsIntermediateModelEClass, GIPS_INTERMEDIATE_MODEL__CONSTRAINTS);
		createEReference(gipsIntermediateModelEClass, GIPS_INTERMEDIATE_MODEL__OBJECTIVES);
		createEReference(gipsIntermediateModelEClass, GIPS_INTERMEDIATE_MODEL__GLOBAL_OBJECTIVE);
		createEReference(gipsIntermediateModelEClass, GIPS_INTERMEDIATE_MODEL__IBEX_MODEL);
		createEReference(gipsIntermediateModelEClass, GIPS_INTERMEDIATE_MODEL__CONFIG);

		ilpConfigEClass = createEClass(ILP_CONFIG);
		createEAttribute(ilpConfigEClass, ILP_CONFIG__SOLVER);
		createEAttribute(ilpConfigEClass, ILP_CONFIG__SOLVER_HOME_DIR);
		createEAttribute(ilpConfigEClass, ILP_CONFIG__SOLVER_LICENSE_FILE);
		createEAttribute(ilpConfigEClass, ILP_CONFIG__BUILD_LAUNCH_CONFIG);
		createEAttribute(ilpConfigEClass, ILP_CONFIG__MAIN_FILE);
		createEAttribute(ilpConfigEClass, ILP_CONFIG__ENABLE_TIME_LIMIT);
		createEAttribute(ilpConfigEClass, ILP_CONFIG__ILP_TIME_LIMIT);
		createEAttribute(ilpConfigEClass, ILP_CONFIG__ENABLE_RND_SEED);
		createEAttribute(ilpConfigEClass, ILP_CONFIG__ILP_RND_SEED);
		createEAttribute(ilpConfigEClass, ILP_CONFIG__PRESOLVE);
		createEAttribute(ilpConfigEClass, ILP_CONFIG__ENABLE_PRESOLVE);
		createEAttribute(ilpConfigEClass, ILP_CONFIG__ENABLE_DEBUG_OUTPUT);
		createEAttribute(ilpConfigEClass, ILP_CONFIG__ENABLE_CUSTOM_TOLERANCE);
		createEAttribute(ilpConfigEClass, ILP_CONFIG__TOLERANCE);
		createEAttribute(ilpConfigEClass, ILP_CONFIG__ENABLE_LP_OUTPUT);
		createEAttribute(ilpConfigEClass, ILP_CONFIG__LP_PATH);

		variableSetEClass = createEClass(VARIABLE_SET);
		createEAttribute(variableSetEClass, VARIABLE_SET__NAME);
		createEAttribute(variableSetEClass, VARIABLE_SET__UPPER_BOUND);
		createEAttribute(variableSetEClass, VARIABLE_SET__LOWER_BOUND);

		patternEClass = createEClass(PATTERN);
		createEReference(patternEClass, PATTERN__PATTERN);
		createEAttribute(patternEClass, PATTERN__IS_RULE);

		typeEClass = createEClass(TYPE);
		createEReference(typeEClass, TYPE__TYPE);

		mappingEClass = createEClass(MAPPING);
		createEReference(mappingEClass, MAPPING__CONTEXT_PATTERN);
		createEReference(mappingEClass, MAPPING__FREE_VARIABLES);
		createEReference(mappingEClass, MAPPING__BOUND_VARIABLES);

		gtMappingEClass = createEClass(GT_MAPPING);
		createEReference(gtMappingEClass, GT_MAPPING__RULE);

		patternMappingEClass = createEClass(PATTERN_MAPPING);
		createEReference(patternMappingEClass, PATTERN_MAPPING__PATTERN);

		variableEClass = createEClass(VARIABLE);
		createEAttribute(variableEClass, VARIABLE__TYPE);

		gtParameterVariableEClass = createEClass(GT_PARAMETER_VARIABLE);
		createEReference(gtParameterVariableEClass, GT_PARAMETER_VARIABLE__PARAMETER);
		createEReference(gtParameterVariableEClass, GT_PARAMETER_VARIABLE__RULE);

		constraintEClass = createEClass(CONSTRAINT);
		createEAttribute(constraintEClass, CONSTRAINT__NAME);
		createEAttribute(constraintEClass, CONSTRAINT__DEPENDING);
		createEReference(constraintEClass, CONSTRAINT__EXPRESSION);
		createEAttribute(constraintEClass, CONSTRAINT__CONSTANT);
		createEAttribute(constraintEClass, CONSTRAINT__NEGATED);
		createEReference(constraintEClass, CONSTRAINT__DEPENDENCIES);
		createEReference(constraintEClass, CONSTRAINT__REFERENCED_BY);
		createEReference(constraintEClass, CONSTRAINT__SYMBOLIC_VARIABLE);
		createEReference(constraintEClass, CONSTRAINT__HELPER_VARIABLES);
		createEReference(constraintEClass, CONSTRAINT__HELPER_CONSTRAINTS);

		globalConstraintEClass = createEClass(GLOBAL_CONSTRAINT);

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

		variableReferenceEClass = createEClass(VARIABLE_REFERENCE);
		createEReference(variableReferenceEClass, VARIABLE_REFERENCE__VARIABLE);

		arithmeticNullLiteralEClass = createEClass(ARITHMETIC_NULL_LITERAL);

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

		contextSumExpressionEClass = createEClass(CONTEXT_SUM_EXPRESSION);
		createEReference(contextSumExpressionEClass, CONTEXT_SUM_EXPRESSION__CONTEXT);
		createEReference(contextSumExpressionEClass, CONTEXT_SUM_EXPRESSION__NODE);
		createEReference(contextSumExpressionEClass, CONTEXT_SUM_EXPRESSION__FEATURE);

		mappingSumExpressionEClass = createEClass(MAPPING_SUM_EXPRESSION);
		createEReference(mappingSumExpressionEClass, MAPPING_SUM_EXPRESSION__MAPPING);

		patternSumExpressionEClass = createEClass(PATTERN_SUM_EXPRESSION);
		createEReference(patternSumExpressionEClass, PATTERN_SUM_EXPRESSION__PATTERN);

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

		contextMappingVariablesReferenceEClass = createEClass(CONTEXT_MAPPING_VARIABLES_REFERENCE);
		createEReference(contextMappingVariablesReferenceEClass, CONTEXT_MAPPING_VARIABLES_REFERENCE__VAR);
		createEReference(contextMappingVariablesReferenceEClass, CONTEXT_MAPPING_VARIABLES_REFERENCE__MAPPING_CONTEXT);

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

		iteratorMappingVariableValueEClass = createEClass(ITERATOR_MAPPING_VARIABLE_VALUE);
		createEReference(iteratorMappingVariableValueEClass, ITERATOR_MAPPING_VARIABLE_VALUE__MAPPING_CONTEXT);

		iteratorMappingVariablesReferenceEClass = createEClass(ITERATOR_MAPPING_VARIABLES_REFERENCE);
		createEReference(iteratorMappingVariablesReferenceEClass,
				ITERATOR_MAPPING_VARIABLES_REFERENCE__MAPPING_CONTEXT);
		createEReference(iteratorMappingVariablesReferenceEClass, ITERATOR_MAPPING_VARIABLES_REFERENCE__VAR);

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

		streamNoOperationEClass = createEClass(STREAM_NO_OPERATION);

		streamFilterOperationEClass = createEClass(STREAM_FILTER_OPERATION);
		createEReference(streamFilterOperationEClass, STREAM_FILTER_OPERATION__PREDICATE);

		streamSelectOperationEClass = createEClass(STREAM_SELECT_OPERATION);
		createEReference(streamSelectOperationEClass, STREAM_SELECT_OPERATION__TYPE);

		streamContainsOperationEClass = createEClass(STREAM_CONTAINS_OPERATION);
		createEReference(streamContainsOperationEClass, STREAM_CONTAINS_OPERATION__EXPR);

		// Create enums
		ilpSolverTypeEEnum = createEEnum(ILP_SOLVER_TYPE);
		variableTypeEEnum = createEEnum(VARIABLE_TYPE);
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
		gtMappingEClass.getESuperTypes().add(this.getMapping());
		patternMappingEClass.getESuperTypes().add(this.getMapping());
		variableEClass.getESuperTypes().add(this.getVariableSet());
		gtParameterVariableEClass.getESuperTypes().add(this.getVariable());
		globalConstraintEClass.getESuperTypes().add(this.getConstraint());
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
		variableReferenceEClass.getESuperTypes().add(this.getArithmeticValueExpression());
		arithmeticNullLiteralEClass.getESuperTypes().add(this.getArithmeticLiteral());
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
		contextSumExpressionEClass.getESuperTypes().add(this.getSumExpression());
		mappingSumExpressionEClass.getESuperTypes().add(this.getSumExpression());
		patternSumExpressionEClass.getESuperTypes().add(this.getSumExpression());
		typeSumExpressionEClass.getESuperTypes().add(this.getSumExpression());
		contextTypeValueEClass.getESuperTypes().add(this.getValueExpression());
		contextPatternValueEClass.getESuperTypes().add(this.getValueExpression());
		contextPatternNodeEClass.getESuperTypes().add(this.getValueExpression());
		contextMappingValueEClass.getESuperTypes().add(this.getValueExpression());
		contextMappingNodeEClass.getESuperTypes().add(this.getValueExpression());
		contextMappingVariablesReferenceEClass.getESuperTypes().add(this.getValueExpression());
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
		iteratorMappingVariableValueEClass.getESuperTypes().add(this.getValueExpression());
		iteratorMappingVariableValueEClass.getESuperTypes().add(this.getIterator());
		iteratorMappingVariablesReferenceEClass.getESuperTypes().add(this.getValueExpression());
		iteratorMappingVariablesReferenceEClass.getESuperTypes().add(this.getIterator());
		iteratorMappingFeatureValueEClass.getESuperTypes().add(this.getValueExpression());
		iteratorMappingFeatureValueEClass.getESuperTypes().add(this.getIterator());
		iteratorMappingNodeValueEClass.getESuperTypes().add(this.getValueExpression());
		iteratorMappingNodeValueEClass.getESuperTypes().add(this.getIterator());
		iteratorMappingNodeFeatureValueEClass.getESuperTypes().add(this.getIteratorMappingNodeValue());
		iteratorTypeValueEClass.getESuperTypes().add(this.getValueExpression());
		iteratorTypeValueEClass.getESuperTypes().add(this.getIterator());
		iteratorTypeFeatureValueEClass.getESuperTypes().add(this.getIteratorTypeValue());
		streamExpressionEClass.getESuperTypes().add(this.getSetOperation());
		streamNoOperationEClass.getESuperTypes().add(this.getStreamOperation());
		streamFilterOperationEClass.getESuperTypes().add(this.getStreamOperation());
		streamSelectOperationEClass.getESuperTypes().add(this.getStreamOperation());
		streamContainsOperationEClass.getESuperTypes().add(this.getStreamOperation());

		// Initialize classes, features, and operations; add parameters
		initEClass(gipsIntermediateModelEClass, GipsIntermediateModel.class, "GipsIntermediateModel", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGipsIntermediateModel_Name(), ecorePackage.getEString(), "name", null, 0, 1,
				GipsIntermediateModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGipsIntermediateModel_Variables(), this.getVariableSet(), null, "variables", null, 0, -1,
				GipsIntermediateModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGipsIntermediateModel_Constraints(), this.getConstraint(), null, "constraints", null, 0, -1,
				GipsIntermediateModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGipsIntermediateModel_Objectives(), this.getObjective(), null, "objectives", null, 0, -1,
				GipsIntermediateModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGipsIntermediateModel_GlobalObjective(), this.getGlobalObjective(), null, "globalObjective",
				null, 0, 1, GipsIntermediateModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGipsIntermediateModel_IbexModel(), theIBeXPatternModelPackage.getIBeXModel(), null,
				"ibexModel", null, 1, 1, GipsIntermediateModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGipsIntermediateModel_Config(), this.getILPConfig(), null, "config", null, 1, 1,
				GipsIntermediateModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ilpConfigEClass, ILPConfig.class, "ILPConfig", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getILPConfig_Solver(), this.getILPSolverType(), "solver", null, 0, 1, ILPConfig.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getILPConfig_SolverHomeDir(), ecorePackage.getEString(), "solverHomeDir", null, 0, 1,
				ILPConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getILPConfig_SolverLicenseFile(), ecorePackage.getEString(), "solverLicenseFile", null, 0, 1,
				ILPConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getILPConfig_BuildLaunchConfig(), ecorePackage.getEBoolean(), "buildLaunchConfig", null, 0, 1,
				ILPConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getILPConfig_MainFile(), ecorePackage.getEString(), "mainFile", null, 0, 1, ILPConfig.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getILPConfig_EnableTimeLimit(), ecorePackage.getEBoolean(), "enableTimeLimit", null, 0, 1,
				ILPConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getILPConfig_IlpTimeLimit(), ecorePackage.getEDouble(), "ilpTimeLimit", null, 0, 1,
				ILPConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getILPConfig_EnableRndSeed(), ecorePackage.getEBoolean(), "enableRndSeed", null, 0, 1,
				ILPConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getILPConfig_IlpRndSeed(), ecorePackage.getEInt(), "ilpRndSeed", null, 0, 1, ILPConfig.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getILPConfig_Presolve(), ecorePackage.getEBoolean(), "presolve", null, 0, 1, ILPConfig.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getILPConfig_EnablePresolve(), ecorePackage.getEBoolean(), "enablePresolve", null, 0, 1,
				ILPConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getILPConfig_EnableDebugOutput(), ecorePackage.getEBoolean(), "enableDebugOutput", null, 0, 1,
				ILPConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getILPConfig_EnableCustomTolerance(), ecorePackage.getEBoolean(), "enableCustomTolerance", null,
				0, 1, ILPConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getILPConfig_Tolerance(), ecorePackage.getEDouble(), "tolerance", null, 0, 1, ILPConfig.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getILPConfig_EnableLpOutput(), ecorePackage.getEBoolean(), "enableLpOutput", null, 0, 1,
				ILPConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getILPConfig_LpPath(), ecorePackage.getEString(), "lpPath", null, 0, 1, ILPConfig.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(variableSetEClass, VariableSet.class, "VariableSet", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVariableSet_Name(), ecorePackage.getEString(), "name", null, 0, 1, VariableSet.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVariableSet_UpperBound(), ecorePackage.getEDouble(), "upperBound", null, 0, 1,
				VariableSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getVariableSet_LowerBound(), ecorePackage.getEDouble(), "lowerBound", null, 0, 1,
				VariableSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

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

		initEClass(mappingEClass, Mapping.class, "Mapping", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMapping_ContextPattern(), theIBeXPatternModelPackage.getIBeXContextPattern(), null,
				"contextPattern", null, 1, 1, Mapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMapping_FreeVariables(), this.getVariable(), null, "freeVariables", null, 0, -1,
				Mapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMapping_BoundVariables(), this.getGTParameterVariable(), null, "boundVariables", null, 0, -1,
				Mapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gtMappingEClass, GTMapping.class, "GTMapping", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGTMapping_Rule(), theIBeXPatternModelPackage.getIBeXRule(), null, "rule", null, 1, 1,
				GTMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(patternMappingEClass, PatternMapping.class, "PatternMapping", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPatternMapping_Pattern(), theIBeXPatternModelPackage.getIBeXContext(), null, "pattern", null,
				1, 1, PatternMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(variableEClass, Variable.class, "Variable", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVariable_Type(), this.getVariableType(), "type", null, 0, 1, Variable.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gtParameterVariableEClass, GTParameterVariable.class, "GTParameterVariable", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGTParameterVariable_Parameter(), theIBeXPatternModelPackage.getIBeXParameter(), null,
				"parameter", null, 1, 1, GTParameterVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGTParameterVariable_Rule(), theIBeXPatternModelPackage.getIBeXRule(), null, "rule", null, 1,
				1, GTParameterVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(constraintEClass, Constraint.class, "Constraint", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConstraint_Name(), ecorePackage.getEString(), "name", null, 0, 1, Constraint.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConstraint_Depending(), ecorePackage.getEBoolean(), "depending", null, 0, 1, Constraint.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConstraint_Expression(), this.getBoolExpression(), null, "expression", null, 1, 1,
				Constraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConstraint_Constant(), ecorePackage.getEBoolean(), "constant", null, 0, 1, Constraint.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConstraint_Negated(), ecorePackage.getEBoolean(), "negated", null, 0, 1, Constraint.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConstraint_Dependencies(), this.getConstraint(), this.getConstraint_ReferencedBy(),
				"dependencies", null, 0, -1, Constraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConstraint_ReferencedBy(), this.getConstraint(), this.getConstraint_Dependencies(),
				"referencedBy", null, 0, 1, Constraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConstraint_SymbolicVariable(), this.getVariable(), null, "symbolicVariable", null, 0, 1,
				Constraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConstraint_HelperVariables(), this.getVariable(), null, "helperVariables", null, 0, -1,
				Constraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConstraint_HelperConstraints(), this.getRelationalExpression(), null, "helperConstraints",
				null, 0, -1, Constraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(globalConstraintEClass, GlobalConstraint.class, "GlobalConstraint", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

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

		initEClass(variableReferenceEClass, VariableReference.class, "VariableReference", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVariableReference_Variable(), this.getVariable(), null, "variable", null, 1, 1,
				VariableReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(arithmeticNullLiteralEClass, ArithmeticNullLiteral.class, "ArithmeticNullLiteral", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

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

		initEClass(contextSumExpressionEClass, ContextSumExpression.class, "ContextSumExpression", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getContextSumExpression_Context(), this.getVariableSet(), null, "context", null, 0, 1,
				ContextSumExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getContextSumExpression_Node(), theIBeXPatternModelPackage.getIBeXNode(), null, "node", null, 0,
				1, ContextSumExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getContextSumExpression_Feature(), this.getFeatureExpression(), null, "feature", null, 0, 1,
				ContextSumExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mappingSumExpressionEClass, MappingSumExpression.class, "MappingSumExpression", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMappingSumExpression_Mapping(), this.getMapping(), null, "mapping", null, 1, 1,
				MappingSumExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(patternSumExpressionEClass, PatternSumExpression.class, "PatternSumExpression", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPatternSumExpression_Pattern(), this.getPattern(), null, "pattern", null, 0, 1,
				PatternSumExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
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

		initEClass(contextMappingVariablesReferenceEClass, ContextMappingVariablesReference.class,
				"ContextMappingVariablesReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getContextMappingVariablesReference_Var(), this.getVariableReference(), null, "var", null, 1, 1,
				ContextMappingVariablesReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getContextMappingVariablesReference_MappingContext(), this.getMapping(), null, "mappingContext",
				null, 0, 1, ContextMappingVariablesReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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

		initEClass(iteratorMappingVariableValueEClass, IteratorMappingVariableValue.class,
				"IteratorMappingVariableValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIteratorMappingVariableValue_MappingContext(), this.getMapping(), null, "mappingContext",
				null, 0, 1, IteratorMappingVariableValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iteratorMappingVariablesReferenceEClass, IteratorMappingVariablesReference.class,
				"IteratorMappingVariablesReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIteratorMappingVariablesReference_MappingContext(), this.getMapping(), null, "mappingContext",
				null, 0, 1, IteratorMappingVariablesReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIteratorMappingVariablesReference_Var(), this.getVariableReference(), null, "var", null, 1, 1,
				IteratorMappingVariablesReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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

		initEClass(streamNoOperationEClass, StreamNoOperation.class, "StreamNoOperation", !IS_ABSTRACT, !IS_INTERFACE,
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

		initEClass(streamContainsOperationEClass, StreamContainsOperation.class, "StreamContainsOperation",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStreamContainsOperation_Expr(), this.getValueExpression(), null, "expr", null, 0, 1,
				StreamContainsOperation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(ilpSolverTypeEEnum, ILPSolverType.class, "ILPSolverType");
		addEEnumLiteral(ilpSolverTypeEEnum, ILPSolverType.GUROBI);
		addEEnumLiteral(ilpSolverTypeEEnum, ILPSolverType.GLPK);
		addEEnumLiteral(ilpSolverTypeEEnum, ILPSolverType.CPLEX);

		initEEnum(variableTypeEEnum, VariableType.class, "VariableType");
		addEEnumLiteral(variableTypeEEnum, VariableType.BINARY);
		addEEnumLiteral(variableTypeEEnum, VariableType.INTEGER);
		addEEnumLiteral(variableTypeEEnum, VariableType.REAL);

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
		addEEnumLiteral(relationalOperatorEEnum, RelationalOperator.OBJECT_EQUAL);
		addEEnumLiteral(relationalOperatorEEnum, RelationalOperator.OBJECT_NOT_EQUAL);

		initEEnum(binaryArithmeticOperatorEEnum, BinaryArithmeticOperator.class, "BinaryArithmeticOperator");
		addEEnumLiteral(binaryArithmeticOperatorEEnum, BinaryArithmeticOperator.ADD);
		addEEnumLiteral(binaryArithmeticOperatorEEnum, BinaryArithmeticOperator.SUBTRACT);
		addEEnumLiteral(binaryArithmeticOperatorEEnum, BinaryArithmeticOperator.MULTIPLY);
		addEEnumLiteral(binaryArithmeticOperatorEEnum, BinaryArithmeticOperator.DIVIDE);
		addEEnumLiteral(binaryArithmeticOperatorEEnum, BinaryArithmeticOperator.POW);
		addEEnumLiteral(binaryArithmeticOperatorEEnum, BinaryArithmeticOperator.LOG);

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
		addEEnumLiteral(binaryBoolOperatorEEnum, BinaryBoolOperator.XOR);

		initEEnum(unaryBoolOperatorEEnum, UnaryBoolOperator.class, "UnaryBoolOperator");
		addEEnumLiteral(unaryBoolOperatorEEnum, UnaryBoolOperator.NOT);
		addEEnumLiteral(unaryBoolOperatorEEnum, UnaryBoolOperator.BRACKET);

		initEEnum(streamBoolOperatorEEnum, StreamBoolOperator.class, "StreamBoolOperator");
		addEEnumLiteral(streamBoolOperatorEEnum, StreamBoolOperator.EXISTS);
		addEEnumLiteral(streamBoolOperatorEEnum, StreamBoolOperator.NOTEXISTS);

		// Create resource
		createResource(eNS_URI);
	}

} // GipsIntermediatePackageImpl
