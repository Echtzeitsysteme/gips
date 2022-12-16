package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNode
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingNodeFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingValue
import org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.ContextTypeValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingNodeValue
import org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint
import org.emoflon.gips.intermediate.GipsIntermediate.MappingSumExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveFunctionValue
import org.emoflon.gips.intermediate.GipsIntermediate.TypeSumExpression
import org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression
import org.emoflon.gips.build.transformation.helper.ArithmeticExpressionType
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNode
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorPatternNodeFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorTypeFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.VariableSet
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ContextPatternNodeFeatureValue
import org.emoflon.gips.intermediate.GipsIntermediate.ContextSumExpression
import org.emoflon.gips.intermediate.GipsIntermediate.Mapping
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression
import org.emoflon.gips.intermediate.GipsIntermediate.BoolValueExpression
import org.emoflon.gips.intermediate.GipsIntermediate.PatternSumExpression
import java.util.LinkedList
import java.util.List
import java.util.HashMap
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference
import org.emoflon.gips.intermediate.GipsIntermediate.Variable
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingVariableValue
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingValue
import org.emoflon.gips.intermediate.GipsIntermediate.ContextMappingVariablesReference
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingVariablesReference

class MappingConstraintTemplate extends ConstraintTemplate<MappingConstraint> {

