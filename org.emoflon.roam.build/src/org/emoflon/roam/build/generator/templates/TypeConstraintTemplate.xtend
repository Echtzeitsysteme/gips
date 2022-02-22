package org.emoflon.roam.build.generator.templates

import org.emoflon.roam.build.generator.GeneratorTemplate
import org.emoflon.roam.intermediate.RoamIntermediate.TypeConstraint
import org.emoflon.roam.build.generator.TemplateData
import org.emoflon.roam.build.transformation.helper.RoamTransformationUtils
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticExpression
import org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticExpression
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticLiteral
import org.emoflon.roam.intermediate.RoamIntermediate.DoubleLiteral
import org.emoflon.roam.intermediate.RoamIntermediate.IntegerLiteral
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticValue
import org.emoflon.roam.intermediate.RoamIntermediate.ValueExpression
import org.emoflon.roam.intermediate.RoamIntermediate.MappingSumExpression
import org.emoflon.roam.intermediate.RoamIntermediate.TypeSumExpression
import org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeValue
import org.emoflon.roam.intermediate.RoamIntermediate.ObjectiveFunctionValue
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingValue
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNode
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingFeatureValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeFeatureValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorTypeFeatureValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorTypeValue
import org.emoflon.roam.intermediate.RoamIntermediate.StreamExpression
import org.emoflon.roam.intermediate.RoamIntermediate.FeatureExpression
import org.emoflon.roam.intermediate.RoamIntermediate.SetOperation
import java.util.HashMap
import org.emoflon.roam.intermediate.RoamIntermediate.StreamFilterOperation
import org.emoflon.roam.intermediate.RoamIntermediate.StreamSelectOperation
import org.emoflon.roam.intermediate.RoamIntermediate.BoolExpression
import org.emoflon.roam.intermediate.RoamIntermediate.RelationalExpression
import org.emoflon.roam.intermediate.RoamIntermediate.BoolBinaryExpression
import org.emoflon.roam.intermediate.RoamIntermediate.BoolStreamExpression
import org.emoflon.roam.intermediate.RoamIntermediate.BoolValue
import org.emoflon.roam.intermediate.RoamIntermediate.BoolUnaryExpression
import org.emoflon.roam.intermediate.RoamIntermediate.BoolLiteral
import org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeFeatureValue
import org.emoflon.roam.build.transformation.helper.ArithmeticExpressionType

class TypeConstraintTemplate extends GeneratorTemplate<TypeConstraint> {
	
	new(TemplateData data, TypeConstraint context) {
		super(data, context)
	}
	
		override init() {
		packageName = data.apiData.roamConstraintPkg
		className = data.constraint2constraintClassName.get(context)
		fqn = packageName + "." + className;
		filePath = data.apiData.roamConstraintPkgPath + "/" + className + ".java"
		imports.add("java.util.List")
		imports.add("java.util.LinkedList")
		imports.add("org.eclipse.emf.ecore.EClass")
		imports.add("org.eclipse.emf.ecore.EObject")
		imports.add("org.emoflon.roam.core.RoamEngine")
		imports.add("org.emoflon.roam.core.RoamMapping")
		imports.add("org.emoflon.roam.core.RoamTypeConstraint")
		imports.add("org.emoflon.roam.core.ilp.ILPTerm")
		imports.add("org.emoflon.roam.intermediate.RoamIntermediate.TypeConstraint")
		imports.add(data.classToPackage.getImportsForType(context.modelType.type))
	}
	
	override generate() {
		code = '''package «packageName»;

«FOR imp : imports»
import «imp»;
«ENDFOR»

public class «className» extends RoamTypeConstraint<«context.modelType.type.name»> {
	public «className»(final RoamEngine engine, final TypeConstraint constraint) {
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
protected double buildConstantTerm(final «context.modelType.type.name» context) {
	return «generateConstTermBuilder(constExpr)»;
}
	
@Override
protected List<ILPTerm<Integer, Double>> buildVariableTerms(final «context.modelType.type.name» context) {
	List<ILPTerm<Integer, Double>> terms = new LinkedList<>();
	
	return terms;
}
		'''
	}
	
	def String generateConstTermBuilder(ArithmeticExpression constExpr) {
		return '''«parseConstArithmeticExpression(constExpr)»'''
	}
	
	def String parseConstArithmeticExpression(ArithmeticExpression expr) {
		if(expr instanceof BinaryArithmeticExpression) {
			return transformOperation(expr)
		} else if(expr instanceof UnaryArithmeticExpression) {
			return transformOperation(expr)
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
	
	def String parseConstValueExpression(ValueExpression constExpr) {
		if(constExpr instanceof MappingSumExpression) {
			throw new UnsupportedOperationException("Mapping access not allowed in constant expressions.");
		} else if(constExpr instanceof TypeSumExpression) {
			imports.add(data.classToPackage.getPackage(constExpr.type.type.EPackage))
			return '''indexer.getObjectsOfType(type).stream()
			.«parseStreamExpression(constExpr.filter)»
			.reduce(0, (sum, «getIteratorVariableName(constExpr)») -> {
				sum + «parseConstArithmeticExpression(constExpr.expression)»
			})'''
		} else if(constExpr instanceof ContextTypeFeatureValue) {
			return '''context.«parseFeatureExpression(constExpr.featureExpression)»'''
		} else if(constExpr instanceof ContextTypeValue) {
			return '''context'''
		} else if(constExpr instanceof ObjectiveFunctionValue) {
			throw new UnsupportedOperationException("Objective function value access not allowed in type constraints.");
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
	
	def String transformOperation(BinaryArithmeticExpression operation) {
		switch(operation.operator) {
			case ADD: {
				return '''«parseConstArithmeticExpression(operation.lhs)» + «parseConstArithmeticExpression(operation.rhs)»'''
			}
			case DIVIDE: {
				return '''«parseConstArithmeticExpression(operation.lhs)» / «parseConstArithmeticExpression(operation.rhs)»'''
			}
			case MULTIPLY: {
				return '''«parseConstArithmeticExpression(operation.lhs)» * «parseConstArithmeticExpression(operation.rhs)»'''
			}
			case POW: {
				return '''Math.pow(«parseConstArithmeticExpression(operation.lhs)», «parseConstArithmeticExpression(operation.rhs)»)'''
			}
			case SUBTRACT: {
				return '''«parseConstArithmeticExpression(operation.lhs)» - «parseConstArithmeticExpression(operation.rhs)»'''
			}
		}
	}
	
	def String transformOperation(UnaryArithmeticExpression operation) {
		switch(operation.operator) {
			case ABSOLUTE: {
				return '''Math.abs(«parseConstArithmeticExpression(operation.expression)»)'''
			}
			case BRACKET: {
				return '''(«parseConstArithmeticExpression(operation.expression)»)'''
			}
			case COSINE: {
				return '''Math.cos(«parseConstArithmeticExpression(operation.expression)»)'''
			}
			case NEGATE: {
				return '''-«parseConstArithmeticExpression(operation.expression)»'''
			}
			case SINE: {
				return '''Math.sin(«parseConstArithmeticExpression(operation.expression)»)'''
			}
			case SQRT: {
				return '''Math.sqrt(«parseConstArithmeticExpression(operation.expression)»)'''
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
	val iterator2variableName = new HashMap<SetOperation, String>();
	def String getIteratorVariableName(SetOperation iterator) {
		var itrName = iterator2variableName.get(iterator)
		if(itrName === null) {
			itrName = ""+Character.forDigit(Character.digit('a',0)+iterator2variableName.size,0)
			iterator2variableName.put(iterator, itrName);
		}
		return itrName;
	}
}