package org.emoflon.gips.build.generator.templates.constraint

import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticBinaryExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticBinaryOperator
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticUnaryExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression
import org.emoflon.gips.intermediate.GipsIntermediate.Constraint
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanExpression
import org.emoflon.gips.build.generator.templates.ProblemGeneratorTemplate
import org.emoflon.gips.build.transformation.helper.ArithmeticExpressionType
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils
import java.util.HashMap
import java.util.List
import java.util.LinkedList
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantReference

abstract class ConstraintTemplate <CONTEXT extends Constraint> extends ProblemGeneratorTemplate<CONTEXT> {
	
	new(TemplateData data, CONTEXT context) {
		super(data, context)
	}
	
	def String generateClassSignature();
	
	def String generateClassConstructor();
	
	override getConstants() {
		return context.constants;
	}
	
	override generate() {
		var classContent = "";
		if(context.isConstant) {
			if(context.expression instanceof RelationalExpression) {
				classContent = generateConstantClassContent(context.expression as RelationalExpression)
			} else {
				classContent = generateConstantClassContent(context.expression as BooleanExpression)
			}
		} else {
			if(context.expression instanceof RelationalExpression) {
				classContent = generateVariableClassContent(context.expression as RelationalExpression)
			} else {
				throw new IllegalArgumentException("Boolean values can not be part of linear (in-)equalities.");
			}
		}
		val importStatements = generateImports();
		code = '''«generatePackageDeclaration»		

«importStatements»

«classContent»
'''
	}
	
	def void generateVariableTermBuilder(ArithmeticExpression expr, LinkedList<String> methodCalls) {
		if(expr instanceof ArithmeticBinaryExpression) {
			if(expr.operator == ArithmeticBinaryOperator.ADD) {
				generateVariableTermBuilder(expr.lhs, methodCalls)
				generateVariableTermBuilder(expr.rhs, methodCalls)
			} else if(expr.operator == ArithmeticBinaryOperator.SUBTRACT) {
				throw new UnsupportedOperationException("Code generator does not support subtraction of variables.");
			} else {
				val variable = GipsTransformationUtils.extractVariable(expr);
				if(variable.size > 1)
					throw new UnsupportedOperationException("Access to multiple different variables in the same product is forbidden.");
				
				val builderMethodName = generateBuilder(expr, methodCalls)
				val instruction = '''terms.add(new Term(«getVariable(variable.iterator.next)», «builderMethodName»(context«IF !getConstants().empty», «ENDIF»«getCallParametersForConstants(getConstants())»)));'''
				methodCalls.add(instruction)
			}
		} else if(expr instanceof ArithmeticUnaryExpression) {
				val variable = GipsTransformationUtils.extractVariable(expr);
				if(variable.size > 1)
					throw new UnsupportedOperationException("Access to multiple different variables in the same product is forbidden.");
				
				val builderMethodName = generateBuilder(expr, methodCalls)
				val instruction = '''terms.add(new Term(«getVariable(variable.iterator.next)», «builderMethodName»(context«IF !getConstants().empty», «ENDIF»«getCallParametersForConstants(getConstants())»)));'''
				methodCalls.add(instruction)
		} else if(expr instanceof ConstantReference) {
			generateBuilder(expr, methodCalls)
		} else if(expr instanceof ValueExpression) {
			generateBuilder(expr, methodCalls)
		} else {
			throw new IllegalArgumentException("Terms on the lhs of a linear (in-)equality may not be constant")
		}
	}
	
	def String generateVariableClassContent(RelationalExpression relExpr) {
		var type = null as ArithmeticExpressionType
		if(relExpr.lhs instanceof ArithmeticExpression) {
			type = GipsTransformationUtils.isConstantExpression(relExpr.lhs as ArithmeticExpression)
		} else {
			type = GipsTransformationUtils.isConstantExpression(relExpr.lhs as BooleanExpression)
		}
		
		return '''
«generateClassSignature()»{
	«generateClassConstructor()»
	
	«IF type == ArithmeticExpressionType.constant»
	«generateComplexConstraint(relExpr.lhs as ArithmeticExpression, relExpr.rhs as ArithmeticExpression)»
	«ELSE»
	«generateComplexConstraint(relExpr.rhs as ArithmeticExpression, relExpr.lhs as ArithmeticExpression)»
	«ENDIF»
	
	@Override
	protected double buildConstantLhs(«getContextParameter()») {
		throw new UnsupportedOperationException("Constraint has an lhs that contains ilp variables.");
	}
	
	@Override
	protected boolean buildConstantExpression(«getContextParameter()») {
		throw new UnsupportedOperationException("Constraint has no constant boolean expression.");
	}
		
	«generateDependencyConstraints()»
	«getConstantCalculators(context.constants)»
	«FOR methods : builderMethodDefinitions.values»
	«methods»
	«ENDFOR»
}'''
	}
	
