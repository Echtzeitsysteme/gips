package org.emoflon.gips.build.generator.templates

import java.util.Collection
import java.util.HashMap
import java.util.HashSet
import java.util.LinkedList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EcorePackage
import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticBinaryExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticLiteral
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticUnaryExpression
import org.emoflon.gips.intermediate.GipsIntermediate.AttributeExpression
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanBinaryExpression
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanExpression
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanLiteral
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanUnaryExpression
import org.emoflon.gips.intermediate.GipsIntermediate.Constant
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantLiteral
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantReference
import org.emoflon.gips.intermediate.GipsIntermediate.ContextReference
import org.emoflon.gips.intermediate.GipsIntermediate.DoubleLiteral
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage
import org.emoflon.gips.intermediate.GipsIntermediate.IntegerLiteral
import org.emoflon.gips.intermediate.GipsIntermediate.MappingReference
import org.emoflon.gips.intermediate.GipsIntermediate.MemberExpression
import org.emoflon.gips.intermediate.GipsIntermediate.MemberReference
import org.emoflon.gips.intermediate.GipsIntermediate.NodeExpression
import org.emoflon.gips.intermediate.GipsIntermediate.PatternReference
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression
import org.emoflon.gips.intermediate.GipsIntermediate.RuleReference
import org.emoflon.gips.intermediate.GipsIntermediate.SetConcatenation
import org.emoflon.gips.intermediate.GipsIntermediate.SetElementQuery
import org.emoflon.gips.intermediate.GipsIntermediate.SetExpression
import org.emoflon.gips.intermediate.GipsIntermediate.SetFilter
import org.emoflon.gips.intermediate.GipsIntermediate.SetOperation
import org.emoflon.gips.intermediate.GipsIntermediate.SetReduce
import org.emoflon.gips.intermediate.GipsIntermediate.SetSimpleOperation
import org.emoflon.gips.intermediate.GipsIntermediate.SetSimpleQuery
import org.emoflon.gips.intermediate.GipsIntermediate.SetSimpleSelect
import org.emoflon.gips.intermediate.GipsIntermediate.SetSort
import org.emoflon.gips.intermediate.GipsIntermediate.SetSummation
import org.emoflon.gips.intermediate.GipsIntermediate.SetTransformation
import org.emoflon.gips.intermediate.GipsIntermediate.SetTypeQuery
import org.emoflon.gips.intermediate.GipsIntermediate.SetTypeSelect
import org.emoflon.gips.intermediate.GipsIntermediate.TypeReference
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression
import org.emoflon.gips.intermediate.GipsIntermediate.Variable
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference

abstract class ProblemGeneratorTemplate<CONTEXT extends EObject> extends ClassGeneratorTemplate<CONTEXT> {

	public val iterator2variableName = new HashMap<SetOperation, String>();
	public val builderMethodNamesX = new HashSet<String>
	public val builderMethods = new HashMap<EObject, String>
	public val builderMethodDefinitions = new HashMap<EObject, String>
	public val builderMethodCalls2 = new LinkedList<String>

	extension protected val JavaReturnTypeExtractor returnTypeExtractor

	new(TemplateData data, CONTEXT context) {
		super(data, context)
		returnTypeExtractor = new JavaReturnTypeExtractor(data, this)
	}

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

	def String generateVariableAccess(VariableReference varRef) {
		return generateValueAccess(varRef, false)
	}

	def String generateVariableAccessInSet(VariableReference varRef) {
		return generateValueAccess(varRef, false)
	}

	def String getAdditionalVariableName(VariableReference varRef) {
		return '''engine.getNonMappingVariable(«generateValueAccess(varRef, false)», "«varRef.variable.name»").getName()'''
	}

	def boolean isMappingVariable(VariableReference varRef) {
		return isMappingVariable(varRef.variable)
	}

	def boolean isMappingVariable(Variable variable) {
		return data.mapping2mappingClassName.keySet().stream().filter [ m |
			m.getMappingVariable() !== null && m.getMappingVariable().equals(variable)
		].findAny().isPresent();
	}

