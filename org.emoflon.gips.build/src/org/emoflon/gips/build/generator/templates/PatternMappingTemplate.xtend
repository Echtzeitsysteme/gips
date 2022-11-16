package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.build.generator.GeneratorTemplate
import org.emoflon.gips.intermediate.GipsIntermediate.PatternMapping
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern
import org.emoflon.gips.build.generator.GipsApiData

class PatternMappingTemplate extends GeneratorTemplate<PatternMapping> {
	
	GTPattern pattern;
	
	new(GipsApiData data, PatternMapping context) {
		super(data, context)
	}
	
	override init() {
		packageName = data.gipsMappingPkg
		className = data.mapping2mappingClassName.get(context)
		fqn = packageName + "." + className
		filePath = data.gipsMappingPkgPath + "/" + className + ".java"
		imports.add("org.emoflon.gips.core.gt.GTMapping")
		imports.add(data.rulePackage+"."+data.mapping2patternClassName.get(context))
		imports.add(data.matchPackage+"."+data.mapping2matchClassName.get(context))
		
		pattern = context.contextPattern
		pattern.signatureNodes.forEach[n | helper.addImportForType(n.type)]
	}
	
	override generate() {
		code = '''package «packageName»;
		
«FOR imp : imports»
import «imp»;
«ENDFOR»
		
public class «className» extends GTMapping<«data.mapping2matchClassName.get(context)», «data.mapping2patternClassName.get(context)»> {
	public «className»(final String ilpVariable, final «data.mapping2matchClassName.get(context)» match) {
		super(ilpVariable, match);
	}

	«FOR node : pattern.signatureNodes»
	public «node.type.name» get«node.name.toFirstUpper»() {
		return match.get«node.name.toFirstUpper»();
	}
	«ENDFOR»
}'''
	}

}