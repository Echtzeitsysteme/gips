package org.emoflon.gips.build.generator.templates.function

import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils
import org.emoflon.gips.build.transformation.helper.ArithmeticExpressionType
import org.emoflon.gips.build.generator.templates.ProblemGeneratorTemplate
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticBinaryExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticBinaryOperator
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticUnaryExpression
import org.emoflon.gips.intermediate.GipsIntermediate.LinearFunction
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantReference
import org.emoflon.gips.build.GipsAPIData

abstract class LinearFunctionTemplate <OBJECTIVE extends LinearFunction> extends ProblemGeneratorTemplate<OBJECTIVE> {
	new(GipsAPIData data, OBJECTIVE context) {
		super(data, context)
	}
	
	override getConstants() {
		return context.constants;
	}
	
	override generate() {
		val classContent = generateClassContent()
		val importStatements = generateImports();
		code = '''«generatePackageDeclaration»		

«importStatements»

«classContent»
'''
	}
	
	def String generateClassContent();
	
	def String generateObjective(ArithmeticExpression expr) {
		generateTermBuilder(expr)
		return '''
@Override
protected void buildTerms(«getContextParameter()») {
	«getConstantFields(context.constants)»
	
	«FOR instruction : builderMethodCalls2»
	«instruction»
	«ENDFOR»
}
		'''
	}
	
	def void generateTermBuilder(ArithmeticExpression expr) {
		if(GipsTransformationUtils.isConstantExpression(expr)  == ArithmeticExpressionType.constant) {
			builderMethodCalls2.add('''constantTerms.add(new Constant(«generateConstantExpression(expr)»));''')
			return
		}
		
		if(expr instanceof ArithmeticBinaryExpression) {
			if(expr.operator == ArithmeticBinaryOperator.ADD) {
				generateTermBuilder(expr.lhs)
				generateTermBuilder(expr.rhs)
			} else if(expr.operator == ArithmeticBinaryOperator.SUBTRACT) {
				throw new UnsupportedOperationException("Code generator does not support subtraction of variables.");
			} else {
				val variable = GipsTransformationUtils.extractVariable(expr);
				if(variable.size > 1)
					throw new UnsupportedOperationException("Access to multiple different variables in the same product is forbidden.");
					
				val builderMethodName = generateBuilder(expr, builderMethodCalls2)
				val instruction = '''terms.add(new Term(«getVariable(variable.iterator.next)», «builderMethodName»(context«IF !getConstants().empty», «ENDIF»«getCallParametersForConstants(getConstants())»)));'''
				builderMethodCalls2.add(instruction)
			}
		} else if(expr instanceof ArithmeticUnaryExpression) {
				val variable = GipsTransformationUtils.extractVariable(expr);
				if(variable.size > 1)
					throw new UnsupportedOperationException("Access to multiple different variables in the same product is forbidden.");
				
				val builderMethodName = generateBuilder(expr, builderMethodCalls2)
				val instruction = '''terms.add(new Term(«getVariable(variable.iterator.next)», «builderMethodName»(context«IF !getConstants().empty», «ENDIF»«getCallParametersForConstants(getConstants())»)));'''
				builderMethodCalls2.add(instruction)
		} else if(expr instanceof ConstantReference) {
			generateBuilder(expr, builderMethodCalls2)
		} else {
			generateBuilder(expr as ValueExpression, builderMethodCalls2)
		}
	}
	
}