package org.emoflon.gips.build.generator.templates

import org.eclipse.emf.ecore.EObject
import org.emoflon.gips.intermediate.GipsIntermediate.Variable
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantLiteral
import org.emoflon.gips.intermediate.GipsIntermediate.MappingReference
import org.emoflon.gips.intermediate.GipsIntermediate.TypeReference
import org.emoflon.gips.intermediate.GipsIntermediate.PatternReference
import org.emoflon.gips.intermediate.GipsIntermediate.RuleReference
import org.emoflon.gips.intermediate.GipsIntermediate.ContextReference
import org.emoflon.gips.intermediate.GipsIntermediate.NodeReference
import org.emoflon.gips.intermediate.GipsIntermediate.AttributeReference
import org.emoflon.gips.intermediate.GipsIntermediate.AttributeExpression
import org.emoflon.gips.intermediate.GipsIntermediate.SetExpression
import org.emoflon.gips.intermediate.GipsIntermediate.SetReduce
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanBinaryExpression
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanUnaryExpression
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanLiteral
import org.emoflon.gips.intermediate.GipsIntermediate.SetFilter
import org.emoflon.gips.intermediate.GipsIntermediate.SetTypeSelect
import org.emoflon.gips.intermediate.GipsIntermediate.SetSort
import org.emoflon.gips.intermediate.GipsIntermediate.SetConcatenation
import org.emoflon.gips.intermediate.GipsIntermediate.SetSimpleOperation
import org.emoflon.gips.intermediate.GipsIntermediate.SetTransformation
import org.emoflon.gips.intermediate.GipsIntermediate.SetSummation
import org.emoflon.gips.intermediate.GipsIntermediate.SetSimpleSelect
import org.emoflon.gips.intermediate.GipsIntermediate.SetTypeQuery
import org.emoflon.gips.intermediate.GipsIntermediate.SetElementQuery
import org.emoflon.gips.intermediate.GipsIntermediate.SetSimpleQuery
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage
import org.eclipse.emf.ecore.EcorePackage
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticBinaryExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticUnaryExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticLiteral
import org.emoflon.gips.intermediate.GipsIntermediate.DoubleLiteral
import org.emoflon.gips.intermediate.GipsIntermediate.IntegerLiteral
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanExpression
import java.util.LinkedList
import java.util.HashSet
import org.emoflon.gips.intermediate.GipsIntermediate.SetOperation
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression
import java.util.HashMap
import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.intermediate.GipsIntermediate.Constant
import java.util.Collection
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantReference
import org.emoflon.gips.intermediate.GipsIntermediate.QueryOperator
import org.eclipse.emf.ecore.EEnum
import org.emoflon.gips.intermediate.GipsIntermediate.LinearFunctionReference
import org.eclipse.emf.ecore.EClass
import org.emoflon.gips.build.generator.templates.constraint.TypeConstraintTemplate
import org.emoflon.gips.build.generator.templates.constraint.RuleConstraintTemplate
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator
import java.util.List
import org.emoflon.gips.build.generator.templates.constraint.MappingConstraintTemplate
import org.emoflon.gips.build.generator.templates.constraint.PatternConstraintTemplate
import org.eclipse.emf.ecore.util.EcoreUtil
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanBinaryOperator
import java.util.Collections
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel
import java.util.Set
import org.emoflon.gips.build.generator.templates.function.PatternFunctionTemplate
import org.emoflon.gips.build.generator.templates.function.MappingFunctionTemplate
import org.emoflon.gips.build.generator.templates.function.RuleFunctionTemplate
import org.emoflon.gips.build.generator.templates.function.TypeFunctionTemplate

abstract class ProblemGeneratorTemplate <CONTEXT extends EObject> extends GeneratorTemplate<CONTEXT> {
	
	public val iterator2variableName = new HashMap<SetOperation, String>();
	public val builderMethodNames = new HashSet<String>
	public val builderMethods = new HashMap<EObject,String>
	public val builderMethodDefinitions = new HashMap<EObject,String>
	public val builderMethodCalls2 = new LinkedList<String>
	
	new(TemplateData data, CONTEXT context) {
		super(data, context)
	}
	
	def String generatePackageDeclaration();
	def String generateImports();
	def String getVariable(Variable variable);
	def String getContextParameterType();
	def String getContextParameter() {
		return '''final «getContextParameterType()» context'''
	}
	def String getParametersForVoidBuilder() {
		return '''final List<Term> terms, «getContextParameter()»'''
	}
	def String getCallParametersForVoidBuilder() {
		return '''terms, context'''
	}
	
	def String getVariableInSet(Variable variable) {
		if(isMappingVariable(variable)) {
			return '''elt'''
		} else {
			return '''engine.getNonMappingVariable(elt, "«variable.name»")'''
		}
	}
	
	def String getConstantName(Constant constant) {
		if(constant.isGlobal)
			return constant.name
		else
			return '''c«constant.name.toFirstUpper»'''
	}
	
	def String getConstantValue(Constant constant) {
		if(constant.isGlobal) {
			return '''((«extractReturnType(constant.expression)»)engine.getConstantValue("«getConstantName(constant)»"))'''
		} else {
			return '''«getConstantName(constant)»'''
		}
	}
	
	def String getParametersForConstants(Collection<Constant> constants) {
		return '''«FOR constant : constants SEPARATOR ', '»«extractReturnType(constant.expression)» «getConstantName(constant)»«ENDFOR»'''
	}
	
	def String getCallParametersForConstants(Collection<Constant> constants) {
		return '''«FOR constant : constants SEPARATOR ', '»«getConstantName(constant)»«ENDFOR»'''
	}
	
	def String getCallConstantCalculator(Constant constant) {
		return '''calculate«constant.name.toFirstUpper»(context)'''
	}
	
	def String getConstantCalculator(Constant constant) {
		return '''protected «extractReturnType(constant.expression)» calculate«constant.name.toFirstUpper»(«getContextParameter()») {
	return 	«IF constant.expression instanceof ArithmeticExpression»«generateConstantExpression(constant.expression as ArithmeticExpression)»«ELSE»«generateConstantExpression(constant.expression as BooleanExpression)»«ENDIF»;
}
		'''
	}
	
	def String getConstantFields(Collection<Constant> constants) {
		return '''«FOR constant : constants»
		«extractReturnType(constant.expression)» «getConstantName(constant)» = «getCallConstantCalculator(constant)»;
		«ENDFOR»
		'''
	}
	
