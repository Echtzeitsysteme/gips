package org.emoflon.gips.build.generator.templates.function

import org.emoflon.gips.build.generator.templates.GeneratorTemplate
import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel
import org.emoflon.gips.intermediate.GipsIntermediate.PatternFunction
import org.emoflon.gips.intermediate.GipsIntermediate.RuleFunction
import org.emoflon.gips.build.GipsAPIData

class FunctionFactoryTemplate extends GeneratorTemplate<GipsIntermediateModel> {
	
	new(GipsAPIData data, GipsIntermediateModel context) {
		super(data, context)
	}

	override init() {
		packageName = data.gipsApiPkg
		className = data.functionFactoryClassName
		fqn = packageName + "." + className;
		filePath = data.gipsApiPkgPath + "/" + className + ".java"
		imports.add(data.apiPackage + "." + data.apiAbstractClassName)
		imports.add("org.emoflon.gips.core.api.GipsLinearFunctionFactory")
		imports.add("org.emoflon.gips.core.GipsEngine")
		imports.add("org.emoflon.gips.core.GipsLinearFunction")
		imports.add("org.emoflon.gips.core.GipsTypeLinearFunction")
		imports.add("org.emoflon.gips.core.GipsMappingLinearFunction")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.LinearFunction")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.PatternFunction")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.RuleFunction")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.MappingFunction")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.TypeFunction")
		imports.add(data.gipsApiPkg+"."+data.gipsApiClassName)
		data.function2functionClassName.values.forEach[o | imports.add(data.gipsObjectivePkg+"."+o)]
	}
	
	override generate() {
		code = '''package «packageName»;

«FOR imp : imports»
import «imp»;
«ENDFOR»

public class «className» <MOFLON_API extends «data.apiAbstractClassName»<?>> extends GipsLinearFunctionFactory<«data.gipsApiClassName»<MOFLON_API>, MOFLON_API> {
	public «className»(final «data.gipsApiClassName»<MOFLON_API> engine, final MOFLON_API eMoflonApi) {
		super(engine, eMoflonApi);
	}
	
	@Override
	public GipsLinearFunction<«data.gipsApiClassName»<MOFLON_API>, ? extends LinearFunction, ? extends Object> createLinearFunction(final LinearFunction function) {
		«IF context.functions.isNullOrEmpty»
		throw new IllegalArgumentException("Unknown linear function type: " + function);
		«ELSE»
		switch(function.getName()) {
			«FOR function : context.functions»
			case "«function.name»" -> {
				«IF function instanceof PatternFunction»
				return new «data.function2functionClassName.get(function)»<MOFLON_API>(engine, («function.eClass.name»)function, eMoflonApi.«function.pattern.name.toFirstLower»());
				«ELSEIF function instanceof RuleFunction»
				return new «data.function2functionClassName.get(function)»<MOFLON_API>(engine, («function.eClass.name»)function, eMoflonApi.«function.rule.name.toFirstLower»());
				«ELSE»
				return new «data.function2functionClassName.get(function)»<MOFLON_API>(engine, («function.eClass.name»)function);
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