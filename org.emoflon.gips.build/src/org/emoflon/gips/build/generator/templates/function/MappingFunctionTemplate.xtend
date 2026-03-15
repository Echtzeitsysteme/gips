package org.emoflon.gips.build.generator.templates.function

import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.intermediate.GipsIntermediate.MappingFunction
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference

class MappingFunctionTemplate extends LinearFunctionTemplate<MappingFunction> {

	new(TemplateData data, MappingFunction context) {
		super(data, context)
	}

	override init() {
		packageName = data.apiData.gipsObjectivePkg
		className = data.function2functionClassName.get(context)

		filePath = data.apiData.gipsObjectivePkgPath + "/" + className + ".java"
		imports.add("java.util.List")
		imports.add("java.util.LinkedList")
		imports.add("org.emoflon.gips.core.GipsEngine")
		imports.add("org.emoflon.gips.core.GipsMappingLinearFunction")
		imports.add("org.emoflon.gips.core.milp.model.Term")
		imports.add("org.emoflon.gips.core.milp.model.Constant")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.MappingFunction")
		imports.add(data.apiData.gipsApiPkg + "." + data.gipsApiClassName)
		imports.add(data.apiData.gipsMappingPkg + "." + data.mapping2mappingClassName.get(context.mapping))
	}

	override String generateClassContent() {
		'''
			public class «className» extends GipsMappingLinearFunction<«data.gipsApiClassName», «data.mapping2mappingClassName.get(context.mapping)»>{
				public «className»(final «data.gipsApiClassName» engine, final MappingFunction function) {
					super(engine, function);
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
		if(isMappingVariable(varRef)) {
			if(context.mapping.mappingVariable.equals(varRef.variable)) {
				return '''context'''
			}

			throw new UnsupportedOperationException(
				"Foreign mapping context access is not possible within another mapping context.")
		}

		return super.generateVariableAccess(varRef)
	}

	override String getContextParameterType() {
		return data.mapping2mappingClassName.get(context.mapping)
	}

}
