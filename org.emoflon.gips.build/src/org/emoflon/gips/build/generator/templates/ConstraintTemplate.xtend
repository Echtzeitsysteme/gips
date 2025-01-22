package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.RelationalExpression
import org.emoflon.gips.intermediate.GipsIntermediate.Constraint
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanExpression
import org.emoflon.gips.build.generator.ProblemGeneratorTemplate
import org.emoflon.gips.build.transformation.helper.ArithmeticExpressionType
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils
import java.util.HashMap
import java.util.List
import java.util.LinkedList

abstract class ConstraintTemplate <CONTEXT extends Constraint> extends ProblemGeneratorTemplate<CONTEXT> {
	
	new(TemplateData data, CONTEXT context) {
		super(data, context)
	}
	
	def String generateClassSignature();
	
	def String generateClassConstructor();
	
	def String getContextParameter() {
		return '''final «getContextParameterType()» context'''
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
		«IF relExpr.lhs instanceof ArithmeticExpression» return «generateConstTermBuilder(relExpr.lhs as ArithmeticExpression)»;
		«ELSE» return «generateConstTermBuilder(relExpr.lhs as BooleanExpression)»;
		«ENDIF»
	}
	
	@Override
	protected double buildConstantRhs(«getContextParameter()») {
		«IF relExpr.rhs instanceof ArithmeticExpression» return «generateConstTermBuilder(relExpr.rhs as ArithmeticExpression)»;
		«ELSE» return «generateConstTermBuilder(relExpr.rhs as BooleanExpression)»;
		«ENDIF»
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
		return «generateConstantExpression(relExpr)»;
	}
		
	«generateDependencyConstraints()»
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
		return «generateConstantExpression(boolExpr)»;
	}
	
	«generateDependencyConstraints()»	
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
	return «generateConstTermBuilder(constExpr)»;
}
	
@Override
protected List<Term> buildVariableLhs(«getContextParameter()») {
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