package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.intermediate.GipsIntermediate.TypeExtension
import org.emoflon.gips.build.generator.TemplateData

class TypeExtenderTemplate extends GeneratorTemplate<TypeExtension> {
	
	new(TemplateData data, TypeExtension context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.apiData.gipsTypeExtensionPkg;
		className = data.typeExtension2extenderClassName.get(context)
		fqn = packageName + "." + className
		filePath = data.apiData.gipsTypeExtensionPkgPath + "/" + className + ".java"
		
		imports.add(data.apiData.apiPkg+"."+data.apiData.apiClass)
		imports.add(data.apiData.gipsApiPkg+"."+data.gipsApiClassName)
//		imports.add(data.apiData.gipsTypeExtensionPkg+"."+data.typeExtension2extensionClassName.get(context))
		
		imports.add("org.emoflon.gips.core.GipsTypeExtender")
		imports.add("org.emoflon.gips.intermediate.GipsIntermediate.TypeExtension")
		imports.add("org.emoflon.gips.core.milp.model.Variable")
		imports.add("java.util.Map");
		
		imports.addAll(data.classToPackage.getImportsForType(context.extendedType))
	}
	
	override generate() {
		code = '''
/*
* Generated org.emoflon.gips.build.generator.templates.TypeExtensionTemplate
*/ 			
package «packageName»;

«FOR imp : imports»
import «imp»;
«ENDFOR»

public class «className» extends GipsTypeExtender<«context.extendedType.name», «data.typeExtension2extensionClassName.get(context)»> {
	
	public «className»(final «data.gipsApiClassName» engine, final «data.apiData.apiClass» eMoflonApi, TypeExtension typeExtension) {
		super(engine, eMoflonApi, typeExtension);
	}
	
	protected «data.typeExtension2extensionClassName.get(context)» buildTypeExtension(«context.extendedType.name» context, Map<String, Variable<?>> milpVariables){
		return new «data.typeExtension2extensionClassName.get(context)»(context, milpVariables);
	}

}
'''
	}
	
}