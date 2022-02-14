package org.emoflon.roam.build.generator.templates

import org.emoflon.roam.build.generator.GeneratorTemplate
import org.emoflon.roam.build.generator.TemplateData
import org.emoflon.roam.build.transformation.helper.RoamTransformationUtils
import org.emoflon.roam.intermediate.RoamIntermediate.MappingConstraint
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticExpression
import org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticExpression
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticLiteral
import org.emoflon.roam.intermediate.RoamIntermediate.DoubleLiteral
import org.emoflon.roam.intermediate.RoamIntermediate.IntegerLiteral
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticValue
import org.emoflon.roam.intermediate.RoamIntermediate.ValueExpression
import org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeValue
import org.emoflon.roam.intermediate.RoamIntermediate.TypeSumExpression
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNode
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeFeatureValue
import org.emoflon.roam.intermediate.RoamIntermediate.MappingSumExpression
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingFeatureValue
import org.emoflon.roam.intermediate.RoamIntermediate.ObjectiveFunctionValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorTypeFeatureValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorTypeValue
import java.util.HashMap
import org.emoflon.roam.intermediate.RoamIntermediate.SetOperation
import org.emoflon.roam.intermediate.RoamIntermediate.FeatureExpression
import org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression
import org.emoflon.roam.intermediate.RoamIntermediate.BoolExpression
import org.emoflon.roam.intermediate.RoamIntermediate.BoolBinaryExpression
import org.emoflon.roam.intermediate.RoamIntermediate.BoolUnaryExpression
import org.emoflon.roam.intermediate.RoamIntermediate.BoolLiteral
import org.emoflon.roam.intermediate.RoamIntermediate.RelationalExpression
import org.emoflon.roam.intermediate.RoamIntermediate.BoolStreamExpression
import org.emoflon.roam.intermediate.RoamIntermediate.BoolValue
import org.emoflon.roam.intermediate.RoamIntermediate.StreamFilterOperation
import org.emoflon.roam.intermediate.RoamIntermediate.StreamSelectOperation
import org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeFeatureValue
import org.emoflon.roam.build.transformation.ArithmeticExpressionType
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticOperator
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNodeFeatureValue
import org.eclipse.emf.ecore.EObject

class MappingConstraintTemplate extends GeneratorTemplate<MappingConstraint> {

	val iterator2variableName = new HashMap<SetOperation, String>();
	val builderMethods = new HashMap<EObject,String>
	val builderMethodDefinitions = new HashMap<EObject,String>

	new(TemplateData data, MappingConstraint context) {
		super(data, context)
	}

	override init() {
		packageName = data.apiData.roamConstraintPkg
		className = data.constraint2constraintClassName.get(context)
		fqn = packageName + "." + className;
		filePath = data.apiData.roamConstraintPkgPath + "/" + className + ".java"
		imports.add("java.util.List")
		imports.add("java.util.LinkedList")
		imports.add("org.emoflon.roam.core.RoamEngine")
		imports.add("org.emoflon.roam.core.RoamMappingConstraint")
		imports.add("org.emoflon.roam.core.ilp.ILPTerm")
		imports.add("org.emoflon.roam.intermediate.RoamIntermediate.MappingConstraint")
		imports.add(data.apiData.roamMappingPkg+"."+data.mapping2mappingClassName.get(context.mapping))
	}
	
	override generate() {
		code = '''package «packageName»;

«FOR imp : imports»
import «imp»;
«ENDFOR»

public class «className» extends RoamMappingConstraint<«data.mapping2mappingClassName.get(context.mapping)»>{
	public «className»(final RoamEngine engine, final MappingConstraint constraint) {
		super(engine, constraint);
	}
	«IF RoamTransformationUtils.isConstantExpression(context.expression.lhs) == ArithmeticExpressionType.constant»
	«generateComplexConstraint(context.expression.lhs, context.expression.rhs)»
	«ELSE»
	«generateComplexConstraint(context.expression.rhs, context.expression.lhs)»
	«ENDIF»
}'''
	}
	
	def String generateComplexConstraint(ArithmeticExpression constExpr, ArithmeticExpression dynamicExpr) {
		return '''
@Override
protected Double buildConstantTerm(final «data.mapping2mappingClassName.get(context.mapping)» context) {
	return «generateConstTermBuilder(constExpr)»;
}
	
@Override
protected List<ILPTerm<Integer, Double>> buildVariableTerms(final «data.mapping2mappingClassName.get(context.mapping)» context) {
	List<ILPTerm<Integer, Double>> terms = new LinkedList<>();
	
	return terms;
}

«FOR generator : builderMethodDefinitions.values»
«generator»
«ENDFOR»
		'''
	}
	
