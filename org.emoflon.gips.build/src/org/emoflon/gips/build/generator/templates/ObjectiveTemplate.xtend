package org.emoflon.gips.build.generator.templates

import java.util.HashSet
import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.build.transformation.helper.ArithmeticExpressionType
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.Objective
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticBinaryExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticBinaryOperator
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticUnaryExpression
import org.emoflon.gips.intermediate.GipsIntermediate.LinearFunction
import org.emoflon.gips.intermediate.GipsIntermediate.LinearFunctionReference
import org.emoflon.gips.intermediate.GipsIntermediate.Variable
import org.emoflon.gips.intermediate.GipsIntermediate.Constant

class ObjectiveTemplate extends ProblemGeneratorTemplate<Objective> {

	protected val referencedObjectives = new HashSet<LinearFunction>;

	new(TemplateData data, Objective context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.apiData.gipsObjectivePkg
		className = data.objectiveClassName
		fqn = packageName + "." + className;
		filePath = data.apiData.gipsObjectivePkgPath + "/" + className + ".java"
		imports.add("java.util.List")
		imports.add("java.util.LinkedList")
		imports.add("org.emoflon.gips.core.GipsEngine")
		imports.add("org.emoflon.gips.core.GipsObjective")
		imports.add("org.emoflon.gips.core.milp.model.Term")
		imports.add("org.emoflon.gips.core.milp.model.Constant")
		imports.add("org.emoflon.gips.core.milp.model.LinearFunction")
		imports.add("org.emoflon.gips.core.milp.model.WeightedLinearFunction")
		imports.add("org.emoflon.gips.core.milp.model.NestedLinearFunction")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.Objective")
	}
	
	override getConstants() {
		return context.constants;
	}
	
	override generate() {
		val classContent = generateClassContent()
		val attributes = generateAttributes();
		val initAttributes = generateInitAttributes();
		val importStatements = generateImports();
		code = '''«generatePackageDeclaration»

«importStatements»

public class «className» extends GipsObjective{
	
	«attributes»
	
	public «className»(final GipsEngine engine, final Objective objective) {
		super(engine, objective);
	}
	
	«initAttributes»
	
	«classContent»
	
	«FOR methods : builderMethodDefinitions.values»
	«methods»
	«ENDFOR»
}'''
	}
	
	override String generatePackageDeclaration() {
		return '''package «packageName»;'''
	}
	
	override String generateImports() {
		return '''«FOR imp : imports»
import «imp»;
«ENDFOR»'''
	}
	
	def String generateAttributes() {
			referencedObjectives.forEach[o | imports.add(data.apiData.gipsObjectivePkg+"."+data.function2functionClassName.get(o))]
		return '''«FOR obj : referencedObjectives»
	protected «data.function2functionClassName.get(obj)» «obj.name.toFirstLower»;
«ENDFOR»'''
	}
	
	def String generateInitAttributes() {
		return '''@Override
protected void initLocalObjectives() {
	«FOR obj : referencedObjectives»
	«obj.name.toFirstLower» = («data.function2functionClassName.get(obj)») engine.getLinearFunctions().get("«obj.name»");
	«ENDFOR»
}'''
	}
	
	def String generateClassContent() {
		return '''«generateObjective(context.expression)»'''		
	}
	
	def String generateObjective(ArithmeticExpression expr) {
		generateTermBuilder(expr)
		return '''@Override
protected void buildTerms() {
	«getConstantFields(context.constants)»
	
	«FOR instruction : builderMethodCalls2»
	«instruction»
	«ENDFOR»
	
	«getConstantCalculators(context.constants)»
}'''
	}
		
	override getVariable(Variable variable) {
		throw new UnsupportedOperationException("Direct variable access is not possible within the objective context.")
	}
	
	override String getContextParameterType() {
		return ""
	}
	
	override String getContextParameter() {
		return ""
	}
	
	override getCallConstantCalculator(Constant constant) {
		return '''calculate«constant.name.toFirstUpper»()'''
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
				val functions = GipsTransformationUtils.extractLinearFunction(expr);
					if(functions.size != 1)
						throw new UnsupportedOperationException("Access to multiple different objective functions in the same product is forbidden.");
					
				val function = functions.iterator.next
				referencedObjectives.add(function)
				val builderMethodName = generateBuilder(expr, builderMethodCalls2)
				val instruction = '''weightedFunctions.add(new WeightedLinearFunction(«function.name».getLinearFunctionFunction(), «builderMethodName»(«getCallParametersForConstants(getConstants())»)));'''
				builderMethodCalls2.add(instruction)
			}
		} else if(expr instanceof ArithmeticUnaryExpression) {
				val functions = GipsTransformationUtils.extractLinearFunction(expr);
					if(functions.size != 1)
						throw new UnsupportedOperationException("Access to multiple different objective functions in the same product is forbidden.");
				
				val function = functions.iterator.next
				referencedObjectives.add(function)
				val builderMethodName = generateBuilder(expr, builderMethodCalls2)
				val instruction = '''weightedFunctions.add(new WeightedLinearFunction(«function.name».getLinearFunctionFunction(), «builderMethodName»(«getCallParametersForConstants(getConstants())»)));'''
				builderMethodCalls2.add(instruction)
		} else if(expr instanceof LinearFunctionReference) {
			referencedObjectives.add(expr.function)
			builderMethodCalls2.add('''weightedFunctions.add(new WeightedLinearFunction(«expr.function.name».getLinearFunctionFunction(), 1.0));''')
		} else {
			generateBuilder(expr as ValueExpression, builderMethodCalls2)
		}
	}
	
}