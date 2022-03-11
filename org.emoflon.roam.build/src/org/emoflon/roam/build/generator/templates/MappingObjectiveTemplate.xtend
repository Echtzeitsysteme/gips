package org.emoflon.roam.build.generator.templates

import org.emoflon.roam.build.generator.TemplateData
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
import org.emoflon.roam.intermediate.RoamIntermediate.MappingSumExpression
import org.emoflon.roam.intermediate.RoamIntermediate.ObjectiveFunctionValue
import org.emoflon.roam.intermediate.RoamIntermediate.TypeSumExpression
import org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticExpression
import org.emoflon.roam.intermediate.RoamIntermediate.ValueExpression
import org.emoflon.roam.intermediate.RoamIntermediate.ContextPatternNode
import org.emoflon.roam.intermediate.RoamIntermediate.ContextPatternValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorPatternValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorPatternNodeValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorPatternFeatureValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorPatternNodeFeatureValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorTypeValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorTypeFeatureValue
import org.emoflon.roam.intermediate.RoamIntermediate.VariableSet
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticExpression
import org.emoflon.roam.intermediate.RoamIntermediate.ContextPatternNodeFeatureValue
import org.emoflon.roam.intermediate.RoamIntermediate.MappingObjective

class MappingObjectiveTemplate extends ObjectiveTemplate<MappingObjective> {

	new(TemplateData data, MappingObjective context) {
		super(data, context)
	}

	override init() {
		packageName = data.apiData.roamObjectivePkg
		className = data.objective2objectiveClassName.get(context)
		fqn = packageName + "." + className;
		filePath = data.apiData.roamObjectivePkgPath + "/" + className + ".java"
		imports.add("java.util.List")
		imports.add("java.util.LinkedList")
		imports.add("org.emoflon.roam.core.RoamEngine")
		imports.add("org.emoflon.roam.core.RoamMappingObjective")
		imports.add("org.emoflon.roam.core.ilp.ILPTerm")
		imports.add("org.emoflon.roam.intermediate.RoamIntermediate.MappingObjective")
		imports.add(data.apiData.roamMappingPkg+"."+data.mapping2mappingClassName.get(context.mapping))
	}
	
	override String generatePackageDeclaration() {
		return '''package «packageName»;'''
	}
	
	override String generateImports() {
		return '''«FOR imp : imports»
import «imp»;
«ENDFOR»'''
	}
	
	override String generateClassContent() {
		return '''
public class «className» extends RoamMappingObjective<«data.mapping2mappingClassName.get(context.mapping)»>{
	public «className»(final RoamEngine engine, final MappingObjective objective) {
		super(engine, objective);
	}
	
	«generateObjective(context.expression)»
	
	«FOR methods : builderMethodDefinitions.values»
	«methods»
	«ENDFOR»
}'''
	}
	
	override String generateObjective(ArithmeticExpression expr) {
		generateTermBuilder(expr)
		return '''
	
@Override
protected List<ILPTerm<Integer, Double>> buildTerms(final «data.mapping2mappingClassName.get(context.mapping)» context) {
	List<ILPTerm<Integer, Double>> terms = new LinkedList<>();
	«FOR instruction : builderMethodCalls»
	«instruction»
	«ENDFOR»
	return terms;
}
		'''
	}
	
