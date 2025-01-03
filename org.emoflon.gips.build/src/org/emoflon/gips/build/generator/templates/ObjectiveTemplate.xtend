package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.build.generator.GeneratorTemplate
import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticLiteral
import org.emoflon.gips.intermediate.GipsIntermediate.DoubleLiteral
import org.emoflon.gips.intermediate.GipsIntermediate.IntegerLiteral
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticValue
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeValue
import org.emoflon.gips.intermediate.GipsIntermediate.TypeSumExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNode
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.MappingSumExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveFunctionValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeValue
import java.util.HashMap
import org.emoflon.gips.intermediate.GipsIntermediate.SetOperation
import org.emoflon.gips.intermediate.GipsIntermediate.FeatureExpression
import org.emoflon.gips.intermediate.GipsIntermediate.StreamExpression
import org.emoflon.gips.intermediate.GipsIntermediate.BoolExpression
import org.emoflon.gips.intermediate.GipsIntermediate.BoolBinaryExpression
import org.emoflon.gips.intermediate.GipsIntermediate.BoolUnaryExpression
import org.emoflon.gips.intermediate.GipsIntermediate.BoolLiteral
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression
import org.emoflon.gips.intermediate.GipsIntermediate.BoolValue
import org.emoflon.gips.intermediate.GipsIntermediate.StreamFilterOperation
import org.emoflon.gips.intermediate.GipsIntermediate.StreamSelectOperation
import org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticOperator
import org.eclipse.emf.ecore.EObject
import java.util.HashSet
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils
import java.util.LinkedList
import org.emoflon.gips.intermediate.GipsIntermediate.VariableSet
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNode
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternValue
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNodeFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNodeFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.SumExpression
import org.emoflon.gips.intermediate.GipsIntermediate.Objective
import org.emoflon.gips.build.transformation.helper.ArithmeticExpressionType
import org.emoflon.gips.intermediate.GipsIntermediate.MappingObjective
import org.emoflon.gips.intermediate.GipsIntermediate.ContextSumExpression
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping
import org.emoflon.gips.intermediate.GipsIntermediate.Pattern
import org.emoflon.gips.intermediate.GipsIntermediate.Type
import org.eclipse.emf.ecore.EcorePackage
import org.emoflon.gips.intermediate.GipsIntermediate.PatternSumExpression
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingVariableValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingValue
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference
import org.emoflon.gips.intermediate.GipsIntermediate.Variable
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingVariablesReference
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingVariablesReference

abstract class ObjectiveTemplate <OBJECTIVE extends Objective> extends GeneratorTemplate<OBJECTIVE> {

	public val iterator2variableName = new HashMap<SetOperation, String>();
	public val builderMethodNames = new HashSet<String>
	public val builderMethods = new HashMap<EObject,String>
	public val builderMethodDefinitions = new HashMap<EObject,String>
	public val builderMethodCalls = new LinkedList<String>

	new(TemplateData data, OBJECTIVE context) {
		super(data, context)
	}
	
	override generate() {
		val classContent = generateClassContent()
		val importStatements = generateImports();
		code = '''«generatePackageDeclaration»		

«importStatements»

«classContent»
'''
	}
	
	def String generatePackageDeclaration();
	
	def String generateImports();
	
	def String generateClassContent();
	
	def String generateObjective(ArithmeticExpression expr);
	