	def String getConstantCalculators(Collection<Constant> constants) {
		return '''«FOR constant : constants»
		«getConstantCalculator(constant)»
		
		«ENDFOR»
		'''
	}
	
	def Collection<Constant> getConstants();
	
	def String getIterator(ContextReference reference) {
		if(reference.isLocal)
			return '''context'''
			
		var current = reference as EObject;
		while(current.eContainer !== null) {
			if(current.eContainer instanceof RelationalExpression) {
				if(current.eContainer.eContainer instanceof SetSort) {
					if(current.eContainingFeature.equals(GipsIntermediatePackage.eINSTANCE.relationalExpression_Lhs)) {
						return '''elt1'''
					} else {
						return '''elt2'''
					}
				} else {
					current = current.eContainer
				}
			} else {
				current = current.eContainer
			}
		}

		return '''elt''';
	}
	
	def String generateConstTermBuilder(ArithmeticExpression constExpr) {
		return '''«generateConstantExpression(constExpr)»'''
	}
	
	def String generateConstTermBuilder(BooleanExpression constExpr) {
		return '''«generateConstantExpression(constExpr)»'''
	}
	
	def String getAdditionalVariableName(VariableReference varRef) {
		return '''engine.getNonMappingVariable(context, "«varRef.variable.name»").getName()'''
	}
	
	def String generateBuilder(ArithmeticBinaryExpression expr, LinkedList<String> methodCalls) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		val method = '''
	protected double «methodName»(«getContextParameter()»«IF !getConstants().empty», «ENDIF»«getParametersForConstants(getConstants())») {
		return «generateConstantExpression(expr)»;
	}
		'''
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	def String generateBuilder(ArithmeticUnaryExpression expr, LinkedList<String> methodCalls) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		val method = '''
	protected double «methodName»(«getContextParameter()»«IF !getConstants().empty», «ENDIF»«getParametersForConstants(getConstants())») {
		return «generateConstantExpression(expr)»;
	}
		'''
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	def void generateBuilder(ConstantReference expr, LinkedList<String> methodCalls) {
		if(expr.setExpression === null){
			return
		}
			
		if(expr.setExpression.setReduce === null){
			return
		}
		
		val sumExpression = expr.setExpression.setReduce as SetSummation
		
		val builderMethodName = '''builder_«builderMethods.size»'''
		val instruction = '''«builderMethodName»(«callParametersForVoidBuilder»«IF !getConstants().empty», «ENDIF»«getCallParametersForConstants(getConstants())»);'''
		methodCalls.add(instruction)
		
		builderMethods.put(expr, builderMethodName)
		imports.add("java.util.stream.Collectors")
		val vars = new HashSet
		val varRefs = new HashSet
		
		GipsTransformationUtils.extractVariableReference(sumExpression.expression).forEach[ref |
			if(vars.add(ref.variable)) {
				varRefs.add(ref)
			}
		]
		
		if(vars.size > 1 || varRefs.size > 1)
			throw new UnsupportedOperationException("A single sum operation over a set of variables may not contain different kinds of variables.")
		val variable = varRefs.iterator.next
		
		val method = '''
	protected void «builderMethodName»(«parametersForVoidBuilder»«IF !getConstants().empty», «ENDIF»«getParametersForConstants(getConstants())») {
		«generateValueAccess(expr)»
		«IF expr.setExpression.setOperation !== null»«generateConstantExpression(expr.setExpression.setOperation)»«ENDIF»
		.forEach(elt -> {
			«IF variable.local» terms.add(new Term(«getVariable(variable.variable)», (double)«generateConstantExpression(sumExpression.expression)»));
			«ELSE»terms.add(new Term(«getVariableInSet(variable.variable)», (double)«generateConstantExpression(sumExpression.expression)»));
			«ENDIF»
		});
	}
		'''
		builderMethodDefinitions.put(expr, method)
	}
	
	def void generateBuilder(ValueExpression expr, LinkedList<String> methodCalls) {
		if(expr instanceof VariableReference) {
			val instruction = '''terms.add(new Term(«getVariable(expr.variable)», 1.0));'''
			methodCalls.add(instruction)
			return
		}
		
		if(expr.setExpression === null){
			return
		}
			
		if(expr.setExpression.setReduce === null){
			return
		}
		
		val sumExpression = expr.setExpression.setReduce as SetSummation
		
		val builderMethodName = '''builder_«builderMethods.size»'''
		val instruction = '''«builderMethodName»(«callParametersForVoidBuilder»«IF !getConstants().empty», «ENDIF»«getCallParametersForConstants(getConstants())»);'''
		methodCalls.add(instruction)
		
		builderMethods.put(expr, builderMethodName)
		imports.add("java.util.stream.Collectors")
		val vars = new HashSet
		val varRefs = new HashSet
		
		GipsTransformationUtils.extractVariableReference(sumExpression.expression).forEach[ref |
			if(vars.add(ref.variable)) {
				varRefs.add(ref)
			}
		]
		
		if(vars.size > 1 || varRefs.size > 1)
			throw new UnsupportedOperationException("A single sum operation over a set of variables may not contain different kinds of variables.")
		val variable = varRefs.iterator.next
		
		val method = '''
	protected void «builderMethodName»(«parametersForVoidBuilder»«IF !getConstants().empty», «ENDIF»«getParametersForConstants(getConstants())») {
		«generateValueAccess(expr, false)»
		«IF expr.setExpression.setOperation !== null»«generateConstantExpression(expr.setExpression.setOperation)»«ENDIF»
		.forEach(elt -> {
			«IF variable.local» terms.add(new Term(«getVariable(variable.variable)», (double)«generateConstantExpression(sumExpression.expression)»));
			«ELSE»terms.add(new Term(«getVariableInSet(variable.variable)», (double)«generateConstantExpression(sumExpression.expression)»));
			«ENDIF»
		});
	}
		'''
		builderMethodDefinitions.put(expr, method)
	}
	
	/**
	 * Returns a list of Strings containing all names of context node accesses of the given Boolean expression.
	 */
	 @Deprecated
	def List<String> getContextNodeAccess(BooleanExpression expr) {
		// Single relational expression only
		if (expr instanceof RelationalExpression) {
			return getContextNodeAccess(expr)
		}
		// Composition of multiple boolean expressions
		else if (expr instanceof BooleanBinaryExpression) {
			return getContextNodeAccess(expr)
		}
	}

