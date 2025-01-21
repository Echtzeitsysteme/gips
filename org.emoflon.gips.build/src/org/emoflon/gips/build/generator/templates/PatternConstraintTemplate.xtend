package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression
import org.emoflon.gips.build.transformation.helper.ArithmeticExpressionType
import org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression
import java.util.LinkedList
import java.util.HashMap
import java.util.List
import org.emoflon.gips.intermediate.GipsIntermediate.Variable
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanExpression

class PatternConstraintTemplate extends ConstraintTemplate<PatternConstraint> {

	new(TemplateData data, PatternConstraint context) {
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
		imports.add("org.emoflon.gips.core.gt.GipsPatternConstraint")
		imports.add("org.emoflon.gips.core.milp.model.Term")
		imports.add("org.emoflon.gips.core.milp.model.Constraint")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.PatternConstraint")
		imports.add(data.apiData.gipsApiPkg+"."+data.gipsApiClassName)
		imports.add(data.apiData.matchesPkg+"."+data.ibex2matchClassName.get(context.pattern))
		imports.add(data.apiData.rulesPkg+"."+data.ibex2ibexClassName.get(context.pattern))
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
		var type = null as ArithmeticExpressionType
		if(relExpr.lhs instanceof ArithmeticExpression) {
			type = GipsTransformationUtils.isConstantExpression(relExpr.lhs as ArithmeticExpression)
		} else {
			type = GipsTransformationUtils.isConstantExpression(relExpr.lhs as BooleanExpression)
		}
		
		return '''
public class «className» extends GipsPatternConstraint<«data.gipsApiClassName», «data.ibex2matchClassName.get(context.pattern)», «data.ibex2ibexClassName.get(context.pattern)»>{
	public «className»(final «data.gipsApiClassName» engine, final PatternConstraint constraint, final «data.ibex2ibexClassName.get(context.pattern)» pattern) {
		super(engine, constraint, pattern);
	}
	«IF type == ArithmeticExpressionType.constant»
	«generateComplexConstraint(relExpr.lhs as ArithmeticExpression, relExpr.rhs as ArithmeticExpression)»
	«ELSE»
	«generateComplexConstraint(relExpr.rhs as ArithmeticExpression, relExpr.lhs as ArithmeticExpression)»
	«ENDIF»
	
	@Override
	protected double buildConstantLhs(final «data.ibex2matchClassName.get(context.pattern)» context) {
		throw new UnsupportedOperationException("Constraint has an lhs that contains ilp variables.");
	}
	
	@Override
	protected boolean buildConstantExpression(final «data.ibex2matchClassName.get(context.pattern)» context) {
		throw new UnsupportedOperationException("Constraint has no constant boolean expression.");
	}
		
	«generateDependencyConstraints()»
	«FOR methods : builderMethodDefinitions.values»
	«methods»
	«ENDFOR»
}'''
	}
	
	override String generateConstantClassContent(RelationalExpression relExpr) {
		if(!relExpr.isRequiresComparables) {
			return '''
public class «className» extends GipsPatternConstraint<«data.gipsApiClassName», «data.ibex2matchClassName.get(context.pattern)», «data.ibex2ibexClassName.get(context.pattern)»>{
	public «className»(final «data.gipsApiClassName» engine, final PatternConstraint constraint, final «data.ibex2ibexClassName.get(context.pattern)» pattern) {
		super(engine, constraint, pattern);
	}
	
	@Override
	protected double buildConstantLhs(final «data.ibex2matchClassName.get(context.pattern)» context) {
		«IF relExpr.lhs instanceof ArithmeticExpression» return «generateConstTermBuilder(relExpr.lhs as ArithmeticExpression)»;
		«ELSE» return «generateConstTermBuilder(relExpr.lhs as BooleanExpression)»;
		«ENDIF»
	}
	
	@Override
	protected double buildConstantRhs(final «data.ibex2matchClassName.get(context.pattern)» context) {
		«IF relExpr.rhs instanceof ArithmeticExpression» return «generateConstTermBuilder(relExpr.rhs as ArithmeticExpression)»;
		«ELSE» return «generateConstTermBuilder(relExpr.rhs as BooleanExpression)»;
		«ENDIF»
	}
	
	@Override
	protected List<Term> buildVariableLhs(final «data.ibex2matchClassName.get(context.pattern)» context) {
		throw new UnsupportedOperationException("Constraint has no lhs containing ilp variables.");
	}
	
	@Override
	protected boolean buildConstantExpression(final «data.ibex2matchClassName.get(context.pattern)» context) {
		throw new UnsupportedOperationException("Constraint has no constant boolean expression.");
	}
		
	«generateDependencyConstraints()»
	«FOR methods : builderMethodDefinitions.values»
	«methods»
	«ENDFOR»
}'''		
		} else {
			return '''
public class «className» extends GipsPatternConstraint<«data.gipsApiClassName», «data.ibex2matchClassName.get(context.pattern)», «data.ibex2ibexClassName.get(context.pattern)»>{
	public «className»(final «data.gipsApiClassName» engine, final PatternConstraint constraint, final «data.ibex2ibexClassName.get(context.pattern)» pattern) {
		super(engine, constraint, pattern);
	}
	
	@Override
	protected double buildConstantLhs(final «data.ibex2matchClassName.get(context.pattern)» context) {
		throw new UnsupportedOperationException("Constraint has no arithmetic lhs.");
	}
	
	@Override
	protected double buildConstantRhs(final «data.ibex2matchClassName.get(context.pattern)» context) {
		throw new UnsupportedOperationException("Constraint has no arithmetic lhs.");
	}
	
	@Override
	protected List<Term> buildVariableLhs(final «data.ibex2matchClassName.get(context.pattern)» context) {
		throw new UnsupportedOperationException("Constraint has no lhs containing ilp variables.");
	}
	
	@Override
	protected boolean buildConstantExpression(final «data.ibex2matchClassName.get(context.pattern)» context) {
		return «generateConstantExpression(relExpr)»;
	}
		
	«generateDependencyConstraints()»
	«FOR methods : builderMethodDefinitions.values»
	«methods»
	«ENDFOR»
}'''
		}

	}
	
