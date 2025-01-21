package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.MappingConstraint
import org.emoflon.gips.build.transformation.helper.ArithmeticExpressionType
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression
import java.util.LinkedList
import java.util.List
import java.util.HashMap
import org.emoflon.gips.intermediate.GipsIntermediate.Variable
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanExpression

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
		imports.add("org.emoflon.gips.core.milp.model.Term")
		imports.add("org.emoflon.gips.core.milp.model.Constraint")
		if(context.isDepending) {
			imports.add("org.emoflon.gips.core.milp.model.BinaryVariable");
			imports.add("org.emoflon.gips.core.milp.model.RealVariable");
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
		var type = null as ArithmeticExpressionType
		if(relExpr.lhs instanceof ArithmeticExpression) {
			type = GipsTransformationUtils.isConstantExpression(relExpr.lhs as ArithmeticExpression)
		} else {
			type = GipsTransformationUtils.isConstantExpression(relExpr.lhs as BooleanExpression)
		}
		
		return '''
public class «className» extends GipsMappingConstraint<«data.gipsApiClassName», «data.mapping2mappingClassName.get(context.mapping)»>{
	public «className»(final «data.gipsApiClassName» engine, final MappingConstraint constraint) {
		super(engine, constraint);
	}
	«IF type == ArithmeticExpressionType.constant»
	«generateComplexConstraint(relExpr.lhs as ArithmeticExpression, relExpr.rhs as ArithmeticExpression)»
	«ELSE»
	«generateComplexConstraint(relExpr.rhs as ArithmeticExpression, relExpr.lhs as ArithmeticExpression)»
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
		if(!relExpr.isRequiresComparables) {
			return '''
public class «className» extends GipsMappingConstraint<«data.gipsApiClassName», «data.mapping2mappingClassName.get(context.mapping)»>{
	public «className»(final «data.gipsApiClassName» engine, final MappingConstraint constraint) {
		super(engine, constraint);
	}
	
	@Override
	protected double buildConstantLhs(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		«IF relExpr.lhs instanceof ArithmeticExpression» return «generateConstTermBuilder(relExpr.lhs as ArithmeticExpression)»;
		«ELSE» return «generateConstTermBuilder(relExpr.lhs as BooleanExpression)»;
		«ENDIF»
	}
	
	@Override
	protected double buildConstantRhs(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		«IF relExpr.rhs instanceof ArithmeticExpression» return «generateConstTermBuilder(relExpr.rhs as ArithmeticExpression)»;
		«ELSE» return «generateConstTermBuilder(relExpr.rhs as BooleanExpression)»;
		«ENDIF»
	}
	
	@Override
	protected List<Term> buildVariableLhs(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		throw new UnsupportedOperationException("Constraint has no lhs containing ilp variables.");
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
		} else {
			return '''
public class «className» extends GipsMappingConstraint<«data.gipsApiClassName», «data.mapping2mappingClassName.get(context.mapping)»>{
	public «className»(final «data.gipsApiClassName» engine, final MappingConstraint constraint) {
		super(engine, constraint);
	}
	
	@Override
	protected double buildConstantLhs(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		throw new UnsupportedOperationException("Constraint has no arithmetic lhs.");
	}
	
	@Override
	protected double buildConstantRhs(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		throw new UnsupportedOperationException("Constraint has no arithmetic lhs.");
	}
	
	@Override
	protected List<Term> buildVariableLhs(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		throw new UnsupportedOperationException("Constraint has no lhs containing ilp variables.");
	}
	
	@Override
	protected boolean buildConstantExpression(final «data.mapping2mappingClassName.get(context.mapping)» context) {
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
public class «className» extends GipsMappingConstraint<«data.gipsApiClassName», «data.mapping2mappingClassName.get(context.mapping)»>{
	public «className»(final «data.gipsApiClassName» engine, final MappingConstraint constraint) {
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
	
	@Override
	protected List<Term> buildVariableLhs(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		throw new UnsupportedOperationException("Constraint has no lhs containing ilp variables.");
	}
	
	@Override
	protected boolean buildConstantExpression(final «data.mapping2mappingClassName.get(context.mapping)» context) {
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
	protected List<Constraint> buildAdditionalConstraints(final «data.mapping2mappingClassName.get(context.mapping)» context) {
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
	protected List<Constraint> buildAdditionalConstraints(final «data.mapping2mappingClassName.get(context.mapping)» context) {
		List<Constraint> additionalConstraints = new LinkedList<>();
		Constraint constraint = null;
		List<Term> terms = new LinkedList<>();
		double constTerm = 0.0;
		
		«FOR constraint : context.helperConstraints»
		«FOR instruction : constraint2methodCalls.get(constraint)»
		«instruction»
		«ENDFOR»
		constTerm = «generateConstTermBuilder(constraint.rhs as ArithmeticExpression)»;
		constraint = new Constraint(terms, RelationalOperator.«constraint.operator.name()», constTerm);
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
protected List<Term> buildVariableLhs(final «data.mapping2mappingClassName.get(context.mapping)» context) {
	List<Term> terms = Collections.synchronizedList(new LinkedList<>());
	«FOR instruction : builderMethodCalls2»
	«instruction»
	«ENDFOR»
	return terms;
}
		'''
	}
	
	override String getVariable(Variable variable) {
		if(isMappingVariable(variable) && context.mapping.mappingVariable.equals(variable)) {
			return '''context'''
		} else if(!isMappingVariable(variable)) {
			return '''engine.getNonMappingVariable(context, "«variable.name»")'''
		} else {
			throw new UnsupportedOperationException("Foreign mapping context access is not possible within another mapping context.")
		}
	}
	
	override String getContextParameterType() {
		return data.mapping2mappingClassName.get(context.mapping)
	}
	
}