	def String generateConstTermBuilder(ArithmeticExpression constExpr) {
		return '''«parseConstArithmeticExpression(constExpr)»'''
	}
	
	def String generateVariableTermBuilder(ArithmeticExpression expr) {
		if(expr instanceof BinaryArithmeticExpression) {
			if(expr.operator == BinaryArithmeticOperator.ADD || expr.operator == BinaryArithmeticOperator.SUBTRACT) {
				return generateVariableTermBuilder(expr.lhs) + generateVariableTermBuilder(expr.rhs)
			} else if(expr.operator == BinaryArithmeticOperator.MULTIPLY || expr.operator == BinaryArithmeticOperator.DIVIDE) {
				
			} else {
				//CASE: Pow
				
			}
		} else if(expr instanceof UnaryArithmeticExpression) {
			generateBuilderMethod(expr)
			return '''terms.add(«builderMethods.get(expr)»(context));
			'''
		} else if(expr instanceof ArithmeticValue) {
			return generateVariableTermBuilder(expr.value)
		} else {
			throw new IllegalAccessException("Ilp term may not be constant")
		}
	}
	
	def String generateVariableTermBuilder(ValueExpression expr) {
		if(expr instanceof MappingSumExpression) {
			generateBuilderMethod(expr);
			return '''«builderMethods.get(expr)»(context);'''
		} else if(expr instanceof TypeSumExpression) {
			
		} else if(expr instanceof ContextTypeFeatureValue) {
			throw new IllegalAccessException("Ilp term may not be constant.")
		} else if(expr instanceof ContextTypeValue) {
			throw new IllegalAccessException("Ilp term may not be constant.")
		} else if(expr instanceof ContextMappingNodeFeatureValue) {
			generateBuilderMethod(expr)
			return '''terms.add(«builderMethods.get(expr)»(context));
			'''
		} else if(expr instanceof ContextMappingNode) {
			throw new IllegalAccessException("Ilp term may not contain complex objects.")
		} else if(expr instanceof ContextMappingValue) {
			generateBuilderMethod(expr)
			return '''terms.add(«builderMethods.get(expr)»(context));
			'''
		} else if(expr instanceof ObjectiveFunctionValue) {
			throw new IllegalAccessException("Ilp term may not contain references to objective functions.")
		} else if(expr instanceof IteratorMappingValue) {
			throw new UnsupportedOperationException("Iterators may not be used outside of lambda expressions")
		} else if(expr instanceof IteratorMappingFeatureValue) {
			throw new UnsupportedOperationException("Iterators may not be used outside of lambda expressions")
		} else if(expr instanceof IteratorMappingNodeFeatureValue) {
			throw new UnsupportedOperationException("Iterators may not be used outside of lambda expressions")
		} else if(expr instanceof IteratorMappingNodeValue) {
			throw new IllegalAccessException("Ilp term may not contain complex objects.")
		} else {
			// CASE: IteratorTypeValue or IteratorTypeFeatureValue 
			throw new IllegalAccessException("Ilp term may not be constant.")
		}
	}
	
	def void generateBuilderMethod(UnaryArithmeticExpression expr) {
		val methodName = '''builder_«builderMethods.size»'''
		val method = '''
	protected ILPTerm<Integer, Double> «methodName»(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		return null;
	}
		'''
		builderMethods.put(expr, methodName)
		builderMethodDefinitions.put(expr, method)
	}
	
	def void generateBuilderMethod(MappingSumExpression expr) {
		val methodName = '''builder_«builderMethods.size»'''
		val method = '''
	protected void «methodName»(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		for(«data.mapping2mappingClassName.get(expr.mapping)» «getIteratorVariableName(expr)» : engine.getMapper(«expr.mapping.name»).getMappings().values().parallelStream()
			.«parseStreamExpression(expr.filter)».collect(Collectors.toList())) {
			double constValue = «parseArithmeticExpression(expr.expression, false)
			»;
			ILPTerm<Integer, Double> term = new ILPTerm<Integer, Double>(«getIteratorVariableName(expr)», constValue);
			terms.add(term);
		}
		return new ILPTerm<Integer, Double>(context, 1.0);
	}
		'''
		builderMethods.put(expr, methodName)
		builderMethodDefinitions.put(expr, method)
	}
	