	override String generateConstantClassContent(BooleanExpression boolExpr) {
		return '''
public class «className» extends GipsPatternConstraint<«data.gipsApiClassName», «data.ibex2matchClassName.get(context.pattern)», «data.ibex2ibexClassName.get(context.pattern)»>{
	public «className»(final «data.gipsApiClassName» engine, final PatternConstraint constraint, final «data.ibex2ibexClassName.get(context.pattern)» pattern) {
		super(engine, constraint, pattern);
	}
	
	@Override
	protected double buildConstantLhs(final «data.ibex2matchClassName.get(context.pattern)» context) {
		throw new UnsupportedOperationException("Constraint has no relational expression.");
	}
	
	@Override
	protected double buildConstantRhs(final «data.ibex2matchClassName.get(context.pattern)» context) {
		throw new UnsupportedOperationException("Constraint has no relational expression.");
	}
	
	@Override
	protected List<Term> buildVariableLhs(final «data.ibex2matchClassName.get(context.pattern)» context) {
		throw new UnsupportedOperationException("Constraint has no lhs containing ilp variables.");
	}
	
	@Override
	protected boolean buildConstantExpression(final «data.ibex2matchClassName.get(context.pattern)» context) {
		return «generateConstantExpression(boolExpr)»;
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
	protected List<ILPConstraint> buildAdditionalConstraints(final «data.ibex2matchClassName.get(context.pattern)» context) {
		throw new UnsupportedOperationException("Constraint has no depending or substitute constraints.");
	}
		'''
		} else {
			imports.add("org.emoflon.gips.intermediate.GipsIntermediate.RelationalOperator")
			val constraint2methodCalls = new HashMap<RelationalExpression, List<String>>
			for(RelationalExpression constraint : context.helperConstraints) {
				val methodCalls = new LinkedList<String>
				constraint2methodCalls.put(constraint, methodCalls);
				generateVariableTermBuilder(constraint.lhs as ArithmeticExpression, methodCalls)
			}
			return '''
	@Override
	protected List<ILPConstraint> buildAdditionalConstraints(final «data.ibex2matchClassName.get(context.pattern)» context) {
		List<ILPConstraint> additionalConstraints = new LinkedList<>();
		ILPConstraint constraint = null;
		List<ILPTerm> terms = new LinkedList<>();
		double constTerm = 0.0;
		
		«FOR constraint : context.helperConstraints»
		«FOR instruction : constraint2methodCalls.get(constraint)»
		«instruction»
		«ENDFOR»
		constTerm = «generateConstTermBuilder(constraint.rhs as ArithmeticExpression)»;
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
protected double buildConstantRhs(final «data.ibex2matchClassName.get(context.pattern)» context) {
	return «generateConstTermBuilder(constExpr)»;
}
	
@Override
protected List<ILPTerm> buildVariableLhs(final «data.ibex2matchClassName.get(context.pattern)» context) {
	List<ILPTerm> terms = Collections.synchronizedList(new LinkedList<>());
	«FOR instruction : builderMethodCalls2»
	«instruction»
	«ENDFOR»
	return terms;
}
		'''
	}
	
	override String getVariable(Variable variable) {
		if(!isMappingVariable(variable)) {
			return '''engine.getNonMappingVariable(context, "«variable.name»")'''
		} else {
			throw new UnsupportedOperationException("Mapping context access is not possible within a pattern context.")
		}
	}
	
	override String getContextParameterType() {
		return data.ibex2matchClassName.get(context.pattern)
	}
}