package org.emoflon.gips.build.generator.templates

import java.util.HashMap
import java.util.HashSet
import java.util.LinkedList
import org.eclipse.emf.ecore.EObject
import org.emoflon.gips.build.generator.GeneratorTemplate
import org.emoflon.gips.build.transformation.helper.ArithmeticExpressionType
import org.emoflon.gips.build.transformation.helper.GipsTransformationUtils
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticLiteral
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticValue
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.BinaryArithmeticOperator
import org.emoflon.gips.intermediate.GipsIntermediate.DoubleLiteral
import org.emoflon.gips.intermediate.GipsIntermediate.GlobalObjective
import org.emoflon.gips.intermediate.GipsIntermediate.IntegerLiteral
import org.emoflon.gips.intermediate.GipsIntermediate.Objective
import org.emoflon.gips.intermediate.GipsIntermediate.ObjectiveFunctionValue
import org.emoflon.gips.intermediate.GipsIntermediate.SetOperation
import org.emoflon.gips.intermediate.GipsIntermediate.UnaryArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression
import org.emoflon.gips.build.generator.GipsApiData

class GlobalObjectiveTemplate extends GeneratorTemplate<GlobalObjective> {

	protected val referencedObjectives = new HashSet<Objective>;
	protected val iterator2variableName = new HashMap<SetOperation, String>();
	protected val builderMethodNames = new HashSet<String>
	protected val builderMethods = new HashMap<EObject,String>
	protected val builderMethodDefinitions = new HashMap<EObject,String>
	protected val builderMethodCalls = new LinkedList<String>

	new(GipsApiData data, GlobalObjective context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.gipsObjectivePkg
		className = data.globalObjectiveClassName
		fqn = packageName + "." + className;
		filePath = data.gipsObjectivePkgPath + "/" + className + ".java"
		imports.add("java.util.List")
		imports.add("java.util.LinkedList")
		imports.add("org.emoflon.gips.core.GipsEngine")
		imports.add("org.emoflon.gips.core.GipsGlobalObjective")
		imports.add("org.emoflon.gips.core.ilp.ILPTerm")
		imports.add("org.emoflon.gips.core.ilp.ILPConstant")
		imports.add("org.emoflon.gips.core.ilp.ILPLinearFunction")
		imports.add("org.emoflon.gips.core.ilp.ILPWeightedLinearFunction")
		imports.add("org.emoflon.gips.core.ilp.ILPNestedLinearFunction")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.GlobalObjective")
	}
	
	override generate() {
		val classContent = generateClassContent()
		val attributes = generateAttributes();
		val initAttributes = generateInitAttributes();
		val importStatements = generateImports();
		code = '''«generatePackageDeclaration»

«importStatements»

public class «className» extends GipsGlobalObjective{
	
	«attributes»
	
	public «className»(final GipsEngine engine, final GlobalObjective objective) {
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
			referencedObjectives.forEach[o | imports.add(data.gipsObjectivePkg+"."+data.objective2objectiveClassName.get(o))]
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
				if(GipsTransformationUtils.isConstantExpression(expr)  == ArithmeticExpressionType.constant) {
					instruction = '''constantTerms.add(new ILPConstant(«builderMethodName»()));'''
				} else {
					val objectives = GipsTransformationUtils.extractObjective(expr);
					if(objectives.size != 1)
						throw new UnsupportedOperationException("Access to multiple different objective functions in the same product is forbidden.");
					
					val objective = objectives.iterator.next
					referencedObjectives.add(objective)
					instruction = '''weightedFunctions.add(new ILPWeightedLinearFunction(«objective.name».getObjectiveFunction(), «builderMethodName»()));'''
				}
				builderMethodCalls.add(instruction)
			}
		} else if(expr instanceof UnaryArithmeticExpression) {
				val builderMethodName = generateBuilder(expr)
				var instruction = ""
				if(GipsTransformationUtils.isConstantExpression(expr)  == ArithmeticExpressionType.constant) {
					instruction = '''constantTerms.add(new ILPConstant(«builderMethodName»()));'''
				} else {
					val objectives = GipsTransformationUtils.extractObjective(expr);
					if(objectives.size != 1)
						throw new UnsupportedOperationException("Access to multiple different objective functions in the same product is forbidden.");
					
					val objective = objectives.iterator.next
					referencedObjectives.add(objective)
					instruction = '''weightedFunctions.add(new ILPWeightedLinearFunction(«objective.name».getObjectiveFunction(), «builderMethodName»()));'''
				}
				builderMethodCalls.add(instruction)
		} else if(expr instanceof ArithmeticValue) {
				if(GipsTransformationUtils.containsContextExpression(expr)) {
					throw new UnsupportedOperationException("Access to mappings, contexts and iterators not allowed.");
				} else {
					val objectives = GipsTransformationUtils.extractObjective(expr);
					if(objectives.size != 1)
						throw new UnsupportedOperationException("Access to multiple different objective functions in the same product is forbidden.");
						
					val objective = objectives.iterator.next
					referencedObjectives.add(objective)
					val instruction = '''weightedFunctions.add(new ILPWeightedLinearFunction(«objective.name».getObjectiveFunction(), 1.0));'''
					builderMethodCalls.add(instruction)
				}
		} else {
			if(expr instanceof IntegerLiteral) {
				val instruction = '''constantTerms.add(new ILPConstant((double)«expr.literal»));'''
				builderMethodCalls.add(instruction);
			} else {
				val doubleLit = expr as DoubleLiteral
				val instruction = '''constantTerms.add(new ILPConstant(«doubleLit.literal»));'''
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
					return '''Math.pow(«parseExpression(expr.lhs, contextType)», «parseExpression(expr.rhs, contextType)»)'''
				}
				case SUBTRACT: {
					return '''«parseExpression(expr.lhs, contextType)» - «parseExpression(expr.rhs, contextType)»'''
				}
				case LOG: {
					return '''Math.log(«parseExpression(expr.lhs, contextType)») / Math.log(«parseExpression(expr.rhs, contextType)»)'''
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
			} else if(expr instanceof IntegerLiteral) {
				return String.valueOf(expr.literal)
			} else {
				return '''null'''
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