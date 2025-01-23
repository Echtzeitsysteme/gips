package org.emoflon.gips.build.generator.templates.function

import org.emoflon.gips.build.generator.templates.GeneratorTemplate
import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel
import org.emoflon.gips.intermediate.GipsIntermediate.PatternFunction
import org.emoflon.gips.intermediate.GipsIntermediate.RuleFunction

class FunctionFactoryTemplate extends GeneratorTemplate<GipsIntermediateModel> {
	
	new(TemplateData data, GipsIntermediateModel context) {
		super(data, context)
	}

	override init() {
		packageName = data.apiData.gipsApiPkg
		className = data.functionFactoryClassName
		fqn = packageName + "." + className;
		filePath = data.apiData.gipsApiPkgPath + "/" + className + ".java"
		imports.add(data.apiData.apiPkg + "." + data.apiData.apiClass)
		imports.add("org.emoflon.gips.core.api.GipsLinearFunctionFactory")
		imports.add("org.emoflon.gips.core.GipsEngine")
		imports.add("org.emoflon.gips.core.GipsLinearFunction")
		imports.add("org.emoflon.gips.core.GipsTypeLinearFunction")
		imports.add("org.emoflon.gips.core.GipsMappingLinearFunction")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.LinearFunction")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.PatternFunction")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.MappingFunction")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.TypeFunction")
		imports.add(data.apiData.gipsApiPkg+"."+data.gipsApiClassName)
		data.function2functionClassName.values.forEach[o | imports.add(data.apiData.gipsObjectivePkg+"."+o)]
	}
	
	override generate() {
		code = '''package «packageName»;

«FOR imp : imports»
import «imp»;
«ENDFOR»

public class «className» extends GipsObjectiveFactory<«data.gipsApiClassName», «data.apiData.apiClass»> {
	public «className»(final «data.gipsApiClassName» engine, final «data.apiData.apiClass» eMoflonApi) {
		super(engine, eMoflonApi);
	}
	
	@Override
	public GipsLinearFunction<«data.gipsApiClassName», ? extends LinearFunction, ? extends Object> createObjective(final LinearFunction function) {
		«IF context.functions.isNullOrEmpty»
		throw new IllegalArgumentException("Unknown linear function type: " + function);
		«ELSE»
		switch(objective.getName()) {
			«FOR function : context.functions»
			case "«function.name»" -> {
				«IF function instanceof PatternFunction»
				return new «data.function2functionClassName.get(function)»(engine, («function.eClass.name»)function, eMoflonApi.«function.pattern.name.toFirstLower»());
				«ELSEIF function instanceof RuleFunction»
				return new «data.function2functionClassName.get(function)»(engine, («function.eClass.name»)function, eMoflonApi.«function.rule.name.toFirstLower»());
				«ELSE»
				return new «data.function2functionClassName.get(function)»(engine, («function.eClass.name»)function);
				«ENDIF»
			}
			«ENDFOR»
			default -> {
				throw new IllegalArgumentException("Unknown linear function type: " + function);	
			}
		}
		«ENDIF»
			
	}
}'''
	}

	
}