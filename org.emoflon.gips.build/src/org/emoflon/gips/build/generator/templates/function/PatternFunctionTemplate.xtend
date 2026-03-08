package org.emoflon.gips.build.generator.templates.function

import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.intermediate.GipsIntermediate.PatternFunction
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference

class PatternFunctionTemplate extends LinearFunctionTemplate<PatternFunction> {

	new(TemplateData data, PatternFunction context) {
		super(data, context)
	}

	override init() {
		packageName = data.apiData.gipsObjectivePkg
		className = data.function2functionClassName.get(context)

		filePath = data.apiData.gipsObjectivePkgPath + "/" + className + ".java"
		imports.add("java.util.List")
		imports.add("java.util.LinkedList")
		imports.add("org.emoflon.gips.core.GipsEngine")
		imports.add("org.emoflon.gips.core.gt.GipsPatternLinearFunction")
		imports.add("org.emoflon.gips.core.milp.model.Term")
		imports.add("org.emoflon.gips.core.milp.model.Constant")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.PatternFunction")
		imports.add(data.apiData.gipsApiPkg + "." + data.gipsApiClassName)
		imports.add(data.apiData.matchesPkg + "." + data.ibex2matchClassName.get(context.pattern))
		imports.add(data.apiData.rulesPkg + "." + data.ibex2ibexClassName.get(context.pattern))
	}

	override String generateClassContent() {
		'''
			public class «className» extends GipsPatternLinearFunction<«data.gipsApiClassName», «data.ibex2matchClassName.get(context.pattern)», «data.ibex2ibexClassName.get(context.pattern)»>{
				public «className»(final «data.gipsApiClassName» engine, final PatternFunction function, final «data.ibex2ibexClassName.get(context.pattern)» pattern) {
					super(engine, function, pattern);
				}
				
				«generateObjective(context.expression)»
				
				«FOR methods : builderMethodDefinitions.values»
					«methods»
				«ENDFOR»
				
				«getConstantCalculators(context.constants)»
			}
		'''
	}

	override String generateVariableAccess(VariableReference varRef) {
		if(isMappingVariable(varRef))
			throw new UnsupportedOperationException("Mapping context access is not possible within a pattern context.")

		return generateVariableAccess(varRef)
	}

	override String getContextParameterType() {
		return data.ibex2matchClassName.get(context.pattern)
	}
}
