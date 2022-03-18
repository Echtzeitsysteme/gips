package org.emoflon.roam.build.generator.templates

import org.emoflon.roam.build.generator.TemplateData
import org.emoflon.roam.build.transformation.helper.ArithmeticExpressionType
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticExpression
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingNodeFeatureValue
import org.emoflon.roam.intermediate.RoamIntermediate.ContextMappingValue
import org.emoflon.roam.intermediate.RoamIntermediate.MappingObjective
import org.emoflon.roam.intermediate.RoamIntermediate.MappingSumExpression
import org.emoflon.roam.intermediate.RoamIntermediate.TypeSumExpression
import org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticExpression
import org.emoflon.roam.intermediate.RoamIntermediate.ValueExpression
import org.emoflon.roam.intermediate.RoamIntermediate.VariableSet

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
		imports.add("org.emoflon.roam.core.ilp.ILPConstant")
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
		} else {
			val tse = expr as TypeSumExpression
			return generateBuilder(tse)
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
		return '''«methodName»''';
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
		return '''«methodName»''';
	}

	
}