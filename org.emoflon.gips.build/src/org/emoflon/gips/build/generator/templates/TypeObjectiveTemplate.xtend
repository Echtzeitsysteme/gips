package org.emoflon.roam.build.generator.templates

import org.emoflon.roam.build.generator.TemplateData
import org.emoflon.roam.build.transformation.helper.ArithmeticExpressionType
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticExpression
import org.emoflon.roam.intermediate.RoamIntermediate.MappingSumExpression
import org.emoflon.roam.intermediate.RoamIntermediate.TypeObjective
import org.emoflon.roam.intermediate.RoamIntermediate.TypeSumExpression
import org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticExpression
import org.emoflon.roam.intermediate.RoamIntermediate.ValueExpression
import org.emoflon.roam.intermediate.RoamIntermediate.VariableSet

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
		imports.add("org.emoflon.roam.core.ilp.ILPConstant")
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
protected void buildTerms(final «context.modelType.type.name» context) {
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