	def String getConstantName(Constant constant) {
		if(constant.isGlobal)
			return constant.name
		else
			return '''c«constant.name.toFirstUpper»'''
	}

	def String getConstantValue(Constant constant) {
		if(constant.isGlobal) {
			return '''((«extractReturnType(constant.expression, imports)»)engine.getConstantValue("«getConstantName(constant)»"))'''
		} else {
			return '''«getConstantName(constant)»'''
		}
	}

	def String getParametersForConstants(Collection<Constant> constants) {
		return '''«FOR constant : constants SEPARATOR ', '»«extractReturnType(constant.expression, imports)» «getConstantName(constant)»«ENDFOR»'''
	}

	def String getCallParametersForConstants(Collection<Constant> constants) {
		return '''«FOR constant : constants SEPARATOR ', '»«getConstantName(constant)»«ENDFOR»'''
	}

	def String getCallConstantCalculator(Constant constant) {
		return '''calculate«constant.name.toFirstUpper»(context)'''
	}

	def String generateConstantCalculator(Constant constant) {
		'''
			protected «extractReturnType(constant.expression, imports)» calculate«constant.name.toFirstUpper»(«getContextParameter()») {
				return 	«IF constant.expression instanceof ArithmeticExpression»«generateConstantExpression(constant.expression as ArithmeticExpression)»«ELSE»«generateConstantExpression(constant.expression as BooleanExpression)»«ENDIF»;
			}
		'''
	}

	def String generateConstantFields(Collection<Constant> constants) {
		'''
			«FOR constant : constants»
				«extractReturnType(constant.expression, imports)» «getConstantName(constant)» = «getCallConstantCalculator(constant)»;
			«ENDFOR»
		'''
	}

	def String getConstantCalculators(Collection<Constant> constants) {
		return '''«FOR constant : constants»
		«generateConstantCalculator(constant)»
		
		«ENDFOR»
		'''
	}

	def Collection<Constant> getConstants();

