package org.emoflon.roam.build.generator.templates

import java.util.HashMap
import java.util.HashSet
import java.util.LinkedList
import org.eclipse.emf.ecore.EObject
import org.emoflon.roam.build.generator.GeneratorTemplate
import org.emoflon.roam.build.generator.TemplateData
import org.emoflon.roam.build.transformation.helper.ArithmeticExpressionType
import org.emoflon.roam.build.transformation.helper.RoamTransformationUtils
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticExpression
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticLiteral
import org.emoflon.roam.intermediate.RoamIntermediate.ArithmeticValue
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticExpression
import org.emoflon.roam.intermediate.RoamIntermediate.BinaryArithmeticOperator
import org.emoflon.roam.intermediate.RoamIntermediate.DoubleLiteral
import org.emoflon.roam.intermediate.RoamIntermediate.GlobalObjective
import org.emoflon.roam.intermediate.RoamIntermediate.IntegerLiteral
import org.emoflon.roam.intermediate.RoamIntermediate.Objective
import org.emoflon.roam.intermediate.RoamIntermediate.ObjectiveFunctionValue
import org.emoflon.roam.intermediate.RoamIntermediate.SetOperation
import org.emoflon.roam.intermediate.RoamIntermediate.UnaryArithmeticExpression
import org.emoflon.roam.intermediate.RoamIntermediate.ValueExpression

class GlobalObjectiveTemplate extends GeneratorTemplate<GlobalObjective> {

	protected val referencedObjectives = new HashSet<Objective>;
	protected val iterator2variableName = new HashMap<SetOperation, String>();
	protected val builderMethodNames = new HashSet<String>
	protected val builderMethods = new HashMap<EObject,String>
	protected val builderMethodDefinitions = new HashMap<EObject,String>
	protected val builderMethodCalls = new LinkedList<String>

	new(TemplateData data, GlobalObjective context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.apiData.roamObjectivePkg
		className = data.globalObjectiveClassName
		fqn = packageName + "." + className;
		filePath = data.apiData.roamObjectivePkgPath + "/" + className + ".java"
		imports.add("java.util.List")
		imports.add("java.util.LinkedList")
		imports.add("org.emoflon.roam.core.RoamEngine")
		imports.add("org.emoflon.roam.core.RoamGlobalObjective")
		imports.add("org.emoflon.roam.core.ilp.ILPTerm")
		imports.add("org.emoflon.roam.core.ilp.ILPConstant")
		imports.add("org.emoflon.roam.core.ilp.ILPLinearFunction")
		imports.add("org.emoflon.roam.core.ilp.ILPWeightedLinearFunction")
		imports.add("org.emoflon.roam.core.ilp.ILPNestedLinearFunction")
		imports.add("org.emoflon.roam.intermediate.RoamIntermediate.GlobalObjective")
	}
	
	override generate() {
		val classContent = generateClassContent()
		val attributes = generateAttributes();
		val initAttributes = generateInitAttributes();
		val importStatements = generateImports();
		code = '''«generatePackageDeclaration»

«importStatements»

public class «className» extends RoamGlobalObjective{
	
	«attributes»
	
	public «className»(final RoamEngine engine, final GlobalObjective objective) {
		super(engine, objective);
	}
	
	«initAttributes»
	
	«classContent»
	
	«FOR methods : builderMethodDefinitions.values»
	«methods»
	«ENDFOR»
}'''
	}
	
	def String generatePackageDeclaration() {
		return '''package «packageName»;'''
	}
	
	def String generateImports() {
		return '''«FOR imp : imports»
import «imp»;
«ENDFOR»'''
	}
	
	def String generateAttributes() {
			referencedObjectives.forEach[o | imports.add(data.apiData.roamObjectivePkg+"."+data.objective2objectiveClassName.get(o))]
		return '''«FOR obj : referencedObjectives»
	protected «data.objective2objectiveClassName.get(obj)» «obj.name.toFirstLower»;
«ENDFOR»'''
	}
	
