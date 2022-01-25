/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.emoflon.roam.intermediate.RoamIntermediate.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage
 * @generated
 */
public class RoamIntermediateAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static RoamIntermediatePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RoamIntermediateAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = RoamIntermediatePackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
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
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RoamIntermediateSwitch<Adapter> modelSwitch = new RoamIntermediateSwitch<Adapter>() {
		@Override
		public Adapter caseRoamIntermediateModel(RoamIntermediateModel object) {
			return createRoamIntermediateModelAdapter();
		}

		@Override
		public Adapter caseVariableSet(VariableSet object) {
			return createVariableSetAdapter();
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
		public Adapter caseTypeConstraint(TypeConstraint object) {
			return createTypeConstraintAdapter();
		}

		@Override
		public Adapter caseMappingConstraint(MappingConstraint object) {
			return createMappingConstraintAdapter();
		}

		@Override
		public Adapter caseContext(Context object) {
			return createContextAdapter();
		}

		@Override
		public Adapter caseIterator(Iterator object) {
			return createIteratorAdapter();
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
		public Adapter caseArithmeticStreamExpression(ArithmeticStreamExpression object) {
			return createArithmeticStreamExpressionAdapter();
		}

		@Override
		public Adapter caseSumExpression(SumExpression object) {
			return createSumExpressionAdapter();
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
		public Adapter caseBoolValue(BoolValue object) {
			return createBoolValueAdapter();
		}

		@Override
		public Adapter caseBoolStreamExpression(BoolStreamExpression object) {
			return createBoolStreamExpressionAdapter();
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
		public Adapter caseContextTypeValue(ContextTypeValue object) {
			return createContextTypeValueAdapter();
		}

		@Override
		public Adapter caseContextMappingNode(ContextMappingNode object) {
			return createContextMappingNodeAdapter();
		}

		@Override
		public Adapter caseIteratorValue(IteratorValue object) {
			return createIteratorValueAdapter();
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
		public Adapter caseContextMappingNodeFeatureValue(ContextMappingNodeFeatureValue object) {
			return createContextMappingNodeFeatureValueAdapter();
		}

		@Override
		public Adapter caseIteratorFeatureValue(IteratorFeatureValue object) {
			return createIteratorFeatureValueAdapter();
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
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediateModel
	 * @generated
	 */
	public Adapter createRoamIntermediateModelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.VariableSet <em>Variable Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.VariableSet
	 * @generated
	 */
	public Adapter createVariableSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.Type <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.Type
	 * @generated
	 */
	public Adapter createTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.Mapping <em>Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.Mapping
	 * @generated
	 */
	public Adapter createMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.Constraint <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.Constraint
	 * @generated
	 */
	public Adapter createConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.TypeConstraint <em>Type Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.TypeConstraint
	 * @generated
	 */
	public Adapter createTypeConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.MappingConstraint <em>Mapping Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.MappingConstraint
	 * @generated
	 */
	public Adapter createMappingConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.Context <em>Context</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.Context
	 * @generated
	 */
	public Adapter createContextAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.Iterator <em>Iterator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.Iterator
	 * @generated
	 */
	public Adapter createIteratorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression <em>Arithmetic Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression
	 * @generated
	 */
	public Adapter createArithmeticExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticExpression <em>Binary Arithmetic Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticExpression
	 * @generated
	 */
	public Adapter createBinaryArithmeticExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticExpression <em>Unary Arithmetic Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticExpression
	 * @generated
	 */
	public Adapter createUnaryArithmeticExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticStreamExpression <em>Arithmetic Stream Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticStreamExpression
	 * @generated
	 */
	public Adapter createArithmeticStreamExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.SumExpression <em>Sum Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.SumExpression
	 * @generated
	 */
	public Adapter createSumExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.MappingSumExpression <em>Mapping Sum Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.MappingSumExpression
	 * @generated
	 */
	public Adapter createMappingSumExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.TypeSumExpression <em>Type Sum Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.TypeSumExpression
	 * @generated
	 */
	public Adapter createTypeSumExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticValue <em>Arithmetic Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticValue
	 * @generated
	 */
	public Adapter createArithmeticValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticLiteral <em>Arithmetic Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticLiteral
	 * @generated
	 */
	public Adapter createArithmeticLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.IntegerLiteral <em>Integer Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.IntegerLiteral
	 * @generated
	 */
	public Adapter createIntegerLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.DoubleLiteral <em>Double Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.DoubleLiteral
	 * @generated
	 */
	public Adapter createDoubleLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolExpression <em>Bool Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BoolExpression
	 * @generated
	 */
	public Adapter createBoolExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolBinaryExpression <em>Bool Binary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BoolBinaryExpression
	 * @generated
	 */
	public Adapter createBoolBinaryExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolUnaryExpression <em>Bool Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BoolUnaryExpression
	 * @generated
	 */
	public Adapter createBoolUnaryExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolValue <em>Bool Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BoolValue
	 * @generated
	 */
	public Adapter createBoolValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolStreamExpression <em>Bool Stream Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BoolStreamExpression
	 * @generated
	 */
	public Adapter createBoolStreamExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.BoolLiteral <em>Bool Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.BoolLiteral
	 * @generated
	 */
	public Adapter createBoolLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.ValueExpression <em>Value Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ValueExpression
	 * @generated
	 */
	public Adapter createValueExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeValue <em>Context Type Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeValue
	 * @generated
	 */
	public Adapter createContextTypeValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNode <em>Context Mapping Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNode
	 * @generated
	 */
	public Adapter createContextMappingNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.IteratorValue <em>Iterator Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.IteratorValue
	 * @generated
	 */
	public Adapter createIteratorValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.FeatureExpression <em>Feature Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.FeatureExpression
	 * @generated
	 */
	public Adapter createFeatureExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.FeatureLiteral <em>Feature Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.FeatureLiteral
	 * @generated
	 */
	public Adapter createFeatureLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeFeatureValue <em>Context Type Feature Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeFeatureValue
	 * @generated
	 */
	public Adapter createContextTypeFeatureValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNodeFeatureValue <em>Context Mapping Node Feature Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNodeFeatureValue
	 * @generated
	 */
	public Adapter createContextMappingNodeFeatureValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.IteratorFeatureValue <em>Iterator Feature Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.IteratorFeatureValue
	 * @generated
	 */
	public Adapter createIteratorFeatureValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression <em>Stream Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression
	 * @generated
	 */
	public Adapter createStreamExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.roam.intermediate.RoamIntermediate.StreamOperation <em>Stream Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.roam.intermediate.RoamIntermediate.StreamOperation
	 * @generated
	 */
	public Adapter createStreamOperationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //RoamIntermediateAdapterFactory
