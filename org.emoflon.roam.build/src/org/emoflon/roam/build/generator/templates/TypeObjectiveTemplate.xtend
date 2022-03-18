package org.emoflon.roam.build.generator.templates

import org.emoflon.roam.build.generator.TemplateData
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticExpression
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNode
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNodeFeatureValue
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingValue
import org.emoflon.roam.intermediate.RoamIntermediate.ContextPatternNode
import org.emoflon.roam.intermediate.RoamIntermediate.ContextPatternValue
import org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeFeatureValue
import org.emoflon.roam.intermediate.RoamIntermediate.ContextTypeValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingFeatureValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeFeatureValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingNodeValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorMappingValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorPatternFeatureValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorPatternNodeFeatureValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorPatternNodeValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorPatternValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorTypeFeatureValue
import org.emoflon.roam.intermediate.RoamIntermediate.IteratorTypeValue
import org.emoflon.roam.intermediate.RoamIntermediate.MappingSumExpression
import org.emoflon.roam.intermediate.RoamIntermediate.ObjectiveFunctionValue
import org.emoflon.roam.intermediate.RoamIntermediate.TypeSumExpression
import org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticExpression
import org.emoflon.roam.intermediate.RoamIntermediate.ValueExpression
import org.emoflon.roam.intermediate.RoamIntermediate.VariableSet
import org.emoflon.roam.intermediate.RoamIntermediate.ContextPatternNodeFeatureValue
import org.emoflon.roam.intermediate.RoamIntermediate.TypeObjective

class TypeObjectiveTemplate extends ObjectiveTemplate<TypeObjective> {
	
	new(TemplateData data, TypeObjective context) {
		super(data, context)
	}
	
		override init() {
		packageName = data.apiData.roamObjectivePkg
		className = data.objective2objectiveClassName.get(context)
		fqn = packageName + "." + className;
		filePath = data.apiData.roamObjectivePkgPath + "/" + className + ".java"
		imports.add("java.util.List")
		imports.add("java.util.LinkedList")
		imports.add("org.eclipse.emf.ecore.EClass")
		imports.add("org.eclipse.emf.ecore.EObject")
		imports.add("org.emoflon.roam.core.RoamEngine")
		imports.add("org.emoflon.roam.core.RoamMapping")
		imports.add("org.emoflon.roam.core.RoamTypeObjective")
		imports.add("org.emoflon.roam.core.ilp.ILPTerm")
		imports.add("org.emoflon.roam.intermediate.RoamIntermediate.TypeObjective")
		imports.add(data.classToPackage.getImportsForType(context.modelType.type))
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
public class «className» extends RoamTypeConstraint<«context.modelType.type.name»> {
	public «className»(final RoamEngine engine, final TypeConstraint constraint) {
		super(engine, constraint);
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
protected List<ILPTerm<Integer, Double>> buildTerms(final «context.modelType.type.name» context) {
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
			return generateForeignBuilder(expr)
		} else if(expr instanceof TypeSumExpression) {
			return generateBuilder(expr)
		} else if(expr instanceof ContextTypeFeatureValue) {
			throw new UnsupportedOperationException("Ilp term may not be constant.")
		} else if(expr instanceof ContextTypeValue) {
			throw new UnsupportedOperationException("Ilp term may not be constant.")
		} else if(expr instanceof ContextMappingNodeFeatureValue) {
			throw new UnsupportedOperationException("Mapping context access is not possible within a type context.")
		} else if(expr instanceof ContextMappingNode) {
			throw new UnsupportedOperationException("Mapping context access is not possible within a type context.")
		} else if(expr instanceof ContextMappingValue) {
			throw new UnsupportedOperationException("Mapping context access is not possible within a type context.")
		} else if(expr instanceof ContextPatternNodeFeatureValue) {
			throw new UnsupportedOperationException("Ilp term may not be constant.")
		} else if(expr instanceof ContextPatternNode) {
			throw new UnsupportedOperationException("Ilp term may not be constant.")
		} else if(expr instanceof ContextPatternValue) {
			throw new UnsupportedOperationException("Ilp term may not be constant.")
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
	
	override getContextVariable(VariableSet variable) {
		throw new UnsupportedOperationException("Mapping context access is not possible within a type context.")
	}
	
	override generateBuilder(BinaryArithmeticExpression expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		val method = '''
	protected double «methodName»(final «context.modelType.type.name» context) {
		return «parseExpression(expr, ExpressionContext.varConstraint)»;
	}
		'''
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	override generateBuilder(UnaryArithmeticExpression expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		val method = '''
	protected double «methodName»(final «context.modelType.type.name» context) {
		return «parseExpression(expr, ExpressionContext.varConstraint)»;
	}
		'''
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	override generateForeignBuilder(MappingSumExpression expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		imports.add(data.apiData.roamMappingPkg+"."+data.mapping2mappingClassName.get(expr.mapping))
		imports.add("java.util.stream.Collectors")
		val method = '''
	protected void «methodName»(final List<ILPTerm<Integer, Double>> terms, final «context.modelType.type.name» context) {
		for(«data.mapping2mappingClassName.get(expr.mapping)» «getIteratorVariableName(expr)» : engine.getMapper("«expr.mapping.name»").getMappings().values().parallelStream()
			.map(mapping -> («data.mapping2mappingClassName.get(expr.mapping)») mapping)
			.«parseExpression(expr.filter, ExpressionContext.varStream)».collect(Collectors.toList())) {
			terms.add(new ILPTerm<Integer, Double>(«getIteratorVariableName(expr)», (double)«parseExpression(expr.expression, ExpressionContext.varConstraint)»));
		}
	}
		'''
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	override generateBuilder(TypeSumExpression expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		imports.add("java.util.stream.Collectors")
		val method = '''
	protected void «methodName»(final List<ILPTerm<Integer, Double>> terms, final «context.modelType.type.name» context) {
		for(«expr.type.type.name» «getIteratorVariableName(expr)» : indexer.getObjectsOfType("«expr.type.name»").parallelStream()
			.map(type -> («expr.type.type.name») type)
			.«parseExpression(expr.filter, ExpressionContext.varStream)».collect(Collectors.toList())) {
			terms.add(new ILPTerm<Integer, Double>(«getIteratorVariableName(expr)», (double)«parseExpression(expr.expression, ExpressionContext.varConstraint)»));
		}
	}
		'''
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	override generateBuilder(MappingSumExpression expr) {
		throw new UnsupportedOperationException("Mapping context access is not possible within a type context.")
	}

}