	/**
	 * Returns a list of Strings containing all names of context node accesses of the given Boolean binary expression.
	 * The only implemented type of operator is `AND`. Hence, this method only supports sub expressions concatenated
	 * via `&`.
	 */
	 @Deprecated
	def List<String> getContextNodeAccess(BooleanBinaryExpression expr) {
		var foundNodes = new LinkedList<String>
		switch (expr.operator) {
			case AND: {
				foundNodes.addAll(getContextNodeAccess(expr.lhs))
				foundNodes.addAll(getContextNodeAccess(expr.rhs))
			}
			default: {
				// Do nothing
			}
		}
		return foundNodes
	}

	/**
	 * Returns a list of Strings containing all names of context node accesses of the given relational expression.
	 * The requirements to find the necessary accesses are:
	 * - the operator must be `EQUAL`
	 * - one side of the expression must be local and the other side must not be local
	 * - both sides must not have an attribute access
	 * 
	 * Therefore, this method only captures accesses like `element.nodes.a == context.nodes.b`.
	 */
	 @Deprecated
	def List<String> getContextNodeAccess(RelationalExpression expr) {
		var foundNodes = new LinkedList<String>

		// Only if the operator is `EQUAL`
		if (expr.operator.equals(RelationalOperator.EQUAL)) {
			// One side must be local and one must not be local
			if (expr.lhs instanceof NodeReference && expr.rhs instanceof NodeReference) {
				var lhsnode = expr.lhs as NodeReference
				var rhsnode = expr.rhs as NodeReference
				// Both sides must not have an attribute access
				if (lhsnode.attribute === null && rhsnode.attribute === null) {
					if (lhsnode.local && !rhsnode.local || !lhsnode.local && rhsnode.local) {
						if (lhsnode.local) {
							foundNodes.add(lhsnode.node.name)
						} else if (rhsnode.local) {
							foundNodes.add(rhsnode.node.name)
						}
					}
				}
			}
		}

		return foundNodes
	}

	/**
	 * Returns a list of Strings containing all names of context node accesses of the given set expression.
	 * The requirements to find the necessary accesses are:
	 * - the expression must be of type `filter`
	 * - the operator used within the filter must be `EQUAL`
	 * - one side of the respective expression must be local and the other side must not be local
	 * - both respective sides must not have an attribute access
	 * 
	 * Therefore, this method only captures accesses like `filter(element.nodes.a == context.nodes.b)`.
	 */
	 @Deprecated
	def List<String> getContextNodeAccess(SetOperation expr) {
		var foundNodes = new LinkedList<String>

		if (expr instanceof SetFilter) {
			// Single relational expression
			if (expr.expression instanceof RelationalExpression) {
				var relExpr = expr.expression as RelationalExpression
				foundNodes.addAll(getContextNodeAccess(relExpr))
			}
			// Multiple expressions composed
			else if (expr.expression instanceof BooleanBinaryExpression) {
				var boolBinExpr = expr.expression as BooleanBinaryExpression
				foundNodes.addAll(getContextNodeAccess(boolBinExpr))
			}
		}

		return foundNodes
	}

	/**
	 * Returns `true` if the given set operation fulfills all conditions for the context of the
	 * respective constraint to be indexed.
	 */
	def boolean isContextIndexerApplicable(SetOperation expr) {
		if (expr instanceof SetFilter) {
			// Single relational expression
			if (expr.expression instanceof RelationalExpression) {
				var relExpr = expr.expression as RelationalExpression
				return isContextIndexerApplicable(relExpr);
			} // Multiple expressions composed
			else if (expr.expression instanceof BooleanBinaryExpression) {
				var boolBinExpr = expr.expression as BooleanBinaryExpression
				return isContextIndexerApplicable(boolBinExpr);
			}
		}
		return false;
	}

	/**
	 * Returns `true` if the given boolean expression fulfills all conditions for the context of
	 * the respective constraint to be indexed.
	 */
	def boolean isContextIndexerApplicable(BooleanExpression expr) {
		// Single relational expression only
		if (expr instanceof RelationalExpression) {
			return isContextIndexerApplicable(expr)
		} // Composition of multiple boolean expressions
		else if (expr instanceof BooleanBinaryExpression) {
			return isContextIndexerApplicable(expr)
		}
		return false;
	}

	/**
	 * Returns `true` if the given boolean binary expression fulfills all conditions for the context
	 * of the respective constraint to be indexed.
	 */
	def boolean isContextIndexerApplicable(BooleanBinaryExpression expr) {
		switch (expr.operator) {
			case AND: {
				var lhs = isContextIndexerApplicable(expr.lhs)
				var rhs = isContextIndexerApplicable(expr.rhs)
				return lhs || rhs
			}
			default: {
				// Do nothing
			}
		}
		return false;
	}

