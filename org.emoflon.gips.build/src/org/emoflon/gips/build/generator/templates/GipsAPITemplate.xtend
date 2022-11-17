package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.build.generator.GeneratorTemplate
import org.emoflon.gips.build.generator.GipsApiData
import org.emoflon.ibex.common.engine.IBeXPMEngineInformation

class GipsAPITemplate extends GeneratorTemplate<IBeXPMEngineInformation> {
	
	new(GipsApiData data, IBeXPMEngineInformation context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.gipsApiPkg;
		className = data.gipsApiClassNames.get(context)
		fqn = packageName + "." + className;
		filePath = data.gipsApiPkgPath + "/" + className + ".java"
		imports.add("org.emoflon.gips.core.api.GipsEngineAPI")
		imports.add(data.apiPackage + "." + data.apiAbstractClassName)
		imports.add(data.apiPackage + "." + data.apiClassNames.get(context))
	}
	
	override generate() {
		code = '''package «packageName»;
		
«FOR imp : imports»
import «imp»;
«ENDFOR»
		
public class «data.gipsApiClassNames.get(context)» extends «data.gipsApiClassName» <«data.apiClassNames.get(context)»>{
	
	public «data.gipsApiClassNames.get(context)»() {
		super(new «data.apiClassNames.get(context)»());
	}
	
}'''
	}
	
}