	new(TemplateData data, MappingConstraint context) {
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
		imports.add("org.emoflon.gips.core.GipsEngine")
		imports.add("org.emoflon.gips.core.GipsMappingConstraint")
		imports.add("org.emoflon.gips.core.ilp.ILPTerm")
		imports.add("org.emoflon.gips.core.ilp.ILPConstraint")
		if(context.isDepending) {
			imports.add("org.emoflon.gips.core.ilp.ILPBinaryVariable");
			imports.add("org.emoflon.gips.core.ilp.ILPRealVariable");
		}
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint")
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
	
	override String generateVariableClassContent(RelationalExpression relExpr) {
		return '''
public class «className» extends GipsMappingConstraint<«data.gipsApiClassName», «data.mapping2mappingClassName.get(context.mapping)»>{
	public «className»(final «data.gipsApiClassName» engine, final MappingConstraint constraint) {
		super(engine, constraint);
	}
	«IF GipsTransformationUtils.isConstantExpression( relExpr.lhs) == ArithmeticExpressionType.constant»
	«generateComplexConstraint(relExpr.lhs, relExpr.rhs)»
	«ELSE»
	«generateComplexConstraint(relExpr.rhs, relExpr.lhs)»
	«ENDIF»
	
	@Override
	protected double buildConstantLhs(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		throw new UnsupportedOperationException("Constraint has an lhs that contains ilp variables.");
	}
	
	@Override
	protected boolean buildConstantExpression(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		throw new UnsupportedOperationException("Constraint has no constant boolean expression.");
	}
		
	«generateDependencyConstraints()»
	«FOR methods : builderMethodDefinitions.values»
	«methods»
	«ENDFOR»
}'''
	}
	
	override String generateConstantClassContent(RelationalExpression relExpr) {
		throw new UnsupportedOperationException("Mapping constraints may not be constant at build time.");
	}
	
	override String generateConstantClassContent(BoolValueExpression boolExpr) {
		throw new UnsupportedOperationException("Mapping constraints may not be constant at build time.");
	}
	
	override generateDependencyConstraints() {
		if(!context.isDepending) {
			return '''
	@Override
	protected List<ILPConstraint> buildAdditionalConstraints(final «data.mapping2mappingClassName.get(context.mapping)» context) {
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
	protected List<ILPConstraint> buildAdditionalConstraints(final «data.mapping2mappingClassName.get(context.mapping)» context) {
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
protected double buildConstantRhs(final «data.mapping2mappingClassName.get(context.mapping)» context) {
	return «generateConstTermBuilder(constExpr)»;
}
	
@Override
protected List<ILPTerm> buildVariableLhs(final «data.mapping2mappingClassName.get(context.mapping)» context) {
	List<ILPTerm> terms = Collections.synchronizedList(new LinkedList<>());
	«FOR instruction : builderMethodCalls2»
	«instruction»
	«ENDFOR»
	return terms;
}
		'''
	}
	
	override generateBuilder(ValueExpression expr, LinkedList<String> methodCalls) {
		if(expr instanceof MappingSumExpression) {
			if(expr.mapping == context.mapping) {
				val builderMethodName = generateBuilder(expr, methodCalls)
				val instruction = '''«builderMethodName»(terms, context);'''
				methodCalls.add(instruction)
			} else {
				throw new UnsupportedOperationException("Referencing other mapping variables from within a mapping context is not allowed.")
			}
		} if(expr instanceof ContextSumExpression) {
			val builderMethodName = generateBuilder(expr, methodCalls)
			val instruction = '''«builderMethodName»(terms, context);'''
			methodCalls.add(instruction)
		} else if(expr instanceof TypeSumExpression) {
			val builderMethodName = generateForeignBuilder(expr, methodCalls)
			val instruction = '''«builderMethodName»(terms, context);'''
				methodCalls.add(instruction)
		} else if(expr instanceof PatternSumExpression) {
			val builderMethodName = generateForeignBuilder(expr, methodCalls)
			val instruction = '''«builderMethodName»(terms, context);'''
				methodCalls.add(instruction)
		} else if(expr instanceof ContextTypeFeatureValue) {
			throw new UnsupportedOperationException("Type context access is not possible within a mapping context.")
		} else if(expr instanceof ContextTypeValue) {
			throw new UnsupportedOperationException("Type context access is not possible within a mapping context.")
		} else if(expr instanceof ContextMappingNodeFeatureValue) {
			val builderMethodName = generateBuilder(expr)
			val instruction = '''«builderMethodName»(terms, context);'''
			methodCalls.add(instruction)
		} else if(expr instanceof ContextMappingNode) {
			throw new UnsupportedOperationException("Ilp term may not contain complex objects.")
		} else if(expr instanceof ContextMappingValue) {
			val builderMethodName = generateBuilder(expr)
			val instruction = '''«builderMethodName»(terms, context);'''
			methodCalls.add(instruction)
		} else if(expr instanceof ContextMappingVariablesReference) {
			val builderMethodName = generateBuilder(expr)
			val instruction = '''«builderMethodName»(terms, context);'''
			methodCalls.add(instruction)
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
		} else if(expr instanceof IteratorMappingVariableValue) {
			throw new UnsupportedOperationException("Iterators may not be used outside of lambda expressions")
		} else if(expr instanceof IteratorMappingFeatureValue) {
			throw new UnsupportedOperationException("Iterators may not be used outside of lambda expressions")
		} else if(expr instanceof IteratorMappingNodeFeatureValue) {
			throw new UnsupportedOperationException("Iterators may not be used outside of lambda expressions")
		} else if(expr instanceof IteratorMappingNodeValue) {
			throw new UnsupportedOperationException("Iterators may not be used outside of lambda expressions")
		} else if(expr instanceof IteratorMappingVariablesReference) {
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
	
	override String getVariable(VariableSet variable) {
		if(variable instanceof Mapping && variable == context.mapping) {
			return '''context'''
		} else if(variable instanceof Variable) {
			return '''engine.getNonMappingVariable(context, "«variable.name»")'''
		} else {
			throw new UnsupportedOperationException("Foreign mapping context access is not possible within another mapping context.")
		}
		
	}
		
	override getAdditionalVariableName(VariableReference varRef) {
		return '''engine.getNonMappingVariable(context, "«varRef.variable.name»").getName()'''
	}
	
	override String generateBuilder(BinaryArithmeticExpression expr, LinkedList<String> methodCalls) {
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
	
	override String generateBuilder(UnaryArithmeticExpression expr, LinkedList<String> methodCalls) {
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
	
	override String generateBuilder(MappingSumExpression expr, LinkedList<String> methodCalls) {
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
	protected void «methodName»(final List<ILPTerm> terms, final «data.mapping2mappingClassName.get(context.mapping)» context) {
		for(«data.mapping2mappingClassName.get(expr.mapping)» «getIteratorVariableName(expr)» : engine.getMapper("«expr.mapping.name»").getMappings().values().parallelStream()
			.map(mapping -> («data.mapping2mappingClassName.get(expr.mapping)») mapping)
			«getFilterExpr(expr.filter, ExpressionContext.varStream)».collect(Collectors.toList())) {
			«IF containsOnlyMappingVariable»terms.add(new ILPTerm(context, (double)«parseExpression(expr.expression, ExpressionContext.varConstraint)»));
			«ELSE»terms.add(new ILPTerm(context.get«variableRef.name.toFirstUpper»(), (double)«parseExpression(expr.expression, ExpressionContext.varConstraint)»));
			«ENDIF»
		}
	}
		'''
		
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	override String generateBuilder(ContextSumExpression expr, LinkedList<String> methodCalls) {
		if(!(expr.context instanceof Mapping && expr.context == context.mapping))
			throw new UnsupportedOperationException("Wrong context type!")
		
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		imports.add(data.apiData.gipsMappingPkg+"."+data.mapping2mappingClassName.get(expr.context))
		val method = '''
	protected void «methodName»(final List<ILPTerm> terms, final «data.mapping2mappingClassName.get(context.mapping)» context) {
		double constant = context.get«expr.node.name.toFirstUpper»().«parseFeatureExpression(expr.feature)».parallelStream()
					«getFilterExpr(expr.filter, ExpressionContext.varStream)»
					.map(«getIteratorVariableName(expr)» -> «parseExpression(expr.expression, ExpressionContext.constConstraint)»)
					.reduce(0.0, (sum, value) -> sum + value);
								
		terms.add(new ILPTerm(context, constant));
	}
		'''
		
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	override String generateForeignBuilder(MappingSumExpression expr, LinkedList<String> methodCalls) {
		throw new UnsupportedOperationException("Referencing other mapping variables from within a mapping context is not allowed.")
	}
	
	override String generateForeignBuilder(TypeSumExpression expr, LinkedList<String> methodCalls) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		imports.add("java.util.stream.Collectors")
		imports.add(data.classToPackage.getImportsForType(expr.type.type))
		var method = "";
		
		if(context.isConstant && context.expression instanceof RelationalExpression) {
			method = '''
	protected double «methodName»(final List<ILPTerm> terms, final «data.mapping2mappingClassName.get(context.mapping)» context) {
		return indexer.getObjectsOfType("«expr.type.type.name»").parallelStream()
			.map(type -> («expr.type.type.name») type)
			«getFilterExpr(expr.filter, ExpressionContext.constStream)»
			.map(type -> (double)«parseExpression(expr.expression, ExpressionContext.constConstraint)»)
			.reduce(0.0, (sum, value) -> sum + value);
	}
		'''
		} else if(!context.isConstant && context.expression instanceof RelationalExpression) {
			method = '''
	protected void «methodName»(final List<ILPTerm> terms, final «data.mapping2mappingClassName.get(context.mapping)» context) {
		for(«expr.type.type.name» «getIteratorVariableName(expr)» : indexer.getObjectsOfType("«expr.type.type.name»").parallelStream()
			.map(type -> («expr.type.type.name») type)
			«getFilterExpr(expr.filter, ExpressionContext.varStream)».collect(Collectors.toList())) {
			terms.add(new ILPTerm(«getIteratorVariableName(expr)», (double)«parseExpression(expr.expression, ExpressionContext.varConstraint)»));
		}
	}
		'''
		} else if(context.isConstant && !(context.expression instanceof RelationalExpression)) {
			method = '''
	protected boolean «methodName»(final List<ILPTerm> terms, final «data.mapping2mappingClassName.get(context.mapping)» context) {
		throw new UnsupportedOperationException("TODO: Implement stream-boolean expressions at root level.");
	}
		''' 
		} else {
			throw new UnsupportedOperationException("Boolean values can not be translated into ILP constraints.");
		}
		
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	override String generateForeignBuilder(PatternSumExpression expr, LinkedList<String> methodCalls) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		imports.add("java.util.stream.Collectors")
		imports.add(data.apiData.matchesPkg+"."+data.pattern2matchClassName.get(expr.pattern))
		var method = "";
		
		if(context.isConstant && context.expression instanceof RelationalExpression) {
			
			method = '''
	protected double «methodName»(final List<ILPTerm> terms, final «data.mapping2mappingClassName.get(context.mapping)» context) {
		return engine.getEMoflonAPI().«expr.pattern.name»().findMatches(false).parallelStream()
			«getFilterExpr(expr.filter, ExpressionContext.constStream)»
			.map(type -> (double)«parseExpression(expr.expression, ExpressionContext.constConstraint)»)
			.reduce(0.0, (sum, value) -> sum + value);
	}
		'''
		} else if(!context.isConstant && context.expression instanceof RelationalExpression) {
			
			method = '''
	protected void «methodName»(final List<ILPTerm> terms, final «data.mapping2mappingClassName.get(context.mapping)» context) {
		for(«data.pattern2matchClassName.get(expr.pattern)» «getIteratorVariableName(expr)» : engine.getEMoflonAPI().«expr.pattern.name»().findMatches(false).parallelStream()
			«getFilterExpr(expr.filter, ExpressionContext.varStream)».collect(Collectors.toList())) {
			terms.add(new ILPTerm(«getIteratorVariableName(expr)», (double)«parseExpression(expr.expression, ExpressionContext.varConstraint)»));
		}
	}
		'''
		} else if(context.isConstant && !(context.expression instanceof RelationalExpression)) {
			method = '''
	protected boolean «methodName»(final List<ILPTerm> terms, final «data.mapping2mappingClassName.get(context.mapping)» context) {
		throw new UnsupportedOperationException("TODO: Implement stream-boolean expressions at root level.");
	}
		''' 
		} else {
			throw new UnsupportedOperationException("Boolean values can not be translated into ILP constraints.");
		}
		
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	def String generateBuilder(ContextMappingValue expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		val method = '''
	protected void «methodName»(final List<ILPTerm> terms, final «data.mapping2mappingClassName.get(context.mapping)» context) {
		terms.add(new ILPTerm(context, 1.0));
	}
		'''
		builderMethodDefinitions.put(expr, method)
		return methodName;
	}
	
	def String generateBuilder(ContextMappingNodeFeatureValue expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		val method = '''
	protected void «methodName»(final List<ILPTerm> terms, final «data.mapping2mappingClassName.get(context.mapping)» context) {
		terms.add(new ILPTerm(context, (double)context.get«expr.node.name.toFirstUpper»().«parseFeatureExpression(expr.featureExpression)»));
	}
		'''
		builderMethodDefinitions.put(expr, method)
		return methodName;
	}
	
	def String generateBuilder(ContextMappingVariablesReference expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		val method = '''
	protected void «methodName»(final List<ILPTerm> terms, final «data.mapping2mappingClassName.get(context.mapping)» context) {
		terms.add(new ILPTerm(context.get«expr.^var.variable.name.toFirstUpper»(), 1.0));
	}
		'''
		builderMethodDefinitions.put(expr, method)
		return methodName;
	}
	
}