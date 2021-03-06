/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.emoflon.gips.intermediate.GipsIntermediate.*;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance
 * hierarchy. It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the
 * result of the switch. <!-- end-user-doc -->
 * 
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage
 * @generated
 */
public class GipsIntermediateSwitch<T> extends Switch<T> {
	/**
	 * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static GipsIntermediatePackage modelPackage;

	/**
	 * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public GipsIntermediateSwitch() {
		if (modelPackage == null) {
			modelPackage = GipsIntermediatePackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a
	 * non null result; it yields that result. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case GipsIntermediatePackage.GIPS_INTERMEDIATE_MODEL: {
			GipsIntermediateModel gipsIntermediateModel = (GipsIntermediateModel) theEObject;
			T result = caseGipsIntermediateModel(gipsIntermediateModel);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.ILP_CONFIG: {
			ILPConfig ilpConfig = (ILPConfig) theEObject;
			T result = caseILPConfig(ilpConfig);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.VARIABLE_SET: {
			VariableSet variableSet = (VariableSet) theEObject;
			T result = caseVariableSet(variableSet);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.PATTERN: {
			Pattern pattern = (Pattern) theEObject;
			T result = casePattern(pattern);
			if (result == null)
				result = caseVariableSet(pattern);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.TYPE: {
			Type type = (Type) theEObject;
			T result = caseType(type);
			if (result == null)
				result = caseVariableSet(type);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.MAPPING: {
			Mapping mapping = (Mapping) theEObject;
			T result = caseMapping(mapping);
			if (result == null)
				result = caseVariableSet(mapping);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.GT_MAPPING: {
			GTMapping gtMapping = (GTMapping) theEObject;
			T result = caseGTMapping(gtMapping);
			if (result == null)
				result = caseMapping(gtMapping);
			if (result == null)
				result = caseVariableSet(gtMapping);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.PATTERN_MAPPING: {
			PatternMapping patternMapping = (PatternMapping) theEObject;
			T result = casePatternMapping(patternMapping);
			if (result == null)
				result = caseMapping(patternMapping);
			if (result == null)
				result = caseVariableSet(patternMapping);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.VARIABLE: {
			Variable variable = (Variable) theEObject;
			T result = caseVariable(variable);
			if (result == null)
				result = caseVariableSet(variable);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.CONSTRAINT: {
			Constraint constraint = (Constraint) theEObject;
			T result = caseConstraint(constraint);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.GLOBAL_CONSTRAINT: {
			GlobalConstraint globalConstraint = (GlobalConstraint) theEObject;
			T result = caseGlobalConstraint(globalConstraint);
			if (result == null)
				result = caseConstraint(globalConstraint);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.OBJECTIVE: {
			Objective objective = (Objective) theEObject;
			T result = caseObjective(objective);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.GLOBAL_OBJECTIVE: {
			GlobalObjective globalObjective = (GlobalObjective) theEObject;
			T result = caseGlobalObjective(globalObjective);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.CONTEXT: {
			Context context = (Context) theEObject;
			T result = caseContext(context);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.PATTERN_CONSTRAINT: {
			PatternConstraint patternConstraint = (PatternConstraint) theEObject;
			T result = casePatternConstraint(patternConstraint);
			if (result == null)
				result = caseContext(patternConstraint);
			if (result == null)
				result = caseConstraint(patternConstraint);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.TYPE_CONSTRAINT: {
			TypeConstraint typeConstraint = (TypeConstraint) theEObject;
			T result = caseTypeConstraint(typeConstraint);
			if (result == null)
				result = caseContext(typeConstraint);
			if (result == null)
				result = caseConstraint(typeConstraint);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.MAPPING_CONSTRAINT: {
			MappingConstraint mappingConstraint = (MappingConstraint) theEObject;
			T result = caseMappingConstraint(mappingConstraint);
			if (result == null)
				result = caseContext(mappingConstraint);
			if (result == null)
				result = caseConstraint(mappingConstraint);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.PATTERN_OBJECTIVE: {
			PatternObjective patternObjective = (PatternObjective) theEObject;
			T result = casePatternObjective(patternObjective);
			if (result == null)
				result = caseContext(patternObjective);
			if (result == null)
				result = caseObjective(patternObjective);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.TYPE_OBJECTIVE: {
			TypeObjective typeObjective = (TypeObjective) theEObject;
			T result = caseTypeObjective(typeObjective);
			if (result == null)
				result = caseContext(typeObjective);
			if (result == null)
				result = caseObjective(typeObjective);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.MAPPING_OBJECTIVE: {
			MappingObjective mappingObjective = (MappingObjective) theEObject;
			T result = caseMappingObjective(mappingObjective);
			if (result == null)
				result = caseContext(mappingObjective);
			if (result == null)
				result = caseObjective(mappingObjective);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.ARITHMETIC_EXPRESSION: {
			ArithmeticExpression arithmeticExpression = (ArithmeticExpression) theEObject;
			T result = caseArithmeticExpression(arithmeticExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.BINARY_ARITHMETIC_EXPRESSION: {
			BinaryArithmeticExpression binaryArithmeticExpression = (BinaryArithmeticExpression) theEObject;
			T result = caseBinaryArithmeticExpression(binaryArithmeticExpression);
			if (result == null)
				result = caseArithmeticExpression(binaryArithmeticExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.UNARY_ARITHMETIC_EXPRESSION: {
			UnaryArithmeticExpression unaryArithmeticExpression = (UnaryArithmeticExpression) theEObject;
			T result = caseUnaryArithmeticExpression(unaryArithmeticExpression);
			if (result == null)
				result = caseArithmeticExpression(unaryArithmeticExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.SET_OPERATION: {
			SetOperation setOperation = (SetOperation) theEObject;
			T result = caseSetOperation(setOperation);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.ARITHMETIC_VALUE_EXPRESSION: {
			ArithmeticValueExpression arithmeticValueExpression = (ArithmeticValueExpression) theEObject;
			T result = caseArithmeticValueExpression(arithmeticValueExpression);
			if (result == null)
				result = caseArithmeticExpression(arithmeticValueExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.ARITHMETIC_VALUE: {
			ArithmeticValue arithmeticValue = (ArithmeticValue) theEObject;
			T result = caseArithmeticValue(arithmeticValue);
			if (result == null)
				result = caseArithmeticValueExpression(arithmeticValue);
			if (result == null)
				result = caseArithmeticExpression(arithmeticValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.ARITHMETIC_LITERAL: {
			ArithmeticLiteral arithmeticLiteral = (ArithmeticLiteral) theEObject;
			T result = caseArithmeticLiteral(arithmeticLiteral);
			if (result == null)
				result = caseArithmeticValueExpression(arithmeticLiteral);
			if (result == null)
				result = caseArithmeticExpression(arithmeticLiteral);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.VARIABLE_REFERENCE: {
			VariableReference variableReference = (VariableReference) theEObject;
			T result = caseVariableReference(variableReference);
			if (result == null)
				result = caseArithmeticValueExpression(variableReference);
			if (result == null)
				result = caseArithmeticExpression(variableReference);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.ARITHMETIC_NULL_LITERAL: {
			ArithmeticNullLiteral arithmeticNullLiteral = (ArithmeticNullLiteral) theEObject;
			T result = caseArithmeticNullLiteral(arithmeticNullLiteral);
			if (result == null)
				result = caseArithmeticLiteral(arithmeticNullLiteral);
			if (result == null)
				result = caseArithmeticValueExpression(arithmeticNullLiteral);
			if (result == null)
				result = caseArithmeticExpression(arithmeticNullLiteral);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.INTEGER_LITERAL: {
			IntegerLiteral integerLiteral = (IntegerLiteral) theEObject;
			T result = caseIntegerLiteral(integerLiteral);
			if (result == null)
				result = caseArithmeticLiteral(integerLiteral);
			if (result == null)
				result = caseArithmeticValueExpression(integerLiteral);
			if (result == null)
				result = caseArithmeticExpression(integerLiteral);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.DOUBLE_LITERAL: {
			DoubleLiteral doubleLiteral = (DoubleLiteral) theEObject;
			T result = caseDoubleLiteral(doubleLiteral);
			if (result == null)
				result = caseArithmeticLiteral(doubleLiteral);
			if (result == null)
				result = caseArithmeticValueExpression(doubleLiteral);
			if (result == null)
				result = caseArithmeticExpression(doubleLiteral);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.BOOL_EXPRESSION: {
			BoolExpression boolExpression = (BoolExpression) theEObject;
			T result = caseBoolExpression(boolExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.BOOL_BINARY_EXPRESSION: {
			BoolBinaryExpression boolBinaryExpression = (BoolBinaryExpression) theEObject;
			T result = caseBoolBinaryExpression(boolBinaryExpression);
			if (result == null)
				result = caseBoolExpression(boolBinaryExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.BOOL_UNARY_EXPRESSION: {
			BoolUnaryExpression boolUnaryExpression = (BoolUnaryExpression) theEObject;
			T result = caseBoolUnaryExpression(boolUnaryExpression);
			if (result == null)
				result = caseBoolExpression(boolUnaryExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.BOOL_VALUE_EXPRESSION: {
			BoolValueExpression boolValueExpression = (BoolValueExpression) theEObject;
			T result = caseBoolValueExpression(boolValueExpression);
			if (result == null)
				result = caseBoolExpression(boolValueExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.BOOL_VALUE: {
			BoolValue boolValue = (BoolValue) theEObject;
			T result = caseBoolValue(boolValue);
			if (result == null)
				result = caseBoolValueExpression(boolValue);
			if (result == null)
				result = caseBoolExpression(boolValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.BOOL_STREAM_EXPRESSION: {
			BoolStreamExpression boolStreamExpression = (BoolStreamExpression) theEObject;
			T result = caseBoolStreamExpression(boolStreamExpression);
			if (result == null)
				result = caseBoolValueExpression(boolStreamExpression);
			if (result == null)
				result = caseBoolExpression(boolStreamExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.RELATIONAL_EXPRESSION: {
			RelationalExpression relationalExpression = (RelationalExpression) theEObject;
			T result = caseRelationalExpression(relationalExpression);
			if (result == null)
				result = caseBoolValueExpression(relationalExpression);
			if (result == null)
				result = caseBoolExpression(relationalExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.BOOL_LITERAL: {
			BoolLiteral boolLiteral = (BoolLiteral) theEObject;
			T result = caseBoolLiteral(boolLiteral);
			if (result == null)
				result = caseBoolValueExpression(boolLiteral);
			if (result == null)
				result = caseBoolExpression(boolLiteral);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.VALUE_EXPRESSION: {
			ValueExpression valueExpression = (ValueExpression) theEObject;
			T result = caseValueExpression(valueExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.SUM_EXPRESSION: {
			SumExpression sumExpression = (SumExpression) theEObject;
			T result = caseSumExpression(sumExpression);
			if (result == null)
				result = caseValueExpression(sumExpression);
			if (result == null)
				result = caseSetOperation(sumExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.CONTEXT_SUM_EXPRESSION: {
			ContextSumExpression contextSumExpression = (ContextSumExpression) theEObject;
			T result = caseContextSumExpression(contextSumExpression);
			if (result == null)
				result = caseSumExpression(contextSumExpression);
			if (result == null)
				result = caseValueExpression(contextSumExpression);
			if (result == null)
				result = caseSetOperation(contextSumExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.MAPPING_SUM_EXPRESSION: {
			MappingSumExpression mappingSumExpression = (MappingSumExpression) theEObject;
			T result = caseMappingSumExpression(mappingSumExpression);
			if (result == null)
				result = caseSumExpression(mappingSumExpression);
			if (result == null)
				result = caseValueExpression(mappingSumExpression);
			if (result == null)
				result = caseSetOperation(mappingSumExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.PATTERN_SUM_EXPRESSION: {
			PatternSumExpression patternSumExpression = (PatternSumExpression) theEObject;
			T result = casePatternSumExpression(patternSumExpression);
			if (result == null)
				result = caseSumExpression(patternSumExpression);
			if (result == null)
				result = caseValueExpression(patternSumExpression);
			if (result == null)
				result = caseSetOperation(patternSumExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.TYPE_SUM_EXPRESSION: {
			TypeSumExpression typeSumExpression = (TypeSumExpression) theEObject;
			T result = caseTypeSumExpression(typeSumExpression);
			if (result == null)
				result = caseSumExpression(typeSumExpression);
			if (result == null)
				result = caseValueExpression(typeSumExpression);
			if (result == null)
				result = caseSetOperation(typeSumExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.CONTEXT_TYPE_VALUE: {
			ContextTypeValue contextTypeValue = (ContextTypeValue) theEObject;
			T result = caseContextTypeValue(contextTypeValue);
			if (result == null)
				result = caseValueExpression(contextTypeValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.CONTEXT_PATTERN_VALUE: {
			ContextPatternValue contextPatternValue = (ContextPatternValue) theEObject;
			T result = caseContextPatternValue(contextPatternValue);
			if (result == null)
				result = caseValueExpression(contextPatternValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.CONTEXT_PATTERN_NODE: {
			ContextPatternNode contextPatternNode = (ContextPatternNode) theEObject;
			T result = caseContextPatternNode(contextPatternNode);
			if (result == null)
				result = caseValueExpression(contextPatternNode);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.CONTEXT_MAPPING_VALUE: {
			ContextMappingValue contextMappingValue = (ContextMappingValue) theEObject;
			T result = caseContextMappingValue(contextMappingValue);
			if (result == null)
				result = caseValueExpression(contextMappingValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.CONTEXT_MAPPING_NODE: {
			ContextMappingNode contextMappingNode = (ContextMappingNode) theEObject;
			T result = caseContextMappingNode(contextMappingNode);
			if (result == null)
				result = caseValueExpression(contextMappingNode);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.OBJECTIVE_FUNCTION_VALUE: {
			ObjectiveFunctionValue objectiveFunctionValue = (ObjectiveFunctionValue) theEObject;
			T result = caseObjectiveFunctionValue(objectiveFunctionValue);
			if (result == null)
				result = caseValueExpression(objectiveFunctionValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.FEATURE_EXPRESSION: {
			FeatureExpression featureExpression = (FeatureExpression) theEObject;
			T result = caseFeatureExpression(featureExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.FEATURE_LITERAL: {
			FeatureLiteral featureLiteral = (FeatureLiteral) theEObject;
			T result = caseFeatureLiteral(featureLiteral);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.CONTEXT_TYPE_FEATURE_VALUE: {
			ContextTypeFeatureValue contextTypeFeatureValue = (ContextTypeFeatureValue) theEObject;
			T result = caseContextTypeFeatureValue(contextTypeFeatureValue);
			if (result == null)
				result = caseContextTypeValue(contextTypeFeatureValue);
			if (result == null)
				result = caseValueExpression(contextTypeFeatureValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.CONTEXT_PATTERN_NODE_FEATURE_VALUE: {
			ContextPatternNodeFeatureValue contextPatternNodeFeatureValue = (ContextPatternNodeFeatureValue) theEObject;
			T result = caseContextPatternNodeFeatureValue(contextPatternNodeFeatureValue);
			if (result == null)
				result = caseContextPatternNode(contextPatternNodeFeatureValue);
			if (result == null)
				result = caseValueExpression(contextPatternNodeFeatureValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.CONTEXT_MAPPING_NODE_FEATURE_VALUE: {
			ContextMappingNodeFeatureValue contextMappingNodeFeatureValue = (ContextMappingNodeFeatureValue) theEObject;
			T result = caseContextMappingNodeFeatureValue(contextMappingNodeFeatureValue);
			if (result == null)
				result = caseContextMappingNode(contextMappingNodeFeatureValue);
			if (result == null)
				result = caseValueExpression(contextMappingNodeFeatureValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.ITERATOR: {
			Iterator iterator = (Iterator) theEObject;
			T result = caseIterator(iterator);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.ITERATOR_PATTERN_VALUE: {
			IteratorPatternValue iteratorPatternValue = (IteratorPatternValue) theEObject;
			T result = caseIteratorPatternValue(iteratorPatternValue);
			if (result == null)
				result = caseValueExpression(iteratorPatternValue);
			if (result == null)
				result = caseIterator(iteratorPatternValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.ITERATOR_PATTERN_FEATURE_VALUE: {
			IteratorPatternFeatureValue iteratorPatternFeatureValue = (IteratorPatternFeatureValue) theEObject;
			T result = caseIteratorPatternFeatureValue(iteratorPatternFeatureValue);
			if (result == null)
				result = caseValueExpression(iteratorPatternFeatureValue);
			if (result == null)
				result = caseIterator(iteratorPatternFeatureValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.ITERATOR_PATTERN_NODE_VALUE: {
			IteratorPatternNodeValue iteratorPatternNodeValue = (IteratorPatternNodeValue) theEObject;
			T result = caseIteratorPatternNodeValue(iteratorPatternNodeValue);
			if (result == null)
				result = caseValueExpression(iteratorPatternNodeValue);
			if (result == null)
				result = caseIterator(iteratorPatternNodeValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.ITERATOR_PATTERN_NODE_FEATURE_VALUE: {
			IteratorPatternNodeFeatureValue iteratorPatternNodeFeatureValue = (IteratorPatternNodeFeatureValue) theEObject;
			T result = caseIteratorPatternNodeFeatureValue(iteratorPatternNodeFeatureValue);
			if (result == null)
				result = caseIteratorPatternNodeValue(iteratorPatternNodeFeatureValue);
			if (result == null)
				result = caseValueExpression(iteratorPatternNodeFeatureValue);
			if (result == null)
				result = caseIterator(iteratorPatternNodeFeatureValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.ITERATOR_MAPPING_VALUE: {
			IteratorMappingValue iteratorMappingValue = (IteratorMappingValue) theEObject;
			T result = caseIteratorMappingValue(iteratorMappingValue);
			if (result == null)
				result = caseValueExpression(iteratorMappingValue);
			if (result == null)
				result = caseIterator(iteratorMappingValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.ITERATOR_MAPPING_VARIABLE_VALUE: {
			IteratorMappingVariableValue iteratorMappingVariableValue = (IteratorMappingVariableValue) theEObject;
			T result = caseIteratorMappingVariableValue(iteratorMappingVariableValue);
			if (result == null)
				result = caseValueExpression(iteratorMappingVariableValue);
			if (result == null)
				result = caseIterator(iteratorMappingVariableValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.ITERATOR_MAPPING_FEATURE_VALUE: {
			IteratorMappingFeatureValue iteratorMappingFeatureValue = (IteratorMappingFeatureValue) theEObject;
			T result = caseIteratorMappingFeatureValue(iteratorMappingFeatureValue);
			if (result == null)
				result = caseValueExpression(iteratorMappingFeatureValue);
			if (result == null)
				result = caseIterator(iteratorMappingFeatureValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.ITERATOR_MAPPING_NODE_VALUE: {
			IteratorMappingNodeValue iteratorMappingNodeValue = (IteratorMappingNodeValue) theEObject;
			T result = caseIteratorMappingNodeValue(iteratorMappingNodeValue);
			if (result == null)
				result = caseValueExpression(iteratorMappingNodeValue);
			if (result == null)
				result = caseIterator(iteratorMappingNodeValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.ITERATOR_MAPPING_NODE_FEATURE_VALUE: {
			IteratorMappingNodeFeatureValue iteratorMappingNodeFeatureValue = (IteratorMappingNodeFeatureValue) theEObject;
			T result = caseIteratorMappingNodeFeatureValue(iteratorMappingNodeFeatureValue);
			if (result == null)
				result = caseIteratorMappingNodeValue(iteratorMappingNodeFeatureValue);
			if (result == null)
				result = caseValueExpression(iteratorMappingNodeFeatureValue);
			if (result == null)
				result = caseIterator(iteratorMappingNodeFeatureValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.ITERATOR_TYPE_VALUE: {
			IteratorTypeValue iteratorTypeValue = (IteratorTypeValue) theEObject;
			T result = caseIteratorTypeValue(iteratorTypeValue);
			if (result == null)
				result = caseValueExpression(iteratorTypeValue);
			if (result == null)
				result = caseIterator(iteratorTypeValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.ITERATOR_TYPE_FEATURE_VALUE: {
			IteratorTypeFeatureValue iteratorTypeFeatureValue = (IteratorTypeFeatureValue) theEObject;
			T result = caseIteratorTypeFeatureValue(iteratorTypeFeatureValue);
			if (result == null)
				result = caseIteratorTypeValue(iteratorTypeFeatureValue);
			if (result == null)
				result = caseValueExpression(iteratorTypeFeatureValue);
			if (result == null)
				result = caseIterator(iteratorTypeFeatureValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.STREAM_EXPRESSION: {
			StreamExpression streamExpression = (StreamExpression) theEObject;
			T result = caseStreamExpression(streamExpression);
			if (result == null)
				result = caseSetOperation(streamExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.STREAM_OPERATION: {
			StreamOperation streamOperation = (StreamOperation) theEObject;
			T result = caseStreamOperation(streamOperation);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.STREAM_NO_OPERATION: {
			StreamNoOperation streamNoOperation = (StreamNoOperation) theEObject;
			T result = caseStreamNoOperation(streamNoOperation);
			if (result == null)
				result = caseStreamOperation(streamNoOperation);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.STREAM_FILTER_OPERATION: {
			StreamFilterOperation streamFilterOperation = (StreamFilterOperation) theEObject;
			T result = caseStreamFilterOperation(streamFilterOperation);
			if (result == null)
				result = caseStreamOperation(streamFilterOperation);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.STREAM_SELECT_OPERATION: {
			StreamSelectOperation streamSelectOperation = (StreamSelectOperation) theEObject;
			T result = caseStreamSelectOperation(streamSelectOperation);
			if (result == null)
				result = caseStreamOperation(streamSelectOperation);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GipsIntermediatePackage.STREAM_CONTAINS_OPERATION: {
			StreamContainsOperation streamContainsOperation = (StreamContainsOperation) theEObject;
			T result = caseStreamContainsOperation(streamContainsOperation);
			if (result == null)
				result = caseStreamOperation(streamContainsOperation);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Model</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Model</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGipsIntermediateModel(GipsIntermediateModel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ILP
	 * Config</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ILP
	 *         Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseILPConfig(ILPConfig object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Variable
	 * Set</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Variable
	 *         Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVariableSet(VariableSet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Pattern</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Pattern</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePattern(Pattern object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Type</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseType(Type object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Mapping</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMapping(Mapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>GT
	 * Mapping</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>GT
	 *         Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGTMapping(GTMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pattern
	 * Mapping</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pattern
	 *         Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePatternMapping(PatternMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Variable</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVariable(Variable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Constraint</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConstraint(Constraint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Global
	 * Constraint</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Global
	 *         Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGlobalConstraint(GlobalConstraint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Objective</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Objective</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseObjective(Objective object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Global
	 * Objective</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Global
	 *         Objective</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGlobalObjective(GlobalObjective object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Context</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Context</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseContext(Context object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pattern
	 * Constraint</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pattern
	 *         Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePatternConstraint(PatternConstraint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type
	 * Constraint</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type
	 *         Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTypeConstraint(TypeConstraint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mapping
	 * Constraint</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mapping
	 *         Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMappingConstraint(MappingConstraint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pattern
	 * Objective</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pattern
	 *         Objective</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePatternObjective(PatternObjective object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type
	 * Objective</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type
	 *         Objective</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTypeObjective(TypeObjective object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mapping
	 * Objective</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mapping
	 *         Objective</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMappingObjective(MappingObjective object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Arithmetic Expression</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Arithmetic Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArithmeticExpression(ArithmeticExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Binary
	 * Arithmetic Expression</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Binary
	 *         Arithmetic Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBinaryArithmeticExpression(BinaryArithmeticExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Unary
	 * Arithmetic Expression</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unary
	 *         Arithmetic Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUnaryArithmeticExpression(UnaryArithmeticExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Set
	 * Operation</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Set
	 *         Operation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSetOperation(SetOperation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Arithmetic Value Expression</em>'. <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Arithmetic Value Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArithmeticValueExpression(ArithmeticValueExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Arithmetic Value</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Arithmetic Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArithmeticValue(ArithmeticValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Arithmetic Literal</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Arithmetic Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArithmeticLiteral(ArithmeticLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Variable
	 * Reference</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Variable
	 *         Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVariableReference(VariableReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Arithmetic Null Literal</em>'. <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Arithmetic Null Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArithmeticNullLiteral(ArithmeticNullLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Integer
	 * Literal</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Integer
	 *         Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIntegerLiteral(IntegerLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Double
	 * Literal</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Double
	 *         Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDoubleLiteral(DoubleLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bool
	 * Expression</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bool
	 *         Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBoolExpression(BoolExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bool
	 * Binary Expression</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bool
	 *         Binary Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBoolBinaryExpression(BoolBinaryExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bool
	 * Unary Expression</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bool
	 *         Unary Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBoolUnaryExpression(BoolUnaryExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bool
	 * Value Expression</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bool
	 *         Value Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBoolValueExpression(BoolValueExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bool
	 * Value</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bool
	 *         Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBoolValue(BoolValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bool
	 * Stream Expression</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bool
	 *         Stream Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBoolStreamExpression(BoolStreamExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Relational Expression</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Relational Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRelationalExpression(RelationalExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Bool
	 * Literal</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Bool
	 *         Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBoolLiteral(BoolLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Value
	 * Expression</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Value
	 *         Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseValueExpression(ValueExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sum
	 * Expression</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sum
	 *         Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSumExpression(SumExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Context
	 * Sum Expression</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Context
	 *         Sum Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseContextSumExpression(ContextSumExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mapping
	 * Sum Expression</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mapping
	 *         Sum Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMappingSumExpression(MappingSumExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pattern
	 * Sum Expression</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pattern
	 *         Sum Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePatternSumExpression(PatternSumExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type Sum
	 * Expression</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Sum
	 *         Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTypeSumExpression(TypeSumExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Context
	 * Type Value</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Context
	 *         Type Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseContextTypeValue(ContextTypeValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Context
	 * Pattern Value</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Context
	 *         Pattern Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseContextPatternValue(ContextPatternValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Context
	 * Pattern Node</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Context
	 *         Pattern Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseContextPatternNode(ContextPatternNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Context
	 * Mapping Value</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Context
	 *         Mapping Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseContextMappingValue(ContextMappingValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Context
	 * Mapping Node</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Context
	 *         Mapping Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseContextMappingNode(ContextMappingNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Objective Function Value</em>'. <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Objective Function Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseObjectiveFunctionValue(ObjectiveFunctionValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Feature
	 * Expression</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Feature
	 *         Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFeatureExpression(FeatureExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Feature
	 * Literal</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Feature
	 *         Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFeatureLiteral(FeatureLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Context
	 * Type Feature Value</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Context
	 *         Type Feature Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseContextTypeFeatureValue(ContextTypeFeatureValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Context
	 * Pattern Node Feature Value</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Context
	 *         Pattern Node Feature Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseContextPatternNodeFeatureValue(ContextPatternNodeFeatureValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Context
	 * Mapping Node Feature Value</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Context
	 *         Mapping Node Feature Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseContextMappingNodeFeatureValue(ContextMappingNodeFeatureValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>Iterator</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>Iterator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIterator(Iterator object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Iterator
	 * Pattern Value</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Iterator
	 *         Pattern Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIteratorPatternValue(IteratorPatternValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Iterator
	 * Pattern Feature Value</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Iterator
	 *         Pattern Feature Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIteratorPatternFeatureValue(IteratorPatternFeatureValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Iterator
	 * Pattern Node Value</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Iterator
	 *         Pattern Node Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIteratorPatternNodeValue(IteratorPatternNodeValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Iterator
	 * Pattern Node Feature Value</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Iterator
	 *         Pattern Node Feature Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIteratorPatternNodeFeatureValue(IteratorPatternNodeFeatureValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Iterator
	 * Mapping Value</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Iterator
	 *         Mapping Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIteratorMappingValue(IteratorMappingValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Iterator
	 * Mapping Variable Value</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Iterator
	 *         Mapping Variable Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIteratorMappingVariableValue(IteratorMappingVariableValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Iterator
	 * Mapping Feature Value</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Iterator
	 *         Mapping Feature Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIteratorMappingFeatureValue(IteratorMappingFeatureValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Iterator
	 * Mapping Node Value</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Iterator
	 *         Mapping Node Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIteratorMappingNodeValue(IteratorMappingNodeValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Iterator
	 * Mapping Node Feature Value</em>'. <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Iterator
	 *         Mapping Node Feature Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIteratorMappingNodeFeatureValue(IteratorMappingNodeFeatureValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Iterator
	 * Type Value</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Iterator
	 *         Type Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIteratorTypeValue(IteratorTypeValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Iterator
	 * Type Feature Value</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Iterator
	 *         Type Feature Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIteratorTypeFeatureValue(IteratorTypeFeatureValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Stream
	 * Expression</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Stream
	 *         Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStreamExpression(StreamExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Stream
	 * Operation</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Stream
	 *         Operation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStreamOperation(StreamOperation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Stream
	 * No Operation</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Stream
	 *         No Operation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStreamNoOperation(StreamNoOperation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Stream
	 * Filter Operation</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Stream
	 *         Filter Operation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStreamFilterOperation(StreamFilterOperation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Stream
	 * Select Operation</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Stream
	 *         Select Operation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStreamSelectOperation(StreamSelectOperation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Stream
	 * Contains Operation</em>'. <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Stream
	 *         Contains Operation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStreamContainsOperation(StreamContainsOperation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of
	 * '<em>EObject</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last
	 * case anyway. <!-- end-user-doc -->
	 * 
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of
	 *         '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} // GipsIntermediateSwitch
