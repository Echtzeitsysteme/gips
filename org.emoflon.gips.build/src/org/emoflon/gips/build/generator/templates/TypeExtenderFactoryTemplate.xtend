package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediateModel
import org.emoflon.gips.build.generator.TemplateData

class TypeExtenderFactoryTemplate extends GeneratorTemplate<GipsIntermediateModel> {

	new(TemplateData data, GipsIntermediateModel context) {
		super(data, context)
	}

	override init() {
		packageName = data.apiData.gipsApiPkg;
		className = data.typeExtensionFactoryClassName
		fqn = packageName + "." + className
		filePath = data.apiData.gipsApiPkgPath + "/" + className + ".java"

		imports.add("org.emoflon.gips.core.api.GipsTypeExtenderFactory")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.TypeExtension")
		imports.add("org.emoflon.gips.core.GipsTypeExtender")

		imports.add(data.apiData.apiPkg + "." + data.apiData.apiClass)
		imports.add(data.apiData.gipsApiPkg + "." + data.gipsApiClassName)

		data.model.extendedTypes
			.map[m | data.typeExtension2extenderClassName.get(m)]
			.forEach[m | imports.add(data.apiData.gipsTypeExtensionPkg+"."+m)]
	}

	override generate() {
		code = '''
/*
* Generated org.emoflon.gips.build.generator.templates.TypeExtensionFactoryTemplate
*/ 	
package «packageName»;

«FOR imp : imports»
	import «imp»;
«ENDFOR»

public class «className» extends GipsTypeExtenderFactory<«data.gipsApiClassName», «data.apiData.apiClass»> {
	public «className»(final «data.gipsApiClassName» engine, final «data.apiData.apiClass» eMoflonApi) {
		super(engine, eMoflonApi);
	}

	@Override
	public GipsTypeExtender<?, ?> createTypeExtender(final TypeExtension typeExtension) {
		«IF context.extendedTypes.empty»
			throw new IllegalArgumentException("Unknown TypeExtension: "+typeExtension);
		«ELSE»		
			return switch(typeExtension.getName()){
				«FOR typeExtension : context.extendedTypes»
				case "«typeExtension.name»" -> new «data.typeExtension2extenderClassName.get(typeExtension)»(engine, eMoflonApi, typeExtension);
				«ENDFOR»
				default -> throw new IllegalArgumentException("Unknown TypeExtension: "+typeExtension);	
			};		
		«ENDIF»
	}

}'''
	}

}