	def String generateInitAttributes() {
		return '''@Override
protected void initLocalObjectives() {
	«FOR obj : referencedObjectives»
	«obj.name.toFirstLower» = («data.objective2objectiveClassName.get(obj)») engine.getObjectives().get("«obj.name»");
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
	
	def void generateTermBuilder(ArithmeticExpression expr) {
		if(expr instanceof BinaryArithmeticExpression) {
			if(expr.operator == BinaryArithmeticOperator.ADD) {
				generateTermBuilder(expr.lhs)
				generateTermBuilder(expr.rhs)
			} else if(expr.operator == BinaryArithmeticOperator.SUBTRACT) {
				throw new UnsupportedOperationException("Code generator does not support subtraction expressions.");
			} else {	
				val builderMethodName = generateBuilder(expr)
				var instruction = ""
				if(RoamTransformationUtils.containsContextExpression(expr) == ArithmeticExpressionType.constant) {
					instruction = '''constantTerms.add(new ILPConstant<Double>(«builderMethodName»()));'''
				} else {
					val objectives = RoamTransformationUtils.extractObjective(expr);
					if(objectives.size != 1)
						throw new UnsupportedOperationException("Access to multiple different objective functions in the same product is forbidden.");
					
					val objective = objectives.iterator.next
					referencedObjectives.add(objective)
					instruction = '''weightedTerms.add(new ILPWeightedLinearFunction<Integer>(«objective.name».getObjectiveFunction(), «builderMethodName»()));'''
				}
				builderMethodCalls.add(instruction)
			}
		} else if(expr instanceof UnaryArithmeticExpression) {
				val builderMethodName = generateBuilder(expr)
				var instruction = ""
				if(RoamTransformationUtils.containsContextExpression(expr) == ArithmeticExpressionType.constant) {
					instruction = '''constantTerms.add(new ILPConstant<Double>(«builderMethodName»()));'''
				} else {
					val objectives = RoamTransformationUtils.extractObjective(expr);
					if(objectives.size != 1)
						throw new UnsupportedOperationException("Access to multiple different objective functions in the same product is forbidden.");
					
					val objective = objectives.iterator.next
					referencedObjectives.add(objective)
					instruction = '''weightedTerms.add(new ILPWeightedLinearFunction<Integer>(«objective.name».getObjectiveFunction(), «builderMethodName»()));'''
				}
				builderMethodCalls.add(instruction)
		} else if(expr instanceof ArithmeticValue) {
				if(RoamTransformationUtils.containsContextExpression(expr) == ArithmeticExpressionType.constant) {
					throw new UnsupportedOperationException("Access to mappings, contexts and iterators not allowed.");
				} else {
					val objectives = RoamTransformationUtils.extractObjective(expr);
					if(objectives.size != 1)
						throw new UnsupportedOperationException("Access to multiple different objective functions in the same product is forbidden.");
						
					val objective = objectives.iterator.next
					referencedObjectives.add(objective)
					val instruction = '''weightedTerms.add(new ILPWeightedLinearFunction<Integer>(«objective.name».getObjectiveFunction(), 1.0));'''
					builderMethodCalls.add(instruction)
				}
		} else {
			if(expr instanceof IntegerLiteral) {
				val instruction = '''constantTerms.add(new ILPConstant<Double>((double)«expr.literal»));'''
				builderMethodCalls.add(instruction);
			} else {
				val doubleLit = expr as DoubleLiteral
				val instruction = '''constantTerms.add(new ILPConstant<Double>(«doubleLit.literal»));'''
				builderMethodCalls.add(instruction);
			}
		}
	}
	
	def String generateBuilder(BinaryArithmeticExpression expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		val method = '''
	protected double «methodName»() {
		return (double) «parseExpression(expr, ExpressionContext.varConstraint)»;
	}
		'''
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	def String generateBuilder(UnaryArithmeticExpression expr) {
		val methodName = '''builder_«builderMethods.size»'''
		builderMethods.put(expr, methodName)
		val method = '''
	protected double «methodName»() {
		return «parseExpression(expr, ExpressionContext.varConstraint)»;
	}
		'''
		builderMethodDefinitions.put(expr, method)
		return methodName
	}
	
	def String parseExpression(ArithmeticExpression expr, ExpressionContext contextType) {
		if(expr instanceof BinaryArithmeticExpression) {
			switch(expr.operator) {
				case ADD: {
					return '''«parseExpression(expr.lhs, contextType)» + «parseExpression(expr.rhs, contextType)»'''
				}
				case DIVIDE: {
					return '''«parseExpression(expr.lhs, contextType)» / «parseExpression(expr.rhs, contextType)»'''
				}
				case MULTIPLY: {
					return '''«parseExpression(expr.lhs, contextType)» * «parseExpression(expr.rhs, contextType)»'''
				}
				case POW: {
					return '''Math.pow(«parseExpression(expr.lhs, contextType)», «parseExpression(expr.rhs, contextType)»'''
				}
				case SUBTRACT: {
					return '''«parseExpression(expr.lhs, contextType)» - «parseExpression(expr.rhs, contextType)»'''
				}
			}
		} else if(expr instanceof UnaryArithmeticExpression) {
			switch(expr.operator) {
				case ABSOLUTE: {
					return '''Math.abs(«parseExpression(expr.expression, contextType)»)'''
				}
				case BRACKET: {
					return '''(«parseExpression(expr.expression, contextType)»)'''
				}
				case COSINE: {
					return '''Math.cos(«parseExpression(expr.expression, contextType)»)'''
				}
				case NEGATE: {
					return '''-«parseExpression(expr.expression, contextType)»'''
				}
				case SINE: {
					return '''Math.sin(«parseExpression(expr.expression, contextType)»)'''
				}
				case SQRT: {
					return '''Math.sqrt(«parseExpression(expr.expression, contextType)»)'''
				}
			}
		} else if(expr instanceof ArithmeticLiteral) {
			if(expr instanceof DoubleLiteral) {
				return String.valueOf(expr.literal)
			} else {
				return String.valueOf((expr as IntegerLiteral).literal)
			}
		} else {
			val value = expr as ArithmeticValue
			switch(contextType) {
				case constConstraint: {
					return parseExpression(value.value, ExpressionContext.constConstraint);
				}
				case constStream: {
					return parseExpression(value.value, ExpressionContext.constStream);
				}
				case varConstraint: {
					return parseExpression(value.value, ExpressionContext.varConstraint);
				}
				case varStream: {
					return parseExpression(value.value,  ExpressionContext.varStream);
				}
			}
		}
		
	}
	
	def String parseExpression(ValueExpression constExpr, ExpressionContext contextType) {
		switch(contextType) {
			case constConstraint: {
				return parseConstExpression(constExpr);
			}
			case constStream: {
				return parseConstStreamExpression(constExpr);
			}
			case varConstraint: {
				return parseVarExpression(constExpr)
			}
			case varStream: {
				return parseVarStreamExpression(constExpr)
			}
		}
	}
	
	def String parseConstExpression(ValueExpression constExpr) {
		if(constExpr instanceof ObjectiveFunctionValue) {
			//This should have been taken care of already. -> Constant 1 doesn't hurt... 
			return '''1.0'''
		} else {
			throw new UnsupportedOperationException("Access to mappings, contexts and iterators not allowed.");
		}
	}
	
	def String parseConstStreamExpression(ValueExpression constExpr) {
		if(constExpr instanceof ObjectiveFunctionValue) {
			//This should have been taken care of already. -> Constant 1 doesn't hurt... 
			return '''1.0'''
		} else {
			throw new UnsupportedOperationException("Access to mappings, contexts and iterators not allowed.");
		}
	}
	
	def String parseVarExpression(ValueExpression varExpr) {
		if(varExpr instanceof ObjectiveFunctionValue) {
			//This should have been taken care of already. -> Constant 1 doesn't hurt... 
			return '''1.0'''
		} else {
			throw new UnsupportedOperationException("Access to mappings, contexts and iterators not allowed.");
		}
	}
	
	def String parseVarStreamExpression(ValueExpression varExpr) {
		if(varExpr instanceof ObjectiveFunctionValue) {
			//This should have been taken care of already. -> Constant 1 doesn't hurt... 
			return '''1.0'''
		}else {
			throw new UnsupportedOperationException("Access to mappings, contexts and iterators not allowed.");
		}
	}
	
}