	override generateBuilder(ValueExpression expr) {
		if(expr instanceof MappingSumExpression) {
			if(expr.mapping == context.mapping) {
				val builderMethodName = generateBuilder(expr)
				val instruction = '''«builderMethodName»(terms, context);'''
				builderMethodCalls.add(instruction)
			} else {
				throw new UnsupportedOperationException("Referencing other mapping variables from within a mapping context is not allowed.")
			}
		} else if(expr instanceof TypeSumExpression) {
			val builderMethodName = generateBuilder(expr)
			val instruction = '''«builderMethodName»(terms, context);'''
				builderMethodCalls.add(instruction)
		} else if(expr instanceof ContextTypeFeatureValue) {
			throw new UnsupportedOperationException("Type context access is not possible within a mapping context.")
		} else if(expr instanceof ContextTypeValue) {
			throw new UnsupportedOperationException("Type context access is not possible within a mapping context.")
		} else if(expr instanceof ContextMappingNodeFeatureValue) {
			val builderMethodName = generateBuilder(expr)
			val instruction = '''«builderMethodName»(context);'''
			builderMethodCalls.add(instruction)
		} else if(expr instanceof ContextMappingNode) {
			throw new UnsupportedOperationException("Ilp term may not contain complex objects.")
		} else if(expr instanceof ContextMappingValue) {
			val builderMethodName = generateBuilder(expr)
			val instruction = '''«builderMethodName»(context);'''
			builderMethodCalls.add(instruction)
		} else if(expr instanceof ContextPatternNodeFeatureValue) {
			throw new UnsupportedOperationException("Pattern context access is not possible within a mapping context.")
		}  else if(expr instanceof ContextPatternNode) {
			throw new UnsupportedOperationException("Pattern context access is not possible within a mapping context.")
		} else if(expr instanceof ContextPatternValue) {
			throw new UnsupportedOperationException("Pattern context access is not possible within a mapping context.")
		} else if(expr instanceof ObjectiveFunctionValue) {
			throw new UnsupportedOperationException("Ilp term may not contain references to objective functions.")
		} else if(expr instanceof IteratorMappingValue) {
			throw new UnsupportedOperationException("Iterators may not be used outside of lambda expressions")
		} else if(expr instanceof IteratorMappingFeatureValue) {
			throw new UnsupportedOperationException("Iterators may not be used outside of lambda expressions")
		} else if(expr instanceof IteratorMappingNodeFeatureValue) {
			throw new UnsupportedOperationException("Iterators may not be used outside of lambda expressions")
		} else if(expr instanceof IteratorMappingNodeValue) {
			throw new UnsupportedOperationException("Iterators may not be used outside of lambda expressions")
		} else if(expr instanceof IteratorPatternValue || expr instanceof IteratorPatternFeatureValue 
				|| expr instanceof IteratorPatternNodeValue || expr instanceof IteratorPatternNodeFeatureValue) {
			throw new UnsupportedOperationException("Iterators may not be used outside of lambda expressions")
		} else if(expr instanceof IteratorTypeValue || expr instanceof IteratorTypeFeatureValue ) {
			throw new UnsupportedOperationException("Iterators may not be used outside of lambda expressions")
		} else {
			// CASE: IteratorTypeValue or IteratorTypeFeatureValue 
			throw new IllegalArgumentException("Unknown value expression Type: " + expr);
		}
	}
	
	override String getContextVariable(VariableSet variable) {
		return '''context'''
	}
	
	override String generateBuilder(BinaryArithmeticExpression expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		val method = '''
	protected double «methodName»(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		return «parseExpression(expr, ExpressionContext.varConstraint)»;
	}
		'''
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	override String generateBuilder(UnaryArithmeticExpression expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		val method = '''
	protected double «methodName»(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		return «parseExpression(expr, ExpressionContext.varConstraint)»;
	}
		'''
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	override String generateBuilder(MappingSumExpression expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		imports.add(data.apiData.roamMappingPkg+"."+data.mapping2mappingClassName.get(expr.mapping))
		imports.add("java.util.stream.Collectors")
		val method = '''
	protected void «methodName»(final List<ILPTerm<Integer, Double>> terms, final «data.mapping2mappingClassName.get(context.mapping)» context) {
		for(«data.mapping2mappingClassName.get(expr.mapping)» «getIteratorVariableName(expr)» : engine.getMapper("«expr.mapping.name»").getMappings().values().parallelStream()
			.map(mapping -> («data.mapping2mappingClassName.get(expr.mapping)») mapping)
			.«parseExpression(expr.filter, ExpressionContext.varStream)».collect(Collectors.toList())) {
			ILPTerm<Integer, Double> term = new ILPTerm<Integer, Double>(context, (double)«parseExpression(expr.expression, ExpressionContext.varConstraint)»);
			terms.add(term);
		}
	}
		'''
		
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	override String generateForeignBuilder(MappingSumExpression expr) {
		throw new UnsupportedOperationException("Referencing other mapping variables from within a mapping context is not allowed.")
	}
	
	override String generateBuilder(TypeSumExpression expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		imports.add("java.util.stream.Collectors")
		val method = '''
	protected void «methodName»(final List<ILPTerm<Integer, Double>> terms, final «data.mapping2mappingClassName.get(context.mapping)» context) {
		for(«expr.type.type.name» «getIteratorVariableName(expr)» : indexer.getObjectsOfType("«expr.type.name»").parallelStream()
			.map(type -> («expr.type.type.name») type)
			.«parseExpression(expr.filter, ExpressionContext.varStream)».collect(Collectors.toList())) {
			terms.add(new ILPTerm<Integer, Double>(context, (double)«parseExpression(expr.expression, ExpressionContext.varConstraint)»));
		}
	}
		'''
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	def String generateBuilder(ContextMappingValue expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		val method = '''
	protected ILPTerm<Integer, Double> «methodName»(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		return new ILPTerm<Integer, Double>(context, 1.0);
	}
		'''
		builderMethodDefinitions.put(expr, method)
		return methodName;
	}
	
	def String generateBuilder(ContextMappingNodeFeatureValue expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		val method = '''
	protected ILPTerm<Integer, Double> «methodName»(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		return new ILPTerm<Integer, Double>(context, (double)«parseExpression(expr, ExpressionContext.varConstraint)»);
	}
		'''
		builderMethodDefinitions.put(expr, method)
		return methodName;
	}

	
}