	def void generateTermBuilder(ArithmeticExpression expr) {
		if(expr instanceof BinaryArithmeticExpression) {
			if(expr.operator == BinaryArithmeticOperator.ADD) {
				generateTermBuilder(expr.lhs)
				generateTermBuilder(expr.rhs)
			} else if(expr.operator == BinaryArithmeticOperator.SUBTRACT) {
				throw new UnsupportedOperationException("Code generator does not support subtraction expressions.");
			} else {
				val builderMethodName = generateBuilder(expr)
				var instruction = ""
				if(GipsTransformationUtils.isConstantExpression(expr)  == ArithmeticExpressionType.constant) {
					if(context instanceof MappingObjective) {
						instruction = '''terms.add(new ILPTerm(context, «builderMethodName»(context)));'''
					} else {
						instruction = '''constantTerms.add(new ILPConstant(«builderMethodName»(context)));'''
					}
				} else {
					val variables = GipsTransformationUtils.extractVariable(expr);
					if(variables.size != 1)
						throw new UnsupportedOperationException("Access to multiple different variables in the same product is forbidden.");
		
					val variable = variables.iterator.next
					instruction = '''terms.add(new ILPTerm(«getContextVariable(variable)», «builderMethodName»(context)));'''
				}
				builderMethodCalls.add(instruction)
			}
		} else if(expr instanceof UnaryArithmeticExpression) {
				val builderMethodName = generateBuilder(expr)
				var instruction = ""
				if(GipsTransformationUtils.isConstantExpression(expr)  == ArithmeticExpressionType.constant) {
					if(context instanceof MappingObjective) {
						instruction = '''terms.add(new ILPTerm(context, «builderMethodName»(context)));'''
					} else {
						instruction = '''constantTerms.add(new ILPConstant(«builderMethodName»(context)));'''
					}
				} else {
					val variables = GipsTransformationUtils.extractVariable(expr);
					if(variables.size != 1)
						throw new UnsupportedOperationException("Access to multiple different variables in the same product is forbidden.");
		
					val variable = variables.iterator.next
					instruction =  '''terms.add(new ILPTerm(«getContextVariable(variable)», «builderMethodName»(context)));'''
				}
				builderMethodCalls.add(instruction)
		} else if(expr instanceof ArithmeticValue) {
			generateBuilder(expr.value);
		} else if(expr instanceof VariableReference) {
			val variables = GipsTransformationUtils.extractVariable(expr);
			if(variables.size != 1)
				throw new UnsupportedOperationException("Access to multiple different variables in the same product is forbidden.");
		
			val variable = variables.iterator.next
			val instruction = '''terms.add(new ILPTerm(«getContextVariable(null)».get«variable.name.toFirstUpper»()), 1.0));'''
			builderMethodCalls.add(instruction);
		} else {
			if(expr instanceof IntegerLiteral) {
				var instruction = ""
				if(context instanceof MappingObjective) {
					instruction = '''terms.add(new ILPTerm(context, (double)«expr.literal»));'''
				} else {
					instruction = '''constantTerms.add(new ILPConstant((double)«expr.literal»));'''
				}
				builderMethodCalls.add(instruction);
			} else {
				val doubleLit = expr as DoubleLiteral
				var instruction = ""
				if(context instanceof MappingObjective) {
					instruction = '''terms.add(new ILPTerm(context, «doubleLit.literal»));'''
				} else {
					instruction = '''constantTerms.add(new ILPConstant(«doubleLit.literal»));'''
				}
				builderMethodCalls.add(instruction);
			}
		}
	}
	
	def void generateBuilder(ValueExpression expr) {
		if(expr instanceof MappingSumExpression || expr instanceof TypeSumExpression || expr instanceof ContextSumExpression || expr instanceof PatternSumExpression || expr instanceof ContextMappingVariablesReference) {
			val builderMethodName = generateIteratingBuilder(expr);
			builderMethodCalls.add('''«builderMethodName»(context);''')
		} else {
			var instruction = "";
			val type = GipsTransformationUtils.isConstantExpression(expr)
			if(type == ArithmeticExpressionType.constant) {
				if(context instanceof MappingObjective) {
					instruction = '''terms.add(new ILPTerm(context, «generateConstantBuilder(expr, type)»));'''
				} else {
					instruction = '''constantTerms.add(new ILPConstant(«generateConstantBuilder(expr, type)»));'''
				}
			} else {
				val variables = GipsTransformationUtils.extractVariable(expr);
				if(variables.size != 1)
					throw new UnsupportedOperationException("Access to multiple different variables in the same stream expression is forbidden.");
				
				val builderMethodName = generateConstantBuilder(expr, type);
				val variable = variables.iterator.next
				if(!(variable instanceof Variable)) {
					instruction = '''terms.add(new ILPTerm(«getContextVariable(variable)», «builderMethodName»(context)));'''
				} else {
					instruction = '''terms.add(new ILPTerm(«getContextVariable(null)».get«variable.name.toFirstUpper»(), «builderMethodName»(context)));'''
				}
			}
			builderMethodCalls.add(instruction);
		}
	}
	
	def String getContextVariable(VariableSet variable);
	
	def String generateIteratingBuilder(ValueExpression expr);
	
	def String generateConstantBuilder(ValueExpression expr, ArithmeticExpressionType type);
	
	def String generateBuilder(BinaryArithmeticExpression expr);
	
	def String generateBuilder(UnaryArithmeticExpression expr);
	
	def String generateBuilder(MappingSumExpression expr);
	
	def String generateBuilder(ContextSumExpression expr);
	
	def String generateForeignBuilder(TypeSumExpression expr);
	
	def String generateForeignBuilder(PatternSumExpression expr);
	
	def String generateForeignBuilder(MappingSumExpression expr);
	
