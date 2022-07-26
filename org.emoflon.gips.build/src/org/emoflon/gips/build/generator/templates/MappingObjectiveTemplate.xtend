package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.build.transformation.helper.ArithmeticExpressionType
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNodeFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingValue
import org.emoflon.gips.intermediate.GipsIntermediate.MappingObjective
import org.emoflon.gips.intermediate.GipsIntermediate.MappingSumExpression
import org.emoflon.gips.intermediate.GipsIntermediate.TypeSumExpression
import org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression
import org.emoflon.gips.intermediate.GipsIntermediate.VariableSet
import org.emoflon.gips.intermediate.GipsIntermediate.ContextSumExpression
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping
import org.emoflon.gips.intermediate.GipsIntermediate.PatternSumExpression

class MappingObjectiveTemplate extends ObjectiveTemplate<MappingObjective> {

	new(TemplateData data, MappingObjective context) {
		super(data, context)
	}

	override init() {
		packageName = data.apiData.gipsObjectivePkg
		className = data.objective2objectiveClassName.get(context)
		fqn = packageName + "." + className;
		filePath = data.apiData.gipsObjectivePkgPath + "/" + className + ".java"
		imports.add("java.util.List")
		imports.add("java.util.LinkedList")
		imports.add("org.emoflon.gips.core.GipsEngine")
		imports.add("org.emoflon.gips.core.GipsMappingObjective")
		imports.add("org.emoflon.gips.core.ilp.ILPTerm")
		imports.add("org.emoflon.gips.core.ilp.ILPConstant")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.MappingObjective")
		imports.add(data.apiData.gipsApiPkg+"."+data.gipsApiClassName)
		imports.add(data.apiData.gipsMappingPkg+"."+data.mapping2mappingClassName.get(context.mapping))
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
public class «className» extends GipsMappingObjective<«data.gipsApiClassName», «data.mapping2mappingClassName.get(context.mapping)»>{
	public «className»(final «data.gipsApiClassName» engine, final MappingObjective objective) {
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
protected void buildTerms(final «data.mapping2mappingClassName.get(context.mapping)» context) {
	«FOR instruction : builderMethodCalls»
	«instruction»
	«ENDFOR»
}
		'''
	}
	
	override generateIteratingBuilder(ValueExpression expr) {
		if(expr instanceof MappingSumExpression) {
			if(expr.mapping == context.mapping) {
				return generateBuilder(expr)
			} else {
				throw new UnsupportedOperationException("Referencing other mapping variables from within a mapping context is not allowed.")
			}
		} else if(expr instanceof ContextSumExpression) {
			if(expr.context == context.mapping) {
				return generateBuilder(expr)
			} else {
				throw new UnsupportedOperationException("Referencing other mapping variables from within a mapping context is not allowed.")
			}
		} else if (expr instanceof TypeSumExpression) {
			return generateForeignBuilder(expr)
		} else if (expr instanceof PatternSumExpression) {
			return generateForeignBuilder(expr)
		} else {
			throw new UnsupportedOperationException("Unknown sum expression type.")
		}
	}
	
	override generateConstantBuilder(ValueExpression expr, ArithmeticExpressionType type) {
		if(type == ArithmeticExpressionType.constant) {
			return parseExpression(expr, ExpressionContext.constConstraint)
		} else {
			if(expr instanceof ContextMappingNodeFeatureValue) {
				return generateBuilder(expr)
			} else if(expr instanceof ContextMappingValue) {
				return generateBuilder(expr)
			} else {
				return parseExpression(expr, ExpressionContext.varConstraint)
			}
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
		imports.add(data.apiData.gipsMappingPkg+"."+data.mapping2mappingClassName.get(expr.mapping))
		imports.add("java.util.stream.Collectors")
		val method = '''
	protected void «methodName»(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		for(«data.mapping2mappingClassName.get(expr.mapping)» «getIteratorVariableName(expr)» : engine.getMapper("«expr.mapping.name»").getMappings().values().parallelStream()
			.map(mapping -> («data.mapping2mappingClassName.get(expr.mapping)») mapping)
			«getFilterExpr(expr.filter, ExpressionContext.varStream)».collect(Collectors.toList())) {
			ILPTerm term = new ILPTerm(context, (double)«parseExpression(expr.expression, ExpressionContext.varConstraint)»);
			terms.add(term);
		}
	}
		'''
		
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	override String generateBuilder(ContextSumExpression expr) {
		if(!(expr.context instanceof Mapping && expr.context == context.mapping))
			throw new UnsupportedOperationException("Wrong context type!")
		
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		imports.add(data.apiData.gipsMappingPkg+"."+data.mapping2mappingClassName.get(expr.context))
		val method = '''
	protected void «methodName»(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		double constant = context.get«expr.node.name.toFirstUpper»().«parseFeatureExpression(expr.feature)».parallelStream()
					«getFilterExpr(expr.filter, ExpressionContext.varStream)»
					.map(«getIteratorVariableName(expr)» -> «parseExpression(expr.expression, ExpressionContext.varConstraint)»)
					.reduce(0.0, (sum, value) -> sum + value);
								
		terms.add(new ILPTerm(context, constant));
	}
		'''
		
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	override String generateForeignBuilder(MappingSumExpression expr) {
		throw new UnsupportedOperationException("Referencing other mapping variables from within a mapping context is not allowed.")
	}
	
	def String generateBuilder(ContextMappingValue expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		val method = '''
	protected double «methodName»(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		return 1.0;
	}
		'''
		builderMethodDefinitions.put(expr, method)
		return '''«methodName»''';
	}
	
	def String generateBuilder(ContextMappingNodeFeatureValue expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		val method = '''
	protected double «methodName»(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		return (double)«parseExpression(expr, ExpressionContext.varConstraint)»;
	}
		'''
		builderMethodDefinitions.put(expr, method)
		return '''«methodName»''';
	}
	
	override generateForeignBuilder(TypeSumExpression expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		imports.add(data.classToPackage.getImportsForType(expr.type.type))
		val method = '''
	protected void «methodName»(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		double constant = indexer.getObjectsOfType("«expr.type.type.name»").parallelStream()
					.map(type -> («expr.type.type.name») type)
					«getFilterExpr(expr.filter, ExpressionContext.varStream)»
					.map(«getIteratorVariableName(expr)» -> «parseExpression(expr.expression, ExpressionContext.varConstraint)»)
					.reduce(0.0, (sum, value) -> sum + value);
								
		terms.add(new ILPTerm(context, constant));
	}
		'''
		
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	override generateForeignBuilder(PatternSumExpression expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		imports.add(data.apiData.matchesPkg+"."+data.pattern2matchClassName.get(expr.pattern))
		val method = '''
	protected void «methodName»(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		double constant = engine.getEMoflonAPI().«expr.pattern.name»().findMatches(false).parallelStream()
					«getFilterExpr(expr.filter, ExpressionContext.varStream)»
					.map(«getIteratorVariableName(expr)» -> «parseExpression(expr.expression, ExpressionContext.varConstraint)»)
					.reduce(0.0, (sum, value) -> sum + value);
								
		terms.add(new ILPTerm(context, constant));
	}
		'''
		
		builderMethodDefinitions.put(expr, method)
		return methodName
	}

	
}