package org.emoflon.gips.build.generator.templates.function

import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.build.generator.templates.ProblemGeneratorTemplate
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticBinaryExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticBinaryOperator
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticUnaryExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantReference
import org.emoflon.gips.intermediate.GipsIntermediate.LinearFunction
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression

abstract class LinearFunctionTemplate<OBJECTIVE extends LinearFunction> extends ProblemGeneratorTemplate<OBJECTIVE> {
	new(TemplateData data, OBJECTIVE context) {
		super(data, context)
	}

	override getConstants() {
		return context.constants;
	}

	def String generateObjective(ArithmeticExpression expr) {
		computeTermBuilder(expr)
		'''
			@Override
			protected void buildTerms(«getContextParameter()») {
				«generateConstantFields(context.constants)»
				
				«FOR instruction : builderMethodCalls2»
					«instruction»
				«ENDFOR»
			}
		'''
	}

	def void computeTermBuilder(ArithmeticExpression expr) {
		if(GipsTransformationUtils.isConstantExpression(expr)) {
			builderMethodCalls2.add('''constantTerms.add(new Constant(«generateConstantExpression(expr)»));''')
			return
		}

		if(expr instanceof ArithmeticBinaryExpression) {
			if(expr.operator == ArithmeticBinaryOperator.ADD) {
				computeTermBuilder(expr.lhs)
				computeTermBuilder(expr.rhs)
			} else if(expr.operator == ArithmeticBinaryOperator.SUBTRACT) {
				throw new UnsupportedOperationException("Code generator does not support subtraction of variables.");
			} else {
				val variable = GipsTransformationUtils.extractVariableReference(expr);
				if(variable.size > 1)
					throw new UnsupportedOperationException(
						"Access to multiple different variables in the same product is forbidden.");

				val builderMethodName = createBuilderMethod(expr, builderMethodCalls2)
				val instruction = '''terms.add(new Term(«generateVariableAccess(variable.iterator.next)», «builderMethodName»(context«IF !getConstants().empty», «ENDIF»«getCallParametersForConstants(getConstants())»)));'''
				builderMethodCalls2.add(instruction)
			}
		} else if(expr instanceof ArithmeticUnaryExpression) {
			val variable = GipsTransformationUtils.extractVariableReference(expr);
			if(variable.size > 1)
				throw new UnsupportedOperationException(
					"Access to multiple different variables in the same product is forbidden.");

			val builderMethodName = createBuilderMethod(expr, builderMethodCalls2)
			val instruction = '''terms.add(new Term(«generateVariableAccess(variable.iterator.next)», «builderMethodName»(context«IF !getConstants().empty», «ENDIF»«getCallParametersForConstants(getConstants())»)));'''
			builderMethodCalls2.add(instruction)
		} else if(expr instanceof ConstantReference) {
			createBuilderMethod(expr, builderMethodCalls2)
		} else {
			createBuilderMethod(expr as ValueExpression, builderMethodCalls2)
		}
	}

}