	def void generateBuilderMethod(TypeSumExpression expr) {
		val methodName = '''builder_«builderMethods.size»'''
		val method = '''
	protected void «methodName»(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		for(«expr.type.type.name» «getIteratorVariableName(expr)» : indexer.getObjectsOfType("«expr.type.name»").parallelStream()
			.«parseStreamExpression(expr.filter)».collect(Collectors.toList())) {
			double constValue = «parseArithmeticExpression(expr.expression, false)»;
			ILPTerm<Integer, Double> term = new ILPTerm<Integer, Double>(«getIteratorVariableName(expr)», constValue);
			terms.add(term);
		}
		return new ILPTerm<Integer, Double>(context, 1.0);
	}
		'''
		builderMethods.put(expr, methodName)
		builderMethodDefinitions.put(expr, method)
	}
	
	def void generateBuilderMethod(ContextMappingValue expr) {
		val methodName = '''builder_«builderMethods.size»'''
		val method = '''
	protected ILPTerm<Integer, Double> «methodName»(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		return new ILPTerm<Integer, Double>(context, 1.0);
	}
		'''
		builderMethods.put(expr, methodName)
		builderMethodDefinitions.put(expr, method)
	}
	
	def void generateBuilderMethod(ContextMappingNodeFeatureValue expr) {
		val methodName = '''builder_«builderMethods.size»'''
		val method = '''
	protected ILPTerm<Integer, Double> «methodName»(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		return new ILPTerm<Integer, Double>(context, context.«parseFeatureExpression(expr.featureExpression)»);
	}
		'''
		builderMethods.put(expr, methodName)
		builderMethodDefinitions.put(expr, method)
	}
	
	def String parseArithmeticExpression(ArithmeticExpression expr, boolean isConstant) {
		if(isConstant) {
			return parseConstArithmeticExpression(expr);
		} else {
			return parseStreamArithmeticExpression(expr);
		}
	}
	
	def String parseConstArithmeticExpression(ArithmeticExpression expr) {
		if(expr instanceof BinaryArithmeticExpression) {
			return transformOperation(expr, true)
		} else if(expr instanceof UnaryArithmeticExpression) {
			return transformOperation(expr, true)
		} else if(expr instanceof ArithmeticLiteral) {
			if(expr instanceof DoubleLiteral) {
				return String.valueOf(expr.literal)
			} else {
				return String.valueOf((expr as IntegerLiteral).literal)
			}
		} else {
			val value = expr as ArithmeticValue
			return parseConstValueExpression(value.value)
		}
	}
	
	def String parseStreamArithmeticExpression(ArithmeticExpression expr) {
		if(expr instanceof BinaryArithmeticExpression) {
			return transformOperation(expr, false)
		} else if(expr instanceof UnaryArithmeticExpression) {
			return transformOperation(expr, false)
		} else if(expr instanceof ArithmeticLiteral) {
			if(expr instanceof DoubleLiteral) {
				return String.valueOf(expr.literal)
			} else {
				return String.valueOf((expr as IntegerLiteral).literal)
			}
		} else {
			val value = expr as ArithmeticValue
			return parseValueExpression(value.value, false)
		}
	}
	
	def String parseValueExpression(ValueExpression constExpr, boolean isConstant) {
		if(isConstant) {
			return parseConstValueExpression(constExpr);
		} else {
			return parseStreamValueExpression(constExpr);
		}
	}
	