	/**
	 * Returns `true` if the given relational expression fulfills all conditions for the context of
	 * the respective constraint to be indexed.
	 */
	def boolean isContextIndexerApplicable(RelationalExpression relExpr) {
		// Only if the operator is `EQUAL`
		if (relExpr.operator.equals(RelationalOperator.EQUAL)) {
			// One side must be local and one must not be local
			if (relExpr.lhs instanceof NodeReference && relExpr.rhs instanceof ContextReference) {
				var lhsnode = relExpr.lhs as NodeReference
				var rhsnode = relExpr.rhs as ContextReference
				// Both sides must not have an attribute access
				if (lhsnode.attribute === null && !(rhsnode instanceof AttributeReference)) {
					if (lhsnode.local && !rhsnode.local || !lhsnode.local && rhsnode.local) {
						return true;
					}
				}
			} else if (relExpr.lhs instanceof ContextReference && relExpr.rhs instanceof NodeReference) {
				var lhsnode = relExpr.lhs as ContextReference
				var rhsnode = relExpr.rhs as NodeReference
				// Both sides must not have an attribute access
				if (rhsnode.attribute === null && !(lhsnode instanceof AttributeReference)) {
					if (lhsnode.local && !rhsnode.local || !lhsnode.local && rhsnode.local) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * This method converts a given list of context node names to a getter string.
	 * 
	 * Example input: "{a, b, cde}"
	 * Example output: "getA(), getB(), getCde()"
	 */
	def String convertContextNodeAccessToGetterCalls(List<String> contextNodeAccesses) {
		var foundGetters = ''''''
		for (var i = 0; i < contextNodeAccesses.size; i++) {
			var candidate = contextNodeAccesses.get(i).toFirstUpper
			foundGetters += '''context.get«candidate»()'''
			if (i < contextNodeAccesses.size - 1) {
				foundGetters += ''', '''
			}
		}
		return foundGetters
	}

	def String generateConstantExpression(ArithmeticExpression expression) {
		if(expression instanceof ArithmeticBinaryExpression) {
			switch(expression.operator) {
				case ADD: {
					return '''«generateConstantExpression(expression.lhs)» + «generateConstantExpression(expression.rhs)»'''
				}
				case DIVIDE: {
					return '''(«generateConstantExpression(expression.lhs)») / («generateConstantExpression(expression.rhs)»)'''
				}
				case MULTIPLY: {
					return '''(«generateConstantExpression(expression.lhs)») * («generateConstantExpression(expression.rhs)»)'''
				}
				case POW: {
					return '''Math.pow(«generateConstantExpression(expression.lhs)», «generateConstantExpression(expression.rhs)»)'''
				}
				case SUBTRACT: {
					return '''«generateConstantExpression(expression.lhs)» - «generateConstantExpression(expression.rhs)»'''
				}
				case LOG: {
					return '''Math.log(«generateConstantExpression(expression.lhs)») / Math.log(«generateConstantExpression(expression.rhs)»)'''
				}
			}
		} else if(expression instanceof ArithmeticUnaryExpression) {
			switch(expression.operator) {
				case ABSOLUTE: {
					return '''Math.abs(«generateConstantExpression(expression.operand)»)'''
				}
				case BRACKET: {
					return '''(«generateConstantExpression(expression.operand)»)'''
				}
				case COSINE: {
					return '''Math.cos(«generateConstantExpression(expression.operand)»)'''
				}
				case NEGATE: {
					return '''-«generateConstantExpression(expression.operand)»'''
				}
				case SINE: {
					return '''Math.sin(«generateConstantExpression(expression.operand)»)'''
				}
				case SQRT: {
					return '''Math.sqrt(«generateConstantExpression(expression.operand)»)'''
				}
			}
		} else if(expression instanceof ArithmeticLiteral) {
			if(expression instanceof DoubleLiteral) {
				return String.valueOf(expression.literal)
			} else {
				return String.valueOf((expression as IntegerLiteral).literal)
			}
		} else if(expression instanceof ConstantLiteral) {
			switch(expression.constant) {
				case E: {
					return '''Math.E'''
				}
				case NULL: {
					return '''null'''
				}
				case PI: {
					return '''Math.PI'''
				}
			}
		} else if(expression instanceof ConstantReference) {
			if(expression.setExpression !== null) {
				return '''«getConstantValue(expression.constant)»«generateConstantExpression(expression.setExpression)»'''
			} else {
				return getConstantValue(expression.constant)
			}
		} else if(expression instanceof ValueExpression) {
			return generateConstantExpression(expression)
		} else {
			// CASE: LinearFunctionReference -> return a constant 1 since the variable should have already been extracted.
			return '''1.0'''
		}
		
	}
	
	def String generateConstantExpression(ValueExpression expression) {
		var instruction = generateValueAccess(expression, true)
		if(expression.setExpression !== null) {
			instruction += generateConstantExpression(expression.setExpression)
		}
		return instruction;
	}
	
	def String generateValueAccess(ValueExpression expression, boolean requiresReturn) {
		var instruction = "";
		
		if(expression instanceof MappingReference) {
			instruction = generateValueAccess(expression as MappingReference, requiresReturn)
		} else if(expression instanceof TypeReference) {
			instruction = generateValueAccess(expression as TypeReference, requiresReturn)
		} else if(expression instanceof PatternReference) {
			instruction = generateValueAccess(expression as PatternReference, requiresReturn)
		} else if(expression instanceof RuleReference) {
			instruction = generateValueAccess(expression as RuleReference, requiresReturn)
		} else if(expression instanceof NodeReference) {
			instruction = generateValueAccess(expression as NodeReference, requiresReturn)
		} else if(expression instanceof AttributeReference) {
			instruction = generateValueAccess(expression as AttributeReference, requiresReturn)
		} else if(expression instanceof VariableReference) {
			instruction = generateValueAccess(expression as VariableReference, requiresReturn)
		} else {
			instruction = generateValueAccess(expression as ContextReference, requiresReturn)
		}
		
		return instruction;
	}
		
	def String generateValueAccess(ContextReference expression, boolean requiresReturn){
		// CASE: ContextReference
		return getIterator(expression as ContextReference)
	}
	
	def String generateValueAccess(VariableReference expression, boolean requiresReturn){
		// CASE: VariableReference -> return a constant 1 since the variable should have already been extracted.
		return '''1.0'''
	}
	
	def String generateValueAccess(AttributeReference expression, boolean requiresReturn){
		return '''«getIterator(expression)».«generateAttributeExpression(expression.attribute)»'''
	}
	
	def String generateValueAccess(NodeReference expression, boolean requiresReturn){
		var instruction = '''«getIterator(expression)».get«expression.node.name.toFirstUpper»()'''
		if(expression.attribute !== null) {
			instruction = '''«instruction».«generateAttributeExpression(expression.attribute)»'''
		}
		return instruction
	}
	
	def String generateValueAccess(RuleReference expression, boolean requiresReturn){
		imports.add(data.apiData.matchesPkg+"."+data.ibex2matchClassName.get(expression.rule))
		return '''engine.getEMoflonAPI().«expression.rule.name»().findMatches(false).parallelStream()'''
	}
	
	def String generateValueAccess(PatternReference expression, boolean requiresReturn){
		imports.add(data.apiData.matchesPkg+"."+data.ibex2matchClassName.get(expression.pattern))
		return '''engine.getEMoflonAPI().«expression.pattern.name»().findMatches(false).parallelStream()'''
	}
	
	def String generateValueAccess(TypeReference expression, boolean requiresReturn){
		imports.add(data.classToPackage.getImportsForType(expression.type))
		return '''indexer.getObjectsOfType("«expression.type.name»").parallelStream().map(type -> («expression.type.name») type)'''
	}
	
	def String generateValueAccess(MappingReference expression, boolean requiresReturn) {
		imports.add(data.apiData.gipsMappingPkg + "." + data.mapping2mappingClassName.get(expression.mapping))
		val original = '''engine.getMapper("«expression.mapping.name»").getMappings().values().parallelStream()
		.map(mapping -> («data.mapping2mappingClassName.get(expression.mapping)») mapping)'''

		// If a return is required, use the original implementation
		if(requiresReturn)
			return original

		// Else, try to use the mapping indexer implementation
		// Determine what to search for with the indexer
		// If the resulting string keeps empty, there is nothing to index
		var searchFor = ""

		// If this generates a type constraint or a type function, use `context`
		if((this instanceof TypeConstraintTemplate || this instanceof TypeFunctionTemplate)
			&& expression.setExpression.setOperation !== null) {
			// `context` can only be used if the expression does not use attribute accesses, etc.
			if(isContextIndexerApplicable(expression.setExpression.setOperation)) {
				searchFor = '''context'''
			}

		}else if (this instanceof RuleConstraintTemplate || this instanceof MappingConstraintTemplate ||
					this instanceof PatternConstraintTemplate || this instanceof RuleFunctionTemplate ||
					this instanceof MappingFunctionTemplate || this instanceof PatternFunctionTemplate){
			// If this generates a {rule, mapping, pattern} constraint or a {rule, mapping, pattern} function, search for context node accesses

			// If the expression is a mapping reference and there is a set operation, search for context node accesses
			if (expression instanceof MappingReference && expression.setExpression.setOperation !== null) {
				val indexableExpressions = findPossibleIndexableRelationalExpressions(expression.setExpression.setOperation)
				val contextNodeAccess = getContextNodeAccess(indexableExpressions)
				searchFor = '''«convertContextNodeAccessToGetterCalls(contextNodeAccess)»'''
			}
			// Otherwise (i.e., if there is no mapping reference or it has no set (filter) expression,
			// the indexer implementation cannot be used
		}

		// If nothing can be indexed, use the original implementation
		if(searchFor.isBlank)
			return original

		var nodesToBeCached = findIndexableSetNodes(expression).map['''"«it»"'''].join(', ')

		// If there can be at least one node indexed, use the indexer implementation
		// Mapping indexer
		imports.add("java.util.Set")
		imports.add("org.emoflon.gips.core.MappingIndexer")
		imports.add("org.emoflon.gips.core.GlobalMappingIndexer")
		imports.add("org.emoflon.gips.core.GipsMapper")

		return '''
final GipsMapper<?> mapper = engine.getMapper("«expression.mapping.name»");
final GlobalMappingIndexer globalIndexer = GlobalMappingIndexer.getInstance();
globalIndexer.createIndexer(mapper);
final MappingIndexer indexer = globalIndexer.getIndexer(mapper);
indexer.initIfNecessary(mapper, Set.of(«nodesToBeCached»));
indexer.getMappingsOfNodes(Set.of(«searchFor»)).parallelStream()
.map(mapping -> («data.mapping2mappingClassName.get(expression.mapping)») mapping)'''
	}
				
	def String generateValueAccess(ConstantReference expression) {
		var instruction = "";
		if(expression.constant.expression instanceof ValueExpression) {
			instruction = generateConstantExpression(expression.constant.expression as ValueExpression, true) 
		} else if(expression.constant.expression instanceof SetExpression)  {
			instruction = generateConstantExpression(expression.constant.expression as SetExpression, true)
		}

		return instruction;
	}
	
		/**
	 * Returns a list of {@link RelationalExpression}
	 */
	def dispatch List<RelationalExpression> findPossibleIndexableRelationalExpressions(EObject expr) {
		return Collections.emptyList
	}

	/**
	 * Returns a list of RelationalExpressions which can be indexed.
	 * The requirements to be included are:
	 * <ul>
	 * <li> the expression must be a conjunction
	 * <li> the relational operator must be `EQUAL`
	 * <li> both sides must either be a node reference or a context reference
	 * <li> both respective sides must not have an attribute access
	 * </ul>
	 * 
	 * Therefore, this method only captures accesses like `filter(element.nodes.a == context.nodes.b)`.
	 */
	def dispatch List<RelationalExpression> findPossibleIndexableRelationalExpressions(SetFilter expr) {
		val results = new LinkedList<RelationalExpression>()
		
		results.addAll(findPossibleIndexableRelationalExpressions(expr.expression))
		if(expr.next !== null)
			results.addAll(findPossibleIndexableRelationalExpressions(expr.next))		
		
		return results
	}
	
	def boolean isNodeOrContextReference(EObject obj){
		if(obj instanceof NodeReference)
			return obj.attribute === null
		return obj instanceof ContextReference
	}

	def dispatch List<RelationalExpression> findPossibleIndexableRelationalExpressions(RelationalExpression expr) {
		val results = new LinkedList<RelationalExpression>()
		
		if(expr.operator === RelationalOperator.EQUAL) {
			var isMatch = isNodeOrContextReference(expr.lhs) && isNodeOrContextReference(expr.rhs)
			if(isMatch)
				results.add(expr)
		}
		
		return results
	}

	def dispatch List<RelationalExpression> findPossibleIndexableRelationalExpressions(BooleanBinaryExpression expr) {
		val results = new LinkedList<RelationalExpression>()
		
		if(expr.operator == BooleanBinaryOperator.AND) {
			results.addAll(findPossibleIndexableRelationalExpressions(expr.lhs))
			results.addAll(findPossibleIndexableRelationalExpressions(expr.rhs))
		}
		
		return results
	}
	
	def List<String> getContextNodeAccess(List<RelationalExpression> possibleIndexableExpressions) {
		val results = new LinkedList<String>()

		// we only care for local nodes (-> context.node.x) when the other side is not local! (-> element.node.y)
		for (expr : possibleIndexableExpressions) {
			if(expr.lhs instanceof NodeReference && expr.rhs instanceof NodeReference) {
				val lhsnode = expr.lhs as NodeReference
				val rhsnode = expr.rhs as NodeReference

				if(lhsnode.attribute === null && rhsnode.attribute === null) {
					if(lhsnode.local && !rhsnode.local) {
						results.add(lhsnode.node.name)
					} else if(!lhsnode.local && rhsnode.local) {
						results.add(rhsnode.node.name)
					}
				}
			}
		}

		return results
	}

	def List<String> getSetNodeAccess(List<RelationalExpression> possibleIndexableExpressions) {
		val results = new LinkedList<String>()

		// we only care for non-local nodes (-> element.node.x) when the other side is  local! (-> context.node.y)
		for (expr : possibleIndexableExpressions) {
			val lhsnode = expr.lhs as ContextReference
			val rhsnode = expr.rhs as ContextReference

			if(lhsnode.local && !rhsnode.local) {
				if(rhsnode instanceof NodeReference)
					if(rhsnode.attribute === null)
						results.add(rhsnode.node.name)
			} else if(!lhsnode.local && rhsnode.local) {
				if(lhsnode instanceof NodeReference)
					if(lhsnode.attribute === null)
						results.add(lhsnode.node.name)
			}
		}

		return results
	}
	
	def Set<String> findIndexableSetNodes(MappingReference mappingReference) {
		val results = new HashSet<String>()

		val toBeScanned = new LinkedList<EObject>()
		val root = EcoreUtil.getRootContainer(mappingReference, true) as GipsIntermediateModel
		toBeScanned.addAll(root.constraints)
		toBeScanned.addAll(root.functions)
		toBeScanned.addAll(root.objective)

		val iterator = EcoreUtil.getAllProperContents(toBeScanned, true)
		while(iterator.hasNext) {
			val eObject = iterator.next;
			if(eObject instanceof MappingReference) {
				iterator.prune

				if(eObject.mapping === mappingReference.mapping && eObject.setExpression.setOperation !== null) {
					val references = findPossibleIndexableRelationalExpressions(eObject.setExpression.setOperation)
					results.addAll(getSetNodeAccess(references))
				}
			}
		}

		return results
	}
		
	def String generateConstantExpression(ValueExpression expression, boolean ignoreReduce) {
		var instruction = generateValueAccess(expression, true)
		if(expression.setExpression !== null) {
			instruction += generateConstantExpression(expression.setExpression, ignoreReduce)
		}
		return instruction;
	}
	
	def String generateAttributeExpression(AttributeExpression expression) {
		var getOrIs = "get"
		if(expression.feature.EType == EcorePackage.Literals.EBOOLEAN)
			getOrIs = "is";
			
		if(expression.next === null) {
					var scalarOrMany = ""
					if(expression.feature.isMany) {
						scalarOrMany = ".parallelStream()"
					}
			return '''«getOrIs»«expression.feature.name.toFirstUpper»()«scalarOrMany»'''
		} else {
			return '''«getOrIs»«expression.feature.name.toFirstUpper»().«generateAttributeExpression(expression.next)»'''
		}
	}
	
	def String generateConstantExpression(SetExpression expression) {
		var instruction = "";
		if(expression.setOperation !== null) {
			instruction += generateConstantExpression(expression.setOperation);
		}
		if(expression.setReduce !== null) {
			instruction += generateConstantExpression(expression.setReduce);
		}
		return instruction;
	}
	
	def String generateConstantExpression(SetExpression expression, boolean ignoreReduce) {
		var instruction = "";
		if(expression.setOperation !== null) {
			instruction += generateConstantExpression(expression.setOperation);
		}
		if(expression.setReduce !== null && !ignoreReduce) {
			instruction += generateConstantExpression(expression.setReduce);
		}
		return instruction;
	}
	
	def String generateConstantExpression(SetOperation expression) {
		var instruction = "";
		if(expression instanceof SetFilter) {
			instruction = '''.filter(elt -> «generateConstantExpression(expression.expression)»)'''
		} else if(expression instanceof SetTypeSelect) {
			imports.add(data.classToPackage.getImportsForType(expression.type))
			instruction = '''.filter(elt -> (elt instanceof «expression.type.name»))
			.map(elt -> («expression.type.name») elt)'''
		} else if(expression instanceof SetSort) {
			instruction = '''.sorted((elt1, elt2) -> «generateConstantExpression(expression.predicate)»)'''
		} else if(expression instanceof SetConcatenation) {
			switch(expression.operator){
				case APPEND: {
					instruction = '''.flatMap(stream -> Stream.of(stream, «generateConstantExpression(expression.other)»))'''
				}
				case PREPEND: {
					instruction = '''.flatMap(stream -> Stream.of(«generateConstantExpression(expression.other)», stream))'''
				}
			}
		} else if(expression instanceof SetSimpleOperation) {
			instruction = '''.distinct()'''
		} else if(expression instanceof SetTransformation) {
			instruction = '''.flatMap(elt -> «generateConstantExpression(expression.expression)»)'''
		} else {
			throw new UnsupportedOperationException("Unknown set operation.")
		}
		
		if(expression.next !== null) {
			instruction += generateConstantExpression(expression.next)
		}
		return instruction;
	}
	
	def String generateConstantExpression(SetReduce expression) {
		if(expression instanceof SetSummation) {
			return '''.map(elt -> (double)(«generateConstantExpression(expression.expression)»)).reduce(0.0, (sum, elt) -> sum + elt)'''
		} else if(expression instanceof SetSimpleSelect) {
			switch(expression.operator) {
				case ANY: {
					return '''.findAny().orElse(null)'''
				}
				case FIRST: {
					return '''.findFirst().orElse(null)'''
				}
				case LAST: {
					return '''.reduce((current, next) -> next).orElse(null)'''
				}
			}
		} else if(expression instanceof SetTypeQuery) {
			return '''.filter(elt -> (elt instanceof «expression.type.name»)).findAny().isPresent()'''
		} else if(expression instanceof SetElementQuery) {
			return '''.filter(elt -> elt.equals(«generateConstantExpression(expression.element)»)).findAny().isPresent()'''
		} else if(expression instanceof SetSimpleQuery) {
			switch(expression.operator) {
				case COUNT: {
					return '''.count()'''
				}
				case EMPTY: {
					return '''.count() <= 0'''
				}
				case NOT_EMPTY: {
					return '''.findAny().isPresent()'''
				}
			}
		} else {
			throw new UnsupportedOperationException("Unknown set reduce operation.")
		}
	}
	
	def String generateConstantExpression(BooleanExpression expr) {
		if(expr instanceof BooleanBinaryExpression) {
			switch(expr.operator) {
				case AND: {
					return '''«generateConstantExpression(expr.lhs)» && «generateConstantExpression(expr.rhs)»'''
				}
				case OR: {
					return '''«generateConstantExpression(expr.lhs)» || «generateConstantExpression(expr.rhs)»'''			
				}
				case XOR: {
					return '''«generateConstantExpression(expr.lhs)» ^ «generateConstantExpression(expr.rhs)»'''		
				}
			}
		} else if(expr instanceof BooleanUnaryExpression) {
			switch(expr.operator) {
				case NOT: {
					return '''!«generateConstantExpression(expr.operand)»'''
				}
				case BRACKET: {
					return '''(«generateConstantExpression(expr.operand)»)'''
				}
			}
		} else if(expr instanceof BooleanLiteral) {
			return '''«(expr.literal)?"true":"false"»''';
		} else if(expr instanceof ConstantLiteral) {
			switch(expr.constant) {
				case NULL: {
					return '''null'''
				}
				default: {
					throw new UnsupportedOperationException("Numeric values may not be part of boolean expressions.")
				}
			}
		} else if(expr instanceof ConstantReference) {
			if(expr.setExpression !== null) {
				return '''«getConstantValue(expr.constant)»«generateConstantExpression(expr.setExpression)»'''
			} else {
				return getConstantValue(expr.constant)
			}
		} else if(expr instanceof RelationalExpression) {
			return generateConstantExpression(expr)
		} else {
			// CASE: ValueExpression, which can evaluate to a boolean value
			return generateConstantExpression(expr as ArithmeticExpression);
		}
	}
	
	def String generateConstantExpression(RelationalExpression expr) {
		if(expr.lhs instanceof ArithmeticExpression) {
			switch(expr.operator) {
				case EQUAL: {
					if(expr.requiresComparables) {
						return '''«generateConstantExpression(expr.lhs as ArithmeticExpression)».equals(«generateConstantExpression(expr.rhs as ArithmeticExpression)»)'''
					} else {
						return '''«generateConstantExpression(expr.lhs as ArithmeticExpression)» == «generateConstantExpression(expr.rhs as ArithmeticExpression)»'''
					}
				}
				case GREATER: {
					if(expr.requiresComparables) {
						return '''«generateConstantExpression(expr.lhs as ArithmeticExpression)».compareTo(«generateConstantExpression(expr.rhs as ArithmeticExpression)») > 0'''
					} else {
						return '''«generateConstantExpression(expr.lhs as ArithmeticExpression)» > «generateConstantExpression(expr.rhs as ArithmeticExpression)»'''
					}
				}
				case GREATER_OR_EQUAL: {
					if(expr.requiresComparables) {
						return '''«generateConstantExpression(expr.lhs as ArithmeticExpression)».compareTo(«generateConstantExpression(expr.rhs as ArithmeticExpression)») >= 0'''
					} else {
						return '''«generateConstantExpression(expr.lhs as ArithmeticExpression)» >= «generateConstantExpression(expr.rhs as ArithmeticExpression)»'''
					}
				}
				case LESS: {
					if(expr.requiresComparables) {
						return '''«generateConstantExpression(expr.lhs as ArithmeticExpression)».compareTo(«generateConstantExpression(expr.rhs as ArithmeticExpression)») < 0'''
					} else {
						return '''«generateConstantExpression(expr.lhs as ArithmeticExpression)» < «generateConstantExpression(expr.rhs as ArithmeticExpression)»'''
					}
				}
				case LESS_OR_EQUAL: {
					if(expr.requiresComparables) {
						return '''«generateConstantExpression(expr.lhs as ArithmeticExpression)».compareTo(«generateConstantExpression(expr.rhs as ArithmeticExpression)») <= 0'''
					} else {
						return '''«generateConstantExpression(expr.lhs as ArithmeticExpression)» < «generateConstantExpression(expr.rhs as ArithmeticExpression)»'''
					}
				}
				case NOT_EQUAL: {
					if(expr.requiresComparables) {
						return '''!«generateConstantExpression(expr.lhs as ArithmeticExpression)».equals(«generateConstantExpression(expr.rhs as ArithmeticExpression)»)'''
					} else {
						return '''«generateConstantExpression(expr.lhs as ArithmeticExpression)» != «generateConstantExpression(expr.rhs as ArithmeticExpression)»'''
					}
				}
			}
		} else {
			switch(expr.operator) {
				case EQUAL: {
					if(!expr.requiresComparables) {
						return '''«generateConstantExpression(expr.lhs as BooleanExpression)» == «generateConstantExpression(expr.rhs as BooleanExpression)»'''
					} else {
						throw new UnsupportedOperationException("Boolean values cannot be compared to complex values (i.e. objects).")
					}
				}
				case NOT_EQUAL: {
					if(!expr.requiresComparables) {
						return '''«generateConstantExpression(expr.lhs as BooleanExpression)» != «generateConstantExpression(expr.rhs as BooleanExpression)»'''
					} else {
						throw new UnsupportedOperationException("Boolean values cannot be compared to complex values (i.e. objects).")
					}
				}
				default: {
					throw new UnsupportedOperationException("Boolean values cannot be compared with operators other than '==' and '!='.")
				}
			}
		}
	}
	
	def String extractReturnType(EObject expression) {
		if(expression instanceof ArithmeticExpression) {
			return extractReturnType(expression as ArithmeticExpression)
		} else {
			return extractReturnType(expression as BooleanExpression)
		}
	}
	
	def String extractReturnType(BooleanExpression expression) {
		if (expression instanceof BooleanBinaryExpression) {
			return '''boolean''';
		} else if (expression instanceof BooleanUnaryExpression) {
			return '''boolean''';
		} else if (expression instanceof BooleanLiteral) {
			return '''boolean''';
		} else if (expression instanceof ConstantLiteral) {
			return '''boolean''';
		} else if (expression instanceof ConstantReference) {
			return extractReturnType(expression.constant.expression as BooleanExpression);
		} else if (expression instanceof ArithmeticExpression) {
			return extractReturnType(expression as ArithmeticExpression);
		} else {
			return '''boolean''';
		}
	}

	def String extractReturnType(ArithmeticExpression expression) {
		if (expression instanceof ArithmeticBinaryExpression) {
			val lhs = extractReturnType(expression.getLhs);
			val rhs = extractReturnType(expression.getRhs);
			if (!lhs.equals(rhs))
				throw new UnsupportedOperationException("Arithmetic operator types are mismatching.");

			return lhs;
		} else if (expression instanceof ArithmeticUnaryExpression) {
			return extractReturnType(expression.getOperand());
		} else if (expression instanceof ArithmeticLiteral) {
			return '''double''';
		} else if (expression instanceof ConstantLiteral) {
			return '''double'''
		} else if (expression instanceof LinearFunctionReference) {
			return '''double''';	
		} else if (expression instanceof ConstantReference) {
			return extractReturnType(expression.constant.expression as ArithmeticExpression);
		} else {
			return extractReturnType(expression as ValueExpression);
		}
	}

	def String extractReturnType(ValueExpression expression) {
		var objectType = ""
		var expressionType = "";
		if (expression instanceof MappingReference) {
			imports.add("java.util.stream.Stream");
			imports.add(data.apiData.gipsMappingPkg+"."+data.mapping2mappingClassName.get(expression.mapping));
			expressionType = '''Stream<«data.mapping2mappingClassName.get(expression.mapping)»>''';
			objectType = data.mapping2mappingClassName.get(expression.mapping);
		} else if (expression instanceof TypeReference) {
			imports.add("java.util.stream.Stream");
			imports.add(data.classToPackage.getImportsForType(expression.type));
			expressionType = '''Stream<«expression.type.name»>''';
			objectType = expression.type.name;
		} else if (expression instanceof PatternReference) {
			imports.add("java.util.stream.Stream");
			imports.add(data.apiData.matchesPkg+"."+data.ibex2matchClassName.get(expression.pattern))
			expressionType = '''Stream<«data.ibex2matchClassName.get(expression.pattern)»>''';
			objectType = data.ibex2matchClassName.get(expression.pattern);
		} else if (expression instanceof RuleReference) {
			imports.add("java.util.stream.Stream");
			imports.add(data.apiData.matchesPkg+"."+data.ibex2matchClassName.get(expression.rule))
			expressionType = '''Stream<«data.ibex2matchClassName.get(expression.rule)»>''';
			objectType = data.ibex2matchClassName.get(expression.rule);
		} else if (expression instanceof NodeReference) {
			expressionType = extractReturnType(expression);
			objectType = expressionType;
		} else if (expression instanceof AttributeReference) {
			expressionType = extractReturnType(expression);
			objectType = expressionType;
		} else if (expression instanceof VariableReference) {
			expressionType = '''double''';
			objectType = expressionType;
		} else {
			// Case: ContextReference
			expressionType = getContextParameterType();
			objectType = expressionType;
		}

		if (expression.getSetExpression() !== null) {
			if (expression.setExpression.setOperation !== null) {
				imports.add("java.util.stream.Stream");
				objectType = extractReturnType(objectType, expression.setExpression.setOperation);
				expressionType = '''Stream<«objectType»>''';
			}
			if (expression.setExpression.setReduce !== null) {
				val reduce = expression.getSetExpression().getSetReduce();
				if (reduce instanceof SetSummation) {
					return '''double''';
				} else if (reduce instanceof SetSimpleSelect) {
					return objectType;
				} else if (reduce instanceof SetTypeQuery) {
					return '''boolean''';
				} else if (reduce instanceof SetElementQuery) {
					return '''boolean''';
				} else if (reduce instanceof SetSimpleQuery) {
					if (reduce.operator == QueryOperator.EMPTY || reduce.operator == QueryOperator.NOT_EMPTY) {
						return '''boolean''';
					} else {
						return '''double''';
					}
				}
			}
			return expressionType;
		} else {
			return expressionType;
		}
	}
	
	def String extractReturnType(String previousType, SetOperation expression) {
		var currentType = "";
		if(expression instanceof SetFilter) {
			currentType = previousType;
		} else if(expression instanceof SetTypeSelect) {
			imports.add(data.classToPackage.getImportsForType(expression.type));
			currentType = expression.type.name;
		} else if(expression instanceof SetSort) {
			currentType = previousType;
		} else if(expression instanceof SetConcatenation) {
			currentType = previousType;
		} else if(expression instanceof SetSimpleOperation) {
			currentType = previousType;
		} else {
			val transform = expression as SetTransformation;
			currentType = extractReturnType(transform.expression);
			if(currentType.contains("Stream")) {
				val first = currentType.split("<");
				val second = first.get(0).split(">");
				currentType = second.get(0);
			} 
		}
		
		if(expression.next !== null) {
			return extractReturnType(currentType, expression.next);
		} else {
			return currentType;
		}
	}

	def String extractReturnType(NodeReference expression) {
		if (expression.attribute === null) {
			imports.add(data.classToPackage.getImportsForType(expression.node.type));
			return expression.node.type.name;
		} else {
			return extractReturnType(expression.attribute);
		}
	}

	def String extractReturnType(AttributeReference expression) {
		return extractReturnType(expression.attribute);
	}

	def String extractReturnType(AttributeExpression expression) {
		if (expression.next === null) {
			if (expression.feature.EType == EcorePackage.Literals.EBOOLEAN) {
				return '''boolean''';
			} else if (expression.getFeature().getEType() == EcorePackage.Literals.EDOUBLE) {
				return '''double''';
			} else if (expression.getFeature().getEType() == EcorePackage.Literals.EFLOAT) {
				return '''double''';
			} else if (expression.getFeature().getEType() == EcorePackage.Literals.EBYTE) {
				return '''double''';
			} else if (expression.getFeature().getEType() == EcorePackage.Literals.ESHORT) {
				return '''double''';
			} else if (expression.getFeature().getEType() == EcorePackage.Literals.EINT) {
				return '''double''';
			} else if (expression.getFeature().getEType() == EcorePackage.Literals.ELONG) {
				return '''double''';
			} else if (expression.getFeature().getEType() == EcorePackage.Literals.ESTRING) {
				return '''String''';
			} else if (expression.getFeature().getEType() instanceof EClass) {
				imports.add(data.classToPackage.getImportsForType(expression.getFeature().getEType()));
				return expression.feature.EType.name;
			} else if (expression.feature.EType instanceof EEnum) {
				imports.add(data.classToPackage.getImportsForType(expression.feature.EType));
				return expression.feature.EType.name;
			} else {
				throw new IllegalArgumentException("Unsupported data type: " + expression.getFeature().getEType());
			}
		} else {
			return extractReturnType(expression.getNext());
		}
	}
}