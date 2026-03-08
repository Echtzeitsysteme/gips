package org.emoflon.gips.build.generator.templates.function

import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.intermediate.GipsIntermediate.RuleFunction
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference

class RuleFunctionTemplate extends LinearFunctionTemplate<RuleFunction> {

	new(TemplateData data, RuleFunction context) {
		super(data, context)
	}

	override init() {
		packageName = data.apiData.gipsObjectivePkg
		className = data.function2functionClassName.get(context)

		filePath = data.apiData.gipsObjectivePkgPath + "/" + className + ".java"
		imports.add("java.util.List")
		imports.add("java.util.LinkedList")
		imports.add("org.emoflon.gips.core.GipsEngine")
		imports.add("org.emoflon.gips.core.gt.GipsRuleLinearFunction")
		imports.add("org.emoflon.gips.core.milp.model.Term")
		imports.add("org.emoflon.gips.core.milp.model.Constant")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.RuleFunction")
		imports.add(data.apiData.gipsApiPkg + "." + data.gipsApiClassName)
		imports.add(data.apiData.matchesPkg + "." + data.ibex2matchClassName.get(context.rule))
		imports.add(data.apiData.rulesPkg + "." + data.ibex2ibexClassName.get(context.rule))
	}

	override String generateClassContent() {
		'''
			public class «className» extends GipsRuleLinearFunction<«data.gipsApiClassName», «data.ibex2matchClassName.get(context.rule)», «data.ibex2ibexClassName.get(context.rule)»>{
				public «className»(final «data.gipsApiClassName» engine, final RuleFunction function, final «data.ibex2ibexClassName.get(context.rule)» rule) {
					super(engine, function, rule);
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
			
		return super.generateVariableAccess(varRef)
	}

	override String getContextParameterType() {
		return data.ibex2matchClassName.get(context.rule)
	}
}