	def String parseExpression(ArithmeticExpression expr, ExpressionContext contextType) {
		if(expr instanceof BinaryArithmeticExpression) {
			switch(expr.operator) {
				case ADD: {
					return '''«parseExpression(expr.lhs, contextType)» + «parseExpression(expr.rhs, contextType)»'''
				}
				case DIVIDE: {
					return '''«parseExpression(expr.lhs, contextType)» / «parseExpression(expr.rhs, contextType)»'''
				}
				case MULTIPLY: {
					return '''«parseExpression(expr.lhs, contextType)» * «parseExpression(expr.rhs, contextType)»'''
				}
				case POW: {
					return '''Math.pow(«parseExpression(expr.lhs, contextType)», «parseExpression(expr.rhs, contextType)»)'''
				}
				case SUBTRACT: {
					return '''«parseExpression(expr.lhs, contextType)» - «parseExpression(expr.rhs, contextType)»'''
				}
				case LOG: {
					return '''Math.log(«parseExpression(expr.lhs, contextType)») / Math.log(«parseExpression(expr.rhs, contextType)»)'''
				}
			}
		} else if(expr instanceof UnaryArithmeticExpression) {
			switch(expr.operator) {
				case ABSOLUTE: {
					return '''Math.abs(«parseExpression(expr.expression, contextType)»)'''
				}
				case BRACKET: {
					return '''(«parseExpression(expr.expression, contextType)»)'''
				}
				case COSINE: {
					return '''Math.cos(«parseExpression(expr.expression, contextType)»)'''
				}
				case NEGATE: {
					return '''-«parseExpression(expr.expression, contextType)»'''
				}
				case SINE: {
					return '''Math.sin(«parseExpression(expr.expression, contextType)»)'''
				}
				case SQRT: {
					return '''Math.sqrt(«parseExpression(expr.expression, contextType)»)'''
				}
			}
		} else if(expr instanceof ArithmeticLiteral) {
			if(expr instanceof DoubleLiteral) {
				return String.valueOf(expr.literal)
			} else if(expr instanceof IntegerLiteral) {
				return String.valueOf(expr.literal)
			} else {
				return '''null'''
			}
		} else {
			val value = expr as ArithmeticValue
			switch(contextType) {
				case constConstraint: {
					return parseExpression(value.value, ExpressionContext.constConstraint);
				}
				case constStream: {
					return parseExpression(value.value, ExpressionContext.constStream);
				}
				case varConstraint: {
					return parseExpression(value.value, ExpressionContext.varConstraint);
				}
				case varStream: {
					return parseExpression(value.value,  ExpressionContext.varStream);
				}
			}
		}
		
	}
	
	def String parseExpression(BoolExpression expr, ExpressionContext contextType) {
		if(expr instanceof BoolBinaryExpression) {
			switch(expr.operator) {
				case AND: {
					return '''«parseExpression(expr.lhs, contextType)» && «parseExpression(expr.rhs, contextType)»'''
				}
				case OR: {
					return '''«parseExpression(expr.lhs, contextType)» || «parseExpression(expr.rhs, contextType)»'''			
				}
				case XOR: {
					return '''«parseExpression(expr.lhs, contextType)» ^ «parseExpression(expr.rhs, contextType)»'''
				}
			}
		} else if(expr instanceof BoolUnaryExpression) {
			switch(expr.operator) {
				case NOT: {
					return '''!«parseExpression(expr.expression, contextType)»'''
				}
				case BRACKET: {
					return '''(«parseExpression(expr.expression, contextType)»)'''
				}
			}
		} else if(expr instanceof BoolLiteral) {
			return '''«(expr.literal)?"true":"false"»''';
		} else if(expr instanceof RelationalExpression) {
			return parseRelationalExpression(expr, contextType)
		} else {
			val value = expr as BoolValue;
			switch(contextType) {
				case constConstraint: {
					return parseExpression(value.getValue(), ExpressionContext.constStream);
				}
				case constStream: {
					return parseExpression(value.getValue(), ExpressionContext.constStream);
				}
				case varConstraint: {
					return parseExpression(value.getValue(), ExpressionContext.varStream);
				}
				case varStream: {
					return parseExpression(value.getValue(), ExpressionContext.varStream);
				}
			}
		}
	}
	
