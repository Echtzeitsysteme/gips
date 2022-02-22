package org.emoflon.roam.build.generator.templates

import org.emoflon.roam.build.generator.TemplateData
import org.emoflon.roam.build.transformation.helper.RoamTransformationUtils
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNode
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNodeFeatureValue
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingValue
import org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeFeatureValue
import org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingFeatureValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeFeatureValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingValue
import org.emoflon.roam.intermediate.RoamIntermediate.MappingConstraint
import org.emoflon.roam.intermediate.RoamIntermediate.MappingSumExpression
import org.emoflon.roam.intermediate.RoamIntermediate.ObjectiveFunctionValue
import org.emoflon.roam.intermediate.RoamIntermediate.TypeSumExpression
import org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticExpression
import org.emoflon.roam.intermediate.RoamIntermediate.ValueExpression
import org.emoflon.roam.build.transformation.helper.ArithmeticExpressionType

class MappingConstraintTemplate extends ConstraintTemplate<MappingConstraint> {

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
	
	«FOR methods : builderMethodDefinitions.values»
	«methods»
	«ENDFOR»
}'''
	}
	
	override String generateComplexConstraint(ArithmeticExpression constExpr, ArithmeticExpression dynamicExpr) {
		return '''
@Override
protected double buildConstantTerm(final «data.mapping2mappingClassName.get(context.mapping)» context) {
	return «generateConstTermBuilder(constExpr)»;
}
	
@Override
protected List<ILPTerm<Integer, Double>> buildVariableTerms(final «data.mapping2mappingClassName.get(context.mapping)» context) {
	List<ILPTerm<Integer, Double>> terms = new LinkedList<>();
	«generateVariableTermBuilder(dynamicExpr)»
	return terms;
}

«FOR generator : builderMethodDefinitions.values»
«generator»
«ENDFOR»
		'''
	}
	
override generateVariableTermBuilder(ValueExpression expr) {
		if(expr instanceof MappingSumExpression) {
			if(expr.mapping == context.mapping) {
				generateBuilderMethod(expr);
				return '''
			«builderMethods.get(expr)»(context);'''
			} else {
				generateForeignBuilderMethod(expr)
				'''
			«builderMethods.get(expr)»();'''
			}
			
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
	
	override void generateBuilderMethod(UnaryArithmeticExpression expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		val method = '''
	protected ILPTerm<Integer, Double> «methodName»(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		return null;
	}
		'''
		builderMethodDefinitions.put(expr, method)
	}
	
	override void generateBuilderMethod(MappingSumExpression expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		val method = '''
	protected void «methodName»(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		for(«data.mapping2mappingClassName.get(expr.mapping)» «getIteratorVariableName(expr)» : engine.getMapper(«expr.mapping.name»).getMappings().values().parallelStream()
			.«parseExpression(expr.filter, ExpressionContext.varStream)».collect(Collectors.toList())) {
			double constValue = «parseExpression(expr.expression, ExpressionContext.varConstraint)
			»;
			ILPTerm<Integer, Double> term = new ILPTerm<Integer, Double>(«getIteratorVariableName(expr)», constValue);
			terms.add(term);
		}
	}
		'''
		
		builderMethodDefinitions.put(expr, method)
	}
	
	override void generateBuilderMethod(TypeSumExpression expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		val method = '''
	protected void «methodName»(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		for(«expr.type.type.name» «getIteratorVariableName(expr)» : indexer.getObjectsOfType("«expr.type.name»").parallelStream()
			.«parseExpression(expr.filter, ExpressionContext.varStream)».collect(Collectors.toList())) {
			double constValue = «parseExpression(expr.expression, ExpressionContext.varConstraint)»;
			ILPTerm<Integer, Double> term = new ILPTerm<Integer, Double>(«getIteratorVariableName(expr)», constValue);
			terms.add(term);
		}
		return new ILPTerm<Integer, Double>(context, 1.0);
	}
		'''
		builderMethodDefinitions.put(expr, method)
	}
	
	def void generateBuilderMethod(ContextMappingValue expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		val method = '''
	protected ILPTerm<Integer, Double> «methodName»(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		return new ILPTerm<Integer, Double>(context, 1.0);
	}
		'''
		builderMethodDefinitions.put(expr, method)
	}
	
	def void generateBuilderMethod(ContextMappingNodeFeatureValue expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		val method = '''
	protected ILPTerm<Integer, Double> «methodName»(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		return new ILPTerm<Integer, Double>(context, context.«parseFeatureExpression(expr.featureExpression)»);
	}
		'''
		builderMethodDefinitions.put(expr, method)
	}
	

	
}