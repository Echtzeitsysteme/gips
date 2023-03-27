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
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeValue
import org.emoflon.gips.intermediate.GipsIntermediate.MappingSumExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveFunctionValue
import org.emoflon.gips.intermediate.GipsIntermediate.TypeSumExpression
import org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression
import org.emoflon.gips.intermediate.GipsIntermediate.VariableSet
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNodeFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.ContextSumExpression
import org.emoflon.gips.intermediate.GipsIntermediate.GlobalConstraint
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression
import org.emoflon.gips.intermediate.GipsIntermediate.BoolValueExpression
import org.emoflon.gips.intermediate.GipsIntermediate.PatternSumExpression
import java.util.LinkedList
import java.util.HashMap
import java.util.List
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference
import org.emoflon.gips.intermediate.GipsIntermediate.Variable
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingVariableValue
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingVariablesReference

class GlobalConstraintTemplate extends ConstraintTemplate<GlobalConstraint> {
	
	new(TemplateData data, GlobalConstraint context) {
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
		imports.add("org.emoflon.gips.core.GipsGlobalConstraint")
		imports.add("org.emoflon.gips.core.ilp.ILPTerm")
		imports.add("org.emoflon.gips.core.ilp.ILPConstraint")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.GlobalConstraint")
		imports.add(data.apiData.gipsApiPkg+"."+data.gipsApiClassName)
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
public class «className» extends GipsGlobalConstraint<«data.gipsApiClassName»> {
	public «className»(final «data.gipsApiClassName» engine, final GlobalConstraint constraint) {
		super(engine, constraint);
	}
	«IF GipsTransformationUtils.isConstantExpression( relExpr.lhs) == ArithmeticExpressionType.constant»
	«generateComplexConstraint(relExpr.lhs, relExpr.rhs)»
	«ELSE»
	«generateComplexConstraint(relExpr.rhs, relExpr.lhs)»
	«ENDIF»
	
	@Override
	protected double buildConstantLhs() {
		throw new UnsupportedOperationException("Constraint has an lhs that contains ilp variables.");
	}
	
	@Override
	protected boolean buildConstantExpression() {
		throw new UnsupportedOperationException("Constraint has no constant boolean expression.");
	}
	
	«generateDependencyConstraints()»
	«FOR methods : builderMethodDefinitions.values»
	«methods»
	«ENDFOR»
}'''
	}
	
	override String generateConstantClassContent(RelationalExpression relExpr) {
		if(relExpr.operator != RelationalOperator.OBJECT_EQUAL && 
			relExpr.operator != RelationalOperator.OBJECT_NOT_EQUAL) {
			return '''
public class «className» extends GipsGlobalConstraint<«data.gipsApiClassName»> {
	public «className»(final «data.gipsApiClassName» engine, final GlobalConstraint constraint) {
		super(engine, constraint);
	}
	
	@Override
	protected double buildConstantLhs() {
		return «generateConstTermBuilder(relExpr.lhs)»;
	}
	
	@Override
	protected double buildConstantRhs() {
		return «generateConstTermBuilder(relExpr.rhs)»;
	}
	
	@Override
	protected List<ILPTerm> buildVariableLhs() {
		throw new UnsupportedOperationException("Constraint has no lhs containing ilp variables.");
	}
	
	@Override
	protected boolean buildConstantExpression() {
		throw new UnsupportedOperationException("Constraint has no constant boolean expression.");
	}
	
	«generateDependencyConstraints()»
	«FOR methods : builderMethodDefinitions.values»
	«methods»
	«ENDFOR»
}'''
		} else {
			return '''
public class «className» extends GipsGlobalConstraint<«data.gipsApiClassName»> {
	public «className»(final «data.gipsApiClassName» engine, final GlobalConstraint constraint) {
		super(engine, constraint);
	}
	
	@Override
	protected double buildConstantLhs() {
		throw new UnsupportedOperationException("Constraint has no arithmetic lhs.");
	}
	
	@Override
	protected double buildConstantRhs() {
		throw new UnsupportedOperationException("Constraint has no arithmetic rhs.");
	}
	
	@Override
	protected List<ILPTerm> buildVariableLhs() {
		throw new UnsupportedOperationException("Constraint has no lhs containing ilp variables.");
	}
	
	@Override
	protected boolean buildConstantExpression() {
		return «parseExpression(relExpr, ExpressionContext.constConstraint)»;
	}
	
	«generateDependencyConstraints()»
	«FOR methods : builderMethodDefinitions.values»
	«methods»
	«ENDFOR»
}'''
		}

	}
	
	override String generateConstantClassContent(BoolValueExpression boolExpr) {
		return '''
public class «className» extends GipsGlobalConstraint<«data.gipsApiClassName»> {
	public «className»(final «data.gipsApiClassName» engine, final GlobalConstraint constraint) {
		super(engine, constraint);
	}
	
	@Override
	protected double buildConstantLhs() {
		throw new UnsupportedOperationException("Constraint has no relational expression.");
	}
	
	@Override
	protected double buildConstantRhs() {
		throw new UnsupportedOperationException("Constraint has no relational expression.");
	}
	
	@Override
	protected List<ILPTerm> buildVariableLhs() {
		throw new UnsupportedOperationException("Constraint has no lhs containing ilp variables.");
	}
	
	@Override
	protected boolean buildConstantExpression() {
		return «parseExpression(boolExpr, ExpressionContext.constConstraint)»;
	}
	
	«generateDependencyConstraints()»
	«FOR methods : builderMethodDefinitions.values»
	«methods»
	«ENDFOR»
}'''
	}
	
	override generateDependencyConstraints() {
		if(!context.isDepending) {
			return '''
	@Override
	protected List<ILPConstraint> buildAdditionalConstraints() {
		throw new UnsupportedOperationException("Constraint has no depending or substitute constraints.");
	}
		'''
		} else {
			imports.add("org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator")
			val constraint2methodCalls = new HashMap<RelationalExpression, List<String>>
			for(RelationalExpression constraint : context.helperConstraints) {
				val methodCalls = new LinkedList<String>
				constraint2methodCalls.put(constraint, methodCalls);
				generateVariableTermBuilder(constraint.lhs, methodCalls)
			}
			return '''
	@Override
	protected List<ILPConstraint> buildAdditionalConstraints() {
		List<ILPConstraint> additionalConstraints = new LinkedList<>();
		ILPConstraint constraint = null;
		List<ILPTerm> terms = new LinkedList<>();
		double constTerm = 0.0;
		
		«FOR constraint : context.helperConstraints»
		«FOR instruction : constraint2methodCalls.get(constraint)»
		«instruction»
		«ENDFOR»
		constTerm = «generateConstTermBuilder(constraint.rhs)»;
		constraint = new ILPConstraint(terms, RelationalOperator.«constraint.operator.name()», constTerm);
		additionalConstraints.add(constraint);
		terms = new LinkedList<>();
		
		«ENDFOR»
		
		return additionalConstraints;
	}
		'''
		}
	}
	
	override String generateComplexConstraint(ArithmeticExpression constExpr, ArithmeticExpression dynamicExpr) {
		generateVariableTermBuilder(dynamicExpr, builderMethodCalls2)
		return '''
@Override
protected double buildConstantRhs() {
	return «generateConstTermBuilder(constExpr)»;
}
	
@Override
protected List<ILPTerm> buildVariableLhs() {
	List<ILPTerm> terms = Collections.synchronizedList(new LinkedList<>());
	«FOR instruction :  builderMethodCalls2»
	«instruction»
	«ENDFOR»
	return terms;
}
		'''
	}
	
	override generateBuilder(ValueExpression expr, LinkedList<String> methodCalls) {
		if(expr instanceof MappingSumExpression) {
				val builderMethodName = generateForeignBuilder(expr, methodCalls)
				val instruction = '''«builderMethodName»(terms);'''
				methodCalls.add(instruction)
		} else if(expr instanceof TypeSumExpression) {
			val builderMethodName = generateForeignBuilder(expr, methodCalls)
			val instruction = '''«builderMethodName»(terms);'''
				methodCalls.add(instruction)
		} else if(expr instanceof PatternSumExpression) {
			val builderMethodName = generateForeignBuilder(expr, methodCalls)
			val instruction = '''«builderMethodName»(terms);'''
				methodCalls.add(instruction)
		} else if(expr instanceof ContextSumExpression) {
			throw new UnsupportedOperationException("There is no specific context available for global constraints.")
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
		} else if(expr instanceof ContextMappingVariablesReference) {
			throw new UnsupportedOperationException("Mapping context access is not possible within a pattern context.")
		}else if(expr instanceof ContextPatternNodeFeatureValue) {
			throw new UnsupportedOperationException("Ilp term may not be constant.")
		} else if(expr instanceof ContextPatternNode) {
			throw new UnsupportedOperationException("Ilp term may not be constant.")
		} else if(expr instanceof ContextPatternValue) {
			throw new UnsupportedOperationException("Ilp term may not be constant.")
		} else if(expr instanceof ObjectiveFunctionValue) {
			throw new UnsupportedOperationException("Ilp term may not contain references to objective functions.")
		} else if(expr instanceof IteratorMappingValue) {
			throw new UnsupportedOperationException("Iterators may not be used outside of lambda expressions")
		} else if(expr instanceof IteratorMappingVariableValue) {
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
	
	override getVariable(VariableSet variable) {
		if(variable instanceof Variable) {
			return '''engine.getNonMappingVariable(constraint, "«variable.name»")'''
		} else {
			throw new UnsupportedOperationException("Mapping context access is not possible within the global context.")
		}
		
	}
	
	override getAdditionalVariableName(VariableReference varRef) {
		return '''engine.getNonMappingVariable(constraint, "«varRef.variable.name»").getName()'''
	}
	
	override generateBuilder(ContextSumExpression expr, LinkedList<String> methodCalls) {
		throw new UnsupportedOperationException("There is no specific context available for global constraints.")
	}
	
	override generateBuilder(BinaryArithmeticExpression expr, LinkedList<String> methodCalls) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		val method = '''
	protected double «methodName»() {
		return «parseExpression(expr, ExpressionContext.varConstraint)»;
	}
		'''
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	override generateBuilder(UnaryArithmeticExpression expr, LinkedList<String> methodCalls) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		val method = '''
	protected double «methodName»() {
		return «parseExpression(expr, ExpressionContext.varConstraint)»;
	}
		'''
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	override generateForeignBuilder(MappingSumExpression expr, LinkedList<String> methodCalls) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		imports.add(data.apiData.gipsMappingPkg+"."+data.mapping2mappingClassName.get(expr.mapping))
		imports.add("java.util.stream.Collectors")
		val vars = GipsTransformationUtils.extractVariable(expr.expression)
		var containsOnlyMappingVariable = false
		var variableRef = null as VariableSet
		// TODO: Temporary dirty bug fix
		if(vars.contains(expr.mapping) && (vars.size == 1 || vars.size == 0)) {
			containsOnlyMappingVariable = true;
		} else if(!vars.contains(expr.mapping) && vars.size == 1 || vars.contains(expr.mapping) && vars.size == 2 ) {
			containsOnlyMappingVariable = false;
			variableRef = vars.filter[v | !v.equals(expr.mapping)].findFirst[true]
		} else {
			throw new UnsupportedOperationException("Mapping sum expression may not contain more than one variable reference.")
		}
		val method = '''
	protected void «methodName»(final List<ILPTerm> terms) {
		for(«data.mapping2mappingClassName.get(expr.mapping)» «getIteratorVariableName(expr)» : engine.getMapper("«expr.mapping.name»").getMappings().values().stream()
			.map(mapping -> («data.mapping2mappingClassName.get(expr.mapping)») mapping)
			«getFilterExpr(expr.filter, ExpressionContext.varStream)».collect(Collectors.toList())) {
			«IF containsOnlyMappingVariable»terms.add(new ILPTerm(«getIteratorVariableName(expr)», (double)«parseExpression(expr.expression, ExpressionContext.varConstraint)»));
			«ELSE»terms.add(new ILPTerm(«getIteratorVariableName(expr)».get«variableRef.name.toFirstUpper»(), (double)«parseExpression(expr.expression, ExpressionContext.varConstraint)»));
			«ENDIF»
		}
	}
		'''
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	override generateForeignBuilder(TypeSumExpression expr, LinkedList<String> methodCalls) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		imports.add("java.util.stream.Collectors")
		imports.add(data.classToPackage.getPackage(expr.type.type.EPackage))
		var method = "";
		
		if(context.isConstant && context.expression instanceof RelationalExpression) {
			method = '''
	protected double «methodName»(final List<ILPTerm> terms) {
		return indexer.getObjectsOfType(«expr.type.type.EPackage.name».eINSTANCE.get«expr.type.type.name»()).stream()
			.map(type -> («expr.type.type.name») type)
			«getFilterExpr(expr.filter, ExpressionContext.constStream)»
			.map(type -> (double)«parseExpression(expr.expression, ExpressionContext.constConstraint)»)
			.reduce(0.0, (sum, value) -> sum + value);
	}
		'''
		} else if(!context.isConstant && context.expression instanceof RelationalExpression) {
			method = '''
	protected void «methodName»(final List<ILPTerm> terms) {
		for(«expr.type.type.name» «getIteratorVariableName(expr)» : indexer.getObjectsOfType(«expr.type.type.EPackage.name».eINSTANCE.get«expr.type.type.name»()).stream()
			.map(type -> («expr.type.type.name») type)
			«getFilterExpr(expr.filter, ExpressionContext.varStream)».collect(Collectors.toList())) {
			terms.add(new ILPTerm(«getIteratorVariableName(expr)», (double)«parseExpression(expr.expression, ExpressionContext.varConstraint)»));
		}
	}
		'''
		} else if(context.isConstant && !(context.expression instanceof RelationalExpression)) {
			method = '''
	protected boolean «methodName»(final List<ILPTerm> terms) {
		throw new UnsupportedOperationException("TODO: Implement stream-boolean expressions at root level.");
	}
		''' 
		} else {
			throw new UnsupportedOperationException("Boolean values can not be translated into ILP constraints.");
		}
		
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	override generateForeignBuilder(PatternSumExpression expr, LinkedList<String> methodCalls) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		imports.add("java.util.stream.Collectors")
		imports.add(data.apiData.matchesPkg+"."+data.pattern2matchClassName.get(expr.pattern))
		var method = "";
		
		if(context.isConstant && context.expression instanceof RelationalExpression) {
			
			method = '''
	protected double «methodName»(final List<ILPTerm> terms) {
		return engine.getEMoflonAPI().«expr.pattern.name»().findMatches(false).stream()
			«getFilterExpr(expr.filter, ExpressionContext.constStream)»
			.map(type -> (double)«parseExpression(expr.expression, ExpressionContext.constConstraint)»)
			.reduce(0.0, (sum, value) -> sum + value);
	}
		'''
		} else if(!context.isConstant && context.expression instanceof RelationalExpression) {
			
			method = '''
	protected void «methodName»(final List<ILPTerm> terms) {
		for(«data.pattern2matchClassName.get(expr.pattern)» «getIteratorVariableName(expr)» : engine.getEMoflonAPI().«expr.pattern.name»().findMatches(false).stream()
			«getFilterExpr(expr.filter, ExpressionContext.varStream)».collect(Collectors.toList())) {
			terms.add(new ILPTerm(«getIteratorVariableName(expr)», (double)«parseExpression(expr.expression, ExpressionContext.varConstraint)»));
		}
	}
		'''
		} else if(context.isConstant && !(context.expression instanceof RelationalExpression)) {
			method = '''
	protected boolean «methodName»(final List<ILPTerm> terms) {
		throw new UnsupportedOperationException("TODO: Implement stream-boolean expressions at root level.");
	}
		''' 
		} else {
			throw new UnsupportedOperationException("Boolean values can not be translated into ILP constraints.");
		}
		
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	override generateBuilder(MappingSumExpression expr, LinkedList<String> methodCalls) {
		throw new UnsupportedOperationException("Mapping context not available in global context.")
	}

}