	def String parseRelationalExpression(RelationalExpression expr, ExpressionContext contextType) {
		switch(expr.operator) {
			case EQUAL: {
				return '''«parseExpression(expr.lhs, contextType)» == «parseExpression(expr.rhs, contextType)»'''
			}
			case GREATER: {
				return '''«parseExpression(expr.lhs, contextType)» > «parseExpression(expr.rhs, contextType)»'''
			}
			case GREATER_OR_EQUAL: {
				return '''«parseExpression(expr.lhs, contextType)» >= «parseExpression(expr.rhs, contextType)»'''
			}
			case LESS: {
				return '''«parseExpression(expr.lhs, contextType)» < «parseExpression(expr.rhs, contextType)»'''
			}
			case LESS_OR_EQUAL: {
				return '''«parseExpression(expr.lhs, contextType)» <= «parseExpression(expr.rhs, contextType)»'''
			}
			case NOT_EQUAL: {
				return '''«parseExpression(expr.lhs, contextType)» != «parseExpression(expr.rhs, contextType)»'''
			}
			case OBJECT_EQUAL: {
				return '''«parseExpression(expr.lhs, contextType)».equals(«parseExpression(expr.rhs, contextType)»)'''
			}
			case OBJECT_NOT_EQUAL: {
				return '''!«parseExpression(expr.lhs, contextType)».equals(«parseExpression(expr.rhs, contextType)»)'''
			}
		}
	}
	
	def String parseExpression(StreamExpression expr, ExpressionContext contextType) {
		switch(contextType) {
			case constConstraint: {
				return parseConstExpression(expr);
			}
			case constStream: {
				return parseConstExpression(expr);
			}
			case varConstraint: {
				return parseVarExpression(expr);
			}
			case varStream: {
				return parseVarExpression(expr);
			}
		}
	}
	
	def String parseConstExpression(StreamExpression expr) {
		if(expr.current instanceof StreamFilterOperation) {
			if(expr.child === null) {
				return '''filter(«getIteratorVariableName(expr)» -> «parseExpression((expr.current as StreamFilterOperation).predicate, ExpressionContext.constStream)»)'''
			} else {
				return '''filter(«getIteratorVariableName(expr)» -> «parseExpression((expr.current as StreamFilterOperation).predicate, ExpressionContext.constStream)»)
				.«parseExpression(expr.child, ExpressionContext.constStream)»'''
			}
		} else if (expr.current instanceof StreamSelectOperation) {
			val selectOp = expr.current as StreamSelectOperation
			imports.add(data.classToPackage.getImportsForType(selectOp.type))
			if(expr.child === null) {
				return '''filter(«getIteratorVariableName(expr)» -> «getIteratorVariableName(expr)» instanceof «selectOp.type.name»)
				.map(«getIteratorVariableName(expr)» -> («selectOp.type.name») «getIteratorVariableName(expr)»)'''
			} else {
				return '''filter(«getIteratorVariableName(expr)» -> «getIteratorVariableName(expr)» instanceof «selectOp.type.name»)
				.map(«getIteratorVariableName(expr)» -> («selectOp.type.name») «getIteratorVariableName(expr)»)
				.«parseExpression(expr.child, ExpressionContext.constStream)»'''
			}
		} else {
			return ''''''
		}
	}
	
	def String parseVarExpression(StreamExpression expr) {
		if(expr.current instanceof StreamFilterOperation) {
			if(expr.child === null) {
				return '''filter(«getIteratorVariableName(expr)» -> «parseExpression((expr.current as StreamFilterOperation).predicate, ExpressionContext.varStream)»)'''
			} else {
				return '''filter(«getIteratorVariableName(expr)» -> «parseExpression((expr.current as StreamFilterOperation).predicate, ExpressionContext.varStream)»)
				.«parseExpression(expr.child, ExpressionContext.varStream)»'''
			}
		} else if (expr.current instanceof StreamSelectOperation) {
			val selectOp = expr.current as StreamSelectOperation
			imports.add(data.classToPackage.getImportsForType(selectOp.type))
			if(expr.child === null) {
				return '''filter(«getIteratorVariableName(expr)» -> «getIteratorVariableName(expr)» instanceof «selectOp.type.name»)
				.map(«getIteratorVariableName(expr)» -> («selectOp.type.name») «getIteratorVariableName(expr)»)'''
			} else {
				return '''filter(«getIteratorVariableName(expr)» -> «getIteratorVariableName(expr)» instanceof «selectOp.type.name»)
				.map(«getIteratorVariableName(expr)» -> («selectOp.type.name») «getIteratorVariableName(expr)»)
				.«parseExpression(expr.child, ExpressionContext.varStream)»'''
			}
		} else {
			return ''''''
		}
	}
	
	def String parseExpression(ValueExpression constExpr, ExpressionContext contextType) {
		switch(contextType) {
			case constConstraint: {
				return parseConstExpression(constExpr);
			}
			case constStream: {
				return parseConstStreamExpression(constExpr);
			}
			case varConstraint: {
				return parseVarExpression(constExpr)
			}
			case varStream: {
				return parseVarStreamExpression(constExpr)
			}
		}
	}
	