	def String generateIterator(ContextReference reference) {
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

	def String createBuilderMethod(ArithmeticBinaryExpression expr, LinkedList<String> methodCalls) {
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

	def String createBuilderMethod(ArithmeticUnaryExpression expr, LinkedList<String> methodCalls) {
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

	def String createBuilderMethod(ConstantReference expr, LinkedList<String> methodCalls) {
		if(expr.setExpression === null)
			return null

		if(expr.setExpression.setReduce === null)
			return null

		val sumExpression = expr.setExpression.setReduce as SetSummation

		val builderMethodName = '''builder_«builderMethods.size»'''
		val instruction = '''«builderMethodName»(«callParametersForVoidBuilder»«IF !getConstants().empty», «ENDIF»«getCallParametersForConstants(getConstants())»);'''
		methodCalls.add(instruction)

		builderMethods.put(expr, builderMethodName)
		imports.add("java.util.stream.Collectors")
		val vars = new HashSet
		val varRefs = new HashSet

		GipsTransformationUtils.extractVariableReference(sumExpression.expression).forEach [ ref |
			if(vars.add(ref.variable)) {
				varRefs.add(ref)
			}
		]

		if(vars.size > 1 || varRefs.size > 1)
			throw new UnsupportedOperationException(
				"A single sum operation over a set of variables may not contain different kinds of variables.")
		val variable = varRefs.iterator.next

		val method = '''
			protected void «builderMethodName»(«parametersForVoidBuilder»«IF !getConstants().empty», «ENDIF»«getParametersForConstants(getConstants())») {
				«generateValueAccess(expr)»
				«IF expr.setExpression.setOperation !== null»«generateConstantExpression(expr.setExpression.setOperation)»«ENDIF»
				.forEach(elt -> {
					terms.add(new Term(«generateVariableAccessInSet(variable)», (double) «generateConstantExpression(sumExpression.expression)»));
				});
			}
		'''

		builderMethodDefinitions.put(expr, method)

		return builderMethodName
	}

	def String createBuilderMethod(ValueExpression expr, LinkedList<String> methodCalls) {
		if(expr instanceof VariableReference) {
			val value = generateValueAccess(expr as VariableReference, false)
			val instruction = '''terms.add(new Term(«value», «generateConstantExpression(expr as VariableReference, false)»));'''
			methodCalls.add(instruction)
			return null
		}

		if(expr.setExpression === null)
			return null

		if(expr.setExpression.setReduce === null)
			return null

		val sumExpression = expr.setExpression.setReduce as SetSummation

		val builderMethodName = '''builder_«builderMethods.size»'''
		val instruction = '''«builderMethodName»(«callParametersForVoidBuilder»«IF !getConstants().empty», «ENDIF»«getCallParametersForConstants(getConstants())»);'''
		methodCalls.add(instruction)

		builderMethods.put(expr, builderMethodName)
		imports.add("java.util.stream.Collectors")
		val vars = new HashSet
		val varRefs = new HashSet

		GipsTransformationUtils.extractVariableReference(sumExpression.expression).forEach [ ref |
			if(vars.add(ref.variable)) {
				varRefs.add(ref)
			}
		]

		if(vars.size > 1 || varRefs.size > 1)
			throw new UnsupportedOperationException(
				"A single sum operation over a set of variables may not contain different kinds of variables.")
		val variable = varRefs.iterator.next

		val method = '''
			protected void «builderMethodName»(«parametersForVoidBuilder»«IF !getConstants().empty», «ENDIF»«getParametersForConstants(getConstants())») {
				«generateValueAccess(expr, false)»
				«IF expr.setExpression.setOperation !== null»«generateConstantExpression(expr.setExpression.setOperation)»«ENDIF»
				.forEach(elt -> {
					terms.add(new Term(«generateVariableAccessInSet(variable)», (double) «generateConstantExpression(sumExpression.expression)»));
				});
			}
		'''

		builderMethodDefinitions.put(expr, method)

		return builderMethodName
	}

	def String generateConstantExpression(ValueExpression expression, boolean ignoreReduce) {
		var instruction = "";

		if(expression instanceof VariableReference) {
			instruction = '''1.0''' // turn a variable into a constant
		} else {
			instruction = generateValueAccess(expression, true)

		}

		if(expression.setExpression !== null) {
			instruction += generateConstantExpression(expression.setExpression, ignoreReduce)
		}
		return instruction;
	}

	def String generateConstantExpression(ValueExpression expression) {
		return generateConstantExpression(expression, false);
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

		} else if(expression instanceof VariableReference) {
			instruction = generateValueAccess(expression as VariableReference, requiresReturn)

		} else if(expression instanceof MemberReference) {
			instruction = generateValueAccess(expression as MemberReference, requiresReturn)

		} else {
			instruction = generateValueAccess(expression as ContextReference, requiresReturn)

		}

		return instruction;
	}

	def String generateValueAccess(ContextReference expression, boolean requiresReturn) {
		// CASE: ContextReference
		return generateIterator(expression as ContextReference)
	}

	def String generateValueAccess(VariableReference expression, boolean requiresReturn) {
		var getter = generateIterator(expression)
		if(isMappingVariable(expression.variable))
			return getter;
		
		if(expression.previous !== null)
			getter += generateExpression(expression.previous)

		return '''engine.getNonMappingVariable(«getter», "«expression.variable.name»")'''
	}

	def String generateValueAccess(MemberReference expression, boolean requiresReturn) {
		var getter = generateIterator(expression)
		if(expression.member !== null)
			getter += generateExpression(expression.member)

		return getter
	}

	def String generateValueAccess(RuleReference expression, boolean requiresReturn) {
		imports.add(data.apiData.matchesPkg + "." + data.ibex2matchClassName.get(expression.rule))
		return '''engine.getEMoflonAPI().«expression.rule.name»().findMatches(false).parallelStream()'''
	}

	def String generateValueAccess(PatternReference expression, boolean requiresReturn) {
		imports.add(data.apiData.matchesPkg + "." + data.ibex2matchClassName.get(expression.pattern))
		return '''engine.getEMoflonAPI().«expression.pattern.name»().findMatches(false).parallelStream()'''
	}

	def String generateValueAccess(TypeReference expression, boolean requiresReturn) {
		imports.add(data.classToPackage.getImportsForType(expression.type))
		return '''indexer.getObjectsOfType("«expression.type.name»").parallelStream().map(type -> («expression.type.name») type)'''
	}

	def String generateValueAccess(MappingReference expression, boolean requiresReturn) {
		imports.add(data.apiData.gipsMappingPkg + "." + data.mapping2mappingClassName.get(expression.mapping))
		val original = '''engine.getMapper("«expression.mapping.name»").getMappings().values().parallelStream()
		.map(mapping -> («data.mapping2mappingClassName.get(expression.mapping)») mapping)'''

		// If a return is required, use the original implementation
		return original
	}

	def String generateValueAccess(ConstantReference expression) {
		var instruction = "";
		if(expression.constant.expression instanceof ValueExpression) {
			instruction = generateConstantExpression(expression.constant.expression as ValueExpression, true)
		} else if(expression.constant.expression instanceof SetExpression) {
			instruction = generateConstantExpression(expression.constant.expression as SetExpression, true)
		}

		return instruction;
	}

	def String generateExpression(MemberExpression expression) {
		if(expression instanceof NodeExpression) {
			return generateExpression(expression as NodeExpression)

		} else if(expression instanceof AttributeExpression) {
			return generateExpression(expression as AttributeExpression)

		}

		throw new IllegalArgumentException("Unknown value: " + expression)
	}

	def String generateExpression(NodeExpression expression) {
		var getter = '''.get«expression.node.name.toFirstUpper»()'''
		if(expression.next !== null)
			getter += generateExpression(expression.next)
		return getter
	}

	def String generateExpression(AttributeExpression expression) {
		val isBoolean = expression.feature.EType == EcorePackage.Literals.EBOOLEAN
		val getOrIs = isBoolean ? "is" : "get"
		val scalarOrMany = expression.feature.isMany ? ".parallelStream()" : ""
		var getter = '''.«getOrIs»«expression.feature.name.toFirstUpper»()«scalarOrMany»'''
		if(expression.next !== null)
			getter += generateExpression(expression.next)
		return getter
	}

	def String generateConstantExpression(SetExpression expression) {
		return generateConstantExpression(expression, false);
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
			switch (expression.operator) {
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
			switch (expression.operator) {
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
			switch (expression.operator) {
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
			switch (expr.operator) {
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
			switch (expr.operator) {
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
			switch (expr.constant) {
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

	def String generateConstantExpression(ArithmeticExpression expression) {
		if(expression instanceof ArithmeticBinaryExpression) {
			switch (expression.operator) {
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
			switch (expression.operator) {
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
			switch (expression.constant) {
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

	def String generateConstantExpression(RelationalExpression expr) {
		if(expr.lhs instanceof ArithmeticExpression) {
			switch (expr.operator) {
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
			switch (expr.operator) {
				case EQUAL: {
					if(!expr.requiresComparables) {
						return '''«generateConstantExpression(expr.lhs as BooleanExpression)» == «generateConstantExpression(expr.rhs as BooleanExpression)»'''
					} else {
						throw new UnsupportedOperationException(
							"Boolean values cannot be compared to complex values (i.e. objects).")
					}
				}
				case NOT_EQUAL: {
					if(!expr.requiresComparables) {
						return '''«generateConstantExpression(expr.lhs as BooleanExpression)» != «generateConstantExpression(expr.rhs as BooleanExpression)»'''
					} else {
						throw new UnsupportedOperationException(
							"Boolean values cannot be compared to complex values (i.e. objects).")
					}
				}
				default: {
					throw new UnsupportedOperationException(
						"Boolean values cannot be compared with operators other than '==' and '!='.")
				}
			}
		}
	}

}
