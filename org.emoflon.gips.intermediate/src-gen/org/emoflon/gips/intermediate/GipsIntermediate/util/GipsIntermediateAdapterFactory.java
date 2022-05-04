/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.emoflon.gips.intermediate.GipsIntermediate.*;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides
 * an adapter <code>createXXX</code> method for each class of the model. <!--
 * end-user-doc -->
 * 
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage
 * @generated
 */
public class GipsIntermediateAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static GipsIntermediatePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public GipsIntermediateAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = GipsIntermediatePackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object. <!--
	 * begin-user-doc --> This implementation returns <code>true</code> if the
	 * object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * 
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected GipsIntermediateSwitch<Adapter> modelSwitch = new GipsIntermediateSwitch<Adapter>() {
		@Override
		public Adapter caseGipsIntermediateModel(GipsIntermediateModel object) {
			return createGipsIntermediateModelAdapter();
		}

		@Override
		public Adapter caseILPConfig(ILPConfig object) {
			return createILPConfigAdapter();
		}

		@Override
		public Adapter caseVariableSet(VariableSet object) {
			return createVariableSetAdapter();
		}

		@Override
		public Adapter casePattern(Pattern object) {
			return createPatternAdapter();
		}

		@Override
		public Adapter caseType(Type object) {
			return createTypeAdapter();
		}

		@Override
		public Adapter caseMapping(Mapping object) {
			return createMappingAdapter();
		}

		@Override
		public Adapter caseConstraint(Constraint object) {
			return createConstraintAdapter();
		}

		@Override
		public Adapter caseObjective(Objective object) {
			return createObjectiveAdapter();
		}

		@Override
		public Adapter caseGlobalObjective(GlobalObjective object) {
			return createGlobalObjectiveAdapter();
		}

		@Override
		public Adapter caseContext(Context object) {
			return createContextAdapter();
		}

		@Override
		public Adapter casePatternConstraint(PatternConstraint object) {
			return createPatternConstraintAdapter();
		}

		@Override
		public Adapter caseTypeConstraint(TypeConstraint object) {
			return createTypeConstraintAdapter();
		}

		@Override
		public Adapter caseMappingConstraint(MappingConstraint object) {
			return createMappingConstraintAdapter();
		}

		@Override
		public Adapter casePatternObjective(PatternObjective object) {
			return createPatternObjectiveAdapter();
		}

		@Override
		public Adapter caseTypeObjective(TypeObjective object) {
			return createTypeObjectiveAdapter();
		}

		@Override
		public Adapter caseMappingObjective(MappingObjective object) {
			return createMappingObjectiveAdapter();
		}

		@Override
		public Adapter caseArithmeticExpression(ArithmeticExpression object) {
			return createArithmeticExpressionAdapter();
		}

		@Override
		public Adapter caseBinaryArithmeticExpression(BinaryArithmeticExpression object) {
			return createBinaryArithmeticExpressionAdapter();
		}

		@Override
		public Adapter caseUnaryArithmeticExpression(UnaryArithmeticExpression object) {
			return createUnaryArithmeticExpressionAdapter();
		}

		@Override
		public Adapter caseSetOperation(SetOperation object) {
			return createSetOperationAdapter();
		}

		@Override
		public Adapter caseArithmeticValueExpression(ArithmeticValueExpression object) {
			return createArithmeticValueExpressionAdapter();
		}

		@Override
		public Adapter caseArithmeticValue(ArithmeticValue object) {
			return createArithmeticValueAdapter();
		}

		@Override
		public Adapter caseArithmeticLiteral(ArithmeticLiteral object) {
			return createArithmeticLiteralAdapter();
		}

		@Override
		public Adapter caseIntegerLiteral(IntegerLiteral object) {
			return createIntegerLiteralAdapter();
		}

		@Override
		public Adapter caseDoubleLiteral(DoubleLiteral object) {
			return createDoubleLiteralAdapter();
		}

		@Override
		public Adapter caseBoolExpression(BoolExpression object) {
			return createBoolExpressionAdapter();
		}

		@Override
		public Adapter caseBoolBinaryExpression(BoolBinaryExpression object) {
			return createBoolBinaryExpressionAdapter();
		}

		@Override
		public Adapter caseBoolUnaryExpression(BoolUnaryExpression object) {
			return createBoolUnaryExpressionAdapter();
		}

		@Override
		public Adapter caseBoolValueExpression(BoolValueExpression object) {
			return createBoolValueExpressionAdapter();
		}

		@Override
		public Adapter caseBoolValue(BoolValue object) {
			return createBoolValueAdapter();
		}

		@Override
		public Adapter caseBoolStreamExpression(BoolStreamExpression object) {
			return createBoolStreamExpressionAdapter();
		}

		@Override
		public Adapter caseRelationalExpression(RelationalExpression object) {
			return createRelationalExpressionAdapter();
		}

		@Override
		public Adapter caseBoolLiteral(BoolLiteral object) {
			return createBoolLiteralAdapter();
		}

		@Override
		public Adapter caseValueExpression(ValueExpression object) {
			return createValueExpressionAdapter();
		}

		@Override
		public Adapter caseSumExpression(SumExpression object) {
			return createSumExpressionAdapter();
		}

		@Override
		public Adapter caseContextSumExpression(ContextSumExpression object) {
			return createContextSumExpressionAdapter();
		}

		@Override
		public Adapter caseMappingSumExpression(MappingSumExpression object) {
			return createMappingSumExpressionAdapter();
		}

		@Override
		public Adapter caseTypeSumExpression(TypeSumExpression object) {
			return createTypeSumExpressionAdapter();
		}

		@Override
		public Adapter caseContextTypeValue(ContextTypeValue object) {
			return createContextTypeValueAdapter();
		}

		@Override
		public Adapter caseContextPatternValue(ContextPatternValue object) {
			return createContextPatternValueAdapter();
		}

		@Override
		public Adapter caseContextPatternNode(ContextPatternNode object) {
			return createContextPatternNodeAdapter();
		}

		@Override
		public Adapter caseContextMappingValue(ContextMappingValue object) {
			return createContextMappingValueAdapter();
		}

		@Override
		public Adapter caseContextMappingNode(ContextMappingNode object) {
			return createContextMappingNodeAdapter();
		}

		@Override
		public Adapter caseObjectiveFunctionValue(ObjectiveFunctionValue object) {
			return createObjectiveFunctionValueAdapter();
		}

		@Override
		public Adapter caseFeatureExpression(FeatureExpression object) {
			return createFeatureExpressionAdapter();
		}

		@Override
		public Adapter caseFeatureLiteral(FeatureLiteral object) {
			return createFeatureLiteralAdapter();
		}

		@Override
		public Adapter caseContextTypeFeatureValue(ContextTypeFeatureValue object) {
			return createContextTypeFeatureValueAdapter();
		}

		@Override
		public Adapter caseContextPatternNodeFeatureValue(ContextPatternNodeFeatureValue object) {
			return createContextPatternNodeFeatureValueAdapter();
		}

		@Override
		public Adapter caseContextMappingNodeFeatureValue(ContextMappingNodeFeatureValue object) {
			return createContextMappingNodeFeatureValueAdapter();
		}

		@Override
		public Adapter caseIterator(Iterator object) {
			return createIteratorAdapter();
		}

		@Override
		public Adapter caseIteratorPatternValue(IteratorPatternValue object) {
			return createIteratorPatternValueAdapter();
		}

		@Override
		public Adapter caseIteratorPatternFeatureValue(IteratorPatternFeatureValue object) {
			return createIteratorPatternFeatureValueAdapter();
		}

		@Override
		public Adapter caseIteratorPatternNodeValue(IteratorPatternNodeValue object) {
			return createIteratorPatternNodeValueAdapter();
		}

		@Override
		public Adapter caseIteratorPatternNodeFeatureValue(IteratorPatternNodeFeatureValue object) {
			return createIteratorPatternNodeFeatureValueAdapter();
		}

		@Override
		public Adapter caseIteratorMappingValue(IteratorMappingValue object) {
			return createIteratorMappingValueAdapter();
		}

		@Override
		public Adapter caseIteratorMappingFeatureValue(IteratorMappingFeatureValue object) {
			return createIteratorMappingFeatureValueAdapter();
		}

		@Override
		public Adapter caseIteratorMappingNodeValue(IteratorMappingNodeValue object) {
			return createIteratorMappingNodeValueAdapter();
		}

		@Override
		public Adapter caseIteratorMappingNodeFeatureValue(IteratorMappingNodeFeatureValue object) {
			return createIteratorMappingNodeFeatureValueAdapter();
		}

		@Override
		public Adapter caseIteratorTypeValue(IteratorTypeValue object) {
			return createIteratorTypeValueAdapter();
		}

		@Override
		public Adapter caseIteratorTypeFeatureValue(IteratorTypeFeatureValue object) {
			return createIteratorTypeFeatureValueAdapter();
		}

		@Override
		public Adapter caseStreamExpression(StreamExpression object) {
			return createStreamExpressionAdapter();
		}

		@Override
		public Adapter caseStreamOperation(StreamOperation object) {
			return createStreamOperationAdapter();
		}

		@Override
		public Adapter caseStreamFilterOperation(StreamFilterOperation object) {
			return createStreamFilterOperationAdapter();
		}

		@Override
		public Adapter caseStreamSelectOperation(StreamSelectOperation object) {
			return createStreamSelectOperationAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel
	 * <em>Model</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel
	 * @generated
	 */
	public Adapter createGipsIntermediateModelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig <em>ILP
	 * Config</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ILPConfig
	 * @generated
	 */
	public Adapter createILPConfigAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.VariableSet
	 * <em>Variable Set</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.VariableSet
	 * @generated
	 */
	public Adapter createVariableSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Pattern
	 * <em>Pattern</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Pattern
	 * @generated
	 */
	public Adapter createPatternAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Type <em>Type</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will
	 * catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Type
	 * @generated
	 */
	public Adapter createTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Mapping
	 * <em>Mapping</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Mapping
	 * @generated
	 */
	public Adapter createMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Constraint
	 * <em>Constraint</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Constraint
	 * @generated
	 */
	public Adapter createConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Objective
	 * <em>Objective</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Objective
	 * @generated
	 */
	public Adapter createObjectiveAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.GlobalObjective
	 * <em>Global Objective</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.GlobalObjective
	 * @generated
	 */
	public Adapter createGlobalObjectiveAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Context
	 * <em>Context</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Context
	 * @generated
	 */
	public Adapter createContextAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint
	 * <em>Pattern Constraint</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint
	 * @generated
	 */
	public Adapter createPatternConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.TypeConstraint
	 * <em>Type Constraint</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.TypeConstraint
	 * @generated
	 */
	public Adapter createTypeConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint
	 * <em>Mapping Constraint</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint
	 * @generated
	 */
	public Adapter createMappingConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.PatternObjective
	 * <em>Pattern Objective</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.PatternObjective
	 * @generated
	 */
	public Adapter createPatternObjectiveAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.TypeObjective <em>Type
	 * Objective</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.TypeObjective
	 * @generated
	 */
	public Adapter createTypeObjectiveAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.MappingObjective
	 * <em>Mapping Objective</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.MappingObjective
	 * @generated
	 */
	public Adapter createMappingObjectiveAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression
	 * <em>Arithmetic Expression</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression
	 * @generated
	 */
	public Adapter createArithmeticExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression
	 * <em>Binary Arithmetic Expression</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression
	 * @generated
	 */
	public Adapter createBinaryArithmeticExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticExpression
	 * <em>Unary Arithmetic Expression</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticExpression
	 * @generated
	 */
	public Adapter createUnaryArithmeticExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.SetOperation <em>Set
	 * Operation</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.SetOperation
	 * @generated
	 */
	public Adapter createSetOperationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticValueExpression
	 * <em>Arithmetic Value Expression</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticValueExpression
	 * @generated
	 */
	public Adapter createArithmeticValueExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticValue
	 * <em>Arithmetic Value</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticValue
	 * @generated
	 */
	public Adapter createArithmeticValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticLiteral
	 * <em>Arithmetic Literal</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticLiteral
	 * @generated
	 */
	public Adapter createArithmeticLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IntegerLiteral
	 * <em>Integer Literal</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IntegerLiteral
	 * @generated
	 */
	public Adapter createIntegerLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.DoubleLiteral
	 * <em>Double Literal</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.DoubleLiteral
	 * @generated
	 */
	public Adapter createDoubleLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolExpression
	 * <em>Bool Expression</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BoolExpression
	 * @generated
	 */
	public Adapter createBoolExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolBinaryExpression
	 * <em>Bool Binary Expression</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BoolBinaryExpression
	 * @generated
	 */
	public Adapter createBoolBinaryExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolUnaryExpression
	 * <em>Bool Unary Expression</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BoolUnaryExpression
	 * @generated
	 */
	public Adapter createBoolUnaryExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolValueExpression
	 * <em>Bool Value Expression</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BoolValueExpression
	 * @generated
	 */
	public Adapter createBoolValueExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolValue <em>Bool
	 * Value</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BoolValue
	 * @generated
	 */
	public Adapter createBoolValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolStreamExpression
	 * <em>Bool Stream Expression</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BoolStreamExpression
	 * @generated
	 */
	public Adapter createBoolStreamExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression
	 * <em>Relational Expression</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression
	 * @generated
	 */
	public Adapter createRelationalExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.BoolLiteral <em>Bool
	 * Literal</em>}'. <!-- begin-user-doc --> This default implementation returns
	 * null so that we can easily ignore cases; it's useful to ignore a case when
	 * inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.BoolLiteral
	 * @generated
	 */
	public Adapter createBoolLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression
	 * <em>Value Expression</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression
	 * @generated
	 */
	public Adapter createValueExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.SumExpression <em>Sum
	 * Expression</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.SumExpression
	 * @generated
	 */
	public Adapter createSumExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextSumExpression
	 * <em>Context Sum Expression</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextSumExpression
	 * @generated
	 */
	public Adapter createContextSumExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.MappingSumExpression
	 * <em>Mapping Sum Expression</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.MappingSumExpression
	 * @generated
	 */
	public Adapter createMappingSumExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.TypeSumExpression
	 * <em>Type Sum Expression</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.TypeSumExpression
	 * @generated
	 */
	public Adapter createTypeSumExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeValue
	 * <em>Context Type Value</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeValue
	 * @generated
	 */
	public Adapter createContextTypeValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternValue
	 * <em>Context Pattern Value</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternValue
	 * @generated
	 */
	public Adapter createContextPatternValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNode
	 * <em>Context Pattern Node</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNode
	 * @generated
	 */
	public Adapter createContextPatternNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingValue
	 * <em>Context Mapping Value</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingValue
	 * @generated
	 */
	public Adapter createContextMappingValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNode
	 * <em>Context Mapping Node</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNode
	 * @generated
	 */
	public Adapter createContextMappingNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveFunctionValue
	 * <em>Objective Function Value</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveFunctionValue
	 * @generated
	 */
	public Adapter createObjectiveFunctionValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.FeatureExpression
	 * <em>Feature Expression</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.FeatureExpression
	 * @generated
	 */
	public Adapter createFeatureExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.FeatureLiteral
	 * <em>Feature Literal</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.FeatureLiteral
	 * @generated
	 */
	public Adapter createFeatureLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeFeatureValue
	 * <em>Context Type Feature Value</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeFeatureValue
	 * @generated
	 */
	public Adapter createContextTypeFeatureValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNodeFeatureValue
	 * <em>Context Pattern Node Feature Value</em>}'. <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNodeFeatureValue
	 * @generated
	 */
	public Adapter createContextPatternNodeFeatureValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNodeFeatureValue
	 * <em>Context Mapping Node Feature Value</em>}'. <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNodeFeatureValue
	 * @generated
	 */
	public Adapter createContextMappingNodeFeatureValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.Iterator
	 * <em>Iterator</em>}'. <!-- begin-user-doc --> This default implementation
	 * returns null so that we can easily ignore cases; it's useful to ignore a case
	 * when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.Iterator
	 * @generated
	 */
	public Adapter createIteratorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternValue
	 * <em>Iterator Pattern Value</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternValue
	 * @generated
	 */
	public Adapter createIteratorPatternValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternFeatureValue
	 * <em>Iterator Pattern Feature Value</em>}'. <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternFeatureValue
	 * @generated
	 */
	public Adapter createIteratorPatternFeatureValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeValue
	 * <em>Iterator Pattern Node Value</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeValue
	 * @generated
	 */
	public Adapter createIteratorPatternNodeValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeFeatureValue
	 * <em>Iterator Pattern Node Feature Value</em>}'. <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeFeatureValue
	 * @generated
	 */
	public Adapter createIteratorPatternNodeFeatureValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingValue
	 * <em>Iterator Mapping Value</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingValue
	 * @generated
	 */
	public Adapter createIteratorMappingValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingFeatureValue
	 * <em>Iterator Mapping Feature Value</em>}'. <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingFeatureValue
	 * @generated
	 */
	public Adapter createIteratorMappingFeatureValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeValue
	 * <em>Iterator Mapping Node Value</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeValue
	 * @generated
	 */
	public Adapter createIteratorMappingNodeValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeFeatureValue
	 * <em>Iterator Mapping Node Feature Value</em>}'. <!-- begin-user-doc --> This
	 * default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeFeatureValue
	 * @generated
	 */
	public Adapter createIteratorMappingNodeFeatureValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeValue
	 * <em>Iterator Type Value</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeValue
	 * @generated
	 */
	public Adapter createIteratorTypeValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeFeatureValue
	 * <em>Iterator Type Feature Value</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeFeatureValue
	 * @generated
	 */
	public Adapter createIteratorTypeFeatureValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamExpression
	 * <em>Stream Expression</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.StreamExpression
	 * @generated
	 */
	public Adapter createStreamExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamOperation
	 * <em>Stream Operation</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.StreamOperation
	 * @generated
	 */
	public Adapter createStreamOperationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamFilterOperation
	 * <em>Stream Filter Operation</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.StreamFilterOperation
	 * @generated
	 */
	public Adapter createStreamFilterOperationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class
	 * '{@link org.emoflon.gips.intermediate.GipsIntermediate.StreamSelectOperation
	 * <em>Stream Select Operation</em>}'. <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.emoflon.gips.intermediate.GipsIntermediate.StreamSelectOperation
	 * @generated
	 */
	public Adapter createStreamSelectOperationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case. <!-- begin-user-doc --> This
	 * default implementation returns null. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // GipsIntermediateAdapterFactory
