package org.emoflon.roam.build.generator.templates

import org.emoflon.roam.build.generator.TemplateData
import org.emoflon.roam.build.transformation.helper.ArithmeticExpressionType
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticExpression
import org.emoflon.roam.intermediate.RoamIntermediate.MappingSumExpression
import org.emoflon.roam.intermediate.RoamIntermediate.PatternObjective
import org.emoflon.roam.intermediate.RoamIntermediate.TypeSumExpression
import org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticExpression
import org.emoflon.roam.intermediate.RoamIntermediate.ValueExpression
import org.emoflon.roam.intermediate.RoamIntermediate.VariableSet

class PatternObjectiveTemplate extends ObjectiveTemplate<PatternObjective> {

	new(TemplateData data, PatternObjective context) {
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
		imports.add("org.emoflon.roam.core.gt.RoamPatternObjective")
		imports.add("org.emoflon.roam.core.ilp.ILPTerm")
		imports.add("org.emoflon.roam.core.ilp.ILPConstant")
		imports.add("org.emoflon.roam.intermediate.RoamIntermediate.PatternObjective")
		imports.add(data.apiData.matchesPkg+"."+data.pattern2matchClassName.get(context.pattern))
		imports.add(data.apiData.rulesPkg+"."+data.pattern2patternClassName.get(context.pattern))
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
public class «className» extends RoamPatternObjective<«data.pattern2matchClassName.get(context.pattern)», «data.pattern2patternClassName.get(context.pattern)»>{
	public «className»(final RoamEngine engine, final PatternObjective objective, final «data.pattern2patternClassName.get(context.pattern)» pattern) {
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
protected void buildTerms(final «data.pattern2matchClassName.get(context.pattern)» context) {
	«FOR instruction : builderMethodCalls»
	«instruction»
	«ENDFOR»
}
		'''
	}
	
	override generateIteratingBuilder(ValueExpression expr) {
		if(expr instanceof MappingSumExpression) {
			return generateForeignBuilder(expr)
		} else {
			val tse = expr as TypeSumExpression
			return generateBuilder(tse)
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
	protected double «methodName»(final «data.pattern2matchClassName.get(context.pattern)» context) {
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
	protected double «methodName»(final «data.pattern2matchClassName.get(context.pattern)» context) {
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
		imports.add(data.apiData.roamMappingPkg+"."+data.mapping2mappingClassName.get(expr.mapping))
		imports.add("java.util.stream.Collectors")
		val method = '''
	protected void «methodName»(final List<ILPTerm<Integer, Double>> terms, final «data.pattern2matchClassName.get(context.pattern)» context) {
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
	
	override String generateBuilder(TypeSumExpression expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		imports.add("java.util.stream.Collectors")
		val method = '''
	protected void «methodName»(final List<ILPTerm<Integer, Double>> terms, final «data.pattern2matchClassName.get(context.pattern)» context) {
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
	
}