	def String generateConstantClassContent(RelationalExpression relExpr) {
		if(!relExpr.isRequiresComparables) {
			return '''
«generateClassSignature()»{
	«generateClassConstructor()»
	
	@Override
	protected double buildConstantLhs(«getContextParameter()») {
		«getConstantFields(context.constants)»
		«IF relExpr.lhs instanceof ArithmeticExpression» return «generateConstTermBuilder(relExpr.lhs as ArithmeticExpression)»;«ELSE» return «generateConstTermBuilder(relExpr.lhs as BooleanExpression)»;«ENDIF»
	}
	
	@Override
	protected double buildConstantRhs(«getContextParameter()») {
		«getConstantFields(context.constants)»
		«IF relExpr.rhs instanceof ArithmeticExpression» return «generateConstTermBuilder(relExpr.rhs as ArithmeticExpression)»;«ELSE» return «generateConstTermBuilder(relExpr.rhs as BooleanExpression)»;«ENDIF»
	}
	
	@Override
	protected List<Term> buildVariableLhs(«getContextParameter()») {
		throw new UnsupportedOperationException("Constraint has no lhs containing ilp variables.");
	}
	
	@Override
	protected boolean buildConstantExpression(«getContextParameter()») {
		throw new UnsupportedOperationException("Constraint has no constant boolean expression.");
	}
		
	«generateDependencyConstraints()»
	«getConstantCalculators(context.constants)»
	«FOR methods : builderMethodDefinitions.values»
	«methods»
	«ENDFOR»
}'''		
		} else {
			return '''
«generateClassSignature()»{
	«generateClassConstructor()»
	
	@Override
	protected double buildConstantLhs(«getContextParameter()») {
		throw new UnsupportedOperationException("Constraint has no arithmetic lhs.");
	}
	
	@Override
	protected double buildConstantRhs(«getContextParameter()») {
		throw new UnsupportedOperationException("Constraint has no arithmetic lhs.");
	}
	
	@Override
	protected List<Term> buildVariableLhs(«getContextParameter()») {
		throw new UnsupportedOperationException("Constraint has no lhs containing ilp variables.");
	}
	
	@Override
	protected boolean buildConstantExpression(«getContextParameter()») {
		«getConstantFields(context.constants)»
		return «generateConstantExpression(relExpr)»;
	}
		
	«generateDependencyConstraints()»
	«getConstantCalculators(context.constants)»
	«FOR methods : builderMethodDefinitions.values»
	«methods»
	«ENDFOR»
}'''
		}

	}
	
	def String generateConstantClassContent(BooleanExpression boolExpr) {
		return '''
«generateClassSignature()»{
	«generateClassConstructor()»
	
	@Override
	protected double buildConstantLhs(«getContextParameter()») {
		throw new UnsupportedOperationException("Constraint has no relational expression.");
	}
	
	@Override
	protected double buildConstantRhs(«getContextParameter()») {
		throw new UnsupportedOperationException("Constraint has no relational expression.");
	}
	
	@Override
	protected List<Term> buildVariableLhs(«getContextParameter()») {
		throw new UnsupportedOperationException("Constraint has no lhs containing ilp variables.");
	}
	
	@Override
	protected boolean buildConstantExpression(«getContextParameter()») {
		«getConstantFields(context.constants)»
		return «generateConstantExpression(boolExpr)»;
	}
	
	«generateDependencyConstraints()»
	«getConstantCalculators(context.constants)»
	«FOR methods : builderMethodDefinitions.values»
	«methods»
	«ENDFOR»
}'''
	}
	
	def String generateComplexConstraint(ArithmeticExpression constExpr, ArithmeticExpression dynamicExpr) {
		generateVariableTermBuilder(dynamicExpr, builderMethodCalls2)
		return '''
@Override
protected double buildConstantRhs(«getContextParameter()») {
	«getConstantFields(context.constants)»
	return «generateConstTermBuilder(constExpr)»;
}
	
@Override
protected List<Term> buildVariableLhs(«getContextParameter()») {
	«getConstantFields(context.constants)»
	List<Term> terms = Collections.synchronizedList(new LinkedList<>());
	«FOR instruction : builderMethodCalls2»
	«instruction»
	«ENDFOR»
	return terms;
}
		'''
	}
	
	def generateDependencyConstraints() {
		if(!context.isDepending) {
			return '''
	@Override
	protected List<Constraint> buildAdditionalConstraints(«getContextParameter()») {
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
	protected List<Constraint> buildAdditionalConstraints(«getContextParameter()») {
		«getConstantFields(context.constants)»
		
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
	
}