	def String parseConstExpression(ValueExpression constExpr) {
		if(constExpr instanceof MappingSumExpression) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof TypeSumExpression) {
			imports.add(data.classToPackage.getImportsForType(constExpr.type.type))
			return '''indexer.getObjectsOfType("«constExpr.type.type.name»").parallelStream()
			.map(type -> («constExpr.type.type.name») type)
			«getFilterExpr(constExpr.filter, ExpressionContext.constConstraint)»
			.map(«getIteratorVariableName(constExpr)» -> 1.0 * «parseExpression(constExpr.expression, ExpressionContext.constConstraint)»)
			.reduce(0.0, (sum, value) -> sum + value)'''
		} else if(constExpr instanceof PatternSumExpression) {
			imports.add(data.apiData.matchesPkg+"."+data.pattern2matchClassName.get(constExpr.pattern))
			return '''engine.getEMoflonAPI().«constExpr.pattern.name»().findMatches(false).parallelStream()
			«getFilterExpr(constExpr.filter, ExpressionContext.constConstraint)»
			.map(«getIteratorVariableName(constExpr)» -> 1.0 * «parseExpression(constExpr.expression, ExpressionContext.constConstraint)»)
			.reduce(0.0, (sum, value) -> sum + value)'''
		} else if(constExpr instanceof ContextSumExpression) {
			if(constExpr.context instanceof Mapping) {
				throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
			} else if(constExpr.context instanceof Pattern) {
				return '''context.get«constExpr.node.name.toFirstUpper»().«parseFeatureExpression(constExpr.feature)».stream()
			«getFilterExpr(constExpr.filter, ExpressionContext.constConstraint)»
			.map(«getIteratorVariableName(constExpr)» -> 1.0 * «parseExpression(constExpr.expression, ExpressionContext.constConstraint)»)
			.reduce(0.0, (sum, value) -> sum + value)'''
			} else if(constExpr.context instanceof Type) {
				return '''context.«parseFeatureExpression(constExpr.feature)».stream()
			«getFilterExpr(constExpr.filter, ExpressionContext.constConstraint)»
			.map(«getIteratorVariableName(constExpr)» -> 1.0 * «parseExpression(constExpr.expression, ExpressionContext.constConstraint)»)
			.reduce(0.0, (sum, value) -> sum + value)'''
			} else {
				throw new UnsupportedOperationException("Unknown context type.");
			}
		} else if(constExpr instanceof ContextTypeFeatureValue) {
			return '''context.«parseFeatureExpression(constExpr.featureExpression)»'''
		} else if(constExpr instanceof ContextTypeValue) {
			return '''context'''
		} else if(constExpr instanceof ContextPatternNodeFeatureValue) {
			return '''context.get«constExpr.node.name.toFirstUpper»().«parseFeatureExpression(constExpr.featureExpression)»'''
		} else if(constExpr instanceof ContextPatternNode) {
			return '''context.get«constExpr.node.name.toFirstUpper»()'''
		} else if(constExpr instanceof ContextPatternValue) {
			return '''context'''
		} else if(constExpr instanceof ObjectiveFunctionValue) {
			throw new UnsupportedOperationException("Objective function value access not allowed in mapping constraints.");
		} else if(constExpr instanceof ContextMappingValue) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof ContextMappingVariablesReference) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof ContextMappingNodeFeatureValue) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof ContextMappingNode) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof IteratorMappingValue) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof IteratorMappingVariableValue) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof IteratorMappingVariablesReference) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof IteratorMappingFeatureValue) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof IteratorMappingNodeFeatureValue) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof IteratorMappingNodeValue) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof IteratorPatternValue) {
			return '''«getIteratorVariableName(constExpr.stream)»'''
		}  else if(constExpr instanceof IteratorPatternFeatureValue) {
			return '''«getIteratorVariableName(constExpr.stream)».«parseFeatureExpression(constExpr.featureExpression)»'''
		} else if(constExpr instanceof IteratorPatternNodeFeatureValue) {
			return '''«getIteratorVariableName(constExpr.stream)».get«constExpr.node.name.toFirstUpper»().«parseFeatureExpression(constExpr.featureExpression)»'''
		} else if(constExpr instanceof IteratorPatternNodeValue) {
			return '''«getIteratorVariableName(constExpr.stream)».get«constExpr.node.name.toFirstUpper»()'''
		} else if(constExpr instanceof IteratorTypeFeatureValue){
			return '''«getIteratorVariableName(constExpr.stream)».«parseFeatureExpression(constExpr.featureExpression)»'''
		} else {
			val itrTypVal = constExpr as IteratorTypeValue
			return '''«getIteratorVariableName(itrTypVal.stream)»'''
		}
	}
	
	def String parseConstStreamExpression(ValueExpression constExpr) {
		if(constExpr instanceof MappingSumExpression) {
			throw new UnsupportedOperationException("Nested mapping streams not allowed in mapping stream expressions.");
		} else if(constExpr instanceof TypeSumExpression) {
			throw new UnsupportedOperationException("Nested stream expressions are not allowed.");
		} else if(constExpr instanceof ContextTypeFeatureValue) {
			return '''context.«parseFeatureExpression(constExpr.featureExpression)»'''
		} else if(constExpr instanceof ContextTypeValue) {
			return '''context'''
		} else if(constExpr instanceof ContextPatternNodeFeatureValue) {
			return '''context.get«constExpr.node.name.toFirstUpper»().«parseFeatureExpression(constExpr.featureExpression)»'''
		} else if(constExpr instanceof ContextPatternNode) {
			return '''context.get«constExpr.node.name.toFirstUpper»()'''
		} else if(constExpr instanceof ContextPatternValue) {
			return '''context'''
		} else if(constExpr instanceof ObjectiveFunctionValue) {
			throw new UnsupportedOperationException("Objective function value access not allowed in mapping constraints.");
		} else if(constExpr instanceof ContextMappingValue) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof ContextMappingVariablesReference) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof ContextMappingNodeFeatureValue) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof ContextMappingNode) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof IteratorMappingValue) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof IteratorMappingVariableValue) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof IteratorMappingVariablesReference) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof IteratorMappingFeatureValue) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof IteratorMappingNodeFeatureValue) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof IteratorMappingNodeValue) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof IteratorPatternValue) {
			return '''«getIteratorVariableName(constExpr.stream)»'''
		}  else if(constExpr instanceof IteratorPatternFeatureValue) {
			return '''«getIteratorVariableName(constExpr.stream)».«parseFeatureExpression(constExpr.featureExpression)»'''
		} else if(constExpr instanceof IteratorPatternNodeFeatureValue) {
			return '''«getIteratorVariableName(constExpr.stream)».get«constExpr.node.name.toFirstUpper»().«parseFeatureExpression(constExpr.featureExpression)»'''
		} else if(constExpr instanceof IteratorPatternNodeValue) {
			return '''«getIteratorVariableName(constExpr.stream)».get«constExpr.node.name.toFirstUpper»()'''
		} else if(constExpr instanceof IteratorTypeFeatureValue){
			return '''«getIteratorVariableName(constExpr.stream)».«parseFeatureExpression(constExpr.featureExpression)»'''
		} else {
			val itrTypVal = constExpr as IteratorTypeValue
			return '''«getIteratorVariableName(itrTypVal.stream)»'''
		}
	}
	
	def String parseVarExpression(ValueExpression varExpr) {
		if(varExpr instanceof MappingSumExpression) {
			throw new UnsupportedOperationException("Mapping stream expressions may not be part of multiplications, fractions, exponentials, roots etc.");
		} else if(varExpr instanceof TypeSumExpression) {
			imports.add(data.classToPackage.getImportsForType(varExpr.type.type))
			return '''indexer.getObjectsOfType("«varExpr.type.type.name»").parallelStream()
			.map(type -> («varExpr.type.type.name») type)
			«getFilterExpr(varExpr.filter, ExpressionContext.varConstraint)»
			.map(«getIteratorVariableName(varExpr)» -> 1.0 * «parseExpression(varExpr.expression, ExpressionContext.varConstraint)»)
			.reduce(0.0, (sum, value) -> sum + value)'''
		} else if(varExpr instanceof PatternSumExpression) {
			imports.add(data.apiData.matchesPkg+"."+data.pattern2matchClassName.get(varExpr.pattern))
			return '''engine.getEMoflonAPI().«varExpr.pattern.name»().findMatches(false).parallelStream()
			«getFilterExpr(varExpr.filter, ExpressionContext.varConstraint)»
			.map(«getIteratorVariableName(varExpr)» -> 1.0 * «parseExpression(varExpr.expression, ExpressionContext.varConstraint)»)
			.reduce(0.0, (sum, value) -> sum + value)'''
		} else if(varExpr instanceof ContextSumExpression) {
			if(varExpr.context instanceof Mapping) {
				throw new UnsupportedOperationException("Mapping stream expressions may not be part of multiplications, fractions, exponentials, roots etc.");
			} else if(varExpr.context instanceof Pattern) {
				return '''context.get«varExpr.node.name.toFirstUpper»().«parseFeatureExpression(varExpr.feature)».stream()
			«getFilterExpr(varExpr.filter, ExpressionContext.varConstraint)»
			.map(«getIteratorVariableName(varExpr)» -> 1.0 * «parseExpression(varExpr.expression, ExpressionContext.varConstraint)»)
			.reduce(0.0, (sum, value) -> sum + value)'''
			} else if(varExpr.context instanceof Type) {
				return '''context.«parseFeatureExpression(varExpr.feature)».stream()
			«getFilterExpr(varExpr.filter, ExpressionContext.varConstraint)»
			.map(«getIteratorVariableName(varExpr)» -> 1.0 * «parseExpression(varExpr.expression, ExpressionContext.varConstraint)»)
			.reduce(0.0, (sum, value) -> sum + value)'''
			} else {
				throw new UnsupportedOperationException("Unknown context type.");
			}
		} else if(varExpr instanceof ContextTypeFeatureValue) {
			return '''context.«parseFeatureExpression(varExpr.featureExpression)»'''
		} else if(varExpr instanceof ContextTypeValue) {
			return '''context'''
		} else if(varExpr instanceof ContextPatternNodeFeatureValue) {
			return '''context.get«varExpr.node.name.toFirstUpper»().«parseFeatureExpression(varExpr.featureExpression)»'''
		} else if(varExpr instanceof ContextPatternNode) {
			return '''context.get«varExpr.node.name.toFirstUpper»()'''
		} else if(varExpr instanceof ContextPatternValue) {
			return '''context'''
		} else if(varExpr instanceof ObjectiveFunctionValue) {
			throw new UnsupportedOperationException("Objective function value access not allowed in mapping constraints.");
		} else if(varExpr instanceof ContextMappingValue) {
			//This should have been taken care of already. -> Constant 1 doesn't hurt... 
			return '''1.0'''
		} else if(varExpr instanceof ContextMappingVariablesReference) {
			//This should have been taken care of already. -> Constant 1 doesn't hurt... 
			return '''1.0'''
		} else if(varExpr instanceof ContextMappingNodeFeatureValue) {
			return '''context.get«varExpr.node.name.toFirstUpper»().«parseFeatureExpression(varExpr.featureExpression)»'''
		} else if(varExpr instanceof ContextMappingNode) {
			return '''context.get«varExpr.node.name.toFirstUpper»()'''
		} else if(varExpr instanceof IteratorMappingValue) {
			return '''«getIteratorVariableName(varExpr.stream)»'''
		} else if(varExpr instanceof IteratorMappingVariableValue) {
			//This should have been taken care of already. -> Constant 1 doesn't hurt... 
			return '''1.0'''
		} else if(varExpr instanceof IteratorMappingVariablesReference) {
			//This should have been taken care of already. -> Constant 1 doesn't hurt... 
			return '''1.0'''
		} else if(varExpr instanceof IteratorMappingFeatureValue) {
			return '''«getIteratorVariableName(varExpr.stream)».«parseFeatureExpression(varExpr.featureExpression)»'''
		} else if(varExpr instanceof IteratorMappingNodeFeatureValue) {
			return '''«getIteratorVariableName(varExpr.stream)».get«varExpr.node.name.toFirstUpper»().«parseFeatureExpression(varExpr.featureExpression)»'''
		} else if(varExpr instanceof IteratorMappingNodeValue) {
			return '''«getIteratorVariableName(varExpr.stream)».get«varExpr.node.name.toFirstUpper»()'''
		} else if(varExpr instanceof IteratorPatternValue) {
			return '''«getIteratorVariableName(varExpr.stream)»'''
		}  else if(varExpr instanceof IteratorPatternFeatureValue) {
			return '''«getIteratorVariableName(varExpr.stream)».«parseFeatureExpression(varExpr.featureExpression)»'''
		} else if(varExpr instanceof IteratorPatternNodeFeatureValue) {
			return '''«getIteratorVariableName(varExpr.stream)».get«varExpr.node.name.toFirstUpper»().«parseFeatureExpression(varExpr.featureExpression)»'''
		} else if(varExpr instanceof IteratorPatternNodeValue) {
			return '''«getIteratorVariableName(varExpr.stream)».get«varExpr.node.name.toFirstUpper»()'''
		} else if(varExpr instanceof IteratorTypeFeatureValue){
			return '''«getIteratorVariableName(varExpr.stream)».«parseFeatureExpression(varExpr.featureExpression)»'''
		} else {
			val itrTypVal = varExpr as IteratorTypeValue
			return '''«getIteratorVariableName(itrTypVal.stream)»'''
		}
	}
	
	def String parseVarStreamExpression(ValueExpression varExpr) {
		if(varExpr instanceof MappingSumExpression) {
			throw new UnsupportedOperationException("Nested mapping streams not allowed in mapping stream expressions.");
		} else if(varExpr instanceof TypeSumExpression) {
			throw new UnsupportedOperationException("Nested stream expressions are not allowed.");
		} else if(varExpr instanceof ContextTypeFeatureValue) {
			return '''context.«parseFeatureExpression(varExpr.featureExpression)»'''
		} else if(varExpr instanceof ContextTypeValue) {
			return '''context'''
		} else if(varExpr instanceof ContextPatternNodeFeatureValue) {
			return '''context.get«varExpr.node.name.toFirstUpper»().«parseFeatureExpression(varExpr.featureExpression)»'''
		} else if(varExpr instanceof ContextPatternNode) {
			return '''context.get«varExpr.node.name.toFirstUpper»()'''
		} else if(varExpr instanceof ContextPatternValue) {
			return '''context'''
		} else if(varExpr instanceof ObjectiveFunctionValue) {
			throw new UnsupportedOperationException("Objective function value access not allowed in mapping constraints.");
		} else if(varExpr instanceof ContextMappingValue) {
			//This should have been taken care of already. -> Constant 1 doesn't hurt... 
			return '''1.0'''
		} else if(varExpr instanceof ContextMappingVariablesReference) {
			//This should have been taken care of already. -> Constant 1 doesn't hurt... 
			return '''1.0'''
		} else if(varExpr instanceof ContextMappingNodeFeatureValue) {
			return '''context.get«varExpr.node.name.toFirstUpper»().«parseFeatureExpression(varExpr.featureExpression)»'''
		} else if(varExpr instanceof ContextMappingNode) {
			return '''context.get«varExpr.node.name.toFirstUpper»()'''
		} else if(varExpr instanceof IteratorMappingValue) {
			return '''«getIteratorVariableName(varExpr.stream)»'''
		} else if(varExpr instanceof IteratorMappingVariableValue) {
			//This should have been taken care of already. -> Constant 1 doesn't hurt... 
			return '''1.0'''
		} else if(varExpr instanceof IteratorMappingVariablesReference) {
			//This should have been taken care of already. -> Constant 1 doesn't hurt... 
			return '''1.0'''
		} else if(varExpr instanceof IteratorMappingFeatureValue) {
			return '''«getIteratorVariableName(varExpr.stream)».«parseFeatureExpression(varExpr.featureExpression)»'''
		} else if(varExpr instanceof IteratorMappingNodeFeatureValue) {
			return '''«getIteratorVariableName(varExpr.stream)».get«varExpr.node.name.toFirstUpper»().«parseFeatureExpression(varExpr.featureExpression)»'''
		} else if(varExpr instanceof IteratorMappingNodeValue) {
			return '''«getIteratorVariableName(varExpr.stream)».get«varExpr.node.name.toFirstUpper»()'''
		} else if(varExpr instanceof IteratorPatternValue) {
			return '''«getIteratorVariableName(varExpr.stream)»'''
		} else if(varExpr instanceof IteratorPatternFeatureValue) {
			return '''«getIteratorVariableName(varExpr.stream)».«parseFeatureExpression(varExpr.featureExpression)»'''
		} else if(varExpr instanceof IteratorPatternNodeFeatureValue) {
			return '''«getIteratorVariableName(varExpr.stream)».get«varExpr.node.name.toFirstUpper»().«parseFeatureExpression(varExpr.featureExpression)»'''
		} else if(varExpr instanceof IteratorPatternNodeValue) {
			return '''«getIteratorVariableName(varExpr.stream)».get«varExpr.node.name.toFirstUpper»()'''
		} else if(varExpr instanceof IteratorTypeFeatureValue){
			return '''«getIteratorVariableName(varExpr.stream)».«parseFeatureExpression(varExpr.featureExpression)»'''
		} else {
			val itrTypVal = varExpr as IteratorTypeValue
			return '''«getIteratorVariableName(itrTypVal.stream)»'''
		}
	}
	
	def String getFilterExpr(StreamExpression expr, ExpressionContext context) {
		val fltr = parseExpression(expr, context)
		if(fltr.isEmpty)
			return fltr
			
		return "." + fltr
	}
	
	def String parseFeatureExpression(FeatureExpression expr) {
		var getOrIs = "get"
		if(expr.current.feature.EType == EcorePackage.Literals.EBOOLEAN)
			getOrIs = "is";
			
		if(expr.child === null) {
			return '''«getOrIs»«expr.current.feature.name.toFirstUpper»()'''
		} else {
			return '''«getOrIs»«expr.current.feature.name.toFirstUpper»().«parseFeatureExpression(expr.child)»'''
		}
	}

	def String getIteratorVariableName(SetOperation iterator) {
		var itr = iterator
		if(iterator.eContainer instanceof SumExpression) {
			itr = iterator.eContainer as SetOperation
		}
		var itrName = iterator2variableName.get(itr)
		if(itrName === null) {
			itrName = '''itr_«iterator2variableName.size»'''
			iterator2variableName.put(itr, itrName);
		}
		return itrName;
	}
	
}