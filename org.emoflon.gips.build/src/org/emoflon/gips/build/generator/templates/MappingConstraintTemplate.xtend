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
import org.emoflon.gips.intermediate.GipsIntermediate.IteratorMappingValue
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
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint")
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
public class «className» extends GipsMappingConstraint<«data.mapping2mappingClassName.get(context.mapping)»>{
	public «className»(final GipsEngine engine, final MappingConstraint constraint) {
		super(engine, constraint);
	}
	«IF GipsTransformationUtils.isConstantExpression( relExpr.lhs) == ArithmeticExpressionType.constant»
	«generateComplexConstraint(relExpr.lhs, relExpr.rhs)»
	«ELSE»
	«generateComplexConstraint(relExpr.rhs, relExpr.lhs)»
	«ENDIF»
	
	@Overide
	protected boolean buildConstantLhs(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		throw new UnsupportedOperationException("Constraint has an lhs that contains ilp variables.");
	}
	
	@Overide
	protected boolean buildConstantExpression(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		throw new UnsupportedOperationException("Constraint has no constant boolean expression.");
	}
		
	«FOR methods : builderMethodDefinitions.values»
	«methods»
	«ENDFOR»
}'''
	}
	
	override String generateConstantClassContent(RelationalExpression relExpr) {
		return '''
public class «className» extends GipsMappingConstraint<«data.mapping2mappingClassName.get(context.mapping)»>{
	public «className»(final GipsEngine engine, final MappingConstraint constraint) {
		super(engine, constraint);
	}
	
	@Override
	protected double buildConstantLhs(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		return «generateConstTermBuilder(relExpr.lhs)»;
	}
	
	@Override
	protected double buildConstantRhs(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		return «generateConstTermBuilder(relExpr.rhs)»;
	}
	
	@Overide
	protected boolean buildVariableLhs(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		throw new UnsupportedOperationException("Constraint has no lhs containing ilp variables.");
	}
	
	@Overide
	protected boolean buildConstantExpression(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		throw new UnsupportedOperationException("Constraint has no constant boolean expression.");
	}
		
	«FOR methods : builderMethodDefinitions.values»
	«methods»
	«ENDFOR»
}'''
	}
	
	override String generateConstantClassContent(BoolValueExpression boolExpr) {
		return '''
public class «className» extends GipsMappingConstraint<«data.mapping2mappingClassName.get(context.mapping)»>{
	public «className»(final GipsEngine engine, final MappingConstraint constraint) {
		super(engine, constraint);
	}
	
	@Override
	protected double buildConstantLhs(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		throw new UnsupportedOperationException("Constraint has no relational expression.");
	}
	
	@Override
	protected double buildConstantRhs(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		throw new UnsupportedOperationException("Constraint has no relational expression.");
	}
	
	@Overide
	protected boolean buildVariableLhs(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		throw new UnsupportedOperationException("Constraint has no lhs containing ilp variables.");
	}
	
	@Overide
	protected boolean buildConstantExpression(final «data.mapping2mappingClassName.get(context.mapping)» context) {
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
protected double buildConstantTerm(final «data.mapping2mappingClassName.get(context.mapping)» context) {
	return «generateConstTermBuilder(constExpr)»;
}
	
@Override
protected List<ILPTerm<Integer, Double>> buildVariableTerms(final «data.mapping2mappingClassName.get(context.mapping)» context) {
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
			if(expr.mapping == context.mapping) {
				val builderMethodName = generateBuilder(expr)
				val instruction = '''«builderMethodName»(terms, context);'''
				builderMethodCalls.add(instruction)
			} else {
				throw new UnsupportedOperationException("Referencing other mapping variables from within a mapping context is not allowed.")
			}
		} if(expr instanceof ContextSumExpression) {
			val builderMethodName = generateBuilder(expr)
			val instruction = '''«builderMethodName»(terms, context);'''
			builderMethodCalls.add(instruction)
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
		imports.add(data.apiData.gipsMappingPkg+"."+data.mapping2mappingClassName.get(expr.mapping))
		imports.add("java.util.stream.Collectors")
		val method = '''
	protected void «methodName»(final List<ILPTerm<Integer, Double>> terms, final «data.mapping2mappingClassName.get(context.mapping)» context) {
		for(«data.mapping2mappingClassName.get(expr.mapping)» «getIteratorVariableName(expr)» : engine.getMapper("«expr.mapping.name»").getMappings().values().parallelStream()
			.map(mapping -> («data.mapping2mappingClassName.get(expr.mapping)») mapping)
			«getFilterExpr(expr.filter, ExpressionContext.varStream)».collect(Collectors.toList())) {
			ILPTerm<Integer, Double> term = new ILPTerm<Integer, Double>(context, (double)«parseExpression(expr.expression, ExpressionContext.varConstraint)»);
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
	protected void «methodName»(final List<ILPTerm<Integer, Double>> terms, final «data.mapping2mappingClassName.get(context.mapping)» context) {
		double constant = context.get«expr.node.name.toFirstUpper»().«parseFeatureExpression(expr.feature)».parallelStream()
					«getFilterExpr(expr.filter, ExpressionContext.varStream)»
					.map(«getIteratorVariableName(expr)» -> «parseExpression(expr.expression, ExpressionContext.constConstraint)»)
					.reduce(0.0, (sum, value) -> sum + value);
								
		terms.add(new ILPTerm<Integer, Double>(context, constant));
	}
		'''
		
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	override String generateForeignBuilder(MappingSumExpression expr) {
		throw new UnsupportedOperationException("Referencing other mapping variables from within a mapping context is not allowed.")
	}
	
	override String generateBuilder(TypeSumExpression expr) {
//		val methodName = '''builder_«builderMethods.size»'''
//		builderMethods.put(expr, methodName)
//		imports.add("java.util.stream.Collectors")
//		val method = '''
//	protected void «methodName»(final List<ILPTerm<Integer, Double>> terms, final «data.mapping2mappingClassName.get(context.mapping)» context) {
//		for(«expr.type.type.name» «getIteratorVariableName(expr)» : indexer.getObjectsOfType("«expr.type.name»").parallelStream()
//			.map(type -> («expr.type.type.name») type)
//			«getFilterExpr(expr.filter, ExpressionContext.varStream)».collect(Collectors.toList())) {
//			terms.add(new ILPTerm<Integer, Double>(context, (double)«parseExpression(expr.expression, ExpressionContext.varConstraint)»));
//		}
//	}
//		'''
//		builderMethodDefinitions.put(expr, method)
//		return methodName
		throw new UnsupportedOperationException("Foreign type stream expr not yet implemented.");
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
		return new ILPTerm<Integer, Double>(context, (double)context.«parseFeatureExpression(expr.featureExpression)»);
	}
		'''
		builderMethodDefinitions.put(expr, method)
		return methodName;
	}
	
}