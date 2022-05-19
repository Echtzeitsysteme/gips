package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.build.transformation.helper.ArithmeticExpressionType
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNode
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNodeFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingValue
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNode
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternValue
import org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeValue
import org.emoflon.gips.intermediate.GipsIntermediate.MappingSumExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveFunctionValue
import org.emoflon.gips.intermediate.GipsIntermediate.TypeConstraint
import org.emoflon.gips.intermediate.GipsIntermediate.TypeSumExpression
import org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression
import org.emoflon.gips.intermediate.GipsIntermediate.VariableSet
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNodeFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.ContextSumExpression
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression
import org.emoflon.gips.intermediate.GipsIntermediate.BoolValueExpression
import org.emoflon.gips.intermediate.GipsIntermediate.PatternSumExpression
import javax.management.relation.RelationException

class TypeConstraintTemplate extends ConstraintTemplate<TypeConstraint> {
	
	new(TemplateData data, TypeConstraint context) {
		super(data, context)
	}
	
		override init() {
		packageName = data.apiData.gipsConstraintPkg
		className = data.constraint2constraintClassName.get(context)
		fqn = packageName + "." + className;
		filePath = data.apiData.gipsConstraintPkgPath + "/" + className + ".java"
		imports.add("java.util.List")
		imports.add("java.util.LinkedList")
		imports.add("java.util.Collections");
		imports.add("org.eclipse.emf.ecore.EClass")
		imports.add("org.eclipse.emf.ecore.EObject")
		imports.add("org.emoflon.gips.core.GipsEngine")
		imports.add("org.emoflon.gips.core.GipsMapping")
		imports.add("org.emoflon.gips.core.GipsTypeConstraint")
		imports.add("org.emoflon.gips.core.ilp.ILPTerm")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.TypeConstraint")
		imports.add(data.apiData.gipsApiPkg+"."+data.gipsApiClassName)
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

	override String generateVariableClassContent(RelationalExpression relExpr) {
		return '''
public class «className» extends GipsTypeConstraint<«data.gipsApiClassName», «context.modelType.type.name»> {
	public «className»(final «data.gipsApiClassName» engine, final TypeConstraint constraint) {
		super(engine, constraint);
	}
	«IF GipsTransformationUtils.isConstantExpression( relExpr.lhs) == ArithmeticExpressionType.constant»
	«generateComplexConstraint(relExpr.lhs, relExpr.rhs)»
	«ELSE»
	«generateComplexConstraint(relExpr.rhs, relExpr.lhs)»
	«ENDIF»
	
	@Override
	protected double buildConstantLhs(final «context.modelType.type.name» context) {
		throw new UnsupportedOperationException("Constraint has an lhs that contains ilp variables.");
	}
	
	@Override
	protected boolean buildConstantExpression(final «context.modelType.type.name» context) {
		throw new UnsupportedOperationException("Constraint has no constant boolean expression.");
	}
		
	«FOR methods : builderMethodDefinitions.values»
	«methods»
	«ENDFOR»
}'''
	}
	
	override String generateConstantClassContent(RelationalExpression relExpr) {
		return '''
public class «className» extends GipsTypeConstraint<«data.gipsApiClassName», «context.modelType.type.name»> {
	public «className»(final «data.gipsApiClassName» engine, final TypeConstraint constraint) {
		super(engine, constraint);
	}
	
	@Override
	protected double buildConstantLhs(final «context.modelType.type.name» context) {
		return «generateConstTermBuilder(relExpr.lhs)»;
	}
	
	@Override
	protected double buildConstantRhs(final «context.modelType.type.name» context) {
		return «generateConstTermBuilder(relExpr.rhs)»;
	}
	
	@Override
	protected List<ILPTerm<Integer, Double>> buildVariableLhs(final «context.modelType.type.name» context) {
		throw new UnsupportedOperationException("Constraint has no lhs containing ilp variables.");
	}
	
	@Override
	protected boolean buildConstantExpression(final «context.modelType.type.name» context) {
		throw new UnsupportedOperationException("Constraint has no constant boolean expression.");
	}
		
	«FOR methods : builderMethodDefinitions.values»
	«methods»
	«ENDFOR»
}'''
	}
	
	override String generateConstantClassContent(BoolValueExpression boolExpr) {
		return '''
public class «className» extends GipsTypeConstraint<«data.gipsApiClassName», «context.modelType.type.name»> {
	public «className»(final «data.gipsApiClassName» engine, final TypeConstraint constraint) {
		super(engine, constraint);
	}
	
	@Override
	protected double buildConstantLhs(final «context.modelType.type.name» context) {
		throw new UnsupportedOperationException("Constraint has no relational expression.");
	}
	
	@Override
	protected double buildConstantRhs(final «context.modelType.type.name» context) {
		throw new UnsupportedOperationException("Constraint has no relational expression.");
	}
	
	@Override
	protected List<ILPTerm<Integer, Double>> buildVariableLhs(final «context.modelType.type.name» context) {
		throw new UnsupportedOperationException("Constraint has no lhs containing ilp variables.");
	}
	
	@Override
	protected boolean buildConstantExpression(final «context.modelType.type.name» context) {
		return «parseExpression(boolExpr, ExpressionContext.constConstraint)»
	}
		
	«FOR methods : builderMethodDefinitions.values»
	«methods»
	«ENDFOR»
}'''
	}
	
	override String generateComplexConstraint(ArithmeticExpression constExpr, ArithmeticExpression dynamicExpr) {
		generateVariableTermBuilder(dynamicExpr)
		return '''
@Override
protected double buildConstantRhs(final «context.modelType.type.name» context) {
	return «generateConstTermBuilder(constExpr)»;
}
	
@Override
protected List<ILPTerm<Integer, Double>> buildVariableLhs(final «context.modelType.type.name» context) {
	List<ILPTerm<Integer, Double>> terms = Collections.synchronizedList(new LinkedList<>());
	«FOR instruction : builderMethodCalls»
	«instruction»
	«ENDFOR»
	return terms;
}
		'''
	}
	
	override generateBuilder(ValueExpression expr) {
		if(expr instanceof MappingSumExpression) {
				val builderMethodName = generateForeignBuilder(expr)
				val instruction = '''«builderMethodName»(terms, context);'''
				builderMethodCalls.add(instruction)
		} else if(expr instanceof TypeSumExpression) {
			val builderMethodName = generateForeignBuilder(expr)
			val instruction = '''«builderMethodName»(terms, context);'''
				builderMethodCalls.add(instruction)
		} else if(expr instanceof PatternSumExpression) {
			val builderMethodName = generateForeignBuilder(expr)
			val instruction = '''«builderMethodName»(terms, context);'''
				builderMethodCalls.add(instruction)
		} else if(expr instanceof ContextSumExpression) {
			generateBuilder(expr);
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
	
	override generateBuilder(ContextSumExpression expr) {
		throw new UnsupportedOperationException("Ilp term may not be constant.")
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
		imports.add(data.apiData.gipsMappingPkg+"."+data.mapping2mappingClassName.get(expr.mapping))
		imports.add("java.util.stream.Collectors")
		val method = '''
	protected void «methodName»(final List<ILPTerm<Integer, Double>> terms, final «context.modelType.type.name» context) {
		for(«data.mapping2mappingClassName.get(expr.mapping)» «getIteratorVariableName(expr)» : engine.getMapper("«expr.mapping.name»").getMappings().values().parallelStream()
			.map(mapping -> («data.mapping2mappingClassName.get(expr.mapping)») mapping)
			«getFilterExpr(expr.filter, ExpressionContext.varStream)».collect(Collectors.toList())) {
			terms.add(new ILPTerm<Integer, Double>(«getIteratorVariableName(expr)», (double)«parseExpression(expr.expression, ExpressionContext.varConstraint)»));
		}
	}
		'''
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	override generateForeignBuilder(TypeSumExpression expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		imports.add("java.util.stream.Collectors")
		imports.add(data.classToPackage.getImportsForType(expr.type.type))
		var method = "";
		
		if(context.isConstant && context.expression instanceof RelationException) {
			method = '''
	protected double «methodName»(final List<ILPTerm<Integer, Double>> terms, final «context.modelType.type.name» context) {
		return indexer.getObjectsOfType("«expr.type.type.name»").parallelStream()
			.map(type -> («expr.type.type.name») type)
			«getFilterExpr(expr.filter, ExpressionContext.constStream)»
			.map(type -> (double)«parseExpression(expr.expression, ExpressionContext.constConstraint)»)
			.reduce(0.0, (sum, value) -> sum + value);
	}
		'''
		} else if(!context.isConstant && context.expression instanceof RelationException) {
			method = '''
	protected void «methodName»(final List<ILPTerm<Integer, Double>> terms, final «context.modelType.type.name» context) {
		for(«expr.type.type.name» «getIteratorVariableName(expr)» : indexer.getObjectsOfType("«expr.type.type.name»").parallelStream()
			.map(type -> («expr.type.type.name») type)
			«getFilterExpr(expr.filter, ExpressionContext.varStream)».collect(Collectors.toList())) {
			terms.add(new ILPTerm<Integer, Double>(«getIteratorVariableName(expr)», (double)«parseExpression(expr.expression, ExpressionContext.varConstraint)»));
		}
	}
		'''
		} else if(context.isConstant && !(context.expression instanceof RelationException)) {
			method = '''
	protected boolean «methodName»(final List<ILPTerm<Integer, Double>> terms, final «context.modelType.type.name» context) {
		throw new UnsupportedOperationException("TODO: Implement stream-boolean expressions at root level.");
	}
		''' 
		} else {
			throw new UnsupportedOperationException("Boolean values can not be translated into ILP constraints.");
		}
		
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	override generateForeignBuilder(PatternSumExpression expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		imports.add("java.util.stream.Collectors")
		imports.add(data.apiData.matchesPkg+"."+data.pattern2matchClassName.get(expr.pattern))
		var method = "";
		
		if(context.isConstant && context.expression instanceof RelationException) {
			
			method = '''
	protected double «methodName»(final List<ILPTerm<Integer, Double>> terms, final «context.modelType.type.name» context) {
		return engine.getEMoflonAPI().«expr.pattern.name»().findMatches(false).parallelStream()
			.«getFilterExpr(expr.filter, ExpressionContext.constStream)»
			.map(type -> (double)«parseExpression(expr.expression, ExpressionContext.constStream)»)
			.reduce(0.0, (sum, value) -> sum + value);
	}
		'''
		} else if(!context.isConstant && context.expression instanceof RelationException) {
			
			method = '''
	protected void «methodName»(final List<ILPTerm<Integer, Double>> terms, final «context.modelType.type.name» context) {
		for(«data.pattern2matchClassName.get(expr.pattern)» «getIteratorVariableName(expr)» : engine.getEMoflonAPI().«expr.pattern.name»().findMatches(false).parallelStream()
			«getFilterExpr(expr.filter, ExpressionContext.varStream)».collect(Collectors.toList())) {
			terms.add(new ILPTerm<Integer, Double>(«getIteratorVariableName(expr)», (double)«parseExpression(expr.expression, ExpressionContext.varConstraint)»));
		}
	}
		'''
		} else if(context.isConstant && !(context.expression instanceof RelationException)) {
			method = '''
	protected boolean «methodName»(final List<ILPTerm<Integer, Double>> terms, final «context.modelType.type.name» context) {
		throw new UnsupportedOperationException("TODO: Implement stream-boolean expressions at root level.");
	}
		''' 
		} else {
			throw new UnsupportedOperationException("Boolean values can not be translated into ILP constraints.");
		}
		
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	override generateBuilder(MappingSumExpression expr) {
		throw new UnsupportedOperationException("Mapping context access is not possible within a type context.")
	}

}