	def String parseConstValueExpression(ValueExpression constExpr) {
		if(constExpr instanceof MappingSumExpression) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof TypeSumExpression) {
			imports.add(data.classToPackage.getPackage(constExpr.type.type.EPackage))
			return '''indexer.getObjectsOfType(«constExpr.type.type.EPackage.name».eINSTANCE.get«constExpr.type.type.name»()).stream()
			.«parseStreamExpression(constExpr.filter)»
			.reduce(0, (sum, «getIteratorVariableName(constExpr)») -> {
				sum + «parseConstArithmeticExpression(constExpr.expression)»
			})'''
		} else if(constExpr instanceof ContextTypeFeatureValue) {
			throw new UnsupportedOperationException("Type context access not allowed in mapping constraints.");
		} else if(constExpr instanceof ContextTypeValue) {
			throw new UnsupportedOperationException("Type context access not allowed in mapping constraints.");
		} else if(constExpr instanceof ObjectiveFunctionValue) {
			throw new UnsupportedOperationException("Objective function value access not allowed in mapping constraints.");
		} else if(constExpr instanceof ContextMappingValue) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof ContextMappingNode) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof IteratorMappingValue) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof IteratorMappingFeatureValue) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof IteratorMappingNodeFeatureValue) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof IteratorMappingNodeValue) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof IteratorTypeFeatureValue){
			return '''«getIteratorVariableName(constExpr.stream)».«parseFeatureExpression(constExpr.featureExpression)»'''
		} else {
			val itrTypVal = constExpr as IteratorTypeValue
			return '''«getIteratorVariableName(itrTypVal.stream)»'''
		}
	}
	
	def String parseStreamValueExpression(ValueExpression constExpr) {
		if(constExpr instanceof MappingSumExpression) {
			throw new UnsupportedOperationException("Nested mapping streams not allowed in mapping stream expressions.");
		} else if(constExpr instanceof TypeSumExpression) {
			imports.add(data.classToPackage.getPackage(constExpr.type.type.EPackage))
			return '''indexer.getObjectsOfType(«constExpr.type.type.EPackage.name».eINSTANCE.get«constExpr.type.type.name»()).stream()
			.«parseStreamExpression(constExpr.filter)»
			.reduce(0, (sum, «getIteratorVariableName(constExpr)») -> {
				sum + «parseConstArithmeticExpression(constExpr.expression)»
			})'''
		} else if(constExpr instanceof ContextTypeFeatureValue) {
			throw new UnsupportedOperationException("Type context access not allowed in mapping constraints.");
		} else if(constExpr instanceof ContextTypeValue) {
			throw new UnsupportedOperationException("Type context access not allowed in mapping constraints.");
		} else if(constExpr instanceof ObjectiveFunctionValue) {
			throw new UnsupportedOperationException("Objective function value access not allowed in mapping constraints.");
		} else if(constExpr instanceof ContextMappingValue) {
			throw new UnsupportedOperationException("Mapping context access not allowed in mapping stream expressions.");
		} else if(constExpr instanceof ContextMappingNode) {
			throw new UnsupportedOperationException("Complex objects are not allowed within arithmetic expressions.");
		} else if(constExpr instanceof IteratorMappingValue) {
			return "1"
		} else if(constExpr instanceof IteratorMappingFeatureValue) {
			return '''«getIteratorVariableName(constExpr.stream)».«parseFeatureExpression(constExpr.featureExpression)»'''
		} else if(constExpr instanceof IteratorMappingNodeFeatureValue) {
			return '''«getIteratorVariableName(constExpr.stream)».get«constExpr.node.name.toFirstUpper»().«parseFeatureExpression(constExpr.featureExpression)»'''
		} else if(constExpr instanceof IteratorMappingNodeValue) {
			throw new UnsupportedOperationException("Complex objects are not allowed within arithmetic expressions.");
		} else if(constExpr instanceof IteratorTypeFeatureValue){
			return '''«getIteratorVariableName(constExpr.stream)».«parseFeatureExpression(constExpr.featureExpression)»'''
		} else {
			//CASE: IteratorTypeValue
			throw new UnsupportedOperationException("Complex objects are not allowed within arithmetic expressions.");
		}
	}
	
	def String transformOperation(BinaryArithmeticExpression operation, boolean isConstant) {
		switch(operation.operator) {
			case ADD: {
				return '''«parseArithmeticExpression(operation.lhs, isConstant)» + «parseArithmeticExpression(operation.rhs, isConstant)»'''
			}
			case DIVIDE: {
				return '''«parseArithmeticExpression(operation.lhs, isConstant)» / «parseArithmeticExpression(operation.rhs, isConstant)»'''
			}
			case MULTIPLY: {
				return '''«parseArithmeticExpression(operation.lhs, isConstant)» * «parseArithmeticExpression(operation.rhs, isConstant)»'''
			}
			case POW: {
				return '''Math.pow(«parseArithmeticExpression(operation.lhs, isConstant)», «parseArithmeticExpression(operation.rhs, isConstant)»'''
			}
			case SUBTRACT: {
				return '''«parseArithmeticExpression(operation.lhs, isConstant)» - «parseArithmeticExpression(operation.rhs, isConstant)»'''
			}
		}
	}
	
	def String transformOperation(UnaryArithmeticExpression operation, boolean isConstant) {
		switch(operation.operator) {
			case ABSOLUTE: {
				return '''Math.abs(«parseArithmeticExpression(operation.expression, isConstant)»)'''
			}
			case BRACKET: {
				return '''(«parseArithmeticExpression(operation.expression, isConstant)»)'''
			}
			case COSINE: {
				return '''Math.cos(«parseArithmeticExpression(operation.expression, isConstant)»)'''
			}
			case NEGATE: {
				return '''-«parseArithmeticExpression(operation.expression, isConstant)»'''
			}
			case SINE: {
				return '''Math.sin(«parseArithmeticExpression(operation.expression, isConstant)»)'''
			}
			case SQRT: {
				return '''Math.sqrt(«parseArithmeticExpression(operation.expression, isConstant)»)'''
			}
			
		}
	}
	
	def String parseBooleanExpression(BoolExpression expr) {
		if(expr instanceof BoolBinaryExpression) {
			return transformOperation(expr)
		} else if(expr instanceof BoolUnaryExpression) {
			return transformOperation(expr)
		} else if(expr instanceof BoolLiteral) {
			return '''«(expr.literal)?"true":"false"»''';
		} else if(expr instanceof RelationalExpression) {
			return parseRelationalExpression(expr);
		} else if(expr instanceof BoolStreamExpression) {
			switch(expr.operator) {
				case EXISTS: {
					return '''«parseStreamExpression(expr.stream)».isPresent()'''
				}
				case NOTEXISTS: {
					return '''!«parseStreamExpression(expr.stream)».isPresent()'''
				}
			}
		} else {
			val value = expr as BoolValue;
			return parseConstValueExpression(value.getValue());
		}
	}
	
	def String transformOperation(BoolBinaryExpression operation) {
		switch(operation.operator) {
			case AND: {
				return '''«parseBooleanExpression(operation.lhs)» && «parseBooleanExpression(operation.rhs)»'''
			}
			case OR: {
				return '''«parseBooleanExpression(operation.lhs)» || «parseBooleanExpression(operation.rhs)»'''			
			}
		}
	}
	
	def String transformOperation(BoolUnaryExpression operation) {
		switch(operation.operator) {
			case NOT: {
				return '''!«parseBooleanExpression(operation.expression)»'''
			}
		}
	}
	
	def String parseRelationalExpression(RelationalExpression expr) {
		switch(expr.operator) {
			case EQUAL: {
				return '''«parseConstArithmeticExpression(expr.lhs)» == «parseConstArithmeticExpression(expr.lhs)»'''
			}
			case GREATER: {
				return '''«parseConstArithmeticExpression(expr.lhs)» > «parseConstArithmeticExpression(expr.lhs)»'''
			}
			case GREATER_OR_EQUAL: {
				return '''«parseConstArithmeticExpression(expr.lhs)» >= «parseConstArithmeticExpression(expr.lhs)»'''
			}
			case LESS: {
				return '''«parseConstArithmeticExpression(expr.lhs)» < «parseConstArithmeticExpression(expr.lhs)»'''
			}
			case LESS_OR_EQUAL: {
				return '''«parseConstArithmeticExpression(expr.lhs)» <= «parseConstArithmeticExpression(expr.lhs)»'''
			}
			case NOT_EQUAL: {
				return '''«parseConstArithmeticExpression(expr.lhs)» != «parseConstArithmeticExpression(expr.lhs)»'''
			}
			
		}
	}

	
	def String parseStreamExpression(StreamExpression expr) {
		if(expr.current instanceof StreamFilterOperation) {
			if(expr.child === null) {
				return '''filter(«getIteratorVariableName(expr)» -> «parseBooleanExpression((expr.current as StreamFilterOperation).predicate)»)'''
			} else {
				return '''filter(«getIteratorVariableName(expr)» -> «parseBooleanExpression((expr.current as StreamFilterOperation).predicate)»)
				.«parseStreamExpression(expr.child)»'''
			}
		} else {
			val selectOp = expr.current as StreamSelectOperation
			imports.add(data.classToPackage.getImportsForType(selectOp.type))
			if(expr.child === null) {
				return '''filter(«getIteratorVariableName(expr)» -> «getIteratorVariableName(expr)» instanceof «selectOp.type.name»)
				.map(«getIteratorVariableName(expr)» -> («selectOp.type.name») «getIteratorVariableName(expr)»)'''
			} else {
				return '''filter(«getIteratorVariableName(expr)» -> «getIteratorVariableName(expr)» instanceof «selectOp.type.name»)
				.map(«getIteratorVariableName(expr)» -> («selectOp.type.name») «getIteratorVariableName(expr)»)
				.«parseStreamExpression(expr.child)»'''
			}
		}
	}
	
	def String parseFeatureExpression(FeatureExpression expr) {
		if(expr.child === null) {
			//TODO: Watch out for boolean attributes -> isXXXX() instead of getXXXX()
			return '''get«expr.current.feature.name.toFirstUpper»()'''
		} else {
			return '''get«expr.current.feature.name.toFirstUpper»().«parseFeatureExpression(expr.child)»'''
		}
	}

	def String getIteratorVariableName(SetOperation iterator) {
		var itrName = iterator2variableName.get(iterator)
		if(itrName === null) {
			itrName = ""+Character.forDigit(Character.digit('a',0)+iterator2variableName.size,0)
			iterator2variableName.put(iterator, itrName);
		}
		return itrName;
	}
	
}