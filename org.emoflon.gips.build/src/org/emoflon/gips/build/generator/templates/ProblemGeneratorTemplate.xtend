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
			return '''(«extractReturnType(constant.expression)»)engine.getConstantValue("«getConstantName(constant)»")'''
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
		«ENDFOR»'''
	}
	
	def String getConstantCalculators(Collection<Constant> constants) {
		return '''«FOR constant : constants»
		«getConstantCalculator(constant)»
		
		«ENDFOR»'''
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
			return getConstantValue(expression.constant)
		} else if(expression instanceof ValueExpression) {
			return generateConstantExpression(expression)
		} else {
			// CASE: LinearFunctionReference -> return a constant 1 since the variable should have already been extracted.
			return '''1.0'''
		}
		
	}
	
	def String generateConstantExpression(ValueExpression expression) {
		var instruction = generateValueAccess(expression)
		if(expression.setExpression !== null) {
			instruction += generateConstantExpression(expression.setExpression)
		}
		return instruction;
	}
	
	def String generateValueAccess(ValueExpression expression) {
		var instruction = "";
		if(expression instanceof MappingReference) {
			imports.add(data.apiData.gipsMappingPkg+"."+data.mapping2mappingClassName.get(expression.mapping))
			instruction = '''engine.getMapper("«expression.mapping.name»").getMappings().values().parallelStream()
			.map(mapping -> («data.mapping2mappingClassName.get(expression.mapping)») mapping)'''
		} else if(expression instanceof TypeReference) {
			imports.add(data.classToPackage.getImportsForType(expression.type))
			instruction = '''indexer.getObjectsOfType("«expression.type.name»").parallelStream()
						.map(type -> («expression.type.name») type)'''
		} else if(expression instanceof PatternReference) {
			imports.add(data.apiData.matchesPkg+"."+data.ibex2matchClassName.get(expression.pattern))
			instruction = '''engine.getEMoflonAPI().«expression.pattern.name»().findMatches(false).parallelStream()'''
		} else if(expression instanceof RuleReference) {
			imports.add(data.apiData.matchesPkg+"."+data.ibex2matchClassName.get(expression.rule))
			instruction = '''engine.getEMoflonAPI().«expression.rule.name»().findMatches(false).parallelStream()'''
		} else if(expression instanceof NodeReference) {
			instruction = getIterator(expression)
			instruction = '''«instruction».get«expression.node.name.toFirstUpper»()'''
			if(expression.attribute !== null) {
				instruction = '''«instruction».«generateAttributeExpression(expression.attribute)»'''
			}
		} else if(expression instanceof AttributeReference) {
			instruction = getIterator(expression)
			instruction = '''«instruction».«generateAttributeExpression(expression.attribute)»'''
		} else if(expression instanceof VariableReference) {
			// CASE: VariableReference -> return a constant 1 since the variable should have already been extracted.
			instruction = '''1.0'''
		} else {
			// CASE: ContextReference
			instruction = getIterator(expression as ContextReference)
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
			return getConstantValue(expr.constant)
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
			imports.add("java.util.Collection");
			imports.add(data.apiData.gipsMappingPkg+"."+data.mapping2mappingClassName.get(expression.mapping));
			expressionType = '''Collection<«data.mapping2mappingClassName.get(expression.mapping)»>''';
			objectType = data.mapping2mappingClassName.get(expression.mapping);
		} else if (expression instanceof TypeReference) {
			imports.add("java.util.Collection");
			imports.add(data.classToPackage.getImportsForType(expression.type));
			expressionType = '''Collection<«expression.type.name»>''';
			objectType = expression.type.name;
		} else if (expression instanceof PatternReference) {
			imports.add("java.util.Collection");
			imports.add(data.apiData.matchesPkg+"."+data.ibex2matchClassName.get(expression.pattern))
			expressionType = '''Collection<«data.ibex2matchClassName.get(expression.pattern)»>''';
			objectType = data.ibex2matchClassName.get(expression.pattern);
		} else if (expression instanceof RuleReference) {
			imports.add("java.util.Collection");
			imports.add(data.apiData.matchesPkg+"."+data.ibex2matchClassName.get(expression.rule))
			expressionType = '''Collection<«data.ibex2matchClassName.get(expression.rule)»>''';
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
				imports.add("java.util.Collection");
				objectType = extractReturnType(objectType, expression.setExpression.setOperation);
				expressionType = '''Collection<«objectType»>''';
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
			if(currentType.contains("Collection")) {
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