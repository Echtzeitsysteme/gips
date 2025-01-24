package org.emoflon.gips.build.generator.templates

import java.util.HashMap
import java.util.HashSet
import java.util.LinkedList
import org.eclipse.emf.ecore.EObject
import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.build.transformation.helper.ArithmeticExpressionType
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.Objective
import org.emoflon.gips.intermediate.GipsIntermediate.SetOperation
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticBinaryExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticBinaryOperator
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticUnaryExpression
import org.emoflon.gips.intermediate.GipsIntermediate.LinearFunction
import org.emoflon.gips.intermediate.GipsIntermediate.LinearFunctionReference
import org.emoflon.gips.intermediate.GipsIntermediate.Variable

class ObjectiveTemplate extends ProblemGeneratorTemplate<Objective> {

	protected val referencedObjectives = new HashSet<LinearFunction>;
	protected val iterator2variableName = new HashMap<SetOperation, String>();
	protected val builderMethodNames = new HashSet<String>
	protected val builderMethods = new HashMap<EObject,String>
	protected val builderMethodDefinitions = new HashMap<EObject,String>
	protected val builderMethodCalls = new LinkedList<String>

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
	«obj.name.toFirstLower» = («data.function2functionClassName.get(obj)») engine.getObjectives().get("«obj.name»");
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
	«FOR instruction : builderMethodCalls»
	«instruction»
	«ENDFOR»
}'''
	}
		
	override getVariable(Variable variable) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override getContextParameterType() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	def void generateTermBuilder(ArithmeticExpression expr) {
		if(GipsTransformationUtils.isConstantExpression(expr)  == ArithmeticExpressionType.constant) {
			builderMethodCalls2.add('''constantTerms.add(new ILPConstant(«generateConstantExpression(expr)»(context)));''')
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
				val instruction = '''weightedFunctions.add(new ILPWeightedLinearFunction(«function.name».getObjectiveFunction(), «builderMethodName»()));'''
				builderMethodCalls2.add(instruction)
			}
		} else if(expr instanceof ArithmeticUnaryExpression) {
				val functions = GipsTransformationUtils.extractLinearFunction(expr);
					if(functions.size != 1)
						throw new UnsupportedOperationException("Access to multiple different objective functions in the same product is forbidden.");
				
				val function = functions.iterator.next
				referencedObjectives.add(function)
				val builderMethodName = generateBuilder(expr, builderMethodCalls2)
				val instruction = '''weightedFunctions.add(new ILPWeightedLinearFunction(«function.name».getObjectiveFunction(), «builderMethodName»()));'''
				builderMethodCalls2.add(instruction)
		} else if(expr instanceof LinearFunctionReference) {
			builderMethodCalls2.add('''weightedFunctions.add(new ILPWeightedLinearFunction(«expr.function.name».getObjectiveFunction(), 1.0));''')
		} else {
			generateBuilder(expr as ValueExpression, builderMethodCalls2)
		}
	}
	
}