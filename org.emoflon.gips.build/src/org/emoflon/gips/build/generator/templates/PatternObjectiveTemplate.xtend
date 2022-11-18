package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.build.transformation.helper.ArithmeticExpressionType
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.MappingSumExpression
import org.emoflon.gips.intermediate.GipsIntermediate.PatternObjective
import org.emoflon.gips.intermediate.GipsIntermediate.TypeSumExpression
import org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression
import org.emoflon.gips.intermediate.GipsIntermediate.VariableSet
import org.emoflon.gips.intermediate.GipsIntermediate.ContextSumExpression
import org.emoflon.gips.intermediate.GipsIntermediate.PatternSumExpression
import org.emoflon.gips.build.generator.GipsApiData

class PatternObjectiveTemplate extends ObjectiveTemplate<PatternObjective> {

	new(GipsApiData data, PatternObjective context) {
		super(data, context)
	}

	override init() {
		packageName = data.gipsObjectivePkg
		className = data.objective2objectiveClassName.get(context)
		fqn = packageName + "." + className;
		filePath = data.gipsObjectivePkgPath + "/" + className + ".java"
		imports.add("java.util.List")
		imports.add("java.util.LinkedList")
		imports.add("org.emoflon.gips.core.GipsEngine")
		imports.add("org.emoflon.gips.core.gt.GipsPatternObjective")
		imports.add("org.emoflon.gips.core.ilp.ILPTerm")
		imports.add("org.emoflon.gips.core.ilp.ILPConstant")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.PatternObjective")
		imports.add(data.gipsApiPkg+"."+data.gipsApiClassName)
		imports.add(data.matchPackage+"."+data.pattern2matchClassName.get(context.pattern.pattern))
		if(data.pattern2rule.containsKey(context.pattern.pattern))
			imports.add(data.rulePackage+"."+data.pattern2patternClassName.get(context.pattern.pattern))
		else
			imports.add(data.patternPackage+"."+data.pattern2patternClassName.get(context.pattern.pattern))
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
public class «className» extends GipsPatternObjective<«data.gipsApiClassName»<?>, «data.pattern2matchClassName.get(context.pattern.pattern)», «data.pattern2patternClassName.get(context.pattern.pattern)»>{
	public «className»(final «data.gipsApiClassName»<?> engine, final PatternObjective objective, final «data.pattern2patternClassName.get(context.pattern.pattern)» pattern) {
		super(engine, objective, pattern);
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
protected void buildTerms(final «data.pattern2matchClassName.get(context.pattern.pattern)» context) {
	«FOR instruction : builderMethodCalls»
	«instruction»
	«ENDFOR»
}
		'''
	}
	
	override generateIteratingBuilder(ValueExpression expr) {
		if(expr instanceof MappingSumExpression) {
			return generateForeignBuilder(expr)
		} else if(expr instanceof ContextSumExpression) {
			return generateBuilder(expr)
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
			return parseExpression(expr, ExpressionContext.varConstraint)
		}
	}
	
	override String getContextVariable(VariableSet variable) {
		throw new UnsupportedOperationException("Mapping context access is not possible within a pattern context.")
	}
	
	override generateBuilder(BinaryArithmeticExpression expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		val method = '''
	protected double «methodName»(final «data.pattern2matchClassName.get(context.pattern.pattern)» context) {
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
	protected double «methodName»(final «data.pattern2matchClassName.get(context.pattern.pattern)» context) {
		return «parseExpression(expr, ExpressionContext.varConstraint)»;
	}
		'''
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	override String generateBuilder(MappingSumExpression expr) {
		throw new UnsupportedOperationException("Mapping context access is not possible within a pattern context.")
	}
	
	override String generateForeignBuilder(MappingSumExpression expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		imports.add(data.gipsMappingPkg+"."+data.mapping2mappingClassName.get(expr.mapping))
		imports.add("java.util.stream.Collectors")
		val method = '''
	protected void «methodName»(final «data.pattern2matchClassName.get(context.pattern.pattern)» context) {
		for(«data.mapping2mappingClassName.get(expr.mapping)» «getIteratorVariableName(expr)» : engine.getMapper("«expr.mapping.name»").getMappings().values().parallelStream()
			.map(mapping -> («data.mapping2mappingClassName.get(expr.mapping)») mapping)
			«getFilterExpr(expr.filter, ExpressionContext.varStream)».collect(Collectors.toList())) {
			terms.add(new ILPTerm(«getIteratorVariableName(expr)», (double)«parseExpression(expr.expression, ExpressionContext.varConstraint)»));
		}
	}
		'''
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	override generateForeignBuilder(TypeSumExpression expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		helper.addImportForType(expr.type.type)
		val method = '''
	protected void «methodName»(final «data.pattern2matchClassName.get(context.pattern.pattern)» context) {
		double constant = indexer.getObjectsOfType("«expr.type.type.name»").parallelStream()
					.map(type -> («expr.type.type.name») type)
					«getFilterExpr(expr.filter, ExpressionContext.constStream)»
					.map(«getIteratorVariableName(expr)» -> «parseExpression(expr.expression, ExpressionContext.constConstraint)»)
					.reduce(0.0, (sum, value) -> sum + value);
					
		constantTerms.add(new ILPConstant(constant));		
	}
		'''
		
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	override generateForeignBuilder(PatternSumExpression expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		imports.add(data.matchPackage+"."+data.pattern2matchClassName.get(expr.pattern))
		val method = '''
	protected void «methodName»(final «data.pattern2matchClassName.get(context.pattern.pattern)» context) {
		double constant = engine.getEMoflonAPI().«expr.pattern.name»().findMatches(false).parallelStream()
					«getFilterExpr(expr.filter, ExpressionContext.constStream)»
					.map(«getIteratorVariableName(expr)» -> «parseExpression(expr.expression, ExpressionContext.constConstraint)»)
					.reduce(0.0, (sum, value) -> sum + value);
								
		constantTerms.add(new ILPConstant(constant));	
	}
		'''
		
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	override String generateBuilder(ContextSumExpression expr) {
		throw new UnsupportedOperationException("Ilp term may not be constant.")
	}
	

	
}