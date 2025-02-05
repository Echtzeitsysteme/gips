package org.emoflon.gips.build.generator.templates.function

import org.emoflon.gips.intermediate.GipsIntermediate.PatternFunction
import org.emoflon.gips.intermediate.GipsIntermediate.Variable
import org.emoflon.gips.build.GipsAPIData

class PatternFunctionTemplate extends LinearFunctionTemplate<PatternFunction> {

	new(GipsAPIData data, PatternFunction context) {
		super(data, context)
	}

	override init() {
		packageName = data.gipsObjectivePkg
		className = data.function2functionClassName.get(context)
		fqn = packageName + "." + className;
		filePath = data.gipsObjectivePkgPath + "/" + className + ".java"
		imports.add("java.util.List")
		imports.add("java.util.LinkedList")
		imports.add("org.emoflon.gips.core.GipsEngine")
		imports.add("org.emoflon.gips.core.gt.GipsPatternLinearFunction")
		imports.add("org.emoflon.gips.core.milp.model.Term")
		imports.add("org.emoflon.gips.core.milp.model.Constant")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.PatternFunction")
		imports.add(data.gipsApiPkg+"."+data.gipsApiClassName)
		imports.add(data.matchPackage+"."+data.ibex2matchClassName.get(context.pattern))
		imports.add(data.rulePackage+"."+data.ibex2ibexClassName.get(context.pattern))
	}
	
	override String generatePackageDeclaration() {
		return '''package «packageName»;'''
	}
	
	override String generateImports() {
		return '''«FOR imp : imports»
import «imp»;
«ENDFOR»'''
	}
	
	override String generateClassContent() {
		return '''
public class «className» extends GipsPatternLinearFunction<«data.gipsApiClassName», «data.ibex2matchClassName.get(context.pattern)», «data.ibex2ibexClassName.get(context.pattern)»>{
	public «className»(final «data.gipsApiClassName» engine, final PatternFunction function, final «data.ibex2ibexClassName.get(context.pattern)» pattern) {
		super(engine, function, pattern);
	}
	
	«generateObjective(context.expression)»
	
	«FOR methods : builderMethodDefinitions.values»
	«methods»
	«ENDFOR»
	
	«getConstantCalculators(context.constants)»
}'''
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